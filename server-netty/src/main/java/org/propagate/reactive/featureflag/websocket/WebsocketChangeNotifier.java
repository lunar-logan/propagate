package org.propagate.reactive.featureflag.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.util.Either;
import org.propagate.reactive.featureflag.service.FeatureFlagService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class WebsocketChangeNotifier implements WebSocketHandler {
    private static final int DEFAULT_DELAY_DURATION = 10;
    private final FeatureFlagService featureFlagService;
    private final ObjectMapper mapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                generateDelayedFFUpdateEvents(DEFAULT_DELAY_DURATION)
                        .map(Either.lift(featureFlags -> session.textMessage(mapper.writeValueAsString(featureFlags))))
                        .log()
                        .filter(Either::isSuccess)
                        .map(Either::getRight)
        );
    }

    /**
     * Returns a {@code Flux} that generates an event every "x" seconds, containing all
     * the feature flags.
     *
     * @param delayDuration amount of time to wait in seconds before emitting each event
     * @return {@code Flux<List<FeatureFlag>>}
     */
    private Flux<List<FeatureFlag>> generateDelayedFFUpdateEvents(int delayDuration) {
        return Flux.interval(Duration.ofSeconds(delayDuration))
                .flatMap(ignore -> featureFlagService.getAllFeatureFlags()
                        .collectList());
    }
}
