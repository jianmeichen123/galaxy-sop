<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="claimtc deltc" >
	<div class="title_bj" id="popup_name"></div>
	<p class="tips">
		<input type="hidden" id="del_deliver_id" value="" />
		<!-- <b class="null tips_d">ico</b> -->
		是否删除总注资计划？
	</p>
    <div class="btnbox">
    	<a href="javascript:;" class="pubbtn bluebtn" data-btn="appr_delete">确定</a>
    	<a href="javascript:;" class="pubbtn fffbtn" id="isDelete" data-close="close">取消</a>
    </div>
</div>