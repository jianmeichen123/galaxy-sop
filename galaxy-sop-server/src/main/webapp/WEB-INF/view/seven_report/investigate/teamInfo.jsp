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

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

</head>
<body>
<!-- <ul class="h_navbar clearfix">
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基础<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
                </ul> -->
  <!--隐藏-->
<div class="bj_hui_on"></div>
 <div id="tab-content">
 <jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include>
		<div class="tabtxt" id="page_all">
		<!--tab-->


			<!--tab end-->
		</div>
	</div>

<jsp:include page="../../report_basic/jquery-tmpl.jsp" flush="true"></jsp:include>
<script type="text/javascript">
var path = '<%=path%>';
createMenus(5);
var isEditable = "${isEditable}";
$(function() {
	//right_anchor("DNO2?reportType=2");
})
//整体页面显示
sendGetRequest(platformUrl.queryAllTitleValues + 'DNO3?reportType=2', null,
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
