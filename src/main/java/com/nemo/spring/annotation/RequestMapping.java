package com.nemo.spring.annotation;

import java.lang.annotation.*;

/**
 * @author mingLong.zhao
 * @Target(ElementType.TYPE)——接口、类、枚举、注解
 * @Target(ElementType.METHOD)——方法
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
