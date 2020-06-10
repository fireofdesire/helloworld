package com.springT;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test 
{
    public static void main(String[] args) 
    {
		ApplicationContext aoc = new ClassPathXmlApplicationContext("springT.xml");
		((PersonService) aoc.getBean("personService")).save();;
		((BookService) aoc.getBean("bookService")).save();
		//泛型依赖注入:注入一个组件时，它的泛型也是参考标准。
		//spring可以使用带泛型的父类类型来确定子类的类型
		System.out.println(((BookService) aoc.getBean("bookService")).getClass());
		System.out.println(((BookService) aoc.getBean("bookService")).getClass().getGenericSuperclass());
	}
}
