<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="claimtc deltc">
	<div class="title_bj" id="popup_name">提示</div>
	<p class=" tips_d_new" id="showMessage">
		<input type="hidden" id="del_deliver_id" value="" />
		<b class="null tips_d ">ico</b>
		存在实际拨款信息，不允许删除操作？
	</p>
    <div class="btnbox">
    	<a href="javascript:;" class="pubbtn bluebtn" data-close="close">确定</a>
    </div>
</div>