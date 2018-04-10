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
	<link href="<%=path%>/css/searchGlobal.css" type="text/css" rel="stylesheet" />
	<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
	<jsp:include page="../common/taglib.jsp"></jsp:include>
	
</head>

<body> 
	<div class="pagebox clearfix">
		<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
		<div class="ritmin">
			<h2 class='system_inform'>系统通知<span data-code="add_notice" class='fr add_pro_common add_system'>新建</span></h2>	
			<div class="tableSearch" id="custom-toolbar">
				<div class="searchTerm NEWsearchCss">
					<label>发送时间：</label>
					<input class='' readonly='readonly' type='text' id="startTime" name="startTime"/>
					<span class='system_arrive'>至</span>					
					<input  class='' readonly='readonly' type='text' id="endTime" name="endTime"/> 
					<label class='system_status'>状态：</label>
					<div class='select_container'>
						<select id="sendStatus" name="sendStatus" class="selectpicker" >
						</select>
					</div>
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
				        	<th data-field="userStr"  data-width="14%" data-align="left">创建人</th>
				        	<th data-field="createTimeStr"  data-width="14%" data-align="left">创建时间</th>
				        	<th data-field="sendTimeStr"  data-width="18%" data-align="left">推送通知时间</th>
				        	<th data-field="messageStatusStr"  data-width="12%" data-align="left">状态</th>
				        	<th data-field="osType"  data-width="14%" data-align="left">发送平台</th>
				        	<th data-field="projectName"  data-width="14%" data-align="left" data-formatter="optFormatter">操作</th>
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
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
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
		search : false,
		onLoadSuccess:function(data){ 
		}
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
			}else if(code=="edit_notice"){
				return '<%=path%>/html/edit_notices.html';
			}
		return "";
	}
	
	$('.add_system').click(function(){//新建
		var code = $(this).attr('data-code');
		sessionStorage.setItem('editStatus','')
		$.getHtml({
			url:getDetailUrl(code),
			okback:function(){ 
			}
		});
		$('.close').addClass('tast-close')//添加关闭按钮
	})
	function system_close(id){
		$.getHtml({
			url:getDetailUrl("system_close"),
			okback:function(){
		$("#confirmClose").click(function(){
				var dataJson={
						id:id,
						sendStatus:"messageStatus:3" 
				}
				sendPostRequestByJsonObj(
				platformUrl.updateMessage, 
				dataJson,
				function(data){ 
					if(data.result.status=="OK"){ 
						 layer.msg("关闭成功")	  
						$("#noticeTable").bootstrapTable('refresh');
						$("#popbg").remove();
						$("#powindow").remove();
					}
				 })
		})
			}
	
		})
		$('.close').addClass('tast-close')//添加关闭按钮

	}
	function system_delete(id){
		$.getHtml({
			url:getDetailUrl('system_delete'),
			okback:function(){
				$("#confirmDelete").click(function(){
					var dataJson={
							id:id
					}
					sendPostRequestByJsonObj(
					platformUrl.deleteMessage, 
					dataJson,
					function(data){ 
						if(data.result.status=="OK"){ 
							 layer.msg("删除成功")	  
							$("#noticeTable").bootstrapTable('refresh');
							$("#popbg").remove();
							$("#powindow").remove();
						}
					 }) 
					
					
				})
			}
			
		})
		$('.close').addClass('tast-close')//添加关闭按钮

	}
	function system_edit(id,status){
		var code="add_notice";
		//该判断只是消息状态为“已发送”情况下弹出edit_notice.html，其他时候编辑页面为add_notice.html
	/* 	if(status=="/messageStatus:2/"){
			code="edit_notice";
		} */
		sessionStorage.setItem('editStatus',status)
		$.getHtml({
			url:getDetailUrl(code),
			okback:function(){
				queryMessage(id,status);
			}
		})
		$('.close').addClass('tast-close')//添加关闭按钮

	}
	function queryMessage(id,status){ 
		var dataJson={
				id:id
		}
		sendPostRequestByJsonObj(
		platformUrl.queryMessage, 
		dataJson,
		function(data){ 
			if(data.result.status=="OK"){ 
				if(null!=data.entityList&&data.entityList.length>0){ 
					  message=data.entityList[0];
					$("#messageId").val(message.id); 
				     if(status=='/messageStatus:2/'){//已发送 
				    	//var arr=message.osType.split("/");
				    	var str="/";
				    	var arr=message.osType.replace(new RegExp(str, 'g'),"、");
				    	var spanArr =  $('.sys_platform span');
				    	 $(".messageContent").html(message.messageContent);
				    	 $('.sys_platform').html(arr); 
				    	 $('.sended_update_time').html(message.sendTimeStr); 
				    	 $("#slpk_two").html("<option selected='selected' value=' '>请选择</option><option index='1' value='messageStatus:2'>已发送</option><option index='2' value='messageStatus:3'>已关闭</option>");

					 	 $("input[name=upgradeTime_two]").val(message.upgradeTimeStr); 
		    	 		 $("#slpk_two").selectpicker('val', message.sendStatus);
						 $("#slpk_two").selectpicker('refresh');
				     }else{
							$("textarea[name=messageContent]").val(message.messageContent);
							$("input[name=upgradeTime]").val(message.upgradeTimeStr);
							$('.radio_cont').removeClass('radio_checked');
							 $(".radio_cont").each(function (i) {
								var value=$(this).find("input:first-child").val();
								if(value==message.isNowSend){
									$(this).addClass("radio_checked");
									var name = $(this).attr('data-name');
									if(name=="setTime"){
										$('.system_radio_second .setTime').show();
									}else{
										$('.system_radio_second .setTime').hide();
									}
								}
							})
							 $("input[name=sendTime]").val(message.sendTimeStr);
							 	$('#slpk').selectpicker('val', message.sendStatus);
							$('#slpk').selectpicker('refresh');
						     var arr=message.osType.split("/");
				     }
				     //推送通知时间
				     
				     
				     
				     
					 $(".highlighCheckbox").each(function (i) {
						 var value=$(this).find("input:first-child").val();
						 for(var i=0;i<arr.length;i++){
							 if(value==arr[i]){
								 $(this).addClass('highlighCheckbox_checked');
							 }
						 }
					})
				}
				}
				
		 })
		
		
	}
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
				$(".selectpicker").html(options) 
			});
		}
	 /*  $(".selectpicker").selectpicker({  
         noneSelectedText : '请选择'//默认显示内容  
     }); */
    
	 $(window).on('load', function() { 
		 	$('.selectpicker').selectpicker('val', '');  
	        $('.selectpicker').selectpicker('refresh');  
	    }); 
	 
	 
    
	 function optFormatter(value, row, index)
		{
		 var content="";
		 if(row.sendStatus!="messageStatus:3"){
			 content += "<span data-code='system_close' class='system_close' onclick='system_close("+row.id+",/"+row.sendStatus+"/)'>关闭</span>&nbsp;&nbsp;";
			 content += "<span data-code='add_notice' class='system_edit' onclick='system_edit("+row.id+",/"+row.sendStatus+"/)'   >编辑</span>&nbsp;&nbsp;";
			   
		    }
				 content += "<span data-code='system_delete' class='system_delete' onclick='system_delete("+row.id+",/"+row.sendStatus+"/)' >删除</span>";
			return content;
			
		}
	 function formatDateTime(inputTime) {    
		    var date = new Date(inputTime);  
		    var y = date.getFullYear();    
		    var m = date.getMonth() + 1;    
		    m = m < 10 ? ('0' + m) : m;    
		    var d = date.getDate();    
		    d = d < 10 ? ('0' + d) : d;    
		    var h = date.getHours();  
		    h = h < 10 ? ('0' + h) : h;  
		    var minute = date.getMinutes();  
		    var second = date.getSeconds();  
		    minute = minute < 10 ? ('0' + minute) : minute;    
		    second = second < 10 ? ('0' + second) : second;   
		    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;    
		};  
		   
</script>
