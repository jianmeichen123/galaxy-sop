<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<dl>
	<dt>
		<h3 class="ico t3">数据报表</h3>
	</dt>
	<dd class="zzbox">
		<div id="histogram" class="histogram" style="height: 160px"></div>
	</dd>
</dl>
<script src="<%=path %>/js/indexProjectProgress.js" type="text/javascript"></script>
