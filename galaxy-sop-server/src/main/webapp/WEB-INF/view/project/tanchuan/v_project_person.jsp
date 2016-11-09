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
                <dd><input type="text" class="txt" name="personName" /></dd>
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
                <dd><input type="text" class="txt" name="personDuties" /></dd>
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
                <dd><input type="text" class="txt" name="personTelephone" placeholder="请输入电话号码"/></dd>
            </dl>
            
            <dl class="fmdl fl">
                <dt><em class="red">*</em>&nbsp;是否为联系人：</dt>
                <dd class="clearfix">
                    <label><input type="radio" name="personSex1" value="0" checked="checked" />是</label>
                    <label><input type="radio" name="personSex1" value="1" />否</label>
                </dd>
            </dl>
            
            <dl class="fmdl fl block">
                <dt>备注：</dt>
                <dd><textarea maxlength="50" name="endComment"></textarea></dd>
            </dl>
            
            </form>
        </div>
        
        
        
        <div class="qualifications">
            <h3>学历背景</h3>
            <span onclick="toAddPersonLearning(/'/');" class="blue fr add"  data-name="学历背景">添加</span>
            
            <div id="learning_table_custom_toolbar">
				<input type="hidden" name="personId" value="">
			</div>
            <table style="table-layout:fixed"  id="per_learning_table" 
               	data-url="<%=path %>/galaxy/project/queryProPerLearn" data-method="post" 
	       		data-toolbar="#learning_table_custom_toolbar" data-id-field="id" data-unique-id="id" >
				<colgroup >
					<col style="width:25%;">
					<col style="width:15%;">
					<col style="width:25%;">
					<col style="width:15%;">
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
	                     <th data-formatter="pro_learning_format">操作</th>
					</tr>
				</thead>
			</table> 

            <!-- <table id="learning-table" style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                <thead>
                 <tr>
                     <th>毕业院校</th>
                     <th>专业</th>
                     <th>时间</th>
                     <th>学历</th>
                     <th>操作</th>
                 </tr>
                </thead>
                <tbody id="learning-tbody">
                 
                </tbody>
            </table> -->
        </div>
        
        
        
        <div class="qualifications">
            <h3>工作履历</h3>
            <span  onclick="toAddPersonWork(/'/');" class="blue fr add"  data-name="工作履历" >添加</span>
            
            <div id="work_table_custom_toolbar">
				<input type="hidden" name="personId" value="">
			</div>
            <table style="table-layout:fixed"  id="per_work_table" 
               	data-url="<%=path %>/galaxy/project/queryProPerWork" data-method="post" 
	       		data-toolbar="#work_table_custom_toolbar" data-id-field="id" data-unique-id="id" >
				<colgroup >
					<col style="width:30%;">
					<col style="width:30%;">
					<col style="width:20%;">
					<col style="width:20%;">
				</colgroup>
				<thead>
					<tr>
						<th data-field="deleteIndex"  data-align="center" data-formatter="deleteIndex_Format" ></th>
						<!--  beginWork overWork beginWorkStr overWorkStr -->
						<th data-field="BE_time"  data-align="center" data-formatter="work_TimeFormat">时间</th>
						
						<th data-field="companyName"  data-align="center" >任职公司名称</th>
		                <th data-field="workPosition"  data-align="center" >职位</th>
	                    <th data-formatter="pro_work_format">操作</th>
					</tr>
				</thead>
			</table> 
            
            <!-- <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                <tr>
                    <th>时间</th>
                    <th>任职公司名称</th>
                    <th>职位</th>
                    <th>操作</th>
                </tr>
                <tr>
                    <td>2016-01-05 - 2016-10-12</td>
                    <td>公司名称</td>
                    <td>交互</td>
                    <td><a class="blue" href="javascript:void(0)">删除</a></td>
                </tr>
            </table> -->
        </div>
        
    </div>
    
        
    <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="save_file" >确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<jsp:include page="../../common/validateJs.jsp" flush="true"></jsp:include>

<script>

//TODO 学习


/*
 * 新建 :c 、 编辑:e    学习经历 - 弹窗
 */
var learnSelectRow;
var isCreatOrEditLearn = "c";  //判断   是新创建 person ：c  是编辑person ： e  是新创建编辑person ： ne  是已有编辑 person ： oe 

