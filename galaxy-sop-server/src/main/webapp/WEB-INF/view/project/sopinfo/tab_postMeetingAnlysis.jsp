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

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<!-- 富文本编辑器 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/sopinfo.js"></script>
</head>



<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin">
	
    	<jsp:include page="sopcommon.jsp" flush="true"></jsp:include>
		
        <div class="new_left">
        	<div class="tabtable assessment label_static">
			<!-- tab标签 -->
            <ul class="tablink tablinks">
                <li><a href="javascript:;" onclick="showTabs('${pid}',0)">基本信息</a></li>
                <c:choose>
	            <c:when test="${aclViewProject==true }">
                <li><a href="javascript:;" onclick="showTabs('${pid}',1)">团队成员</a></li>
                <li><a href="javascript:;" onclick="showTabs('${pid}',2)">股权结构</a></li>
                <li><a href="javascript:;" onclick="showTabs('${pid}',3)">访谈记录</a></li>
                <li><a href="javascript:;" onclick="showTabs('${pid}',4)">会议纪要</a></li>
				<li class="on"><a href="javascript:;" onclick="showTabs('${pid}',9)">运营分析</a></li>
                <li><a href="javascript:;" onclick="showTabs('${pid}',5)">项目文档</a></li>
                <li><a href="javascript:;" onclick="showTabs(${pid},6)">操作日志</a></li>
                </c:when>
                <c:otherwise>
                <li class="no"><a href="javascript:;">团队成员</a></li>
                <li class="no"><a href="javascript:;">股权结构</a></li>
                <li class="no"><a href="javascript:;">访谈记录</a></li>
                <li class="no"><a href="javascript:;">会议纪要</a></li>
				<li class="no"><a href="javascript:;">项目文档</a></li>
                <li class="no"><a href="javascript:;">操作日志</a></li> 
                </c:otherwise>
             	</c:choose>
            </ul>
            <!-- 运营分析 -->
            <div id="post_meeting_anlysis">
            	<div class="member proOperation">
                    <div class="top clearfix">
                        <!--按钮-->
                        <div class="btnbox_f btnbox_f1 clearfix">
                        	<c:if test="${isCreatedByUser}">
                            <a class="pbtn bluebtn h_bluebtn" href="tchtml/conference.html" data-btn="conference" data-name='添加运营会议纪要'>添加运营会议纪要</a>
                        	</c:if>
                            <a class="pbtn bluebtn h_bluebtn" href="tchtml/health_case.html" data-btn="health_case" data-name='健康状况变更记录'>健康状况记录变更</a>
                            <c:if test="${isCreatedByUser}">
                            <a class="pbtn bluebtn h_bluebtn" href="tchtml/statustc.html" data-btn="status" data-name='健康状况'>健康状态</a>
                            </c:if>
                        </div>
                    </div>
                    <!-- 搜索条件 -->
                    <div class="min_document pro_analysis clearfix">
                    <form id="search_meet">
                      <div class="bottom searchall clearfix">
                        <dl class="fmdl fmdll clearfix">
                          <dt>类型：</dt>
                          <dd id="search_meet_type">
                          </dd>
                        </dl>
                        <dl class="fmdl fmdll clearfix">
                          <dt>会议日期：</dt>
                          <dd>
								<input type="text" class="datepicker txt time" name="meet_startDate"  /> 
								<span>至</span>
								<input type="text" class="datepicker txt time" name="meet_endDate"  />
						  </dd>
                          <dd><a href="javascript:;" class="bluebtn ico cx">查询</a></dd>
                        </dl>
                      </div>
                      </form>
                    </div>                            
                    <!--表格内容-->
                    <table name="meetGrid" width="100%" cellspacing="0" cellpadding="0" class="commonsize delivery">
                    </table>
        		</div>  
        	</div>
          </div>
        </div>
        <!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>	
    </div>
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>

<script src="<%=path %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<!-- 分页二css+四js -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- file -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>

<script type="text/javascript" src="<%=path%>/js/tabPostMeetingAnlysis.js"></script>

</html>


