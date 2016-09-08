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
											var $planGrantMoney = $("#form_edit_actual_dialog").find("#label_plan_grant_money") //计划拨款金额
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
											if(data.entity.planGrantMoney){
												$planGrantMoney.html((data.entity.planGrantMoney ? addCommas(fixSizeDecimal(parseFloat(data.entity.planGrantMoney))) : 0) + "元");
											}
											if(data.entity.grantMoney){
												if(formdata.operatorFlag=='3'){
													$formGrantMoney.html(addCommas(fixSizeDecimal(data.entity.grantMoney)) + "元");
												}else{
													$formGrantMoney.val(data.entity.grantMoney);
												}
												
											}
											if(data.entity.surplusGrantMoney || data.entity.surplusGrantMoney==0){
													var grantMoneyOld=$formGrantMoney.val(); 
													var remainMoney = data.entity.surplusGrantMoney;
													remainMoney = addCommas(fixSizeDecimal(parseFloat(remainMoney)));
													remainMoneyTotal=data.entity.surplusGrantMoney+Number(grantMoneyOld); // 剩余+实际拨款
													$surplusGrantMoney.html("剩余金额" + remainMoney + "元");											         
													$formGrantMoney.blur(function(){
													var grantMoney=$formGrantMoney.val();
													
													if(!beforeSubmitById("form_edit_actual_dialog")){
														return false;
													}
													
													if(grantMoney<0){
														$surplusGrantMoney.html("剩余金额" + remainMoney + "元");
										 			 }else{
										 				var remainMoneyNew=fixSizeDecimal(parseFloat(remainMoneyTotal)-parseFloat(grantMoney),2);
										 				remainMoneyNew = parseFloat(remainMoneyNew);
														remainMoney = addCommas(fixSizeDecimal(parseFloat(remainMoneyNew)));
														if(remainMoneyNew<0 || remainMoneyNew==0){
															$surplusGrantMoney.html("剩余金额0元");
														}else{
														    $surplusGrantMoney.html("剩余金额" + remainMoney + "元");
														}
										 			 }
														          
												  })
											}
											$okBtn.click(function(){
												var saveParam = {
														preMoney : 	data.entity.grantMoney ? data.entity.grantMoney : 0,
														surplusGrantMoney : data.entity.surplusGrantMoney ? data.entity.surplusGrantMoney : 0
														
												}
												operator.save(saveParam);
											});
											$cancelBtn.click(function(){
												editApprActualDialog.close(_this);
											});
										}else{
											layer.msg(data.result.errorCode);
										}
									});
								},
								/**
								 * 编辑实际拨款弹窗
								 * preMoney : 初始实际金额
								 * surplusGrantMoney : 剩余金额
								 */
								save : function(saveParam){
									var grantMoney = $("#form_edit_actual_dialog").find("#form_grant_money").val();
									if(!beforeSubmitById("form_edit_actual_dialog")){
										return false;
									}else{
//										console.log(saveParam);
										//原理: 本次编辑后实际金额 - 本次编辑前 实际金额  应该小于等于 本次添加前剩余金额
										//修改后金额与初始实际金额差
										var fallMoney = fixSizeDecimal(parseFloat(grantMoney) - parseFloat(saveParam.preMoney),2);
										fallMoney = parseFloat(fallMoney);
										saveParam.surplusGrantMoney = parseFloat(fixSizeDecimal(parseFloat(saveParam.surplusGrantMoney),2));
//										console.log("修改后金额与修改前的金额差:" + fallMoney);
//										console.log("初始剩余金额" + saveParam.surplusGrantMoney);
										console.log(fallMoney>saveParam.surplusGrantMoney);
										if(fallMoney > saveParam.surplusGrantMoney){
											layer.msg("实际拨款金额之和大于分期拨款金额");
											return false;
										}
									}
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
												formdata.callFuc(data);
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



