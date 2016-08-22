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
<title>项目日志</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/sopinfo.js"></script>
</head>



<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
 	<div class="ritmin">
 	
    <jsp:include page="sopcommon.jsp" flush="true"></jsp:include>
        
        
        <div class="new_left">
        	<div class="tabtable assessment label_static">
          	<!-- tab标签 -->
            <jsp:include page="tab_header.jsp?index=6" flush="true"></jsp:include>

            
            <!-- 操作日志
            <div data-tab="con" > 
               -->
            	<div class="tabtable_con">
            		<c:if test="${aclViewProject==true or isThyy}">
					<div id="custom-toolbar" class='none'>
						<input type="hidden" id="projectId" name="projectId" value="">   <!-- 项目id -->
					</div>
					
					<table id="project_info_log" class="commonsize"
						data-url="<%=path%>/galaxy/operatlog/query"
						data-page-list="[10,20,30]" data-toolbar="#custom-toolbar">
						<thead>
							<tr>
								<th data-field="createdTime" data-align="left" data-formatter="longTimeFormat">时间</th>
								<th data-field="uname" data-align="left" >操作者</th>
								<th data-field="operationType" data-align="left">动作</th>
								<th data-field="operationContent" data-align="left">对象</th>
								<th data-field="projectName" data-align="left" data-formatter="projectNameFormatter">项目</th>
								<th data-field="reason" data-align="left" data-formatter="reason">原因</th>
								<th data-field="sopstage" data-align="left" >业务</th>
							</tr>
						</thead>
					</table>
					</c:if>	
                </div>                 
           <!--  </div>
           tab end-->
            
          </div>
        </div>
        
        <!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>
       
    </div>
 
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>


<!-- 分页二css+四js -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>


<script>
	var proinfo = '${proinfo}';
	//proinfo = JSON.parse(proinfo);
	var proid = '${pid}';
	var pname = '${pname}';
	var selectRow = null;
	function projectNameFormatter(value,row,index){
		var str=row.projectName;
		if(str.length>12){
			subStr = str.substring(0,12);
			var options = "<span title='"+str+"'>"+subStr+"</span>";
			return options;
		}
		else{
			var options = "<span title='"+str+"'>"+str+"</span>";
			return options;
		}
	}

	

$(function(){
	createMenus(5);
	
	$("#projectId").val(proid);
	
	$('#project_info_log').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
		uniqueId: "id", 
		idField : "id",
		clickToSelect: true,
        search: false,
	});

	
	
});	
	
function reason(value){
	if(value == '' || value == null || value == 'null' || typeof(value) == "undefined"){
		return "--";
	}
	else{
		var resetUrl = "<a  href='javascript:void(0);' title='"+value+"'><div class='width_1'>"+value+"</div></a>";
		return   resetUrl;
	}
}
	
</script>
</html>


