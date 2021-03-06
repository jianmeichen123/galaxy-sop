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
<link href="<%=path %>/css/jquery-ui.min.css" type="text/css" rel="stylesheet">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<!-- 富文本编辑器 -->
<%-- <link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet"> --%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<script src="<%=path%>/js/jquery-ui.min.js" type="text/javascript"></script>
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
    	<h2>会议纪要</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <%-- <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/project/progress/meetAddView" data-btn="meeting" class="pubbtn lpubbtn bluebtn ico c4">添加会议纪要</a>
            </div> --%>
        </div>

	    <!-- 搜索条件 -->
        <div class="min_document clearfix" id="custom-toolbar" >
          <div class="bottom searchall clearfix">
          
            <!-- <dl class="fmdl fmmr fmdll clearfix"> -->
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>会议类型：</dt>
              <dd class="clearfix">
					<label><input type="radio" name="meetingType" value="" checked="checked" />不限 </label>
			 		<label><input type="radio" name="meetingType" value="meetingType:1" />内评会 </label>
					<label><input type="radio" name="meetingType" value="meetingType:2" />CEO评审</label>
					<label><input type="radio" name="meetingType" value="meetingType:3" />立项会</label>
					<label><input type="radio" name="meetingType" value="meetingType:4" />投决会</label>
              </dd>
            </dl>
            
           <!--  <input type="hidden" name="uid" id="uid" value="" />
            <script>
            	$("#uid").val(userId);
            </script> -->
            
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>会议日期：</dt>
              <dd>
                <input type="text" class="datepicker txt time" readonly id="startTime" name="startTime"  style="height:23px;"/>
                <span>至</span>
                <input type="text" class="datepicker txt time" readonly id="endTime" name="endTime"  style="height:23px;"/>
                
                <!-- <input type="text" class="txt time" id="startTime" name="startTime" /> -->
              </dd>
            </dl>
            
            <dl class="fmdl fmdll clearfix">
              <dt></dt>
              <dd>
                <input type="text" class="txt s_txt" placeholder="请输入项目名称或项目编码"  id="keyword" name="keyword" maxlength="100"/>  <!-- proNameCode -->
              </dd>
              <dd>
              <c:if test="${fx:hasPremission('meetingRecord_add')}">
               <a href="javascript:;" class="bluebtn ico cx xz" id="add-meeting-btn">新增</a>
              </c:if>
               <a href="javascript:;" class="bluebtn ico cx" action="querySearch">搜索</a>
              </dd>
            </dl>
            
          </div>
        </div>
		<div class="tab-pane active" id="view">
			<table id="data-table" data-url="<%=path %>/galaxy/meeting/queryMeet"
				data-method="post" data-side-pagination="server"
				data-pagination="true" data-toolbar="#custom-toolbar"
				data-page-list="[5,10,20]" data-id-field="id" data-unique-id="id"
				data-show-refresh="false">
				<colgroup >
					<col style="width:30%;"> <!-- 名称 -->
					<col style="width:20%;"> <!-- 名称 -->
					<col style="width:50%;">  <!-- 状态 -->
				</colgroup>
				<thead>
					<tr>
						 <th  data-formatter="metcolumnFormat" data-class="th_no1">会议概况</th>
						<!-- <th data-field="hygk" >会议概况</th> -->
						<th   data-formatter="meetProInfoFormat" >项目信息</th>  
						<th data-field="meetingNotes"  data-formatter="meetFormatLog">会议纪要</th>
					</tr>
				</thead>
			</table>

		</div>


	</div>
</div>



<!-- bootstrap-table  -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- upload -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script>
var transferingIds = "${fx:getTransferingPids()}".split(",");
</script>
<script src="<%=path %>/js/meeting.js" type="text/javascript"></script>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>

<script type="text/javascript">

$(function(){
	createMenus(7);
	
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
		clickToSelect: true,
        search: false,
        onLoadSuccess: function(){
        	var len=$(".meeting_result").length
        	for(var i=0;i<len;i++){
        		console.log($(".meeting_result").eq(i).text());
        		if($(".meeting_result").eq(i).text()=="通过"){
        			$(".meeting_result").eq(i).addClass("color_pass");
            	}else if($(".meeting_result").eq(i).text()=="待定"){
            		$(".meeting_result").eq(i).addClass("color_undetermined");  
            	}else if($(".meeting_result").eq(i).text()=="否决"){
            		$(".meeting_result").eq(i).addClass("red");
            		
            	}
        	}
        
        }
	});
	
});

