package org.propagate.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureFlag implements Serializable {
    private String id;
    /**
     * Every feature flag is uniquely identified by its key
     */
    @NotBlank(message = "Feature flag key must not be null or blank")
    private String key;

    @NotBlank(message = "Feature flag name must not be null or blank")
    private String name;

    private String description;

    @NotNull(message = "Feature flag type must not be null")
    private FeatureFlagType type;

    @NotEmpty(message = "Variations must not be null or empty")
    private List<@Valid Variation> variations;

    @NotEmpty(message = "Rollout rules must not be null or empty")
    private List<@Valid RolloutRules> rolloutRules;

    @Builder.Default
    private boolean archived = false;
    @Builder.Default
    private boolean targeting = false;
    private Date created;
    private Date lastUpdated;
}
