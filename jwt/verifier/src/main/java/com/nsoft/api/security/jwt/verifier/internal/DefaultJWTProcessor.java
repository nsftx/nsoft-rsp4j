package com.nsoft.api.security.jwt.verifier.internal;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;
import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.jwt.verifier.JWTProcessorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Optional;

public class DefaultJWTProcessor implements JWTProcessor {

    private final Logger logger = LoggerFactory.getLogger(DefaultJWTProcessor.class);

    private final ConfigurableJWTProcessor<SecurityContext> processor;

    public DefaultJWTProcessor() throws MalformedURLException {
        this.processor = new com.nimbusds.jwt.proc.DefaultJWTProcessor<>();

        JWKSource<SecurityContext> jwkSource = new RemoteJWKSet<>(
                new URL(getConfiguration().getJWKSUrl()));

        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(
                getConfiguration().getSigningAlgorithm(), jwkSource);

        processor.setJWSKeySelector(keySelector);
        processor.setJWTClaimsSetVerifier(new JWTClaimsVerifier(getConfiguration()));
    }

    @Override
    public Optional<JWTClaimsSet> process(String token) {
        try {
            return Optional.of(new ImmutableJWTClaimsSet(processor.process(token, null)));
        } catch (ParseException | JOSEException | BadJOSEException e) {
            logger.debug("Failed to process incoming token:", e);
            return Optional.empty();
        }
    }

    private static class JWTClaimsVerifier extends DefaultJWTClaimsVerifier<SecurityContext> {

        private final JWTProcessorConfiguration configuration;

        private JWTClaimsVerifier(
                JWTProcessorConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public void verify(com.nimbusds.jwt.JWTClaimsSet claimsSet, SecurityContext context) throws BadJWTException {
            super.verify(claimsSet, context);

            if (!claimsSet.getIssuer().equals(configuration.getIssuer())) {
                throw new BadJWTException("Invalid token issuer");
            }
        }
    }
}
