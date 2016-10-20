<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">


	<!-- 项目文档 -->
	<dl resource-mark="div_pro_doc" id="dan_k" style="display:none;position:relative;">
                	<dt><h3 class="ico t5">项目文档</h3></dt>
                    <dd>
                    	<table width="100%" cellspacing="0"  cellpadding="0" id="file_gird_index">
                            
                        </table>
                    </dd>
                    <dd class="clearfix position">
                    	<a  href="javascript:void(0)" onclick="Sopfile()"  class="more null" id="file_gird_more">more</a>
                    </dd>
    </dl>

<script src="<%=path%>/js/fileindex.js" type="text/javascript"></script>
<script type="text/javascript">

var fileInitData = {
		_domid : "file_gird_index"
}
fileGridIndex.init(fileInitData);

</script>

</body>
</html>

