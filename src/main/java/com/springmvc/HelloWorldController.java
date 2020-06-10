package com.springmvc;


import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.group.Response;
import org.aspectj.weaver.patterns.IScope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@SessionAttributes(value = {"map","modelMap","model"})
@RequestMapping("/hi")
@Controller
public class HelloWorldController 
{
	/*
	springmvc使用步骤：
	    1.导包:spring-web、spring-webmvc
	    2.web.xml文件的配置
	      1.配置前端控制器DispatcherServlet:
	        1.配置springmvc配置文件所在的类路径位置(contextConfigLocation)
	          	注:不配置路径的情况下会在WEB-INF文件夹下去找一个xxx-servlet.xml文件作为配置文件，xxx是web.xml中配置的前端控制器的名字。
	        2.配置前端控制器的拦截级别(/或/*)，/*会拦截jsp请求，/则不会拦截jsp请求。
	    3.springmvc配置文件的配置：
	      1.使用context:componentscan添加扫描注解的基本包
	      2.配置视图解析器(InternalResourceViewResolver)
            1.配置视图解析器前缀时一定要在前后都加上/，如:/WEB-INF/view/，
                                    否则会导致Requestmapping加在类上时出现映射错误。
	*/
	
	/*
	springmvc的运行流程:
	    请求---->前端控制器----->映射处理器------>前段控制器-------->处理器适配器
	 ----->处理器------>处理器适配器------>前段控制器------->视图解析器----->渲染视图------>响应到客户端    
	*/
	
