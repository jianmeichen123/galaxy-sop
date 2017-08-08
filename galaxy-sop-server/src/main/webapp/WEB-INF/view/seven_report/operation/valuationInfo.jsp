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

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

<title>项目详情66666</title>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path %>/js/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<!-- 时间插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
</head>
<body>
<!-- <ul class="h_navbar clearfix">
     <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基础<br/>信息</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
    <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('8')">融资及<br/>估值</li>

  </ul> -->
   <!--隐藏-->
<div class="bj_hui_on"></div>
  <jsp:include page="../../report_basic/jquery-tmpl.jsp" flush="true"></jsp:include>
  <div id="tab-content">
		<div class="tabtxt valuation" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>
	</div>


<script type="text/javascript">
var path = '<%=path%>';
createMenus(5);
var isEditable = "${isEditable}";
$(function() {
	//right_anchor("DNO3?reportType=2");
})
//整体页面显示
sendGetRequest(platformUrl.queryAllTitleValues + 'ONO9?reportType=7', null,
	function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			$("#page_list").tmpl(entity).appendTo('#page_all');
			picData(projectInfo.id,1);
			customBuilder();
			$(".section").each(function(){
				$(this).showResults(true);
				var table = $(this).find('.mb_24 table');
				table.each(function(){
					if($(this).find('tr').length<=1){
						$(this).hide();
						if($(this).parents('dl').find('dd:gt(0)').length<=0){
							$(this).parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
						}
					}
					else{
						$(this).show();
					}
				})

			});
			//调整表格
			$("table").css({"width":"80%","table-layout":"fixed"});
			mustData(projectInfo.id,0);
			toggle_btn($('.anchor_btn span'),1);
			fun_click();
			hideNav();

		} else {

		}

})
</script>
</body>


</html>
