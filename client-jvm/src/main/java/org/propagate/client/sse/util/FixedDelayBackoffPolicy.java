package org.propagate.client.sse.util;

import java.time.Duration;
import java.util.Objects;

public class FixedDelayBackoffPolicy implements BackoffPolicy {
    private final Duration delay;

    public FixedDelayBackoffPolicy(Duration delay) {
        this.delay = Objects.requireNonNull(delay);
    }

    @Override
    public long nextDelayMillis() {
        return delay.toMillis();
    }
}
