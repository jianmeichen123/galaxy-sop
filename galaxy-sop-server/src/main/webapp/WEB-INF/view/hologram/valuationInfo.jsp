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

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

<title>项目详情</title>
</head>
<body>
<ul class="h_navbar clearfix">
     <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基本<br/>信息</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
    <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('8')">融资及<br/>估值</li>

  </ul>
  <jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include>
  <div id="tab-content">
		<div class="tabtxt" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>
	</div>


<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path%>/js/hologram/hologram_common.js"></script>
<script type="text/javascript">
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO9", null,
		function(data) {
		console.log(data);
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				$("#page_list").tmpl(entity).appendTo('#page_all');
				customBuilder();
				$(".section").each(function(){
					$(this).showResults(true);
				});
				//调整表格
				$("table").css({"width":"80%","table-layout":"fixed"});
				//页面显示表格现实与隐藏
				$.each($('.mb_24 table'),function(){
					if($(this).find('tr').length<=1){
						$(this).hide();
						$(this).parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
						}
					else{
						$(this).show();
					}
				})
			} else {

			}
			
		})
	function customBuilder()
	{
		var div = $("div[data-code='NO9_3_12']");
		var titleId = div.data('titleId');
		var table = $("<table data-title-id='"+titleId+"'></table>")
		var header = $("<tr></tr>");
		var row = $("<tr></tr>");
		
		var dls = $("dl[data-parent-id='"+titleId+"']");
		$.each(dls,function(){
			var dl = $(this);
			var name = dl.find('dt').text();
			var dd = dl.find('dd');
			header.append("<th>"+name.replace(':','')+"</th>");
			row.append("<td class='field' data-title-id='"+dd.data('titleId')+"'>未填写</td>")
		});
		dls.remove();
		table.append(header);
		table.append(row);
		div.after(table);
		
	}
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');
		var sTop=$(window).scrollTop();
		event.stopPropagation();
		$("#"+id_code).hide();
		$(".h#a_"+id_code).css("background","#fafafa");
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					sec.showResults();
					validate();
					$("#b_"+id_code).validate();
					//调整表格
					$("table").css({"width":"90%","table-layout":"fixed"});
					$(".h_edit .sign_title").css("margin-bottom","20px");
					//文本域剩余字符数
					for(var i=0;i<$(".textarea_h").length;i++){
						var len=$(".textarea_h").eq(i).val().length;
						var initNum=$(".num_tj").eq(i).find("label").text();
						$(".num_tj").eq(i).find("label").text(initNum-len);
						
					}
					/* 文本域自适应高度 */
					for(var i=0;i<$("textarea").length;i++){
						var textareaId=$("textarea").eq(i).attr("id");
						autoTextarea(textareaId);
					}
					//检查表格tr是否10行
					check_table_tr_edit();
				} else {

				}
		}) 
		$('body,html').scrollTop(sTop);  //定位
		//编辑表格显示隐藏
		 check_table();
	});
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		$(".h#a_"+id_code).css("background","#fff");
		event.stopPropagation();
		deletedRowIds = new Array();
	});
	//通用保存
	$('div').delegate(".h_save_btn","click",function(event){
		var btn = this;
		var id_code = $(this).attr('attr-save');
		event.stopPropagation();
		var sec = $(this).closest('form');
		var fields = sec.find("input[type='text'],input:checked,textarea");
		var data = {
			projectId : projectInfo.id
		};
		//普通结果
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
		
		if(!$("#b_"+id_code).validate().form())
		{
			return;
		}
		
		sendPostRequestByJsonObj(
			platformUrl.saveOrUpdateInfo , 
			data,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					updateInforTime(projectInfo.id,"financingTime");
					layer.msg('保存成功');
					$(".h#a_"+id_code).css("background","#fff");
					deletedRowIds = new Array();
					var parent = $(sec).parent();
					var id = parent.data('sectionId');
					$(btn).next().click();
					refreshSection(id)
				} else {

				}
		}) 
	});
function refreshSection(id)
{
	var sec = $(".section[data-section-id='"+id+"']");
	sec.showResults(true);
}
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
	}
	return "";
}
function editRow(ele)	
{
	var code = $(ele).closest('table').data('code');
	var row = $(ele).closest('tr');
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			var title = $("#pop-title");
			title.text(title.text().replace('添加','编辑'));
			$.each($("#detail-form").find("input, select, textarea"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				ele.val(row.data(name));
			});
			$("#detail-form input[name='index']").val(row.index());
			$("#save-detail-btn").click(function(){
				saveForm($("#detail-form"));
				
			});
		}//模版反回成功执行	
	});
}
var deletedRowIds = new Array();
function delRow(ele)
{
	var _div=$(ele).closest("div");
	var tableId=$(ele).closest("table").data('titleId');
	layer.confirm('是否删除?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero) {
		var tr = $(ele).closest('tr');
		var id = tr.data('id');
		
		if(typeof id != 'undefined' && id>0)
		{
			deletedRowIds.push(id);
		}
		tr.remove();
		check_table();   
		if(!has_len_tr(tableId,10)){   //检查是否10条tr
			$(_div).find(".bluebtn").show();
		}
		$(".layui-layer-close1").click();
	}, function(index) {
	});
	
}
function addRow(ele)
{
	var code = $(ele).prev().data('code');
	var _this = $(ele);
	var tableId=$(ele).prev().data('titleId');
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			$("#detail-form input[name='projectId']").val(projectInfo.id);
			$("#detail-form input[name='titleId']").val($(ele).prev().data('titleId'));
			$("#save-detail-btn").click(function(){
				saveForm($("#detail-form"));
				check_table();
				if(has_len_tr(tableId,10)){   //检查是否10条tr
					_this.hide();
				} 
			});
		}//模版反回成功执行	
	});
}
function saveForm(form)
{
	if($(form).validate().form())
	{
		var data = $(form).serializeObject();
		saveRow(data);
	}
}

/**
 * 保存至到tr标签data属性
 */
function saveRow(data)
{
	data = JSON.parse(data);
	var titleId = data.titleId;
	var index = data.index;
	if(typeof index == 'undefined' || index == null || index == '')
	{
		var tr = buildRow(data,true);
		$('table[data-title-id="'+titleId+'"].editable').append(tr);
	}
	else
	{
		var tr = $('table[data-title-id="'+titleId+'"].editable').find('tr:eq('+index+')');
		for(var key in data)
		{
			if(key.indexOf('field')>-1)
			{
				tr.data(key,data[key]);
				tr.find('td[data-field-name="'+key+'"]').text(data[key]);
			}
		}
	}
	$("a[data-close='close']").click();
}

function getTableRowLimit(code)
{
	if(code == 'investor-situation' || code =='operation-indices')
	{
		return 20;
	}
	return 10;
}
</script>
</body>


</html>
