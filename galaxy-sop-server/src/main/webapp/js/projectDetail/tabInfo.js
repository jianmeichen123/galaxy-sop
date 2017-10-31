$(function(){
		//统一显示
		if(isTransfering == 'true')
		{
			$('[data-on="data-open"]').addClass('limits_gray');
		}
		//统一关
		$('[data-on="close"]').on('click',function(){
			var close=$(this).attr('data-name')
			$('.'+close+'_current').hide();//basic_current
			$('.'+close+'_on').hide();
			$('.'+close+'_center').show();
			$('.bj_hui_on').hide();
			$('.tip-yellowsimple').hide();
			$("label.error").hide();
			$("body").css('overflow-y','auto');
		})
		
		/**
		 * 组装数据
		 */
		function responseData(){
			var sel_val = $("#financeStatusDs").text();
			if(sel_val!="A+轮"){
				$("#finance_status_sel").find('option[text='+sel_val+']').prop("selected",true);
			}
		}
		
	   /**
		 * 加载项目详情数据
		 */
		var projectInfoDetail = '';
		var projectInfoReport=[];
		sendGetRequest(Constants.sopEndpointURL+"/galaxy/infoProject/getTitleRelationResults/4/"+projectInfo.id, null, function(data){
			if(data.result.status=='OK'){
				console.log(data)
				projectInfoDetail=data.userData.pro;
				projectInfoList=data.userData.report[0].childList;
			}
		})
		//项目名称截断
		if(projectInfoDetail.projectName.length>24){
			var str=projectInfoDetail.projectName.substring(0,24);
		}
		$("#project_name").text(str);
		$("#project_name").attr("title",projectInfo.projectName);
		var projectPro = projectInfoDetail.projectProgress;
		if(projectPro=="projectProgress:10"){
			$("#end").hide();
			$("#s").hide();
		}
		var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);
			$("#project_name_title").text(projectInfoDetail.projectName);
			$("#project_name_t").text(projectInfoDetail.projectName);
			$("#project_name").text(projectInfoDetail.projectName);
			$("#project_code").text(projectInfoDetail.projectCode);
			$("#create_date").text(projectInfoDetail.createDate);
			$("#updateDate").text(projectInfoDetail.updateDate);
			$("#createUname").text(projectInfoDetail.createUname);
			$("#projectCareerline").text(projectInfoDetail.projectCareerline);
			$("#projectType").text(projectInfoDetail.type);
			$("#projectProgress").text(projectInfoDetail.progress);
			$("#projectStatusDs").text(projectInfoDetail.projectProgress=="projectProgress:10"?"":projectInfoDetail.projectStatusDs);
			$("#financeStatusDs").text(projectInfoDetail.financeStatusDs==null?"-":projectInfoDetail.financeStatusDs);
			$("#industryOwnDs").text(projectInfoDetail.industryOwnDs);
			$("#faName").text(projectInfoDetail.faFlagStr);
			if(projectInfoDetail.faFlag=="projectSource:1"){
				$("#faName").attr('data-original-title',projectInfoDetail.faName);
				$("#faName[data-toggle='tooltip']").tooltip();//提示
			}else{
				$("#faName").removeAttr('data-original-title');
			}
			$('.version19_detail_header_box').css('visibility','visible')
		    $("#remarkStr").text(projectInfoDetail.remark==""?"无":(projectInfoDetail.remark==null?"无":projectInfoDetail.remark));
		    var ht=projectProgress(data)
			//$("#insertImg").html(ht);
			//详情展示投资形式处理
			$("#financeMode").text(typeof(projectInfoDetail.fModeRemark)=="undefined"?"—":(projectInfoDetail.fModeRemark==0?"—":projectInfoDetail.fModeRemark));
		    $("#financeMode").attr("data-original-title",typeof(projectInfoDetail.fModeRemark)=="undefined"?"—":(projectInfoDetail.fModeRemark==0?"—":"点击查看"+projectInfoDetail.fModeRemark+"列表"));
		    $("#financeMode").tooltip();//提示
		    if($("#financeMode").text()=="独投"){
				$("#financeMode").removeAttr("data-original-title");
				$("#financeMode").addClass("hide");
			}else{
				$("#financeMode").removeClass("hide");
			}
		    
		    if(projectInfoDetail.financeMode!=undefined&&projectInfoDetail.financeMode!="financeMode:0"){
				jointDeliveryList(projectInfoDetail.jointDeliveryList);
				//列表无数据时不显示表格
				var trLen=$("#jointDelivery").find("tr:gt(0)").length;
				if(trLen==0){
					$("#jointDelivery").hide();
				}else{
					$("#jointDelivery").show();
				}
			}
			//report信息
		    updataReport(projectInfoList);	    
			if(roleId==4){   //投资经理a看投资经理B的项目，团队，法人，股权，融资隐藏
				var roleProject=$('#createUname').text();
				var roleLogin=$('.man_info .name').text();
				if(roleProject==roleLogin){
					$('.role_hide').show();
				}
			}else{
				$('.role_hide').show();
			}
			var p;
			var fs;
			
        $("[data-on='data-open']").click(function (){
        	    isDelete=[];
				if($(this).hasClass('limits_gray'))
				{
					return;
				}
				var open=$(this).attr('data-cont');
				var common = $(this).attr('data-name');
				if(open==="invest"){
					$(".basic_common_width").css('max-height','505px');
				};
				if(open==="legal"){
					var companyName= $('#projectCompany').text();
					if($(this).hasClass('limits_gray'))
					{
						return;
					}
					buildShareResult("4","5812");
					
				}
				
				//外层div一直显示 basic_on  show
				$('.'+common+'_on').show();
				//内部弹窗根据条件显示
				$('.'+open+'_current').show();
				$('.bj_hui_on').show();//遮罩层
				$("body").css('overflow','hidden');
				//浏览器窗口带下改变，弹层重新定位
				popMiddle()
				function popMiddle(){
					var wh = parseInt($(".basic_common_width").outerWidth(true)),
					ht = parseInt($(".basic_common_width").outerHeight(true));
					var win_w = $(window).width(),
					win_h = $(window).height(),
					win_x = (win_w-wh)/2,
					win_y = (win_h-ht)/2;
					//弹出层定位+显示
					$(".basic_common_width").Fixed({
						x:win_x,
						y:win_y
					});
				}
				$(window).resize(function(){
					popMiddle()
				})
				//基本信息修改
				$("#editImg").html(ht);
				$("#project_name_edit").val(projectInfoDetail.projectName);
				$("#create_date_edit").text(projectInfoDetail.createDate);
				$("#updateDate_edit").text(projectInfoDetail.updateDate);
				$("#createUname_edit").text(projectInfoDetail.createUname);
				$("#projectCareerline_edit").text(projectInfoDetail.projectCareerline);
				$("#projectType_edit").text(projectInfoDetail.type);
				$("#projectProgress_edit").text(projectInfoDetail.progress);
				$("#projectStatusDs_edit").text(projectInfoDetail.projectStatusDs);
				$("#financeStatusDs_edit").text(projectInfoDetail.financeStatusDs);
				$("#industry_own_sel").val(projectInfoDetail.industryOwnDs);
				$("#finance_status_sel").val(projectInfoDetail.financeStatusDs)
				$("input[name='projectSource']").val(projectInfoDetail.faFlagStr).attr('data-flag',projectInfoDetail.faFlag);
				//添加投资形式字段
				if(projectInfoDetail.financeMode!=undefined&&projectInfoDetail.financeMode!=""){
					var financeForms=$("input[name='investForm']");
					for(var i=0;i<financeForms.length;i++){
						if(financeForms[i].value==projectInfoDetail.financeMode){
							financeForms[i].parentNode.className += " radio_checked";
							financeForms[i].checked=true;
						}
				
				}
					//机构显示
					var investForm= $("input[name='investForm']:checked").val();
					if(investForm=="financeMode:1"){
						$(".institution .new_color_gray").text("领投机构：");
						$(".invest_institue").show();
					}else if(investForm=="financeMode:2"){
						$(".institution .new_color_gray").text("合投机构：");
						$(".invest_institue").show();
					}else{
						$(".invest_institue").hide();
					}
					
					if(projectInfoDetail.financeMode!=undefined&&projectInfoDetail.financeMode!="financeMode:0"){
						jointDeliveryEdit(projectInfoDetail.jointDeliveryList);
					}
				}else{
					$("input[name='investForm']").removeAttr("checked");
					$(".inputsForm").find(".block_inputs").remove();
					$(".institution").hide();
				}
				//详情页无列表，取消再编辑，取消上一步操作
				if($("#jointDelivery").is(":hidden")){
					$(".institutionBtn span").css("margin-top","0")
				}
				//投资形式合投，领头编辑页面投资列表处理
				
				 p=projectInfoDetail.industryOwn;
			    fs=projectInfoDetail.financeStatus;
			    var sectionName = $(this).data('name');
			    if('basic' == sectionName)
		    	{
			    	//融资
			    	sendGetRequest(platformUrl.queryAllTitleValues+'FNO1?reportType=4', null,CallBackB);
			    	sendGetRequest(platformUrl.searchDictionaryChildrenItems+"industryOwn",null,CallBackA);
			    	/**
			    	 * 查询项目来源
			    	 * @version 2017-09-18
			    	 */
			    	$("select[name='projectSource'] option").not(":first").remove();   //项目来源加载前清空
			    	sendGetRequest(platformUrl.searchDictionaryChildrenItems+"projectSource", null,CallBackC);
		    	}
			    responseData()//数据反显
			    radio_faFlag(projectInfoDetail.faFlag);
				if(typeof(projectInfoDetail.faFlag)!="underfined" && projectInfoDetail.faFlag=="projectSource:1"){
					$("select[name='projectSource']").find("option[value='"+projectInfoDetail.faFlag+"']").prop("selected",true);
					$("#faNameEdit").val(projectInfoDetail.faName);
					$("#faNameEdit").css("display","block");
					$("#faNameEdit").attr('required','required');
				}else{
					$("select[name='projectSource']").find("option[value='"+projectInfoDetail.faFlag+"']").prop("selected",true);
					$("#faNameEdit").css("display","none");
				}
			
			})
				function CallBackB(data){
			    var _dom=$("#finance_status_sel").next('ul');
			        _dom.html("");
			        //_dom.append('<option value="">--请选择--</option>');
			    var childNum = _dom.find("option").length;
			    var valueId=$("#financeStatusDs").attr("value");
			    var resultId=$("#financeStatusDs").attr("data-result-id");
			    var entity=data.entity.childList[0];
			    $("#finance_status_sel").attr({"data-title-id":entity.titleId,"data-type":entity.type,"data-result-id":resultId});
			    if(!childNum || childNum !=0 ){
			    	$.each(entity.valueList,function(){ 
			    		_dom.append("<li value='"+this.id+"' data-title-id='"+this.titleId+"' text='"+this.name+"'>"+this.name+"</li>");
						
					});
			    }
			    $("select[data-title-id]").change(function(){
			    	$(this).attr("tochange",true)
			    })
				$("#dropdown ul li").click(function(){
					var target = $(this).closest('#dropdown').find('input');
					target.removeClass('up')
					var txt = $(this).text(); 
					target.val(txt)
					$("#dropdown ul").hide(); 
					$(this).closest('#dropdown').find('input').attr('tochange',true);
			});
				 
			}
			function CallBackA(data){
			       var _dom=$("#industry_own_sel").next('ul');
			           _dom.html("");
			           //_dom.append('<option value="">--请选择--</option>');
			       var childNum = _dom.find("option").length;
				    if(!childNum || childNum !=0 ){
				    	$.each(data.entityList,function(){
								if(this.code==p){
									_dom.append("<li selected value='"+this.code+"'>"+this.name+"</li>");
								}else{
									_dom.append("<li value='"+this.code+"'>"+this.name+"</li>");
								}
						});
				    }
					$("#dropdown ul li").click(function(){
						var target = $(this).closest('#dropdown').find('input');
						target.removeClass('up')
						var txt = $(this).text(); 
						target.val(txt)
						$("#dropdown ul").hide(); 
				});
					 
				}
			function CallBackC(data){
			       var _dom=$("input[name='projectSource']").next('ul');
			           _dom.html("");
			           //_dom.append('<option value="">--请选择--</option>');
			       var childNum = _dom.find("option").length;
				    if(!childNum || childNum !=0 ){
				    	$.each(data.entityList,function(){
								if(this.code==p){
									_dom.append("<li selected value='"+this.code+"'>"+this.name+"</li>");
								}else{
									_dom.append("<li index='"+this.value+"' value='"+this.code+"'>"+this.name+"</li>");
								}
						});
				    }
					$("#dropdown ul li").click(function(){
						var target = $(this).closest('#dropdown').find('input');
						target.removeClass('up')
						var txt = $(this).text(); 
						var faFlag=$(this).attr('value');
						target.val(txt).attr('data-flag',faFlag);
						$("#dropdown ul").hide(); 
						if(txt=="FA"){
							$("#projectSource-error").hide();
							$(this).closest('td').find(".input_FA").show();
							$(this).closest('td').find(".input_FA").attr("required","required");
						}else{
							$(this).closest('td').find(".input_FA").hide();
							$(this).closest('td').find(".input_FA").remove("required");
							$("#faNameEdit-error").hide();
						}
				});
				}
			//表格渲染 
			info_table("NO9_1","融资历史：",$("table.fina_history"));
			info_table("NO9_1","股权结构：",$("#equity"));
			$(".location_show").show();
			$.each($(".member table"),function(){
				var table =$(this);
				check_table_tr_edit(table);
			})
			
			/**
			 * 商业计划
			 */
			var data = {
					_projectId : pid,
					_projectName : projectInfoDetail.projectName,
					_domId :'business_plan'			
			}
			initPage.init(data);
			/**
		 * 计算初始估值
		 */
		$("#project_share_ratio_edit").blur(function(){
			var valuations = calculationValuations();
			$("#project_valuations_edit").val("");
			if(valuations){
				$("#project_valuations_edit").val(valuations).attr("tochange",true);
			}
		});
		/**
		 * 计算初始估值
		 * project_contribution_edit
		 * project_valuations_edit
		 * project_share_ratio_edit
		 */
		$("#project_contribution_edit").blur(function(){
			var valuations = calculationValuations();
			$("#project_valuations_edit").val("");
			if(valuations){
				$("#project_valuations_edit").val(valuations).attr("tochange",true);
			}
		});
		/**
		 * 计算初始估值
		 */
		function calculationValuations(){
			var projectShareRatio = $("#project_share_ratio_edit").val();
			var projectContribution = $("#project_contribution_edit").val();
			if(projectShareRatio > 0 && projectContribution > 0){
				return (projectContribution * (100/projectShareRatio)).toFixed(4);
			}
			return null;
		}
		//实际值计算************************************************************
		/**
		 * 计算实际估值
		 */
		$("#finalShareRatio_edit").blur(function(){
			var valuations = finalValuations();
			$("finalValuations_edit").val("");
			if(valuations){
				$("#finalValuations_edit").val(valuations).attr("tochange",true);
			}
		});
		/**
		 * 计算实际投资
		 * project_contribution_edit
		 * project_valuations_edit
		 * project_share_ratio_edit
		 */
		$("#finalContribution_edit").blur(function(){
			var valuations = finalValuations();
			$("#finalValuations_edit").val("");
			if(valuations){
				$("#finalValuations_edit").val(valuations).attr("tochange",true);
			}
		});
		/**
		 * 计算初始估值
		 */
		function finalValuations(){
			var projectShareRatio = $("#finalShareRatio_edit").val();
			var projectContribution = $("#finalContribution_edit").val();
			if(projectShareRatio > 0 && projectContribution > 0){
				return (projectContribution * (100/projectShareRatio)).toFixed(4);
			}
			return null;
		}


		function projectProgress(data){
			var projectPro = projectInfoDetail.projectProgress;
			var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);
			var proStatus = projectInfoDetail.projectStatus;
			var pronum = proStatus.substring(proStatus.lastIndexOf(":")+1,proStatus.length);
			if(pronum == 0 || pronum == 1){
				return "<img src='"+Constants.sopEndpointURL+"img/process/p"+num+".gif' >";
			}else{
				return "<img src='"+Constants.sopEndpointURL+"img/process/pd"+num+".gif'>";
			}
		}
		$("[data-on='save']").click(function(){
			var s_type=$(this).attr("save_type");
			var data="";
			if(s_type=="finance"){
				if(!$("#basicForm").validate().form())
				{
					return;
				}
				data="";
				saveBaseInfo("basicForm",s_type);
				return;
			}else if(s_type=="save_basic"||s_type=="real_invest"){
				data=getUpdateData();
				if(!$("#basicForm").validate().form())
				{
					return;
				}
				saveBaseInfo("basicForm");
				sendPostRequestByJsonObj(platformUrl.updateProject,data, function(data2){
					if(data2.result.status=="OK"){
						layer.msg(data2.result.message);
						//弹窗关闭
						var close="basic"
						$('.'+close+'_current').hide();//basic_current
						$('.'+close+'_on').hide();
						$('.'+close+'_center').show();
						$('.bj_hui_on').hide();
						$("body").css('overflow-y','auto');
						sendGetRequest(Constants.sopEndpointURL+"/galaxy/infoProject/getTitleRelationResults/4/"+projectInfo.id,null, function(data){	
							projectInfoDetail=data.userData.pro;
							$("#project_name_t").text(projectInfoDetail.projectName);
							$("#industryOwnDs").text(projectInfoDetail.industryOwnDs);
							$("#financeStatusDs").text(projectInfoDetail.financeStatusDs==null?"-":projectInfoDetail.financeStatusDs);
							$("#projectType").text(projectInfoDetail.type);
							$("#faName").text(projectInfoDetail.faFlagStr);
							if(projectInfoDetail.faFlag=="projectSource:1"){
								$("#faName").attr('data-original-title',projectInfoDetail.faName);
								$("#faName[data-toggle='tooltip']").tooltip();//提示
							}else{
								$("#faName").removeAttr('data-original-title');
							}
						});
						updateReportMoney(); 
						jointDeliveryList(projectInfoDetail.jointDeliveryList);//合投机构 
						$("#financeMode").text(typeof(projectInfoDetail.fModeRemark)=="undefined"?"—":(projectInfoDetail.fModeRemark==0?"—":projectInfoDetail.fModeRemark));
						$("#financeMode").attr("data-original-title",typeof(projectInfoDetail.fModeRemark)=="undefined"?"—":(projectInfoDetail.fModeRemark==0?"—":"点击查看"+projectInfoDetail.fModeRemark+"列表"));
						$("#financeMode").tooltip();//提示
						if($("#financeMode").text()=="独投"){
							$("#financeMode").removeAttr("data-original-title");
							$("#financeMode").addClass("hide");
						}else{
							$("#financeMode").removeClass("hide");
						}
					}else {
							layer.msg(data2.result.message);
					}
					
				});
			}
		})
		
		
		
		
		function getUpdateData(){
			var id=$("#pid").val();
			var pname=$("#project_name_edit").val().trim();
			var industry_own=$("#industry_own_sel").attr('data-flag');
			var faFlag=$('input[name="projectSource"]').attr('data-flag');
			var faName="";
			if(faFlag=='projectSource:1'){
				faName=$("#faNameEdit").val();
			}else{
				faName="";
			}
			//处理投资形式
			var investForm= $(".radio_checked input").attr("value");
			var arr=[];
			if(investForm=="financeMode:1"||investForm=="financeMode:2"){
				var jointDeliverys= $(".block_inputs");
				for(var i=0;i<jointDeliverys.length;i++){
					var obj={"deliveryName":"",
							 "deliveryAmount":"",
							 "deliveryCurrency":"",
							 "deliveryShareRatio":"",
						    };
					var jointDelivery=jointDeliverys[i];
					    var isUpdate=jointDelivery.childNodes[0].childNodes[0].getAttribute("data-id");
					    if(isUpdate!=null){
					    	obj.id=isUpdate;
					    }
				        obj.deliveryName=jointDelivery.childNodes[0].childNodes[0].value;
				        obj.deliveryAmount=jointDelivery.childNodes[1].childNodes[0].value;
				        obj.deliveryCurrency=jointDelivery.childNodes[2].childNodes[0].firstElementChild.getAttribute("m-val");
				        obj.deliveryShareRatio=jointDelivery.childNodes[3].childNodes[0].value;
				        arr[i]=obj;
				}
			}
			var formatData={"id":id,
					       "projectName":pname,
					       "industryOwn":industry_own,
		  	               "faFlag":faFlag,
		  	               "faName":faName,
		  	               "financeMode":investForm,
                           "jointDeliveryList":arr,
                           "isDelete":isDelete
			};
			return formatData;
		}
});
$("select[name='projectSource']").change(function() {
	// 0 y; 1 n
	var $selectedvalue = $(this).find("option:selected").attr("value");
	radio_faFlag($selectedvalue);
});

