package com.nemo.spring.annotation;

import java.lang.annotation.*;

/**
 * @author mingLong.zhao
 * @Target(ElementType.TYPE)——接口、类、枚举、注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
