package com.nsoft.api.security.jwt.verifier;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nsoft.api.security.jwt.verifier.exception.ProcessorInstantiationException;
import com.nsoft.api.security.jwt.verifier.internal.DefaultJWTProcessorConfiguration;
import com.nsoft.api.security.jwt.verifier.internal.DefaultJWTProcessor;

import java.net.MalformedURLException;
import java.util.Optional;

public interface JWTProcessor {

    Optional<JWTClaimsSet> process(String token);

    default JWTProcessorConfiguration getConfiguration() {
        return new DefaultJWTProcessorConfiguration();
    }

    static JWTProcessor getDefault() {
        try {
            return new DefaultJWTProcessor();
        } catch (MalformedURLException e) {
            throw new ProcessorInstantiationException();
        }
    }
}
