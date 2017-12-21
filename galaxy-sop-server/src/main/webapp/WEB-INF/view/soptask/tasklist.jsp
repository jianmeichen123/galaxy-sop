<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
<style>
.fixed-table-container{padding-left:0px !important;padding-right:10px !important;border-radius:0px !important;}
.fixed-table-body{border-radius:0px !important;}
</style>
<%-- <link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css"  type="text/css"> --%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
   <!--bootstrap-table-->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!--  <script src="<%=path %>/js/soptask.js" type="text/javascript"></script>-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

</head>

<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix task-pagebox">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	 <!--页眉-->
        <div class="top clearfix task-top">
        <h2 class='taskdetail-top-title'>${title }</h2>
            <!--tips连接-->
        	<ul class="tipslink task_tipslink">
            	<li class="on task-tips-li"><a href="javascript:;" id="all" data-query-url="<%=request.getContextPath() %>/galaxy/soptask/list/all">全部<span><!-- (14) --></span></a></li>
                <c:if test="${fx:hasPremission('task_list_claim') }">	
                <li class='task-tips-li'><a href="javascript:;" id="claim" data-query-url="<%=request.getContextPath() %>/galaxy/soptask/list/unclaimed">待认领<span><!-- (10) --></span></a></li>
                </c:if>
                <c:if test="${fx:hasPremission('task_list_dispose') }">	
                <li class='task-tips-li'><a href="javascript:;" id="todeal" data-query-url="<%=request.getContextPath() %>/galaxy/soptask/list/unfinished">待完工<span><!-- (4) --></span></a></li>
                </c:if>
                <c:if test="${fx:hasPremission('task_list_complete') }">	
                <li class='task-tips-li'><a href="javascript:;"id="finish" data-query-url="<%=request.getContextPath() %>/galaxy/soptask/list/finished">已完成</a></li>
                </c:if>
                <c:if test="${fx:hasPremission('task_list_dep') }">	
                <li class='bottom_none'>|</li> 
                <li class='task-tips-li'><a href="javascript:;" id="dep-unfinished" data-query-url="<%=request.getContextPath() %>/galaxy/soptask/list/depUnfinished">部门待完工<span><!-- (2) --></span></a></li>
                </c:if>
           </ul>
          <div class="searchbox clearfix task-searchbox"  id="custom-toolbar">
            <input type="hidden"  id="tipslink_val"/>
            <input type="hidden"  id="flagUrl" name="flagUrl" value="${flagUrl}"/>
            <input  name="keyword" type="text" placeholder="请输入项目名称或发起人名称" class="txt task_input"/>
             <span class='more-task fr' style='display:none'>指派任务</span>
            <a href="javascript:;" class="bluebtn ico cx task-cx bluebtn_new"  action="querySearch" id="search-task-btn">搜索</a>
	          <ul class='task-toggle'>
	          	<li data-code='transfer-task'>移交任务</li>
	          	<li data-code='abandon-task'>放弃任务</li>
	          </ul>
      	  </div>
          
        </div>
    <!--右中部内容-->
 	<div class="ritmin task-ritmin">
 	<div>
        <!--表格内容-->
			<table class='no-radius table_new_style' width="100%"  cellspacing="0" cellpadding="0" 
			 id="task-table" data-url=""  data-page-list="[10, 20, 30]" data-show-refresh="true" 
	         data-toolbar="#custom-toolbar" >
			   <thead>
			    <tr>
			        <th data-field="taskName"  data-width="20%" >任务名称</th>
			        <th data-field="taskType"  data-align='center'>任务类型</th>
			        <th data-field="projectName"  class="col-md-2" data-formatter="projectNameFormatter" data-width="20%">所属项目</th>
			        <th data-field="taskDeadlineformat"  class="col-md-1 status " data-align='center'>发起时间</th>
			        <th data-field="createUname"  class="col-md-2" data-align='center'>发起人</th>
			        <th data-field="taskStatusDesc"  class="col-md-2" data-align='center'>任务状态</th>
					<th  class="col-md-2" data-field="caozuohtml" data-align='center'>操作</th>
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
	
//计算距离的左边距
detailHeaderWidth();
function detailHeaderWidth(){
	  var  w_lft=$(".lft").width();
	  	$('.task-top').css({'margin-left':w_lft});
}
$(window).resize(function(){
	detailHeaderWidth();
})	
})
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
    var flag="${flagUrl}";
    var num=0;
    if(flag=="jl"){
    	num=10;
    }
    if(flag=="pz"){
    	num=11;
    }
    if(flag=="gq"){
    	num=12;
    }
    if(flag=="jz"){
    	num=9;
    }
	$(function(){
		var flag="${flagUrl}";
		var num=2;
		if(flag=="jz"){
			num=9;
		}
		if(flag=="jl"){
			num=10;		
				}
		if(flag=="gq"){
			num=12;
		}
		if(flag=="pz"){
			num=11;
		}
		createMenus(num);
		
	});
</script>
</html>
