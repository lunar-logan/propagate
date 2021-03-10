package org.propagate.common.domain.constraint;

import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.rollout.ConditionalRollout;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ConditionalRolloutVariationConstraintValidator implements ConstraintValidator<ConditionalRolloutVariationsConstraint, FeatureFlag> {

    @Override
    public boolean isValid(FeatureFlag value, ConstraintValidatorContext context) {
        List<ConditionalRollout> conditionalRollout = value.getConditionalRollout();
        if (conditionalRollout != null) {
            List<@NotEmpty String> variations = value.getVariations();
            for (ConditionalRollout rollout : conditionalRollout) {
                if (!variations.contains(rollout.getVariation())) return false;
            }
        }
        return true;
    }
}
