<%@ page language="java" pageEncoding="UTF-8"%>
<div class="meetingtc">
	<div class="top clearfix">
    	<div class="searchall clearfix">
            <dl>
            	<dt>项目 :</dt>
                <dd>
                	<select id="projectId" name="projectId" >
                    </select>
               	 <!-- <input type="text" placeholder="请输入关键字查找" class="txt"/> -->
                </dd>
            </dl>
            <!-- <a href="javascript:;" class="searchbtn null">搜索</a> -->
        </div>
        <dl class="fmdl clearfix">
            <dt>会议召开日期：</dt>
            <dd>
            	<input class="form-control" type="date" id="meetingDateStr" name = "meetingDateStr" />
            </dd>
        </dl>
    </div>
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>会议类型：</dt>
            <dd class="clearfix">
                <label><input type="radio" name="meetingType" value="meetingType:1"/>内评会</label>
                <label><input type="radio" name="meetingType" value="meetingType:2"/>CEO评审</label>
                <label><input type="radio" name="meetingType" value="meetingType:3"/>立项会</label>
                <label><input type="radio" name="meetingType" value="meetingType:4"/>投决会</label>
            </dd>
        </dl>
        <dl class="fmdl clearfix">
            <dt>会议结论：</dt>
            <dd class="clearfix">
                <label><input type="radio" name="meetingResult" value="meetingResult:1"/>通过</label>
                <label><input type="radio" name="meetingResult" value="meetingResult:2"/>待定</label>
                <label><input type="radio" name="meetingResult" value="meetingResult:3"/>否决</label>
            </dd>
        </dl>
    </div>
    
    <dl class="fmdl clearfix">
       <dt>会议纪要:</dt>
       <dd>
       	  <div type="text/plain" id="meetingNotes" style="width:100%;height:100px;"></div>
		</dd>
      </dl>
        
    <dl class="fmdl clearfix">
        <dt>会议录音：</dt>
        
        <div class="fmload clearfix">
            <dd>
	        	<input type="text" name="fileName" id="fileName" class="txt" readonly="readonly"/>
	        </dd>
	        <dd>
	        	<a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">上传录音</a>
    		</dd>
        </div>
    </dl>
    <div class="btnbox"><!--  saveMeet() -->
    	<a href="javascript:;" class="pubbtn bluebtn" id="savemeet">保存</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>



<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>

 <script type="text/javascript">
		UM.getEditor('meetingNotes');
</script>

