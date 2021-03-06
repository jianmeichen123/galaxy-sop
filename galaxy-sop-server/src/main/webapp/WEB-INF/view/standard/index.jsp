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
.fixed-table-container .table_new_style thead th .th-inner {padding: 8px 0;}
table td{
padding:0 20px!important;
}
.table_new_style th{    padding-left: 20px !important;}
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
	table-layout: fixed;
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
.table_new_style td{
	overflow:hidden;
	white-space:nowrap; 
	text-overflow:ellipsis; 
}
</style>
</head>

<body>
	<div class="pagebox">
		<div class="conbox">
			<h2 class="h2">填写标准</h2>
			<table class='no-radius table_new_style standard'
				width="100%" cellspacing="0" cellpadding="0" id="standard-table"
				data-url="<%=request.getContextPath()%>/galaxy/standard/search"
				data-page-list="[10, 20, 30]" data-show-refresh="true" data-unique-id="id">
				<thead>
					<tr>
						<th data-field="moduleName" data-width="140px">涉及模块</th>
						<th data-field="statusDesc" data-width="140px">状态</th>
						<th data-field="standardDetails"  data-formatter="detailFormatter">标准详情</th>
						<th data-field="opt" data-width="140px" data-formatter="optFormatter" class="edit">操作</th>
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
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
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
		pagination : false,
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
			if(row.id==7){
				value="<span style=\"display:none;\">"+value+"</span>"+value.replace(/<[^>]+>/g,""); 
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
		if(row.moduleCode==1){
			var content = '<label class="blue" data-btn="btn" onclick="PopR(this,\'sE\')">查看</label>&nbsp;&nbsp;';
			content += '<label class="blue" data-btn="btn" onclick="PopR(this,\'edit\')">编辑</label>&nbsp;&nbsp;';
			
		}else{
			var content = '<label class="blue" data-btn="btn" onclick="PopR(this,\'s\')">查看</label>&nbsp;&nbsp;';
			content += '<label class="blue" data-btn="btn" onclick="PopR(this,\'e\')">编辑</label>&nbsp;&nbsp;';
			
		}
		content += '<label class="blue" data-btn="btn" onclick="toggleStandard('+row.id+',\''+action+'\')">'+actionDesc+'</label>';
		
		return content;
		
	}
	/* function showDetail(index, opt)
	{
		var row = $table.bootstrapTable('getRowByUniqueId',index); 
	}
	*/function toggleStandard(id,opt)
	{  
		var statusCode=1;
		var msg="开启成功"
		if(opt=="close"){
			statusCode=0;
			msg="关闭成功"
		}
		var dataJson={
				id:id,
				status:statusCode 
		}
		sendPostRequestByJsonObj(
		platformUrl.toggleStandard, 
		dataJson,
		function(data){ 
			if(data.result.status=="OK"){ 
				 layer.msg(msg)	  
				$("#standard-table").bootstrapTable('refresh');
				$("#popbg").remove();
				$("#powindow").remove();
			}
		 })
	} 
	
	function PopR(event, status) {
		var that = $(event);
		var tr = that.closest("tr");
		var uniqueid=tr.data("uniqueid");
		var statusC = that.next().text();
		if(statusC=="开启"){
			statusC=0;
		}else{
			statusC=1;
		}
		$.getHtml({
			url : "/sop/html/writePop.html",//模版请求地址 
			data : "",//传递参数
			okback : function() {
				$('.close').addClass('tast-close')//添加关闭按钮 
				var name = tr.find("td:first").text();
				var text = tr.find("td").eq(2).text();
				var standardDetails="";
				if (status == "e") {
					$(".edit").show();
					$(".edit dd[name='name']").text(name);
					$("#Viewtext").show().val(text);
					var inputNum = $("#Viewtext").val().length;
					var valNUm = 50-inputNum; 
					$("#Number").text(valNUm);
				} else if (status == "s") {
					$(".see").show();
					$(".see dd[name='name']").text(name);
					$(".see dd[name='text']").text(text);
				}else if (status == "sE") {
					$(".see").show();
					$(".see dd[name='name']").text(name);
					text = tr.find("td").eq(2).find("span").html();
					var str = "<blockquote class='intw_summary'>"+text+"</blockquote>"
					$(".see dd[name='text']").html(str);
				}else if(status=="edit"){
					$(".num_tj").remove();
					var viewNotes=CKEDITOR.replace('viewNotes',{height:'100px',width:'420px'});
					$(".edit").show();
					$(".edit dd[name='name']").text(name); 
					text = tr.find("td").eq(2).find("span").html();
					$("#viewNotes").html(text)
					$("#viewNotes").show(); 
					}
				
				$("#save_standard").click(function(){  
					//statusCode
					 var validate =$("#detail-form").validate().form();
					if(!validate){
						return;
					}
					var standardDetails=""; 
					if($("#Viewtext").is(":hidden")){
						standardDetails =$.trim(CKEDITOR.instances.viewNotes.getData());
						if(!standardDetails){
							 layer.msg("标准详情不能为空")	  
							return;
						}
					}else{
						standardDetails=$("#Viewtext").val();
					}
					var dataJson={
							id:uniqueid,
							standardDetails:standardDetails ,
							status:statusC,
					}
					sendPostRequestByJsonObj(
					platformUrl.saveStandard, 
					dataJson,
					function(data){ 
						if(data.result.status=="OK"){
							 layer.msg("保存成功")	  
							$("#standard-table").bootstrapTable('refresh');
							$("#popbg").remove();
							$("#powindow").remove();
						}
					 })
				})
			}
		})
	}
</script>