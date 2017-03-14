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
<body>
<ul class="h_navbar clearfix">
                  <li data-tab="nav" class="fl h_nav1 active">基本<br/>信息</li>
                  <li data-tab="nav" class="fl h_nav2">项目</li>
                  <li data-tab="nav" class="fl h_nav2">团队</li>
                  <li data-tab="nav" class="fl h_nav1">运营<br/>数据</li>
                  <li data-tab="nav" class="fl h_nav2">竞争</li>
                  <li data-tab="nav" class="fl h_nav1">战略及<br/>策略</li>
                  <li data-tab="nav" class="fl h_nav2">财务</li>
                  <li data-tab="nav" class="fl h_nav2">法务</li>
                  <li data-tab="nav" class="fl h_nav1">融资及<br/>估值</li>
                </ul>
                <script>
                $(function(){
                /* 	 $('.h_navbar').tabLazyChange({
                  		onchangeSuccess:function(index){
                  			switch(index){
                  				case 0: alert("11");initBaseInfo();  break;  //标签0:基本信息
                  				case 1: initProjectInfo(); break;  //标签1:项目
                  				case 2: initTeamInfo(); break;  //标签2: 团队
                  				case 3: initOperateInfo();   break;  //标签3:运营数据
                  				case 4: initCompeteInfo();   break;  //标签4:竞争
                  				case 5: initPlanInfo();   break;  //标签5:战略及策略
                  				case 6: initFinanceInfo();   break;  //标签6:财务
                  				case 7: initJusticeInfo();   break;  //标签7:法务
                  				case 8: initValuationInfo();   break;  //标签8:融资及估值
                  				default: return false;
                  			}
                  			
                  		}
                  }); */
                })

                </script>
               
</body>


</html>
