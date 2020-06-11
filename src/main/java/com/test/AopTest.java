package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aop.Target;
import com.aop.TargetInterface;

public class AopTest 
{
    public static void main(String[] args) {
		ApplicationContext aoc = new ClassPathXmlApplicationContext("announceAop.xml");
		//spring会智能的区分目标类是否实现了接口，如果实行了接口，spring-aop底层就使用jdk动态代理
	    (aoc.getBean(TargetInterface.class)).div(1, 1); 
		System.out.println((aoc.getBean(TargetInterface.class)).getClass());
		 
		//spring会智能的区分目标类是否实现了接口，如果没有实现接口，spring-aop底层就使用cglib动态代理
		/*
		 * (aoc.getBean(Target.class)).div(1, 1);
		 * System.out.println((aoc.getBean(Target.class)).getClass());
		 */
		System.out.println("git推送一号");
	}
}
