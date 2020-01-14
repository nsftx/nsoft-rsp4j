package com.nsoft.api.security.jwt.brep;

import com.nsoft.api.security.jwt.brep.token.ClaimHolder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * An object assembler abstraction used to assemble a bounded representation of a {@link
 * com.nsoft.api.security.jwt.verifier.JWTClaimsSet} from raw claim data. The claim data should be
 * received from a {@link ClaimMutator<HOLDER>} instance, typically via {@link
 * ClaimMutator#from}.
 *
 * @param <HOLDER> incoming claim holder
 * @author Mislav Milicevic
 * @see #asHolder()
 * @see #asMap()
 * @see #asStream()
 * @since 2020-01-10
 */
public interface ClaimAssembler<HOLDER extends ClaimHolder> {

    /**
     * Uses raw claim data received from a {@link ClaimMutator<HOLDER>} instance to assemble a
     * bounded representation of the received claim data using a {@link ClaimHolder} instance.
     * <p>
     * If successful, the assembled representation instance is returned. Error handling is not
     * strictly defined and is left to the implementor.
     *
     * @return {@link ClaimHolder} instance
     */
    HOLDER asHolder();

    /**
     * Uses raw claim data received from a {@link ClaimMutator<HOLDER>} instance to assemble a
     * bounded representation of the received claim data using a {@link Map} instance. Claim names
     * and values are represented as keys and values associated with those keys in the returned
     * {@link Map} instance.
     * <p>
     * If successful, the assembled representation instance is returned. Error handling is not
     * strictly defined and is left to the implementor.
     *
     * @return {@link Map} containing claims as key value pairs
     */
    Map<String, Object> asMap();

    /**
     * By default, asStream() is a convenience method meant to return a {@link Stream} instance
     * retrieved from the result of {@link #asMap()}. Implementors are allowed to override the
     * functionality of this method.
     *
     * @return {@link Stream} containing claims represented by {@link Entry} instances
     */
    default Stream<Entry<String, Object>> asStream() {
        return asMap().entrySet().stream();
    }
}
