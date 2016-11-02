<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 文件上传 -->
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- 上传执行文件 -->
<script type='text/javascript' src='<%=request.getContextPath() %>/js/teamSheetNew.js'></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/addPlanbusiness.js'></script>
<!-- step2 end -->