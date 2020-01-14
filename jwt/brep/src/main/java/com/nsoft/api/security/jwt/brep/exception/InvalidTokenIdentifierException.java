package com.nsoft.api.security.jwt.brep.exception;

/**
 * @author Mislav Milicevic
 * @since 2020-01-10
 */
public final class InvalidTokenIdentifierException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public InvalidTokenIdentifierException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public InvalidTokenIdentifierException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public InvalidTokenIdentifierException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    public InvalidTokenIdentifierException(final Throwable throwable) {
        super(throwable);
    }
}
