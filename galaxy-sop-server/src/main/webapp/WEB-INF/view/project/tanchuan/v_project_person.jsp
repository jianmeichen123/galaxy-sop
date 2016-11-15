<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addPersontc">
	<div class="title_bj" id="popup_name"></div>
	
	
    <div class="addPerson_all">
    
<!--     
type  name="personName"  name="personDuties"  name="personBirthdayStr"  name="personTelephone"

radio name="personSex"   
    --> 
        <div class="info clearfix">
        	<form id="person_form">
        	
        	<!-- 项目id -->
        	<input type="hidden" name="projectId" id="person_project_id" value="" />
        	
        	<!-- 项目的成员   人力资源id -->
        	<input type="hidden" name="id" id="person_pool_id" value="" />
        	
            <h3>基本信息</h3>
            <dl class="fmdl fl">
                <dt><em class="red">*</em>&nbsp;姓名：</dt>
                <dd>
                	<input type="text" class="txt" name="personName" valtype="required"  msg="<font color=red>*</font>姓名不能为空"/>
                </dd>
            </dl>
            
            <dl class="fmdl fl">
                <dt><em class="red">*</em>&nbsp;性别：</dt>
                <dd class="clearfix">
                    <label><input type="radio" name="personSex" value="0" checked="checked" />男</label>
                    <label><input type="radio" name="personSex" value="1" />女</label>
                </dd>
            </dl>
            
            <dl class="fmdl fl">
                <dt><em class="red">*</em>&nbsp;当前职务：</dt>
                <dd>
                	<input type="text" class="txt" name="personDuties" valtype="required"  msg="<font color=red>*</font>当前职务不能为空"/>
                </dd>
            </dl>
            
            <dl class="fmdl fl">
                <dt><em class="red">*</em>&nbsp;出生日期：</dt>
                <dd>
                    <input type="text" class="datetimepickerHour txt time" name="personBirthdayStr"
                    	readonly  value=""  valtype="required"  msg="<font color=red>*</font>出生日期不能为空" />
                </dd>
            </dl>
            
             <dl class="fmdl fl">
                <dt>电话号码：</dt>
                <dd>
                	<input type="text" class="txt" name="personTelephone" 
                		placeholder="请输入电话号码" />
                		<!--  valtype="MOBILE"  msg="<font color=red>*</font>电话号码格式不正确" -->
                </dd>
            </dl>
            
            <dl class="fmdl fl">
                <dt><em class="red">*</em>&nbsp;是否为联系人：</dt>
                <dd class="clearfix">
                    <label><input type="radio" name="isContacts" value="0" checked="checked" />是</label>
                    <label><input type="radio" name="isContacts" value="1" />否</label>
                </dd>
            </dl>
           
            <dl class="fmdl fl block">
                <dt>备注：</dt>
                <dd><textarea maxlength="50" name="remark" placeholder="50字以内"></textarea></dd>
            </dl>
            
            </form>
        </div>
        
        
        <div class="qualifications">
            <h3><em class="red">*</em>&nbsp;学历背景</h3>
            <span onclick="toAddPersonLearning(null);" class="blue fr add"  data-name="学历背景">添加</span>
            
            <div id="learning_table_custom_toolbar">
				<input type="hidden" name="personId" value="" />
			</div>
            <table  id="per_learning_table" 
               	data-url="<%=path %>/galaxy/project/queryProPerLearn" data-method="post" 
	       		data-toolbar="#learning_table_custom_toolbar" data-id-field="deleteIndex" data-unique-id="deleteIndex" >
				<colgroup >
					<col style="width:0;">
					<col style="width:25%;">
					<col style="width:15%;">
					<col style="width:30%;">
					<col style="width:10%;">
					<col style="width:20%;">
				</colgroup>
				<thead>
					<tr>
						<th data-field="deleteIndex"  data-align="center" data-formatter="deleteIndex_Format" ></th>
						<th data-field="school"  data-align="center" >毕业院校</th>
		                <th data-field="major"  data-align="center" >专业</th>
	                     
	                     <!--  beginDate overDate beginDateStr overDateStr -->
	                     <th data-field="BE_time"  data-align="center" data-formatter="learn_TimeFormat">时间</th>
	                     <th data-field="degree"  data-align="center" >学历</th>
	                     <th data-field="learn_op" data-formatter="pro_learning_format">操作</th>
					</tr>
				</thead>
			</table> 
        </div>
        
        
        
        <div class="qualifications">
            <h3><em class="red">*</em>&nbsp;工作履历</h3>
            <span  onclick="toAddPersonWork(null);" class="blue fr add"  data-name="工作履历" >添加</span>
            
            <div id="work_table_custom_toolbar">
				<input type="hidden" name="personId" value="" />
			</div>
            <table id="per_work_table" 
               	data-url="<%=path %>/galaxy/project/queryProPerWork" data-method="post" 
	       		data-toolbar="#work_table_custom_toolbar" data-id-field="deleteIndex" data-unique-id="deleteIndex" >
				<colgroup >
					<col style="width:0;">
					<col style="width:30%;">
					<col style="width:30%;">
					<col style="width:20%;">
					<col style="width:20%;">
				</colgroup>
				<thead>
					<tr>
						<th data-field="deleteIndex"  data-align="center" data-formatter="work_deleteIndex_Format" ></th>
						<!--  beginWork overWork beginWorkStr overWorkStr -->
						<th data-field="BE_time"  data-align="center" data-formatter="work_TimeFormat">时间</th>
						
						<th data-field="companyName"  data-align="center" >任职公司名称</th>
		                <th data-field="workPosition"  data-align="center" >职位</th>
	                    <th data-field="work_op" data-formatter="pro_work_format">操作</th>
					</tr>
				</thead>
			</table> 
        </div>
        
    </div>
    
        
    <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="save_file" onclick="savePerson()">确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<jsp:include page="../../common/validateJs.jsp" flush="true"></jsp:include>

<script>
$('input[name="personBirthdayStr"]').datepicker({
    format: 'yyyy-mm-dd',
    language: "zh-CN",
    autoclose: true,
    todayHighlight: false,
    defaultDate : Date,
    today: "Today",
    todayBtn:'linked',
    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
    forceParse:false,
    currentText: 'Now'
});


$("input:radio[name='isContacts']").change(function() {
	// 0 y; 1 n
	var $selectedvalue = $("input:radio[name='isContacts']:checked").val();
	radio_isContacts_tel($selectedvalue);
});




/* 
//验证输入框内不能输入特殊字符,输入就立刻清除
function cleanSpelChar(th){
    if(/["'<>%;)(&+]/.test(th.value)){
          $(th).val(th.value.replace(/["'<>%;)(&+]/,""));
    }
} */

 

</script>