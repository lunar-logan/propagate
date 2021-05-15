package org.propagate.persistence.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document
public class FeatureFlagMongoEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String key;

    private String name;

    private String description;

    private String type;

    private Set<String> variations;

    private Set<RolloutRulesMongoEntity> rolloutRules;

    private boolean archived;

    private boolean targeting;

    private Date created;

    private Date lastUpdated;
}
