searchFH();
var isEditable = '$(isEditable)';

/**
 * 加载该项目的融资历史
 */
function searchFH(){
	sendPostRequest(platformUrl.searchFH+"/"+pid,  function(data){
		formatterTable(data.entityList);
		if(data.entityList.length>=10){
            $("#add").css("display","none");
		}else{

            $("#add").css("display","block");
		}
	});
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
					"<td  title='"+obj.financeFrom+"'>"+obj.financeFrom+"</td>"+
					"<td>"+obj.financeAmount+"</td>"+
					"<td>"+data[obj.financeUnit]+"</td>"+
					"<td>"+obj.financeProportion+"</td>"+
					"<td>"+obj.financeStatusDs+"</td>";
			var html_1 =
					"<td>"+
						"<a class='finance_edit blue'   onclick=\"toUpdateOrSave('"+obj.id+"')\" href='javascript:void(0)' data-name='编辑融资历史'>编辑 &nbsp;</a>"+
						"<a class='finance_delete blue' onclick=\"deleteFinance('"+obj.id+"')\" href='javascript:void(0)'>删除</a>"+
					"</td>";
			
			var html_2 = "</tr>";
			if(isEditable &&( isEditable==false || isEditable=='false' )){
				html = html + html_2;
			}else{
				html = html + html_1 + html_2;
			}
			$("#financeHistory_table").append(html);
		}
		
	}
	
}
/**
 * 跳转至新增修改页面
 */
var fhId="";
function toUpdateOrSave(id){
	var $self = $(this);
	var _url =platformUrl.toUpateOrSaveFH;
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			console.log(id);
			if(null==id&& undefined==id){
				$("#popup_name").text("添加融资历史");
				/**
				 * 获取融资状态下拉项
				 * @version 2016-06-21
				 */
				createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus", 17);

			}else{
				$("#popup_name").text("编辑融资历史");
				getFinanceHistory(id);//修改页面数据加载
				fhId=id;
			}	
		}//模版反回成功执行	
	});
	return false;
};
function updateOrsave(){
	var _url="";
	if(fhId==""){
		_url=platformUrl.saveFH+"/"+pid;
	}else{
		_url=platformUrl.upateFHSave+"/"+fhId;
	}
	var nowFormData = $("#updateOrSave_HF").serializeObject();
	if(beforeSubmitById("updateOrSave_HF")){
	     sendPostRequestByJsonStr(_url, nowFormData, function(data){
			$.popupOneClose();
			$.locksCreenOpen();
			searchFH(pid);
		});
	}
}
function getFinanceHistory(id){
	sendPostRequest(platformUrl.getFH+"/"+id,  function(data){
			setDataFinance(data.entity);
	});
}
function deleteFinance(id){
	layer.confirm(
			'确定要删除数据？',
			function(index){
				layer.close(index);
				var url = platformUrl.deleteFH+"/"+id;
				sendPostRequest(
					url,
					function(data){
						if(data.result.status=="OK")
						{
							layer.msg('删除成功');
							$.popupOneClose();
							$.locksCreenOpen();
							searchFH();
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
 

