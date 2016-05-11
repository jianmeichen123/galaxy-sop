<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="edit_nametc" id="edit-project-dialog">
  <div class="top clearfix">
      <dl class="fmdl fml">
        <dt>旧项目名称：</dt>
        <dd><input type="text" class="txt" name="originProjectName" disabled/></dd>
      </dl>
       <dl class="fmdl">
        <dt>新项目名称：</dt>
        <dd><input type="text" class="txt" name="projectName"/></dd>
      </dl> 
  </div>
    <div class="btnbox">
      <a href="javascript:;" class="pubbtn bluebtn" id="submit-btn">确定</a><a href="javascript:;" class="pubbtn fffbtn" data-close="close" id="cancel-btn">取消</a>
    </div>
  </div>
<script>
var $dialog;
$(function(){
	$dialog = $("#edit-project-dialog");
	$dialog.find("[name='originProjectName']").val(ideaInfo.projectName);
	$dialog.find("[name='projectName']").val(ideaInfo.projectName);
	
	$dialog.find("#submit-btn").click(function(){
		editProjectName();
	});
});
function editProjectName()
{
	var projectName = $dialog.find("[name='projectName']").val();
	if(projectName == null || $.trim(projectName).length==0)
	{
		layer.msg("请输入新项目名称.");
		return;
	}
	if($.trim(projectName) == $.trim(ideaInfo.projectName))
	{
		layer.msg("新项目名称与旧项目名称相同.");
		return;
	}
	var data = {
		id : ideaInfo.id,
		projectName : projectName
	};
	sendPostRequestByJsonObj(
			platformUrl.ideaEditProjectName,
			data,
			function(data){
				if(data.result.status=='OK')
				{
					$dialog.find("#cancel-btn").click();
					layer.msg('编辑项目名称成功');
					//reload idea info
					refreshStageDialog(ideaInfo.id);
					//refresh project list
					$('#project-table').bootstrapTable('refresh');
				}
				else 
				{
					var msg = data.result.message || '编辑项目名称失败';
					layer.msg(msg);
				}
			}
	);
}
</script>