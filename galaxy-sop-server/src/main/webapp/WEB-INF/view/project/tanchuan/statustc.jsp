<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<div class="statustc status">

	<div class="title_bj" id="popup_name">添加健康状况</div>

	<div class="status_con">
		<form id="health_form">
		
		<input type="hidden" name="projectId" value="" />
		<dl class="fmdl clearfix">
			<dt>健康状况：</dt>
			<dd>
			    <label class="radio"> <input type="radio" name="healthState" value="0"> 初始 </label> 
				<label class="radio"> <input type="radio" name="healthState" value="1"> 高于预期 </label> 
				<label class="radio"> <input type="radio" name="healthState" value="2" checked="checked"> 正常 </label> 
				<label class="radio"> <input type="radio" name="healthState" value="3"> 健康预警 </label>
				<label class="radio"> <input type="radio" name="healthState" value="4"> 清算 </label>
			</dd>
		</dl>
		
		<dl class="fmdl clearfix">
			<dt>风险点：</dt>
			<dd class="clearfix">
				<textarea class="area" name="rematk" cols="45" rows="5" maxlength="50"></textarea>
			</dd>
		</dl>
		
		</form>
		
		<div class="btnbox" id="but_oper">
			<a href="javascript:;" class="pubbtn bluebtn" onclick="save_health()">确定</a> <a
				href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
		</div>
	</div>

</div>