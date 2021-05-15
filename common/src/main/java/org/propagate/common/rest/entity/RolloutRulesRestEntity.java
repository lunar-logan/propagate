package org.propagate.common.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RolloutRulesRestEntity {
    @NotBlank
    private String namespace;

    private @NotBlank String defaultVariationTargetingOff;

    private @NotBlank String defaultVariationTargetingOn;

    private List<@Valid RolloutRuleRestEntity> rolloutRules;
}
