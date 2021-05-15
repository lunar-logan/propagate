package org.propagate.common.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RolloutRuleRestEntity implements Serializable {
    @NotBlank
    private String type;

    private List<ConditionalDistributionRestEntity> conditionalDistribution;

    private List<PercentDistributionRestEntity> percentDistribution;

    private Date created;

    private Date lastUpdated;
}