//是否为来源于中介
function radio_faFlag(isContactsV){
	console.log(isContactsV);
	var phone = $("input[name='faName']");
	if (isContactsV == 'projectSource:1') {
		$("input[name='faName']").show();
	} else{
		$("input[name='faName']").hide();
		$("#faNameEdit-error").remove();
	} 
}
function jointDeliveryList(list){
	$("#jointDelivery").show().children().remove(); 
	var html="<tr><th>投资人/投资机构</th><th>投资金额（万元）</th><th>币种</th><th>占股比例（%）</th></tr>";
	var temp=$("#jointDelivery");
	temp.append(html);
	if(list.length<=0||list==undefined){
		temp.append("<tr class=\"no-records-found\"><td colspan=\"4\" style=\" text-align:center !important;color:#bbb;border:0;line-height:32px !important\" class=\"noinfo no_info01\"><label class=\"no_info_icon_xhhl\">没有找到匹配的记录</label></td></tr>");
		return;
	}
	for(var i=0;i<list.length;i++){
		if(list[i].deliveryCurrency=="currency:1"){
			list[i].deliveryCurrency='美元'
		}else if(list[i].deliveryCurrency=="currency:0"){
			list[i].deliveryCurrency='人民币'
		}
	   var html="<tr><td>"+list[i].deliveryName+"</td><td>"+list[i].deliveryAmount+"</td><td>"+list[i].deliveryCurrency+"</td><td>"+list[i].deliveryShareRatio+"</td></tr>";
	   temp.append(html);
	}	
}
var name_opt = new Array()
function jointDeliveryEdit(list){
	$(".inputsForm").children(".block_inputs").remove(); 
	for(var i=0;i<list.length;i++){
		var inputsRow='<div class="block_inputs institue_content">'
	        +'<span class="input_box"><input placeholder="机构名称" data-id="'+list[i].id+'" value="'+list[i].deliveryName+'" class="name inves_input input_stock_left" name="deliveryName'+i+'" required maxLength="50" data-msg-required="<font color=red>*</font>必填，且不超过50字" data-rule-delivery="true" data-msg-delivery="<font color=red>*</font>不能为空"/></span>'
	        +'<span class="input_box"><input placeholder="投资金额（万元）" value="'+list[i].deliveryAmount+'" name="deliveryAmount'+i+'" class="inves_input" required data-rule-amount="true" data-msg-required="<font color=red>*</font>支持0-1000000的四位小数" data-msg-amount="<font color=red>*</font><i></i>支持0-1000000的四位小数"/></span>'
	        +'<span class="input_box"><div id="dropdown"> <input class="input_select" onclick="dropdown_select(this,event)" type="text" value="人民币" m-val="currency:0" id="industry_own_sel" name="industryOwn" required data-msg-required="<font color=red>*</font><i></i>行业归属不能为空" aria-required="true"/> <ul class="base_select_ul"><li value="currency:0">人民币</li><li value="currency:1">美元</li></ul></div></span>'
	        +'<span class="input_box"><input placeholder="占股比例（%）"  value="'+list[i].deliveryShareRatio+'" name="deliveryShareRatio'+i+'" class="inves_input inves_stock" required data-rule-share="true" data-msg-required="<font color=red>*</font>0-100间的两位小数" data-msg-share="<font color=red>*</font><i></i>0-100间的两位小数"/></span>'
	          +'<em class="inves_delete"></em>'
	          +'</div>';
		$(".inputsForm").append(inputsRow);
		 name_opt.push(list[i].deliveryCurrency);
	};
	
	$('.block_inputs').each(function(){
		var index = $(this).index()
		var _this = $(this);
		if(name_opt[index]==='currency:0' || name_opt[index]==='人民币'){
			
			_this.find('#industry_own_sel').attr("value","人民币").attr("m-val","currency:0");
		}else{
			
			_this.find('#industry_own_sel').attr("value","美元").attr("m-val","currency:1");
		}
	});
}
buildShareResult("4","5812");
function buildShareResult(reportType,relateId){
	sendGetRequest(platformUrl.getRelateTitleResults +reportType+"/"+relateId+"/"+projectInfo.id, null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK')
				{
					var entityList = data.entityList;
					if(entityList && entityList.length >0)
					{
						$.each(entityList,function(){
							var title = this;
							$("input[data-title-id='"+title.id+"']").attr({"data-type":title.type});	
							
							if(null!=title.resultList&&title.resultList.length>0){
								var _val =title.resultList[0].contentDescribe1;	
								//这个是公共的 所以需要判断ID
								if ((title.id =="3004"||title.id =="3010"||title.id =="3011"||title.id =="3012")&&_val) {
									if(_val.indexOf('.')>-1){
										var num=_val.split('.');
										if(num[0].length>9){
											_val=_val;
										}else{
											_val=Number(_val).toFixed(4)
										}
									}
									_val = _parsefloat(_val);
									var I_val=_val;
								}else{
									var I_val=_val;
								}
								if(_val==undefined){
									_val="—"
								}else{
									if(title.id=="1916"||title.id=="1943"||title.id=="3004"||title.id=="3012"){
										var Tval= change_number(_val);
										_val = _parsefloat(Tval[0]);
										$(".new_color_black[data-title-id='"+title.id+"']").next().text(Tval[1]+"元")
									}
								}
								
								$(".new_color_black[data-title-id='"+title.id+"']").text(_val);
								$("input[data-title-id='"+title.id+"']").val(title.resultList[0].contentDescribe1==undefined ?"":I_val).attr({"data-result-id":title.resultList[0].id});	
								if(title.id=="3010"){
									if(_val==undefined||_val=="—"){
										_val=0;
									}
									base_chart("invest_chart"," ","#fd88b8",['#c4e4ff','#73bfff'],[_val,100-_val]);
								}
							}else{
								$(".new_color_black[data-title-id='"+title.id+"']").text("—");
								if(title.id=="3010"){
									base_chart("invest_chart"," ","#fd88b8",['#c4e4ff','#73bfff'],["0","100"]);
								}}
						});
					}
				}
			})
}
//保存后刷新
function updataReport(projectInfoList){
	if(projectInfoList && projectInfoList.length>0){
    	$.each(projectInfoList,function(i,o){
	    	if(o.nodeName=='本轮融资轮次'){
	    		$("label[data-title-id='"+o.titleId+"']").attr({"value":o.value,"data-result-id":o.resultId});
	    	}else if(o.nodeName=='融资计划'){
	    		var entityList=o.childList;
	    		if(entityList && entityList.length>0){
	    			$.each(entityList,function(){
						var title = this;
						$("input[data-title-id='"+title.titleId+"']").attr("data-type",title.type);	
						if(null!=title.value&& undefined!=title.value){
							var _val = title.value;
							_val=_parsefloat(_val);
							var I_val=_val
							if(_val==undefined){
								_val="—"
							}else{
								if(title.titleId=="1916"||title.titleId=="1943"||title.titleId=="3004"||title.titleId=="3012"){
									var Tval= change_number(_val);
									_val = _parsefloat(Tval[0]);
									$(".new_color_black[data-title-id='"+title.titleId+"']").next().text(Tval[1]+"元")
								}
							}
							
							$(".new_color_black[data-title-id='"+title.titleId+"']").text(_val);
							$("input[data-title-id='"+title.titleId+"']").val(title.value==undefined ?"":I_val).attr({"data-result-id":title.resultId});	
							if(title.titleId=="1917"){
								console.log(_val);
								if(_val==undefined||_val=="—"){_val=0;}
								base_chart("finance_chart"," ","#fd88b8",['#ffbad7','#fff3f8'],[_val,100-_val]);
							}
						}else{
							$(".new_color_black[data-title-id='"+title.titleId+"']").text("—")
							if(title.titleId=="1917"){
								base_chart("finance_chart"," ","#fd88b8",['#ffbad7','#fff3f8'],["0","100"]);
							}
							
						}
					});
	    		}
	    	}else if(o.nodeName=='实际投资'){
	    		var entityList=o.childList;
	    		if(entityList && entityList.length>0){
	    			$.each(entityList,function(){
	    				var title = this;
						$("input[data-title-id='"+title.titleId+"']").attr({"data-type":title.type});	
						if(null!=title.value&& undefined!=title.value){
							var _val =title.value;	
							//这个是公共的 所以需要判断ID
							if ((title.titleId =="3004"||title.titleId =="3010"||title.titleId =="3011"||title.titleId =="3012")&&_val) {
								if(_val.indexOf('.')>-1){
									var num=_val.split('.');
									if(num[0].length>9){
										_val=_val;
									}else{
										_val=Number(_val).toFixed(4)
									}
								}
								_val = _parsefloat(_val);
								var I_val=_val;
							}else{
								var I_val=_val;
							}
							if(_val==undefined){
								_val="—"
							}else{
								if(title.titleId=="1916"||title.titleId=="1943"||title.titleId=="3004"||title.titleId=="3012"){
									var Tval= change_number(_val);
									_val = _parsefloat(Tval[0]);
									$(".new_color_black[data-title-id='"+title.id+"']").next().text(Tval[1]+"元")
								}
							}
							
							$(".new_color_black[data-title-id='"+title.titleId+"']").text(_val);
							$("input[data-title-id='"+title.titleId+"']").val(title.value==undefined ?"":I_val).attr({"data-result-id":title.resultId});	
							if(title.titleId=="3010"){
								if(_val==undefined||_val=="—"){
									_val=0;
								}
								base_chart("invest_chart"," ","#fd88b8",['#c4e4ff','#73bfff'],[_val,100-_val]);
							}
						}else{
							$(".new_color_black[data-title-id='"+title.titleId+"']").text("—");
							if(title.titleId=="3010"){
								base_chart("invest_chart"," ","#fd88b8",['#c4e4ff','#73bfff'],["0","100"]);
							}}
					});
	    		}
	    		
	    		
	    	}			    	
	    })
    }
}
function updateReportMoney(){
	var projectInfoListNew=[];
	sendGetRequest(Constants.sopEndpointURL+"/galaxy/infoProject/getTitleRelationResults/4/"+projectInfo.id, null, function(data){
		if(data.result.status=='OK'){
			projectInfoListNew=data.userData.report[0].childList;
			updataReport(projectInfoListNew);
		}
	})
}
//给input赋予tochange属性
$("input[data-title-id]").on("input",function(){
	$(this).attr("tochange",true);
})
$("input[data-title-id=\"1816\"]").attr("tochange",true);
//div模拟select下拉框

