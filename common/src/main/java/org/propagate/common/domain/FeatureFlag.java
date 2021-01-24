package org.propagate.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.propagate.common.domain.constraint.PercentRolloutConstraint;
import org.propagate.common.domain.constraint.ConditionalRolloutVariationsConstraint;
import org.propagate.common.domain.constraint.PercentRolloutVariationConstraint;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Valid
@ConditionalRolloutVariationsConstraint
@PercentRolloutVariationConstraint
@PercentRolloutConstraint
public class FeatureFlag implements Serializable {
    @NotNull
    @Valid
    private ID id;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private FeatureFlagType type;

    @NotEmpty
    @Valid
    private List<@NotEmpty String> variations;

    @Valid
    private List<ConditionalRollout> conditionalRollout;

    @Valid
    private List<PercentageRollout> percentRollout;

    @NotEmpty
    private String defaultRollout;

    @Builder.Default
    private boolean archived = false;

    @Builder.Default
    private boolean targeting = false;
}
