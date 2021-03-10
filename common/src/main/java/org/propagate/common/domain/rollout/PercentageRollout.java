package org.propagate.common.domain.rollout;

import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;

@SuperBuilder(toBuilder = true)
public class PercentageRollout extends RolloutRule {
    @Min(0)
    private int percent;

    public PercentageRollout(String variation, @Min(0) int percent) {
        super(variation);
        this.percent = percent;
    }

    public PercentageRollout() {
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "PercentageRollout{" +
                "percent=" + percent +
                ", variation='" + variation + '\'' +
                '}';
    }
}
