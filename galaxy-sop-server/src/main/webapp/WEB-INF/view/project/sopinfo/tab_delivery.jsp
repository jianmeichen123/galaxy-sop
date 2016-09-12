<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>项目详情</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>


<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/sopinfo.js"></script>
<style type="text/css">
.bars{margin:0 !important;}
</style>
</head>


<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
 	<div class="ritmin">
 	
    	<jsp:include page="sopcommon.jsp" flush="true"></jsp:include>


		<div class="new_left">
			<div class="tabtable assessment label_static">
				<!-- tab标签 -->
	            <jsp:include page="tab_header.jsp?index=7" flush="true"></jsp:include>



			<!-- 交割前事项
			<div data-tab="con">
			 -->
			<div class="member">
				<c:if test="${isEditable }">
				<div class="top clearfix">
					<!--按钮-->
					<div class="btnbox_f btnbox_f1 clearfix">
						<a href="javascript:void(0)"  class="pubbtn bluebtn ico c4" data-btn='to_add_deliver' data-name='添加事项'></a>
					</div>
				</div>
				</c:if>
				
				<div class="min_document clearfix" id="custom-toolbar" style="display:none;" >
					<div class="bottom searchall clearfix">
						<input type="hidden" id="projectId" name="projectId" value="">   <!-- 项目id -->
					</div>
				</div>
				
				<table id="project_delivery_table" class="commonsize delivery"
					data-page-list="[10, 20, 30]" 
					data-url="<%=path%>/galaxy/delivery/queryprodeliverypage" 
					data-id-field="id"
					data-toolbar="#custom-toolbar">
					<thead>
						<tr>
							<th data-field="delDescribe" data-align="left" data-formatter="infoDeliverFormat" data-width="25%">事项简述</th>
							<th data-field="del_status" data-align="left" class="data-input sort" data-sortable="true" data-formatter="statusFormat">状态<span></span></th>
							<th data-field="endByUname" data-align="left">编辑人</th>
							<th data-field="updatedTime" data-align="left" data-formatter="longTime_Format" >编辑日期</th>
							<th data-field="fileNum" data-align="left" data-formatter="notReturn_Format">附件数</th>
							<th data-align="left" data-formatter="operFormat">操作</th>
						</tr>
					</thead>
				</table>
				
			</div>
			<!-- </div>
				tab end-->

			</div>
		</div>

		<!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>
        
    </div>
 
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>


<!-- 分页二css+四js -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- file -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script src="<%=path %>/js/batchUpload.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>

<script>

	var proid = projectInfo.id;
	var deliver_selectRow = null;
	var isTransfering = "${fx:isTransfering(pid) }";
$(function(){
	createMenus(5);
	
	$("#projectId").val(proid);
	if(isTransfering == 'true')
	{
		$("[data-btn='to_add_deliver']").addClass('limits_gray');
	}
	init_bootstrapTable('project_delivery_table',10);
	//刷新右侧投后运营简报信息
	$("#project_delivery_table").on('load-success.bs.table',function(table,data){
		if(data.pageList.total>0 && isTransfering == 'true')
		{
			$.each($("#project_delivery_table tr"),function(){
				$(this).find("td:last").addClass('limits_gray');
			});
			$.each($("#project_delivery_table label.blue"), function(){
				var text = $(this).text();
				if(text == '编辑' || text == '删除')
				{
					$(this).removeAttr('onclick');
				}
			});
		}
		setThyyInfo();
	});
	if(projectInfo.projectStatus == 'projectStatus:2' || projectInfo.projectStatus == 'projectStatus:3' || projectInfo.projectStatus == 'meetingResult:3' || admin!="true"){
		$("[data-btn='to_add_deliver']").off();
		$("[data-btn='to_add_deliver']").remove();
	} else {
		$("[data-btn='to_add_deliver']").text("添加事项");
		$("[data-btn='to_add_deliver']").on("click",function(){
			var $self = $(this);
			if($self.hasClass('limits_gray'))
			{
				return;
			}
			var _name= $self.attr("data-name");
			var _url = Constants.sopEndpointURL + '/galaxy/delivery/toadddeliver';
			$.getHtml({
				url:_url,
				data:"",
				okback:function(){
					$("#popup_name").html(_name);
					$("#deliver_form [name='projectId']").val(proid);
					toInitBachUpload();
					$("#filelist").css("display","none")
				}
			});
			return false;
		});
	}
});	

