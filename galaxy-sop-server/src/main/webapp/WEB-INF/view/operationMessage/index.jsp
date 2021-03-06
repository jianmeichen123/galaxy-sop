<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
	String path = request.getContextPath(); 
    java.util.Date date=new java.util.Date();
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">

<title>星河投</title>


<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

</head>

<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
     <!--右中部内容-->
 	<div class="ritmin message">
    	<div class="new_tit_a"><a href="#" onclick="backIndex()">工作桌面</a><img alt="" src="<%=path %>/img/arrow-brumd.png" class="arrow"/>消息提醒</div>
    	<div class="new_tit_b">
	        <span class="new_color size18">消息提醒</span>
	      </div>
	      
      <!-- 消息提醒内容 -->
			<table id="message-table" data-url="operationMessageQueryList"  data-page-list="[10,20,30]" data-show-refresh="true">
				<thead>
				    <tr>
			        	<th data-field="createdTime"  data-class="message_t" data-formatter="longTimeFormat_Chines" >日期时间</th>
			        	<th data-field="content"   data-class="message_n" data-formatter="projectNameFormat">消息</th>
 					</tr>	
 				</thead>
			</table>

    </div>

</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<!-- bootstrap-table  -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=request.getContextPath() %>/js/init.js"></script>	
<script type="text/javascript">
	$(function(){
		createMenus(1);
		$(".pagebox .lft li ul").hide();
		var initParams,
				pageParams=cookieOperator.getDataNoDelete({_paramKey : 'messageList',_path : "/"}),
				toPageNum = 1,
				initPageSize = 10;
		if(typeof(pageParams) !== 'undefined' && pageParams.pageSize !=''){
			initPageSize = pageParams.pageSize;
			
			if(pageParams.pageNum && typeof(pageParams.pageNum) != "undefined"){
				toPageNum = pageParams.pageNum;
			}
			
			deleteCookie("messageList","/");
		}
		
		
		$('#message-table').bootstrapTable({
			queryParamsType: 'size|page',
			pageSize:initPageSize,
			pageNumber:toPageNum,
			showRefresh : false,
			url : platformUrl[$('#message-table').attr("data-url")],
			sidePagination: 'server',
			method : 'post',
			sortOrder : 'desc',
			sortName : 'send_time',
			pagination: true,
	        search: false,
	        customData: function(options, data){
        		options.totalRows = data.entity.total;
        		return data.entity.dataList;
	        },
	        //返回附带参数功能代码
	        queryParams : function(param){
	        	return param;
	        },
	        onLoadSuccess: function (data) {
	        	if(typeof(initParams) !== 'undefined' && initParams.pageNum != ''){
	    			if(initParams.pageNum==1){
	    				return;
	    			}else{
	    				$('.pagination li').removeClass('active');
	    				if($('.pagination .page-number').length< initParams.pageNum){
	    					for(var i=$('.pagination .page-number').length; i>0; i--){
	    						$('.pagination .page-number').eq(i).html('<a href="javascript:void(0)">'+i+'</a>');
	    					}
	    				}

	    				$('.pagination li').each(function(){
	    	    			if($(this).text()==initParams.pageNum){
	    	    				$(this).click();
	    	    				//$(this).addClass('active')
	    	    			}
	    				})
	    			}
	    		}
	        	initPageSize=10;
	        	if($(".new_tit_a a").text()=='工作桌面'){
	    			$("#menus li").eq(0).addClass("on").siblings().removeClass();
	    		}
	        }
		});
		
		var url = platformUrl.markeMessageAsReaded;
		var data = {time:new Date().getTime(),uid:"<%=userId%>"};
		var callback = function(data){
			reloadMessage();
		};
		sendPostRequestByJsonObj(url,data,callback);
	});
	function projectNameFormat(value, row, index){
		var content = value;
		//外部项目
		if('1.1.4' == row.type)
		{
			content = content.replace('"<pname>',"<a href='"+Constants.ctdnURL+"project_qy.html?projCode="+row.remarkId+"' target='_blank' class='blue project_name'>").replace('name"',"");
		}
		else if('1.2.5' == row.type || '1.2.6' == row.type)
		{
			//尽职调查、股权交割
			var target = $('#menus a[data-menueid=1071]').attr('href');
			content = content.replace('"<pname>',"<a href='"+target+"' class='blue project_name'>\"").replace('name"',"");
		}
		else
		{
			var projectId = row.projectId;
			if(typeof(row.projectId) == 'undefined')
			{
				projectId = row.remarkId;
			}
			content = content.replace('"<pname>',"<a href='#' onclick=\"viewDetail(\'pro\',\'" + projectId + "\')\" class='blue project_name'>\"").replace('name"',"");
		}
		content = content.replace('</pname>"',"\"</a>");
		return content;
	}
	function viewDetail(mark,id){
		if(exit(id)==false){
			return  ;
		}
		var url = '';
		if(mark == 'pro'){
			url = Constants.sopEndpointURL + "/galaxy/project/detail/" +id;
		}else if(mark =='zx'){
			url = Constants.sopEndpointURL + "/galaxy/idea?zixunid="+id+"&_is_menu_=true";
		}
				
		//项目详情页返回地址
		setCookie("project_detail_back_path", Constants.sopEndpointURL + 'galaxy/operationMessage/index',6,'/');
		var options = $("#message-table").bootstrapTable('getOptions');
		var tempPageSize = options.pageSize ? options.pageSize : 10;
		var tempPageNum = options.pageNumber ? options.pageNumber : 1;
		var formdata = {
				_paramKey : 'messageList',
				_url : url,
				_path : "/",
				_param : {
					pageNum : tempPageNum,
	        		pageSize : tempPageSize
				}
		}
		
		var href_url=window.location;
		setCookie("href_url", href_url,24,'/');
		cookieOperator.forwardPushCookie(formdata);
	}
	function backIndex(){
		 var url=Constants.sopEndpointURL+"/galaxy/redirect";
		 forwardWithHeader(url);
	}
	function exit(id){
		var data2 = {
				'id' : id
		}
		var  flag=false;
	  var url="<%=path %>/galaxy/project/checkProjectExit";
		sendPostRequestByJsonObj(url,data2,function(data){
				if(data.result.status=="ERROR"){
					if(data.result.errorCode == "project-delete"){
						layer.msg("项目已经被删除。");
						flag= false;
					}
				}else{
					flag= true;
				}
		})
		return flag
	}
</script>
</html>
