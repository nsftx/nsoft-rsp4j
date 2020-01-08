package com.nsoft.api.security.test_support;

import com.nsoft.api.security.jwt.verifier.JWTProcessorConfiguration;
import com.nsoft.api.security.jwt.verifier.internal.DefaultJWTProcessor;

import java.net.MalformedURLException;

public class LocalProcessor extends DefaultJWTProcessor {

    public LocalProcessor() throws MalformedURLException {
        super();
    }

    @Override
    public JWTProcessorConfiguration getConfiguration() {
        return new LocalProcessorConfiguration();
    }
}
