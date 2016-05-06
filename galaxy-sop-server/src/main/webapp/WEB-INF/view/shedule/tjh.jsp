<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<!-- 日期插件 -->
<link href="<%=path %>/bootstrap-datetimepicker/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="<%=path %>/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>投决会排期</h2>
    	<input type="hidden" id="project_id" value=""/>
        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="custom-toolbar">
		     <input type="hidden"  id="tipslink_val" name="proType" value="2" />
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
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<table id="data-table" data-url="project/queryScheduling/2" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-align="center" class="data-input" data-formatter="indexFormatter">#</th>
				    	<th data-field="projectCode" data-align="center" class="data-input">项目编码</th>
				    	<th data-field="projectName" data-align="center" class="data-input">项目名称</th>
				    	<th data-field="scheduleStatus" data-align="center" class="data-input" data-formatter="statusFormatter">排期状态</th>
				    	<th data-field="meetingDate" data-align="center" class="data-input" data-formatter="meetingDateFormatter">上次过会时间</th>
				    	<th data-field="projectCareerline" data-align="center" class="data-input">投资事业线</th>
				    	<th data-field="createUname" data-align="center" class="data-input">投资经理</th>
				    	<th data-field="meetingRate" data-align="center" class="data-input" data-formatter="rateFormatter">过会率</th>
				    	<th data-field="applyTimeStr" data-align="center" class="data-input">申请时间</th>
				    	<th data-align="center" class="data-input" data-formatter="dataFormatter">排期时间</th>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>
<!-- 日期插件 -->
<script type="text/javascript" src="<%=path %>/bootstrap-datetimepicker/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
	createMenus(19);
	function indexFormatter(value, row, index){
		return index + 1;
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
	function rateFormatter(value,row,index){
		if(value == 0){
			return "还未过会";
		}else{
			return row.meetingRate;
		}
	}
	function meetingDateFormatter(value, row, index){
		if(row.meetingDate){
			return row.meetingDateStr;
		}else{
			return "还未过会";
		}
	}
	function dataFormatter(value, row, index){
		if(row.isEdit == '1'){
			if(row.reserveTime){
				return timeHtml = '<input size="16" value="'+row.reserveTimeStr+'" type="text" readonly class="form_datetime">';
			}else{
				return timeHtml = '<input size="16" type="text" readonly class="form_datetime">';
			}
		}else{
			return timeHtml = row.reserveTimeStr;
		}
	}
	tiggerTable1($("#data-table"),10,function(){
		$('.form_datetime').datetimepicker({
			/**
			 * 指定日期的格式
			 * 注意：该插件自己定义了一套，整个过程均使用这一套
			 * yyyy-mm-dd
			 * yyyy-mm-dd hh:ii:ss
			 * yyyy-mm-ddThh:ii:ssZ
			 * MM -- 五月/六月/...
			 */
			format: 'yyyy-mm-dd hh:ii:ss',
			language: "zh-CN",
			startView: 2,
			minView: 0,
			maxView: 4,
			todayBtn:'linked',
			autoclose: true
		}).on('changeDate', function(ev){
			//当日期被改变时被触发
			console.log(ev.date.valueOf());
		});
	});
	
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
</script>
</html>
