package org.propagate.client.spi;

import java.util.Map;
import java.util.function.Supplier;

public interface PropagateClient extends Runnable, AutoCloseable {
    String eval(String key, Map<String, Object> ctx, Supplier<String> fallback);

    static PropagateClientBuilder builder() {
        return new PropagateClientBuilder();
    }
}