	/*
	springmvc的注解细节:
	  @RequestMapping:可以标在类和方法上：
	                     标注在类上:为整个类的方法加了一层基础映射路径
	                     标注在方法上:就是直接为方法加映射路径
	                     属性细节:
	             method:规定请求方式，默认条件下是什么请求都行
	             params:规定请求参数
	                    1.params={"username"}:规定请求必须带有参数username
	                    2.params={"!username"}:规定请求不能带有参数username
	                    3.params={"username!=1"}:规定请求不能带有值为1的参数username
	             headers:规定请求头
	                    1.headers = {"User-Agent=Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36"}:规定只能由谷歌浏览器来访问
	             consumes:规定请求的内容类型，即是规定请求头中的Content-Type
	             produces:告诉浏览器返回的内容类型是什么，实质就是响应浏览器的时候给响应头中加Content-Type:text/html;charset=utf-8
	             
	             url模糊匹配:实质是url地址可以使用模糊通配符
	             1. ?:替代任意一个字符，这个字符必须存在
	             2. *:替代任意多个字符或一层路径，如果是替代路径，则必须存在一层路径
	             3. **:能替代多层路径
	             4.url优先级总结: 精确url > 带?的url > 带*的url > 带**的url
	                     错误细节:4开头的都是客户端错误
	  @PathVariable:取得路径上的占位符的值。
	             1.url请求路径上可以有占位符
	             2.占位符语法:{变量名}
	             3.占位符只能占一层路径
	             4.可以有多个占位符分别放在多层路径下
	      默认取得请求参数的方式:直接把参数名取得和请求参数一样，这样就能接受到这个请求参数的值
	                                                     请求参数有值就接收到相应的值；请求参数不带值，则接收到null值
	  @RequestParam:获取请求所带的参数，实质就是把请求参数映射到方法的参数上。
	                     属性细节:
	             value:指定要获取的参数的key
	             required:设置请求中是否可以不带请求参数，默认情况下不带参数会报错。
	             defalultValue:给请求参数一个默认值
	  @RequestHeader:获取请求头的信息
	                     属性细节:与@RequestParam的属性细节大致相同
	  @CookieValue:获取请求头中的JessionId
	REST风格:以请求方式区分对资源的操作
	 REST风格的url请求方式:
	             1.url地址的起名方式: /资源名/资源的标识符
	                                           例：/book/1   +   请求方式:GET ---->查询1号图书
	                 /book/1   +   请求方式:POST ---->添加1号图书
	                 /book/1   +   请求方式:PUT ---->更新1号图书
	                 /book/1   +   请求方式:DELETE ---->删除1号图书
	             2.如何发起PUT/DELETE请求
	               1.在web.xml配置文件中添加HiddenHttpMethodFilter这个由springmvc提供的过滤器 ，它能够用来把普通的POST请求转为PUT/DELETE请求
	               2.创建一个请求方式为post的form表单，表单中添加一个name为_method，value为的PUT/DELETE的input标签，value的值就表示转化为哪种请求
	               3.8.0版本以上的tomcat会出现一个问题，jsp不支持DELETE/PUT的请求方式
	                                                   解决方案:在jsp的page标签内添加isErrorPage这个属性，它的作用是设置jsp出现异常时，是否把异常放到jsp九大内置对象之一的exception中去。
    springmvc可以完成对象封装和级联属性对象封装
      1.对象封装:把传入的请求参数，封装成一个相应的对象，前提是传入的请求参数的名字要与对象的属性名一致
      2.级联属性对象封装:对象中有一个对象类型的属性，可以把以"属性名.属性名"的形式的传入的请求参数的值，封装到这个对象类型的属性中。
    springmvc可以直接在参数上使用部分原生api:
      HttpServletRequest
      HttpServletResponse
      HttpSession
      
      local:国际化有关的区域信息对象
      InputStream
      OutputStream
      reader
      writer
	*/
	/*
	springmvc传递数据到页面的几种方式:
	1.使用原生api(request、session)
	2.在方法的参数里传入Model、Map或ModelMap
	   细节：
	    1)使用map、model、modelmap、ModelAndView传值到页面的时候只会存放在请求域中，所以只能用requestScope.属性名取值(requestScope.可以省略)
	    2)三者的关系：Model、Map、ModelMap最后都是通过BindingAwareModelMap对象保存数据,如图所示
	      Map(接口)                                                                         Model(接口)
	      ||                                //
	      ||                               //
	      \/                              // 
	      ModelMap(类)                                                               //
	            \\                      //
	             \\ ExtendedModelMap   //  
	              \\ (继承ModelMap)   //   
	               \\                // 
 	                \\              //
	                 BindingAwareModelMap     
	3.返回ModelAndView类型的对象
	     细节:
	     1)：ModelAndView对象既包含了视图信息，又有数据模型(请求的数据)
	4.使用springmvc的注解@SessionAttributes(value="键",types="值的类型的全类名")传递值到页面，并存在session域中
	     细节:
	     1)：这个注解是针对map、model、modelMap传递值到页面时使用的，它能将符合条件的传递值多复制一份放到Session域中
	        (1)：value="key":当传递值的键是key，就把这个传递值放一份到session域,如果没有key,就会以方法返回值的类型的字符串作为key
	        (2)：type="java.lang.String.class"当传递值的值的类型是String时，就把这个传递值放一份到session域
	     2)：推荐使用原生的session来存数据到session域中，使用注解会有可能会引发未知异常。
	*/
	/*
	@ModelAttribute的作用:解决全字段更新带来问题(更新数据的时候把传递的值封装成对象传入方法,这可能会导致出现一个问题，页面没有传过来的字段值被改成了null)
	  1.写一个被加了@ModelAttribute注解的方法，这个方法用于从数据库中查询到想要修改的数据并封装成对象，
	       在这个方法中添加Model/ModelMap/Map类型参数，通过这个参数把待修改对象作为键值对的值存起来，
	       以供更新数据的方法使用，因为这个方法会提前于更新方法运行，更新方法的对象参数不再是默认使用，
	       而是根据键来拿取前置方法存好的对象(在对象参数之前加上@ModelAttribute(value="键"))。
	  2.springmvc把页面传入的值自动封装成对象的原理:
	    1)new一个全是null值的对象
	    2)用不是null的传入值替换掉对象的相应属性值
	*/
	/*
	请求页面:的方式
	    1)使用相对路径的形式,如"../../view","../../"表示上两级目录下面的内容
	    2)使用"forward:"前缀请求转发到页面
	    3)使用"redirect:"前缀重定向到页面
	            总结:
	       1使用forward或redirect前缀时就不会通过配置的视图解析器拼接字符串地址，
	       2注解@RequestMapping加在类上后，forward的连续请求转发或redirect的连续重定向的返回地址必须要加上注解@RequestMapping设置的全局映射地址，
	                      如"forward:/hi/forward1"或"redirect:/hi/redirect1";
	       3使用"redirect:"前缀重定向到页面时是不能传递参数的，
	                     但是可以通过springmvc九大组件之一的FlashMapManager来携带参数，
	                     每次拿到重定向前的参数后都会删除匹配的参数和过期的参数。
	       4.使用前缀进行请求转发或重定向时，可以看成是从客户端浏览器发起请求。
	视图解析器:
	  1.根据ModelAndView对象里的视图信息得到视图对象。
	  2.使用视图对象渲染视图。(将数据显示到页面上，实质就是把数据放到request域中)
	      注:视图解析器可以配多个，当进行视图解析时，一个视图解析器解析失败了，下一个继续上，
	      直到解析成功或所有视图解析器都解析失败。
	springmvc导入jstl包实现国际化:
	  1.导入jstl包
	  2.写国际化资源文件（properties文件），国际化文件的命名是有规则且极其严格的，错一点儿都显示不出来。
	  3.配置一个id必须为messageSource的国际化资源文件管理器
	  4.页面直接导入fmt标签库
	请求不通过Controller直接到达页面:
	  1.使用springmvc自带的标签mvc:view-controller并进行相关配置
                        例：<mvc:view-controller path="请求路径" view-name="页面的名字" />
      2.开启mvc注解驱动模式，如果不开启就会导致其它要经过Controller的请求失效
                        例：<mvc:annotation-driven></mvc:annotation-driven>
            自定义视图解析器和视图类的步骤:
            1.编写自定义的视图解析器和自定义视图类
              (1)编写自定义视图解析器:
                 1)实现ViewResolver接口，重写返回视图对象的方法
                 2)实现Ordered接口，重写设置视图解析器执行优先级的方法(setOrder(),getOrder())。
              (2)编写自定义视图类:
                 1)实现View接口，重写render()视图渲染方法和getContentType()获取响应内容类型的方法。
                 2)render()渲染方法中还需要设置一下响应(response)的内容类型(ContentType)
            2.把视图解析器加入ioc容器
              (1)写一个bean，把自定义视图解析器注册进去，再设置所有能设置order属性的视图解析器的order的值。
	*/
	/*
	CRUD:Create、Retrieve(查询)、Update、Delete、
	
	写数据库的外部配置文件db.properties时，千万不要用username作为属性名，这是系统的变量名，
	使用它会使配置文件的属性值被系统变量的值覆盖。
	
	jsp可以使用form标签库来将模型数据中的属性和html页面中的表单元素绑定，这样一来，就能更方便的实现在页面的数据回显
	注:使用from标签库时，因为springmvc认为path属性所指定的每一项都要回显到页面上，
	所以要在隐含数据模型对象加上一个key为command的属性

	<mvc:default-servlet-handler/>：设置前端控制器默认放过静态资源请求(css样式、js文件等) ，让tomcat来处理这些请求，
	                               用了这个标签后，动态资源的请求就进不来了，必须加上<mvc:annotation-driven>
	*/
	/*
	springmvc从页面传递参数到后端时数据绑定的流程:
	  1创建一个WebDataBinder对象:
	    1)WebDataBinder对象中的ConversionService组件负责完成数据
	            类型转换和格式化(String---->各种数据类型,String----->各种形式的日期)
	    2)WebDataBinder对象中的Validator组件完成对数据合法性的校验
	    3)BindingResult用于保存入参对象和校验错误对象。
	自定义数据类型转换的步骤:
	  1实现Converter<S,T>接口
	    (1)S:待转换类型
	    (2)T:要变成的目标类型
	  2.注册一个ConversionServiceFactoryBean工厂到容器中(实际就是得到的是bean工厂所创建的ConversionService组件)，
	      在这个bean中配置它的Converter属性的值是自定义的Converter。
	          结构如下:   <bean>
		        <property>
		          <set>
		            <bean></bean>
		          </set>
		        </property>
		      </bean>
	  3.告诉springmvc使用自定义的数据类型转换器
	日期格式化注解@DateTimeFormat的使用步骤:
	  1.给实体类的日期属性加上@DateTimeFormat(pattern="具体格式");
	  2.使用了@DateTimeFormat注解时，自定义数据类型转换器时，
	      注册到容器中的bean工厂应换成FormattingConversionFactoryBean类，
	      否则日期@DateTimeFormat注解就会失效
	  3注意:使用的Date类是util包下的类
	数据校验的步骤:
	  1.导入hibernate-validator依赖包
	  2.在实体类的属性上添加校验相关注解
	  3.到控制器中使用@Valid注解,然后在校验注解的后面传入BindingResult result,result用来封装传入对象和校验错误对象
	  4.根据result校验错误对象的haserrors()方法得到的结果，写判断逻辑，无错误就返回请求的页面，有错误就返回原页面。
	  5.注:使用form标签库的form:errors标签可以在回显的页面上提示错误信息
	  6.注:使用原生form表单时，可以把错误信息放到隐含模型中，以供页面进行错误信息的显示
	  7.注:如何定制错误的提示信息:
	    (1).校验注解中有一个message属性用于自定义错误信息
	    (2).写国际化配置文件
	       1)配置文件中key是有规则的，必须严格遵循(更精确的规则优先于相对模糊的规则)
	                         例:Email.employee.email        校验规则.隐含模型中这个对象的key.对象的属性
	          Email.email                 校验规则.对象的属性
	          Email.java.lang.String      校验规则.属性类型
	          Email                       校验规则
	       2)可以使用{数字}作为占位符动态传入校验注解的参数
	                          例:{0} : 传入校验的对象的属性名
	          {*} : 传入校验注解的参数的具体值
	       3)用了form标签库的情况下，则不需要再使用fmt标签库来进行错误信息回显，
	                           因为form:errors标签就能完成国际化错误信息的匹配及回显
	
	springmvc的ajax使用步骤:
	  1.导包(Jackson三件套)
	  2.使用相关注解(ResponseBody、@JsonIgnore等注解)
	传入HttpEntity类型参数能接收请求体和请求头
	通过ResponseEntity可以返回响应数据，还可以定制响应头
	@ResponseBody的作用:把对象转为json数据返回给浏览器，实质就是把返回数据放到响应体中。
	@RequestBody的作用:接收json数据封装为对象,获取请求的请求体，只有post请求才有请求体，get请求是没有请求体的
	  注: 1.前端定义的json对象，一定要使用JSON.stringify(json对象)方法来转化为json字符串，
	         并且contentType(发送的数据类型)要设置为"application/json".
	   2.前台向后台传json数据时，一定要和实体类中的属性set方法的参数一一对应，
	         也可以不一一对应，使用@JsonProperty("自定义的前台参数名"),但这样会导致其它问题(建议不这么做。)
	   3.一定要遵循Java的约定俗成的规则(如:驼峰命名法)
	springmvc的下载:
	  1.取得要下载的文件的流
		(1)找到待下载文件的路径
		  用ServletContext类型的对象的getRealPath()方法取得项目真实路径，即"webapp"下的路径
		(2)拿文件流
		  使用InputStream流,使用byte数组将文件流读进去
		(3)使用new String(("html笔记.txt").getBytes("UTF-8"),"ISO8859-1")解决文件名乱码
	  2.将拿到的文件流使用ResponseEntity()返回
	springmvc的上传:
	  1.导入fileupload依赖包:commons-fileupload、commons-io
	  2.文件上传表单：enctype="multipart/form-data"
	    (1)单文件上传:写一个type为file的input标签
	    (2)多文件上传:写多个type为file的input标签
	  3.配置id为multipartResolver文件上传解析器
	    (1)配置defaultEncoding(默认编码)为utf-8
	    (2)配置maxUploadSize(最大上传的大小)，值使用spring表达式"#{1024*1024*10}"配置
	  4.在Controller的中写上传方法
	   (1).使用MultipartFile file来接收相应的上传文件
	   (2).使用file.transferTo(磁盘路径+file.getOriginalFilename()))来把文件上传到指定路径;
	      file.getOriginalFilename():得到上传文件的完整名字
	Controller的方法中特殊的返回类型(例:请求信息封装成对象等)是由HttpMessageConverter接口支持实现的
	拦截器:升级版过滤器;允许目标方法执行之前进行拦截操作，或者目标方法运行之后进行一些其它处理,拦截器不能向后传递修改后的request,自然无法实现敏感字符的过滤。
	  JavaWeb:Filter
	  SpringMvc:HandlerInterceptor接口:
	    preHandle():目标方法执行之前调用；返回boolean值;如果是true就放行(chain.doFilter())，否则不放行
	    postHandle():在目标方法运行之后调用
	    afterCompletion():在请求整个完成之后，来到页面之后，chain.doFilter()放行。
	拦截器的使用步骤:
	  1.自定义一个实现了HandlerInterceptor接口的拦截器
	  2.把这个拦截器注册到容器中
	   (1)使用bean直接注册，默认拦截所有请求
	   (2)在<mvc:interceptor>标签的内部使用bean注册，可以指定拦截哪些请求
	      1)先配置拦截哪些请求
	      2)再注册拦截器的bean
	拦截器的执行流程:
	  1单拦截器
	   (1)正常情况：拦截器的preHandle()--->目标方法------->postHandle()方法--------->页面渲染--------->afterCompletion()
	   (2)异常情况：
	      1)只要preHandle()不放行,后面的流程都不执行
	      2)只要放行了，afterCompletion()方法就会执行
	  2多拦截器
	   (1)正常情况:拦截器1的preHandle()--->拦截器2的preHandle()-->目标方法----->拦截器2的postHandle()方法----->拦截器1的postHandle()方法------>页面渲染------->拦截器2的afterCompletion()---->拦截器1的afterCompletion()
	   (2)异常情况:
	     1)多个拦截器执行顺序与容器中的拦截器注册顺序有关
	     2)拦截器的preHandle()方法按照顺序执行
	     3)拦截器的postHandle()方法按照逆序执行
	     4)拦截器的afterCompletion()方法按照逆序执行
	     5)哪一块没有放行，后面都不会放行(除了第5条的特殊情况)
	     6)已放行的拦截器的afterCompletion()方法总会执行
	自定义国际化信息切换按钮:
	  1.实现国际化的四大基本步骤
	  2.添加两个切换区域信息的超链接，如:<a href="locale?locale=en_US">English</a>
	  3.写一个实现了localResolver接口的自定义区域信息解析器
	  4.把自定义区域信息解析器注册到容器中
	      或
	  5.注册一个SpringMVC自带的LocaleChangeInterceptor区域信息拦截器到容器中
	  6注册一个SpringMVC自带的SessionLocaleResolver区域信息解析器到容器中
	异常解析的流程:
	  1.出现异常
	  2.使用三个默认的异常解析器进行解析
	   (1)ExeceptionHandlerExceptionResolver:
	      1)使用@ExeceptionHandler(异常类型的全类名)注解标注在异常处理方法上
	      2)在异常处理方法的参数列表传入Exception类型的变量来接收异常信息
	      3)返回类型设为ModelAndView，以便把异常信息传递到自定义的错误页面
	      4)如果有多个@ExeceptionHandler标注的异常处理方法都能处理某个异常，则优先使用更精确的异常处理方法
	      5)可以将所有的异常处理方法集中写在一个类里，这个类称为全局异常处理器，必须在类上加上@ControllerAdvice注解，
	                      这个异常处理类才能生效
	   (2)ResponseStatusExeceptionResolver:
	      1)使用@ResponseStatus(reason=自定义的异常原因，value=自定义的异常状态码)标注在自定义的异常类上
	      2)在Controller方法中抛出自定义的异常
	   (3)DefaultHandlerExceptionResolver:能够解析SpringMVC自带的且没有被其它异常处理器处理的异常
	   (4)SimpleMappingExceptionResolver:
	      1)注册一个SimpleMappingExeceptionResolver到容器中
	      2)进行相关配置(exceptionMappings:配置相关异常发生时，去到哪个错误显示页面，在内部使用props标签配置；exceptionAttribute:指定页面获取异常信息时用的key值)
	详细的springmvc的运行流程:
	  1.客户端发出请求，前端控制器(DispatcherServlet)收到请求，调用doDispatch()进行处理
	  2.根据处理器映射器(HandlerMapping)中保存的映射信息，找到能处理当前请求的处理器执行链(包含拦截器)
	  3.根据得到的处理器执行链找到它的适配器(HandlerAdapter，实质就是一个反射工具)
	  4.拦截器的preHandle()方法执行
	  5.适配器执行目标方法,并返回一个ModleAndView对象
	  6.拦截器的postHandle()方法执行
	  7.页面渲染流程
	   (1)如果有异常使用异常解析器进行处理;处理完返回一个ModleAndView对象
	   (2)调用render()方法进行页面渲染
	   (3)执行拦截器的afterCompletion()方法
	spring和springmvc的整合思想:
	  1.spring负责业务逻辑组件(事务控制、数据源等)
	  2.springmvc负责网站功能组件(视图解析器、文件上传解析器、ajax组件等)
	spring和springmvc整合的两种方式:
	  1.单容器:
	    1.直接在spring的配置文件中使用import标签引入springmvc.xml,不推荐这种方式，分工不明确
	  2.多容器
	    1.写spring.xml配置文件，这里面扫描业务逻辑组件(使用扫描包时的context:exclude-filter标签实现)
	    2.写springmvc.xml配置文件，这里面扫描网站功能组件(使用扫描包时的context:include-filter标签和use-default-filters="false"实现)
	    3.web.xml文件中使用ContextLoaderListener配置spring配置文件的路径,这样一来项目启动就会创建spring容器
	    4.注意多容器情况下，spring容器默认是父容器，springmvc容器是子容器，子容器能拿父容器的组件,但是父容器不能拿子容器的组件  
	*/
	
