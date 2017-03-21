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


	<div id="tab-content">
		<div class="tabtxt" id="page_all"> 
		
			<div class="h radius" id="NO1_1">
				
			
			</div>
			
			<div class="h radius" id="NO1_2">
			
			</div>
			
		</div>
	</div>




				

<script type="text/javascript">

	
	function toGetHtmlByMark(title,mark){
		var tilelist = title.childList;
		var html = "";
		$.each(tilelist,function(i,o){
			if(this.sign  && this.sign == 3){
				html += "<div class=\"mb_24 clearfix\">" + this.name + "</div>";
				sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/" + this.code, null, function(data) {
					var result = data.result.status;
					if (result == 'OK') {
						var sign_title = data.entity;
						html += switchTypeByMark(sign_title,mark);
					}
				});
			}else{
				html += switchTypeByMark(this,mark);
			}
		});
		return html;
	}
	
	function toShowTitleHtml(title,html){
		var s_div = 
			"<div class=\"h_look h_team_look clearfix\" id=\"a_"+title.code+"\" >" +
				"<div class=\"h_btnbox\">" +
			    	"<span class=\"h_edit_btn\" attr-id='" + title.code + "'>编辑</span>" +
			    "</div>" +
				"<div class=\"h_title\">" + title.name + "</div>" +
				html +
			"</div>";
			
		return s_div;
	}


	//页面显示
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/NO1_1", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			console.log(entity);
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO1_1").html(s_div);
		}
	});
	//页面显示
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/NO1_2", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			var html = toGetHtmlByMark(entity,'s');
			var s_div = toShowTitleHtml(entity, html);
			$("#NO1_2").html(s_div);
		}
	});
	

	function toEditTitleHtml(title,html){
		var s_div = 
			"<div class=\"h_edit h_team_look clearfix\" id=\"b_"+title.code+"\" >" +
				"<div class=\"h_btnbox\">" +
			    	"<span class=\"h_save_btn\" >保存</span>" +
			    	"<span class=\"h_cancel_btn\" data-on=\"h_cancel\" attr-hide=\""+title.code+"\" >取消</span>" +
			    "</div>" +
				"<div class=\"h_title\">" + title.name + "</div>" +
				html +
				"<div class=\"h_edit_btnbox clearfix\">" +
			    	"<span class=\"pubbtn bluebtn fl\" >保存</span>" +
			    	"<span class=\"pubbtn h_cancel_btn fffbtn fl\" data-on=\"h_cancel\" attr-hide=\""+title.code+"\" >取消</span>" +
			    "</div>" +
			"</div>";
			
		return s_div;
	}
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
		var id_code = $(this).attr('attr-id');
		
		event.stopPropagation();
		
		$("#a_"+id_code).hide();
		 sendGetRequest(platformUrl.editProjectAreaInfo + pid +"/"+id_code, null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					var html = toGetHtmlByMark(entity,'e');
					var s_div = toEditTitleHtml(entity, html);
					
					$("#"+id_code).append(s_div);
				}
		}) 
	});
	
</script>

</body>


</html>
