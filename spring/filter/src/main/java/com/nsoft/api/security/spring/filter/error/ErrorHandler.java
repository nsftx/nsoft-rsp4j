package com.nsoft.api.security.spring.filter.error;

import com.nsoft.api.security.spring.filter.internal.error.DefaultErrorHandler;
import com.nsoft.api.security.spring.filter.internal.error.FallbackErrorHandler;

import javax.servlet.http.HttpServletResponse;

public interface ErrorHandler {

    void handleInvalidBearerTokenError(HttpServletResponse response);

    void handleJWTProcessingError(HttpServletResponse response);

    static ErrorHandler getDefault() {
        return new DefaultErrorHandler();
    }

    static ErrorHandler getFallback() {
        return new FallbackErrorHandler();
    }
}
