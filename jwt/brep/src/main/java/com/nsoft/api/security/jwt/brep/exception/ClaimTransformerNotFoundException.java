package com.nsoft.api.security.jwt.brep.exception;

/**
 * @author Mislav Milicevic
 * @since 2020-01-10
 */
public final class ClaimTransformerNotFoundException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public ClaimTransformerNotFoundException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ClaimTransformerNotFoundException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public ClaimTransformerNotFoundException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    public ClaimTransformerNotFoundException(final Throwable throwable) {
        super(throwable);
    }
}
