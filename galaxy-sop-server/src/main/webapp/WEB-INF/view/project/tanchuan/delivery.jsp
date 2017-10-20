<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!-- 校验 -->
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">

<div class="addmentc">
	<div class="title_bj" id="popup_name"></div>
	
    <div class="form clearfix grantPartTc" id="container">
        <div class="conference_all" >
        	<form id="deliver_form" >
	        <input type="hidden" id="grantId" data-name="id" name="id" value=""/>
        	<input type="hidden" id="projectId" name="projectId" value="" />
        	<input type="hidden" id="titleId" name="titleId" value="1810" />
        	<input name="updateTimeSign" data-name="updateTimeSign" data-type="19" type="hidden" >
        	<input name="updateTimeStr" data-name="updateTimeStr" data-type="19" type="hidden" >
            <dl class="fmdl clearfix">
                <dt>事项简述：</dt>
                <dd>
                    <input type="text" class="txt"  id="delDescribe" name="field1" data-name="field1" data-type="19" oninput="change(event)" onporpertychange="change(event)" maxlength="24" required/>
                </dd>
            </dl>
            
            <dl class="fmdl fl_l">
                 <dt>详细内容：</dt>
                 <dd><textarea class="area delivery_textarea" name="field2" data-name="field2" data-type="19" id="details" cols="45" rows="5" maxlength="100" oninput="change(event);"  required></textarea></dd>
            </dl>
            
            <dl class="fmdl fl_l">
                 <dt>完成情况：</dt>
                 <dd><label for=""><input type="radio" name="field3" data-name="field3" onclick="change(event)" data-type="19" value="2175" checked="checked" >未完成</label></dd>
                 <dd><label for=""><input type="radio" name="field3" data-name="field3" onclick="change(event)" data-type="19" value="2176">已完成</label></dd>
            </dl>
            
            </form>
            
            <div class="affrim_line"></div>
            
            
            <dl class="fmdl fl_l" id="choose_up_file">
		                 <dt>上传附件 ：</dt>
		                 <div class="fmload clearfix">
				            <dd>
					        	<input  type="text"  class="txt" name="textarea2" id="textarea2" readonly="readonly"></input>
					        </dd>
					        <dd>
					        	<a href="javascript:;"  class="register_all_affrim fl" onclick="change(event)" id="select_btn">选择附件</a>
				    		</dd>
				        </div>
		            </dl>  
		            <dl class="fmdl fl_l" id="show_up_file">
		                 <table style="width:500px;margin: auto;" id="filelist"  cellspacing="0" cellpadding="0">
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
    
    
    <div class="button_affrim" id="choose_oper">
        <a href="javascript:;"  class="register_all_affrim fl" id="win_ok_btn">确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
  	
</div>
<script src="<%=path %>/js/validate/jquery.validate.min.js" type="text/javascript"></script>  
<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/partFile.js"></script>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<script>
$("#deliver_form").validate({});
$.validator.setDefaults({
	errorElement:'span'
});

function change(){
	   var time = new Date().format("yyyy-MM-dd");
	   $("input[name=updateTimeStr]").val(time);
	   $("input[name=updateTimeSign]").val(1);
}
</script>