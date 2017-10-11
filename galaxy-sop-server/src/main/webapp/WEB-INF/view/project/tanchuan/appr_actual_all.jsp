<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
	String path = request.getContextPath(); 
%>

<div class="addmentc">
		<div class="title_bj" id="popup_name"></div>
		<form action="" method="post" id="b_apprGrantTotal">
	    <div class="form clearfix">
	        <div class="edit_actual" id="info">
	             <dl class="fmdl fl_l  clearfix">
	                <dt>星河投资方主体 ：</dt>
	                <dd>
	                	<div>
	                    	<input class="edittxt" type="text" data-title-id="3020" data-result-id="${result3020}" value="${value3020}" data-type="8" id="investors" name="3020" value="" maxLength="50"  
	                    		valType="OTHER" regString="^.{1,50}$" data-rule-verify_3020="true"  data-msg-verify_3020="<font color=red>*</font>只能输入50个字符 "/>
	                    </div>
	                </dd>
	            </dl>
                <input type="hidden" name="projectId" id="projectId" value="${projectId}">
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划总注资金额 ：</dt>
	                <dd>	
	                	<div id="setValue">
	                    		<input class=" txt " type="text" id="grantMoney" data-title-id="3004" data-result-id="${result3004}" data-type="19" name="1"  
	                    		value="<fmt:formatNumber value="${value3004}" pattern="#.####" maxFractionDigits="4" > </fmt:formatNumber>" onblur="set_finalValuations()"
	                    		data-rule-verify_94="true"  data-msg-verify_94="<font color=red>*</font>支持9位长度的四位小数" allowNULL="no" valType="LIMIT_11_NUMBER" />
	                    	<span class='money'>万元</span>
	                    </div> 
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>股权占比 ：</dt>
	                <dd>
	                	<div id="setValue">
	                    	<input class="txt" type="text" data-title-id="3010" data-result-id="${result3010}" data-type="19" size ="10" id="finalShareRatio" name="2" 
	                    		value="<fmt:formatNumber value="${value3010}" pattern="#.####" maxFractionDigits="4" > </fmt:formatNumber>"  onblur="set_finalValuations()"
	                    		maxLength="20"  allowNULL="no" valType="OTHER"  data-rule-verify_3010="true"  data-msg-verify_3010="<font color=red>*</font>支持0到100之间的四位小数"/>
	                    	<span class='money'>%</span>
	                    </div>
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>加速服务费占比 ：</dt>
	                <dd>
	                	<div id="setValue"> <!-- regString="^(\d{1,2}(\.\d{1,4})?)$"   -->
	                    	<input class="txt" type="text" data-title-id="3011" data-result-id="${result3011}" data-type="19" size ="10" id="serviceCharge" name="3" value="<fmt:formatNumber value="${value3011}" pattern="#.####" maxFractionDigits="4" > </fmt:formatNumber>" 
	                    		maxLength="20"  allowNULL="no" valType="OTHER"  data-rule-verify_3011="true"  data-msg-verify_3011="<font color=red>*</font>支持0到5之间的四位小数"/>
	                    	<span class='money'>%</span>
	                    </div>
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>项目估值：</dt>
	                <dd>
	                	<div id="setValue">
	                    	<input class="txt" type="text" data-title-id="3012" data-result-id="${result3012}" value="<fmt:formatNumber value="${value3012}" pattern="#.####" maxFractionDigits="4" />" data-type="19" id="finalValuations" name="4" value="" 
	                    		maxLength="20"  allowNULL="no" valType="LIMIT_11_NUMBER" data-rule-verify_3012="true"  data-msg-verify_3012="<font color=red>*</font>支持13位长度的四位小数" />
	                    	<span class='money'>万元</span>
	                    </div>
	                </dd>
	            </dl>
	             
	        </div>
	    </div>

	    <div class="button_affrim">
	         <a href="javascript:;" onclick="save()" class="register_all_affrim fl">保存</a>
	        
	        <a href="javascript:;" id="win_cancel_btn" class="register_all_input fr" data-close="close">取消</a>
	    </div> 
	  </form> 	
	</div>
<!-- 跟全息图相似的 -->
<script src="<%=path%>/js/seven_report/seven_report_common.js"></script>
<script src="<%=path %>/js/seven_report/basic_fun.js" type="text/javascript"></script>
<script src="<%=path %>/js/seven_report/save_ok.js" type="text/javascript"></script>     	
	
	<script>
	 
	
	
	
	$(function(){
		if($("#grantMoney").val() == '0.0' || $("#grantMoney").val() == '0.00' || $("#grantMoney").val() == '0.0000'){
			$("#grantMoney").val(null);
		}
		if($("#finalShareRatio").val() == '0.0' || $("#finalShareRatio").val() == '0.00' || $("#finalShareRatio").val() == '0.0000'){
			$("#finalShareRatio").val(null);
		}
		if($("#serviceCharge").val() == '0.0' || $("#serviceCharge").val() == '0.00' || $("#serviceCharge").val() == '0.0000'){
			$("#serviceCharge").val(null);
		}
		if($("#finalValuations").val() == '0.0' || $("#finalValuations").val() == '0.00' || $("#finalValuations").val() == '0.0000'){
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
	var data = {
			projectId : '${projectId}'
		};
	var infoModeList = new Array();
	function save(){
		
		 if(!$("#b_apprGrantTotal").validate().form())
			{
				return false;
				
			}  
		var fields = $.find("input[type='text'][data-title-id]");
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var result = field.data('resultId');
			if(result==undefined){
				result=null;
			}
			var infoMode = {
				titleId	: field.data('titleId'),
				tochange:true,
				resultId:result,
				type : type
			};
			if(type==19 || type==8)
			{	
				infoMode.remark1 = field.val();
				if(field.data('titleId') == "3004"){   //特殊处理决策里面的投资金额
					infoMode.reportType=3;
				}
			}
			if (infoMode != null) {
		        infoModeList.push(infoMode);
		    } 
		});
		data.infoModeList = infoModeList;
		//JSON.stringify(jsonObj)
		
		sendPostRequestByJsonObjNoCache(
				platformUrl.saveOrUpdateInfo , 
				data,
				true,
				function(data) {
					var result = data.result.status;
					if (result == 'OK') {
						$("#powindow").remove();
						$("#popbg").remove();
						initTabAppropriation('${projectId}');
					}
                   
			});
	
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
	
	function getData(div){
		var json={};
	    var list = div.find("*[name]");
        $(list).each(function(){
           var key = "";
           var value = "";
           var tagName = $(this).get(0).tagName;

           if(tagName == "INPUT"|| tagName == "TEXTAREA"){
				 key = $(this).attr("name");
				 value = $(this).val();
           }
           if(tagName=="SELECT"){
              key = $(this).attr("name");
              value = $(this).find("option:selected").val();
           }
           if(tagName == "SPAN"){
             key = $(this).attr("name");
             value = $(this).text();
             if(!value){
             	value= null;
             }
           }
           json[key]=value;
     })

     return json;
	}
	
	
	
	$("#b_apprGrantTotal").validate({});
	 
	$.validator.setDefaults({
		errorElement:'span'
	});
	
	</script>