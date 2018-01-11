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
 			<div class='projectContent'>
 			<!--星河资讯  -->
 				<div class='xhtContent'>
					<table id="xhtConsult" class='outerProject newsProject' data-url="<%=path %>/galaxy/infoDanao/queryXhtAppZixunPage">
						<thead>
							<tr>
								<th data-field="create_time" data-formatter="xhtProjectContent"></th>
							</tr>
						</thead>
					</table>
				</div>
				<!-- 创投咨询 -->
				<div class='dnContent'>
					<table id="dnConsult" class='outerProject newsProject'  data-url="<%=path %>/galaxy/infoDanao/queryDnZixunPage">
						<thead>
								<tr>
									<th data-field="ctime" data-formatter="ctProjectContent"></th>
								</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
<script type="text/javascript">
$('.consut_span span').click(function(){
	$('.consut_span span').removeClass('hasBackround');
	$(this).addClass('hasBackround');
	var index = $(this).index();
	if(index == 0){
		console.log('0000')
		
		$('.dnContent').hide()
		$('.xhtContent').show()
		xhtMessage()
		
	}else if(index == 1){
		console.log('111')
		
		$('.dnContent').show()
		$('.xhtContent').hide()
		ctDnConsult()
	}
	
});

	/* 星河资讯========================================== */
	 xhtMessage()
 	function  xhtMessage(){
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
	}
	/* 星河咨询datamatter */
  	function xhtProjectContent(value,row,index){
		 if(row.zixunImage =='undefined'){
			 alert('11')
		}else{
			var imgTag = "<img class='fl leftPic' src='"+row.zixunImage+"'/>"
				$('.tdContent').append("<p>Hello world!</p>");
				$('span').appendTo('.tdContent')
				console.log(imgTag)
		}
		var html = "<div class='tdContent'>"+
					/* "<img class='fl leftPic' src='"+row.zixunImage+"'/>"+ */
					"<div class='rightContent'>"+
					"<h3>'"+row.title+"'</h3>"+
					 "<p class='outerProjectTitle'>'"+row.overview+"'</p>"+
					"<p>"+
					"<span class='picEm picEmOne'>'"+row.ctimeStr+"'</span>"+
					"<span class='picEm picEmOne resource'><span>来自:</span>'"+row.auther+"'</span>"+
					"</p>"+
					"</div>"+
					"</div>"
		return html;
		
	}   
	
	
	
	/* 创投咨询 ===================================*/
	
	 function ctDnConsult(){ 
		
		function queryParams(params){
			return {
				pageNo:params.offset/params.limit+1,
				pageSize:params.limit,
				keyword:'',
				pageSearchInfo:'dnZixun',
				order:'desc',
				orderBy:'ctime'
			}
		}
		
		$('#dnConsult').bootstrapTable({
			pageSize:10,
			pageNumber:1,
			method:'post',
			pagination:true,
			pageList:[10,20,30],
			sortOrder : 'desc',
			sortName : 'ctime',
			sidePagination:'server',
			queryParams:queryParams,
			onLoadSuccess:function(data){
				//console.log(data)
			}
	})
		
		
	 } 
	/* 创投咨询datamatter */
	function ctProjectContent(value,row,index){
		var html = "<div class='tdContent'>"+
					"<img class='fl leftPic' src='"+row.zixunImage+"'/>"+
					"<div class='rightContent'>"+
					"<h3>'"+row.title+"'</h3>"+
					"<p class='outerProjectTitle'>'"+row.overview+"'</p>"+
					"<p>"+
					"<span class='picEm picEmOne'>'"+row.ctimeStr+"'</span>"+
					"<span class='picEm picEmOne resource'><span>来自:</span>'"+row.auther+"'</span>"+
					"</p>"+
					"</div>"+
					"</div>"
		return html;
		
	}
	
	
	

	
</script>

