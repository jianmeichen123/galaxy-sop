<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<div id="post-meeting-dialog">
	<form id="win_post_meeting_form">
	<div class="editPostMeetingtc">
		<div class="title_bj" id="popup_name">添加运营会议纪要</div>
	    <div class="form clearfix">
	        <div class="conference_all">
	            <dl class="fmdl fl_l  clearfix">
	                <dt>会议时间 ：</dt>
	                <dd >
	                	<div id="div_meetingDateStr">
	                		<input type="hidden" name="id" id="id" value="0"/>
	                		<input type="hidden" name="meetingName" value="0" id="meetingName"/>
	                    	<input type="text" class="datetimepickerHour txt time" id="meetingDateStr" readonly="readonly" name="meetingDateStr" valType="required" msg="<font color=red>*</font>会议时间不能为空"  />
	                    </div> 
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l clearfix" id="edit_meeting_type">
	            		<dt>会议类型 ：</dt>
	                 	<dd><label for=""><input name="meetingType" type="radio" >周纪要</label></dd>
	                	<dd><label for=""><input name="meetingType" type="radio">月纪要</label></dd>
	                 	<dd><label for=""><input name="meetingType" type="radio">季度纪要</label></dd>   
	            </dl>
	            <dl class="fmdl fl_l clearfix">
	                 <dt>会议纪要 ：</dt>
	                 <dd>
	                 	<div id="div_meetingNotes">
	                 		<textarea class="area" name="meetingNotes" id="meetingNotes" cols="45" rows="5" valType="required" msg="<font color=red>*</font>会议纪要不能为空"></textarea>
	                 	</div>
	                 </dd>
	            </dl>  
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

            <div style='display:block;' id="div_show_up_file">
            	<dl class="fmdl fl_l" id="show_up_file">
	                 <table style="width:530px;margin: auto;" id="filelist" cellspacing="0" cellpadding="0">
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
	    </div>
	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn"  class="register_all_affrim fl">确定</a>
	        <a href="javascript:;" id="win_cancel_btn"  class="register_all_input fr">取消</a>
	    </div>  	
	</div>
	</form>
</div>

<!-- time -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<%-- <script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForHour.js"></script> --%>
<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<script src="<%=path %>/js/jquery.showLoading.min.js"></script>

<script type="text/javascript">
	//初始化时间
	$('.datetimepickerHour').datetimepicker({
	       inline: true,
	       sideBySide: true,
	       language: "zh-CN",
	       autoclose: true,
	       todayHighlight: false,
		    today: "Today",
		    todayBtn:'linked',
		    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
		    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
		    forceParse:false,
		    currentText: 'Now',
		   defaultDate : new Date(),
		   todayBtn:  1
	    }).on('changeDate', function(ev){
	    	$('.tip-yellowsimple').each(function(){
	    		if( $(this).text()=='*会议时间不能为空'){
	    			 $(this).remove();
	    		}
	    	})
	       //alert( $('.tip-yellowsimple').text()=='*会议时间不能为空');
	    });
</script>