package com.nsoft.api.security.jwt.brep;

import static java.util.Objects.requireNonNull;

import com.nsoft.api.security.jwt.brep.exception.ClaimHolderMissingDefaultConstructorException;
import com.nsoft.api.security.jwt.brep.exception.ClaimMutatorNotFoundException;
import com.nsoft.api.security.jwt.brep.exception.InvalidTokenIdentifierException;
import com.nsoft.api.security.jwt.brep.exception.TokenIdentifierMismatchException;
import com.nsoft.api.security.jwt.brep.token.ClaimHolder;
import com.nsoft.api.security.jwt.brep.token.TokenIdentifier;

import java.util.function.Supplier;

/**
 * bRep is a JSON Web Token (JWT) claim set bounding API. The bounding process is done in three
 * stages, all of which are directly dependant on the previous.
 * <p>
 * {@link BRepEntryPoint} is the process entry point, in which token identification is performed.
 * This information is used to construct a {@link ClaimMutator} instance which is used to execute
 * various mutations on the input {@link com.nsoft.api.security.jwt.verifier.JWTClaimsSet}. The
 * mutated data set is passed to a {@link ClaimAssembler} instance responsible for handling the
 * final representation of the claim data.
 * <p>
 * API implementors are required to implement the following interfaces:
 * <ul>
 *     <li>{@link ClaimHolder}</li>
 *     <li>{@link BRepEntryPoint}</li>
 *     <li>{@link ClaimMutator}</li>
 *     <li>{@link ClaimAssembler}</li>
 * </ul>
 * <p>
 * Optional interfaces with default implementations provided:
 * <ul>
 *     <li>{@link TokenIdentifier} (Default: {@link com.nsoft.api.security.jwt.brep.token.type.TokenType})</li>
 * </ul>
 *
 * @author Mislav Milicevic
 * @see #getClaims(TokenIdentifier, Class)
 * @see #getClaims(Supplier, Class)
 * @since 2020-01-10
 */
public interface BRepEntryPoint<IDENTIFIER extends TokenIdentifier> {

    /**
     * The entry point to the {@link com.nsoft.api.security.jwt.verifier.JWTClaimsSet} bounding
     * process. A specific {@link ClaimMutator<HOLDER>} instance is returned depending on the {@link
     * TokenIdentifier} input parameter.
     * <p>
     * If the {@link TokenIdentifier} input is invalid, then a {@link InvalidTokenIdentifierException}
     * is thrown.
     * <p>
     * If a {@link ClaimMutator<HOLDER>} instance is not found for a valid {@link TokenIdentifier},
     * a {@link ClaimMutatorNotFoundException} should be thrown. Handling of a {@code null} {@link
     * TokenIdentifier} is not strictly defined and is left to the implementor to handle.
     *
     * @param tokenIdentifier object used to identify different types of incoming tokens, must not
     * be {@code null}
     * @param <HOLDER> {@link ClaimHolder}
     * @return {@link ClaimMutator<HOLDER>} instance
     * @throws InvalidTokenIdentifierException if tokenIdentifier is invalid
     * @throws ClaimMutatorNotFoundException if a {@link ClaimMutator<HOLDER>} instance is not found
     * for a valid tokenIdentifier
     * @throws ClaimHolderMissingDefaultConstructorException if {@link Class<HOLDER>} doesn't have a
     * default constructor available
     * @throws TokenIdentifierMismatchException if {@code tokenIdentifier} doesn't match the
     * identifier of the {@link HOLDER}
     */
    <HOLDER extends ClaimHolder<IDENTIFIER>>
    ClaimMutator<HOLDER> getClaims(final IDENTIFIER tokenIdentifier, final Class<HOLDER> holder);

    /**
     * A convenience method used to retrieve a {@link TokenIdentifier} from a {@link
     * Supplier<TokenIdentifier>}. The retrieve {@link TokenIdentifier} is passed to {@link
     * #getClaims(TokenIdentifier, Class)} for further handling.
     * <p>
     * If the {@link Supplier<TokenIdentifier>} is null, a {@link NullPointerException} is thrown.
     *
     * @param supplier used to retrieve a {@link TokenIdentifier}, must not be {@code null}
     * @param <HOLDER> {@link ClaimHolder}
     * @return {@link ClaimMutator<HOLDER>} instance
     * @throws NullPointerException if {@link Supplier<TokenIdentifier>} is null
     */
    default <HOLDER extends ClaimHolder<IDENTIFIER>>
    ClaimMutator<HOLDER> getClaims(final Supplier<IDENTIFIER> supplier, Class<HOLDER> holder) {
        return getClaims(requireNonNull(supplier).get(), holder);
    }
}
