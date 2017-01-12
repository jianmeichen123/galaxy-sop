<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
	String path = request.getContextPath(); 
%>


<div class="addmentc">
		<div class="title_bj" id="popup_name_look">查看总注资计划</div>
		
	    <div class="form clearfix">
	        <div class="edit_actual">
	            <dl class="fmdl fl_l  clearfix">
	                <dt>协议名称 ：</dt>
	                <dd>
	                	<span>${actualTotalInfo.grantName}</span>
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>投资方 ：</dt>
	                <dd>
	                	<span>${actualTotalInfo.investors}</span>
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l  clearfix">
	                <dt>目标公司 ：</dt>
	                <dd>
	                	<span>${actualTotalInfo.projectCompany}</span>
	                </dd>
	            </dl>
	            
                <%-- <dl class="fmdl fl_l  clearfix">
	                <dt>计划注资时间 ：</dt>
	                <dd>
	                	<div>${actualTotalInfo.partDetail}</div> 
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>实际注资日期 ：</dt>
	                <dd>
	                	<div>${actualTotalInfo.actualTime}</div> 
	                </dd>
	            </dl>
	             --%>
	             <dl class="fmdl fl_l  clearfix">
	                <dt>计划总注资金额：</dt>
	                <dd>
	                	<div><span><fmt:formatNumber value="${actualTotalInfo.grantMoney}" pattern="#.####" minFractionDigits="4" > </fmt:formatNumber>万元</span></div> 
	                </dd>
	            </dl>
                
	            <dl class="fmdl fl_l  clearfix">
	                <dt>股权占比：</dt>
	                <dd>
	                	<div><span><fmt:formatNumber value="${actualTotalInfo.finalShareRatio}" pattern="#.####" minFractionDigits="4" > </fmt:formatNumber>%</span></div> 
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l  clearfix">
	                <dt>加速服务费占比：</dt>
	                <dd>
	                	<div><span><fmt:formatNumber value="${actualTotalInfo.serviceCharge}" pattern="#.####" minFractionDigits="4" > </fmt:formatNumber>%</span></div> 
	                </dd>
	            </dl>
	            
                 <%-- <dl class="fmdl fl_l  clearfix">
	                <dt>实际注资金额 ：</dt>
	                <dd>
	                	<div>${actualTotalInfo.actualMoney}</div> 
                        <div class="gray">剩余金额${actualTotalInfo.remainMoney}万元</div> 
	                </dd>
	            </dl> --%>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>项目估值：</dt>
	                <dd>
	                	<div><span><fmt:formatNumber value="${actualTotalInfo.finalValuations}" pattern="#.####" minFractionDigits="4" > </fmt:formatNumber>万元</span></div> 
	                </dd>
	            </dl>
	            
	        </div>
	        
	    </div>
	    
	</div>