package com.nsoft.api.security.spring.resolver.internal.compatibility.j9;

import static java.util.Objects.requireNonNull;

public final class Objects {

    private Objects() {
    }

    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }
}
