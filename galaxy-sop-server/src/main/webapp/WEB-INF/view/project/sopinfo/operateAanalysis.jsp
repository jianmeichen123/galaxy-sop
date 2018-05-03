<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<% 
	String path = request.getContextPath(); 
%>
<div class="member">
	<ul class="version19_tablink newTab180 clearfix">
		<li data-tab='nav' class="on">运营会议纪要</li>
		<li data-tab='nav'>健康状况</li>
		<li data-tab='nav'>运营数据记录</li>
	</ul>
	<div id="operateA" class="operateA">
	
	</div>
</div>
<script>

var defaultnum=4; 
$('.newTab180').tabLazyChange({
	defaultnum:defaultnum,
	onchangeSuccess:function(index){ 
		switch(index){
			case 0: health_meet();  break;  //标签0:基本信息
			case 1: health_change(); break;  //标签1:团队成员
			case 2: health_record(); break;  //标签2: 股权结构
			default: return false;
		} 
		//右侧刷新	 
		/* $.getDivHtml({
			domid : "operateA",
			url : platformUrl.toRight + "/" + projectId,//模版请求地址
			data:"",//传递参数				
		})  */
		
	}
});  
function health_meet(){ 
	$.getTabHtml({
		url : platformUrl.health_meet+ "/" + projectId,
		data : "",
		obj : $("#operateA"),
	});
}
function health_change(){ 
	$.getTabHtml({
		url : platformUrl.health_change+ "/" + projectId ,
		data : "",
		obj : $("#operateA"),
	});
}
function health_record(){ 
	$.getTabHtml({
		url : platformUrl.health_record+ "/" + projectId ,
		data : "",
		obj : $("#operateA"),
	});
}

</script>

























