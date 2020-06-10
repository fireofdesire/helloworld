package com.beanfactory;

import com.pojo.Book;

public class BookInstanceFactory 
{
    //实例工厂
	public Book getBook(String bookName,String bookWriter)
    {
		System.out.println("实例工厂开始工作");
    	Book book = new Book();
    	book.setBookName(bookName);
    	book.setBookWriter(bookWriter);
    	return book;
    }
}
