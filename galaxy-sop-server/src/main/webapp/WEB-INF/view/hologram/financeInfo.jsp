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
</head>
<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

<body>
<ul class="h_navbar clearfix">
   <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基础<br/>信息</li>
   <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
   <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
   <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
   <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
   <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
   <li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('6')">财务</li>
   <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
   <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>

 </ul>
<jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include>               
                  <div class="tabtxt" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script type="text/javascript">
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO7", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				$("#page_list").tmpl(entity).appendTo('#page_all');
				$(".section").each(function(){
					$(this).showResults(true);
				});
			} else {

			}
		})
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
		var section = $(this).parents('.section');
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');
		$(".h#a_"+id_code).css("background","#fafafa");
		event.stopPropagation();
		$("#"+id_code).hide();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					sec.showResults();
					validate();
					$("#b_"+id_code).validate();
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
				} else {

				}
		}) 
	});
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		$(".h#a_"+id_code).css("background","#fff");
		event.stopPropagation();
	});
	//通用保存
	$('div').delegate(".h_save_btn","click",function(event){
		var id_code = $(this).attr('attr-save');
		event.stopPropagation();
		var sec = $(this).closest('form');
		var fields = sec.find("input[type='text'],input:checked,textarea");
		var data = {
			projectId : projectInfo.id
		};
		var infoModeList = new Array();
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var infoMode = {
				titleId	: field.data('titleId'),
				type : type
			};
			if(type==2 || type==3 || type==4)
			{
				infoMode.value = field.val()
			}
			else if(type==1)
			{
				infoMode.remark1 = field.val();
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
		//验证插件调用
		if(!$("#b_"+id_code).validate().form())
		{
			return;
		}
		if( beforeSubmit()){
			///validate();
			sendPostRequestByJsonObj(
					platformUrl.saveOrUpdateInfo , 
					data,
					function(data) {
						var result = data.result.status;
						if (result == 'OK') {
							updateInforTime(projectInfo.id,"financeTime");
							layer.msg('保存成功');
							$('#'+id_code).show();
							$('#b_'+id_code).remove();
							$(".h#a_"+id_code).css("background","#fff");
							var pid=$('#a_'+id_code).attr("data-section-id");
							 setDate(pid,true);	
						} else {

						}
				}) 
		}
		
	});
	/* $('div').delegate(".h_save_btn","click",function(event){
		var id_code = $(this).attr('attr-save');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
	}); */
	
	
</script>
</body>


</html>