	@RequestMapping(value = "/hello/**",method = RequestMethod.GET,params = {"username","userpass"},headers = {"User-Agent=Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36"})
    public String goIndex()
    {
		System.out.println("springmvc第一次hello");
    	return "index";
    }
	
	@RequestMapping("/{name}/{sex}")
	public String mainView(@PathVariable("name") String name,@PathVariable("sex") String sex)
	{
		System.out.println(name+sex);
		return "index";
	}
	
	@RequestMapping("/")
	public String getRestView(@RequestParam(value = "name",required = false) String name,@RequestHeader("User-Agent") String user_agent,@CookieValue(value = "JSESSIONID",required = false) String jessionId)
	{
		System.out.println(name+jessionId+user_agent);
		return "restRequest";
	}
	
	//REST风格增删改查
	@RequestMapping(value = "/book/{bookId}",method = RequestMethod.POST)
	public String add(@PathVariable("bookId") String bookId) 
	{
		System.out.println("添加"+bookId+"号图书");
		return "index";
	}
	@RequestMapping(value = "/book/{bookId}",method = RequestMethod.DELETE)
	public String delete(@PathVariable("bookId") String bookId) 
	{
		System.out.println("删除"+bookId+"号图书");
		return "index";
	}
	@RequestMapping(value = "/book/{bookId}",method = RequestMethod.PUT)
	public String update(@PathVariable("bookId") String bookId) 
	{
		System.out.println("修改"+bookId+"号图书");
		return "index";
	}
	@RequestMapping(value = "/book/{bookId}",method = RequestMethod.GET)
	public String select(@PathVariable("bookId") String bookId) 
	{
		System.out.println("查找"+bookId+"号图书");
		return "index";
	}
	
