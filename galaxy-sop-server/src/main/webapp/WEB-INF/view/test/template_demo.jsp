<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星test</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=path %>/css/bootstrap.min-v3.3.5.css"  />-->
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- 保存进度条 -->
<link href="<%=path %>/css/showLoading.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
    <!--右中部内容-->
 	<div class="ritmin">
 	<a class="blue" href="javascript:team_fun(11)">团队查看</a>&nbsp;&nbsp;&nbsp;
 	<a class="blue" href="javascript:team_compile(11)">团队编辑</a>&nbsp;&nbsp;&nbsp;
 	</div>
 
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>


<script>
//本页面相关  查看
function team_fun(id){
	$.getHtml({
		url:'/sop/html/team_xk.html',//模版请求地址
		data:"",//传递参数
		okback:function(date){
			$('#qualifications_popup_name').html('名字')
		}
			
	});
}
//编辑
function team_compile(id){
	$.getHtml({
		url:'/sop/html/team_compile.html',//模版请求地址
		data:"",//传递参数
		okback:function(date){
			$('#qualifications_popup_name').html('编辑简历')
		}
			
	});
}
//学习经历
function team_learn(id){
	$.getHtml({
		url:'/sop/html/team_learn.html',//模版请求地址
		data:"",//传递参数
		okback:function(date){
			$('#team_learn').html('添加学习经历')
		}
			
	});
}
//工作经历
function team_work(id){
	$.getHtml({
		url:'/sop/html/team_work.html',//模版请求地址
		data:"",//传递参数
		okback:function(date){
			$('#team_learn').html('添加工作经历')
		}
			
	});
}
//教育经历
function team_education(id){
	$.getHtml({
		url:'/sop/html/team_education.html',//模版请求地址
		data:"",//传递参数
		okback:function(date){
			$('#team_learn').html('添加教育经历')
		}
			
	});
}
</script>

</body>
</html>