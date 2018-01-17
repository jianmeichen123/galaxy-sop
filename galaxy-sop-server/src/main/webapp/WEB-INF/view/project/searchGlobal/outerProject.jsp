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
								<input type="hidden" name="ventrue">
								<input type="hidden" name="outerproject">
								<input type="hidden" name="consult">
								<input type="hidden" name="totalNumber">
								
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
	var keyword = getHrefParamter("keyword");
	function queryParams(params){
		return {
			pageNo:params.offset/params.limit,
			pageSize:params.limit,
			keyword:keyword,
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
			var totalObject = data.userData;
			var venterProjectNumber =totalObject.xhtProjectTotal; //创投项目
			var outterProjectNumber =totalObject.dnProjectTotal; //外部项目
			var zixunProjectNumber =totalObject.xhtAppZixunTotal; //星河资讯
			var totalProjectNumber =totalObject.dnZixunTotal; //创投资讯
			console.log(totalObject)
			var  zixunTotal = parseInt(zixunProjectNumber)+parseInt(totalProjectNumber)
			/*获取页面的值  */
			$(".ventrueTotal").html("<span>（"+venterProjectNumber+"）</span>")//创投资讯
			$('.outerTotal').html("<span>（"+outterProjectNumber+"）</span>")	//外部项目资讯
			$('.zixunTotal').html("<span>（"+zixunTotal+"）</span>")//资讯
			
			var allTotal = parseInt(venterProjectNumber)+parseInt(outterProjectNumber)+parseInt(zixunTotal)
			$('.totalNumber').html("<span>"+allTotal+"</span>")	;
			
			
			
			
			
			
			
			
		}
	
	
	
	
})
	 
	
	
});
	function projectContent(value,row,index){
	
		/* 完整结构 */
		/*  var html = "<div class='tdContent'><a href="+row.href+" target='_blank'><img class='fl leftPic'/ src="+row.projImage+"></a>"+
					"<div class='rightContent'>"+
					"<h3>"+row.projTitle+"<span>"+row.latestFinanceRound+"</span></h3>"+
					"<p class='outerProjectTitle'>"+row.introduce+"</p>"+
					"<p>"+
						"<span class='picEm picEmOne picOrigin'><em></em>"+row.districtSubName+"</span>"+
						"<span class='picEm picEmTwo'><em></em>"+row.industryName+"</span>"+
					"</p>"+
					"<input class='outerProjectTotal' type='hidden' name='ventrue' value="+row.pageSize+">"+
					"</div>"+
					"</div>"  */
			var html = "";		
			 	html += "<div class='tdContent'><a href="+row.href+" target='_blank'><img class='fl leftPic'/ src="+row.projImage+"></a>"+
						"<div class='rightContent'>"+
						"<h3>"+row.projTitle+"<span>"+row.latestFinanceRound+"</span></h3>"
						
				if(row.introduce){
					html += "<p class='outerProjectTitle outerProjectNew'>"+row.introduce+"</p>"+
							"<p>"
				}		
						
				 if(row.districtSubName){
					html += "<span class='picEm picEmOne picOrigin'><em></em>"+row.districtSubName+"</span>"
			 	}
				if(row.industryName){
					html +="<span class='picEm picEmTwo'><em></em>"+row.industryName+"</span>"+
						 "</p>"
				}
				html +="<input class='outerProjectTotal' type='hidden' name='ventrue' value="+row.pageSize+">"+
					 "</div>"+
					 "</div>" 
				
	
					
					
		
		return html;
	}

	
	
	
</script>

