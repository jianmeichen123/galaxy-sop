function saveBaseInfo(dom){
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
		if(type==14 )
		{
			infoMode.value = field.val();
		}else if(type==19 || type==1){
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
					buildShareResult("4","5812");   //法人信息保存刷新
				} else {
					layer.msg("操作失败!");
				}
		});
	
}