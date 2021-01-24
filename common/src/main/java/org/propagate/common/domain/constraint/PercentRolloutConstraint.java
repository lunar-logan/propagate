package org.propagate.common.domain.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;

@Documented
@Constraint(validatedBy = PercentRolloutConstraintValidator.class)
@Target( { TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PercentRolloutConstraint {
    String message() default "Sum of all percent values must be 100";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
