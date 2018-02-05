<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path%>/css/axure.css" type="text/css" rel="stylesheet" />
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../common/taglib.jsp"></jsp:include>
<script src="<%=request.getContextPath()%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<style>
.pagebox {
	padding-top: 50px;
	padding-left: 60px;
	box-sizing: border-box;
}

@media ( min-width : 1359px) {
	.pagebox {
		padding-top: 50px;
		padding-left: 9.375%;
	}
}

.conbox {
	padding: 20px;
}

.h2 {
	font-family: Microsoft YaHei;
	font-size: 24px;
	color: #202533;
	line-height: 32px;
	text-align: left;
	font-weight: normal;
}

table {
	width: 100%;
	margin-top: 20px;
}

table td, table th {
	font-family: Microsoft YaHei;
	padding: 0 10px;
	color: #5a626d;
}

table th {
	font-size: 14px;
	color: #5a626d;
	font-weight: bold;
}
</style>
</head>

<body>
	<div class="pagebox">
		<div class="conbox">
			<h2 class="h2">填写标准</h2>
			<table class='no-radius table_new_style'
				width="100%" cellspacing="0" cellpadding="0" id="standard-table"
				data-url="<%=request.getContextPath()%>/galaxy/standard/search"
				data-page-list="[10, 20, 30]" data-show-refresh="true" data-unique-id="id">
				<thead>
					<tr>
						<th data-field="moduleName">涉及模块</th>
						<th data-field="statusDesc">状态</th>
						<th data-field="standardDetails" data-width="50%" data-formatter="detailFormatter">标准详情</th>
						<th data-field="opt" data-formatter="optFormatter" class="edit">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>

	</div>
</body>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/footer.jsp"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp"></jsp:include>

</html>
<script>
	createMenus(5);
	$table = $("#standard-table").bootstrapTable({
		queryParamsType : 'size|page',
		pageSize : 10,
		showRefresh : false,
		sidePagination : 'server',
		method : 'post',
		sortOrder : 'desc',
		sortName : 'created_time',
		pagination : true,
		search : false,
	    queryParams: function(params){
	    	params.page = params.pageNum;
	    	params.size = params.pageSize;
	    	return params;
	    }
	});
	function detailFormatter(value, row, index)
	{
		if(value)
		{
			if(value.length>40)
			{
				return '<span title="'+value+'">'+value.substring(0,40)+'...</span>';
			}
			return value;
		}
		return "-";
	}
	function optFormatter(value, row, index)
	{
		var status = row.status;
		var action = 'close';
		var actionDesc = '关闭';
		if(status == 0)
		{
			action = 'open';
			actionDesc = '开启';
		}
		var content = '<label class="blue" data-btn="btn" onclick="PopR(this,\'s\')">查看</label>&nbsp;&nbsp;';
		content += '<label class="blue" data-btn="btn" onclick="PopR(this,\'e\')">编辑</label>&nbsp;&nbsp;';
		content += '<label class="blue" data-btn="btn" onclick="toggleStandard('+row.id+',\''+action+'\')">'+actionDesc+'</label>';
		return content;
		
	}
	/* function showDetail(index, opt)
	{
		var row = $table.bootstrapTable('getRowByUniqueId',index); 
	}
	*/function toggleStandard(id,opt)
	{
		var row = $("#standard-table").bootstrapTable('getRowByUniqueId',index); 
	} 
	
	function PopR(event, status) {
		var that = $(event);
		var tr = that.closest("tr");
		$.getHtml({
			url : "/sop/html/writePop.html",//模版请求地址 
			data : "",//传递参数
			okback : function() {
				var name = tr.find("td:first").text();
				var text = tr.find("td").eq(2).text();
				if (status == "e") {
					$(".edit").show();
					$(".edit dd[name='name']").text(name);
					$(".edit textarea").val(text);
				} else if (status == "s") {
					$(".see").show();
					$(".see dd[name='name']").text(name);
					$(".see dd[name='text']").text(text);
				}
			}
		})
	}
</script>