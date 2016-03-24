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

<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<!-- 富文本编辑器 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<!-- common -->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

<style>
.tab-pane table th:nth-child(3) {
    width: 55%;
}
</style>
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
            <div class="btnbox_f btnbox_f1 clearfix">   <!-- onclick="umInit()" -->
                <a href="<%=path %>/galaxy/project/progress/interViewAdd" data-btn="interview" class="pubbtn lpubbtn bluebtn ico c4"  >添加访谈记录</a>
            </div>
        </div>
       
       
        <!-- 搜索条件 -->
        <div class="min_document clearfix"  id="custom-toolbar">
          <div class="bottom searchall clearfix">
          
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>访谈日期：</dt>
              <dd>
              	<!-- <input class="form-control" type="date" id = "startTime" name="startTime"  /> -->
                <input type="text" class="datepicker txt time" readonly id="startTime" name="startTime" style="height:23px;"/>
                <span>至</span>
                <input type="text" class="datepicker txt time" readonly id="endTime"  name="endTime" style="height:23px;"/>
              </dd>
            </dl>
            
            <dl class="fmdl fmdll clearfix">
              <dt></dt>
              <dd>
                <input type="text" class="txt s_txt" placeholder="请输入项目名称或编号" id = "proNameCode" name="proNameCode" />
              </dd>
              
              <dd>
               <a href="javascript:;" class="bluebtn ico cx" action="querySearch">查询</a>
              </dd>

            </dl>
          </div>
        </div>
        
        
          <div class="tab-pane active" id="view">		
			<table  id="data-table" data-url="<%=path %>/galaxy/project/progress/queryInterview" data-method="post" 
	          		data-side-pagination="server" data-pagination="true" 
	          		data-toolbar="#custom-toolbar" data-page-list="[5,10,20]"
					data-id-field="lottoId" data-show-refresh="true">
				<colgroup >
					<col style="width:200px;"> <!-- 名称 -->
					<col style="width:200px;"> <!-- 名称 -->
					<col style="width:500px;">  <!-- 状态 -->
				</colgroup>
				<thead>
					<tr>
						<th data-align="center" data-formatter="intervierInfoFormat">访谈概况</th>
						<!-- <th data-field="ftgk" data-align="center">访谈概况</th> -->
						<th data-field="proName" data-align="center">所属项目</th>  
						<th data-field="viewNotes" data-align="center" data-formatter="sublengthFormat">访谈日志</th>
					</tr>
				</thead>
			</table>

           </div>
    </div>
</div>


<!-- 分页二css+四js -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- file -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>

<!-- clude -->
<script src="<%=path %>/js/interview.js" type="text/javascript"></script>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>

<script type="text/javascript">
$(function(){
	createMenus(6);
	
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		pageList : [5, 10, 20 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
	});
	
});
</script>
</body>

</html>

