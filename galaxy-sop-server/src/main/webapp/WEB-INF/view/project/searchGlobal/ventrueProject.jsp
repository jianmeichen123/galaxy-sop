<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 		<div class='one pagination_common ventrueContent'>
			<table id='searchTable' data-url="<%=path %>/galaxy/infoDanao/queryXhtProjectPage" data-auth="isShow" class='createProject table table-hover' style="width:100%;">
				<thead>
					<th data-field="projectName" data-formatter="projectInfo">项目</th>
					<th data-field="finance_status" data-formatter="financeStatusFormat">融资状态</th>
					<th data-field="project_progress" data-formatter="projectProgress">项目进度</th>
					<th data-field="createUname">投资经理</th>
					<th data-field="project_status" data-formatter="projectStatusFormat">项目状态</th>
					<th data-field="updated_time" data-formatter="updateFormat"  data-sortable="true">最后编辑时间</th>
				</thead> 
				 <tbody>
			
					
				
					</tbody>
				</table> 
		</div>
</div>

<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>  
<script type="text/javascript">

$(function(){
	//返回附带参数功能代码
	var initParams,
		pageParams=cookieOperator.getDataNoDelete({_paramKey : 'projectList',_path : "/"}),
		initPageSize = 10;
		if(typeof(pageParams) !== 'undefined' && pageParams.pageSize !=''){
			initPageSize = pageParams.pageSize;
		}
	var keyword = getHrefParamter("keyword");
	 $('.globleSearchInput').val(keyword)
	function queryParams(params){
		return {
			pageNum:params.offset/params.limit,
			pageSize:params.limit,
			keyword:keyword,
			pageSearchInfo:'xhtProject',
			direction:'desc',
			property:'updated_time'
		}
	}
	
	$('#searchTable').bootstrapTable({
		queryParamsType:'limit',
		pageSize:initPageSize,
		//pageNumber:1,
		showRefresh : false,
		method:'post',
		//contentType: "application/x-www-form-urlencoded",
		pagination:true,
		pageList:[10,20,30],
		sortOrder : 'desc',
		sortName : 'updated_time',
		sidePagination:'server',
		//queryParams:queryParams,
		//返回附带参数功能代码
         queryParams : function(param){
        	if(getCookieValue("backProjectList")!=''){
        		initParams = cookieOperator.pullCookie({_paramKey : 'projectList',_path : "/"});
        		//deleteCookie("backProjectList","/");
        	}else{
        		initParams=undefined;
        	}
        	if(typeof(initParams)=='undefined'){
    			param.pageNum=param.offset/param.limit;
    			param.pageSize=param.limit;
    			param.keyword=keyword;
    			param.pageSearchInfo='xhtProject';
    			param.direction='desc';
    			param.property='updated_time' 
        	}else{
    			//param.pageNum = initParams.pageNum-1;
        		param.pageNum = initParams.offset/initParams.pageSize;
        		param.pageSize = initParams.pageSize;
        		param.limit=initParams.pageSize;
 	        	param.keyword=keyword;
 	        	param.offset=initParams.offset;
 	        	param.pageSearchInfo='xhtProject';
    			param.direction='desc';
    			param.property='updated_time';
        	}
        	return param;
        }, 
		onLoadSuccess:function(data){
			
			var isShow='${fx:hasPremission('project_search_sop')}';
			if(isShow=="false"&&$(".no_info_icon_xhhl").text()==""){
				$(".no_info_icon_xhhl").text("暂无数据权限");
			}else{
				$(".no_info_icon_xhhl").text("没有找到匹配的记录");
				
			}
			var totalObject = data.userData;
			var venterProjectNumber =totalObject.xhtProjectTotal; //创投项目
			var outterProjectNumber =totalObject.dnProjectTotal; //外部项目
			var zixunProjectNumber =totalObject.xhtAppZixunTotal; //星河资讯
			var totalProjectNumber =totalObject.dnZixunTotal; //创投资讯
			var  zixunTotal = parseInt(zixunProjectNumber)+parseInt(totalProjectNumber)
			/*获取页面的值  */
			$(".ventrueTotal").html("<span>（"+venterProjectNumber+"）</span>")//创投资讯
			$('.outerTotal').html("<span>（"+outterProjectNumber+"）</span>")	//外部项目资讯
			$('.zixunTotal').html("<span>（"+zixunTotal+"）</span>")//资讯
			
			var allTotal = parseInt(venterProjectNumber)+parseInt(outterProjectNumber)+parseInt(zixunTotal)
			$('.totalNumber').html("<span>"+allTotal+"</span>")	;
			//带参返回
        	 if(typeof(initParams) !== 'undefined' && initParams.pageNum != ''){
    			if(initParams.pageNum==1){
    				return;
    			}else{
    				$('.pagination li').removeClass('active');
    				if($('.pagination .page-number').length< initParams.pageNum)
    				{
    					var len = $('.pagination .page-number').length;
    					var totalPages = $("#searchTable").bootstrapTable('getOptions').totalPages;
    					var end = initParams.pageNum + Math.floor(len/2);
    					if(end>totalPages)
						{
    						end = totalPages;
						}
    					
    					for(var i=len-1; i>=0; i--)
    					{
    						$('.pagination .page-number').eq(i).html('<a href="javascript:void(0)">'+ end-- +'</a>');
    					}
    				}
					
    				$('.pagination li').each(function(){
    	    			if($(this).text()==initParams.pageNum){
    	    				//$(this).addClass('active');
    	    				$(this).click();
    	    				 return false;
    	    			}
    				});
    			}
    		} 
			
        	
		}
	
	
	
	
})


















	
	
})

