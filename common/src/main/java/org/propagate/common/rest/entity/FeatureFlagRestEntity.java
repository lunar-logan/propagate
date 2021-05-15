package org.propagate.common.rest.entity;

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
public class FeatureFlagRestEntity implements Serializable {
    private String id;

    @NotBlank
    private String key;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private String type;

    @NotEmpty
    private List<@NotBlank String> variations;

    @NotEmpty
    private List<@Valid RolloutRulesRestEntity> rolloutRules;

    @Builder.Default
    private boolean archived = false;

    @Builder.Default
    private boolean targeting = false;

    private Date created;

    private Date lastUpdated;
}