	@RequestMapping("/addObject")
	public String addObject(Book book)
	{
		System.out.println(book);
		return "index";
	}
	
	@RequestMapping("/getModel")
	public String getModel(Model model)
	{
		model.addAttribute("model", "使用Model传值到页面");
		return "index";
	}
	@RequestMapping("/getModelMap")
	public String getModelMap(ModelMap modelMap)
	{
		modelMap.addAttribute("modelMap", "使用ModelMap传值到页面");
		return "index";
	}
	@RequestMapping("/getMap")
	public String getMap(Map<String, String> map)
	{
		map.put("map", "使用Map传值到页面");
		return "index";
	}
	
	@RequestMapping("/getModelAndView")
	public ModelAndView getModelAndView()
	{
		ModelAndView mv = new ModelAndView("index");
//		mv.setViewName("index");
		mv.addObject("name", "小李 ");
		return mv;
	}
	
	@RequestMapping("getSession")
	public String useJdkApi(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.setAttribute("way", "使用原生的session传值到页面，并保存在session域中");
		return "index";
	}
	
	@RequestMapping("getWriter")
	public String useModelAttribute(@ModelAttribute("writer") Writer writer)
	{
		System.out.println(writer);
		return "index";
	}
	@ModelAttribute
	public void setWriter(String Name,Model model)
	{
		System.out.println(Name);
		Writer writer = new Writer();
		writer.setSex("man");
//		如果在只添加了值，那么就会以值的类型的字符串作为key
		model.addAttribute(writer);
		System.out.println(model);
//		model.addAttribute("writer", writer);
	}
	
