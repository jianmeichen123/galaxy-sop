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
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus", 17);
	
	$("#createDate").val(new Date().format("yyyy-MM-dd"));
	createMenus(5);
	//获取TOKEN 用于验证表单提交
	sendPostRequest(platformUrl.getToken,function(data){
		TOKEN=data.TOKEN;
		return TOKEN;
	});
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
})

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
function add(){
	var val=$('input:radio[name="projectType"]:checked').val();
	if(val == null || typeof(val) == "undefined"){
		$("#projectTypeTip").css("display","block");
		return;
	}
	var nowFormData = $("#add_form").serializeObject();
	//if(beforeSubmit()){
	     sendPostRequestByJsonStr(platformUrl.addProject, $("#add_form").serializeObject(), function(data){
			var re=data;
			$("#id").val(data.entity.id);
		});
	//}
	     return true;
}
function calculationValuations(){
	var projectShareRatio = $("#formatShareRatio").val();
	var projectContribution = $("#formatContribution").val();
	if(projectShareRatio > 0 && projectContribution > 0){
		return projectContribution * (100/projectShareRatio);
	}
	return null;
}
