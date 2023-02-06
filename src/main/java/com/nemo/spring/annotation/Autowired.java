package com.nemo.spring.annotation;

import java.lang.annotation.*;

/**
 * @author mingLong.zhao
 * @Target(ElementType.FIELD)——字段、枚举的常量
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
