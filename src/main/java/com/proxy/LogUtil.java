package com.proxy;

import java.lang.reflect.Method;

//Object...args:接受个数可变的实参
public class LogUtil 
{
    public static void before(Method method,Object...args)
    {
    	System.out.println("方法:"+method.getName()+"开始执行");
    }
    public static void after(Method method,Object...args)
    {
    	System.out.println("方法:"+method.getName()+"执行完毕");
    }
    public static void exeception(Method method,Exception e)
    {
    	System.out.println("方法:"+method.getName()+"出现异常"+"异常信息如下:"+e.getCause());
    }
}
