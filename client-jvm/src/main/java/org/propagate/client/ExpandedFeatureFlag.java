package org.propagate.client;

import org.propagate.common.domain.FeatureFlagType;
import org.propagate.query.Query;

import java.util.List;
import java.util.Map;

public class ExpandedFeatureFlag {
    private String key;
    private String env;
    private String name;
    private String description;
    private List<String> variations;
    private FeatureFlagType type;
    private Map<Query, String> conditionalRollout;
    private Map<String, Integer> percentRollout;
    private String defaultRollout;
    private boolean archived;

    public ExpandedFeatureFlag() {
    }

    public ExpandedFeatureFlag(String key, String env, String name, String description, List<String> variations, FeatureFlagType type, Map<Query, String> conditionalRollout, Map<String, Integer> percentRollout, String defaultRollout, boolean archived) {
        this.key = key;
        this.env = env;
        this.name = name;
        this.description = description;
        this.variations = variations;
        this.type = type;
        this.conditionalRollout = conditionalRollout;
        this.percentRollout = percentRollout;
        this.defaultRollout = defaultRollout;
        this.archived = archived;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getVariations() {
        return variations;
    }

    public void setVariations(List<String> variations) {
        this.variations = variations;
    }

    public FeatureFlagType getType() {
        return type;
    }

    public void setType(FeatureFlagType type) {
        this.type = type;
    }

    public Map<Query, String> getConditionalRollout() {
        return conditionalRollout;
    }

    public void setConditionalRollout(Map<Query, String> conditionalRollout) {
        this.conditionalRollout = conditionalRollout;
    }

    public Map<String, Integer> getPercentRollout() {
        return percentRollout;
    }

    public void setPercentRollout(Map<String, Integer> percentRollout) {
        this.percentRollout = percentRollout;
    }

    public String getDefaultRollout() {
        return defaultRollout;
    }

    public void setDefaultRollout(String defaultRollout) {
        this.defaultRollout = defaultRollout;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "ExpandedFeatureFlag{" +
                "key='" + key + '\'' +
                ", env='" + env + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", variations=" + variations +
                ", type=" + type +
                ", conditionalRollout=" + conditionalRollout +
                ", percentRollout=" + percentRollout +
                ", defaultRollout='" + defaultRollout + '\'' +
                ", archived=" + archived +
                '}';
    }
}
