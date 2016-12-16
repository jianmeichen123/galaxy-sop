//全局变量
var hasClosed=false;
var canUseBut = false;
var canToOption = false;
var code = "0";
/*
 * 查看项目阶段详情的弹出层
 */
var alertid="";
var projectId;
var proDef = $.Deferred();
function info(id){
	projectId = id;
	var _url = Constants.sopEndpointURL + '/galaxy/ips';
	alertid=id;
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			$(".myprojecttc .tabtable").tabchange5();
			$('.searchbox').toggleshow();
			leicj();
			/**
			 * 加载项目详情数据   meetingResult:3
			 */
			sendGetRequest(platformUrl.detailProject + id, {}, function(data){
				hasClosed = (data.entity.projectStatus == 'meetingResult:3' || data.entity.projectStatus == 'projectStatus:2' || data.entity.projectStatus == 'projectStatus:3');
				var updatedTime = Number(data.entity.createdTime).toDate().format('yyyy-MM-dd');
				
				var isGreenChannel_6 = "false";
				if(data.entity.greanChannel && data.entity.greanChannel != null && data.entity.greanChannel.indexOf("6")!=-1){
					isGreenChannel_6 = "true";
				}
				
				
				//项目的最新动态
				if(data.entity.hasOwnProperty('updatedTime')){
					updatedTime = Number(data.entity.updatedTime).toDate().format('yyyy-MM-dd');
				}
				if(fileGrid){
					fileGrid.initFlag = false;
				}
				$("#pj-title-updated-time").html('<span>&#40;</span>'+updatedTime+'<span>&#41;</span>');
				//设置全局参数
				$("#project_name").text(data.entity.projectName);
				$("input[name='projectId']").val(data.entity.id);
				$("#project_id").val(id);
				//是否允许当前人进行SOP流转操作
				if(parseInt(data.entity.createUid) == parseInt(userId)){
					canToOption = true;
				}else{
					canToOption = false;
				}
				//解析元素id和项目阶段值，以便之后做控制
				var progress = data.entity.projectProgress;
				progress = progress.replace(":","_");
				var index = progress.substr("projectProgress_".length);

				//if(index == 1 || index == 3 || index == 4 || index == 6 || index == 7 ){//尽职调查去掉文件验证
				if(index == 1 || index == 3 || index == 4 || index == 7 ){
					checkCanUse(index,data.entity.id,data.entity.projectType);
				}
				for(var i = 1; i<11; i++){
					//当前阶段之后的tab变为不可用
					if(i > index){
						$("#projectProgress_" + i).addClass("disabled");
						$("#projectProgress_" + i).attr("disabled","disabled");
					}
					/**
					 * sop弹框在被弹出后，对当前Tab进行控制
					 */
					if(i == 1){
						if(hasClosed){
							$("#options_point1").remove();
						}
						if(!canUseBut){
							$("#qdnbps").remove();
						}
						//加载访谈接触记录的分页数据
						tiggerTable($("#" + progress + "_table"),3);
					}
					if(i == 2){
						if(hasClosed){
							$("#options_point2").remove();
						}
					}
					if(i == 3){
						if(hasClosed){
							$("#options_point3").remove();
						}
						if(!canUseBut){
							$("#lxhpq").remove();
							if(code == '100'){
								$("#add_ceomeet").remove();
							}else if(code == '101'){
								$("#applyCeoMeeting").remove();
							}
						}else{
							$("#add_ceomeet").remove();
							$("#applyCeoMeeting").remove();
						}
						
					}
					if(i == 4){
						if(hasClosed){
							$("#options_point4").remove();
						}
						if(!canUseBut){
							$("#reset_btn").remove();
						}else{
							$("#add_lxhmeet").remove();
						}
					}
					if(i == 5){
						tzyxs(0);
					}
					if(i == 6){
						if(hasClosed){
							$("#jzdc_options").remove();
						/*}//尽职调查去掉文件验证
						if(!canUseBut){*/
							$("#tjhsqBut").remove();
						}
						jzdc();
					}
					if(i == 7){
						if(hasClosed){
							$("#options_point7").remove();
						}
						if(!canUseBut){
							$("#inSure_btn").remove();
						}else{
							$("#add_tjhmeet").remove();
						}
					}
					if(i == 8){
						if(hasClosed){
							$("#tzxy_options").remove();
						}
						tzxy(data.entity.stockTransfer,data.entity.projectType);
					}
					if(i == 9){
						gqjg();
					}
					
					//为Tab添加点击事件，用于重新刷新
					$("#projectProgress_" + i).on("click",function(){
						var id = $(this).attr("id");
						var indexNum = id.substr(id.length-1,1);
						if(indexNum == '1'){
							if(parseInt(indexNum) < parseInt(index) || !canUseBut){
								$("#qdnbps").remove();
							}
						    $("#projectProgress_1_con").css("display","block");
							tiggerTable($("#projectProgress_1_table"),3);
						}else if(indexNum == '2'){
						    $("#projectProgress_2_con").css("display","block");
							tiggerTable($("#projectProgress_2_table"),3);
							if(index != '2'){
								$("#options_point2").remove();
							}
						}else if(indexNum == '3'){
							if(parseInt(indexNum) < parseInt(index) || !canUseBut){
								$("#lxhpq").remove();
							}
							$("#projectProgress_3_con").css("display","block");
							tiggerTable($("#projectProgress_3_table"),3);
							if(index != '3'){
								$("#options_point3").remove();
							}else if(canUseBut){
								$("#add_ceomeet").remove();
							}
						} else if(indexNum == '4'){
							$("#projectProgress_4_con").css("display","block");
							if(parseInt(indexNum) < parseInt(index) || !canUseBut){
								$("#reset_btn").css("display","none");
							}
						    tiggerTable($("#projectProgress_4_table"),3);
						    if(index != '4'){
						    	$("#options_point4 .toggle").remove();
						    }else if(canUseBut){
						    	$("#add_lxhmeet").remove();
						    }
						} else if(indexNum == '5'){
							$("#projectProgress_7_con").css("display","none");
							$("#projectProgress_5").addClass("on");
							$("#projectProgress_5_con").css("display","block");
							if(parseInt(indexNum) < parseInt(index)){
								tzyxs(1);
							}else{
								tzyxs(0);
							}
						}else if(indexNum == '6'){
							$("#projectProgress_5_con").css("display","none");
							 $("#projectProgress_6_con").css("display","block");
							 tiggerTable($("#projectProgress_6_table"),3);
							 
							 if(parseInt(indexNum) < parseInt(index)){
								 if(isGreenChannel_6 == "false"){
									 $("#scywjzdcbg").remove();
								 }
							 /*}else if(!canUseBut){*///尽职调查去掉文件验证
								$("#tjhsqBut").remove();
							}
							
						}else if(indexNum == '7'){
							$("#projectProgress_6_con").css("display","none");
							$("#projectProgress_7_con").css("display","block");
							if(parseInt(indexNum) < parseInt(index) || !canUseBut){
								$("#inSure_btn").css("display","none");
							}
							 tiggerTable($("#projectProgress_7_table"),3);
							 if(index != '7'){
							  $("#options_point7").remove();
							 }else if(canUseBut){
								 $("#add_tjhmeet").remove();
							 }
						}else if(indexNum == '8'){
							$("#projectProgress_7_con").css("display","none");
							$("#projectProgress_8_con").css("display","block");
							
							 if(parseInt(indexNum) < parseInt(index)){
								 $("#tzxy_options").remove();
							 }
							 tzxy(data.entity.stockTransfer,data.entity.projectType);
						}else if(indexNum == '9'){
							$("#projectProgress_8_con").css("display","none");
							$("#projectProgress_9").addClass("on");
							$("#projectProgress_9_con").css("display","block");
							gqjg();
						}
					});
				}
				$("#projectProgress").on("click",function(){
					$("#progress").addClass("on");
					$("#projectProgress_con").css("display","block");
					tiggerTable($("#projectProgress_table"),5);
					//$("#projectProgress_table").bootstrapTable("refresh");
				});
				$("#fileRepository").on("click",function(){
					$("#fileRepository").addClass("on");
					$("#file_repository").css("display","block");
					data = {
							_domid : "file_repository_table",
							_projectId : projectId,
							_progress : progress
					}
					fileGrid.init(data);
				});				
				$("#" + progress).addClass("on");
				$("#" + progress + "_con").css("display","block");
				//是否允许当前人进行SOP流转操作
				if(!canToOption){
					$(".option_item_mark").remove();
				}
				proDef.resolve();
				//立项报告列表
				$("#lx_report_table").bootstrapTable();
				initLxReportTable();
			},null);
		}
	});
	return false;


	
	
	
}
function infoCallBack(data){}
/**
 * 会议相关Tab页面按钮是否可用
 */
