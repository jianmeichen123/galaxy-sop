$(function(){
	/**
	 * 查询事业线
	 * @version 2016-06-21
	 */
	createCareelineOptions(platformUrl.getCareerlineList,"industryOwn","select");
	/**
	 * 获取融资状态下拉项
	 * @version 2016-06-21
	 */
	createDictionaryOptionsBak(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus_select", 17);
	
	$("#createDate").val(new Date().format("yyyy-MM-dd"));
	createMenus(5);
	$("#formatShareRatio").blur(function(){
		var valuations = calculationValuations();
		if(valuations != null){
			$("#formatValuations").val(valuations.toFixed(2));
		}
	});
	$("#formatContribution").blur(function(){
		var valuations = calculationValuations();
		if(valuations != null){
			$("#formatValuations").val(valuations.toFixed(2));
		}
	});
	$('input:radio[name="projectType"]').click(function(){
		$(this).attr("checked","checked");
		$("#projectTypeTip").css("display","none");
	});
	
	$("[data-btn='add_history']").on("click",function(){ 
		var $self = $(this);
		var _url =platformUrl.addFinanceHistory;
		var _name= $self.attr("data-name");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$("#popup_name").html(_name);
			}//模版反回成功执行	
		});
		return false;
	});
	formatterTable(null);
})
//格式化
function shareOperatFormater(val,row,index)
{
   var e = '<span class="edit" data-id="'+row.id+'">编辑</span> ';  
   var d = '<span class="del" data-id="'+row.id+'">删除</span>';  
   return e+d;  
}
function setText(obj){
		if(obj=="set"){
			$("#faName").attr("style","display:inline-block;")
			$("#faName").removeAttr("allowNULL");
			$("#faName").focus();
			if($("#faName").val()=="请输入FA名称"){
				$("#faName").attr("style","color:#999;");
			}
		}else{
			$('.tip-yellowsimple').remove();
			$("#faName").val('');
			$("#faName").attr("allowNULL","yes");
			$("#faName").attr("style","display:none;");
		}
		
	}
var formData;
$('input[name="formatValuations"]').change(function(){
	if($(this).val() != '' && !new RegExp("^(([1-9][0-9]{0,10})|([0-9]{1,11}\.[1-9]{1,2})|([0-9]{1,11}\.[0][1-9]{1})|([0-9]{1,11}\.[1-9]{1}[0])|([1-9][0-9]{0,10}\.[0][0]))$").test($(this).val())){
		$("#valuations-tip").css("display","block");
	}else{
		$("#valuations-tip").css("display","none");
	}
});
$('input[name="formatValuations"]').blur(function(){
	if($(this).val() != '' && !new RegExp("^(([1-9][0-9]{0,10})|([0-9]{1,11}\.[1-9]{1,2})|([0-9]{1,11}\.[0][1-9]{1})|([0-9]{1,11}\.[1-9]{1}[0])|([1-9][0-9]{0,10}\.[0][0]))$").test($(this).val())){
		$("#valuations-tip").css("display","block");
	}else{
		$("#valuations-tip").css("display","none");
	}
});
function add(){
	$("#valuations-tip").css("display","none");
	var formatValuations = $('input[name="formatValuations"]').val();
	if(formatValuations != '' && !new RegExp("^(([1-9][0-9]{0,10})|([0-9]{1,11}\.[1-9]{1,2})|([0-9]{1,11}\.[0][1-9]{1})|([0-9]{1,11}\.[1-9]{1}[0])|([1-9][0-9]{0,10}\.[0][0]))$").test(formatValuations)){
		$("#valuations-tip").css("display","block");
		return;
	}
	var val=$('input:radio[name="projectType"]:checked').val();
	if(val == null || typeof(val) == "undefined"){
		$("#projectTypeTip").css("display","block");
		return;
	}
	var nowFormData = $("#add_form").serializeObject();
	if(beforeSubmitByIdNext("add_form")){
	     sendPostRequestByJsonStr(platformUrl.addProject+"/1", $("#add_form").serializeObject(), function(data){
			var re=data;
			$("#flagId").val(data.entity.id);
			pid = data.entity.id;
		});
	   return true;
	}
}
$("[data-btn='page0']").click(function(){
	var val=$('#radio_n').attr("checked");
	var val1=$('#radio_w').attr("checked");
	if(beforeSubmitByIdNext("add_form") && (val=="checked" || val1=="checked") ){
		$("[data-btn='page0'] span[data-btn='next']").removeClass("disabled");
		  return true;
		   	}
})
function calculationValuations(){
	var projectShareRatio = $("#formatShareRatio").val();
	var projectContribution = $("#formatContribution").val();
	if(projectShareRatio > 0 && projectContribution > 0){
		return projectContribution * (100/projectShareRatio);
	}
	return null;
}
