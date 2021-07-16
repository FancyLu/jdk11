package org.springframework.boot.sutdy.extend_life_cycle.springFactories.samePackage;

import org.springframework.boot.sutdy.extend_life_cycle.springFactories.MyEnableAutoConfiguration;
import org.springframework.boot.sutdy.extend_life_cycle.springFactories.SpringFactoriesOtherPackage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author nicky
 * @date 2021/7/8 上午10:06
 */
@MyEnableAutoConfiguration
@ComponentScan("org.springframework.boot.sutdy.extend_life_cycle.springFactories.samePackage")
public class DoTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DoTest.class);
		SpringFactoriesSamePackage item = context.getBean(SpringFactoriesSamePackage.class);
		item.doSomeThing1();
		SpringFactoriesOtherPackage item2 = context.getBean(SpringFactoriesOtherPackage.class);
		item2.doSomeThing1();
	}
}
