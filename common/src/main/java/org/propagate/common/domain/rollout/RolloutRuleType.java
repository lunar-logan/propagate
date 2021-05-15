package org.propagate.common.domain.rollout;

import javax.validation.constraints.NotNull;

public enum RolloutRuleType {
    PERCENT,
    CONDITIONAL;

    public static RolloutRuleType fromName(@NotNull String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        for (RolloutRuleType ruleType : values()) {
            if (ruleType.name().equalsIgnoreCase(name)) {
                return ruleType;
            }
        }
        throw new IllegalArgumentException("Unknown enum value \"" + name + "\"");
    }
}
