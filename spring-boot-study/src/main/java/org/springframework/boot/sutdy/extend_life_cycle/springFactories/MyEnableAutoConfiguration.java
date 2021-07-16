package org.springframework.boot.sutdy.extend_life_cycle.springFactories;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author nicky
 * @date 2021/7/8 上午10:07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MyEnableAutoConfigurationImportSelector.class)
public @interface MyEnableAutoConfiguration {

}
