package org.springframework.boot.sutdy.extend_life_cycle.command;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author nicky
 * @date 2021/9/1 上午10:11
 */
@Component
public class ApplicationRunnerTest implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("springboot加载完成，回调ApplicationRunner.run....");
	}
}
