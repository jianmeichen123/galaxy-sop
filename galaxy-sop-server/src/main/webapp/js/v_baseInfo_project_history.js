/**
 * 加载该项目的融资历史
 */
sendPostRequest(platformUrl.searchFH+"/"+pid,  function(data){
	console.log(pid);
	formatterTable(data.entityList);
});
function formatterTable(entity){
	$("#financeHistory_table").children('tr').remove();
	var html;
	if(null==entity||""==entity){
		html="<tr><td colspan='7' style='text-align:center !important;color:#bbb;border:0;line-height:32px !important' class='noinfo no_info01'><label class='no_info_icon_xhhl'>没有找到匹配的记录</label></td></tr>";
		$("#financeHistory_table").append(html);
	}else{
		if(entity.length>=10){
			$("#add_history").css("display","none");
		}else{
			$("#add_history").css("display","block");
		}
		 var data={
					"0":"人民币",
					"1":"美元", 
				    "2":"英镑" ,
				    "3":"欧元" 
				 };
		for(var i=0;i<entity.length;i++){
			var obj=entity[i];
			html=
				"<tr>"+
					"<td>"+obj.financeDateStr+"</td>"+
					"<td>"+obj.financeFrom+"</td>"+
					"<td>"+obj.financeAmount+"</td>"+
					"<td>"+data[obj.financeUnit]+"</td>"+
					"<td>"+obj.financeProportion+"</td>"+
					"<td>"+obj.financeStatusDs+"</td>"+
					"<td>"+
						"<a class='finance_edit blue'   onclick=\"updateFinance('"+obj.uuid+"')\" href='javascript:void(0)' data-name='编辑融资历史'>编辑 &nbsp;</a>"+
						"<a class='finance_delete blue' onclick=\"deleteFinance('"+obj.uuid+"')\" href='javascript:void(0)'>删除</a>"+
					"</td>"+
			   "</tr>";
			$("#financeHistory_table").append(html);
		}
		
	}
	
}
/**
 * 跳转至新增修改页面
 */
function toUpdateOrSave(id){
	var $self = $(this);
	var _url =platformUrl.toUpateOrSaveFH;
	var _name=$(".finance_edit").attr("data-name");
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			$("#popup_name").text(_name);
			if(null!=id&&"underfined"!=id){//修改页面
				getFinanceHistory(id);//修改页面数据加载
			}
		}//模版反回成功执行	
	});
	return false;
};
function updateOrsave(){
	var nowFormData = $("#updateOrSave_HF").serializeObject();
	if(beforeSubmitById("updateOrSave_HF")){
	     sendPostRequestByJsonStr(platformUrl.saveFH+"/"+pid, nowFormData, function(data){
			var re=data;
			$("#flagId").val(data.entity.id);
			pid = data.entity.id;
			$.popupOneClose();
			$("body").css("overflow","auto")
			formatterTable(re.entity.fh);
		});
	}
}
function getFinanceHistory(uuid){
	sendPostRequest(platformUrl.getFH+"/"+pid,  function(data){
			setDataFinance(data.entity);
	});
}
var historyid;
function updateFinanceHistory(){
	var nowFormData = $("#update_Historyform").serializeObject();
	if(beforeSubmitById("add_Historyform")){
	     sendPostRequestByJsonStr(platformUrl.upateFHSave+"/"+pid, nowFormData, function(data){
			pid = data.entity.id;
			$.popupOneClose();
			$("body").css("overflow","auto")
			formatterTable(data.entity.fh);
		});
	}
}
function deleteFinance(id){
	var nowFormData = $("#add_Historyform").serializeObject();
	 sendPostRequestByJsonStr(platformUrl.deleteFH+"/"+id, nowFormData, function(data){
			var re=data;
			$.popupOneClose();
			$("body").css("overflow","auto")
			formatterTable(re.entity.fh);
	});
	
}

function setDataFinance(data){
	historyUuid=data.id;
	$("#financeDetail dd input")
	.each(function(){
		var self = $(this);
		if(self.attr('id') != 'undefined')
		{
		   var id = self.attr('id');
		   var formatter = self.data('formatter');
		   var text = data[id];
		   self.val(text);
		}
	});
	var index=data["financeStatus"].split(":")[1];
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus", index);
	var financeUnit=$("#financeUnit option");
	for(var i=0;i<financeUnit.length;i++){
		if(data['financeUnit']==financeUnit[i].value){
			financeUnit[i].selected=true;
		}
	}
}
 

