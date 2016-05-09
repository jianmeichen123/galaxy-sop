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
      <a href="javascript:;" class="pubbtn bluebtn" >确定</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>
<script>
var $dialog ;
$(function(){
	$dialog = $("#create-project-dialog");
	$dialog.find("[name='projectName']").val(ideaInfo.ideaName);
});

function createProject()
{
	var $dialog = $("#create-project-dialog");
	
}
</script>
  