package com.mybatis.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.dao.DogDao;
import com.mybatis.dao.EmployeeDao;
import com.mybatis.dao.SlutDao;
import com.mybatis.dao.TeacherDao;
import com.mybatis.pojo.Employee;
import com.mybatis.pojo.Slut;

public class Test 
{
    public static void main(String[] args) throws IOException {
		
    	String resource = "MyBatisConfig.xml";
    	//1.new SqlSessionFactoryBuilder().build(全局配置文件的路径)根据全局配置文件创建出一个SqlSessionFactory
    	//SqlSessionFactory:是SqlSession工厂，负责SqlSession对象
    	//SqlSession:sql会话(代表和数据库的一次会话)
    	SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(resource));
    	//2.openSession()：获取和数据库的一次会话
    	SqlSession session = factory.openSession();
    	//使用SqlSession操作数据库，获取到dao接口的实现
    	EmployeeDao dao = session.getMapper(EmployeeDao.class);
    	SlutDao dao1 = session.getMapper(SlutDao.class);
    	DogDao dao2 = session.getMapper(DogDao.class);
    	TeacherDao dao3 = session.getMapper(TeacherDao.class); 
//    	dao.addEmployee(new Employee(null,"赵七",1,"123456@qq.com"));
//    	dao.deleteEmployee(2);
//    	dao.updateEmployee(new Employee(2,"李二",1,"lier@qq.com"));
    	System.out.println(dao.getEmployeeById(1));
    	session.commit();
//    	System.out.println("--------------------");
//    	SqlSession session2 = factory.openSession();
//    	EmployeeDao dao_2 = session2.getMapper(EmployeeDao.class);
//    	System.out.println(dao_2.getEmployeeById(1));
//    	session2.close();
//    	dao.getAllEmployeeReturnList();
//    	System.out.println(dao.getAllEmployeeReturnMap());
//    	System.out.println(dao.getEmployeeByIdReturnMap(1));
//    	System.out.println(dao1.getAllSlutInfo());
//    	System.out.println(dao2.getDogInfoById("1"));
//    	System.out.println(dao2.getDogInfoByIdSimple("1"));
//    	dao2.getDogInfoByIdSimple("1").getDogName();
//    	System.out.println(dao3.getTeacherById("1"));
//    	dao1.getSlutInfo(new Slut("li",null));
//    	List<String> list = new ArrayList<String>();
//    	list.add("li");
//    	list.add("lin");
//    	dao1.getSlutInfoIn(list);
//    	dao1.getSlutInfoChoose(new Slut(null,"sham"));
//    	dao1.updateSlutInfo(new Slut("li","司马杂种"));
//    	session.commit();
    	session.close();
	}
}
