

function dateFormatter(val,row,index)
	{
		if(!isNaN(val))
		{
			return Number(val).toDate().format("yyyy-MM-dd");
		}
		return val;
	}
	function datetimeFormatter2(val,row,index)
	{	
		if(row.updatedTime != null){
			if(!isNaN(row.updatedTime)){
				return Number(row.updatedTime).toDate().format("yyyy-MM-dd hh:mm:ss");
			}
		}else if(row.createdTime != null){
			if(!isNaN(row.createdTime)){
				return Number(row.createdTime).toDate().format("yyyy-MM-dd hh:mm:ss");
			}
		}
		return val;
	}
	function datetimeFormatter(val,row,index)
	{
		if(!isNaN(val))
		{
			return Number(val).toDate().format("yyyy-MM-dd hh:mm:ss");
		}
		return val;
	}
	function progressFormatter(val,row,index)
	{
		if(val != null)
		{
			return $('[name="ideaProgress"] option[value="'+val+'"]').text();
		}
	}
	function ideaNameLinkFormatter(val,row,index)
	{
		var text=val.substring(0,12);
		return '<a href="#" class="blue" onclick="showIdeaDetail(\'' + row.id + '\')" title="'+val+'">'+text+'</a>';
	}

	function ideaNameLinkFormatter2(val,row,index)
	{
		return '<a href="#" class="blue" onclick="infoIdea(\'' + row.id + '\',\''+  row.ideaName + '\')">'+val+'</a>';
	}
	function proNameLinkFormatter(val,row,index)
	{
		return '<a href="#" class="blue" onclick="infoPro(\'' + row.projectId + '\')">'+val+'</a>';
	}
	function infoIdea(ideaid,ideaName){
		$("#powindow,#popbg").remove();
		$("#custom-toolbar [name='keyword']").val(ideaName);
		$("#data-table").bootstrapTable('refresh');
		showIdeaDetail(ideaid);
	}
	function infoPro(pid){
		window.location.href=platformUrl.projectDetail+pid;
	}
	
	function refreshIdeaList()
	{
		$("#data-table").bootstrapTable('refresh');
	}
	function getDepartment($depField)
	{
		sendGetRequest(
				platformUrl.getIdeaDepartment,
				null,
				function(data){
					if(data.result.status = 'OK')
					{
						$depField.empty();
						if(data.entityList.length >1)
						{
							$depField.append('<option value="">全部</option>');
						}
						$.each(data.entityList,function(){
							$depField.append('<option value="'+this.id+'">'+this.name+'</option>');
						});
					}
				}
		);
	}
	//基本信息  -- 数据展示
	function getIdeaInfo(id) {
		var idea = null;
			
		var url = platformUrl.detailIdea + "/"+id;
		sendGetRequest(url, {"id" : id}, 
			function(data){
			if(data.result.status == "Error")
			{
				return;
			}
			idea = data.entity;
			if(idea.ideaProgress=="ideaProgress:4"||idea.ideaProgress=="ideaProgress:1"){
				$("#claimantdis").hide();
			}
			setGiveUpInfo(data.userData);
			stockTransfer = idea.stockTransfer;
			$("#IdeaId").val(idea.id);
			$("#ideaDetail dd")
			.each(function(){
				var self = $(this);
				if(self.attr('id') != 'undefined')
				{
					var id = self.attr('id');
					var formatter = self.data('formatter');
					
					var text = idea[id]
					if($.isFunction(window[formatter]))
					{
						text = window[formatter].call(window,text);
					}
					if(id=="ideaDesc"){
						self.html(text);
					}else{
						self.text(text);
					}
					
				}
			if(self.attr('id') == 'ideaSource')
				{
				self.attr("title",text);
				}
			if(self.attr('id') == 'ideaName')
			{
			self.attr("title",text);
			}
				
			});
		});
		return idea;
	}
	
	
	//stage 弹出
	var ideaInfo;
	function showIdeaDetail(ideaId)
	{
		var layerIndex = layer.load(2);
		var _url = platformUrl.ideaGoStage+"?id="+ideaId;
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".creativetc .tabtable").tabchange2();
				
				//基本信息  -- 数据展示  index =1
				ideaInfo = getIdeaInfo(ideaId);
				$(".idea-title").text(ideaInfo.ideaName);
				$("[data-id='ideaNowId']").val(ideaId);
				$("#idea-progress-log").text(ideaInfo.latestLog);
				//解析元素id和项目阶段值，以便之后做控制
				var progress = ideaInfo.ideaProgress;
				progress = progress.replace(":","_");
				var index = Number(progress.substr("ideaProgress_".length));
				
				bindTabClickEvents(index);  // tab 选项卡绑定 click 事件    用于重新刷新
				ideaLoaded(ideaInfo,index); // 控制按钮显示、隐藏、可用。禁用等
				bindTcEvents(index);  //按钮 bind 弹窗
				//cyToProShow();  //创建项目  阶段，点击项目列表 - <a>项目名称</a> 弹出事件
				
				//显示当前阶段
				if(index == '2' || index == '3' || index== '5')
				{
					$("#" + progress ).click();
				}
				
				var id=$("#IdeaId").val();
				$("#editBtn").click(function(){
					sendGetRequest(platformUrl.detailIdea+"/"+id,null,initCallBack.getdetailIdeaInfoCallBack);
					if($(this).text()=='编辑'){
						$('#edit_and_add').html('编辑创意');
					}
				});
				layer.close(layerIndex);
				
			}//end okback 模版反回成功执行	
			
		});
		return false;

	}
	
	// 调研   $('#ideaProgress_2_table') 表格动态加载初始化   pagination: true,
	function init_ideaProgress_2_table(index){
		$('#ideaProgress_2_table').bootstrapTable({
			queryParamsType: 'size|page',
			pageSize:3,
			showRefresh : false ,
			sidePagination: 'server',
			method : 'post',
			pagination: false,
	        search: false,
	        onLoadSuccess: function (data) {
	        	if (index == 2) {
	        		if (ideaInfo.claimantUid != userId) {
	        			$('#ideaProgress_2_table').bootstrapTable('hideColumn', 'operateFile');
	        		}
	        	} else {
	        		$('#ideaProgress_2_table').bootstrapTable('hideColumn', 'operateFile');
	        	}
	        }
		});
	}
	
	
	/**
	 * 刷新创意阶段弹窗
	 */
	function refreshStageDialog(ideaId)
	{
		$("#powindow,#popbg").remove();
		showIdeaDetail(ideaId);
	}
	
	
	// tab 选项卡绑定 click 事件    用于重新刷新
	function bindTabClickEvents(index){
		// == begin  tab 选项卡绑定 click 事件    用于重新刷新
		for(var i = 1; i<6; i++){
			
			if(i > index || (i>1 && index==4)){      //当前阶段之后的tab变为不可用;搁置时，与待认领相同；
				$("#ideaProgress_" + i).addClass("disabled");
				$("#ideaProgress_" + i).attr("disabled",'disabled');
			}
			
			$("#ideaProgress_" + i).on("click",function(){
				var id = $(this).attr("id");
				var indexNum = id.substr(id.length-1,1);
				switch(indexNum){
				case '1':
					
					break;
				case '2' :
					var clickN = $("#ideaProgress_2").data("clickn");
					if(clickN != '0'){
						$("#ideaProgress_2_table").bootstrapTable('refresh');
					}else{
						//tiggerTable($("#ideaProgress_2_table"),3);
						init_ideaProgress_2_table(index);
						$("#ideaProgress_2").data("clickn","n");
					}
					//tiggerTable($("#ideaProgress_2_table"),3);
					break;
				case '3':
					var clickN = $("#ideaProgress_3").data("clickn");
					if(clickN != '0'){
						$("#ideaProgress_3_table").bootstrapTable('refresh');
					}else{
						tiggerTable($("#ideaProgress_3_table"),2);
						$("#ideaProgress_3").data("clickn","n");
					}
					break;
				case '5':
					
					break;
				default :
					break;
				}
			});
		}
		$("#ideaOperateLog").on("click",function(){
			var clickN = $("#ideaOperateLog").data("clickn");
			if(clickN != '0'){
				$("#ideaProgress_log_table").bootstrapTable('refresh');
			}else{
				tiggerTable($("#ideaProgress_log_table"),8);
				$("#ideaOperateLog").data("clickn","n");
			}
		});
		// == end tab 选项卡绑定  click 事件
	}
	
	/**
	 * 各阶段按钮绑定事件、弹窗
	 * 由项目当前阶段显示隐藏按钮
	 */	
	function bindTcEvents(index){
		
		//基本信息 -->编辑
		$("[data-btn='edit']").on("click",function(){
			var $self = $(this);
			var $subling=$self.next().val();
			var _url = $self.attr("href");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){	
					getIdeaInfoEdit($subling,"edit")
				}//模版反回成功执行	
			});
			return false;
		});
		//基本信息 -->历史信息
		$("[data-btn='history']").on("click",function(){
			var $self = $(this);
			var _url = $self.attr("href");
			$.getHtml({
				url:_url//模版反回成功执行	
			});
			return false;
		});
		//基本信息 -->认领
		$("[data-btn='claim']").on("click",function(){
			var $self = $(this);
			var id=$self.next().next().val();
		//	var _url = $self.attr("href");
			 claimFun(id);
			/*$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					 claimFun(id);
				     $(".claimtc_close").on("click",function(){
				    	 refreshIdeaList();
				    	 refreshStageDialog(id);
					})
				
				}//模版反回成功执行	
			});*/
			//return false;
		});
		
		
		
		//放弃btn
		$("[data-btn='abandon']").on("click",function(){
			var $self = $(this);
			var id=$self.attr("value");
			var _url = platformUrl.GiveUpIdea;
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){		
					//$("[data-btn='saveReason']").on("click",function(){
					$("#saveReason").on("click",function(){
						var abReason=$("#givUpReason").val();
						if(id != ''){
							sendPostRequestByJsonObj(platformUrl.ideaUpdateIdea, {"id" :id,"abReason":abReason,"ideaProgress":"ideaProgress:4"}, function(data){
								if(data.result.status == 'OK')
								{
									layer.msg("放弃成功！");
									$(".abandon").find("[data-close='close']").click();
									$(".creativetc").find("[data-close='close']").click();
									refreshIdeaList();
							
								}else{
									layer.msg(data.result.message);
								}
								
							});
						}
					});
				}//模版反回成功执行	
			});
			return false;
		});
		
		
		
		//创建立项会  --> 添加会议纪要
		$("[data-btn='meeting']").on("click",function(){
			var $self = $(this);
			var _url = $self.attr("href");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					initCyMeetUpload();
				}//模版反回成功执行	
			});
			return false;
		});
		//创建立项会 --> 创建项目
		$("[data-btn='create']").on("click",function(){
			var $self = $(this);
			var _url = $self.attr("href");
			$.getHtml({
				url:_url,//模版请求地址
				data:{ideaId:ideaInfo.id},//传递参数
				okback:function(){							
				}//模版反回成功执行	
			});
			return false;
		});
		
		
		
		//创建项目 -->编辑项目名称
		$("[data-btn='edit_name']").on("click",function(){
			var $self = $(this);
			var _url = $self.attr("href");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){							
				}//模版反回成功执行	
			});
			return false;
		});
		
	}
	
	
	//创建项目  阶段，点击项目列表 - <a>项目名称</a> 弹出事件
	function cyToProShow(){
		
		//创建项目，点击项目列表 - <a>项目名称</a>
		$('#project_name').click(function(){
			$('.block').css({display: 'none',});;
			$(".aa").show();
			$('.tablink li').eq(0).addClass('on').siblings().removeClass('on');
		});
		$('#project_name').click(function(){
			$(".tabtable_con .block").eq(0).show().siblings().hide();
			$('.tablink li').eq(0).addClass('on').siblings().removeClass('on');
		});
	}
	
	
	
	///======= 调研     =====  ///
	function ideaOperateFormat(val,row,index){
		if(row.fileKey){
			return "<a  href=\"#\" onclick=\"showUploadPopup(\'e\'"+",\'"+row.projectProgress+"\',\'"+row.id+"\');\" class=\"blue\" >更新</a>" ;
		}
		return "-";
	}
	
	function ideaFileDownFormat(val,row,index){
		if(row.fileKey){
			var text=row.fileName.substring(0,8);
			return "<a href=\"#\"  onclick=\"filedown("+row.id+","+row.fileKey+");\" class=\"blue\" title=\'"+row.fileName+"\'>"+text+"</a>" ;
		}
		return "-";
	}
	// mark :u/上传  e/更新    prograss：ideaProgress:2 projectId:ideaid  fileid:
	function showUploadPopup(mark,prograss,fileid){
		$.popup({
			txt:$("#upload-dialog").html(),
			showback:function(){
				if(mark=='e'){
					$('.title_bj').html('更新可行性报告');					
				}
				var _this = this;
				if(upreport(_this,mark,prograss,fileid)){
					initIdeaUpload(_this);
				}
			}
		});
	}
	
	function upreport(_dialog,mark,prograss,fileid){
		var projectId = $("#ideaProgress_2_con").find("[data-id='ideaId']").val();
		$(_dialog.id).find("input[name='projectId']").val(projectId);
		$(_dialog.id).find("input[name='projectProgress']").val(prograss);
		if(mark == 'e'){
			$(_dialog.id).find("input[name='id']").val(fileid);
			$(_dialog.id).find("input[name='isEdit']").val("edit");
		}
		return true;
	}
	
	
	//popup 弹窗 加载
	function initIdeaUpload(_dialog){
		var ideaUploader = new plupload.Uploader({
			runtimes : 'html5,html4,flash,silverlight',
			browse_button : $(_dialog.id).find("#file-select-btn")[0], 
			url : platformUrl.ideaUpReport,
			multi_selection:false,
			filters : {
				max_file_size : '25mb',
				mime_types: paramsFilter(null)
			},

			init: {
				PostInit: function() {
					$(_dialog.id).find("#upload-btn").click(function(){
						if(ideaUploader.files.length==0){
							layer.msg("请选择文件.");
							return;
						}else{
							ideaUploader.start();
						}
						return false;
					});
				},

				FilesAdded: function(up, files) {
					if(ideaUploader.files.length >= 1){
						ideaUploader.splice(0, ideaUploader.files.length-1);
					} 
					$.each(files, function() {
						$(_dialog.id).find("input[name='fileName']").val(this.name);
						attrFileType($(_dialog.id).find("[name='fileType']"),this);
					});
				},
				BeforeUpload : function(up,file){
					$(_dialog.id).showLoading(
							 {
							    'addClass': 'loading-indicator'						
							 });
					var $form = $(_dialog.id).find("#upload-form");
					var data = JSON.parse($form.serializeObject());
					data['fileType']=$(_dialog.id).find("[name='fileType']").val();
					up.settings.multipart_params = data;
				},
				FileUploaded: function(up, files, rtn) {
					$(_dialog.id).hideLoading();
					var data = $.parseJSON(rtn.response);
					if(data.result.status == 'OK')
					{
						layer.msg("上传成功.");
						$(_dialog.id).find("[data-close='close']").click();
						$("#cy_start_lxh").show(); //启动创建立项会
						$("#cy_up_report").remove(); //上传文档
						$("#ideaProgress_2_table").bootstrapTable('refresh');
					}
					else
					{	
						ideaUploader.splice(0, ideaUploader.files.length);
						$(_dialog.id).find("input[name='fileName']").val("");
						layer.msg(data.result.message);
					}
				},
				Error: function(up, err) {
					$(_dialog.id).hideLoading();
					layer.msg(err.message);
				}
			}
		});

		ideaUploader.init();
		
	}
	
	//启动立项会
	function stratLxh(ideaId){
		var url = platformUrl.ideaStartMeet + "/"+ideaId;
		sendGetRequest(url, null, function(data){
			if(data.result.status == "ERROR"){
				layer.msg(data.result.message );
				return;
			}else if(data.result.status == "OK"){
				idea = data.entity;
				$("#data-table").bootstrapTable('refresh');
				$("#powindow,#popbg").remove();
				showIdeaDetail(ideaId);
			}
			
		});
		
	}
	
	
	//gethtml 弹窗 加载
	function initCyMeetUpload(){
		var res = null;
		//----- begin init  plupload 
		var meetuploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : $("#cyfile-select-btn")[0], // you can pass in id...
			url : platformUrl.saveCyMeetRecord,
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
						//var res = getMeetCondition(null,"projectId", "meetingDateStr", null,"meetingTypeTc", "meetingResult","meetingNotes");
						res = getMeetCondition("y",$("[data-id='ideaNowId']").val(), "meetingDateStr", "y","meetingType:3", "meetingResult","meetingNotes");
						if(res == false || res == "false"){
							up.stop();
							$("#savemeet").removeClass("disabled");
							return;
						}
						
						//var file = $("#fileName").val(); //up.files.length
						var file = up.files; //
						if(file.length > 0){
							up.settings.multipart_params = res;
							meetuploader.start();
						}else{
							sendPostRequestByJsonObj(platformUrl.saveCyMeetRecord,res,function(data){
								var result = data.result.status;
								if(result == "ERROR"){ //OK, ERROR
									$("#savemeet").removeClass("disabled");
									layer.msg(data.result.message);
									return;
								}else{
									layer.msg("保存成功", {time : 500});
									var _this = $("#ideaProgress_3_table");
									if(_this == null || _this.length == 0 || _this == undefined){
										removePop1();
									}else{
										$("#ideaProgress_3_table").bootstrapTable('refresh');
										$("#cyMeetTcC").click();
										//removePop2();
									}
									
									if(res.meetingResult == "meetingResult:1"){
										$("[data-btn='meeting']").remove(); //添加会议
										$("[data-btn='create']").show(); //创建成项目
									}else if(res.meetingResult == "meetingResult:3"){
										$("[data-btn='meeting']").remove(); //添加会议
										$("[data-btn='create']").remove(); //创建成项目
										//removePop1();
										//refreshIdeaList();
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
					$('#powindow[data-id="popid1"]').hideLoading();
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
						var _this = $("#ideaProgress_3_table");
						if(_this == null || _this.length == 0 || _this == undefined){
							removePop1();
						}else{
							$("#ideaProgress_3_table").bootstrapTable('refresh');
							$("#cyMeetTcC").click();
							//removePop2();
						}
						if(res.meetingResult == "meetingResult:1"){
							$("[data-btn='meeting']").remove(); //添加会议
							$("[data-btn='create']").show(); //创建成项目
						}else if(res.meetingResult == "meetingResult:3"){
							$("[data-btn='meeting']").remove(); //添加会议
							$("[data-btn='create']").remove(); //创建成项目
							//removePop1();
							//refreshIdeaList();
						}
					}
				},
				BeforeUpload:function(up){
					$('#powindow[data-id="popid1"]').showLoading(
							 {
							    'addClass': 'loading-indicator'						
							 });
				},
				Error: function(up, err) {
					$('#powindow[data-id="popid1"]').hideLoading();
					$("#savemeet").removeClass("disabled");
					$("#fileName").val("");
					layer.msg(err.message);
				}
			}
		});
		meetuploader.init();
		//----- end init upload
	}
  function claimFun(id){
	  if(id != ''){
			sendPostRequestByJsonObj(platformUrl.ideaClimat, {"id" :id,"ideaProgress":"ideaProgress:2"}, function(data){
				
				if(data.result.status == 'OK')
				{
					layer.msg("认领成功！");
					//getIdeaInfo(id)
					 refreshIdeaList();
			    	 refreshStageDialog(id);
				//setTimeout("$('.claimtc').find('[data-close='close']').click();",2000);  
					
				}else{
					layer.msg(data.result.message);
				}
				
			});
		}

  }
  

  /**
 * Idea信息加载后处理相关内容
 * @param idea
 */
