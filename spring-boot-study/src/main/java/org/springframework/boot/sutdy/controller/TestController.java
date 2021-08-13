package org.springframework.boot.sutdy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @author nicky
 * @date 2021/7/30 下午2:37
 */
@RestController
@RequestMapping
public class TestController {
	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public String test(@RequestParam("param1") String param1) {
		return "Hellow World!"+param1;
	}
	@RequestMapping(path = "/test1", method = RequestMethod.GET)
	public String test1() {
		return "Hellow World1!";
	}

	public String test2() {
		return "Hellow World2!";
	}
}
