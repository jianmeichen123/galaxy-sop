<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 		<div class='three pagination_common'>
 			<div class='consut_span'>
 				<span class='hasBackround'>星河资讯</span>
 				<span>创投资讯</span>
 			</div>
				<table id="xhtConsult" class='outerProject newsProject' data-url="<%=path %>/galaxy/infoDanao/queryXhtAppZixunPage">
					<thead>
						<tr>
							<th data-field="create_time" data-formatter="xhtProjectContent"></th>
						</tr>
					</thead>
				<!-- <tr>
						<td>
							<div class='tdContent'>
								<img class='fl leftPic'/>
								<div class='rightContent'>
									<h3>花心区块链研究院</h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm picEmOne'>1小时前</span>
										<span class='picEm picEmTwo'>来自投资中国</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr> -->
				
					
				
				</table>
			
			<table id="ctConsult" class='outerProject newsProject'>
				<thead>
						<tr>
							<th data-formatter="ctProjectContent"></th>
						</tr>
				</thead>
			
			
			</table>
			
			
			</div>
</div>
<script type="text/javascript">

	$('.consut_span span').click(function(){
		$('.consut_span span').removeClass('hasBackround');
		$(this).addClass('hasBackround');
		
	});
	
	$(function(){
	
		function queryParams(params){
			return {
				pageNo:params.offset/params.limit+1,
				pageSize:params.limit,
				keyword:'',
				pageSearchInfo:'dnProject',
				order:'desc',
				orderBy:'create_time'
			}
		}
		
		$('#xhtConsult').bootstrapTable({
			pageSize:10,
			pageNumber:1,
			method:'post',
			pagination:true,
			pageList:[10,20,30],
			sortOrder : 'desc',
			sortName : 'updated_time',
			sidePagination:'server',
			queryParams:queryParams,
			onLoadSuccess:function(data){
				//console.log(data)
			}
	})
		
	});
	/* 星河咨询 */
	function xhtProjectContent(value,row,index){
		var html = "<div class='tdContent'>"+
					"<img class='fl leftPic' src='"+row.zixunImage+"'/>"+
					"<div class='rightContent'>"+
					"<h3>'"+row.title+"'</h3>"+
					/* "<p class='outerProjectTitle'>'"+row.overview+"'</p>"+ */
					"<p>"+
					"<span class='picEm picEmOne'>'"+row.ctimeStr+"'</span>"+
					"<span class='picEm picEmOne resource'><span>来自:</span>'"+row.auther+"'</span>"+
					"</p>"+
					"</div>"+
					"</div>"
		return html;
		
	}
	
	/* 创投咨询 */
	
	
	
	
	
</script>

