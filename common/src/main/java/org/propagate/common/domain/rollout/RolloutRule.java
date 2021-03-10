package org.propagate.common.domain.rollout;

import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder(toBuilder = true)
public abstract class RolloutRule implements Serializable {
    protected String variation;

    public RolloutRule() {
    }

    public RolloutRule(String variation) {
        this.variation = variation;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }
}
