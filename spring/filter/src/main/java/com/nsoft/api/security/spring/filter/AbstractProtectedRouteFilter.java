package com.nsoft.api.security.spring.filter;

import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.spring.filter.error.ErrorHandler;
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
import java.util.function.Supplier;

/**
 * A {@link javax.servlet.Filter} abstraction used to limit interaction with specified REST routes.
 * In order to add the {@link javax.servlet.Filter} implementation to the {@link FilterChain}, the
 * implementor must annotate their implementation class with {@link org.springframework.stereotype.Component}
 * <p>
 * The implementor is expected to register the routes they wish to protect inside the {@link
 * AbstractProtectedRouteFilter#registerProtectedRoutes(ProtectedRouteRegistry)} method. The route
 * registration process is described inside the {@link ProtectedRouteRegistry} class.
 * <p>
 * The implemented {@link javax.servlet.Filter} internally uses a {@link JWTProcessor}
 * implementation to validate the Bearer token that was provided when accessing a protected REST
 * route.
 * <p>
 * By default, the {@link JWTProcessor} implementation being used by the {@link
 * javax.servlet.Filter} is specifically designed to accept Identity and Access tokens issued by
 * Chameleon Accounts. The implementor may use their own implementation of {@link JWTProcessor} by
 * setting it via {@link AbstractProtectedRouteFilter#setJWTProcessor(JWTProcessor)}.
 *
 * @author Mislav Milicevic
 * @see #registerProtectedRoutes
 * @see ProtectedRouteRegistry
 * @see #setJWTProcessor
 * @see JWTProcessor
 * @since 2019-10-01
 */
public abstract class AbstractProtectedRouteFilter extends GenericFilterBean {

    private final ProtectedRouteRegistry protectedRouteRegistry = new ProtectedRouteRegistry();
    private ProtectedRouteHandler protectedRouteHandler;

    /**
     * The implementor is expected to register the routes they wish to protect inside this method.
     * <p>
     * To register a protected route, the implementor must invoke {@link
     * ProtectedRouteRegistry#registerRoute}
     *
     * @param registry used to register protected routes
     */
    public abstract void registerProtectedRoutes(final ProtectedRouteRegistry registry);

    /**
     * Sets the {@link JWTProcessor} instance that should be used when processing incoming Bearer
     * tokens.
     *
     * @param jwtProcessor to use when processing incoming Bearer tokens
     */
    public void setJWTProcessor(final JWTProcessor jwtProcessor) {
        protectedRouteHandler.setJWTProcessor(jwtProcessor);
    }

    /**
     * Sets the {@link JWTProcessor} instance provided through a {@link Supplier} that should be
     * used when processing incoming Bearer tokens.
     *
     * @param jwtProcessorSupplier used to retrieve the {@link JWTProcessor} instance
     */
    public void setJWTProcessor(final Supplier<JWTProcessor> jwtProcessorSupplier) {
        protectedRouteHandler.setJWTProcessor(jwtProcessorSupplier);
    }

    /**
     * Sets the {@link ErrorHandler} instance that should be used when handling errors that occur
     * during Bearer token processing.
     *
     * @param errorHandler to use when handling errors such as invalid token processing
     */
    public void setErrorHandler(final ErrorHandler errorHandler) {
        protectedRouteHandler.setErrorHandler(errorHandler);
    }

    /**
     * Sets the {@link ErrorHandler} instance provided through a {@link Supplier} that should be
     * used when handling errors that occur during Bearer token processing.
     *
     * @param errorHandlerSupplier used to retrieve the {@link ErrorHandler} instance
     */
    public void setErrorHandler(final Supplier<ErrorHandler> errorHandlerSupplier) {
        protectedRouteHandler.setErrorHandler(errorHandlerSupplier);
    }

    @Override
    public void initFilterBean() {
        registerProtectedRoutes(protectedRouteRegistry);

        protectedRouteHandler = new ProtectedRouteHandler(protectedRouteRegistry);
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
