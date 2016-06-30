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
            <ul class="tablink tablinks">
                 <li><a href="javascript:;" onclick="showTabs('${pid}',0)">基本信息</a></li>
                 <c:choose>
	            <c:when test="${aclViewProject==true}">
                <li><a href="javascript:;" onclick="showTabs('${pid}',1)">团队成员</a></li>
                <li><a href="javascript:;" onclick="showTabs('${pid}',2)">股权结构</a></li>
                <li><a href="javascript:;" onclick="showTabs('${pid}',3)">访谈记录</a></li>
                <li><a href="javascript:;" onclick="showTabs('${pid}',4)">会议纪要</a></li>
                <li><a href="javascript:;" onclick="showTabs(${pid},5)">项目文档</a></li>
                <li class="on"><a href="javascript:;" onclick="showTabs(${pid},6)">操作日志</a></li>
                </c:when>
                <c:otherwise>
                <li class="no"><a href="javascript:;">团队成员</a></li>
                <li class="no"><a href="javascript:;">股权结构</a></li>
                <li class="no"><a href="javascript:;">访谈记录</a></li>
                <li class="no"><a href="javascript:;">会议纪要</a></li>
				<li class="no"><a href="javascript:;">项目文档</a></li>
                <li class="no"><a href="javascript:;">操作日志</a></li> 
                </c:otherwise>
             	</c:choose>
            </ul>

            
            <!-- 操作日志
            <div data-tab="con" > 
               -->
            	<div class="tabtable_con">
            		<c:if test="${aclViewProject==true}">
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
	
	
	
</script>
</html>