//获取 页面数据\保存数据
function paramsContion(){
	
	if(!beforeSubmit()){
		return false;
	}
	
	var condition = JSON.parse($("#deliver_form").serializeObject());
	condition.fileReidsKey = Date.parse(new Date());
	condition.fileNum = $("#filelist").find("tr").length - 1;
	
	var oldFids=[];
	var oldfileids = $("input[name='oldfileids']");
	if(oldfileids && oldfileids.length > 0){
		
		$.each(oldfileids, function(i) { 
			var idVal = oldfileids[i].value;
		   	if(!isNaN(idVal)){
		   		oldFids.push(idVal);
		   	}
		});
		condition.fileIds = oldFids;
	}
	
	return condition;
}

function toInitBachUpload(){
	toBachUpload(Constants.sopEndpointURL+'galaxy/sopFile/sendUploadByRedis',
					Constants.sopEndpointURL + '/galaxy/delivery/operdelivery',"textarea2","select_btn","save_file","container","filelist",
					paramsContion,"deliver_form",saveCallBackFuc);
}


/**
 * 回调函数
 */
 
function saveCallBackFuc(data){
	removePop1();
	//启用滚动条
	 $(document.body).css({
	   "overflow-x":"auto",
	   "overflow-y":"auto"
	 });
	$("#project_delivery_table").bootstrapTable('refresh');
}

/**
 *  状态 format
 */
function statusFormat(value,row,index){  
	return row.statusFormat;
}


function notReturn_Format(value,row,index){  
	return value==null?0:value ;
}



/**
 *  查看
 */
function infoDeliverFormat(value,row,index){  
	var old = value;
	var cut = cutStr(25,old);
	var hasCut = getLength(value) >= 25;
	var info = "<label class=\"blue\" onclick=\"deliverInfoEdit('"+row.id+"','v')\" >"+cut+"</label>";
	if(hasCut && hasCut == true){
		info = "<label class=\"blue\" onclick=\"deliverInfoEdit('"+row.id+"','v')\" title='"+old+"' >"+cut+"</label>";
	}
	return info;
}
function cutStr(theNum,theOldStr){
	var leaveStr = "";
	var leng = getLength(theOldStr);
	if(theNum >= leng){
		return theOldStr;
	}else{
		var cont = 0;
		for (var i = 0; i < theOldStr.length; i++) {
			if (theOldStr.charCodeAt(i) >= 0x4e00 && theOldStr.charCodeAt(i) <= 0x9fa5){ 
				cont += 2;
			}else {
				cont++;
			}
			if(cont >= theNum){
				break;
			}
			leaveStr += theOldStr.charAt(i);
		}
		return leaveStr + "...";
	}
	return theOldStr;
}




/**
 * 编辑   删除   下载
 */
function operFormat(value,row,index){  
	var edit = "<label class=\"blue\" onclick=\"deliverInfoEdit('"+row.id+"','e')\" >编辑</label>";
	var del = "<label class=\"blue\" onclick=\"to_del_deliver('"+row.id+"')\" >删除</label>";
	var downfile = "<label class=\"blue\" onclick=\"to_download_deliver('"+row.id+"')\">下载附件</label>";
	var content = "";
	if("${isCreatedByUser}"=="true" && isTransfering == 'false'){
		content += edit;
		content += del;
	}
	if(row.fileNum && row.fileNum != 0 && row.fileNum != '0'){
		content += downfile;
	}
	
	return content;
}


