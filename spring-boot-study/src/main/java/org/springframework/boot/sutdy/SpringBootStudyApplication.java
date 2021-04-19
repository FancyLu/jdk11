package org.springframework.boot.sutdy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sutdy.service.TestServiceA;
import org.springframework.context.ApplicationContext;

/**
 * 必须要多加一个包，因为默认是扫描当前类所在包下的所有bean
 */
@SpringBootApplication
public class SpringBootStudyApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringBootStudyApplication.class, args);

		TestServiceA testServiceA = applicationContext.getBean(TestServiceA.class);
		testServiceA.doSomething("from main");
		System.out.println("springboot build success!...");
	}

}
