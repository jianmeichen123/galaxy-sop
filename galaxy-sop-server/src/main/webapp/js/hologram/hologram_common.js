function editOpen(){
	$(".h_edit_btn").click(function(){
		/*var part=$(this).parent().parent(".h_look").parent(".radius").attr("id").split('_');
		var edit_id='h_edit_'+part[1];
		$(this).attr("id",edit_id)*/
	    $(this).parent().parent(".h_look").hide();
	    $(this).parent().parent(".h_look").siblings(".h_edit").show();
	    $(this).parent().parent().parent(".h").css("background","#fbfbfb")
	  })
	/*取消*/
	$('span[data-on="h_cancel"]').click(function(){
	  $(this).parent().parent(".h_edit").hide();
	  $(this).parent().parent(".h_edit").siblings(".h_look").show();
	  $(this).parent().parent().parent(".h").css("background","#fff");
	})
}
/*多选标签*/
$("div").delegate(".check_label","click",function(event){
	  $(this).toggleClass('active');
	  event.stopPropagation();
});
/*文本域字数统计*/
function countChar(textareaName,spanName,maxLimit){
	//var maxLimit=10;
	var textArea=document.getElementById(textareaName);
	var spanCount=document.getElementById(spanName);
	if (textArea.value.length>maxLimit){
		spanCount.innerHTML='0';
		textArea.value = textArea.value.substring(0, maxLimit);
	}else{
		spanCount.innerHTML =maxLimit-textArea.value.length;
	}
}

