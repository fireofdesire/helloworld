<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>主页面</title>
</head>
<body>
   <h1>Helloworld</h1>
   <!-- 使用map、model、modelmap、ModelAndView传值到页面的时候只会存放在请求域中，所以只能用requestScope.属性名取值(requestScope.可以省略) -->
   ${map}<br/>
   ${modelMap}<br/>
   ${model}<br/>
   ${name}<br/>
   <!-- 原生的session传值到页面并存在session域中 -->
   ${sessionScope.way}
   <!-- springmvc的注解传值并存到session域中 -->
   ${sessionScope.map}<br/>
   ${sessionScope.modelMap}<br/>
   ${sessionScope.model}<br/>
   <!-- 快速请求 -->
   ${quickRequest}
   <!-- 对传入的日期进行格式化处理式化 -->
   ${ConversionStringtoDate}
   <!-- 上传结果 -->
   ${result}
</body>
</html>