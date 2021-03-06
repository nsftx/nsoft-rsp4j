package com.nsoft.api.security.spring.filter.route;

import static java.util.Objects.requireNonNull;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
 * @see #registerRoutes(String...)
 * @see #registerRoutes(Collection)
 * @see #registerRoutes(Map)
 * @since 2019-10-01
 */
public final class ProtectedRouteRegistry {

    private final boolean automaticTrail;

    final Set<AntPathRequestMatcher> protectedRoutes = new HashSet<>();

    public ProtectedRouteRegistry(boolean automaticTrail) {
        this.automaticTrail = automaticTrail;
    }

    /**
     * Registers a route to be considered as protected by {@link com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
     *
     * @param route to register, must not be {@code null}
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoute(final String route) {
        requireNonNull(route, "route must not be null");

        protectedRoutes.add(new AntPathRequestMatcher(route));

        if (automaticTrail) {
            protectedRoutes.add(new AntPathRequestMatcher(createCompensatoryRoute(route)));
        }

        return this;
    }

    /**
     * Registers a route, received from a {@link Supplier<String>}, to be considered as protected by
     * {@link com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
     *
     * @param routeSupplier used to supply a route to register, must not be null
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoute(final Supplier<String> routeSupplier) {
        requireNonNull(routeSupplier, "routeSupplier must not be null");

        return registerRoute(routeSupplier.get());
    }

    /**
     * Registers multiple routes to be considered as protected by {@link
     * com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
     *
     * @param routes to register, must not be {@code null}
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoutes(final String... routes) {
        requireNonNull(routes, "routes must not be null");

        Stream.of(routes).forEach(this::registerRoute);

        return this;
    }

    /**
     * Registers multiple routes to be considered as protected by {@link
     * com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
     *
     * @param routes to register, must not be {@code null}
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoutes(final Collection<String> routes) {
        requireNonNull(routes, "routes must not be null");

        routes.forEach(this::registerRoute);

        return this;
    }

    /**
     * Registers a route to be considered as protected by {@link com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
     * when the route is invoked through a specified HTTP request method.
     * <p>
     * For a list of valid HTTP request methods, reference https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
     *
     * @param route to register, must not be {@code null}
     * @param method delimiter, must be a valid HTTP request method (https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods)
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoute(final String route, final String method) {
        requireNonNull(route, "route must not be null");
        requireNonNull(method, "method must not be null");

        protectedRoutes.add(new AntPathRequestMatcher(route, method));

        if (automaticTrail) {
            protectedRoutes.add(new AntPathRequestMatcher(createCompensatoryRoute(route), method));
        }

        return this;
    }

    /**
     * Registers a route to be considered as protected by {@link com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter}
     * when the route is invoked through a specified HTTP request method.
     * <p>
     * For a list of valid HTTP request methods, reference https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
     *
     * @param route to register, must not be {@code null}
     * @param methods set of method delimiters, must be a set of valid HTTP request methods
     * (https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods)
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoute(final String route, final String... methods) {
        requireNonNull(route, "route must not be null");
        requireNonNull(route, "methods must not be null");

        Stream.of(methods).forEach(method -> registerRoute(route, method));

        return this;
    }

    /**
     * Registers multiple routes to be considered as protected by {@link
     * com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter} when the routes are
     * invoked through a specified HTTP request method.
     * <p>
     * {@code registerRoutes} takes a Map of key-value pairs as a parameter, where the key is the
     * route and the value is the method associated with the route.
     * <p>
     * For a list of valid HTTP request methods, reference https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
     *
     * @param routes to register, must not be {@code null}
     * @return current {@link ProtectedRouteRegistry} instance
     */
    public ProtectedRouteRegistry registerRoutes(final Map<String, String> routes) {
        requireNonNull(routes, "routes must not be null");

        routes.forEach(this::registerRoute);

        return this;
    }

    private String createCompensatoryRoute(String route) {
        requireNonNull(route, "route must not be null");

        if (route.endsWith("/")) {
            route = route.substring(0, route.length() - 1);
        } else {
            route = route + "/";
        }

        return route;
    }
}
