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
	              	<input type="checkbox" value="0">待排期
	              	<input type="checkbox" value="1">已排期
	              	<input type="checkbox" value="2">已通过
	              	<input type="checkbox" value="3">已否决
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
				    	<th data-align="center" class="data-input" data-formatter="indexFormatter">#</th>
				    	<th data-field="projectCode" data-align="center" class="data-input">项目编码</th>
				    	<th data-field="projectName" data-align="center" class="data-input">项目名称</th>
				    	<th data-align="center" class="data-input" data-formatter="statusFormatter">排期状态</th>
				    	<th data-field="lastTime" data-align="center" class="data-input">上次过会时间</th>
				    	<th data-field="projectCareerline" data-align="center" class="data-input">投资事业线</th>
				    	<th data-field="createUname" data-align="center" class="data-input">投资经理</th>
				    	<th data-field="projectId" data-align="center" class="data-input">过会率</th>
				    	<th data-field="applyTime" data-align="center" class="data-input">申请时间</th>
				    	<th data-align="center" class="data-input" data-formatter="dataFormatter">排期时间</th>
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
	function indexFormatter(value, row, index){
		return index + 1;
	}
	function statusFormatter(value, row, index){
		var status = "待排期";
		if(value == 1){
			status = "已排期";
		}else if(value == 2){
			status = "已通过";
		}else if(value == 3){
			status = "已否决";
		}
		return status;
	}
	function dataFormatter(value, row, index){
		return '<input type="text" class="datepicker-text time time-input" name="" id="" readonly value=""/>';
	}
	sendGetRequest(platformUrl.getDepartMentDict+"/department",null,function(data){
		var $optionArray = [];
		var ii = 0;
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
