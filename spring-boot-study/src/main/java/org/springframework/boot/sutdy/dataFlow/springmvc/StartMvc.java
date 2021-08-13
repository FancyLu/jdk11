package org.springframework.boot.sutdy.dataFlow.springmvc;

import org.apache.catalina.core.ApplicationContextFacade;
import org.apache.catalina.core.ContainerBase;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.util.LifecycleBase;
import org.apache.coyote.http11.Http11Processor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
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
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

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
	 * {@link AbstractApplicationContext#refresh()}
	 * {@link TomcatServletWebServerFactory#getWebServer(org.springframework.boot.web.servlet.ServletContextInitializer...)}
	 *
	 * 2. DispatcherServletRegistrationBean(ServletContextInitializer)配置到当前Context
	 * 		spring.factories加载{@link DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration(org.springframework.web.servlet.DispatcherServlet, org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties, org.springframework.beans.factory.ObjectProvider)}
	 * 	    DispatcherServletRegistrationBean  implements ServletContextInitializer 并且.servlet = DispatcherServlet
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
	 * 		//getServletContext实例化ApplicationContext，返回的却是持有ApplicationContext的ApplicationContextFacade门面模式)
	 * 4.2 将DispatcherServlet加载到tomact {@link DispatcherServlet}
	 * 		DispatcherServlet extend HttpServlet implements Servlet 重写了HttpServlet的doGet/doPost等,会在以下地方注册到Tomact
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
	 * RequestMapping在以下位置处理
	 * @RequestMapping的载体
	 * {@link AbstractHandlerMethodMapping.MappingRegistration}
	 * RequestMappingHandlerMapping的注册
	 * {@link WebMvcAutoConfiguration.EnableWebMvcConfiguration#requestMappingHandlerMapping(org.springframework.web.accept.ContentNegotiationManager, org.springframework.format.support.FormattingConversionService, org.springframework.web.servlet.resource.ResourceUrlProvider)}
	 *
	 * RequestMapping-Method的注册
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
	 * RequestMapping-Method的获取
	 * {@link DispatcherServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * {@link FrameworkServlet#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * {@link DispatcherServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * {@link DispatcherServlet#doDispatch(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * {@link AbstractHandlerMethodMapping#lookupHandlerMethod(java.lang.String, javax.servlet.http.HttpServletRequest)}
	 * {@link AbstractHandlerMethodMapping#addMatchingMappings(java.util.Collection, java.util.List, javax.servlet.http.HttpServletRequest)}
	 * 		( this.mappingRegistry.getRegistrations().get(mapping)));//获取@RequestMapping-Method，key- {@link RequestMappingInfo}
	 *
	 * RequestMapping-Method的调用
	 * {@link DispatcherServlet#doDispatch(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * 		（ ha.handle(processedRequest, response, mappedHandler.getHandler());）
	 * {@link AbstractHandlerMethodAdapter#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)}
	 * {@link RequestMappingHandlerAdapter#handleInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.method.HandlerMethod)}
	 * 		（mav = invokeHandlerMethod(request, response, handlerMethod);）
	 * {@link ServletInvocableHandlerMethod#invokeAndHandle(org.springframework.web.context.request.ServletWebRequest, org.springframework.web.method.support.ModelAndViewContainer, java.lang.Object...)}
	 * 		（invokeForRequest(webRequest, mavContainer, providedArgs);）
	 * {@link InvocableHandlerMethod#invokeForRequest(org.springframework.web.context.request.NativeWebRequest, org.springframework.web.method.support.ModelAndViewContainer, java.lang.Object...)}
	 * {@link InvocableHandlerMethod#doInvoke(java.lang.Object...)}
	 * 		（method.invoke(getBean(), args);//调用@RequestMapping-Method）
	 *
	 * 	RequestMapping-Method的形参
	 *
	 * 把请求值写入request
	 * {@link Http11Processor#service(org.apache.tomcat.util.net.SocketWrapperBase)}
	 * 		（getAdapter().service(request, response);）
	 *
	 *
	 * 把返回值写入response
	 * {@link StringHttpMessageConverter#writeInternal(java.lang.String, org.springframework.http.HttpOutputMessage)}
	 * 		（StreamUtils.copy(str, charset, outputMessage.getBody());）
	 *
	 * org.springframework.boot.web.servlet.ServletContextInitializer
	 *
	 *
	 *
	 * 九大组件在以下位置初始化
	 * {@link DispatcherServlet#initStrategies(org.springframework.context.ApplicationContext)}
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
	 *  {@link org.apache.catalina.core.ApplicationContext} implements {@link ServletContext}
	 * ServletContext等同于【org.apache.catalina.Context】，其在以下方法持有Context的引用
	 * {@link org.apache.catalina.core.ApplicationContext#getContext(java.lang.String)}
	 *  实例化ApplicationContext
	 * {@link StandardContext#startInternal()}
	 *		(entry.getKey().onStartup(entry.getValue(), getServletContext());//getServletContext实例化ApplicationContext)
	 *  新增servlet
	 * {@link ServletContext#addServlet(java.lang.String, java.lang.Class)}
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(StartMvc.class, args);
	}
}
