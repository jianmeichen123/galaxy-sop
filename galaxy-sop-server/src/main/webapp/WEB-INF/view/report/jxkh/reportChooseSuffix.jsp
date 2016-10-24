<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<style>
.radiosbox input{margin-left:20px;}
</style>
<div class="addmentc margin_45" id="reportChooseSuffix">
	<div class="title_bj">报表格式选择</div>
    <div class="form clearfix">
        <div class="role_all">
        	<ul>	
        		<li class='pocein'>
        			<p class="tips deltc">
					<b class="null tips_d">ico</b>
						请选择报表导出格式
					</p>
				</li>
                <li>
                <form id="chooseForm">
                    <div class="radiosbox" style="width:190px;margin:0 auto;">
                    	<input type="radio" name="suffix" value="xlsx" checked="checked">XLSX
                    	<input type="radio" name="suffix" value="csv">CSV
                    </div>  
                </form>
                </li>
                <li>
                    <div class="fl width_150 align_r">　</div>
                    <div class="fl">
	                    <div class="button_affrim">
	                    <a href="javascript:;" class="register_all_affrim fl" id="button_confirm" >确定</a>
	                    <a href="javascript:;" class="register_all_input fr" id="button_close" >取消</a>
	                 	</div>
                    </div>
                </li>
           
            </ul>
        </div>
    </div>
    
  	
</div>

