<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<ul class="tablink tablinks projectDetail">
		<li data-tab="nav"><a href="javascript:;">基本信息</a></li>
	<c:choose>
		<c:when test="${isThyy }">
				<li data-tab="nav" class="no"><a href="javascript:;">团队成员</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">股权结构</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">访谈记录</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">会议纪要</a></li>	
				<li data-tab="nav"><a href="javascript:;">交割前事项</a></li>
				<li data-tab="nav"><a href="javascript:;">注资信息</a></li>
				<li data-tab="nav"><a href="javascript:;">运营分析</a></li>
				<li data-tab="nav"><a href="javascript:;">项目文档</a></li>
				<li data-tab="nav"><a href="javascript:;">操作日志</a></li>
		</c:when>
		<c:when test="${aclViewProject==true }">
				<li data-tab="nav"><a href="javascript:;">团队成员</a></li>
				<li data-tab="nav"><a href="javascript:;">股权结构</a></li>
				<li data-tab="nav"><a href="javascript:;">访谈记录</a></li>
				<li data-tab="nav"><a href="javascript:;">会议纪要</a></li>	
				<li data-tab="nav"><a href="javascript:;">交割前事项</a></li>
				<li data-tab="nav"><a href="javascript:;">注资信息</a></li>
				<li data-tab="nav"><a href="javascript:;">运营分析</a></li>
				<li data-tab="nav"><a href="javascript:;">项目文档</a></li>
				<li data-tab="nav"><a href="javascript:;">操作日志</a></li>
		</c:when>
		<c:otherwise>
				<li data-tab="nav" class="no"><a href="javascript:;">团队成员</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">股权结构</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">访谈记录</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">会议纪要</a></li>	
				<li data-tab="nav" class="no"><a href="javascript:;">交割前事项</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">注资信息</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">运营分析</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">项目文档</a></li>
				<li data-tab="nav" class="no"><a href="javascript:;">操作日志</a></li>
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
