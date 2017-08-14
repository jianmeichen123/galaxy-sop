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
<link href="<%=path %>/css/seven_report/skins/all.css" type="text/css" rel="stylesheet"/>
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
    <jsp:include page="../reportcommon.jsp" flush="true"></jsp:include>
    <div class="new_left">
       	<ul class="h_navbar clearfix" id="eva-tabs">
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO1" data-relate-id="1001">项目<br />评测</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO2" data-relate-id="1031">团队<br />评测</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO3" data-relate-id="1071">运营<br />测评</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO4" data-relate-id="1091">竞争<br />测评</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO5" data-relate-id="1110">融资<br />测评</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO6" data-relate-id="1116">退出<br />测评</li>
		</ul>
		<div class="test_top">
			<ul class="clearfix" id="title-info">
				<li class="test_top_first">项目综合测评得分:<span id="total-score">0</span></li>
				<li><font id="part-title-name">项目评测</font>得分:<span id="part-score">0</span></li>
				<li>权重:<span id="part-weight" >100%</span></li>
				<li class="test_top_last disabled" id="save-rpt-btn">
					<em>保存</em>
				</li>
			</ul>
		</div>
		<form id="table_box">
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
		</form>
		
		<!--弹窗1  -->	
		<div class="gapPopup">
			<div class="Button popupButton">
				<em onclick="right(this,'textarea')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
			</div>
			<span class="show_edit"></span>
		</div>
		<!--弹窗2 -->	
		<div class="h_look h h_team_look clearfix ch_opration">
		</div>
		<!-- 图片展示弹窗 -->
		<div class="customer_income">
			<div class="Button popupButton ch_reason_stock">
					<i onclick="closeX(this)" class="wrong h_cancel_btn"></i>
			</div>
			<img class="img_inner" src="" />
			<a target="_blank" href="" class="master_pic">原图</a>			
		</div>
		
<!-- 股权结构展示弹窗 -->	
<div class="reasonable_stock">
	<div class="Button popupButton ch_reason_stock">
			<i class="wrong close_tab"></i>
	</div>
	<div class="reasonable_box">
		<table border="1">
			<thead>
				<th>持股人</th>
				<th>持股比例</th>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	
</div>
		
	</div>
	<!-- 遮罩层 -->
	<div class="mashLayer"></div>
	
	
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
var reportType = 1;
var projId="${projectId}";
</script>
<script src="<%=path%>/js/seven_report/eva_report/validate.js"></script>
<script src="<%=path%>/js/hologram/hologram_common.js"></script>	
<script src="<%=path%>/js/seven_report/eva_common.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/icheck.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/seven_basic.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/autosize.js"></script>	
<!-- 时间插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>	
</body>
<script>
function getDetailUrl(code)
{
	if(code == 'share-holding')
	{
		return '<%=path%>/html/team_add_cgr.html';
	}
	return "";
}
function beforeSave(){
	$.getHtml({
		url:'<%=path%>/html/beforeSave.html',  
		data:"",//传递参数
		okback:function(){
			$("#cancel").click(function(){  //取消
				$("a[data-close=\"close\"]").click();
			});
			$("#leave").click(function(){
				//点击直接离开跳到项目列表页
				var url=Constants.sopEndpointURL+"/galaxy/mpl";
				forwardWithHeader(url);
			});
			$("#save").click(function(){
				//点击保存，保存数据，并跳到项目列表页
				$("a[data-close=\"close\"]").click();
				$("#save-rpt-btn").click();
				var url=Constants.sopEndpointURL+"/galaxy/mpl";
				forwardWithHeader(url);
			})
		}//模版反回成功执行	
	});
	return false;
}
//离开页面提示

 $(function(){
	 $(".usermsg #daiban,.usermsg .work").click(function(){
		 var _href=window.location.href;
			if(_href=platformUrl.toEvalindex){
				var result=$(".pagebox").attr("data-result");
				if(result=="true"){
					$(window).unbind('beforeunload');
					beforeSave();
					return false;
				}else{
					$(window).unbind('beforeunload');
				}
			}
	});

}) 

</script>

</html>
