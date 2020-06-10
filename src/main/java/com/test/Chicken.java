package com.test;

import java.util.Scanner;

public class Chicken {
	public static void main(String[] args) {
		System.out.println("请输入天数:");
		Scanner s = new Scanner(System.in);
		int day = s.nextInt();
		System.out.println("吃鸡数："+sub(day));
	}
	public static int sub(int n)
	{
		if(n<=2)
		{
			return 1;
		}
		return sub(n-1)+sub(n-2);
	}
}
