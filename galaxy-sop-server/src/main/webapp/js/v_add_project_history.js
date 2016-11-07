function addFinanceHistory(){
	
	var flagId=$("#flagId").val();
	if(flagId==""){
		flagId=null;
	}
	var nowFormData = $("#add_Historyform").serializeObject();
	     sendPostRequestByJsonStr(platformUrl.saveFinanceHistory+"/"+flagId, nowFormData, function(data){
			var re=data;
			$("#flagId").val(data.entity.id);
			$.popupOneClose();
			$("body").css("overflow","auto")
			formatterTable(re.entity.fh);
		});
	     return true;
}
function formatterTable(entity){
	var html;
	if(null==entity){
		html="<tr><td rowspan='7' align='center'>暂无数据<td></tr>";
	}else{
		for(var i=0;i<entity.length;i++){
			var obj=entity[i];
			html=
				"<tr>"+
					"<td>"+obj.financeDate+"</td>"+
					"<td>"+obj.financeFrom+"</td>"+
					"<td>"+obj.financeAmount+"</td>"+
					"<td>"+obj.financeUnit+"</td>"+
					"<td>"+obj.financeProportion+"</td>"+
					"<td>"+obj.financeStatus+"</td>"+
					"<td>"+
						"<a class='finance_edit blue'   onclick='deleteFinance("+obj.uuid+")' href='javascript:void(0)'>编辑 &nbsp;</a>"+
						"<a class='finance_delete blue' onclick='updateFinance("+obj.uuid+")' href='javascript:void(0)'>删除</a>"+
					"</td>"+
			   "</tr>";
		}
		
	}
	
	$("#financeHistory_table").append(html);
	
}
function deleteFinance(uuid){
	 sendPostRequestByJsonStr(platformUrl.deleteFinanceHistory+"/"+uuid+"/"+$("#flagId").val(), nowFormData, function(data){
			var re=data;
			formatterTable(re.entity.fh);
		});
	
}
function updateFinance(uuid){
	
	
}