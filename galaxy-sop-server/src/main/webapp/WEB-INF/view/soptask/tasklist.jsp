<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<% 
	String path = request.getContextPath(); 
    java.util.Date date=new java.util.Date();
    String url=(String)request.getAttribute("flagUrl");
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>


<%-- <link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css"  type="text/css"> --%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
   <!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
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
    	<h2>待办任务</h2>
        <!--页眉-->
        <div class="top clearfix" id="custom-toolbar">
        	<!--搜索-->
          <div class="searchbox clearfix">
            <input type="hidden"  id="tipslink_val"/>
            <input type="hidden"  id="flagUrl" name="flagUrl" value="${flagUrl}"/>
            <input  name="keyword" type="text" placeholder="请输入项目名称或投资经理姓名" class="txt"/>
            <a href="javascript:;" class="bluebtn ico cx"  action="querySearch">搜索</a>

          </div>
            <!--tips连接-->
        	<ul class="tipslink">
            	<li class="on"><a href="javascript:;" id="all" query-by="all" query-val="all">全部<span><!-- (14) --></span></a></li>
                <li><a href="javascript:;" id="urgent" query-by="taskOrder" query-val="1">紧急<span><!-- (2) --></span></a></li>
                <li><a href="javascript:;" id="normal" query-by="taskOrder" query-val="0" >正常<span><!-- (5) --></span></a></li>
                <%if("".equals(url)){ %>
                      <li><a href="javascript:;" id="claim" query-by="taskStatus" query-val="taskStatus:1">待认领<span><!-- (10) --></span></a></li>
                <%} %>
                <li><a href="javascript:;" id="todeal" query-by="taskStatus" query-val="taskStatus:2">待完工<span><!-- (4) --></span></a></li>
                <li><a href="javascript:;"id="finish" query-by="taskStatus" query-val="taskStatus:3">已完成</a></li>   
          </ul>
        </div>
        <!--表格内容-->
						<table width="100%" cellspacing="0" cellpadding="0" 
						 id="data-table" data-url="<%=request.getContextPath() %>/galaxy/soptask/taskListByRole"  data-page-list="[10, 20, 30]" data-show-refresh="true" 
				         data-toolbar="#custom-toolbar" >
						   <thead>
						    <tr>
						        <th data-field="orderRemark"  class="data-input">优先级</th>
						        <th data-field="hours"  class="data-input">剩余时间(h)</th>
						        <th data-field="taskDeadlineformat"  class="col-md-1 status ">提交日期</th>
						        <th data-field="taskType"  >任务类型</th>
						        <th data-field="taskName"  width="200px" >任务名称</th>
						        <th data-field="taskStatus"  class="col-md-2" >任务状态</th>
						        <th data-field="projectName"  class="col-md-2" data-formatter="projectNameFormatter">所属项目</th>
						        <th data-field="createUname"  class="col-md-2" >投资经理</th>
								<th  class="col-md-2" data-field="caozuohtml" >操作</th>
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
	var aa=$("a[resource-mark]")
	  if(isContainResourceByMark("interView")){
	    	$('div[resource-mark="interView"]').css("display","block");
	    }
	
</script>
</html>
