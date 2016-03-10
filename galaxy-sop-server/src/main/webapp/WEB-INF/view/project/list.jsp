<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星SOP-添加项目</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/my_ext.js"></script>
<script src="<%=path %>/js/my.js"></script>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>

<!-- 富文本编辑器 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>

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
                <a href="添加项目.html" class="pubbtn bluebtn ico c4">添加项目</a>
                <a href="编辑项目.html" class="pubbtn bluebtn ico c5">编辑</a>
            </div>
        </div>
        <!-- 搜索条件 -->
		<div class="min_document clearfix">
			<div class="bottom searchall clearfix" id="custom-toolbar">
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
						<input type="text" class="txt" id="search_text" placeholder="请输入姓名或手机号" />
					</dd>
					<dd>
						<button type="submit" class="bluebtn ico cx" name="querySearch">搜索</button>
					</dd>
				</dl>
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<table id="data-table" data-url="project/spl" data-height="555" 
				data-page-list="[1, 5, 50]" data-toolbar="#custom-toolbar">
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
<!-- 弹出页面 -->
<div id="addFile" class="archivestc" style="display: none;">
	<dl class="fmdl clearfix">
    	<dt>档案来源：</dt>
        <dd class="clearfix">
        	<label><input name="fileSource" type="radio" value = "1" checked="checked"/>内部</label>
            <label><input name="fileSource" type="radio" value = "2"/>外部</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>存储类型：</dt>
        <dd>
        	<select id="fileType">
            	<option>sadasd</option>
            </select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>业务分类：</dt>
        <dd>
        	<select id="fileWorkType" disabled="disabled">
            	<option>sadasd</option>
            </select>
        </dd>
        <dd>
        	<label><input type="checkbox"/>签署凭证</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>所属项目：</dt>
        <dd>
        	<input type="text" placeholder="请输入项目名称或编号" class="txt"/>
        </dd>
        <dd><a class="searchbtn null" href="javascript:;">搜索</a></dd>
    </dl>
    
     <dl class="fmdl clearfix">
    	<dt>文档上传：</dt>
        <dd>
        	<input type="text" class="txt" id="fileTxt"/>
        </dd>
        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="selectBtn">选择档案</a></dd>
    </dl>  

    <a href="javascript:;" class="pubbtn bluebtn" id="uploadBtn";>上传保存</a>
    <input type="hidden" id="pathInput" value="<%=path%>">
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/sopFile.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script type="text/javascript">
	createMenus(5);
	/**
	 * 分页数据生成操作内容
	 */
	function editor(value, row, index){
		var id=row.id;
		var options = "<a href='#' data-btn='myproject' onclick='info(" + id + ")'>查看</a>";
		return options;
	}
	/**
	 * 查看项目阶段详情的弹出层
	 */
	function info(id){
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
							tiggerTable($("#" + progress + "_table"),3);
						}
						if(i == 5){
							projectProgress5(id);
						}
						if(i == 9){
							projectProgress9(id);
						}
						
						//为Tab添加点击事件，用于重新刷新
						$("#projectProgress_" + i).on("click",function(){
							var id = $(this).attr("id");
							
							if(id == 'projectProgress_5'){
								$("#projectProgress_7_con").css("display","none");
								$("#projectProgress_5").addClass("on");
								$("#projectProgress_5_con").css("display","block");
								projectProgress5($("#project_id").val());
							}
							if(id == 'projectProgress_9'){
								$("#projectProgress_8_con").css("display","none");
								$("#projectProgress_9").addClass("on");
								$("#projectProgress_9_con").css("display","block");
								projectProgress9($("#project_id").val());
							}
							var indexNum = id.substr(id.length-1,1);
							if(indexNum == '1'){
								$('#projectProgress_1_table').bootstrapTable({url:'<%=path%>/galaxy/project/progress/queryInterview',method:'post',queryParams: queryParams ,queryParamsType: "limit",detailView: false,sidePagination: "server",pageSize: 10,pageList: [10, 25, 50, 100],search: false,showRefresh: true,minimumCountColumns: 2,clickToSelect: false,});
								$("#projectProgress_1_table").bootstrapTable('refresh');
							}
						});
					}
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
	function air(){
		var _url='<%=path%>/galaxy/air';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				//初始化文件上传
				toinitUpload(sopContentUrl + "/galaxy/project/progress/addFileInterview",
						"select_btn","file_object","save_interview",
						function getSaveCondition(){
							var	condition = {};
							var projectId = $("#project_id").val();
							var viewDateStr = $("#viewDate").val();
							var viewTarget = $.trim($("#viewTarget").val());
							var um = UM.getEditor('viewNotes');
							var viewNotes = $.trim(um.getContent());
							var fileId = $("#viewfileID").val();
							if(projectId == null || projectId == ""){
								alert("项目不能为空");
								return false;
							}else{
								condition.projectId = projectId;
							}
							if(viewDateStr == null ||  viewDateStr == ""){
								alert("日期不能为空");
								return false;
							}else{
								condition.viewDateStr = viewDateStr;
							}
							if(viewTarget == null ||  viewTarget == ""){
								alert("对象不能为空");
								return false;
							}else{
								condition.viewTarget = viewTarget;
							}
							if(viewNotes != null && viewNotes!= ""){
								condition.viewNotes = viewNotes;
							}
							if(fileId != null && fileId!= ""){
								condition.fileId = fileId;
							}
							/*var	condition = {
								"projectId" : projectId,
								"viewDate" : viewDate,
								"viewTarget" : viewTarget,
								"viewNotes" : viewNotes,
								"fileId" : fileId
							};*/
							return condition;
						});
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
				$.getHtml({
					url:sopContentUrl + "/galaxy/tip/1",//模版请求地址
					data:"",//传递参数
					okback:function(){
						
					}
				});
			});
		}
	}
	
	/**
	 * 上传投决会议记录
	 */
	function voto(){
		var _url='<%=path%>/galaxy/voto';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				//初始化文件上传
				toinitUpload(sopContentUrl + "/galaxy/project/progress/addfilemeet",
						"file-select-btn","fileName","savemeet",
						function getMeetCondition(){
						var	condition = {};
						var projectId = $("#project_id").val();
						var meetingDateStr = $.trim($("#meetingDateStr").val());
						var meetingType = $.trim($('input:radio[name="meetingType"]:checked').val());
						var meetingResult = $.trim($('input:radio[name="meetingResult"]:checked').val());
						//var meetingNotes = $.trim($("#meetingNotes").val());
						var um = UM.getEditor('meetingNotes');
						var meetingNotes = $.trim(um.getContent());
						var fileId = $("#meetfileID").val();
						if(projectId == null || projectId == ""){
							alert("项目不能为空");
							return false;
						}else{
							condition.projectId = projectId;
						}
						if(meetingDateStr == null ||  meetingDateStr == ""){
							alert("日期不能为空");
							return false;
						}else{
							condition.meetingDateStr = meetingDateStr;
						}
						if(meetingType == null ||  meetingType == ""){
							alert("类型不能为空");
							return false;
						}else{
							condition.meetingType = meetingType;
						}
						if(meetingResult == null ||  meetingResult == ""){
							alert("结果不能为空");
							return false;
						}else{
							condition.meetingResult = meetingResult;
						}
						if(meetingNotes != null && meetingNotes!= ""){
							condition.meetingNotes = meetingNotes;
						}
						if(fileId != null && fileId!= ""){
							condition.fileId = fileId;
						}
						return condition;
					});
			}
		});
		return false;
	}
	
	
