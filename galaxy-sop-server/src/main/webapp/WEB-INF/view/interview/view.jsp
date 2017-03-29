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
<title>星河投</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<!-- 富文本编辑器 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<!-- common -->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

<style>
.tab-pane table th:nth-child(3) {
    width: 55%;
}
.tab-pane table td:nth-child(3){text-align:left !important;}
.table td{line-height:22px;}
</style>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>访谈记录</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">   <!-- onclick="umInit()" -->
                <a href="<%=path %>/galaxy/project/progress/interViewAdd" data-btn="interview" class="pubbtn lpubbtn bluebtn ico c4"  >添加访谈记录</a>
            </div>
        </div>
       
       
        <!-- 搜索条件 -->
        <div class="min_document clearfix"  id="custom-toolbar">
          <div class="bottom searchall clearfix">
          
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>访谈时间：</dt>
              <dd>
              	<!-- <input class="form-control" type="date" id = "startTime" name="startTime"  /> -->
                <input type="text" class="datepicker txt time" readonly id="startTime" name="startTime" style="height:23px;"/>
                <span>至</span>
                <input type="text" class="datepicker txt time" readonly id="endTime"  name="endTime" style="height:23px;"/>
              </dd>
            </dl>
            
            <!--  <input type="hidden" name="direction" value="asc"  /> desc   asc
            
            <input type="hidden" name="uid" id="uid" value="" />
            <script>
            	$("#uid").val(userId);
            </script>  -->
            <dl class="fmdl fmdll clearfix">
              <dt></dt>
              <dd>
                <input type="text" class="txt s_txt" placeholder="请输入项目名称或项目编码" id = "keyword" name="keyword" />   <!-- proNameCode -->
              </dd>
              
              <dd>
               <a href="javascript:;" class="bluebtn ico cx" action="querySearch">搜索</a>
              </dd>

            </dl>
          </div>
        </div>
        
        
          <div class="tab-pane active" id="view">		
			<table style="table-layout:fixed"  id="data-table" data-url="<%=path %>/galaxy/project/progress/queryInterview" data-method="post" 
	          		data-side-pagination="server" data-pagination="true" 
	          		data-toolbar="#custom-toolbar" data-page-list="[5,10,20]"
					data-id-field="id" data-unique-id="id" data-show-refresh="true">
				<colgroup >
					<col style="width:30%;"> <!-- 名称 -->
					<col style="width:20%;"> <!-- 名称 -->
					<col style="width:50%;">  <!-- 状态 -->
				</colgroup>
				<thead>
					<tr>
						<th  data-formatter="intervierInfoFormat" data-class="th_no1">访谈概况</th>
						<th  data-field="proName" >所属项目</th>  
						<th  data-field="viewNotes"  data-formatter="viewNotesFormat">访谈纪要</th>
						<!-- <th  data-field="createdId" ></th> -->
					</tr>
				</thead>
			</table>

           </div>
    </div>
</div>


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
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>

<script>
var transferingIds = "${fx:getTransferingPids()}".split(",");
</script>
<!-- clude -->
<script src="<%=path %>/js/interview.js" type="text/javascript"></script>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>

<script type="text/javascript">
$(function(){
	createMenus(6);
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		pageList : [5, 10, 20 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
		clickToSelect: true,
        search: false,
       /*  onLoadSuccess: function (data) {
        	$(".option_item_mark").click(function(){
        		interviewSelectRow = $('#data-table').bootstrapTable('getSelections');
        		showviewdetail(interviewSelectRow);
        		//console.log($(this).data());
        	});
        } */
	});
	
	
});

var interviewSelectRow = null;
function interviewFormatLog(value,row,index){
	var len = getLength($.trim(value));
	if(value != ''){
		var strlog=delHtmlTag(value);
		var strrrr=strlog;
		if(len>100){
			var subValue = $.trim(value).substring(100).replace("<p>","").replace("</p>","").replace("white-space: normal;","");
			/*var rc = "<div id=\"log\" style=\"text-align:left;margin-left:20%;\" class=\"text-overflow\" title='"+strrrr+"'>"+subValue+'...'+'</div>';*/
			var rc = "<div id=\"log\" style=\"text-align:left;margin-left:20%;\" class=\"text-overflow\" >"+
						subValue+
						"<a href=\"javascript:;\" class=\"fffbtn  option_item_mark\"  onclick=\"showviewdetail("+row.id+")\" >详情<a>"+    
					'</div>';
			return rc;
		}else{
			return strlog;
		}
	}
}
function showLogdetail(selectRowId){
	var interviewSelectRow = $('#data-table').bootstrapTable('getRowByUniqueId', selectRowId);
	var _url = Constants.sopEndpointURL+"/galaxy/project/progress/interViewLog";
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
		var um=UM.getEditor('viewNotes');
		um.setContent(interviewSelectRow.viewNotes);
		var uid='<%=userId%>';
		if(uid!=interviewSelectRow.createdId){
			$("#interviewsave").hide();
			um.setDisabled();
		}
		//$("#interviewsave").hide();
		$("#vid").val(selectRowId);
	}//模版反回成功执行	
});
	return false;
}
function interviewsave(){  
		var um = UM.getEditor('viewNotes');
	var log = um.getContent();
	var pid=$("#vid").val();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateInterview, {"id" : pid, "viewNotes" : log}, function(data){
			if (data.result.status=="OK") {
				$("#hint_all").css("display","none");
				layer.msg("保存成功");
				$(".meetingtc").find("[data-close='close']").click();
				$("#data-table").bootstrapTable('refresh');
			} else {
				layer.msg(data.result.message);
				$("#hint_all").css("display","block");
			}
			
		});
	}
}
</script>

</body>

</html>

