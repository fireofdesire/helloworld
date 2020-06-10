package com.xmlaop;

public class XmlTarget 
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
