package org.propagate.common.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FeatureFlag implements Serializable {
    private ID id;

    private String name;

    private String description;

    private FeatureFlagType type;

    private List<String> variations;

    private List<Condition> conditionalRollout;

    private Map<String, Integer> percentRollout;

    private String defaultRollout;

    private boolean archived;

    public FeatureFlag() {
    }

    public FeatureFlag(ID id, String name, String description, FeatureFlagType type, List<String> variations, List<Condition> conditionalRollout, Map<String, Integer> percentRollout, String defaultRollout, boolean archived) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.variations = variations;
        this.conditionalRollout = conditionalRollout;
        this.percentRollout = percentRollout;
        this.defaultRollout = defaultRollout;
        this.archived = archived;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
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

    public FeatureFlagType getType() {
        return type;
    }

    public void setType(FeatureFlagType type) {
        this.type = type;
    }

    public List<String> getVariations() {
        return variations;
    }

    public void setVariations(List<String> variations) {
        this.variations = variations;
    }

    public List<Condition> getConditionalRollout() {
        return conditionalRollout;
    }

    public void setConditionalRollout(List<Condition> conditionalRollout) {
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
        return "FeatureFlag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", variations=" + variations +
                ", conditionalRollout=" + conditionalRollout +
                ", percentRollout=" + percentRollout +
                ", defaultRollout='" + defaultRollout + '\'' +
                ", archived=" + archived +
                '}';
    }
}
