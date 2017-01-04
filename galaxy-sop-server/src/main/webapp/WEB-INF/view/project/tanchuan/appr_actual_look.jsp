<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<div class="addmentc">
		<div class="title_bj" id="popup_name_look">查看实际注资信息</div>
	    <div class="form clearfix">
	        <div class="edit_actual">
	            <dl class="fmdl fl_l  clearfix">
	                <dt>协议名称 ：</dt>
	                <dd>
	                	<span>${actualInfo.totalName}</span>
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>投资方 ：</dt>
	                <dd>
	                	<span>${actualInfo.investors}</span>
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l  clearfix">
	                <dt>目标公司 ：</dt>
	                <dd>
	                	<span>${actualInfo.projectCompany}</span>
	                </dd>
	            </dl>
	            
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划注资时间 ：</dt>
	                <dd>
	                	<div>${actualInfo.partDetail}</div> 
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>实际注资日期 ：</dt>
	                <dd>
	                	<div>${actualInfo.actualTime}</div> 
	                </dd>
	            </dl>
	            
	             <dl class="fmdl fl_l  clearfix">
	                <dt>计划总注资金额：</dt>
	                <dd>
	                	<div>${actualInfo.finalContribution}</div> 
	                </dd>
	            </dl>
                <%-- <dl class="fmdl fl_l  clearfix">
	                <dt>计划注资金额：</dt>
	                <dd>
	                	<div>${actualInfo.partMoney}</div> 
	                </dd>
	            </dl> --%>
	            <dl class="fmdl fl_l  clearfix">
	                <dt>股权占比：</dt>
	                <dd>
	                	<div>${actualInfo.finalShareRatio}</div> 
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l  clearfix">
	                <dt>加速服务费占比：</dt>
	                <dd>
	                	<div>${actualInfo.serviceCharge}</div> 
	                </dd>
	            </dl>
	            
                 <dl class="fmdl fl_l  clearfix">
	                <dt>实际注资金额 ：</dt>
	                <dd>
	                	<div>${actualInfo.actualMoney}</div> 
                        <div class="gray">剩余金额${actualInfo.remainMoney}元</div> 
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>项目估值：</dt>
	                <dd>
	                	<div>${actualInfo.finalValuations}</div> 
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>附件：</dt>
	                <dd>
	                	<div>aaaaaa.txt</div> 
	                	<div>bbbbb.action</div> 
	                </dd>
	            </dl>
	        </div>
	        
	        
	    </div>
	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn" class="register_all_affrim fl">确定</a>
	        <a href="javascript:;" id="win_cancel_btn" class="register_all_input fr" data-close="close">取消</a>
	    </div>  	
	</div>