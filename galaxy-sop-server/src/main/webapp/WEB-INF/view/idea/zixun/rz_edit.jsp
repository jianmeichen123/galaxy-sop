<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<div class="addmentc creative_edit1 zixuntc">
	<div class="title_bj" id="popup_name1"></div>
	
	<form id="rzForm">
	
	<input type="hidden" name="id" value="" />
	<div class="form clearfix">
		<div class="left">
			<dl class="fmdl fml">
				<dt>融资时间：</dt>
				<dd>
					<input type="text" class="txt" name="financeDate" />
				</dd>
			</dl>
		</div>
		<div class="right">
			<dl class="fmdl">
				<dt>融资金额：</dt>
				<dd>
					<input type="text" class="txt" name="financeAmount" />
				</dd>
			</dl>
		</div>
	</div>
	</form>
	
	<div class="btnbox">
		<a href="javascript:;" class="pubbtn bluebtn" onclick="updateRzSave()">确定</a>
		<a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
	</div>
</div>

