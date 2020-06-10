package com.springmvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

//自定义视图
public class MyView implements View
{

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return "text/html";
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		//设置响应页面的内容类型
		response.setContentType("text/html");
		String args = "";
		List<String> list = (List<String>) model.get("video");
		for(String s : list)
		{
			args = args+s+"<br/>";
		}
		response.getWriter().write(args);
	}

}
