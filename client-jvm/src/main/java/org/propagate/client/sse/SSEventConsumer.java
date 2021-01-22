package org.propagate.client.sse;

public interface SSEventConsumer extends AutoCloseable {
    void start();

    static SSEventConsumerBuilder newBuilder() {
        return new SSEventConsumerBuilder();
    }
}
