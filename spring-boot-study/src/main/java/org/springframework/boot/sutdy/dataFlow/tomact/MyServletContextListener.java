package org.springframework.boot.sutdy.dataFlow.tomact;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author nicky
 * @date 2021/8/31 下午6:01
 */
public class MyServletContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("MyServletContextListener.contextInitialized = " + sce);
	}
}
