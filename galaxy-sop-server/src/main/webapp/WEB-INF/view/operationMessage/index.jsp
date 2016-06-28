<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
	String path = request.getContextPath(); 
    java.util.Date date=new java.util.Date();
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">

<title>繁星</title>


<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

</head>

<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
     <!--右中部内容-->
 	<div class="ritmin message">
    	<div class="new_tit_a"><a href="#" onclick="backIndex()">工作桌面</a>>消息提醒</div>
    	<div class="new_tit_b">
	        <span class="new_color size18">消息提醒</span>
	      </div>
	      
      <!-- 消息提醒内容 -->
			<table id="data-table" data-url="operationMessageQueryList"  data-page-list="[10,20,30]" data-show-refresh="true">
				<thead>
				    <tr>
			        	<th data-field="createdTime" data-align="left" data-class="message_t" data-formatter="longTimeFormat_Chines" >日期时间</th>
			        	<th data-field="content" data-align="left"  data-class="message_n" data-formatter="projectNameLineFormat">消息</th>
 					</tr>	
 				</thead>
			</table>

    </div>

</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<!-- bootstrap-table  -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=request.getContextPath() %>/js/init.js"></script>	

<script type="text/javascript">
	$(function(){
		createMenus(3);
	});
	
	function backIndex(){
		 var url=Constants.sopEndpointURL+"/galaxy/redirect";
		 forwardWithHeader(url);
	}
	
	
</script>
</html>
