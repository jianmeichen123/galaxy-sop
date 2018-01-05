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

	var projectInfo = ${projectInfo}; 
	createMenus(5); 
	//导航 
	var _url = Constants.sopEndpointURL +"/galaxy/infoDanao/searchProject"; 
	var projectName= projectInfo.projectName; 
	var projectId=projectInfo.id;
	
	$("#projectName").text(projectName);
	var data={
   			"keyword":projectName,
			"orderBy":"projTitle",
    		}
	function infoDetail(event){ 
		var compCode=$(event).attr("compCode");
		var projCode=$(event).attr("projCode"); 
		var dataJson={
				projId:projectId,
				projCode:projCode,
				compCode:compCode
		} 
		sendPostRequestByJsonObj(
				 Constants.sopEndpointURL + "/galaxy/infoDanao/saveConstat", 
				dataJson,
				function(data){
					 if(data.result.status=="OK"){
						 var DN_projectName=$(event).closest("tr").find(".DN_name").html()
							forwardWithHeader(Constants.sopEndpointURL + "/galaxy/infoDanao/info/"+projectId+"?DN_projectName="+DN_projectName);
					 } 
					
				})
		
	} 
	//分页
	initTable(_url,data,0);  
	function jumpPage(){
		forwardWithHeader(Constants.sopEndpointURL + "/galaxy/project/detail/"+projectId+ "?backurl=list");
	} 
</script>