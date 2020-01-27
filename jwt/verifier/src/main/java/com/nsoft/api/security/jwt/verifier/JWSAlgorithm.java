package com.nsoft.api.security.jwt.verifier;

/**
 * An enumerator containing algorithm name references that can be used to sign a JWT
 *
 * @author Mislav Milicevic
 * @since 2020-01-27
 */
public enum JWSAlgorithm {
    HS256,
    HS384,
    HS512,
    RS256,
    RS384,
    RS512,
    ES256,
    ES384,
    ES512,
    PS256,
    PS384,
    PS512,
    EdDSA
}
