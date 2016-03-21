<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css"  type="text/css"> -->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>

<!-- 富文本编辑器 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

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
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/project/progress/meetAddView" data-btn="meeting" class="pubbtn lpubbtn bluebtn ico c4">添加会议纪要</a>
            </div>
        </div>

	    <!-- 搜索条件 -->
        <div class="min_document clearfix" id="custom-toolbar" >
          <div class="bottom searchall clearfix">
          
            <!-- <dl class="fmdl fmmr fmdll clearfix"> -->
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>会议类型：</dt>
              <dd class="clearfix">
					<label><input type="radio" name="meetingType" value="" />不限 </label>
			 		<label><input type="radio" name="meetingType" value="meetingType:1" />内评会 </label>
					<label><input type="radio" name="meetingType" value="meetingType:2" />CEO评审</label>
					<label><input type="radio" name="meetingType" value="meetingType:3" />立项会</label>
					<label><input type="radio" name="meetingType" value="meetingType:4" />投决会</label>
              </dd>
            </dl>
            
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>会议日期：</dt>
              <dd>
                <input  class="txt time"  type="date" id="startTime" name="startTime" />
                <span>至</span>
                <input  class="txt time" type="date"  id="endTime" name="endTime" />
                
                <!-- <input type="text" class="txt time" id="startTime" name="startTime" /> -->
              </dd>
            </dl>
            
            <dl class="fmdl fmdll clearfix">
              <dt></dt>
              <dd>
                <input type="text" class="txt s_txt" placeholder="请输入项目名称或编号"  id="proNameCode" name="proNameCode"/>
              </dd>
              <dd>
               <a href="javascript:;" class="bluebtn ico cx" action="querySearch">查询</a>
              </dd>
            </dl>
            
          </div>
        </div>
        
		<div class="tab-pane active" id="view">
			<table id="data-table" data-url="<%=path %>/galaxy/project/progress/queryMeet"
				data-method="post" data-side-pagination="server"
				data-pagination="true" data-toolbar="#custom-toolbar"
				data-page-list="[1,2,4,8,50]" data-id-field="lottoId"
				data-show-refresh="false">
				<colgroup >
					<col style="width:20%;"> <!-- 名称 -->
					<col style="width:20%;"> <!-- 名称 -->
					<col style="width:60%;">  <!-- 状态 -->
				</colgroup>
				<thead>
					<tr>
						 <th data-align="center" data-formatter="meetInfoFormat">会议概况</th>
						<!-- <th data-field="hygk" data-align="center">会议概况</th> -->
						<th data-align="center"  data-formatter="meetProInfoFormat" >项目信息</th>  
						<th data-field="meetingNotes" data-align="center" >会议记要</th>  <!-- data-formatter="subLengthFormat" -->
					</tr>
				</thead>
			</table>

		</div>


	</div>
</div>



<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>

</body>

<!-- bootstrap-table  -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<script src="${ctx}/js/plupload.full.min.js" type="text/javascript"></script>

<script src="<%=path %>/js/meeting.js" type="text/javascript"></script>

</html>

