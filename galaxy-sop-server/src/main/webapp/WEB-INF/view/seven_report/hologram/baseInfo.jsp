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
<title>项目详情-全息报告</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
var path = '<%=path%>';
</script>
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
</head>

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>


<body >
<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
<div class="ritmin">
    <jsp:include page="../reportcommon.jsp" flush="true"></jsp:include>
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
		<!-- 隐藏域，用于草稿箱20类型的全部清空操作 -->
		<span class="none" data-title-id="1108" data-type="14"></span>
			<div class="h radius" id="NO1_1" data-section-id="1116"> </div>
			
			<div class="h radius base_con2" id="NO1_2" data-section-id="1117"> </div>
			
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


<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>
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
<script src="<%=path %>/js/hologram/auto_save.js" type="text/javascript"></script>	
<script src="<%=path %>/js/hologram/auto_show.js" type="text/javascript"></script>	
<!-- 下拉多选框 -->	
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">
createMenus(5);
var isEditable = "${isEditable}";
function getDetailUrl(code)
{
	if(code == 'team-person')
	{
		return '<%=path%>/html/czr_pop.html';
	}
	return "";
}


table_Value = {};
table_filed = {};

delComArr=[];
table_toedit_Value = {};
table_tosave_Value = {};
userInfo=[];