//弹窗  编辑 、保存  --  初始化
function toAddPersonLearning(row){
	/* if(row && row!=null && typeof(row)!='undefined' ){ //判断是编辑模式
		learnSelectRow = row;
		
		if(row.id && row.id!=null && typeof(row.id)!='undefined' ){
			isCreatOrEditLearn = "oe";
		}else{
			isCreatOrEditLearn = "ne";
		}
	}else{ //判断是添加模式
		
	} */
	
	if(row && row!=null && typeof(row)!='undefined' ){ //判断是编辑模式
		learnSelectRow = row;
		isCreatOrEditLearn = "e";
	}
	
	var _url=Constants.sopEndpointURL + '/galaxy/project/addProPerLearning';
	var $self = $(this);
	var _name= $self.attr("name");
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#qualifications_popup_name").html(_name);
			
			$("#learn_person_Id").val($("#person_pool_id").val());
			$("#learn_id").val(learnSelectRow.id);
			
			//if(isCreatOrEditLearn == "oe" || isCreatOrEditLearn == "ne"){//获取数据
			if(isCreatOrEditLearn == "e"){//获取数据
				console.log("编辑learn ： "+learnSelectRow);
				$("#learn_id").val(learnSelectRow.id);
			}
		}
	});
	return false;
}

	
// 新建  -- 编辑   学习经历
function savePersonLearning(){
	var learn = JSON.parse($("#add_person_learning").serializeObject());
	
	console.log("save learn : " + learn);
	
	var id = learn.id;
	var perondId = learn.personId;
	
	if(perondId && perondId!=null && typeof(perondId)!='undefined'){  //有人员信息，编辑 保存 数据库
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"/galaxy/project/saveOrEditProPerLearn",learn,function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}else{
				learn.id = data.id;
				layer.msg("保存成功", {time : 500});
				//$("#per_learning_table").bootstrapTable('refresh');
				$('#per_learning_table').bootstrapTable('updateRow', {index: learnSelectRow.deleteIndex, row: learn});
			}
		});
	}else{
		if(isCreatOrEditLearn == "e"){
			$('#per_learning_table').bootstrapTable('updateRow', {index: learnSelectRow.deleteIndex, row: learn});
		}else{
			$('#per_learning_table').bootstrapTable('append', learn);
		}
	}
	
	//去除弹层
	removePop1();
	$.popupTwoClose();
}



//删除
function deleteLearn(value,row,index){
	if(row && row!=null && typeof(row)!='undefined' && row.id!=null && typeof(row.id)!='undefined' ){
		sendGetRequest(Constants.sopEndpointURL + "/galaxy/project/deleteProPerLearning/"+row.id, null, function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}
		});
	}
		
	$('#per_learning_table').bootstrapTable('remove', {field: 'deleteIndex', values: index});
}




//TODO 工作


/*
 * 新建 :c 、 编辑:e    学习经历 - 弹窗
 */

var workSelectRow;
var isCreatOrEditWork = "c";  //判断   是新创建 person ：c  是新创建编辑person ： ne  是已有编辑 person ： oe 

//弹窗  --  初始化
function toAddPersonWork(row){
	/* if(row && row!=null && typeof(row)!='undefined' ){
		workSelectRow = row;
		
		if(row.id && row.id!=null && typeof(row.id)!='undefined' ){
			isCreatOrEditWork = "oe";
		}else{
			isCreatOrEditWork = "ne";
		}
	} */
	
	if(row && row!=null && typeof(row)!='undefined' ){ //判断是编辑模式
		workSelectRow = row;
		isCreatOrEditWork = "e";
	}
	
	var _url=Constants.sopEndpointURL + '/galaxy/project/addProPerWork';
	var $self = $(this);
	var _name= $self.attr("name");
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#qualifications_popup_name").html(_name);
			
			$("#work_person_Id").val($("#person_pool_id").val());
			$("#work_id").val(workSelectRow.id);
			
			if(isCreatOrEditWork == "e"){//获取数据
				console.log("编辑work ： "+workSelectRow);
				$("#work_id").val(workSelectRow.id);
			}
		}
	});
	return false;
}

//新建  -- 编辑   工作经历
function savePersonWork(){
	var work = JSON.parse($("#add_person_work").serializeObject());
	
	console.log("save work : " + work);
	
	var id = work.id;
	var perondId = work.personId;
	
	if(perondId && perondId!=null && typeof(perondId)!='undefined'){  //有人员信息，编辑 保存 数据库
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"/galaxy/project/saveOrEditProPerWork",work,function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}else{
				work.id = data.id;
				layer.msg("保存成功", {time : 500});
				//$("#per_learning_table").bootstrapTable('refresh');
				$('#per_work_table').bootstrapTable('updateRow', {index: workSelectRow.deleteIndex, row: work});
			}
		});
	}else{
		if(isCreatOrEditLearn == "e"){
			$('#per_work_table').bootstrapTable('updateRow', {index: workSelectRow.deleteIndex, row: work});
		}else{
			$('#per_work_table').bootstrapTable('append', work);
		}
	}
	
	//去除弹层
	removePop1();
	$.popupTwoClose();
}



//删除
function deleteWork(value,row,index){
	if(row && row!=null && typeof(row)!='undefined' && row.id!=null && typeof(row.id)!='undefined' ){
		sendGetRequest(Constants.sopEndpointURL + "/galaxy/project/deleteProPerWork/"+row.id, null, function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}
		});
	}
		
	$('#per_work_table').bootstrapTable('remove', {field: 'deleteIndex', values: index});
}






//验证输入框内不能输入特殊字符,输入就立刻清除
function cleanSpelChar(th){
    if(/["'<>%;)(&+]/.test(th.value)){
          $(th).val(th.value.replace(/["'<>%;)(&+]/,""));
    }
}

 

</script>