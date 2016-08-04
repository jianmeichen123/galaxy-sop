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
                <li>
                    <div class="fl width_150 align_r">拒接项目原因：</div>
                    <div class="fl"><textarea class="role_toolTip_area" name="refuseReason"></textarea></div>
                </li>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
                    <div class="button_affrim">
                    <a href="javascript:;" class="register_all_affrim fl" action="refuseReason" >确认</a>
                    <a href="javascript:;" class="register_all_input fr" data-close="close">取消</a>
                 </div>
                    </div>
                </li>
            </ul>
               
        </div>
    </div>
    
  	
</div>