//////////////////   zf    LPH:内评会  CEO：ceo评审    LXH：立项会     ////////////////////////
	/* plupload上传对象初始化,   绑定保存
		fileSelectBtnId: 选择文件按钮id
		addUrl ： 保存调用路径url
		saveBtnId： 保存按钮ID
		inputFileId ： 文件名显示input框id
		chooseN：请求会议类型  LPH:内评会  CEO：ceo评审    LXH：立项会
	*/
	function initNUpload(fileSelectBtnId,addUrl,saveBtnId,inputFileId,chooseN) {
		
		// 定义 上传插件 方法 、  plupload 上传对象初始化
		var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : $("#"+fileSelectBtnId)[0],   // $("#file-select-btn")[0]
			url : addUrl,
			multipart:true,
			multi_selection:false,
			filters : {
				max_file_size : '10mb',
				mime_types: [
				    {title : "YP files", extensions : "mp3,avi"},
					{title : "Image files", extensions : "jpg,gif,png"},
					{title : "Zip files", extensions : "zip,rar"},
					{title : "Offices files", extensions : "doc,docx,excel"}
				]
			},
			init: {
				//上传按钮点击事件 - 开始上传
				PostInit: function() {
					$("#"+saveBtnId).click(function(){
						uploader.start();
						return false;
					});
				},
				//添加上传文件后，把文件名 赋值 给 input
				FilesAdded: function(up, files) {
					plupload.each(files, function(file) {
						/*document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';*/
						$("#"+inputFileId).val(file.name);
					});
				},
				
				//上传进度
				UploadProgress: function(up, file) {
				},
				//文件上传后， 返回值  赋值,  再ajax 保存入库
				FileUploaded: function(up, files, rtn) {
					var response = $.parseJSON(rtn.response);
					var rs = response.result.status;
					if(rs == "ERROR"){ //OK, ERROR
						alert("error "+response.result.message);
						return;
					}
					alert("保存成功");
					location.reload(true);
				},
				BeforeUpload:function(up){
					//表单函数提交
					//alert(JSON.stringify(getMeetCondition()));
					var res;
					if(chooseN == "LPH"){
						res = getNcondition("LPH_meetingDateStr","meetingType:1","LPH_meetingResult","LPH_meetingNotes");
					}else if (chooseN == "CEO"){
						res = getNcondition("CEO_meetingDateStr","meetingType:2","CEO_meetingResult","CEO_meetingNotes");
					}else if (chooseN == "LXH"){
						res = getNcondition("LXH_meetingDateStr","meetingType:3","LXH_meetingResult","LXH_meetingNotes");
					}else if (chooseN == "TJH"){
						res = getNcondition("TJH_meetingDateStr","meetingType:4","TJH_meetingResult","TJH_meetingNotes");
					}
					
					if(res == false || res =="false"){
						up.stop();
						return;
					}
					
					up.settings.multipart_params = res;
				},
				Error: function(up, err) {
					alert("错误"+err);
					//document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
				}
			}
		});

		uploader.init();
	}
	
	//验证获取参数
	function getNcondition(dateId,meetingType,resultRadioName,noteId){
		var	condition = {};
		
		var projectId = $("input[name='projectId']").val();
		//var projectId = $("#project_id").val();
		var meetingDateStr = $.trim($("#"+dateId).val());
		var meetingType = meetingType;
		var meetingResult = $.trim($('input:radio[name='+resultRadioName+']:checked').val());
		var um = UM.getEditor(noteId);
		var meetingNotes = $.trim(um.getContent());
		
		if(projectId == null || projectId == ""){
			alert("项目不能为空");
			return false;
		}
		if(meetingDateStr == null ||  meetingDateStr == ""){
			alert("日期不能为空");
			return false;
		}
		if(meetingResult == null ||  meetingResult == ""){
			alert("结果不能为空");
			return false;
		}
		if(meetingNotes == null || meetingNotes == ""){
			alert("记录不能为空");
			return false;
		}
		
		condition.projectId = projectId;
		condition.meetingDateStr = meetingDateStr;
		condition.meetingType = meetingType;
		condition.meetingResult = meetingResult;
		condition.meetingNotes = meetingNotes;
		return condition;
	}
	
	function addLPH(){
		var _url='<%=path %>/galaxy/lphtc';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$('.edui-container').show();
				initNUpload("LPH_file-select-btn",sopContentUrl + "/galaxy/project/progress/addfilemeet","LPH_savemeet","LPH_fileName","LPH");
				//setLPHtc();
			}//模版反回成功执行	
		});
		return false;
	}
	
	function addCEOPS(){
		var _url='<%=path %>/galaxy/ceopstc';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$('.edui-container').show();
				initNUpload("CEO_file-select-btn",sopContentUrl + "/galaxy/project/progress/addfilemeet","CEO_savemeet","CEO_fileName","CEO");
				//setLPHtc();
			}//模版反回成功执行	
		});
		return false;
	}

	function lxhpq(){
		var projectId = $("input[name='projectId']").val();
		var _url='<%=path %>/galaxy/project/progress/proSchedule/'+projectId;
		
		sendGetRequest(_url,null,function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				alert("申请失败  "+data.result.message);
				return;
			}else{
				alert("申请成功");
				location.reload(true);
			}
		},null);
	}
	
	function addLXH(){
		var _url='<%=path %>/galaxy/lxhtc';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$('.edui-container').show();
				initNUpload("LXH_file-select-btn",sopContentUrl + "/galaxy/project/progress/addfilemeet","LXH_savemeet","LXH_fileName","LXH");
				//setLPHtc();
			}//模版反回成功执行	
		});
		return false;
	}
	
	function addTJH(){
		var _url='<%=path %>/galaxy/tjhtc';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$('.edui-container').show();
				initNUpload("TJH_file-select-btn",sopContentUrl + "/galaxy/project/progress/addfilemeet","TJH_savemeet","TJH_fileName","TJH");
				//setLPHtc();
			}//模版反回成功执行	
		});
		return false;
	}
	
	function projectProgress9(id){
		$.ajax({
			url : '/galaxy/project/progress/proFileInfo/'+id+'/9',
			data : null,
			async : false,
			type : 'GET',
			dataType : "json",
			contentType:"application/json; charset=UTF-8",
			cache : false,
			error:function(){     
		        alert('error');     
		    }, 
			success : function(data) {
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
											
											if (dataList[p].fileStatusDesc == "缺失") { 
												handlehtml ='<td><a href="javascript:; " class="blue">催办</a></td>';
											}else{
												handlehtml = '<td>'+dataList[p].fileName+'</td>';
											}
											var endhtml ="";
											if (dataList[p].fileStatusDesc == "缺失") { 
												endhtml ='<td></td>';
											}else{
												endhtml = '<td><a href="javascript:; " onclick="handleDownload('+dataList[p].id+');" class="blue">附件</a></td>';
											}
											
											htmlstart +='<tr>'+
											'<td>'+dataList[p].fWorktype+'</td>'+
											'<td>'+dataList[p].createDate+'</td>'+
											typehtml+
											'<td></td>'+
											handlehtml+   
											endhtml+   
											'</tr>';   
											
								}
					var htmlend= '</tbody></table>';
					$("#projectProgress_9_con").html(htmlstart+htmlend);
			}
		});
	}
	function projectProgress5(id){
		$.ajax({
			url : '/galaxy/project/progress/proFileInfo/'+id+'/5',
			data : null,
			async : false,
			type : 'GET',
			dataType : "json",
			contentType:"application/json; charset=UTF-8",
			cache : false,
			error:function(){     
		        alert('error');     
		    }, 
			success : function(data) {
				 var json = eval(data);
				 var dataList=json.entityList;
					for(var p in dataList){
						var handlefile="";
				        if (dataList[p].fileStatusDesc == "缺失") { 
				        	handlefile ='<td><a href="javascript:; " class="pubbtn fffbtn llpubbtn" onclick="init('+id+','+1+');">上传投资意向书</a></td>';
						}else{
							handlefile = '<td><a href="javascript:; " class="pubbtn fffbtn llpubbtn">更新投资意向书</a><a  href="javascript:; " class="pubbtn fffbtn lpubbtn" onclick="init('+id+','+2+');">上传签署证明</a></td>';
						}
						 var htmlhead = '<div class="btnbox_f btnbox_f1 btnbox_m clearfix">'+
					        '<a href="javascript:;" class="pubbtn fffbtn llpubbtn">下载投资意向书模板</a>'+
					        handlefile+
					        '</div>'+
					        '<div class="process clearfix">'+
					        '<h2>投资意向书盖章流程</h2>'+
					        '<img src="img/process.png" alt="">'+
					        '</div>';
					        
						 var htmlstart=htmlhead+'<table width=\"100%" cellspacing="0" cellpadding="0" >'+
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
								var typehtml = "";
								if (typeof(dataList[p].fType) == "undefined") { 
									typehtml ='<td></td>';
								}else{
									typehtml = '<td>'+dataList[p].fType+'</td>';
								}
								var handlehtml ="";
								
								if (dataList[p].fileStatus == "fileStatus:1") { 
									handlehtml ='<td><a href="javascript:; " class="blue">催办</a></td>';
								}else{
									handlehtml = '<td>'+dataList[p].fileName+'</td>';
								}
								
								var endhtml ="";
								if (dataList[p].fileStatusDesc == "缺失") { 
									endhtml ='<td></td>';
								}else{
									endhtml = '<td><a href="javascript:;" onclick="handleDownload('+dataList[p].id+');" class="blue">附件</a></td>';
								}
								
								htmlstart +='<tr>'+
								'<td>'+dataList[p].fWorktype+'</td>'+
								'<td>'+dataList[p].createDate+'</td>'+
								typehtml+
								'<td></td>'+
								handlehtml+   
								endhtml+   
								'</tr>';   
								
					}
					var htmlend= '</tbody></table>';
					$("#projectProgress_5_con").html(htmlstart+htmlend);
			}
		});
	
	}
	
	function init(id,type){
		var urls ='';
		if(type == '1'){
			urls = '/galaxy/sopFile/projectFileUpload/';
		}
		if(type == '2'){
			urls = '/galaxy/sopFile/commonUpload/';
		}
		var utils = {
				path : $("#pathInput").val(),
				each : function(_data,_dom,type){
					_dom.empty();
					$.each(_data.entityList,function(){
							_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
							  $("#fileWorkType option[value='fileWorktype:5']").attr("selected","selected");
					});
				}
		}
		//上传弹出框
		var popPanel = {
				init : function(){
						//判断对话框是否存在
						if(popPanel.isCreate()){
							alert("打开popup面板");
							$("#popbg,#pop").show();
						}else{
							$.popup({
								txt : $("#addFile").html(),
								showback:function(){

									var _this = this;
									//plupload上传对象初始化
									var uploader = new plupload.Uploader({
										runtimes : 'html5,flash,silverlight,html4',
										browse_button : $(_this.id).find("#selectBtn")[0], // you can pass in id...
										url : utils.path + urls,
										multipart:true,
										multi_selection:false,
										filters : {
											max_file_size : '10mb',
											mime_types: [
												{title : "Image files", extensions : "jpg,gif,png"},
												{title : "Zip files", extensions : "zip,rar"},
												{title : "Offices files", extensions : "doc,docx,excel"}
											]
										},
										init: {
											PostInit: function(){
												
												//上传按钮点击事件开始上传
												$(_this.id).find("#uploadBtn").click(function(){
													alert("上传保存事件并关闭弹出框");
													uploader.start();
													return false;
												})
											},
											FilesAdded: function(up, files) {
												plupload.each(files, function(file) {
													$(_this.id).find("#fileTxt").val(file.name);
												});
											},
											UploadProgress: function(up, file) {
											},
											FileUploaded:function(up,file,result){
												if(result.status==200){
													var _restmp = $.parseJSON(result.response);
													alert(_restmp.status)
													if(_restmp.result.status == "OK"){
														alert("上传成功");
														popPanel.close();
														
													}else{
														alert(_restmp.result.message);
													}
													
												}else{
													alert("上传失败");
												}
											},
											BeforeUpload:function(up){
												
												var form = {
														"fileSource" : $(_this.id).find("input[name='fileSource']:checked").val(),
														"fileType" : $(_this.id).find("#fileType").val(),
														"fileWorkType" : $(_this.id).find("#fileWorkType").val(),
														"projectId" : id	
												}
												
												up.settings.multipart_params = form;
											},
											Error: function(up, err) {
												alert("上传失败"+err);
											}
										}
									});							
									//初始化plupload插件
									uploader.init();								
								},
								hideback:function(t){
									
								}
								
							});
						}
				},
				initData : function(_this){
					sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,popPanel.initDataCallBack,null);
					sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorkType",null,popPanel.initDataCallBack,null);
				},
				initDataCallBack : function(data){
					var _dom;
					var _type;
					switch(data.result.message){
					case "fileType" : 
						_dom = $("#addFile").find("#fileType");
						break;
					case "fileWorkType":
						_dom = $("#addFile").find("#fileWorkType");
						break;
					}
					utils.each(data,_dom,null);
					
				},
				//判断弹出框是否已经存在
				isCreate : function(){
					return $("#pop").length>0;
				},
				//关闭弹出框
				close : function(){
					$("#popbg,#pop").hide();
				},
				//若存在打开弹出框
				open : function(){
					$("#popbg,#pop").show();
				}
		}
		
		//弹出层初始化
		popPanel.init();
		//初始化上传面板页面数据
		popPanel.initData();
	}
	
	function handleDownload(id)
	{
		var url = "<%=path %>"+platformUrl.downLoadFile+"/"+id;
		window.location.href=url;
	}
	
	   //页面传参
    function queryParams(params) {
		var projectIdstr=$("#project_id").val();
    	return {
	    	pageSize: params.limit,
	    	pageNum: params.offset,
	    	order: params.order,
	    	projectId:projectIdstr,
    	};
    }
</script>
</html>