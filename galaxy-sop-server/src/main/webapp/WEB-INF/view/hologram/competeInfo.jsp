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
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')">基本<br />信息 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br />数据 </li>
		<li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('4')">竞争</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br />策略 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br />估值 </li>
	</ul>


	<div id="tab-content">
		<div class="tabtxt" id="page_all"> 
		
			<div class="h radius" id="NO5_2"> </div>
			<div class="h radius" id="NO5_3"> </div>
			<div class="h radius" id="NO5_4"> </div>
			<div class="h radius" id="NO5_5"> </div>
			<div class="h radius" id="NO5_6"> </div>
			<div class="h radius" id="NO5_7"> </div>
			<div class="h radius" id="NO5_8"> </div>
			
		</div>
	</div>




				

<script type="text/javascript">



	//页面显示
	/* sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/NO5_1", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			console.log(entity);
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_1").html(s_div);
		}
	}); */
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/NO5_2", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_2").html(s_div);
		}
	});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/NO5_3", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_3").html(s_div);
		}
	});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/NO5_4", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_4").html(s_div);
		}
	});
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/NO5_5", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_5").html(s_div);
		}
	});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/NO5_6", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_6").html(s_div);
		}
	});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/NO5_7", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_7").html(s_div);
		}
	});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/NO5_8", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_8").html(s_div);
		}
	});
	
	
</script>

</body>


</html>