package com.warders.api.common.argumentresolver.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Parameter(hidden = true)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER})
public @interface InjectUserId {

}
