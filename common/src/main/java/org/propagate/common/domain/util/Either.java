package org.propagate.common.domain.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class Either<R> {
    private final Exception left;
    private final R right;

    public Either(Exception left, R right) {
        this.left = left;
        this.right = right;
    }

    public Exception getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public boolean isSuccess() {
        return getLeft() == null;
    }

    public static <R> Either<R> left(Exception ex) {
        return new Either<>(ex, null);
    }

    public static <R> Either<R> right(R value) {
        return new Either<>(null, value);
    }

    public <V> Either<V> map(Function<R, V> mapper) {
        if (isSuccess()) {
            return new Either<>(null, mapper.apply(getRight()));
        }
        return new Either<>(getLeft(), null);
    }

    public R orElseGet(Supplier<R> supplier) {
        if (!isSuccess()) {
            return supplier.get();
        }
        return getRight();
    }
}
