package com.nsoft.api.security.jwt.verifier;

/**
 * A configuration object used by {@link JWTProcessor} during construction and token verification.
 * <p>
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
     * Returns the JSON Web Key Set (JWKS) URL (ex. https://accounts.nsoft.com/.well-known/jwks.json)
     *
     * @return JSON Web Key Set URL
     */
    String getJWKSUrl();

    /**
     * Returns the token issuer {@link String} that needs to match the issuer {@link String} in the
     * token that is being processed.
     *
     * @return required token issuer
     */
    String getIssuer();

    /**
     * Returns the {@link JWSAlgorithm} that needs to match the algorithm used to sign the token
     * that is being processed.
     *
     * @return required token signing algorithm
     */
    JWSAlgorithm getSigningAlgorithm();

    /**
     * Returns the connect timeout duration used by the {@link JWTProcessor} when refreshing the
     * local JWKS cache.
     *
     * @return connection timeout duration
     */
    int getConnectTimeout();

    /**
     * Sets the connect timeout duration used by the {@link JWTProcessor} when refreshing the local
     * JWKS cache.
     * <p>
     * Once the {@link JWTProcessor} is constructed, invocation of this method will have no effect
     * on the connect timeout duration used by the constructed {@link JWTProcessor} instance.
     *
     * @param connectTimeout to be set, must not be less than 0
     */
    void setConnectTimeout(int connectTimeout);

    /**
     * Returns the read timeout duration used by the {@link JWTProcessor} when refreshing the local
     * JWKS cache.
     *
     * @return read timeout duration
     */
    int getReadTimeout();

    /**
     * Sets the read timeout duration used by the {@link JWTProcessor} when refreshing the local
     * JWKS cache.
     * <p>
     * Once the {@link JWTProcessor} is constructed, invocation of this method will have no effect
     * on the read timeout duration used by the constructed {@link JWTProcessor} instance.
     *
     * @param readTimeout to be set, must not be less than 0
     */
    void setReadTimeout(int readTimeout);
}
