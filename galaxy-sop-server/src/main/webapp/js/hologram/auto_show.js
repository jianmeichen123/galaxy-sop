$.fn.showResultsDrafts = function(readonly,flag){
		var sec = $(this);
		var pid = $(this).data('sectionId');
		if(pid == 1302){
		     sendGetRequest(platformUrl.queryMemberList+pid+"/"+projectInfo.id,null,function(data){
		        var result = data.result.status;
                if (result == 'OK')
                {
                   var entityList = data.entityList;
                    $(entityList).each(function(){
                        if($(this)[0]["tableHeader"]){
                            data = $(this)[0]
                        }
                    })
                    
                    buildMemberTableDraft(sec,data);
                }
		     })
		}else{
		sendGetRequest(platformUrl.getTitleResultsDraftBox + pid+'/'+projectInfo.id, null,
				function(data) {
        			var result = data.result.status;
        			if (result == 'OK')
        			{
        				var entityList = data.entityList;
        				if(entityList && entityList.length >0)
        				{
        					var sum=0;
        					console.log(entityList)
        					$.each(entityList,function(){
        						var title = this;
        						if((title.resultMGList && title.resultMGList.length>0) || (title.fixedTableMGList && title.fixedTableMGList.length>0) || (title.dataMGList && title.dataMGList.length>0)){
        							sum++;
        						}
        						if(flag=='result'){
        							buildResultsDraft(sec,title,readonly);
            						buildTableDraft(sec,title);
            						buildfinxedTableDraft(sec,title,readonly);
            						dtWidth();
        						}
        					});
        					console.log(sum)
        					if(sum>0){
        						$('.history_block').show();
        						$('.history_block').closest('.h_edit').addClass('history_block_edit');
        					}else{
        						$('.history_block').hide();
        						$('.history_block').closest('.h_edit').removeClass('history_block_edit');
        					}
        					
        				}
        			}
        		})
		}
};
function buildResultsDraft(sec,title,readonly)
{
	//普通字段
	if(null!=title.resultMGList&&title.resultMGList.length>0)
	{
		if(title.type == 1)
		{
			if(readonly == true)
			{
				$(".field[data-title-id='"+title.id+"']").text(title.resultMGList[0].contentDescribe1==undefined ?"未填写":_parsefloat(title.resultMGList[0].contentDescribe1));
			}
			else
			{	
				var result_id = title.resultMGList[0].id;				
				$("input[data-title-id='"+title.id+"']").val(title.resultMGList[0].contentDescribe1==undefined ?"":_parsefloat(title.resultMGList[0].contentDescribe1)).attr("resultId",result_id);	
				$("input[data-title-id='"+title.id+"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
			}
		}
		if(title.type == 2)
		{
			if(readonly == true)
			{
				$(".field[data-title-id='"+title.id+"']").text(title.resultMGList[0].valueName);
			}
			else
			{	
				var result_id = title.resultMGList[0].id;
				$("input[data-title-id='"+title.id+"'][value='"+title.resultMGList[0].contentChoose+"']").attr('checked','true');
				$("input[data-title-id='"+title.id+"']").attr("resultId",result_id);
				$("input[data-title-id='"+title.id+"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
			}
		}
		else if(title.type == 3)
		{
			$.each(title.resultMGList,function(i,n)
			{
				if(readonly == true)
				{
					$("dd[data-id='"+n.contentChoose+"']").text(n.valueName).show();
				}
				else
				{	
					$("dt[data-id='"+ title.id +"'],dt[data-title-id='"+ title.id +"']").next('dd').find("li[data-id='"+ n.contentChoose +"'],li[data-value='"+ n.contentChoose +"']").addClass('active');
					$("dt[data-id='"+ title.id +"'],dt[data-title-id='"+ title.id +"']").attr("tochange",true);
				}
			});

			if (readonly == true)
			{
				var dds = $("dt[data-type='3'][data-title-id='"+ title.id +"']").siblings().children();
				$.each(dds,function(i,n)
				{
					if ($(this).text() == '未选择')
					{
						$(this).hide();
					}
				});
			}
		}
		else if(title.type == 4)
		{
			for(var i=0;i<title.resultMGList.length;i++){
					$('dt[data-tid="'+title.id+'"]').next('dd').find('select[name="'+title.id+'"]').eq(i).find('option[value="'+title.resultMGList[i].contentChoose+'"]').attr('selected','selected').attr("resultId", title.resultMGList[i].id);
					$('dt[data-tid="'+title.id+'"]').attr("tochange",true);
					if(title.resultMGList[i].contentChoose){
						var options=''
						sendGetRequest(platformUrl.queryValuesByVpid + title.resultMGList[i].contentChoose, null, function(data) {
				    		var result = data.result.status;
				    		if (result == 'OK') {
				    			var entitys = data.entityList;
				    			$.each(entitys,function(i,o){
				    				options+="<option value='"+o.id+ "' data-title-id='"+title.id+"' data-type='"+title.type+"' >"  + o.name + "</option>"
				    			});
				    		}
				    		$('dt[data-tid="'+title.id+'"]').next('dd').find('select[name="'+title.id+'"]').eq(i+1).append(options);
				    	});
					}
			   }
		}
		else if(title.type == 5)
		{
			$.each(title.resultMGList,function(i,n){
				if(readonly == true){
					if(n.contentChoose!=null){
						//单选按钮回显
						$(".type_radio[data-id='"+title.id+"']").text(n.valueName);
						
					}else{
						$(".field[data-title-id='"+n.titleId+"']").html((n.contentDescribe1==undefined || textarea_show(n.contentDescribe1)==0)?"未填写":n.contentDescribe1);
					}
				}else{
					var str=n.contentDescribe1;
					var result_id = n.id;
					if(str){
						str=str.replace(/<br\/>/g,'\n');
						str=str.replace(/<br>/g,'\n');
						str=str.replace(/&nbsp;/g," ");
					}

						
					if(n.contentChoose!=null){
						//单选按钮回显
						$("input[data-title-id='"+n.titleId+"'][value='"+n.contentChoose+"']").attr('checked','true').siblings().removeAttr("checked");
						$("input[data-title-id='"+n.titleId+"']").attr("resultId",result_id);
						$("input[data-title-id='"+n.titleId+"'][value='"+n.contentChoose+"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
					}else{
						$("textarea[data-title-id='"+n.titleId+"']").val((n.contentDescribe1==undefined || textarea_show(n.contentDescribe1)==0 )?"":str).attr("resultId",result_id);
						$("textarea[data-title-id='"+n.titleId+"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
					}
					
				}
			});
		}
		else if(title.type == 12)
		{
			var dd = $("dt[data-type='12'][data-title-id='"+ title.id +"']").siblings('dd').eq(0);
			var n = title.resultMGList[0];
			var result_id = n.id;
			$("input[name='"+title.id+"']").attr("resultId",result_id) ;
			if (n.contentDescribe1)
			{
				if(readonly == true)
				{
					dd.text(n.contentDescribe1);
				}
				else
				{
					
					$("input[data-id='"+title.id+"']").val(n.contentDescribe1==undefined ?"":n.contentDescribe1);
					$("input[data-id='"+title.id+"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
				}
			}
			
			if(n.contentChoose)
			{
				if(readonly == true)
				{
					if ( n.contentDescribe1 == undefined )
					{
						dd.text(n.valueName);
					}
				}
				else
				{
					var result_id = n.id;
					$("dt[data-title-id='"+ title.id +"']").next('dd').find("input[type='radio'][data-id='"+ n.contentChoose +"']").attr('checked','true').attr("resultId",result_id);
					$("input[type='radio'][data-id='"+ n.contentChoose +"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
				}
			}

			if(readonly != true)
			{
				var dt = $("dt[data-type='12'][data-title-id='"+ title.id +"']");
				var dl = dt.parent();
				var radios = dl.find('input[type="radio"]');
				var last_id = dl.find('input[type="radio"]:last').attr('data-id');
				var inputText = dl.find('input[type="text"]:last');
				if ( n.contentChoose == last_id ){
					inputText.attr('disabled',false);
				}else{
					inputText.attr('disabled',true);
					inputText.removeAttr('required');
					inputText.val('');
				}
				$.each(radios , function ( i ,n )
				{
					$(this).unbind('change').bind('change',function(){
						if ( $(this).attr('data-id') == last_id )
						{
							inputText.attr('disabled',false);
							inputText.attr('required' , true);
							/*inputText.bind('blur',function () {
								var inputTextVlue = $.trim(inputText.val())
								if(inputTextVlue=='')
								{
									layer.msg('不能为空!');
									inputText.focus();
								}
							});*/
						}
						else
						{
							inputText.attr('disabled',true);
							inputText.attr('required' , false);
						}
					});
				});
			}
		}
		else if(title.type == 13)
		{
			
			var dt = $("dt[data-type='13'][data-title-id='"+ title.id +"']");
			var dl = dt.parent();
			var inputText = dl.find('input[type="text"]:last');
			if(readonly == true){
				$("dt[data-id='"+ title.id +"']").siblings(".checked_div").find("dd[data-code]").text("");
				$("dt[data-id='"+ title.id +"']").siblings(".checked_div").find("dd[data-code]").hide();
				$.each(title.resultMGList,function(i,n){
					$("dd[data-id='"+n.contentChoose+"']").text(n.valueName).show();
					if(n.contentDescribe1){ 
						$("dd[data-id='"+n.contentChoose+"']").text(n.valueName).hide();
						$("dd[data-id='"+n.contentChoose+"']").text(n.contentDescribe1).show();
					}
					$("dt[data-id='"+ title.id +"']").siblings(".checked_div").find(".field").hide();
				})
				//判断是否选择
				var i=0;
				if($("dt[data-id='"+ title.id +"']").siblings(".checked_div").find("dd[data-code]").is(":visible")){
					i++;
				}
				if(i==0){
					$("dt[data-id='"+ title.id +"']").siblings(".checked_div").find(".field").show();
				}else{
					$("dt[data-id='"+ title.id +"']").siblings(".checked_div").find(".field").hide();
				}
			}else{
				var dt = $("dt[data-type='13'][data-title-id='"+ title.id +"'],dt[data-type='13'][data-tid='"+ title.id +"']");
				var dd = dt.siblings();
				var last_id = dd.find('li.check_label:last').attr('data-id');
				var inputText = dd.find('input[type="text"]:last');
				if ( title.resultMGList[0].contentChoose == last_id ){
					inputText.attr('disabled',false);
					inputText.removeClass('disabled');
				}else{
					inputText.val('');
					inputText.attr('disabled',true);
					inputText.addClass('disabled');
					inputText.removeAttr('required');
				}
				$("dt[data-id='"+ title.id +"'],dt[data-tid='"+ title.id +"']").next('dd').find("li").removeClass('active');
				$.each(title.resultMGList,function(i,n){
					var result_id= n.id;	
					$("dt[data-id='"+ title.id +"'],dt[data-tid='"+ title.id +"']").next('dd').find("li[data-id='"+ n.contentChoose +"']").addClass('active').attr("resultId",result_id);
					$("li[data-id='"+ n.contentChoose +"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
					if(n.contentDescribe1){  
						$("dt[data-id='"+ title.id +"'],dt[data-tid='"+ title.id +"']").next('dd').find("input[type='text']").val(n.contentDescribe1).attr("resultId",result_id);
						inputText.attr('disabled',false);
						inputText.removeClass('disabled');
						inputText.attr('required' , true);
					}
				})
			}
			
		}
		else if(title.type == 15)
		{
			if(readonly == true)
			{
				var dds = $("dd[data-title-id='" + title.id + "']");
				var contentDescribe1=title.resultMGList[0].contentDescribe1;
				var contentDescribe2=title.resultMGList[0].contentDescribe2;
				dds.eq(0).html((contentDescribe1==undefined || textarea_show(contentDescribe1)==0)?"未填写":contentDescribe1);
				dds.eq(1).html((contentDescribe2==undefined || textarea_show(contentDescribe2)==0)?"未填写":contentDescribe2);
			}
			else
			{
				var str=title.resultMGList[0].contentDescribe1;
				if(str){
					str=str.replace(/<br\/>/g,'\n');
					str=str.replace(/<br>/g,'\n');
					str=str.replace(/&nbsp;/g," ");
				}
				var str2=title.resultMGList[0].contentDescribe2;
				if(str2){
					str2=str2.replace(/<br\/>/g,'\n');
					str2=str2.replace(/<br>/g,'\n');
					str2=str2.replace(/&nbsp;/g," ");
				}
				var textareas = $("textarea[data-title-id='" + title.id + "'][data-type='15']");
				var result_id = title.resultMGList[0].id;
				textareas.eq(0).val((title.resultMGList[0].contentDescribe1==undefined || textarea_show(title.resultMGList[0].contentDescribe1)==0)?"":str).attr("resultId",result_id);
				textareas.eq(1).val((title.resultMGList[0].contentDescribe2==undefined || textarea_show(title.resultMGList[0].contentDescribe2)==0)?"":str2).attr("resultId",result_id);
				textareas.closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
			}
		}
		else if(title.type == 16)
		{
			if(readonly == true)
			{
				var dds = $("dd[data-title-id='" + title.id + "']");
				var str=title.resultMGList[0].contentDescribe1;
				if(str !=undefined && str.indexOf("<sitg>")>-1){
					var conStr=str.split("<sitg>");
					var sum=0;
				   for(var i=0;i<conStr.length;i++){
						if(conStr[i].indexOf("</sitg>")>-1){
							var inputsValueLen=conStr[i].substring(0,conStr[i].indexOf("</sitg>")).trim().length;
							sum +=inputsValueLen
						}
					}
				}
				if(str){
					str=str.replace(/<sitg>/g,'（');
					str=str.replace(/<\/sitg>/g,'）');
				}
				dds.html((title.resultMGList[0].contentDescribe1==undefined || sum==0) ?"未填写":str);
			}
	        else{
				var str=title.resultMGList[0].contentDescribe1;
				var result_id=title.resultMGList[0].id;
				var div=$(".inputs_block").closest(".h_edit_txt");
				div.find('dt').attr("tochange",true);
				if(str !=undefined){
					 div.children("dd").find("input").attr("resultId",result_id);
				}
				if(str !=undefined && str.indexOf("<sitg>")>-1){
					var str=str.split("<sitg>");
					var inputsValueList=[];
				   for(var i=0;i<str.length;i++){
						if(str[i].indexOf("</sitg>")>-1){
							var inputsValue=str[i].substring(0,str[i].indexOf("</sitg>"));
							inputsValueList.push(inputsValue);
						}
					}
				   for(var j=0;j<div.children("dd").length;j++){
					   div.children("dd").eq(j).find("input").val(inputsValueList[j].trim());
					   
				   }
				}
			}
		}
		else if(title.type == 8)
		{
			if(readonly == true)
			{
				var contentDescribe=title.resultMGList[0].contentDescribe1;
				$(".field[data-title-id='"+title.id+"']").html((contentDescribe==undefined || textarea_show(contentDescribe)==0) ?"未填写":title.resultMGList[0].contentDescribe1);
			}
			else
			{
				var str=title.resultMGList[0].contentDescribe1;
				var result_id = title.resultMGList[0].id;
				if(str){
					str=str.replace(/<br\/>/g,'\n');
					str=str.replace(/<br>/g,'\n');
					str=str.replace(/&nbsp;/g," ");
				}
				$("textarea[data-title-id='"+title.id+"']").val((title.resultMGList[0].contentDescribe1==undefined || textarea_show(title.resultMGList[0].contentDescribe1)==0)?"":str).attr("resultId",result_id);
				$("textarea[data-title-id='"+title.id+"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
			}
		}
		else if(title.type == 14)
		{
			if(readonly == true)
			{
				$("dd[class='field'][data-title-id='"+ title.id +"']").text(title.resultMGList[0].valueName==undefined ?"未选择":title.resultMGList[0].valueName);
				//$(".field[data-title-id='"+title.id+"']").text(title.resultMGList[0].valueName);
			}
			else
			{
				var result_id = title.resultMGList[0].id;
				$('select[data-id="' + title.id + '"],select[data-title-id="' + title.id + '"]').val( title.resultMGList[0].contentChoose ).attr("resultId",result_id);
				$('select[data-id="' + title.id + '"],select[data-title-id="' + title.id + '"]').closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
				
			}
		}
		else if(title.type == 19 )
		{
			if(readonly == true)
			{
				var _val = title.resultMGList[0].contentDescribe1;
				var test_num = $(".field[data-title-id='"+title.id+"']").next().text();
				if(test_num.indexOf("元")<0){
					_val = _parsefloat(_val);
					var moneyT =test_num;
				}else{
					if(_val==undefined){
						_val="未填写"
					}else{
						var res = change_number(_val);
						_val = _parsefloat(res[0]);
						var moneyT = res[1]+"元";
					}
				}
				
				$(".field[data-title-id='"+title.id+"']").text(_val);
				if(title.resultMGList[0].contentDescribe1 !=undefined){
					$(".field[data-title-id='"+title.id+"']").next().text(moneyT).show();
				}else{
					$(".field[data-title-id='"+title.id+"']").next().hide();
				}
			}
			else
			{	
				var result_id = title.resultMGList[0].id;				
				$("input[data-title-id='"+title.id+"']").val(title.resultMGList[0].contentDescribe1==undefined ?"":_parsefloat(title.resultMGList[0].contentDescribe1)).attr("resultId",result_id);		
				$("input[data-title-id='"+title.id+"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
			}
		}
		else if( title.type == 20)
		{
			if(readonly == true)
			{
				var str = title.resultMGList[0].contentDescribe2
				var strs= new Array();
				if(str!=null || str!=undefined){
					strs=str.split("p")
				}
				var _val = title.resultMGList[0].contentDescribe1;
				if(_val==undefined){
					_val="未填写"
				}else{
					var res = change_number(_val);
					_val = _parsefloat(res[0]);
					var moneyT = res[1];
				}
				$(".field[data-title-id='"+title.id+"']").text(_val);
				if($(".field[data-title-id='"+title.id+"']").text() !='未填写'){					
					$(".field[data-title-id='"+title.id+"']").next().text(moneyT).show();
					$(".field[data-title-id='"+title.id+"']").next().next().text(strs[0]).show();
				}else{
					$(".field[data-title-id='"+title.id+"']").next().hide();
					$(".field[data-title-id='"+title.id+"']").next().next().text(strs[0]).hide();
				}
			}
			else
			{	
				var result_id = title.resultMGList[0].id;	
				var result_parentId = title.resultMGList[0].titleId
				$("input[data-title-id='"+title.id+"']").val(title.resultMGList[0].contentDescribe1==undefined ?"":_parsefloat(title.resultMGList[0].contentDescribe1)).attr("resultId",result_id);
				var str = title.resultMGList[0].contentDescribe2
				var strs= new Array();
				if(str!=null || str!=undefined){
					strs=str.split("p")
				}
				var value=strs[1];
				$('#'+result_parentId+'_select').val(value);
				$("input[data-title-id='"+title.id+"']").closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
			}
		}else if(title.type==21){
			var result_id = title.resultMGList[0].id;	
			var result_parentId = title.resultMGList[0].titleId;
			$('select[data-title-id="' + result_parentId + '"]').val( title.resultMGList[0].contentChoose ).attr("resultId",result_id);;
			var dt = $("dt[data-type='21'][data-tid='"+ result_parentId +"']");
			var dd = dt.siblings();
			var last_id = dd.find('select option:last').attr('value');
			var inputText = dd.siblings('input[type="text"][data-type="21"]');
			if ( title.resultMGList[0].contentChoose == last_id ){
				inputText.attr('disabled',false);
				inputText.removeClass('disabled');
			}else{
				inputText.val('');
				inputText.attr('disabled',true);
				inputText.addClass('disabled');
				inputText.removeAttr('required');
			}
			$.each(title.resultMGList,function(i,n){
				var result_id= n.id;					
				if(n.contentDescribe1){  
					inputText.val(n.contentDescribe1).attr("resultId",result_id);
					inputText.attr('disabled',false);
					inputText.removeClass('disabled');
					inputText.attr('required' , true);
				}
			})
			$('select[data-title-id="' + result_parentId + '"]').closest('div.mb_24,dl.h_edit_txt').find('dt').attr("tochange",true);
			
		}
	//最外部else
	}else{
		if(title.type == 3){
			if (readonly == true)
			{
				var dds = $("dt[data-type='3'][data-title-id='"+ title.id +"']").siblings().children();
				$.each(dds,function(i,n)
				{
					if ($(this).text() == '未选择')
					{
						$(this).hide();
					}
				});
				var dd='<dd>未选择</dd>';
				$("dt[data-type='3'][data-title-id='"+ title.id +"']").siblings().append(dd);
			}
		}else if(title.type == 13){
			if (readonly == true)
			{
				$("dt[data-id='"+ title.id +"']").siblings(".checked_div").find("dd[data-code]").text("");
				$("dt[data-id='"+ title.id +"']").siblings(".checked_div").find("dd[data-code]").hide();
				$("dt[data-id='"+ title.id +"']").siblings(".checked_div").find(".field").show();
			}
		}
	}
}
function buildMemberTableDraft(sec,title){
        //列表Header
	
    	if(title.tableHeader)
    	{
    		var header = title.tableHeader;
    		var tables = $("table.editable[data-title-id='"+header.titleId+"']");
    		$.each(tables,function(){
    			var table = $(this);
    			table.attr('data-code',header.code);
    			table.empty();
    			var tr="<tr>";
    			for(var key in header)
    			{   //过滤掉电话字段
                    if(key.indexOf('field')>-1 && key != "field4")
                    {
                        tr +='<th data-field-name="'+key+'">'+header[key]+'</th>';
                    }
                }
    			//var editable = table.hasClass('editable');
                tr +='<th data-field-name="opt">操作</th>';
                tr+="</tr>";
    			table.append(tr);
    		});
    	}
    	//列表Row
    	if(title.dataMGList)
    	{
    	    var list = title.dataMGList;
    		$.each(list,function(){
    			var row = this;
    			var tables = $("table.editable[data-title-id='"+row.titleId+"']");

    			$.each(tables,function(){
    				var table = $(this);
    				var headerList = table.find('tbody').find('tr:eq(0)').find("th[data-field-name!='opt']");
    				var tr = buildMemberRowDraft(headerList,row,table.hasClass('editable'));
    				table.append(tr);
    			});
    		});
    	}
}
function buildMemberRowDraft(headerList,row,showOpts)
{
	var tr=$("<tr data-row-id='"+row.id+"'></tr>");
	tr.data("person",row);
	if(row.other && row.other!=''){
		row.field2=row.field2+"-"+row.other;
	}else{
		row.field2=row.field2;
	}
    for(var key in row)
   	{
    	//设置data
   		tr.data(key,row[key]);
   	}
    $(headerList).each(function(){
        var key = $(this).attr("data-field-name");
        tr.data(key,row[key]);
        if(key.indexOf('field')>-1)
        {
            if(row[key]){
                //select字段在页面缓存根据id取value
            	if(key == "field2"){
                	if(row[key].indexOf("1363")>-1){
                		var field=row.field2.split("-");
                		if(field.length>1){
                			map_edu[row[key]]=row.field2.substring(5,row.field2.length);
                		}else{
                    		map_edu[row[key]]="";
                    	}
                	}
                	if(map_edu[row[key]]==""||map_edu[row[key]]==undefined||map_edu[row[key]]=="undefined"){
                		map_edu[row[key]]="未知";
                	}
                     tr.append('<td data-field-name="'+key+'">'+map_edu[row[key]]+'</td>');
                     return;
                }else if(key == "field5"){
                	if(map_pos[row[key]]==""||map_pos[row[key]]==undefined||map_pos[row[key]]=="undefined"){
                		map_pos[row[key]]="未知"
                	}
                     tr.append('<td data-field-name="'+key+'">'+map_pos[row[key]]+'</td>');
                     return;
                }else if(key == "field3"){
                	if(map_sex[row[key]]==""||map_sex[row[key]]==undefined||map_sex[row[key]]=="undefined"){
                		map_sex[row[key]]="未知"
                	}
                    tr.append('<td data-field-name="'+key+'">'+map_sex[row[key]]+'</td>');
                    return;
               }else{
                     tr.append('<td data-field-name="'+key+'">'+row[key]+'</td>');
                }
            }else{
                tr.data(key,"未知");
                tr.append('<td data-field-name="'+key+'">未知</td>');
            }
        }

    })

	var td = $('<td data-field-name="opt"></td>');
    if(showOpts == true)
    {
        td.append('<span class="blue" data-btn="btn" onclick="showMemberRow(this)">查看</span>');
        td.append('<span class="blue" data-btn="btn" onclick="editMemberRow(this)">编辑</span>');
        td.append('<span class="blue" data-btn="btn" onclick="delRow(this)">删除</span>');
        tr.append(td);
    }else{
        td.append('<span class="blue" data-btn="btn" onclick="showMemberRow(this)">查看</span>');
        tr.append(td);
    }
	return tr;
}
function buildTableDraft(sec,title)
{
	//列表Header
	
	if(title.tableHeader)
	{
		var header = title.tableHeader;
		var tables = $("table.editable[data-title-id='"+header.titleId+"']");
		$.each(tables,function(){
			var table = $(this);
			table.attr('data-code',header.code);
			table.attr('data-funFlag',header.funFlag);			
			table.empty();
			var tr="<tr>";
			for(var key in header)
			{
				if(key.indexOf('field')>-1)
				{
					if((header.titleId == '1810'||header.titleId == '1811') && key == 'field2'){
						continue;
					}
					if(header.code=='finance-history'&&(key == 'field8'||key == 'field9'||key == 'field10')){
						continue;
					}
					if(header.code=='competitor_obvious'&&(key != 'field1')){
						continue;
					}
					if(header.code=='competitor_potential'&&(key != 'field1')){
						continue;
					}
					if(key!="opt"){
					    tr +='<th data-field-name="'+key+'">'+header[key]+'</th>';
					}
				}
			}
			if(header.titleId =='1810'||header.titleId =='1811')
			{
				tr +='<th data-field-name="updateUserName">编辑人</th>';
				tr +='<th data-field-name="updateTimeStr">编辑日期</th>';
			}
			var editable = table.hasClass('editable');
		
			if(editable == true||header.funFlag=="1")
			{
				tr +='<th data-field-name="opt">操作</th>';
			}
			tr+="</tr>";
			table.append(tr);
		});
	}
	//列表Row
	if(title.dataMGList)
	{
		$.each(title.dataMGList,function(){
			var row = this;
			var tables = $("table.editable[data-title-id='"+row.titleId+"']");
			tables.show();   //有数据表格显示
			$.each(tables,function(){
				var table = $(this);
				var tr = buildRowDraft(row,table.hasClass('editable'),row.titleId);
				table.append(tr);
				//增加显示字段限制，，市场同类公司估值参考
				var dataCode = table.attr('data-code');
				if(dataCode === 'valuation-reference'){
					var targetTd = table.find('tr').find('td:eq(0)');
					targetTd.addClass('limit-number');
					$('.limit-number').each(function(){
						var _this = $(this);
						var tdText = _this.text();
						_this.attr('title',tdText);
					})
				};
				//股权结构合理性
				if(dataCode === 'share-holding'|| dataCode === "equity-structure"){
					var targetTd = table.find('tr').find('td:eq(0)');
					targetTd.addClass('limit-number');
					$('.limit-number').each(function(){
						var _this = $(this);
						var tdText = _this.text();
						_this.attr('title',tdText);
							
					})
				}
				//主要战略投资人/财务投资人；
				if(dataCode === "investor-situation" ){
					var targetTd = table.find('tr').find('td:eq(1)');
					targetTd.addClass('limit-number');
					targetTd.css({width:'14.28%',overflow:"hidden"});
					targetTd.css('text-overflow','ellipsis');
					targetTd.css('white-space','nowrap');
					$('.limit-number').each(function(){
						var _this = $(this);
						var tdText = _this.text();
						_this.attr('title',tdText);
							
					})
				};
				
			});
		});
	}
}
function buildRowDraft(row,showOpts,titleId)
{
	var table =$('table.editable[data-title-id="'+titleId+'"]:eq(0)');
	var ths =table.find("th") ;
	var tr=$("<tr data-row-id='"+row.id+"'></tr>");
	var titleId = table.attr('data-title-id');
	for(var key in row)
	{
		//设置data
		tr.data(key,row[key]);
	}
	$.each(ths,function()
	{
		var $this = $(this);
		var k  = $this.data('fieldName');
		/*if(k === 'field1'){
			num = k;
		}*/
		if(k!="opt"){
			if(row[k]!=undefined && row[k]!=null){
				if(titleId=="1906"||titleId=="1920"||titleId=="1325"){			
					if(k=="field2"){
						row[k] = _parsefloat(row[k]);
					}
				}
				if(titleId=="1548"||titleId=="3022"){					
					if(k=="field3"){
						row[k] = _parsefloat(row[k]);
					}
				}
				if(titleId=="1903"||titleId=="1908"){
					if(k=="field3"||k=="field4"||k=="field5")
					row[k] = _parsefloat(row[k]);
				}
				
				tr.append('<td data-field-name="'+k+'">'+row[k]+'</td>');
			}else{
				tr.append('<td data-field-name="'+k+'"></td>');
			}
			
			//新增的时候添加title
			if(titleId==='1325'){//股权结构合理性
				var targetTd = tr.find('td[data-field-name="field1"]');
				targetTd.attr('title',targetTd.text());
			}else if(titleId==='1908'){//主要战略投资人，财务投资人投资情况
				var targetTd = tr.find('td[data-field-name="field2"]');
				targetTd.attr('title',targetTd.text());
			}else if(titleId==='1920'){//市场同类公司估值参考
				var targetTd = tr.find('td[data-field-name="field1"]');
				targetTd.attr('title',targetTd.text());
			}else if(titleId==='1906'){//市场同类公司估值参考
				var targetTd = tr.find('td[data-field-name="field1"]');
				targetTd.attr('title',targetTd.text());
			}
			
			
		}
			
	});
	var funFlg=$('table.editable[data-title-id="'+titleId+'"]').attr("data-funFlag");
	var td = $('<td data-field-name="opt"></td>');
	if(showOpts == true)
	{
		if(funFlg=="1"){
			td.append('<span class="blue" data-btn="btn" onclick="editRow(this)">查看</span>');
		}
		td.append('<span class="blue" data-btn="btn" onclick="editRow(this)">编辑</span>');
		td.append('<span class="blue" data-btn="btn" onclick="delRow(this)">删除</span>');
		tr.append(td);
	}else{
		if(funFlg=="1"){
			td.append('<span class="blue" data-btn="btn" onclick="editRow(this)">查看</span>');
		    tr.append(td);
		}
	};
	
	return tr;
	

}
function buildfinxedTableDraft(sec,title,readonly){
	if(null!=title.fixedTableMGList&&title.fixedTableMGList.length>0){
	  if(readonly == true)
		{
		  $.each(title.fixedTableMGList,function(i,n){
				$("td[data-format='"+n.rowNo+"_"+n.colNo+"']").text(n.valueName);
			});
		}else
		{
			$.each(title.fixedTableMGList,function(i,n){
				$("td[data-flag='"+n.colNo+"']").find("input[data-row='row"+n.rowNo+"'][value="+n.content+"]").attr('checked','true');
				$("td[data-flag='"+n.colNo+"']").find("input[data-row='row"+n.rowNo+"']").attr('data-value-id',n.id);
				
			});
		}
	}
}
