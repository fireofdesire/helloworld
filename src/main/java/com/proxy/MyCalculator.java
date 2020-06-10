package com.proxy;

public class MyCalculator implements Calculator
{

	@Override
	public int add(int i, int j) 
	{
		return i+j;
	}

	@Override
	public int subtract(int i, int j) 
	{
		return i-j;
	}

}