	@RequestMapping("/forward1")
	public String forward1()
	{
	  return "forward:/forward.jsp";
	}
	@RequestMapping("/forward2")
	public String forward2()
	{
	  return "forward:/hi/forward1";
	}
	@RequestMapping("/redirect1")
	public String redirect1()
	{
	  return "redirect:/redirect.jsp";
	}
	@RequestMapping("/redirect2")
	public String redirect2()
	{
	  return "redirect:/hi/redirect1";
	}
	//相对路径跳转页面
	@RequestMapping("/view")
	public String view()
	{
	  return "../../view";
	}
	
	//自定义视图解析器和视图的使用
	@RequestMapping("/sex")
	public String useCustomeViewResolver(Model model)
	{
		List<String> list = new ArrayList<String>();
		list.add("苍老师.avi");
		list.add("波老师.avi");
		model.addAttribute("video",list);
		return "sex:/*";
	}
	
	//快速请求
	@RequestMapping(value = "/quickRequest")
	public ModelAndView quickRequest(@RequestParam("writerInfo") Writer writer )
	{
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("quickRequest","快速请求");
		System.out.println(writer);
		return mv;
	}
	
	//传入的日期格式化
	@RequestMapping(value = "/ConversionStringtoDate",method = RequestMethod.GET)
	public ModelAndView ConversionStringtoDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date,@NumberFormat(pattern = "##,###.##") Double money)
	{
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("ConversionStringtoDate","对传入的日期进行格式化处理");
		System.out.println(date+" "+money);
		return mv;
	}
	
	//进入ajax.jsp页面
	@RequestMapping(value = "/toAjaxView",method = RequestMethod.GET)
	public ModelAndView toAjaxView()
	{
		ModelAndView mv = new ModelAndView("ajax");
		return mv;
	}
	
	//ajax配合Jackson三件套完成网页的局部刷新
	@ResponseBody
	@RequestMapping("/ajax1")
	public List<Writer> ajax1(@RequestBody String str,HttpEntity<String> header)
	{
		System.out.println("请求体："+str);
		System.out.println("---------------------------------");
		System.out.println("请求头："+header);
		List<Writer> list = new ArrayList<Writer>();
		list.add(new Writer("托尔斯泰","男"));
		list.add(new Writer("阿加莎.克莉斯缇","女"));
		return list;
	}
	
	//ajax完成json格式数据的发送
	@ResponseBody
	@RequestMapping(value = "/ajax2",method = RequestMethod.POST)
	public Writer ajax2(@RequestBody Writer writer)
	{
		System.out.println("接收到的json数据"+writer);
		return writer;
	}
	
	@RequestMapping("/ResponseEntity")
	//通过ResponseEntity返回数据和定制响应头
	public ResponseEntity<String> setResponseHeaderByResponseEntity()
	{
		String body="<h1>success<h1>";
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Set-Cookie", "username=jake");
		headers.add("Content-Type","text/html;charset=utf-8");
		ResponseEntity<String> r = new ResponseEntity<String>(body, headers, HttpStatus.ACCEPTED);
		return r;
	}
	
	//springmvc下载
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//1.取得要下载的文件的流
		//(1)找到待下载文件的路径
