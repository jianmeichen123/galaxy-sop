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
<title>创意</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>


<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>


<!-- bootstrap-table  -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>


<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>

<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>


<!-- upload -->
<script id="a" src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/fx.upload.js" type="text/javascript"></script>

<!-- 多文本 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<!-- 圈圈 -->
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>

<script src="<%=path %>/js/zixun.js"></script>
</head>


<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
	<div class="ritmin">
 	
       	<div class="tabtable assessment label_static1"  data-id='tab-block'>
          	<!-- tab标签 -->
            <ul class="tablink tablinks projectDetail">
				<li data-tab="nav" data-name="tag_zixun">创意资讯</li>
				<li data-tab="nav" data-name="tag_cy">项目创意</li>
				<%-- <c:choose>
					<c:when test="${isThyy }">
							<li data-tab="nav" class="no" disabled="disabled">团队成员</li>
							<li data-tab="nav" class="no" disabled="disabled">股权结构</li>
					</c:when>
					<c:when test="${aclViewProject==true }">
							<li data-tab="nav">团队成员</li>
							<li data-tab="nav">股权结构</li>
					</c:when>
					<c:otherwise>
						<li data-tab="nav" class="no" disabled="disabled">项目文档</li>
						<li data-tab="nav" class="no" disabled="disabled">操作日志</li>
					</c:otherwise>
				</c:choose> --%>
				
			</ul> 			
		</div>
    
    </div>
        
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
</body>

<script type="text/javascript">

var indextoid = "${indextoid}";
var defaultnum = 0;
if(indextoid){
	defaultnum = 1;
}


$(function(){
	
	createMenus(21);
	
	$('.projectDetail').tabLazyChange({
		defaultnum:defaultnum,
		onchangeSuccess:function(index){
			switch(index){
				case 0: initTabZixun();  break;  
				case 1: initTabIdea();   break;  
				default: return false;
			}
		}
	});
	
	/* if(indextoid){
		$("[data-name='tag_cy']").click();
		$(".projectDetail li").removeClass();
		$("[data-name='tag_cy']").addClass("on");
		initTabIdea();
	} */
	
})

function initTabZixun(){
	defaultnum = 0;
	$.getTabHtml({
		url : Constants.sopEndpointURL + "/galaxy/zixun/index"
	});
}

function initTabIdea(){
	defaultnum = 0;
	$.getTabHtml({
		url : platformUrl.ideaList,
	});
}

</script>

</html>