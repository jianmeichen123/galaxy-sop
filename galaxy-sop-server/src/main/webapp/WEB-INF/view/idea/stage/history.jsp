<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="title_bj">历史信息</div>
<div class="ritmin create_historytc margin_45">

	<div class="clearfix ">
	  <h3 class="idea-title">调研</h3>       
	</div>
	<div id="research-history-params">
		<input type="hidden" name="projectId" value="${id}">
	</div>
	<table id="research-history" class="history-table" data-id-field="id" data-url="<%=path%>/galaxy/idea/queryIdeaDyList" data-toolbar="#research-history-params" data-page-size="10">
		<thead>
			<tr>
				<th data-field="fileUName" data-align="left" >上传者</th>
				<th data-field="fType" data-align="left" >存档类型</th>
				<th data-field="projectProgress" data-align="left" data-formatter="progressFormatter">创意状态</th>
				<th data-field="careerLineName" data-align="left" >所属事业线</th>
				<th data-field="updatedDate" data-align="left" >更新时间</th>
				<th data-align="left" data-formatter="ideaFileDownFormat">附件查看</th>
			</tr>
		</thead>
	</table>
	<div class="clearfix">
	  <h3 class="idea-title idea-title-create">创建立项会</h3>       
	</div>
	<div id="cjlxh-history-params">
		<input type="hidden" name="projectId" value="${id}">
					<input type="hidden" name="meetingType" value="meetingType:3">
					<input type="hidden" name="recordType" value="1">
					<input type="hidden" name="meetValid" value="1">
	</div>
	<table id="cjlxh-history" class="history-table" data-id-field="id" data-url="<%=path%>/galaxy/idea/queryCyMeet" data-toolbar="#cjlxh-history-params" data-page-size="10">
		<colgroup >
			<col style="width:30%;"> <!-- 名称 -->
			<col style="width:70%;">  <!-- 状态 -->
		</colgroup>
		<thead>
			<tr>
				<th data-field="meetInfo" data-align="left" data-formatter="metcolumnFormat">会议概况</th>
				<th data-field="meetingNotes" data-align="left" data-formatter="meetNoteFormatter">会议纪要</th>
			</tr>
		</thead>
	</table>
</div>
<script>
$(".history-table").bootstrapTable({
	queryParamsType: 'size|page', // undefined
	showRefresh : false ,
	sidePagination: 'server',
	method : 'post',
	pagination: true,
    search: false
});
/* 
function meetInfoFormatter(val,row,index)
{
	var fileinfo = "";
	var rc = "";
	if(row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
	}
	rc = "<div style=\"text-align:left;margin-left:20%;\">"+
				"会议日期："+row.meetingDateStr+
				"</br>会议结论："+row.meetingResultStr+
				"</br>会议录音："+fileinfo+
			"</div>" ;
	return rc;
} */
function meetNoteFormatter(value,row,index){
	var len = getLength($.trim(value.replace(/<[^>]+>/g,"")));
	if(value != ''){
		var strlog=value.replace(/<[^>]+>/g,"");
		var strrrr=strlog;
		if(len>100){
			var subValue = $.trim(value.replace(/<[^>]+>/g,"")).substring(0,100);
			var rc = "<div id=\"log\" style=\"text-align:left;margin-left:20%;\" class=\"text-overflow\" title='"+strrrr+"'>"+subValue+'...'+'</div>';
			
			return rc;
		}else{
			return strlog;
		}
	}

}
</script>