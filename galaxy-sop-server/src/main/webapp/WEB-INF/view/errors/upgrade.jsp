<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>    
<style>
.upgrade{
	position:absolute;
	top:0;
	left:0;
	right:0;
	bottom:0;
}
.center{position: absolute;top:50%;left:50%; transform: translate(-50%, -50%);width:500px;height:auto;}
.p1{
font-family:Microsoft YaHei;
font-size:20px;
color:#333333;
text-align:left;
margin-bottom: 20px;
text-align:center;
}
.p2{
font-family:Microsoft YaHei;
font-size:14px;
color:#666666;
text-align:center;
line-height: 25px;
}
</style>
</head>

<body >

	<div class="upgrade">
		  <div class="center Img">
		  	<img src="<%=path%>/img/upgrade.png" alt="">
		  	<p class="p1">系统升级中...</p>
		  	<p class="p2">为了让您更好的使用星河投，我们正在对系统进行升级，升级期间暂时无法访问，
给您带来的不便，敬请谅解。</p>
		  </div>
	</div>
	

</body>   
</html> 