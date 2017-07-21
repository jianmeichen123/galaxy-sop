<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!doctype html>
<html class="scroll">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目详情</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
var pageId = "project";
</script>
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
</head>

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>


<body >
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
<div class="ritmin">
    <jsp:include page="../project/sopinfo/sopcommon.jsp" flush="true"></jsp:include>
    <div class="new_left">
       	<ul class="h_navbar clearfix">
			<li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('0')">基础<br />信息 </li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br />数据 </li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br />策略 </li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
			<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
			<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br />估值 </li>
		</ul>
		<div id="tab-content base" class="base_tab-content"  data-id="tab-block">
		<div class="tabtxt" id="page_all"> 
		
			<div class="h radius" id="NO1_1"> </div>
			
			<div class="h radius base_con2" id="NO1_2"> </div>
			
		</div>
	</div>
	
	</div>
    <!--右边-->
<%--     <jsp:include page="./includeRight.jsp" flush="true"></jsp:include> --%>
    <div class="new_right" id="new_right"></div>
    
    
	

       <!--隐藏-->
<div class="bj_hui_on"></div>
	
	</div>

</div>


<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<!-- 公用js -->
<script src="<%=path%>/js/jquery-1.12.2.min.js"></script>
<script src="<%=path %>/js/common.js"></script>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/teamSheetNew.js"></script>
<script src="<%=path %>/js/planbusiness.js"></script>
<script src="<%=path %>/js/projectDetail/tabFile.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/projectDetail.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/appro.js" type="text/javascript"></script>
<script src="<%=path %>/js/base_appropriation.js" type="text/javascript"></script>
<script src="<%=path %>/js/batchUpload.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/proPerOp.js"></script>
<script src="<%=path %>/js/v_baseInfo_project_history.js" type="text/javascript"></script>
 <!-- layer -->
<script src="<%=path %>/js/layer/layer.js"></script>
<!--提示验证  -->
<script src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script src="<%=path %>/js/hologram/base_table.js"></script>
<script src="<%=path %>/js/hologram/baseInfo.js"></script>	
<script src="<%=path%>/js/hologram/hologram_common.js"></script>			

<script type="text/javascript">
createMenus(5);
var isEditable = "${isEditable}";



table_Value = {};
table_filed = {};

delComArr=[];
table_toedit_Value = {};
table_tosave_Value = {};
var codeArr = ['NO1_1','NO1_2'];
sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/", codeArr, backFun);
	

