package org.springframework.boot.sutdy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nicky
 * @date 2021/8/20 下午2:11
 */
@Service
public class TestService {
	@Autowired
	TestController testController;
	public void test(){
		testController.test1();
	}
}
