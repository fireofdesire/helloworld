package com.beanfactory;

import com.pojo.Book;

public class BookStaticFactory 
{
	//静态工厂
    public static Book getBook(String bookName,String bookWriter)
    {
    	System.out.println("静态工厂开始工作");
    	Book book = new Book();
    	book.setBookName(bookName);
    	book.setBookWriter(bookWriter);
    	return book;
    }
}
