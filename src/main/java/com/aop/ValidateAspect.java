package com.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
//多个切面类同时切入目标方法的时候，默认切入顺序是按字母顺序排的
//@Order注解:能够手动指定切面执行顺序，参数越小优先级越高
@Order(1)
public class ValidateAspect 
{
	 //可以通过全类名.方法名的形式引用别的切面的可重用切面表达式
	 @Before("com.aop.MyAspect.extract()")
	 public static void before(JoinPoint joinPoint)
	    {
	    	System.out.println("ValidateAspect切面"+"方法:"+joinPoint.getSignature().getName()+"开始执行，"+"参数如下:"+Arrays.asList(joinPoint.getArgs()));
	    }
	 @After("com.aop.MyAspect.extract()")
	    public static void after(JoinPoint joinPoint)
	    {
	    	System.out.println("ValidateAspect切面"+"方法:"+joinPoint.getSignature().getName()+"执行完毕");
	    }
	 @AfterReturning(value = "com.aop.MyAspect.extract()",returning = "result")
	    public static void re(JoinPoint joinPoint,Object result)
	    {
	    	System.out.println("ValidateAspect切面"+"方法:"+joinPoint.getSignature().getName()+"执行完毕并返回结果:"+result);
	    }
	 @AfterThrowing(value = "com.aop.MyAspect.extract()",throwing = "exception")
	    public static void exeception(JoinPoint joinPoint,Exception exception)
	    {
	    	System.out.println("ValidateAspect切面"+"方法:"+joinPoint.getSignature().getName()+"出现异常,异常信息如下:"+exception);
	    }
}
