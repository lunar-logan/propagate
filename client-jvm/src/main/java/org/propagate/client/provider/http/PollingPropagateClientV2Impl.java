package org.propagate.client.provider.http;

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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

@Deprecated
public class PollingPropagateClientV2Impl implements PropagateClient {
    private static final Logger logger = LoggerFactory.getLogger(PollingPropagateClientV2Impl.class);

    private final Map<ID, ExpandedFeatureFlag> featureFlags = new ConcurrentHashMap<>();

    private final String env;
    private final URI uri;
    private final Supplier<HttpClient> client;
    private final ScheduledExecutorService scheduler;
    private final ExpandedFeatureFlagMapper expandedFeatureFlagMapper;
    private final long delayMillis;
    private final AtomicBoolean started = new AtomicBoolean(false);

    public PollingPropagateClientV2Impl(String env, URI uri, Supplier<HttpClient> client, ScheduledExecutorService scheduler, ExpandedFeatureFlagMapper expandedFeatureFlagMapper, long delayMillis) {
        this.env = env;
        this.uri = uri;
        this.client = client;
        this.scheduler = scheduler;
        this.expandedFeatureFlagMapper = expandedFeatureFlagMapper;
        this.delayMillis = delayMillis;
    }

    public PollingPropagateClientV2Impl(String env, URI uri) {
        this(env, uri, new HttpClientSupplier(),
                Executors.newSingleThreadScheduledExecutor(), new ExpandedFeatureFlagMapperImpl(new DefaultQueryFactoryImpl()),
                5000);
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
            scheduler.scheduleWithFixedDelay(
                    new PollingTask(client.get(), uri, new ObjectMapper(), expandedFeatureFlagMapper),
                    0,
                    delayMillis,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    private class PollingTask implements Runnable {
        private final HttpClient client;
        private final URI uri;
        private final ObjectMapper objectMapper;
        private final ExpandedFeatureFlagMapper featureFlagMapper;

        public PollingTask(HttpClient client, URI uri, ObjectMapper objectMapper, ExpandedFeatureFlagMapper featureFlagMapper) {
            this.client = client;
            this.uri = uri;
            this.objectMapper = objectMapper;
            this.featureFlagMapper = featureFlagMapper;
        }

        @Override
        public void run() {
            final HttpRequest request = getHttpRequest();
            try {
                final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final List<FeatureFlag> streamingFeatureFlags = objectMapper.readValue(response.body(), new TypeReference<>() {
                });
                logger.info("Loaded feature flags: {}", streamingFeatureFlags);
                synchronized (featureFlags) {
                    streamingFeatureFlags.forEach(featureFlag -> {
                        featureFlags.put(featureFlag.getId(), featureFlagMapper.map(featureFlag));
                    });
                }
            } catch (Exception ex) {
                logger.error("Failed to fetch feature flag", ex);
            }
        }

        private HttpRequest getHttpRequest() {
            return HttpRequest.newBuilder(uri).GET().build();
        }
    }

    private static class HttpClientSupplier implements Supplier<HttpClient> {

        @Override
        public HttpClient get() {
            return HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMillis(303))
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();
        }
    }
}
