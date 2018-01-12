<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 		<div class='two pagination_common outerContent'>
				<table id="outerProject" data-url='<%=path %>/galaxy/infoDanao/queryDnProjectPage' class='outerProject'>
					<thead>
						<tr>
							<th data-formatter='projectContent'></th>
						</tr>
					</thead>
					<!-- <tr>
						<td>
							<div class='tdContent'>
								<img class='fl leftPic'/>
								<div class='rightContent'>
									<h3>花心区块链研究院<span>A轮</span></h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm picEmOne'><em></em>江苏</span>
										<span class='picEm picEmTwo'><em></em>企业服务</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr> -->
					
				
				
				</table>
			
			
			
			</div>
			
</div>
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>  
<script type="text/javascript">
$(function(){
	
	function queryParams(params){
		return {
			pageNo:params.offset/params.limit,
			pageSize:params.limit,
			keyword:'',
			pageSearchInfo:'dnProject',
			order:'desc',
			orderBy:'setupDT'
		}
	}
	
	$('#outerProject').bootstrapTable({
		queryParamsType:'limit',
		pageSize:10,
		pageNumber:1,
		method:'post',
		pagination:true,
		//dataType: "json",
		pageList:[10,20,30],
		//sortOrder : 'desc',
		sortName : 'updated_time',
		sidePagination:'server',
		queryParams:queryParams,
		onLoadSuccess:function(data){
			console.log(data)
		}
	
	
	
	
})
	 
	
	
});
	function projectContent(value,row,index){
		var html = "<div class='tdContent'><img class='fl leftPic'/ src='"+row.projImage+"' >"+
					"<div class='rightContent'>"+
					"<h3>'"+row.projTitle+"'<span>'"+row.latestFinanceRound+"'</span></h3>"+
					"<p class='outerProjectTitle'>'"+row.introduce+"'</p>"+
					"<p>"+
						"<span class='picEm picEmOne'><em></em>'"+row.districtSubName+"'</span>"+
						"<span class='picEm picEmTwo'><em></em>'"+row.industryName+"'</span>"+
					"</p>"+
					"</div>"+
					"</div>"
		
		return html;
	}
	
	
	
</script>

