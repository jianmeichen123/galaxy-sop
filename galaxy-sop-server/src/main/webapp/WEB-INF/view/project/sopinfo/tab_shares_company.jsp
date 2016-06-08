<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<form id="company-info-form">
<input type="hidden" name="id" value="${projectId }">
<div class="tabtable_con" style="width:650px;">
	<div class="new_r_compile">
		<span class="new_ico_project"></span> <span class="new_color size16">法人信息</span>
		<div class="compile_on_center">
	        <div class="compile_on_right">
	            <span class="compile_on_right_b" onclick="saveCompanyInfo()">保存</span>
	            <span class="compile_on_right_q" data-close="close">取消</span>
	        </div> 
			<table width="100%" cellspacing="0" cellpadding="0" class="new_table" id="company-info">
				<tr>
					<td>
						<span class="new_color_gray">公司名称：</span> 
						<span class="new_color_black"><input type="text" name="projectCompany" class="new_nputr"></span>
					</td>
					<td>
						<span class="new_color_gray">组织代码：</span> 
						<span class="new_color_black"><input type="text" name="projectCompanyCode" class="new_nputr"></span>
					</td>
				</tr>
				<tr>
					<td>
						<span class="new_color_gray">法&nbsp;&nbsp;人：</span> 
						<span class="new_color_black"><input type="text" name="companyLegal" class="new_nputr"></span></td>
					<td>
						<span class="new_color_gray">成立时间：</span> 
						<span class="new_color_black"><input type="text" name="formationDate"  class="datepicker-text time"></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</form>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>

<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path%>/js/common.js" type="text/javascript"></script>
<script>
	$(function(){
		initFormData();
		$('#company-info-form [name="formationDate"]').datepicker({
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
		
	});
	function initFormData()
	{
		var $form = $('#company-info-form');
		$form.find('[name="projectCompany"]').val(getVal(projectInfo.projectCompany,''));
		$form.find('[name="projectCompanyCode"]').val(getVal(projectInfo.projectCompanyCode,''));
		$form.find('[name="companyLegal"]').val(getVal(projectInfo.companyLegal,''));
		var date = '';
		if(!isNaN(projectInfo.formationDate))
		{
			date = new Date(projectInfo.formationDate).format('yyyy-MM-dd');
		}
		$form.find('[name="formationDate"]').val(date);
	}
	function saveCompanyInfo()
	{
		var url = platformUrl.saveCompanyInfo;
		var data = JSON.parse($("#company-info-form").serializeObject());
		if(data.formationDate != null && data.formationDate != '')
		{
			var date = $('#company-info-form [name="formationDate"]').datepicker('getDate');
			data['formationDate'] = date.getTime();
		}
		
		sendPostRequestByJsonObj(
			url, 
			data, 
			function(data){
				if(data.result.status=='OK')
				{
					layer.msg("保存成功!");
					$(".pop .close").click();
					refreshCompanyInfo();
				}
				else
				{
					layer.msg(data.result.message);
				}
		});
	}
</script>