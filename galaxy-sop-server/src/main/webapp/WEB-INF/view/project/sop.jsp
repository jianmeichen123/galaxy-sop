<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<style>
.bars{display:none;}
#projectProgress_1_table th:nth-child(1),#projectProgress_2_table th:nth-child(1),#projectProgress_3_table th:nth-child(1),#projectProgress_4_table th:nth-child(1),#projectProgress_7_table th:nth-child(1) {
    width: 50%;
}
#projectProgress_1_table td,#projectProgress_2_table td,#projectProgress_3_table td,#projectProgress_4_table td,#projectProgress_7_table td{line-height:22px;text-align:left !important;}
.content{
			float: left;
			padding:0 10px;
		}
#projectProgress_1_table td.noinfo{text-align:center !important;}
		
</style>
<div class="myprojecttc">
	<a href="javascript:;" data-close="close" class="close null">关闭</a>
	<!-- 项目介绍 -->
	<div class="pjt_introduct clearfix">
		<div class="clearfix">
			<h3 id="project_name"></h3>
		<!--  	<dl class="update">
				<dt>最新内容更新</dt>
				<dd  id="pj-title-updated-time">
					<span>&#40;</span>2016-01-26<span>&#41;</span>
				</dd>
			</dl>-->
		</div>

		<!-- <ul class="pjt_brf clearfix">
              <li><b class="b4"></b>2016-01-22</li>
              <li>内部评审会</li>
              <li>
                <span class="grey"><b class="b4"></b>会议结论：</span>跟踪
              </li>
            </ul> -->
	</div>

	<div class="tabtable">
		<!-- tab标签 -->
		<ul class="tablink">
			<li data-tab="nav">基本信息</li>
			<li data-tab="nav" id="fileRepository">档案库</li>
			<li data-tab="nav" id="projectProgress_1">接触访谈</li>
			<li data-tab="nav" id="projectProgress_2">内部评审</li>
			<li data-tab="nav" id="projectProgress_3">CEO评审</li>
			<li data-tab="nav" id="projectProgress_4">立项会</li>
			<li data-tab="nav" id="projectProgress_5">投资意向书</li>
			<li data-tab="nav" id="projectProgress_6">尽职调查</li>
			<li data-tab="nav" id="projectProgress_7">投决会</li>
			<li data-tab="nav" id="projectProgress_8">投资协议</li>
			<li data-tab="nav" id="projectProgress_9">股权交割</li>
			<li data-tab="nav" id="projectProgress_10">投后运营</li>
			<li data-tab="nav" id="projectProgress">操作日志</li>
		</ul>
		<!-- tab内容 -->
			<!-- tab内容 -->
		<div class="tabtable_con tabtable_con_close">
			<!-- 项目基本信息 -->
			<div class="block block2 show" data-tab="con">
				<dl>
              		<dt>商业计划书</dt>
               		<dd id="business_plan_dd" class="fctbox">
