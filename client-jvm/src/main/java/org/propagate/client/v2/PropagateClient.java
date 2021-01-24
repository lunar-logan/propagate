package org.propagate.client.v2;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public interface PropagateClient extends Runnable {
    String eval(String key, Map<String, Object> ctx, Supplier<String> fallback);

    static PropagateClientBuilder newBuilder(String env) {
        return new PropagateClientBuilder(Objects.requireNonNull(env));
    }
}
