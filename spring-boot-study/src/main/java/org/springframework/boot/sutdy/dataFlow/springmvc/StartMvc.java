package org.springframework.boot.sutdy.dataFlow.springmvc;

import org.apache.catalina.core.ApplicationContextFacade;
import org.apache.catalina.core.ContainerBase;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.util.LifecycleBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.sutdy.controller.TestController;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.DynamicRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;

/**
 * Standard 标准的
 * @author nicky
 * @date 2021/7/30 下午6:42
 */
@Import(value = {TestController.class})
@SpringBootApplication
public class StartMvc {
	/**
	 * 1. tomact启动前加载部分配置
	 * {@link TomcatServletWebServerFactory#getWebServer(org.springframework.boot.web.servlet.ServletContextInitializer...)}
	 *
	 * 2. DispatcherServletRegistrationBean(ServletContextInitializer)配置到当前Context
	 * （DispatcherServletRegistrationBean  implements ServletContextInitializer 并且.servlet = DispatcherServlet）
	 * {@link TomcatServletWebServerFactory#prepareContext(org.apache.catalina.Host, org.springframework.boot.web.servlet.ServletContextInitializer[])}
	 * 		（context.setPath(getContextPath());//设置contextPath，默认为空字符串，即拦截所有请求）
	 * {@link TomcatServletWebServerFactory#configureContext(org.apache.catalina.Context, org.springframework.boot.web.servlet.ServletContextInitializer[])}
	 * 		（context.addServletContainerInitializer(starter, NO_CLASSES);//累加ServletContextInitializer到Context）
	 *
	 * 3 toamact.start
	 * {@link TomcatServletWebServerFactory#getTomcatWebServer(org.apache.catalina.startup.Tomcat)}
	 * {@link TomcatWebServer#initialize()}
	 * {@link LifecycleBase#start()}//会依次调用tomact所有子容器的start方法 {@link ContainerBase.StartChild#call()}
	 *
	 * 4.1 context.start
	 * {@link org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedContext#start()}
	 * 		(startInternal();)
	 * {@link StandardContext#startInternal()}
	 * 		(entry.getKey().onStartup(entry.getValue(), getServletContext());
	 * 		//getServletContext实例化ApplicationContext，返回的却是持有ApplicationContext的ApplicationContextFacade门店模式)
	 * 	4.2 将DispatcherServlet加载到tomact
	 * 		DispatcherServletRegistrationBean(ServletContextInitializer).onStartup
	 * {@link DispatcherServletRegistrationBean#onStartup(javax.servlet.ServletContext)}
	 * {@link DispatcherServletRegistrationBean#addRegistration(java.lang.String, javax.servlet.ServletContext)}
	 * {@link ApplicationContextFacade#addServlet(java.lang.String, javax.servlet.Servlet)}
	 * {@link org.apache.catalina.core.ApplicationContext#addServlet(java.lang.String, java.lang.String, javax.servlet.Servlet, java.util.Map)}
	 *
	 * TomcatEmbeddedContext拦截哪些请求
	 * {@link TomcatServletWebServerFactory#prepareContext(org.apache.catalina.Host, org.springframework.boot.web.servlet.ServletContextInitializer[])}
	 * 		（context.setPath(getContextPath());//设置contextPath，默认为空字符串，即拦截所有请求）
	 *
	 * DispatcherServlet拦截哪些请求
	 * 1.配置path
	 * {@link DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration(org.springframework.web.servlet.DispatcherServlet, org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties, org.springframework.beans.factory.ObjectProvider)}
	 * 		（webMvcProperties.getServlet().getPath()="/" -> 这个路径为DispatcherServlet拦截的路径(路径默认为"/"，可通过spring.mvc.servlet.path配置)）
	 * 2.设置path-DispatcherServlet的映射关系
	 * {@link DispatcherServletRegistrationBean#onStartup(javax.servlet.ServletContext)}
	 * 		(register(description, servletContext);)
	 * {@link DynamicRegistrationBean#register(java.lang.String, javax.servlet.ServletContext)}
	 * {@link DispatcherServletRegistrationBean#configure(javax.servlet.ServletRegistration.Dynamic)}
	 * {@link org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedContext#addServletMappingDecoded(java.lang.String, java.lang.String, boolean)}
	 * 		(servletMappings.put(adjustedPattern, name);)
	 *
	 * 如何分发请求的，如何接入requestMapping
	 * {@link RequestMappingHandlerAdapter}
	 * {@link RequestMappingHandlerAdapter#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)}
	 *
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
	 * ServletContextInitializer会在以下地方回调onStartup，并传入当前Context
	 * {@link TomcatServletWebServerFactory#configureContext(org.apache.catalina.Context, org.springframework.boot.web.servlet.ServletContextInitializer[])}
	 * 		（context.addServletContainerInitializer(starter, NO_CLASSES);//累加ServletContextInitializer到tomact）
	 * {@link StandardContext#addServletContainerInitializer(javax.servlet.ServletContainerInitializer, java.util.Set)}
	 * {@link ServletWebServerApplicationContext#selfInitialize(javax.servlet.ServletContext)}
	 * {@link Tomcat#start()}
	 * {@link LifecycleBase#start()}
	 * {@link StandardContext#startInternal()}
	 * 		(entry.getKey().onStartup(entry.getValue(), getServletContext());//回调onStartup)
	 * {@link ServletContextInitializer#onStartup(javax.servlet.ServletContext)}
	 *
	 *
	 * 九大组件在以下位置初始化
	 * {@link DispatcherServlet#initStrategies(org.springframework.context.ApplicationContext)}
	 *
	 *
	 * RequestMapping在以下位置处理
	 * {@link RequestMappingHandlerMapping#afterPropertiesSet()}
	 *
	 * org.springframework.boot.web.servlet.ServletContextInitializer
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(StartMvc.class, args);
	}
}
