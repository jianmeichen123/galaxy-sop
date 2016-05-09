<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="claimtc abandon">
	<dl>
		<dt>原因</dt>
		<dd><textarea name="" id="givUpReason" cols="30" rows="10"></textarea></dd>
	</dl>
    <div class="btnbox">
    	<a href="javascript:;" class="pubbtn bluebtn" id="saveReason" >确定</a><a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
    </div>
</div>