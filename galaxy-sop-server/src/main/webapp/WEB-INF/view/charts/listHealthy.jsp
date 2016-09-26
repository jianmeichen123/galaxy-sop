<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
    String flag=(String)request.getAttribute("flagUrl");
    String title="";
    if(flag.equals("healthHighNum")){
    	title="高于预期";
    }else if(flag.equals("healthGoodNum")){
    	title="健康";
    }else if(flag.equals("healthWarnNum")){
    	title="健康预警";
    }else{
    	title="全部";
    }
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
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>

<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" /

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin prj_all" >
    	<div class="new_tit_a"><a href="#" onclick="backIndex()">工作桌面</a>>项目健康度</div>
    		
    	<div class="new_tit_b">
        	<span class="size18" id="project_name_t"><%=title %></span>
        	<span class="b_span"> 
	        	<a href="#" onclick="back();">返回></a>
			</span>
        </div>
    	 	<div class="min_document clearfix" id="health-custom-toolbar" style="display:none;" >
				<div class="bottom searchall clearfix">
					<input type="hidden" name="flagUrl" value="${ flagUrl}"> 
				</div>
			</div>
     	<div class="tab-pane active ctlist" id="view">	
			<table id="chart_health_table" data-url="<%=path %>/galaxy/health/getHealthChartGrid" 
				data-page-list="[10, 20, 30]" data-toolbar="#health-custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
			        	<th data-field="projectName"  data-formatter="projectNameInfo" class="data-input" data-width="16%">项目名称</th>
			        	<th data-field="projectCareerline"  class="data-input" data-width="9%">投资事业线</th>
			        	<th data-field="healthStateDesc"  data-formatter="healthStateFormat"    data-width="6%">健康状态<span></span></th>
			        	<th data-field="rematk"   data-formatter="rematkFormat" class="data-input" data-width="17%">风险点</th>
			        	<th data-field="userName"   class="data-input sort" data-sortable="true" data-width="8%">分析人<span></span></th>
			        	<th data-field="createdTime" data-formatter="longTime_Format"  data-width="5%">分析日期</th>
			        	</tr>
 				</thead>
			</table>
           </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>

<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>


<script type="text/javascript">
    $(function(){
    	createMenus(1);
    	init_bootstrapTable('chart_health_table',5);
    	
    })
	 function rematkFormat(value,row,index){
			var str=row.rematk;
			var options="";
			if(str.length>10){
				subStr = str.substring(0,10);
				var options = "<a href='#'  title='"+str+"'>"+subStr+"</a>";
			}else{
				var options = "<a href='#' title='"+str+"'>"+str+"</a>";
			}
			return options;
		
	 }
	 
	 function healthStateFormat(value,row,index){
		 var healthState=row.healthState;
		 var healthStates={
				 "1":"高于预期",
				 "2":"正常",
				 "3":"健康预警"
		 }
		 return  healthStates[healthState];
	 }
	 
	 function projectNameInfo(value,row,index){
		 
		 var id=row.projectId;
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
	 function proInfo(id){
			//项目详情页返回地址
			setCookie("project_detail_back_path", Constants.sopEndpointURL + 'galaxy/mpl',6,'/');
			//返回附带参数功能代码
			var options = $("#project-table").bootstrapTable('getOptions');
			var tempPageSize = options.pageSize ? options.pageSize : 10;
			var tempPageNum = options.pageNumber ? options.pageNumber : 1;
			var projectType = $("select[name='projectType']").val();
			var financeStatus = $("select[name='financeStatus']").val();
			var projectProgress = $("select[name='projectProgress']").val();
			var projectStatus = $("select[name='projectStatus']").val();
			var projectDepartid = $("select[name='projectDepartid']").val();
			var createUid = $("select[name='createUid']").val();
			var nameCodeLike = $("input[name='nameCodeLike']").val();
			var projectPerson = $("input[name='projectPerson']").val();
			var faFlag = $("input[name='faFlag']:checked").val();
			
			var formdata = {
					_paramKey : 'projectList',
					_url : Constants.sopEndpointURL + "/galaxy/project/detail/" + id,
					_path : "/",
					_param : {
						pageNum : tempPageNum,
		        		pageSize : tempPageSize,
		        		projectType : projectType,
		        		financeStatus : financeStatus,
		        		projectProgress : projectProgress,
		        		projectStatus : projectStatus,
		        		projectDepartid : projectDepartid,
		        		createUid : createUid,
		        		nameCodeLike : nameCodeLike,
		        		projectPerson:projectPerson,
		        		faFlag:faFlag
					}
			}
			var href_url=window.location;
			setCookie("href_url", href_url,24,'/');
			cookieOperator.forwardPushCookie(formdata);
		}
	 function back(){
			var href_url=getCookieValue("href_url");
			if(href_url){
				deleteCookie("href_url","/");
				window.location=href_url;
			}/* else
				window.history.go(-1); */
				
		}
	   function backIndex(){
	    	forwardWithHeader("<%= path%>/galaxy/role/index");
	    }
	 
</script>
</html>
