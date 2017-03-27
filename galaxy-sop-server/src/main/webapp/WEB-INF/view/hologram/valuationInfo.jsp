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
  <div id="tab-content">
		<div class="tabtxt" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>
	</div>

<!--点击编辑例子 -->
<script id="ifelse" type="text/x-jquery-tmpl">
<form id="b_\${code}">
<div class="h_edit section" >
	<div class="h_btnbox">
		<span class="h_save_btn" attr-save="\${code}">保存</span><span class="h_cancel_btn"
			data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}
		
	{{if sign=="3"}}
		<div>\${name}</div>
		{{each(i,childList) childList}}
		<div class="mb_16">
	   <dl class="h_edit_txt clearfix">
		<dt data-type="\${type}"  data-title-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
		{{if type=="1"}}
		<dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>

		{{else type=="2"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-title-id="\${titleId}" data-type="\${type}" name="\${titleId}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="3"}}
		<dd class="fl_none">
		<ul class="h_edit_checkbox clearfix">
			{{each(i,valueList) valueList}}
			<li class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="4"}}
		{{each(i,valueList) valueList}}
		<dd>
		  <select name="" id="">
			<option value="">\${name}</option>
		  </select>
		</dd>
		{{/each}}

		{{else type=="5"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">500</label>/500
			</p>
		</dd>


		{{else type=="6"}}
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}

		{{else type=="7"}}
			<dd class="fl_none clearfix">
			 <ul class="h_imgs">

			 </ul>
			 <ul class="h_imgs">
				<li class="h_imgs_add"><input type="file"></li>
			</ul>
			</dd>
			<dd class="fl_none red">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>
		{{else type=="8"}}
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">0</label>/2000
			</p>
		</dd>

		{{else type=="10"}}
		<dd class="fl_none">
			< data-title-id="\{id}" class="editable"></table>
			<span class="pubbtn bluebtn margin_btn" onclick="addRow(this)">新增</span>
		</dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>
		
		{{else type=="12"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>


		{{else type=="13"}}
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" placeholder="\${placeholder}"/></dd>
		
		{{else type=="14"}}
		<select data-id="\${id}">
		{{each(i,valueList) valueList}}
		<option data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</option>
		{{/each}}
		</select>


		{{/if}}
	  </dl>
	</div>

		{{/each}}
	{{else}}
	<div class="mb_16">
	   <dl class="h_edit_txt clearfix">		
		{{if type=="1"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>

		{{else type=="2"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" name="\${titleId}" value="\${id}" data-title-id="\${titleId}" data-type="\${type}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="3"}}
		<dt class="fl_none" data-type="\${type}"  data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
		<dd class="fl_none">
		<ul class="h_edit_checkbox clearfix">
			{{each(i,valueList) valueList}}
			<li class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="4"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd>
		  <select name="" id="">
			<option value="">\${name}</option>
		  </select>
		</dd>
		{{/each}}

		{{else type=="5"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" data-value="\${value}" data-type="\${type}" placeholder="\${placeholder}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${titleId}" data-type="\${type}" data-parentId="\${parentId}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">500</label>/500
			</p>
		</dd>

		{{else type=="6"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}

		{{else type=="7"}}
        <dd class="fl_none clearfix">
        <ul class="h_imgs mgedit"  id="edit-\${id}"></ul>
        <ul class="h_imgs">
        <li class="h_imgs_add"><input type="file" file-title-id="\${id}" id="selected_file_\${id}"></li>
        </ul>
        </dd>
        <dd class="fl_none red">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>

		{{else type=="8"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">0</label>/2000
			</p>
		</dd>

		{{else type=="10"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd class="fl_none">
			<table data-title-id="\${id}"  class="editable">

			</table>
			<span class="pubbtn bluebtn margin_btn" onclick="addRow(this)">新增</span>
		  </dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>


		{{else type=="13"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" placeholder="\${placeholder}"/></dd>
		
		{{else type=="14"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<select data-id="\${id}">
		{{each(i,valueList) valueList}}
		<option data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</option>
		{{/each}}
		</select>


		{{/if}}
	  </dl>
	</div>


	{{/if}}

	{{/each}}
	<div class="h_edit_btnbox clearfix">
	  <span class="pubbtn bluebtn h_save_btn fl" data-on="save" attr-save="\${code}">保存</span>
	  <span class="pubbtn fffbtn fl h_cancel_btn" data-name="basic" data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>

</div>
</form>
</script>



<!--页面例子 -->
<script id="page_list" type="text/x-jquery-tmpl">
{{each(i,childList) childList}}
<div class="h radius section" id="a_\${code}" data-section-id="\${id}">
  <div class="h_look h_team_look clearfix" id="\${code}">
	<c:if test="${isEditable}">
	<div class="h_btnbox"><span class="h_edit_btn" attr-id="\${code}">编辑</span></div>
	</c:if>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}                    
	{{if sign=="3"}}
	<div>\${name}</div>
		{{each(i,childList) childList}}
			<div class="mb_24 clearfix">
	  <dl class="clearfix">
		<dt data-type="\${type}" data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>

		{{if type=="5"}}                        
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		<dd>备注</dd>

		{{else type=="2"}}
		<dd class="field" data-value="\${value}" data-title-id="\${id}" data-code="\${code}">未选择</dd>

		{{else type=="3"}}
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="6"}}
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="7"}}
		<dd class="fl_none mglook" id="look-\${id}" ata-value="\${value}" data-id="\${id}" data-code="\${code}">
			</dd>

		{{else type=="8"}}
		<dd class="fl_none field" data-title-id="\${id}">未填写</dd>

		{{else type=="4"}}
		{{each(i,valueList) valueList}}
		<dd>未选择</dd>
		{{/each}}
		
		{{else type=="10"}}
		<dd><table data-title-id="\${id}"></table></dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="13"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="14"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{/if}}                      
		</dl>		
	</div>
		{{/each}}

	{{else}}
	<div class="mb_24 clearfix">
	  <dl class="clearfix">
		{{if type=="1"}}
		<dt  data-type="\${type}">\${name}</dt>  
         <dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="5"}}       
		<dt  data-type="\${type}">\${name}</dt>                 
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		<dd>备注</dd>

		{{else type=="2"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-value="\${value}" data-title-id="\${id}" data-code="\${code}">未选择</dd>

		{{else type=="3"}}
		<dt  data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="6"}}
		<dt  data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="7"}}
		<dd class="fl_none mglook" id="look-\${id}" ata-value="\${value}" data-id="\${id}" data-code="\${code}">
			</dd>

		{{else type=="8"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd class="fl_none field" data-title-id="\${id}">未填写</dd>

		{{else type=="4"}}
		<dt data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd>未选择</dd>
		{{/each}}

		{{else type=="10"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd><table data-title-id="\${id}"></table></dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="13"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="14"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{/if}}                      
		</dl>		
	</div>

	{{/if}}
	{{/each}}
  </div>
</div>
{{/each}}
</script>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path%>/js/hologram/hologram_common.js"></script>
<script type="text/javascript">
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO9", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				console.log(entity)
				$("#page_list").tmpl(entity).appendTo('#page_all');
				$(".section").each(function(){
					$(this).showResults(true);
				});
			} else {

			}
		})
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');
		
		event.stopPropagation();
		$("#"+id_code).hide();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					console.log(entity)
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					sec.showResults();
					validate();
					$("#b_"+id_code).validate();
				} else {

				}
		}) 
	});
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
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
			else if(type==1 || type==8)
			{
				infoMode.remark1 = field.val()
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
					layer.msg('保存成功');
					
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
	if(confirm('确定要删除？'))
	{
		var tr = $(ele).closest('tr');
		var id = tr.data('id');
		
		if(typeof id != 'undefined' && id>0)
		{
			deletedRowIds.push(id);
		}
		tr.remove();
	}
	
}
function addRow(ele)
{
	var code = $(ele).prev().data('code')
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			$("#detail-form input[name='projectId']").val(projectInfo.id);
			$("#detail-form input[name='titleId']").val($(ele).prev().data('titleId'));
			$("#save-detail-btn").click(function(){
				saveForm($("#detail-form"));
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
</script>
</body>


</html>
