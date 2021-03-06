<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	                		<select data-title-id="3020" data-result-id="${result3020}" value="${value3020}" data-type="14" id="investors" name="3020">
	                			<option value="">请选择</option>
	                			<c:forEach var="item" items="${investorList }">
	                			<option value="${item.id }">${item.name }</option>
	                			</c:forEach>
	                		</select>
	                    </div>
	                </dd>
	            </dl>
                <input type="hidden" name="projectId" id="projectId" value="${projectId}">
                <dl class="fmdl fl_l  clearfix">
	                <dt>计划总注资金额 ：</dt>
	                <dd>	
	                	<div id="setValue">
	                    		<input class=" txt " type="text" id="grantMoney" data-title-id="3004" data-result-id="${result3004}" data-type="19" name="1"  
	                    		value="<fmt:formatNumber value="${value3004}" pattern="#.######" maxFractionDigits="6" > </fmt:formatNumber>" onblur="set_finalValuations()"
	                    		data-rule-verify_96="true"  data-msg-verify_96="<font color=red>*</font>金额最大允许输入9位整数和6位小数" allowNULL="no" valType="LIMIT_11_NUMBER" />
	                    	<span class='money'>万元</span>
	                    </div> 
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>股权占比 ：</dt>
	                <dd>
	                	<div id="setValue">
	                    	<input class="txt" type="text" data-title-id="3010" data-result-id="${result3010}" data-type="19" size ="10" id="finalShareRatio" name="2" 
	                    		value="<fmt:formatNumber value="${value3010}" pattern="#.#####" maxFractionDigits="5" > </fmt:formatNumber>"  onblur="set_finalValuations()"
	                    		maxLength="20"  allowNULL="no" valType="OTHER"  data-rule-verify_35="true"  data-msg-verify_35="<font color=red>*</font>0到100之间的5位小数"/>
	                    	<span class='money'>%</span>
	                    </div>
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>加速服务费占比 ：</dt>
	                <dd>
	                	<div id="setValue"> <!-- regString="^(\d{1,2}(\.\d{1,4})?)$"   -->
	                    	<input class="txt" type="text" data-title-id="3011" data-result-id="${result3011}" data-type="19" size ="10" id="serviceCharge" name="3" value="<fmt:formatNumber value="${value3011}" pattern="#.####" maxFractionDigits="4" > </fmt:formatNumber>" 
	                    		maxLength="20"  allowNULL="no" valType="OTHER"  data-rule-verify_3011="true"  data-msg-verify_3011="<font color=red>*</font>0到5之间的两位小数"/>
	                    	<span class='money'>%</span>
	                    </div>
	                </dd>
	            </dl>
	              <dl class="fmdl fl_l  clearfix">
	                <dt>项目估值：</dt>
	                <dd>
	                	<div id="setValue">
	                	<input type="hidden" name="">
	                    	<input class="txt" type="text" data-title-id="3012" data-result-id="${result3012}" data-type="19" id="finalValuations" name="4" value="" 
	                    		maxLength="20"  allowNULL="no" valType="LIMIT_11_NUMBER" data-rule-verify_136="true"  data-msg-verify_136="<font color=red>*</font>金额最大允许输入13位整数和6位小数" />
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
	 
	$("#investors").val('${value3020}');
	
	
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
		set_finalValuations(1);
		var val1=$("#finalValuations").prev().val(),
		val2=$("#finalValuations").val(),
		val3=val1-val2;
		if(val3>10||val3<-10){
			layer.msg('项目估值的修改结果超出自动计算得出结论的 +/-10万');
			return;
		}
		var fields = $('#b_apprGrantTotal').find("input[type='text'][data-title-id],select[data-title-id]");
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
			if(type==19 || type==8 || type == 14)
			{	
				if(type == 14)
				{
					infoMode.value = field.val();
				}
				else
				{
					infoMode.remark1 = field.val();
				}
				if(field.data('titleId') == "3004"){   //特殊处理决策里面的投资金额
					infoMode.reportType=3;
				    if(field.hasClass('disabled')){
				    	infoMode.tochange=false;
				    }
					
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
						rightMoneyCount('${projectId}');
					}
                   
			});
	
	}
	
	//项目估值
	function set_finalValuations(val){
		var finalValuations_val;
		var val2 = $("#finalShareRatio").val();
		var val1 = $("#grantMoney").val(); 
	 	var res =finalValue (val1,val2)
		$("#finalValuations").prev().val(res);
		if(!val){ $("#finalValuations").val(res);}
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
	//格式化项目估值
	var _val='${value3012}';
	if(_val.indexOf('.')>-1){
		var num=_val.split('.');
		if(num[0].length>9){
			_val=_val;
		}else{
			_val=Number(_val).toFixed(6)
		}
	}
	_val = _parsefloat(_val);
	$('input[data-title-id="3012"]').val(_val);
function rightMoneyCount(proid){
	sendPostRequest(platformUrl.getApprProcess+"/"+proid,appropriationProcessBack);
	 function appropriationProcessBack(data){
	 	var result = data.result.status;
	 	if(result == "ERROR"){ //OK, ERROR
	 		layer.msg(data.result.message);
	 		return;
	 	}else{
	 		 var grantTotal = data.userData;
	 		 var sumPlanMoney=grantTotal.sumPlanMoney;
	 		 var sumActualMoney=grantTotal.sumActualMoney;
	 		 $("#planMoney").val(sumPlanMoney);
	 		  setData(sumPlanMoney,sumActualMoney);
	 		 if(typeof(sumActualMoney)=="underfined"||null==sumActualMoney||sumActualMoney==0){
	 			sumActualMoney=0;
	 		 }else{
	 			 if(sumActualMoney==0.000000){
	 				sumActualMoney=0;
	 			 }
	 		 }
	 		 if(null==sumPlanMoney||typeof(sumPlanMoney)=="underfined"||sumPlanMoney==0){
	 			    sumPlanMoney=0;
		 		 }else{
		 			 if(sumPlanMoney==0.00){
		 				sumPlanMoney=0;
		 			 }
		 		 }
	 		$(".money_complete").text(sumActualMoney);
	 		$(".money_total").text(sumPlanMoney);
	  	}
	 }
}
	</script>