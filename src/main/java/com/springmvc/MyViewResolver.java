package com.springmvc;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

//自定义视图解析器
public class MyViewResolver implements ViewResolver,Ordered
{
    private Integer order = 0;
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if(viewName.startsWith("sex:"))
		{
			return new MyView();
		}
		else
		{
		return null;
		}
	}
    
	//自定义视图解析器的执行优先级方法，order越小优先级越高
	@Override
	public int getOrder() 
	{
		return order;
	}
	public void setOrder(Integer order)
	{
		this.order = order;
	}

}
