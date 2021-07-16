package org.springframework.boot.sutdy.extend_life_cycle.springFactories.samePackage;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

/**
 * @author nicky
 * @date 2021/7/8 上午10:36
 */
@Service
public class SpringFactoriesSamePackage {

	public SpringFactoriesSamePackage() {
		System.out.println(">>>>>>>>>>加载了SpringFactoriesSamePackage");
	}

	public void doSomeThing1() {
		System.out.println(ExceptionUtils.getStackTrace(new Throwable()));
	}
}
