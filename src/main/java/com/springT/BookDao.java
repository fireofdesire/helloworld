package com.springT;

import org.springframework.stereotype.Repository;

import com.pojo.Book;

@Repository
public class BookDao extends BaseDao<Book>
{

	@Override
	public void save() 
	{
		System.out.println("BookDao--------保存图书");		
	}

}
