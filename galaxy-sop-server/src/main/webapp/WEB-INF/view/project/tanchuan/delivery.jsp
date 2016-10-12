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
	<div class="title_bj" id="popup_name"></div>
	
    <div class="form clearfix" id="container">
        <div class="conference_all">
        	<form id="deliver_form" >
        	<input type="hidden" name="projectId" value="" />
            <dl class="fmdl clearfix">
                <dt>事项简述：</dt>
                <dd>
                    <input type="text" class="txt"  id="delDescribe" name="delDescribe"  maxlength="24" valType="required" msg="<font color=red>*</font>事项简述不能为空" />
                </dd>
            </dl>
            
            <dl class="fmdl fl_l">
                 <dt>详细内容：</dt>
                 <dd><textarea class="area" name="details" id="details" cols="45" rows="5" maxlength="100" valType="required" msg="<font color=red>*</font>详细内容不能为空"></textarea></dd>
            </dl>
            
            <dl class="fmdl fl_l">
                 <dt>完成情况：</dt>
                 <dd><label for=""><input type="radio" name="delStatus" value="0" checked="checked" >未完成</label></dd>
                 <dd><label for=""><input type="radio" name="delStatus" value="1">已完成</label></dd>
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
			        	<a href="javascript:;"  class="register_all_affrim fl" id="select_btn">选择附件</a>
		    		</dd>
		        </div>
            </dl>  
            <dl class="fmdl fl_l" id="show_up_file">
                 <table style="width:530px;margin: auto;" id="filelist"  cellspacing="0" cellpadding="0">
                    <tr>
                      <th style="width:265px;">文件名称</th>
                      <th style="width:105px" align="center">文件大小</th>
                      <th style="width:80px" align="center">操作</th>
                      <th style="width:80px" align="center">进度</th>
                    </tr>
                 </table> 
            </dl>
        </div>
    </div>
    
    
    <div class="button_affrim" id="choose_oper">
        <a href="javascript:;"  class="register_all_affrim fl" id="save_file">确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
  	
</div>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>