//		ServletContext context = request.getServletContext();
//		String realPath = context.getRealPath("/uploadanddownload/download.txt");
		//(2)拿文件流
		FileInputStream inputStream = new FileInputStream("E:\\upload\\html笔记.txt");
		byte[] tmp = new byte[inputStream.available()];
		inputStream.read(tmp);
		inputStream.close();
		
		//2.将拿到的文件流返回
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Disposition","attachment;filename="+new String(("html笔记.txt").getBytes("UTF-8"),"ISO8859-1"));

		return new ResponseEntity<byte[]>(tmp, httpHeaders,HttpStatus.OK);
	}
	//spring的单文件上传
	@RequestMapping("/oneupload")
	public ModelAndView oneupload(@RequestParam("username") String userName,@RequestParam("headerimg") MultipartFile file)
	{
		ModelAndView mv = new ModelAndView("index");
		//设置文件上传的路径
		try 
		{
			file.transferTo(new File("E:\\upload\\"+file.getOriginalFilename()));
			mv.addObject("result", "上传成功");
		} catch (IllegalStateException | IOException e) 
		{
            mv.addObject("result", e.getMessage());
		}
		return mv;
	}
	//spring的多文件上传
	@RequestMapping("/manyupload")
	public ModelAndView manyupload(@RequestParam("username") String userName,@RequestParam("headerimg") MultipartFile[] files)
	{
		ModelAndView mv = new ModelAndView("index");
		//设置文件上传的路径
		try 
		{
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					file.transferTo(new File("E:\\upload\\"+file.getOriginalFilename()));
				}
			}
			mv.addObject("result", "上传成功");
		} catch (IllegalStateException | IOException e) 
		{
            mv.addObject("result", e.getMessage());
		}
		return mv;
	}
	
	//页面国际化信息显示的切换
	@RequestMapping("/locale")
	public String changeLocale()
	{
//		测试除零异常会被集中写的全局异常处理类的方法所处理
//		int i = 10/0;
		
//		测试@ResponseStatus注解处理自定义异常
//		if(true)
//		{
//		throw new MyException();
//		}
		
//		SimpleMappingExceptionResolver的使用
//		String s = null;
//		System.out.println(s.charAt(0));
		
		return "restRequest";
	}
	
	//ExeceptionHandlerExceptionResolver的使用
	@ExceptionHandler(Exception.class)
	public ModelAndView Exception(Exception exception)
	{
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("errorMessage", exception.getMessage());
		return mv;
	}
}
