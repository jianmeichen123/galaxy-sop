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
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../../common/taglib.jsp"></jsp:include> 
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

</head>

<body >

	<div class="pagebox">
		<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
		<jsp:include page="infoJsp.jsp" flush="true"></jsp:include>
 
	</div>
	

</body> 
<jsp:include page="../../common/header.jsp" ></jsp:include>
<jsp:include page="../../common/footer.jsp" ></jsp:include></body>
<jsp:include page="../../common/uploadwin.jsp" ></jsp:include>
	  
</html>
<script>
$(function(){

	createMenus(5); 
	//导航
	function getURLParameter(name) {
	    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	var _url = Constants.sopEndpointURL +"/galaxy/infoDanao/searchProject";
	var projectName=getURLParameter("projectName"); 
	var projectId=getURLParameter("projectId");
	$("#projectName").text(projectName);
	var data={
   			"keyword":projectName,
			"orderBy":"projTitle",
    		}
	//分页
	initTable(_url,data,0); 

	function infoDetail(event){
		var compCode=$(event).attr("compCode");
		var projCode=$(event).attr("projCode");
		forwardWithHeader(Constants.sopEndpointURL + "/galaxy/test/jtym2/?&compCode="+compCode+"&projCode="+projCode+"&projectId="+projectId+"&projectName="+projectName);
		}  

})	
	
	
	
	
	
	
	
	
	
	
	
</script>
