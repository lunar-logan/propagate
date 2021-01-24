package org.propagate.common.domain.constraint;

import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.PercentageRollout;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PercentRolloutConstraintValidator implements ConstraintValidator<PercentRolloutConstraint, FeatureFlag> {
    @Override
    public boolean isValid(FeatureFlag value, ConstraintValidatorContext context) {
        List<PercentageRollout> percentRollout = value.getPercentRollout();
        if (percentRollout != null) {
            int sum = percentRollout.stream().map(PercentageRollout::getPercent).mapToInt(x -> x).sum();
            return sum == 100;
        }
        return true;
    }
}
