package com.nsoft.api.security.jwt.verifier.internal;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.jwt.verifier.JWTProcessorConfiguration;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Optional;

public class DefaultJWTProcessor implements JWTProcessor {

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
            return Optional.of(processor.process(token, null));
        } catch (ParseException | JOSEException | BadJOSEException e) {
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
        public void verify(JWTClaimsSet claimsSet) throws BadJWTException {
            super.verify(claimsSet);

            if (!claimsSet.getIssuer().equals(configuration.getIssuer())) {
                throw new BadJWTException("Invalid token issuer");
            }
        }
    }
}
