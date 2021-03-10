package org.propagate.reactive.featureflag.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document
public class FeatureFlagEntity implements Serializable {
//    @Id
//    private IDEntity id;

    @Id
    private String id;

    private String name;

    private String description;

    private String type;

    /**
     * List of all the valid variations
     */
    private List<String> variations;

    @Deprecated
    private List<ConditionalRolloutEntity> conditionalRollout;

    @Deprecated
    private List<PercentRolloutEntity> percentRollout;

    private List<RolloutEntity> rolloutRules;

    private String defaultRollout;

    @Builder.Default
    private boolean archived = Boolean.FALSE;

    @Builder.Default
    private boolean targeting = false;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    @Version
    private Long version;
}
