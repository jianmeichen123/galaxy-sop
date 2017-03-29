//textarea_h自适应高度
function textarea_h(data){
//	超出2000截取字符串
	data.value=data.value.replace(/\<br \/\>/g,'\n');
	var taxt_length = data.value.length;
	if(taxt_length>2000){
		data.value = data.value.substr(0,2000);
		$(font_num).html(0);
	}else{
//		字数
		var parent= data.parentNode;
		var p_node = parent.childNodes[1];
		var font_num = p_node.childNodes[0];
		$(font_num).html(2000 - taxt_length);
	}
	$(data).css("height","auto");
	if(data.scrollHeight> $(data).height()){
		 $(data).height(data.scrollHeight);
	}
}
function backFun(data){
	var result = data.result.status;
	if (result == 'OK') {
		var entity = data.entity;
		var html = toGetHtmlByMark(entity,'s');
		var s_div = toShowTitleHtml(entity, html);
		$("#"+entity.code).html(s_div);
	}
}


//区域显示
function showArea(code){
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/" + code, null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#"+code).html(s_div);
		}
	});
}


function toShowTitleHtml(title,html){
	
	var editHtm = "";
	if(isEditable && isEditable == 'true') {
		editHtm = "<span class=\"h_edit_btn\" attr-id='" + title.code + "'>编辑</span>"
	}
	var titleDiv = "" ;
	if(title.name){
		titleDiv = "<div class=\"h_title sec_box\">" + title.name + "</div>" ;
	}
	
	var s_div = 
		"<div class=\"h_look h_team_look clearfix\" id=\"a_"+title.code+"\" >" +
			"<div class=\"h_btnbox\">" +
				editHtm +
		    "</div>" +
		    titleDiv +
			html +
		"</div>";
		
	return s_div;
}

function toEditTitleHtml(title,html){
	var titleDiv = "" ;
	if(title.name){
		titleDiv = "<div class=\"h_title\">" + title.name + "</div>" ;
	}
	var s_div = 
		"<div class=\"h_edit h_team_look base_team_look clearfix\" id=\"b_"+title.code+"\" >" +
			"<div class=\"h_btnbox\">" +
		    	"<span class=\"h_save_btn\" data-on=\"save\" attr-save=\""+title.code+"\">保存</span>" +
		    	"<span class=\"h_cancel_btn\" data-on=\"h_cancel\" attr-hide=\""+title.code+"\" >取消</span>" +
		    "</div>" +
		    titleDiv +
			html +
			"<div class=\"h_edit_btnbox clearfix\">" +
		    	"<span class=\"pubbtn bluebtn h_save_btn fl\" data-on=\"save\" attr-save=\""+title.code+"\" >保存</span>" +
		    	"<span class=\"pubbtn fffbtn fl h_cancel_btn\" data-on=\"h_cancel\" attr-hide=\""+title.code+"\" >取消</span>" +
		    "</div>" +
		"</div>";
	return s_div;
}

function toGetHtmlByMark(title,mark){
	var html = "";
	if(title.type){
		html += switchTypeByMark(title,mark);
	}
	
	var tilelist = title.childList;
	
	$.each(tilelist,function(i,o){
		if(this.sign  && this.sign == 3){
			html += "<div class=\"mb_24 clearfix\">" + this.name + "</div>";
			sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/" + this.code, null, function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var sign_title = data.entity;
					html += toGetHtmlByMark(sign_title,mark);
				}
			});
		}else{
			html += switchTypeByMark(this,mark);
		}
	});
	return html;
}

function switchTypeByMark(title,mark){
	var html = "";
	switch (title.type) {
        case 1:  
        	html += type_1_html(title,mark);
            break;
        case 2:  
        	html += type_2_html(title,mark);
            break;
        case 3:   
        	html += type_3_html(title,mark);
            break;
        case 4:   
        	html += type_4_html(title,mark);
            break;
        case 5:  
        	html += type_5_html(title,mark);
            break;
        case 6:  
        	html += type_6_html(title,mark);
            break;
        case 7:   
        	html += type_7_html(title,mark);
            break;
        case 8:   
        	html += type_8_html(title,mark);
            break;
        case 9:  
        	//html += type_9_html(title);
            break;
        case 10:  
        	html += type_10_html(title,mark);
            break;
        case 11:   
        	html += type_11_html(title,mark);
            break;
        case 12:   
        	html += type_12_html(title,mark);
            break;
        case 13:   
        	html += type_13_html(title,mark);
            break;
        case 14:   
        	html += type_14_html(title,mark);
            break;
        default:
            break;
    }
	return html;
}



