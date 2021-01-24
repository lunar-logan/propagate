package org.propagate.common.domain;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ID implements Serializable {
    @NotEmpty
    private String key;

    private String env;
}