function checkCanUse(index,projectId,projectType){
	var condition = {};
	condition["index"] = index ;
	condition["projectId"] = projectId ;
	condition["projectType"] = projectType;
	sendGetRequest(platformUrl.checkCanUse,condition,function(data){
		var result = data.result.status;
		if(result == "OK"){ //OK, ERROR
			canUseBut = data.result.message;
			code = data.result.errorCode;
			if(canUseBut == true || canUseBut == "true"){
				canUseBut == true;
			}else{
				canUseBut == false;
			}
			return;
		}
	});
}

/**
 * 申请CEO评审排期
 */
function applyCeoMeeting(){
	var pid = projectId;
	if(pid != '' && pid != null && pid != undefined){
		sendGetRequest(platformUrl.inCeoMeetingPool + pid, {}, function(data){
			var result = data.result.status;
			if(result == "OK"){ 
				if($.isFunction(refreshProjectList))
				{
					refreshProjectList.call();
				}
				layer.msg("申请CEO评审会成功!");
				$("#powindow,#popbg").remove();
				info(pid);
			}else{
				layer.msg(data.result.message);
			}
		});
	}
}

	
/**
 * 添加接触访谈纪要弹出层
 */
function air(indexNum){
	$("[data-id='popid1']").remove();
	loadJs();
	var _url=Constants.sopEndpointURL + '/galaxy/air';
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(_this){
			$(".meetingtc").tabchange();
			$('.searchbox').toggleshow();
			leicj();
			//初始化文件上传
			toinitUpload(platformUrl.stageChange, projectId,"select_btn","file_object","save_interview","",
					function getSaveCondition(){
						var	condition = {};
						var pid = projectId;
						var viewDateStr = $("#viewDate").val();
						var viewTarget = $.trim($("#viewTarget").val());
						var um = UM.getEditor('viewNotes');
						var viewNotes = $.trim(um.getContent());
						var fileId = $("#viewfileID").val();
						if(pid == null || pid == ""){
							return;
						}
						if(viewDateStr == null ||  viewDateStr == ""){
							return false;
						}
						if(viewTarget == null ||  viewTarget == ""){
						$("#viewTarget").focus();
							return false;
						}

						$.popupTwoClose(); 
						condition.pid = pid;
						condition.stage = "projectProgress:1";
						condition.createDate = viewDateStr;
						condition.target = viewTarget;
						condition.content = viewNotes;
						condition.fileId = fileId;
						/*var	condition = {
							"pid" : pid,
							"stage" : "projectProgress:1",
							"createDate" : viewDate,
							"target" : viewTarget,
							"content" : viewNotes,
							"fileId" : fileId
						};*/
						return condition;
					},indexNum,null,_this);
		}
	});
	return false;
}

/**
 * 启动内部评审
 */