//1:文本、2:单选（Radio）、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、
//7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)、14单选（select）

// 1:文本 
function type_1_html(title,mark){
	var htitle = "<dt>"+title.name+"</dt>";
	
	var results = title.resultList;
	
	if(mark == 's'){
		var hresult = "<dd>未填写</dd>";
		if(results && results[0] && results[0].contentDescribe1){
			hresult = "<dd>"+results[0].contentDescribe1+"</dd>";
		}
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}else{
		var value = '';
		if(results && results[0] && results[0].contentDescribe1) value = results[0].contentDescribe1;
		var placeholder = '';
		if(title.placeholder) placeholder = title.placeholder;
		
		eresult = "<input type=\"text\" class=\"txt\" value='"+ value +"' " +
					"data-title-id='"+title.id+"' data-type='"+title.type+"' placeholder='"+placeholder+"' />";
		return  "<div class=\"mb_24 clearfix\">" + htitle + eresult + "</div>";
	}
}



function one_select_edit(title,inputtype){
	var eresult = "";
	var values = title.valueList;
	
	//if(values.length < 6){
	if(inputtype == 'radio'){
		var li = "";
		$.each(values,function(i,o){
			if(this.checked){
				li +=  "<li><input type=\"radio\" value='"+this.id+"' name='"+title.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' checked=\"true\" />" + this.name  + "</li>";
			}else
				li +=  "<li><input type=\"radio\" value='"+this.id+"' name='"+title.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' />" + this.name  + "</li>";
		});
		eresult = 
			"<dd>" +
				"<ul class=\"h_radios clearfix\">" +
					li + 
				"</ul>" +
			"</dd>";	
	}else{
		var li = "<option data-title-id='"+title.id+"' data-type='"+title.type+"' value='' >请选择</option>";
    	$.each(values,function(i,o){
			if(this.checked){
				li +=  "<option value='"+this.id+ "' data-title-id='"+title.id+"' data-type='"+title.type+"' selected=\"selected\" >"  + this.name + "</option>";
			}else
				li +=  "<option value='"+this.id+ "' data-title-id='"+title.id+"' data-type='"+title.type+"' >"  + this.name + "</option>";
		});
    	eresult = 
    		"<dd>" +
		    	"<select>" +
					li +
				"</select>" +
	    	"</dd>";
	}
	
	return eresult;
}
// 2:单选
function type_2_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"' >"+title.name+"</dt>";
	if(mark == 's'){
		var hresult = "<dd>未选择</dd>";
		var results = title.resultList;
		if(results && results[0] && results[0].valueName){
			hresult = "<dd>"+results[0].valueName+"</dd>";
		}
		return  "<div class=\"mb_24 division_dd base_half clearfix\">" + htitle + hresult + "</div>";
	}else{
		var eresult = one_select_edit(title,'radio');
		return  "<div class=\"mb_24 clearfix\">" + htitle + eresult + "</div>";
	}
}




// 3:复选
function type_3_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"'>"+title.name+"</dt>";
	
	if(mark == 's'){
		var hresult = "<dd>未选择</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].valueName){
			hresult = "";
			$.each(results,function(i,o){
				hresult +=  "<dd class=\"border_dd\">"+this.valueName+"</dd>";
			});
		}
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}else{
		htitle = "<dt data-title-id='"+title.id+"' data-type='"+title.type+"' >"+title.name+"</dt>";
		
		var li = "";
		var values = title.valueList;
		$.each(values,function(i,o){
			if(this.checked){
				li +=  "<li class=\"check_label active\" data-value='"+this.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' >"  + this.name + "</li>";
			}else
				li +=  "<li class=\"check_label\" data-value='"+this.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' >"  + this.name + "</li>";
		});
		var eresult = 
			"<dd >" +
				"<ul class=\"h_edit_checkbox pro_innovation  select_strategy clearfix\">" +
					li +
				"</ul>" +
			"</dd>";
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + eresult + "</div>";
	}
}


 

