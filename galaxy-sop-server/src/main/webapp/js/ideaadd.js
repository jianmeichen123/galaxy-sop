var um;	
var ideaAddDialog = {
			fileKey : undefined,
			init : function(_formdata){
				ideaAddDialog.initData();
				ideaAddDialog.callFuc = _formdata._callFuc;
				$.popup({
					txt : $("#addDialog").html(),
					showback:function(){
						//alert("弹出层初始化");
						var _this = this;
						$("#addDialog").find(".meetingtc").remove();
						$(_this.id).find("#win_idea_name").attr("valType",'required');
						$(_this.id).find("#win_idea_department").attr("valType",'required');
						initDialogVal();	
						var operator = {
								save : function(){
									if(beforeSubmit()){
										var form = $("#idea_form").serializeObject();
										form = jQuery.parseJSON(form);
//										var um = UM.getEditor('win_idea_desc');
										form.ideaDesc = delHtmlTag(um.getContent());
										if(form.id==""){
											form.id = 0;
										}
										$(".pop").showLoading(
												 {
												    'addClass': 'loading-indicator'						
												 });
										sendPostRequestByJsonObj(platformUrl.addIdea,form,operator.saveCallBackFuc);

									}
									
								},
								saveCallBackFuc : function(data){
									if(data.result.status=="OK"){
										layer.msg(data.result.errorCode);
										if(data.id!=null&&typeof(data.id)!="undefind"){
											getIdeaInfo(data.id);
										}
										refreshIdeaList();
									}else{
										layer.msg(data.result.errorCode);
									}
									$(".pop").hideLoading();
//									$(".creativetc").remove();
//									$(".close").remove();
									operator.close(_this);
									ideaAddDialog.callFuc();
									
								},
								//关闭弹出框
								close : function(_this){
										//关闭对外接口
										_this.hideback.apply(_this);
										$(_this.id).remove();
										$('.tip-yellowsimple').hide();
										//判断是否关闭背景
										if($(".pop").length==0){
											$("#popbg").hide();	
										}
								}
						};
						
						$(_this.id).find("#win_uploadBtn").click(operator.save);
						//关闭事件绑定
						$(_this.id).find("#win_cancelBtn").click(function(){
							operator.close(_this);
						});
						um = UM.getEditor('win_idea_desc');
						um.ready(function() {
						    //设置编辑器的内容
//							um.setContent(_formdata._ideaDescHtml);
						    //获取html内容，返回: <p>hello</p>
//						    var html = ue.getContent();
//						    //获取纯文本内容，返回: hello
//						    var txt = ue.getContentTxt();
							ideaAddDialog.fillData(_this,_formdata,um);
							//保存事件绑定
							
						});

						
						
						
						
						
						
					},
					hideback:function(t){
						var _this = this;
						ideaAddDialog.resetData(_this);
						var win_idea_desc = $(_this.id).find("#win_idea_desc")[0];
						$("#addDialog").html($(_this.id).children(".poptxt").html());
						$("#addDialog").find(".edui-container").remove();
						$("#addDialog").find(".eduidd").append(win_idea_desc);
						$("#addDialog").find("#win_idea_desc").removeAttr("class");	
						$("#addDialog").find("#win_idea_desc").removeAttr("contenteditable");
						$("#addDialog").find("#win_idea_desc").css("width","100%");
						$("#addDialog").find("#umeditor_textarea_ideaDescHtml").remove();
						$("#addDialog").find("#win_idea_name").removeAttr("valType");
						$("#addDialog").find("#win_idea_department").removeAttr("valType");
						um.destroy();
						$(_this.id).find("#win_idea_desc").remove();
					}
					
				});
			},
			fillData : function(_this,_formdata,_um){
				var $id = $(_this.id).find("#win_idea_id");
				var $ideaCode = $(_this.id).find("#win_idea_code");
				var $ideaName = $(_this.id).find("#win_idea_name");
				var $departmentId = $(_this.id).find("#win_idea_department");
				var $createUid = $(_this.id).find("#win_idea_create_id");
				var $createUname = $(_this.id).find("#win_idea_create_name");
				var $createDate = $(_this.id).find("#win_idea_create_Date");
				var $ideaSource = $(_this.id).find("#win_idea_source");
				//创意id
				if(_formdata._id){
					$id.val(_formdata._id);
				}
				
				//创意编码
				if(_formdata._ideaCode){
					//此处应该获取项目名称
					$ideaCode.val(_formdata._ideaCode);
					$ideaCode.attr("readonly","readonly");
				}
				
				//提出人
				if(_formdata._createdUid && _formdata._createdUname){
					$createUid.val(_formdata._createdUid);
					$createUname.val(_formdata._createdUname);
					$createUname.attr("readonly","readonly");
//					$createUname.attr("disabled","disabled");
					
				}
				
				//创意名称
				if(_formdata._ideaName && _formdata._ideaName){
					$ideaName.val(_formdata._ideaName);
				}
				
				//提出时间
				if(_formdata._createDate){
					$createDate.val(_formdata._createDate);
					$createDate.attr("readonly","readonly");
//					$createDate.attr("disabled","disabled");
				}
				//所属事业线
				if(_formdata._departmentId){
					$departmentId.val(_formdata._departmentId);
					if(_formdata._departmentEditable=='false' ||( _formdata._ideaideaProgress!="ideaProgress:1"&&_formdata._ideaideaProgress!="ideaProgress:4")){
						$departmentId.attr("readonly","readonly");
						$departmentId.attr("class","disabled");
					}
					
				}
				
				//创意来源
				if(_formdata._ideaSource){
					$ideaSource.val(_formdata._ideaSource);
				}
				
				if(_formdata._ideaDescHtml){
//					var um = UM.getEditor('win_idea_desc');
					_um.setContent(_formdata._ideaDescHtml);	
				}else{
					_um.setContent("");
				}
				
				
				


			},
			resetData : function(_this){	
				$(_this.id).find("#win_idea_id").val("");
				$(_this.id).find("#win_idea_code").val("");
				$(_this.id).find("#win_idea_create_id").val("");
				$(_this.id).find("#win_idea_create_name").val("");
				$(_this.id).find("#win_idea_department").val("");
				$(_this.id).find("#win_idea_create_Date").val("");
				$(_this.id).find("#win_idea_source").val("");
				
				
			},
			initData : function(){
				//所属事业线
				sendGetRequest(platformUrl.getDepartMentDict+"/department",null,ideaAddDialog.initDataCallBack);
			},
			initDataCallBack : function(data){
				var _dom = $("#win_idea_department");
				utils.each(data,_dom,"all");
				},
			callFuc : function(){
				
			}
	};
	
	var utils = {
			each : function(_data,_dom,type){
				_dom.empty();
				if(type=="all"){
					_dom.append("<option value=''>--请选择--</option>");
				}
				$.each(_data.entityList,function(){
					if(this.code){
						_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
					}else{
						_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
					}
					
				});
			}
	}
	
	var initCallBack = {
			getAddIdeaInfoCallBack : function(data){
				if(data.result.status=="OK"){
					formdata = {
							_ideaCode : data.entity.ideaCode,
							_createdUid : data.entity.createdUid,
							_createdUname : data.entity.createdUname,
							_departmentId : data.entity.departmentId,
							_createDate : data.entity.createDate,
							_departmentEditable : data.entity.departmentEditable,
							_callFuc : function(){}
					}
					ideaAddDialog.init(formdata);
				}else{
					layer.msg(data.result.errorCode);
				}
				
			},
			getdetailIdeaInfoCallBack : function(data){
				if(data.result.status=="OK"){
					formdata = {
							_id:data.entity.id==null?"underfind":data.entity.id,
							_ideaCode : data.entity.ideaCode,
							_ideaName : data.entity.ideaName,
							_createdUid : data.entity.createdUid,
							_createdUname : data.entity.createdUname,
							_departmentId : data.entity.departmentId,
							_departmentEditable : data.entity.departmentEditable,
							_createDate : data.entity.createDate,
							_ideaSource : data.entity.ideaSource,
							_ideaDescHtml : data.entity.ideaDescHtml,
							_ideaideaProgress:data.entity.ideaProgress,
							_callFuc : function(){
								$(".creativetc").find("[data-close='close']").click();
								showIdeaDetail(data.entity.id);
							}
					}
					ideaAddDialog.init(formdata);
				}else{
					layer.msg(data.result.errorCode);
				}
			}
	}
	
	function init(){
		$("#addBtn").click(function(){
			sendGetRequest(platformUrl.getAddIdeaInfo,null,initCallBack.getAddIdeaInfoCallBack);
		});
	}
	
$(document).ready(init());

