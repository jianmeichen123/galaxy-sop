<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<dl resource-mark="div_tip_message" class="r_news" style="display:none;position:relative;">
<dt><h3 class="ico t4">消息提醒</h3></dt>
   <dd>
   <table id="message-data-table" data-url="operationMessageQueryList" data-page-size="3" data-page-list="[3,20,30]" data-show-refresh="true">
	<thead>
	    <tr>
        	<th data-field="createdTime" data-align="left" data-width="35%" data-formatter="longTimeFormat_Chines" >日期时间</th>
        	<th data-field="content" data-align="left"  data-width="65%" data-formatter="projectNameLineFormat">消息</th>
		</tr>	
	</thead>
    </table>
   </dd>
   <dd class="clearfix position">
       <a href="<%=path %>/galaxy/operationMessage/index" class="more null">more</a>
    </dd>
</dl>

<script>
$(function(){
	$('#message-data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:4,
		showRefresh : false ,
		url : platformUrl[$('#message-data-table').attr("data-url")],
		sidePagination: 'server',
		method : 'post',
		sortOrder : 'desc',
		sortName : 'updated_time',
		pagination: true,
        search: false,
        onLoadSuccess: function (data){
        	if(data.pageList.total<4){
        		$(".r_news .more").css("display","none");
        	}
        }
	});
});

</script>