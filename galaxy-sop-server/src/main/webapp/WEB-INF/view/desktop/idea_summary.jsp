<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>

	<dl style="position: relative; class="Creative_library" resource-mark="idea_summary">

		<dt>
			<h3 class="ico t2">项目创意</h3>
		</dt>

		<dd>
			<table width="100%" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<!-- <th>创意编号</th> -->
						<th>创意名称</th>
						<!-- <th>所属行业</th> -->
						<th>创建时间</th>
						<!-- <th>最后编辑时间</th> -->
						<th>创建人</th>
						<th>状态</th>
					</tr>
				</thead>

				<tbody id="cy_index">
				</tbody>
			</table>
		</dd>

		<dd class="clearfix position">
			<a href="javascript:;" onclick="toCyPage()" class="more null">more</a>
		</dd>

	</dl>

<script type="text/javascript">

		
		selectCyIndex();
	
	
	
	//主页创意
	function selectCyIndex(){ 
		var jsonData={"pageNum":0,"pageSize":3,"isforindex":"isfor"}; 
		sendPostRequestByJsonObj(platformUrl.sopcyshouye,jsonData, cyIndexCallback);
	}
	function cyIndexCallback(data){
		//组装数据
		var tbodyList = $("#cy_index");
		
		var list =  data.pageList.content;
		if(list != null && list != "" && typeof(list) != 'undefined' && list.length != 0 ){
	      var ideaProgress = {
				"ideaProgress:1":"待认领",
				"ideaProgress:2":"调研",
				"ideaProgress:3":"创建立项会",
				"ideaProgress:4":"搁置",
				"ideaProgress:5":"创建项目"
			};
	      
	      
			$.each(list, function(i, temp){
				var ideaProgressDesc = "";
				if (temp.ideaProgress in ideaProgress) {
					if (temp.ideaProgress == "ideaProgress:1" || temp.ideaProgress == "ideaProgress:4" ) {
						ideaProgressDesc = "<a href=\'javascript:;\' class=\'blue\' onclick=\'toCyOper("+temp.id+")\' >" + ideaProgress[temp.ideaProgress] + '</a>';
					} else {
						ideaProgressDesc = ideaProgress[temp.ideaProgress];
					}
				}
				
				var tr='<tr>'+
					/* '<td>'+ temp.ideaCode+'</td>'+ */
					'<td class="cutstr" title="'+ temp.ideaName+'">'+ temp.ideaName+'</td>'+
					/* '<td>'+ temp.departmentDesc+'</td>'+  */
					'<td>'+ ((isNaN(temp.createdTime))?'&nbsp;&nbsp;-': Number(temp.createdTime).toDate().format("yyyy-MM-dd"))+'</td>'+
					/* '<td>'+ ((isNaN(temp.updatedTime))?'&nbsp;&nbsp;-': Number(temp.updatedTime).toDate().format("yyyy-MM-dd"))+'</td>'+ */
					'<td class="cutstrName" title="'+ temp.createdUname+'">'+ temp.createdUname+'</td>'+
					'<td>'+ ideaProgressDesc+'</td>'+
					'</tr>'; 
				tbodyList.append(tr);
			});
			cutStr(10,'cutstr');
			cutStr(4,'cutstrName');
			if(list.length<3){
				$("#cy_index").parent().parent().siblings().children('.more').css("display","none");	
			}
		}else{
			$("#cy_index").parent().parent().siblings().children('.more').css("display","none");
			var noData =
				'<tr>'+
				'<td colspan="7" class="no_info"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
		}	
	}
	
	function toCyPage(){
		window.location.href=$("#menus").find("[data-menueid='137']").attr("href");
	}
</script>


