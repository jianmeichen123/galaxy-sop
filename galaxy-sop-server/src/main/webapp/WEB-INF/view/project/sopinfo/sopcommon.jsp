<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
	<div class="new_tit_a"><a href="#" id="workDesk">工作桌面</a>><a href="#">创投项目</a>><span id="project_name_title"></span></div>
    	
    	<div class="new_tit_b">
        	<span class="new_color size18" id="project_name"></span><span class="new_color" id="project_code"></span>
        	<span class="b_span"><a href="#">返回项目列表></a></span>
        </div>
</body>
</html>
<script>
$(function(){
	var pid='${pid}';
	if(null==pid||typeof(pid)=="underfind"||pid==""){
		pid='${projectId}';
	}
	sendGetRequest(platformUrl.detailProject + pid, {}, function(data){	
		$("#project_name_title").text(data.entity.projectName);
		$("#project_name").text(data.entity.projectName);
		$("#project_code").text(data.entity.projectCode);
	});

   $("#workDesk").click(function(){
	   var url=Constants.sopEndpointURL+"/galaxy/index";
	   forwardWithHeader(url);
	
   })

})

</script>
