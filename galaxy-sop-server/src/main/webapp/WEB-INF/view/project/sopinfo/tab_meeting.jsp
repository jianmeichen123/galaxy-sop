<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>项目详情</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<!-- 富文本编辑器 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
</head>
</head>


<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
 	<div class="ritmin">
 	
    	<div class="new_tit_a"><a href="#">工作桌面</a>><a href="#">创投项目</a>>Utter绝对潮流</div>
    	
    	<div class="new_tit_b">
        	<span class="new_color size18">Utter绝对潮流</span><span class="new_color">ID987786600009</span>
        	<span class="b_span"><a href="#">返回项目列表></a></span>
        </div>
        
        
        <div class="new_left">
        	<div class="tabtable assessment label_static">
          	<!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">基本信息</a></li>
                <li data-tab="nav"><a href="javascript:;">团队成员</a></li>
                <li data-tab="nav"><a href="javascript:;">股权结构</a></li>
                <li data-tab="nav"><a href="javascript:;">访谈记录</a></li>
                <li data-tab="nav" class="on"><a href="javascript:;">会议纪要</a></li>
                <li data-tab="nav"><a href="javascript:;">项目文档</a></li>
                <li data-tab="nav"><a href="javascript:;">操作日志</a></li>
            </ul>

            
             <!-- 会议纪要
            <div data-tab="con" > 
              -->
            	<div class="tabtable_con">
            	
                   <!--按钮-->
					<!-- <div class="top clearfix"> -->
					<div class="new_r_compile">
						<div class="btnbox_f btnbox_f1 clearfix">
							<a href="#" onclick="toAddProMeet();" class="pubbtn lpubbtn bluebtn ico c4">添加会议纪要</a>
						</div>
					</div>

					<!-- 会议信息 -->
					<div id="custom-toolbar">
						<input type="hidden" id="projectId" name="projectId" value="">   <!-- 项目id -->
						
						<dl class="fmdl fmmr fmdll clearfix">
							<dt>会议类型：</dt>
							<dd class="clearfix">
								<select id="meetingType" name="meetingType" >
									<option value="">全部</option>
									<option value="meetingType:1">内评会</option>
									<option value="meetingType:2">CEO评审</option>
									<option value="meetingType:3">立项会</option>
									<option value="meetingType:4">投决会</option>
                    			</select>
							</dd>
						</dl>

						<dl class="fmdl fmmr fmdll clearfix">
							<dt>会议日期：</dt>
							<dd>
								<input type="text" class="datepicker txt time" readonly id="startTime" name="startTime" style="height: 23px;" /> 
								<span>至</span>
								<input type="text" class="datepicker txt time" readonly id="endTime" name="endTime" style="height: 23px;" />
							</dd>
							<dd>
								<a href="javascript:;" class="bluebtn ico cx" action="querySearch">查询</a>
							</dd>
						</dl>
					</div>
					
					<table id="data-table"
						data-url="<%=path%>/galaxy/project/progress/queryMeet" 
						data-id-field="id" 
						data-toolbar="#custom-toolbar">
						<thead>
							<tr>
								<th data-field="meetinfo" data-align="center" data-formatter="metcolumnFormat">会议概况</th>
								<th data-field="meetingTypeStr" data-align="center" >会议类型</th>
								<th data-field="meetingNotes" data-align="center" data-formatter="tc_viewNotesFormat_noinfo">会议纪要</th>
								<th data-field="oper" data-align="center" data-formatter="meetOperFormat">操作</th>
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
<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>



<script>

	var proinfo = '${proinfo}';
	//proinfo = JSON.parse(proinfo);
	var proid = '${pid}';
	var pname = '${pname}';
	var selectRow = null;
	

$(function(){
	createMenus(5);
	
	$("#projectId").val(proid);
	
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		pageList : [5, 10, 20 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
		uniqueId: "id", 
		idField : "id",
		clickToSelect: true,
        search: false,
	});
	
	$('[data-on="compile"]').on('click', function() {
		$('.bj_hui_on').show();
		$('.compile_on').show();
	})
	$('[data-on="close"]').on('click', function() {
		$('.bj_hui_on').hide();
		$('.compile_on').hide();
	})
	
});	
	
	
	

/**
 * 添加接触访谈纪要弹出层
 */
function toAddProMeet(){
	var _url=Constants.sopEndpointURL + '/galaxy/project/progress/meetAddView';
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#proselect").remove();
			initMeetUpload();
			//$('.edui-container').show();
		}
	});
	return false;
}



