package org.propagate.reactive.featureflag.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RolloutEntity implements Serializable {
    private String environment;
    private List<RolloutRuleEntity> rules;
}
