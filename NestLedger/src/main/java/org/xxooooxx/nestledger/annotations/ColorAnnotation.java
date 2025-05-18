/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.xxooooxx.nestledger.validator.ColorAnnotationValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ColorAnnotationValidator.class})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ColorAnnotation {
    String message() default "顏色錯誤";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
