<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />
<div class="addmentc margin_45">
	<div class="title_bj abandon-title">删除原因</div>
    <div class="form clearfix">
        <div class="role_all">
        <form action="" id="form_refuse_project" method="post">
        <input type="hidden" name="projectId" value="">
        	<ul>	
        		<li class='pocein'>
        			<p class="tips deltc">
					<b class="null tips_d">ico</b>
					你确定要删除项目吗？
					</p>
					<p>删除创投项目会通知该项目投资经理</p>
				</li>
                <li>
                    <div class="fl width_150 align_r"><font color=red>*</font>删除原因：</div>
                    <div class="fl"><textarea  class="role_toolTip_area" name="deleteReason" maxlength="100" valType="required" msg="<font color=red>*</font>原因不能为空"></textarea></div>  
                </li>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
                    <div class="button_affrim">
                    <a href="javascript:;" class="register_all_affrim fl" id="button_confirm" >确定</a>
                    <a href="javascript:;" class="register_all_input fr" data-close="close" >取消</a>
                 </div>
                    </div>
                </li>
           
            </ul>
           </form>   
        </div>
    </div>
    
  	
</div>

<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<script>

/******************Save Start***********************/

	var validator = $("#button_confirm").validate({
						focusCleanup:true,
						onfocusout:false,
						onclick:false,
						focusCleanup:true
					});

$("#button_confirm").click(function(){
	if(!validator.form()){
		return;
	}
	var projectId = $("#form_refuse_project input[name='projectId']").val();
	var deleteReason  = $("#form_refuse_project textarea[name='deleteReason']").val();
	var data = {
			'id'	:	projectId,
			'deleteReason':deleteReason
		};
	var callback = function(data) {
			if (data.result.status != "OK") {
				layer.msg(data.result.message);
			} else {
				layer.msg("删除成功", {
					time : 1000
				}, function() {
					var url = $("#menus .on a").attr('href');
					window.location = url;
				});
			}

		};
		var _url = "<%=path %>/galaxy/project/deletePro?_="+new Date().getTime();
		
		sendPostRequestByJsonObj(_url, data, callback);
	});
	/******************Save End***********************/
</script>