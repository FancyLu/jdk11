package org.springframework.boot.sutdy.dataFlow.springmvc;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.util.LifecycleBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.sutdy.controller.TestController;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;

/**
 * @author nicky
 * @date 2021/7/30 下午6:42
 */
@Import(value = {TestController.class})
@SpringBootApplication
public class StartMvc {
	/**
	 *
	 * DispatcherServletRegistrationBean在以下位置加载
	 * （DispatcherServletRegistrationBean  implements ServletContextInitializer 并且.servlet = DispatcherServlet）
	 * {@link DispatcherServletAutoConfiguration}(spring.factories加载DispatcherServletAutoConfiguration)
	 * {@link DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration(org.springframework.web.servlet.DispatcherServlet, org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties, org.springframework.beans.factory.ObjectProvider)}
	 * {@link DispatcherServletRegistrationBean}
	 *
	 * {@link DispatcherServlet}
	 * DispatcherServlet extend HttpServlet implements Servlet
	 * 重写了HttpServlet的doGet/doPost等
	 * 会在以下地方注册到Tomact
	 * {@link ServletRegistrationBean#addRegistration(java.lang.String, javax.servlet.ServletContext)}
	 *
	 *  {@link org.apache.catalina.core.ApplicationContext} implements {@link ServletContext}
	 * ServletContext等同于【org.apache.catalina.Context】，其在以下方法持有Context的引用
	 * {@link org.apache.catalina.core.ApplicationContext#getContext(java.lang.String)}
	 *  实例化ApplicationContext
	 * {@link StandardContext#startInternal()}
	 *		(entry.getKey().onStartup(entry.getValue(), getServletContext());//getServletContext实例化ApplicationContext)
	 *  新增servlet
	 * {@link ServletContext#addServlet(java.lang.String, java.lang.Class)}
	 *
	 *
	 * {@link ServletContextInitializer#onStartup(javax.servlet.ServletContext)}
	 * ServletContextInitializer会在以下地方回调onStartup，并传入当前Context
	 * {@link TomcatServletWebServerFactory#configureContext(org.apache.catalina.Context, org.springframework.boot.web.servlet.ServletContextInitializer[])}
	 * 		（context.addServletContainerInitializer(starter, NO_CLASSES);//累加ServletContextInitializer到tomact）
	 * {@link StandardContext#addServletContainerInitializer(javax.servlet.ServletContainerInitializer, java.util.Set)}
	 * {@link ServletWebServerApplicationContext#selfInitialize(javax.servlet.ServletContext)}
	 * {@link Tomcat#start()}
	 * {@link LifecycleBase#start()}
	 * {@link StandardContext#startInternal()}
	 * 		(entry.getKey().onStartup(entry.getValue(), getServletContext());//回调onStartup)
	 *
	 *
	 * 九大组件在以下位置初始化
	 * {@link DispatcherServlet#initStrategies(org.springframework.context.ApplicationContext)}
	 *
	 * @RequestMapping的载体
	 * {@link AbstractHandlerMethodMapping.MappingRegistration}
	 *
	 * RequestMapping的注册
	 * {@link RequestMappingHandlerMapping#afterPropertiesSet()}
	 * {@link AbstractHandlerMethodMapping#afterPropertiesSet()}
	 * {@link AbstractHandlerMethodMapping#initHandlerMethods()}
	 * {@link AbstractHandlerMethodMapping#processCandidateBean(java.lang.String)}
	 * 		(isHandler(beanType);//判断当前Class是否持有 @Controller/@RequestMapping注解)
	 * {@link AbstractHandlerMethodMapping#detectHandlerMethods(java.lang.Object)}
	 * 		(getMappingForMethod(method, userType);判断方法是否持有@RequestMapping)
	 * {@link AbstractHandlerMethodMapping#registerHandlerMethod(java.lang.Object, java.lang.reflect.Method, java.lang.Object)}
	 * {@link AbstractHandlerMethodMapping.MappingRegistry#register(java.lang.Object, java.lang.Object, java.lang.reflect.Method)}
	 * 		（this.registry.put(mapping, new MappingRegistration()）//存入@RequestMapping-Method，key- {@link RequestMappingInfo}
	 *
	 * RequestMapping的映射
	 * {@link DispatcherServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * {@link FrameworkServlet#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * {@link DispatcherServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * {@link AbstractHandlerMethodMapping#lookupHandlerMethod(java.lang.String, javax.servlet.http.HttpServletRequest)}
	 * {@link AbstractHandlerMethodMapping#addMatchingMappings(java.util.Collection, java.util.List, javax.servlet.http.HttpServletRequest)}
	 * 		( this.mappingRegistry.getRegistrations().get(mapping)));//获取@RequestMapping-Method，key- {@link RequestMappingInfo}
	 *
	 * RequestMapping-Method的调用
	 * {@link InvocableHandlerMethod#doInvoke(java.lang.Object...)}
	 *
	 * org.springframework.boot.web.servlet.ServletContextInitializer
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(StartMvc.class, args);
	}
}
