<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.galaxyinternet.framework.core.constants.Constants"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
String endpoint = (String)application.getAttribute(Constants.GALAXYINTERNET_FX_ENDPOINT);
%>
<!-- 这里写js和css文件---------start -->
<script src="<%=request.getContextPath() %>/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/json2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure_ext.js" type="text/javascript"></script>
<!-- end -->
<script type="text/javascript">
	var contextEndPoint = '<%=endpoint%>';
	endpointObj = JSON.parse(contextEndPoint);
</script>
<script src="<%=request.getContextPath() %>/js/platformUrl.js" type="text/javascript"></script>