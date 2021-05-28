package org.propagate.client.provider.sse;

import java.util.Objects;

public class SSEvent {
    private final String id;
    private final String event;
    private final String data;

    public SSEvent(String id, String event, String data) {
        this.id = id;
        this.event = event;
        this.data = Objects.requireNonNull(data);
    }

    public String getId() {
        return id;
    }

    public String getEvent() {
        return event;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "SSEvent{" +
                "id='" + id + '\'' +
                ", event='" + event + '\'' +
                ", data=" + data +
                '}';
    }
}
