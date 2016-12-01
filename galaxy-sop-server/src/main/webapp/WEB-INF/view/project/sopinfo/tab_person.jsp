<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="aclViewProject"
	value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }"
	scope="request" />
	
<c:set var="isEditable"
	value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}"
	scope="request" />
	
<% 
	String path = request.getContextPath(); 
%>


<input type="hidden" id="pid" name="id" value="${pid}" />

<c:if test="${aclViewProject==true}">
	<div class="member">
		<c:if test="${isEditable}">
			<div class="top clearfix">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 clearfix">
					<a id="add_person_btn" href="javascript:;" onclick="toAddPerson(null,null);" class="pubbtn bluebtn ico c4 add_prj add_profile" data-name="团队成员">添加</a>
					<!--  <a href="javascript:;" class="pubbtn bluebtn edit_profile" onclick="toSureMsg();">完善简历</a> -->
				</div>
			</div>
		</c:if>
		
		<!--表格内容-->
		<div class="tab-pane active commonsize" id="view">
			<table id="tablePerson"  data-height="555" data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" >
			</table>
		</div>

	</div>
</c:if>




<script>

	var proinfo = '${proinfo}';
	var proid = '${pid}';
	var pname = '${pname}';
	var interviewSelectRow = null;
	var projectId ='${pid}';
	var flag = '${flag}';
	var isCreatedByUser = "${fx:isCreatedByUser('project',pid) }";
	var isTransfering = "${fx:isTransfering(pid) }";


$(function(){
	getTabPerson();
	if(isTransfering == 'true'){
		$("#add_person_btn").addClass('limits_gray');
	}
});	

	


	<%-- function reloadJS() {
		loadJs('<%=request.getContextPath() %>/js/axure.js'); 
	} --%>
</script>

