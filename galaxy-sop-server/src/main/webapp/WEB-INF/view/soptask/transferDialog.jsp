<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<div class="qualificationstc errortc finace_history_tc task-abandon-content" style="overflow:hidden;">
	<div class="title_bj abandon-title" >移交任务</div>
	<form action="" id="detail-form">
    <div class="qualifications_all task-todeal">
        <ul>
        	<li>
        		<em>已选择<span id="numOfTask"></span>个任务</em>
        	</li>
        	<li>
        		<em>接收部门：</em><span class='task-department'>${depName }</span>
        	</li>
        	<li class='select-simulate'>
        		<em class='task-recive-person'>接收人：</em>
        		<input class="choice" type='text' placeholder='请选择' readonly required data-msg-required="*接收人不能为空">
        		<input type='hidden' class="hiddenVal" name="targetUserId" >
        		<ul class='toggle-ul'>
        			<!-- <li>请选择</li> -->
        			<c:forEach var="item" items="${users }">
        			<li value="${item.id }">${item.realName }</li>
        			</c:forEach>
        		</ul>
        	</li>
        	<li class='task-todeal-textarea'>
        		<em class='task-reason'>移交原因：</em>
        		<textarea placeholder='请输入移交原因' name="reason" required maxLength="100" data-msg-required="*移交原因不能为空"></textarea>
        	</li>
        </ul>
      	
    
  		<div class="button_affrim tast-btn">
	        <a href="javascript:;"  class="register_all_affrim fl task-confirm" id="save-detail-btn">确定</a>
	        <a href="javascript:;"  class="register_all_input fr task-cancle"  data-close="close">取消</a>
   		 </div>

</div>

<script>

$('.select-simulate input').click(function(event){
	$(this).closest('.select-simulate').siblings('.select-simulate').find('input[type="text"]').removeClass('up');	
	$(this).closest('.select-simulate').siblings('.select-simulate').find('.toggle-ul').slideUp("fast");
	var ul =  $(this).parent().find('ul.toggle-ul');
	var _this = $(this);	
	_this.toggleClass("up");
	ul.slideToggle("fast");
	event.stopPropagation(); 
	$(document).on("click", function(){
		ul.slideUp("fast");
		_this.removeClass('up');		
	});
});
 $('.select-simulate ul li').click(function(){
	var _this = $(this);
	var liText = _this.text()
	var liVal = _this.val();
	$('.hiddenVal').val(liVal);
	var target = _this.closest('.select-simulate').find('input').removeClass('up')
	$('.select-simulate input.choice').val(liText);
	_this.parent().hide();
	
}) 
//var rows = $("#task-table").bootstrapTable('getSelections');
	if($('.highlighCheckbox_th').hasClass('highlighCheckbox_checked')){
		var len = $('.highlighCheckbox_checked').length-1;
	}else{
		var len = $('.highlighCheckbox_checked').length;
	}
	
	$("#numOfTask").text(len);


/******************Validate Start***********************/
jQuery.validator.addMethod("not_blank", function(value, element) {   
	var regx =/\s*\S+/;
	return this.optional(element) || regx.test(value);
}, "*移交原因不能全为空格");

var validator = $("#detail-form").validate({
	focusCleanup:true,
	onfocusout:false,
	onclick:false,
	focusCleanup:true,
	rules:{
		reason : "not_blank"
	}
});
/******************Validate End***********************/

/******************Save Start***********************/
$("#save-detail-btn").click(function(){	
	if(!validator.form()){
		return;
	}
	$(this).addClass('disabled');
	var ids = getSelectedIds();
	var targetUserId = $("#detail-form input[name='targetUserId']").val();
	var reason  = $("#detail-form textarea[name='reason']").val();
	var data = {
		'targetUserId'	:	targetUserId,
		'reason'		:	reason,
		'ids'			:	ids
	};
 var callback = function(data){
		if(data.result.status == 'OK')
		{
			layer.msg('移交成功',{time:'1000'},function(){
				$("#powindow [data-close='close']").click();
				if($("#task-table").length>0)
				{
					$("#task-table").bootstrapTable("refresh");
				}
				else
				{
					backToTaskList();
				}
			});
		}
		else
		{
			layer.msg('移交失败');
			$(this).removeClass('disabled');
		}
	};
	sendPostRequestByJsonObj(platformUrl.transferTask, data, callback);
});
/******************Save End***********************/
</script>
