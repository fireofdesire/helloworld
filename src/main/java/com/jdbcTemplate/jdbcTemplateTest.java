package com.jdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class jdbcTemplateTest 
{
    public static void main(String[] args) throws BeansException, SQLException {
		ApplicationContext aoc = new ClassPathXmlApplicationContext("ioc3.xml");
		JdbcTemplate jdbcTemplate = aoc.getBean(JdbcTemplate.class);
		System.out.println(jdbcTemplate);
		Connection connection = ((DataSource) aoc.getBean("dataSource")).getConnection();
		System.out.println(connection);
		//更新
		/*
		 * String updateSql = "update test set username = ? where username = ?"; 
		 * int result1 = jdbcTemplate.update(updateSql,"李大狗","李二狗");
		 * System.out.println(result1);
		*/
		
		//一条插入
		/*
		 * String insertsql = "insert into test values(?,?)"; 
		 * int result2 = jdbcTemplate.update(insertsql,"男","李二狗"); 
		 * System.out.println(result2);
		*/
		
		//批量插入
		/*
		 * String insertsql2 = "insert into test values(?,?)"; 
		 * //List的长度就是sql语句执行的次数
		 * //object[]就是每次sql语句执行时所用的参数 
		 * List<Object[]> batchArgs = new ArrayList<Object[]>(); 
		 * batchArgs.add(new Object[] {"男","憨憨一号"});
		 * batchArgs.add(new Object[] {"男","憨憨二号"}); 
		 * int[] result3 =jdbcTemplate.batchUpdate(insertsql2, batchArgs); 
		 * for(int i : result3) 
		 * {
		 * System.out.println(i); 
		 * }
		*/
		
		//查询结果封装成一个对象返回
		//注意实体类的属性必须与数据库中的列名一致，否则封装不上，也可以使用别名来完成一一对应，就能封装上了
		String selectSql = "select sex,username userName from test where username = ?";
		//queryForObject:用于查询单行数据，查询结果封装成一个对象返回;或者查询单个数据，直接返回数据
		//new BeanPropertyRowMapper<>(TestPojo.class):告诉jdbcTemplate数据库中的数据如何与java中的实体类的属性对应
		System.out.println((TestPojo) jdbcTemplate.queryForObject(selectSql, new BeanPropertyRowMapper<>(TestPojo.class), "李二狗"));
		
		//查询结果封装成一个List集合返回
		//注意使用的是jdbcTemplate.query()方法，而不是queryForList()方法，二者的返回类型不同
		//模糊查询最好把'%'与占位参数进行拼接来使用,如:"李"+"%"
		String selectSql2 = "select sex,username userName from test where username like ?";
		System.out.println(jdbcTemplate.query(selectSql2,new BeanPropertyRowMapper<>(TestPojo.class),"李"+"%"));
		
		//mysql的函数的使用
		String selectMaxSql = "select max(degree) from score";
		System.out.println(jdbcTemplate.queryForObject(selectMaxSql,Integer.class));
		
		//具名参数:用在数据库查询sql语句中,顾名思义，即是有名字的参数，只不过位置是在sql字符串语句中。语法格式： :参数名
		//比起占位符，具名参数的优势在于，它不用像占位符一样还要顾忌?的位置,直接将具名参数的值都放在一个map中就行了
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = aoc.getBean(NamedParameterJdbcTemplate.class);
		String namedParameterSql = "select * from test where username = :name and sex = :sex";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sex", "男");
		map.put("name", "李二狗");
		System.out.println(namedParameterJdbcTemplate.queryForList(namedParameterSql,map));
		//具名参数在使用时:可以使用BeanPropertySqlParameterSource方法传入对象给具名参数赋值，但是对象中的参数的名称一定得是对应的。
		String parameterSourceSql = "select * from test where username = :userName and sex = :sex";
		TestPojo testPojo = new TestPojo("男","李二狗");
		System.out.println(namedParameterJdbcTemplate.queryForList(parameterSourceSql,new BeanPropertySqlParameterSource(testPojo)));
		
		//使用dao层调用方法进行数据库交互
		TestPojoDao testPojoDao = aoc.getBean(TestPojoDao.class);
		System.out.println(testPojoDao.getInfoByName("冬泳怪哥"));
	}
}
