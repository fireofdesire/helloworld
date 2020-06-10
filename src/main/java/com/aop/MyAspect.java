package com.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Aspect+@Component:把这个类注册到ioc容器中,并告诉ioc容器这是一个切面类
@Aspect
@Component
//多个切面类同时切入目标方法的时候，默认切入顺序是按字母顺序排的
//@Order注解:能够手动指定切面执行顺序，参数越小优先级越高
@Order(2)
public class MyAspect 
{
	    @Pointcut("execution(public int com.aop.Target.*(int, int))")
	    public void extract() {}
	    //JoinPoint(Aspect包下的):封装了当前的目标方法的详细信息
	    //前置通知
        //@Before("execution(public int com.aop.Target.*(int, int))")
	    //抽取可重用的切入点表达式
	    @Before("extract()")
	    public static void before(JoinPoint joinPoint)
	    {
	    	System.out.println("方法:"+joinPoint.getSignature().getName()+"开始执行，"+"参数如下:"+Arrays.asList(joinPoint.getArgs()));
	    }
        //后置通知
        //@After("execution(public int com.aop.Target.*(int, int))")
	    //抽取可重用的切入点表达式
	    @After("extract()")
	    public static void after(JoinPoint joinPoint)
	    {
	    	System.out.println("方法:"+joinPoint.getSignature().getName()+"执行完毕");
	    }
        //返回通知
        //returning = "result":告诉spring目标方法的返回值用参数result来接收
        @AfterReturning(value = "execution(public int com.aop.Target.*(int, int))",returning = "result")
	    public static void re(JoinPoint joinPoint,Object result)
	    {
	    	System.out.println("方法:"+joinPoint.getSignature().getName()+"执行完毕并返回结果:"+result);
	    }
        //异常通知
        //throwing = "exception":告诉spring目标方法的异常用参数exception来接收
        @AfterThrowing(value = "execution(public int com.aop.Target.div(int, int))",throwing = "exception")
	    public static void exeception(JoinPoint joinPoint,Exception exception)
	    {
	    	System.out.println("方法:"+joinPoint.getSignature().getName()+"出现异常,异常信息如下:"+exception);
	    }
        
        //环绕通知:本质就是完整的动态代理，是前置通知、后置通知、异常通知、返回通知的合体版，且他能影响目标方法的执行与否。
        //在同一个切面上，环绕通知的执行优先级比普通通知要高，环绕通知和其它切面的普通通知的执行顺序，则仅取决切面之间的执行顺序
        @Around("extract()")
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
