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
	<div class="title_bj" id="finace_popup_name"></div>

	
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
                    <input type="text" class="txt"  name="field2" valType="OTHER" allowNULL="yes"  data-rule-verify_101="true" regString="^[^\s](.{0,49})$" data-msg-verify_101="<font color=red>*</font>不能超过50字符"/>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
             <dt>投资金额：</dt>
                <dd>
                    <input type="text" class="txt fl" name="field3" allowNULL="yes"  data-rule-verify_82="true" data-msg-vverify_82="<font color=red>*</font>不能超过99999999整数和两位小数"/>&nbsp;<span>万元</span>
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
                	 <input type="text" class="txt fl" name="field4" allowNULL="yes" data-rule-vinputValRule_3="true"  data-msg-vinputValRule_3="<font color=red>*</font>支持0～100的整数和两位小数"/>&nbsp;<span>%</span>
                </dd>
           </dl>
           <dl class="fmdl clearfix">
           <dt>估值金额：</dt>
                <dd>
                    <input type="text" class="txt fl" name="field5" allowNULL="yes" valType="LIMIT_10_NUMBER" data-rule-verify_102="true" data-msg-verify_102="<font color=red>*</font>支持四位小数"/>&nbsp;<span>万元</span>
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
					<label><input type="radio" name="field8" value="2203">新股</label>
				    <label><input type="radio" name="field8" value="2204">老股</label>
                </dd>
             </dl>
           <div class="team_wid">
            	<p>合同关键条款：</p>
            	<textarea class="team_textarea" name="field9" maxlength="1000" id="finace_area" oninput="countChar('finace_area','label_now','2000')"></textarea>
            	<div class="finace_fnum num_tj"><span for="" id="label_now">2000</span>/2000</div>
            </div>
           <div class="team_wid">
            	<p>对赌或业绩承诺条款：</p>
            	<textarea class="team_textarea" name="field10" maxlength="1000" id="now_area" oninput="countChar('now_area','label_now_next','2000')"></textarea>
            	<div class="finace_fnum num_tj"><span for="" id="label_now_next">2000</span>/2000</div>
            </div>
        </div>
    </div>
      	</form>
      	<!-- 查看显示下面内容 -->
      	<div class="see_block">
            <div class="conference_all">
            <dl class="fmdl clearfix wid_2">
                <dt>融资时间：</dt>
                <dd name="field1"></dd>
                  
            </dl>
            <dl class="fmdl clearfix wid_2 wid">
                 <dt>投资方(机构或个人)：</dt>
                <dd name="field2"></dd>
            </dl>
            <dl class="fmdl clearfix wid_2">
             <dt>投资金额：</dt>
                <dd name="field3"></dd>
                 
            </dl>
          <dl class="fmdl clearfix wid_2">
            <dt>股权占比：</dt>
                <dd name="field4"></dd>
              
           </dl>
           <dl class="fmdl clearfix wid_2">
           <dt>估值金额：</dt>
           <dd name="field5"></dd>
            </dl>
           <dl class="fmdl clearfix wid_2">
            <dt>融资轮次：</dt>
                <dd name="field7"></dd>            
           
           </dl>
           <dl class="fmdl  wid_2">
				<dt>新老股：</dt>
				<dd name="field8"></dd> 
				</dl>
             	<dl class="fmdl wid clearfix block">
           		<dt>合同关键条款：</dt>
	                <dd name="field9"></dd> 
	             </dl>
	             <dl class="fmdl wid clearfix block">
	           		<dt>对赌或业绩承诺条款：</dt>
	                <dd name="field10"></dd> 
	             </dl>
        	</div>	
        </div>
    
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
    
    //金额联动
    function calculationValuations(){
		var projectParent = $("input[name='field3']").val();
		var projectChildren = $("input[name='field4']").val();
		if(projectParent > 0 && projectChildren > 0){
			return projectParent * (100/projectChildren);
		}
		return null;
	}
    $("div").delegate("input[name='field3']","blur",function(){
    	var valuations = calculationValuations();
		if(valuations != null){
			$("input[name='field5']").val(valuations.toFixed(2));
		}
	});
    $("div").delegate("input[name='field4']","blur",function(){
    	var valuations = calculationValuations();
		if(valuations != null){
			$("input[name='field5']").val(valuations.toFixed(2));
		}
	});
    
})

//selectContext();
//initDialogValstr("updateOrSave_HF");
</script>
