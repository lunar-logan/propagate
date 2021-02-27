package org.propagate.reactive.featureflag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionalRolloutEntity implements Serializable {
    private String variation;

    private String expression;
}
