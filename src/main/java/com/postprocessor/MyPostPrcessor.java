package com.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

//BeanPostProcessor:spring自带的后置处理器接口
public class MyPostPrcessor implements BeanPostProcessor
{
    //bean初始化方法之前执行的方法
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化方法之前");
		return bean;
	}
    
	//bean初始化方法之后执行的方法
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("初始化方法之后");
		return bean;
	}

}
