<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8"> 
<title>星河投</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include> 
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/> 

</head>

<body >
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
		<div class="ritmin searchPage"> 
			<p class='searchNumber'>共搜索到<span class="totalNumber">24</span>条结果</p>
			
			<div class='search-top'>
				<ul class="clearfix to-task-tips">
					<li data-tab="nav" class="bottomColor">创投项目<span class='ventrueTotal'></span></li>
					<li data-tab="nav">外部项目<span class="outerTotal"></span></li>
					<li data-tab="nav">资讯<span class="zixunTotal"></span></li>
				</ul>
				
			</div>
			<div data-id="tab-block" class="searchContainer">
	    		<!-- <div id='tab-content' data-id="tab-content">
			    	<div class='tabtxt searchContainer'>
			    	
			    	</div>
		    	</div> -->
	    	</div>
		
		
		
		
		</div> 
		
</div>

</body> 
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
	  
</html>
<script>
$(function(){
	//导航
	createMenus(5);

/* 导航切换 */
 $('.search-top ul li').click(function(){
	var _this = $(this);
	_this.addClass('bottomColor').siblings().removeClass('bottomColor');
 });

 detailHeaderWidth();
 function detailHeaderWidth(){
 	  var  w_lft=$(".lft").width();
 	  	$('.searchPage').css({'margin-left':w_lft+20});
 	  	$('.searchPage').css('width','1395px')
 }
 $(window).resize(function(){
 	detailHeaderWidth();
 })	
 
 
 
 
 


});
$('.to-task-tips').tabLazyChange({
	defaultnum:0,
	onchangeSuccess : function(index){
		switch(index){
			case 0 : initTabventrueProject();break;
			case 1 :initTabOuterProject();break;
			case 2 :initTabConsultProject();break;
		}
		
		
	}
})	
	//页面请求地址
	function initTabventrueProject(){
		$.getTabHtml({
			url : platformUrl.toVentrueProject
		});
	}
	function initTabOuterProject(){
		
		$.getTabHtml({
			url : platformUrl.toOuterProject
		});
	}
	function initTabConsultProject(){
		$.getTabHtml({
			url : platformUrl.toConsultProject
		});
	}

	


</script>
