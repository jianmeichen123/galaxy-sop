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
	  
</html>
<script> 

	//导航
	createMenus(5);
	function getURLParameter(name) {
	    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	var _url = Constants.sopEndpointURL +"/galaxy/infoDanao/searchProject";
	var projectName=getURLParameter("projectName"); 
	var projectId=getURLParameter("projectId"); 
	var compCode=getURLParameter("compCode"); 
	var projCode=getURLParameter("projCode"); 
	$("#projectName").text(projectName);
	var _url = Constants.sopEndpointURL +"galaxy/infoDanao/searchProjectInfo/";
	var jsonObj={
			projId:projectId,
			projCode:projCode,
			compCode:compCode,
			titleCode:"1303",
	}
	sendPostRequestByJsonObj(_url, jsonObj, function(data){
	 
	}) 
	function jumpPage(){
		forwardWithHeader(Constants.sopEndpointURL + "/galaxy/project/detail/"+projectId+ "?backurl=list");
	} 
	
	
	function position(){
		var right=$(".infoBox").offset().right;
		var left=$(".infoBox").offset().left;
		$(".fixedbottom").css({
			"right":right,
			"left":left,
		})
	}
	position();
	$(window).resize(function(){
		position();
	}) 


</script>