<!--                 <a href="javascript:;" class="ico f1" data-btn="upload" onclick="uploadBusinessPlan()" >更新</a> -->
<!--                 <a href="javascript:;" class="ico f1" data-btn="download" onclick="downloadBusinessPlan()" >下载</a> -->
              		</dd>
            	</dl>
				<dl>
					<dt>项目描述</dt>
					<dd class="describe" id="prodescribe_show"></dd>
					<!-- <dd class="fctbox">
						<a href="javascript:;" class="ico1 f2" data-btn="describe">查看详情</a>
						<a href="javascript:;"  data-btn="hide" style="display:none" class="ico f3">收起</a>
					</dd> -->
				</dl>
				<dl>
					<dt>产品服务</dt>
					<dd class="describe" id="business_model_show"></dd>
					<!-- <dd class="fctbox">
							<a href="javascript:;" class="ico1 f2" data-btn="describe">查看详情</a>
							<a href="javascript:;"  data-btn="hide" style="display:none"  class="ico f3">收起</a>
					</dd> -->
				</dl>
					<dl>
					<dt>公司定位</dt>
					<dd class="describe" id="location_show"></dd>
					<!-- <dd class="fctbox">
							<a href="javascript:;" class="ico1 f2" data-btn="describe">查看详情</a>
							<a href="javascript:;"  data-btn="hide" style="display:none"  class="ico f3">收起</a>
					</dd> -->
				</dl>
					<dl>
					<dt>用户画像</dt>
					<dd class="describe" id="portrait_show"></dd>
					<!-- <dd class="fctbox">
							<a href="javascript:;" class="ico1 f2" data-btn="describe">查看详情</a>
							<a href="javascript:;"  data-btn="hide" style="display:none"  class="ico f3">收起</a>
					</dd> -->
				</dl>
					<dl>
					<dt>竞情分析</dt>
					<dd class="describe" id="analysis_show"></dd>
					<!-- <dd class="fctbox">
							<a href="javascript:;" class="ico1 f2" data-btn="describe">查看详情</a>
							<a href="javascript:;"  data-btn="hide" style="display:none"  class="ico f3">收起</a>
					</dd> -->
				</dl>
				<!-- 
				<dl>
					<dt>项目描述</dt>
					<dd class="describe" id="projectDesc_show"></dd>
					<dd class="fctbox">
							<a href="javascript:;" class="ico1 f2" data-btn="describe">查看详情</a>
							<a href="javascript:;"  data-btn="hide" style="display:none"  class="ico f3">收起</a>
					</dd>
				</dl> -->
				<dl>
					<dt>团队成员</dt>
					<dd class="full_w describe clearfix">
						<div class="clearfix">
						</div>
						<div class="tab-pane active" id="view">
							<table id="tab_tablePerson" data-method="post"
								data-show-refresh="true" data-side-pagination="server"
								data-pagination="true" data-page-list="[10,20,30]"
								data-search="false">
							</table>
						</div>

					</dd>
					<!-- <dd class="fctbox">
						<a href="javascript:;" class="ico1 f2" data-btn="describe">查看详情</a>
						<a href="javascript:;" data-btn="hide" style="display: none"
							class="ico f3">收起</a>
					</dd> -->
				</dl>
				
				<dl>
					<dt>股权结构</dt>
					<dd class="full_w describe clearfix">
						<div class="clearfix"></div>
						<div class="tab-pane active" id="pView">
							<table id="table" data-method="post"
								data-show-refresh="true">
							</table>
						</div>
					</dd>
					<!-- <dd class="fctbox">
						<a href="javascript:;" class="ico1 f2" data-btn="describe">查看详情</a>
						<a href="javascript:;" data-btn="hide" style="display: none"
							class="ico f3">收起</a>
					</dd> -->
				</dl>
				<!-- 
				<dl class="no_border">
					<dt>档案库</dt>
					<dd class="describe">11111</dd>
					<dd class="fctbox">
						<a href="javascript:;" class="ico1 f2">查看详情</a>
						<a href="javascript:;"  data-btn="hide" style="display:none"  class="ico f3">收起</a>
					</dd>
				</dl> -->
			</div>

			<!-- 档案库信息 -->
			<div class="block" data-tab="con" id="file_repository">
				<!-- 查询选项 -->
				<form id = 'file_repository_search_form'>
				<div class="searchbox">
					<div class="show_more">
						<a href="#" class="blue open ico1 f4" data-btn='show'>展开</a> <a
							href="#" class="blue searchbox_hidden hide ico1 f3"
							data-btn='hide'>收起</a>
					</div>
					<div class="default">
						<dl class="fmdl fmmr fmmm">
							<dt>文件来源：</dt>
							<dd>
								<label for=""><input type="radio" name="search_fileSource" value="all" checked>不限</label>
							</dd>
							<dd>
								<label for=""><input type="radio" name="search_fileSource" value="1">内部</label>
							</dd>
							<dd>
								<label for=""><input type="radio" name="search_fileSource" value="2">外部</label>
							</dd>
						</dl>
						<dl class="fmdl fmmr fmmm" >
							<dt>业务分类：</dt>
							<dd>
								<select name="search_fileWorktype" id="search_file_worktype">
									<option value = "all">全部</option>
								</select>
							</dd>
						</dl>
					</div>
					<div class="searchbox_hidden" data-btn='box' >
						<dl class="fmdl fmmr fmmm" id="search_file_status">
							<dt>档案状态：</dt>
						</dl>
						<dl class="fmdl fmmr fmmm" id="search_file_type">
							<dt>存储类型：</dt>
						</dl>
						<dl class="fmdl fmmr fmmm clearfix">
							<dt>更新时间：</dt>
							<dd>
								<input type="text" class="datepicker txt time" name="file_startDate" value="2016-01-01" /> <span>至</span>
								<input type="text" class="datepicker txt time" name="file_endDate" value="2016-01-01" />
							</dd>
							<dd>
								<a href="javascript:;" id="file_repository_btn" class="pubbtn bluebtn">搜索</a>
							</dd>
						</dl>
					</div>
					

				</div>
				</form>
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" class="pubbtn fffbtn" id="file-show-mail-btn">发送选中</a> 
				</div>

				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0" id="file_repository_table"></table>

			</div>
			<!-- 接触访谈信息 -->
			<div class="block" data-tab="con" id="projectProgress_1_con">
				<!--按钮-->
				<div id="options_point1" class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="#" id="limitInterview" data-btn="interview" onclick="air(1);" class="pubbtn fffbtn lpubbtn option_item_mark">添加访谈记录</a>
					<a href="javascript:startReview();" id="qdnbps" class="pubbtn fffbtn lpubbtn option_item_mark">启动内部评审</a>
				</div>
				<div id="projectProgress_1_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
				</div>
				<table id="projectProgress_1_table"
					data-url="<%=path%>/galaxy/project/progress/queryInterview"
					data-page-list="[3,10,20]"
					data-id-field="id" data-unique-id="id"
					data-side-pagination="server" data-pagination="true"
					 data-toolbar="#projectProgress_1_table_custom-toolbar">
					<thead>
						<tr>
							<th  data-formatter="intervierInfoFormat" class="th_no1">访谈概况</th>
						<!-- <th data-field="ftgk" >访谈概况</th> -->
						<th data-field="viewNotes"  data-formatter="tc_viewNotesFormat">访谈日志</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<!-- 内部评审信息      2  meetingType:1 -->
			<div class="block" data-tab="con" id="projectProgress_2_con">

				<!--按钮-->
				<div id="options_point2" class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" onclick="addMettingRecord(2,'meetingType:1')" id="addMeeting" data-btn="interview" class="pubbtn fffbtn lpubbtn option_item_mark">添加会议记录</a>
				</div>
				<div id="projectProgress_2_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name="meetingType" value="meetingType:1">
				</div>
				<!--表格内容-->
				<table id="projectProgress_2_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"
					data-toolbar="#projectProgress_2_table_custom-toolbar" data-page-list="[3,10]">
					<thead>
						<tr>
							<th  data-formatter="metcolumnFormat" class="th_no1">会议概况</th>
							<th data-field="meetingNotes"  data-formatter="tc_formatLog">会议纪要</th>
						</tr>
					</thead>
				</table>
			</div>



			<!-- CEO评审信息   3   meetingType:2-->
			<div class="block" data-tab="con" id="projectProgress_3_con">
				<!--按钮-->
				<div id="options_point3" class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a id="add_ceomeet" href="javascript:;" onclick="addMettingRecord(3,'meetingType:2')" class="pubbtn fffbtn lpubbtn option_item_mark">添加会议记录</a> 
					<a href="javascript:;" onclick="toEstablishStage()" id="lxhpq" class="pubbtn fffbtn lpubbtn option_item_mark">申请立项会排期</a>
					<a id="applyCeoMeeting" href="javascript:;" onclick="applyCeoMeeting()" class="pubbtn fffbtn lpubbtn option_item_mark">申请CEO评审排期</a>
				</div>
				
				<div id="projectProgress_3_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:2">
				</div>
				<!--表格内容-->
				<table id="projectProgress_3_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[3,10]"
					data-toolbar="#projectProgress_3_table_custom-toolbar">
					<thead>
						<tr>
							<th  data-formatter="metcolumnFormat" class="th_no1">会议概况</th>
							<th data-field="meetingNotes"  data-formatter="tc_formatLog">会议纪要</th>
						</tr>
					</thead>
				</table>

			</div>
			
			
			
			<!-- 立项会信息 -->
			<div class="block" data-tab="con" id="projectProgress_4_con">
				<!--按钮-->
				<div id="options_point4" class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a id="add_lxhmeet" href="javascript:;" onclick="addMettingRecord(4,'meetingType:3')" class="pubbtn fffbtn lpubbtn option_item_mark toggle">添加会议记录</a>
					<a id="reset_btn" href="javascript:;" onclick="toLxmeetingPool()" class="pubbtn fffbtn lpubbtn option_item_mark toggle">申请立项会排期</a>
					<a id="upload_lx_report" href="javascript:;" onclick="showLxUpload()" class="pubbtn fffbtn lpubbtn option_item_mark" style="display:none;">添加项目立项报告</a>
				</div>
				<table id="lx_report_table" >
					<thead>
						<tr>
							<th data-field="fWorktype" >业务类型</th>
							<th data-field="createdTime"  data-formatter="longTime_Format">创建日期</th>
							<th data-field="fType"  >存档类型</th>
							<th data-field="updatedTime"  data-formatter="longTime_Format">更新日期</th>
							<th data-field="fileStatusDesc" >档案状态</th>
							<th data-field="operateFile"  data-formatter="lxReportFormatter">操作</th>
						</tr>
					</thead>
				</table>
				<div id="projectProgress_4_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:3">
				</div>
				<!--表格内容-->
				<table id="projectProgress_4_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[3,10]"
					data-toolbar="#projectProgress_4_table_custom-toolbar">
					<thead>
						<tr>
							<th  data-formatter="metcolumnFormat" class="th_no1">会议概况</th>
							<th data-field="meetingNotes"  data-formatter="tc_formatLog">会议纪要</th>
						</tr>
					</thead>
				</table>

			</div>
			
			
			<!-- 投决会信息 -->
			<div class="block" data-tab="con" id="projectProgress_7_con">
				<!--按钮-->
				<div id="options_point7" class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a id="add_tjhmeet" href="javascript:;" onclick="addMettingRecord(7,'meetingType:4')"  class="pubbtn fffbtn lpubbtn option_item_mark">添加会议记录</a>
					<a id="inSure_btn" href="javascript:;" onclick="inSureMeetingPool()"  class="pubbtn fffbtn lpubbtn option_item_mark">申请投决会排期</a>
				</div>

				<div id="projectProgress_7_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:4">
				</div>
				<!--表格内容-->
				<table id="projectProgress_7_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[3,10]"
					data-toolbar="#projectProgress_7_table_custom-toolbar">
					<thead>
						<tr>
							<th  data-formatter="metcolumnFormat" class="th_no1">会议概况</th>
							<th data-field="meetingNotes"  data-formatter="tc_formatLog">会议纪要</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<!-- 投资意向书信息 -->
			<div class="block" data-tab="con" id="projectProgress_5_con">
			</div>
			<!-- 尽职调查信息 -->
			<div class="block" data-tab="con" id="projectProgress_6_con">
				<!--按钮-->
				<div id="jzdc_options" class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" onclick="inTjh()" class="pubbtn fffbtn lpubbtn option_item_mark toggle" id="tjhsqBut">申请投决会排期</a>
					<a href="javascript:;" onclick="uploadYwjd()" class="pubbtn fffbtn llpubbtn option_item_mark toggle"  id="scywjzdcbg">上传业务尽职调查报告</a>
					<a href="javascript:;" id="jdqd_report_upload_btn" onclick="showJDUpload('fileWorktype:18')" class="pubbtn fffbtn llpubbtn option_item_mark">上传尽职调查启动会报告</a>
					<a href="javascript:;" id="jdzj_report_upload_btn" onclick="showJDUpload('fileWorktype:19')" class="pubbtn fffbtn llpubbtn option_item_mark">上传尽职调查总结会报告</a>
				</div>
				<div class="process clearfix">
					<h2>业务尽调报告审核流程</h2>
					<img src="<%=path %>/img/process1.png" alt="">
				</div>
				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th style="padding-left:5px;">业务类别</th>
							<th>更新日期</th>
							<th>经办部门</th>
							<th>存储类型</th>
							<th>档案状态</th>
							<th>催办</th>
							<th>查看附件</th>
						</tr>
					</thead>
					<tbody id="fileList">
					</tbody>
				</table>
				<!-- 尽调启动会报告 -->
				<div id="jdqd_table_params">
					<input type="hidden" name="projectId">
					<input type="hidden" name="fileWorktype" value="fileWorktype:18">
				</div>
				<table id="jdqd_report_table" data-url="sopFile/query" data-toolbar="#jdqd_table_params" data-method="post">
					<thead>
						<tr>
							<th data-field="fWorktype">业务类型</th>
							<th data-field="createdTime" data-formatter="longTime_Format">创建日期</th>
							<th data-field="fType">存档类型</th>
							<th data-field="updatedTime" data-formatter="longTime_Format">更新日期</th>
							<th data-field="fileStatusDesc">档案状态</th>
							<th data-field="operateFile" data-formatter="jdReportFormatter">操作</th>
						</tr>
					</thead>
				</table>
				<!-- 尽调总结会报告 -->
				<div id="jdzj_table_params">
					<input type="hidden" name="projectId">
					<input type="hidden" name="fileWorktype" value="fileWorktype:19">
				</div>
				<table id="jdzj_report_table" data-url="sopFile/query" data-toolbar="#jdzj_table_params" data-method="post">
					<thead>
						<tr>
							<th data-field="fWorktype">业务类型</th>
							<th data-field="createdTime" data-formatter="longTime_Format">创建日期</th>
							<th data-field="fType">存档类型</th>
							<th data-field="updatedTime" data-formatter="longTime_Format">更新日期</th>
							<th data-field="fileStatusDesc">档案状态</th>
							<th data-field="operateFile" data-formatter="jdReportFormatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>
			<!-- 投资协议 -->
			<div class="block" data-tab="con" id="projectProgress_8_con">
				<!--按钮-->
				<div id="tzxy_options" class="btnbox_f btnbox_f1 btnbox_m clearfix">
				<div id="stock_transfer_model" class="option_item_mark">
					<input type="checkbox" value="1" class="input_checkbox" onclick="selected(this);" id="stock_transfer">
				    <label for="stock_transfer" class="check-box"></label> 
					<label for="stock_transfer" class="check-tit">涉及股权转让</label>
				</div>
				</div>
				<div class="process clearfix">
					<h2>投资协议盖章流程</h2>
					<img src="<%=path %>/img/process2.png" alt="">
				</div>

				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0" id="teamSeheetDataGrid">
					<thead>
						<tr>
							<th>业务分类</th>
							<th>存储类型</th>
							<th>更新日期</th>
							<th>档案状态</th>
							<th>模板下载</th>
							<th>上传附件</th>
							<th>查看附件</th>
							<th>签署凭证</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<!-- 股权交割信息  -->
			<div class="block" data-tab="con" id="projectProgress_9_con">
			</div>
			<!-- 投后运营  -->
			<div class="block" data-tab="con" id="projectProgress_10_con">
				<p>暂无内容</p>
			</div>
			<!-- 操作日志  -->
			<div class="block" data-tab="con" id="projectProgress_con">
				<div id="pro_message_custom-toolbar">
					<input type="hidden" name="projectId" value="">
				</div>
				<table id="projectProgress_table"
					data-url="<%=path%>/galaxy/operatlog/query"
					data-page-list="[5,10,15]" data-toolbar="#pro_message_custom-toolbar">
					<thead>
						<tr>
							<th data-field="createdTime"  data-formatter="longTimeFormat">时间</th>
							<th data-field="uname"  >操作者</th>
							<th data-field="operationType" >动作</th>
							<th data-field="operationContent" >对象</th>
							<th data-field="projectName"  >项目</th>
							<th data-field="reason" data-formatter="reason">原因</th>
							<th data-field="sopstage"  >业务</th>
						</tr>
					</thead>
				</table>
			</div>
		

			
		</div>
	</div>
