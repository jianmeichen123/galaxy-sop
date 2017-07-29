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
<link href="<%=path %>/css/seven_report/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/seven_report/sevenReport.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
var pageId = "project";
</script>
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
</head>

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>


<body >
<jsp:include page="test-tmpl.jsp" flush="true"></jsp:include>
<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
<div class="ritmin">
    <jsp:include page="../..//project/sopinfo/sopcommon.jsp" flush="true"></jsp:include>
    <div class="new_left">
       	<ul class="h_navbar clearfix">
			<li data-tab="navInfo" class="fl h_nav1 active" onclick="testChange('0')">项目<br />评测</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="testChange('1')">团队<br/>评测</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="testChange('2')">运营<br />测评</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="testChange('3')">竞争<br />测评</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="testChange('4')">融资<br />测评</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="testChange('5')">退出<br />测评</li>
		</ul>
		<div class="test_top">
			<ul class="clearfix">
				<li class="test_top_first">项目综合测评得分:<span>90</span></li>
				<li>项目评测得分:<span>23</span></li>
				<li>权重:<span>30%</span></li>
				<li class="test_top_last">
					<em>保存</em>
				</li>
				
			</ul>
		</div>
		<table border="1" cellpadding="5" style="table-layout:fixed;word-break:break-all;">
			<thead>
				<tr>
					<td>评测指标</th>
					<td>指标细化</th>
					<td>指标详情</th>
					<td>分值</th>
					<td>评分细则</th>
					<td>打分</th>
					<td>核算得分</th>
				</tr>
			</thead>
			<tbody id="page_all">
			
			</tbody>
		</table>
		
	</div>
	<!-- 遮罩层 -->
	<div class="mashLayer"></div>
	<!--弹窗  -->
	<!-- 项目定位 -->
	
	<div class="popup">
		<div class="Button popupButton">
			<em onclick="right(this)" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
		</div>
		<div class="popup_content">
			<p>
				<span>该项目是一个通过或基于</span><textarea class="adption" placeholder="(请填写)"/></textarea><span>的</span>
				<textarea class="adption" placeholder="(请填写)"/></textarea><span>和</span><textarea class="adption" placeholder="(请填写)"/></textarea>，
				<span>为</span><textarea class="adption" placeholder="(请填写)"/></textarea><span>提供</span><textarea class="adption" placeholder="(请填写)"/></textarea>
				<span>产品和服务</span>，<textarea class="adption" placeholder="(请填写)"/></textarea><span>刚需或解决了</span><textarea class="adption" placeholder="(请填写)"/></textarea>
			</p>
		</div>
	</div>
    <!--右边-->
<%--     <jsp:include page="./includeRight.jsp" flush="true"></jsp:include> --%>
    <div class="new_right" id="new_right"></div>


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
<script src="<%=path%>/js/hologram/hologram_common.js"></script>			

<script type="text/javascript">
createMenus(5);
//页面数据
sendGetRequest(platformUrl.queryAllTitleValues+ "ENO1?reportType=1", null,
	function(data){
	var result = data.result.status;
	if (result == 'OK') {
		var entity = data.entity;
		console.log(entity)
		$("#test_tmpl").tmpl(entity).appendTo('#page_all');
		/*显示结果  */
		
		$("tr").each(function(){
 
		});
	}
	
})
//测试




//整体页面显示


	
	
	
	
	
	

</script>
<script src="<%=path%>/js/seven_report/seven_basic.js"></script>	
</body>


</html>
