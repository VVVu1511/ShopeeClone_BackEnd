package com.example.identityservice.validator;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { DobValidator.class})
public @interface DobConstraints {
	String message() default "Invalid date of birth";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
