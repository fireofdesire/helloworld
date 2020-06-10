package com.test;

import java.util.Scanner;

public class NumToString {
	public static void main(String[] args)
	{
		System.out.println("请输入一个正整数:");
		Scanner s = new Scanner(System.in);
		int num = s.nextInt();
		System.out.println(convertToString(num));
		System.out.println((char) (1+'A'));
	}
	public static StringBuffer convertToString(int n) 
	{
		StringBuffer sbu = new StringBuffer();
		while (n > 0) 
		{
		   n--;
		   sbu.append((char) (n % 26 + 'A'));      
		   n =n / 26;
		}
		return sbu.reverse();
	}
}
