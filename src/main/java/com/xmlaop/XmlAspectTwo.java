package com.xmlaop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;

public class XmlAspectTwo 
{
	 public static void before(JoinPoint joinPoint)
	    {
	    	System.out.println("ValidateAspect切面"+"方法:"+joinPoint.getSignature().getName()+"开始执行，"+"参数如下:"+Arrays.asList(joinPoint.getArgs()));
	    }
	 
	    public static void after(JoinPoint joinPoint)
	    {
	    	System.out.println("ValidateAspect切面"+"方法:"+joinPoint.getSignature().getName()+"执行完毕");
	    }
	    
	    public static void re(JoinPoint joinPoint,Object result)
	    {
	    	System.out.println("ValidateAspect切面"+"方法:"+joinPoint.getSignature().getName()+"执行完毕并返回结果:"+result);
	    }
	    
	    public static void exeception(JoinPoint joinPoint,Exception exception)
	    {
	    	System.out.println("ValidateAspect切面"+"方法:"+joinPoint.getSignature().getName()+"出现异常,异常信息如下:"+exception);
	    }
}
