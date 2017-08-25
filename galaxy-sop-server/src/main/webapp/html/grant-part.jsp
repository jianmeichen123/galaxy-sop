<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 时间插件 -->
<link href="/sop/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="/sop/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="/sop/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/sop/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/sop/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/sop/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script src="/sop/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script src="<%=path %>/js/utils.js"></script>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<div class="addmentc qualificationstc">
	<div class="title_bj" id="grant_popup_name">添加分拨计划</div>
  <form id="detail-form">
		<input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="titleId" type="hidden">
    	<input name="subCode" type="hidden">
		<input type="hidden" id="remainMoney" value=""/>
		<input type="hidden" id="totalMoney" value=""/>
		<input type="hidden" id="NewRemainMoney" value=""/>
		<input type="hidden" id="prevPlanMoney" value=""/>
	    <div class="form clearfix" id="actual_aging_container">
	        <div class="appr_aging">
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资名称：</dt>
		                <dd>
		                    	<input class=" txt "  name="field1" type="text" value="分拨" placeholder="分拨"/>
		                </dd>
		            </dl>
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资时间 ：</dt>
		                <dd>
		                	<div>
		                    	<input readonly name="field2" class="datepicker fl txt time"  type="text" data-date-format="yyyy-mm-dd"/>
		                    </div> 
		                </dd>
		            </dl>
	                 <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资金额 ：</dt>
		                <dd>
		                	
		                	<div class='moeny_all'>
		                    	<input class=" txt "  name="field3" type="text" value="" required data-rule-verify_94="true"  data-msg-verify_94="<font color=red>*</font>支持9位长度的四位小数"/>
		                    	<span id="editMoney" class="bj_hui"></span>
		                    	<span class='money'>万元</span>
		                    </div> 
	                        <div class="gray">剩余金额<span id="formatRemainMoney"></span>万元</div> 
		                </dd>
		            </dl>
		             <dl class="fmdl fl_l">
                 <dt>付款条件：</dt>
                 <dd>
                 	<textarea class="area" name="field4" id="now_area" oninput="countChar('now_area','label_now_next','2000')" valType="required" msg="<font color=red>*</font>详细内容不能为空"></textarea>
                 	<p class="num_tj"><span for="" id="label_now_next">2000</span>/2000</p>
                 </dd>
            </dl>
	        </div>
	    </div>
	    </form>
	    <!-- 查看显示下面内容 -->
            <div class="see_block">
            	<div class="appr_aging">
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资名称：</dt>
		                <dd>
		                    	<dd name="field1"></dd>
		                </dd>
		            </dl>
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资时间 ：</dt>
		                <dd>
		                	<dd name="field2"></dd>
		                </dd>
		            </dl>
	                 <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资金额 ：</dt>
		                <dd name="field3" class="money"></dd>
		               	<dd class="remainMoney gray">剩余金额<span></span>万元</dd>
		                
		            </dl>
		        <dl class="fmdl fl_l">
                 <dt>付款条件：</dt>
                 <dd name="field4" class="textarea"></dd>
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
		 $("#detail-form").validate({});
		$.validator.setDefaults({
			errorElement:'span'
		});
	})

	
	</script>
