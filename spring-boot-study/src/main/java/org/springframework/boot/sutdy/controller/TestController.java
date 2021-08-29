package org.springframework.boot.sutdy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sutdy.config.MySpingAopAnnotion;
import org.springframework.boot.sutdy.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nicky
 * @date 2021/7/30 下午2:37
 */
@Controller
@RequestMapping
@MySpingAopAnnotion
public class TestController {
	@Autowired
	TestService testService;

	public TestController() {
		System.out.println("TestController.init");
	}
	@ResponseBody
	@RequestMapping(path = "/test1", method = RequestMethod.GET)
	public String test1() {
		return "Hellow World1!";
	}

	@ResponseBody
	@MySpingAopAnnotion
	@RequestMapping(path = "/test2", method = RequestMethod.GET)
	public String test2() {
		testService.test();
		return "Hellow World2!";
	}

	public String test3() {
		return "Hellow World3!";
	}
}
