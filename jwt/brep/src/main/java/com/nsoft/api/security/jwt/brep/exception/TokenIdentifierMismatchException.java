package com.nsoft.api.security.jwt.brep.exception;

/**
 * @author Mislav Milicevic
 * @since 2020-01-10
 */
public final class TokenIdentifierMismatchException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public TokenIdentifierMismatchException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public TokenIdentifierMismatchException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public TokenIdentifierMismatchException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    public TokenIdentifierMismatchException(final Throwable throwable) {
        super(throwable);
    }
}
