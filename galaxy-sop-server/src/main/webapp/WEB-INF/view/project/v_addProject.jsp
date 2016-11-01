<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!-- 通用样式 -->
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
createMenus(5);
</script>
<script src="<%=path%>/js/v_add_project_1.js"></script>
<script src="<%=path%>/js/v_add_project_2.js"></script>
<script src="<%=path%>/js/v_add_project_3.js"></script>
<script src="<%=path%>/js/v_add_project_4.js"></script>
</body>
</html>