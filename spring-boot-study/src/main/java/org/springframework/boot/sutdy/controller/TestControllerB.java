package org.springframework.boot.sutdy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nicky
 * @date 2021/7/30 下午2:37
 */
@RestController
@RequestMapping
public class TestControllerB {
	@Autowired
	TestController testController;
	@RequestMapping(path = "/testB", method = RequestMethod.GET)
	public String test() {
		return "Hellow World!";
	}
	@RequestMapping(path = "/testB1", method = RequestMethod.GET)
	public String test1() {
		return "Hellow World1!";
	}
}
