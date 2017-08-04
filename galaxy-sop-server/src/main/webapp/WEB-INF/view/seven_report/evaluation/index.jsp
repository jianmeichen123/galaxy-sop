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
				<li class="test_top_first">项目综合测评得分:<span id="total-score">0</span></li>
				<li><font id="part-title-name">项目评测</font>得分:<span id="part-score">0</span></li>
				<li>权重:<span id="part-weight" >100%</span></li>
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

/**
 * 加载标题
 */
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
			$("#part-title-name").text(entity.name);
			$("#test_tmpl").tmpl(entity).appendTo('#page_all');
			/*显示结果  */
			/* 16类型内容处理 */
	
			var content_16 = $(".content_16").text();		
			content_16=content_16.replace(/<sitg>/g,'（');
			content_16=content_16.replace(/<\/sitg>/g,'）');
			$(".content_16").text(content_16); 
			//显示结果和分数向
			showResultAndScoreList(relateId);
			 //修改分数时自动计算
			 $(".score-column select,input").change(function(){
				 calcScore();
			 });
			
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
 * 显示结果和分数项
 */
function showResultAndScoreList(relateId)
{
	sendGetRequest(
			platformUrl.getRelateTitleResults+"1/"+relateId+"/${projectId}", 
			null,
			function(data){
				if(data.result.status == 'OK')
				{
					$.each(data.entityList,function(){
						var rid = this.relateId;
						var weight = this.weight;
						//分数选择
						if(rid==relateId)
						{
							$("#part-weight").text(this.weight+"%");
						}
						if(weight != 'undefined')
						{
							$("span[class='title-weight'][data-relate-id='"+rid+"']").html("<br/>( "+weight+"% )");
						}
						var autoList = this.autoList;
						if(typeof autoList != 'undefined' && autoList.length>0 )
						{
							var sel = $('td[class="score-column"][data-relate-id="'+rid+'"]').find('select');
							sel.empty();
							sel.append('<option>请选择</option>')
							$.each(autoList,function(){
								sel.append('<option>'+this.grade+'</option>')
							});
						}
						//结果
						buildResult(this)
					});
					initScore(relateId);
				}
			}
		);
}
/**
 * 回显分数
 */
function popScore(titles,relateId)
{
	$.each(titles,function(rid,score){
		if(rid == 0)
		{
			$("#total-score").text(score);
		}
		else if(rid == relateId)
		{
			$("#part-score").text(score);
		}
		else
		{
			var td = $('td[class="score-column"][data-relate-id="'+rid+'"]');
			if(rid.indexOf('-')>0)
			{
				var arr = rid.split('-');
				rid = arr[0];
				var subId = arr[1];
				td = $('td[class="score-column"][data-relate-id="'+rid+'"][data-sub-id="'+subId+'"]');
			}
			var ele = td.children('input,select');
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
/**
 * 页面加载时获取
 */
function initScore(relateId)
{
	sendGetRequest(
			platformUrl.getScores, 
			{"parentId":0,"projectId":"${projectId}","reportType":"1"},
			function(data){
				if(data.result.status == 'OK')
				{
					var titles = data.userData;
					popScore(titles);
				}
			}
		);
}
/**
 * 计算分数分数
 */
function calcScore()
{
	var rid = $("#eva-tabs li.active").data('relateId');
	var data = {
		"parentId": 0	,
		"relateId": rid,
		"reportType": 1,
		"projectId":"${projectId}"
	};
	var items = new Array();
	$(".title-value").each(function(){
		var _this = $(this);
		var relateId = _this.data('relateId');
		var subId = typeof _this.data('subId')=='undefined' ? null:_this.data('subId');
		console.log(relateId+"="+subId);
		var values = getTitleValue(relateId);
		var score = getScore(relateId,subId);
		items.push({
			"relateId": relateId,
			"subId": subId,
			"values": values,
			"score": score
		});
	});
	data.items = items;
	sendPostRequestByJsonObj(
			platformUrl.calculateScore, 
			data,
			function(data){
				if(data.result.status == 'OK')
				{
					var titles = data.userData;
					popScore(titles,rid);
				}
			}
		);
}
function getTitleValue(relateId)
{
	var _ele = $(".title-value[data-relate-id='"+relateId+"']");
	var val = _ele.attr('data-title-value');
	if(typeof val == 'undefined' || val.length==0)
	{
		return null;
	}
	return val.split(',');
}
function getScore(relateId,subId)
{
	var score = null;
	var td = $('td[class="score-column"][data-relate-id="'+relateId+'"]');
	if(subId >0)
	{
		td = $('td[class="score-column"][data-relate-id="'+relateId+'"][data-sub-id="'+subId+'"]');
	}
	var ele = td.children('input,select');
	if(ele.length ==0)
	{
		score = td.text();
	}
	else
	{
		score = ele.val();
	}
	if(score == "" || isNaN(score))
	{
		return null;
	}
	return score;
}

function afterTitleSaved()
{
	calcScore();
}
/**
 * 结果回显
 */
function buildResult(title)
{
	var results = title.resultList;
	var type = title.type;
	if(typeof results == 'undefined' || results.length == 0)
	{
		return;
	}
	var _ele = $('.title-value[data-title-id="'+title.id+'"]');
	//Radio
	if(type == 2)
	{
		_ele.attr('data-title-value',results[0].contentChoose)
		_ele.text(results[0].valueName);
	}
	else if(type == 8)
	{
		_ele.text(results[0].contentDescribe1);
	}
	else if(type == 15)
	{
		_ele = $('.title-value[data-title-id="'+title.id+'"][data-sub-id="'+title.subId+'"]');
		var val = results[0]["contentDescribe"+title.subId];
		if(typeof val == 'undefined')
		{
			_ele.text('未填写');
		}
		else
		{
			_ele.text(val);
		}
		
	}
}


	
	
	
	
	
	

</script>
<script src="<%=path%>/js/seven_report/eva_report/icheck.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/seven_basic.js"></script>	
</body>


</html>
