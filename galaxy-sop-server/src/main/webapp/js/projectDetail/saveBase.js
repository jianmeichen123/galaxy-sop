function saveBaseInfo(dom,val1,val2,val3){
	var infoModeList = new Array();
	var fields = $("#"+dom).find("input[data-title-id],select[data-title-id]");
	var data = {
			projectId : projectInfo.id
		};
	$.each(fields,function(){
		var field = $(this);
		var type = field.data('type');
		var _tochange =field.attr("tochange");
		var sele = field.get(0).tagName;
		var _resultId = field.attr("data-result-id");
		/*if(sele=="SELECT"){
			var _resultId = field.attr("resultId");
		}else{
			var _resultId = field.attr("resultId");
		}*/
		if(_tochange==undefined){
			_tochange=false;
		}
		if(_resultId==undefined){
			_resultId=null;
		}
		if(_tochange==undefined){
			_tochange=false;
		}
		var infoMode = {
			titleId	: field.data('titleId'),
			tochange:_tochange,
			resultId:_resultId,
			type : type
		};
		if(field.data('titleId')=="1118"&&type=="23"){  
				var judgment = $("input[name=projectSource]").attr("m-val");
				if(judgment!='2257'&&judgment!='2262'){ 
					var judgName = $(".man_info .name").text();
					var val = $("select[data-title-id=1118]").find("option:contains("+judgName+")").attr("value");
					 if(val!=undefined){
						 var infoMode = {
								titleId	: field.data('titleId'),
								tochange:true,
								resultId:"",
								type : type,
								value:val
							};						 
					 }else{
						 val = $("select[data-title-id=1118]").find("option").last().attr("value");
						 var infoMode = {
								titleId	: field.data('titleId'),
								tochange:true,
								resultId:resultId13,
								type : type,
								value:val,
								remark1:judgName
								
							};
					 }
					 infoModeList.push(infoMode); 
					 data.infoModeList = infoModeList;
					 return;
				}else if(judgment=='2257'){
					data.deletedResultTids=['1118'];
					return;
				}else{ 
					var values =[] ; 
					var doms = $(".selectcheck li.selected span");
					$.each(doms,function(){ 
						values.push($(this).attr('data-value'))
					})  
					var remark = $('.selectcheck .addpro-input').val();
					var other = $('.selectcheck .addpro-input').attr("ovalue");  
					for(i=0;i<values.length;i++){ 
						var resultId13=$("select#selectRadio").find("option[value="+values[i]+"]").attr("data-result-id")
						if(resultId13==undefined){
							resultId13=null
						}
						var infoMode = {
							titleId	: field.data('titleId'),
							tochange:true,
							resultId:resultId13,
							type : type
						};
						var that = values[i]; 
						infoMode.value=that;  
						if(other==that&&remark!=''&&remark!=null){  
							infoMode.remark1=remark;
						}
						infoModeList.push(infoMode); 
					}  
					data.infoModeList = infoModeList;
					return;
				}

			}else if(field.hasClass("inputSouce")&&type==1){  
				var infoMode = {
						titleId	: field.data('titleId'),
						tochange:true,
						resultId:_resultId,
						type : type
					};	 
				infoMode.remark1=field.val();
			}else if(type==14 )
		{
			infoMode.value = field.attr('m-val');
		}else if(type==19 || (!field.hasClass("inputSouce")&&type==1)){
			infoMode.remark1 = field.val();
		}	
		if (infoMode != null&&type!="13") {
	        infoModeList.push(infoMode);
	    } 
		data.infoModeList = infoModeList;
	}); 
	sendPostRequestByJsonObjNoCache(
			platformUrl.saveOrUpdateInfo , 
			data,
			true,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					layer.msg('保存成功');
					updateReportMoneyBasic();	
					if(dom=="basicForm"&&val1=="finance"){	
						updateReportMoney(); 
					}
//					弹窗关闭
					var close="basic"
					$('.'+close+'_current').hide();//basic_current
					$('.'+close+'_on').hide();
					$('.'+close+'_center').show();
					$('.bj_hui_on').hide();
					$('.tip-yellowsimple').hide();
					$("body").css('overflow-y','auto');
					
					
					if(dom=='company-info-form'){
						$("#projectCompany").text(val1);
						$('#companyLegal').text(val3);
						$('#formationDate').text(val2);
					}
				} else {
					layer.msg("操作失败!");
				}
		});
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

