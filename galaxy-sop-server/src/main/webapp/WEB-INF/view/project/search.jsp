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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]--> 
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include> 
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/> 
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>  
</head>

<body >
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
		<div class="ritmin searchPage"> 
			<p class='searchNumber'>共搜索到<span>24</span>条结果</p>
			
			<div class='search-top'>
				<ul class="clearfix to-task-tips">
					<li data-tab="nav" class="bottomColor">创投项目<span>（12）</span></li>
					<li data-tab="nav">外部项目<span>（6）</span></li>
					<li data-tab="nav">咨询<span>（6）</span></li>
				</ul>
				
			</div>
			<div data-id="tab-block">
	    		<div id='tab-content' data-id="tab-content">
			    	<div class='tabtxt searchContainer'>
			    	
			    	</div>
		    	</div>
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
 $('.selectpicker').selectpicker({
  style: 'btn-info',
  size: 4
});
/* 导航切换 */
 $('.search-top ul li').click(function(){
	var _this = $(this);
	_this.addClass('bottomColor').siblings().removeClass('bottomColor');
 });

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