function ideaLoaded(idea, index) {

	//==== begin  index = 1\4       创意刚创建 \搁置
	if (index == 1) {
		$("[data-btn='history']").hide(); //历史信息
		//if (idea.createdUid == userId) {   //任何角色都可以认领
			/*if (roleId != 4) {
				$("[data-btn='claim']").hide(); //认领
			}*/
			if(isContainResourceByMark("idea_claim")){
			       $('button[resource-mark="idea_claim"]').css("display","inline-block");
			}
		//}
	} else if (index == 4) { //index = 4   搁置
		//if (idea.createdUid == userId) {
			/*if (roleId != 4) {
				$("[data-btn='claim']").hide(); //认领
			}*/
			if(isContainResourceByMark("idea_claim")){
			       $('button[resource-mark="idea_claim"]').css("display","inline-block");
			}
		//}
	} else {
		$("[data-btn='claim']").hide(); //认领
	}

	if (idea.createdUid != userId || index > 4) {
		$("[data-btn='edith']").hide(); //编辑
	}
	
	if(index > 1){
		sendGetRequest(platformUrl.ideaCheckHistory + "/" + idea.id, null,
				function(data) {
					if (data.result.status == "ERROR") {
						layer.msg(data.result.message);
						$("[data-btn='history']").hide();
						return;
					} else if (data.result.status == "OK") {
						var canuse = data.result.message;
						if (canuse == 'n') {
							$("[data-btn='history']").hide(); //历史信息
						}
					}
				});
	}
	
	//==== end index = 1 \ 4   

	
	//====  index = 2   调研
	if (index == 2 && ideaInfo.claimantUid == userId) {
		sendGetRequest(platformUrl.ideaCheckHassReport + "/" + ideaInfo.id, null,
				function(data) {
					if (data.result.status == "ERROR") {
						layer.msg(data.result.message);
						return;
					} else if (data.result.status == "OK") {
						var num = data.result.message;
						if (num == 'not') {
							$("#cy_start_lxh").hide(); //启动创建立项会
						}else{
							$("#cy_up_report").remove(); //上传文档
						}
					}
				});
		
		
	} else {
		$("#options_point2").remove();
	}
	//==== end index = 2    调研

	
	//====  index = 3   创建立项会
	if (index == 3  && ideaInfo.claimantUid == userId) {
		sendGetRequest(platformUrl.ideaCheckPassMeet + "/" + ideaInfo.id, null,
				function(data) {
					if (data.result.status == "ERROR") {
						layer.msg(data.result.message);
						return;
					} else if (data.result.status == "OK") {
						var message = data.result.message;
						if (message == 'pass') {
							$("[data-btn='meeting']").remove(); //添加会议
						} else if(message == 'vote'){
							$("[data-btn='meeting']").remove(); //添加会议
							$("[data-btn='create']").remove(); //创建成项目
						}else{
							$("[data-btn='create']").hide(); //创建成项目
						}
					}

				});
		
	} else {
		$("#options_point3").remove();
	}
	//==== end index = 3   

	// begin index = 5   创建项目
	if (index == 5) {
		if(userId != idea.claimantUid){
			$("[data-btn='edit_name']").remove();
		}
	} else {
		$("[data-btn='edit_name']").remove();
	}
	// end index = 5   

}	
function setGiveUpInfo(abandoned){
	 var str="";
	 
	  if(typeof(abandoned.giveup) != "undefined"&&abandoned.giveup.length>0){
		 $.each(abandoned.giveup, function (i, value) {
			 var addiv='<div class="give_up clearfix">'+
			  '<div class="top clearfix"> <dl><dt>放弃人：</dt> <dd>'+value.abUsername+'</dd></dl>'+
			  '<dl><dt>放弃时间：</dt> <dd>'+value.abDatetimeToString.substr(0,10)+'</dd></dl></div>'+
			  '<div class="bottom clearfix"><dl><dt>放弃原因：</dt><dd>'+value.abReason+'</dd></dl></div>'+
			  '</div>';
			   str=str+addiv;
         });
		 $("#ideaDetail").append(str);
	  }
	 }
	        
       
  
  
