package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xmlaop.XmlTarget;

public class xmlAopTest 
{
    public static void main(String[] args) {
		ApplicationContext aoc = new ClassPathXmlApplicationContext("xmlaop.xml");
		XmlTarget target = (XmlTarget) aoc.getBean("xmlTarget");
		target.add(1, 2);
	}
}
