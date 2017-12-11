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
		if(field.data('titleId')=="1118"&&type=="13"){
				//获取多选带备注数据 
				var values =[] ; 
				var doms = $(".selectcheck li.selected span");
				$.each(doms,function(){ 
					values.push($(this).attr('data-value'))
				})  
				var remark = $('.selectcheck .addpro-input').val();
				var other = $('.selectcheck .addpro-input').attr("ovalue");  
				for(i=0;i<values.length;i++){ 
					var infoMode = {
							titleId	: field.data('titleId'),
							tochange:'true',
							resultId:_resultId,
							type : type
						};
					var that = values[i]; 
					infoMode.value=that;  
					if(other==that&&remark!=''&&remark!=null){  
						infoMode.remark1=remark;
					}
					infoModeList.push(infoMode); 
				}  

			}else if(type==1){ 				
				infoMode.value=field.val();
			}else if(type==14 )
		{
			infoMode.value = field.attr('data-flag');
		}else if(type==19 ){
			infoMode.remark1 = field.val();
		}	
		if (infoMode != null) {
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