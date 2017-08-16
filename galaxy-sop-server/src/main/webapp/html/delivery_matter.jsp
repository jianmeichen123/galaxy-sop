<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">

<div class="addmentc">
	<div class="title_bj" id="delivery_popup_name"></div>
	
    <div class="form clearfix" id="container">
    
        <div class="conference_all">
        	<form action="" id="detail-form">
         <input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="updateTimeStr" type="hidden" >
    	<input name="updateUserName" type="hidden" value="${realName }">
    	<input name="titleId" type="hidden">
            <dl class="fmdl clearfix">
                <dt>事项简述：</dt>
                <dd>
                    <input type="text" class="txt"  name="field1" oninput="change(event)" onporpertychange="change(event)"  maxlength="24" valType="required" msg="<font color=red>*</font>事项简述不能为空"/>
                </dd>
            </dl>
            
            <dl class="fmdl fl_l">
                 <dt>详细内容：</dt>
                 <dd><textarea class="area" name="field2" oninput="change(event)" onporpertychange="change(event)" cols="45" rows="5" maxlength="100" valType="required" msg="<font color=red>*</font>详细内容不能为空"></textarea></dd>
            </dl>
            
            </form>
            <!-- 查看显示下面内容 -->
            <div class="see_block">
            	<dl class="fmdl clearfix">
                	<dt>事项简述：</dt>
                	<dd name="field1"></dd>
            	</dl>
            
            	<dl class="fmdl fl_l">
                	 <dt>详细内容：</dt>
                 	<dd name="field2"></dd>
           		</dl>
            </div>
            
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
      
    })
    $.validator.setDefaults({
        	errorElement:'span'
        });
   function change(){
	   var time = new Date().format("yyyy-MM-dd hh:mm:ss");
	   $("input[name=updateTimeStr]").val(time)
   }
</script>