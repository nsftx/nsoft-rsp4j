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

public abstract class AbstractProtectedRouteFilter extends GenericFilterBean {

    private final ProtectedRouteRegistry protectedRouteRegistry = new ProtectedRouteRegistry();
    private ProtectedRouteHandler protectedRouteHandler;

    private ErrorHandler errorHandler = ErrorHandler.getDefault();
    private JWTProcessor jwtProcessor = JWTProcessor.getDefault();

    public abstract void registerProtectedRoutes(ProtectedRouteRegistry registry);

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

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = Objects.requireNonNullElseGet(errorHandler, ErrorHandler::getFallback);
    }

    public void setJWTProcessor(JWTProcessor jwtProcessor) {
        this.jwtProcessor = Objects.requireNonNullElseGet(jwtProcessor, JWTProcessor::getDefault);
    }
}
