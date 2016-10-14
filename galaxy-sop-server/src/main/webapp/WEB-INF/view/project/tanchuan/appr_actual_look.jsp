<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<div class="addmentc">
		<div class="title_bj" id="popup_name_look"></div>
	    <div class="form clearfix">
	        <div class="edit_actual">
	            <dl class="fmdl fl_l  clearfix">
	                <dt>协议名称 ：</dt>
	                <dd>
	                	<span>${actualInfo.totalName}</span>
	                </dd>
	            </dl>
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划拨款时间 ：</dt>
	                <dd>
	                	<div>${actualInfo.partDetail}</div> 
	                </dd>
	            </dl>
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划拨款金额：</dt>
	                <dd>
	                	<div>${actualInfo.partMoney}</div> 
	                </dd>
	            </dl>
                 <dl class="fmdl fl_l  clearfix">
	                <dt>实际拨款金额 ：</dt>
	                <dd>
	                	<div>${actualInfo.actualMoney}</div> 
                        <div class="gray">剩余金额${actualInfo.remainMoney}元</div> 
	                </dd>
	            </dl>
	        </div>
	    </div>
	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn" class="register_all_affrim fl">确定</a>
	        <a href="javascript:;" id="win_cancel_btn" class="register_all_input fr" data-close="close">取消</a>
	    </div>  	
	</div>