function updateReportMoneyBasic(){
	var projectInfoListNew=[];
	sendGetRequest(Constants.sopEndpointURL+"/galaxy/infoProject/getTitleRelationResults/4/"+projectInfo.id, null, function(data){
		if(data.result.status=='OK'){
			projectInfoListNew=data.userData.report[0].childList;
			if(projectInfoListNew && projectInfoListNew.length>0){
		    	$.each(projectInfoListNew,function(i,o){
			    	if(o.nodeName=='本轮融资轮次'){
			    		$("label[data-title-id='"+o.titleId+"']").attr({"value":o.value,"data-result-id":o.resultId});
			    	}		    	
			    })
			    }
			projectInfoDetail=data.userData.pro;   
			reportResult=data.userData.report; 
			$("#project_name_t").text(projectInfoDetail.projectName);
			$("#project_name_t").attr("title",projectInfoDetail.projectName);
			$("#project_name_t").attr("pid",projectInfoDetail.id);
			$("#industryOwnDs").text(projectInfoDetail.industryOwnDs);
			$("#financeStatusDs").text(projectInfoDetail.financeStatusDs==null?"-":projectInfoDetail.financeStatusDs);
			$("#projectType").text(projectInfoDetail.type); 
			$("#financeStatusDs").text(projectInfoDetail.financeStatusDs==null?"-":projectInfoDetail.financeStatusDs);
			$("#faName").text(projectInfoDetail.faFlagStr).attr("data",projectInfoDetail.faFlag).removeAttr('data-original-title');
			if(projectInfoDetail.projectName.length>20){
				$('.project_name_t').css('font-size','24px')
			}else{
				$('.project_name_t').css('font-size','28px')
			}
			//FA类型展示
			showFaname()
		}
	})
}
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
						if(title.resultId){
							$("input[data-title-id='"+title.titleId+"']").attr("data-result-id",title.resultId);	
						}
						if(null!=title.value&& undefined!=title.value&&""!=title.value){
							var _val = title.value;
							_val=_parsefloat(_val);
							var I_val=_val
							if(_val==undefined){
								_val="暂无数据"
							}else{
								if(title.titleId=="1916"||title.titleId=="1943"||title.titleId=="3004"||title.titleId=="3012"){
									var Tval= change_number(_val);
									_val = _parsefloat(Tval[0]);
									$(".new_color_black[data-title-id='"+title.titleId+"']").next().text(Tval[1]+"元")
								}
							}
							
							$(".new_color_black[data-title-id='"+title.titleId+"']").text(_val).removeClass("font-normal");
							$("input[data-title-id='"+title.titleId+"']").val(title.value==undefined ?"":I_val).attr({"data-result-id":title.resultId});	
							if(title.titleId=="1917"){
								if(_val==undefined||_val=="暂无数据"){_val=0;}
								if(_val!=0){
									base_chart("finance_chart"," ","#fd88b8",['#ffbad7','#fff3f8'],[_val,100-_val]);
								}else if(_val==0){
									$("#finance_chart").html("");
								}
							}
							$(".new_color_black[data-title-id='"+title.titleId+"']").next("span").show()
						}else{
							$(".new_color_black[data-title-id='"+title.titleId+"']").text("暂无数据").addClass("font-normal");
							$(".new_color_black[data-title-id='"+title.titleId+"']").next("span").hide();
							$("input[data-title-id='"+title.titleId+"']").val("");
							if(title.titleId=="1917"){
								$("#finance_chart").html("");
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
						if(title.resultId){
							$("input[data-title-id='"+title.titleId+"']").attr("data-result-id",title.resultId);	
						}
						if(null!=title.value&& undefined!=title.value&&""!=title.value){
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
								_val="暂无数据"
							}else{
								if(title.titleId=="1916"||title.titleId=="1943"||title.titleId=="3004"||title.titleId=="3012"){
									var Tval= change_number(_val);
									_val = _parsefloat(Tval[0]);
									$(".new_color_black[data-title-id='"+title.titleId+"']").next().text(Tval[1]+"元")
								}
							}
							
							$(".new_color_black[data-title-id='"+title.titleId+"']").text(_val).removeClass("font-normal");
							$("input[data-title-id='"+title.titleId+"']").val(title.value==undefined ?"":I_val).attr({"data-result-id":title.resultId});	
							if(title.titleId=="3010"){
								if(_val==undefined||_val=="暂无数据"){
									_val=0;
								}
								if(_val!=0){
									base_chart("invest_chart"," ","#fd88b8",['#c4e4ff','#73bfff'],[_val,100-_val]);
								}else{
									$("#invest_chart").html("");
								}
								
							}
							$(".new_color_black[data-title-id='"+title.titleId+"']").next("span").show()
						}else{
							$(".new_color_black[data-title-id='"+title.titleId+"']").text("暂无数据").addClass("font-normal");;
							$(".new_color_black[data-title-id='"+title.titleId+"']").next("span").hide();
							$("input[data-title-id='"+title.titleId+"']").val("");	
							if(title.titleId=="3010"){
								$("#invest_chart").html("");
						}}
					});
	    		}
	    		
	    		
	    	}			    	
	    })
    }
}



 