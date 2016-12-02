<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!-- 表单验证 -->
<%-- <script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js" type="text/javascript"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/reset.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!-- ajax -->
<script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>
<!-- bootstrap-table 
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<link rel="stylesheet" href="<%=path %>/css/bootstrap.min-v3.3.5.css"  type="text/css">-->
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-report.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>



