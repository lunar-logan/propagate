package org.propagate.client;

import java.util.Map;
import java.util.function.Supplier;

public interface PropagateClient {
    String eval(String key, String env, Map<String, Object> context, Supplier<String> fallbackSupplier);

    void start();


}
