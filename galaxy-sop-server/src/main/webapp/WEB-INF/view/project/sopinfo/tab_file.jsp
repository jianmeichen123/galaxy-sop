<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
	Long projectId = (Long)request.getAttribute("projectId");
%>
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isThyy" value="${fx:hasRole(20)}" scope="request"/>  
		  <!-- 档案库信息 -->
          <div id="file_repository">
          	<c:if test="${aclViewProject==true or isThyy}">
          	<form id = 'file_repository_search_form'>
          	<div class="member document">
          	 <c:if test="${fx:isCreatedByUser('project',projectId) }">
                  <div class="top clearfix">
                      <!--按钮-->
                      <div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" class="pubbtn bluebtn email_btn" id="file-show-mail-btn">发邮件给</a>
				</div>
                  </div>
               </c:if>
                  <!-- 搜索条件 -->
               <div class="min_document clearfix" >
               <!-- 查询选项 -->
                 <div class="searchbox_document">
                   <div class="show_more" style="z-index:9;">
                     <a href="#" class="blue open ico1 f4" data-btn='show'>展开</a>
                     <a href="#" class="blue searchbox_hidden hide ico1 f3" data-btn='hide'>收起</a>
                   </div>
                   <div class="default">
                      <dl class="fmdl">
                        <dt>文件来源：</dt>
                        <dd><label for=""><input type="radio" name="search_fileSource" value="all" checked/>不限</label></dd>
                        <dd><label for=""><input type="radio" name="search_fileSource" value="1">内部</label></dd>
                        <dd><label for=""><input type="radio" name="search_fileSource" value="2">外部</label></dd>
                      </dl>                  
                      <dl class="fmdl">
                        <dt>业务分类：</dt>
                        <dd>
                          <select name="search_fileWorktype" id="search_file_worktype">
                            <option value = "all">全部</option>  
                          </select>
                        </dd>
                      </dl>                  
                   </div>
                   <div class="searchbox_hidden" data-btn='box'>                 
                     <dl class="fmdl" id="search_file_status">
                       <dt>档案状态：</dt>
                     </dl>                  
                     <dl class="fmdl" id="search_file_type">
                       <dt>存储类型：</dt>
                     </dl>                  
                     <dl class="fmdl clearfix">
                        <dt>更新时间：</dt>
                        <dd>
							<input type="text" class="datepicker txt time" name="file_startDate" value="2016-01-01" /> 
							<span>至</span>
							<input type="text" class="datepicker txt time" name="file_endDate" value="2016-01-01" />
					   </dd>
                       <dd>
						 <a href="javascript:;" id="file_repository_btn" class="pubbtn bluebtn">查询</a>
					   </dd>
                     </dl>
                   </div>
                         
                 </div>
               </div>
   
                          <!--表格内容-->
                  <table width="100%" id="file_repository_table" cellspacing="0" cellpadding="0" class="commonsize"></table>  
      	</div>
      	</form>  
      	</c:if>
      </div>

<jsp:include page="../../common/uploadwin.jsp" flush="true"></jsp:include>
<jsp:include page="../../sopFile/projectDialog.jsp" flush="true"></jsp:include>
<jsp:include page="/galaxy/sopFile/showMailDialog" flush="true"></jsp:include>


<!-- 分页二css+四js -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- file -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>

<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>

<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script>
// 	var prograss = '${prograss}';
// 	var isTransfering = "${fx:isTransfering(projectId) }";
	function getPrograss(){
		return "${prograss}";
	}
	function getIsTransfering(){
		return "${fx:isTransfering(projectId) }";
	}
  
</script>

<script src="<%=path %>/js/teamSheetNew.js"></script>
<script src="<%=path %>/js/filerepository.js"></script>
