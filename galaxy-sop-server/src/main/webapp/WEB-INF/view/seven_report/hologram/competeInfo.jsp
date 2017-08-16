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
var key = Date.parse(new Date());
var keyJSON={};
var deleteJSON={};
var mustids = "${mustids}";
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO5", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				console.log(entity);
				$("#page_list").tmpl(entity).appendTo('#page_all');
				$(".section").each(function(){
					$(this).showResults(true);
					var table = $(this).find('.mb_24 table');
					table.each(function(){
    					resizetable($(this))
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
	$('div').delegate(".h_edit_btn", "click", function(event) {	
		var section = $(this).parents('.section');
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
					if(id_code=="NO5_1"){   //竞争俩字
						$("#b_"+id_code).closest(".section").find(".h_title").text("竞争");
					}
					sec.showResults();
					var table = $(this).find('.mb_24 table');
					table.each(function(){
					    resizetable($(this))
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
		 $(".editable").each(function(){resizetable($(this))})
		//编辑表格显示隐藏
		 check_table();
	});
	//通用保存
	$('div').delegate(".h_save_btn", "click", function(event) {
		var save_this = $(this).parents('.radius');
		var btn = this;
		var id_code = $(this).attr('attr-save');
		event.stopPropagation();
		var sec = $(this).closest('form');
		var fields = sec.find("input[type='text'],input:checked,textarea");
		var fields_value1=sec.find("li[class='check_label active'],li.active");
		var data = {
			projectId : projectInfo.id
		};

		//普通结果
		var infoModeList = new Array();
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var _tochange =field.parents("dd").prev().attr("tochange");
			var sele = field.parent().get(0).tagName;
			if(sele=="SELECT"){
				var _resultId = field.parent().attr("resultId");
			}else{
				var _resultId = field.attr("resultId");
			}
			if(_tochange==undefined){
				_tochange=false;
			}
			var infoMode = {
				titleId	: field.data('titleId'),
				tochange:_tochange,
				resultId:_resultId,
				type : type
			};
			if(type==2 || type==3 || type==4)
			{
				infoMode.value = field.val()
			}
			else if(type==1)
			{
				infoMode.remark1 = field.val()
			}
			else if(type==8)
			{
				var str=field.val();
				var str=str.replace(/\n|\r\n/g,"<br>")
				var str=str.replace(/\s/g,"&nbsp;");
				infoMode.remark1 = str;
			}
			infoModeList.push(infoMode);
		});
		data.infoModeList = infoModeList;
		//多选和多选加备注特殊处理
		$.each(fields_value1, function() {			
			var field = $(this);
			var _tochange =field.parents("dd").prev().attr("tochange");
			if(_tochange==undefined){
				_tochange=false;
			}			
			if(_tochange == true||_tochange == "true"){
				var _resultId = field.attr("resultId");
				if(_resultId==undefined  || _resultId=="undefined"){
					_resultId=null
				}
				console.log(field);
				var infoMode = {
						titleId : field.data('titleId'),
						type : field.data('type'),
						tochange:_tochange,
						resultId:_resultId,
						value : field.attr('value')
					};
				var type = field.data('type');
				if(type==13){
					var field_v = field.data('id');
	                 var last_id = field.closest('ul').find('li.check_label:last').attr('data-id');
	                 var dt = field.closest('dt[data-type="13"]');
	                 if ( field_v == last_id)
	                 {
	                 	//其他
	                     infoMode.remark1 = field.closest('.h_edit_txt').find('input:last').val();
	                 }
	                 else
	                 {
	                     infoMode.remark1 = '' ;
	                 }
				}
	             
				infoModeList.push(infoMode);
			}
			
		});
		//表格
		var infoTableModelList = new Array();
		$.each(sec.find("table.editable"),function(){
			$.each($(this).find('tr:gt(0)'),function(){
				var row = $(this).data();
				if(row.id=="")
				{
					row.id=null;
				}
				infoTableModelList.push($(this).data());
			});
		});
	
		data.infoTableModelList = infoTableModelList;
		data.deletedRowIds = deletedRowIds;
//估值表格显示隐藏
		$.each($('table.editable'),function(){
			var table_id = $(this).attr('data-title-id');
			var noedi_table = $('table[data-title-id='+table_id+']')
			if($(this).find('tr').length<=1){
				if(noedi_table.parents('dl').find('dd').length<= 2){
					$('table[data-title-id='+table_id+']').parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
				}
				noedi_table.hide();
			}
			else{
				noedi_table.show();
				noedi_table.parents('dl').find('.no_enter').remove();
				
			}
		})
		//上传图片相关
		var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
		var key = keyJSON["b_"+id_code];
		var deleteids = deleteJSON["delete_"+id_code];
		var params = {};
		params.projectId =  projectInfo.id;
		params.fileReidsKey = key;
		params.deleteids = deleteids;
		
		if(!$("#b_"+id_code).validate().form())
		{
			return;
		}
		
		/* sendPostRequestByJsonObj(
			platformUrl.saveOrUpdateInfo , 
			data,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					updateInforTime(projectInfo.id,"financingTime");
					layer.msg('保存成功');
					$(".h#a_"+id_code).css("background","#fff");
					$(".bj_hui_on").hide();
					deletedRowIds = new Array();
					var parent = $(sec).parent();
					var id = parent.data('sectionId');
					$(btn).next().click();
					refreshSection(id);
					btn_disable(0);
				    toggle_btn($('.anchor_btn span'),0,save_this);
				} else {

				}
		}) */ 
	//$("body").showLoading();
		console.log(data);
		sendPostRequestByJsonObjNoCache(sendFileUrl,params,true,function(dataParam){
			//进行上传
			var result = dataParam.result.status;
			if(result == "OK"){
				sendPostRequestByJsonObjNoCache(
						platformUrl.saveOrUpdateInfo , 
						data,
						true,
						function(data) {
							var result = data.result.status;
							if (result == 'OK') {
								updateInforTime(projectInfo.id,"financingTime");
								layer.msg('保存成功');
								$(".h#a_"+id_code).css("background","#fff");
								$(".bj_hui_on").hide();
								deletedRowIds = new Array();
								var parent = $(sec).parent();
								var id = parent.data('sectionId');
								$(btn).next().click();
								$(".loading-indicator-overlay").remove();
								$(".loading-indicator").remove();
								refreshSection(id);
								picData(projectInfo.id);
								btn_disable(0);
							    toggle_btn($('.anchor_btn span'),0,save_this);
							    //重新格式化表格,select radio 字段id转换value
							    $('table').each(function(){
                                    resizetable($(this))
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
							} else {
								layer.msg("操作失败!");
							}
					});
			}else{
				layer.msg("操作失败!");
			}
			
		});

	});
});
	function getDetailUrl(code)
	{
		if(code == 'equity-structure')
		{
			return '<%=path%>/html/funcing_add_gd.html';
		}
		else if(code == 'investor-situation')
		{
			return '<%=path%>/html/funcing_add_tz.html';
		}
		else if(code =='operation-indices')
		{
			return '<%=path%>/html/fincing_add_yx.html';
		}
		else if(code == 'valuation-reference')
		{
			return '<%=path%>/html/fincing_add_tl.html';
		}
		else if(code == 'financing-milestone')
		{
			return '<%=path%>/html/fincing_add_jd.html';
		}else if(code == 'competition-comparison')
		{
			return '<%=path%>/html/compete_save.jsp';
		}
		return "";
	}
</script>

</body>


</html>