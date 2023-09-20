package com.winter.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class Card {
	
	//advice
	//@Before("execution(* com.winter.app.aop.Transfer.*())")
	//@After("execution(* com.winter.app.aop.Transfer.*())")
	@Around("execution(* com.winter.app.aop.Transfer.*())")
	public Object cardCheck(ProceedingJoinPoint joinPoint)throws Throwable{
		
		log.info("======================");
		log.info("Card Check 타기");
		
		Object [] args = joinPoint.getArgs();
		for(Object object : args) {
		log.info("Args : {}", object);
		}
		
		Object obj = joinPoint.proceed();
		
		log.info("Card Check 내리기");
		
		log.info("======================");
		return obj;
		
	}

}
