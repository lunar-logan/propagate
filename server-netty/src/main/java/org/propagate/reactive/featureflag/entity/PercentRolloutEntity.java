package org.propagate.reactive.featureflag.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class PercentRolloutEntity extends RolloutRuleEntity {
    private int percent;
}
