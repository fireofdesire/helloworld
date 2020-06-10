package com.aop;

import org.springframework.stereotype.Component;

//扫描目标类
@Component
public class Target implements TargetInterface
{
    public int add(int x,int y) 
    {
    	System.out.println("x+y="+(x+y));
    	return x+y;
    }
    public int div(int x,int y) 
    {
    	System.out.println("x/y="+(x/y));
    	return x/y;
    }
}
