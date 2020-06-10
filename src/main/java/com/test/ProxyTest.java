package com.test;

import java.util.Arrays;

import com.proxy.Calculator;
import com.proxy.CalculatorProxy;
import com.proxy.MyCalculator;

public class ProxyTest 
{
	//动态代理实现日志记录
	/*jdk原生动态代理的缺点:
	1.代码复杂
	2.被代理类必须要实现至少一个接口，这些接口是代理类和被代理类之间的唯一联系。 
	*/
	  
	public static void main(String[] args) 
	{
		Calculator calculator = new MyCalculator();
	    Calculator proxy = CalculatorProxy.getProxy(calculator);
	    System.out.println(proxy.add(1, 2));
	    //被代理类必须要实现至少一个接口，这些接口是代理类和被代理类之间的唯一联系。 
	    System.out.println(Arrays.asList(proxy.getClass().getInterfaces()));
	}
	
}
