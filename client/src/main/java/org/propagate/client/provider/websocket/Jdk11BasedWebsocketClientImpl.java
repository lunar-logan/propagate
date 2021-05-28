//package org.propagate.client.provider.websocket;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.propagate.client.mapper.ExpandedFeatureFlagMapper;
//import org.propagate.client.mapper.ExpandedFeatureFlagMapperImpl;
//import org.propagate.client.model.ExpandedFeatureFlag;
//import org.propagate.client.provider.websocket.jdk11.ReconnectingWebSocket;
//import org.propagate.client.spi.PropagateClient;
//import org.propagate.common.domain.FeatureFlag;
//import org.propagate.query.DefaultQueryFactoryImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.WebSocket;
//import java.time.Duration;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Random;
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.function.Supplier;
//
//public class Jdk11BasedWebsocketClientImpl implements PropagateClient {
//    private static final Logger logger = LoggerFactory.getLogger(Jdk11BasedWebsocketClientImpl.class);
//    private final Map<String, ExpandedFeatureFlag> featureFlags = new ConcurrentHashMap<>();
//    private final AtomicBoolean started = new AtomicBoolean(false);
//    private final ObjectMapper mapper = new ObjectMapper();
//    private final WebSocketConnector webSocketConnector = new WebSocketConnector();
//    private final String env;
//    private final URI uri;
//    private final ExecutorService executorService;
//    private final ExpandedFeatureFlagMapper expandedFeatureFlagMapper;
//
//    public Jdk11BasedWebsocketClientImpl(String env, URI uri, ExecutorService executorService, ExpandedFeatureFlagMapper expandedFeatureFlagMapper) {
//        this.env = env;
//        this.uri = uri;
//        this.executorService = executorService;
//        this.expandedFeatureFlagMapper = expandedFeatureFlagMapper;
//    }
//
//    public Jdk11BasedWebsocketClientImpl(String env, URI uri) {
//        this(env, uri, Executors.newCachedThreadPool(), new ExpandedFeatureFlagMapperImpl(new DefaultQueryFactoryImpl()));
//    }
//
//    @Override
//    public String eval(String key, Map<String, Object> ctx, Supplier<String> fallback) {
//        final ExpandedFeatureFlag expandedFeatureFlag = featureFlags.get(key);
//        if (expandedFeatureFlag == null) {
//            return fallback.get();
//        }
//
//        if (!expandedFeatureFlag.isTargeting()) {
//            return expandedFeatureFlag.getDefaultRolloutTargetingOff() != null ?
//                    expandedFeatureFlag.getDefaultRolloutTargetingOff()
//                    : fallback.get();
//        }
//
//        if (expandedFeatureFlag.getPercentRolloutRules() != null) {
//            final Optional<String> result = EvaluatorFactory.percentRolloutEvaluator(expandedFeatureFlag, env, ctx).eval();
//            return result.orElseGet(fallback);
//        }
//        if (expandedFeatureFlag.getConditionalRolloutRules() != null) {
//            Optional<String> result = EvaluatorFactory.conditionalRolloutEvaluator(expandedFeatureFlag, env, ctx).eval();
//            return result.orElseGet(fallback);
//        }
//        return expandedFeatureFlag.getDefaultRolloutTargetingOn() != null ?
//                expandedFeatureFlag.getDefaultRolloutTargetingOn()
//                : fallback.get();
//    }
//
//    @Override
//    public void run() {
//        if (started.compareAndSet(false, true)) {
//            webSocketConnector.run();
//            logger.info("Websocket started....");
//        }
//    }
//
//    protected void accept(CharSequence featureFlagText) {
//        try {
//            final List<FeatureFlag> streamingFeatureFlags = mapper.readValue(featureFlagText.toString(), new TypeReference<>() {
//            });
//            logger.info("Loaded feature flags: {}", streamingFeatureFlags);
//            expandAndUpdateFeatureFlagDefs(streamingFeatureFlags);
//        } catch (JsonProcessingException ex) {
//            logger.error("Failed to fetch feature flag", ex);
//        }
//    }
//
//    private void expandAndUpdateFeatureFlagDefs(List<FeatureFlag> streamingFeatureFlags) {
//        synchronized (featureFlags) {
//            streamingFeatureFlags.forEach(
//                    featureFlag -> featureFlags.put(featureFlag.getKey(), expandedFeatureFlagMapper.map(featureFlag))
//            );
//        }
//    }
//
//    @Override
//    public void close() throws Exception {
//        webSocketConnector.close();
//        executorService.shutdownNow();
//    }
//
//
//    private class WebSocketConnector implements Runnable, AutoCloseable {
//        private volatile boolean stop = false;
//        private volatile WebSocket ws;
//        private final Random rng = new Random(System.currentTimeMillis());
//
//        private ReconnectingWebSocket rws = new ReconnectingWebSocket(
//                () -> HttpClient.newHttpClient()
//                        .newWebSocketBuilder()
//                        .connectTimeout(Duration.ofMillis(300))
//                        .buildAsync(uri, new WebsocketEventListener())
//                        .join());
//
//        @Override
//        public void run() {
//            rws.run();
//        }
//
//        @Override
//        public void close() throws Exception {
////            stop = true;
////            if (ws != null) ws.abort();
//        }
//    }
//
//    private class WebsocketEventListener implements WebSocket.Listener {
//        @Override
//        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
//            return CompletableFuture.completedFuture(data)
//                    .thenAccept(Jdk11BasedWebsocketClientImpl.this::accept);
//        }
//
//        @Override
//        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
//            logger.info("Connection close for {}, status={}, reason=\"{}\"", webSocket, statusCode, reason);
//            return null;
//        }
//
//
//        @Override
//        public void onError(WebSocket webSocket, Throwable error) {
//            logger.error("Error while communicating through websocket", error);
//        }
//    }
//}
