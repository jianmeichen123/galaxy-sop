<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<% 
	String path = request.getContextPath(); 
    java.util.Date date=new java.util.Date();
    System.out.println(date);
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>消息提醒</title>


<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css"  type="text/css">
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
   <!-- bootstrap-table -->
<link rel="stylesheet" href="/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!--  <script src="<%=path %>/js/soptask.js" type="text/javascript"></script>-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

</head>

<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin">
    	<h2>消息提醒</h2>
        <!--页眉-->
        <div class="top clearfix" id="custom-toolbar">
        	<!--搜索-->
          <div class="searchbox clearfix">
            <input type="hidden"  id="tipslink_val" name="module" value="1" class="on"/>
            <input  name="keyword" type="text" placeholder="请输入项目名" class="txt"/>
            <a href="javascript:;" class="bluebtn ico cx"  action="querySearch">查询</a>

          </div>
        		<select name="type">
	        		<option value="">全部</option>
	        		<option value="1">项目</option>
	        		<option value="2">任务</option>
        		</select>
            <!--tips连接-->
        	<ul class="tipslink">

                <li><a href="javascript:;" query-by="module" query-val="1">广播消息<span><!-- (10) --></span></a></li>
                <li><a href="javascript:;"  query-by="module" query-val="2">与我有关 <span><!-- (4) --></span></a></li>
          </ul>
        </div>
        <!--表格内容-->
						<table width="100%" cellspacing="0" cellpadding="0" 
						 id="data-table" data-url="<%=request.getContextPath() %>/galaxy/operationMessage/queryList"  data-page-list="[1, 10, 30]"
						 data-toolbar="#custom-toolbar"
				         >
						   <thead>
						    <tr>
					        <th data-field="department" data-align="center" class="data-input">投资线/部门</th>
					        <th data-field="role" data-align="center"  class="data-input">角色</th>
					        <th data-field="type" data-align="center" class="col-md-1 status ">消息类型</th>
					        <th data-field="projectName" data-align="center" >项目名称</th>
					        <th data-field="operator" data-align="center" >操作人</th>
					        <th data-field="content" data-align="center" class="col-md-2" >消息内容</th>
					        <th data-field="module" data-align="center" class="col-md-2" >模块</th>
   						 	</tr>	
   						 	</thead>
					</table>
	

    </div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=request.getContextPath() %>/js/soptask.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure_ext.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>	
<!-- bootstrap-table -->
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=request.getContextPath() %>/js/init.js"></script>	

<script type="text/javascript">
	$(function(){
		createMenus(2);
		
	});
</script>
</html>
