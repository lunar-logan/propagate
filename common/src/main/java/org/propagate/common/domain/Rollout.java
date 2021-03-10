package org.propagate.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.propagate.common.domain.rollout.RolloutRule;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Rollout implements Serializable {
    @NotEmpty
    private String environment;

    @NotEmpty
    @Valid
    private List<RolloutRule> rules;
}
