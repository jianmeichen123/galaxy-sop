<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<dl>
	<dt>
		<h3 class="ico t3">数据报表</h3>
	</dt>
	<dd class="zzbox">
		<div id="histogram" class="histogram" style="height: 160px"></div>
	</dd>
</dl>
<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/highcharts_ext.js" type="text/javascript"></script>
<script src="<%=path %>/js/indexProjectProgress.js" type="text/javascript"></script>
<script>
var formdata = {
		domid : "histogram"
}
chartIndexPProgressUtils.init(formdata);
</script>