function startReview(){
	var pid = projectId;
	if(pid != '' && pid != null && pid != undefined){
		sendGetRequest(platformUrl.startReview + pid, {}, function(data){
			var result = data.result.status;
			if(result == "OK"){
				if($.isFunction(refreshProjectList))
				{
					refreshProjectList.call();
				}
				layer.msg("启动内部评审成功!");
				$("#powindow,#popbg").remove();
				info(pid);
			}else{
				var  _url=Constants.sopEndpointURL+"/galaxy/project/prompt?sid="+sessionId+"&guid="+userId;
			 	$.getHtml({
					url:_url,//模版请求地址
					data:"",//传递参数
					okback:function(){
			
					}//模版反回成功执行	
				});
				//layer.msg(data.result.message);
			}
		});
	}
}

/**
 * 上传会议记录
 */
 function addMettingRecord(num,meetingType){
		$("[data-id='popid1']").remove();
		var pid=projectId;
		loadJs();
		var _url=Constants.sopEndpointURL + '/galaxy/mr/';
		$.getHtml({
		url:_url+pid,//模版请求地址
		data:"",//传递参数
		okback:function(_this){
			$(".meetingtc").tabchange();
			$('.searchbox').toggleshow();
			leicj(meetingType);
			toinitUpload(platformUrl.stageChange,projectId, "meeting_select_btn","meeting_file_object","save_meeting","",
					function getSaveCondition(){
						var	condition = {};
						var pid = projectId;
						var meetingDateStr = $.trim($("#meeting_date").val());
						var meetingResult = $.trim($("input:radio[name='meetingResult']:checked").val());
						var um = UM.getEditor("meeting_notes");
						var meetingNotes = $.trim(um.getContent());
						if(pid == null || pid == ""){
							return;
						}
						if(meetingDateStr == null ||  meetingDateStr == ""){
							return;
						}
						if(meetingType == null ||  meetingType == ""){
							return;
						}
						if(meetingResult == null ||  meetingResult == ""){
							return;
						}
						$.popupTwoClose(); 
						condition.pid = pid;
						condition.stage = "projectProgress:"+num;
						condition.createDate = meetingDateStr;
						condition.meetingType = meetingType;
						condition.result = meetingResult;
						condition.content = meetingNotes;
						return condition;
					},
					num,
					function(){
						var meetingResult = $.trim($("input:radio[name='meetingResult']:checked").val());
						if('meetingResult:1' == meetingResult && $.isFunction(refreshProjectList))
						{
							refreshProjectList.call();
						}
					},
					_this
			);
		}
	});
	return false;
}

 /**
  * CEO评审阶段申请立项会排期
  */
function toEstablishStage(){
	var pid = projectId;
	if(pid != '' && pid != null && pid != undefined){
		sendGetRequest(platformUrl.toEstablishStage + pid, {}, function(data){
			var result = data.result.status;
			if(result == "OK"){ 
				if($.isFunction(refreshProjectList))
				{
					refreshProjectList.call();
				}
				layer.msg("申请立项会成功!");
				$("#powindow,#popbg").remove();
				info(pid);
			}else{
				layer.msg(data.result.message);
			}
		});
	}
}
 
/**
  * 立项会阶段申请立项会排期
  */
function toLxmeetingPool(){
	var pid = projectId;
	if(pid != '' && pid != null && pid != undefined){
		sendGetRequest(platformUrl.inLxmeetingPool + pid, {}, function(data){
			var result = data.result.status;
			if(result == "OK"){ 
				layer.msg("申请立项会成功!");
				$("#powindow,#popbg").remove();
				info(pid);
			}else{
				layer.msg(data.result.message);
			}
		});
	}
}

 /**
  * 动态生成投资意向书阶段HTML
  */
function tzyxs(flag){
	 var pid = projectId;
	 if(pid != '' && pid != null){
		 /**
		  *  生成尽职调查报告列表
		  */
		 sendGetRequest(Constants.sopEndpointURL + '/galaxy/project/progress/proFileInfo/'+pid+'/5',
				 null, function(data){
					 var json = eval(data);
					 var dataList=json.entityList;
					 for(var ii = 0 ; ii < dataList.length ; ii++){
						    var p = ii ;	
							var handlefile="";
							if(!hasClosed && canToOption){
								handlefile='<a href="javascript:;" onclick="downloadTemplate(\'templateType:1\');" class="pubbtn fffbtn llpubbtn">下载投资意向书模板</a>';
						        if (dataList[p].fileStatusDesc == "缺失") { 
						        	handlefile +='<td><a href="javascript:; " class="pubbtn fffbtn llpubbtn" onclick="addFile(5,0);">上传投资意向书</a></td>';
								}else{
									var fileSource =  dataList[p].fileSource;
									handlefile += '<td><a href="javascript:; " class="pubbtn fffbtn llpubbtn" onclick="updateSopFile('+'\''+dataList[p].projectProgress+'\','+fileSource+',\''+dataList[p].fileWorktype+'\',\''+dataList[p].fileType+'\','+dataList[p].id+","+0+')">更新投资意向书</a><a  href="javascript:; " class="pubbtn fffbtn lpubbtn" onclick="addFile(5,1);">上传签署凭证</a></td>';
								}
							}
							
					        var htmlhead = '<div id="tzyxs_options" class="btnbox_f btnbox_f1 btnbox_m clearfix">'+
					        	handlefile+'</div>'+
						        '<div class="process clearfix">'+
						        '<h2>投资意向书盖章流程</h2>'+
						        '<img src="'+Constants.sopEndpointURL+'img/process.png" alt="">'+
						        '</div>';
						        
						    var htmlstart=htmlhead+'<table width=\"100%" cellspacing="0" cellpadding="0" >'+
					             '<thead>'+ '<tr>'+ '<th style="padding-left:5px;">业务分类</th>'+ '<th>创建日期</th>'+
					             '<th>存储类型</th>'+ '<th>更新日期</th>'+ '<th>档案状态</th>'+
					             '<th>查看附件</th>'+ '</tr>'+ '</thead>'+ '<tbody>';
							var typehtml = "";
							if (typeof(dataList[p].fType) == "undefined") { 
								typehtml ='<td>未知</td>';
							}else{
								typehtml = '<td>'+dataList[p].fType+'</td>';
							}
							
							var endhtml ="";
							if (dataList[p].fileStatusDesc == "缺失") { 
								endhtml ='<td>缺失</td>';
							}else{
								endhtml = '<td><a href="javascript:;" onclick="filedown('+dataList[p].id+');" class="blue">查看</a></td>';
							}
							
							htmlstart +='<tr>'+
							'<td style="padding-left:5px;">'+dataList[p].fWorktype+'</td>'+
							'<td>'+dataList[p].createDate+'</td>'+
							typehtml
							+'<td>'+getVal(dataList[p].updatedDate,'')+'</td>'
							+'<td>'+dataList[p].fileStatusDesc+'</td>'+
							endhtml+
							'</tr>';   
									
						}
						var htmlend= '</tbody></table>';
						$("#projectProgress_5_con").html(htmlstart+htmlend);
						if(flag == 1){
				        	$("#tzyxs_options").remove();
				        }
				 });
	 }
 }
	 
