function doSumbit(projectId){
	$('input[name="projectIds"]').val(projectId);
	/**
	 * 查询事业线
	 * @version 2016-08-03
	 */
	createCareelineOptions(platformUrl.getCareerlineList,"afterDepartmentId",1);
	sendGetRequest(platformUrl.getCareerlineList,null,callBackA);
	/**
	 * 根据事业线查询相应的投资经理
	 * @version 2016-08-03
	 */
	/*if(null==$('select[name="afterDepartmentId"]').val()||$('select[name="afterDepartmentId"]').val()==""){
          createUserOptions(platformUrl.getUserList+"0", "afterUid",1);
	}else{
		  createUserOptions(platformUrl.getUserList+$('select[name="afterDepartmentId"]').val(), "afterUid",1);
			
	}*/
	var targetInput=$('ul[name="afterDepartmentId"]').closest('.select-simulate').find('input');
	if(null==targetInput.val()||targetInput.val()==""){
        //createUserOptions(platformUrl.getUserList+"0", "afterUid",1);
        sendGetRequest(platformUrl.getUserList+"0",null,callBackB);
	}else{
		  //createUserOptions(platformUrl.getUserList+$('select[name="afterDepartmentId"]').val(), "afterUid",1);
		  sendGetRequest(platformUrl.getUserList+targetInput.val(),null,callBackB);
			
	}
	/**
	 * 改变事业线时获取该事业线下的投资经理
	 * @version 2016-06-21
	 */
/*	$('select[name="afterDepartmentId"]').change(function(){
		var did = $('select[name="afterDepartmentId"]').val();
		if(did == null || did == ''){
			createUserOptions(platformUrl.getUserList+"0", "afterUid",1);
		}else{
			createUserOptions(platformUrl.getUserList+did, "afterUid", 1);
		}
	});*/
	//接收部门下拉赋值
	function callBackA(data){
		 var _dom=$('ul[name="afterDepartmentId"]');
         _dom.html("");
         $.each(data.entityList,function(){
        	 _dom.append("<li value='"+this.id+"'>"+this.name+"</li>");
		});
	   $('ul[name="afterDepartmentId"] li').click(function(){
		   $(this).closest('.select-simulate').find('span.error').hide();
			var target = $(this).closest('.select-simulate').find('input[type="text"]');
			target.removeClass('up')
			var txt = $(this).text(); 
			target.val(txt);
			$(this).closest('.select-simulate').find('input[type="hidden"]').val($(this).val());
			$(".select-simulate ul").hide();
			$('ul[name="afterUid"]').closest('.select-simulate').find('input').val('');
			//级联投资经理下拉框的值
			var targetVal = $(this).val();
			if(targetVal == null || targetVal == ''){
				sendGetRequest(platformUrl.getUserList+"0",null,callBackB);
			}else{
				sendGetRequest(platformUrl.getUserList+targetVal,null,callBackB);
			}
	   });
	}
	//事业线下的投资经理下拉赋值
	function callBackB(data){
		if(data.entityList.length==0){
			$('ul[name="afterUid"]').addClass("borderNone");
		}else{
			$('ul[name="afterUid"]').removeClass("borderNone");
		}
		 var _dom=$('ul[name="afterUid"]');
        _dom.html("");
        $.each(data.entityList,function(){
       	 _dom.append("<li value='"+this.idstr+"'>"+this.realName+"</li>");
		});
	   $('ul[name="afterUid"] li').click(function(){
		   $(this).closest('.select-simulate').find('span.error').hide();
			var target = $(this).closest('.select-simulate').find('input[type="text"]');
			target.removeClass('up')
			var txt = $(this).text(); 
			target.val(txt);
			$(this).closest('.select-simulate').find('input[type="hidden"]').val($(this).val());
			$(".select-simulate ul").hide(); 
	   });
	}
	$("#projectTransfer").on("click",function() {
		$('.select-simulate').find('input[type="text"]').removeClass('up');
		$('.toggle-ul').hide();
		if(!$('#detail-form').validate().form()){//验证不通过时候执行
			return false;	
		}
		$(this).addClass('disabled');
		var reqUrl=platformUrl.applyTransfer;
		sendPostRequestByJsonStr(reqUrl, $("#detail-form").serializeObject(), callbackFun);

	});
}
function callbackFun(data){
	if (data.result.status != "OK") {
			layer.msg(data.result.message);
			$("#projectTransfer").removeClass('disabled');
	} else {
		   var message="";
		   if($("#actionStyle").val()=="transfer"){
			   message="移交成功";
		   }else{
			   message="指派成功";
		   }
		    layer.msg(message,{time:1000},function(){
			var url = $("#menus .on a").attr('href');
			if($("#numCheck").css("display")=="none"){
				var url = $("#menus .on a").attr('href');
				window.location=url;
			 }else{
				 window.location=platformUrl.toAssignProject+'?from='+$("#actionStyle").val();
			 }
			$("#projectTransfer").removeClass('disabled');
		});
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
	window.location=window.location;
}

}