function getNextSelect(title,cid){
	var rselect = {};
	sendGetRequest(platformUrl.queryProNvaluesInfo + pid + "/"+ title.id  +"/"+cid ,null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entitys = data.entityList;
			rselect = nselectHtml(entitys,title,cid);
		}
	});
	return rselect;
}
function nselectHtml(values,title,cid){
	//var results = title.resultList;
	
	var has_checked = false;
	var li = "<option data-title-id='"+title.id+"' data-type='"+title.type+"' value='' >请选择</option>";
	
	$.each(values,function(i,o){
		if(this.checked){
			has_checked = true;
			cid = this.id;
			li +=  "<option value='"+this.id+ "' data-title-id='"+title.id+"' data-type='"+title.type+"' selected=\"selected\" >"  + this.name + "</option>";
		}else{
			li +=  "<option value='"+this.id+ "' data-title-id='"+title.id+"' data-type='"+title.type+"' >"  + this.name + "</option>";
		}
	});
	if(!has_checked){
		cid = null;
	}
	
	var relu_ht =
			"<select onchange=\"showConstarct(this,'"+title.id+ "','" + title.type + "')\" >" +
				li +
			"</select>" ;
	var return_re = {
			htm : relu_ht,
			vpid : cid
	}
	return return_re;
    	
}
// 4:级联选择
function type_4_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"'>"+title.name+"</dt>";
	
	if(mark == 's'){
		var hresult = "<dd>未选择</dd>";
		
		var results = title.resultList;
		if(results && results.length > 0 ){
			hresult = "";
			$.each(results,function(i,o){
				if(this.valueName){
					hresult +=  "<dd>"+this.valueName+" &nbsp;&nbsp;</dd>";
				}
			});
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}else{
		var eresult = "";
		
		var checked_cid;
		for(var i = 1; i <6; i++){
			if(i == 1){
				var values = title.valueList;
				var return_j = nselectHtml(values,title,checked_cid);
				checked_cid = return_j.vpid;
				eresult += return_j.htm;
			}else{
				if(checked_cid){
					var return_j = getNextSelect(title,checked_cid);
					checked_cid = return_j.vpid;
					eresult += return_j.htm;
				}else{
					var return_j = nselectHtml({},title,checked_cid);
					eresult += return_j.htm;
				}
			}
		}
		
		return  "<div class=\"mb_24 select_box clearfix\">" + htitle +eresult +"</div>";
	}
}
function showConstarct(thisSelect,tid,type){
	var _this = $(thisSelect);
	var vid = _this.find("option:selected").val();
	if(vid){
		var nextSelect = _this.next();
		if(nextSelect && nextSelect.length == 1){
			
			var li = "<option data-title-id='"+tid+"' data-type='"+type+"' value='' >请选择</option>";
			var li_htm = li;
			sendGetRequest(platformUrl.queryValuesByVpid + vid, null, function(data) {
	    		var result = data.result.status;
	    		if (result == 'OK') {
	    			var entitys = data.entityList;
	    			
	    			$.each(entitys,function(i,o){
	    				li_htm +=  "<option value='"+this.id+ "' data-title-id='"+tid+"' data-type='"+type+"' >"  + this.name + "</option>";
	    			});
	    		}
	    		
	    		$(nextSelect).html(li_htm);
	    		
	    		for(var i = 0; i<5; i ++){
	    			nextSelect = $(nextSelect).next();
    				if(nextSelect && nextSelect.length == 1){
    					$(nextSelect).html(li);
    				}else{
    					break;
    				}
    			}
	    	});
		}
	}
} 
  



//5:单选带备注 (textarea)
function type_5_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"' >"+title.name+"</dt>";
	
	var results = title.resultList;
	
	if(mark == 's'){
		var hresult_1 = "<dd>未选择</dd>";
		var hresult_2 = "<dd class=\"fl_none division_dd\">未填写</dd>";
		
		if(results && results[0] && results[0].id){
			for(var i = 0;  i < results.length; i++ ){
				if(results[i].contentDescribe1){
					hresult_2 =  "<dd class=\"fl_none division_dd\">"+results[i].contentDescribe1+"</dd>";
				}else if(results[i].valueName){
					hresult_1 =  "<dd>"+results[i].valueName+"</dd>";
				}
			}
		}
		
		return  "<div class=\"mb_24  clearfix\">" + htitle + hresult_1 + "<br/>" + hresult_2 + "</div>";
	}else{
		var eresult_1 = one_select_edit(title,'radio');
		
		var r_value = '';
		if(results && results.length > 0){
			for(var i = 0;  i < results.length; i++ ){
				if(results[i].contentDescribe1){
					r_value = results[i].contentDescribe1;
					break;
				}
			}
		}
		var eresult_2 = 
			"<dd class=\"fl_none fl_none_box\">" +
				"<textarea class=\"textarea_h\" " +
					"data-title-id='"+title.id+"' data-type='"+title.type+"' oninput='textarea_h(this)'   placeholder='"+title.placeholder+"' >" +
					 	r_value +
				"</textarea>" +
				"<p class=\"num_tj\"><label>0</label><span>/2000</span></p>" +
			"</dd>";	
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + eresult_1 + "<br/>" + eresult_2 + "</div>";
	}
	
}


