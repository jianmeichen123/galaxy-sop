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
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>

<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>全部创意</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/idea/add" class="pubbtn bluebtn ico c4">添加创意</a>
            </div>
        </div>
        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="custom-toolbar">
			<div class="bottom searchall clearfix search_adjust">
				<dl class="fmdl fmdll clearfix">
					<dt>关键字：</dt>
					<dd>
						<input type="text" class="txt" name="keyword" placeholder="创意名称或编码" />
					</dd>
				</dl>
				<dl class="fmdl fml fmdll clearfix">
	              <dt>所属事业线：</dt>
	              <dd>
	                <select name="departmentId">
	                  <option value="">全部</option>
	                </select>
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	              <dt>进度：</dt>
	              <dd>
	                <select name="ideaProgress">
	                  <option value="">全部</option>
	                  <option value="ideaProgress:1">待认领</option>
	                  <option value="ideaProgress:2">调研</option>
	                  <option value="ideaProgress:3">创建立项会</option>
	                  <option value="ideaProgress:4">搁置</option>
	                  <option value="ideaProgress:5">创建项目</option>
	                  <option value="ideaProgress:6">立项会</option>
	                  <option value="ideaProgress:7">投决会</option>
	                </select>
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	            	<dt>提出人：</dt>
	            	<dd>
	            	<input type="text" class="txt" name="createdUname" size="8"/>
	            	</dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	            	<dt>提出时间：</dt>
	            	<dd>
	            	<input type="text" class="datepicker txt time" readonly id="createdDate" name="createdDate"  style="height:23px;"/>
	            	</dd>
	            	<dd>
						<button type="submit" class="bluebtn ico cx" action="querySearch">搜索</button>
				    </dd>
	            </dl>
				
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<table id="data-table" data-url="idea/search" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="ideaCode" data-align="center" class="data-input">创意编码</th>
			        	<th data-field="ideaName" data-align="center" class="data-input">创意名称</th>
			        	<th data-field="departmentDesc" data-align="center" class="data-input">所属事业线</th>
			        	<th data-field="createdUname" data-align="center" class="data-input">提出人</th>
			        	<th data-field="createdTime" data-align="center" class="data-input" data-formatter="dateFormatter">提出时间</th>
			        	<th data-field="updatedTime" data-align="center" class="data-input" data-formatter="dateFormatter">最后编辑时间</th>
			        	<th data-field="ideaProgress" data-align="center" class="data-input" data-formatter="progressFormatter">进度</th>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=request.getContextPath() %>/js/operationMessage.js" type="text/javascript"></script>
<script id="a" src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/fx.upload.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/my_ext.js"></script>
<script src="<%=path %>/js/my.js"></script>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>

<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>

<script src="<%=path %>/js/idea-common.js"></script>
<script type="text/javascript">
	createMenus(21);
	getDepartment($("#custom-toolbar [name='departmentId']"));
	
	$("#custom-toolbar [name='ideaProgress']").change(function(){
		var val = $(this).val();
		if(val == 'ideaProgress:6' || val == 'ideaProgress:7')
		{
			$(this).val("");
			$.getHtml({
				url:platformUrl.ideaProjectList,
				okback:function(){
					
				}
			});
		}
	});
</script>

</html>
