package com.nsoft.api.security.spring.filter;

import static java.util.Objects.requireNonNull;

import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.jwt.verifier.JWTProcessorConfiguration;
import com.nsoft.api.security.spring.filter.error.ErrorHandler;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Configuration object used to configure the behaviour of {@link AbstractProtectedRouteFilter}
 *
 * @author Mislav Milicevic
 * @since 2020-01-23
 */
public final class ProtectedRouteFilterConfiguration {

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
     * If set, using {@link #setJWTProcessor} is considered illegal and will raise an exception when
     * constructing the {@link AbstractProtectedRouteFilter}
     * <p>
     * If not set, it is expected that a {@link JWTProcessor} instance is set via {@link
     * #setJWTProcessor}
     *
     * @param jwtProcessorConfiguration {@link JWTProcessorConfiguration} instance to use
     */
    public void setJWTProcessorConfiguration(
            final JWTProcessorConfiguration jwtProcessorConfiguration) {
        this.jwtProcessorConfiguration = jwtProcessorConfiguration;
    }

    /**
     * Sets the {@link JWTProcessorConfiguration} instance that will be used by the {@link
     * AbstractProtectedRouteFilter}. The configuration instance is passed to a default {@link
     * JWTProcessor} instance for usage ({@link JWTProcessor#fromConfiguration})
     * <p>
     * If set, using {@link #setJWTProcessor} is considered illegal and will raise an exception when
     * constructing the {@link AbstractProtectedRouteFilter}
     * <p>
     * If not set, it is expected that a {@link JWTProcessor} instance is set via {@link
     * #setJWTProcessor}
     *
     * @param supplier {@link JWTProcessorConfiguration} instance supplier, must not be {@code
     * null}
     */
    public void setJWTProcessorConfiguration(final Supplier<JWTProcessorConfiguration> supplier) {
        requireNonNull(supplier, "supplier must not be null");

        this.jwtProcessorConfiguration = supplier.get();
    }

    protected JWTProcessor getJwtProcessor() {
        return jwtProcessor;
    }

    /**
     * Sets the {@link JWTProcessor} instance that will be used by the {@link
     * AbstractProtectedRouteFilter}.
     * <p>
     * If set, using {@link #setJWTProcessorConfiguration} is considered illegal and will raise an
     * exception when constructing the {@link AbstractProtectedRouteFilter}
     * <p>
     * If not set, it is expected that a {@link JWTProcessorConfiguration} instance is set via
     * {@link #setJWTProcessorConfiguration}
     *
     * @param jwtProcessor {@link JWTProcessor} instance to use
     */
    public void setJWTProcessor(final JWTProcessor jwtProcessor) {
        this.jwtProcessor = jwtProcessor;
    }

    /**
     * Sets the {@link JWTProcessor} instance that will be used by the {@link
     * AbstractProtectedRouteFilter}.
     * <p>
     * If set, using {@link #setJWTProcessorConfiguration} is considered illegal and will raise an
     * exception when constructing the {@link AbstractProtectedRouteFilter}
     * <p>
     * If not set, it is expected that a {@link JWTProcessorConfiguration} instance is set via
     * {@link #setJWTProcessorConfiguration}
     *
     * @param supplier {@link JWTProcessor} instance supplier, must not be {@code null}
     */
    public void setJWTProcessor(final Supplier<JWTProcessor> supplier) {
        requireNonNull(supplier, "supplier must not be null");

        this.jwtProcessor = supplier.get();
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
     *
     * @param errorHandler {@link ErrorHandler} instance to use
     */
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    /**
     * Sets the {@link ErrorHandler} instance that will be used by the {@link
     * AbstractProtectedRouteFilter}.
     * <p>
     * If not set, the {@link AbstractProtectedRouteFilter} will use the default {@link
     * ErrorHandler} instance instead ({@link ErrorHandler#getDefault()})
     *
     * @param supplier {@link ErrorHandler} instance supplier, must not be {@code null}
     */
    public void setErrorHandler(final Supplier<ErrorHandler> supplier) {
        requireNonNull(supplier, "supplier must not be null");

        this.errorHandler = supplier.get();
    }

    protected boolean isAutomaticTrailCompensation() {
        return automaticTrailCompensation;
    }

    /**
     * Enables/disables automatic trailing slash compensation.
     * <p>
     * When configuring a WebMvc application, if {@code PathMatchConfigurer#setUserTrailingSlashMatch}
     * is enabled (enabled by default), incoming request are able to bypass the Auth filter by
     * adding a trailing slash.
     * <p>
     * We want {@code /route} and {@code /route/} to produce same results when interacted upon with
     * the same REST verb. This situation leads to users registering their routes two times (with
     * and without trailing slash)
     * <p>
     * When automatic trail compensation is enabled, all registered routes will automatically
     * receive a compensatory route to handle this scenario.
     * <p>
     * Automatic trail compensation is enabled by default.
     *
     * @param automaticTrailCompensation enables automatic trailing slash compensation if {@code
     * true}, disables it if {@code false}
     */
    public void setAutomaticTrailCompensation(boolean automaticTrailCompensation) {
        this.automaticTrailCompensation = automaticTrailCompensation;
    }

    /**
     * Enables/disables automatic trailing slash compensation.
     * <p>
     * When configuring a WebMvc application, if {@code PathMatchConfigurer#setUserTrailingSlashMatch}
     * is enabled (enabled by default), incoming request are able to bypass the Auth filter by
     * adding a trailing slash.
     * <p>
     * We want {@code /route} and {@code /route/} to produce same results when interacted upon with
     * the same REST verb. This situation leads to users registering their routes two times (with
     * and without trailing slash)
     * <p>
     * When automatic trail compensation is enabled, all registered routes will automatically
     * receive a compensatory route to handle this scenario.
     * <p>
     * Automatic trail compensation is enabled by default.
     *
     * @param supplier {@link BooleanSupplier}
     */
    public void setAutomaticTrailCompensation(final BooleanSupplier supplier) {
        requireNonNull(supplier, "supplier must not be null");

        this.automaticTrailCompensation = supplier.getAsBoolean();
    }
}
