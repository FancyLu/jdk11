package org.springframework.boot.sutdy.extend_life_cycle.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author nicky
 * @date 2021/9/1 上午10:11
 */
@Component
public class CommandLineRunnerTest implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		System.out.println("springboot加载完成，回调CommandLineRunner.run....");
	}
}
