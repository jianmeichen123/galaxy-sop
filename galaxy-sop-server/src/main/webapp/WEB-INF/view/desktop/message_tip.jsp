<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<dl>
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

	$('#message-data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:4,
		showRefresh : false ,
		url : platformUrl[$('#message-data-table').attr("data-url")],
		sidePagination: 'server',
		method : 'post',
		sortOrder : 'desc',
		sortName : 'updated_time',
		pagination: false,
		queryParams:function(param){
			param.pageNum=0;
			param.pageSize=3;
			return param;
		},
        search: false,
        onLoadSuccess: function (data){
        	if(data.pageList.total<3){
        		$(".r_news .more").css("display","none");
        	}
        }
	});


</script>