<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>

<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>我的项目</h2>
    	<input type="hidden" id="project_id" value=""/>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/app" class="pubbtn bluebtn ico c4">添加项目</a>
                <!-- <a href="编辑项目.html" class="pubbtn bluebtn ico c5">编辑</a> -->
            </div>
        </div>
        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="custom-toolbar">
			<div class="bottom searchall clearfix search_adjust">
				<dl class="fmdl fml fmdll clearfix">
	              <dt>项目类别：</dt>
	              <dd>
	                <select name="projectType">
	                  <option value="">全部</option>
	                  <option value="projectType:1">外部投资</option>
	                  <option value="projectType:2">内部创建</option>
	                </select>
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	              <dt>项目进度：</dt>
	              <dd>
	                <select name="projectProgress">
	                  <option value="">全部</option>
	                  <option value="projectProgress:1">接触访谈</option>
	                  <option value="projectProgress:2">内部评审</option>
	                  <option value="projectProgress:3">CEO评审</option>
	                  <option value="projectProgress:4">立项会</option>
	                  <option value="projectProgress:5">投资意向书</option>
	                  <option value="projectProgress:6">尽职调查</option>
	                  <option value="projectProgress:7">投资决策会</option>
	                  <option value="projectProgress:8">投资协议</option>
	                  <option value="projectProgress:9">股权交割</option>
	                  <!-- <option value="projectProgress:10">投后运营</option> -->
	                </select>
	              </dd>
	            </dl>
				<dl class="fmdl fmdll clearfix">
					<dt></dt>
					<dd>
						<input type="text" class="txt" name="nameCodeLike" placeholder="请输入项目名称或项目编码" />
					</dd>
					<dd>
						<button type="submit" class="bluebtn ico cx" action="querySearch">搜索</button>
					</dd>
				</dl>
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<table id="data-table" data-url="project/spl" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="projectCode" data-align="center" class="data-input">项目编码</th>
			        	<th data-field="projectName" data-align="center" class="data-input">项目名称</th>
			        	<th data-field="progress" data-align="center" class="data-input">项目进度</th>
			        	<th data-field="type" data-align="center" class="data-input">项目类型</th>
			        	<th data-field="createDate" data-align="center" class="data-input">创建日期</th>
			        	<th data-align="center" class="col-md-2" data-formatter="editor">操作</th>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
</div>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script id="a" src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/fx.upload.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/my_ext.js"></script>
<script src="<%=path %>/js/my.js"></script>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>

<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>




<script type="text/javascript" src="<%=path %>/js/teamSheet.js"></script>
<script type="text/javascript" src="<%=path %>/js/filerepository.js"></script>

