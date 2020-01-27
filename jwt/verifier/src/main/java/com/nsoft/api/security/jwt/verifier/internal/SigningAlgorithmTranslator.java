package com.nsoft.api.security.jwt.verifier.internal;

import com.nsoft.api.security.jwt.verifier.JWSAlgorithm;

final class SigningAlgorithmTranslator {
    static com.nimbusds.jose.JWSAlgorithm toNimbusAlgorithm(JWSAlgorithm algorithm) {
        return com.nimbusds.jose.JWSAlgorithm.parse(algorithm.name());
    }

    private SigningAlgorithmTranslator() {
    }
}