//plupload 上传对象初始化, 绑定保存saveMeetFile
function initMeetUpload() {
	// 定义 上传插件 方法 、  plupload 上传对象初始化
	var meetuploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#file-select-btn")[0], // you can pass in id...
		url : platformUrl.stageChange,
		multipart:true,
		multi_selection:false,
		filters : {
			max_file_size : '25mb',
			mime_types: paramsFilter(1)
		},

		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function(up) {
				$("#savemeet").click(function(){
					$("#savemeet").addClass("disabled");
					var res = getMeetCondition("y",proid, "meetingDateStr", null,"meetingTypeTc", "meetingResult","meetingNotes");
					if(res == false || res == "false"){
						up.stop();
						$("#savemeet").removeClass("disabled");
						return;
					}
					if(res.meetingType == 'meetingType:1'){
						res.stage = "projectProgress:2";
					}else if(res.meetingType == 'meetingType:2'){
						res.stage = "projectProgress:3";
					}else if(res.meetingType == 'meetingType:3'){
						res.stage = "projectProgress:4";
					}else if(res.meetingType == 'meetingType:4'){
						res.stage = "projectProgress:7";
					}
					
					var file = $("#fileName").val(); //up.files.length
					if(file.length > 0){
						up.settings.multipart_params = res;
						meetuploader.start();
					}else{
						sendPostRequestByJsonObj(platformUrl.saveMeetFile,res,function(data){
							var result = data.result.status;
							if(result == "ERROR"){ //OK, ERROR
								$("#savemeet").removeClass("disabled");
								layer.msg(data.result.message);
								return;
							}else{
								layer.msg("保存成功", {time : 500});
								var _this = $("#data-table");
								if(_this == null || _this.length == 0 || _this == undefined){
									removePop1();
								}else{
									$("#data-table").bootstrapTable('refresh');
									removePop1();
								}
							}
						});
					}
					return false;
				});
			},
			
			FilesAdded: function(up, files) {
				if(meetuploader.files.length >= 2){
					meetuploader.splice(0, meetuploader.files.length-1)  //解决多次文件选择后，文件都存入upload
				}
				plupload.each(files, function(file) {
					$("#fileName").val(file.name);
				});
			},
			
			UploadProgress: function(up, file) {
			},
			
			
			FileUploaded: function(up, files, rtn) {  //文件上传后回掉
				var response = $.parseJSON(rtn.response);
				var rs = response.result.status;
				if(rs == "ERROR"){ //OK, ERROR
					$("#savemeet").removeClass("disabled");
					$("#fileName").val("");
					meetuploader.splice(0, meetuploader.files.length)
					layer.msg(response.result.message);
					return;
				}else{
					layer.msg("保存成功", {time : 500});
					var _this = $("#data-table");
					if(_this == null || _this.length == 0 || _this == undefined){
						removePop1();
					}else{
						$("#data-table").bootstrapTable('refresh');
						removePop1();
					}
				}
			},
			BeforeUpload:function(up){
			},
			Error: function(up, err) {
				$("#savemeet").removeClass("disabled");
				$("#fileName").val("");
				layer.msg(err.message);
			}
		}
	});

	meetuploader.init();
}




function meetOperFormat(value,row,index){
	var info = "<a href=\"javascript:;\" class=\"fffbtn  option_item_mark\"  onclick=\"notesInfoEdit('"+row.id+"','v')\" >查看<a>";
	var edit = "";
	
	if(userId==row.uid){
		edit = "   <a href=\"javascript:;\" class=\"fffbtn  option_item_mark\"  onclick=\"notesInfoEdit('"+row.id+"','e')\" >编辑<a>";
	}
	return info + edit;
}


function notesInfoEdit(selectRowId,type){
	selectRow = $('#data-table').bootstrapTable('getRowByUniqueId', selectRowId);
	var _url = Constants.sopEndpointURL+"/galaxy/project/progress/interViewLog";
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			var um=UM.getEditor('viewNotes');
			um.setContent(selectRow.meetingNotes);
			if(type == 'v'){
				$("#interviewsave").remove();
			}
		}
	});
	return false;
}

function interviewsave(){  
	var um = UM.getEditor('viewNotes');
	var log = um.getContent();
	var id= selectRow.id;
	if(id != ''){
		sendPostRequestByJsonObj(platformUrl.updateMeet, {"id" : id, "meetingNotes" : log}, function(data){
			if (data.result.status=="OK") {
				layer.msg("保存成功");
				removePop1();
				$("#data-table").bootstrapTable('refresh');
			} else {
				layer.msg(data.result.message);
			}
			
		});
	}
}


	


	
	
</script>
</html>


