package org.propagate.client.sse;

import java.net.URI;
import java.util.function.Consumer;

public class SSEventConsumerBuilder {
    private String uri;
    private Consumer<SSEvent> consumer;

    public SSEventConsumerBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public SSEventConsumerBuilder onMessage(Consumer<SSEvent> consumer) {
        this.consumer = consumer;
        return this;
    }

    public SSEventConsumer build() {
        return new SSEventConsumerImpl(URI.create(uri), consumer);
    }
}
