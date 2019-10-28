package com.nsoft.api.security.spring.resolver;

import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;
import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.spring.resolver.internal.util.HeaderUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public final class JWTClaimsSetArgumentResolver implements HandlerMethodArgumentResolver {

    private final JWTProcessor jwtProcessor = JWTProcessor.getDefault();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(JWTClaimsSet.class);
    }

    @Override
    public JWTClaimsSet resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        final String bearerToken = HeaderUtil.extractBearerToken(request)
                .orElseThrow(() -> new Exception("Missing or invalid bearer token"));

        return jwtProcessor
                .process(bearerToken)
                .orElseThrow(() -> new Exception("Missing JWTClaimsSet"));
    }
}
