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

<!-- 高管/投资经理 -->
<c:set var="isCreatedByUser" value="${fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

<style type="text/css">
.bars{margin:0 !important;}
</style>
<script src="<%=path %>/js/partFile.js"></script>
</head>


<body>





			<!-- 交割前事项
			<div data-tab="con">
			 -->
			<div class="member">
				<c:if test="${isEditable }">
				<div class="top clearfix">
					<!--按钮-->
					<div class="btnbox_f btnbox_f1 clearfix">
						<a href="javascript:void(0)"  class="pubbtn bluebtn ico c4" data-btn='to_add_deliver' data-name='添加事项' style='display:none;'></a>
					</div>
				</div>
				</c:if>
				
				<!-- <div class="min_document clearfix" id="custom-toolbar" style="display:none;" >
					<div class="bottom searchall clearfix">
						<input type="hidden" id="projectId" name="projectId" value="">   项目id
					</div>
				</div> -->
				<table id="project_delivery_table" class="commonsize delivery"
					data-page-list="[10, 20, 30]" 
					data-id-field="id"
					data-toolbar="#custom-toolbar" data-show-refresh="true">
					<thead>
						<tr>
							<th data-field="field1" data-align="left" data-formatter="infoDeliverFormat" data-width="25%">事项简述</th>
							<th data-field="field3" data-align="left" class="data-input sort" data-sortable="true" data-formatter="statusFormat">状态<span></span></th>
							<th data-field="endByUname" data-align="left">编辑人</th>
							<th data-field="updatedTime" data-align="left" data-formatter="longTime_Format" >编辑日期</th>
							<th data-field="field5" data-align="left" data-formatter="notReturn_Format">附件数</th>
							<th data-align="left" class="w_150" data-formatter="operFormat">操作</th>
						</tr>
					</thead>
				</table>
				
			</div>
			<!-- </div>
				tab end-->

</body>


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
	var key = Date.parse(new Date());
	var keyJSON={};
	var deleteJSON={};
	var proid = projectInfo.id;
	var deliver_selectRow = null;
	var isTransfering = "${fx:isTransfering(pid) }";
$(function(){
	
	$("#projectId").val(proid);
	if(isTransfering == 'true')
	{
		$("[data-btn='to_add_deliver']").addClass('limits_gray');
	}
	/* init_bootstrapTable('project_delivery_table',10); */
	$(".fixed-table-pagination").remove();
	//刷新右侧投后运营简报信息
	function check_tr(table){
		var tr_length = table.find("tr").length;
		if(tr_length>=10){
			$("[data-btn='to_add_deliver']").hide();
		}else{
			$("[data-btn='to_add_deliver']").show();
		}
		
	}
	$("#project_delivery_table").on('load-success.bs.table',function(table,data){
		check_tr($("#project_delivery_table tbody"));
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
					key = Date.parse(new Date());
					delete deleteJSON.partDelFile;
					 keyJSON["b_part"]=key;
					 var params = {};
					 params.fileReidsKey = key;
					 params.projectId =  proid;
					 params.titleId = "1810";
					 toBachPartUpload(Constants.sopEndpointURL+'galaxy/informationFile/sendInformationByRedis',
								null,"textarea2","select_btn","win_ok_btn","container","filelist",
								params,"deliver_form",null,null);
					$("#filelist").css("display","none")
				}
			});
			return false;
		});
	}
	
	$('#project_delivery_table').bootstrapTable({
		queryParamsType: 'size|page',
		pageSize:5,
		showRefresh : false ,
		url : Constants.sopEndpointURL+"/galaxy/delivery/queryprodeliverypage",
		sidePagination: 'server',
		method : 'post',
		//sortOrder : 'desc',
		//sortName : 'updated_time',
		pagination: true,
	    search: false,
	    queryParams:function(param){
	    	param.titleId = '1810';
	    	param.projectId = proid;
	    	return param;
	    },
	    onLoadSuccess: function (data) {
	    	
	    }
	});
});	
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
	if(row.field5 && row.field5 != 0 && row.field5 != '0'){
		content += downfile;
	}
	
	return content;
}


