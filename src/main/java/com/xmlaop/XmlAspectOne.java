package com.xmlaop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class XmlAspectOne 
{
    public void extract() {}

    public static void before(JoinPoint joinPoint)
    {
    	System.out.println("方法:"+joinPoint.getSignature().getName()+"开始执行，"+"参数如下:"+Arrays.asList(joinPoint.getArgs()));
    }
    
    public static void after(JoinPoint joinPoint)
    {
    	System.out.println("方法:"+joinPoint.getSignature().getName()+"执行完毕");
    }
    
    public static void re(JoinPoint joinPoint,Object result)
    {
    	System.out.println("方法:"+joinPoint.getSignature().getName()+"执行完毕并返回结果:"+result);
    }

    public static void exeception(JoinPoint joinPoint,Exception exception)
    {
    	System.out.println("方法:"+joinPoint.getSignature().getName()+"出现异常,异常信息如下:"+exception);
    }
    
    public Object around(ProceedingJoinPoint pjp) throws Throwable
    {
    	Object result = null;
    	Object[] args = pjp.getArgs();
    	try 
    	{
    		//环绕前置通知
    		System.out.println("环绕前置通知:"+"方法:"+pjp.getSignature().getName()+"开始执行，"+"参数如下:"+Arrays.asList(pjp.getArgs()));
			result = pjp.proceed(args);
			//环绕返回通知
			System.out.println("环绕返回通知:"+"方法:"+pjp.getSignature().getName()+"执行完毕返回结果:"+result);
			
		} 
    	catch (Exception e) 
    	{
			//环绕异常通知
    		System.out.println("环绕异常通知:"+"方法:"+pjp.getSignature().getName()+"出现异常,异常信息如下:"+e);
    		//因为异常已经被捕获，必须把异常再抛出去，否则普通的异常通知方法无法捕获到异常，也就不会执行
    		throw new RuntimeException(e);
		}
    	finally 
    	{
			//环绕后置通知
    		System.out.println("环绕后置通知:"+"方法:"+pjp.getSignature().getName()+"执行完毕");
		}
    	return result;
    }
}
