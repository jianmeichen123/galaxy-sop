<%@ page language="java" pageEncoding="UTF-8"%>
<div class="meetingtc">
	<div class="top clearfix">
    	<!-- <div class="searchall clearfix">
            <dl>
            	<dt>项目 :</dt>
                <dd>
                	&nbsp;&nbsp;<span id="LPH_project_name"></span>
                </dd>
            </dl>
        </div> -->
        
        <dl class="fmdl clearfix">
            <dt>会议召开日期：</dt>
            <dd>
            	<input class="form-control" type="date" id="TJH_meetingDateStr" />
            </dd>
        </dl>
    </div>
    
    <div class="min clearfix">
    	<!-- <dl class="fmdl fml clearfix">
            <dt>会议类型：</dt>
            <dd class="clearfix">
            	<label><span>内评会</span></label>
            	
                <label><input type="radio" name="meetingType" value="meetingType:1"/>内评会</label>
                <label><input type="radio" name="meetingType" value="meetingType:2"/>CEO评审</label>
                <label><input type="radio" name="meetingType" value="meetingType:3"/>立项会</label>
                <label><input type="radio" name="meetingType" value="meetingType:4"/>投决会</label>
            </dd>
        </dl> -->
        <dl class="fmdl clearfix">
            <dt>会议结论：</dt>
            <dd class="clearfix">
                <label><input type="radio" name="TJH_meetingResult" value="meetingResult:1"/>通过</label>
                <label><input type="radio" name="TJH_meetingResult" value="meetingResult:2"/>待定</label>
                <label><input type="radio" name="TJH_meetingResult" value="meetingResult:3"/>否决</label>
            </dd>
        </dl>
    </div>
    
    <dl class="fmdl clearfix">
       <dt>会议纪要:</dt>
       <dd>
       	  <div type="text/plain" id="TJH_meetingNotes" style="width:100%;height:100px;"></div>
		</dd>
      </dl>
        
    <dl class="fmdl clearfix">
        <dt>会议录音：</dt>
        
        <div class="fmload clearfix">
            <dd>
	        	<input type="text" name="fileName" id="TJH_fileName" class="txt" readonly="readonly"/>
	        </dd>
	        <dd>
	        	<a href="javascript:;" class="pubbtn fffbtn" id="TJH_file-select-btn">上传录音</a>
    		</dd>
        </div>
            
    </dl>
    <div class="btnbox"><!--  saveMeet() -->
    	<a href="javascript:;" class="pubbtn bluebtn" id="TJH_savemeet">保存</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>
<script src="${ctx}/js/plupload.full.min.js" type="text/javascript"></script>
 
 <script type="text/javascript">
		UM.getEditor('TJH_meetingNotes');
</script>