function formatMeetNote(value,row,index){
	var str=delHtmlTag($.trim(value))
	var len=0;
	if(str!="" && typeof(str)!="undefined"){
		len = getLength(str);
	}
	if(value != ''){
		var strlog=delHtmlTag(value);
		var strrrr=strlog;
		if(len>200){
			var subValue =str.substring(0,200); 
			var rc = "<div id=\"log\" style=\"text-align:left;\" class=\"text-overflow\">"+
			subValue+
			"..."+"<a href=\"javascript:;\" class=\"blue option_item_mark\"  onclick=\"showLogdetail(\'"+row.id+"\',\'"+row.uid+"\',\'"+value +"\')\" >详情<a>"+    
		'</div>';
			return rc;
		}else {
			return strlog+"<a href=\"javascript:;\" class=\"blue option_item_mark\"  onclick=\"showLogdetail(\'"+row.id+"\',\'"+row.uid+"\',\'"+value +"\')\" >详情<a>";
		}
	}else{
		return "<a href=\"javascript:;\" class=\"blue option_item_mark\"  onclick=\"showLogdetail(\'"+row.id+"\',\'"+row.uid+"\',\'"+value +"\')\" >详情<a>"
	}
}

function showLogdetail(id,createdId,notes){
	var _url = Constants.sopEndpointURL+"/galaxy/idea/editnotes";
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			var um=UM.getEditor('notes');
			um.setContent(notes);
			if(userId!=createdId){
				$("#savenotes").hide();
			}
			$("#notesid").val(id);
		}//模版反回成功执行	
	});
	return false;
}

function savenotes(){  
	var um = UM.getEditor('notes');
	var notes = um.getContent();
	var pid=$("#notesid").val();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateMeet, {"id" : pid, "meetingNotes" : notes}, function(data){
			if (data.result.status=="OK") {
				layer.msg("保存成功");
				$(".meetingtc").find("[data-close='close']").click();
				$("#ideaProgress_3_table").bootstrapTable('refresh');
			} else {
				layer.msg(data.result.message);
			}
			
		});
	}
}