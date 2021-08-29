package org.springframework.boot.sutdy.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sutdy.controller.TestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author nicky
 * @date 2021/8/20 下午2:11
 */
@Service
public class TestService implements ApplicationContextAware, ServletContextListener {
	private ApplicationContext applicationContext;
	private WebApplicationContext servletContextEvent;

//	@Autowired
//	TestController testController;

	public TestService() {
		System.out.println("TestService init...");
	}

	public void test(){
//		testController.test1();
		applicationContext.getId();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		this.servletContextEvent = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}
}
