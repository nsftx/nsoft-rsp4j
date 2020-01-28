package com.nsoft.api.security.spring.resolver;

import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;
import com.nsoft.api.security.jwt.verifier.JWTProcessor;
import com.nsoft.api.security.jwt.verifier.JWTProcessorConfiguration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Extracts information from a Bearer token needed to construct a {@link JWTClaimsSet} and allows
 * {@link JWTClaimsSet} to be used as an argument in controller methods.
 *
 * @author Mislav Milicevic
 * @since 2019-10-28
 */
public final class JWTClaimsSetArgumentResolver extends
        JWTClaimsSetArgumentResolverSupport implements HandlerMethodArgumentResolver {

    public JWTClaimsSetArgumentResolver(JWTProcessor processor) {
        super(processor);
    }

    public JWTClaimsSetArgumentResolver(JWTProcessorConfiguration configuration) {
        super(configuration);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(JWTClaimsSet.class);
    }

    @Override
    public JWTClaimsSet resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        final String bearerToken = extractBearerToken(request);

        return getClaimsSet(bearerToken);
    }
}