$(function() {
	right_anchor(1);
	//通用取消编辑
	$('div').delegate(".h_cancel_btn", "click", function(event) {
		var _this = $(this);
		var id_code = $(this).attr('attr-hide');
		$('#a_' + id_code).show();
		$('#b_' + id_code).remove();
		$(".bj_hui_on").hide();
		$(".h#"+id_code).css("background","#fff");
		dtWidth();
		btn_disable(0);
		event.stopPropagation();
		//base_half
		if(_this.is(':visible')){
			_this.siblings('.base_half').css('width','50%');
		}
	});
	
	//通用编辑显示
	$('div').delegate(".h_edit_btn", "click", function(event) {
		var _this = $(this);
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
				$(".bj_hui_on").show();
				var sTop=$(window).scrollTop();
				$.each($('.textarea_h'),function(i,data){
					  $(this).val($(this).val().replace(/\<br\/\>/g,'\n'));
					  $(this).val($(this).val().replace(/&nbsp;/g," "));
					  var oldnum= $(this).siblings('p').find('label').html();
					  var font_num = oldnum - $(this).val().length;
					  $(this).siblings('p').find('label').html(font_num);
				});
				btn_disable(1);
				edit_bsaicfun("base");
				/*基本信息 多级联动change特殊  */
				/* 文本域自适应高度 */
				for(var i=0;i<$("textarea").length;i++){
					var textareaId=$("textarea").eq(i).attr("id");
					autoTextarea(textareaId);
				}
				/* $('html,body').scrollTop(sTop);  //定位 */
			}
			//去除base_half 类名
			if(_this.is(':hidden')){
				_this.parents('.h_look').siblings('.h_edit').find('.base_half').css('width','100%');
				
			}
		})
		
	});
	
	//通用保存
	$('div').delegate(".h_save_btn", "click", function(event) {
		var beroreCheck = false;
		
		event.stopPropagation();
		var _this = $(this);
		var id_code = $(this).attr('attr-save');
		var fields_value = $("#b_" + id_code).find("input:checked,option:selected");
		var fields_remark1 = $("#b_" + id_code).find("input[type='text'],textarea");
		//var fields_value1 = $("#b_" + id_code).find(".check_label");
		var fields_value1 = $("#b_" + id_code).find(".active");
		var dt_type_3 = $("#b_" + id_code).find("dt[data-type='3'],dt[data-type='6'],dt[data-type='13']");		
		//1:文本、2:单选、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、
		//7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)
		var data = {
			projectId : projectInfo.id
		};
		var infoModeList = new Array();
		$.each(fields_value, function() {
			var field = $(this);
			var valu = null;
			var sele = field.parent().get(0).tagName;
			if (field.val() && field.val().length > 0) {
				valu = field.val();
			}
			//判断是否到是select
			if(sele=="SELECT"){
				var _resultId = field.parent().attr("resultId");
			}else{
				var _resultId = field.attr("resultId");
			}
			var _tochange =field.closest("div").find("dt").attr("tochange");
			
			if(_tochange==undefined){
				_tochange=false;
			}
			var infoMode = {
				titleId : field.data('titleId'),
				type : field.data('type'),
				tochange:_tochange,
				resultId:_resultId,
				value : valu
			};
			infoModeList.push(infoMode);
		});
		$.each(fields_value1, function() {
			var field = $(this);
			// 有active  选中的class ，isActived = true，     不选中，isActived = false；
			/* var isActived=false;
			if($(this).hasClass("active")){
				isActived = true;
			} */
			
			var _tochange =field.closest("div").find("dt").attr("tochange");
			if(_tochange==undefined){
				_tochange=false;
			}
			
			if(_tochange == 'true'){
				var _resultId = field.attr("resultId");
				if(_resultId==undefined  || _resultId=="undefined"){
					_resultId=null
				}
				
				/* if(_resultId != null || isActived == true){
					var vlau  = null;
					if(isActived == true){
						vlau = field.data('value');
					}
				}
				 */
				var infoMode = {
						titleId : field.data('titleId'),
						type : field.data('type'),
						tochange:_tochange,
						resultId:_resultId,
						value : field.data('value')
					};
				infoModeList.push(infoMode);
			}
			
		});
		$.each(fields_remark1, function() {
			var field = $(this);
			var typ = field.data('type');
			var name = field.data('name');
			var _tochange =field.closest("div").find("dt").attr("tochange");
			var _resultId = field.attr("resultId");
			if(_resultId==undefined||_resultId=="undefined"){
				_resultId=null
			}
			if(_tochange==undefined){
				_tochange=false;
			}
			if(typ == '5'){
				field.val(field.val().replace(/ /g,"&nbsp;"));
			}
			var value = field.val().replace(/\n/g,'<br/>');
			var infoMode = {
				titleId : field.data('titleId'),
				tochange:_tochange,
				resultId:_resultId,
				type : typ
			};
			
			if(typ == '12' || typ == '13' ){
				var value = field.val();
				var disabled = field.attr("disabled");
				if(disabled && (disabled == true || disabled == "disabled")){  //其它未选中
					infoMode.remark1 = null;
				}else{
					if(value.trim() && value.trim().length > 0){
						infoMode.remark1 = value;
					}else{
						$(field.next()).show();
						beroreCheck = true;
					}
				}
			}else if(typ == '15' && name == 'remark2'){
				infoMode.remark2 = value;
			}else{
				infoMode.remark1 = value;
			}
		
			infoModeList.push(infoMode);
		});
		data.infoModeList = infoModeList;
		
		
		if(beroreCheck){
			event.stopPropagation();
			return;
		}
		//多选不选择的时候：
		var deletedResultTids = new Array();
		$.each(dt_type_3, function() {
			var _this = $(this);
			var _tochange =_this.attr("tochange");
			var active = _this.parent().find('dd .active');
			if(_tochange && _tochange == 'true' && !(active && active.length > 0)){
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
		if(!$("#c_"+id_code).validate().form())
		{
			return;
		}
		console.log("!!!!!!!!!!!!!!!!");
		console.log(data);
		sendPostRequestByJsonObj(platformUrl.saveOrUpdateInfo, data, function(data) {
			var result = data.result.status;
			console.log(data)
			if (result == 'OK') {
				updateInforTime(projectInfo.id,"informationTime");
				layer.msg('保存成功');
				showArea(id_code);
				$(".bj_hui_on").hide();
				btn_disable(0);
				toggle_btn($('.anchor_btn span'),1);
				$(".h_look .ismust").hide();
				$(".h#"+id_code).css("background","#fff");
			} else {
				layer.msg('保存失败');
			}
		});
		//base_half
		if(_this.is(':visible')){
			_this.siblings('.base_half').css('width','50%');
		}
		dtWidth();
	});
});

</script>

</body>


</html>
