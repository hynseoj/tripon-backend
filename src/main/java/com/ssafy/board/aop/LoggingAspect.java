package com.ssafy.board.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

//	@Before(value = "execution(* com.ssafy.board.model.mapper.Board*.*(..))")
//	public void loggin(JoinPoint joinPoint) {
//		log.debug("before call method : {} ", joinPoint.getSignature());
//		log.debug("메서드 선언부 : {} 전달 파라미터 : {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
//	}

	@Around(value = "execution(* com.ssafy.board.model.mapper.Board*.*(..))")
	public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("around call method : {} ", joinPoint.getSignature());
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object proceed = joinPoint.proceed();

		stopWatch.stop();

		log.debug("summary : {}", stopWatch.shortSummary());
		log.debug("totalTime : {}", stopWatch.getTotalTimeMillis());
		log.debug("pretty : {}", stopWatch.prettyPrint());

		return proceed;
	}

//	@AfterReturning(value = "execution(* com.ssafy.board.model.mapper.Board*.list*(..))", returning = "obj")
//	public void afterReturningMethod(JoinPoint joinPoint, Object obj) {
//		log.debug("afterReturning call method : {} ", joinPoint.getSignature());
//		log.debug("return value : {}", obj);
//	}
//
//	@AfterThrowing(value = "execution(* com.ssafy.board.model..Board*.list*(..))", throwing = "exception")
//	public void afterThrowingMethod(JoinPoint joinPoint, Exception exception) {
//		log.debug("afterThrowing call method : {}", joinPoint.getSignature());
//		log.debug("exception : {}", exception);
//	}
//
//	@After(value = "execution(* com.ssafy.board.model..Board*.list*(..))")
//	public void afterMethod(JoinPoint joinPoint) {
//		log.debug("after call method : {}", joinPoint.getSignature());
//	}

}