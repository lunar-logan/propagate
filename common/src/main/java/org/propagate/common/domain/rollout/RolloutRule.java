package org.propagate.common.domain.rollout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RolloutRule {
    private Long id;
    @NotNull(message = "Rule type must not be null")
    private RolloutRuleType ruleType;
    private List<@Valid ConditionalDistribution> conditionalDistribution;
    private List<@Valid PercentDistribution> percentDistribution;
    private Date created;
    private Date lastUpdated;
}
