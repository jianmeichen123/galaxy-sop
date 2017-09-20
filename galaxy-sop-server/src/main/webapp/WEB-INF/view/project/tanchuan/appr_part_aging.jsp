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
	    <div class="form clearfix" id="actual_aging_container">
	        <div class="appr_aging">
	           <form id="actual_aging_form">
	               <dl class="fmdl fl_l  clearfix">
	               <input type="hidden" id="projectId" name="projectId" value=""/>
	               <input type="hidden" id="grantId" data-name="id" data-type="19" name="id" value=""/>
		            <dt>分拨名称 ：</dt>
		                <dd>
		                	<div>
		                    	<input class="edittxt" id="grantDetail" data-name="field1" data-type="19" type="text" value="" maxLength="20" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>只能输入20个字符"/>
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
		                    	<input class=" txt " id="grantMoney" data-name="field3" data-type="19" type="text" value="" allownull="no" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持9位长度的四位小数"/>
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
	<script>
	   $(function(){
		   /* var remainMoney = '${remainMoney}';
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
			            
		  }) */
		  
	   });
	   var data = {};
	   var infoTableModelList = new Array();
	   $("#win_ok_btn").click(function(){
		    var key = keyJSON["b_part"];
			var deleteids = deleteJSON["partDelFile"];
			var projectId = $("#projectId").val();
			data.projectId = projectId;
			
			var fileLength1 = $("#filelist tr").length;
			var fs = "";
			$('#filelist tr').each(function(){
				 var id = $(this).attr("id");
				 if(id != undefined)
				 {
					 id = id.replace("tr", "");
					 if(!isNaN(id)){
						 if(fs){
							 fs = fs +","+id;
					     }else{
					    	 fs = id;
					     }
					 }
				 }
				
			});
			var fields = $.find("input[type='text'][data-type],textarea");
			var id =  $("#grantId").val();
			if(!id){
				id = null;
			}
			var infoMode = {
					titleId	: "3022",
					subCode:"grant-part",
					id:id,
					rowId:id,
					field5:parseInt(fileLength1) - 1
			};
			$.each(fields,function(){
				var field = $(this);
				var type = field.data('type');
				var name = field.data('name');
				
				if(type==19)
				{	
					infoMode[name] = field.val();
				}
			});
			
			var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
			var params = {};
			params.projectId =  projectInfo.id;
			params.fileReidsKey = key;
			params.deleteids = deleteids;
			$("body").showLoading();
			console.log("保存的 data");
			console.log(data);
			sendPostRequestByJsonObjNoCache(sendFileUrl,params,false,function(dataParam){
				//进行上传
				var result = dataParam.result.status;
				if(result == "OK"){
					var fids = dataParam.entity.fids;
					if(fids != null && fids != undefined){
						fs=fs+","+fids;
					}
					if(fs){
						infoMode.relateFileId = fs;
					}
					if (infoMode != null) {
						infoTableModelList.push(infoMode);
				    } 
					data.infoTableModelList = infoTableModelList;
					console.log("测试测试566:"+JSON.stringify(data));
					sendPostRequestByJsonObjNoCache(
							platformUrl.saveOrUpdateInfo , 
							data,
							true,
							function(data) {
								var result = data.result.status;
								if (result == 'OK') {
									$("#powindow").remove();
									$("#popbg").remove();
									$("body").hideLoading();
									initTabAppropriation(pId);
								} else {
									layer.msg("操作失败!");
								}
						});
				}else{
					layer.msg("操作失败!");
				}
				
			});
	   	
	   });

	
	</script>
