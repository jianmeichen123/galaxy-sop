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

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>


<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/sopinfo.js"></script>
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
	            <ul class="tablink tablinks">
	                <li><a href="javascript:;" onclick="showTabs(${pid},0)">基本信息</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},1)">团队成员</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},2)">股权结构</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},3)">访谈记录</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},4)">会议纪要</a></li>
	                <li class="on"><a href="javascript:;" onclick="showTabs(${pid},7)">交割前事项</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},8)">拨款信息</a></li>
                	<li><a href="javascript:;" onclick="showTabs(${pid},9)">运营分析</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},5)">项目文档</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},6)">操作日志</a></li>
	                
	            </ul>



			<!-- 交割前事项
			<div data-tab="con">
			 -->
			<div class="member">
				<div class="top clearfix">
					<!--按钮-->
					<div class="btnbox_f btnbox_f1 clearfix">
						<a href="javascript:void(0)"  class="pubbtn bluebtn ico c4" data-btn='to_add_deliver' data-name='添加事项'></a>
					</div>
				</div>
				
				<div class="min_document clearfix" id="custom-toolbar" style="display:none;" >
					<div class="bottom searchall clearfix">
						<input type="hidden" id="projectId" name="projectId" value="">   <!-- 项目id -->
					</div>
				</div>
				
				<table id="project_delivery_table" class="commonsize delivery"
					data-url="<%=path%>/galaxy/delivery/queryprodeliverypage" 
					data-id-field="id"  data-page-list="[10, 20, 30]"
					data-toolbar="#custom-toolbar">
					<thead>
						<tr>
							<th data-field="delDescribe" data-align="center" data-formatter="infoDeliverFormat" >事项简述</th>
							<th data-field="del_status" data-align="center" class="data-input sort" data-sortable="true" data-formatter="statusFormat">状态</th>
							<th data-field="endByUname" data-align="center">编辑人</th>
							<th data-field="updatedTime" data-align="center" data-formatter="longTime_Format" >编辑日期</th>
							<th data-field="fileNum" data-align="center" >附件数</th>
							<th data-align="center" data-formatter="operFormat">操作</th>
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



<script>

	var proid = projectInfo.id;
	var deliver_selectRow = null;

$(function(){
	createMenus(5);
	
	$("#projectId").val(proid);
	
	$('#project_delivery_table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
		uniqueId: "id", 
		idField : "id",
		clickToSelect: true,
        search: false,
	});
	
	
	if(projectInfo.projectStatus == 'projectStatus:2' || projectInfo.projectStatus == 'projectStatus:3' || projectInfo.projectStatus == 'meetingResult:3' || admin!="true"){
		$("[data-btn='to_add_deliver']").off();
		$("[data-btn='to_add_deliver']").remove();
	} else {
		$("[data-btn='to_add_deliver']").text("添加事项");
		$("[data-btn='to_add_deliver']").on("click",function(){
			var $self = $(this);
			var _name= $self.attr("data-name");
			var _url = Constants.sopEndpointURL + '/galaxy/delivery/toadddeliver';
			$.getHtml({
				url:_url,
				data:"",
				okback:function(){
					$("#popup_name").html(_name);
					$("#deliver_form [name='projectId']").val(proid);
				}
			});
			return false;
		});
	}
});	
	

/**
 *  状态 format
 */
function statusFormat(value,row,index){  
	return row.statusFormat;
}

/**
 *  查看
 */
function infoDeliverFormat(value,row,index){  
	var info = "<label class=\"blue\" onclick=\"deliverInfoEdit('"+row.id+"','v')\" >"+value+"</label>";
	return info;
}

/**
 * 编辑   删除   下载
 */
function operFormat(value,row,index){  
	var edit = "<label class=\"blue\" onclick=\"deliverInfoEdit('"+row.id+"','e')\" >编辑</label>";
	var del = " <label class=\"blue\" onclick=\"to_del_deliver('"+row.id+"')\" >删除</label>";
	var downfile = " <label class=\"blue\">下载附件</label>";
	var content = "";
	if("${isCreatedByUser}"=="true")
	{
		content += edit;
		content += del;
	}
	content += downfile;
	return content;
	return edit+del+downfile;
}


/**
 * 查看/编辑  事项
 */
function deliverInfoEdit(selectRowId,type){
	//deliver_selectRow = $('#project_delivery_table').bootstrapTable('getRowByUniqueId', selectRowId);
	//var $self = $(this);
	//var _name= $self.attr("data-name");
	var _url = Constants.sopEndpointURL + '/galaxy/delivery/tomatterdeliver';

	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			
			_url = Constants.sopEndpointURL + '/galaxy/delivery/selectdelivery/'+selectRowId;
			sendGetRequest(_url, {}, function(data){
				var result = data.result.status;
				if(result == "OK"){
					var deliverInfo = data.entity;
					
					$("#deliver_form [name='delDescribe']").val(deliverInfo.delDescribe);
					$("#deliver_form [name='details']").text(deliverInfo.details);
					$("#deliver_form [name='delStatus'][value='"+deliverInfo.delStatus+"']").attr("checked",'checked');
					
					if(type == 'v'){
						$("#popup_name").html("查看事项信息");
						$("#choose_oper").remove();
						$("#choose_up_file").remove();
					}else if(type == 'e'){
						$("#popup_name").html("编辑事项信息");
						$("#deliver_form [name='id']").val(deliverInfo.id);
						$("#deliver_form [name='projectId']").val(proid);
					}
				}else{
					layer.msg(data.result.message);
				}
			});
		}
	});
	return false;
}


function save_deliver(){  
	var content = JSON.parse($("#deliver_form").serializeObject());
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
 */
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
}

function del_deliver(){  
	var id = $("#del_deliver_id").val();
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