function dropdown_select(data,event){
	var _this = $(data);
	var ul = _this.closest('#dropdown').find('ul');
	$('#dropdown ul').slideUp('fast');
	$('#dropdown ul').hide('fast');
	if(ul.css("display")=="none"){
		_this.addClass('up');
		ul.slideDown("fast");
	}else{ 
		ul.slideUp("fast");
		_this.removeClass('up');
	} 
	event.stopPropagation(); 
	$("#dropdown ul li").click(function(){
		event.stopPropagation(); 
		var target = $(this).closest('#dropdown').find('input');
		target.removeClass('up')
		var txt = $(this).text(); 
		target.val(txt).attr("m-val",$(this).attr("value"));
		$("#dropdown ul").hide(); 
	});
	$(document).on("click", function(){
		if(ul.css("display")!="none"){
			ul.slideUp("fast");
			_this.removeClass('up');
		}
		
    });
}

//改变保存按钮top值
$('.input_FA ,#project_name_edit').blur(function(){
	if($('.error').is(":visible")){
		$('.basic_message .basic_mes_button').css('margin-top','0')
	}
})
$('[data-on="save"]').click(function(){
	if($('.error').is(":visible")){
		$('.basic_message .basic_mes_button').css('margin-top','0')
	}
})
$('[data-on="data-open"]').click(function(){
	$('.basic_message .basic_mes_button').css('margin-top','63px')
})