/**
 * 上传文档
 */
 function addFile(num,i){
	 $("[data-id='popid1']").remove();
	 loadJs();
	var _url=Constants.sopEndpointURL + '/galaxy/tzyx';
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(_this){
			$(".meetingtc").tabchange();
			$('.searchbox').toggleshow();
			
			
			leicj();
			if(i == 1){
				$('.title_bj').html('上传签署凭证');
				$("#voucherType").attr("checked","checked");
				$("#voucherType").attr("disabled",true);
			}
			else
			{
				$('.title_bj').html('上传投资意向书');
				$("#voucherDiv").css("display","none");
				
			}
			toinitUpload(platformUrl.stageChange, projectId,"select_file_btn","file_obj","save_file_btn","fileType",
					function getSaveCondition(){
				var	condition = {};
				var pid = projectId;
				if(pid == null || pid == ""){
					return;
				}
				var type = $("input[name='fileSource']:checked").val();
				if(type == null || type == ""){
					return;
				}
				var fileType = $("#fileType").val();
				if(fileType == null || fileType == ""){
					return;
				}
				var fileWorktype = $("#fileWorkType").val();
				if(fileWorktype == null || fileWorktype == ""){
					return;
				}
				var voucherType = $("input[id='voucherType']:checked").val();
				condition.pid = pid;
				condition.stage = "projectProgress:"+num;
				condition.type = type;
				condition.fileType = fileType;
				condition.fileWorktype = fileWorktype;
				condition.voucherType = voucherType;
				return condition;
			},
			null,
			function(){
				if(i == 1 && $.isFunction(refreshProjectList)){
					refreshProjectList.call();
				}
			},_this);
		}
	});
	return false;
}
/**
 * 更新文件：投资意向书、业务尽职调查、投资协议
 * @param stage  项目阶段
 * @param fileSource 文件来源：内部|外部
 * @param fileWorkType 业务分类
 * @param fileType 档案类型：文档|图片...
 * @param id 文件id:sopfile主键
 * @param voucher 是否有签署证明显示 0:无;1:有
 */
