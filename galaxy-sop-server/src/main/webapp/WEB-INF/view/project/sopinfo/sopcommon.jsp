<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="new_tit_a"><a href="#" onclick="backIndex()">工作桌面</a>><a href="#" onclick="ProjectList()">创投项目</a>><span id="project_name_title"></span></div>
    	
    	<div class="new_tit_b">
        	<span class="size18" id="project_name_t"></span><span class="new_color" id="project_code_t"></span>
        	<span class="b_span"> 
        		<c:choose>
	        		<c:when test="${mark == 't' || mark == 'm' }">
						<a href="javascript:history.back()" onclick='back_href()'>返回></a>
					</c:when>
					<c:otherwise>
	        		  <a href="javascript:history.back()" onclick='backProjectList()'>返回项目列表></a>
	        		</c:otherwise>
				</c:choose>
			</span>
        </div>
</body>
<script src="<%=request.getContextPath() %>/js/cookie.js"></script>
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<script>
var pid='${pid}';
if(null==pid||typeof(pid)=="underfind"||pid==""){
	pid='${projectId}';
}

var projectInfo = '';
sendGetRequest(platformUrl.detailProject + pid, {}, function(data){	
	projectInfo = data.entity;
});

$(function(){
	if(projectInfo.projectName.length>24){
		var str=projectInfo.projectName.substring(0,24);
	}
	$("#project_name_title").text(projectInfo.projectName);
	$("#project_name_t").text(str);
	$("#project_name_t").attr("title",projectInfo.projectName);
	$("#project_code_t").text(projectInfo.projectCode);

   $("#workDesk").click(function(){
	   var url=Constants.sopEndpointURL+"/galaxy/index";
	   forwardWithHeader(url);
   })

})
function back_href(){
	setCookie("backProjectList", 'click',24,'/');
}
function backProjectList(){
	//ie兼容
	setCookie("backProjectList", 'click',24,'/');
	//var url = platformUrl.projectList+"?backSign=true";
	//forwardWithHeader(url);
}
/**
 * 面包屑
 * @version 2016-06-21
 */
function backIndex(){
 var url=Constants.sopEndpointURL+"/galaxy/redirect";
 forwardWithHeader(url);
}
function ProjectList(){
	 var url=Constants.sopEndpointURL+"/galaxy/mpl";
	 forwardWithHeader(url);
	}
</script>

</html>

