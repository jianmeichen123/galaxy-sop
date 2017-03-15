<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目详情</title>
</head>
<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>

<body>
<ul class="h_navbar clearfix">
                     <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基本<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
           
                </ul>
</body>


</html>
