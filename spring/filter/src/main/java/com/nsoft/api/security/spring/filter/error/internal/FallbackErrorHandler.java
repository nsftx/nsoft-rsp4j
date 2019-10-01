package com.nsoft.api.security.spring.filter.error.internal;

import com.nsoft.api.security.spring.filter.error.ErrorHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FallbackErrorHandler implements ErrorHandler {

    @Override
    public void handleInvalidBearerTokenError(HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleJWTProcessingError(HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
