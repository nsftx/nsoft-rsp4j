package com.nsoft.api.security.spring.filter;

import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;
import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.spring.filter.error.ErrorHandler;
import com.nsoft.api.security.spring.filter.internal.util.HeaderUtil;
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
import java.util.Objects;
import java.util.Optional;

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

    private ErrorHandler errorHandler = ErrorHandler.getDefault();
    private JWTProcessor jwtProcessor = JWTProcessor.getDefault();

    /**
     * The implementor is expected to register the routes they wish to protect inside this method.
     * <p>
     * To register a protected route, the implementor must invoke {@link
     * ProtectedRouteRegistry#registerRoute}
     *
     * @param registry used to register protected routes
     */
    public abstract void registerProtectedRoutes(ProtectedRouteRegistry registry);

    /**
     * Sets the {@link ErrorHandler} instance that should be used when handling errors such as
     * invalid token processing.
     * <p>
     * If {@code null} is passed as a parameter, a fallback instance ({@link
     * ErrorHandler#getFallback()}) is used.
     *
     * @param errorHandler to use when handling errors such as invalid token processing
     */
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = Objects.requireNonNullElseGet(errorHandler, ErrorHandler::getFallback);
    }

    /**
     * Sets the {@link JWTProcessor} instance that should be used when processing incoming Bearer
     * tokens.
     * <p>
     * If {@code null} is passed as a parameter, the default Chameleon Accounts specific
     * implementation is used.
     *
     * @param jwtProcessor to use when processing incoming Bearer tokens
     */
    public void setJWTProcessor(JWTProcessor jwtProcessor) {
        this.jwtProcessor = Objects.requireNonNullElseGet(jwtProcessor, JWTProcessor::getDefault);
    }

    @Override
    public void initFilterBean() {
        registerProtectedRoutes(protectedRouteRegistry);

        protectedRouteHandler = new ProtectedRouteHandler(protectedRouteRegistry);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!protectedRouteHandler.requestNeedsAuthorization(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Optional<String> optionalBearerToken = HeaderUtil.extractBearerToken(httpServletRequest);

        if (optionalBearerToken.isEmpty()) {
            errorHandler.handleInvalidBearerTokenError(httpServletResponse);
            return;
        }

        String bearerToken = optionalBearerToken.get();

        Optional<JWTClaimsSet> optionalClaims = jwtProcessor.process(bearerToken);

        if (optionalClaims.isEmpty()) {
            errorHandler.handleJWTProcessingError(httpServletResponse);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
