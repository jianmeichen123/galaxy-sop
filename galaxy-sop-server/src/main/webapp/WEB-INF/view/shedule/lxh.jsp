<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
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
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>立项会排期</h2>
    	<input type="hidden" id="project_id" value=""/>
        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="custom-toolbar">
		     <input type="hidden"  id="tipslink_val" name="proType" value="1" />
			<div class="bottom searchall clearfix search_adjust">
				<dl class="fmdl fml fmdll clearfix">
	              <dt>排期状态：</dt>
	              <dd>
	              	<input type="checkbox" value="1">待排期
	              	<input type="checkbox" value="1">已排期
	              	<input type="checkbox" value="1">已通过
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	              <dt>所属事业线：</dt>
	              <dd>
	                <select name="careline">
	                  <option value="all">全部</option>
	                </select>
	              </dd>
	            </dl>
				<dl class="fmdl fmdll clearfix">
					<dt></dt>
					<dd>
						<input type="text" class="txt" name="keyword" placeholder="项目名称或编号" />
					</dd>
					<dd>
						<button type="submit" class="bluebtn ico cx" action="querySearch">搜索</button>
					</dd>
				</dl>
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<table id="data-table" data-url="project/queryScheduling/1" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="projectId" data-align="center" class="data-input">项目ID</th>
			        	<th data-align="center" class="col-md-2" data-formatter="editor">操作</th>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>

<script type="text/javascript">
	createMenus(18);
	function editor(value, row, index){
		var id=row.id;
		var options = "<a href='#' class='blue' data-btn='myproject' onclick='info(" + id + ")'>项目流程</a>";
		if(row.projectStatus != 'meetingResult:3' && parseInt(row.createUid) == parseInt(userId)){
			options += "<a href='<%=path%>/galaxy/upp/"+id+"' class=\'blue\'>编辑项目</a>";
		}
		return options;
	}
	sendGetRequest(platformUrl.getDepartMentDict+"/department",null,function(data){
		var $optionArray = [];
		var ii = 0;
		console.log(data);
		$.each(data.entityList,function(i,current){
			if(current.type == 1){
				$optionArray[ii] = '<option value="'+current.id+'">'+current.name+'</option>';
				ii++;
			}
		});
		var $optionHtml = $($optionArray.join(''));
		$optionHtml.insertAfter($('option[value="all"]'));
	});
</script>

</html>
