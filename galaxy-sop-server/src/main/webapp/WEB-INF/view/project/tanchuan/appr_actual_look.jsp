<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<div class="addmentc">
		<div class="title_bj" id="popup_name_look">查看实际注资信息</div>
	    <div class="form clearfix">
	        <div class="edit_actual">
	          
	                <form id="actual_aging_form">
		           
	                <dl class="fmdl fl_l  clearfix">
	                <dt>实际注资名称 ：</dt>
	                <dd>
	                	<div id="grantName">
	                    </div>
	                </dd>
	                </dl> 
	                <dl class="fmdl fl_l  clearfix">
		                <dt>实际注资时间 ：</dt>
		                <dd>
		                	<div id="grantDetail">
		                    </div> 
		                </dd>
		            </dl>
	                 <dl class="fmdl fl_l  clearfix">
		                <dt>实际注资金额 ：</dt>
		                <dd>
		                	
		                	<div class='moeny_all'>
		                	    <span id="grantMoney"></span>
		                    	<span id="editMoney" class="bj_hui"></span><span>万元</span>
		                    	
		                    </div> 
	                        <div class="gray">剩余金额<span id="formatRemainMoney"></span>万元</div> 
		                </dd>
		            </dl>
                 </form>
		            <dl class="fmdl fl_l" id="show_up_file">
		                 <table style="width:530px;margin: auto;" id="filelist"  cellspacing="0" cellpadding="0">
		                 <thead>
		                    <tr>
		                      <th style="width:265px;">文件名称</th>
		                      <th style="width:105px" align="center">文件大小</th>
		                    </tr>
		                 </thead>
		                 </table> 
		            </dl>
               
	        </div>
	        
	        
	    </div>
	    <div class="button_affrim">
	    
	    </div>  	
	</div>