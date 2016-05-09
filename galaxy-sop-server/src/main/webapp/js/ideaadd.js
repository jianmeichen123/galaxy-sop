	var ideaAddDialog = {
			fileKey : undefined,
			init : function(_formdata){
				ideaAddDialog.initData();
				ideaAddDialog.callFuc = _formdata.callFuc;
				$.popup({
					txt : $("#addDialog").html(),
					showback:function(){
						//alert("弹出层初始化");
						var _this = this;
						$("#addDialog").find(".meetingtc").remove();
						ideaAddDialog.fillData(_this,_formdata);
						initDialogVal();
						//保存事件绑定
						$(_this.id).find("#win_uploadBtn").click(ideaAddDialog.save);
						//关闭事件绑定
						$(_this.id).find("#win_cancelBtn").click(function(){
							//将弹框隐藏
							$("#addDialog").html($(_this.id).children(".poptxt").html());
							$(_this.id).remove();	
							//判断是否关闭背景
							if($(".pop").length==0){
								$("#popbg").hide();	
								$('.tip-yellowsimple').hide();	//表单验证提示关闭
							}
					});
						UM.getEditor('win_idea_desc');
					},
					hideback:function(t){
						var _this = this;
						$("#addDialog").html($(_this.id).children(".poptxt").html());
					}
					
				});
			},
			fillData : function(_this,_formdata){
				console.log("渲染页面数据");
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
					$departmentId.attr("readonly","readonly");
//					$departmentId.attr("disabled","disabled");
					$departmentId.attr("class","disabled");
				}
				
				//创意来源
				if(_formdata._ideaSource){
					$ideaSource.val(_formdata._ideaSource);
				}
				
				if(_formdata._ideaDescHtml){
					var um = UM.getEditor('win_idea_desc');
					um.setContent(_formdata._ideaDescHtml);
				}
				
				
				


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
				
			},
			//关闭弹出框
			close : function(_this){
					//关闭对外接口
					_this.hideback.apply(_this);
					$(_this.id).remove();
					//判断是否关闭背景
					if($(".pop").length==0){
						$("#popbg").hide();	
					}
			},
			save : function(){
				if(beforeSubmit()){
					var form = $("#idea_form").serializeObject();
					form = jQuery.parseJSON(form);
					var um = UM.getEditor('win_idea_desc');
					form.ideaDesc = delHtmlTag(um.getContent());
					sendPostRequestByJsonObj(platformUrl.addIdea,form,ideaAddDialog.saveCallBackFuc);

				}
				
			},
			saveCallBackFuc(data){
				console.log(data);
			}
	};
	
	var utils = {
			each : function(_data,_dom,type){
				_dom.empty();
				if(type=="all"){
					_dom.append("<option value='all'>--请选择--</option>");
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
							_createDate : data.entity.createDate,
							_ideaSource : data.entity.ideaSource,
							_ideaDescHtml : data.entity.ideaDesc,
							_callFuc : function(){}
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

