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
	<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<jsp:include page="../common/taglib.jsp"></jsp:include>
	
</head>

<body> 
	<div class="pagebox clearfix">
		<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
		<div class="ritmin">
			<h2 class='system_inform'>系统通知<span data-code="add_notice" class='fr add_pro_common add_system'>新建</span></h2>	
			<div class="tableSearch" id="custom-toolbar">
				<div class="searchTerm">
					<label>发送时间：</label>
					<input class='' readonly='readonly' type='text' id="startTime" name="startTime"/>
					<span class='system_arrive'>至</span>					
					<input  class='' readonly='readonly' type='text' id="endTime" name="endTime"/> 
					<label class='system_status'>状态：</label>
					<select id="sendStatus" name="sendStatus" class="selectpicker" >
					</select>
					<a href="javascript:;" class='system_querySearch' action="querySearch">搜索</a>
					<!--<span class='system_querySearch'>查询</span>  -->
				</div>
				
			</div>	
				<div class="tab-pane active" id="view">
				<table   class='assingTable table-hover systemtTable' 
				id="noticeTable" 
				style='table-layout:fixed;'
				width="100%" cellspacing="0" cellpadding="0" 
				data-url="<%=request.getContextPath()%>/galaxy/systemMessage/searchSystemMessage"
			    data-method="post" 
			    data-side-pagination="server"
			    data-toolbar="#custom-toolbar"
				data-pagination="true" 
				data-page-list="[10, 20, 30]" 
				data-show-refresh="false"
				data-id-field="id" 
				 data-unique-id="id">
					<thead>
					    <tr> 
				        	<th data-field="messageContent"  data-width="14%" data-align="left">通知内容</th>
				        	<th data-field="createId"  data-width="14%" data-align="left">创建人</th>
				        	<th data-field="createTime"  data-width="14%" data-align="left">创建时间</th>
				        	<th data-field="sendTime"  data-width="18%" data-align="left">推送通知时间</th>
				        	<th data-field="sendStatus"  data-width="12%" data-align="left">状态</th>
				        	<th data-field="osType"  data-width="14%" data-align="left">发送平台</th>
				        	<th data-field="projectName"  data-width="14%" data-align="left">操作</th>
	 					</tr>	
	 				</thead>

				</table> 
			</div>
			
			
		</div>
	</div>
</body>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/footer.jsp"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp"></jsp:include>

</html>
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<!--  时间插件-->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script>
	createMenus(5);
	 $("#noticeTable").bootstrapTable({
		queryParamsType : 'size|page',
		pageSize : 10,
		pageNum:1,
		showRefresh : false,
		sidePagination : 'server',
		method : 'post',
		sortOrder : 'desc',
		sortName : 'created_time',
		clickToSelect: true,
		pagination : true,
		search : false
	});
	//日期选择
	$('.searchTerm input[name="startTime"]').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    defaultDate : Date,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now'
	});
	$('.searchTerm input[name="endTime"]').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    defaultDate : Date,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now'
	});
	$("a[action='querySearch']").click(function(){
		var startTime = $('input[name="startTime"]').val();
		var endTime = $('input[name="endTime"]').val();
		if(startTime>endTime){
			layer.msg('开始时间不能大于结束时间')
		}
		
	})

	function getDetailUrl(code)
	{
		if(code =='add_notice')
		{	
			return '<%=path%>/html/add_notices.html';
		}else if(code=='system_close'){
			return '<%=path%>/html/close_system.html';
		}else if(code=='system_delete')	{
			return '<%=path%>/html/delete_system.html';
			}
		return "";
	}
	
	$('.add_system').click(function(){//新建
		var code = $(this).attr('data-code');
		$.getHtml({
			url:getDetailUrl(code),
			okback:function(){
				
			}
			
		});
		$('.close').addClass('tast-close')//添加关闭按钮
	})
	$('.system_close,.system_delete').click(function(){//关闭和删除事件
		var code = $(this).attr('data-code');
		$.getHtml({
			url:getDetailUrl(code),
			okback:function(){
				
			}
		})
		$('.close').addClass('tast-close')//添加关闭按钮
		
	})
	
	//编辑按钮
	$('.system_edit').click(function(){
		var code = $(this).attr('data-code');
		$.getHtml({
			url:getDetailUrl(code),
			okback:function(){
				
			}
		})
		$('.close').addClass('tast-close')//添加关闭按钮
	})
	selectMessageStatus(platformUrl.searchDictionaryChildrenItems+"messageStatus", "messageStatus",1);
    //系统消息状态
	 function selectMessageStatus(url, name, mark,selectIndex){
			sendGetRequest(url,null, function(data){
				var options = [];
				if(mark == 1){
					options.push('<option value="">全部</option>');
				}
				$.each(data.entityList, function(i, value){
					if(selectIndex && i == selectIndex){
						options.push('<option index="'+i+'" selected="selected" value="'+value.code+'">'+value.name+'</option>');
					}else{
						options.push('<option index="'+i+'" value="'+value.code+'">'+value.name+'</option>');
					}
				});
				//$('select[name="'+name+'"]').append(options.join(''));
				$("#sendStatus").html(options) 
			});
		}
	
</script>
