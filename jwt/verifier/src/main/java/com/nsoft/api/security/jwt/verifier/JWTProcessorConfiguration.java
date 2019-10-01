package com.nsoft.api.security.jwt.verifier;

import com.nimbusds.jose.JWSAlgorithm;

/**
 * A configuration object used by {@link JWTProcessor} during construction and token verification.
 *
 * Contains necessary information such as:
 * <ul>
 *     <li>JSON Web Key Set URL</li>
 *     <li>Issuer that should've signed the token being processed</li>
 *     <li>The algorithm that should've been used for token signing</li>
 * </ul>
 *
 * @author Mislav Milicevic
 * @since 2019-10-01
 */
public interface JWTProcessorConfiguration {

    /**
     * @return JSON Web Key Set remote URL
     */
    String getJWKSUrl();

    /**
     * @return required token issuer
     */
    String getIssuer();

    /**
     * @return required token signing algorithm
     */
    JWSAlgorithm getSigningAlgorithm();

}
