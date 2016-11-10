<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addPersontc">
	<div class="title_bj" id="popup_name"></div>
		<input type="hidden" value="${uuid}" name="uuid">
        <div class="addPerson_all" id="person-pool">
            <div class="qualifications">
                <h3>基本信息</h3>
                <table id="learning-table" style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table learning-table">
                    <thead>
	                    <tr>
	                        <th>姓名</th>
	                        <th>当前职务</th>
	                        <th>性别</th>
	                        <th>出生日期</th>
	                        <th>电话号码</th>
	                        <th>备注</th>
	                    </tr>
                    </thead>
                    <tbody id="detail-tbody">
	                    
                    </tbody>
                </table>
            </div>
            <div class="qualifications">
                <h3>学历背景</h3>
                <table id="learning-table" style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                    <thead>
	                    <tr>
	                        <th>毕业院校</th>
	                        <th>专业</th>
	                        <th>时间</th>
	                        <th>学历</th>
	                    </tr>
                    </thead>
                    <tbody id="learning-tbody">
	                    
                    </tbody>
                </table>
            </div>
            <div class="qualifications">
                <h3>工作履历</h3>
                <table id="work-table" style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
	                <thead>
	                    <tr>
	                        <th>时间</th>
	                        <th>任职公司名称</th>
	                        <th>职位</th>
	                    </tr>
	                </thead>
                    <tbody id="work-tbody">
	                    
                    </tbody>
                </table>
            </div>
        </div>
</div>
<script>
$(function(){
	var uuid = $('input[name="uuid"]').val();
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/lookPerson/"+uuid+"/"+id, 
			null, 
			function(data){
		generatePersonDetail(data);
	});
});
</script>