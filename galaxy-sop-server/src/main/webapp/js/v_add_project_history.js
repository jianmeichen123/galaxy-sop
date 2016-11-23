function addFinanceHistory(){
	
	var flagId=$("#flagId").val();
	if(flagId==""){
		flagId=null;
	}
	var nowFormData = $("#add_Historyform").serializeObject();
	if(beforeSubmitById("add_Historyform")){
	
	     sendPostRequestByJsonStr(platformUrl.saveFinanceHistory+"/"+flagId, nowFormData, function(data){
			var re=data;
			$("#flagId").val(data.entity.id);
			pid = data.entity.id;
			$.popupOneClose();
			$.locksCreenOpen();
			formatterTable(re.entity.fh);
		});
	}
}

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
					"<td title='"+obj.financeFrom+"'>"+obj.financeFrom+"</td>"+
					"<td title='"+parseFloat(obj.financeAmount).toString()+"'>"+parseFloat(obj.financeAmount).toString()+"</td>"+
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


function updateFinance(uuid){
	var $self = $(this);
	var _url =platformUrl.updateFinanceHistory;
	var _name=$(".finance_edit").attr("data-name");
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			$("#popup_name").text(_name);
			getFinanceHistory(uuid);
		}//模版反回成功执行	
	});
	return false;
};
function deleteFinance(uuid){
	var nowFormData = $("#add_Historyform").serializeObject();
	layer.confirm(
			'确定要删除数据？',
			function(index){
				layer.close(index);
				var url = platformUrl.deleteFinanceHistory+"/"+uuid+"/"+$("#flagId").val();
				sendPostRequestByJsonStr(
					url,
					nowFormData,
					function(data){
						if(data.result.status=="OK")
						{
							layer.msg('删除成功');
							var re=data;
							$.popupOneClose();
							$.locksCreenOpen();
							formatterTable(re.entity.fh);
						}
						else
						{
							layer.msg(data.result.message);
						}
						
					}
				);
			}
		);
}
var historyUuid;
function updateFinanceHistory(){
	var nowFormData = $("#update_Historyform").serializeObject();
	if(beforeSubmitById("update_Historyform")){
	     sendPostRequestByJsonStr(platformUrl.updateSave+"/"+historyUuid+"/"+$("#flagId").val(), nowFormData, function(data){
			$("#flagId").val(data.entity.id);
			pid = data.entity.id;
			$.popupOneClose();
			$.locksCreenOpen();
			formatterTable(data.entity.fh);
		});
	}
}
function getFinanceHistory(uuid){
	sendPostRequest(platformUrl.getFinanceHistory+"/"+uuid+"/"+$("#flagId").val(),  function(data){
			setDataFinance(data.entity);
	});
}

function setDataFinance(data){
	historyUuid=data.uuid;
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
	createDictionaryOptionsBak(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus_select2", index);
	var financeUnit=$("#financeUnit option");
	for(var i=0;i<financeUnit.length;i++){
		if(data['financeUnit']==financeUnit[i].value){
			financeUnit[i].selected=true;
		}
	}
}
 

