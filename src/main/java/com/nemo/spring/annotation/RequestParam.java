package com.nemo.spring.annotation;

import java.lang.annotation.*;

/**
 * @author mingLong.zhao
 * @Target(ElementType.PARAMETER)——方法参数
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
}
