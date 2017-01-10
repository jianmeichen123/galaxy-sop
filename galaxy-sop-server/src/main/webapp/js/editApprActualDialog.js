/**
 * 编辑实际注资弹窗
 * actualId : 实际付款ID 当为编辑和查看时 必传
 * parentId : 分拨付款ID 当为添加时必传
 * operatorFlag : 1:添加 2:编辑 3: 查看 默认为添加
 * callFuc : 自定义回掉
 * 	
 *  
 */

var preMoney = 0;  //初始实际金额
var surplusGrantMoney = 0; //初始剩余金额

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
										
										preMoney =	data.entity.grantMoney ? data.entity.grantMoney : 0;
										surplusGrantMoney = data.entity.surplusGrantMoney ? data.entity.surplusGrantMoney : 0;
														
										var $investors = $("#form_edit_actual_dialog").find("#label_investors"); //投资方
										var $projectCompany = $("#form_edit_actual_dialog").find("#label_projectCompany"); //目标公司
										
										var $actualTimeDiv = $("#form_edit_actual_dialog").find("#div_label_actualTime"); //实际注资日期  -- 123
										var $actualTime = $("#form_edit_actual_dialog").find("#label_actualTime"); //实际注资日期  -- 123
										
										//var $finalContribution = $("#form_edit_actual_dialog").find("#label_finalContribution"); //项目注资金额
										var $finalShareRatio = $("#form_edit_actual_dialog").find("#label_finalShareRatio"); //股权占比
										var $serviceCharge = $("#form_edit_actual_dialog").find("#label_serviceCharge"); //加速服务费占比
										var $finalValuations = $("#form_edit_actual_dialog").find("#label_finalValuations"); //项目估值
										
										
										var $protocolName = $("#form_edit_actual_dialog").find("#label_protocol_name"); //创业服务协议
										var $planGrantTime = $("#form_edit_actual_dialog").find("#label_plan_grant_time"); //计划注资时间
										var $planGrantMoney = $("#form_edit_actual_dialog").find("#label_plan_grant_money"); //计划注资金额
										var $labelGrantMoney = $("#form_edit_actual_dialog").find("#label_grant_money"); //实际注资协议上层标签
										var $formGrantMoney = $("#form_edit_actual_dialog").find("#form_grant_money"); //实际注资金额文本
										var $surplusGrantMoney = $("#form_edit_actual_dialog").find("#label_surplus_grant_money"); //剩余金额
										var $labelPopName = $("#form_edit_actual_dialog").find("#label_pop_name");
										var $okBtn = $("#form_edit_actual_dialog").find("#win_ok_btn");
										var $cancelBtn = $("#form_edit_actual_dialog").find("#win_cancel_btn");
										var popName = "";
										//popName 组装
										//按钮显示的控制
										if(formdata.operatorFlag=="2"){
											//编辑模式
											popName = "编辑";
											$("#show_actual_file").remove();
											
											if(data.entity.files ==null || data.entity.files.length == 0 ){
												$("#filelist").hide();
											}else{
												$.each(data.entity.files,function(){
													var but = "<button type='button' id='"+this.id+"btn' onclick=del('"+this.id+"','"+this.fileName+"','textarea2')>删除</button>" ;
													var htm = "<tr id='"+this.id+"tr'>"+
																	"<td>"+this.fileName+"."+this.fileSuffix+
																		"<input type=\"hidden\" name=\"oldfileids\" value='"+this.id+"' />"+
																	"</td>"+
																	"<td>"+plupload.formatSize(this.fileLength)+"</td>"+
																	"<td>"+ but +"</td>"+
																	"<td>100%</td>"+
																"</tr>"
													$("#filelist").append(htm);
												});
											}
											
											operator.toInitBachUpload();
										}else if(formdata.operatorFlag=="3"){
											//查看模式
											popName = "查看";
											$labelGrantMoney.html("<div id='form_grant_money'></div>");
											$formGrantMoney = $("#form_edit_actual_dialog").find("#form_grant_money");
											
											$actualTimeDiv.html("<div id='label_actualTime_r'></div>");
											$actualTime = $("#form_edit_actual_dialog").find("#label_actualTime_r");
											
											
											$.each(data.entity.files,function(){
												var htm = "<div>"+
																this.fileName+"."+this.fileSuffix+
															"</div>"
												$("#show_actual_file").find("dd").append(htm);
											});
											
											$("#choose_up_file").remove();
											$("#show_up_file").remove();
											$okBtn.hide();
											$cancelBtn.hide();
										}else{
											//添加模式	
											popName = "添加";
											$("#show_actual_file").remove();
											$("#filelist").hide();
											operator.toInitBachUpload();
										}
										
										$labelPopName.html(popName + "实际注资信息");
										
										
										if(data.entity.protocolName){  //创业服务协议
											$protocolName.html(data.entity.protocolName);
										}
										
										if(data.entity.investors){  //投资方
											$investors.html(data.entity.investors);
										}
										if(data.entity.projectCompany){  //目标公司
											$projectCompany.html(data.entity.projectCompany);
										}
										
										if(data.entity.planGrantTime){ //计划注资时间
											$planGrantTime.html(data.entity.planGrantTime)
										}
										
										if(data.entity.actualTime){  //实际注资日期
											if(formdata.operatorFlag=='3'){
												$actualTime.html(data.entity.actualTime);
											}else{
												$actualTime.val(data.entity.actualTime);
											}
										}
										/*
										if(data.entity.finalContribution){ 
											$finalContribution.html(addCommas(fixSizeDecimal(data.entity.finalContribution)) + " 万元")
										}
										 */
										if(data.entity.planGrantMoney){
											$planGrantMoney.html((data.entity.planGrantMoney ? fixSizeDecimal(parseFloat(data.entity.planGrantMoney),4) : 0) + " 万元");
										}
										
										if(data.entity.finalShareRatio){ //股权占比
											$finalShareRatio.html(data.entity.finalShareRatio + " %");
										}
										
										if(data.entity.serviceCharge){ //加速服务费占比
											$serviceCharge.html(data.entity.serviceCharge + " %");
										}
										
										
										if(data.entity.grantMoney){ //实际注资金额
											if(formdata.operatorFlag=='3'){
												$formGrantMoney.html(fixSizeDecimal(data.entity.grantMoney,4) + " 万元");
											}else{
												$formGrantMoney.val(data.entity.grantMoney);
											}
											
										}
										if(data.entity.surplusGrantMoney || data.entity.surplusGrantMoney==0){ //剩余金额
												var grantMoneyOld=$formGrantMoney.val(); 
												var remainMoney = data.entity.surplusGrantMoney;
												remainMoney = fixSizeDecimal(parseFloat(remainMoney),4);
												remainMoneyTotal=data.entity.surplusGrantMoney+Number(grantMoneyOld); // 剩余+实际注资
												$surplusGrantMoney.html("剩余金额" + remainMoney + " 万元");											         
												$formGrantMoney.blur(function(){
												var grantMoney=$formGrantMoney.val();
												if(!beforeSubmitById("form_edit_actual_dialog")){
													$surplusGrantMoney.html("剩余金额" + fixSizeDecimal(parseFloat(remainMoneyTotal),4) + " 万元");		
													return false;
												}
												
												if(grantMoney<0){
													$surplusGrantMoney.html("剩余金额" + remainMoney + " 万元");	
									 			 }else{
									 				var remainMoneyNew=fixSizeDecimal(parseFloat(remainMoneyTotal)-parseFloat(grantMoney),4);
									 				remainMoneyNew = parseFloat(remainMoneyNew);
													remainMoney = fixSizeDecimal(parseFloat(remainMoneyNew),4);
													if(remainMoneyNew<0 || remainMoneyNew==0){
														$surplusGrantMoney.html("剩余金额0元");
													}else{
													    $surplusGrantMoney.html("剩余金额" + remainMoney + " 万元");		
													}
									 			 }
													          
											  })
										}
										
										if(data.entity.finalValuations){ //项目估值
											$finalValuations.html(fixSizeDecimal(data.entity.finalValuations,4) + " 万元");		
										}
										
										/*$okBtn.click(function(){
											var saveParam = {
													preMoney : 	data.entity.grantMoney ? data.entity.grantMoney : 0,
													surplusGrantMoney : data.entity.surplusGrantMoney ? data.entity.surplusGrantMoney : 0
													
											}
											operator.save(saveParam);
										});*/
										$cancelBtn.click(function(){
											editApprActualDialog.close(_this);
										});
									}else{
										layer.msg(data.result.errorCode);
									}
								});
							},
							/**
							 * 编辑实际注资弹窗
							 * preMoney : 初始实际金额
							 * surplusGrantMoney : 剩余金额
							 */
							toInitBachUpload : function(){
								toBachUpload(Constants.sopEndpointURL+'galaxy/sopFile/sendUploadByRedis',
										platformUrl.saveApprActual,"textarea2","select_btn","win_ok_btn","container","filelist",
										operator.paramsContion,"form_edit_actual_dialog",operator.saveCallBackFuc);
							},
							paramsContion : function(){
								
								var grantMoney = $("#form_edit_actual_dialog").find("#form_grant_money").val();
								if(!beforeSubmitById("form_edit_actual_dialog")){
									return false;
								}else{
									//原理: 本次编辑后实际金额 - 本次编辑前 实际金额  应该小于等于 本次添加前剩余金额
									//修改后金额与初始实际金额差
									var fallMoney = fixSizeDecimal(parseFloat(grantMoney) - parseFloat(preMoney),4);
									fallMoney = parseFloat(fallMoney);
									surplusGrantMoney = parseFloat(fixSizeDecimal(parseFloat(surplusGrantMoney),4));
//										console.log("修改后金额与修改前的金额差:" + fallMoney);
//										console.log("初始剩余金额" + saveParam.surplusGrantMoney);
									console.log(fallMoney>surplusGrantMoney);
									if(fallMoney > surplusGrantMoney){
										layer.msg("实际注资金额之和大于分期注资金额");
										return false;
									}
								}
								var form = {
									id : formdata.actualId,
									partGrantId : formdata.parentId,
									grantMoney : $("#form_edit_actual_dialog").find("#form_grant_money").val(),
									actualTime : $("#form_edit_actual_dialog").find("#label_actualTime").val()
								}
								
								form.fileReidsKey = Date.parse(new Date());
								form.fileNum = $("#filelist").find("tr").length - 1;
								
								var oldFids=[];
								var oldfileids = $("input[name='oldfileids']");
								if(oldfileids && oldfileids.length > 0){
									$.each(oldfileids, function(i) { 
										var idVal = oldfileids[i].value;
									   	if(!isNaN(idVal)){
									   		oldFids.push(idVal);
									   	}
									});
									form.fileIds = oldFids;
								}
								console.log(form);
								return form;
							},
							saveCallBackFuc : function(data){
								if(data.result.status=='OK'){
									layer.msg("保存成功");
									editApprActualDialog.close(_this);
									if(formdata.callFuc){
										formdata.callFuc(data);
									}
								}else{
									layer.msg(data.result.errorCode);
								}
							}
							/*,
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
										layer.msg("实际注资金额之和大于分期注资金额");
										return false;
									}
								}
								var form = {
									id : formdata.actualId,
									partGrantId : formdata.parentId,
									grantMoney : $("#form_edit_actual_dialog").find("#form_grant_money").val(),
									actualTime : $("#form_edit_actual_dialog").find("#label_actualTime").val()
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
							}*/
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