//6:复选带备注(textarea)
function type_6_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"' >"+title.name+"</dt>";
	
	var results = title.resultList;
	
	if(mark == 's'){
		var hresult_1 = "<dd>未选择</dd>";
		var hresult_2 = "<dd class=\"fl_none division_dd\">未填写</dd>";
		
		if(results && results[0] && results[0].id){
			
			var hasC = false;
			var cr = "";
			$.each(results,function(i,o){
				if(this.valueName){
					hasC = true;
					cr +=  "<dd >"+this.valueName+"</dd> &nbsp;&nbsp;";
				}else if(this.contentDescribe1){
					hresult_2 = "<dd class=\"fl_none division_dd\">"+this.contentDescribe1+"</dd>";
				}
			});
			
			if(hasC == true){
				hresult_1 = cr;
			}
		}

		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult_1 + "<br/>" + hresult_2 + "</div>";
	}else{
		htitle = "<dt class=\"fl_none\">"+title.name+"</dt>";
		
		var li = "";
		var values = title.valueList;
		$.each(results,function(i,o){
			if(this.checked){
				li +=  "<li class=\"check_label active\" data-value='"+this.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' >"  + this.name + "</li>";
			}else{
				li +=  "<li class=\"check_label\" data-value='"+this.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' >"  + this.name + "</li>";
			}
		});
		var eresult_1 = 
			"<dd class=\"fl_none\">" +
				"<ul class=\"h_edit_checkbox clearfix\">" +
					li +
				"</ul>" +
			"</dd>";	
		
		
		var r_value = '';
		if(results && results.length > 0){
			for(var i = 0;  i < results.length; i++ ){
				if(results[i].contentDescribe1){
					r_value = results[i].contentDescribe1;
					break;
				}
			}
		}
		var eresult_2 = 
			"<dd class=\"fl_none\">" +
				"<textarea class=\"textarea_h\" " +
					"data-title-id='"+title.id+"' data-type='"+title.type+"' oninput='textarea_h(this)'  placeholder='"+title.placeholder+"' >" +
					 	r_value +
				"</textarea>" +
				"<p class=\"num_tj\"><label>0</label><span>/2000</span></p>" +
			"</dd>";	
		
		return  "<div class=\"mb_24 clearfix\">" + htitle  + eresult_1 + "<br/>" + eresult_2 + "</div>";
	}
	
}




// 7:附件
function type_7_html(title){
	
	return  "<dt class=\"fl_none\"> 除去非主营业务外，运营数据曲线变化（细分项目、拆分到年度、月度、周、日）：</dt>" +
			"<dd class=\"fl_none\">" +
				"<img src=\"img/loginbg.gif\" alt=''>" +
				"<img src=\"img/loginbg.gif\" alt=''>" +
			"</dd>";
}



