package com.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//通过使用注解注册组件到ioc容器时，组件在ioc容器中的id默认是首字母小写的类名
@Component(value = "explainbook")
@Scope(value = "prototype")
public class Book 
{
    private String bookName;
    private String bookWriter;
    
    //bean的生命周期方法：初始化方法
    public void initbook()
    {
    	System.out.println("初始化方法调用");
    }
    
  //bean的生命周期方法：销毁方法
    public void destorybook()
    {
    	System.out.println("销毁方法调用");
    }
    
	public Book() {
		super();
		System.out.println("book创建了");
	}
	public Book(String bookName, String bookWriter) {
		super();
		this.bookName = bookName;
		this.bookWriter = bookWriter;
	}
	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", bookWriter=" + bookWriter + "]";
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookWriter() {
		return bookWriter;
	}
	public void setBookWriter(String bookWriter) {
		this.bookWriter = bookWriter;
	}
}
