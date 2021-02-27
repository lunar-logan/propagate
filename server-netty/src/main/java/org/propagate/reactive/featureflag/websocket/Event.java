package org.propagate.reactive.featureflag.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Event<T> {
    private final T data;
}