/**
 * 查看/编辑  事项
 */
 function deliverInfoEdit(selectRowId,type){
		deliver_selectRow = $('#project_delivery_table').bootstrapTable('getRowByUniqueId', selectRowId);
		//var $self = $(this);
		//var _name= $self.attr("data-name");
		var _url = Constants.sopEndpointURL + '/galaxy/delivery/tomatterdeliver';
		if(type == 'v'){
			_url = Constants.sopEndpointURL + '/galaxy/delivery/todeliverinfo';
		}
		$.getHtml({
			url:_url,
			data:"",
			okback:function(){
				if(type == 'v'){
					$("#popup_name").html("查看事项信息");
					$("#deliver_form [name='delDescribe']").text(deliver_selectRow.delDescribe);
					$("#deliver_form [name='details']").text(deliver_selectRow.details);
					$("#deliver_form [name='delStatus']").text(deliver_selectRow.statusFormat);
				}else{
					_url = Constants.sopEndpointURL + '/galaxy/delivery/selectdelivery/'+selectRowId;
					sendGetRequest(_url, {}, function(data){
						var result = data.result.status;
						if(result == "OK"){
							var deliverInfo = data.entity;
							$("#popup_name").html("编辑事项信息");
							$("#deliver_form [name='id']").val(deliverInfo.id);
							$("#deliver_form [name='projectId']").val(proid);
							$("#deliver_form [name='delDescribe']").val(deliverInfo.delDescribe);
							$("#deliver_form [name='details']").text(deliverInfo.details);
							$("#deliver_form [name='delStatus'][value='"+deliverInfo.delStatus+"']").attr("checked",'checked');
							
							$.each(data.entity.files,function(){
								var but = type == 'v' ? " -" : "<button type='button' id='"+this.id+"btn' onclick=del('"+this.id+"','"+this.fileName+"','textarea2')>删除</button>" ;
								var htm = "<tr id='"+this.id+"tr'>"+
												"<td>"+this.fileName+"."+this.fileSuffix+
													"<input type=\"hidden\" name=\"oldfileids\" value='"+this.id+"' />"+
												"</td>"+
												"<td>"+plupload.formatSize(this.fileLength)+"</td>"+
												"<td>"+ but +"</td>"+
												"<td>100%</td>"+
											"</tr>"
								$("#filelist").append(htm);
							});
							
							toInitBachUpload();
							var fileLen=$("#filelist tr:gt(0)").length;
							//console.log(fileLen)
							if(fileLen==0){
								$("#filelist").css("display","none");
							}
							
						}else{
							layer.msg(data.result.message);
						}
					});
				}
				
			}
		});
		return false;
	}

/**
 * 保存  事项
 */
function save_deliver(){  
	var content = paramsContion();
	var _url =  Constants.sopEndpointURL + '/galaxy/delivery/operdelivery'
	sendPostRequestByJsonObj(_url, content, function(data){
		if (data.result.status=="OK") {
			layer.msg("保存成功");
			removePop1();
			$("#project_delivery_table").bootstrapTable('refresh');
		} else {
			layer.msg(data.result.message);
		}
	});
}



/**
 * 删除  事项

function to_del_deliver(selectRowId){
	var _url = Constants.sopEndpointURL + '/galaxy/delivery/todeldeliver/';
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#popup_name").html("提示");
			$("#del_deliver_id").val(selectRowId);
		}
	});
	return false;
} */
function to_del_deliver(selectRowId){
	layer.confirm('是否删除事项?',
		{
		  btn: ['确定', '取消'] 
		}, 
		function(index, layero){
			del_deliver(selectRowId);
		}, 
		function(index){
		}
	);
}

function to_download_deliver(id){
	try {
		var url = Constants.sopEndpointURL + '/galaxy/delivery/downloadBatchFile'+"/"+id;
		layer.msg('正在下载，请稍后...',{time:2000});
		window.location.href=url+"?sid="+sessionId+"&guid="+userId;
	} catch (e) {
		layer.msg("下载失败");
	}
}

function del_deliver(id){  
	//var id = $("#del_deliver_id").val();
	var _url =  Constants.sopEndpointURL + '/galaxy/delivery/deldelivery/'+id;
	sendPostRequestByJsonObj(_url, {}, function(data){
		if (data.result.status=="OK") {
			layer.msg("删除成功");
			removePop1();
			$("#project_delivery_table").bootstrapTable('refresh');
		} else {
			layer.msg(data.result.message);
		}
	});
}	




	
</script>
</html>

