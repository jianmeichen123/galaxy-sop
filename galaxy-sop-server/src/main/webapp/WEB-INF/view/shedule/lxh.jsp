<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<!-- 日期插件 -->
<link rel="stylesheet" type="text/css" media="all" href="<%=path %>/plugins/daterangepicker/daterangepicker.css" />

<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
.demo { position: relative; }
.demo i {
	position: absolute; bottom: 10px; right: 24px; top: auto; cursor: pointer;
}
.daterangepicker .calendar{
	max-width: none;
}
</style>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>
    	<c:if test="${pageType eq 0}">立项会排期</c:if>
    	<c:if test="${pageType eq 1}">投决会排期</c:if>
    	<c:if test="${pageType eq 2}">CEO评审会排期</c:if>
    	</h2>
    	<input type="hidden" id="project_id" value="1"/>
        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="custom-toolbar">
		     <input type="hidden"  id="tipslink_val" name="proType" value="0" />
			<div class="bottom searchall clearfix search_adjust">
				<dl class="fmdl fml fmdll clearfix">
	              <dt>排期状态：</dt>
	              <dd>
	              	<input type="radio" name="scheduleStatus" value="0" checked>待排期
	              	<input type="radio" name="scheduleStatus" value="1">已排期
	              	<input type="radio" name="scheduleStatus" value="2">已通过
	              	<input type="radio" name="scheduleStatus" value="3">已否决
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	              <dt>所属事业线：</dt>
	              <dd>
	                <select name="careline">
	                  <option value="">全部</option>
	                </select>
	              </dd>
	            </dl>
				<dl class="fmdl fmdll clearfix">
					<dt></dt>
					<dd>
						<input type="text" class="txt" name="keyword" placeholder="项目名称或编号" />
					</dd>
					<dd>
						<button type="submit" class="bluebtn ico cx" action="querySearch">搜索</button>
					</dd>
					
				</dl>
				<dl>
				    <dd>
				       <button id="pqcSubmit" type="button" class="bluebtn ico pq" onclick="confirmSubmit();return false;">排期</button>
					</dd>
				</dl>
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<table id="meeting-shedule-list" data-url="project/queryScheduling/${pageType}" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				        <th  class="data-input" data-formatter="indexFormatter">#</th>
				    	<th data-field="projectCode"  class="data-input">项目编码</th>
				    	<th data-field="projectName"  class="data-input" data-formatter="projectNameFormatter">项目名称</th>
				    	<th data-field="scheduleStatus"  class="data-input" data-formatter="statusFormatter">排期状态</th>
				    	<th data-field="meetingDate"  class="data-input" data-formatter="meetingDateFormat" data-sortable="true" data-sortorder="desc">上次过会时间<span class="caret1"></span></th>
				    	<th data-field="projectCareerline"  class="data-input">投资事业线</th>
				    	<th data-field="createUname"  class="data-input">投资经理</th>
				    	<th data-field="meetingRate"  class="data-input" data-formatter="rateFormatter" data-sortable="true" data-sortOrder="desc">过会率(%)<span class="caret1"></span></th>
				    	<th data-field="applyTimeStr"  class="data-input" data-sortable="true" data-sorter="apply_time" data-sortOrder="asc">申请时间<span class="caret1"></span></th>
				    	<th data-field="reserveTimeStart"  class="data-input" data-formatter="dataFormatter" data-sortable="true" data-sortorder="desc">排期时间<span class="caret1"></span></th>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<link rel="stylesheet" href="<%=path %>/plugins/daterangepicker/css/font-awesome.min.css"  type="text/css">
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>
<!-- 日期插件 -->
<script type="text/javascript" src="<%=path %>/plugins/bootstrap-3.3.2/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/plugins/daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=path %>/plugins/daterangepicker/daterangepicker-customer.js"></script>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<script type="text/javascript">
    var flag =true;
    var roleIdList = '${roleIdList}';
    
    var menu='${pageType}';
    var meetingType="";
    if(menu == '0'){
    	if(roleIdList.indexOf("19") > 0){
        	$("#pqcSubmit").attr("style","display:none");
        	flag =false;
        }
    	createMenus(18);
    	meetingType="meetingType:3";
    }
    if(menu == '1'){
    	if(roleIdList.indexOf("19") > 0){
        	$("#pqcSubmit").attr("style","display:none");
        	flag =false;
        }
    	meetingType="meetingType:4";
    	createMenus(19);
    }
    if(menu == '2'){
    	if(roleIdList.indexOf("18") > 0){
        	$("#pqcSubmit").attr("style","display:none");
        	flag =false;
        }
    	createMenus(20);
    	meetingType="meetingType:2";
    	
    }
	function indexFormatter(value, row, index){
		var str = index+1;
		return str;
	}
	function statusFormatter(value, row, index){
		var status = "待排期";
		if(value == 1){
			status = "已排期";
		}else if(value == 2){
			status = "已通过";
		}else if(value == 3){
			status = "已否决";
		}
		return status;
	}
	function meetingDateFormatter(value, row, index){
		if(row.meetingDate){
			return row.meetingDateStr;
		}else{
			return "还未过会";
		}
	}
	
	function meetingDateFormat(value){
		return value==null||value==''||value=='-'||value==undefined ? ' ' : new Date(value).format("yyyy-MM-dd"); 
	}
	
	function rateFormatter(value,row,index){
		if(value == 0){
			return "还未过会";
		}else{
			return row.meetingRate;
		}
	}
	function dataFormatter(value, row, index){
		if(flag){
			if(row.isEdit == '1'){
				if(row.reserveTimeStartStr){
					return timeHtml = '<input id="test'+index+'" data-pid="'+row.id+'" size="40" name="reserveTime" value="'+row.reserveTimeStartStr+' - '+row.reserveTimeEndStr+'" type="text" readonly class="form_datetime "/>'+"<a href=\"javascript:cleard('test"+index+"');\" class=\"red\"><i class=\"fa fa-close\"></i></a>";
				}else{
					return timeHtml = '<input id="test'+index+'" data-pid="'+row.id+'" size="40" name="reserveTime" type="text" value=""  class="form_datetime ">'+"<a href=\"javascript:cleard('test"+index+"');\" class=\"red\"><i class=\"fa fa-close\"></i></a>";
				}
			}else{
				if(row.isTransfor == '0'){
					return timeHtml  = '项目移交中不允许排期';
				}
				if(typeof(row.reserveTimeStartStr) == "undefined"){
					return timeHtml  = '未进行排期';
				}else{
					return timeHtml = row.reserveTimeStartStr+' - '+row.reserveTimeEndStr;
				}
				
			}
		}else{
			if(typeof(row.reserveTimeStartStr) == "undefined"){
				return timeHtml  = '未进行排期';
			}else{
				return timeHtml = row.reserveTimeStartStr+' - '+row.reserveTimeEndStr;
			}
		}
		
	}
	function projectNameFormatter(value, row, index){
		var str=row.projectName;
		if(str.length>7){
			str = str.substring(0,7);
		}
		return '<a href="#" class="blue" title="'+row.projectName+'" onclick="viewDetail(' + row.projectId + ')">'+str+'</a>';
	}
	function viewDetail(id){
		//项目详情页返回地址
		var currentUrl = location.href;
		setCookie("project_detail_back_path", currentUrl,6,'/');
		var options = $("#meeting-shedule-list").bootstrapTable('getOptions');
		var tempPageSize = options.pageSize ? options.pageSize : 10;
		var tempPageNum = options.pageNumber ? options.pageNumber : 1;
		var scheduleStatus = $("input[name='scheduleStatus']:checked").val();
		var careline = $("select[name='careline']").val();
		var keyword = $("input[name='keyword']").val();
		var formdata = {
				_paramKey : 'meetingSheduleList',
				_url : Constants.sopEndpointURL + "/galaxy/project/detail/" + id,
				_path : "/",
				_param : {
					pageNum : tempPageNum,
	        		pageSize : tempPageSize,
	        		scheduleStatus : scheduleStatus,
	        		careline : careline,
	        		keyword : keyword
				}
		}
		
		var href_url=window.location;
		setCookie("href_url", href_url,24,'/');
		cookieOperator.forwardPushCookie(formdata);
	}
	
	sendGetRequest(platformUrl.getDepartMentDict+"/department",null,function(data){
		var $optionArray = [];
		var ii = 0;
		$.each(data.entityList,function(i,current){
			if(current.type == 1){
				$optionArray[ii] = '<option value="'+current.id+'">'+current.name+'</option>';
				ii++;
			}
		});
		var $optionHtml = $($optionArray.join(''));
		$optionHtml.insertAfter($('option[value=""]'));
	});

	var initParams,toPageNum = 1,
			pageParams=cookieOperator.getDataNoDelete({_paramKey : 'meetingSheduleList',_path : "/"}),
			initPageSize = 10;
	if(typeof(pageParams) !== 'undefined' && pageParams.pageSize !=''){
		initPageSize = pageParams.pageSize;
		
		if(pageParams.pageNum && typeof(pageParams.pageNum) != "undefined"){
			toPageNum = pageParams.pageNum;
		}
		
		deleteCookie("meetingSheduleList","/");
	}
	$("button[action='querySearch']").click(function(){
		initParams = cookieOperator.pullCookie({_paramKey : 'meetingSheduleList',_path : "/"});
	});
	$("#meeting-shedule-list").bootstrapTable({
		queryParamsType: 'size|page',
		pageSize:initPageSize,
		pageNumber:toPageNum,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
        //返回附带参数功能代码
	    queryParams : function(param){
	    	if(getCookieValue("backProjectList")!=''){
        		initParams = cookieOperator.pullCookie({_paramKey : 'meetingSheduleList',_path : "/"});
        		deleteCookie("backProjectList","/");
        	}else{
	        	initParams=undefined;
	        }
	    	if(typeof(initParams) !== 'undefined'){
	    		//param.pageNum = initParams.pageNum - 1;
	        	//param.pageSize = initParams.pageSize;
	        	param.keyword = initParams.keyword;
	        	if(initParams.scheduleStatus != ''){
	        		param.scheduleStatus = initParams.scheduleStatus;
	        		$("input[name='scheduleStatus'][value='"+initParams.scheduleStatus+"']").attr("checked","true");
	        	}
	        	if(initParams.careline != ''){
	        		param.careline = initParams.careline;
	        		$("select[name='careline']").val(initParams.careline);
	        	}
	        	$("input[name='keyword']").val(initParams.keyword);
	        	var options = $("#meeting-shedule-list").bootstrapTable('getOptions');
	 	        options.pageNumber = initParams.pageNum - 1;
	    	}
	        return param;
	    },
        onLoadSuccess: function(){


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
        	
			var options = {
				"singleDatePicker": false,
				"showDropdowns": true,
				"showWeekNumbers": false,
				"showISOWeekNumbers": false,
				"timePicker": true,
				"timePicker24Hour": true,
				"timePickerIncrement": 5,
				"timePickerSeconds": false,
				"locale": {
					"format": "YYYY-MM-DD HH:mm",
					"separator": " - ",
					"applyLabel": "确定",
					"cancelLabel": "取消",
					"fromLabel": "From",
					"toLabel": "To",
					"customRangeLabel": "自定义",
					"daysOfWeek": [
						"周日",
						"周一",
						"周二",
						"周三",
						"周四",
						"周五",
						"周六"
					],
					"monthNames": [
						"一月",
						"二月",
						"三月",
						"四月",
						"五月",
						"六月",
						"七月",
						"八月",
						"九月",
						"十月",
						"十一月",
						"十二月"
					],
					"firstDay": 1
				},
				"autoUpdateInput": false,
				"startDate": true,
				"endDate": true,
				"minDate": 0,
				"maxDate": 0,
				"opens": "left",
				"drops": "down",
				"buttonClasses": "btn btn-sm",
				"applyClass": "btn-success",
				"cancelClass": "btn-default"
	    	};
        	$('.form_datetime').daterangepicker(options, function(start, end, label) { 
				console.log('New date range selected: ' 
					+ start.format('YYYY-MM-DD HH:mm') + ' - ' 
					+ end.format('HH:mm') 
					+ ' (predefined range: ' + label + ')'); 
			});
      		$('.form_datetime').on('apply.daterangepicker',function(ev, picker) {
          		$(this).val(picker.startDate.format('YYYY-MM-DD HH:mm') + ' - ' + picker.endDate.format('YYYY-MM-DD HH:mm') );
     		});
		}
	});
	
	
	function cleard(id){
	  $("#"+id).val("");
	}
	
	
	function confirmSubmit(){
		$("#view").showLoading(
				{
					'addClass': 'loading-indicator'						
				});
		var columnValue = $("input[name='reserveTime']");
		var obj = [];
		$.each(columnValue, function(i,current){
			var pid = $(this).attr('data-pid');
			var datevalue=$(this).val();
			var PassRate = {};
	             PassRate.id = parseInt(pid);
	        if(datevalue != ''){
	        		var str=datevalue.split(" - ");
	        		PassRate.reserveTimeStartStr =str[0]+":00";
		        	PassRate.reserveTimeEndStr =str[1]+":00";
		        	//PassRate.reserveTimeStartStr =new Date().format("yyyy-MM-dd hh:mm:ss");
		        	//PassRate.reserveTimeEndStr =new Date().format("yyyy-MM-dd hh:mm:ss");
		        	
	        }else{
	            PassRate.reserveTimeStartStr = null;
		        PassRate.reserveTimeEndStr = null;
	        }
	        PassRate.scheduleStatus =1;
	        PassRate.meetingType = meetingType;
	        obj.push(PassRate);
		});
	    sendPostRequestByJsonObj(platformUrl.reserveTime, obj,updateCallBack);
	    
	}
	//保存成功回调
	function updateCallBack(data){
		var result = data.result.status;
		if(result == "ERROR"){ //OK, ERROR
			layer.msg(data.result.message);
		    $("#loading-indicator-view").remove();
		    $("#loading-indicator-view-overlay").remove();
			return;
		}else{
			layer.msg(data.result.message);
		    $("#loading-indicator-view").remove();
		    $("#loading-indicator-view-overlay").remove();
		    //window.location.reload();
		    $('#meeting-shedule-list').bootstrapTable('refresh');  
		}
	    
	}
</script>
</html>
