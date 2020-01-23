package com.nsoft.api.security.jwt.verifier;

import static java.util.Objects.requireNonNull;

import com.nsoft.api.security.jwt.verifier.exception.ProcessorInstantiationException;
import com.nsoft.api.security.jwt.verifier.internal.DefaultJWTProcessor;
import com.nsoft.api.security.jwt.verifier.internal.DefaultJWTProcessorConfiguration;

import java.net.MalformedURLException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A JSON Web Token processor abstraction used to process and verify OAuth2 access tokens.
 *
 * NSoft's security API directly integrates with Chameleon Accounts by providing an out-of-the-box
 * JWT processor which pulls and caches a JSON Web Key Set provided by the Chameleon team used for
 * token verification by the processor.
 *
 * To use the default processor designed for Chameleon Accounts, you can access it by invoking
 * {@link JWTProcessor#getDefault()} factory method.
 *
 * @author Mislav Milicevic
 * @since 2019-10-01
 */
public interface JWTProcessor {

    /**
     * Processes and verifies an OAuth2 access token. If the verification is successful, an {@link
     * Optional<JWTClaimsSet>} will be returned. Otherwise, an empty {@link Optional} will be
     * returned.
     *
     * @param token OAuth2 access token, must not be null {@code null}
     * @return {@link JWTClaimsSet} if token is verified successfully
     */
    Optional<JWTClaimsSet> process(String token);

    /**
     * Processes and verifies an OAuth2 access token. If the verification is successful, an {@link
     * Optional<JWTClaimsSet>} will be returned. Otherwise, an empty {@link Optional} will be
     * returned.
     *
     * @param tokenSupplier OAuth2 access token supplier, must not be {@code null}
     * @return {@link JWTClaimsSet} if token is verified successfully
     */
    default Optional<JWTClaimsSet> process(Supplier<String> tokenSupplier) {
        return process(requireNonNull(tokenSupplier).get());
    }

    /**
     * Returns a configuration that should be used by {@link JWTProcessor} implementors during
     * processor construction and token validation.
     *
     * @return configuration used by {@link JWTProcessor}
     */
    default JWTProcessorConfiguration getConfiguration() {
        return new DefaultJWTProcessorConfiguration();
    }

    /**
     * A factory method used to retrieve the default {@link JWTProcessor} implementation.
     *
     * @return default {@link JWTProcessor} implementation
     */
    static JWTProcessor getDefault() {
        try {
            return new DefaultJWTProcessor();
        } catch (MalformedURLException e) {
            throw new ProcessorInstantiationException();
        }
    }
}
