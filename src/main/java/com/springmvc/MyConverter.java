package com.springmvc;

import org.springframework.core.convert.converter.Converter;

public class MyConverter implements Converter<String, Writer>
{
    //自定义数据类型转换器
	@Override
	public Writer convert(String source) 
	{
		//source:快速请求所传入的参数
		String s[] = source.split(",");
		Writer writer = new Writer(s[0],s[1]);
		return writer;
	}

}
