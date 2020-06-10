package com.springmvc;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;

public class MyLocationReslover implements LocaleResolver
{

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale = request.getLocale();
		String myLocale = request.getParameter("locale");
		if(myLocale!=null&&!myLocale.equals(""))
		{
			locale = new Locale(myLocale.split("_")[0],myLocale.split("_")[1]);
		}
		return locale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub
		
	}

}