// 8:文本域
function type_8_html(title,mark){
	var htitle = "<dt data-tid='"+title.id+"' >"+title.name+"</dt>";
	
	var results = title.resultList;
	
	if(mark == 's'){
		var hresult = "<dd class=\"fl_none division_dd\">未填写</dd>";
		if(results && results[0] && results[0].contentDescribe1){
			hresult = "<dd class=\"fl_none division_dd \" >"+results[0].contentDescribe1+"</dd>";
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + "<br/>" + hresult + "</div>";
	}else{
		htitle = "<dt class=\"fl_none\">"+title.name+"</dt>";

		var r_value = '';
		if(results && results[0] && results[0].contentDescribe1) r_value = results[0].contentDescribe1;
		
		var eresult =
			"<dd class=\"fl_none\">" +
				"<textarea class=\"textarea_h\" data-title-id='"+title.id+"' data-type='"+title.type+"' oninput='textarea_h(this)'  placeholder='"+title.placeholder+"'>" +
					r_value +
				"</textarea>" +
				"<p class=\"num_tj\"><label>0</label><span>/2000</span></p>" +
			"</dd>";
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + "<br/>" + eresult + "</div>";
	}
}



//10:表格
function type_10_html(title,mark){
	table_delComArr[title.id] = [];
	table_toedit_Value[title.id] = {};
	table_tosave_Value[title.id] = {};
	
	var htitle = "<dt data-tid='"+title.id+"' data-type='"+title.type+"' >"+title.name+"</dt>";
	 
	var tableHeader = title.tableHeader;
	var dataList = title.dataList;
	
	if(mark == 's'){
		
		var filed_sort = [];
		if(title.code == 'NO5_7_1'){   //综合竞争比较
			filed_sort = ['field1','field2','field3','field4','field5'];
			table_filed[title.id] = filed_sort;
		}
		
		var hresult = "<dd>未填写</dd>";
		if(dataList != null && dataList.length != 0){
			
			table_value(title.id,dataList);
			
			hresult = "<dd><table data-talbe-tid='"+title.id+"' ><thead><tr>"
			
			var th = "";
			for(var i = 0 ; i < filed_sort.length; i++){
				th +='<th>'+tableHeader[filed_sort[i]]+'</th>';
			}
			
			hresult += th + "</tr></thead><tbody>";
			
			var tr = "";
			$.each(dataList,function(){
				tr += '<tr>';
				for(var i = 0 ; i < filed_sort.length; i++){
					tr +='<td>'+this[filed_sort[i]]+'</td>';
				}
				tr += "</tr>";
			});
			hresult += (tr + "</tbody></table></dd>");
		}
		return  "<div class=\"mb_24 clearfix\"><dl class=\"clearfix\">" + htitle + "<br/>" + hresult + "</dl></div>";
	}else{
		var to_add = "<a href='javascript:;' class=\"blue pubbtn bluebtn btn_compet\" onclick=\"add_"+title.code+"('"+title.id+"','"+title.code+"')\" >新增</a>";
		
		var filed_sort = table_filed[title.id];
		
		var eresult = "<dd><table><thead>";
		
		var th = "<tr>";
		for(var i = 0 ; i < filed_sort.length; i++){
			var filed  = filed_sort[i];
			th +='<th>'+tableHeader[filed]+'</th>';
		}
		th +='<th>操作</th>';
		
		eresult += th + "</tr></thead><tbody data-tbody-tid='"+title.id+"' data-tbody-tcode='"+title.code+"' >";
		
		if(dataList != null && dataList.length != 0){
			var tr = "";
			$.each(dataList,function(i,o){
				tr += '<tr data-opt="old" data-result-id="'+o.id+'" >';
				for(var i = 0 ; i < filed_sort.length; i++){
					tr +='<td>'+o[filed_sort[i]]+'</td>';
				}
				
				var edit = "<a href='javascript:;' class=\"blue\" onclick=\"edit_"+title.code+"(this,'"+title.id+"','"+o.id+"')\" >编辑</a>";
				var del = "&nbsp;<a href='javascript:;' class=\"blue\" onclick=\"del_"+title.code+"(this,'"+title.id+"','"+o.id+"')\" >删除</a>";
				tr += ('<td>' + edit + del + '</td>');
				
				tr += "</tr>";
			});
			eresult += tr ;
		}
		
		eresult += "</tbody></table></dd>";
		return  "<div class=\"mb_24 clearfix\">" + htitle + to_add + "<br/>" + eresult + "</div>";
	}
}





// 11:静态数据
function type_11_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"' >"+title.name+"</dt>";
	var hresult = "<dd> - </dd>";
	
	switch (title.code) {
        case "NO1_1_1":  //项目编号
        	var results = title.resultList;
        	if(mark == 's'){
        		if(results && results[0] && results[0].contentDescribe1){
        			hresult = "<dd>"+results[0].contentDescribe1+"</dd>";
        		}else{
        			hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
        		}
        	}else{
        		if(results && results[0] && results[0].contentDescribe1){
        			hresult = "<input type=\"text\" class=\"txt\" value='"+results[0].contentDescribe1+"' data-title-id='"+title.id+"' data-type='"+title.type+"' />";
        		}else{
        			hresult = "<input type=\"text\" class=\"txt\" value='"+projectInfo.projectCode+"' data-title-id='"+title.id+"' data-type='"+title.type+"' />";
        		}
        	}
            break;
        case "NO1_1_2":  //项目负责人
        	hresult = "<dd>"+ projectInfo.createUname +"</dd>";
            break;
        case "NO1_1_3":   //项目合伙人
        	if(projectInfo.hhrName){
        		hresult = "<dd>"+ projectInfo.hhrName +"</dd>";
        	}
            break;
        case "NO1_1_4":   //隶属事业部
        	hresult = "<dd>"+ projectInfo.projectCareerline +"</dd>";
            break;
        default:
            break;
    }
	
	return  "<div class=\"mb_24 base_half clearfix\">" + htitle + hresult + "</div>";
}





