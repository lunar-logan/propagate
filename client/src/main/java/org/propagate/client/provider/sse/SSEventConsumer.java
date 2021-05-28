package org.propagate.client.provider.sse;

public interface SSEventConsumer extends AutoCloseable {
    void start();

    static SSEventConsumerBuilder newBuilder() {
        return new SSEventConsumerBuilder();
    }
}
