<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!doctype html>
<html class="scroll">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目详情1</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
var pageId = "project";
var path = '<%=path%>';
</script>
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
</head>

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>


<body >
<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
<div class="ritmin">
    <jsp:include page="../reportcommon.jsp" flush="true"></jsp:include>
    <div class="new_left">
       	<ul class="h_navbar clearfix">
			<li data-tab="navInfo" class="fl h_nav1 active" onclick="tabOperateChange('0')">投资<br />方案</li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabOperateChange('1')">团队</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabOperateChange('2')">运营<br />数据 </li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabOperateChange('3')">市场<br />与开发</li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabOperateChange('4')">竞争</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabOperateChange('5')">战略及<br />策略 </li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabOperateChange('6')">财务</li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabOperateChange('7')">法务</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabOperateChange('8')">融资及<br />估值 </li>
		</ul>
		<div id="tab-content base" class="base_tab-content"  data-id="tab-block">
		<div class="tabtxt" id="page_all"> 
		
		
			
		</div>
	</div>
	
	</div>
    <!--右边-->
<%--     <jsp:include page="./includeRight.jsp" flush="true"></jsp:include> --%>
    <div class="new_right" id="new_right"></div>
    
    
	

       <!--隐藏-->
    <div class="bj_hui_on"></div>
	<jsp:include page="../../report_basic/jquery-tmpl.jsp" flush="true"></jsp:include>
	</div>

</div>


<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>
<!-- 公用js -->
<script src="<%=path%>/js/jquery-1.12.2.min.js"></script>
<script src="<%=path %>/js/common.js"></script>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/teamSheetNew.js"></script>
<script src="<%=path %>/js/planbusiness.js"></script>
<script src="<%=path %>/js/projectDetail/tabFile.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/projectDetail.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/appro.js" type="text/javascript"></script>
<script src="<%=path %>/js/base_appropriation.js" type="text/javascript"></script>
<script src="<%=path %>/js/batchUpload.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/proPerOp.js"></script>
<script src="<%=path %>/js/v_baseInfo_project_history.js" type="text/javascript"></script>
 <!-- layer -->
<script src="<%=path %>/js/layer/layer.js"></script>
<!--提示验证  -->
<script src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script src="<%=path %>/js/hologram/base_table.js"></script>
<script src="<%=path %>/js/hologram/baseInfo.js"></script>

<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">	


<script src="<%=path%>/js/seven_report/operation/operation_common.js"></script>	

<!-- 跟全息图相似的 -->
<script src="<%=path%>/js/seven_report/seven_report_common.js"></script>
<script src="<%=path %>/js/seven_report/basic_fun.js" type="text/javascript"></script>
<script src="<%=path %>/js/seven_report/save_ok.js" type="text/javascript"></script>
<script type="text/javascript">
createMenus(5);
var isEditable = "${isEditable}";
$(function() {
	right_anchor("ONO1?reportType=7","seven","hide");
})
//整体页面显示
sendGetRequest(platformUrl.queryAllTitleValues + 'ONO1?reportType=7', null,
	function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			console.log(entity);
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