//12:单选带备注（input）
function type_12_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"' >"+title.name+"</dt>";
	
	var results = title.resultList;
	if(mark == 's'){
		var hresult = "<dd>未选择</dd>";
		
		if(results && results[0] && results[0].id){
			for(var i = 0;  i < results.length; i++ ){
				if(results[i].contentDescribe1){
					hresult = "<dd>"+results[0].contentDescribe1+"</dd>";
					break;
				}else if(results[i].valueName){
					hresult = "<dd>"+results[0].valueName+"</dd>";
				}
			}
		}
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}else{
		var li = "";
		var values = title.valueList;
		$.each(values,function(i,o){
			if(this.checked){
				li +=  "<li> <input type=\"radio\" value='"+this.id+"' name='"+title.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' checked=\"true\" />" + this.name + "</li>";
			}else
				li +=  "<li> <input type=\"radio\" value='"+this.id+"' name='"+title.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' />" + this.name + "</li>";
		});
		
		var r_value = '';
		if(results && results.length > 0){
			for(var i = 0;  i < results.length; i++ ){
				if(results[i].contentDescribe1){
					r_value = results[i].contentDescribe1;
					break;
				}
			}
		}
		var toadd_li = "<input type=\"text\" class=\"txt\" value='"+ r_value +"' " +
									"data-title-id='"+title.id+"' data-type='"+title.type+"' placeholder='"+title.placeholder+"' />";
		
		var eresult = 
			"<dd>" +
				"<ul class=\"h_radios clearfix\">" +
					li + toadd_li +
				"</ul>" +
			"</dd>";	
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + eresult + "</div>";
	}
}



//13:复选带备注（input）
function type_13_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"' >"+title.name+"</dt>";
	
	var results = title.resultList;
	
	if(mark == 's'){
		var hresult = "<dd>未选择</dd>";
		
		if(results && results[0] && results[0].id){
			hresult = "";
			$.each(results,function(i,o){
					if(this.valueName && this.valueName != '其他'){
						hresult +=  "<dd  class=\"border_dd\">"+this.valueName+"</dd>";
					}else if(this.contentDescribe1){
						hresult +=  "<dd class=\"border_dd\">"+this.contentDescribe1+"</dd>";
					}
			});
		}

		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}else{
		var li = "<li> ";
		var values = title.valueList;
		$.each(values,function(i,o){
			if(this.checked){
				li +=  "<li class=\"check_label active\" data-value='"+this.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"'>"  + this.name + "</li>";
			}else
				li +=  "<li class=\"check_label\" data-value='"+this.id+"' data-title-id='"+title.id+"' data-type='"+title.type+"' >"  + this.name + "</li>";
		});
		
		var r_value = '';
		if(results && results.length > 0){
			for(var i = 0;  i < results.length; i++ ){
				if(results[i].contentDescribe1){
					r_value = results[i].contentDescribe1;
					break;
				}
			}
		}
		var toadd_li = "<input type=\"text\" class=\"txt\" value='"+ r_value +"' " +
									"data-title-id='"+title.id+"' data-type='"+title.type+"' placeholder='"+title.placeholder+"' maxlength='40' />";
		
		var eresult = 
			"<dd>" +
				"<ul class=\"h_radios h_edit_checkbox pro_innovation clearfix\">" +
					li + toadd_li + "</li>"
				"</ul>" +
			"</dd>";	
			
		return  "<div class=\"mb_24 clearfix\">" + htitle  + eresult + "</div>";
	}
}




//14:单选
function type_14_html(title,mark){
	
	var htitle = "<dt data-tid='"+title.id+"' >"+title.name+"</dt>";
	if(mark == 's'){
		var hresult = "<dd>未选择</dd>";
		var results = title.resultList;
		if(results && results[0] && results[0].valueName){
			hresult = "<dd>"+results[0].valueName+"</dd>";
		}
		return  "<div class=\"mb_24 division_dd base_half clearfix\">" + htitle + hresult + "</div>";
	}else{
		var eresult = one_select_edit(title,'select');
		return  "<div class=\"mb_24 clearfix\">" + htitle + eresult + "</div>";
	}
}


