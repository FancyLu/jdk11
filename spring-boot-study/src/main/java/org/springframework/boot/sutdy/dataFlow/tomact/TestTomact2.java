package org.springframework.boot.sutdy.dataFlow.tomact;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.web.SpringServletContainerInitializer;

import javax.servlet.ServletException;
import java.io.File;

/**
 * ServletContainerInitializer
 *
 * ContextLoader
 * ServletContextListener
 * ContextLoaderListener
 *
 * BeanFactory
 *
 * {@link SpringServletContainerInitializer} @HandlesTypes(WebApplicationInitializer.class)
 * WebApplicationInitializer
 */
public class TestTomact2 {
    public static void main(String[] args) throws ServletException, LifecycleException {
        //使用Java内置tomcat运行SpringMVC框架
        //原理：tomcat加载到SpringMVC注解启动方式，就会创建SpringMVC容器
        start();
    }

    public static void start() throws LifecycleException, ServletException {
        //创建tomcat服务器
        Tomcat tomcatServer = new Tomcat();

        //设置port
        //构建Connector对象,此对象负责与客户端的连接.
        Connector con = new Connector("HTTP/1.1");
        //设置服务端的监听端口
        con.setPort(8083);
        //将Connector注册到service中
        tomcatServer.getService().addConnector(con);

        //设置port
        //tomcatServer.setPort(8086);

        //读取项目路径,'/'可以加载静态资源
        StandardContext ctx = (StandardContext) tomcatServer.addWebapp("/", new File("/home/nicky/work_space/tjs-workspace/spring-boot/spring-boot-study/src/main").getAbsolutePath());
        //禁止重新载入
        ctx.setReloadable(false);
        //class文件读取地址
        File addtionWebInfoClasses = new File("target/classes");

        //创建WebRoot
        WebResourceRoot resources = new StandardRoot(ctx);
        //tomcat内部读取class文件进行执行
        //内部虚拟Tomcat空间
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", addtionWebInfoClasses.getAbsolutePath(), "/"));
        tomcatServer.start();
        System.out.println("Java语言创建Tomcat启动成功");
        //异步进行接收请求
        tomcatServer.getServer().await();

    }
}
