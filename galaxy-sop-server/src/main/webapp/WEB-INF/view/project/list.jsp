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
<link rel="stylesheet" href="<%=path %>/bootstrap-table/bootstrap-table.css"  type="text/css">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!-- 分页一css+三js -->
<script src="<%=path %>/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>
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
                <a href="编辑项目.html" class="pubbtn bluebtn ico c5">编辑</a>
            </div>
        </div>
        <!-- 搜索条件 -->
		<div class="min_document clearfix">
			<div class="bottom searchall clearfix">
				<dl class="fmdl fml fmdll clearfix">
					<dt>账户状态：</dt>
					<dd>
						<label for=""><input type="radio" name="status">不限</label>
						<label for=""><input type="radio" id="disabled" value="1" name="status">已禁用</label>
					</dd>
				</dl>
				<dl class="fmdl fml fmdll clearfix">
					<dt>所属部门：</dt>
					<dd>
						<select id='selectDept'>
							<option value="">全部</option>
						</select>
					</dd>
				</dl>
				<dl class="fmdl fmdll clearfix">
					<dt></dt>
					<dd>
						<input type="text" class="txt" id="search_text" placeholder="请输入姓名或手机号" />
					</dd>
					<dd>
						<a href="javascript:void(0)" class="bluebtn ico cx" onclick="searchForm()">查询</a>
					</dd>
				</dl>
			</div>
		</div>
		<div class="tab-pane active" id="view">		
			<div id="custom-toolbar">
			    <div class="form-inline" role="form">
			        <div class="form-group">
			            <div class="input-group">
			                <input class="form-control" type="text" placeholder="名称" name="nameCodeLike">
			            </div>
			        </div>
			        <div class="form-group">
			            <div class="input-group">
			                <input class="form-control" type="date" placeholder="创建时间(开始)" name="createTimeStart">
			            </div>
			        	<div class="input-group">
			                <input class="form-control" type="date" placeholder="创建时间(结束)" name="createTimeEnd">
			            </div>
			        </div>
			        <button type="submit" class="btn btn-default" name="querySearch">搜索</button>
			    </div>
			</div>
			<table id="data-table" data-url="project/spl" data-height="555" 
				data-method="post" data-show-refresh="true" 
				data-side-pagination="server" data-pagination="true" 
				data-page-list="[1, 5, 50]" data-search="false">
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
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
	function editor(){
		return "";
	}
</script>
</html>

