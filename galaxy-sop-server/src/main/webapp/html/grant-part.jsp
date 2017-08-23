<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script src="<%=path %>/js/utils.js"></script>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<div class="addmentc qualificationstc">
	<div class="title_bj" id="grant_popup_name">添加分拨计划</div>
  <form id="detail-form">
		<input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="titleId" type="hidden">
    	<input name="subCode" type="hidden">
	    <div class="form clearfix" id="actual_aging_container">
	        <div class="appr_aging">
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资名称：</dt>
		                <dd>
		                    	<input class=" txt "  name="field1" type="text" value="" placeholder="分拨"/>
		                </dd>
		            </dl>
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资时间 ：</dt>
		                <dd>
		                	<div>
		                    	<input class="txt"  name="field2" type="text" value="" maxLength="20" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>只能输入20个字符"/>
		                    </div> 
		                </dd>
		            </dl>
	                 <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资金额 ：</dt>
		                <dd>
		                	
		                	<div class='moeny_all'>
		                    	<input class=" txt "  name="field3" type="text" value="" required data-rule-verify_94="true"  data-msg-verify_94="<font color=red>*</font>支持9位长度的四位小数"/>
		                    	<span id="editMoney" class="bj_hui"></span>
		                    	<span class='money'>万元</span>
		                    </div> 
	                        <div class="gray">剩余金额<span id="formatRemainMoney"></span>万元</div> 
		                </dd>
		            </dl>
		             <dl class="fmdl fl_l">
                 <dt>付款条件：</dt>
                 <dd>
                 	<textarea class="area" name="field4" id="now_area" oninput="countChar('now_area','label_now_next','2000')" valType="required" msg="<font color=red>*</font>详细内容不能为空"></textarea>
                 	<p class="num_tj"><span for="" id="label_now_next">2000</span>/2000</p>
                 </dd>
            </dl>
	        </div>
	    </div>
	    </form>
	    <!-- 查看显示下面内容 -->
            <div class="see_block">
            	<div class="appr_aging">
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资名称：</dt>
		                <dd>
		                    	<dd name="field1"></dd>
		                </dd>
		            </dl>
	                <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资时间 ：</dt>
		                <dd>
		                	<dd name="field2"></dd>
		                </dd>
		            </dl>
	                 <dl class="fmdl fl_l  clearfix">
		                <dt>计划注资金额 ：</dt>
		                <dd name="field3" class="money"></dd>
		               	<dd class="remainMoney gray">剩余金额<span></span>万元</dd>
		                
		            </dl>
		        <dl class="fmdl fl_l">
                 <dt>付款条件：</dt>
                 <dd name="field4" class="textarea"></dd>
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
		 $("#detail-form").validate({});
		$.validator.setDefaults({
			errorElement:'span'
		});
	})
	remainMoney();
	function remainMoney(){   //计算剩余金额
		var params={};
		params.projectId = projectInfo.id;
		params.titleId = "3004";
		sendPostRequestByJsonObj(
					Constants.sopEndpointURL+'/galaxy/infoProject/getTotalAppr' , 
					params,
					function(data){
						if(data.result.status == "OK"){
							if(typeof(data.userData) == "object"){
								if(data.userData.totalMoney || data.userData.remainMoney){
									var remainMoney=data.userData.remainMoney;
									$("#formatRemainMoney").text(remainMoney == null ? 0 : remainMoney);
									$(".moeny_all input").on("input",function(){
										var val=$(this).val();
										if(val>0){
											var remainMoneyNew=remainMoney-val;
											$("#formatRemainMoney").text(remainMoneyNew < 0 ? 0 : remainMoneyNew);
										}
									})
								}
							}
						}
					});
	}
	
	</script>
