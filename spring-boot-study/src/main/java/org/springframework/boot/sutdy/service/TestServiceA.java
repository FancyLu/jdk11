package org.springframework.boot.sutdy.service;

//import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nicky
 * @date 2021/4/15 下午2:35
 */
@Service
public class TestServiceA {
	@Autowired
	TestServiceB testServiceB;

	public void doSomething(String from){
		testServiceB.doSomething("from b: "+from);
		System.out.println(from);
//		System.out.println(ExceptionUtils.getStackTrace(new Throwable()));
	};
}
