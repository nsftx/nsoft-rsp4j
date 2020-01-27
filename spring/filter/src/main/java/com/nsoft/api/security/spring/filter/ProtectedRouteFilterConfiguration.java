package com.nsoft.api.security.spring.filter;

import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.jwt.verifier.JWTProcessorConfiguration;
import com.nsoft.api.security.spring.filter.error.ErrorHandler;

/**
 * @author Mislav Milicevic
 * @since 2020-01-23
 */
final class ProtectedRouteFilterConfiguration {

    private JWTProcessorConfiguration jwtProcessorConfiguration;

    private JWTProcessor jwtProcessor;
    private ErrorHandler errorHandler;

    private boolean automaticTrailCompensation;

    ProtectedRouteFilterConfiguration() {
    }

    protected JWTProcessorConfiguration getJwtProcessorConfiguration() {
        return jwtProcessorConfiguration;
    }

    /**
     * Sets the {@link JWTProcessorConfiguration} instance that will be used by the {@link
     * AbstractProtectedRouteFilter}. The configuration instance is passed to a default {@link
     * JWTProcessor} instance for usage ({@link JWTProcessor#fromConfiguration})
     * <p>
     * If set, using {@link #setJwtProcessor} is considered illegal and will raise an exception when
     * constructing the {@link AbstractProtectedRouteFilter}
     * <p>
     * If not set, it is expected that a {@link JWTProcessor} instance is set via {@link
     * #setJwtProcessor}
     *
     * @param jwtProcessorConfiguration {@link JWTProcessorConfiguration} instance to use
     */
    public void setJwtProcessorConfiguration(
            JWTProcessorConfiguration jwtProcessorConfiguration) {
        this.jwtProcessorConfiguration = jwtProcessorConfiguration;
    }

    protected JWTProcessor getJwtProcessor() {
        return jwtProcessor;
    }

    /**
     * Sets the {@link JWTProcessor} instance that will be used by the {@link
     * AbstractProtectedRouteFilter}.
     * <p>
     * If set, using {@link #setJwtProcessorConfiguration} is considered illegal and will raise an
     * exception when constructing the {@link AbstractProtectedRouteFilter}
     * <p>
     * If not set, it is expected that a {@link JWTProcessorConfiguration} instance is set via
     * {@link #setJwtProcessorConfiguration}
     *
     * @param jwtProcessor {@link JWTProcessor} instance to use
     */
    public void setJwtProcessor(JWTProcessor jwtProcessor) {
        this.jwtProcessor = jwtProcessor;
    }

    protected ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    /**
     * Sets the {@link ErrorHandler} instance that will be used by the {@link
     * AbstractProtectedRouteFilter}.
     * <p>
     * If not set, the {@link AbstractProtectedRouteFilter} will use the default {@link
     * ErrorHandler} instance instead ({@link ErrorHandler#getDefault()})
     */
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    protected boolean isAutomaticTrailCompensation() {
        return automaticTrailCompensation;
    }

    /**
     * Enables/disables automatic trailing slash coverage
     *
     * @param automaticTrailCompensation enables automatic trailing slash coverage if {@code true},
     * disables it if {@code false}
     */
    public void setAutomaticTrailCompensation(boolean automaticTrailCompensation) {
        this.automaticTrailCompensation = automaticTrailCompensation;
    }
}
