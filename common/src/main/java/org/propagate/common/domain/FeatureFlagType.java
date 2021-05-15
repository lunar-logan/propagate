package org.propagate.common.domain;

import javax.validation.constraints.NotNull;

public enum FeatureFlagType {
    BOOLEAN,
    STRING,
    JSON;

    public static FeatureFlagType fromName(@NotNull String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        for (FeatureFlagType ruleType : values()) {
            if (ruleType.name().equalsIgnoreCase(name)) {
                return ruleType;
            }
        }
        throw new IllegalArgumentException("Unknown enum value \"" + name + "\"");
    }
}
