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
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script>

<title>项目详情</title>
<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>
</head>
<body>
      <ul class="h_navbar clearfix">
                     <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('0')" >基本<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
           
                </ul>
</body>

<!-- <script>
$(function(){
	 $('.h_navbar').tabInfoChange({		
		onchangeSuccess:function(index){
			switch(index){
				case 0: initBaseInfo();  break;  //标签0:基本信息
				case 1:initProjectInfo(); break;  //标签1:项目
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
}); 
	 
})
//基本信息
	function initBaseInfo(){
	alert("23");
		$.getTabHtmlInfo({
			url : platformUrl.toBaseInfo
		}); 
	}
	   //项目
		function initProjectInfo(){
			alert("11")
		 $.getTabHtmlInfo({
				url : platformUrl.toProjectInfo 
			}); 
		}
		 //团队
		function initTeamInfo(){
			$.getTabHtmlInfo({
				url : platformUrl.toTeamInfo 
			});
		}
		 //运营数据
		function initOperateInfo(){
			$.getTabHtml({
				url : platformUrl.toOperateInfo 
			});
		}
		//竞争
		function initCompeteInfo(){
			$.getTabHtml({
				url : platformUrl.toCompeteInfo 
			});
		}
		//战略以及策略
		function initPlanInfo(){
			$.getTabHtml({
				url : platformUrl.toPlanInfo 
			});
		}
		//财务
		function initFinanceInfo(){
			$.getTabHtml({
				url : platformUrl.toFinanceInfo 
			});
		}
		//法务
		function initJusticeInfo(){
			$.getTabHtml({
				url : platformUrl.toJusticeInfo 
			});
		}
		//融资及估值
		function initValuationInfo(){
			$.getTabHtml({
				url : platformUrl.toValuationInfo 
			});
		}
 -->

</script>
</html>
