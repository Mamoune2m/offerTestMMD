/* 
 * Class: com.example.offertest.aspect.AopLogging
 * Author: Modou Mamoune DIENE
 * Date: 20/07/2021
 * Project: AF Offer Technical Test
 */

package com.example.offertest.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AopLogging {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declarations
	@Pointcut("execution(* com.example.offertest.controller.*.*(..))")
	private void forControllerPackage() {}

	// add @Before advice to log inputs
	@Before("forControllerPackage()")
	public void beforeControllersExecution(JoinPoint joinPoint) {
		//Format method with input arguments to log
		String methodInputs = joinPoint.getSignature().toShortString();
		
		// get the arguments
		Object[] args = joinPoint.getArgs();
		if(args.length > 0) {
			methodInputs += " called with input:";
			for(Object arg : args) {
				methodInputs += " " + arg.toString();
			}
		}
		logger.info("<== " + methodInputs + " ==>");
	}
	
	//Log execution time with Around
	@Around("forControllerPackage()")
	public Object aroundControllerExecution(ProceedingJoinPoint theProceedingJoinPoint) throws Throwable {
		
		// print out method we are advising on
		String method = theProceedingJoinPoint.getSignature().toShortString();
		
		// get begin timestamp
		long begin = System.currentTimeMillis();
		
		try {
			//Method execution
			Object result = theProceedingJoinPoint.proceed();
			
			// compute duration and display it
			logDuration(begin, "success", method);
			
			return result;
		}
		catch (Exception e) {
			// compute duration and display it
			logDuration(begin, "error", method);
			
			throw e;
		}
	}
	
	//Compute duration
	public void logDuration(long begin, String status, String methodName) {
		// compute duration and display it
		long duration = System.currentTimeMillis() - begin;
		logger.info("<== Method " + methodName + " call finish in " + status + " and take " + duration + " ms duration ==>");
	}
	
	// Display error result with AfterThrowing advice
	@AfterThrowing(pointcut="forControllerPackage()", throwing="exception")
	public void errorControllerExecution(JoinPoint theJoinPoint, Object exception) {
		// display method we are returning from
		String methodName = theJoinPoint.getSignature().toShortString();
				
		// display data returned
		logger.info("<== Method " + methodName + " call fail with exception " + exception + " ==>");
	}

	// Display outputs with AfterReturning advice
	@AfterReturning(pointcut="forControllerPackage()", returning="result")
	public void afterReturning(JoinPoint theJoinPoint, Object result) {
		// display method we are returning from
		String methodName = theJoinPoint.getSignature().toShortString();
				
		// display data returned
		logger.info("<== Method " + methodName + " run successfully with result: " + result + " ==>");
	
	}
}
