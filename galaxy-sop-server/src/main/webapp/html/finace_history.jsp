<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js" charset="UTF-8"></script>
<div class="historytc" id="financeDetail">
	<div class="title_bj" id="popup_name"></div>
	<form action="" id="detail-form">
			
    <input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="titleId" type="hidden">
    <div class="form clearfix">
        <div class="conference_all">
            <dl class="fmdl clearfix">
                <dt>融资时间：</dt>
                <dd>
                    <input type="text" class="datepicker-text txt time" readonly=""  name="field1" value="">
                </dd>
                  <dl class="fmdl clearfix">
                 <dt>投资方(机构或个人)：</dt>
                <dd>
                    <input type="text" class="txt"  name="field2" valType="OTHER" allowNULL="yes" regString="^[^\s](.{0,49})$" msg="<font color=red>*</font>不能超过50字符"/>
                </dd>
            </dl>
            </dl>
            <dl class="fmdl clearfix">
             <dt>投资金额：</dt>
                <dd>
                    <input type="text" class="txt fl" name="field3" allowNULL="yes" valType="LIMIT_10_NUMBER" msg="<font color=red>*</font>支持四位小数"/>&nbsp;<span>万元</span>
                </dd>
                 
                <dt>币种：</dt>
                <dd>
                    <select name="field6" id="financeUnit">
                        <option value="0"  name="financeUnit" >人民币</option>
                        <option value="1" name="financeUnit" selected="selected">美元</option>
          
                    </select>
                </dd>
            </dl>
          <dl class="fmdl clearfix">
            <dt>股权占比：</dt>
                <dd>
                    <input type="text" class="txt fl" name="field4" valType="OTHER" allowNULL="yes" regString="^(\d{1,2}(\.\d{1,2})?|100(\.[0]{1,2})|100)$" msg="<font color=red>*</font>0-100之间的两位小数"/>&nbsp;<span>%</span>
                </dd>
           <dt>估值金额：</dt>
                <dd>
                    <input type="text" class="txt fl" name="field5" allowNULL="yes" valType="LIMIT_10_NUMBER" msg="<font color=red>*</font>支持四位小数"/>&nbsp;<span>万元</span>
                </dd>
              
           </dl>
           <dl class="fmdl clearfix">
            <dt>融资轮次：</dt>
                <dd>
					<select name="field7" class='new_nputr'>
			         </select>
                </dd> 
                 <dt>新老股：</dt>
                <dd>
					<input type="radio" name="field8">新股
				    <input type="radio" name="field8">老股
                </dd> 
           
           </dl>
            <dl class="fmdl clearfix">
            <dt>合同关键条款：</dt>
                <dd>
					<textarea rows="" cols="" name="field9">
					
					
					</textarea>
                </dd> 
               
           
           </dl>
           <dl class="fmdl clearfix">
            <dt>对赌或业绩承诺条款：</dt>
                <dd>
					<textarea rows="" cols="" name="field10">
					
					
					</textarea>
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
	$('input[name="field1"]').val(new Date().format("yyyy-MM-dd"));
});
$(function(){
    $("#detail-form").validate({});
    $.validator.setDefaults({
    	errorElement:'span'
    });
})
//initDialogValstr("updateOrSave_HF");
</script>