/**
 * 查看/编辑  事项
 */
 function deliverInfoEdit(selectRowId,type){
		deliver_selectRow = $('#project_delivery_table').bootstrapTable('getRowByUniqueId', selectRowId);
		var _url = Constants.sopEndpointURL + '/galaxy/delivery/tomatterdeliver';
		if(type == 'v'){
			_url = Constants.sopEndpointURL + '/galaxy/delivery/todeliverinfo';
		}
		$.getHtml({
			url:_url,
			data:"",
			okback:function(){
				if(type == 'v'){
					$('#popup_name').text('查看事项');
				}else{
					$('#popup_name').text('编辑事项');
				}
					
					key = Date.parse(new Date());
					delete deleteJSON.partDelFile;
					 keyJSON["b_part"]=key;
					 var params = {};
					 params.fileReidsKey = key;
					 params.projectId =  proid;
					 params.titleId = "1810";
					_url = Constants.sopEndpointURL + '/galaxy/delivery/selectdelivery/'+selectRowId;
					sendGetRequest(_url, {}, function(data){
						var result = data.result.status;
						if(result == "OK"){
							var deliverInfo = data.entity;
							if(type != "v"){
								$("#projectId").val(deliverInfo.projectId);
								$("#deliver_form [data-name='id']").val(deliverInfo.id);
								$("#deliver_form [data-name='field1']").val(deliverInfo.field1);
								$("#deliver_form [data-name='field2']").val(deliverInfo.field2);
								$("#deliver_form [data-name='field3']").val(deliverInfo.field3);
							}else{
								$("#delDescribe").html(deliverInfo.field1);
								$("#details").html(deliverInfo.field2);
								$("#delStatus").html(deliverInfo.field3);
							}
							$.each(data.entity.fileList,function(){
								var but = "<button type='button' id='"+this.id+"btn' onclick=delPart('"+this.id+"','"+this.fileName+"','textarea2','partDelFile')>删除</button>" ;
								var htm = "<tr id='"+this.id+"tr'>"+
												"<td>"+this.fileName+"."+this.fileSuffix+
													"<input type=\"hidden\" name=\"oldfileids\" value='"+this.id+"' />"+
												"</td>"+
												"<td>"+plupload.formatSize(this.fileLength)+"</td>";
									if(type != "v"){
										htm+=	"<td>"+ but +"</td><td>100%</td>";
									}			
									htm+= "</tr>";
								$("#filelist").append(htm);
							});
							var fileLen=$("#filelist tr:gt(0)").length;
							if(fileLen==0){
								$("#filelist").css("display","none");
							}
							toBachPartUpload(Constants.sopEndpointURL+'galaxy/informationFile/sendInformationByRedis',
										null,"textarea2","select_btn","win_ok_btn","container","filelist",
										params,"deliver_form",null,null);
							
							
						}else{
							layer.msg(data.result.message);
						}
					});
				
			}
		});
		return false;
	}

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

/**
 * 删除文件列表的文件
 * @param id
 * @param name
 * @param fieInputId
 */
function delPart(id,name,fieInputId,partDelFile){
	 if(deleteJSON[partDelFile]){
       deleteJSON[partDelFile] = deleteJSON[partDelFile] +","+id;
   }else{
       deleteJSON[partDelFile] = id;
   }
	 var params = {};
	  params.projectId =  proid;
	  params.fileReidsKey = key;
	  params.newFileName = id;
   //文件id
   sendPostRequestByJsonObj(Constants.sopEndpointURL+'galaxy/informationFile/deleteRedisFile',params,function(data){
			//进行上传
			var result = data.status;
			if(result == "OK"){
			     //删除
			     $("#"+fieInputId).val($("#"+fieInputId).val().replace(name,""));
				 $("#"+id+"tr").remove();
			      var fieInputLen=$("tr[id]").length;
			      if(fieInputLen==0){
			      	$("#filelist").css("display","none");
			      }
			}else{
				layer.msg("删除失败!");
			}
	  });
}


	
</script>
</html>

