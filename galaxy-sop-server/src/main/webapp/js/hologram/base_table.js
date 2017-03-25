
/**
{
	tid : 
		{
			listdata_id : listdata
		}
}
 */
var table_Value = {};
function table_value(tid,listdatas){
	var id_listdata = {};
	for(var i = 0 ; i < listdatas.length; i++){
		var id = listdatas[i].id;
		id_listdata[id] = listdatas[i];
	}
	table_Value[tid] = id_listdata;
}
/**
{
	tid :[f1,f2……]
}
 */
var table_filed = {};

//删除 table-result

var table_delComArr = {};
var table_toedit_Value = {};
var table_tosave_Value = {};

function tosave_table_value(op_mark,tid,listdata){
	var id_listdata = {};
	var id = listdata.id;
	/*if(op_mark == "old"){
		if(table_Value[tid]){
			id_listdata = table_Value[tid];
		}
		id_listdata[id] = listdata;
		table_Value[tid] = id_listdata;
	}else */
	if(op_mark == "edit"){
		if(table_toedit_Value[tid]){
			id_listdata = table_toedit_Value[tid];
		}
		id_listdata[id] = listdata;
		table_toedit_Value[tid] = id_listdata;
	}else{
		if(table_tosave_Value[tid]){
			id_listdata = table_tosave_Value[tid];
		}
		var data = listdata;
		data.id = null;
		id_listdata[id] = data;
		table_tosave_Value[tid] = id_listdata;
	}
}

function getTableRowData(op_mark,tid,dataid){
	var id_listdata = null;
	if(op_mark == "old"){
		id_listdata = table_Value[tid];
	}else if(op_mark == "edit"){
		id_listdata = table_toedit_Value[tid];
	}else{
		id_listdata = table_tosave_Value[tid];
	}
	var data = id_listdata[dataid];
	return data;
}
function pushTableDelRowId(op_mark,tid,rid){
	if(op_mark == "new"){
		var toAdds = table_tosave_Value[tid];
		if(toAdds){
			for(var key in toAdds){
				if(key == rid){
					table_tosave_Value[tid][key] = null;
					break;
				}
			}
		}
	}else{
		if(op_mark == "edit"){
			var toEdits = table_toedit_Value[tid];
			if(toEdits){
				for(var key in toEdits){
					if(key == rid){
						table_toedit_Value[tid][key] = null;
						break;
					}
				}
			}
		}
		
		if(table_delComArr[tid]){
			table_delComArr[tid].push(rid);
		}else{
			var delComArr=[];
			delComArr.push(rid);
			table_delComArr[tid] = delComArr;
		}
	}
}


/**
obj_a: <a>点击元素
tid : 总标题id
rid ：一条结果集的id
 */
function del_NO5_7_1(obj_a,tid,rid){
	var tr = $(obj_a).closest('tr');
	var op_mark = $(tr).data("opt");
	
	layer.confirm('是否删除?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero) {
		pushTableDelRowId(op_mark,tid,rid);
		$(tr).remove();
		$(".layui-layer-close1").click();
	}, function(index) {
	});
}


function table_td_html(data,tid,tcode,rid){
	var td = "";
	var filed_sort = table_filed[tid];
	for(var i = 0 ; i < filed_sort.length; i++){
		var filed = filed_sort[i];
		td +='<td>'+data[filed]+'</td>';
	}
	var edit = "<a href='javascript:;' class=\"blue\" onclick=\"edit_"+tcode+"(this,'"+tid+"','"+rid+"')\" >编辑</a>";
	var del = "&nbsp;<a href='javascript:;' class=\"blue\" onclick=\"del_"+tcode+"(this,'"+tid+"','"+rid+"')\" >删除</a>";
	td += ('<td>' + edit + del + '</td>');
	
	return td;
}