var codeArr = ['NO1_1','NO1_2'];
//显示
sendGetRequest(platformUrl.getUsersInfo, null, function(data){
	userInfo=data.entityList; 
}); 
sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/", codeArr, backFun);   
$(function() {
	right_anchor("NO1");
	$(".exportReport").show();
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
		resouceShow('s');  //项目来源特殊处理
		event.stopPropagation();
		//base_half
		if(_this.is(':visible')){
			_this.siblings('.base_half').css('width','50%');
		}
		var code=_this.find("table").attr("data-code");  
		if($("table[data-code='team-person']").find("tbody tr").length>0){
			$("table[data-code='team-person']").show(); 
		} 
	});
	
	//通用编辑显示
	$('div').delegate(".h_edit_btn", "click", function(event) {		
		var _this = $(this);
		var base_editbtn = $(this);
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.radius');
		var sTop=$(window).scrollTop();
		event.stopPropagation();
		sendGetRequest(platformUrl.editProjectAreaInfo + pid + "/" + id_code, null, function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity; 
				var html = toGetHtmlByMark(entity, 'e');	 
				var s_div = toEditTitleHtml(entity, html);				
				$("#a_" + id_code).hide();
				$("#" + id_code).append(s_div);
				
				resouceShow('e');   //项目来源特殊处理
				//下拉多选添加resultId
				$.each(entity.childList,function(i,n){
					var title = this;
					buildTable(sec,title);    
					if(n.type=='23'){
						if(n.resultList){
							$.each(n.resultList,function(i,o){
								if(o.id){
									$('.selectpicker').find('option[value="'+o.valueId+'"]').attr('data-result-id',o.id);  //历史数据
									$('.selectpicker').find('option[value="'+o.contentChoose+'"]').attr('data-result-id',o.id);
								}
							})
						}
					}
				})
				if($("table[data-code='team-person'].editable").find("tbody tr").length>0){
					$("table[data-code='team-person']").show(); 
				}  
				//项目承揽人多选
				$('.selectpicker').selectpicker(); 
				$(".h#"+id_code).css("background","#fafafa");
				$(".bj_hui_on").show();
				//获取20类型的select下拉的值，便于草稿箱操作
				var span1108=$(".h#"+id_code).find('select[data-title-id="1108"]').val();
				$('span[data-title-id="1108"][data-type="14"]').text(span1108);
				draftbox(sec);    //草稿箱保存，回显公共方法
				var sTop=$(window).scrollTop();
				$(window).scrollTop(sTop);
				validate();
				$.each($('.textarea_h'),function(i,data){
					  $(this).val($(this).val().replace(/\<br\/\>/g,'\n'));
					  $(this).val($(this).val().replace(/&nbsp;/g," "));
					  var oldnum= $(this).siblings('p').find('label').html();
					  var font_num = oldnum - $(this).val().length;
					  $(this).siblings('p').find('label').html(font_num);
				});
				//21类型操作
				$(".select_21").next().find("select").change(function(){
					var s_res= $(this).find("option:selected").text();
					if(s_res=="其他"){
						$(this).parent().next().removeAttr("disabled").removeClass("disabled");
					}else{
						$(this).parent().next().attr("disabled","disabled").addClass("disabled");
					}
				})
				//23类型操作
				$('.selectpicker').change(function(){
					var valList=$(this).val();
					var val=$(this).val();
					if(val!='请选择'){
						$(this).closest('.resource_branch_01').find('.error_span').addClass('select_input');   //隐藏bitianxian
					}
					var lastId=$(this).find('option:last').attr('value');
					 if(JSON.stringify(valList).indexOf(lastId)>-1){
						 $(this).closest('.resource_branch_01').find('input').removeClass('select_input');
					} else{
						$(this).closest('.resource_branch_01').find('input').addClass('select_input');
						$(this).closest('.resource_branch_01').find('span.error').addClass('select_input');
					}
				})
				btn_disable(1);
				edit_bsaicfun("base");
				/*基本信息 多级联动change特殊  */
				/* 文本域自适应高度 */
				for(var i=0;i<$("textarea").length;i++){
					var textareaId=$("textarea").eq(i).attr("id");
					autoTextarea(textareaId);
				}
				//项目阶段select下拉特殊处理
				if(id_code=='NO1_1'){
					var valRound=$(".h_look dt[data-tid=\"1108\"]").siblings("dd").text();
					if(valRound!='尚未获投' && valRound !='不明确'){
						$('select[name="1108"]').find("option:first").remove();
					}
				}
			}
			//去除base_half 类名
			if(_this.is(':hidden')){
				_this.parents('.h_look').siblings('.h_edit').find('.base_half').css('width','100%');
				
			}
			var select_txt=$('select[name="1108"]').find("option:first").text();
			if(select_txt!='请选择'){
				$('.h_edit select[name="1108"]').find("option:first").css('color','#000');
			}
		})
		
	});
	
	//通用保存
	$('div').delegate(".h_save_btn", "click", function(event) {		
		var sec = $(this).closest('form');
		var beroreCheck = false;
		var sTop=$(window).scrollTop();
		event.stopPropagation();
		var _this = $(this);
		var id_code = $(this).attr('attr-save');
		var fields_value = $("#b_" + id_code).find("input:checked,option:selected");
		var fields_remark1 = $("#b_" + id_code).find("input[type='text'],textarea");
		//var fields_value1 = $("#b_" + id_code).find(".check_label");
		var fields_value1 = $("#b_" + id_code).find(".active");
		var dt_type_3 = $("#b_" + id_code).find("dt[data-type='3'],dt[data-type='6'],dt[data-type='13'],dt[data-type='23']");	
		var fields_value_li=$("#b_" + id_code).find(".selectpicker li.selected");   //23类型特殊处理
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
			if(field.parent().get(0).name=='1118'){
				return;
			}
			if (field.val() && field.val().length > 0) {
				valu = field.val();
			}
			var _tochange =field.closest("div").find("dt").attr("tochange");
			if(_tochange && _tochange == 'true'){
                var infoMode = null;
				//判断是否到是select
				if(sele=="SELECT"){
					if(field.parent().get(0).name=='1108'){
						if(valu==null){
							var valRound=$(".h_look dt[data-tid=\"1108\"]").siblings("dd").text();
							valu=valRound;
						}else{
							valu=field.val();
						}
						var _resultId = field.parent().attr("resultId");
					}
					var _resultId = field.parent().attr("resultId");
					if(_resultId==undefined  || _resultId=="undefined" || _resultId==""){
						_resultId=null
					}
					infoMode = {
							titleId : field.data('titleId'),
							type : field.data('type'),
							tochange:_tochange,
							resultId:_resultId,
							value : valu
						};
				}else{
					var _resultId = field.closest("dd").attr("resultId");
					if(_resultId==undefined  || _resultId=="undefined" || _resultId==""){
						_resultId=null
					}
					infoMode = {
							titleId : field.data('titleId'),
							type : field.data('type'),
							tochange:_tochange,
							resultId:_resultId,
                        	value : valu
						};
				}
				
				infoModeList.push(infoMode);
			}
		});
		$.each(fields_value_li, function() {   //下拉多选
			var field = $(this);
			var valu = null;
			var _tochange =field.closest(".resource_branch_01").find("dt").attr("tochange");
			if(_tochange && _tochange == 'true'){
	                var infoMode = null;
					valu=field.find('span').attr('data-value');
					var inpu=field.closest('.resource_branch_01').find('.input_21');
					var rvalue = inpu.val();
					var last_id=field.closest(".resource_branch_01").find('select').find('option:last').attr('value');
					if(valu==last_id){
						var remark=true
					}
					var _resultId = field.closest(".resource_branch_01").find('option[value="'+valu+'"]').data("resultId");
					if(_resultId==undefined  || _resultId=="undefined" || _resultId==""){
						_resultId=null
					}
					infoMode = {
							titleId : field.find('span').data('titleId'),
							type : field.find('span').data('type'),
							tochange:_tochange,
							resultId:_resultId,
							value : valu
						};
					if(remark==true){
						infoMode.remark1=rvalue;
					}
				infoModeList.push(infoMode);
			}
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
			if(typ=="21" || typ=="23"){
				return;
			}
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
				if(disabled == "disabled"){  //其它未选中
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
		if(beroreCheck){
			event.stopPropagation();
			return;
		}
		
		//多选不选择的时候：
		var deletedResultTids = new Array();
		$.each(dt_type_3, function() {
			var _this = $(this);
			var _tochange =_this.attr("tochange");
			if(_this.data('type')=='23'){
				var active = _this.parent().find('select option[selected]');
				if(_tochange && _tochange == 'true' && !(active && active.length > 0)){
					var tid = _this.data('tid');
					deletedResultTids.push(tid);
				}
			}else{
				var active = _this.parent().find('dd .active');
				if(_tochange && _tochange == 'true' && !(active && active.length > 0)){
					var tid = _this.data('titleId');
					deletedResultTids.push(tid);
				}
			}
		});
		data.deletedResultTids = deletedResultTids; 
		//表格
		var infoTableModelList = new Array(); 
		$.each(sec.find("table.editable"),function(){
			var that =$(this);
			deletedRowIdsDraft($(this));   //删除tr保存数据库再保存
			$.each(that.find('tr:gt(0)'),function(){ 
				var row = $(this).data(); 
				if(row.id=="")
				{
					row.id=null;
				}				
				if(row.resultId){
					row.id=row.resultId;
				}
				if(row.titleId=="1103"){ 
					delete row.field1Str;  
					delete row.field2Str; 
					delete row.field3; 
					delete row.field3Str; 
					delete row.field4; 
					delete row.field4Str;  
					delete row.field3Id;    
					infoTableModelList.push($(this).data());
				}else{
					infoTableModelList.push($(this).data());
				}
			});
		}); 
		data.infoTableModelList = infoTableModelList;
		data.deletedRowIds = deletedRowIds; 
		 
		var txtOption=$('dt[data-tid=\'1118\']').closest('.resource_branch_01').find('.filter-option').text();
		if(txtOption=='请选择'){
			$('dt[data-tid=\'1118\']').closest('.resource_branch_01').find('span.error_span').removeClass('select_input');
		}else{
			$('span.select_input').hide();
		}
		if(!$("#c_"+id_code).validate().form() || ($('span.error_span').closest('.resource_branch_01').is(':visible') && txtOption=='请选择'))
		{
			return;
		}
	   sendPostRequestByJsonObj(platformUrl.saveOrUpdateInfo, data, function(data) {
			var result = data.result.status; 
			if (result == 'OK') {
				updateInforTime(projectInfo.id,"NO1");
				layer.msg('保存成功');
				var pid = $('#'+id_code).data('sectionId');
				removeAutoData(pid);   //保存删除草稿箱数据
				showArea(id_code);
				$(".bj_hui_on").hide();
				btn_disable(0);
				toggle_btn($('.anchor_btn span'),1);
				$(".h_look .ismust").hide();
				$(".h#"+id_code).css("background","#fff");
				 $('html,body').scrollTop(sTop);  //定位
				 resouceShow('s');  //项目来源特殊处理  
				if($("table[data-code='team-person']").find("tbody tr").length>0){
					$("table[data-code='team-person']").show(); 
				} 
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
