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
	<div class="title_bj abandon-title" >放弃任务</div>
	<form action="" id="detail-form">
    <div class="qualifications_all task-todeal">
        <ul>
        	<li>
        		<em>已选择<span id="numOfTask"></span>个任务</em>
        	</li>
        	<li class='task-todeal-textarea'>
	        	<div class="task-qulification">
		    		<p style="padding:0 0 15px 80px"><span></span>放弃任务会清除已上传的文件，你确定要放弃此任务吗？</p>
		     	</div>
        		<em class='task-reason'>放弃原因：</em>
        		<textarea placeholder='请输入放弃原因' name="reason" required maxLength="100" data-msg-required="*放弃原因不能为空"></textarea>
        	</li>
        </ul>
      	
    
  		<div class="button_affrim tast-btn">
	        <a href="javascript:;"  class="register_all_affrim fl task-confirm" id="save-detail-btn">确定</a>
	        <a href="javascript:;"  class="register_all_input fr task-cancle"  data-close="close">取消</a>
   		 </div>

</div>

<script>

$('.select-simulate input').click(function(){
	var ul = $('.toggle-ul');
	var _this = $(this);
	if(ul.css('display')=='none'){
		_this.addClass('up');
		ul.slideDown('fast')
	}else{
		ul.slideUp('fast');
		_this.removeClass('up');
	}	
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
var validator = $("#detail-form").validate({
	focusCleanup:true,
	onfocusout:false,
	onclick:false,
	focusCleanup:true
});
/******************Validate End***********************/

/******************Save Start***********************/
$("#save-detail-btn").click(function(){
	if(!validator.form()){
		return;
	}
	$(this).addClass('disabled');
	var ids = getSelectedIds();
	console.log(ids);
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
			layer.msg('放弃成功',{time:'1000'},function(){
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
			layer.msg('放弃失败');
			$(this).removeClass('disabled');
		}
	};
	sendPostRequestByJsonObj(platformUrl.giveupTask, data, callback); 
});
/******************Save End***********************/
</script>
