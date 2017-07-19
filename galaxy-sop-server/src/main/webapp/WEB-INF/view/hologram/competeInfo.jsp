<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目详情</title>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path%>/js/hologram/hologram_common.js"></script>
</head>

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>


<body>
<!--隐藏-->

<div class="bj_hui_on"></div>
<jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include>               
<div class="tabtxt" id="page_all">
<!--tab-->


<!--tab end-->
</div>


				

<script type="text/javascript">
var mustids = "${mustids}";
console.log(mustids);
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO5", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				debugger;
				var entity = data.entity;
				$("#page_list").tmpl(entity).appendTo('#page_all');
				$(".section").each(function(){
					$(this).showResults(true);
				});
				mustData(projectInfo.id,0);
				fun_click();
			} else {

			}
		})


	
$(function() {
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var _this = $(this).parents(".radius");
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		$(".bj_hui_on").hide();
		btn_disable(0);
		$(".h#a_"+id_code).css("background","#fff");
		event.stopPropagation();
	});
	
	//通用编辑显示
	$('div').delegate(".h_edit_btn", "click", function(event) {		var section = $(this).parents('.section');
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');
		var str ="";
		if($(this).parents(".h_btnbox").siblings(".h_title").find("span").is(":visible")){
			str =" <span style='color:#ff8181;display:inline'>（如果该项目涉及此项内容，请进行填写，反之可略过）</span>";
		}else{
			str ="";
		}
		event.stopPropagation();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					sec.showResults();
					var table = $(this).find('.mb_24 table');
					table.each(function(){
						if($(this).find('tr').length<=1){
							$(this).hide();
							if($(this).parents('dl').find('dd:gt(0)').length<=0){
								$(this).parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
							} 
						}
						else{
							$(this).show();
						}
					})
					$(".h#a_"+id_code).css("background","#fafafa");
					$("#"+id_code).hide();
					validate();
					btn_disable(1);
					$("#b_"+id_code).validate();
					$(".bj_hui_on").show();
					section.find(".h_title span").remove();
					section.find(".h_title").append(str);
					//文本域剩余字符数
					var textarea_h = section.find('.textarea_h');
					for(var i=0;i<textarea_h.length;i++){
						var len=textarea_h.eq(i).val().length;
						var initNum=textarea_h.parent('dd').find(".num_tj").eq(i).find("label").text();
						textarea_h.parent('dd').find(".num_tj").eq(i).find("label").text(initNum-len);
					}
					/* 文本域自适应高度 */
					for(var i=0;i<$("textarea").length;i++){
						var textareaId=$("textarea").eq(i).attr("id");
						autoTextarea(textareaId);
					}
					edit_bsaicfun();
				} else {

				}
		}) 
	});
	//通用保存
	$('div').delegate(".h_save_btn", "click", function(event) {
		event.stopPropagation();
		var _this = $(this);
		var save_this = $(this).parents('.radius');
		var id_code = $(this).attr('attr-save');
		var fields_value = $("#b_" + id_code).find("input:checked,option:selected");
		var fields_remark1 = $("#b_" + id_code).find("input[type='text'],textarea");
		var fields_value1 = $("#b_" + id_code).find(".active");
		var dt_type_3 = $("#b_" + id_code).find("dt[data-type='3']");
		//1:文本、2:单选、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、
		//7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)
		var data = {
			projectId : projectInfo.id
		};
		var infoModeList = new Array();
		$.each(fields_value, function() {
			var field = $(this);
			var valu = null;
			if (field.val() && field.val().length > 0) {
				valu = field.val();
			}
			var infoMode = {
				titleId : field.data('titleId'),
				type : field.data('type'),
				value : valu
			};
			infoModeList.push(infoMode);
		});
		$.each(fields_value1, function() {
			var field = $(this);
			var infoMode = {
				titleId : field.data('titleId'),
				type : field.data('type'),
				value : field.data('value')
			};
			infoModeList.push(infoMode);
		});
		$.each(fields_remark1, function() {
			var field = $(this);
			field.val(field.val().replace(/ /g,"&nbsp;"));
			var typ = field.data('type');
			var name = field.data('name');
			var value = field.val().replace(/\n/g,'<br/>');
			var _tochange =field.parents("dd").prev().attr("tochange");
			var _resultId = field.attr("resultId");
			if(_tochange==undefined){
				_tochange=false;
			}
			var infoMode = {
				titleId : field.data('titleId'),
				tochange:_tochange,
				resultId:_resultId,
				type : typ
			};
			
			if(typ == '12' || typ == '13' ){
				var disabled = field.attr("disabled");
				if(disabled && (disabled == true || disabled == "disabled")){
					infoMode.remark1 = null;
				}else{
					infoMode.remark1 = value;
				}
			}else if(typ == '15' && name == 'remark2'){
				infoMode.remark2 = value;
			}else{
				infoMode.remark1 = value;
			}
		
			infoModeList.push(infoMode);
		});
		data.infoModeList = infoModeList;
		//多选不选择的时候：
		var deletedResultTids = new Array();
		$.each(dt_type_3, function() {
			var _this = $(this);
			var active = _this.parent().find('dd .active');
			if(!(active && active.length > 0)){
				var tid = _this.data('titleId');
				deletedResultTids.push(tid);
			}
		});
		data.deletedResultTids = deletedResultTids;
		
		
		//表格
		var talbes = $("#b_"+id_code).find("[data-type='10']");
		if(talbes){
			var infoTableModelList = new Array();
			var deletedRowIds = new Array();
			
			for(var i=0; i<talbes.length; i++){
				var tid = $(talbes[0]).data("tid");
				
				var toAdds = table_tosave_Value[tid];
				if(toAdds){
					for(var key2 in toAdds){
						if(toAdds[key2]!=null) infoTableModelList.push(toAdds[key2]);
					}
					table_tosave_Value[tid] = {};
				}
				
				var toEdits = table_toedit_Value[tid];
				if(toEdits){
					for(var key2 in toEdits){
						if(toEdits[key2]!=null) infoTableModelList.push(toEdits[key2]);
					}
					table_toedit_Value[tid] = {};
				}
				
				var todels = table_delComArr[tid];
				if(todels && todels.length>0){
					for(var j=0; j<todels.length; j++){
						deletedRowIds.push(todels[j]);
					}
					table_delComArr[tid] = [];
				}
			}
			
			data.infoTableModelList = infoTableModelList;
			data.deletedRowIds = deletedRowIds;
		}
		if(!$("#b_"+id_code).validate().form())
		{
			return;
		}
		sendPostRequestByJsonObj(platformUrl.saveOrUpdateInfo, data, function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				updateInforTime(projectInfo.id,"competeTime");
				layer.msg('保存成功');
				$('#'+id_code).show();
				$('#b_'+id_code).remove();
				$(".bj_hui_on").hide();
				btn_disable(0);
				$(".h#a_"+id_code).css("background","#fff");
				var pid=$('#a_'+id_code).attr("data-section-id");
			    setDate(pid,true);	
			    toggle_btn($('.anchor_btn span'),0,save_this);
			} else {
				layer.msg('保存失败');
			}
		});
	});
});



</script>

</body>


</html>