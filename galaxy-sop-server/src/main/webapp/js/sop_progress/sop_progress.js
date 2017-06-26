// JavaScript Document
var projectId;
function  progress(id){
	projectId = id;
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/progress/index",//模版请求地址
		data:"",//传递参数
		okback:function(){
			$(".close").addClass("progress_close")
		}
	});
}

function interviewList(){
	$('#projectProgress_1_table').bootstrapTable('destroy');
	$('#projectProgress_1_table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		pageList : [5, 10, 20 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
		uniqueId: "id", 
		idField : "id",
		clickToSelect: true,
	    search: false,
	    columns: [
                  {
                      title: '时间',
                      field: 'viewDateStr',
                      valign: 'left',
                  },
                  {
                      title: '结论',
                      field: 'interviewResult',
                      valign: 'left',
                  }, {
                      title: '结论原因',
                      field: 'resultReason',
                      valign: 'left',
                  },
                    {
                        title: '操作',
                        field: 'oper',
                        valign: 'left',
                        formatter:'viewOperFormat'
                     }
              ],
	    onLoadSuccess:function(data){
	    	if(data.pageList.total>0)
	   		{
	    		$.each($('#projectProgress_1_table tr'),function(){
	    			var $this = $(this);
	    			$this.find('td:last').addClass('limits_gray');
	    			$this.find('td:last .edit').removeAttr('onclick');
	    		});
	   		}
	    }
	});
}
function meetList(type){
	$('#projectProgress_1_table').bootstrapTable('destroy');
	$("#meetingType").val(type);
	$('#projectProgress_1_table').bootstrapTable({
		url:Constants.sopEndpointURL+'/galaxy/progress/p/queryMeet',
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		pageList : [5, 10, 20 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
		uniqueId: "id", 
		idField : "id",
		clickToSelect: true,
	    search: false,
	    columns: [
                  {
                      title: '会议概况',
                      field: 'meetinfo',
                      valign: 'left',
                      formatter:'metcolumnFormat'
                  },
                  {
                      title: '会议类型',
                      field: 'meetingTypeStr',
                      valign: 'left'
                  },
                  {
                      title: '会议纪要',
                      field: 'meetingNotes',
                      valign: 'left',
                      formatter:'tc_viewNotesFormat_noinfo'
                  },
                    {
                        title: '操作',
                        field: 'oper',
                        valign: 'left',
                        formatter:'viewOperFormat'
                     }
              ],
	    onLoadSuccess:function(data){
	    	if(data.pageList.total>0)
	   		{
	    		$.each($('#projectProgress_1_table tr'),function(){
	    			var $this = $(this);
	    			$this.find('td:last').addClass('limits_gray');
	    			$this.find('td:last .edit').removeAttr('onclick');
	    		});
	   		}
	    }
	});
}
/**
 *  查看  or 编辑  
 */
function viewOperFormat(value,row,index){  
	var info = "<span class=\"see blue\"  onclick=\"notesInfoEdit('"+row.id+"','v')\" >查看</span>";
	var edit = "";
	
	//if(userId==row.createdId && isTransfering == 'false'){
		edit = " <span class=\"see blue\"  onclick=\"notesInfoEdit('"+row.id+"','e')\" >编辑</span>";
	//}
	return info + edit;
}
function notesInfoEdit(selectRowId,type){
	interviewSelectRow = $('#projectProgress_1_table').bootstrapTable('getRowByUniqueId', selectRowId);
	var _url = Constants.sopEndpointURL+"/galaxy/progress/p1/view"+"/"+type;
	var res = {};
	res.projectId = projectId;
	res.id = selectRowId;
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/progress/p1/queryInterview",res,function(data){
				var result = data.result.status;
				//渲染数据|待后续加
				if(result == "OK"){
					var res = data.pageList.content;
					type=="e" ? $("#viewDate").val(res[0].viewDateStr) : $("#viewDate").text(res[0].viewDateStr);
					type=="e" ? $("#viewTarget").val(res[0].viewTarget) : $("#viewTarget").text(res[0].viewTarget);
					type=="e" ? $("#reasonOther").val(res[0].reasonOther) : $("#reasonOther").text(res[0].reasonOther);
					type=="e" ? $("#viewNotes").val(res[0].viewNotes) : $("#viewNotes").html(res[0].viewNotes);
					type=="e" ? '' : $("#interviewResult").html(res[0].interviewResult);
					if(res[0].fileId){
						type=="e" ? '' : $("#file").html("<a href=\"javascript:filedown("+res[0].fileId+","+res[0].fkey+");\" class=\"blue\" >"+res[0].fname+"</a>");
					}
					
				}
			});
		}
	});
	return false;
}
/**
 * 添加访谈记录弹窗
 * @param id
 */
function  p1(id){
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/progress/p1",//模版请求地址
		data:"",//传递参数
		okback:function(){
			
		}
	});
}

/**
 * 获取 访谈表格数据，返回 jsonObj 对象
 * 
 * @param hasProid
 *            是否传入项目id值，'y':是
 * @param projectId
 *            传入项目id值， 或选择器id
 * @param viewDateId
 *            时间id
 * @param viewTargetId
 *            目标id
 * @param viewNotesId
 *            记录id
 */
function getInterViewParams(hasProid,projectId,
		viewDateId,
		viewTargetId,
		viewNotesId){
	var	condition = {};
	
	if(!beforeSubmit()){
		return false;
	}
	if(hasProid == "y" ){
		var projectId = $.trim(projectId);
	}else{
		var projectId = $("#"+projectId).val();
	}
	var viewDateStr = $("#"+viewDateId).val();
	var viewTarget = $.trim($("#"+viewTargetId).val());
	var viewNotes = $.trim(CKEDITOR.instances.viewNotes.getData());
	$('#con_id input[name=interviewResult]:checked').val()
	
	if(projectId == null || projectId == ""){
		layer.msg("项目不能为空");
		return false;
	}
	if(viewTarget == null ||  viewTarget == ""){
		layer.msg("对象不能为空");
		return false;
	}else{
		if(getLength(viewTarget) > 100){
			layer.msg("对象长度最大100字节");
			return false;
		}
	}
	
	if(viewNotes != null && viewNotes.length > 0){
		if(getLength(viewNotes) > 9000){
			layer.msg("访谈记录长度最大9000字符");
			return false;
		}
	}
	
	condition.projectId = projectId;
	condition.viewDateStr = viewDateStr;
	condition.viewTarget = viewTarget;
	condition.viewNotes = viewNotes;
	
	return condition;
}

/**
 * 项目阶段推进
 * @param nextProgress 下一阶段编码。 e.g. projectProgress:2
 * @returns
 */
function nextProgress(nextProgress)
{
	sendPostRequestByJsonObj(
		platformUrl.projectStageChange,
		{id:projectId, stage:nextProgress},
		function(data){
			if(data.result.status == 'OK')
			{
				layer.msg('提交成功');
			}
			else if(data.result.message != null)
			{
				layer.msg(data.result.message);
			}
		}
	);
}