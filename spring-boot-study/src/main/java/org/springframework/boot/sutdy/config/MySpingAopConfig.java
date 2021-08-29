package org.springframework.boot.sutdy.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.weaver.patterns.KindedPointcut;
import org.springframework.stereotype.Component;

/**
 * @author nicky
 * @date 2021/1/13 下午12:06
 */
@Aspect
@Component
public class MySpingAopConfig {

    /**
     * {@link KindedPointcut#matchInternal(org.aspectj.weaver.Shadow)}//匹配逻辑
     */
    @Pointcut(value = "@annotation(org.springframework.boot.sutdy.config.MySpingAopAnnotion)")
//	@Pointcut("execution(* org.springframework.study.dataflow.study_aop.service.*.*(..))")
    public void point(){}

    @Around("point()")
    public void around(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("tjsdemo.tjsmystudy.spring.aop.springaop.MySpingAopConfig.around before");
        try {
			pj.proceed();
		}finally {
			System.out.println("tjsdemo.tjsmystudy.spring.aop.springaop.MySpingAopConfig.around after");
		}
    }
}