function updateSopFile(stage,fileSource,fileWorkType,fileType,id,voucher){
	$("[data-id='popid1']").remove();
	loadJs();
	//投资意向书页面
	var _url=Constants.sopEndpointURL + '/galaxy/tzyx';
	//弹出尽职调查页面
	if(stage == "projectProgress:6"){
		_url=Constants.sopEndpointURL + '/galaxy/jzdc';
	}
	//投资协议页面
	if(stage == "projectProgress:8"){
		_url=Constants.sopEndpointURL + '/galaxy/tzxy';
	}
	//股权转让协议页面
	if(fileWorkType == "fileWorktype:7"){
	    _url=Constants.sopEndpointURL + '/galaxy/gqzr';
	}
	$.getHtml({
		url:_url,
		okback:function(_this){
			if(voucher == 1){
				$("#voucherType").attr("disabled",true);
				$("#voucherType").attr("checked","checked");
			}else{
				$("#voucherDiv").css("display","none");
			}
			if(fileWorkType=='fileWorktype:1'){
				$('.title_bj').html('更新业务尽职调查报告');
			}
			if(fileWorkType=='fileWorktype:5'){
				$('.title_bj').html('更新投资意向书');
			}else{
				$('.title_bj').html('更新上传文件');
			}
			$("input[name='fileSource'][value='"+fileSource+"']").attr("checked",true);
			$("#fileType").val(fileType);
			$("#fileWorkType").val(fileWorkType);
//			var _this = this;
			var uploader = $.fxUpload({
				props:{
					browse_button:'select_file_btn',
					url:platformUrl.updateFile,
					init:{
						PostInit: function(up) {
							$("#save_file_btn").click(function(){
								if(up.files.length == 0)
								{
									layer.msg("请选择文件.");
									return;
								}
								uploader.start();
							});
						},
						FilesAdded: function(up, files) {
							
							/* if(up.files.length > 1){
								up.splice(0, ip.files.length-1)
							} */
							//解决多次文件选择后，文件都存入upload
							if(uploader.files.length >= 1){
								uploader.splice(0, uploader.files.length-1)
							}
							$.each(files, function(i,o) {
								$("#file_obj").val(this.name);
								var arr = new Array();
								arr = this.name.split(".");
								var type="";
								if(arr){
									type=arr[1];
								}
								var filtersparams=paramsFilter(null);
								for(var i=0;i<filtersparams.length;i++){
									var value=filtersparams[i];
									var valueExt=value.extensions;
									if(valueExt.indexOf(type) >= 0 ){
										var myvalue=value.title;
										$("#fileType").val(myvalue);
									}
								}
							});
						},
						BeforeUpload:function(up){
							
							$(_this.id).showLoading(
									 {
									    'addClass': 'loading-indicator'						
									 });
							
							var condition = {};
							var pid = projectId;
							var type = $("input[name='fileSource']:checked").val();
							if(type == null || type == ""){
								layer.msg("档案来源不能为空");
								up.stop();
								return;
							}
							var fileType = $("#fileType").val();
							if(fileType == null || fileType == ""){
								layer.msg("存储类型不能为空");
								up.stop();
								return;
							}
							var fileWorktype = $("#fileWorkType").val();
							if(fileWorktype == null || fileWorktype == ""){
								layer.msg("业务分类不能为空");
								up.stop();
								return;
							}
							if(voucher == 1){
								var voucherType = $("input[id='voucherType']:checked").val();
								condition.voucherType = voucherType;
							}
							//投资协议阶段所用
							if(stage == "projectProgress:8"){
								var hasStockTransfer = $("input[id='stock_transfer']:checked").val();
								condition.hasStockTransfer=hasStockTransfer;
							}
							condition.pid = pid;
							condition.stage = stage;
							condition.type = type;
							condition.fileType = fileType;
							condition.fileWorktype = fileWorktype;
							condition.id = id;
							up.settings.multipart_params=condition;
						},
						FileUploaded: function(up, files, rtn) {
							$(_this.id).hideLoading();
							var data = $.parseJSON(rtn.response);
							
							if(data.result.status == "OK")
							{
								layer.msg(data.result.message);
								var pid = projectId
								$("#powindow,#popbg").remove();
								info(pid);
								$.popupTwoClose();
							}
							else
							{
								layer.msg("上传失败.");
							}
						}
					}
				}
			});
		}
		
	});
} 
 /**
  * 尽职调查
  */
 function jzdc(){
	 
	 var pid = projectId;
	 if(pid != '' && pid != null){
		 /**
		  *  生成尽职调查报告列表
		  */
		 sendGetRequest(
				 Constants.sopEndpointURL + '/galaxy/project/progress/proFileInfo/'+pid+'/6', 
				 null, function(data){
			 var html = "";
			 $.each(data.entityList, function(i,o){
				 html += "<tr>";
				 if(o.fileWorktype == 'fileWorktype:1'){
					 html += "<td style='padding-left:5px;'>业务尽职调查报告";
					 html += "</td><td>" +getVal(o.updatedDate,o.createDate)+ "</td>";
					 html += "<td>"+o.careerLineName+"</td>";
				 }else if(o.fileWorktype == 'fileWorktype:2'){
					 html += "<td  style='padding-left:5px;'>人事尽职调查报告";
					 html += "</td><td>" + getVal(o.updatedDate,o.createDate) + "</td>";
					 html += "<td>人事部</td>";
				 }else if(o.fileWorktype == 'fileWorktype:3'){
					 html += "<td  style='padding-left:5px;'>法务尽职调查报告";
					 html += "</td><td>" + getVal(o.updatedDate,o.createDate) + "</td>";
					 html += "<td>法务部</td>";
				 }else if(o.fileWorktype == 'fileWorktype:4'){
					 html += "<td  style='padding-left:5px;'>财务尽职调查报告";
					 html += "</td><td>" + getVal(o.updatedDate,o.createDate) + "</td>";
					 html += "<td>财务部</td>";
				 }
				 if(o.fileStatus == 'fileStatus:1' || o.fileValid == '0'){
					 html += "<td  style='padding-left:5px;'>未知</td><td>缺失</td>";
					 if(o.fileWorktype != 'fileWorktype:1' && canToOption){
						 html +='<td><a href="javascript:; " onclick="taskUrged('+o.id+');"class="blue">催办 </a></td>';
					 }else{
						 html += "<td></td>";
					 }
					 html += "<td>无</td>";
				 }else if(o.fileStatus == 'fileStatus:2'){
					 if(o.fileWorktype == 'fileWorktype:1'){
						 $("#jzdc_options a:eq(1)").text('更新业务尽职调查报告');
						 $("#jzdc_options a:eq(1)").attr("onclick","updateSopFile('"+o.projectProgress+"',"+o.fileSource+",'"+o.fileWorktype+"','"+o.fileType+"',"+o.id+","+0+")");
					 }
					 html += "<td>"+o.fType+"</td>";
					 html += "<td>已上传</td>";
					 html += "<td></td>";
					 html += "<td><a href='javascript:filedown("+o.id+");'  class='blue'>查看</a></td>";
				 }else if(o.fileStatus == 'fileStatus:3'){
					 html += "<td>已签署</td>";
					 html += "<td></td>";
					 html += "<td><a href='javascript:void(0);'>" + o.fileName + "</a></td>";
				 }
				 html += "</tr>";
		   	 });
			 $("#fileList").append(html);
		 });
	 }
}
 /**
 * 点击上传业务尽调报告按钮
 */
function uploadYwjd(){
	$("[data-id='popid1']").remove();
	loadJs();
	var _url=Constants.sopEndpointURL + '/galaxy/jzdc';
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(_this){
			$('.title_bj').html('上传业务尽调报告')
			$(".meetingtc").tabchange();
			$('.searchbox').toggleshow();
			leicj();
			toinitUpload(platformUrl.businessAdjustment,projectId, "select_file_btn","file_obj","save_file_btn","fileType",
					function getSaveCondition(){
				var	condition = {};
				var pid = projectId;
				if(pid == null || pid == ""){
					return;
				}
				var type = $("input[name='fileSource']:checked").val();
				if(type == null || type == ""){
					return;
				}
				var fileType = $("#fileType").val();
				if(fileType == null || fileType == ""){
					return;
				}
				var fileWorktype = $("#fileWorkType").val();
				if(fileWorktype == null || fileWorktype == ""){
					return;
				}
				condition.pid = pid;
				condition.stage = "projectProgress:6";
				condition.type = type;
				condition.fileType = fileType;
				condition.fileWorktype = fileWorktype;
				return condition;
			},null,null,_this);
		}
	});
	return false;
}
/**
 * 尽职调查--点击申请投决会按钮
 */