<script type="text/javascript">

	createMenus(5);
	/**
	 * 分页数据生成操作内容
	 */
	function editor(value, row, index){
		var id=row.id;
		var options = "<a href='#' class='blue' data-btn='myproject' onclick='info(" + id + ")'>项目流程</a>";
		if(row.projectStatus != 'meetingResult:3'){
			options += "<a href='<%=path%>/galaxy/upp/"+id+"' class=\'blue\'>编辑项目</a>";
		}
		return options;
	}
	
	//全局变量
	var hasClosed=false;
	/**
	 * 查看项目阶段详情的弹出层
	 */
	 var alertid="";
		function info(id){
			alertid=id;
		var _url='<%=path%>/galaxy/ips';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".myprojecttc .tabtable").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				/**
				 * 加载项目详情数据
				 */
				sendGetRequest(platformUrl.detailProject + id, {}, function(data){
					hasClosed = (data.entity.projectStatus == 'meetingResult:3');
					var pp = data.entity.projectProgress;
					var pNum = pp.substr(pp.length-1,1);
					var updatedTime = Number(data.entity.createdTime).toDate().format('yyyy-MM-dd');
					if(data.entity.hasOwnProperty('updatedTime'))
					{
						updatedTime = Number(data.entity.updatedTime).toDate().format('yyyy-MM-dd');
					}
					$("#pj-title-updated-time").html('<span>&#40;</span>'+updatedTime+'<span>&#41;</span>');
					$("#project_name").text(data.entity.projectName);
					$("input[name='projectId']").val(data.entity.id);
					$("#project_id").val(id);
					var progress = data.entity.projectProgress;
					progress = progress.replace(":","_");
					var index = progress.substr(progress.length-1,1);
					
					for(var i = 1; i<10; i++){
						if(i > index){
							//当前阶段之后的tab变为不可用
							$("#projectProgress_" + i).addClass("disabled");
						}
						if(i == 1){
							if(hasClosed){
								$("#options_point1").remove();
							}
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
						}
						if(i == 4){
							if(hasClosed){
								$("#options_point4").remove();
							}
						}
						if(i == 5){
							tzyxs(0);
						}
						if(i == 6){
							if(hasClosed){
								$("#jzdc_options").remove();
							}
							jzdc();
						}
						if(i == 7){
							if(hasClosed){
								$("#options_point7").remove();
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
							console.log("indexNum:"+indexNum);
							if(indexNum == '1'){
								if(parseInt(indexNum) < parseInt(pNum)){
									$("#qdnbps").remove();
								}
							    $("#projectProgress_1_con").css("display","block");
								tiggerTable($("#projectProgress_1_table"),3);
							}else if(indexNum == '2'){
							    $("#projectProgress_2_con").css("display","block");
								tiggerTable($("#projectProgress_2_table"),3);
							}else if(indexNum == '3'){
								if(parseInt(indexNum) < parseInt(pNum)){
									$("#lxhpq").remove();
								}
								$("#projectProgress_3_con").css("display","block");
								tiggerTable($("#projectProgress_3_table"),3);
							} else if(indexNum == '4'){
								$("#projectProgress_4_con").css("display","block");
								if(parseInt(indexNum) < parseInt(pNum)){
									$("#reset_btn").css("display","none");
								}
							    tiggerTable($("#projectProgress_4_table"),3);
							} else if(indexNum == '5'){
								$("#projectProgress_7_con").css("display","none");
								$("#projectProgress_5").addClass("on");
								$("#projectProgress_5_con").css("display","block");
								if(parseInt(indexNum) < parseInt(pNum)){
									tzyxs(1);
								}else{
									tzyxs(0);
								}
							}else if(indexNum == '6'){
								$("#projectProgress_5_con").css("display","none");
								 $("#projectProgress_6_con").css("display","block");
								 tiggerTable($("#projectProgress_6_table"),3);
								 if(parseInt(indexNum) < parseInt(pNum)){
									 $("#jzdc_options").remove();
								 }
							}else if(indexNum == '7'){
								$("#projectProgress_6_con").css("display","none");
								$("#projectProgress_7_con").css("display","block");
								if(parseInt(indexNum) < parseInt(pNum)){
									$("#inSure_btn").css("display","none");
								}
								 tiggerTable($("#projectProgress_7_table"),3);
							}else if(indexNum == '8'){
								$("#projectProgress_7_con").css("display","none");
								$("#projectProgress_8_con").css("display","block");
								
								 if(parseInt(indexNum) < parseInt(pNum)){
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
								_projectId : $("#project_id").val(),
								_progress : progress
						}
						fileGrid.init(data);
					});				
					$("#" + progress).addClass("on");
					$("#" + progress + "_con").css("display","block");
					
					
					
					
					
					
				},null);
			}
		});
		return false;
	}
	/**
	 * 上传接触访谈纪要弹出层
	 */
	function air(indexNum){
		
		$("[data-id='popid1']").remove();
		
		loadJs();
		
		var _url='<%=path%>/galaxy/air';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				//初始化文件上传
				toinitUpload(platformUrl.stageChange, $("#project_id").val(),"select_btn","file_object","save_interview","",
						function getSaveCondition(){
							var	condition = {};
							var pid = $("#project_id").val();
							var viewDateStr = $("#viewDate").val();
							var viewTarget = $.trim($("#viewTarget").val());
							var um = UM.getEditor('viewNotes');
							var viewNotes = $.trim(um.getContent());
							var fileId = $("#viewfileID").val();
							if(pid == null || pid == ""){
								return;
							}
							if(viewDateStr == null ||  viewDateStr == ""){
								alert("日期不能为空");
								return false;
							}
							if(viewTarget == null ||  viewTarget == ""){
								alert("对象不能为空");
								return false;
							}
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
						},indexNum);
			}
		});
		return false;
	}
	
	/**
	 * 启动内部评审
	 */
	function startReview(){
		var pid = $("#project_id").val();
		if(pid != '' && pid != null && pid != undefined){
			sendGetRequest(platformUrl.startReview + pid, {}, function(data){
				var result = data.result.status;
				if(result == "OK"){
					layer.msg("启动内部评审成功!");
					$("#powindow,#popbg").remove();
					info(pid);
				}else{
					layer.msg(data.result.message);
				}
			});
		}
	}
	
	/**
	 * 上传会议记录
	 */
	 function addMettingRecord(num,meetingType){
		$("[data-id='popid1']").remove();
		 
		loadJs();
		var _url='<%=path %>/galaxy/mr';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj(meetingType);
				toinitUpload(platformUrl.stageChange,$("#project_id").val(), "meeting_select_btn","meeting_file_object","save_meeting","",
						function getSaveCondition(){
							var	condition = {};
							var pid = $("#project_id").val();
							var meetingDateStr = $.trim($("#meeting_date").val());
							var meetingResult = $.trim($("input:radio[name='meetingResult']:checked").val());
							var um = UM.getEditor("meeting_notes");
							var meetingNotes = $.trim(um.getContent());
							if(pid == null || pid == ""){
								alert("项目不能为空");
								return;
							}
							if(meetingDateStr == null ||  meetingDateStr == ""){
								alert("日期不能为空");
								return;
							}
							if(meetingType == null ||  meetingType == ""){
								alert("会议类型不能为空");
								return;
							}
							if(meetingResult == null ||  meetingResult == ""){
								alert("结果不能为空");
								return;
							}
							condition.pid = pid;
							condition.stage = "projectProgress:"+num;
							condition.createDate = meetingDateStr;
							condition.meetingType = meetingType;
							condition.result = meetingResult;
							condition.content = meetingNotes;
							return condition;
						},num);
			}
		});
		return false;
	}
	
	 /**
	  * CEO评审阶段申请立项会排期
	  */
	function toEstablishStage(){
		var pid = $("#project_id").val();
		if(pid != '' && pid != null && pid != undefined){
			sendGetRequest(platformUrl.toEstablishStage + pid, {}, function(data){
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
	  * 立项会阶段申请立项会排期
	  */
	function toLxmeetingPool(){
		var pid = $("#project_id").val();
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
		 var pid = $("#project_id").val();
		 if(pid != '' && pid != null){
			 /**
			  *  生成尽职调查报告列表
			  */
			 sendGetRequest(
					 sopContentUrl + '/galaxy/project/progress/proFileInfo/'+pid+'/5',
					 null, function(data){
						 var json = eval(data);
						 var dataList=json.entityList;
							for(var p in dataList){
								var handlefile="";
								if(!hasClosed){
									handlefile='<a href="javascript:;" onclick="downloadTemplate(\'fileWorkType:5\');" class="pubbtn fffbtn llpubbtn">下载投资意向书模板</a>';
							        if (dataList[p].fileStatusDesc == "缺失") { 
							        	handlefile +='<td><a href="javascript:; " class="pubbtn fffbtn llpubbtn" onclick="addFile(5,0);">上传投资意向书</a></td>';
									}else{
										var fileSource =  dataList[p].fileSource;
										handlefile += '<td><a href="javascript:; " class="pubbtn fffbtn llpubbtn" onclick="updateTzyxs('+fileSource+')">更新投资意向书</a><a  href="javascript:; " class="pubbtn fffbtn lpubbtn" onclick="addFile(5,1);">上传签署证明</a></td>';
									}
								}
								
						        var htmlhead = '<div id="tzyxs_options" class="btnbox_f btnbox_f1 btnbox_m clearfix">'+
						        handlefile+'</div>'+
							        '<div class="process clearfix">'+
							        '<h2>投资意向书盖章流程</h2>'+
							        '<img src="<%=path%>/img/process.png" alt="">'+
							        '</div>';
							        
								 var htmlstart=htmlhead+'<table width=\"100%" cellspacing="0" cellpadding="0" >'+
									             '<thead>'+
									                '<tr>'+
									                 '<th>业务分类</th>'+
									                 '<th>创建日期</th>'+
									                 '<th>存储类型</th>'+
									                 '<th>更新日期</th>'+
									                 '<th>档案状态</th>'+
									                 '<th>查看附件</th>'+
									                 '</tr>'+
									            '</thead>'+                                                                                                                                   
									             '<tbody>';
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
										'<td>'+dataList[p].fWorktype+'</td>'+
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
		var _url='<%=path %>/galaxy/tzyx';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				if(i == 1){
					$("#voucherType").attr("checked","checked");
					$("#voucherType").attr("disabled",true);
				}
				else
				{
					$("#voucherDiv").css("display","none");
					
				}
				toinitUpload(platformUrl.stageChange, $("#project_id").val(),"select_file_btn","file_obj","save_file_btn","fileType",
						function getSaveCondition(){
					var	condition = {};
					var pid = $("#project_id").val();
					if(pid == null || pid == ""){
						alert("项目不能为空");
						return;
					}
					var type = $("input[name='fileSource']:checked").val();
					if(type == null || type == ""){
						alert("档案来源不能为空");
						return;
					}
					var fileType = $("#fileType").val();
					if(fileType == null || fileType == ""){
						alert("存储类型不能为空");
						return;
					}
					var fileWorktype = $("#fileWorkType").val();
					if(fileWorktype == null || fileWorktype == ""){
						alert("业务分类不能为空");
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
				},null);
			}
		});
		return false;
	}
	function updateTzyxs(fileSource){
		$("[data-id='popid1']").remove();
		 loadJs();
		var _url='<%=path %>/galaxy/tzyx';
		$.getHtml({
			url:_url,
			okback:function(){
				$("#voucherDiv").css("display","none");
				$("input[name='fileSource'][value='"+fileSource+"']").attr("checked",true);
								

				var uploader = $.fxUpload({
					props:{
						browse_button:'select_file_btn',
						url:platformUrl.stageChange,
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
								var condition = {};
								var pid = $("#project_id").val();
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
								var voucherType = $("input[id='voucherType']:checked").val();
								condition.pid = pid;
								condition.stage = "projectProgress:5";
								condition.type = type;
								condition.fileType = fileType;
								condition.fileWorktype = fileWorktype;
								condition.voucherType = voucherType;
								
								up.settings.multipart_params=condition;
							},
							FileUploaded: function(up, files, rtn) {
								var data = $.parseJSON(rtn.response);
								
								if(data.result.status == "OK")
								{
									layer.msg(data.result.message);
									var pid = $("#project_id").val()
									$("#powindow,#popbg").remove();
									info(pid);
								}
								else
								{
									layer.msg("上传失败.");
								}
							}
						}
					}
				});
				console.log(uploader);
			}
			
		});
	} 
	 /**
	  * 尽职调查
	  */
	 function jzdc(){
		 
		 var pid = $("#project_id").val();
		 if(pid != '' && pid != null){
			 /**
			  *  生成尽职调查报告列表
			  */
			 sendGetRequest(
					 sopContentUrl + '/galaxy/project/progress/proFileInfo/'+pid+'/6', 
					 null, function(data){
				 var html = "";
				 $.each(data.entityList, function(i,o){
					 console.log(o);
					 html += "<tr>";
					 if(o.fileWorktype == 'fileWorktype:1'){
						 html += "<td>业务尽职调查报告";
						 html += "</td><td>" + o.createDate + "</td>";
						 html += "<td>"+o.careerLineName+"</td><td>"+o.fType+"</td>";
					 }else if(o.fileWorktype == 'fileWorktype:2'){
						 html += "<td>人事尽职调查报告";
						 html += "</td><td>" + o.createDate + "</td>";
						 html += "<td>人事部</td><td>"+o.fType+"</td>";
					 }else if(o.fileWorktype == 'fileWorktype:3'){
						 html += "<td>法务尽职调查报告";
						 html += "</td><td>" + o.createDate + "</td>";
						 html += "<td>法务部</td><td>"+o.fType+"</td>";
					 }else if(o.fileWorktype == 'fileWorktype:4'){
						 html += "<td>财务尽职调查报告";
						 html += "</td><td>" + o.createDate + "</td>";
						 html += "<td>财务部</td><td>"+o.fType+"</td>";
					 }
					 if(o.fileStatus == 'fileStatus:1' || o.fileValid == '0'){
						 html += "<td>缺失</td>";
						 if(o.fileWorktype != 'fileWorktype:1'){
							 html +='<td><a href="javascript:; " onclick="taskUrged('+o.id+');"class="blue">催办 </a></td>';

						 }else{
							 html += "<td></td>";
						 }
						 html += "<td>无</td>";
					 }else if(o.fileStatus == 'fileStatus:2'){
						 if(o.fileWorktype == 'fileWorktype:1'){
							 $("#jzdc_options a:eq(0)").text('更新业务尽职调查报告')
						 }
						 html += "<td>已上传</td>";
						 html += "<td></td>";
						 html += "<td><a href='javascript:filedown("+o.id+");'>查看</a></td>";
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
		var _url='<%=path %>/galaxy/jzdc';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				toinitUpload(platformUrl.stageChange,$("#project_id").val(), "select_file_btn","file_obj","save_file_btn","fileType",
						function getSaveCondition(){
					var	condition = {};
					var pid = $("#project_id").val();
					if(pid == null || pid == ""){
						alert("项目不能为空");
						return;
					}
					var type = $("input[name='fileSource']:checked").val();
					if(type == null || type == ""){
						alert("档案来源不能为空");
						return;
					}
					var fileType = $("#fileType").val();
					if(fileType == null || fileType == ""){
						alert("存储类型不能为空");
						return;
					}
					var fileWorktype = $("#fileWorkType").val();
					if(fileWorktype == null || fileWorktype == ""){
						alert("业务分类不能为空");
						return;
					}
					condition.pid = pid;
					condition.stage = "projectProgress:6";
					condition.type = type;
					condition.fileType = fileType;
					condition.fileWorktype = fileWorktype;
					return condition;
				},null);
			}
		});
		return false;
	}
	/**
	 * 尽职调查--点击申请投决会按钮
	 */
	function inTjh(){
		var pid = $("#project_id").val();
		if(pid != '' && pid != null && pid != undefined){
			sendGetRequest(
					platformUrl.inTjh + pid,
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
	 * 投决会--点击申请投决会按钮
	 */
	function inSureMeetingPool(){
		var pid = $("#project_id").val();
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
		var pid = $("#project_id").val();
		if(pid != '' && pid != null){
			var _table = $("#teamSeheetDataGrid");
			var _tbody = _table.find("tbody");
			_tbody.empty();
			sendPostRequestByJsonObj(
					platformUrl.searchSopFileListWithoutPage,
					{"projectId" : pid},
					function(data){
						
						_tbody.empty();
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
									$tr.append('<td></td>') ;
								}	
								$tr.append('<td>'+this.fileStatusDesc+'</td>') ;
								if(this.fileWorktype == 'fileWorktype:6'){
									if(this.fileKey == null){	
										$tr.append('<td><a href="javascript:;" onclick="tzxyAlert(8,0);" class="blue">上传</a></td>');
									}else{
										$tr.append('<td><a href="javascript:;" onclick="filedown('+this.id+');" class="blue">查看</a></td>'); 	
									}
									if(this.voucherFileKey == null){	
										$tr.append('<td><a href="javascript:;" onclick="tzxyAlert(8,1);" class="blue">上传</a></td>');
									}else{
										$tr.append('<td><a href="javascript:;" onclick="filedown('+this.voucherId+',null,\'voucher\'); " class="blue">查看</a></td>'); 	
									}
								}else if(this.fileWorktype == 'fileWorktype:7'){
									
									if(this.fileKey == null){	
										$tr.append('<td><a href="javascript:;" onclick="gqzrAlert(8,0);" class="blue">上传</a></td>');
									}else{
										$tr.append('<td><a href="javascript:;" onclick="filedown('+this.id+'); " class="blue">查看</a></td>'); 	
									}
									if(this.voucherFileKey == null){	
										$tr.append('<td><a href="javascript:;" onclick="gqzrAlert(8,1);" class="blue">上传</a></td>');
									}else{
										$tr.append('<td><a href="javascript:;" onclick="filedown('+this.voucherId+',null,\'voucher\');" class="blue">查看</a></td>'); 	
									}
								}
								_tbody.append($tr);
								//涉及股权转让
								if(st == 1){
									$("#stock_transfer").attr("checked","checked");
									$("#stock_transfer").attr("disabled","true");
								}else{
									
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
		var _url='<%=path %>/galaxy/tzxy';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				if(i == 1){
					$("#voucherType").attr("disabled",true);
					$("#voucherType").attr("checked","checked");
				}else{
					$("#tzyxDiv").css("display","none");
				}
				toinitUpload(platformUrl.stageChange,$("#project_id").val(), "select_file_btn","file_obj","save_file_btn","fileType",
						function getSaveCondition(){
					var	condition = {};
					var pid = $("#project_id").val();
					if(pid == null || pid == ""){
						alert("项目不能为空");
						return;
					}
					var type = $("input[name='fileSource']:checked").val();
					if(type == null || type == ""){
						alert("档案来源不能为空");
						return;
					}
					var fileType = $("#fileType").val();
					if(fileType == null || fileType == ""){
						alert("存储类型不能为空");
						return;
					}
					var fileWorktype = $("#fileWorkType").val();
					if(fileWorktype == null || fileWorktype == ""){
						alert("业务分类不能为空");
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
				},null);
			}
		});
		return false;
	}
	
	/**
	 * "是否涉及股权转让"按钮点击事件
	 */
	function selected(obj){
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
		var _url='<%=path %>/galaxy/gqzr';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				if(i == 1){
					$("#voucherType").attr("disabled",true);
					$("#voucherType").attr("checked","checked");
				}else{
					$("#gqzrDiv").css("display","none");
					
				}
				toinitUpload(platformUrl.stageChange,$("#project_id").val(), "select_file_btn","file_obj","save_file_btn","fileType",
						function getSaveCondition(){
					var	condition = {};
					var pid = $("#project_id").val();
					if(pid == null || pid == ""){
						alert("项目不能为空");
						return;
					}
					var type = $("input[name='fileSource']:checked").val();
					if(type == null || type == ""){
						alert("档案来源不能为空");
						return;
					}
					var fileType = $("#fileType").val();
					if(fileType == null || fileType == ""){
						alert("存储类型不能为空");
						return;
					}
					var fileWorktype = $("#fileWorkType").val();
					if(fileWorktype == null || fileWorktype == ""){
						alert("业务分类不能为空");
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
				},null);
			}
		});
		return false;
	}
	 
	 /**
	  * 动态生成股权交割的HTML
	  */
	function gqjg(){
		var pid = $("#project_id").val();
		if(pid != '' && pid != null){
			sendGetRequest(
					platformUrl.getFileList + pid + "/9",
					null,
					function(data){
						var json = eval(data);
						 var dataList=json.entityList;
						 var htmlstart='<table width=\"100%" cellspacing="0" cellpadding="0" >'+
							             '<thead>'+
							                '<tr>'+
							                 '<th>业务分类</th>'+
							                 '<th>创建日期</th>'+
							                 '<th>存储类型</th>'+
							                 '<th>更新日期</th>'+
							                 '<th>催办</th>'+
							                 '<th>查看附件</th>'+
							                 '</tr>'+
							            '</thead>'+                                                                                                                                   
							             '<tbody>';
										for(var p in dataList){
													var typehtml = "";
													if (typeof(dataList[p].fType) == "undefined") { 
														typehtml ='<td></td>';
													}else{
														typehtml = '<td>'+dataList[p].fType+'</td>';
													}
													
													var handlehtml = "";
													if (dataList[p].fileStatusDesc == "缺失" && !hasClosed) { 
														handlehtml ='<td><a href="javascript:; " onclick="taskUrged('+dataList[p].id+');"class="blue">催办</a></td>';
													}else{
														handlehtml = '<td></td>';
													}
													
													var endhtml ="";
													if (dataList[p].fileStatusDesc == "缺失") { 
														endhtml ='<td>'+dataList[p].fileStatusDesc+'</td>';
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
													typehtml+
													'<td>'+getVal(dataList[p].updatedDate,'')+'</td>'+
													handlehtml+   
													endhtml+   
													'</tr>';   
										}
							var htmlend= '</tbody></table>';
							$("#projectProgress_9_con").html(htmlstart+htmlend);
					}
			);	
		}
	}
	function ftcolumnFormat(value, row, index){
		var fileinfo = "" ;
		var rc = "";
		if( row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
			fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
		}
		rc = "<div style=\"text-align:left;margin-left:20%;\">"+
					"访谈日期："+row.viewDateStr+
					"</br>访谈对象："+row.viewTarget+
					"</br>访谈录音："+fileinfo+
				"</div>" ;
		return rc;
	}
		
	function metcolumnFormat(value, row, index){
		var fileinfo = "";
		var rc = "";
		if(row.fileId != null && row.fileId != undefined && row.fileId != "undefined"){
			fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
		}
		rc = "<div style=\"text-align:left;margin-left:20%;\">"+
					"会议日期："+row.meetingDateStr+
					"</br>会议结论："+row.meetingResultStr+
					"</br>会议录音："+fileinfo+
				"</div>" ;
		return rc;
	}
	
	
	function loadJs(){
		$("#f").attr("href","<%=path %>/ueditor/themes/default/css/umeditor.css");
		$("#a").attr("src","<%=path %>/js/plupload.full.min.js");
		$("#a").attr("src","<%=path %>/js/plupload/zh_CN.js");
		$("#b").attr("src","<%=path %>/ueditor/dialogs/map/map.js");
		$("#c").attr("src","<%=path %>/ueditor/umeditor.config.js");
		$("#d").attr("src","<%=path %>/ueditor/umeditor.min.js");
		$("#e").attr("src","<%=path %>/ueditor/lang/zh-cn/zh-cn.js");
		
	}
	//催办
	function taskUrged(id) {
		var json= {"id":id};
		sendGetRequest(platformUrl.taskUrged, json, taskCallback);
	}
	function downFile(id){
		var pidParam = "";
		if(alertid>=0)
		{
			pidParam = "&projectId="+alertid;
		}
		var url = platformUrl.tempDownload+"?id="+id+pidParam;
		forwardWithHeader(url);
	}
	function downloadTemplate(fileWorktype)
	{
		var pidParam = "";
		if(alertid>=0)
		{
			pidParam = "&projectId="+alertid;
		}
		var url = platformUrl.tempDownload+"?worktype="+fileWorktype+pidParam;
		forwardWithHeader(url);
	}
	
	function taskCallback(data) {
		
		if (data.result.status!="OK") {
			layer.msg("催办失败");
		} else {
			layer.msg(data.result.message);
		}
	}
	
	
</script>

</html>
