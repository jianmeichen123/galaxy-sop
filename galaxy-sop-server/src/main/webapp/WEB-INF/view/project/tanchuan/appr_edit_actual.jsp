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
		<div class="title_bj popup_name_edit" id="label_pop_name">添加实际注资信息</div>
		<input type="hidden" id="newRemainMoneyActual" value=""/>
	    <div class="form clearfix" id="container">
	        <div class="edit_actual"  style="min-width:600px;width:740px\9;">
	            
	            <dl class="fmdl fl_l  clearfix">
	               <input type="hidden" id="projectId" name="projectId" value=""/>
	               <input type="hidden" id="grantId" data-name="id" data-type="19" name="id" value=""/>
	                <input type="hidden" id="parentId" name="parentId" value=""/>
	                <input type="hidden" id="code" name="code" value="grant-actual"/>
		            <dt>实际注资名称 ：</dt>
		                <dd>
		                	<div>
		                    	<input class="edittxt" id="grantDetail" data-name="field1" data-type="19" type="text" value="实际" maxLength="20" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>只能输入20个字符"/>
		                    </div> 
		                </dd>
		            </dl>
	                <dl class="fmdl fl_l  clearfix">
		                <dt>实际注资时间 ：</dt>
		                <dd>
		                	<div>
		                	    <input readonly id="field2" name="field2" data-name="field2" data-type="19" class="datepicker fl txt time"  type="text" data-date-format="yyyy-mm-dd"/>
		                    </div> 
		                </dd>
		            </dl>
	                 <dl class="fmdl fl_l  clearfix">
		                <dt>实际注资金额 ：</dt>
		                <dd>
		                	
		                	<div class='moeny_all'>
		                    	<input class=" txt " name="grantMoney" id="grantMoney" data-name="field3" data-type="19" type="text" value="" required data-rule-verify_94="true"  data-msg-verify_94="<font color=red>*</font>支持9位长度的四位小数"/>
		                    	<span id="editMoney" class="bj_hui"></span>
		                    	<span class='money'>万元</span>
		                    </div> 
	                        <div class="gray">剩余金额<span id="formatRemainMoney"></span>万元</div> 
		                </dd>
		            </dl>
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
	</form>	
</div>
 <script src="<%=path %>/js/validate/jquery.validate.min.js" type="text/javascript"></script>  
	<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
	<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>
  <script src="<%=path %>/js/partFile.js"></script>
  	<script>
	alert('dddddddd')
		 $("#form_edit_actual_dialog").validate({});
		$.validator.setDefaults({
			errorElement:'span'
		});
	

	
	</script>






