<%@ page language="java" pageEncoding="UTF-8"%>
<div id="post-meeting-dialog">
	<div class="editPostMeetingtc">
		<div class="title_bj" id="popup_name"></div>
	    <div class="form clearfix">
	        <div class="conference_all">
	            <dl class="fmdl clearfix">
	                <dt>会议时间：</dt>
	                <dd>
	                    <input name="projectTime" id="projectTime" value="2016-07-01" type="hidden">
	                    <input class="datetimepickerHour txt time" style="height:24px;" id="meeting_date" readonly="" value="" type="text">
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l">
	                 <dt>类型 ：</dt>
	                 <dd><label for=""><input type="radio">周纪要</label></dd>
	                 <dd><label for=""><input type="radio">月纪要</label></dd>
	                 <dd><label for=""><input type="radio">季度纪要</label></dd>
	            </dl>
	            <dl class="fmdl fl_l">
	                 <dt>会议纪要 ：</dt>
	                 <dd><textarea class="area" name="textarea2" id="textarea2" cols="45" rows="5"></textarea></dd>
	            </dl>  
	            <div class="affrim_line"></div> 
	        </div>
	    </div>
	    <div class="button_affrim">
	        <a href="javascript:;"  class="register_all_affrim fl">确认</a>
	        <a href="javascript:;"  class="register_all_input fr">取消</a>
	    </div>  	
	</div>
</div>