<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<% 
	String path = request.getContextPath(); 
    java.util.Date date=new java.util.Date();
    String url=(String)request.getAttribute("flagUrl");
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
<%-- <link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css"  type="text/css"> --%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
   <!--bootstrap-table-->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!--  <script src="<%=path %>/js/soptask.js" type="text/javascript"></script>-->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

</head>

<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix task-pagebox">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
        <!--页眉-->
        <div class="top clearfix task-top taskdetail-top">
        	<div class='taskdetail-top-title'><h2 id="task-title"></h2><div class='come-back' onclick="window.history.back();"><span class='task-back'></span><span class='task-back-msg'>返回</span></div></div>
            <!--tips连接-->
        	<ul class="tipslink task_tipslink to-task-tips">
            	<li data-tab="nav" class="on task-tips-li"><a href="javascript:;" id="all" query-by="all" query-val="all">任务详情<span></span></a></li>
                <li data-tab="nav" class='task-tips-li'><a href="javascript:;" id="todeal" query-by="taskStatus" query-val="taskStatus:2">操作日志<span></span></a></li>
          	</ul>
        </div>
	    <!--右中部内容-->
	    <div class="ritmin taskDetail-ritmin">
	    	<div data-id="tab-block">
	    		<div id='tab-content' data-id="tab-content">
			    	<div class='tabtxt'>
			    	
			    	</div>
		    	</div>
	    	</div>
		</div>
	    	
    	 
    </div>
 	
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=request.getContextPath() %>/js/axure_ext.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>	
<!-- bootstrap-table -->
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=request.getContextPath() %>/js/init.js"></script>	

<script type="text/javascript">
//计算距离的左边距
detailHeaderWidth();
function detailHeaderWidth(){
	  var  w_lft=$(".lft").width();
	  	$('.task-top').css({'margin-left':w_lft});
}
$(window).resize(function(){
	detailHeaderWidth();
})
	//ul tab切换显示
	$('.to-task-tips').tabLazyChange({
		defaultnum:0,
		onchangeSuccess : function(index){
			switch(index){
				case 0 : initTabTaskMessage();break;
				case 1 :initTabTaskLog();break;
			}
		}
	})	
	//页面请求地址
	function initTabTaskMessage(){
		$.getTabHtml({
			url : platformUrl.toTaskMesage+'?taskId=${taskId}'
		});
	}
	function initTabTaskLog(){
		console.log(platformUrl.toTaskLog)
		$.getTabHtml({
			url : platformUrl.toTaskLog+'?taskId=${taskId}'
		});
	}
    var flag="${flagUrl}";
    var num=0;
    if(flag=="jl"){
    	num=10;
    }
    if(flag=="pz"){
    	num=11;
    }
    if(flag=="gq"){
    	num=12;
    }
    if(flag=="jz"){
    	num=9;
    }
	$(function(){
		var flag="${flagUrl}";
		var num=2;
		if(flag=="jz"){
			num=9;
		}
		if(flag=="jl"){
			num=10;		
				}
		if(flag=="gq"){
			num=12;
		}
		if(flag=="pz"){
			num=11;
		}
		createMenus(num);
		
	});
</script>
</html>
