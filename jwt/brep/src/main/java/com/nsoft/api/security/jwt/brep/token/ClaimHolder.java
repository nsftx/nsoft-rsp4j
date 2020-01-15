package com.nsoft.api.security.jwt.brep.token;

/**
 * Represents an object which is able to act as a container for the claims created by a {@link
 * com.nsoft.api.security.jwt.brep.ClaimAssembler} instance.
 * <p>
 * The {@link ClaimHolder} interface itself doesn't have any functionality other than acting as a
 * generic bound to a specific set of interfaces used to manipulate an incoming {@link
 * com.nsoft.api.security.jwt.verifier.JWTClaimsSet}.
 *
 * @author Mislav Milicevic
 * @since 2020-01-10
 */
public interface ClaimHolder<IDENTIFIER extends TokenIdentifier> {

    IDENTIFIER getIdentifier();
}
