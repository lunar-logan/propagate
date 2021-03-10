package org.propagate.common.domain.rollout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;

@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PercentageRollout extends RolloutRule {
    @Min(0)
    private int percent;


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
