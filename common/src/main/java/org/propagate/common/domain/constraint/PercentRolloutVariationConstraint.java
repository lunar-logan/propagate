package org.propagate.common.domain.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PercentRolloutVariationConstraintValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PercentRolloutVariationConstraint {
    String message() default "Unknown variation used in percentRollout";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