/**
 * 更新时间格式化
 * @version 2016-06-21
 */
function updateFormat(value,row,index){
	return row.updateDate;
}
/**
 * 项目进度格式化
 * @version 2016-06-21
 */
 function projectProgress(value,row,index){
	var projectPro = row.projectProgress;
	var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);
	var proStatus = row.projectStatus;
	var pronum = proStatus.substring(proStatus.lastIndexOf(":")+1,proStatus.length);

	return row.progress;
}
/**
 * 融资状态格式化
 * @version 2016-06-21
 */
function financeStatusFormat(value,row,index){
	return row.financeStatusDs;
}
/**
 * 项目状态格式化
 * @version 2016-06-21
 */
function projectStatusFormat(value,row,index){
	return row.projectStatusDs;
}
/* 

*项目名称格式化
*
*
*/
function projectInfo(value,row,index){
    var id=row.id;
	var str=row.projectName;
	if(str.length>10){
		subStr = str.substring(0,10);
		var options = '<a href="#" class="blue" data-btn="myproject" onclick="proInfo(' + id + ')" title="'+str+'">'+subStr+'</a>';
		return options;
	}
	else{
		var options = '<a href="#" class="blue" data-btn="myproject" onclick="proInfo(' + id + ')" title="'+str+'">'+str+'</a>';
		return options;
	}
}

function proInfo(id){
	//项目详情页返回地址
	setCookie("project_detail_back_path", Constants.sopEndpointURL + 'galaxy/mpl',6,'/');
	//返回附带参数功能代码
	var options = $("#searchTable").bootstrapTable('getOptions');
	var tempPageSize = options.pageSize ? options.pageSize : 10;
	var tempPageNum = options.pageNumber ? options.pageNumber : 1;
	var formdata = {
			_paramKey : 'projectList',
			_url : Constants.sopEndpointURL + "/galaxy/project/detail/" + id,
			_path : "/",
			_param : {
				pageNum : tempPageNum,
        		pageSize : tempPageSize,
        		offset:tempPageSize*(tempPageNum-1)        		
			}
	}
	var href_url=window.location;
	setCookie("href_url", href_url,24,'/');
	cookieOperator.forwardPushCookie(formdata);
}
	
	

</script>

