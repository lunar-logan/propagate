package org.propagate.client.provider.http;

import org.propagate.client.featureflagclient.FeatureFlagClient;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class FeatureFlagPollingTask implements Runnable {
    private final FeatureFlagClient featureFlagClient;
    private final Consumer<List<FeatureFlagRestEntity>> featureFlagsConsumer;

    public FeatureFlagPollingTask(FeatureFlagClient featureFlagClient, Consumer<List<FeatureFlagRestEntity>> featureFlagsConsumer) {
        this.featureFlagClient = Objects.requireNonNull(featureFlagClient);
        this.featureFlagsConsumer = Objects.requireNonNull(featureFlagsConsumer);
    }

    @Override
    public void run() {
        featureFlagClient.getAllFeatureFlags()
                .fold(
                        featureFlagsConsumer,
                        this::handleFailures
                );
    }

    private void handleFailures(Exception ex) {
        System.err.println("Exception polling feature flags, will retry");
        ex.printStackTrace();
    }
}
