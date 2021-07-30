package org.springframework.boot.sutdy.dataFlow.tomact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sutdy.SpringBootStudyApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import javax.servlet.Servlet;

/**
 * @author nicky
 * @date 2021/7/30 下午4:50
 */
@SpringBootApplication
public class StartTomact {
	/**
	 * 1.引入spring-boot-starter-web包，间接引入了以下class
	 * spring-web下的{@link ConfigurableWebApplicationContext 和 tomcat-embed-core下的{@link Servlet}}
	 *
	 * 2.判断是否包含以上两个class,如果有则认为是web应用
	 * {@link SpringApplication#SpringApplication(org.springframework.core.io.ResourceLoader, java.lang.Class[])}
	 * 		{@link WebApplicationType#deduceFromClasspath()}
	 *
	 * 3. 创建上下文
	 * {@link SpringApplication#run(java.lang.String...)}
	 * (context = createApplicationContext();)
	 * 		{@link AnnotationConfigServletWebServerApplicationContext#AnnotationConfigServletWebServerApplicationContext()}
	 *
	 * 4. 启动tomact
	 * {@link SpringApplication#run(java.lang.String...)}
	 * (refreshContext(context);)
	 *		{@link AbstractApplicationContext#refresh()}
	 * 		( this.onRefresh();//加载IOC)
	 * 			 TomcatServletWebServerFactory在spring.factories配置的ServletWebServerFactoryAutoConfiguration.class的EmbeddedTomcat加载
	 *           {@link org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration}
	 * 			 {@link org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration.EmbeddedTomcat#tomcatServletWebServerFactory(org.springframework.beans.factory.ObjectProvider, org.springframework.beans.factory.ObjectProvider, org.springframework.beans.factory.ObjectProvider)}
	 * 		{@link ServletWebServerApplicationContext#onRefresh()}
	 * 		{@link ServletWebServerApplicationContext#createWebServer()}
	 * 		( this.webServer = factory.getWebServer(getSelfInitializer());)
	 * 			{@link TomcatServletWebServerFactory#getWebServer(org.springframework.boot.web.servlet.ServletContextInitializer...)}
	 * 			( Tomcat tomcat = new Tomcat(); fixme 创建tomact)
	 * 			{@link org.springframework.boot.web.embedded.tomcat.TomcatWebServer#initialize()}
	 * 			( Tomcat tomcat = new Tomcat(); fixme 启动tomact)
	 *
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(StartTomact.class, args);
	}
}
