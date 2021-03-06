<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<% 
	String path = request.getContextPath(); 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
	response.setHeader("Pragma","no-cache"); //HTTP 1.0    
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server 
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!-- 保存进度条 -->
<link href="<%=path %>/css/showLoading.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<style type="text/css">
div.tip-yellowsimple {
    visibility: hidden;
    position: absolute;
    top: 0;
    left: 0;
}
.tip-yellowsimple .tip-arrow-left{
position:absolute;
}

</style>
</head>
<script src="<%=path %>/js/projectTransfer.js"></script>
<body>
	<div class="new_tit_a" id="top_menu"><a href="javascript:;" onclick="backList()">创投项目</a><img alt="" src="<%=path %>/img/arrow-brumd.png" class="arrow"/>
	<a id="project_name_title" href="javascript:;" onclick="backProject()"></a><img alt="" src="<%=path %>/img/arrow-brumd.png"  class="arrow"/>&nbsp;<span class="report_type"></span>
	</div>
    	
    	<div class="new_tit_b">
        	<span class="size18 report_type"></span>
        	<span class="report_select">
        		<input type="text" value="切换报告" class="input_selects" readonly="readonly" UNSELECTABLE='true' disabled>
        		<ul class="select_lists">
        			<li onclick="seven_link(1);">全息报告</li>
        			<li onclick="seven_link(2);">评测报告</li>
        			<li onclick="seven_link(3);">尽调报告</li>
        			<li onclick="seven_link(4);">决策报告</li>
        			<li onclick="seven_link(5);">初评报告</li>
        			<li onclick="seven_link(6);">融资报告</li>
        			<li onclick="seven_link(7);">运营报告</li>
        		</ul>
        	</span>
        	<a href="javascript:;" onclick="downExport()" class="bluebtn ico tj exportReport">导出报告</a>
        	<!-- <span class="b_span"> 
	        	<a href="#" onclick="back();">返回></a>
			</span>  -->
        </div>
</body>
<!-- 高管/投资经理 -->
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isCreatedByUser" value="${fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<c:set var="isThyy" value="${fx:hasRole(20)}" scope="request"/>
<script>

var isCreatedByUser = "${isCreatedByUser}";
var isEditable = "${isEditable}";
var pid='${pid}'=='' ? '${param.pid}' : '${pid}';
if(null==pid||typeof(pid)=="underfind"||pid==""){
	pid='${projectId}';
}

var projectInfo = '';
sendGetRequest(platformUrl.detailProject + pid, {}, function(data){	
	projectInfo = data.entity;
	console.log(projectInfo)
});
$("#project_name_title").text(projectInfo.projectName);
var str=projectInfo.projectName;
if((projectInfo.projectName!=undefined) && (projectInfo.projectName.length>24)){
	str=projectInfo.projectName.substring(0,24);
}

	var _href=window.location.href;
	if(_href.indexOf("toEvalindex")>-1){
		var report_type="评测报告";
	}else if(_href.indexOf("toBaseInfo")>-1){
		var report_type="全息报告";
	}else if(_href.indexOf("investigate")>-1){
		var report_type="尽调报告";
	}else if(_href.indexOf("toDecision")>-1){
		var report_type="决策报告";
	}else if(_href.indexOf("toPreEva")>-1){
		var report_type="初评报告";
	}else if(_href.indexOf("toFinancing")>-1){
		var report_type="融资报告";
	}else if(_href.indexOf("toOperation")>-1){
		var report_type="运营报告";
	}
	$(".report_type").text(report_type);
	divSelect();
	function divSelect(){
		$(".report_select").mouseover(function(){ 
			var _this = $(this).find(".input_selects");
			var ul = _this.next("ul"); 
			_this.addClass('up');
			ul.show(); 
		}).mouseout(function(){
			var _this = $(this).find(".input_selects");
			var ul = _this.next("ul"); 
			ul.hide();
			_this.removeClass('up');
		}); 
	}
	//切换报告相关按钮隐藏
	var reportName=$(".new_tit_b .report_type").text();
	$.each($(".select_lists li"),function(){
		var txt=$(this).text();
		if(txt==reportName){
			$(this).addClass("none");
		}
	})

function seven_link(data,url){
	var _href=window.location.href;
	if(data==1){
		url=platformUrl.toBaseInfo;
	}else if(data==2){
		url=platformUrl.toEvalindex;
	}else if(data==3){
		url=platformUrl.investigate;
	}else if(data==4){
		url=platformUrl.toDecision;
	}else if(data==5){
		url=platformUrl.toPreEva;
	}else if(data==6){
		url=platformUrl.toFinancing;
	}else if(data==7){
		url=platformUrl.toOperation;
	} 
	if((_href.indexOf(platformUrl.toEvalindex)>-1) || (_href.indexOf(platformUrl.toPreEva)>-1)){   //判断评测报告或初评报告
		var result=$(".pagebox").attr("data-result");
		 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
		if(result=="true"){
			//$(window).unbind('beforeunload');
			beforeSave(url+'?pid='+pid);
		}else{
			//$(window).unbind('beforeunload');
			window.location.href = url+'?pid='+pid+"&sid="+sessionId+"&guid="+userId;
		}
	}else{
		window.location.href = url+'?pid='+pid+"&sid="+sessionId+"&guid="+userId;
	}
} 

/**
 * 面包屑
 * @version 2016-06-21
 */
/* function backIndex(){
	  var url=Constants.sopEndpointURL+"/galaxy/index";
	  window.location.href = url+"?sid="+sessionId+"&guid="+userId+"&_is_menu_=true";
	
} */
function backProject(url){  //返回项目详情页
	var _href=window.location.href;
	var url=Constants.sopEndpointURL + "/galaxy/project/detail/" + projectInfo.id;
	if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
		var result=$(".pagebox").attr("data-result");
		 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
		if(result=="true"){
			//$(window).unbind('beforeunload');
			beforeSave(url);
		}else{
			//$(window).unbind('beforeunload');
			if(url!=""){
				forwardWithHeader(url);
			}
		}
	}
}
function backList(url){   //返回项目列表页
	var _href=window.location.href;
	var url=Constants.sopEndpointURL+"/galaxy/mpl";
	if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
		var result=$(".pagebox").attr("data-result");
		 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
		if(result=="true"){
			//$(window).unbind('beforeunload');
			beforeSave(url);
		}else{
			//$(window).unbind('beforeunload');
			forwardWithHeader(url);
		}
	}
}

/**
 * 导出报告
 */
function downExport() {
	var _href=window.location.href;
	if(_href.indexOf('toBaseInfo')>-1){
		 window.location.href = platformUrl.downNOdoc + pid+"?sid="+sessionId+"&guid="+userId;
	}else if(_href.indexOf('toEvalindex')>-1){
		window.location.href = platformUrl.downENdoc + pid+"?sid="+sessionId+"&guid="+userId;
	}
}
</script>

</html>

