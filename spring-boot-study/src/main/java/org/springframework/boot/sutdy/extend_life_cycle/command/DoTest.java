package org.springframework.boot.sutdy.extend_life_cycle.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author nicky
 * @date 2021/9/1 上午10:15
 */
@SpringBootApplication
public class DoTest {
	/**
	 *
	 * {@link SpringApplication#run(java.lang.String...)}
	 * 		(callRunners(context, applicationArguments);)
	 * {@link SpringApplication#callRunners(org.springframework.context.ApplicationContext, org.springframework.boot.ApplicationArguments)}
	 */
	public static void main(String[] args) {
		SpringApplication.run(DoTest.class, args);
	}
}
