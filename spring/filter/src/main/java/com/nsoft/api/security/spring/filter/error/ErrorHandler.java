package com.nsoft.api.security.spring.filter.error;

import com.nsoft.api.security.spring.filter.internal.error.DefaultErrorHandler;
import com.nsoft.api.security.spring.filter.internal.error.FallbackErrorHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * An abstraction used to handle methods that occur during the token validation process.
 * <p>
 * The handler implementor must write any errors that occur to the {@link HttpServletResponse} which
 * is available to them as a method parameter.
 * <p>
 * In case the user wants to use an already existing solution, they can access the {@link
 * DefaultErrorHandler} by invoking {@link ErrorHandler#getDefault()}
 *
 * @author Mislav Milicevic
 * @since 2019-10-01
 */
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