/**
tid : 总标题id
tcode ：code
*/
function add_NO5_7_1(tid, tcode){
	
	var rid = mathRandomNum(6);
	var op_mark = "new";
	
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/infomation/toSaveCompeteTable", 
		data:"",
		okback:function(){
			$("#complete_title").text("添加");
			
			$("#compete-form input[name='projectId']").val(projectInfo.id);
			$("#compete-form input[name='titleId']").val(tid);
			$("#compete-form input[name='id']").val(rid);
			
			$("#compete #save-btn").click(function(){
				var data = $("#compete-form").serializeObject();
				var dataJ  = JSON.parse(data);
				tosave_table_value(op_mark,tid,dataJ);

				var tr = '<tr data-opt="new" data-result-id="'+rid+'" >';
				var td_html = table_td_html(dataJ,tid,tcode,rid);
				tr += (td_html + "</tr>");
				
				$("tbody[data-tbody-tid='"+tid+"']").append(tr);
				
				$("a[data-close='close']").click();
			});
		}	
	});
	return false;
}


/**
obj_a: <a>点击元素
tid : 总标题id
rid ：一条结果集的id
 */
function edit_NO5_7_1(obj_a,tid,rid){
	
	var tcode = $(obj_a).closest('tbody').data("tbodyTcode");
	
	var tr = $(obj_a).closest('tr');
	var op_mark = $(tr).data("opt");
	rid = $(tr).data("resultId");
		
	var entity = getTableRowData(op_mark,tid,rid);
	
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/infomation/toSaveCompeteTable", //toEditCompeteTable
		data:"",
		okback:function(){
			$("#complete_title").text("编辑");
			
			$("#compete-form input[name='projectId']").val(projectInfo.id);
			$("#compete-form input[name='titleId']").val(tid);
			$("#compete-form input[name='id']").val(rid);
			
			$("#compete-form input[name='field1']").val(entity.field1);
			$("#compete-form input[name='field2']").val(entity.field2);
			$("#compete-form input[name='field3']").val(entity.field3);
			$("#compete-form textarea[name='field4']").text(entity.field4);
			$("#compete-form input[name='field5'][value='"+entity.field5+"']").attr('checked','checked');
			
			$("#compete #save-btn").click(function(){
				
				var data = $("#compete-form").serializeObject();
				var dataJ  = JSON.parse(data);
				
				if(op_mark == "old"){
					op_mark = "edit";
					$(tr).data("opt","edit");
				}
				tosave_table_value(op_mark,tid,dataJ);
				
				var td_html = table_td_html(dataJ,tid,tcode,rid);
				$(tr).html(td_html);
				
				$("a[data-close='close']").click();
			});
		}	
	});
	return false;
}











/**
 * 截取、格式化
 */
function zixun_length_Format(value){  
	var old = value;
	var cut = zixun_cutStr(35,old,'...');
	var hasCut = getLength(value) > 35;
	//var info = "<span class=\"blue\"  >"+cut+"</span>";
	if(hasCut && hasCut == true){
		cut = "<span  title='"+old+"' >"+cut+"</span>";
	}
	return cut;
}


/**
 *  字符阶段处理
 *  theNum : 要显示的字节数（字符*2）
 *  theOldStr : 需要长度处理的字符
 *  theEndStr ：截断后附加的字符（ '...'）
 */
function zixun_cutStr(theNum,theOldStr,theEndStr){
	if( theOldStr == null || typeof(theOldStr) == 'undefined' || theOldStr ==''){
		return '-';
	}
	var leaveStr = "";
	var leng = getLength(theOldStr);
	if(theNum < leng){
		var cont = 0;
		for (var i = 0; i < theOldStr.length; i++) {
			if (theOldStr.charCodeAt(i) >= 0x4e00 && theOldStr.charCodeAt(i) <= 0x9fa5){ 
				cont += 2;
			}else {
				cont++;
			}
			if(cont > theNum){
				break;
			}
			leaveStr += theOldStr.charAt(i);
		}
		return leaveStr + theEndStr;
	}
	return theOldStr;
}

//检查录入数据 字节 长度
function getLength(val){
	if(val!=null){
		var len = 0;
		for (var i = 0; i < val.length; i++) {
			if (val.charCodeAt(i) >= 0x4e00 && val.charCodeAt(i) <= 0x9fa5){ 
				len += 2;
			}else {
				len++;
			}
		}
		return len;
	}
	
}



/**
 * 随机获取数字
 */
function mathRandomNum(sixe){
	var sizenum = 6;
	if(sixe){
		sizenum = sixe;
	}
	var num = "";
	for(var i = 0 ; i < sizenum; i++){
		num += Math.floor(Math.random()*10);
	}
	return num;
}








