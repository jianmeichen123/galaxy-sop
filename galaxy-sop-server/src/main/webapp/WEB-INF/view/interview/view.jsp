<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap-table/bootstrap-table.css"  type="text/css">

<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

<!-- 富文本编辑器 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>


</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>访谈跟进</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/project/progress/interViewAdd" data-btn="interview" class="pubbtn lpubbtn bluebtn ico c4">添加访谈记录</a>
            </div>
        </div>
       
          <div class="tab-pane active" id="view">		
			<div id="custom-toolbar">
			    <div class="form-inline" role="form">
			        <div class="form-group">
			            <div class="input-group">
			                <input class="form-control" type="text" placeholder="项目名称或编号" id = "proNameCode" name="proNameCode">
			            </div>
			        </div>
			        <div class="form-group">
			            <div class="input-group">
			                <input class="form-control" type="date" placeholder="访谈日期(开始)" id = "startTime" name="startTime">
			            </div>
			        	<div class="input-group">
			                <input class="form-control" type="date" placeholder="访谈日期(结束)" id = "endTime"  name="endTime">
			            </div>
			        </div>
			        <button type="submit" class="btn btn-default" name="querySearch">搜索</button>
			    </div>
			</div>
			<table  id="data-table" data-url="/galaxy/project/progress/queryInterview" data-method="post" 
	          		data-side-pagination="server" data-pagination="true" 
	          		data-toolbar="#custom-toolbar" data-page-list="[1,2,4,8,50]"
					data-id-field="lottoId" data-show-refresh="true">
				<thead>
					<tr>
						<th data-field="viewDateStr" data-align="center">访谈日期</th>
						<th data-field="proName" data-align="center">所属项目</th>  
						<th data-field="viewNotes" data-align="center">访谈日志</th>
						<th data-field="viewTarget" data-align="center">访谈对象</th>
						<th data-field="fname" data-align="center" data-formatter="fileFormat">会议录音</th>
					</tr>
				</thead>
			</table>

           </div>
           
          
          
    </div>
</div>


<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>

<!-- bootstrap-table  -->
<script src="<%=path %>/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<script src="<%=path %>/js/interview.js" type="text/javascript"></script>


<script type="text/javascript">
$(function(){
	
	/* //height : 400, queryParamsType : 'pageNo',
	$("#interVierTable").bootstrapTable({
		url : platformUrl.selectViewPage,
		method : 'post',
		queryParams:getQueryCondition,
		queryParamsType: 'size|page',
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 2,
		pageList : [2,4, 8],
		showColumns : true,
		showRefresh : true,
		minimumCountColumns : 2,
		clickToSelect : true,
	}); */
	
});



</script>
</html>

