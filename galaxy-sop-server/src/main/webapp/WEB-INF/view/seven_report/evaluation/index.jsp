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
    <jsp:include page="../..//project/sopinfo/sopcommon.jsp" flush="true"></jsp:include>
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
$("#eva-tabs li").click(function(){
	var $li = $(this);
	if($li.hasClass('active'))
	{
		return;
	}
	var code = $li.data('code');
	var relateId = $li.data('relateId');
	$li.siblings().removeClass('active');
	$li.addClass('active');
	sendGetRequest(platformUrl.queryAllTitleValues+code+"?reportType=1", null,
		function(data){
		var result = data.result.status;
		if (result == 'OK') {
			
			$('#page_all').empty();
			var entity = data.entity;
			$("#test_tmpl").tmpl(entity).appendTo('#page_all');
			/*显示结果  */
			/* 16类型内容处理 */
	
			var content_16 = $(".content_16").text();		
			content_16=content_16.replace(/<sitg>/g,'（');
			content_16=content_16.replace(/<\/sitg>/g,'）');
			$(".content_16").text(content_16); 
			showScoreList(relateId);
			
		}
	});
	$.getTabHtml({
		url : platformUrl.toOperateInfo ,
		okback:function(){
			right_anchor(code+"?reportType=1","seven","show");
		}
	});
});
$("#eva-tabs li:eq(0)").click();
// 
/**
 * 显示分数选项
 */
function showScoreList(relateId)
{
	sendGetRequest(
			platformUrl.getRelateTitleResults+"1/"+relateId+"/${projectId}", 
			null,
			function(data){
				if(data.result.status == 'OK')
				{
					$.each(data.entityList,function(){
						var relateId = this.relateId;
						var autoList = this.autoList;
						if(typeof autoList != 'undefined' && autoList.length>0 )
						{
							var sel = $('td[class="score-column"][data-relate-id="'+relateId+'"]').find('select');
							sel.empty();
							sel.append('<option>请选择</option>')
							$.each(autoList,function(){
								sel.append('<option>'+this.grade+'</option>')
							});
						}
					});
					showScore(relateId);
				}
			}
		);
}

function showScore(relateId)
{
	sendGetRequest(
			platformUrl.getScores, 
			{"parentId":relateId,"projectId":"${projectId}","reportType":"1"},
			function(data){
				if(data.result.status == 'OK')
				{
					var titles = data.userData;
					$.each(titles,function(rid,score){
						if(rid == relateId)
						{
							
						}
						else
						{
							console.log(rid+"="+score)
							var td = $('td[class="score-column"][data-relate-id="'+rid+'"]');
							var ele = td.children('input,select');
							console.log(ele[0])
							if(ele.length ==0)
							{
								td.text(score)
							}
							else
							{
								ele.val(score);
							}
						}
					});
				}
			}
		);
}


//整体页面显示


	
	
	
	
	
	

</script>
<script src="<%=path%>/js/seven_report/eva_report/icheck.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/seven_basic.js"></script>	
</body>


</html>
