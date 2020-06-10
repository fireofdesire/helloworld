<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <a href="locale?locale=en_US">English</a>
  <a href="locale?locale=zh_CN">中文</a>
  <!-- 国际化 -->
  <fmt:message key="userName"/>
  <fmt:message key="passWord"/> 
  <br/>
  <br/>
  <br/>
  <!-- REST风格增删改查 -->
  <form action="book/1" method="post">
    <input type="submit" value="添加" />
  </form>
  <form action="book/1" method="post">
    <input type="hidden" name="_method" value="DELETE" />
    <input type="submit" value="删除" />
  </form>
  <form action="book/1" method="post">
    <input type="hidden"  name="_method" value="PUT" />
    <input type="submit" value="修改" />
  </form>
  <form action="book/1" method="get">
    <input type="submit" value="查询" />
  </form>
  
  <form action="addObject" method="post">
    BookName:<input name="BookName" value="双城记" /><br>
    WriterName:<input name="writer.Name" value="狄更斯" /><br>
    Sex:<input name="writer.Sex" value="男" /><br>
    <input type="submit" value="提交" />
  </form>
  
  <!-- ModelAttribute解决全字段更新的问题 -->
  <form action="getWriter" method="post">
    <input type="text" name="Name" value="李潇" />
    <input type="submit" value="修改" />
  </form>
  
  <!-- Model、Map、ModelMap传值 -->
  <a href="getMap">map传值</a><br/>
  <a href="getModel">model传值</a><br/>
  <a href="getModelMap">modelMap传值</a><br/>
  <!-- ModelAndView传值 -->
  <a href="getModelAndView">ModelAndView传值</a><br/>
  <!-- 使用原生Api传值 -->
  <a href="getSession">原生的session传值</a><br/>
  
  <!-- forward和redirect请求页面 -->
  <a href="forward1">forward1</a>
  <a href="forward2">forward2</a>
  <a href="redirect1">redirect1</a>
  <a href="redirect2">redirect2</a>
  <!-- 相对路径请求页面 -->
  <a href="view">相对路径请求页面</a>
  
  <!-- 请求过后直接到达页面,不经过Controller -->
  <a href="StraightGoView">请求过后直接到达页面,不经过Controller</a>
  
  <!-- 自定义视图解析器和视图的使用 -->
  <a href="sex">自定义视图解析器和视图的使用</a>
  
  <!-- 快速请求:把所有user的属性放在一个字符串中发到后端进行处理 -->
  <form action="quickRequest" method="post">
    <input type="text" name="writerInfo" value="jake,男" />
    <input type="submit" value="快速请求提交" /> 
  </form>
  
  <!-- 使用注解对传入的日期和货币数字进行格式化 -->
  <a href="ConversionStringtoDate?date=2019-11-28&money=10,000.00">日期格式化</a>
  
  <!-- 进入ajax.jsp页面 -->
  <a href="toAjaxView">进入ajax.jsp页面</a><br/>
  
  <!-- 单文件文件上传 -->
       单文件文件上传:
  <form action="oneupload" method="post" enctype="multipart/form-data">
      用户头像：<input type="file" name="headerimg" /><br/>
      用户名：<input type="text" name = "username" />
      <input type="submit" />
  </form>
  
  <!-- 多文件文件上传 -->
       多文件文件上传:
  <form action="manyupload" method="post" enctype="multipart/form-data">
      用户头像1：<input type="file" name="headerimg" /><br/>
      用户头像2：<input type="file" name="headerimg" /><br/>
      用户头像3：<input type="file" name="headerimg" /><br/>
      用户头像4：<input type="file" name="headerimg" /><br/>
      用户名：<input type="text" name = "username" />
      <input type="submit" />
  </form>
</body>
</html>