<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>

<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


	<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin prj_all" id="custom-toolbar">
    	<div class="new_tit_a"><a href="#" onclick="backIndex()">工作桌面</a>>项目健康度</div>
    	<input type='hidden' name="">
      	<div class="tab-pane active ctlist" id="view">	
			<table id="project-table" data-url="project/search" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
			        	<th data-field="projectName"  class="data-input" data-width="16%">项目名称</th>
			        	<th data-field="projectCareerline"  class="data-input" data-width="9%">投资事业线</th>
			        	<th data-field="healthStateDesc"    data-width="6%">健康状态<span></span></th>
			        	<th data-field="rematk"   class="data-input" data-width="17%">风险点</th>
			        	<th data-field="userName"   class="data-input sort" data-sortable="true" data-width="8%">分析人<span></span></th>
			        	<th data-field="createdTime" data-formatter="createdFormat""  class="data-input sort" data-sortable="true" data-width="5%">分析日期</th>
 				</thead>
			</table>
           </div>
    </div>
</div>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<script type="text/javascript">
	  createMenus(5);
	 function projectInfo(value,row,index){
		    var id=row.id;
			var str=row.projectName;
			if(str.length>10){
				subStr = str.substring(0,10);
				var options = "<a href='#' class='blue' data-btn='myproject' onclick='proInfo(" + id + ")' title='"+str+"'>"+subStr+"</a>";
				return options;
			}
			else{
				var options = "<a href='#' class='blue' data-btn='myproject' onclick='proInfo(" + id + ")' title='"+str+"'>"+str+"</a>";
				return options;
			}
		}
</script>
</html>
