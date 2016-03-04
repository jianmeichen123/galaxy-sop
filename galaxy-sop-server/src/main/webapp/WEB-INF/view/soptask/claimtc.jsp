<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
    String taskid=(String)request.getAttribute("id");
    String projectId=(String)request.getAttribute("projectId");
%>
<div class="claimtc">
	<p class="tips"><b class="null ok ico">ico</b>认领成功<br>是否现在去处理任务！</p>
    <div class="btnbox">
    	<a href="javascript:;" class="pubbtn bluebtn">是</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">否</a>
    </div>
</div>
<script src="<%=path %>/js/soptask.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		var soptaskid=<%=taskid%>;
		var projectId=<%=projectId%>;
		//单击按钮刷新页列表里面的内容
		$(".pubbtn .bluebtn").on("click", "a", function() {
			this.href='/galaxy/soptask/goClaimtcPage?projectId='+projectId;
		});
	});
</script>