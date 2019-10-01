package com.nsoft.api.security.jwt.verifier;

import com.nimbusds.jose.JWSAlgorithm;

public interface JWTProcessorConfiguration {

    String getJWKSUrl();

    String getIssuer();

    JWSAlgorithm getSigningAlgorithm();

}
