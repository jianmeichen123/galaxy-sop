<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc margin_45">

	<div class="title_bj">撤销移交</div>
    <div class="form clearfix">
        <div class="role_all">
        	<ul>
        	<form action="" id="revoke_form" method="post">
        	<input type="hidden" name="projectId" value="">
                <li>
                    <div class="fl width_150 align_r">撤销移交原因：</div>
                    <div class="fl"><textarea  class="role_toolTip_area" name="undoReason" maxlength="100" ></textarea></div>
                    <div id="revoke-reason" class="tip-yellowsimple" style="visibility: hidden; left: 331px; top: 55px; width: 101px; opacity: 1;"><div class="tip-inner tip-bg-image"><font color="red">*</font>撤销原因不能为空</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div></div>
                </li>
                </form>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
                    <div class="button_affrim">
                    <a href="javascript:;" class="register_all_affrim fl" id="revokeTransfer" >确定</a>
                    <a href="javascript:;" class="register_all_input fr" data-close="close">取消</a>
                 </div>
                    </div>
                </li>
            </ul>
               
        </div>
    </div>
    
  	
</div>

<script>

</script>