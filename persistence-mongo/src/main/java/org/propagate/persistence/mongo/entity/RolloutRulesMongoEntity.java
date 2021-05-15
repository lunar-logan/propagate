package org.propagate.persistence.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RolloutRulesMongoEntity {
    private String namespace;

    private String defaultVariationTargetingOff;

    private String defaultVariationTargetingOn;

    private Set<RolloutRuleMongoEntity> rolloutRules;

    private Date created;

    private Date lastUpdated;
}
