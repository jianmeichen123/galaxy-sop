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
		console.log(entity.childList[0].childList[0].type)
		console.log(typeof(entity.childList[0].childList[0].type))
		$("#test_tmpl").tmpl(entity).appendTo('#page_all');
	}
	
})
//整体页面显示



//这里不需要window.onload  和 $(document).ready(function(){})
	function　mouserover(obj){
		if($(obj).data('edit') == 'true') return;
		var target = $(obj).find('.editPic');
		 target.show();
	};
	function mouseout(obj){
		var target = $(obj).find('.editPic');
		target.hide();
	};
	
	
	
	//弹窗   由不可以编辑变为可以编辑状态
	function canEdit(obj){
		var textarea = $(obj).parent().find('textarea');
		textarea.removeAttr('readonly');
	};
	
	//获取所有的可编辑的td所在列
	
	
	
	//选择题的编辑按钮
	function chooseEdit(obj){
		$(obj).closest('td').addClass('edit_true');
		var pText = $(obj).parent().find('p');
		pText.hide();
		$(obj).hide();
		//对号，×号显示
		$(obj).closest('td').find('.Button').show();
		$(obj).closest('td').find('.radioShow').show();
		//给td加自定义属性
		$(obj).parent().data('edit','true')
	}

	
	//填空题的编辑按钮
	function gapEdit(obj){
		$('.mashLayer').show();
		$('.popup').show();
		
	}
	//  对号函数
	function right(obj){
		//对号，x号消失
		$(obj).parent().hide();
		//raido消失
		$(obj).parent().parent().find('.radioShow').hide();
		var val = $(obj).parent().parent().find('input:checked').val();
		console.log(val)
		$(obj).parent().parent().find('p').html(val);
		$(obj).parent().parent().find('p').show();
		$(obj).parent().parent().find('p').css('color','#000')
		$(obj).closest('td').data('edit','false');
		//select下拉框的值
		var selectText = $(obj).closest('td').find('.select_long').find("option:selected").text();
		$(obj).parent().parent().find('.seclect_choose').html(selectText);
		console.log(selectText)
		//select下拉框消失
		$(obj).closest('td').find('.selectTips').hide();
		//弹窗消失
		$(obj).closest('.popup').hide();
		$('.mashLayer').hide();
		
	}
	//x号函数
	function closeX(obj){
		//对号,x号消失
		$(obj).parent().hide();
		//radio 消失
		$(obj).parent().parent().find('.radioShow').hide();
		//p内容展示
		$(obj).parent().parent().find('p').show();
		$(obj).parent().parent().find('p').css('color','#000');
		$(obj).closest('td').data('edit','false');
		
		//select下拉框消失
		$(obj).closest('td').find('.selectTips').hide();
		//弹窗消失
		//$(obj).parent().parent().hide();
		$(obj).closest('.popup').hide();
		$('.mashLayer').hide();
	}
	
	
	
	
	
	
	
	
	//遮罩层
	function pageHeight(){
		return document.body.scrollHeight;
	}
	function pageWidth(){
		return document.body.scrollWidth;
	}
	$('.mashLayer').height(pageHeight());
	$('.mashLayer').width(pageWidth());
	
	
	
	
	//弹窗
	var popup = document.getElementsByClassName('popup')[0];
	var popupTwo = document.getElementsByClassName("popup_two")[0];
	var popupThree = document.getElementsByClassName("table_three")[0];
	var ch_stock = document.getElementsByClassName("ch_stock")[0];
	adjust(popup);
	adjust(popupTwo);
	adjust(popupThree);
	adjust(ch_stock);
	/* 定位到页面中心 */
	function adjust(id) {
	    var w = $(id).width();
	    var h = $(id).height();
	    
	    var t = scrollY() + (windowHeight()/2) - (h/2);
	    if(t < 0) t = 0;
	    
	    var l = scrollX() + (windowWidth()/2) - (w/2);
	    if(l < 0) l = 0;
	    
	    $(id).css({left: l+'px', top: t+'px'});
	}
    
	
	//浏览器视口的高度
	function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
	}

	//浏览器视口的宽度
	function windowWidth() {
	    var de = document.documentElement;
	    return self.innerWidth || (de && de.clientWidth) || document.body.clientWidth
	}

	/* 浏览器垂直滚动位置 */
	function scrollY() {
	    var de = document.documentElement;
	    return self.pageYOffset || (de && de.scrollTop) || document.body.scrollTop;
	}

	/* 浏览器水平滚动位置 */
	function scrollX() {
	    var de = document.documentElement;
	    return self.pageXOffset || (de && de.scrollLeft) || document.body.scrollLeft;
	}
	
	
	//详情，展开，  收起方法
	$('.detail').click(function(){
		$('.income_structor_content').removeClass('income_structor_content');
		$(this).hide();
		$('.detail').prev().show();
	})
	$('.packup').click(function(){
		$('.income_structor span').addClass('income_structor_content');
		$(this).hide();
		$(this).next().show();
	})
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

</script>

</body>


</html>
