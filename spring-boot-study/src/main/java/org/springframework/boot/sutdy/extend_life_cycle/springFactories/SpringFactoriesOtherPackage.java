package org.springframework.boot.sutdy.extend_life_cycle.springFactories;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

/**
 * @author nicky
 * @date 2021/7/8 上午10:36
 */
@Service
public class SpringFactoriesOtherPackage {
	public SpringFactoriesOtherPackage() {
		System.out.println(">>>>>>>>>>加载了SpringFactoriesOtherPackage");
	}

	public void doSomeThing1() {
		System.out.println(ExceptionUtils.getStackTrace(new Throwable()));
	}
}
