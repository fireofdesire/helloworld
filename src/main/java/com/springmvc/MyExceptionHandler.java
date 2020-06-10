package com.springmvc;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice用来告知spring，这个类是专用处理异常的类
@ControllerAdvice
public class MyExceptionHandler 
{
	@ExceptionHandler(ArithmeticException.class)
	public ModelAndView Exception(NullPointerException exception)
	{
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("errorMessage", exception.getMessage());
		return mv;
	}
}
