package com.nsoft.api.security.spring.filter.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class HeaderUtil {

    private HeaderUtil() {
    }

    public static Optional<String> extractBearerToken(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeader == null) {
            return Optional.empty();
        }

        String[] split = authorizationHeader.split(" ");

        if (split.length != 2) {
            return Optional.empty();
        }

        if (!split[0].equals("Bearer")) {
            return Optional.empty();
        }

        return Optional.of(split[1]);
    }
}
