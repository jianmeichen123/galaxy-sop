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
		$('.dnContent').hide()
		$('.xhtContent').show()
		xhtMessage()
		
	}else if(index == 1){
		$('.dnContent').show()
		$('.xhtContent').hide()
		ctDnConsult()
	}
	
});

	/* 星河资讯========================================== */
	 xhtMessage()// 默认调用星河资讯
 	function  xhtMessage(){
		 var keyword = getHrefParamter("keyword");
		function queryParams(params){
			return {
				pageNo:params.offset/params.limit,
				pageSize:params.limit,
				keyword:keyword,
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
				console.log('星河咨询')
				console.log(data)
				var totalObject = data.userData;
				var venterProjectNumber =totalObject.xhtProjectTotal; //创投项目
				var outterProjectNumber =totalObject.dnProjectTotal; //外部项目
				var zixunProjectNumber =totalObject.xhtAppZixunTotal; //星河资讯
				var totalProjectNumber =totalObject.dnZixunTotal; //创投资讯
				console.log(totalObject)
				var  zixunTotal = parseInt(zixunProjectNumber)+parseInt(totalProjectNumber)
				/*获取页面的值  */
				$(".ventrueTotal").html("<span>("+venterProjectNumber+")</span>")//创投资讯
				$('.outerTotal').html("<span>("+outterProjectNumber+")</span>")	//外部项目资讯
				$('.zixunTotal').html("<span>("+zixunTotal+")</span>")//资讯
				
				var allTotal = parseInt(venterProjectNumber)+parseInt(outterProjectNumber)+parseInt(zixunTotal)
				$('.totalNumber').html("<span>"+allTotal+"</span>")	
			}
	})
	}
	/* 星河咨询datamatter */
  	function xhtProjectContent(value,row,index){
		
		var html = "<div class='tdContent'>"+
					"<img class='fl leftPic' src='"+row.zixunImage+"'/>"+ 
					"<div class='rightContent'>"+
					"<a href='"+row.href+"' target='_blank'><h3>"+row.title+"</h3></a>"+
					 "<p class='outerProjectTitle'>"+row.overview+"</p>"+
					"<p>"+
					"<span class='picEm picEmOne'>"+row.ctimeStr+"</span>"+
					"<span class='picEm picEmOne resource'><span>来自:</span>"+row.auther+"</span>"+
					"</p>"+
					"</div>"+
					"</div>"
					
		return html;
		
	}   
	
	
	
	/* 创投咨询 ===================================*/
	
	 function ctDnConsult(){ 
		 var keyword = getHrefParamter("keyword");
		function queryParams(params){
			return {
				pageNo:params.offset/params.limit,
				pageSize:params.limit,
				keyword:keyword,
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
				var totalObject = data.userData;
				var venterProjectNumber =totalObject.xhtProjectTotal; //创投项目
				var outterProjectNumber =totalObject.dnProjectTotal; //外部项目
				var zixunProjectNumber =totalObject.xhtAppZixunTotal; //星河资讯
				var totalProjectNumber =totalObject.dnZixunTotal; //创投资讯
				console.log(totalObject)
				var  zixunTotal = parseInt(zixunProjectNumber)+parseInt(totalProjectNumber)
				/*获取页面的值  */
				$(".ventrueTotal").html("<span>("+venterProjectNumber+")</span>")//创投资讯
				$('.outerTotal').html("<span>("+outterProjectNumber+")</span>")	//外部项目资讯
				$('.zixunTotal').html("<span>("+zixunTotal+")</span>")//资讯
				
				var allTotal = parseInt(venterProjectNumber)+parseInt(outterProjectNumber)+parseInt(zixunTotal)
				$('.totalNumber').html("<span>"+allTotal+"</span>")	
			}
	})
		
		
	 } 
	/* 创投咨询datamatter */
	function ctProjectContent(value,row,index){
		var html = "<div class='tdContent'>"+
					"<img class='fl leftPic' src='"+row.zixunImage+"'/>"+
					"<div class='rightContent'>"+
					"<a href='"+row.href+"' target='_blank'><h3>"+row.title+"</h3></a>"+
					"<p class='outerProjectTitle'>"+row.overview+"</p>"+
					"<p>"+
					"<span class='picEm picEmOne'>"+row.ctimeStr+"</span>"+
					"<span class='picEm picEmOne resource'><span>来自:</span>"+row.auther+"</span>"+
					"</p>"+
					"</div>"+
					"</div>"
					
		return html;
		
	}
	
	
	

	
</script>

