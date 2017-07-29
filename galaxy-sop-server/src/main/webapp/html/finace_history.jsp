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
<div class="qualificationstc"  id="financeDetail" style="    max-height: 500px;   overflow:hidden; overflow-y: auto;">
	<div class="title_bj" id="popup_name"></div>

	
	<form action="" id="detail-form">
			
    <input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="titleId" type="hidden">
    	<input name="subCode" type="hidden">
    <div class="qualifications_all">
        <div class="conference_all">
            <dl class="fmdl clearfix">
                <dt>融资时间：</dt>
                <dd>
                    <input type="text" class="datepicker-text txt time" readonly=""  name="field1" value="">
                </dd>
                  
            </dl>
            <dl class="fmdl clearfix">
                 <dt>投资方(机构或个人)：</dt>
                <dd>
                    <input type="text" class="txt"  name="field2" valType="OTHER" allowNULL="yes" regString="^[^\s](.{0,49})$" msg="<font color=red>*</font>不能超过50字符"/>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
             <dt>投资金额：</dt>
                <dd>
                    <input type="text" class="txt fl" name="field3" allowNULL="yes" valType="LIMIT_10_NUMBER" msg="<font color=red>*</font>支持四位小数"/>&nbsp;<span>万元</span>
                </dd>
                 
            </dl>
            <dl class="fmdl clearfix">
               <dt>币种：</dt>
               <dd>
                   <select name="field6"  id="field6">
                      <!--  <option value="0"  name="financeUnit" >人民币</option>
                       <option value="1" name="financeUnit" selected="selected">美元</option> -->
         
                   </select>
               </dd>
           </dl>
          <dl class="fmdl clearfix">
            <dt>股权占比：</dt>
                <dd>
                    <input type="text" class="txt fl" name="field4" valType="OTHER" allowNULL="yes" regString="^(\d{1,2}(\.\d{1,2})?|100(\.[0]{1,2})|100)$" msg="<font color=red>*</font>0-100之间的两位小数"/>&nbsp;<span>%</span>
                </dd>
              
           </dl>
           <dl class="fmdl clearfix">
           <dt>估值金额：</dt>
                <dd>
                    <input type="text" class="txt fl" name="field5" allowNULL="yes" valType="LIMIT_10_NUMBER" msg="<font color=red>*</font>支持四位小数"/>&nbsp;<span>万元</span>
                </dd>
            </dl>
           <dl class="fmdl clearfix">
            <dt>融资轮次：</dt>
                <dd>
					<select name="field7" class='new_nputr' id="field7">
					        <option name="111">1111111</option>
			         </select>
                </dd>            
           
           </dl>
           <dl class="fmdl">
           		<dt>新老股：</dt>
                <dd>
					<label><input type="radio" name="field8" value="1">新股</label>
				    <label><input type="radio" name="field8" value="2">老股</label>
                </dd> 
             </dl>
           <div class="team_wid">
            	<p>合同关键条款：</p>
            	<textarea class="team_textarea" name="field9" maxlength="1000" id="finace_area" oninput="countChar(&quot;now_area&quot;,&quot;label_now&quot;,&quot;1000&quot;)"></textarea>
            	<div class="finace_fnum num_tj"><span for="" id="label_now">1000</span>/1000</div>
            </div>
           <div class="team_wid">
            	<p>对赌或业绩承诺条款：</p>
            	<textarea class="team_textarea" name="field10" maxlength="1000" id="now_area" oninput="countChar(&quot;now_area&quot;,&quot;label_now&quot;,&quot;1000&quot;)"></textarea>
            	<div class="finace_fnum num_tj"><span for="" id="label_now">1000</span>/1000</div>
            </div>
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
//selectContext();
//initDialogValstr("updateOrSave_HF");
</script>
