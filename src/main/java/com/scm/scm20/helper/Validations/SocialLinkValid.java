package com.scm.scm20.helper.Validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SocialLinkValidator.class)
public @interface SocialLinkValid {

    String message() default "Invalid Social Link List";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
