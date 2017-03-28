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
<script src="<%=path %>/js/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>
<script src="<%=path%>/js/hologram/team_pop.js"></script>
</head>
<body>
<ul class="h_navbar clearfix">
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基本<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
                </ul>
 <div id="tab-content">
 <jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include>
		<div class="tabtxt" id="page_all">
		<!--tab-->


			<!--tab end-->
		</div>
	</div>

<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script type="text/javascript">
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO3", null,
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
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');

		event.stopPropagation();
		$("#"+id_code).hide();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {

				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					sec.showResults();
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
		event.stopPropagation();
		var sec = $(this).closest('.h_edit');
		var fields = sec.find("input[type='text'],input:checked,textarea,radio,li[class='check_label active']");
		var data = {
			projectId : projectInfo.id
		};

		if(sec.attr("id") =="b_NO3_1"){
        		//表格
        		var dataList = new Array();
        		$.each(sec.find("table.editable"),function(){
        			$.each($(this).find('tr:gt(0)'),function(){
        				var row = $(this).data("obj");
        				if(row.id=="")
        				{
        					row.id=null;
        				}
        				row.projectId=projectInfo.id
        				dataList.push(row);
        			});
        		});
                if(dataList.length==0){
                    var titleId = sec.find("table.editable").attr("data-title-id");
                    alert(titleId)
                    var json = {"projectId":projectInfo.id,"titleId":titleId}
                    dataList.push(json);
                }else if(dataList.length>10){
                    alert("最多只能添加10条记录!")
                    return false;
                }
                sendPostRequestByJsonObj(
                platformUrl.saveTeamMember,
                {"dataList":dataList},
                function(data) {
                    var result = data.result.status;
                    if (result == 'OK') {
                        layer.msg('保存成功');
                        var parent = $(sec).parent();
                        var id = parent.data('sectionId')
                        $(btn).next().click();
                        refreshSection(id)
                    } else {

                    }
            })
            return;
        }
		//普通结果
		var infoModeList = new Array();
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type') || field.closest('.h_edit_txt').find(':first-child').data('type');
			var infoMode = {
				titleId	: field.data('title-id') || field.closest('.h_edit_txt').find(':first-child').data('title-id'),
				type : type
			};
			if(type==2 || type==4)
			{
				infoMode.value = field.val()
			}
			else if (type==3)
			{
				infoMode.value = field.data('id')
			}
			else if(type==5)
			{
				if (field.is('textarea')){
					infoMode.remark1 = field.val()
				}else{
					infoMode.value = field.val()
				}
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
				console.log("data")
				console.log(data)
				infoTableModelList.push($(this).data());
			});
		});

		data.infoTableModelList = infoTableModelList;
		data.deletedRowIds = deletedRowIds;
        sendPostRequestByJsonObj(
        			platformUrl.saveOrUpdateInfo ,
        			data,
        			function(data) {
        				var result = data.result.status;
        				if (result == 'OK') {
        					layer.msg('保存成功');

        					deletedRowIds = new Array();
        					var parent = $(sec).parent();
        					//console.log(parent[0]);
        					var id = parent.data('sectionId');
        					//console.log(id);
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
	}else if (code =='team-members'){

	    return '<%=path%>/html/team_compile.html';
	}else if(code == 'share-holding')
    {
        return '<%=path%>/html/team_add_cgr.html';
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
				var data = $("#detail-form").serializeObject();
				saveRow(data);
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
			$("#detail-form input[name='code']").val($(ele).prev().data('code'));
			$("#save-detail-btn").click(function(){
				var data = $("#detail-form").serializeObject();
				saveRow(data);
			});
		}//模版反回成功执行
	});
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
