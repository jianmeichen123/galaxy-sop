<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>  
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include> 
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

</head>

<body >

	<div class="pagebox">
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>	
	<jsp:include page="infoDJsp.jsp" flush="true"></jsp:include>
		
	</div>
	

</body> 
<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>
<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../../common/uploadwin.jsp" flush="true"></jsp:include>
 <script src="<%=path %>/js/danao/danao.js" type="text/javascript"></script>
</html>
<script> 
$(function(){
	var projectInfo = ${projectInfo}; 
	//导航
	createMenus(5); 
	function getURLParameter(name) {
	    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	var _url = Constants.sopEndpointURL +"/galaxy/infoDanao/searchProject";
	
	var projectName=projectInfo.projectName; 
	var projectId=projectInfo.id;  
	var projCode=projectInfo.danaoProjCode; 
	var DN_name=getURLParameter("DN_projectName")
	$("#projectName").html(DN_name);
	var _url = Constants.sopEndpointURL +"galaxy/infoDanao/searchProjectInfo/";
	var jsonObj={
			projId:projectId,
			projCode:projCode, 
	}; 
	 buildDNinfo(_url,jsonObj);
	 
	function jumpPage(){
		forwardWithHeader(Constants.sopEndpointURL + "/galaxy/project/detail/"+projectId+ "?backurl=list");
	}  
	function position(){  
		var left=$(".infoBox").offset().left; 
		$(".fixedbottom").css({
			"right":0,
			"left":left,
		})
	}
	position();
	$(window).resize(function(){
		position();
	})  

})
	
</script>
