	setInterval(function(){    //定时保存
		
	},1000)
	
	//保存方法
	function auto_save(sec){
		var fields = sec.find("input[type='text'][data-title-id],input:checked,textarea,radio,select[data-title-id],select[name]");
		var fields_value1=sec.find("li[class='check_label active'],li.active");
		var data = {
				projectId : projectInfo.id,
				parentId:sec.data('section-id')
			};
			var infoModeList = new Array();
			var infoModeFixedList = new Array();
			var infoModeInputs=new Array();
			$.each(fields,function(){
				var field = $(this);
				var type = field.data('type') || field.closest('.h_edit_txt').find(':first-child').data('type');
				var _resultId = null;
				var _tochange =field.parents("dl.h_edit_txt").find("dt").attr("tochange") || field.parents(".mb_24").find("dt").attr("tochange");
				var sele = field.parent().get(0).tagName;
				if(sele=="SELECT"){
					_resultId = field.parent().attr("resultId");
				}else{
					_resultId = field.attr("resultId");
				}
				
				if(_tochange==undefined){
					_tochange=false;
				}
				var infoMode = {
					titleId	: field.data('title-id') || field.closest('.h_edit_txt').find(':first-child').data('title-id'),
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
				if(type==2 || type==3 || type==4 || type==14)
				{
					infoMode.value = field.val()
				}
				else if(type==5){  
					if(field.attr("type")=="radio"){
						var val=field.val();
						infoMode.value = val;
					}else{
						var str=field.val();
						var str=str.replace(/\n|\r\n/g,"<br>")
						var str=str.replace(/\s/g,"&nbsp;");
						infoMode.remark1 = str;
					}	
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
				else if(type==1 || type==19)
				{
					infoMode.remark1 = field.val();
				}
				else if(type==8)
				{
					var str=field.val();
					var str=str.replace(/\n|\r\n/g,"<br>")
					var str=str.replace(/\s/g,"&nbsp;");
					infoMode.remark1 = str;
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
				else if( type==20 )
				{	
					infoMode.remark1 = field.val();
					var id  = infoMode.titleId
					var options=$("#"+id+"_select option:selected")
					var name = options.text();
					var value =options.val();
					infoMode.remark2 = name+"p"+value;
				}
				else if( type==21 )
				{	
					infoMode.value = field.val();
					var field_v = field.val();
	                var last_id = field.closest('select').find('option:last').attr('value');
	                if ( field_v == last_id)
                    {
                        infoMode.remark1 = field.closest('.mb_24').find('.input_21').val();
                    }
                    else
                    {
                        infoMode.remark1 = '' ;
                    }
				}
				if (infoMode != null) {
	                infoModeList.push(infoMode);
	            }
			});
			data.infoModeList = infoModeList;
			data.infoFixedTableList=infoModeFixedList;
			data.infoModeInputs=infoModeInputs;
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
							value : field.attr('value') || field.attr('data-value')
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
			console.log(data);
			data.deletedRowIds = deletedRowIds;
			sendPostRequestByJsonObj(
					platformUrl.saveOrUpdateDraftBox , 
					data,
					function(data) {
						var result = data.result.status;
						if (result == 'OK') {
							
						} else {

						}
				}) 
	}
