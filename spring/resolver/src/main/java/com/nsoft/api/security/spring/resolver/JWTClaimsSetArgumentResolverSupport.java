package com.nsoft.api.security.spring.resolver;

import static java.util.Objects.requireNonNull;

import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;
import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.jwt.verifier.JWTProcessorConfiguration;
import com.nsoft.api.security.spring.resolver.internal.util.HeaderUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Support class which provides support for bearer token and {@link JWTClaimsSet} extraction.
 *
 * @author Mislav Milicevic
 * @since 2019-10-28
 */
public abstract class JWTClaimsSetArgumentResolverSupport {

    private static final Exception BEARER_TOKEN_EXCEPTION = new Exception(
            "Missing or invalid token");
    private static final Exception CLAIMS_SET_EXCEPTION = new Exception("Missing JWTClaimsSet");

    /**
     * This {@link JWTProcessor} is used to process incoming Bearer tokens and construct {@link
     * JWTClaimsSet} instances
     */
    private JWTProcessor processor;

    protected JWTClaimsSetArgumentResolverSupport(final JWTProcessor processor) {
        this.processor = requireNonNull(processor, "processor must not be null");
    }

    protected JWTClaimsSetArgumentResolverSupport(final JWTProcessorConfiguration configuration) {
        this.processor = JWTProcessor
                .fromConfiguration(requireNonNull(configuration, "configuration must not be null"));
    }

    /**
     * Extracts a bearer token from an incoming HTTP request
     *
     * @param request incoming HTTP request
     * @return Bearer token
     * @throws Exception if the bearer token is missing or invalid
     */
    protected String extractBearerToken(HttpServletRequest request) throws Exception {
        return HeaderUtil.extractBearerToken(request)
                .orElseThrow(() -> BEARER_TOKEN_EXCEPTION);
    }

    /**
     * Returns a {@link JWTClaimsSet} constructed from a Bearer token
     *
     * @param bearerToken Bearer token
     * @return {@link JWTClaimsSet} constructed from a Bearer token
     * @throws Exception if {@link JWTClaimsSet} is missing
     */
    protected JWTClaimsSet getClaimsSet(String bearerToken) throws Exception {
        return processor.process(bearerToken).orElseThrow(() -> CLAIMS_SET_EXCEPTION);
    }
}
