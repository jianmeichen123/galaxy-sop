<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="qualificationstc errortc finace_history_tc task-abandon-content" style="overflow:hidden;">
	<div class="title_bj abandon-title" >移交任务</div>
	<form action="" id="detail-form">
    <div class="qualifications_all task-todeal">
        <ul>
        	<li>
        		<em>接收部门：</em><span class='task-department'>${depName }</span>
        	</li>
        	<li class='select-simulate'>
        		<em class='task-recive-person'>接收人：</em>
        		<input class="choice" type='text' placeholder='请选择'>
        		<input type='hidden' class="hiddenVal">
        		<ul class='toggle-ul'>
        			<li>请选择</li>
        			<c:forEach var="item" items="${users }">
        			<li value="${item.id }">${item.realName }</li>
        			</c:forEach>
        		</ul>
        	</li>
        	<li class='task-todeal-textarea'>
        		<em class='task-reason'>移交原因：</em>
        		<textarea placeholder='请输入移交原因'></textarea>
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
</script>
