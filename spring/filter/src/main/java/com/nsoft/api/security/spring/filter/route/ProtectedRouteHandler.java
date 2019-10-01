package com.nsoft.api.security.spring.filter.route;

import javax.servlet.http.HttpServletRequest;

public class ProtectedRouteHandler {

    private final ProtectedRouteRegistry protectedRouteRegistry;

    public ProtectedRouteHandler(ProtectedRouteRegistry protectedRouteRegistry) {
        this.protectedRouteRegistry = protectedRouteRegistry;
    }

    public boolean requestNeedsAuthorization(HttpServletRequest request) {
        return protectedRouteRegistry.protectedRoutes.stream().anyMatch(x -> x.matches(request));
    }
}
