<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<script src="<%=path %>/js/utils.js"></script>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<!-- 时间插件 -->
<link href="/sop/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="/sop/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="/sop/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/sop/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/sop/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/sop/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script src="/sop/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<div class="addmentc">
		<div class="title_bj" id="popup_name">编辑会议纪要</div>
		<input type="hidden" id="remainMoneyPart" value=""/>
		<input type="hidden" id="totalMoneyPart" value=""/>
		<input type="hidden" id="valtr" value=""/>
	    <div class="form clearfix" id="actual_aging_container">
	        <div class="appr_aging">
	           <form id="actual_aging_form">
	               <dl class="fmdl fl_l  clearfix">
	               <input type="hidden" id="projectId" name="projectId" value=""/>
	               <input type="hidden" id="grantId" data-name="id" data-type="19" name="id" value=""/>
		            <dt>分拨名称 ：</dt>
		                <dd>
		                	<div>
		                    	<input class="edittxt" id="grantDetail" data-name="field1" data-type="19" type="text" value="分拨" maxLength="20" />
		                    </div> 
		                </dd>
		            </dl>
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资时间 ：</dt>
		                <dd>
		                	<div>
		                	    <input readonly id="field2" name="field2" data-name="field2" data-type="19" class="datepicker fl txt time"  type="text" data-date-format="yyyy-mm-dd"/>
		                    	<!-- <input readonly class="edittxt" id="grantDetail" data-name="field2" data-type="19" class="datepicker fl txt time"  type="text" data-date-format="yyyy-mm-dd"/> -->
		                    </div> 
		                </dd>
		            </dl>
	                 <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资金额 ：</dt>
		                <dd>
		                	
		                	<div class='moeny_all'>
		                    	<input class=" txt " name="grantMoney" id="grantMoney" data-name="field3" data-type="19" type="text" value="" required data-rule-verify_94="true"  data-msg-verify_94="<font color=red>*</font>支持9位长度的四位小数"/>
		                    	<span id="editMoney" class="bj_hui"></span>
		                    	<span class='money'>万元</span>
		                    </div> 
	                        <div class="gray">剩余金额<span id="formatRemainMoney"></span>万元</div> 
		                </dd>
		            </dl>
		            <dl class="fmdl fl_l  clearfix">
		              <dt>付款条件：</dt>
		                <dd>
		                	<div>
		                    	<textarea class="team_textarea" data-name="field4" id="field4" data-type="19"></textarea>
		                    </div> 
		                </dd>
		            </dl>
                 </form>
	           <!--  <div class="affrim_line"></div> -->
	             <dl class="fmdl fl_l" id="choose_up_file">
		                 <dt>上传附件 ：</dt>
		                 <div class="fmload clearfix">
				            <dd>
					        	<input  type="text"  class="txt" name="textarea2" id="textarea2" readonly="readonly"></input>
					        </dd>
					        <dd>
					        	<a href="javascript:;"  class="register_all_affrim fl" id="select_btn">选择附件</a>
				    		</dd>
				        </div>
		            </dl>  
		            <dl class="fmdl fl_l" id="show_up_file">
		                 <table style="width:530px;margin: auto;" id="filelist"  cellspacing="0" cellpadding="0">
		                 <thead>
		                    <tr>
		                      <th style="width:265px;">文件名称</th>
		                      <th style="width:105px" align="center">文件大小</th>
		                      <th style="width:80px" align="center">操作</th>
		                      <th style="width:80px" align="center">进度</th>
		                    </tr>
		                 </thead>
		                 </table> 
		            </dl>
         
	            
	             
	        </div>
	    </div>
	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn" class="register_all_affrim fl">确定</a>
	        <a href="javascript:;" id="win_cancel_btn" class="register_all_input fr" data-close="close">取消</a>
	    </div>  	
	</div>
	 <script src="<%=path %>/js/validate/jquery.validate.min.js" type="text/javascript"></script>  
	<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
	<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>
	 <script src="<%=path %>/js/partFile.js"></script>
		<script>
	$(function(){
		 $("#actual_aging_form").validate({});
		$.validator.setDefaults({
			errorElement:'span'
		});
	})

	
	</script>
