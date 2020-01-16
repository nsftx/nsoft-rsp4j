package com.nsoft.api.security.jwt.brep;

import static java.util.Objects.requireNonNull;

import com.nsoft.api.security.jwt.brep.token.ClaimHolder;
import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;

import java.util.function.Supplier;

/**
 * A mutator abstraction used to mutate {@link JWTClaimsSet} instances into a set of raw claim data.
 * The mutated data is passed to a {@link ClaimAssembler<HOLDER>} instance which handles the
 * in-memory representation of the raw data.
 *
 * @param <HOLDER> {@link ClaimHolder} instance handled by the mutator
 * @author Mislav Milicevic
 * @see #from(JWTClaimsSet)
 * @see #from(Supplier)
 * @since 2020-01-10
 */
@FunctionalInterface
public interface ClaimMutator<HOLDER extends ClaimHolder> {

    /**
     * Uses a {@link JWTClaimsSet} instance to create a raw claim data set which is passed to a
     * {@link ClaimAssembler<HOLDER>} instance. A {@link ClaimAssembler<HOLDER>} instance containing
     * the claim data set is returned upon success. Error handling is not strictly defined and is
     * left to the implementor.
     * <p>
     * The {@link JWTClaimsSet} instance used as the input must not be {@code null}.
     *
     * @param claimsSet incoming {@link JWTClaimsSet}, must not be {@code null}
     * @return {@link ClaimAssembler<HOLDER>} instance
     */
    ClaimAssembler<HOLDER> from(final JWTClaimsSet claimsSet);

    /**
     * Delegates a {@link JWTClaimsSet} instance provided by a {@link Supplier<JWTClaimsSet>} to
     * {@link #from(JWTClaimsSet)}
     * <p>
     * The {@link Supplier} instance must not be {@code null}.
     *
     * @param supplier incoming {@link JWTClaimsSet} {@link Supplier<JWTClaimsSet>}, must not be
     * {@code null}
     * @return {@link ClaimAssembler<HOLDER>} instance
     */
    default ClaimAssembler<HOLDER> from(final Supplier<JWTClaimsSet> supplier) {
        return from(requireNonNull(supplier).get());
    }
}
