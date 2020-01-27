package com.nsoft.api.security.test_support;

import com.nsoft.api.security.jwt.verifier.AbstractJWTProcessorConfiguration;
import com.nsoft.api.security.jwt.verifier.JWSAlgorithm;

public final class LocalProcessorConfiguration extends AbstractJWTProcessorConfiguration {

    @Override
    public String getJWKSUrl() {
        return "http://localhost:18081/.well-known/jwks.json";
    }

    @Override
    public String getIssuer() {
        return "http://localhost:8080";
    }

    @Override
    public JWSAlgorithm getSigningAlgorithm() {
        return JWSAlgorithm.RS256;
    }
}
