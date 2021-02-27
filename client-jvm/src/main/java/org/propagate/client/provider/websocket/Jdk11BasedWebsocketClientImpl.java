package org.propagate.client.provider.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.propagate.client.eval.EvaluatorFactory;
import org.propagate.client.mapper.ExpandedFeatureFlagMapper;
import org.propagate.client.mapper.ExpandedFeatureFlagMapperImpl;
import org.propagate.client.model.ExpandedFeatureFlag;
import org.propagate.client.spi.PropagateClient;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.ID;
import org.propagate.common.domain.util.Either;
import org.propagate.query.DefaultQueryFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class Jdk11BasedWebsocketClientImpl implements PropagateClient {
    private static final Logger logger = LoggerFactory.getLogger(Jdk11BasedWebsocketClientImpl.class);
    private final Map<ID, ExpandedFeatureFlag> featureFlags = new ConcurrentHashMap<>();
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final ObjectMapper mapper = new ObjectMapper();

    private final String env;
    private final URI uri;
    private final ExecutorService executorService;
    private final ExpandedFeatureFlagMapper expandedFeatureFlagMapper;

    public Jdk11BasedWebsocketClientImpl(String env, URI uri, ExecutorService executorService, ExpandedFeatureFlagMapper expandedFeatureFlagMapper) {
        this.env = env;
        this.uri = uri;
        this.executorService = executorService;
        this.expandedFeatureFlagMapper = expandedFeatureFlagMapper;
    }

    public Jdk11BasedWebsocketClientImpl(String env, URI uri) {
        this(env, uri, Executors.newCachedThreadPool(), new ExpandedFeatureFlagMapperImpl(new DefaultQueryFactoryImpl()));
    }

    @Override
    public String eval(String key, Map<String, Object> ctx, Supplier<String> fallback) {
        synchronized (featureFlags) {
            final ExpandedFeatureFlag expandedFeatureFlag = featureFlags.get(ID.builder().key(key).env(env).build());
            if (expandedFeatureFlag == null) {
                return fallback.get();
            }
            if (expandedFeatureFlag.getPercentRollout() != null) {
                final Either<String> result = EvaluatorFactory.percentRolloutEvaluator(expandedFeatureFlag, ctx).eval();
                return result.orElseGet(fallback);
            }
            if (expandedFeatureFlag.getConditionalRollout() != null) {
                Either<String> result = EvaluatorFactory.conditionalRolloutEvaluator(expandedFeatureFlag, ctx).eval();
                return result.orElseGet(fallback);
            }
            return expandedFeatureFlag.getDefaultRollout() != null ?
                    expandedFeatureFlag.getDefaultRollout()
                    : fallback.get();
        }
    }

    @Override
    public void run() {
        if (started.compareAndSet(false, true)) {
            executorService.execute(
                    () -> HttpClient.newHttpClient()
                            .newWebSocketBuilder()
                            .buildAsync(uri, new WebsocketEventListener())
                            .join());
        }
    }

    protected void accept(CharSequence featureFlagText) {
        try {
            final List<FeatureFlag> streamingFeatureFlags = mapper.readValue(featureFlagText.toString(), new TypeReference<>() {
            });
            logger.info("Loaded feature flags: {}", streamingFeatureFlags);
            expandAndUpdateFeatureFlagDefs(streamingFeatureFlags);
        } catch (JsonProcessingException ex) {
            logger.error("Failed to fetch feature flag", ex);
        }
    }

    private void expandAndUpdateFeatureFlagDefs(List<FeatureFlag> streamingFeatureFlags) {
        synchronized (featureFlags) {
            streamingFeatureFlags.forEach(
                    featureFlag -> featureFlags.put(featureFlag.getId(), expandedFeatureFlagMapper.map(featureFlag))
            );
        }
    }

    private class WebsocketEventListener implements WebSocket.Listener {
        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            return CompletableFuture.completedFuture(data)
                    .thenAccept(Jdk11BasedWebsocketClientImpl.this::accept);
        }
    }
}
