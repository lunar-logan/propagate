package org.propagate.common.domain.util;

@FunctionalInterface
public interface CheckedConsumer<T> {
    void accept(T t) throws Exception;
}
