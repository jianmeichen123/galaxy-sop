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
<title>项目详情</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<%-- <link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/> --%>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css">
<!-- 富文本编辑器 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/sopinfo.js"></script>
</head>



<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin">
	
    	<jsp:include page="sopcommon.jsp" flush="true"></jsp:include>
		
        <div class="new_left">
        	<div class="tabtable assessment label_static">
			<!-- tab标签 -->
            <jsp:include page="tab_header.jsp?index=9" flush="true"></jsp:include>
            <!-- 运营分析 -->
            <div id="post_meeting_anlysis">
            	<div class="member proOperation">
                    <div class="top clearfix">
                        <!--按钮-->
                        <div class="btnbox_f btnbox_f1 clearfix">
                        	<c:if test="${isEditable}">
                            <a href="javascript:void(0)" class="pbtn bluebtn h_bluebtn" id="addPostMeetingBtn"  data-btn="conference">添加运营会议纪要</a>
                        	</c:if>
                        	<a href="javascript:void(0)"  class="pbtn bluebtn h_bluebtn" data-btn="health_case" data-name='健康状况变更记录'></a>
                            <c:if test="${isEditable}">
                        	<a href="javascript:void(0)"  class="pbtn bluebtn h_bluebtn" data-btn="health_status" data-name='健康状况'></a>
                            </c:if>
                        </div>
                    </div>
                    <!-- 搜索条件 -->
                    <div class="min_document pro_analysis clearfix">
                    <form id="search_meet">
                      <div class="bottom searchall clearfix">
                        <dl class="fmdl fmdll clearfix">
                          <dt>类型：</dt>
                          <dd id="search_meet_type">
                          </dd>
                        </dl>
                        <dl class="fmdl fmdll clearfix">
                          <dt>会议日期：</dt>
                          <dd>
								<input type="text" class="datepicker txt time" name="meet_startDate"  /> 
								<span>至</span>
								<input type="text" class="datepicker txt time" name="meet_endDate"  />
						  </dd>
                          <dd><a href="javascript:;" id="searchBtn" class="bluebtn ico cx">查询</a></dd>
                        </dl>
                      </div>
                      </form>
                    </div>                            
                    <!--表格内容-->
                    <table id="meetGrid" width="100%" cellspacing="0" cellpadding="0" class="commonsize delivery">
                    </table>
        		</div>  
        	</div>
          </div>
        </div>
        <!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>	
    </div>
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>
<script type="text/javascript">
	var isTransfering = "${fx:isTransfering(pid) }";
	if(isTransfering == 'true')
	{
		$("[data-btn='conference']").addClass('limits_gray');
		$("[data-btn='health_status']").addClass('limits_gray');
	}
	function getProject(){
		return ${proinfo};
	}
</script>
<script src="<%=path %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/common.js" type="text/javascript"></script>
<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<!-- 分页二css+四js -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- file -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datePicker-handler-init.js"></script>
<script type="text/javascript" src="<%=path%>/js/tabPostMeetingAnlysis.js"></script>
<script src="<%=path %>/js/batchUpload.js" type="text/javascript" charset="utf-8"></script>

<script>

//============================健康状况
	var proid = projectInfo.id;
	var deliver_selectRow = null;
	
$(function(){
	show_health_case();
	show_health_status();
});

function hide_health_case(){
	$("[data-btn='health_case']").off();
	$("[data-btn='health_case']").remove();
}
function hide_health_status(){
	$("[data-btn='health_status']").off();
	$("[data-btn='health_status']").remove();
}
 /**
  * 健康记录  列表
  */
function show_health_case(){
	$("[data-btn='health_case']").text("健康状况变更记录");
	$("[data-btn='health_case']").on("click",function(){
		var $self = $(this);
		var _name= $self.attr("data-name");
		var _url = Constants.sopEndpointURL + '/galaxy/health/tohealthlist';
		$.getHtml({
			url:_url,
			data:"",
			okback:function(){
				$("#popup_name").html(_name);
				$("#health-custom-toolbar [name='projectId']").val(proid);
				
				init_bootstrapTable('project_health_table',5);
			}
		});
		return false;
	});
}
/**
 * 健康记录  添加
 */
function show_health_status(){
	$("[data-btn='health_status']").text("健康状况");
	$("[data-btn='health_status']").on("click",function(){
		if($(this).hasClass('limits_gray'))
		{
			return;
		}
		var $self = $(this);
		var _name= $self.attr("data-name");
		var _url = Constants.sopEndpointURL + '/galaxy/health/toaddhealth';
		$.getHtml({
			url:_url,
			data:"",
			okback:function(){
				$("#popup_name").html(_name);
				$("#health_form [name='projectId']").val(proid);
			}
		});
		return false;
	});
}
function save_health(){
	var content = JSON.parse($("#health_form").serializeObject());
	var _url =  Constants.sopEndpointURL + '/galaxy/health/addhealth'
	sendPostRequestByJsonObj(_url, content, function(data){
		if (data.result.status=="OK") {
			layer.msg("保存成功");
			removePop1();
			//启用滚动条
			 $(document.body).css({
			   "overflow-x":"auto",
			   "overflow-y":"auto"
			 });
			//刷新投后运营简报信息
			setThyyInfo();
			$("#project_delivery_table").bootstrapTable('refresh');
		} else {
			layer.msg(data.result.message);
		}
	});
	
}
$(".datepicker").datepicker({
    format: 'yyyy-mm-dd',
    language: "zh-CN",
    autoclose: true,
    todayHighlight: false,
    defaultDate : Date,
    today: "Today",
    todayBtn:'linked',
    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
    forceParse:false,
});
</script>


</html>