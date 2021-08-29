package org.springframework.boot.sutdy.dataFlow.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sutdy.StudyConfig;
import org.springframework.boot.sutdy.config.MySpingAopConfig;
import org.springframework.boot.sutdy.controller.TestController;
import org.springframework.boot.sutdy.service.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import javax.servlet.ServletContextListener;

/**
 * Created by Nicky.Tang on 2021/8/29 10:30 下午
 *
 * @since 02.12.10
 */
@Import(value = {MySpingAopConfig.class})
@ComponentScan(basePackages = {"org.springframework.boot.sutdy.service", "org.springframework.boot.sutdy.controller"})
@SpringBootApplication()
public class ApplactionRelation {
	public static void main(String[] args) {
		StudyConfig.thisDebugNames = "estController";
		ApplicationContext applicationContext = SpringApplication.run(ApplactionRelation.class, args);
		TestController testController = applicationContext.getBean(TestController.class);
//		TestService testService = applicationContext.getBean(TestService.class);
//		testService.test();
	}


}
