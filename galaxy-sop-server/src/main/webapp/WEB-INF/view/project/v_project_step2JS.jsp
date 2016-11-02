<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- step2 start -->
<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<!-- 文件上传 -->
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- table列表 -->
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- 上传执行文件 -->
<script type='text/javascript' src='<%=request.getContextPath() %>/js/teamSheetNew.js'></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/addPlanbusiness.js'></script>
<!-- step2 end -->