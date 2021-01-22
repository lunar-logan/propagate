package org.propagate.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.propagate.client.sse.SSEvent;
import org.propagate.client.sse.SSEventConsumer;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PropagateClientImpl implements PropagateClient {
    private static final Logger logger = LoggerFactory.getLogger(PropagateClientImpl.class);

    private final AtomicBoolean started = new AtomicBoolean(false);
    private final Map<ID, ExpandedFeatureFlag> featureFlags = new ConcurrentHashMap<>();
    private final String uri;
    private final ExpandedFeatureFlagMapper expandedFeatureFlagMapper;

    public PropagateClientImpl(String uri, ExpandedFeatureFlagMapper expandedFeatureFlagMapper) {
        this.uri = uri;
        this.expandedFeatureFlagMapper = expandedFeatureFlagMapper;
    }

    @Override
    public void start() {
        if (started.get()) {
            logger.warn("Client already started");
            return;
        }

        if (started.compareAndSet(false, true)) {
            final SSEventConsumer consumer = SSEventConsumer.newBuilder()
                    .uri(uri)
                    .onMessage(new DefaultSSEventConsumer(new ObjectMapper()))
                    .build();
            consumer.start();
        }
    }

    @Override
    public String eval(String key, String env, Map<String, Object> context, Supplier<String> fallbackSupplier) {
        synchronized (featureFlags) {
            final ExpandedFeatureFlag expandedFeatureFlag = featureFlags.get(ID.valueOf(key, env));
            if (expandedFeatureFlag == null) {
                return fallbackSupplier.get();
            }

            if (expandedFeatureFlag.getPercentRollout() != null) {
                logger.warn("Percent rollout not supported yet");
            }
            if (expandedFeatureFlag.getConditionalRollout() != null) {
                return expandedFeatureFlag.getConditionalRollout().entrySet()
                        .stream()
                        .filter(e -> e.getKey().eval(context))
                        .findAny()
                        .map(Map.Entry::getValue)
                        .or(() -> Optional.ofNullable(expandedFeatureFlag.getDefaultRollout()))
                        .orElseGet(fallbackSupplier);
            }
            return expandedFeatureFlag.getDefaultRollout() != null ? expandedFeatureFlag.getDefaultRollout() : fallbackSupplier.get();
        }
    }

    private class DefaultSSEventConsumer implements Consumer<SSEvent> {
        private final ObjectMapper mapper;

        public DefaultSSEventConsumer(ObjectMapper mapper) {
            this.mapper = Objects.requireNonNull(mapper);
        }

        @Override
        public void accept(SSEvent event) {
            try {
                final List<FeatureFlag> flags = mapper.readValue(event.getData(), new TypeReference<>() {
                });
                logger.info("Loaded flags [{}]", flags);
                synchronized (featureFlags) {
                    flags.forEach(flag -> featureFlags.put(flag.getId(), expandedFeatureFlagMapper.map(flag)));
                }
            } catch (JsonProcessingException e) {
                logger.error("Exception parsing feature flag", e);
            }
        }
    }
}
