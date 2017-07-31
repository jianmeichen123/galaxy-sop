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
<title>项目详情</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
var pageId = "project";
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
    <jsp:include page="../..//project/sopinfo/sopcommon.jsp" flush="true"></jsp:include>
    <div class="new_left">
       	<ul class="h_navbar clearfix">
			<li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('1')">项目</li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br />数据 </li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br />策略 </li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br />估值 </li>
		</ul>
		<div id="tab-content base" class="base_tab-content"  data-id="tab-block">
		<div class="tabtxt" id="page_all"> 
		
			<div class="h radius" id="NO1_1"> </div>
			
			<div class="h radius base_con2" id="NO1_2"> </div>
			
		</div>
	</div>
	
	</div>
    <!--右边-->
<%--     <jsp:include page="./includeRight.jsp" flush="true"></jsp:include> --%>
    <div class="new_right" id="new_right"></div>
    
    
	

       <!--隐藏-->
<div class="bj_hui_on"></div>
	
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
<%-- <script src="<%=path%>/js/hologram/hologram_common.js"></script>		 --%>	
<script src="<%=path%>/js/seven_report/investigate/investigate_common.js"></script>	
<script type="text/javascript">
createMenus(5);


</script>

</body>


</html>
