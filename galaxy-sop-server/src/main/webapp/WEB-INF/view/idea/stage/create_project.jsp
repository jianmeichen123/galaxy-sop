<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="edit_nametc" id="create-project-dialog">
  <div class="top clearfix">
       <dl class="fmdl">
        <dt>项目名称：</dt>
        <dd><input type="text" value="项目11" name="projectName" class="txt"/></dd>
      </dl> 
  </div>
    <div class="btnbox">
      <a href="javascript:;" class="pubbtn bluebtn" id="create-project-btn">确定</a><a href="javascript:;" class="pubbtn fffbtn" data-close="close" id="cancel-btn">取消</a>
    </div>
</div>
<script>
var $dialog ;
$(function(){
	$dialog = $("#create-project-dialog");
	$dialog.find("[name='projectName']").val(ideaInfo.ideaName);
	$dialog.find("#create-project-btn").click(function(){
		createProject();
	});
});

function createProject()
{
	var projectName = $dialog.find("[name='projectName']").val();
	if(projectName == null || $.trim(projectName).length==0)
	{
		layer.msg("请填写项目名称.");
		return;
	}
	var data = {
		ideaId : ideaInfo.id,
		projectName : projectName
	};
	sendGetRequest(
			platformUrl.idea2Project,
			data,
			function(data){
				if(data.result.status=='OK')
				{
					$(".tabtable_con .block").eq(3).show().siblings().hide();
					$('.tablink li').eq(3).addClass('on').siblings().removeClass('on');
					$dialog.find("#cancel-btn").click();
					refreshIdeaList();
					//reload idea info
					ideaInfo=data.entity;
					//refresh project list
					$('#project-table').bootstrapTable('refresh');
					layer.msg('创建项目成功');
				}
				else 
				{
					var msg = data.result.message || '创建项目失败';
					layer.msg(msg);
				}
			}
	);
}

</script>
  