<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%pageContext.setAttribute("defaultRequestUrl", request.getContextPath());%>
<script type="text/javascript" src="../js/jquery.js"></script>
</head>
<body>
    ${defaultRequestUrl} <br/>
	<%=new Date()%>
	<a class="a1" href="${defaultRequestUrl}/hi/ajax1" >ajax局部刷新</a>
	<a class="a2" href="${defaultRequestUrl}/hi/ajax2" >ajax发送json格式数据</a>
	<table>
	  <thead>
	    <tr>
	      <td>name</td>
	      <td>sex</td>
	    </tr>
	  </thead>
	  <tbody class="t">
	  </tbody>
	</table>
    <script type="text/javascript">
      $(".a1").click(function(){
    	  $.ajax({
    		  url:"${defaultRequestUrl}/hi/ajax1",
    		  type:"POST",
    		  data:{rbody:"请求体"},
    		  success:function(data){
    			  console.log(data);
    			  $(".t").empty();
    			  $.each(data,function(){
    				  var writerName = this.name;
    				  var writerSex = this.sex;
    				  $(".t").append("<tr>"+"<td>"+writerName+"</td>"+"<br/>"+"<td>"+writerSex+"</td>"+"</tr>"+"</br>");
    			  })
    		  }
    	  });
    	  return false;
      })
      $(".a2").click(function(){
    	  var w = {name:"张飞",sex:"男"};
    	  alert(w);
    	  var writer = JSON.stringify(w);
    	  alert(writer);
    	  $.ajax({
    		  url:"${defaultRequestUrl}/hi/ajax2",
    		  type:"POST",
    		  data:writer,
    		  contentType:"application/json",
    		  success:function(data){
    			  console.log(data);
    		  }
    	  });
    	  return false;
      })
    </script>
</body>
</html>