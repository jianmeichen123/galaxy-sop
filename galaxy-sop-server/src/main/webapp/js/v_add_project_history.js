function addFinanceHistory(){
	
	var flagId=$("#flagId").val();
	if(flagId==""){
		flagId=null;
	}
	var nowFormData = $("#add_Historyform").serializeObject();
	     sendPostRequestByJsonStr(platformUrl.saveFinanceHistory+"/"+flagId, nowFormData, function(data){
			var re=data;
			$("#flagId").val(data.entity.id);
			pid = data.entity.id;
			$.popupOneClose();
			$("body").css("overflow","auto")
			formatterTable(re.entity.fh);
		});
	     return true;
}
function formatterTable(entity){
	if(entity.length>=10){
		$("#add_history").css("display","none");
	}
	$("#financeHistory_table").children('tr').remove();
	var html;
	if(null==entity){
		html="<tr><td rowspan='7' align='center'>暂无数据<td></tr>";
		$("#financeHistory_table").append(html);
	}else{
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
						"<a class='finance_edit blue'   onclick=\"updateFinance('"+obj.uuid+"')\" href='javascript:void(0)'>编辑 &nbsp;</a>"+
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
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			getFinanceHistory(uuid);
		}//模版反回成功执行	
	});
	return false;
};
function deleteFinance(uuid){
	var nowFormData = $("#add_Historyform").serializeObject();
	 sendPostRequestByJsonStr(platformUrl.deleteFinanceHistory+"/"+uuid+"/"+$("#flagId").val(), nowFormData, function(data){
			var re=data;
			$.popupOneClose();
			$("body").css("overflow","auto")
			formatterTable(re.entity.fh);
	});
	
}
var historyUuid;
function updateFinanceHistory(){
	var nowFormData = $("#update_Historyform").serializeObject();
	     sendPostRequestByJsonStr(platformUrl.updateSave+"/"+historyUuid+"/"+$("#flagId").val(), nowFormData, function(data){
			$("#flagId").val(data.entity.id);
			pid = data.entity.id;
			$.popupOneClose();
			$("body").css("overflow","auto")
			formatterTable(data.entity.fh);
		});
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
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus", index);
	var financeUnit=$("#financeUnit option");
	for(var i=0;i<financeUnit.length;i++){
		if(data['financeUnit']==financeUnit[i].value){
			financeUnit[i].selected=true;
		}
	}
}
 

