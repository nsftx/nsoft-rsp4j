package com.nsoft.api.security.jwt.verifier;

import static java.util.Objects.requireNonNull;

import com.nsoft.api.security.jwt.verifier.exception.ProcessorInstantiationException;
import com.nsoft.api.security.jwt.verifier.internal.DefaultJWTProcessor;

import java.net.MalformedURLException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A JSON Web Token processor abstraction used to process and verify OAuth2 access tokens.
 * <p>
 * A default {@link JWTProcessor} is provided and can be constructed and accessed by invoking {@link
 * #fromConfiguration(JWTProcessorConfiguration)}. A {@link JWTProcessorConfiguration} instance is
 * required to be passed to the method.
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
    JWTProcessorConfiguration getConfiguration();

    /**
     * A factory method used to construct a default {@link JWTProcessor} implementation from a
     * {@link JWTProcessorConfiguration}.
     *
     * @return default {@link JWTProcessor} implementation
     */
    static JWTProcessor fromConfiguration(final JWTProcessorConfiguration configuration) {
        try {
            return new DefaultJWTProcessor(configuration);
        } catch (MalformedURLException e) {
            throw new ProcessorInstantiationException();
        }
    }
}
