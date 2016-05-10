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
		return '<a href="#" class="blue" onclick="showIdeaDetail(\'' + row.id + '\')">'+val+'</a>';
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
				alert(data.result.message );
				return;
			}
			idea = data.entity;
			stockTransfer = idea.stockTransfer;
			var progress = idea.ideaProgress;
			if('ideaProgress:1' != progress && 'ideaProgress:4'!= progress){
				$("[data-btn='claim']").hide();
				$("[data-btn='edith']").hide();
			}
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
					self.text(text);
				}
				
			});
		});
		return idea;
	}
	
	
	/*	*//**创意阶段 - 待认领**//*
	public static final String IDEA_PROGRESS_DRL 			= "ideaProgress:1";
	*//**创意阶段 - 调研**//*
	public static final String IDEA_PROGRESS_DY 			= "ideaProgress:2";
	*//**创意阶段 - 创建立项会**//*
	public static final String IDEA_PROGRESS_CJLXH 			= "ideaProgress:3";
	*//**创意阶段 - 搁置**//*
	public static final String IDEA_PROGRESS_GZ				= "ideaProgress:4";
	*//**创意阶段 - 创建项目**//*
	public static final String IDEA_PROGRESS_CJXM 			= "ideaProgress:5";
*/
	//stage 弹出
	//
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
				//控制按钮显示、隐藏、可用。禁用等
				ideaLoaded(ideaInfo);
				//=====
				$("[data-id='ideaNowId']").val(ideaId);
				//====
				
				//解析元素id和项目阶段值，以便之后做控制
				var progress = ideaInfo.ideaProgress;
				progress = progress.replace(":","_");
				var index = progress.substr("ideaProgress_".length);
				//按钮 bind 弹窗
				bindTcEvents(index);
				cyToProShow();
				
				for(var i = 1; i<6; i++){
					//当前阶段之后的tab变为不可用;搁置时，与待认领相同；
					if(i > index || (i>1 && index==4)){
						$("#ideaProgress_" + i).addClass("disabled");
					}
					
					//为Tab添加点击事件，用于重新刷新
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
								tiggerTable($("#ideaProgress_2_table"),3);
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
				//显示当前阶段
				if(index == '2' || index == '3' || index== '5')
				{
					$("#" + progress ).click();
				}
				$("#ideaOperateLog").on("click",function(){
					var clickN = $("#ideaOperateLog").data("clickn");
					if(clickN != '0'){
						$("#ideaProgress_log_table").bootstrapTable('refresh');
					}else{
						tiggerTable($("#ideaProgress_log_table"),2);
						$("#ideaOperateLog").data("clickn","n");
					}
					//tiggerTable($("#ideaProgress_log_table"),3);
					//$("#projectProgress_table").bootstrapTable("refresh");
				});
				
				//默认打开当前阶段 
				//$("#" + progress).addClass("on");
				//$("#" + progress + "_con").css("display","block");
				var id=$("#IdeaId").val();
				$("#editBtn").click(function(){
					sendGetRequest(platformUrl.detailIdea+"/"+id,null,initCallBack.getdetailIdeaInfoCallBack);
				});
				layer.close(layerIndex);
			}//end okback 模版反回成功执行	
			
		});
		return false;

	}
	/**
	 * 刷新创意阶段弹窗
	 */
	function refreshStageDialog(ideaId)
	{
		$("#powindow,#popbg").remove();
		showIdeaDetail(ideaId);
	}
	
	//各阶段按钮绑定事件、弹窗
	//由项目当前阶段显示隐藏按钮
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
			var _url = $self.attr("href");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					 claimFun(id);
				     $(".claimtc_close").on("click",function(){
				    	 refreshIdeaList();
				    	 refreshStageDialog(id);
					})
				
				}//模版反回成功执行	
			});
			return false;
		});
		
		
		//调研
		
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
									layer.msg(data.result.errorCode);
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
				data:{ieadId:ideaInfo.id},//传递参数
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
			return "<a href=\"#\"  onclick=\"filedown("+row.id+","+row.fileKey+");\" class=\"blue\" >"+row.fileName+"</a>" ;
		}
		return "-";
	}
	
	
	
	
	// mark :u/上传  e/更新    prograss：ideaProgress:2 projectId:ideaid  fileid:
	function showUploadPopup(mark,prograss,fileid){
		$.popup({
			txt:$("#upload-dialog").html(),
			showback:function(){
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
					var $form = $(_dialog.id).find("#upload-form");
					var data = JSON.parse($form.serializeObject());
					data['fileType']=$(_dialog.id).find("[name='fileType']").val();
					up.settings.multipart_params = data;
				},
				FileUploaded: function(up, files, rtn) {
					var data = $.parseJSON(rtn.response);
					if(data.result.status == 'OK')
					{
						layer.msg("上传成功.");
						$(_dialog.id).find("[data-close='close']").click();
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
				$("#data_table").bootstrapTable('refresh');
				$("#powindow,#popbg").remove();
				showIdeaDetail(ideaId);
			}
			
		});
		
	}
	
	
	//gethtml 弹窗 加载
	function initCyMeetUpload(){
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
						var res = getMeetCondition("y",$("[data-id='ideaNowId']").val(), "meetingDateStr", "y","meetingType:3", "meetingResult","meetingNotes");
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
						var _this = $("#ideaProgress_3_table");
						if(_this == null || _this.length == 0 || _this == undefined){
							removePop1();
						}else{
							$("#ideaProgress_3_table").bootstrapTable('refresh');
							$("#cyMeetTcC").click();
							//removePop2();
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
		//----- end init upload
	}
	
	/**
	 * 
	 * 查询创意详情set值
	 * @param id
	 */
  function getIdeaInfoEdit(id,flag)
  {
   var url = platformUrl.detailIdea+"/"+id;
    sendGetRequest(
	url,
	{"id":"${id}"},
	function(data){
		if(data.result.status == "Error")
		{
			alert(data.result.message );
			return;
		}
		var idea = data.entity;
		stockTransfer = idea.stockTransfer;
		var um = UM.getEditor('edit_idea_desc');
	    getDepartment($("#department"));
		um.setContent(idea.ideaDesc);
	     $("#ideaId").val(idea.id);
		$("#ideaEdit dd").each(function(){
		  var self =$(this).children();
		if(self.attr('id') != 'undefined')
			{
			var formatter = self.data('formatter');
				var id = self.attr('id');
				var text = idea[id];
			   if(id=="department"){
				   if(self.children().val()==idea.departmentId){
					   self.children().attr("selected","selected");
					   $("#depid").val(idea.departmentId);
				   }
				  }else if($.isFunction(window[formatter]))
				{
					text = window[formatter].call(window,text);
					self.val(text);
				}else{
				    self.val(text);
				}
				
			}
			
		});
	}
);
}
  
  /**
   * 更新项目信息
   */
  function update(){
	  var um = UM.getEditor('edit_idea_desc');
		var ideaDesc = um.getContent();
	  var date={
			"id": $("#ideaId").val(),
			"ideaName":$("#ideaName").val(),
			"ideaDesc":ideaDesc,
			"ideaDescHtml":ideaDesc,
			"ideaSource":$("#ideaSource").val(),
			"departmentId":$("#depid").val()
	  };
		if(pid != '' && companyLocation != ''){
			sendPostRequestByJsonObj(platformUrl.ideaUpdateIdea, {"id" : pid, "companyLocation" : companyLocation}, saveSuccess());
		}

  	
  }
  function claimFun(id){
	  if(id != ''){
			sendPostRequestByJsonObj(platformUrl.ideaUpdateIdea, {"id" :id,"ideaProgress":"ideaProgress:2"}, function(data){
				
				if(data.result.status == 'OK')
				{
					//layer.msg("认领成功！");
					//setTimeout("$('.claimtc').find('[data-close='close']').click();",2000);  
					
				}else{
					layer.msg(data.result.errorCode);
				}
				
			});
		}

  }
  /**
   * Idea信息加载后处理相关内容
   * @param idea
   */
  function ideaLoaded(idea)
  {
	  //创建项目后 隐藏放弃按钮
	  if(idea.ideaProgress == 'ideaProgress:5')
	  {
		  $('[data-btn="abandon"]').hide();
		  $('[data-btn="create"]').hide();
	  }
	  //放弃时才显示历史信息按钮
	  if( idea.ideaProgress != 'ideaProgress:4')
	  {
		  $("[data-btn='history']").hide();
	  }
	  
  }	 