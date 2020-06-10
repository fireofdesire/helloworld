package com.xmltransaction;

import java.io.FileNotFoundException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class xmlTransactionTest 
{
	public static void main(String[] args) throws FileNotFoundException {
		ApplicationContext aoc = new ClassPathXmlApplicationContext("xmlTransaction.xml");
		StockService service = aoc.getBean(StockService.class);
		//service.checkout("001", "001");
		
		// 有事务的Service组件注册到ioc容器中后，获取实例创建的是Service的代理对象
		//System.out.println(service.getClass());
		//service.checkOutAndChangePrice("001", "001", 5);
	}
	
}
