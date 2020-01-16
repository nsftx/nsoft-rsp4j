package com.nsoft.api.security.jwt.brep.exception;

import com.nsoft.api.security.jwt.brep.token.TokenIdentifier;

/**
 * @author Mislav Milicevic
 * @since 2020-01-10
 */
public final class ClaimMutatorNotFoundException extends RuntimeException {

    private TokenIdentifier tokenIdentifier;

    /**
     * {@inheritDoc}
     */
    public ClaimMutatorNotFoundException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ClaimMutatorNotFoundException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public ClaimMutatorNotFoundException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    public ClaimMutatorNotFoundException(final Throwable throwable) {
        super(throwable);
    }


    public ClaimMutatorNotFoundException(final TokenIdentifier tokenIdentifier) {
        super(String.format("Claim mutator not found for identifier: '%s'",
                tokenIdentifier == null ? null : tokenIdentifier.toString()));

        this.tokenIdentifier = tokenIdentifier;
    }


    public ClaimMutatorNotFoundException(final String message,
            final TokenIdentifier tokenIdentifier) {
        super(String.format("%s: '%s'", message,
                tokenIdentifier == null ? null : tokenIdentifier.toString()));

        this.tokenIdentifier = tokenIdentifier;
    }
}
