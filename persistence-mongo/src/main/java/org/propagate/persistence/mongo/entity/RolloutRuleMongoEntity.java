package org.propagate.persistence.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RolloutRuleMongoEntity {
    private String variation;

    private String ruleType;

    private List<PercentDistributionMongoEntity> percentDistribution;

    private List<ConditionalDistributionMongoEntity> conditionalDistribution;

    private Date created;

    private Date lastUpdated;
}
