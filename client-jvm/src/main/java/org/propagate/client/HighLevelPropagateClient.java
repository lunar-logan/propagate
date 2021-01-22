package org.propagate.client;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class HighLevelPropagateClient implements PropagateClient {
    private final String env;
    private final PropagateClient delegate;

    public HighLevelPropagateClient(String env, PropagateClient delegate) {
        this.env = Objects.requireNonNull(env);
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public String eval(String key, String env, Map<String, Object> context, Supplier<String> fallbackSupplier) {
        return delegate.eval(key, env, context, fallbackSupplier);
    }

    @Override
    public void start() {
        delegate.start();
    }

    public String eval(String key, Map<String, Object> ctx, Supplier<String> fallbackSupplier) {
        return eval(key, env, ctx, fallbackSupplier);
    }

    static PropagateClientBuilder newBuilder(String env) {
        return new PropagateClientBuilder(env);
    }
}
