<%@ page language="java" pageEncoding="UTF-8"%>
<div class="btm">
	<table width="100%" cellspacing="0" cellpadding="0" id="hrjzdc-table">
		<thead>
			<tr>
				<th>创建日期</th>
				<th>存储类型</th>
				<th>更新日期</th>
				<th>档案状态</th>
				<th>查看附件</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<ul>
		<li><a href="javascript:;">上传尽调报告</a></li>
		<li><a href="javascript:;">提交完成</a></li>
	</ul>
</div>
<div id="upload-dialog">

</div>
<script type="text/javascript">
$(function(){
	var url = "../../galaxy/sopFile/selectList";
	var data = {
		"projectId":"${projectId}",
		"fileWorktype":"fileWorktype:1"
	};
	sendPostRequestByJsonObj(
			url,
			data,
			function(data){
				$.each(data.pageList.content,function(){
				console.log(this);
					var $tr = $("<tr></tr>");
					$tr.append('<td>'+(isBlank(this.createdTime) ? "" : Number(this.createdTime).toDate().format("yyyy/MM/dd")) +'</td>');
					$tr.append('<td>'+(isBlank(this.fileTypeDesc) ? "" : this.fileTypeDesc)+'</td>');
					$tr.append('<td>'+(isBlank(this.updatedTime) ? "" : Number(this.updatedTime).toDate().format("yyyy/MM/dd"))+'</td>');
					$tr.append('<td>'+this.fileStatusDesc+'</td>');
					$tr.append('<td>'+(isBlank(this.fileName) ? "" : this.fileName)+'</td>');
					$("#hrjzdc-table tbody").append($tr);
				});
			}
	);
	
});
function isBlank(val)
{
	if(val == "" || val == null || val == 'undefined')
	{
		return true;
	}
	return false;
}
</script>
