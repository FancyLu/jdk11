package org.springframework.boot.sutdy.dataFlow.tomact;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * tomact各个组件
 * https://www.cnblogs.com/haimishasha/p/10740606.html
 * Server -> Service -> Connector -> Engine -> Host -> Context
 * -> Wrapper -> Servlet
 *
 */
public class TestTomcat {
	public static void main(String[] args) throws Exception {
		//构建tomcat对象,此对象为启动tomcat服务的入口对象
		Tomcat t = new Tomcat();
		//构建Connector对象,此对象负责与客户端的连接.
		Connector con = new Connector("HTTP/1.1");
		//设置服务端的监听端口
		con.setPort(8081);
		//将Connector注册到service中
		t.getService().addConnector(con);
		//注册servlet
		Context ctx = t.addContext("/", null);
		Tomcat.addServlet(
				ctx,
				"helloServlet",
				new HttpServlet() {
					@Override
					protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
						resp.getWriter().write("Hello Word!");
					}
				});
		//映射servlet
		ctx.addServletMappingDecoded("/hello", "helloServlet");
		//启动tomcat
		t.start();
		//阻塞当前线程
		System.out.println(Thread.currentThread().getName());
		t.getServer().await();

	}

}