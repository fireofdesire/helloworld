package com.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pojo.Book;
import com.pojo.Dog;
import com.pojo.Person;

public class IocTest 
{
    public static void main(String[] args) throws SQLException {
           ApplicationContext aoc = new ClassPathXmlApplicationContext("ioc.xml");
           //通过bean的id创建bean的实例
           Dog dog = (Dog) aoc.getBean("dog");
           System.out.println(dog);
           //通过bean的类型Type创建bean的实例
           //注意IOC容器中如果有两个以上Type相同但是id不同的bean时，getBean的参数是两个，即id和Type
           Dog dog2 = aoc.getBean("dog2",Dog.class);
           System.out.println(dog2);
           //通过Constructor完成属性注入
           Dog dog3 = (Dog) aoc.getBean("dog3");
           Dog dog4 = (Dog) aoc.getBean("dog4");
           System.out.println(dog3);
           System.out.println(dog4);
           //通过p名称空间完成属性注入
           Dog dog5 = (Dog) aoc.getBean("dog5");
           System.out.println(dog5);
           System.out.println("--------------------------------------------------");
           
           //复杂类型的值的注入
           ApplicationContext aoc2 = new ClassPathXmlApplicationContext("ioc2.xml");
           //null标签注入null值
           Person person = (Person) aoc2.getBean("person");
           System.out.println(person.getName()==null);
           System.out.println("--------------------------------------------------");
           
           //对象类型的值的注入
           //外部bean的引用
           //ref:reference(引用)的方式注入property属性的值
           Person person2 = (Person) aoc2.getBean("person2");
           System.out.println(person2.getBook()==(Book) aoc2.getBean("book"));
           //一个bean实例化后，改变某个依赖于特定的bean的属性的值，则这个特定的bean的值也发生改变
           //但是这种变化只会发生在当前ioc容器创建这个特定的bean时
           person2.getBook().setBookWriter("吴承恩");
           System.out.println((Book) aoc2.getBean("book"));
           //如果重新创建新的ioc容器，则该bean的值不会有任何变化
           ApplicationContext aoc3 = new ClassPathXmlApplicationContext("ioc2.xml");
           System.out.println((Book) aoc3.getBean("book"));
           //内部bean的引用
           System.out.println((Person) aoc2.getBean("person3"));
           System.out.println("--------------------------------------------------");
           
           //List类型的值注入：内部bean注入和外部bean注入(<ref bean="book" />)
           System.out.println((Person)aoc2.getBean("person4"));
           //内部bean是不能被获取的，只能供内部使用，下面的代码就会报错
           //System.out.println((Person)aoc2.getBean("uselessbean"));
           System.out.println("--------------------------------------------------");
           
           //map类型的值的注入
           //使用entry标签完成map类型的值的注入
           System.out.println((Person) aoc2.getBean("person5"));
           System.out.println("--------------------------------------------------");
           
           //properties类型的值的注入
           System.out.println(((Person) aoc2.getBean("person6")).getProperties());
           System.out.println("--------------------------------------------------");
           
           //util名称空间的创建map类型的bean进行引用
           System.out.println(((Person) aoc2.getBean("person7")).getMaps());
           //util名称空间的创建list类型的bean进行引用
           System.out.println(((Person) aoc2.getBean("person7")).getBooks());
           System.out.println("--------------------------------------------------");
           
           //级联属性(bean中属性的属性)的赋值
           //ico容器中的依赖是严格依赖，一旦在引用某个bean时改变了某个属性的值，
           //整个IOC容器范围内，这个被引用的bean都发生了改变
           System.out.println(((Person) aoc2.getBean("person8")));
           System.out.println("--------------------------------------------------");
           
           //parent属性:用于bean配置的继承
           System.out.println(((Person) aoc2.getBean("person9")));
           System.out.println("--------------------------------------------------");
           
           //abstract属性:抽象bean不能创建实例，否则会像下面一样报错，只能用parent属性来继承。
           //System.out.println(((Person) aoc2.getBean("person10")));
           
           //bean组件之间依赖的管理，使用depends-on属性来调整，实质是就是决定bean组件的加载顺序。
           ApplicationContext aoc4 = new ClassPathXmlApplicationContext("ioc3.xml");
           System.out.println("ioc3创建完毕");
           System.out.println("--------------------------------------------------");
           /*
              scope属性:bean的作用域 ,即指定bean是单实例，多实例，request，session，默认都是单实例的
				  singleton:单实例
				    1.在容器启动时就会完成bean实例的创建，并保存在ioc容器中
				    2.无论获取多少次对象，都是获取之前创建好的实例
				  prototype:多实例
				    1.容器启动时不会默认把bean的实例创建好
				    2.在获取对象时，才会对相应bean的实例进行创建，且每一次获取都会创建新的对象。
				  request:同一次request会创建一个bean实例
				  session:同一次session会创建一个bean实例
           */
           //单实例
           aoc4.getBean("dog2");
           aoc4.getBean("dog2");
           //多实例
           aoc4.getBean("dog3");
           aoc4.getBean("dog3");
           System.out.println("--------------------------------------------------");
           
           //静态工厂创建bean实例
           System.out.println(((Book) aoc4.getBean("book2")));
           System.out.println("--------------------------------------------------");
           
           //实例工厂创建bean实例
           System.out.println(((Book) aoc4.getBean("book3")));
           System.out.println("--------------------------------------------------");
           
           //使用实现了spring自带的FactoryBean接口的bean工厂创建bean实例，且可以设置创建的对象是否是单例的。
           System.out.println(((Book) aoc4.getBean("mybookFactory")));
           System.out.println(((Book) aoc4.getBean("mybookFactory")));
           System.out.println("--------------------------------------------------");
           
           //单例bean的生命周期演示：容器启动(调用构造器创建实例对象)======》调用bean的初始化方法======》容器关闭(调用bean的销毁方法)
           ConfigurableApplicationContext cac1 = new ClassPathXmlApplicationContext("ioc3.xml");
           System.out.println("关闭容器");
           cac1.close();
           System.out.println("--------------------------------------------------");
           
           //多实例bean的生命周期：获取bean(调用构造器创建实例对象)======》调用bean的初始化方法======》容器关闭，但是不会销毁相应的实例化对象
           ConfigurableApplicationContext cac2 = new ClassPathXmlApplicationContext("ioc3.xml");
           cac2.getBean("prototypebook");
           System.out.println("关闭容器");
           cac2.close();
           System.out.println("--------------------------------------------------");
           
           //注册数据库连接池
           //按照Type获取ioc容器中的bean组件，可以获取类的所有实现子类
           DataSource ds = aoc4.getBean(DataSource.class);
           System.out.println(ds.getConnection());
           System.out.println("--------------------------------------------------");
           
           //bean的自动装配的三种方式
           //byName:用相应的对象类型的属性的name作为id，自动找到ioc容器中对应id的bean并完成装配，找不到就装配null值
           ApplicationContext aoc5 = new ClassPathXmlApplicationContext("ioc3.xml");
           System.out.println((Person) aoc5.getBean("person2"));
           System.out.println("--------------------------------------------------");
           
           //byType:以属性的Type作为依据去找ioc容器中对应的bean进行装配，找不到相应的bean就装配null值，找到多个符合条件的bean就像下面一样报错
           //System.out.println((Person) aoc5.getBean("person3"));
           System.out.println("--------------------------------------------------");
           
           /*
           Constructor:
	              1.先按照有参构造器的参数类型进行匹配对应的bean，完成装配
	              2如果按照类型找到多个符合条件的bean，则会再把构造器参数的name作为id继续匹配，匹配不成功，就装配null值
	              3不会报错
           */
           System.out.println((Person) aoc5.getBean("person4"));
           System.out.println("--------------------------------------------------");
           
           //上一个ioc容器的配置文件东西过多，新建autowire.xml来验证自动装配的byType方式
           //byType装配方式会把所有符合条件的book装到Book的集合类型(前提是对象中没有单个的book对象属性)
           ApplicationContext atw = new ClassPathXmlApplicationContext("autowire.xml");
           System.out.println(atw.getBean("person"));
           System.out.println("--------------------------------------------------");
           
           //使用spel表达式
           /*
           SPEL(spring expression language)spring表达式语言的用法:#{  }或${  }
			  使用字面量和运算符
			  引用bean或bean的属性值
			 调用非静态方法或静态方法
			    静态方法：#{T(全类名).静态方法名()}
			    非静态方法：对象.方法名
           */
           System.out.println(aoc5.getBean("person5"));
           
           //通过加注解的方式把bean注册到ioc容器中
           /*spring四大注解：
                 @Controller:，针对控制器层，控制器注解
                 @Service:针对业务逻辑层，业务逻辑注解
                 @Repository:针对数据库层(持久化层，dao层)，
                 @Component:可以給任何层的组件加这个注解
             spring使用步骤:
                 1.添加注解
                 2.在spring配置文件配置自动扫描，依赖context名称空间
                 3.导入aop包
           */
           //通过使用注解注册组件到ioc容器时，组件在ioc容器中的id默认是首字母小写的类名,且默认是单实例,但是可以自己调整
           ApplicationContext aoc6 = new  ClassPathXmlApplicationContext("ioc4.xml");
           System.out.println(aoc6.getBean("explainbook")==aoc6.getBean("explainbook"));
           
           //ioc容器配置文件中定义扫描注解的包时，还可以定义扫描包下的类的过滤规则
           /*
           context:include-filter自定义扫描时，在基础包下要扫描的类有哪些。(使用这个标签过滤时，必须把use-default-filters(默认过滤规则)改为false才会生效)
           context:exclude-filter自定义扫描时，在基础包下但不去扫描的类有哪些。
		     type:过滤的规则
			    annotation：通过注解的类型来规定类不会被扫描注册到ioc容器中
			    assignable：通过全类名来规定类不会被扫描注册到ioc容器中
			    aspectj：AOP中的aspectj表达式
			    custom：自定义一个实现了TypeFilter接口的类，重写match方法来过滤不需要注册到容器中的类
			    regex：写正则表达式来过滤不需要注册到容器中的类
			 expression:全类名 
           */
           
           //Autowired注解可以加在构造器、方法、属性和注解上
           //使用Autowired注解会自动去ioc容器中找到匹配的bean进行自动装配，这就是依赖注入，required属性:默认为true，意味着一定要装配上，改为false后，找不到对应的bean时，会装配为null
           /*原理
           1.先按照属性类型去ioc容器中找到对应的组件：
             1)没找到，抛异常
             2)找到一个就直接装配
             3)找到两个以上的满足条件的bean，就按照属性名作为ioc容器中bean的id继续匹配,
               (1)匹配上就装配
               (2)没有匹配上就抛异常 
                                                       注:使用@qualifier指定一个字符串作为ioc容器中的bean的id继续匹配，而不是使用属性本身的名字
                    (1)找到就装配
                    (2)没找到就抛异常       
           */
           
           /*
                                 自动装配注解:Autowired、Resource、Inject
                                                区别：
                    @Autowired:功能更强大，是spring自己的注解
                    @Resource:扩展性更强(换一个容器框架还能用)，是j2ee(Java标准)规定的，但是遗憾的是市面上主流的开源框架只有spring一家，扩展性更强的优势就显得有些鸡肋。
           */
           
           //泛型依赖注入:注入一个组件时，它的泛型也是参考标准。
           //spring可以使用带泛型的父类类型来确定子类的类型
           
           //ioc总结
           /*
           ioc是一个容器，帮助管理所有组件：
               1.依赖注入:@Autowired:自动赋值(从ioc容器中找合适的bean)
               2.想要使用组件，就必须将组件注册到ioc容器当中
               3.容器启动就会创建所有的单实例bean
               4.容器中包含所有的bean
               5spring的ioc容器的底层实现是基于map，这个map会保存所有创建好的bean，并向外界提供获取功能
           */
	}	
}