function inTjh(){
	var pid = projectId;
	if(pid != '' && pid != null && pid != undefined){
		sendGetRequest(
				platformUrl.inTjh + pid,
				null,
				function(data){
					var result = data.result.status;
					if(result == "OK"){ 
						if($.isFunction(refreshProjectList))
						{
							refreshProjectList.call();
						}
						layer.msg("申请成功!");
						$("#powindow,#popbg").remove();
						info(pid);
					}else{
						layer.msg(data.result.message);
					}
				});
	}
}
/**
 * 投决会--点击申请投决会按钮
 */
function inSureMeetingPool(){
	var pid = projectId;
	if(pid != '' && pid != null && pid != undefined){
		sendGetRequest(
				platformUrl.inSureMeetingPool + pid,
				null,
				function(data){
					var result = data.result.status;
					if(result == "OK"){ 
						layer.msg("申请成功!");
						$("#powindow,#popbg").remove();
						info(pid);
					}else{
						layer.msg(data.result.message);
					}
				});
	}
}
/**
 * 动态生成投资协议的HTML
 */
function tzxy(st,projectType){
	//0:首次展示 1：点击触发刷新
	var pid = projectId;
	if(pid != '' && pid != null){
		var _table = $("#teamSeheetDataGrid");
		var _tbody = _table.find("tbody");
		_tbody.empty();
		sendPostRequestByJsonObj(
				platformUrl.searchSopFileListWithoutPage,
				{"projectId" : pid},
				function(data){
					
					_tbody.empty();
					//前置文件是否丢失
					var hasTzxy = false,
						hasTzxyQspz = false,
						hasGqxy = false;
					$.each(data.entityList,function(i,o){
						
							var $tr=$('<tr></tr>');
							
							//页面初始化
							if(this.fileWorktype == 'fileWorktype:7' && st==0){
								$tr.attr("id","gwxt_tr").css("display","none");
							}else if(this.fileWorktype == 'fileWorktype:7' && st==1){
								$tr.attr("id","gwxt_tr");
							}
							
							$tr.append('<td>'+this.fWorktype+'</td>') ;
							if(this.fileType){
								$tr.append('<td>'+this.fType+'</td>');
								$tr.append('<td>'+this.updatedDate+'</td>') ;
							}else{
								$tr.append('<td>未知</td>');
								$tr.append('<td>无</td>') ;
							}	
							$tr.append('<td>'+this.fileStatusDesc+'</td>');
							if(this.fileWorktype == 'fileWorktype:6'){
								if(canToOption){
									var e6 ="downloadTemplate('templateType:2');";
									$tr.append('<td><a class="blue" href="javascript:void(0);" onclick="'+e6+'">下载</a></td>');
								}else{
									$tr.append('<td></td>');
								}
								if(this.fileKey == null){
									if(canToOption){
										$tr.append('<td><a href="javascript:;" onclick="tzxyAlert(8,0);" class="blue">上传</a></td>');
									}else{
										$tr.append('<td></td>');
									}
									$tr.append('<td>无</td>');
								}else{
									hasTzxy = true;
									if(canToOption){
										$tr.append('<td><a href="javascript:;" onclick="updateSopFile('+'\''+this.projectProgress+'\','+this.fileSource+',\''+this.fileWorktype+'\',\''+this.fileType+'\','+this.id+","+0+')" class="blue">更新</a></td>');
									}else{
										$tr.append('<td></td>');
									}
									$tr.append('<td><a href="javascript:;" onclick="filedown('+this.id+');" class="blue">查看</a></td>');	
								}
								if(this.voucherFileKey == null){
									if(canToOption && hasTzxy){
										$tr.append('<td><a href="javascript:;" onclick="tzxyAlert(8,1);" class="blue">上传</a></td>');
									}else{
										$tr.append('<td></td>');
									}
								}else{
									hasTzxyQspz = true;
									$tr.append('<td><a href="javascript:;" onclick="filedown('+this.voucherId+',null,\'voucher\'); " class="blue">查看</a></td>'); 	
								}
							}else if(this.fileWorktype == 'fileWorktype:7'){
								if(canToOption){
									var e7 ="downloadTemplate('templateType:7');";
									$tr.append('<td><a class="blue" href="javascript:void(0);" onclick="'+e7+'">下载</a></td>');
								}else{
									$tr.append('<td></td>');
								}
								if(this.fileKey == null){	
									if(canToOption){
										$tr.append('<td><a href="javascript:;" onclick="gqzrAlert(8,0);" class="blue">上传</a></td>');
									}else{
										$tr.append('<td></td>');
									}
									$tr.append('<td>无</td>');
								}else{
									hasGqxy = true;
									if(canToOption){
										$tr.append('<td><a href="javascript:;" onclick="updateSopFile('+'\''+this.projectProgress+'\','+this.fileSource+',\''+this.fileWorktype+'\',\''+this.fileType+'\','+this.id+","+0+')" class="blue">更新</a></td>');
									}else{
										$tr.append('<td></td>');
									}
									$tr.append('<td><a href="javascript:;" onclick="filedown('+this.id+'); " class="blue">查看</a></td>'); 	
								}
								if(this.voucherFileKey == null){	
									if(canToOption && hasGqxy && hasTzxyQspz){
										$tr.append('<td><a href="javascript:;" onclick="gqzrAlert(8,1);" class="blue">上传</a></td>');
									}else{
										$tr.append('<td></td>');
									}
								}else{
									$tr.append('<td><a href="javascript:;" onclick="filedown('+this.voucherId+',null,\'voucher\');" class="blue">查看</a></td>'); 	
								}
							}
							_tbody.append($tr);
							//涉及股权转让
							if(st == 1){
								$("#stock_transfer").attr("checked","checked");
								if((this.fileWorktype == 'fileWorktype:6' && this.fileKey != null) || (this.fileWorktype == 'fileWorktype:7' && this.fileKey != null)){
									$("#stock_transfer").attr("disabled","true");
								}
							}
					});
				}
		);	
		if(projectType == 'projectType:2'){
			$("#stock_transfer_model").remove();
		}
	}
	 tiggerTable($("#projectProgress_7_table"),3);
}
 
