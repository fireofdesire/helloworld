package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//Calculator calculator:被代理对象
//loader:被代理对象的类加载器
//interfaces:被代理对象实现的接口
//h:方法执行器，帮被代理对象执行目标方法
//Object proxy:代理对象:给jdk使用，任何时候都不要改动这个对象
//Method method:被代理对象要执行的方法
//Object[] args:被代理对象要执行的方法执行时，要传入的参数
public class CalculatorProxy
{
    public static Calculator getProxy(Calculator calculator)
    {
    	ClassLoader loader = calculator.getClass().getClassLoader();
    	Class<?>[] interfaces = calculator.getClass().getInterfaces();
    	InvocationHandler h = new InvocationHandler() 
    	{
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
			{
				System.out.println("代理对象帮你执行目标方法");
				Object result = null;
				try 
				{
					LogUtil.before(method, args);
					//通过反射执行目标方法
					result = method.invoke(calculator, args);
				} 
				catch (Exception e) 
				{
					LogUtil.exeception(method, e);
				}
				LogUtil.after(method, args);
				return result;
			}
		};
    	Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
    	return (Calculator) proxy;
    }
}
