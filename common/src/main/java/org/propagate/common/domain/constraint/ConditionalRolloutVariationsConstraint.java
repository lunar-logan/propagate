package org.propagate.common.domain.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConditionalRolloutVariationConstraintValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalRolloutVariationsConstraint {
    String message() default "Unknown variation used in conditionalRollout";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