</div>
<script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/init.js"></script>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<script>
     var projectId = alertid;
	 //盒子展开隐藏
	 getTabPersonforP();
	 getTabShareforP();
	 	
 	function getTabPersonforP(){
 		var $table = $('#tab_tablePerson');
 	    $table.bootstrapTable({
 	    url: Constants.sopEndpointURL + "/galaxy/project/queryProjectPerson", 
 	    dataType: "json",
 	    pagination: true, //分页
 	    search: false, //显示搜索框
 	    pageList: [10,20,30],
 	   	queryParamsType: 'size|page',
 	    queryParams:function(params){params.projectId =projectId; return params;},
 	    sidePagination: "server", //服务端处理分页
 	          columns: [
 	                  {
 	                    title: '姓名',
 	                      field: 'personName',
 	                      
 	                      valign: 'middle'
 	                  },
 	                  {
                         title: '性别',
                         field: 'personSex',
                         
                         valign: 'middle',
                         formatter:function(value,row,index){ 
                        	 if (row.personSex == 0) {
                     			return "男";
                     		}else if (row.personSex == 1) {
                     			return "女";
                     		}else {
                     			return "-";
                     		}
                         }
 	                    },
 	                    {
 	                        title: '年龄',
 	                          field: 'personAge',
 	                          
 	                          valign: 'middle'
 	                     },
 	                     {
 	                          title: '当前职务',
 	                            field: 'personDuties',
 	                            
 	                            valign: 'middle'
 	                  },
 	                  {
 	                      title: '电话',
 	                        field: 'personTelephone',
 	                        
 	                        valign: 'middle'
 	                  },
 	                  {
 	                      title: '最高学历',
 	                        field: 'highestDegree',
 	                        
 	                        valign: 'middle',
 	                       formatter:function(value,row,index){ 
	                         	if (row.highestDegree == 1) {
	                    			return "高中";
	                    		}else if (row.highestDegree == 2) {
	                    			return "大专";
	                    		}else if (row.highestDegree == 3) {
	                    			return "本科";
	                    		}else if (row.highestDegree == 4) {
	                    			return "硕士";
	                    		}else if (row.highestDegree == 5) {
	                    			return "MBA";
	                    		}else if (row.highestDegree == 6) {
	                    			return "博士";
	                    		}else if (row.highestDegree == 7) {
	                    			return "其他";
	                    		}
	                    		else {
	                    			return "-";
	                    		}
	                        }
 	                  },
 	                  {
 	                      title: '工作年限',
 	                        field: 'workTime',
 	                        
 	                        valign: 'middle'
 	                  }
 	              ]
 	      });
 	 //     $table.bootstrapTable('refresh');
 		}
 	//股权结构列表
 	function getTabShareforP(){
 	var $table = $('#table');
     $table.bootstrapTable({
     url: Constants.sopEndpointURL + "/galaxy/projectShares/selectProjectShares", 
     dataType: "json",
     pagination: true, //分页
     search: false, //显示搜索框
     showRefresh: true,
     pageList: [10,20,30],
     queryParamsType: 'size|page',
     queryParams:function(params){params.projectId =projectId;return params;},
     sidePagination: "server", //服务端处理分页
           columns: [
                   {
                     title: '类型',
                       field: 'sharesType',
                       
                       valign: 'middle'
                   },
                   {
                       title: '所有权人',
                         field: 'sharesOwner',
                         
                         valign: 'middle'
                     },
                     {
                         title: '占比(%)',
                           field: 'sharesRatio',
                           
                           valign: 'middle'
                      },
                      {
                           title: '获取方式',
                             field: 'gainMode',
                             
                             valign: 'middle'
                   },
                   {
                       title: '备注',
                         field: 'remark',
                         
                         valign: 'middle'
                   }
               ]
       });
       $table.bootstrapTable('refresh');
 	}
     
     /**
 	 * 加载项目详情数据
 	 */
 	sendGetRequest(platformUrl.detailProject + alertid, {}, function(data){
 		$("#prodescribe_show").html(data.entity.projectDescribe==null?"暂无项目描述":data.entity.projectDescribe);
 		$("#business_model_show").html(data.entity.projectBusinessModel==null?"暂无产品服务":data.entity.projectBusinessModel);
 		$("#projectDesc_show").html(data.entity.userPortrait==null?"暂无用户画像":data.entity.userPortrait);
 		$("#location_show").html(data.entity.companyLocation==null?"暂无公司定位":data.entity.companyLocation);
 		$("#portrait_show").html(data.entity.userPortrait==null?"暂无用户画像":data.entity.userPortrait);
 		$("#analysis_show").html(data.entity.prospectAnalysis==null?"暂无竞情分析":data.entity.prospectAnalysis);
 		
 		sendGetRequest(platformUrl.getBusinessPlanFile+"/"+alertid,null,function(data){
			var uploadOperator;
			var html;
			if(data.result.status=="OK"){
				//为空时候显示
				if(data.result.errorCode=="null"){				
					html = "<span class='content'>状态：未上传</span><span class='content'>更新时间：无</span>";
				}else{
					html = "<span class='content'>状态：已上传</span><span class='content'>更新时间："+data.entity.createDate+"</span>" + 
						"<a href='javascript:;' class=' f1' data-btn='download' onclick='downloadBusinessPlan(" + data.entity.id +")' >下载</a>";
				}
				$("#business_plan_dd").html(html);
			}else{
				
			}
		});
 		
 		
 		
 		
 	});
	 prodescribe_show();
	 	business_model_show();
	 	location_show();
	 	portrait_show();
	 	analysis_show();
 	function prodescribe_show(){
		var box = document.getElementById("prodescribe_show");  
		var text = box.innerHTML.replace(/<[^>]+>/g,"");  //去掉所有的html标记
		var newBox = document.createElement("span");  
		var btn = document.createElement("a");  
		newBox.innerHTML = text.substring(0,200);  
		btn.innerHTML = text.length >200 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情";  
		newBox.innerHTML = text.substring(0,200);  
		}  
		}  
		box.innerHTML = "";  
		box.appendChild(newBox);  
		box.appendChild(btn);  		
	}
	function business_model_show(){
		var box = document.getElementById("business_model_show");  
		var text = box.innerHTML.replace(/<[^>]+>/g,"");  //去掉所有的html标记
		var newBox = document.createElement("span");  
		var btn = document.createElement("a");  
		newBox.innerHTML = text.substring(0,200);  
		btn.innerHTML = text.length >200 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情";  
		newBox.innerHTML = text.substring(0,200);  
		}  
		}  
		box.innerHTML = "";  
		box.appendChild(newBox);  
		box.appendChild(btn);  		
	}
	function portrait_show(){
		var box = document.getElementById("portrait_show");  
		var text = box.innerHTML.replace(/<[^>]+>/g,"");  //去掉所有的html标记
		var newBox = document.createElement("span");  
		var btn = document.createElement("a");  
		newBox.innerHTML = text.substring(0,200);  
		btn.innerHTML = text.length >200 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情";  
		newBox.innerHTML = text.substring(0,200);  
		}  
		}  
		box.innerHTML = "";  
		box.appendChild(newBox);  
		box.appendChild(btn);  		
	}
	function location_show(){
		var box = document.getElementById("location_show");  
		var text = box.innerHTML.replace(/<[^>]+>/g,"");  //去掉所有的html标记
		var newBox = document.createElement("span");  
		var btn = document.createElement("a");  
		newBox.innerHTML = text.substring(0,200);  
		btn.innerHTML = text.length >200 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情";  
		newBox.innerHTML = text.substring(0,200);  
		}  
		}  
		box.innerHTML = "";  
		box.appendChild(newBox);  
		box.appendChild(btn);  		
	}
	function analysis_show(){
		var box = document.getElementById("analysis_show");  
		var text = box.innerHTML.replace(/<[^>]+>/g,"");  //去掉所有的html标记
		var newBox = document.createElement("span");  
		var btn = document.createElement("a");  
		newBox.innerHTML = text.substring(0,200);  
		btn.innerHTML = text.length >200 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情";  
		newBox.innerHTML = text.substring(0,200);  
		}  
		}  
		box.innerHTML = "";  
		box.appendChild(newBox);  
		box.appendChild(btn);  		
	} 
 	function downloadBusinessPlan(id){
		window.location.href=platformUrl.downLoadFile+'/'+id ;
	}
 	function replaceStr(str){
 		if(str){
 			var result=str.replace(/&nbsp;/g,"").replace("<p>","").replace("</p>","");
 			return result;
 		}
	}
 	
 	//立项报告上传
 	function showLxUpload(id)
 	{
 		$.getHtml({
			url:platformUrl.showReportUpload,
			okback:function(){
				$("#report_upload_form [name='projectId']").val(alertid);
				if(typeof(id) != 'undefined')
				{
					$("#report_upload_form [name='id']").val(id);
					$('.title_bj').html('更新项目立项报告')
				}
			}
		});
 	}
 	function initLxReportTable()
 	{
 		$("#lx_report_table").bootstrapTable('removeAll');
	 	sendPostRequestByJsonObj(
			platformUrl.queryFile, 
			{
				projectId:alertid,
				fileWorktype:"fileWorktype:17"
			}, 
			function(data){
				if(data.entityList.length>0)
				{
					$("#lx_report_table").bootstrapTable('load', data.entityList);
					$("#upload_lx_report").hide();
				}
				else
				{
					$("#upload_lx_report").show();
				}
			}
	 	);
 	}
 	
 	
 	function lxReportFormatter(val,row,index)
 	{
 		var update = canToOption ? '<a href="#" class="blue" onclick="showLxUpload('+row.id+')">更新</a>&nbsp;' : '';
 		var download = '<a href="#" class="blue" onclick="filedown('+row.id+')">查看</a>';
 		var del = '<a href="#" class="blue" style="display:none;" onclick="delLxReport('+row.id+')">删除</a>';
 		return update+download+"&nbsp;"+del;
 	}
 	function delLxReport(id)
 	{
 		layer.confirm("确定删除？",function(i){
 			layer.close(i);
 			sendPostRequestByJsonObj(
 				platformUrl.delFile+"?id="+id,
 				{},
 				function(data){
 					if(data.result.status=='OK')
					{
						layer.msg("删除成功");
						initLxReportTable();
					}
 					else
					{
						layer.msg("删除失败。");
					}
 				}
 			);
 		});
 	}
 	
 	function reason(value){
 		if(value == '' || value == null || value == 'null' || typeof(value) == "undefined"){
 			return "--";
 		}
 		else{
 			var resetUrl = "<a  href='javascript:void(0);' title='"+value+"'><div class='width_1'>"+value+"</div></a>";
 			return   resetUrl;
 		}
 	}
 	function jdReportFormatter(val,row,index)
 	{
 		var update = canToOption ? '<a href="#" class="blue" onclick="showJDUpload(\''+row.fileWorktype+'\','+row.id+')">更新</a>&nbsp;' : '';
 		var download = '<a href="#" class="blue" onclick="filedown('+row.id+')">查看</a>';
 		return update+download;
 	}
 	function showJDUpload(type,id)
 	{
 		$.getHtml({
			url:platformUrl.showReportUpload,
			okback:function(){
				$("#report_upload_form [name='projectId']").val(alertid);
				var action = "上传";
				var name = "";
				$("#report_upload_form [name='fileWorktype']").val(type);
				$("#report_upload_form [name='projectProgress']").val("projectProgress:6");
				if(typeof(id) != 'undefined')
				{
					$("#report_upload_form [name='id']").val(id);
					action = "更新"
				}
				if('fileWorktype:18' == type)
				{
					name="尽职调查启动会报告";
				}
				else if('fileWorktype:19' == type)
				{
					name="尽职调查总结会报告";
				}
				$('.title_bj').html(action+name)
				$('#reportName').html(name)
			}
		});
 	}
 	$.when(proDef).done(function(){
 		$("#jdqd_table_params [name='projectId']").val(alertid);
 		$("#jdzj_table_params [name='projectId']").val(alertid);
 		
 		$("#jdqd_report_table").bootstrapTable();
 		$("#jdqd_report_table").on('load-success.bs.table',function(table,data){
 			if(data.entityList.length>0)
			{
				$("#jdqd_report_table").bootstrapTable('load', data.entityList);
				$("#jdqd_report_upload_btn").hide();
			}
			else
			{
				$("#jdqd_report_upload_btn").show();
			}
 		});
 		
 		$("#jdzj_report_table").bootstrapTable();
 		$("#jdzj_report_table").on('load-success.bs.table',function(table,data){
 			if(data.entityList.length>0)
			{
 				$("#jdzj_report_table").bootstrapTable('load', data.entityList);
				$("#jdzj_report_upload_btn").hide();
			}
			else
			{
				$("#jdzj_report_upload_btn").show();
			}
 		})
 		
 	});
 
</script>
