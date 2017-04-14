<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<ul class="tablink tablinks projectDetail">
<%-- 	<li data-index="0"><a href="javascript:;" onClick="showTabs(${projectId},0)">基本信息</a></li> --%>
		<li data-tab="nav">基本信息</li>
	<c:choose>
		<c:when test="${isThyy }">
				<li data-tab="nav" class="no" disabled="disabled">团队成员</li>
				<li data-tab="nav" class="no" disabled="disabled">股权结构</li>
				<li data-tab="nav" class="no" disabled="disabled">访谈记录</li>
				<li data-tab="nav" class="no" disabled="disabled">会议纪要</li>	
				<li data-tab="nav">交割前事项</li>
				<li data-tab="nav">注资信息</li>
				<li data-tab="nav">运营分析</li>
				<li data-tab="nav">项目文档</li>
				<li data-tab="nav">操作日志</li>
				<li data-tab="nav">全息报告</li>
		</c:when>
		<c:when test="${aclViewProject==true }">
				<li data-tab="nav">团队成员</li>
				<li data-tab="nav">股权结构</li>
				<li data-tab="nav">访谈记录</li>
				<li data-tab="nav">会议纪要</li>	
				<li data-tab="nav">交割前事项</li>
				<li data-tab="nav">注资信息</li>
				<li data-tab="nav">运营分析</li>
				<li data-tab="nav">项目文档</li>
				<li data-tab="nav">操作日志</li>
				<li data-tab="nav">全息报告</li>
		</c:when>
		<c:otherwise>
				<li data-tab="nav" class="no" disabled="disabled">团队成员</li>
				<li data-tab="nav" class="no" disabled="disabled">股权结构</li>
				<li data-tab="nav" class="no" disabled="disabled">访谈记录</li>
				<li data-tab="nav" class="no" disabled="disabled">会议纪要</li>	
				<li data-tab="nav" class="no" disabled="disabled">交割前事项</li>
				<li data-tab="nav" class="no" disabled="disabled">注资信息</li>
				<li data-tab="nav" class="no" disabled="disabled">运营分析</li>
				<li data-tab="nav" class="no" disabled="disabled">项目文档</li>
				<li data-tab="nav" class="no" disabled="disabled">操作日志</li>
				<li data-tab="nav" class="no" disabled="disabled">全息报告</li>
		</c:otherwise>
	</c:choose>
</ul>
<script type="text/javascript">
var index = "${param.index}";
if(index != null && index != '' )
{
	$('li[data-index='+index+']').addClass('on');
}

</script>
