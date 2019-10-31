package com.nsoft.api.security.spring.resolver;

import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;
import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.spring.resolver.internal.util.HeaderUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
    private JWTProcessor processor = JWTProcessor.getDefault();

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

    /**
     * Sets the {@link JWTProcessor} which will be used to process incoming Bearer tokens and
     * construct {@link JWTClaimsSet} instances.
     * <p>
     * If the specified {@link JWTProcessor} is {@code null}, the processor gets set to the default
     * implementation ({@link JWTProcessor#getDefault()})
     *
     * @param processor to use when processing incoming bearer tokens
     */
    public void setProcessor(JWTProcessor processor) {
        this.processor = Objects.requireNonNullElse(processor, JWTProcessor.getDefault());
    }

}
