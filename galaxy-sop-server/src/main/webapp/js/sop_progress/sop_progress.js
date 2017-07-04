// JavaScript Document
var projectId;
function  progress(id){
	projectId = id;
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/progress/index",//模版请求地址
		data:{"projectId":projectId},//传递参数
		okback:function(){
			$(".close").addClass("progress_close");
			/*goToProgress();*/
		}
	});
}
//因为太长放不下所以页码pageList改成不可选  如果产品需要就再改过来。
function interviewList(){
	$('#projectProgress_1_table').bootstrapTable('destroy');
	$('#projectProgress_1_table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		pageList : [5],
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
                      field: 'interviewResultStr',
                      valign: 'left',
                  }, {
                      title: '结论原因',
                      field: 'resultReasonStr',
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
		pageList : [5],
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
                      field: 'meetingDateStr',
                      valign: 'left',
                  },
                  {
                      title: '结论',
                      field: 'meetingResultStr',
                      valign: 'left'
                  },
                  {
                      title: '原因',
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
	    		console.log(data);
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
	var meetingType = "";
	var title = $(".tabtitle h3").text();
	
    if(row){
    	meetingType = row.meetingType;
	}
	var info = "<span class=\"see blue\"  onclick=\"notesInfoEdit('"+row.id+"','v','"+meetingType+"','"+"查看"+title+"')\" >查看</span>";
	var edit = "";
	
	//if(userId==row.createdId && isTransfering == 'false'){
		edit = " <span class=\"see blue\"  onclick=\"notesInfoEdit('"+row.id+"','e','"+meetingType+"','"+"编辑"+title+"')\" >编辑</span>";
	//}
	return info + edit;
}
function notesInfoEdit(selectRowId,type,meetingType,title){
	interviewSelectRow = $('#projectProgress_1_table').bootstrapTable('getRowByUniqueId', selectRowId);
	var _url = Constants.sopEndpointURL+"/galaxy/progress/p1/view"+"/"+type;
	var res = {};
	res.projectId = projectId;
	res.id = selectRowId;
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			var url = Constants.sopEndpointURL + "/galaxy/progress/p1/queryInterview";
			$("#tabtitle").text(title);
			var arrName=[];
			switch(meetingType){
			  case "":
				  //访谈结论radio
				  radioSearch(platformUrl.searchDictionaryChildrenItems+"meetingResult");
				  arrName.push("meetingUndeterminedReason");
				  arrName.push("meetingVetoReason");
				  $("#targetView").attr("style","display:block");
				  break;
			  case "meetingType:3":
				  res.meetingType = meetingType;
				  url = Constants.sopEndpointURL + "/galaxy/progress/p/queryMeet";
				  //会议结论radio
				  radioSearch(platformUrl.searchDictionaryChildrenItems+"meeting3Result");
				  arrName.push("meetingVetoReason");
				  meetingColumns();
				  break;
			  case "meetingType:5":
				  res.meetingType = meetingType;
				  url = Constants.sopEndpointURL + "/galaxy/progress/p/queryMeet";
				  //会议结论radio
				  radioSearch(platformUrl.searchDictionaryChildrenItems+"meeting5Result");
				  arrName.push("meetingFollowingReason");
				  arrName.push("meetingVetoReason");
				  meetingColumns();
				  break;
			  default:
				meetingColumns();
				res.meetingType = meetingType;
				url = Constants.sopEndpointURL + "/galaxy/progress/p/queryMeet";
			}
			selectDict(arrName);
			//渲染数据|待后续加
			sendPostRequestByJsonObj(url,res,function(data){
				var result = data.result.status;
				if(result == "OK"){
					var res = data.pageList.content;
					var time;
					var target;
					var content;
					var result;
					var resultReason;
					var reasonOther;
					var recordId;
					recordId= res[0].id;
					if(meetingType){
						time = res[0].meetingDateStr;
						content = res[0].meetingNotes;
						result = res[0].meetingResultStr;
						resultReason = res[0].resultReasonStr;
						reasonOther = res[0].reasonOther;
						type=="e" ? $("input[name='interviewResult'][value='"+res[0].meetingResult+"']").attr("checked",true) : $("#interviewResult").html(result);
					}else{
						time = res[0].viewDateStr;
						target = res[0].viewTarget;
						content = res[0].viewNotes;
						result = res[0].interviewResultStr;
						resultReason = res[0].resultReasonStr;
						reasonOther = res[0].reasonOther;
						type=="e" ? $("input[name='interviewResult'][value='"+res[0].interviewResult+"']").attr("checked",true) : $("#interviewResult").html(result);
						
					}
					$("#recordId").val(recordId);
					type=="e" ? $("#viewDate").val(time) : $("#viewDate").text(time);
					type=="e" ? $("#viewTarget").val(target) : $("#viewTarget").text(target);
					type=="e" ? $("#reasonOther").val(reasonOther) : $("#reasonOther").text(reasonOther);
					type=="e" ? $("#viewNotes").val(content) : $("#viewNotes").html(content);
					type=="e" ? '' : $("#interviewResult").html(result);
					var reason=res[0].resultReason;
					//结论原因回显
	                 switch(res[0].interviewResult){
	                 case "meetingResult:1":
	                	 break;
	                 case "meetingResult:2":
	                	 $("select[name='meetingUndeterminedReason']").find("option[value='"+reason+"']").attr("selected",true)
	                	 break;
	                 case "meetingResult:3":
	                	 $("select[name='meetingVetoReason']").find("option[value='"+reason+"']").attr("selected",true)
	                	 break;
	                 default:
	                 }
					var other=reasonOther==null||reasonOther==""?"":"("+reasonOther+")";
					type=="e" ? '' : $("#resultReason").html("原因："+resultReason+other);
					if(res[0].fileId){
						type=="e" ? $("#file_object").html("<a href=\"javascript:filedown("+res[0].fileId+","+res[0].fkey+");\" class=\"blue\" >"+res[0].fname+"</a>"): $("#file").html("<a href=\"javascript:filedown("+res[0].fileId+","+res[0].fkey+");\" class=\"blue\" >"+res[0].fname+"</a>");
						$("#file_object").text(res[0].fname);
						$("#select_btn").text("更新");
						$("#file_object").addClass("audio_name")
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
	var viewNotes = $.trim(CKEDITOR.instances.viewNotes.getData());
	
	if(projectId == null || projectId == ""){
		layer.msg("项目不能为空");
		return false;
	}
	if(viewNotes != null && viewNotes.length > 0){
		if(getLength(viewNotes) > 9000){
			layer.msg("访谈记录长度最大9000字符");
			return false;
		}
	}
	condition.projectId = projectId;
	condition.viewDateStr = viewDateStr;
	condition.viewNotes = viewNotes;
	
	return condition;
}
