<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 	<div class="taskDetail-ritmin">
 		 <div class='taskDetail-mesage'>
 		 	<div class='taskDetail-mesage-top'>
	        	<div class='task-item task-item-left'>
	        		<ul>
	        			<li>项目名称：<span id="projectName">创投</span></li>
	        			<li>创建时间：<span id="createDate">2016-12-22</span></li>
	        			<li>事业部总经理：<span id="hhrName">李凯</span></li>
	        		</ul>
	        	</div>
	        	<div class='task-item'>
	        		<li>项目类型：<span id="type">投资</span></li>
	       			<li>投资事业线：<span id="projectCareerline">人工智能</span></li>
	       			<li>公司名称：<span id="projectCompany">星河互联集团</span></li>
	        	</div>
	        	<div class='task-item task-item-right'>
	        		<li>项目编码：<span id="projectCode">27000021</span></li>
	       			<li>投资经理：<span id="createUname">人工智能-投资经理</span></li>
	       			<a href='<%=path %>/galaxy/project/detail/${projectId}?mark=t' class='pro-detail'>项目详细信息 ></a>
	        	</div> 
        	</div>
        	<div class='taskDetail-mesage-table'>
	        	<table width='100%' class='task-detail-table' border='0' cellspacing='0' cellpadding='0'>
	        		<thead>
	        			<tr>
	        				<th>上传日期</th>
	        				<th>存储类型</th>
	        				<th>更新日期</th>
	        				<th>档案状态</th>
	        				<th>操作</th>
	        			</tr>
	        		</thead>
	        		<tbody>
	        			<tr>
	        				<td>2017-04-09</td>
	        				<td>图片</td>
	        				<td>2017-08-04</td>
	        				<td>已上传</td>
	        				<td class='task-operation'>
	        					<a href='#'>查看</a>
	        					<a href='#'>查看历史</a>
	        				</td>
	        				
	        			</tr>
	        		</tbody>
	        	
	        	</table>
        	</div>
        	<div class='taskDetail-mesage-update'>
        		<a href='javascript:;' class="upate-task" id="file-upload-btn">${btnTxt }</a>
        		<a href='javascript:;'  class='upate-task submit-success disabled' id="complete-task-btn" disabled="disabled">提交完成</a>
        	</div>
        </div>
	</div>
</div>

<script type="text/javascript">

	/**********************显示任务详情 START************************/
	var url = platformUrl.detailProject+"/${projectId}";
	var data = {};
	var callback = function(data){
		if(data.result.status == "Error")
		{
			return;
		}
		var project = data.entity;
		$(".task-item span").each(function(){
			var _item = $(this);
			var id = _item.attr('id');
			if(!project.hasOwnProperty(id))
			{
				return;
			}
			_item.text(project[id]);
		});
	};
	sendGetRequest(url,data,callback);
	/**********************显示任务详情 END ************************/
	/**********************显示文件 START ************************/
	function isBlank(val)
	{
		if(val == "" || val == null || val == 'undefined')
		{
			return true;
		}
		return false;
	}
	function downloadFile(ele)
	{
		var row = $(ele).closest("tr");
		var fileId = row.data("id");
		forwardWithHeader(platformUrl.downLoadFile+"/"+fileId);
	}
	
	var url = platformUrl.queryFile;
	var data = {
		"projectId":"${projectId}",
		"fileWorktype":"${fileWorktype}"
	};
	var callback = function(data){
		$(".task-detail-table tbody").empty();
		$.each(data.entityList,function(){
			var $tr = $('<tr data-id="'+this.id+'" data-file-source="'+this.fileSource+'" data-file-type="'+this.fileType+'" data-file-worktype="'+this.fileWorktype+'" data-file-name="'+this.fileName+'"></tr>');
			$tr.append('<td>'+(isBlank(this.createdTime) ? "" : Number(this.createdTime).toDate().format("yyyy/MM/dd")) +'</td>');
			$tr.append('<td>'+(isBlank(this.fType) ? "" : this.fType)+'</td>');
			$tr.append('<td>'+(isBlank(this.updatedTime) ? "" : Number(this.updatedTime).toDate().format("yyyy/MM/dd"))+'</td>');
			$tr.append('<td>'+this.fileStatusDesc+'</td>');
			if(isBlank(this.fileName)){
				$tr.append('<td></td>');
				$("#complete-task-btn").addClass('disabled');
			}
			else
			{
				$tr.append('<td class="task-operation"><span onclick="downloadFile(this)">查看</span></td>');
				$("#complete-task-btn").removeClass('disabled');
				$("#complete-task-btn").removeProp("disabled");
				/* 
				var btnText = $("#show-upload-btn").text();
				if(btnText != null && btnText.indexOf('上传')>-1)
				{
					$("#show-upload-btn").text(btnText.replace('上传','更新'))
				}
				$(".task_noprovide").hide();
				 */
			}
			$(".task-detail-table tbody").append($tr);
		});
		
	};
	sendPostRequestByJsonObj(url, data, callback);
	/**********************显示文件 END ************************/
	
</script>

