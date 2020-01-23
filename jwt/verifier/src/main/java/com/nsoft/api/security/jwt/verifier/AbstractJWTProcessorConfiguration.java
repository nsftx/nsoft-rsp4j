package com.nsoft.api.security.jwt.verifier;

public abstract class AbstractJWTProcessorConfiguration implements JWTProcessorConfiguration {

    private static final int DEFAULT_CONNECT_TIMEOUT = 1000;
    private static final int DEFAULT_READ_TIMEOUT = 1000;

    private int connectTimeout;
    private int readTimeout;

    public AbstractJWTProcessorConfiguration() {
        this(DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    public AbstractJWTProcessorConfiguration(int connectTimeout, int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    @Override
    public int getConnectTimeout() {
        return connectTimeout;
    }

    @Override
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Override
    public int getReadTimeout() {
        return readTimeout;
    }
}
