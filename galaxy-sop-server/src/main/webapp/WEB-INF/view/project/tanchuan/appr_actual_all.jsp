<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<div class="addmentc">
		<div class="title_bj" id="popup_name"></div>
		<form action="" method="post" id="add_form">
	    <div class="form clearfix">
	        <div class="edit_actual" id="info">
	            <dl class="fmdl fl_l  clearfix">
	                <dt>协议名称 ：</dt>
	                <dd>
	                	<div>
	                    	<input class="edittxt" type="text" id="grantName" name="grantName"  valType="OTHER" regString="^[\u4e00-\u9fa5]{1,20}$" msg="<font color=red>*</font>协议名称只能输入20个汉字"/>
	                    </div>
	                </dd>
	            </dl>
                <input type="hidden" name="projectId" value="${projectId}">
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划拨款金额 ：</dt>
	                <dd>	
	                	<div>
	                    	<input class=" txt " type="text" id="grantMoney"  name="grantMoney"  valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持11位长度的两位小数">
	                    </div> 
	                </dd>
	            </dl>
	             
	        </div>
	    </div>

	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn" onclick="saveAppr()" class="register_all_affrim fl">确认</a>
	        <a href="javascript:;" id="win_cancel_btn" class="register_all_input fr">取消</a>
	    </div> 
	    	    </form> 	
	</div>
	<script>
	var 	formData;
	function saveAppr(){
		if(beforeSubmit()){
			sendPostRequestByJsonStr(platformUrl.addGrantTotal, $("#add_form").serializeObject(), function(data){
				if(!data){
					layer.msg("提交表单过于频繁!");
				}else if(data.result.status=="ERROR"){
					
						layer.msg("添加总拨款计划失败!");
				}else{
					layer.msg("添加总拨款计划成功!");
					forwardWithHeader(Constants.sopEndpointURL + "/galaxy/project/toAppropriation/${projectId}");
				}
				
			});
		}
	}
	</script>