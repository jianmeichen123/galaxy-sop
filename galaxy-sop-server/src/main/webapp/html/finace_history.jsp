<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<script>

</script>

<div class="addmentc creative_edit1 zixuntc" id="add_rz" style=" width:400px;">
	<div class="title_bj" id="popup_name1"></div>
	
    <form action="" id="detail-form">
        <input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="titleId" type="hidden">
	<div class="form clearfix" >
		<div class="left">
			<dl class="fmdl fml">
				<dt>融资时间：</dt>
				<dd>
					<input type="text" class="txt" name="financeDate"  maxlength="50"
						valType="MAXBYTE_VAL" regString="100" msg="<font color=red>*</font>不能超过50字符" />
				</dd>
			</dl>
		</div>
		<div class="right">
			<dl class="fmdl">
				<dt>融资金额：</dt>
				<dd>
					<input type="text" class="txt" name="financeAmount"  maxlength="50" 
						valType="MAXBYTE_VAL" regString="100" msg="<font color=red>*</font>不能超过50字符" />
				</dd>
			</dl>
		</div>
	</div>
	</form>
	
	   <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="save-detail-btn">确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<script>
     $(function(){
        $("#detail-form").validate({});
        $.validator.setDefaults({
        	errorElement:'span'
        });
    })
</script>
