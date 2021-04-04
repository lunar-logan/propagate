package org.propagate.client.model;

import org.propagate.common.domain.FeatureFlagType;
import org.propagate.query.Query;

import java.util.List;
import java.util.Map;

public class ExpandedFeatureFlag {
    private String id;
    private String name;
    private String description;
    private List<String> variations;
    private FeatureFlagType type;
    private Map<String, Map<Query, String>> conditionalRolloutRules;
    private Map<String, List<PercentRollout>> percentRolloutRules;
    private String defaultRolloutTargetingOn;
    private String defaultRolloutTargetingOff;
    private boolean targeting;
    private boolean archived;

    public ExpandedFeatureFlag() {
    }

    public ExpandedFeatureFlag(String id, String name, String description, List<String> variations, FeatureFlagType type, Map<String, Map<Query, String>> conditionalRolloutRules, Map<String, List<PercentRollout>> percentRolloutRules, String defaultRolloutTargetingOn, String defaultRolloutTargetingOff, boolean targeting, boolean archived) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.variations = variations;
        this.type = type;
        this.conditionalRolloutRules = conditionalRolloutRules;
        this.percentRolloutRules = percentRolloutRules;
        this.defaultRolloutTargetingOn = defaultRolloutTargetingOn;
        this.defaultRolloutTargetingOff = defaultRolloutTargetingOff;
        this.targeting = targeting;
        this.archived = archived;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<String, Map<Query, String>> getConditionalRolloutRules() {
        return conditionalRolloutRules;
    }

    public void setConditionalRolloutRules(Map<String, Map<Query, String>> conditionalRolloutRules) {
        this.conditionalRolloutRules = conditionalRolloutRules;
    }

    public Map<String, List<PercentRollout>> getPercentRolloutRules() {
        return percentRolloutRules;
    }

    public void setPercentRolloutRules(Map<String, List<PercentRollout>> percentRolloutRules) {
        this.percentRolloutRules = percentRolloutRules;
    }

    public String getDefaultRolloutTargetingOn() {
        return defaultRolloutTargetingOn;
    }

    public void setDefaultRolloutTargetingOn(String defaultRolloutTargetingOn) {
        this.defaultRolloutTargetingOn = defaultRolloutTargetingOn;
    }

    public String getDefaultRolloutTargetingOff() {
        return defaultRolloutTargetingOff;
    }

    public void setDefaultRolloutTargetingOff(String defaultRolloutTargetingOff) {
        this.defaultRolloutTargetingOff = defaultRolloutTargetingOff;
    }

    public boolean isTargeting() {
        return targeting;
    }

    public void setTargeting(boolean targeting) {
        this.targeting = targeting;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", variations=" + variations +
                ", type=" + type +
                ", conditionalRolloutRules=" + conditionalRolloutRules +
                ", percentRolloutRules=" + percentRolloutRules +
                ", defaultRolloutTargetingOn='" + defaultRolloutTargetingOn + '\'' +
                ", defaultRolloutTargetingOff='" + defaultRolloutTargetingOff + '\'' +
                ", targeting=" + targeting +
                ", archived=" + archived +
                '}';
    }
}