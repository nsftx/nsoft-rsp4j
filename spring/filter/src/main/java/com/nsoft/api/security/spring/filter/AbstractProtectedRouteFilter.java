package com.nsoft.api.security.spring.filter;

import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.spring.filter.error.ErrorHandler;
import com.nsoft.api.security.spring.filter.exception.InvalidFilterConfigurationException;
import com.nsoft.api.security.spring.filter.route.ProtectedRouteHandler;
import com.nsoft.api.security.spring.filter.route.ProtectedRouteRegistry;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A {@link javax.servlet.Filter} abstraction used to limit interaction with specified REST routes.
 * In order to add the {@link javax.servlet.Filter} implementation to the {@link FilterChain}, the
 * implementor must annotate their implementation class with {@link org.springframework.stereotype.Component}
 * <p>
 * The implementor is expected to configure the Filter via the {@link #configureFilter} method.
 * <p>
 * The implementor is expected to register the routes they wish to protect inside the {@link
 * #registerProtectedRoutes} method. The route registration process is described inside the {@link
 * ProtectedRouteRegistry} class.
 * <p>
 * The implemented {@link javax.servlet.Filter} internally uses a {@link JWTProcessor}
 * implementation to validate the Bearer token that was provided when accessing a protected REST
 * route.
 * <p>
 *
 * @author Mislav Milicevic
 * @see #configureFilter
 * @see #registerProtectedRoutes
 * @see ProtectedRouteRegistry
 * @see JWTProcessor
 * @since 2019-10-01
 */
public abstract class AbstractProtectedRouteFilter extends GenericFilterBean {

    private ProtectedRouteHandler protectedRouteHandler;

    private ProtectedRouteFilterConfiguration configuration = new ProtectedRouteFilterConfiguration();

    /**
     * The implementor is expected to configure the filter inside this method. The configuration is
     * done by invoking the setter methods inside {@link ProtectedRouteFilterConfiguration}.
     *
     * @param configuration {@link ProtectedRouteFilterConfiguration} instance used to configure the
     * filter
     */
    protected abstract void configureFilter(final ProtectedRouteFilterConfiguration configuration);

    /**
     * The implementor is expected to register the routes they wish to protect inside this method.
     * <p>
     * To register a protected route, the implementor must invoke {@link
     * ProtectedRouteRegistry#registerRoute}
     *
     * @param registry {@link ProtectedRouteRegistry} instance used to register protected routes
     */
    protected abstract void registerProtectedRoutes(final ProtectedRouteRegistry registry);

    @Override
    public void initFilterBean() {
        configureFilter(configuration);

        final ProtectedRouteRegistry protectedRouteRegistry = new ProtectedRouteRegistry(
                configuration.isAutomaticTrailCompensation());
        registerProtectedRoutes(protectedRouteRegistry);

        if (configuration.getJWTProcessor() != null
                && configuration.getJWTProcessorConfiguration() != null) {
            throw new InvalidFilterConfigurationException(
                    "Configuration conflict - can't set both JWTProcessor and JWTProcessorConfiguration");
        }

        if (configuration.getJWTProcessor() == null
                && configuration.getJWTProcessorConfiguration() == null) {
            throw new InvalidFilterConfigurationException(
                    "JWTProcessor or JWTProcessorConfiguration needs to be set");
        }

        final JWTProcessor processor = configuration.getJWTProcessor() == null
                ? JWTProcessor.fromConfiguration(configuration.getJWTProcessorConfiguration())
                : configuration.getJWTProcessor();

        final ErrorHandler errorHandler = configuration.getErrorHandler() == null
                ? ErrorHandler.getDefault()
                : configuration.getErrorHandler();

        protectedRouteHandler = new ProtectedRouteHandler(protectedRouteRegistry, processor,
                errorHandler);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!protectedRouteHandler.requestNeedsAuthorization(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (protectedRouteHandler.handleBearerToken(httpServletRequest, httpServletResponse)) {
            filterChain.doFilter(servletRequest, httpServletResponse);
        }
    }
}
