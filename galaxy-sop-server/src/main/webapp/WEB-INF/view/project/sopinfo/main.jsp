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
<title>项目详情</title>

<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- jsp文件头和头部 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>

<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=path %>/js/validate/lib/jq.validate.js'></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />

<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/js/sopinfo.js"></script>
<script type="text/javascript">

var projectInfo;
$(function(){
	createMenus(5);
	var dtd = $.Deferred();
	$.when(getProjectInfo(dtd))
	.done(function(){
		initProjectData();
	});
	
	activeTab('${index}');
});
function initProjectData()
{
	$('.new_tit_b #project_name').text(projectInfo.projectName);
	$('.new_tit_b #project_code').text(projectInfo.projectCode);
}

function activeTab(index)
{
	$('#project-tabs li:eq('+index+')').addClass('on');
	$('#project-tabs div[data-tab="con"]:eq('+index+')').css('display','block');
}
function clickTab(ele)
{
	var index = $(ele).parent().index();
	if(index != '${index}')
	{
		activeTab(index);
	}
}
/**
*项目信息
*
**/
function getProjectInfo(dtd)
{
	var hasDtd = typeof(dtd) != 'undefined';
	var url = platformUrl.detailProject+"${projectId}";
	sendGetRequest(
		url,
		null,
		function(data){
			projectInfo = data.entity;
			if(hasDtd)
			{
				dtd.resolve();
			}
		}
	);
	if(hasDtd)
	{
		return dtd.promise();
	}
}


</script>
</head>
<body>
<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin">
    <jsp:include page="sopcommon.jsp" flush="true"></jsp:include>
        
        
        <div class="new_left" id="project-tabs">
        	<div class="tabtable assessment label_static">
          	<!-- tab标签 -->
            <jsp:include page="tab_header.jsp" flush="true"></jsp:include>

            <!-- 基本信息 -->
			<div  data-tab="con" ></div>
			<!-- 团队成员 -->
			<div data-tab="con"></div>
			<!-- 股权结构 -->
            <div  data-tab="con" >   
            	<c:if test="${aclViewProject==true }">
                <jsp:include page="/galaxy/project/tabShares">
		        	<jsp:param value="<%=request.getAttribute(\"projectId\") %>" name="id"/>
		        </jsp:include>
            	</c:if>
            </div>
            <!-- 访谈记录 -->
            <div  data-tab="con" ></div>
             <!-- 会议纪要 -->
            <div  data-tab="con" ></div>
             <!-- 项目文档 -->
            <div  data-tab="con" ></div>
             <!-- 操作日志 -->
            <div  data-tab="con" ></div>
            <!--tab end-->
          </div>
        </div>
       <!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>
    </div>
</div>
<!--隐藏-->
<div class="bj_hui_on"></div>
<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>
</html>
