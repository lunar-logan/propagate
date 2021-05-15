package org.propagate.common.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ConditionalDistributionRestEntity implements Serializable {

    @NotBlank
    private String condition;

    @NotBlank
    private String variation;
}
