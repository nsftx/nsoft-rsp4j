package com.nsoft.api.security.spring.filter.route;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.Set;

public final class ProtectedRouteRegistry {

    final Set<AntPathRequestMatcher> protectedRoutes = new HashSet<>();

    public ProtectedRouteRegistry registerRoute(String route) {
        protectedRoutes.add(new AntPathRequestMatcher(route));
        return this;
    }

    public ProtectedRouteRegistry registerRoute(String route, String method) {
        protectedRoutes.add(new AntPathRequestMatcher(route, method));
        return this;
    }


}
