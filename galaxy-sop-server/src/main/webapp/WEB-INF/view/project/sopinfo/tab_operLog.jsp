<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isThyy" value="${fx:hasRole(20)}" scope="request"/>
<% 
	String path = request.getContextPath(); 
%>
          

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


