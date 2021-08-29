package org.springframework.boot.sutdy.config;

import java.lang.annotation.*;

/**
 * @author nicky
 * @date 2021/1/13 下午12:06
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MySpingAopAnnotion {
}
