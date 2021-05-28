package org.propagate.client.provider.http;

import org.propagate.client.eval.EvaluationContext;
import org.propagate.client.eval.FeatureFlagEvaluationService;
import org.propagate.client.featureflagclient.FeatureFlagClient;
import org.propagate.client.spi.PropagateClient;
import org.propagate.client.tenant.TenantContext;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PropagateHttpClient implements PropagateClient {
    private final TenantContext tenantContext;
    private final FeatureFlagClient featureFlagClient;
    private final FeatureFlagHolder featureFlagHolder;
    private final FeatureFlagEvaluationService featureFlagEvaluationService;
    private final ScheduledExecutorService scheduledExecutorService;

    public PropagateHttpClient(TenantContext tenantContext, FeatureFlagClient featureFlagClient, FeatureFlagHolder featureFlagHolder, FeatureFlagEvaluationService featureFlagEvaluationService, ScheduledExecutorService scheduledExecutorService) {
        this.tenantContext = Objects.requireNonNull(tenantContext);
        this.featureFlagClient = Objects.requireNonNull(featureFlagClient);
        this.featureFlagHolder = Objects.requireNonNull(featureFlagHolder);
        this.featureFlagEvaluationService = Objects.requireNonNull(featureFlagEvaluationService);
        this.scheduledExecutorService = Objects.requireNonNull(scheduledExecutorService);
    }

    public PropagateHttpClient(TenantContext tenantContext, FeatureFlagClient featureFlagClient, FeatureFlagEvaluationService featureFlagEvaluationService) {
        this(tenantContext, featureFlagClient, new FeatureFlagHolder(), featureFlagEvaluationService, Executors.newScheduledThreadPool(2));
    }


    @Override
    public String eval(String key, Map<String, Object> ctx, Supplier<String> fallback) {
        return featureFlagHolder.getFeatureFlag(key)
                .flatMap(ff -> evalOptionally(ff, ctx, fallback))
                .orElseGet(fallback);
    }

    private Optional<String> evalOptionally(FeatureFlagRestEntity ff, Map<String, Object> ctx, Supplier<String> fallback) {
        try {
            final EvaluationContext evaluationContext = new EvaluationContext(tenantContext.getEnvironment(), ctx);
            return Optional.of(featureFlagEvaluationService.eval(ff, evaluationContext, fallback));
        } catch (Exception ex) {
            System.err.println("Failed to eval feature flag \"" + ff.getKey() + "\"");
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void close() throws Exception {
        scheduledExecutorService.shutdownNow();
    }

    @Override
    public void run() {
        scheduledExecutorService.scheduleAtFixedRate(new FeatureFlagPollingTask(featureFlagClient, new HandleFeatureFlags()), 0, 30, TimeUnit.SECONDS);
    }

    private class HandleFeatureFlags implements Consumer<List<FeatureFlagRestEntity>> {

        @Override
        public void accept(List<FeatureFlagRestEntity> featureFlagRestEntities) {
            featureFlagHolder.setFeatureFlags(featureFlagRestEntities);
        }
    }
}
