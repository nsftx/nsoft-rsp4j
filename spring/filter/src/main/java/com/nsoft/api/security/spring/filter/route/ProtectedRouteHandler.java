package com.nsoft.api.security.spring.filter.route;

import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;
import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.spring.filter.error.ErrorHandler;
import com.nsoft.api.security.spring.filter.internal.util.HeaderUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A handler for protected routes that are stored in a local {@link ProtectedRouteRegistry}
 * instance.
 * <p>
 * In order to check if the incoming HTTP request requires authorization, the user must invoke
 * {@link ProtectedRouteHandler#requestNeedsAuthorization(HttpServletRequest)}.
 * <p>
 * If authorization is required for the incoming HTTP request, the user should validate the Bearer
 * token located in the Authorization header of the incoming HTTP request. To do so, the user must
 * invoke {@link ProtectedRouteHandler#handleBearerToken(HttpServletRequest, HttpServletResponse)}
 * <p>
 * If the handled token is not valid, an error will be written to the {@link HttpServletResponse}
 * and the invoked method will return {@code false}. Otherwise, the invoked method will return
 * {@code true}
 *
 * @author Mislav Milicevic
 * @see #requestNeedsAuthorization
 * @see #handleBearerToken
 * @since 2019-10-01
 */
public final class ProtectedRouteHandler {

    private final ProtectedRouteRegistry protectedRouteRegistry;

    private JWTProcessor jwtProcessor;
    private ErrorHandler errorHandler;

    public ProtectedRouteHandler(final ProtectedRouteRegistry protectedRouteRegistry) {
        this(protectedRouteRegistry, JWTProcessor.getDefault(), ErrorHandler.getDefault());
    }

    public ProtectedRouteHandler(final ProtectedRouteRegistry protectedRouteRegistry,
            final JWTProcessor jwtProcessor, final ErrorHandler errorHandler) {
        this.protectedRouteRegistry = Objects.requireNonNull(protectedRouteRegistry);

        setJWTProcessor(jwtProcessor);
        setErrorHandler(errorHandler);
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
    public void setJWTProcessor(final JWTProcessor jwtProcessor) {
        this.jwtProcessor = Objects.requireNonNullElseGet(jwtProcessor, JWTProcessor::getDefault);
    }

    /**
     * Sets the {@link JWTProcessor} instance provided through a {@link Supplier} that should be
     * used when processing incoming Bearer tokens.
     * <p>
     * If {@code null} is passed as a parameter, the default Chameleon Accounts specific
     * implementation is used.
     *
     * @param jwtProcessorSupplier used to retrieve the {@link JWTProcessor} instance
     */
    public void setJWTProcessor(final Supplier<JWTProcessor> jwtProcessorSupplier) {
        setJWTProcessor(Objects.requireNonNull(jwtProcessorSupplier.get()));
    }

    /**
     * Sets the {@link ErrorHandler} instance that should be used when handling errors that occur
     * during Bearer token processing.
     * <p>
     * If {@code null} is passed as a parameter, a fallback instance ({@link
     * ErrorHandler#getFallback()}) is used.
     *
     * @param errorHandler to use when handling errors such as invalid token processing
     */
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = Objects.requireNonNullElseGet(errorHandler, ErrorHandler::getFallback);
    }

    /**
     * Sets the {@link ErrorHandler} instance provided through a {@link Supplier} that should be
     * used when handling errors that occur during Bearer token processing.
     * <p>
     * If {@code null} is passed as a parameter, a fallback instance ({@link
     * ErrorHandler#getFallback()}) is used.
     *
     * @param errorHandlerSupplier used to retrieve the {@link ErrorHandler} instance
     */
    public void setErrorHandler(final Supplier<ErrorHandler> errorHandlerSupplier) {
        setErrorHandler(Objects.requireNonNull(errorHandlerSupplier.get()));
    }

    /**
     * Checks if the incoming HTTP request requires authentication. The check is done based on the
     * protected routes that are registered in a {@link ProtectedRouteRegistry} instance that was
     * passed to the current {@link ProtectedRouteHandler} when it was constructed.
     *
     * @param request incoming HTTP request, must not be {@code null}
     * @return whether the incoming HTTP request requires authorization
     */
    public boolean requestNeedsAuthorization(final HttpServletRequest request) {
        Objects.requireNonNull(request);

        return protectedRouteRegistry.protectedRoutes.stream().anyMatch(x -> x.matches(request));
    }

    /**
     * Handles the Bearer token located in the Authorization header which is extracted from the
     * incoming {@link HttpServletRequest}
     * <p>
     * If the handled token is not valid, an error will be written to the {@link
     * HttpServletResponse} via the {@link ErrorHandler} and will return {@code false} as a result.
     * Otherwise, the returning result will be {@code true}
     *
     * @param request incoming HTTP request, must not be {@code null}
     * @param response outgoing response, must not ne {@code null}
     * @return whether the Bearer token was handled successfully
     */
    public boolean handleBearerToken(final HttpServletRequest request,
            final HttpServletResponse response) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(response);

        final Optional<String> optionalToken = HeaderUtil.extractBearerToken(request);

        if (optionalToken.isEmpty()) {
            errorHandler.handleInvalidBearerTokenError(response);
            return false;
        }

        final Optional<JWTClaimsSet> optionalClaims = jwtProcessor.process(optionalToken.get());

        if (optionalClaims.isEmpty()) {
            errorHandler.handleJWTProcessingError(response);
            return false;
        }

        return true;
    }
}