var editScope = "${fx:dataScope('meetingRecord_update')}".split(",");
var delScope = "${fx:dataScope('meetingRecord_delete')}".split(",");
var meetSelectRow = null;
function meetFormatLog(value,row,index){
	var rtn = '';
	if(value){
		var len = getLength($.trim(value).replace(/<[^>]+>/g, ""));
	    subValue=value.replace(/<[^>]+>/g, "");
	}
	if(value != ''){
		var strlog=delHtmlTag(value);
		var strrrr=strlog;
		if(len>120){
			var subValue1 = value.replace(/<[^>]+>/g, "").substring(0,120);
			/*var rc = "<div id=\"log\" style=\"text-align:left;margin-left:20%;\" class=\"text-overflow\" title='"+strrrr+"'>"+subValue+'...'+'</div>';*/
			var rc = "<div id=\"log\" style=\"text-align:left;\" class=\"text-overflow1\" >"+
						subValue1+
						"..."+"<a href=\"javascript:;\" class=\"blue  option_item_mark\"  onclick=\"showMeetDetail("+row.id+")\" >更多<a>"+    
					'</div>';
			rtn = rc;
		}else{
			rtn = strlog;
		}
	}
	var projectCreateUid = row.projectCreateUid+"";
	if($.inArray( projectCreateUid, editScope ) != -1)
	{
		rtn += '&nbsp;<a href="javascript:;" class="blue  option_item_mark"  onclick="editMeeting('+row.id+')" >编辑<a>';
	}
	if($.inArray( projectCreateUid, delScope ) != -1)
	{
		rtn += '&nbsp;<a href="javascript:;" class="blue  option_item_mark"  onclick="delMeeting('+row.id+')" >删除<a>';
	}
	return rtn=='' ? '-' : rtn;
}

function editMeeting(id)
{
	var _url = "<%=path %>/galaxy/meeting/edit?_="+new Date().getTime()+"&id="+id;
	$.getHtml({
		url:_url
	});
}
function delMeeting(id)
{
	var _url = "<%=path %>/galaxy/meeting/del?_="+new Date().getTime()+"&id="+id;
	layer.confirm("确定删除？",function(i){
		layer.close(i);
		sendPostRequestByJsonObj(
			_url,
			{},
			function(data){
				if(data.result.status=='OK')
				{
					layer.msg("删除成功");
					if ($('#data-table').find("tbody tr").length<=1) {
					       $('#data-table').bootstrapTable('prevPage').bootstrapTable('refresh');
					    }else{
					    	$('#data-table').bootstrapTable('refresh');
					    }
					
				}
				else
				{
					layer.msg("删除失败。");
				}
			}
		);
	});
}

function showMeetDetail(selectRowId){
	meetSelectRow = $('#data-table').bootstrapTable('getRowByUniqueId', selectRowId);
	
	var _url = "<%=path %>/galaxy/project/progress/meetAddView";
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			//queryMeetPerPro();
			$(".title_bj").text("会议纪要");
			$('.edui-container').show();
			initMeetTcVal();
		}//模版反回成功执行	
	});
	return false;
}

function initMeetTcVal(){
	console.log(meetSelectRow);
	$("#projectName").val(meetSelectRow.proName).attr("disabled","desabled");
	$("#meetingDateStr").val(meetSelectRow.meetingDateStr).attr("disabled","desabled");
	
	$("input[name='meetingTypeTc'][value='"+meetSelectRow.meetingType+"']").attr("checked",'checked');
	$("input[name='meetingTypeTc']").attr("disabled","desabled");
	$("input[name='meetingResult'][value='"+meetSelectRow.meetingResult+"']").attr("checked",'checked').attr("readonly","readonly");
	$("input[name='meetingResult']").attr("disabled","desabled");
	
	/* meetEditor.setContent(meetSelectRow.meetingNotes);  */
	$("#meetingNotesCon").html(meetSelectRow.meetingNotes) 
	var fileinfo = "";
	if(meetSelectRow.fname!=null && meetSelectRow.fname!=undefined && meetSelectRow.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:;\" onclick=\"filedown("+meetSelectRow.fileId+","+meetSelectRow.fkey+");\" class=\"blue\" >"+meetSelectRow.fname+"</a>"
	}
	$("#fileNotBeUse").html("");
	$("#fileNotBeUse").html("会议录音："+fileinfo);
	
	$("#btnNotBeUse").html("");
	$("#btnNotBeUse").html("<a href=\"javascript:;\" class=\"pubbtn fffbtn\" data-close=\"close\">关闭</a>");
	
}
$("#add-meeting-btn").click(function(){
	showPreAddDialog();
});
function showPreAddDialog()
{
	var _url = "<%=path %>/galaxy/meeting/preAdd";
	$.getHtml({
		url:_url
	});
}
</script>

</body>


</html>

