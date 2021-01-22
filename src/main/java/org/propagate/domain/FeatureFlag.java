package org.propagate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class FeatureFlag implements Serializable {
    @Id
    @NotNull
    @Valid
    private ID id;

    @NotEmpty
    private String name;

    private String description;

    @NotEmpty
    private String type;

    @NotEmpty
    private List<String> variations;

    @Valid
    private List<Condition> conditionalRollout;

    private Map<String, Integer> percentRollout;

    @NotEmpty
    private String defaultRollout;

    @NotNull
    @Builder.Default
    private boolean archived = Boolean.FALSE;
}
