package org.springframework.boot.sutdy.dataFlow.tomact;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * @author nicky
 * @date 2021/8/31 下午6:01
 */
@HandlesTypes(MyServletContainerInitializer.IHandlesTypes.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		System.out.println("MyServletContainerInitializer.onStartup = " + ctx);
		for (Class<?> aClass : c) {
			System.out.println("onStartup.aClass = " + aClass);
		}
	}

	interface IHandlesTypes {

	}

	class HandlesTypesA implements IHandlesTypes {

	}

	class HandlesTypesB implements IHandlesTypes {

	}
}
