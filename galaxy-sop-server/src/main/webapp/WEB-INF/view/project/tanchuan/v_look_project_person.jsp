<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<style>
.bars{display:none;} 
</style>
<div class="addPersontc">
	<div class="title_bj" id="popup_name"></div>
	
        <div class="addPerson_all" id="person-pool">
        
            <div class="qualifications">
                <h3>基本信息</h3>
                
               <div id="pro_per_info_toolbar">
					<input type="hidden" name="id" id="pool_id" value="" />
				</div>
				<div style="border:1px solid #e9ebf2 !important;width:94% !important;margin:20px 20px;border-radius:6px;">
					  <table id="pro_per_info" class="basic_table" data-url="<%=path %>/galaxy/project/queryProPerInfo" data-method="post" 
                	data-toolbar="#pro_per_info_toolbar" >
	        		<thead>
						<tr>
							<th data-field="personName"  data-align="center" data-formatter="personName" >姓名</th>
							<th data-field="personDuties"  data-align="center" data-formatter="personDuties">当前职务</th>
			                <th data-field="personSex"  data-align="center" data-formatter="sexFormat">性别</th>
		                     <th data-field="personBirthday"  data-align="center" data-formatter="date_str_format">出生日期</th>
		                     <th data-field="personTelephone"  data-align="center" >电话号码</th>
		                     <th data-field="remark" data-align="center" data-formatter="remark_format">备注</th>
						</tr>
					</thead>
				</table>
				</div>
            </div>
            
            
            
            <div class="qualifications">
                <h3>学历背景</h3>
                
                <div id="pro_per_learn_toolbar">
					<input type="hidden" name="personId" value="" />
				</div>
				<div style="border:1px solid #e9ebf2 !important;width:94% !important;margin:20px 20px;border-radius:6px;">
	            <table  id="pro_per_learn_table" class="basic_table"  data-url="<%=path %>/galaxy/project/queryProPerLearn" data-method="post" 
		       		data-toolbar="#pro_per_learn_toolbar"  >
					<colgroup >
						<col style="width:30%;">
						<col style="width:20%;">
						<col style="width:40%;">
						<col style="width:10%;">
					</colgroup>
					<thead>
						<tr>
							<th data-field="school"  data-align="center" >毕业院校</th>
			                <th data-field="major"  data-align="center" >专业</th>
		                     <th data-field="BE_time"  data-align="center" data-formatter="learn_TimeFormat">时间</th>
		                     <th data-field="degree"  data-align="center" >学历</th>
						</tr>
					</thead>
				</table>
				</div> 
                
            </div>
            
            
            
            <div class="qualifications">
                <h3>工作履历</h3>
                
                <div id="pro_per_work_toolbar">
					<input type="hidden" name="personId" value="" />
				</div>
				<div style="border:1px solid #e9ebf2 !important;width:94% !important;margin:20px 20px;border-radius:6px;">
	            <table id="pro_per_work_table" class="basic_table"  data-url="<%=path %>/galaxy/project/queryProPerWork" data-method="post" 
		       		data-toolbar="#pro_per_work_toolbar" >
					<colgroup >
						<col style="width:35%;">
						<col style="width:45%;">
						<col style="width:20%;">
					</colgroup>
					<thead>
						<tr>
							<th data-field="BE_time"  data-align="center" data-formatter="work_TimeFormat">时间</th>
							<th data-field="companyName"  data-align="center" >任职公司名称</th>
			                <th data-field="workPosition"  data-align="center" >职位</th>
						</tr>
					</thead>
				</table> 
				</div>
			
            </div>
            
            
      </div>
</div>


