<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<!-- 高管/投资经理 -->
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isCreatedByUser" value="${fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<c:set var="isThyy" value="${fx:hasRole(20)}" scope="request"/>
<!-- 保存进度条 -->
<link href="<%=path %>/css/showLoading.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
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
            	<a href="javascript:void(0)"  class="pbtn bluebtn h_bluebtn" >运营数据记录</a>
                <%-- <c:if test="${isEditable}">
            	<a href="javascript:void(0)"  class="pbtn bluebtn h_bluebtn" data-btn="health_status" data-name='健康状况'></a>
                </c:if> --%>
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
                  <dd><a href="javascript:;" id="searchBtn" class="bluebtn ico cx">搜索</a></dd>
                </dl>
              </div>
              </form>
            </div>                            
            <!--表格内容-->
           <table id="meetGrid" width="100%" cellspacing="0" cellpadding="0" class="commonsize delivery">
           </table>
	</div>  
</div>
     	

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
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/js/init.js"></script>
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
		var _url = Constants.sopEndpointURL + '/galaxy/health/tohealthlist?hiddenBtn=${isEditable}';
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