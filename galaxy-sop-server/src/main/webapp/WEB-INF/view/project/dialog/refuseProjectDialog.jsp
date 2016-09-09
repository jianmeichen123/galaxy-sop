<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />
<div class="addmentc margin_45">
	<div class="title_bj">否决原因</div>
    <div class="form clearfix">
        <div class="role_all">
        <form action="" id="form_refuse_project" method="post">
        	<ul>	
        		<li class='pocein'>
        			<p class="tips deltc">
					<b class="null tips_d">ico</b>
					你确定要否决项目吗？
					</p>
				</li>
                <li>
                    <div class="fl width_150 align_r"><font color=red>*</font>否决原因：</div>
                    <div class="fl"><textarea  class="role_toolTip_area" name="reason" maxlength="100" valType="required" msg="<font color=red>*</font>原因不能为空"></textarea></div>  
                </li>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
                    <div class="button_affrim">
                    <a href="javascript:;" class="register_all_affrim fl" id="button_confirm" >确认</a>
                    <a href="javascript:;" class="register_all_input fr" data-close="close" >取消</a>
                 </div>
                    </div>
                </li>
           
            </ul>
           </form>   
        </div>
    </div>
    
  	
</div>

<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>