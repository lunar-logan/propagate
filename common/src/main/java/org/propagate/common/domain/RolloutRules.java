package org.propagate.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.propagate.common.domain.rollout.RolloutRule;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RolloutRules implements Serializable {
    private Long id;
    @NotBlank(message = "Namespace must not be null or blank")
    private String namespace;
    @NotNull
    @Valid
    private Variation defaultVariationTargetingOff;
    @NotNull
    @Valid
    private Variation defaultVariationTargetingOn;
    private List<@Valid RolloutRule> rolloutRules;
    private Date created;
    private Date lastUpdated;
}
