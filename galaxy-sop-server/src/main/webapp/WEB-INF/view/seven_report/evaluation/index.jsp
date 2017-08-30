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
<title>项目详情-评测报告</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/seven_report/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/seven_report/skins/all.css" type="text/css" rel="stylesheet"/>
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
    <jsp:include page="../reportcommon.jsp" flush="true"></jsp:include>
    <div class="new_left">
       	<ul class="h_navbar clearfix" id="eva-tabs">
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO1" data-relate-id="1001">项目<br />评测</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO2" data-relate-id="1031">团队<br />评测</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO3" data-relate-id="1071">运营<br />评测</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO4" data-relate-id="1091">竞争<br />评测</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO5" data-relate-id="1110">融资<br />评测</li>
			<li data-tab="navInfo" class="fl h_nav1" data-code="ENO6" data-relate-id="1116">退出<br />评测</li>
		</ul>
		<div class="test_top">
			<ul class="clearfix" id="title-info">
				<li class="test_top_first">项目综合测评得分:<span id="total-score">0</span></li>
				<li><font id="part-title-name">项目评测</font>得分:<span id="part-score">0</span></li>
				<li>权重:<span id="part-weight" >100%</span></li>
				<button class="test_top_last" id="save-rpt-btn" disabled="true">
					<em class="disabled">保存</em>
				</button>
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
		<div class="h_look h h_team_look ch_opration" id="scrollbar">
		</div>
		<!-- 图片展示弹窗 -->
		<div class="customer_income">
			<div class="Button popupButton ch_reason_stock">
					<i onclick="closeX(this)" class="wrong h_cancel_btn"></i>
			</div>
			<img class="img_inner" src="" />
			<p target="_blank" class="master_pic">原图</p>			
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
	<!-- 查看原图图片 -->
	<p class="x_picture"></p>
	<img class="o_picture" src="" />
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
<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/hologram/base_table.js"></script>
<script src="<%=path %>/js/hologram/baseInfo.js"></script>				

<script type="text/javascript">
createMenus(5);
var reportType = 1;
var projId="${param.pid}";
var isEditable = "${isEditable}";
</script>
<script src="<%=path%>/js/seven_report/seven_report_common.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/eva_table.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/validate.js"></script>
<script src="<%=path%>/js/seven_report/eva_common.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/icheck.js"></script>
<script src="<%=path%>/js/seven_report/eva_report/textarea.js"></script>	
<script src="<%=path%>/js/seven_report/eva_report/seven_basic.js"></script>
				
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
function beforeSave(url){
	$.getHtml({
		url:'<%=path%>/html/beforeSave.html',  
		data:"",//传递参数
		okback:function(){
			var editbox = $(".radioShow");	
			var edit_status=false;
			$.each(editbox,function(){
				if(!$(this).is(":hidden")){
					edit_status=true;
					return false;
				}
			})
			$("#cancel").click(function(){  //取消
					$("a[data-close=\"close\"]").click();
				});
				$("#leave").click(function(){
					$("a[data-close=\"close\"]").click();
					$(window).unbind('beforeunload');
					//点击直接离开跳到项目列表页
					forwardToPage(url);
				});
				$("#save").click(function(){
					$("a[data-close=\"close\"]").click();
					$("#save-rpt-btn").click();   //点击保存，保存数据
					forwardToPage(url);
					
				})
			if(edit_status==false){
				
			}else{
				var resul = "<p>当前页面正在编辑，确认离开此页面？<p><em>直接离开有可能导致数据丢失，建议保存后在离开</em>";
				$(".tips.deltc").html(resul);
				$("#leave").html("确定").addClass("bluebtn").removeClass("fffbtn").css("margin-left","105px");
				$("#cancel").removeClass("fr").css("margin-left","35px");
				$("#save").remove();
				$(".before_save_tc .con").css("margin-bottom","30px")
			}
			
		}//模版反回成功执行	
	});
	return false;
}

function forwardToPage(url){   //跳到相关页面
	var where=$(".pagebox").attr("data-lis");
	//alert(where);
	if(where=="tab"){  //点击评测报告tab切换
		var code = $(".pagebox").attr('data-code');
		var relateId =$(".pagebox").attr('data-relateId');
		//加载相应tab页；
		$("#eva-tabs li[data-code='"+code+"']").siblings().removeClass('active');
		$("#eva-tabs li[data-code='"+code+"']").addClass('active');
		tabShow(code,relateId);   
		$("#save-rpt-btn").attr("disabled");
		$("#save-rpt-btn em").addClass("disabled");
	}else{   //点击页面其他能跳转的地方
		//跳到相关页					
		forwardWithHeader(url); 
	}
}
//离开页面提示
 $(function(){
	 $(".usermsg #daiban,.usermsg .work").click(function(){
		 var _href=window.location.href;
		 var url=$(this).attr("href");
		 if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
				var result=$(".pagebox").attr("data-result");
				 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
				if(result=="true"){
					//$(window).unbind('beforeunload');
					beforeSave(url);
					return false;
				}else{
					//$(window).unbind('beforeunload');
				}
			}
	});

}) 

</script>

</html>