function tabInfoChange(index){
	$("#tab-content").remove();
	$("#tab-content1").remove();
	$(".tip-yellowsimple").remove();
		switch(index){
	case '0':initBaseInfo(); break;  //标签0:基本信息
	case '1':initProjectInfo(); break;  //标签1:项目
	case '2': initTeamInfo(); break;  //标签2: 团队
	case '3': initOperateInfo();   break;  //标签3:运营数据
	case '4': initCompeteInfo();   break;  //标签4:竞争
	case '5': initPlanInfo();   break;  //标签5:战略及策略
	case '6': initFinanceInfo();   break;  //标签6:财务
	case '7': initJusticeInfo();   break;  //标签7:法务
	case '8': initValuationInfo();   break;  //标签8:融资及估值
	default: return false;
}
}
//基本信息
	function initBaseInfo(){
		$.getTabHtmlInfo({
			url : platformUrl.toBaseInfo
		}); 
	   }
	   //项目
		function initProjectInfo(){
		 $.getTabHtmlInfo({
				url : platformUrl.toProjectInfo 
			}); 
		}
		 //团队
		function initTeamInfo(){
			$.getTabHtmlInfo({
				url : platformUrl.toTeamInfo 
			});
		}
		 //运营数据
		function initOperateInfo(){
			$.getTabHtml({
				url : platformUrl.toOperateInfo 
			});
		}
		//竞争
		function initCompeteInfo(){
			$.getTabHtml({
				url : platformUrl.toCompeteInfo 
			});
		}
		//战略以及策略
		function initPlanInfo(){
			$.getTabHtml({
				url : platformUrl.toPlanInfo 
			});
		}
		//财务
		function initFinanceInfo(){
			$.getTabHtml({
				url : platformUrl.toFinanceInfo 
			});
		}
		//法务
		function initJusticeInfo(){
			$.getTabHtml({
				url : platformUrl.toJusticeInfo 
			});
		}
		//融资及估值
		function initValuationInfo(){
			$.getTabHtml({
				url : platformUrl.toValuationInfo 
			});
		}


	$.fn.showResults = function(readonly){
		var sec = $(this);
		var pid = $(this).data('sectionId');
        var id = $(this).attr('id');
		if(id == "a_NO3_1"){
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
                    buildMemberTable(sec,data);
                }
		     })
		}else{
		sendGetRequest(platformUrl.getTitleResults + pid+'/'+projectInfo.id, null,
        				function(data) {
        			var result = data.result.status;
        			if (result == 'OK')
        			{
        				var entityList = data.entityList;
        				if(entityList && entityList.length >0)
        				{
        					$.each(entityList,function(){
        						var title = this;
        						buildResults(sec,title,readonly);
        						buildTable(sec,title);
        						buildfinxedTable(sec,title,readonly);
        						dtWidth();
        					});
        				}
        			}
        		})
		}
};
function dtWidth(){
	//获取类型为3、13时题干的宽度
	$.each($(".checked_div"),function(){
		var dt_w=$(this).siblings("dt").width();
		$(this).css("margin-left",dt_w+10);
	})
}
function buildResults(sec,title,readonly)
{
	//普通字段
	if(null!=title.resultList&&title.resultList.length>0)
	{
		if(title.type == 1)
		{
			if(readonly == true)
			{
				$(".field[data-title-id='"+title.id+"']").text(title.resultList[0].contentDescribe1==undefined ?"未填写":title.resultList[0].contentDescribe1);
			}
			else
			{
				$("input[data-title-id='"+title.id+"']").val(title.resultList[0].contentDescribe1);
			}
		}
		if(title.type == 2)
		{
			if(readonly == true)
			{
				$(".field[data-title-id='"+title.id+"']").text(title.resultList[0].valueName);
			}
			else
			{
				$("input[data-title-id='"+title.id+"'][value='"+title.resultList[0].contentChoose+"']").attr('checked','true');
			}
		}
		else if(title.type == 3)
		{
			$.each(title.resultList,function(i,n)
			{
				if(readonly == true)
				{
					$("dd[data-id='"+n.contentChoose+"']").text(n.valueName).show();
				}
				else
				{
					$("dt[data-id='"+ title.id +"']").next('dd').find("li[data-id='"+ n.contentChoose +"']").addClass('active');
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
		/*else if(title.type == 5)
		{
			$.each(title.resultList,function(i,n){
				if (n.contentDescribe1){
					if(readonly == true){
						$(".field-remark[data-id='"+ title.id +"']").text(n.contentDescribe1);
					}else{
						$("textarea[class='textarea_h'][data-title-id='"+title.id+"']").val(n.contentDescribe1);
					}
				}
				if(n.contentChoose){
					if(readonly == true){
						$(".field[data-id='"+ title.id +"']").text(n.valueName);
					}else{
						$("dt[data-title-id='"+ title.id +"']").next('dd').find("input[type='radio'][data-id='"+ n.contentChoose +"']").attr('checked','true');
					}
				}
			});
		}*/
		else if(title.type == 12)
		{
			var dd = $("dt[data-type='12'][data-title-id='"+ title.id +"']").siblings('dd').eq(0);
			var n = title.resultList[0];

			if (n.contentDescribe1)
			{
				if(readonly == true)
				{
					dd.text(n.contentDescribe1);
				}
				else
				{
					$("input[data-id='"+title.id+"']").val(n.contentDescribe1) ;
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
					$("dt[data-title-id='"+ title.id +"']").next('dd').find("input[type='radio'][data-id='"+ n.contentChoose +"']").attr('checked','true');
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
		/*else if(title.type == 13) {
			$.each(title.resultList,function(i,n){
				if (n.contentDescribe1){
					if(readonly == true){
						$("dd[class='field'][data-title-id='"+ title.id +"']").text(n.contentDescribe1);
					}else{
						$("input[data-id='"+title.id+"']").val(n.contentDescribe1) ;
					}
				}
				if(n.contentChoose){
					if(readonly == true){
						$(".field[data-id='"+ title.id +"']").text(n.valueName);
					}else{
						$("dt[data-title-id='"+ title.id +"']").next('dd').find("input[type='radio'][data-id='"+ n.contentChoose +"']").attr('checked','true');
					}
				}
			});
		}
		else if(title.type == 14)
		{
			if(readonly == true)
			{
				$("dd[class='field'][data-title-id='"+ title.id +"']").text(title.resultList[0].valueName==undefined ?"未选择":title.resultList[0].valueName);
			}
			else
			{
				$("select[data-id='"+title.id+"']").val(title.resultList[0].contentChoose) ;
			}
		}*/
		else if(title.type == 15)
		{
			if(readonly == true)
			{
				var dds = $("dd[data-title-id='" + title.id + "']");
				dds.eq(0).html(title.resultList[0].contentDescribe1==undefined ?"未填写":title.resultList[0].contentDescribe1);
				dds.eq(1).html(title.resultList[0].contentDescribe2==undefined ?"未填写":title.resultList[0].contentDescribe2);
			}
			else
			{
				var str=title.resultList[0].contentDescribe1;
				if(str){
					str=str.replace(/<br\/>/g,'\n');
					str=str.replace(/<br>/g,'\n');
					str=str.replace(/&nbsp;/g," ");
				}
				var str2=title.resultList[0].contentDescribe2;
				if(str2){
					str2=str2.replace(/<br\/>/g,'\n');
					str2=str2.replace(/<br>/g,'\n');
					str2=str2.replace(/&nbsp;/g," ");
				}
				var textareas = $("textarea[data-title-id='" + title.id + "'][data-type='15']");
				textareas.eq(0).html(str);
				textareas.eq(1).html(str2);
			}
		}
		else if(title.type == 8)
		{
			if(readonly == true)
			{
				$(".field[data-title-id='"+title.id+"']").html(title.resultList[0].contentDescribe1==undefined ?"未填写":title.resultList[0].contentDescribe1);
			}
			else
			{
				var str=title.resultList[0].contentDescribe1;
				if(str){
					str=str.replace(/<br\/>/g,'\n');
					str=str.replace(/<br>/g,'\n');
					str=str.replace(/&nbsp;/g," ");
				}
				$("textarea[data-title-id='"+title.id+"']").val(str);
			}
		}
		else if(title.type == 14)
		{
			if(readonly == true)
			{
				$("dd[class='field'][data-title-id='"+ title.id +"']").text(title.resultList[0].valueName==undefined ?"未选择":title.resultList[0].valueName);
				//$(".field[data-title-id='"+title.id+"']").text(title.resultList[0].valueName);
			}
			else
			{
				$('select[data-id="' + title.id + '"]').val( title.resultList[0].contentChoose );
			}
		}
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
		}
	}
}
function buildMemberTable(sec,title){
        //列表Header
    	if(title.tableHeader)
    	{
    		var header = title.tableHeader;
    		var tables = $("table[data-title-id='"+header.titleId+"']");
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
    	if(title.dataList)
    	{
    	    var list = title.dataList.reverse();
    		$.each(list,function(){
    			var row = this;
    			var tables = $("table[data-title-id='"+row.titleId+"']");

    			$.each(tables,function(){
    				var table = $(this);
    				var headerList = table.find('tbody').find('tr:eq(0)').find("th[data-field-name!='opt']");
    				var tr = buildMemberRow(headerList,row,table.hasClass('editable'));
    				table.append(tr);
    			});
    		});
    	}
}
function buildMemberRow(headerList,row,showOpts)
{
	var tr=$("<tr data-row-id='"+row.id+"'></tr>");
	tr.data("obj",row);
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
                tr.append('<td data-field-name="'+key+'">'+row[key]+'</td>');
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
function buildTable(sec,title)
{
	//列表Header
	if(title.tableHeader)
	{
		var header = title.tableHeader;
		var tables = $("table[data-title-id='"+header.titleId+"']");
		$.each(tables,function(){
			var table = $(this);
			table.attr('data-code',header.code);
			table.empty();
			var tr="<tr>";
			for(var key in header)
			{
				if(key.indexOf('field')>-1)
				{
					tr +='<th data-field-name="'+key+'">'+header[key]+'</th>';
				}
			}
			var editable = table.hasClass('editable');
			if(editable == true)
			{
				tr +='<th data-field-name="opt">操作</th>';
			}
			tr+="</tr>";
			table.append(tr);
		});
	}
	//列表Row
	if(title.dataList)
	{
		$.each(title.dataList,function(){
			var row = this;
			var tables = $("table[data-title-id='"+row.titleId+"']");
			$.each(tables,function(){
				var table = $(this);
				var tr = buildRow(row,table.hasClass('editable'));
				table.append(tr);
			});
		});
	}
}
function buildRow(row,showOpts)
{
	var tr=$("<tr data-row-id='"+row.id+"'></tr>");
	for(var key in row)
	{
		//设置data
		tr.data(key,row[key]);
		if(key.indexOf('field')>-1)
		{
			tr.append('<td data-field-name="'+key+'">'+row[key]+'</td>');
		}
	}
	if(showOpts == true)
	{
		var td = $('<td data-field-name="opt"></td>');
		td.append('<span class="blue" data-btn="btn" onclick="editRow(this)">编辑</span>');
		td.append('<span class="blue" data-btn="btn" onclick="delRow(this)">删除</span>');
		tr.append(td);
	}
	return tr;

}
function buildfinxedTable(sec,title,readonly){
	if(null!=title.fixedTableList&&title.fixedTableList.length>0){
	  if(readonly == true)
		{
		  $.each(title.fixedTableList,function(i,n){
				$("td[data-format='"+n.rowNo+"_"+n.colNo+"']").text(n.valueName);
			});
		}else
		{
			$.each(title.fixedTableList,function(i,n){
				$("td[data-flag='"+n.colNo+"']").find("input[data-row='row"+n.rowNo+"'][value="+n.content+"]").attr('checked','true');
				
			});
		}
	}
}
function setDate(pid, readonly) {
	sendGetRequest(platformUrl.getTitleResults + pid + '/' + projectInfo.id,
			null, function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entityList = data.entityList;
					if (entityList && entityList.length > 0) {
						$.each(entityList, function() {
							var title = this;
							buildResults(null, title, readonly);
							buildfinxedTable(null, title, readonly);
						});
					}
				}
			})

}
/*文件刷新*/
function picData(pid){
	var fileids = $(".mglook");
	var infoFileids = "";
	var data={};
	for(var i = 0;i < fileids.length; i++) {
		  fileids.html("未添加");
		  infoFileids += ","+fileids.eq(i).attr("id").replace("look-","");
	}
	data.projectId = pid;
	data.infoFileids = infoFileids;
	sendPostRequestByJsonObjNoCache(
				Constants.sopEndpointURL+'galaxy/informationFile/getFileByProjectByType' , 
				data,
				function(data) {
					var result = data.result.status;
					if (result == 'OK') {
						var files = data.entity.commonFileList;
						if(files != null && files != ""){
							$.each(files, function (key, value) { 
								var fl = value;
								var html="";
								for(var i = 0;i < fl.length; i++){
									html +='<img src="'+fl[i].fileUrl+'" alt="">';
								}
								$('#'+"look-"+key).html(html);
								
							});
						}
						
					} else {

					}
	});
}
/*文本域字数统计*/
function countChar(textareaName,spanName,maxLimit){
	//var maxLimit=10;
	var textArea=document.getElementById(textareaName);
	var spanCount=document.getElementById(spanName);
	if (textArea.value.length>maxLimit){
		spanCount.innerHTML='0';
		textArea.value = textArea.value.substring(0, maxLimit);
	}else{
		spanCount.innerHTML =maxLimit-textArea.value.length;
	}
}
/**  
 * 文本框根据输入内容自适应高度  
 * @param                {HTMLElement}        输入框元素  
 * @param                {Number}                设置光标与输入框保持的距离(默认0)  
 * @param                {Number}                设置最大高度(可选)  
 */  
var autoTextarea = function(elem, extra, maxHeight) {  
    extra = extra || 0;  
    var elem=document.getElementById(elem);
    var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,  
        isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),  
        addEvent = function(type, callback) {  
            elem.addEventListener ?  
                elem.addEventListener(type, callback, false) :  
                elem.attachEvent('on' + type, callback);  
        },  
        getStyle = elem.currentStyle ? function(name) {  
            var val = elem.currentStyle[name];  
            if (name === 'height' && val.search(/px/i) !== 1) {  
                var rect = elem.getBoundingClientRect();  
                return rect.bottom - rect.top -  
                    parseFloat(getStyle('paddingTop')) -  
                    parseFloat(getStyle('paddingBottom')) + 'px';  
            };  
            return val;  
        } : function(name) {  
            return getComputedStyle(elem, null)[name];  
        },  
        minHeight = parseFloat(getStyle('height'));  
    elem.style.resize = 'none';  
    var change = function() {  
        var scrollTop, height,  
            padding = 0,  
            style = elem.style;  
        if (elem._length === elem.value.length) return;  
        elem._length = elem.value.length;  
        if (!isFirefox && !isOpera) {  
            padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));  
        };  
        scrollTop = document.body.scrollTop || document.documentElement.scrollTop;  
        elem.style.height = minHeight + 'px';  
        if (elem.scrollHeight > minHeight) {  
            if (maxHeight && elem.scrollHeight > maxHeight) {  
                height = maxHeight+10;  
                style.overflowY = 'auto';  
            } else {  
                height = elem.scrollHeight+10;  
                style.overflowY = 'hidden';  
            };  
            style.height = height + extra + 'px';  
            scrollTop += parseInt(style.height) - elem.currHeight;
//            输入时不让body跳动
            
            document.body.scrollTop = scrollTop;  
            document.documentElement.scrollTop = scrollTop;  
            elem.currHeight = parseInt(style.height);  
        };  
    };  
    addEvent('propertychange', change);  
    addEvent('input', change);  
    addEvent('focus', change);  
    change();  
};
/* 通用验证 */
function validate(){
	 var inputs=$("input[type='text']");
	 for(var i=0;i<inputs.length;i++){
		 	var inputValRule=inputs.eq(i).attr("data-valrule");
			var inputValRuleMark=inputs.eq(i).attr("data-valrulemark");
			if(inputValRule=="2" && inputValRuleMark=="3"){
				var validate={
						//"regString":"^[0-9]{1,3}$",
						"data-rule-vinputValRule_2":"true",
						//"required":"required",
						"name":i,
						"data-msg-vinputValRule_2":"<font color=red>*</font>支持0～999的整数"			
				}
				inputs.eq(i).attr(validate);
			}else if(inputValRule=="2" && inputValRuleMark=="2"){
				var validate={
						//"regString":"^[0-9]{1,3}$",
						"data-rule-vinputValRule_1":"true",
						//"required":"required",
						"name":i,
						"data-msg-vinputValRule_1":"<font color=red>*</font>支持0～99的整数"			
				}
				inputs.eq(i).attr(validate);
			}else if(inputValRule=="3" && inputValRuleMark=="3"){
				var validate={
						//"regString":"^[0-9]{1,3}$",
						"data-rule-vinputValRule_3":"true",
						//"required":"required",
						"name":i,
						"data-msg-vinputValRule_3":"<font color=red>*</font>支持0～100的整数和两位小数"			
				}
				inputs.eq(i).attr(validate);
			}else if(inputValRuleMark=="10,2"){
				var validate={
						"data-rule-verify_102":"true",
						"name":i,
						//"required":"required",
						//"regString":"^(([1-9][0-9]{0,9})|([0-9]{1,10}\.[1-9]{1,2})|([0-9]{1,10}\.[0][1-9]{1})|([0-9]{1,10}\.[1-9]{1}[0])|([1-9][0-9]{0,9}\.[0][0]))$",
						"data-msg-verify_102":"<font color=red>*</font>支持0～9999999999的整数和两位小数"			
				}
				inputs.eq(i).attr(validate);
			}else if(inputValRuleMark=="3,2"){
				var validate={
						"data-rule-verify_32":"true",
						//"required":"required",	
						"name":i,
						//"msg":"^(?:[1-9][0-9]?|1[01][0-9]|100)$",
						"data-msg-verify_32":"<font color=red>*</font>支持0～100的整数和两位小数"			
				}
				inputs.eq(i).attr(validate);
			}else if(inputValRuleMark=="5,2"){
				var validate={
						"data-rule-verify_52":"true",
						//"required":"required",
						"name":i,
						//"regString":"^(([1-9][0-9]{0,4})|([0-9]{1,5}\.[1-9]{1,2})|([0-9]{1,5}\.[0][1-9]{1})|([0-9]{1,5}\.[1-9]{1}[0])|([1-9][0-9]{0,4}\.[0][0]))$",
						"data-msg-verify_52":"<font color=red>*</font>支持0～99999的整数和两位小数"			
				}
				inputs.eq(i).attr(validate);
			}else if(inputValRule=="4"){
				var validate={
						"data-rule-vinputValRule_4":"true",
						//"required":"required",
						"name":i,
						//"regString":"^(([1-9][0-9]{0,4})|([0-9]{1,5}\.[1-9]{1,2})|([0-9]{1,5}\.[0][1-9]{1})|([0-9]{1,5}\.[1-9]{1}[0])|([1-9][0-9]{0,4}\.[0][0]))$",
						"data-msg-vinputValRule_4":"<font color=red>*</font>只允许输入数字0~168整数和一位小数"			
				}
				inputs.eq(i).attr(validate);
			}else if(inputValRule=="5"){
				var add_time =i+"_time";
				var validate={
						"class":"time",	
						"data-time":add_time
				}
				inputs.eq(i).attr(validate);
				$("[data-time="+add_time+"]").datepicker({
					language:  'zh-CN',
			        format: 'yyyy-mm',
			        autoclose: true,
			        todayBtn: false,
			        startView: 'year',
			        minView:'year',
	                minViewMode: 1,
	                maxView:'decade',
	                todayHighlight: false
		    	});
			}
	 }
	
	
}
//配置错误提示的节点，默认为label，这里配置成 span （errorElement:'span'）
$.validator.setDefaults({
	errorElement:'span'
});
//inputValRuleMark=="10,2"
jQuery.validator.addMethod("verify_102", function(value, element) {   
	//var verify_102 = /^(0|1.0|1.00|0.0|0.00|([1-9][0-9]{0,9})|([0-9]{1,10}\.[1-9]{1,2})|([0-9]{1,10}\.[0][1-9]{1})|([0-9]{1,10}\.[1-9]{1}[0])|([1-9][0-9]{0,9}\.[0][0])|([1-9][0-9]{0,9}\.[0]))$/;
	//var verify_102 = /^([0]{1}(\.\d{1,2})|0|[1-9]{1}(\.\d{1,2})|[1-9]|[1-9]{1}[0-9]{2,9}(\.\d{1,2})|^[1-9]\d{1,9}|[1-9]{1}[0-9](\.\d{1,2}))$/;
	var verify_102 = /^(\d(\.\d{1,2})?|([1-9][0-9]{1,9})?(\.\d{1,2})?)$/;
	return this.optional(element) || (verify_102.test(value));
}, "不能超过9999999999");
//vinputValRule=="2"
jQuery.validator.addMethod("vinputValRule_2", function(value, element) {   
	var vinputValRule_2 = /^([1-9]{1}[0-9]{0,2})$/;;
	return this.optional(element) || (vinputValRule_2.test(value));
}, "不能超过100"); 
jQuery.validator.addMethod("vinputValRule_1", function(value, element) {   
	var vinputValRule_1 = /^([1-9]{1}[0-9]{0,1}|0)$/;;
	return this.optional(element) || (vinputValRule_1.test(value));
}, "不能超过100")
//vinputValRule=="3"
jQuery.validator.addMethod("vinputValRule_3", function(value, element) {   
	//var verify_3 = /^[0-9]{1,3}$/;
	//var vinputValRule_3 = /^(?:[1-9][0-9]?|1[01][0-9]|100)$/;
	var vinputValRule_3 = /^(([1-9](?:\d{0,1}\.\d{0,2}))|([0](?:\d{0}\.\d{0,2}))|([1-9](\d{0,1}))|100|100.0|100.00|0)$/;
	return this.optional(element) || (vinputValRule_3.test(value));
}, "不能超过100"); 
//inputValRuleMark=="3,2"
jQuery.validator.addMethod("verify_32", function(value, element) {   
	var verify_32 = /^(\d|[1-9]\d?(\.\d{1,2})?|0\.\d{1,2}|100|100\.0{1,2})$/;
	return this.optional(element) || (verify_32.test(value));
}, "不能超过100"); 
//inputValRuleMark=="5,2"
jQuery.validator.addMethod("verify_52", function(value, element) {   
	//var verify_52 = /^(([1-9][0-9]{0,4})|([0-9]{1,5}\.[1-9]{1,2})|([0-9]{1,5}\.[0][1-9]{1})|([0-9]{1,5}\.[1-9]{1}[0])|([1-9][0-9]{0,4}\.[0][0]))$/;
	//var verify_52 =  /^(([1-9][0-9]{0,4})|([0]\.[1-9][0-9])|([0]\.[0-9][1-9])|([1-9][0-9]{1,3}\.[0-9]{1,2})|([1-9][0-9]{0,4}\.[0]{1,2}))$/;
	var verify_52 = /^(\d(\.\d{1,2})?|([1-9][0-9]{1,4})?(\.\d{1,2})?)$/;
	return this.optional(element) || (verify_52.test(value));
}, "不能超过99999"); 
//inputValRule=="4"
jQuery.validator.addMethod("vinputValRule_4", function(value, element) { 
	var vinputValRule_4 = /^((\d|[1-9]\d?|[1][0-6][0-7])(\.\d{1})?|0\.\d|168|168.0)$/;
	return this.optional(element) || (vinputValRule_4.test(value));
}, "不能超过168"); 
//百分数
jQuery.validator.addMethod("percentage", function(value, element) {   
	var percentage = /^\d+(\.\d{2})?$/;
	return this.optional(element) || (percentage.test(value) && value>0 && value <=100);
}, "只能是0～100的整数和两位小数"); 
//更新时间
function updateInforTime(projectId,type){
	var test={};
	test.projectId = projectId;
	test.reflect = type;
	sendPostRequestByJsonObj(
				Constants.sopEndpointURL+'/galaxy/InformationOperationTime/updateOperateTime' , 
				test,
				null);
}
//检查table表格是否有数据
function check_table(){
	$.each($('table.editable'),function(){
		if($(this).find('tr').length<=1){
			$(this).hide();
		}
		else{
			$(this).show();
		}
	})
}	
//检查是否10条tr
function check_table_tr_edit(){
	$.each($("table.editable"),function(){
		var code = $(this).data('code');
		var limit = getTableRowLimit(code);
		var trs=$(this).find("tr").length-1;
		if(trs>=limit){
			$(this).siblings(".bluebtn").hide();
		}else{
			$(this).siblings(".bluebtn").show();
		}
	})
}
function getTableRowLimit(code)
{
	return 10;
}
	
	
	
	
	
