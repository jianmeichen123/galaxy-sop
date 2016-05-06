	function dateFormatter(val,row,index)
	{
		if(!isNaN(val))
		{
			return Number(val).toDate().format("yyyy-MM-dd");
		}
		return val;
	}
	function datetimeFormatter(val,row,index)
	{
		if(!isNaN(val))
		{
			return Number(val).toDate().format("yyyy-MM-dd HH:mm:ss");
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
	function getProjectInfo(id) {
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
	function showIdeaDetail(ideaId)
	{
		var _url = platformUrl.ideaGoStage+"?id="+ideaId;
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				
				$(".creativetc .tabtable").tabchange2();
				
				//基本信息  -- 数据展示  index =1
				var ideaInfo = getProjectInfo(ideaId);
				
				//解析元素id和项目阶段值，以便之后做控制
				var progress = ideaInfo.ideaProgress;
				progress = progress.replace(":","_");
				var index = progress.substr("ideaProgress_".length);
				
				//按钮 bind 弹窗
				bindTcEvents(index);
				cyToProShow();
				
				for(var i = 1; i<6; i++){
					//当前阶段之后的tab变为不可用
					if(i > index){
						$("#ideaProgress" + i).addClass("disabled");
						$("#ideaProgress" + i).attr("disabled","disabled");
					}
					
					//为Tab添加点击事件，用于重新刷新
					$("#ideaProgress_" + i).on("click",function(){
						var id = $(this).attr("id");
						var indexNum = id.substr(id.length-1,1);
						switch(indexNum){
						case '1':
							
							break;
						case '2' : 
							tiggerTable($("#ideaProgress_2_table"),3);
							break;
						case '3':
							
							break;
						case '5':
							
							break;
						default :
							break;
						}
					});
				}	
				
				//默认打开当前阶段 
				$("#" + progress).addClass("on");
				$("#" + progress + "_con").css("display","block");
				
			}//end okback 模版反回成功执行	
		});
		return false;

	}
	
	
	//各阶段按钮绑定事件、弹窗
	//由项目当前阶段显示隐藏按钮
	function bindTcEvents(index){
		
		//基本信息 -->编辑
		$("[data-btn='edit']").on("click",function(){
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
			var _url = $self.attr("href");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){							
				}//模版反回成功执行	
			});
			return false;
		});
		
		
		//调研
		
		//放弃btn
		$("[data-btn='abandon']").on("click",function(){
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
		
		
		//创建立项会  --> 添加会员纪要
		$("[data-btn='meeting']").on("click",function(){
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
		//创建立项会 --> 创建项目
		$("[data-btn='create']").on("click",function(){
			$(".tabtable_con .block").eq(3).show().siblings().hide();
			$('.tablink li').eq(3).addClass('on').siblings().removeClass('on');
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
	function ideaOperateFormat(val,row,index)
	{
		if(row.fileKey){
			return "<a  href=\"#\" onclick=\"upreport(\"e\"+","+\""+row.projectPrograss+","+row.id+");\" class=\"blue\" >更新</a>" ;
		}
		return "-";
	}
	
	function ideaFileDownFormat(val,row,index)
	{
		if(row.fileKey){
			return "<a href=\"#\"  onclick=\"filedown("+row.id+","+row.fileKey+");\" class=\"blue\" >"+row.fileName+"</a>" ;
		}
		return "-";
	}
	
	function upreport(mark,prograss,id){
		if(mark == 'u'){
			$("#upload-dialog").find("input[name='projectId']").val("${id}");
			$("#upload-dialog").find("input[name='projectProgress']").val(prograss);
		}else if(mark == 'e'){
			$("#upload-dialog").find("input[name='id']").val(id);
			$("#upload-dialog").find("input[name='isEdit']").val("edit");
			$("#upload-dialog").find("input[name='projectId']").val("${id}");
			$("#upload-dialog").find("input[name='projectProgress']").val(prograss);
		}
	}
	
	function showUploadPopup(){
		$.popup({
			txt:$("#upload-dialog").html(),
			showback:function(){
				var _this = this;
				initIdeaUpload(_this);
			}
		});
	}
	
	
	function initIdeaUpload(_dialog){
		var ideaUploader = new plupload.Uploader({
			runtimes : 'html5,html4,flash,silverlight',
			browse_button : $(_dialog.id).find("#file-select-btn")[0], 
			url : "<%=path %>/galaxy/idea/ideaUpReport"+"?sid="+sessionId+"&guid="+userId,
			multi_selection:false,
			filters : {
				max_file_size : '25mb',
				mime_types: paramsFilter()
			},

			init: {
				PostInit: function() {
					$(_dialog.id).find("#upload-btn").click(function(){
						if(ideaUploader.files.length==0){
							layer.msg("请选择文件.");
							return;
						}
						ideaUploader.start();
						return false;
					});
				},

				FilesAdded: function(up, files) {
					if(ideaUploader.files.length >= 1){
						ideaUploader.splice(0, ideaUploader.files.length-1)
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
						$("#data-table").bootstrapTable('refresh');
					}
					else
					{
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
	
	
	
	
	
	
	
	
	/*function showIdeaDetail(ideaId)
	{
		var $self = $(this);
		var _url = platformUrl.ideaGoStage+"?id="+ideaId;
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$("[data-btn='meeting']").on("click",function(){
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
				$("[data-btn='abandon']").on("click",function(){
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
				$("[data-btn='edit']").on("click",function(){
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
				$("[data-btn='claim']").on("click",function(){
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
				$(".creativetc .tabtable").tabchange2();
				$('#project_name').click(function(){
					$('.block').css({
						display: 'none',
					});;
					$(".aa").show();
					$('.tablink li').eq(0).addClass('on').siblings().removeClass('on');
				});
				$('#project_name').click(function(){
					$(".tabtable_con .block").eq(0).show().siblings().hide();
					$('.tablink li').eq(0).addClass('on').siblings().removeClass('on');
				});
				$("[data-btn='create']").on("click",function(){
					$(".tabtable_con .block").eq(3).show().siblings().hide();
					$('.tablink li').eq(3).addClass('on').siblings().removeClass('on');
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
			}//模版反回成功执行	
		});
		return false;

	}*/