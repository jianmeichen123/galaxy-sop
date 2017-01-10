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
	                <dt>协议名称：</dt>
	                <input type="hidden"  name="id" id="totallId" >
	                <dd>
	                	<div>
	                    	<input class="edittxt" type="text" id="grantName" name="grantName" maxLength="20"  
	                    		valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>只能输入20个字符"/>
	                    </div>
	                </dd>
	            </dl>
	             <dl class="fmdl fl_l  clearfix">
	                <dt>投资方 ：</dt>
	                <dd>
	                	<div>
	                    	<input class="edittxt" type="text" id="investors" name="investors" maxLength="50"  
	                    		valType="OTHER" regString="^.{1,50}$" msg="<font color=red>*</font>只能输入50个字符"/>
	                    </div>
	                </dd>
	            </dl>
	             <dl class="fmdl fl_l  clearfix">
	                <dt>目标公司 ：</dt>
	                <dd>
	                	<div>
	                    	<input class="edittxt" type="text" id="projectCompany" name="projectCompany" value="${projectCompany }" 
	                    		maxLength="50"  valType="OTHER" regString="^.{1,50}$" msg="<font color=red>*</font>只能输入50个字符"/>
	                    </div>
	                </dd>
	            </dl>
                <input type="hidden" name="projectId" id="projectId" value="${projectId}">
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划总注资金额 ：</dt>
	                <dd>	
	                	<div id="setValue">
	                    	<%-- <input class=" txt " type="text" id="grantMoney"  name="grantMoney"  
	                    		value="${finalContributions}" onblur="set_finalValuations()"
	                    		valType="OTHER" regString="^(0(?:[.](?:[1-9]\d?|0[1-9]))|[1-9][0-9]{0,8}|[1-9][0-9]{0,8}\.[0-9]{1,4})$" 
	                    		msg="<font color=red>*</font>支持9位长度的四位小数" /> --%>
	                    	<input class=" txt " type="text" id="grantMoney"  name="grantMoney"  
	                    		value="${finalContributions}" onblur="set_finalValuations()"
	                    		valType="OTHER" regString="^(0(?:[.](?:[1-9]\d?|0[1-9]))|[1-9][0-9]{0,8}|[1-9][0-9]{0,8}\.[0-9]{1,4})$" msg="<font color=red>*</font>支持9位长度的四位小数"/>
	                    	<span class='money'>万元</span>
	                    </div> 
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>股权占比 ：</dt>
	                <dd>
	                	<div id="setValue">
	                    	<input class="txt" type="text" size ="10" id="finalShareRatio" name="finalShareRatio" 
	                    		value="${finalShareRatio}"  onblur="set_finalValuations()"
	                    		maxLength="20"  allowNULL="no" valType="OTHER" regString="^(\d{1,2}(\.\d{1,4})?)$" 
	                    		msg="<font color=red>*</font>0到100之间的四位小数" />
	                    	<span class='money'>%</span>
	                    </div>
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>加速服务费占比 ：</dt>
	                <dd>
	                	<div id="setValue"> <!-- regString="^(\d{1,2}(\.\d{1,4})?)$"   -->
	                    	<input class="txt" type="text" size ="10" id="serviceCharge" name="serviceCharge" value="${serviceCharge }" 
	                    		maxLength="20"  allowNULL="no" valType="OTHER" regstring="^([0-4](\.\d{1,4})?)$|^(5(\.[0]{1,4})?)$"
	                    		msg="<font color=red>*</font>0到5之间的四位小数" />
	                    	<span class='money'>%</span>
	                    </div>
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>项目估值：</dt>
	                <dd>
	                	<div id="setValue">
	                    	<input class="txt" type="text" id="finalValuations" name="finalValuations" value="${finalValuations }" 
	                    		maxLength="20"  allowNULL="no" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持四位小数"/>
	                    	<span class='money'>万元</span>
	                    </div>
	                </dd>
	            </dl>
	             
	        </div>
	    </div>

	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn" onclick="saveAppr()" class="register_all_affrim fl">确定</a>
	        <a href="javascript:;" id="win_cancel_btn" class="register_all_input fr" data-close="close">取消</a>
	    </div> 
	  </form> 	
	</div>
	
	
	<script>
	$(function(){
		if($("#grantMoney").val() == '0.0'){
			$("#grantMoney").val(null);
		}
		if($("#finalShareRatio").val() == '0.0'){
			$("#finalShareRatio").val(null);
		}
		if($("#serviceCharge").val() == '0.0'){
			$("#serviceCharge").val(null);
		}
		if($("#finalValuations").val() == '0.0'){
			$("#finalValuations").val(null);
		}
	});
	
	var 	formData;
	function saveAppr(){
		if(beforeSubmitById("add_form")){
			console.log($("#add_form").serializeObject());
			sendPostRequestByJsonStr(platformUrl.addGrantTotal, $("#add_form").serializeObject(), function(data){
				if(!data){
					layer.msg("提交表单过于频繁!");
				}else if(data.result.status=="ERROR"){
					if($("#grantName").val().trim().length<1){
						layer.msg("协议名称输入错误!");   //协议名称输入内容全为空格时
					}else if($("#investors").val().trim().length<1){
						layer.msg("投资方输入错误!");   //协议名称输入内容全为空格时
					}else{
						layer.msg(data.result.message);
					}
						
				}else{
					layer.msg(data.result.message);
					var numOfShow = $("#tabApprAllList .agreement:visible").length;
					var url = Constants.sopEndpointURL + "/galaxy/project/toAppropriation/null/${projectId}";
					if(numOfShow>0)
					{
						numOfShow = Math.max(numOfShow,2);
						url+="?numOfShow="+numOfShow;
					}
					$("#powindow").remove();
					$("#popbg").remove();
					$.getTabHtml({
						url : url
					});
					reference('${projectId}');
					//启用滚动条
					 $(document.body).css({
					   "overflow-x":"auto",
					   "overflow-y":"auto"
					 });
				}
				
			});
		}
	}
	
	
	//项目估值
	function set_finalValuations(){
		var finalValuations_val;
		var projectShareRatio = $("#finalShareRatio").val();
		var projectContribution = $("#grantMoney").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			finalValuations_val =  (projectContribution * (100/projectShareRatio)).toFixed(4);
		}
		if(finalValuations_val && finalValuations_val > 0){
			$("#finalValuations").val(finalValuations_val);
		}
	}
	</script>