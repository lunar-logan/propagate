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
public class PercentRolloutEntity implements Serializable {
    private String variation;
    private int percent;
}
