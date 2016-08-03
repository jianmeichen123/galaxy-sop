<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc margin_45">

	<div class="title_bj">移交项目</div>
    <div class="form clearfix">
        <div class="role_all">
        	<ul>
            	<li>
                    <div class="fl width_150 align_r">接受部门：</div>
                    <div class="fl">
                        <select name='beforeDepartmentId' >
                           <option value="">请选择</option>
                        </select>&nbsp;&nbsp;<label class="red">&#42;&nbsp;必填</dd>
                    </div>
                </li>
                <li>
                    <div class="fl width_150 align_r">接收人：</div>
                    <div class="fl">
                         <select name='beforeUid' >
                           <option value="">请选择</option>
                         </select>&nbsp;&nbsp;<label class="red">&#42;&nbsp;必填</dd>
                   </div>
                </li>
                <li>
                    <div class="fl width_150 align_r">移交原因：</div>
                    <div class="fl"><textarea class="role_toolTip_area" name="transferReason"></textarea></div>
                </li>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
                    	<div class="button_affrim">
        <a href="javascript:;" class="register_all_affrim fl" action="save" >确认</a>
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