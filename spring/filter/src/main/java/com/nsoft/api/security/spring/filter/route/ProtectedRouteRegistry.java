package com.nsoft.api.security.spring.filter.route;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A store for protected routes that are checked by {@link com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
 * <p>
 * In order to register a protected route, the user must invoke {@link
 * ProtectedRouteRegistry#registerRoute}
 * <p>
 * {@link ProtectedRouteRegistry} uses Spring's {@link AntPathRequestMatcher} in the background when
 * storing routes, so the usage of certain wildcards is permitted:
 * <ul>
 * <li>{@code ?} matches one character</li>
 * <li>{@code *} matches zero or more characters</li>
 * <li>{@code **} matches zero or more <em>directories</em> in a path</li>
 * <li>{@code {spring:[a-z]+}} matches the regexp {@code [a-z]+} as a path variable named "spring"</li>
 * </ul>
 * <p>
 * The list of wildcards above is taken from {@link org.springframework.util.AntPathMatcher} Javadoc.
 *
 * <h3>Examples</h3>
 * ProtectedRouteRegistry#registerRoute("/a/b/c"); // matches path '/a/b/c/' exactly
 * ProtectedRouteRegistry#registerRoute("/a/b/*"); // matches path'/a/b/[?]' where [?] is any variation of 0 or more characters
 * ProtectedRouteRegistry#registerRoute("/a/b/**"); // matches path'/a/b/[?/?/?]' where [?/?/?] is any variation of 0 or more directories*
 *
 * @author Mislav Milicevic
 * @see #registerRoute(String)
 * @see #registerRoute(String, String)
 * @see #enableAutomaticTrailCompensation(boolean)
 * @since 2019-10-01
 */
public final class ProtectedRouteRegistry {

    private boolean automaticTrail = true;

    final Set<AntPathRequestMatcher> protectedRoutes = new HashSet<>();

    /**
     * Registers a route to be considered as protected by {@link com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
     *
     * @param route to register, must not be null
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoute(final String route) {
        Objects.requireNonNull(route);

        protectedRoutes.add(new AntPathRequestMatcher(route));

        if (automaticTrail) {
            protectedRoutes.add(new AntPathRequestMatcher(createCompensatoryRoute(route)));
        }

        return this;
    }

    /**
     * Registers a route to be considered as protected by {@link com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
     * when the route is invoked through a specified HTTP request method.
     * <p>
     * For a list of valid HTTP request methods, reference https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
     *
     * @param route to register, must not be null
     * @param method delimiter, must be a valid HTTP request method (https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods)
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoute(final String route, final String method) {
        Objects.requireNonNull(route);
        Objects.requireNonNull(method);

        protectedRoutes.add(new AntPathRequestMatcher(route, method));

        if (automaticTrail) {
            protectedRoutes.add(new AntPathRequestMatcher(createCompensatoryRoute(route), method));
        }
        return this;
    }

    /**
     * When configuring a WebMvc application, if {@code PathMatchConfigurer#setUserTrailingSlashMatch}
     * is enabled (enabled by default), incoming request are able to bypass the Auth filter by
     * adding a trailing slash.
     * <p>
     * We want {@code /route} and {@code /route/} to produce same results when interacted upon with
     * the same REST verb. This situation leads to users registering their routes two times (with
     * and without trailing slash)
     * <p>
     * When {@code enableAutomaticTrailCompensation} is enabled, all registered routes will
     * automatically receive a compensatory route to handle this scenario.
     * <p>
     * Automatic trail compensation is enabled by default.
     *
     * @param automaticTrail enable/disable trail compensation
     */
    public void enableAutomaticTrailCompensation(boolean automaticTrail) {
        this.automaticTrail = automaticTrail;
    }

    private String createCompensatoryRoute(String route) {
        Objects.requireNonNull(route);

        if (route.endsWith("/")) {
            route = route.substring(0, route.length() - 1);
        } else {
            route = route + "/";
        }

        return route;
    }
}
