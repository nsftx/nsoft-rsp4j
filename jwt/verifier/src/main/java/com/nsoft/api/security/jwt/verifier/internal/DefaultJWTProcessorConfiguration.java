package com.nsoft.api.security.jwt.verifier.internal;

import com.nimbusds.jose.JWSAlgorithm;
import com.nsoft.api.security.jwt.verifier.AbstractJWTProcessorConfiguration;

public class DefaultJWTProcessorConfiguration extends AbstractJWTProcessorConfiguration {

    private static final String DEFAULT_JWKS_URL = "https://accounts.nsoft.com/.well-known/jwks.json";

    private static final String DEFAULT_ISSUER = "https://accounts.nsoft.com";

    private static final JWSAlgorithm DEFAULT_SIGNING_ALGORITHM = JWSAlgorithm.RS256;

    @Override
    public String getJWKSUrl() {
        return DEFAULT_JWKS_URL;
    }

    @Override
    public String getIssuer() {
        return DEFAULT_ISSUER;
    }

    @Override
    public JWSAlgorithm getSigningAlgorithm() {
        return DEFAULT_SIGNING_ALGORITHM;
    }
}
