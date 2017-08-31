<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<script src="<%=path %>/js/validate/messages_zh.min.js"></script>

<div class="addmentc errortc">
	<div class="title_bj" id="delivery_popup_name"></div>
	
    <div class="form" id="container">
    
        <div class="conference_all">
        	<form action="" id="detail-form">
         <input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="updateTimeStr" type="hidden" >
    	<input name="updateTimeSign" type="hidden" >
    	<input name="field3" type="hidden" value="2175">
    	<input name="updateUserName" type="hidden" id="updateUserName" >
    	<input name="titleId" type="hidden">
            <dl class="fmdl clearfix">
                <dt>事项简述：</dt>
                <dd>
                    <input type="text" class="txt"  name="field1" oninput="change(event)" onporpertychange="change(event)"  maxlength="24" />
                </dd>
            </dl>
            
            <dl class="fmdl fl_l">
                 <dt>详细内容：</dt>
                 <dd>
                	 <textarea class="area" name="field2"  id="area_textarea" oninput="change(event);countChar('area_textarea','label_now_next','100')" cols="45" rows="5"  ></textarea>
                 	 <p class="num_tj"><span for="" id="label_now_next">100</span>/100</p>
                 </dd>
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
                 	<dd name="field2" class="textarea"></dd>
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
	  
       $("#updateUserName").val($(".name").text());
	   var time = new Date().format("yyyy-MM-dd");
	   $("input[name=updateTimeStr]").val(time);
	   $("input[name=updateTimeSign]").val(1);
   }
</script>