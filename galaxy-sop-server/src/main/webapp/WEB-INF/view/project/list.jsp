<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星SOP-添加项目</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!-- bootstrap-table -->
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!-- 分页二css+三js -->
<link rel="stylesheet" href="<%=path %>/css/bootstrap.min-3.3.5.css"  type="text/css">
<link rel="stylesheet" href="<%=path %>/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>我的项目</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="添加项目.html" class="pubbtn bluebtn ico c4">添加项目</a>
            </div>
        </div>
        <!-- 搜索条件 -->
		<div class="min_document clearfix">
			<div class="bottom searchall clearfix" id="custom-toolbar">
				<dl class="fmdl fml fmdll clearfix">
	              <dt>项目类别：</dt>
	              <dd>
	                <select>
	                  <option>全部</option>
	                </select>
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	              <dt>项目进度：</dt>
	              <dd>
	                <select>
	                  <option>全部</option>
	                </select>
	              </dd>
	            </dl>
				<dl class="fmdl fmdll clearfix">
					<dt></dt>
		              <dd>
		                <input type="text" class="txt" placeholder="请输入项目名称或投资经理名称" />
		              </dd>
					<dd>
						<button type="submit" class="bluebtn ico cx" name="querySearch">搜索</button>
					</dd>
				</dl>
			</div>
		</div>
		<div class="tab-pane active" id="view">		
			<table id="data-table" data-url="project/spl" data-height="555" 
				data-method="post" data-show-refresh="true" 
				data-side-pagination="server" data-pagination="true" 
				data-page-list="[2, 15, 50]" data-search="false">
				<thead>
				    <tr>
			        	<th data-field="projectName" data-align="center" class="data-input">项目名称</th>
			        	<th data-field="projectCode" data-align="center" class="data-input">项目状态</th>
			        	<th data-align="center" class="col-md-2" data-formatter="editor">操作</th>
 					</tr>	
 				</thead>
			</table>
       </div>
    </div>
</div>
<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.js"></script>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
	$(function(){
		createMenus(5);
	});
	function editor(value, row, index){
		var id=row.id;
		return "<a data-btn='myproject' href='<%=path %>/galaxy/ips'>查看</a>";
	}
</script>
<script src="<%=path %>/js/init.js"></script>
</html>

