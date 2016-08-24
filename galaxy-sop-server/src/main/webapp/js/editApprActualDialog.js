/**
 * 编辑实际拨款弹窗
 * actualId : 实际付款ID 当为编辑和查看时 必传
 * parentId : 分拨付款ID 当为添加时必传
 * operatorFlag : 1:添加 2:编辑 3: 查看 默认为添加
 * callFuc : 自定义回掉
 * 	
 *  
 */
var editApprActualDialog = {
		init : function(formdata){
			$.getHtml({
				url:platformUrl.toEditApprActual,//模版请求地址
				data:"",//传递参数
				okback:function(_this){
					if(formdata.parentId || formdata.actualId ){
						var operator = {
								init : function(){
									var param = {
											id : formdata.actualId,
											partGrantId : formdata.parentId
									}
									sendPostRequestByJsonObj(platformUrl.initEditApprActual, param, function(data){
										if(data.result.status=='OK'){
											var $protocolName = $("#form_edit_actual_dialog").find("#label_protocol_name") //创业服务协议
											var $planGrantTime = $("#form_edit_actual_dialog").find("#label_plan_grant_time") //计划拨款时间
											var $labelGrantMoney = $("#form_edit_actual_dialog").find("#label_grant_money"); //实际拨款协议上层标签
											var $formGrantMoney = $("#form_edit_actual_dialog").find("#form_grant_money"); //实际拨款金额文本
											var $surplusGrantMoney = $("#form_edit_actual_dialog").find("#label_surplus_grant_money"); //剩余金额
											var $labelPopName = $("#form_edit_actual_dialog").find("#label_pop_name");
											var $okBtn = $("#form_edit_actual_dialog").find("#win_ok_btn");
											var $cancelBtn = $("#form_edit_actual_dialog").find("#win_cancel_btn");
											var popName = "";
											//popName 组装
											//按钮显示的控制
											if(formdata.operatorFlag=="2"){
												//编辑模式
												popName = "编辑"
											}else if(formdata.operatorFlag=="3"){
												//查看模式
												popName = "查看";
												$labelGrantMoney.html("<div id='form_grant_money'></div>");
												$formGrantMoney = $("#form_edit_actual_dialog").find("#form_grant_money");
												$okBtn.hide();
												$cancelBtn.hide();
											}else{
												//添加模式	
												popName = "添加";
											}
											
											$labelPopName.html(popName + "实际拨款信息");
											
											
											if(data.entity.protocolName){
												$protocolName.html(data.entity.protocolName);
											}
											if(data.entity.planGrantTime){
												$planGrantTime.html(data.entity.planGrantTime)
											}
											if(data.entity.grantMoney){
												if(formdata.operatorFlag=='3'){
													$formGrantMoney.html(addCommas(fixSizeDecimal(data.entity.grantMoney)) + "元");
												}else{
													$formGrantMoney.val(data.entity.grantMoney);
												}
												
											}
											if(data.entity.surplusGrantMoney){
												$surplusGrantMoney.html("剩余金额" + addCommas(fixSizeDecimal(data.entity.surplusGrantMoney)) + "元");
											}
											$okBtn.click(operator.save);
											$cancelBtn.click(function(){
												editApprActualDialog.close(_this);
											});
										}else{
											layer.msg(data.result.errorCode);
										}
									});
								},
								save : function(){	
									var form = {
										id : formdata.actualId,
										partGrantId : formdata.parentId,
										grantMoney : $("#form_edit_actual_dialog").find("#form_grant_money").val()
									}
									sendPostRequestByJsonObj(platformUrl.saveApprActual, form, function(data){
										if(data.result.status=='OK'){
											layer.msg("保存成功");
											editApprActualDialog.close(_this);
											if(formdata.callFuc){
												formdata.callFuc();
											}
										}else{
											layer.msg(data.result.errorCode);
										}
									});
								}
						}
						operator.init();
						
					}else{
						layer.msg("参数错误");
					}	
				}
			});
		},
		close : function(_this) {
			$("#popbg01").remove();
			//启用滚动条
			$(document.body).css({
				"overflow-x" : "auto",
				"overflow-y" : "auto"
			});
			//关闭对外接口
			_this.hideback.apply(_this);
			$(_this.id).remove();
			$('.tip-yellowsimple').hide();
			//判断是否关闭背景
			if ($(".pop").length == 0) {
				$("#popbg").hide();
				$('.tip-yellowsimple').hide(); //表单验证提示关闭
			}
			if ($(".pop").length == 0) {
	
				$("#popbg01").remove();
			}
	}
}



