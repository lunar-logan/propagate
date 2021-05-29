package org.propagate.common.domain.rollout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.propagate.common.domain.Variation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ConditionalDistribution {
    @NotBlank
    private String condition;
    @NotNull
    @Valid
    private Variation variation;
}
