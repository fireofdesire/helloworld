package com.jdbcTemplate;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestPojoDao 
{
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<TestPojo> getInfoByName(String Name)
	{
		String sql = "select * from test where username = ?";
		List<TestPojo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TestPojo.class),Name);
		return list;
	}
}
