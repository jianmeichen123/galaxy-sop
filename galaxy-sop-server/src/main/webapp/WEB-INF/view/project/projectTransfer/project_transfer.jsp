<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
   // String projectid=request.getParameter("projectId");
  
%>
<div class="addmentc margin_45">
	<div class="title_bj">移交项目</div>
    <div class="form clearfix">
        <div class="role_all">
        	<ul>
        	<form action="" id="transfer_form" method="post">
            	<li>
            		<input type="hidden" name="projectId" value="">
                    <div class="fl width_150 align_r">接收部门：</div>
                    <div class="fl">
                        <select name='afterDepartmentId' style="width:150px;">
                           <option value="">请选择</option>
                        </select>
                        <div id="receive-did" class="tip-yellowsimple" style="visibility: hidden; left: 266px; top: 55px; width: 101px; opacity: 1;"><div class="tip-inner tip-bg-image"><font color="red">*</font>接收部门不能为空</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div></div>
                    </div>
                </li>
                <li>
                    <div class="fl width_150 align_r">接收人：</div>
                    <div class="fl">
                         <select name='afterUid' style="width:150px;">
                           <option value="">请选择</option>
                         </select>
                         <div id="receive-uid" class="tip-yellowsimple" style="visibility: hidden; left: 266px; top: 106px; width: 101px; opacity: 1;"><div class="tip-inner tip-bg-image"><font color="red">*</font>接收人不能为空</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div></div>
                   </div>
                </li>
                <li>
                    <div class="fl width_150 align_r">移交原因：</div>
                    <div class="fl"><textarea  class="role_toolTip_area" name="transferReason" maxlength="100"></textarea></div>
                    <div id="receive-reason" class="tip-yellowsimple" style="visibility: hidden; left: 332px; top: 157px; width: 101px; opacity: 1;"><div class="tip-inner tip-bg-image"><font color="red">*</font>移交原因不能为空</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div></div>
                </li>
                </form>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
                    	<div class="button_affrim">
        <a href="javascript:;" class="register_all_affrim fl" id="projectTransfer" action="save" >确定</a>
        <a href="javascript:;" class="register_all_input fr" data-close="close">取消</a>
    </div>
                    </div>
                </li>
            </ul>
               
        </div>
    </div>
    
  	
</div>

