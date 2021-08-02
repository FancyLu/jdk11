package org.springframework.boot.sutdy.dataFlow.springmvc.dd;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nicky
 * @date 2021/7/30 下午2:37
 */
@RestController
@RequestMapping
public class TestController {
	@RequestMapping(name = "/test", method = RequestMethod.GET)
	public String test() {
		return "Hellow World!";
	}
}
