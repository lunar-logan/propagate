package org.propagate.common.domain.rollout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.propagate.common.domain.Variation;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PercentDistribution {
    @Min(0)
    @Max(100)
    private int percent;
    @NotNull
    @Valid
    private Variation variation;
}
