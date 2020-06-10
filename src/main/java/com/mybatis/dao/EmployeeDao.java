package com.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mybatis.pojo.Employee;

/*
MyBatis:半自动的持久化层框架(SQL映射框架)，负责与数据库进行交互
       优点:
       1.MyBatis将重要的写sql语句的步骤抽取出来了，可以轻易地完成定制SQL的需求，其他步骤仍然是自动化的
       2.sql语句是写在配置文件中的(更容易维护)
       3.MyBatis是轻量级框架

Hibernate:全自动的数据库交互框架(ORM(Object Relation Mapping)框架)
       优点:
       1.对于简单的数据库操作,Hibernate可以使用@Table(数据库中对应实体类的表名)注解与数据库的表建立映射关系，
                     数据库会自动检索有没有这个，没有就建一个与实体类一致的表，实体类属性变了，数据库中对应的表的字段也会自动更新，不需要写
       sql语句就能完成很多操作
       缺点: 
       1.对于复杂的数据库操作，需要定制SQL来完成，使用Hibernate定制SQL就很困难，必须要十分了解HQL的底层原理，
                     才能完成定制SQL
       2.Hibernate是全字段映射框架，做部分字段映射就很困难
       
用MyBatis操作数据库的步骤:
  1.导包
    (1)mysql-connector-java.jar:mysql数据库驱动
    (2)mybatis.jar:MyBatis依赖包
    (3)log4j.jar:用来打印MyBatis操作数据库的日志，依赖一个类路径下的log4j.properties配置文件，
            且要在MyBatis全局配置文件中settings标签中进行相关配置。
  2.写配置
    (1)写MyBatis的全局配置文件MyBatisConfig.xml:用于指导MyBatis如何正确运行，且内部的标签顺序有严格规定
                  常用标签：
      1)properties标签用于引入外部配置文件
        resource属性:从类路径下引用
        url属性:引用磁盘路径或者网络路径的资源
      2)settings标签:用于开启各种功能
                  开启懒加载：<setting name="lazyLoadingEnabled" value="true"/>
                  开启日志打印：<setting name="logImpl" value="STDOUT_LOGGING"/>	  
      3)typeAliases标签:在标签体里使用不同的标签来取别名
      typeAlias标签：为单个常用的类取别名,设置别名后，在配置文件写全类名可以用类名替换,且类名不区分大小写
      package标签:：为指定包下的类批量取别名，且还可以通过在具体的类上加@Aliase("自定义别名")注解来自定义别名
                  注:建议尽量少使用别名，用了别名就不能用ctrl键快速进入相应的类并做修改
      4)typeHandlers标签:完成MyBatis底层预编译处理或封装结果集时的类型转换操作(java的相关类型<------->数据库的相关类型)
      5)environments标签：配置环境们,default属性指定默认使用哪个环境
      environment标签：配置具体的环境(数据源dataSource和事务管理器transactionMananger)
                  注：实际开发中不会在MyBatis配置文件中配置数据源和事务管理器，因为配置在spring中的数据源和事务管理器功能更强大
      6)databaseIdProvider标签:MyBatis用来提升数据库移植性的标签
          1)在标签内部配置: <property name="MySQL" value="mysql"/>
	                        name:数据库厂商标识，value：为数据库厂商标识所取的别名
	      2)在sql映射配置文件中的增删改查标签上添加属性databaseId(数据库厂商标识)，MyBatis根据厂商标识并结合当前的环境来调用相应语句
	  7)mappers标签：将写好且要使用的sql映射配置文件注册进来
	      配置单个sql映射配置文件：
	   mapper标签:
	     resource属性：从类路径下引用sql映射文件
	     url属性：从磁盘路径下引用sql映射文件
	     class属性：
	             1.直接引用dao层接口的全类名，这种方式要求映射文件名和dao层接口的名字相同且在同级路径上
	             2.可以配合@insert()、@delete()、@update()、@select()注解加在dao层接口的方法上使用，就不需要写sql映射配置文件了
	     批量配置sql映射配置文件：
	   package标签：
	     name属性：dao层所在的包名
	             1.注意批量注册的时候，使用注解直接完成数据库操作的接口是能直接注册进去的，
	                                        而没有使用注解直接完成数据库操作的接口想要注册成功就必须要求sql映射文件的名字和dao层接口的名字一致且在同一级目录下
    (2)sql映射文件*mapper.xml:指导dao层方法如何向数据库发送sql语句，如何执行，如何返回结果集等，相当于对dao层接口的实现描述
             常用标签：
             cache：缓存相关标签
             cacheref：缓存相关标签
             sql：抽取可重用的sql标签
                                       例1：
                <sql id="唯一标识，以供引用">可重用的sql语句</sql>
			    <select id="查询方法名">
			      <include refid="引用可重用的sql语句的标识"></include>			      
			    </select>
             resultMap：结果集映射标签
               mybatis的封装结果集的默认规则：
                 1.按照列名和属性名一一对应的规则进行封装，对不上的就装null值
                 2.没有一一对应时
                  (1)在MyBatis全局配置文件中开启驼峰命名法自动映射功能(aa_bb aaBb)
                  (2)取别名
                  (3)resultMap标签做结果集映射：
                                                       单表查询：
                                                       例1：
                     <resultMap type="指定要做结果集映射的类的全类名" id="唯一标识，以供引用">
					    <id property="属性名"  column="表的主键列名" />
					    <result property="同上"  column="表的普通列名" />
					 </resultMap>
					   注: 1.id标签对主键做映射时MyBatis进行了底层优化，
					   2.result标签则用于对普通列做映射
				       多表查询：
				       例1：级联属性封装对象类型的属性
				      <resultMap type="指定要做结果集映射的类的全类名" id="唯一标识，以供引用">
					    <id property="属性名" column="表的主键列名" />
					    <result property="属性名" column="表的普通列名" />
					    <result property="对象类型的属性.属性名" column="表的普通列名" />
					    <result property="对象类型的属性.属性名" column="表的普通列名" />
				      </resultMap>
				      例2：association(用于多对一和一对一的映射关系)标签封装对象类型的属性
				                   一对一的映射关系：
				      <resultMap type="指定要做结果集映射的类的全类名" id="唯一标识，以供引用">
					    ...略
					    <association property="对象类型的属性的属性名" javaType="对象类型的属性的全类名">
					      <result property="属性名" column="列名" />
					      <result property="属性名" column="列名" />
					    </association>
					  </resultMap>
			                  例3：分步查询
					  <resultMap type="com.mybatis.pojo.Dog" id="唯一标识，以供引用">
					    ...略
					    <association property="dogTag" select="分步查询的后置查询方法的全方法名" column="前置查询查到的数据的某字段作为后置查询的依据字段"></association>
					  </resultMap>
					      注：分布查询可以在MyBatis全局配置文件中开启懒加载(name="lazyLoadingEnabled" value="true")的前提下，
					      开启属性按需加载(name="aggressiveLazyLoading" value="false")，在association或collection标签中的fetchType属性，
					      是用来覆盖MyBatis全局配置的懒加载设置的
				      例4  collection(用于一对多的映射关系)：专用于定义集合属性中的元素的封装
					      一对多的映射关系：外键一定放在多的一方的表中
					  <resultMap type="指定要做结果集映射的类的全类名" id="唯一标识，以供引用">
						...略
						<collection property="集合属性的属性名" ofType="集合属性的内部元素的全类名">
						    <id property="属性名" column="主键列名" />
						    <result property="属性名" column="列名" />
						    <result property="属性名" column="列名" />
						</collection>
					  </resultMap>
					      多对多的映射关系：使用中间表存储映射关系
				       
             add、delete、update、select：增删改查标签
                                       属性细节：
               select标签的useCache属性：配置相关sql操作是否开启二级缓存，默认是开启的。
               add(默认true)、delete(默认true)、update(默认true)、select(默认false)标签的flushCache属性：配置sql操作执行后，
                                                                                                                                                                                                                                                                                         是否清空所有缓存(一级缓存和二级缓存)，
             MyBatis提供了强大的动态sql标签：
             if标签：
                                        例1
                <if test="条件判断语句">
		                         动态sql语句
		        </if>
                                        注：条件判断语句中的部分关键字要进行转义，因为这些关键字是MyBatis的关键字 
	                                   需要转义的关键字                     转义后的关键字
	                <               &lt;
	                >               &gt;
	                &&              and 
	                ||              or   
	         where标签：
	                           例1
	            <where>
	                                                 动态sql语句
	            </where>
	                            注：where标签能够智能地识别标签体里是否有动态sql语句，有就加上where,且能够删除多余的、加在sql语句前面的"and"
             trim标签：
                                       例1
                <trim prefix="为标签体里的sql语句加的前缀" prefixOverrides="要清除的多余前缀" suffix="为标签体里的sql语句加的后缀" suffixOverrides="要清除的多余后缀">
                </trim>
             foreach标签：
                                       例1
             <foreach collection="遍历的集合名" item="遍历得到的元素" index="list的索引或map的key"  open="("  close=")"  separator=",">
              #{name}
             </foreach>
             choose标签：相当于(if(); elseif();else)
                                       例1：
              <choose>
	              <when test="条件判断语句">
	              	动态sql语句
	              </when>
	              <otherwise>
	              	动态sql语句
	              </otherwise>
              </choose>
             set标签：替换update语句中手写的set
             selectkey标签：用在insert标签体里，让不支持自增的数据库实现插入数据主键自增的标签
                                       例： 
               <selectKey resultType="java.lang.Integer" order="BEFORE" keyProperty="id">
		         select max(id)+1 from t_emp
		       </selectKey>
		    	order：不同的数据库的机制不同，MySql用BEFORE，Oracle用After
                keyProperty：指定拿到后的主键赋值给insert语句中的哪个属性
             1)add、delete、update:
                                        属性：
                 parameterType：传入参数类型
                 flushCache：
                 statementType：
                         StateMent、PreparedStateMent、CallableStateMent(使用存储过程时用)
                 useGeneratedKeys：
                                                                           设置是否去拿自增的主键，注意必须去数据库里开启自动递增才会有效。
                 keyProperty：
                                                                           指定自增的字段封装给哪个属性，实质是把拿到自增的主键赋值给实体类的相应的属性
                                                   注：支持自增的数据库才能用useGeneratedKeys、keyProperty实现插入时主键自增
             2)select:
                                         属性：
                 resultType:
                                                                           查询多条记录封装list集合返回时，resultType设置为集合的泛型的全类名
                                                                           查询单条记录封装map集合返回时，resultType设置为map，因为map是MyBatis的关键字，代表Map类型，并且返回的map默认把查到的单行数据的列名作为key，值作为value。
                                                                           查询多条记录封装map集合返回时，resultType设置为集合中的元素的全类名，且必须使用@MapKey("自定义的key，实际就是表中的列名")注解指定返回的map中的key，value则自动存放指定好的类型对象
             MyBatis传入参数的细节：
             1.单参数:
                                           基本类型：
                  #{随便写}
              pojo类型:
                  #{传入的对象的属性名}   
             2.多参数:
                                             多参数情况下，MyBatis会默认的把参数都封装到一个map里,且封装时默认的key是"0","1"......"n"或者param1,param2...paramN,
                                             所以只能使用#{默认的key}取出参数值传入到sql语句中，也可以使用@Param("自定义的key")注解来指定参数封装到map中时的key，
                                             推荐使用@Param("自定义的key")注解来自定义key
             3.map：
               #{key}
             4.#{key,规则}:
                                             例：#{key,jdbcType=Integer}：解决插入null值时，MyBatis无法确定null是什么类型，
                                             就无法实现"java类型----------->数据库类型"的转换，从而报错。
             5.取值方式：
             #{属性名}:参数的传入是预编译的方式，经过预编译处理后才能设置值进去，能防止sql注入。
             ${属性名}:和sql语句直接拼串
                                       注:sql语句只有参数位置支持预编译处理，所以${属性名}的取值方式也是有用武之地的，可以用拼串的方式动态传入表名
             6.ognl表达式：
                                       传入的List类型或Map类型的参数都会有伪属性(size:集合中的元素个数，isEmpty：集合是否为空)
                                       传入的参数额外还有两个属性：
             _parameter:
             1)传入单个参数:代表该参数
             2)传入多个参数:代表多个参数集合在一起的map
             _databaseId:代表当前数据库环境
             标签细节：
      1)mapper标签中的namespace属性的值是dao层接口的全类名
      2)add、delete、update、select等四种sql操作标签的id属性的值是dao层的方法名
      3)select查询标签是有resultType(返回类型)属性和resultMap(结果集映射:自定义结果集的封装规则)属性的，insert增、delete删、update改三种标签都没有resultType(返回类型)属性和resultMap(结果集映射)属性的，
                  但是MyBatis会智能地区分dao层接口中的方法的返回类型，如果为Boolean就返回true或false，如果是int就返回受到影响的数据行数
      4)#{属性名}用来取出传递过来的参数的值
    
    (3)把sql映射文件*mapper.xml给注册到全局配置文件MyBatisConfig.xml当中
  3.具体使用
    (1)直接用
       1)new SqlSessionFactoryBuilder().build(全局配置文件的路径):
                                   根据全局配置文件创建出一个SqlSessionFactory(工厂对象)
		       ①SqlSessionFactory:是SqlSession工厂，负责创建SqlSession对象
		       ②SqlSession:sql会话(代表和数据库的一次会话)
       2)工厂对象.openSession()：获取和数据库进行会话的会话对象
       3)会话对象.getMapper(dao层接口完整类名):使用SqlSession(sql会话对象)操作数据库，获取到dao层接口的实现类的代理对象
       4)dao层实现类的代理对象.查询方法名():使用具体方法实现操作就行了
    (2)整合ssm框架使用
MyBatis缓存机制：
  1.一级缓存：SqlSession级别的缓存(默认开启),局部作用域缓存
    1).同一次SqlSession期间(同一条连接且中途没关闭)查询到的数据，MyBatis就会保存在一个一级缓存中(Map)，
            只要之前查询过的数据，下次这个SqlSession查询相同的数据就直接从缓存中拿
    2).每个SqlSession都有自己的一级缓存(Map)
    3).只要执行一次增删改操作就会自动清空一级缓存
    4).可以使用clearCache()方法手动清空一级缓存
  2.二级缓存：namespace级别缓存(默认关闭)，全局作用域缓存
    (1).二级缓存必须在SqlSession关闭或提交之后才会生效
    (2).二级缓存使用步骤：
	   1)<setting name="cacheEnabled" value="true" />:在全局配置文件中开启全局缓存
	   2)<cache></cache>:在相应的dao层接口的实现*mapper.xml文件中开启二级缓存
	   3)相应的实体类还要实现serializable接口完成序列化。
  3.一级缓存和二级缓存的优先问题
    (1).一级缓存和二级缓存中永远不可能有相同的数据
    (2).任何时候都是先去二级缓存看有没有想要的数据，没有再去一级缓存找，一级缓存也没有，就去数据库查。
  4.整合第三方缓存
      例1：整合ehCache(java进程内的缓存框架)
     1.导包(ehcache-core.jar、mybatis-ehcache.jar、slf4j-api.jar、slf4j-log4j12.jar)
     2.在类路径下创建一个ehcache.xml配置文件
     3.在*.mapper.xml文件中配置使用第三方缓存:
       <cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache> 
      使用第三方缓存的优势：不需要在实体类上实现Serializable接口进行序列化了
*/

public interface EmployeeDao 
{
	
	public Boolean addEmployee(@Param("employee") Employee employee);
	public void deleteEmployee(Integer id);
	public void updateEmployee(@Param("employee") Employee employee);
//	简单的数据库操作可以直接使用注解完成，就不用写sql映射文件了，但必须在MyBatis全局配置文件中的mapper标签下使用class属性配置dao层接口全类名
//	@Select("select * from t_emp where id = #{id}")
	public Employee getEmployeeById(Integer id);
	public List<Employee> getAllEmployeeReturnList();
//	查询单条记录封装map集合返回
	public Map<String, Employee> getEmployeeByIdReturnMap(Integer id);
//	查询多条记录封装map集合返回
	@MapKey("name")
	public Map<Integer, Employee> getAllEmployeeReturnMap();
}
