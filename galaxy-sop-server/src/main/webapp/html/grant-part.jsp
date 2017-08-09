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
<div class="addmentc">
  <form id="detail-form">
		<input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="titleId" type="hidden">
    	<input name="subCode" type="hidden">
	    <div class="form clearfix" id="actual_aging_container">
	        <div class="appr_aging">
	         
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资时间 ：</dt>
		                <dd>
		                	<div>
		                    	<input class="edittxt"  name="field2" type="text" value="" maxLength="20" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>只能输入20个字符"/>
		                    </div> 
		                </dd>
		            </dl>
	                 <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资金额 ：</dt>
		                <dd>
		                	
		                	<div class='moeny_all'>
		                    	<input class=" txt "  name="field3" type="text" value="" allownull="no" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持9位长度的四位小数"/>
		                    	<span id="editMoney" class="bj_hui"></span>
		                    	<span class='money'>万元</span>
		                    </div> 
	                        <div class="gray">剩余金额<span id="formatRemainMoney"></span>万元</div> 
		                </dd>
		            </dl>
		             <dl class="fmdl fl_l">
                 <dt>付款条件：</dt>
                 <dd><textarea class="area" name="field4" cols="45" rows="5" valType="required" msg="<font color=red>*</font>详细内容不能为空">
                     </textarea>
                 </dd>
            </dl>
	        </div>
	    </div>
	    <div class="button_affrim">
	         <a href="javascript:;"  class="register_all_affrim fl" id="save-detail-btn">确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
	    </div>  	
	</div>
	<script>
	   $(function(){
		   var remainMoney = '${remainMoney}';
			  remainMoney = fixSizeDecimal(parseFloat(remainMoney),4);
	          $("#formatRemainMoney").html(remainMoney);	
	          var remainMoneyOld=$("#formatRemainMoney").text();
	         
		  $("#grantMoney").blur(function(){
			 var grantMoney=$("#grantMoney").val();
			 if(!beforeSubmitById("actual_aging_container")){
				 $("#formatRemainMoney").html(remainMoneyOld);
	 				return false;
	 			} 
			 if(grantMoney<0){
	 				$("#formatRemainMoney").html(remainMoney)
	 			 }else{
	 				var remainMoney = '${remainMoney}';
	 				var sremainMoneyNew=remainMoney-Number(grantMoney);
	 				remainMoneyNew = fixSizeDecimal(parseFloat(sremainMoneyNew),4);
	 				
	 				if( sremainMoneyNew < 0 || sremainMoneyNew == 0){
	 				    $("#formatRemainMoney").html("0");
	 				}else{
	 				    $("#formatRemainMoney").html(remainMoneyNew);
	 				      }	 
	 			 }
			            
		  })
		  
	   });
	
	</script>
