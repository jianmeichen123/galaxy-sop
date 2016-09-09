<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<ul class="tablink tablinks">
	<li data-index="0"><a href="javascript:;" onClick="showTabs(${projectId},0)">基本信息</a></li>
	<c:choose>
		<c:when test="${isThyy }">
			<li class="no"><a href="javascript:;">团队成员</a></li>
			<li class="no"><a href="javascript:;">股权结构</a></li>
			<li class="no"><a href="javascript:;">访谈记录</a></li>
			<li class="no"><a href="javascript:;">会议纪要</a></li>
			<li data-index="7"><a href="javascript:;" onclick="showTabs(${projectId},7)">交割前事项</a></li>
			<li data-index="8"><a href="javascript:;" onclick="showTabs('null/${projectId}',8)">拨款信息</a></li>
			<li data-index="9"><a href="javascript:;" onclick="showTabs(${projectId},9)">运营分析</a></li>
			<li data-index="5"><a href="javascript:;" onclick="showTabs(${projectId},5)">项目文档</a></li>
			<li data-index="6"><a href="javascript:;" onclick="showTabs(${projectId},6)">操作日志</a></li>
		</c:when>
		<c:when test="${aclViewProject==true }">
			<li data-index="1"><a href="javascript:;" onClick="showTabs(${projectId},1)">团队成员</a></li>
			<li data-index="2"><a href="javascript:;" onClick="showTabs(${projectId},2)">股权结构</a></li>
			<li data-index="3"><a href="javascript:;" onclick="showTabs(${projectId},3)">访谈记录</a></li>
			<li data-index="4"><a href="javascript:;" onclick="showTabs(${projectId},4)">会议纪要</a></li>
			<li data-index="7"><a href="javascript:;" onclick="showTabs(${projectId},7)">交割前事项</a></li>
			<li data-index="8"><a href="javascript:;" onclick="showTabs('null/${projectId}',8)">拨款信息</a></li>
			<li data-index="9"><a href="javascript:;" onclick="showTabs(${projectId},9)">运营分析</a></li>
			<li data-index="5"><a href="javascript:;" onclick="showTabs(${projectId},5)">项目文档</a></li>
			<li data-index="6"><a href="javascript:;" onclick="showTabs(${projectId},6)">操作日志</a></li>
		</c:when>
		<c:otherwise>
			<li class="no"><a href="javascript:;">团队成员</a></li>
			<li class="no"><a href="javascript:;">股权结构</a></li>
			<li class="no"><a href="javascript:;">访谈记录</a></li>
			<li class="no"><a href="javascript:;">会议纪要</a></li>
			<li class="no"><a href="javascript:;">交割前事项</a></li>
			<li class="no"><a href="javascript:;">拨款信息</a></li>
			<li class="no"><a href="javascript:;">运营分析</a></li>
			<li class="no"><a href="javascript:;">项目文档</a></li>
			<li class="no"><a href="javascript:;">操作日志</a></li>
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
