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
	<div class="new_tit_a" id="top_menu"><a href="#" onclick="backIndex()">工作桌面</a>>
	<%--<c:choose>
		 <c:when test="${empty sessionScope._curr_menu_ }"> --%>
			<a href="#" onclick="projectList()">创投项目</a>
	<%-- 	</c:when>
		<c:otherwise>
			<a href="#">${sessionScope._curr_menu_ }</a>
		</c:otherwise> 
	</c:choose>--%>
	><span id="project_name_title"></span></div>
    	
    	<div class="new_tit_b">
        	<span class="size18" id="project_name_t"></span><span class="new_color" id="project_code_t"></span>
        	<span class="b_span"> 
	        	<a href="#" onclick="back();">返回></a>
			</span>
        </div>
</body>
<%-- <script src="<%=request.getContextPath() %>/js/cookie.js"></script> --%>
<!-- 高管/投资经理 -->
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isCreatedByUser" value="${fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<c:set var="isThyy" value="${fx:hasRole(20)}" scope="request"/>
<script>
/* var number_on;
$(function(){
	if(getCookieValue("number_on")==''){
		setCookie("number_on", '1',24,'/')
		number_on=getCookieValue("number_on");
	}else{
		number_on=getCookieValue("number_on");
		number_on++;
		setCookie("number_on",number_on,24,'/');
	}
}); */
var isCreatedByUser = "${isCreatedByUser}";
var isEditable = "${isEditable}";
var pid='${pid}';
if(null==pid||typeof(pid)=="underfind"||pid==""){
	pid='${projectId}';
}

var projectInfo = '';
sendGetRequest(platformUrl.detailProject + pid, {}, function(data){	
	projectInfo = data.entity;
});


function back(){
	var flag=getCookieValue("cooki_flag");//处理待办任务页面不需要保存参数
	console.log(flag);
	if(null==flag||""==flag||flag=="undefined"){
		setCookie("backProjectList", 'click',24,'/');
	}else{
		deleteCookie("cooki_flag","/");	
	}
	var href_url=getCookieValue("href_url");
	if(href_url){
		deleteCookie("href_url","/");
		window.location=href_url;
	}
}

$(function(){
	var str=projectInfo.projectName;
	if(projectInfo.projectName.length>24){
		str=projectInfo.projectName.substring(0,24);
	}
	$("#project_name_title").text(projectInfo.projectName);
	$("#project_name_t").text(str);
	$("#project_name_t").attr("title",projectInfo.projectName);
	$("#project_code_t").text(projectInfo.projectCode);
    $("#workDesk").click(function(){
	   var url=Constants.sopEndpointURL+"/galaxy/index";
	   forwardWithHeader(url);
    });
});
/**
 * 面包屑
 * @version 2016-06-21
 */
function backIndex(){
    var url=Constants.sopEndpointURL+"/galaxy/redirect";
    forwardWithHeader(url);
}
function projectList(){
	var url=Constants.sopEndpointURL+"/galaxy/mpl";
	forwardWithHeader(url);
}
</script>

</html>

