<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc margin_45">

	<div class="title_bj">拒接项目</div>
    <div class="form clearfix">
        <div class="role_all">
        	<ul>
        	<form action="" id="refuse_form" method="post">
        	<input type="hidden" name="projectId" value="">
                <li>
                    <div class="fl width_150 align_r">拒接原因：</div>
                    <div class="fl"><textarea  class="role_toolTip_area" name="refuseReason" maxlength="100"></textarea></div>
                    <div id="refuse-reason" class="tip-yellowsimple" style="position: absolute;visibility: hidden; left: 331px; top: 55px; width: 101px; opacity: 1;"><div class="tip-inner tip-bg-image"><font color="red">*</font>拒接原因不能为空</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;position: absolute;"></div></div>
                </li>
                </form>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
                    <div class="button_affrim">
                    <a href="javascript:;" class="register_all_affrim fl" id="refuseReason" >确定</a>
                    <a href="javascript:;" class="register_all_input fr" data-close="close">取消</a>
                 </div>
                    </div>
                </li>
            </ul>
               
        </div>
    </div>
    
  	
</div>