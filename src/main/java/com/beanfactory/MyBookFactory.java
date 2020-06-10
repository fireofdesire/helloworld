package com.beanfactory;

import org.springframework.beans.factory.FactoryBean;

import com.pojo.Book;

//实现了spring自带的FactoryBean接口的类会被spring容器识别为bean工厂,且不论是单例还是多例条件下，容器启动时都不会创建相应的bean实例
public class MyBookFactory implements FactoryBean<Book>
{

	//返回创建的对象
	@Override
	public Book getObject() throws Exception {
		System.out.println("MyBookFactory帮你创建了对象");
		Book book = new Book();
		book.setBookName("三个火枪手");
		book.setBookWriter("大仲马");
		return book;
	}
    
	//返回所创建的对象的类型
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Book.class;
	}
    
	//控制创建的对象是否是单例模式
	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
    
}
