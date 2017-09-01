//通用保存
// 核心创始团队 表格删除行使用
var deletedRowIds = new Array();
// 股权结构合理性 表格删除行使用
var deletedRowIdsGq = new Array();

$('div').delegate(".h_save_btn","click",function(event){
	var btn = this;
	var save_this = $(btn).parents('.radius');
	event.stopPropagation();
    var sec = $(this).closest('form');
    var id_code = $(this).attr('attr-save');
    var parent_code;
    if(id_code && id_code.indexOf("_") > 0){
		var arr = new Array();
		arr = id_code.split("_");
		parent_code = arr[0];
	}
    var dt_type_3 = $("#b_" + id_code).find("dt[data-type='3'],dt[data-type='13']");
	var fields = sec.find("input[type='text'][data-title-id],input:checked,textarea,radio,select");
	var fields_value1=sec.find("li[class='check_label active'],li.active");
	var data = {
		projectId : projectInfo.id
	};
	if(!$("#b_"+id_code).validate().form())
	{
		return;
	}
	if($(this).closest('.radius').attr("data-section-id") ==1302){
		//表格
		var titleId = sec.find("table.editable").attr("data-title-id");
        var json = {"projectId":projectInfo.id,"titleId":titleId};
		var dataList = new Array();
		$.each(sec.find("table.editable"),function(){
			$.each($(this).find('tr:gt(0)'),function(){
				var row = $(this).data("person");
				if(row.id=="")
				{
					row.id=null;
				}
				row.projectId=projectInfo.id;
				dataList.push(row);
			});
		});
        json["dataList"]=dataList;
        if(dataList.length>10){
            alert("最多只能添加10条记录!")
            return false;
        }
      //团队表格显示隐藏
		$.each($('table.editable'),function(){
			var table_id = $(this).attr('data-title-id');
			var noedi_table = $('table[data-title-id='+table_id+']');
			if($(this).find('tr:gt(0)').length<=0){
				if(noedi_table.parents('dl').find('dd').length<= 2){
					$('table[data-title-id='+table_id+']').parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
				}
				noedi_table.hide();
			}
			else{
				noedi_table.show();
				noedi_table.parents('dl').find('.no_enter').remove();
				
			}
		})
        sendPostRequestByJsonObj(
        platformUrl.saveTeamMember,
        json,
        function(data) {
            var result = data.result.status;
            if (result == 'OK') {
            	if(parent_code){
            		updateInforTime(projectInfo.id,parent_code);
            	}
                layer.msg('保存成功');
            	$(".h#a_"+id_code).css("background","#fff");
                var parent = $(sec).parent();
                var id = parent.data('sectionId');
                $(btn).next().click();
                refreshSection(id);
                toggle_btn($('.anchor_btn span'),0,save_this);
            } else {

            }
	    })
	    return;
	}
	//团队表格显示隐藏
	$.each($('table.editable'),function(){
		var table_id = $(this).attr('data-title-id');
		var noedi_table = $('table[data-title-id='+table_id+']')
		if($(this).find('tr').length<=1){
			if(noedi_table.parents('dl').find('dd').length<= 2){
				$('table[data-title-id='+table_id+']').parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
			}
			noedi_table.hide();
		}
		else{
			noedi_table.show();
			noedi_table.parents('dl').find('.no_enter').remove();
			
		}
	})
	
	

	data.deletedResultTids = deletedResultTids;
	
	var fieldsValidate = true;
	var infoModeList = new Array();
	var infoModeFixedList = new Array();
	var infoModeInputs=new Array();
	//多选和多选加备注特殊处理
	$.each(fields_value1, function() {			
		var field = $(this);
		var _tochange =field.parents("dd").prev().attr("tochange");
		if(_tochange==undefined){
			_tochange=false;
		}			
		if(_tochange == true||_tochange == "true"){
			var _resultId = field.attr("resultId");
			if(_resultId==undefined  || _resultId=="undefined"){
				_resultId=null
			}
			var infoMode = {
					titleId : field.data('titleId'),
					type : field.data('type'),
					tochange:_tochange,
					resultId:_resultId,
					value : field.attr('value')
				};
			var type = field.data('type');
			if(type==13){
				var field_v = field.data('id');
                 var last_id = field.closest('ul').find('li.check_label:last').attr('data-id');
                 var dt = field.closest('dt[data-type="13"]');
                 if ( field_v == last_id)
                 {
                 	//其他
                     infoMode.remark1 = field.closest('.h_edit_txt').find('input:last').val();
                 }
                 else
                 {
                     infoMode.remark1 = '' ;
                 }
			}
             
			infoModeList.push(infoMode);
		}
		
	});
	$.each(fields,function(){
		var field = $(this);
		var type = field.data('type') || field.closest('.h_edit_txt').find(':first-child').data('type');;
		var _tochange =field.parents("dl.h_edit_txt").find("dt").attr("tochange");
		var sele = field.parent().get(0).tagName;
		/*if(sele=="SELECT"){
			if(!_tochange || _tochange==undefined){
				_tochange =field.parents("dl").find("*[tochange]").attr("tochange");
			}
			var _resultId = field.parent().attr("resultId");
		}else{
			var _resultId = field.attr("resultId");
		}*/
		if(sele=="SELECT"){
			var _resultId = field.parent().attr("resultId");
		}else{
			var _resultId = field.attr("resultId");
		}
		if(_tochange==undefined){
			_tochange=false;
		}
//		var infoMode = {
//			titleId	: field.data('title-id') || field.closest('.h_edit_txt').find(':first-child').data('title-id'),
//			tochange:_tochange,
//			resultId:_resultId,
//			type : type
//		};
		if(_resultId==undefined){
			_resultId=null;
		}
		if(_tochange==undefined){
			_tochange=false;
		}
		var infoMode = {
			titleId	: field.data('titleId') || field.closest('.h_edit_txt').find(':first-child').data('title-id'),
			tochange:_tochange,
			resultId:_resultId,
			type : type
		};
		var infoModeFixed = {
				titleId	: field.data('titleId'),
				type : type,
				rowNo:"",
				colNo:""
			};
		if(type==2  || type==4 || type==14 )
		{
			infoMode.value = field.val();
		}	
		else if (type==3)
		{
			infoMode.value = field.data('id');
		}
		else if(type==1 || type==19 )
		{	
			infoMode.remark1 = field.val();
			if(reportType=="3"){   //特殊处理决策里面的投资金额
				infoMode.reportType=3;
			}
		}
		else if(type==5){  
			if($(field).attr("type")=="radio"){
				var val=field.val();
				infoMode.value = val;
			}else{
				var str=field.val();
				var str=str.replace(/\n|\r\n/g,"<br>")
				var str=str.replace(/\s/g,"&nbsp;");
				infoMode.remark1 = str;
			}	
		}
		else if( type==20 )
		{	
			infoMode.remark1 = field.val();
			//infoMode.remark2 = field.parent().parent().children($('select option:selected')).text();
			var id  = infoMode.titleId
			var options=$("#"+id+"_select option:selected")
			var name = options.text();
			var value =options.val();
			infoMode.remark2 = name+"p"+value;
		}
		else if(type==8)
		{
			var str=field.val();
			var str=str.replace(/\n|\r\n/g,"<br>")
			var str=str.replace(/\s/g,"&nbsp;");
			infoMode.remark1 = str;
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
		}
		else if(type==12)
        {
            if (field.is('textarea') || field.is('input[type="text"]')){

                //infoMode.remark1 = field.val()

            }else{
                infoMode.value = field.val();
                var field_v = field.val();
                var last_id = field.closest('ul').find('input[type="radio"]:last').attr('data-id');
                var dt = field.closest('dt[data-type="12"]');

                if ( field_v == last_id)
                {
                    infoMode.remark1 = field.closest('.h_edit_txt').find('input:last').val();
                    /*if(infoMode.remark1 == null || infoMode.remark1 == undefined || $.trim(infoMode.remark1) == '') {
                        layer.msg('不能为空!');
                        field.closest('.h_edit_txt').find('input:last').focus();
                        fieldsValidate = false;
                    }*/
                }
                else
                {
                    infoMode.remark1 = '' ;
                }
            }
        }
		else if(type == 15)
		{
            var _has = false;
            var str=field.val();
			var str=str.replace(/\n|\r\n/g,"<br>");
			var str=str.replace(/\s/g,"&nbsp;");
            $.each(infoModeList,function(i,n){
                if(infoModeList[i].type == 15 && infoModeList[i].titleId == infoMode.titleId) {
                    _has = true;
                    if(!infoModeList[i].hasOwnProperty('remark1')){
                        infoModeList[i].remark1 = str;
                    }else{
                        infoModeList[i].remark2 = str;
                    }
                }
            });

            if( !_has ) {
                infoMode.remark1 = str;
            }else {
                infoMode = null;
            }
        }
		else if(type == 16)
		{
            var div =  $(".inputs_block").closest(".h_edit_txt");
            var dds=div.find('dd');
            var inputsValueList=[];
            for(var i=0;i<dds.length;i++){
            	_resultId = dds.eq(i).children("input").attr("resultId");
            	if(_resultId && _resultId != undefined){
            		infoMode.resultId = _resultId;
            	}
            	var field=dds.eq(i).children("input").val();
            	inputsValueList.push(field);
            }
            var content='该项目是一个通过或基于<sitg>'+inputsValueList[0]+'</sitg>的<sitg>'+inputsValueList[1]+'</sitg>的<sitg>'+inputsValueList[2]+'</sitg>，连接<sitg>'+inputsValueList[3]+'</sitg>和<sitg>'+inputsValueList[4]+'</sitg>，为<sitg>'+inputsValueList[5]+'</sitg>提供<sitg>'+inputsValueList[6]+'</sitg>的产品或服务，满足了<sitg>'+inputsValueList[7]+'</sitg>的刚需或解决了<sitg>'+inputsValueList[8]+'</sitg>。'
            infoMode.remark1 = content;
            infoModeInputs.push(infoMode);
        }

		if (infoMode != null) {
	        infoModeList.push(infoMode);
	    }
	});
	if ( fieldsValidate == false )
    {
        return false;
    }
	//表格
	var infoTableModelList = new Array();
	$.each(sec.find("table.editable"),function(){
		$.each($(this).find('tr:gt(0)'),function(){
			var row = $(this).data();
			if(row.id=="")
			{
				row.id=null;
			}
			infoTableModelList.push($(this).data());
		});
	});

	data.infoTableModelList = infoTableModelList;
	data.infoModeList = infoModeList;
	data.infoFixedTableList=infoModeFixedList;
	data.infoModeInputs=infoModeInputs;
	data.deletedRowIds = deletedRowIds;
	var h_cancel_btn_code = $(btn).next().attr('attr-session');

    //股权合理性 h_cancel_btn_code 为块titleId
    if (h_cancel_btn_code=='1324'){
        data.deletedRowIds = deletedRowIdsGq;
    }
		
    //多选不选择的时候：
    var deletedResultTids = new Array();
    $.each(dt_type_3, function() {
        var _this = $(this);
        var active = _this.parent().find('dd .active');
        if(!(active && active.length > 0)){
            var tid = _this.data('id');
            deletedResultTids.push(tid);
        }
    });
    data.deletedResultTids = deletedResultTids;
	
	
  //上传图片相关
	var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
	var key = keyJSON["b_"+id_code];
	var deleteids = deleteJSON["delete_"+id_code];
	var params = {};
	params.projectId =  projectInfo.id;
	params.fileReidsKey = key;
	params.deleteids = deleteids;

	$("body").showLoading();
	sendPostRequestByJsonObjNoCache(sendFileUrl,params,true,function(dataParam){
		//进行上传
		var result = dataParam.result.status;
		if(result == "OK"){
			sendPostRequestByJsonObjNoCache(
					platformUrl.saveOrUpdateInfo , 
					data,
					true,
					function(data) {
						var result = data.result.status;
						if (result == 'OK') {
							if(parent_code){
			            		updateInforTime(projectInfo.id,parent_code);
			            	}
							layer.msg('保存成功');
							$(".bj_hui_on").hide();
                            if (h_cancel_btn_code=='1324'){
                                deletedRowIdsGq = new Array();
                            }
        					var parent = $(sec).parent();
        					var id = parent.data('sectionId');
        					$(btn).next().click();
        					refreshSection(id);
        					
							//tabInfoChange('3');
							$('#'+id_code).show();
							$('#b_'+id_code).remove();
							$(".bj_hui_on").hide();
							//右侧按钮显示隐藏
							btn_disable(0);
							$(".h#a_"+id_code).css("background","#fff");
							$(".loading-indicator-overlay").remove();
							$(".loading-indicator").remove();
							dtWidth();
							var pid=$('#a_'+id_code).attr("data-section-id");
							//$('#a_'+id_code).find('dd[data-type="3"]').hide();
							//setDate(pid,true);
							picData(projectInfo.id);
							if(id_code=="PNO1_1"){   //投资金额单独刷新
								var val=$("input[type='hidden'].money").val();
								var childrenVal=$("dd[data-title-id=\"3010\"]").text();
								var resultVal=$("dd[data-title-id=\"3012\"]").text();
								if(resultVal=="未填写"){
									if(childrenVal=="未填写"){
										$("dd[data-title-id=\"3012\"]").text("未填写");
										$("dd[data-title-id=\"3012\"]").next("dd").hide();
									}else{
										if(val==""){
											$("dd[data-title-id=\"3012\"]").text("未填写");
											$("dd[data-title-id=\"3012\"]").next("dd").hide();
										}else{
											$("dd[data-title-id=\"3012\"]").text(val);
											$("dd[data-title-id=\"3012\"]").next("dd").show();
										}
									}
								}else{
									if(val==""){
										$("dd[data-title-id=\"3012\"]").text("未填写");
										$("dd[data-title-id=\"3012\"]").next("dd").hide();
									}else{
										$("dd[data-title-id=\"3012\"]").text(val);
										$("dd[data-title-id=\"3012\"]").next("dd").show();
									}
								}
								
							}							
                            $('table').each(function(){
                                resizetable($(this))
                                if($(this).find('tr').length<=1){
                                    $(this).hide();
                                    if($(this).parents('dl').find('dd:gt(0)').length<=0){
                                        $(this).parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
                                    }
                                }
                                else{
                                    $(this).show();
                                }
                            })
                            //隐藏编辑按钮【运营报告分期编辑按钮】
                            if($("table[data-title-id=3022] tr").length == 1){
                            	$("span[attr-id=ONO9_2]").hide();
                            }
                            
                            toggle_btn($('.anchor_btn span'),0,save_this);
						} else {
							layer.msg("操作失败!");
						}
				});
		}else{
			layer.msg("操作失败!");
		}
		
	}); 

	//toggle_btn($('.anchor_btn span'),0,save_this);
}); 
