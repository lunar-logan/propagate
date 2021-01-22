package org.propagate.client.sse.util;

import java.time.Duration;
import java.util.Objects;

public interface BackoffPolicy {
    long nextDelayMillis();

    static BackoffPolicy fixedDelay(Duration duration) {
        return new FixedDelayBackoffPolicy(Objects.requireNonNull(duration));
    }
}
