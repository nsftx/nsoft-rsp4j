package com.nsoft.api.security.spring.filter.internal.compatibility.j9;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

public final class Objects {

    private Objects() {
    }

    public static <T> T requireNonNullElseGet(T obj, Supplier<? extends T> supplier) {
        return (obj != null) ? obj
                : requireNonNull(requireNonNull(supplier, "supplier").get(), "supplier.get()");
    }
}
