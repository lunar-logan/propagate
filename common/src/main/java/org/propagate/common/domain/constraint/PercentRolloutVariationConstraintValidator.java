package org.propagate.common.domain.constraint;

import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.rollout.PercentageRollout;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class PercentRolloutVariationConstraintValidator implements ConstraintValidator<PercentRolloutVariationConstraint, FeatureFlag> {
    @Override
    public boolean isValid(FeatureFlag value, ConstraintValidatorContext context) {
        List<PercentageRollout> percentageRollouts = value.getPercentRollout();
        if (percentageRollouts != null) {
            List<@NotEmpty String> variations = value.getVariations();
            for (PercentageRollout rollout : percentageRollouts) {
                if (!variations.contains(rollout.getVariation())) return false;
            }
        }
        return true;
    }
}
