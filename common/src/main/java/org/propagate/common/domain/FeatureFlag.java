package org.propagate.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.propagate.common.domain.rollout.ConditionalRollout;
import org.propagate.common.domain.rollout.PercentageRollout;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Valid
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureFlag implements Serializable {
    /**
     * Every feature flag is uniquely identified by its key
     */
    private String key;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private FeatureFlagType type;

    @NotEmpty
    @Valid
    private List<@NotEmpty String> variations;

    @Valid
    @Deprecated
    private List<ConditionalRollout> conditionalRollout;

    @Valid
    @Deprecated
    private List<PercentageRollout> percentRollout;

    @NotEmpty
    private String defaultRollout;

    @Valid
    private List<Rollout> rolloutRules;

    @NotEmpty
    private String defaultRolloutTargetingOn;

    @NotEmpty
    private String defaultRolloutTargetingOff;

    @Builder.Default
    private boolean archived = false;

    @Builder.Default
    private boolean targeting = false;

    private LocalDateTime created;

    private LocalDateTime lastUpdated;
}
