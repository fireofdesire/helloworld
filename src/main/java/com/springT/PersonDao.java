package com.springT;

import org.springframework.stereotype.Repository;

import com.pojo.Person;

@Repository
public class PersonDao extends BaseDao<Person>
{

	@Override
	public void save() 
	{
		System.out.println("PersonDao----保存用户");	
	}

}
