<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<div class="addmentc" id="dialog_actual">
	<form id="form_edit_actual_dialog">
		<div class="title_bj popup_name_edit" id="label_pop_name"></div>
	    <div class="form clearfix">
	        <div class="edit_actual">
	            <dl class="fmdl fl_l  clearfix">
	                <dt>协议名称 ：</dt>
	                <dd>
	                	<span id="label_protocol_name">创业服务协议</span>
	                </dd>
	            </dl>
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划拨款时间 ：</dt>
	                <dd>
	                	<div id="label_plan_grant_time">完成条款4后15个工作日内</div> 
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l  clearfix">
	                <dt>计划拨款金额 ：</dt>
	                <dd>
	                	<div id="label_plan_grant_money"></div> 
	                </dd>
	            </dl>
                <dl class="fmdl fl_l  clearfix">
	                <dt>实际拨款金额 ：</dt>
	                <dd>
	                	
	                	<div id="label_grant_money">
	                    	<input class="txt" id="form_grant_money" type="text" valType="OTHER" regString="^(0(?:[.](?:[1-9]\d?|0[1-9]))|[1-9][0-9]{0,8}|[1-9][0-9]{0,8}\.[0-9]{1,2})$" msg="<font color=red>*</font>支持9位长度的两位小数">
	                   		<span class='money'>元</span>
	                    </div> 
                        <div class="gray" id="label_surplus_grant_money">剩余金额0元</div> 
	                </dd>
	            </dl>
	             
	        </div>
	    </div>
	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn" class="register_all_affrim fl">确认</a>
	        <a href="javascript:;" id="win_cancel_btn" class="register_all_input fr" data-close="close">取消</a>
	    </div>  
	</form>	
</div>