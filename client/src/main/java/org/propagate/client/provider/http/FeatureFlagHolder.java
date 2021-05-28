package org.propagate.client.provider.http;

import org.propagate.common.rest.entity.FeatureFlagRestEntity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureFlagHolder {
    private final Map<String, FeatureFlagRestEntity> featureFlagMap = new ConcurrentHashMap<>();

    public List<FeatureFlagRestEntity> getAllFeatureFlags() {
        return Collections.unmodifiableList(new ArrayList<>(featureFlagMap.values()));
    }

    public Optional<FeatureFlagRestEntity> getFeatureFlag(String key) {
        return Optional.ofNullable(featureFlagMap.get(key));
    }

    public void setFeatureFlags(Collection<FeatureFlagRestEntity> featureFlags) {
        if (featureFlags == null) {
            return;
        }
        synchronized (featureFlagMap) {
            featureFlagMap.clear();
            for (FeatureFlagRestEntity ff : featureFlags) {
                featureFlagMap.put(ff.getKey(), ff.toBuilder().build());
            }
        }
    }
}
