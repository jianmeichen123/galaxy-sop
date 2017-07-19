//通用保存事件
function save_hologram(_data){
	var save_this = _data.parents('.radius');
	if($('.tip-yellowsimple').length > 0){
		return false;
	}
	var id_code = _data.attr('attr-save');
	event.stopPropagation();
	var sec =_data.closest('form');
	var fields = sec.find("input[type='text'],input:checked,textarea,option:selected");
	var data = {
		projectId : projectInfo.id
	};
	
	var infoModeList = new Array();
	$.each(fields,function(){
		var field = $(this);
		var type = field.data('type');
		var _tochange =field.parents("dd").prev().attr("tochange");
		var _resultId = field.attr("resultId");
		if(_tochange==undefined){
			_tochange=false;
		}
		var infoMode = {
			titleId	: field.data('titleId'),
			tochange:_tochange,
			resultId:_resultId,
			type : type
		};
		if(type==2 || type==3 || type==4|| type==14)
		{
			infoMode.value = field.val()
		}
		else if(type==1)
		{
			infoMode.remark1 = field.val()
		}
		else if(type==9){
			var name=field.attr("name");
			var rowNo=name.split("_")[0].substring("3");
			var input=$("input[name="+name+"]");
			var colNo=field.parent().parent().parent().attr("data-flag");
			var titleid=$("table[data-type]").attr("data-test");
			infoModeFixed.rowNo=rowNo;
			infoModeFixed.colNo=colNo;
			infoModeFixed.titleId=titleid;
			infoModeFixed.value=field.val();
			infoModeFixed.valueId=field.data('valueId');
			infoModeFixedList.push(infoModeFixed);
		}else if(type == 16)
		{
            var div =  $(".inputs_block").closest(".h_edit_txt");
            var dds=div.find('dd');
            var inputsValueList=[];
            for(var i=0;i<dds.length;i++){
            	var field=dds.eq(i).children("input").val();
            	inputsValueList.push(field);
            }
            var content='该项目是一个通过或基于<sitg>'+inputsValueList[0]+'</sitg>的<sitg>'+inputsValueList[1]+'</sitg>的<sitg>'+inputsValueList[2]+'</sitg>，连接<sitg>'+inputsValueList[3]+'</sitg>和<sitg>'+inputsValueList[4]+'</sitg>，为<sitg>'+inputsValueList[5]+'</sitg>提供<sitg>'+inputsValueList[6]+'</sitg>的产品或服务，满足了<sitg>'+inputsValueList[7]+'</sitg>的刚需或解决了<sitg>'+inputsValueList[8]+'</sitg>。'
            infoMode.remark1 = content;
            infoModeInputs.push(infoMode);
        }
		else if(type==8)
		{
			var str=field.val();
			var str=str.replace(/\n|\r\n/g,"<br>")
			var str=str.replace(/\s/g,"&nbsp;");
			infoMode.remark1 = str;
		}else if(type==13)
        {
            infoMode.value = field.data('id');
            var field_v = field.data('id');
            var last_id = field.closest('ul').find('li.check_label:last').attr('data-id');
            var dt = field.closest('dt[data-type="13"]');
            if ( field_v == last_id)
            {
                infoMode.remark1 = field.closest('.h_edit_txt').find('input:last').val();
            }
            else
            {
                infoMode.remark1 = '' ;
            }
       
    }
		infoModeList.push(infoMode);
	});
	data.infoModeList = infoModeList;
	//验证插件调用
	if(!$("#b_"+id_code).validate().form())
	{
		return;
	}
	console.log(data);
	if(beforeSubmit()){
		sendPostRequestByJsonObj(
				platformUrl.saveOrUpdateInfo , 
				data,
				function(data) {
					var result = data.result.status;
					if (result == 'OK') {
						updateInforTime(projectInfo.id,"lawTime");
						layer.msg('保存成功');
						$('#'+id_code).show();
						$('#b_'+id_code).remove();
						$(".bj_hui_on").hide();
						btn_disable(0);
						$(".h#a_"+id_code).css("background","#fff");
						var pid=$('#a_'+id_code).attr("data-section-id");
						setDate(pid,true);
					    toggle_btn($('.anchor_btn span'),0,save_this);
					} else {

					}
			}) 
	}
}