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
			
			<div class="h radius" id="NO1_2"> </div>
			
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
		var id_code = $(this).attr('attr-hide');
		$('#a_' + id_code).show();
		$('#b_' + id_code).remove();
		event.stopPropagation();
	});
	
	//通用编辑显示
	$('div').delegate(".h_edit_btn", "click", function(event) {
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
				
				$.each($('.textarea_h'),function(i,data){
					  $(this).css("height",$(this).attr("scrollHeight"));
					  $(this).val($(this).val().replace(/\<br \/\>/g,'\n'));
					  var font_num = 2000 - $(this).val().length;
					  $(this).siblings('p').find('label').html(font_num);
					  var text_height = data.scrollHeight-20;
					  $(this).css("height",text_height) ;
				});
					 

			}
			//判断项目创新类型其他是否选中
			var other_classname = $(".pro_innovation .check_label:last").hasClass('active');
			console.log(other_classname);
			if(!other_classname){
				$(".pro_innovation .txt").attr("readonly","readonly");
			}else{
				$(".pro_innovation .txt").removeAttr("readonly");
			}
			//其他点击事件
			 $(".pro_innovation .check_label:last").click(function(){
				 var $txt = $(".pro_innovation .txt");
				 if ($txt.attr('readonly')) {
					 $txt.removeAttr('readonly');
				    } else {
				    	$txt.attr('readonly',true);
				    }
			 })
			
			//字数限制显示
			$.each($('.textarea_h'),function(i,data){
				$(this).val($(this).val().replace(/\<br \/\>/g,'\n'));
				var font_num = 2000 - $(this).val().length;
				$(this).siblings('p').find('label').html(font_num);
				var height = data.scrollHeight;
				$(this).css("height",height) ;
				 
			})
		})
	});
	
	//通用保存
	$('div').delegate(".h_save_btn", "click", function(event) {
		event.stopPropagation();
		var id_code = $(this).attr('attr-save');

		var fields_value = $("#b_" + id_code).find("input:checked,option:selected");
		var fields_remark1 = $("#b_" + id_code).find("input[type='text'],textarea");
		var fields_value1 = $("#b_" + id_code).find(".active");

		//1:文本、2:单选、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、
		//7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)

		var data = {
			projectId : projectInfo.id
		};
		var infoModeList = new Array();
		$.each(fields_value, function() {
			var field = $(this);
			if (field.val() && field.val().length > 0) {
				var infoMode = {
					titleId : field.data('titleId'),
					type : field.data('type'),
					value : field.val()
				};
				infoModeList.push(infoMode);
			}
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
			var value = field.val().replace(/\n/g,'<br />');
			var infoMode = {
				titleId : field.data('titleId'),
				type : field.data('type'),
				remark1 : value
			};
			infoModeList.push(infoMode);
		});
		data.infoModeList = infoModeList;
		
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
	});
});

</script>

</body>


</html>
