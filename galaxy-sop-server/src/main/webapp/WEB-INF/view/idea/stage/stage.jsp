<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>

<!-- 富文本编辑器 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script id="a" src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>

<script>
	$("#f").attr("href",Constants.sopEndpointURL + "/ueditor/themes/default/css/umeditor.css");
	$("#a").attr("src",Constants.sopEndpointURL + "/js/plupload.full.min.js");
	$("#a").attr("src",Constants.sopEndpointURL + "/js/plupload/zh_CN.js");
	$("#b").attr("src",Constants.sopEndpointURL + "/ueditor/dialogs/map/map.js");
	$("#c").attr("src",Constants.sopEndpointURL + "/ueditor/umeditor.config.js");
	$("#d").attr("src",Constants.sopEndpointURL + "/ueditor/umeditor.min.js");
	$("#e").attr("src",Constants.sopEndpointURL + "/ueditor/lang/zh-cn/zh-cn.js");
</script>

<div class="creativetc" >
  <a href="javascript:;" data-close="close" class="close null">关闭</a>
          <!-- 创意动态 -->
          <div class="pjt_introduct clearfix">
            <div class="clearfix">
              <h3 class="idea-title">食乐淘</h3>       
            </div>
            <ul class="pjt_brf clearfix">
              <li>最新变更动态<span id="idea-progress-log"></span></li>
            </ul>
          </div>

          <div class="tabtable">
          <!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav" id="ideaProgress_1" data-clickn="0">基本信息</li>
                <li data-tab="nav" id="ideaProgress_2" data-clickn="0">调研</li>
                <li data-tab="nav" id="ideaProgress_3" data-clickn="0">创建立项会</li>           
                <li data-tab="nav" id="ideaProgress_5" data-clickn="0">创建项目</li>           
                <li data-tab="nav" id="ideaOperateLog" data-clickn="0">变更动态</li>           
            </ul>
            <!-- tab内容 -->
            <div class="tabtable_con tabtable_con_close">
            <!-- 创意基本信息 -->
              <div class="block block_t show aa"  data-tab="con" id="ideaDetail">
                  <div class="btnbox_f1 btnbox_m clearfix">
                    <button class="pubbtn fffbtn" id="claimt" href="<%=path %>/galaxy/idea/goClaimtcPage" data-btn="claim">认领</button>
                    <button class="pubbtn fffbtn" id="editBtn" data-btn='edith' >编辑</button>
                    <input type="hidden" id="IdeaId">
                    <button class="pubbtn fffbtn" href="<%=path %>/galaxy/idea/showHistory?id=${id}" data-btn="history">历史信息</button>
                  </div>
                  <div class="top clearfix">
                    <dl>
                      <dt>创意编号：</dt>
                      <dd id="ideaCode" class="dw"></dd>
                    </dl>
                    <dl>
                      <dt>创意名称：</dt>
                      <dd id="ideaName" class="dw">食乐淘</dd>
                    </dl>
                    <dl>
                      <dt>创意来源：</dt>
                      <dd id="ideaSource" class="dw"></dd>
                    </dl>
                    <dl>
                      <dt>提出人：</dt>
                      <dd id="createdUname" class="dw">投资经理</dd>
                    </dl>
                    <dl>
                      <dt>所属事业线：</dt>
                      <dd id="departmentDesc" class="dw">O2O</dd>
                    </dl>
                  </div>
                  <div class="mid clearfix">
                    <dl>
                      <dt>提出时间：</dt>
                      <dd id="createdTime" data-formatter="dateFormatter" class="dw">2016-05-08</dd>
                    </dl>
                  </div>
                  <div class="bottom clearfix" id="append">
                    <dl>
                      <dt>创意简述：</dt>
                      <dd id="ideaDesc"></dd>
                    </dl>
                  </div>
              </div>

               <!-- 调研   idea-->
			<div class="block block_t" data-tab="con" id="ideaProgress_2_con">
				<!--按钮-->
				<div id="options_point2" class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" id="cy_up_report"  onclick="showUploadPopup('u','ideaProgress:2','')" class="pubbtn fffbtn lpubbtn option_item_mark">上传可行性报告</a> 
					<a href="javascript:;" id="cy_start_lxh" onclick="stratLxh('${id}')"  class="pubbtn fffbtn lpubbtn option_item_mark">启动创建立项会</a>
					<a href="javascript:;" id="cy_low"  value="${id}" data-btn="abandon" class="pubbtn fffbtn lpubbtn option_item_mark">放弃</a>
				</div>
				
				<div id="ideaProgress_2_params">
					<input type="hidden" name="projectId" data-id="ideaId" value="${id}">
					<input type="hidden" name="projectProgress" value="ideaProgress:2">
					<input type="hidden" name="fileValid" value="1">
				</div>
				<!--表格内容-->
				<table id="ideaProgress_2_table" data-id-field="id"
					data-url="<%=path%>/galaxy/idea/queryIdeaDyList"  
					data-toolbar="#ideaProgress_2_params">
					<thead>
						<tr>
							<th data-field="fileUName" data-align="center" >上传者</th>
							<th data-field="fType" data-align="center" >存档类型</th>
							<th data-field="projectProgress" data-align="center" data-formatter="progressFormatter">创意状态</th>
							<th data-field="careerLineName" data-align="center" >所属事业线</th>
							<th data-field="updatedDate" data-align="center"  data-formatter="datetimeFormatter2">更新时间</th>
							<th data-field="operateFile" data-align="center" data-formatter="ideaOperateFormat">操作</th>
							<th data-align="center" data-formatter="ideaFileDownFormat">附件查看</th>
						</tr>
					</thead>
				</table>
				
			</div>
			
			
			
		<!-- 创建立项会  ideaProgress:3 -->
              <div class="block block_t" data-tab="con"  id="ideaProgress_3_con">
                <!--按钮-->
                <div id="options_point3" class="btnbox_f btnbox_f1 btnbox_m clearfix">
                    <button data-btn="meeting" id="add_cy_meet"  class="pubbtn fffbtn lpubbtn" href="<%=path %>/galaxy/idea/addCyMeetRecordTc" >添加会议纪要</button>
                    <button class="pubbtn fffbtn lpubbtn" href="<%=path %>/galaxy/idea/showCreateProjectDialog" data-btn="create">创建成项目</button>
                   <button class="pubbtn fffbtn" data-btn="abandon" value="${id}">放弃</button>
                </div>

				<div id="ideaProgress_3_params">
					<input type="hidden" name="projectId" value="${id}">
					<input type="hidden" name="meetingType" value="meetingType:3">
					<input type="hidden" name="recordType" value="1">
					<input type="hidden" name="meetValid" value="0">
				</div>
				<!--表格内容-->
				<table id="ideaProgress_3_table"  data-id-field="id"
					data-url="<%=path%>/galaxy/idea/queryCyMeet"  data-page-list="[2,5,10]"
					data-toolbar="#ideaProgress_3_params">
					<colgroup >
						<col style="width:35%;" /> 
						<col style="width:65%;" /> 
					</colgroup>
					<thead>
						<tr>
							<th data-align="center" data-formatter="meetInfoFormat" data-width="50%">会议概况</th>
							<th data-field="meetingNotes" data-align="center" data-formatter="formatLog">会议纪要</th>
							<!-- <th data-field="meetingNotes" data-align="center" data-formatter="formatMeetNote">会议纪要</th>  --> 
						</tr>
					</thead>
				</table>
			</div>
				
		<!-- 创建项目 -->
              <div class="block block_t" data-tab="con">
              	<jsp:include page="/galaxy/idea/showProject">
              		<jsp:param value="${id }" name="ideaId"/>
              	</jsp:include>
              </div> 
              		 <!-- 变更动态 -->
			<div class="block block_t" data-tab="con" id="ideaProgress_log_con">
				<div id="ideaProgress_log_params">
					<input type="hidden" name="relatedId" value="${id}">
					<input type="hidden" name="recordType" value="1">
				</div>
				<table id="ideaProgress_log_table"
					data-url="<%=path%>/galaxy/operatlog/queryIdeaProgressLog"
					data-page-list="[8,15,30]" data-toolbar="#ideaProgress_log_params">
					<thead>
						<tr>
							<th data-field="createdTime" data-align="center" data-formatter="datetimeFormatter">操作时间</th>
							<th data-field="uname" data-align="center" >操作人</th>
							<th data-field="operationContent" data-align="center">变更动态</th>
							<!-- <th data-field="operationType" data-align="center">变更动态</th>
							<th data-field="operationContent" data-align="center">对象</th> -->
							<!--  <th data-field="projectName" data-align="center" >创意</th>-->
							<th data-field="sopstage" data-align="center" >进度</th>
						</tr>
					</thead>
				</table>
			</div>
              </div>              
           </div>
          </div>  
<script type="text/javascript">
/* $(function(){
	getProjectInfo();
});
          
} */

</script>