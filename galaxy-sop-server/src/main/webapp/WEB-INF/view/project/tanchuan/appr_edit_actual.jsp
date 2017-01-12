<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<style>
.moxie-shim{display:none;}
#dialog_actual dl{margin:0;}
#dialog_actual dl dd{font-size:12px;font-family:"宋体";}
.min .fmdl{float:left}
</style>
<div class="addmentc" id="dialog_actual">
	<form id="form_edit_actual_dialog">
		<div class="title_bj popup_name_edit" id="label_pop_name">查看、添加、编辑实际注资信息</div>
	    <div class="form clearfix" id="container">
	        <div class="edit_actual"  style="min-width:600px;">
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>协议名称 ：</dt>
	                <dd>
	                	<span id="label_protocol_name"></span>
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>投资方 ：</dt>
	                <dd>
	                	<span id="label_investors"></span>
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l  clearfix">
	                <dt>目标公司 ：</dt>
	                <dd>
	                	<span id="label_projectCompany"></span>
	                </dd>
	            </dl>
	            
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划注资时间 ：</dt>
	                <dd>
	                	<div id="label_plan_grant_time"></div> 
	                </dd>
	            </dl>
	            
	            <div class="min clearfix">
	            <dl class="fmdl fml clearfix" style="width:60%">
	                <dt>实际注资日期 ：</dt>
	                <dd id="div_label_actualTime">
	                	<input type="text" class="datepicker txt time" readonly id="label_actualTime" name="actualTime"  style="height:23px;"/>
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>计划总注资金额 ：</dt>
	                <dd>
	                	<div id="label_plan_grant_money"></div> 
	                </dd>
	            </dl>
	            </div>
	            <!-- 
	             <dl class="fmdl fl_l  clearfix">
	                <dt>计划总注资金额：</dt>
	                <dd>
	                	<div id="label_finalContribution"></div> 
	                </dd>
	            </dl>
	             -->
	            
	            <div class="min clearfix">
	            <dl class="fmdl fl_l  clearfix" style="width:60%">
	                <dt>股权占比：</dt>
	                <dd>
	                	<div id="label_finalShareRatio"></div> 
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l  clearfix">
	                <dt>加速服务费占比：</dt>
	                <dd>
	                	<div id="label_serviceCharge"></div> 
	                </dd>
	            </dl>
	            </div>			
	            <div class="min clearfix">
                <dl class="fmdl fl_l  clearfix" style="width:60%">
	                <dt>实际注资金额 ：</dt>
	                <dd>
	                	<div id="label_grant_money">
	                    	<input class="txt" id="form_grant_money" type="text" 
	                    		valType="LIMIT_11_NUMBER" 
	                    		msg="<font color=red>*</font>支持9位长度的四位小数">
	                   		<span class='money'>万元</span>
	                    </div> 
                        <div class="gray" id="label_surplus_grant_money">剩余金额0万元</div> 
	                </dd>
	            </dl>
	            
	            <dl class="fmdl fl_l  clearfix">
	                <dt>项目估值：</dt>
	                <dd>
	                	<div id="label_finalValuations"></div> 
	                </dd>
	            </dl>
	            </div>
	            <dl class="fmdl fl_l  clearfix" id="show_actual_file">
	                <table style="width:530px;margin: auto;" id="filelist"  cellspacing="0" cellpadding="0">
		                 <thead>
		                    <tr>
		                      <th style="width:265px;">文件名称</th>
		                      <th style="width:105px" align="center">文件大小</th>
		                      <!-- <th style="width:80px" align="center">操作</th>
		                      <th style="width:80px" align="center">进度</th> -->
		                    </tr>
		                 </thead>
		                 </table> 
	            </dl>
	            <!-- <div class="affrim_line"></div> -->
	            <dl class="fmdl fl_l" id="choose_up_file">
	                 <dt>上传附件 ：</dt>
	                 <div class="fmload clearfix">
			            <dd>
				        	<input  type="text"  class="txt" name="textarea2" id="textarea2" readonly="readonly"></input>
				        </dd>
				        <dd>
				        	<a href="javascript:;"  class="register_all_affrim fl" id="select_btn" style="position: relative; z-index: 1;">选择附件</a>
			    		</dd>
			        </div>
	            </dl>  
	            <dl class="fmdl fl_l" id="show_up_file" >
	                 <table style="width:530px;margin: auto; display:block;" id="filelist"  cellspacing="0" cellpadding="0">
	                    <tr>
	                      <th style="width:265px;">文件名称</th>
	                      <th style="width:105px" align="center">文件大小</th>
	                      <th style="width:80px" align="center">操作</th>
	                      <th style="width:80px" align="center">进度</th>
	                    </tr>
	                 </table> 
	            </dl>
	             
	        </div>
	    </div>
	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn" class="register_all_affrim fl">确定</a>
	        <a href="javascript:;" id="win_cancel_btn" class="register_all_input fr" data-close="close">取消</a>
	    </div>  
	</form>	
</div>






