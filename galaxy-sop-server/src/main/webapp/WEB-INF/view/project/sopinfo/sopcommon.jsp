<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
	<div class="new_tit_a"><a href="#">工作桌面</a>><a href="#">创投项目</a>><span id="project_name_title"></span></div>
    	
    	<div class="new_tit_b">
        	<span class="new_color size18" id="project_name_t"></span><span class="new_color" id="project_code_t"></span>
        	<span class="b_span"><a href="javascript:;" onclick='backProjectList()'>返回项目列表></a></span>
        </div>
</body>

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
	$("#project_name_title").text(projectInfo.projectName);
	$("#project_name_t").text(projectInfo.projectName);
	$("#project_code_t").text(projectInfo.projectCode);

   $("#workDesk").click(function(){
	   var url=Constants.sopEndpointURL+"/galaxy/index";
	   forwardWithHeader(url);
   })

})

function backProjectList(){
	var url = platformUrl.projectList+"?backSign=true";
	forwardWithHeader(url);
}
</script>

</html>

