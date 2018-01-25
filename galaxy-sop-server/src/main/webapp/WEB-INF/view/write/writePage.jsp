<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>  
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../common/taglib.jsp"></jsp:include> 
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<style>
.pagebox{
	padding-top:50px;
	padding-left:60px;
	box-sizing: border-box;
} 
@media (min-width: 1359px) {
	.pagebox{
		padding-top:50px;
		padding-left:9.375%;
	} 
}
.conbox{
	padding:20px;
}
.h2{
font-family:Microsoft YaHei;
font-size:24px;
color:#202533;
line-height:32px;
text-align:left;
font-weight:normal;
}
table{
	width: 100%;
}
table td,table th{
	font-family:Microsoft YaHei;
	padding: 0 10px;
	color:#5a626d;
}
table th{
    font-size: 14px;
    color: #5a626d;
    font-weight: bold;
}
</style>
</head>

<body > 
	<div class="pagebox"> 
		<div class="conbox">
			<h2 class="h2">填写标准</h2>
			<table cellspacing="0"> 
				<thead>
					<tr>
						<th>涉及模块</th>
						<th>状态</th>
						<th width="50%">标准详情</th>
						<th>操作</th> 
					</tr>
				</thead>
				<tbody>
					<tr>
						<td name="filed1">data</td>
						<td name="filed2">data</td>
						<td name="filed3">data</td>
						<td class="edit">
							<label class="blue" data-btn="btn" onclick="seeR(this)">查看</label>
							<label class="blue" data-btn="btn" onclick="editR(this)">编辑</label>
							<label class="blue" data-btn="btn" onclick="closeR(this)">关闭</label>
						</td>
					</tr>
					<tr>
						<td name="filed1">data</td>
						<td name="filed2">data</td>
						<td name="filed3">data</td>
						<td class="edit">
							<label class="blue" data-btn="btn" onclick="seeR(this)">查看</label>
							<label class="blue" data-btn="btn" onclick="editR(this)">编辑</label>
							<label class="blue" data-btn="btn" onclick="closeR(this)">关闭</label>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<jsp:include page="../common/menu.jsp" flush="true"></jsp:include> 
 
	</div>
</body> 
<jsp:include page="../common/header.jsp" ></jsp:include>
<jsp:include page="../common/footer.jsp" ></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" ></jsp:include>
	  
</html>
<script> 
createMenus(5); 
</script>