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
                <li>
                    <div class="fl width_150 align_r">撤销移交原因：</div>
                    <div class="fl"><textarea class="role_toolTip_area" name="undoReason"></textarea></div>
                </li>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
                    <div class="button_affrim">
                    <a href="javascript:;" class="register_all_affrim fl" action="revokeTransfer" >确认</a>
                    <a href="javascript:;" class="register_all_input fr" data-close="close">取消</a>
                 </div>
                    </div>
                </li>
            </ul>
               
        </div>
    </div>
    
  	
</div>

<script>

/**
 * 查询事业线
 * @version 2016-08-03
 */
createCareelineOptions(platformUrl.getCareerlineList,"beforeDepartmentId",1);
/**
 * 根据事业线查询相应的投资经理
 * @version 2016-08-03
 */
createUserOptions(platformUrl.getUserList+$('select[name="beforeDepartmentId"]').val(), "beforeUid", 0);





</script>