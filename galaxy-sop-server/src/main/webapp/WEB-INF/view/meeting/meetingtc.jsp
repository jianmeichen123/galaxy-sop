<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>


<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<!-- 富文本编辑器 -->
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- time 
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDate.js"></script>
-->
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForHour.js"></script>
<!-- 保存进度条 -->
<link href="<%=path %>/css/showLoading.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>

<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<div class="title_bj">新增会议</div>
<div class="meetingtc margin_45" id="add_meet_tc">
	<%-- <div class="top clearfix">
    	<div class="searchall clearfix" id="proselect">
            <dl>
            	<dt>项目 :</dt>
                <dd>
                	<select id="projectId" name="projectId"  
                	valType="required" msg="<font color=red>*</font>项目不能为空">
                    </select>
               	 <!-- <input type="text" placeholder="请输入关键字查找" class="txt"/> -->
                </dd>
            </dl>
            <!-- <a href="javascript:;" class="searchbtn null">搜索</a> -->
        </div>
        <dl class="fmdl clearfix fl">
            <dt>会议召开时间：</dt>  <!-- class="form-control"  -->
            <dd>
            	<input type="text" class="datetimepickerHour txt time" readonly  id="meetingDateStr" name = "meetingDateStr"  style="height:23px;width:150px;"
            	valType="required" msg="<font color=red>*</font>会议日期不能为空"  />
            </dd>
        </dl>
    </div> --%>
    
    
    <div class="min clearfix">
        <dl class="fmdl fml clearfix">
            <dt>会议召开时间：</dt>  <!-- class="form-control"  -->
            <dd  class="clearfix">
            	<input type="text" class="datetimepickerHour txt time" readonly  id="meetingDateStr" name = "meetingDateStr"  
            		style="height:23px;width:150px;"
            		valType="required" msg="<font color=red>*</font>会议日期不能为空"  />
            </dd>
        </dl>
        
        <dl class="fmdl clearfix" id="proselect">
        	<dt>项目 :</dt>
            <dd class="clearfix">
            	<select id="projectId" name="projectId"  valType="required" msg="<font color=red>*</font>项目不能为空">
                </select>
            </dd>
        </dl>
    </div>
    
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix" id="meet_type_dl">
            <dt>会议类型：</dt>
            <dd class="clearfix" id="meetTypeRadio">
                <label><input type="radio" name="meetingTypeTc" value="meetingType:1" checked="checked" onchange="meetTypeValChangeFun()" />内评会</label>
                <label><input type="radio" name="meetingTypeTc" value="meetingType:2" onchange="meetTypeValChangeFun()"/>CEO评审</label>
                <label><input type="radio" name="meetingTypeTc" value="meetingType:3" onchange="meetTypeValChangeFun()"/>立项会</label>
                <label><input type="radio" name="meetingTypeTc" value="meetingType:4" onchange="meetTypeValChangeFun()"/>投决会</label>
            </dd>
        </dl>
        <dl class="fmdl clearfix">
            <dt>会议结论：</dt>
            <dd class="clearfix">
                <label><input type="radio" name="meetingResult" value="meetingResult:1" onchange="meetTypeValChangeFun()" />通过</label>
                <label><input type="radio" name="meetingResult" value="meetingResult:2" checked="checked" onchange="meetTypeValChangeFun()" />待定</label>
                <label><input type="radio" name="meetingResult" value="meetingResult:3" onchange="meetTypeValChangeFun()" />否决</label>
            </dd>
        </dl>
    </div>

     
    <div class="min clearfix toShow_tjh" style="display:none;">
		<dl class="fmdl fml clearfix">
	        <dt><em class="red">*</em>投资金额:</dt>
	        <dd class="clearfix" id="meetTypeRadio">
	           	<input type="text" class='new_nputr_number' id="finalContribution" name="finalContribution"  onblur="set_finalValuations()"
	           		allowNULL="no" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>不能为空且最多支持九位整数四位小数"/>
	            <span class='m_r50'>万元</span>
	            <!-- 
	            <div id="finalContribution_valiate" class="tip-yellowsimple" >
					<div class="tip-inner tip-bg-image">
						<font color="red">*</font>不能为空且最多支持四位小数
					</div>
					<div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
				</div>
				 -->
			</dd>
	    </dl>
	    
	    <dl class="fmdl clearfix">
	        <dt><em class="red">*</em>项目估值:</dt>
	        <dd class="clearfix">
	           	<input type="text" class='new_nputr_number' id="finalValuations" name="finalValuations"  
	           		allowNULL="no" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>不能为空且最多支持九位整数四位小数"/>
	           	<span class='m_r50'>万元</span>
	           	<!-- 
	           	<div id="finalValuations_valiate" class="tip-yellowsimple" >
					<div class="tip-inner tip-bg-image">
						<font color="red">*</font>不能为空且最多支持四位小数
					</div>
					<div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
				</div>
				 -->
	        </dd>
	    </dl>
	</div>
	
	<div class="min clearfix toShow_tjh" style="display:none;">
		<dl class="fmdl fml clearfix">
	        <dt><em class="red">*</em>股权占比:</dt>
	        <dd class="clearfix" id="meetTypeRadio">
	           	<input type="text" class='new_nputr_number' id="finalShareRatio" name="finalShareRatio"  onblur="set_finalValuations()" 
	           		allowNULL="no" valType="OTHER" regString="^(\d{1,2}(\.\d{1,4})?)$" msg="<font color=red>*</font>0到100之间的四位小数"/>
	           	<span class='m_r50'>% </span>
	           	<!-- 
	           	<div id="finalShareRatio_valiate" class="tip-yellowsimple" >
					<div class="tip-inner tip-bg-image">
						<font color="red">*</font>不能为空且最多支持四位小数
					</div>
					<div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
				</div>
				 -->
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	        <dt><em class="red">*</em>加速服务费占比:</dt>
	        <dd class="clearfix" id="meetTypeRadio">
	           	<input type="text" class='new_nputr_number' id="serviceCharge" name="serviceCharge" 
	           		allownull="no" valtype="OTHER" regstring="^([0-4](\.\d{1,4})?)$|^(5(\.[0]{1,4})?)$" msg="<font color=red>*</font>0到5之间的四位小数" />
	           	<span class='m_r50'>% </span>
	           	<!-- 
	           	<div id="serviceCharge_valiate" class="tip-yellowsimple" >
					<div class="tip-inner tip-bg-image">
						<font color="red">*</font>不能为空且最多支持四位小数
					</div>
					<div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
				</div>
				 -->
	        </dd>
	    </dl>
	</div>
    
    <dl class="fmdl clearfix">  <!-- class="um_width" -->
       <dt>会议纪要:</dt>
       <dd>
       	  <%-- <div type="text/plain" id="meetingNotes"  style="width:100%;height:150px;max-height:150px;overflow:auto;" 
       	 	 valType="requiredDiv" regString="^.{0,9000}$" msg="<font color=red>*</font>会议纪要不能超过9000字节" >
       	  </div> --%>
       	  
       	  <div type="text/plain" id="meetingNotes"  style="width:100%;height:110px;max-height:150px;overflow:auto;" 
       	 	 valType="MAXBYTE" regString="9000" msg="<font color=red>*</font>会议纪要不能超过9000字节" >
       	  </div>
       	  
		</dd>
      </dl>
        
    <dl class="fmdl clearfix" id="fileNotBeUse">
        <dt>会议录音：</dt>
        
        <div class="fmload clearfix">
            <dd>
	        	<input type="text" name="fileName" id="fileName" class="txt" readonly="readonly" />
	        </dd>
	        <dd>
	        	<a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">上传录音</a>
    		</dd>
        </div>
    </dl>
    <div class="btnbox" id="btnNotBeUse">
    	<a href="javascript:;" class="pubbtn bluebtn" id="savemeet">保存</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>

 <script type="text/javascript">
	var meetEditor = UM.getEditor('meetingNotes');
		$("#meetingDateStr").val(new Date().format("yyyy-MM-dd hh:mm"));
		$('#meetingDateStr').datetimepicker({
		    format: 'yyyy-mm-dd hh:mm',
		    autoclose: true,
		    minView: 0,
		    minuteStep:5
		}); 
</script>

