package com.springmvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason="反正我错了",value = HttpStatus.NOT_ACCEPTABLE)
public class MyException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
