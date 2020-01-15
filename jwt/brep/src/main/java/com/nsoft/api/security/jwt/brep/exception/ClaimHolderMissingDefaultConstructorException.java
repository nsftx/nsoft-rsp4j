package com.nsoft.api.security.jwt.brep.exception;

/**
 * @author Mislav Milicevic
 * @since 2020-01-10
 */
public class ClaimHolderMissingDefaultConstructorException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public ClaimHolderMissingDefaultConstructorException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ClaimHolderMissingDefaultConstructorException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public ClaimHolderMissingDefaultConstructorException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    public ClaimHolderMissingDefaultConstructorException(final Throwable throwable) {
        super(throwable);
    }
}
