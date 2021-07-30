package org.springframework.boot.sutdy.dataFlow.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sutdy.dataFlow.tomact.StartTomact;
import org.springframework.context.ApplicationContext;

/**
 * @author nicky
 * @date 2021/7/30 下午6:42
 */
@SpringBootApplication
public class StartMvc {
	/**
	 * org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration(org.springframework.web.servlet.DispatcherServlet, org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties, org.springframework.beans.factory.ObjectProvider)
	 * org.springframework.boot.web.servlet.ServletContextInitializer
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(StartTomact.class, args);
	}
}