/**
 * 投资协议弹出层
 */
 function tzxyAlert(num,i){
	 $("[data-id='popid1']").remove();
	 loadJs();
	var _url=Constants.sopEndpointURL + '/galaxy/tzxy';
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(_this){
			$(".meetingtc").tabchange();
			$('.searchbox').toggleshow();
			leicj();
			if(i == 1){
				$('.title_bj').html('上传签署凭证')
				$("#voucherType").attr("disabled",true);
				$("#voucherType").attr("checked","checked");
			}else{
				$("#voucherDiv").css("display","none");
			}
			toinitUpload(platformUrl.stageChange,projectId, "select_file_btn","file_obj","save_file_btn","fileType",
					function getSaveCondition(){
				var	condition = {};
				var pid = projectId;
				if(pid == null || pid == ""){
					return;
				}
				var type = $("input[name='fileSource']:checked").val();
				if(type == null || type == ""){
					return;
				}
				var fileType = $("#fileType").val();
				if(fileType == null || fileType == ""){
					return;
				}
				var fileWorktype = $("#fileWorkType").val();
				if(fileWorktype == null || fileWorktype == ""){
					return;
				}
				var voucherType = $("input[id='voucherType']:checked").val();
				var hasStockTransfer = $("input[id='stock_transfer']:checked").val();
				condition.pid = pid;
				condition.stage = "projectProgress:"+num;
				condition.type = type;
				condition.fileType = fileType;
				condition.fileWorktype = fileWorktype;
				condition.voucherType = voucherType;
				condition.hasStockTransfer=hasStockTransfer;
				return condition;
			},
			null,
			function(){
				if(i==1 && $.isFunction(refreshProjectList))
				{
					refreshProjectList.call();
				}
			},_this);
		}
	});
	return false;
}

/**
 * "是否涉及股权转让"按钮点击事件
 */
function selected(obj){
	var pid = projectId;
	if(pid != '' && pid != null && pid != undefined){
		sendGetRequest(
				platformUrl.storeUrl + pid,
				null,
				function(data){
				});
	}
	
	if(obj.checked){
		$("#gwxt_tr").css("display","table-row");
	}else{
		$("#gwxt_tr").css("display","none");
	}
	
}
 /**
  * 股权转让协议弹出层
  */
 function gqzrAlert(num,i){
	 $("[data-id='popid1']").remove();
	 loadJs();
	var _url=Constants.sopEndpointURL + '/galaxy/gqzr';
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(_this){
			$(".meetingtc").tabchange();
			$('.searchbox').toggleshow();
			leicj();
			if(i == 1){
				$("#voucherType").attr("disabled",true);
				$("#voucherType").attr("checked","checked");
				$('.title_bj').html('上传签署凭证');
			}else{
				$("#voucherDiv").css("display","none");
				
			}
			toinitUpload(platformUrl.stageChange,projectId, "select_file_btn","file_obj","save_file_btn","fileType",
					function getSaveCondition(){
				var	condition = {};
				var pid = projectId;
				if(pid == null || pid == ""){
					return;
				}
				var type = $("input[name='fileSource']:checked").val();
				if(type == null || type == ""){
					return;
				}
				var fileType = $("#fileType").val();
				if(fileType == null || fileType == ""){
					return;
				}
				var fileWorktype = $("#fileWorkType").val();
				if(fileWorktype == null || fileWorktype == ""){
					return;
				}
				var voucherType = $("input[id='voucherType']:checked").val();
				var hasStockTransfer = $("input[id='stock_transfer']:checked").val();
				condition.pid = pid;
				condition.stage = "projectProgress:"+num;
				condition.type = type;
				condition.fileType = fileType;
				condition.fileWorktype = fileWorktype;
				condition.voucherType = voucherType;
				condition.hasStockTransfer=hasStockTransfer;
				return condition;
			},
			null,
			function(){
				if(i==1 && $.isFunction(refreshProjectList))
				{
					refreshProjectList.call();
				}
			},_this);
		}
	});
	return false;
}
 
 /**
  * 动态生成股权交割的HTML
  */