/**
 * chart案例开始
 */
function base_chart(data_id,name,border_color,pice_color,data){
	var option = {
		animation:false,
	    series: [{
	        name: name,
	        type: 'pie',
	        radius: '90%',
	        center: ['50%', '50%'],
	        clockwise: false,
	        legendHoverLink:false,
	        hoverAnimation:false,
	        silent:true,
	        startAngle:60,
	        hoverOffset:0,
	        selectedOffset:3,
	        minAngle:0,
	        
	        data: [{
	            value: data[1],
	        }, {
	            value: data[0],
	            selected :true
	        }],
	        label: {
	            normal: {
	                textStyle: {
	                    color: '#999',
	                    fontSize: 14,
	                }
	            }
	        },
	        labelLine: {
	            normal: {
	                show: false
	            }
	        },
	        itemStyle: {
	            normal: {
	                borderWidth: 0,
	                borderColor: border_color,
	            },
	            emphasis : {
	                borderWidth: 2,
	                borderColor: border_color,
	            }
	        }
	    }],
	    color: [
	        pice_color[0],
	        pice_color[1]
	    ],
	    backgroundColor: 'none'
	};
	if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") 
		  { 
			option.tooltip.backgroundColor="#fff";
		  }
	var sdata_id = echarts.init(document.getElementById(data_id));
	sdata_id.setOption(option, true);
}

/**
 * chart案例结束
 */