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
     <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基本<br/>信息</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
    <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('8')">融资及<br/>估值</li>

  </ul>
  <div id="tab-content">
		<div class="tabtxt">


			<!--例子 -->
			<div class="h radius" id="div_ifelse">

			</div>

			<!-- defrd -->

<script id="ifelse" type="text/x-jquery-tmpl">
<div class="h_edit">
	<div class="h_btnbox">
		<span class="h_save_btn">保存</span><span class="h_cancel_btn"
			data-on="h_cancel">取消</span>
	</div>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}
		<div class="mb_16">
			<dl class="h_edit_txt clearfix">
		
			<dt class="fl_none" data-type="\${type}">\${name}</dt>
			{{if type=="1"}}
			<dd class="fl_none">
				<dd class="fl_none">
				<textarea class="textarea_h" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}"></textarea>
				<p class="num_tj">
					<label for="">0</label>/2000
				</p>
			</dd>
			</dd>
			{{else type=="2"}}
			<dd class="fl_none">
				<ul class="h_radios clearfix">
					{{each(i,valueList) valueList}}
                    <li><input type="radio" name="\${titleId}" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}">\${name}</li>
					{{/each}}
                 </ul>
			</dd>
			{{else type=="8"}}
			<dd class="fl_none">
				<dd class="fl_none">
				<textarea class="textarea_h" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}"></textarea>
				<p class="num_tj">
					<label for="">0</label>/2000
				</p>
			</dd>
			</dd>
			{{else}}
			<dd class="fl_none">
				<textarea class="textarea_h">eee</textarea>
				<p class="num_tj">
					<label for="">0</label>/2000
				</p>
			</dd>
			{{/if}}
			</dl>
			
		</div>
	{{/each}}
	
	<div class="h_edit_btnbox clearfix">
		<span class="pubbtn bluebtn fl" data-on="save" data-code="\${code}">保存</span> <span
			class="pubbtn fffbtn fl" data-name="basic" data-on="h_cancel">取消</span>
	</div>
</div>										
</script>



			<!--tab end-->
		</div>
	</div>

	<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
	<script type="text/javascript">
		sendGetRequest(platformUrl.queryAllTitleValues + "NO2_2", null,
				function(data) {
					console.log(data);
					var result = data.result.status;
					if (result == 'OK') {
						var entity = data.entity;
						$("#ifelse").tmpl(entity).appendTo('#div_ifelse');
					} else {

					}
				})
	</script>
</body>


</html>
