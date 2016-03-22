<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.galaxyinternet.framework.core.constants.Constants,com.galaxyinternet.framework.core.oss.OSSConstant"
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
<%
User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
String sessionId = "";
String realName = "";
Long userId=null;
if(null != user) {
	sessionId = user.getSessionId();
	if(null != user.getRealName()){
		realName = user.getRealName();
	}
	userId = user.getId();
}
String endpoint = (String)application.getAttribute(OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
%>

<!-- 这里写js和css文件---------start -->
<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jquery-validate.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/json2.js" type="text/javascript"></script>
<!-- end -->

<script type="text/javascript">
	var contextEndPoint = '<%=endpoint%>';
	endpointObj = JSON.parse(contextEndPoint);
	var sessionId = '<%=sessionId%>';
	var realName = '<%=realName%>';
	var userId = '<%=userId%>';
</script>
<script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure_ext.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/platformUrl.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/layer/layer.js"></script>
