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
<body>
	<ul class="h_navbar clearfix">
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')">基本<br />信息 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br />数据 </li>
		<li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('4')">竞争</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br />策略 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br />估值 </li>
	</ul>


	<div id="tab-content">
		<div class="tabtxt" id="page_all">

			<div class="h radius" id="NO5_2">

				<div class="h_edit">
					<div class="h_btnbox">
						<span class="h_save_btn">保存</span>
						<span class="h_cancel_btn" data-on="h_cancel">取消</span>
					</div>
					
					<div class="h_title">股权结构合理性</div>
					
					<div class="mb_16">
						<dl class="h_edit_txt clearfix">
							<dt data-type="10" data-id="1325" data-code="NO3_8_1" data-parentid="1301">团队重要成员是否拥有足够、合理的股权:</dt>
							<dd class="fl_none">
								<table>
									<tbody>
										<tr>
											<th>姓名</th>
											<th>职位</th>
											<th>性别</th>
											<th>最高学历</th>
											<th>操作</th>
										</tr>
										<tr>
											<th>罗振宇</th>
											<td>CEO</td>
											<td>男</td>
											<td>博士</td>
											<td><span class="blue" data-btn="btn">查看</span><span
												class="blue" data-btn="btn">编辑</span><span class="blue"
												data-btn="btn">删除</span></td>
										</tr>
									</tbody>
								</table>
								<span class="pubbtn bluebtn" href="html/team_xk.html"
									data-btn="addmen">新增</span>
							</dd>
						</dl>
					</div>
					
					<div class="h_edit_btnbox clearfix">
						<span class="pubbtn bluebtn fl" data-on="save">保存</span> <span
							class="pubbtn fffbtn fl" data-name="basic" data-on="h_cancel">取消</span>
					</div>
				</div>
			</div>

			<div class="h radius" id="NO5_1"> </div>
			<div class="h radius" id="NO5_3"> </div>
			<div class="h radius" id="NO5_4"> </div>
			<div class="h radius" id="NO5_5"> </div>
			<div class="h radius" id="NO5_6"> </div>
			<div class="h radius" id="NO5_7"> </div>
			<div class="h radius" id="NO5_8"> </div>
			
		</div>
	</div>




				

<script type="text/javascript">
	//页面显示
	/* sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid +"/NO5_1", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			console.log(entity);
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO5_1").html(s_div);
		}
	}); */
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid + "/NO5_1",
			null, function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					var html = toGetHtmlByMark(entity, 's');
					var s_div = toShowTitleHtml(entity, html);
					$("#NO5_1").html(s_div);
				}
			});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid + "/NO5_3",
			null, function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					var html = toGetHtmlByMark(entity, 's');
					var s_div = toShowTitleHtml(entity, html);
					$("#NO5_3").html(s_div);
				}
			});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid + "/NO5_4",
			null, function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					var html = toGetHtmlByMark(entity, 's');
					var s_div = toShowTitleHtml(entity, html);
					$("#NO5_4").html(s_div);
				}
			});
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid + "/NO5_5", null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					var html = toGetHtmlByMark(entity, 's');
					var s_div = toShowTitleHtml(entity, html);
					$("#NO5_5").html(s_div);
				}
			});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid + "/NO5_6",
			null, function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					var html = toGetHtmlByMark(entity, 's');
					var s_div = toShowTitleHtml(entity, html);
					$("#NO5_6").html(s_div);
				}
			});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid + "/NO5_7",
			null, function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					var html = toGetHtmlByMark(entity, 's');
					var s_div = toShowTitleHtml(entity, html);
					$("#NO5_7").html(s_div);
				}
			});
	sendGetRequestTasync(platformUrl.queryProjectAreaInfo + pid + "/NO5_8",
			null, function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					var html = toGetHtmlByMark(entity, 's');
					var s_div = toShowTitleHtml(entity, html);
					$("#NO5_8").html(s_div);
				}
			});

	$(function() {
		//通用取消编辑
		$('div').delegate(".h_cancel_btn", "click", function(event) {
			var id_code = $(this).attr('attr-hide');
			$('#a_' + id_code).show();
			$('#b_' + id_code).remove();
			event.stopPropagation();
		});

		//通用编辑显示
		$('div').delegate(
				".h_edit_btn",
				"click",
				function(event) {
					var id_code = $(this).attr('attr-id');
					event.stopPropagation();
					sendGetRequest(platformUrl.editProjectAreaInfo + pid + "/"
							+ id_code, null, function(data) {
						var result = data.result.status;
						if (result == 'OK') {
							var entity = data.entity;
							var html = toGetHtmlByMark(entity, 'e');
							var s_div = toEditTitleHtml(entity, html);

							$("#a_" + id_code).hide();
							$("#" + id_code).append(s_div);
						}
					})
				});

		//通用保存
		$('div').delegate(
				".h_save_btn",
				"click",
				function(event) {
					event.stopPropagation();
					var id_code = $(this).attr('attr-save');

					var fields_value = $("#b_" + id_code).find(
							"input:checked,option:selected");
					var fields_remark1 = $("#b_" + id_code).find(
							"input[type='text'],textarea");
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
						var infoMode = {
							titleId : field.data('titleId'),
							type : field.data('type'),
							remark1 : field.val()
						};
						infoModeList.push(infoMode);
					});
					data.infoModeList = infoModeList;

					sendPostRequestByJsonObj(platformUrl.saveOrUpdateInfo,
							data, function(data) {
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