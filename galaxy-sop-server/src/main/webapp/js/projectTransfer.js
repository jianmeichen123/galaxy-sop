function doSumbit(projectId){
	$('input[name="projectId"]').val(projectId);
	/**
	 * 查询事业线
	 * @version 2016-08-03
	 */
	createCareelineOptions(platformUrl.getCareerlineList,"afterDepartmentId",1);
	/**
	 * 根据事业线查询相应的投资经理
	 * @version 2016-08-03
	 */
	if(null==$('select[name="afterDepartmentId"]').val()||$('select[name="afterDepartmentId"]').val()==""){
          createUserOptions(platformUrl.getUserList+"0", "afterUid",1);
	}else{
		  createUserOptions(platformUrl.getUserList+$('select[name="afterDepartmentId"]').val(), "afterUid",1);
			
	}
	/**
	 * 改变事业线时获取该事业线下的投资经理
	 * @version 2016-06-21
	 */
	$('select[name="afterDepartmentId"]').change(function(){
		var did = $('select[name="afterDepartmentId"]').val();
		if(did == null || did == ''){
			createUserOptions(platformUrl.getUserList+"0", "afterUid",1);
		}else{
			createUserOptions(platformUrl.getUserList+did, "afterUid", 1);
		}
	});
	
	$("select[name='afterDepartmentId']").on("change",function(){
		var did = $(this).val();
		if(did != ''){
			$("#receive-did").css("visibility","hidden");
		}
	});
	$("select[name='afterUid']").on("change",function(){
		var uid = $(this).val();
		if(uid != ''){
			$("#receive-uid").css("visibility","hidden");
		}
	});
	$("textarea[name='transferReason']").on("keydown",function(){
		var reason = $(this).val();
		if(reason != ''){
			$("#receive-reason").css("visibility","hidden");
		}
	});
	$("#projectTransfer").on("click",function() {
		var did = $("select[name='afterDepartmentId']").val();
		if(did == ''){
			$("#receive-did").css("visibility","inherit");
			return;
		}
		var uid = $("select[name='afterUid']").val();
		if(uid == 0){
			$("#receive-uid").css("visibility","inherit");
			return;
		}
		var reason = $("textarea[name='transferReason']").val();
		if(reason == ''){
			$("#receive-reason").css("visibility","inherit");
			return;
		}
		var reqUrl=platformUrl.applyTransfer;
		sendPostRequestByJsonStr(reqUrl, $("#transfer_form").serializeObject(), callbackFun);
	});
}
function callbackFun(data){
	if (data.result.status != "OK") {
			layer.msg(data.result.message);
	} else {
		layer.msg("提交成功")
		//window.location=window.location;
		window.location.reload();
	}

}

function revokeTransfer(projectId){
	$("textarea[name='undoReason']").on("keydown",function(){
		var revokeReason = $(this).val();
		if(revokeReason != ''){
			$("#revoke-reason").css("visibility","hidden");
		}
	});
	
	$("#revokeTransfer").click(function(){
		$('input[name="projectId"]').val(projectId);
		var revokeReason = $("textarea[name='undoReason']").val();
		if(revokeReason == ''){
			$("#revoke-reason").css("visibility","inherit");
			return;
		}
		var reqUrl=platformUrl.undoTransfer;
		sendPostRequestByJsonStr(reqUrl, $("#revoke_form").serializeObject(), callbackFunRevoke);
	});
}
function callbackFunRevoke(data){
	if (data.result.status != "OK") {
		layer.msg("项目撤销移交失败");
} else {
	layer.msg("项目撤销移交成功")
	//window.location=window.location;
	window.location.reload();
}

}
function setText(obj){
	if(obj=="set"){
		$("#faNameEdit").attr("style","display:inline-block;")
		$("#faNameEdit").removeAttr("allowNULL");
		$("#faNameEdit").focus();
	}else{
		if($('.tip-yellowsimple')[0]){
			$('.tip-yellowsimple').remove();
		}
		$("#faNameEdit").val(projectInfo.faName);
		$("#faNameEdit").attr("allowNULL","yes");
		$("#faNameEdit").attr("style","display:none;");
	}
}