function gqjg(){
	var pid = projectId;
	if(pid != '' && pid != null){
		sendGetRequest(
				platformUrl.getFileList + pid + "/9",
				null,
				function(data){
					var json = eval(data);
					 var dataList=json.entityList;
					 var htmlstart='<table width=\"100%" cellspacing="0" cellpadding="0" >'+
						 '<thead>'+'<tr>'+'<th>业务分类</th>'+'<th>创建日期</th>'+'<th>存储类型</th>'+
						 '<th>更新日期</th>'+'<th>催办</th>'+'<th>查看附件</th>'+'</tr>'+'</thead>'+'<tbody>';
					 
					 for(var ii = 0 ; ii < dataList.length ; ii++){
						    var p = ii ;	
						var typehtml = "";
						if (typeof(dataList[p].fType) == "undefined" || dataList[p].fileValid == '0') { 
							typehtml ='<td></td>';
						}else{
							typehtml = '<td>'+dataList[p].fType+'</td>';
						}
						
						var updateHtml = "";
						if(dataList[p].fileStatusDesc == "缺失" || dataList[p].fileValid == '0'){
							updateHtml = "<td></td>";
						}else{
							updateHtml = '<td>'+getVal(dataList[p].updatedDate,'')+'</td>';
						}
						
						
						var handlehtml = "";
						if ((dataList[p].fileStatusDesc == "缺失" || dataList[p].fileValid == '0') && !hasClosed && canToOption) { 
							handlehtml ='<td><a href="javascript:; " onclick="taskUrged('+dataList[p].id+');"class="blue">催办</a></td>';
						}else{
							handlehtml = '<td></td>';
						}
						
						var endhtml ="";
						if (dataList[p].fileStatusDesc == "缺失" || dataList[p].fileValid == '0') { 
							endhtml ='<td>缺失</td>';
						}else{
							endhtml = '<td><a href="javascript:; " onclick="filedown('+dataList[p].id+');" class="blue">查看</a></td>';
						}
						
						var updatedDate ="";
						if (dataList[p].updatedDate == null || dataList[p].updatedDate == "") { 
							updatedDate =dataList[p].createDate;
						}else{
							updatedDate = dataList[p].updatedData;
						}
						
						htmlstart +='<tr>'+
						'<td>'+dataList[p].fWorktype+'</td>'+
						'<td>'+dataList[p].createDate+'</td>'+
						typehtml+updateHtml+handlehtml+   
						endhtml+   
						'</tr>';   
					 }
					 var htmlend= '</tbody></table>';
					 $("#projectProgress_9_con").html(htmlstart+htmlend);
				});	
	}
}
/**
 * 格式化富文本保存的内容，以契合页面展示的要求

function ftcolumnFormat(value, row, index){
	var fileinfo = "" ;
	var rc = "";
	if( row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
	}
	
	var targetStr = row.viewTarget;
	var subStr = "";
	var targerHtml="";
	if(targetStr.length>6){
		subStr = targetStr.substring(0,6)+"...";
		targerHtml = "</br>访谈对象：<span title="+targetStr+">"+subStr+"</span>";
	}else{
		targerHtml = "</br>访谈对象："+targetStr;
	}
	
	rc = "<div style=\"text-align:left;margin-left:20%;padding:10px 0;\">"+
				"访谈时间："+row.viewDateStr+
				targerHtml+
				"</br>访谈录音："+fileinfo+
			"</div>" ;
	return rc;
}

 * 格式化富文本保存的内容，以契合页面展示的要求

function metcolumnFormat(value, row, index){
	var fileinfo = "";
	var rc = "";
	if(row.fileId != null && row.fileId != undefined && row.fileId != "undefined"){
		fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
	}
	rc = "<div style=\"text-align:left;margin-left:20%;padding:10px 0;\">"+
				"会议日期："+row.meetingDateStr+
				"</br>会议结论："+row.meetingResultStr+
				"</br>会议录音："+fileinfo+
			"</div>" ;
	return rc;
}
 */	
/**
 * sop阶段中富文本弹出层的静态资源加载
 */
function loadJs(){
	$("#f").attr("href",Constants.sopEndpointURL + "/ueditor/themes/default/css/umeditor.css");
	$("#a").attr("src",Constants.sopEndpointURL + "/js/plupload.full.min.js");
	$("#a").attr("src",Constants.sopEndpointURL + "/js/plupload/zh_CN.js");
	$("#b").attr("src",Constants.sopEndpointURL + "/ueditor/dialogs/map/map.js");
	$("#c").attr("src",Constants.sopEndpointURL + "/ueditor/umeditor.config.js");
	$("#d").attr("src",Constants.sopEndpointURL + "/ueditor/umeditor.min.js");
	$("#e").attr("src",Constants.sopEndpointURL + "/ueditor/lang/zh-cn/zh-cn.js");
	
}
/**
 * 催办
 */
function taskUrged(id) {
	var json= {"id":id};
	sendGetRequest(platformUrl.taskUrged, json, taskCallback);
}
function taskCallback(data) {
	if (data.result.status!="OK") {
		layer.msg("催办失败");
	} else {
		layer.msg(data.result.message);
	}
}
/**
 * sop中的下载文件
 */
function downFile(id){
	var pidParam = "";
	if(alertid>=0)
	{
		pidParam = "&projectId="+alertid;
	}
	var url = platformUrl.tempDownload+"?id="+id+pidParam;
	forwardWithHeader(url);
}
/**
 * sop中的下载模板
 */
function downloadTemplate(templateType)
{
	var pidParam = "";
	if(alertid>=0)
	{
		pidParam = "&projectId="+alertid;
	}
	var url = platformUrl.tempDownload+"?worktype="+templateType+pidParam;
	forwardWithHeader(url);
}
function showLogdetail(selectRowId){
	var interviewSelectRow = $('#projectProgress_1_table').bootstrapTable('getRowByUniqueId', selectRowId);
	var _url = Constants.sopEndpointURL+"/galaxy/project/progress/interViewLog";
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
		var um=UM.getEditor('viewNotes');
		um.setContent(interviewSelectRow.viewNotes);
		$("#vid").val(selectRowId);
		if(typeof(variable) !== 'undefined' && uid!=interviewSelectRow.createdId){
			$("#interviewsave").hide();
			um.setDisabled();
		}
		
	}//模版反回成功执行	
});
	return false;
}
function interviewsave(){  
		var um = UM.getEditor('viewNotes');
	var log = um.getContent();
	var pid=$("#vid").val();
	if(pid != '' && log != ''){
		sendPostRequestByJsonObj(platformUrl.updateInterview, {"id" : pid, "viewNotes" : log}, function(data){
			if (data.result.status=="OK") {
				$("#hint_all").css("display","none");
				layer.msg("保存成功");
				$(".meetingtc").find("[data-close='close']").click();
				$("#projectProgress_1_table").bootstrapTable('refresh');
			} else {
				layer.msg(data.result.message);
				$("#hint_all").css("display","block");
			}
			
		});
	}
}
