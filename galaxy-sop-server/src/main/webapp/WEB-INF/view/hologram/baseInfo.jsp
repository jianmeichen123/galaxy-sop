<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目详情</title>
</head>

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>


<body>
	<ul class="h_navbar clearfix">
		<li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('0')">基本<br />信息 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br />数据 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br />策略 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br />估值 </li>
	</ul>


	<div id="tab-content base" class="base_tab-content">
		<div class="tabtxt" id="page_all"> 
		
			<div class="h radius" id="NO1_1"> </div>
			
			<div class="h radius base_con2" id="NO1_2"> </div>
			
		</div>
	</div>




				

<script type="text/javascript">
var isEditable = "${isEditable}";

table_Value = {};
table_filed = {};

delComArr=[];
table_toedit_Value = {};
table_tosave_Value = {};
var codeArr = ['NO1_1','NO1_2'];
sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/", codeArr, backFun);
	

$(function() {
	//通用取消编辑
	$('div').delegate(".h_cancel_btn", "click", function(event) {
		var _this = $(this);
		var id_code = $(this).attr('attr-hide');
		$('#a_' + id_code).show();
		$('#b_' + id_code).remove();
		$(".h#"+id_code).css("background","#fff");
		event.stopPropagation();
		if(_this.is(':visible')){
			console.log("编辑隐藏");
			$('.base_half').css('width','50%');
		}
	});
	
	//通用编辑显示
	$('div').delegate(".h_edit_btn", "click", function(event) {
		var base_editbtn = $(this);
		var id_code = $(this).attr('attr-id');
		event.stopPropagation();
		sendGetRequest(platformUrl.editProjectAreaInfo + pid + "/" + id_code, null, function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				var html = toGetHtmlByMark(entity, 'e');
				var s_div = toEditTitleHtml(entity, html);
				$("#a_" + id_code).hide();
				$("#" + id_code).append(s_div);
				$(".h#"+id_code).css("background","#fafafa");
				
				$.each($('.textarea_h'),function(i,data){
					  $(this).css("height",$(this).attr("scrollHeight"));
					  $(this).val($(this).val().replace(/\<br \/\>/g,'\n'));
					  $(this).val($(this).val().replace(/&nbsp;/g," "));
					  var font_num = 2000 - $(this).val().length;
					  $(this).siblings('p').find('label').html(font_num);
					  var text_height = data.scrollHeight-20;
					  $(this).css("height",text_height) ;
				});
			}
			
			//去除base_half 类名
			if(base_editbtn.is(':hidden')){
				console.log("编辑隐藏");
				$('.base_half').css('width','100%');
			}
			
			//字数限制显示
			$.each($('.textarea_h'),function(i,data){
				$(this).val($(this).val().replace(/\<br \/\>/g,'\n'));
				var font_num = 2000 - $(this).val().length;
				$(this).siblings('p').find('label').html(font_num);
				var height = data.scrollHeight;
				$(this).css("height",height+10) ;
				 
			})
		})
	});
	
	//通用保存
	$('div').delegate(".h_save_btn", "click", function(event) {
		event.stopPropagation();
		var _this = $(this);
		var id_code = $(this).attr('attr-save');

		var fields_value = $("#b_" + id_code).find("input:checked,option:selected");
		var fields_remark1 = $("#b_" + id_code).find("input[type='text'],textarea");
		var fields_value1 = $("#b_" + id_code).find(".active");
		var dt_type_3 = $("#b_" + id_code).find("dt[data-type='3']");
		
		$(".h#"+id_code).css("background","#fff");
		
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
			var value = field.val().replace(/\n/g,'<br />');
			var infoMode = {
				titleId : field.data('titleId'),
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
		
		sendPostRequestByJsonObj(platformUrl.saveOrUpdateInfo, data, function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				layer.msg('保存成功');
				showArea(id_code);
			} else {
				layer.msg('保存失败');
			}
		});
		//base_half
		if(_this.is(':visible')){
			console.log("编辑隐藏");
			$('.base_half').css('width','50%');
		}
	});
});



</script>

</body>


</html>
