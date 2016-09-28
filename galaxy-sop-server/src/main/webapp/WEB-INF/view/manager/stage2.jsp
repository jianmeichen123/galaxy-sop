<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<style>
#projectProgress_1_table th:nth-child(1),#projectProgress_2_table th:nth-child(1),#projectProgress_3_table th:nth-child(1),#projectProgress_4_table th:nth-child(1),#projectProgress_7_table th:nth-child(1) {
    width: 50%;
}
#projectProgress_1_table td,#projectProgress_2_table td,#projectProgress_3_table td,#projectProgress_4_table td,#projectProgress_7_table td{line-height:22px;text-align:left !important;}
#projectProgress_1_table th,#projectProgress_2_table th,#projectProgress_3_table th,#projectProgress_4_table th,#projectProgress_7_table th{
    width: 50%;
}
.content{
			float: left;
			padding:0 10px;
		}
</style>
<div class="myprojecttc">
	<a href="javascript:;" data-close="close" class="close null">关闭</a>
	<!-- 项目介绍 -->
	<div class="pjt_introduct clearfix">
		<div class="clearfix">
			<h3 id="project_name"></h3>
		</div>	
	</div>

	<div class="tabtable">
		<!-- tab标签 -->
		<ul class="tablink">
			<li data-tab="nav"><a href="javascript:;">基本信息</a></li>
			<li data-tab="nav" id="fileRepository"><a href="javascript:;">档案库</a></li>
			<li data-tab="nav" id="projectProgress_1"><a href="javascript:;">接触访谈</a></li>
			<li data-tab="nav" id="projectProgress_2"><a href="javascript:;">内部评审</a></li>
			<li data-tab="nav" id="projectProgress_3"><a href="javascript:;">CEO评审</a></li>
			<li data-tab="nav" id="projectProgress_4"><a href="javascript:;">立项会</a></li>
			<li data-tab="nav" id="projectProgress_5"><a href="javascript:;">投资意向书</a></li>
			<li data-tab="nav" id="projectProgress_6"><a href="javascript:;">尽职调查</a></li>
			<li data-tab="nav" id="projectProgress_7"><a href="javascript:;">投决会</a></li>
			<li data-tab="nav" id="projectProgress_8"><a href="javascript:;">投资协议</a></li>
			<li data-tab="nav" id="projectProgress_9"><a href="javascript:;">股权交割</a></li>
			<li data-tab="nav" id="projectProgress_10"><a href="javascript:;">投后运营<a/></li>
			<li data-tab="nav" id="projectProgress"><a href="javascript:;">操作日志</a></li>
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
							<table id="tablePerson" data-height="555" data-method="post"
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
							<table id="table" data-height="555" data-method="post"
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
				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0" id="file_repository_table">
				</table>
			</div>
			<!-- 接触访谈信息 -->
			<div class="block" data-tab="con" id="projectProgress_1_con">
				<div id="projectProgress_1_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
				</div>
				<table id="projectProgress_1_table"
					data-url="<%=path%>/galaxy/project/progress/queryInterview"
					data-page-list="[10,20,30]"
					data-id-field="id" data-unique-id="id" 
					data-side-pagination="server"
					 data-toolbar="#projectProgress_1_table_custom-toolbar" data-show-refresh="true" > 
					<thead>
						<tr>
							<th  data-formatter="ftcolumnFormat">访谈概况</th>
						<!-- <th data-field="ftgk" >访谈概况</th> -->
						<th data-field="viewNotes"  data-formatter="tc_viewNotesFormat">访谈日志</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<!-- 内部评审信息      2  meetingType:1 -->
			<div class="block" data-tab="con" id="projectProgress_2_con">

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
							<th  data-formatter="metcolumnFormat">会议概况</th>
							<th data-field="meetingNotes"  data-formatter="tc_formatLog">会议纪要</th>
						</tr>
					</thead>
				</table>
			</div>



			<!-- CEO评审信息   3   meetingType:2-->
			<div class="block" data-tab="con" id="projectProgress_3_con">
				<div id="projectProgress_3_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:2">
				</div>
				<!--表格内容-->
				<table id="projectProgress_3_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[10,20,30]"
					data-toolbar="#projectProgress_3_table_custom-toolbar">
					<thead>
						<tr>
							<th  data-formatter="metcolumnFormat">会议概况</th>
							<th data-field="meetingNotes"  data-formatter="tc_formatLog">会议纪要</th>
						</tr>
					</thead>
				</table>

			</div>
			
			
			
			<!-- 立项会信息 -->
			<div class="block" data-tab="con" id="projectProgress_4_con">
				<div id="projectProgress_4_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:3">
				</div>
				<!--表格内容-->
				<table id="projectProgress_4_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[10,20,30]"
					data-toolbar="#projectProgress_4_table_custom-toolbar">
					<thead>
						<tr>
							<th  data-formatter="metcolumnFormat">会议概况</th>
							<th data-field="meetingNotes"  data-formatter="tc_formatLog">会议纪要</th>
						</tr>
					</thead>
				</table>

			</div>
			
			
			<!-- 投决会信息 -->
			<div class="block" data-tab="con" id="projectProgress_7_con">
				<div id="projectProgress_7_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:4">
				</div>
				<!--表格内容-->
				<table id="projectProgress_7_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[10,20,30]"
					data-toolbar="#projectProgress_7_table_custom-toolbar">
					<thead>
						<tr>
							<th  data-formatter="metcolumnFormat">会议概况</th>
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
				<div class="process clearfix">
					<h2>业务尽调报告审核流程</h2>
					<img src="<%=path %>/img/process1.png" alt="">
				</div>
				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>业务类别</th>
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
			</div>
			<!-- 投资协议 -->
			<div class="block" data-tab="con" id="projectProgress_8_con">
				<div class="process clearfix">
					<h2>投资协议盖章流程</h2>
					<img src="<%=path %>/img/process2.png" alt="">
				</div>

				<!--表格内容-->
				<table class="fixed_width" width="100%" cellspacing="0" cellpadding="0" id="teamSeheetDataGrid">
					<thead>
						<tr>
							<th>业务分类</th>
							<th>存储类型</th>
							<th>更新日期</th>
							<th>档案状态</th>
							<th>上传/查看附件</th>
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
					data-page-list="[10,20,30]" data-toolbar="#pro_message_custom-toolbar">
					<thead>
						<tr>
							<th data-field="createdTime"  data-formatter="longTimeFormat">时间</th>
							<th data-field="uname"  >操作者</th>
							<th data-field="operationType" >动作</th>
							<th data-field="operationContent" >对象</th>
							<th data-field="projectName"  >项目</th>
							<th data-field="sopstage"  >业务</th>
						</tr>
					</thead>
				</table>
			</div>
		


			
		</div>
	</div>
</div>
<script src="<%=path %>/js/base64.js" type="text/javascript"></script>
<script src="<%=path %>/js/common.js" type="text/javascript"></script>
<script src="<%=path %>/js/init.js" type="text/javascript"></script>
<script src="<%=path %>/js/manager/js/filerepository.js"></script>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<%-- <script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script> --%>
<%-- <script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script> --%>
<script>
var uid='${galax_session_user.id }';
$(function(){
	initData("${projectId}");
});
	//盒子展开隐藏
	getTabPersonforP();
	 getTabShareforP();

 	function getTabPersonforP(){
 		var $table = $('#tablePerson');
 	    $table.bootstrapTable({
 	    url: Constants.sopEndpointURL + "/galaxy/project/queryProjectPerson", 
 	    dataType: "json",
 	    pagination: true, //分页
 	    search: false, //显示搜索框
 	    pageList: [10,20,30],
 	    queryParams: queryParamsforP,
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
     queryParams: queryParamsforP,
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
     //页面传参
     function queryParamsforP(params) {
     	return {
 	    	pageSize: params.limit,
 	    	pageNum: params.offset,
 	    	order: params.order,
 	    	projectId:"${projectId}"
     	};
     }
     /**
 	 * 加载项目详情数据
 	 */
 	sendGetRequest(platformUrl.detailProject + "${projectId}", {}, function(data){
 		$("#prodescribe_show").html(data.entity.projectDescribe==null?"暂无项目描述":data.entity.projectDescribe);
 		$("#business_model_show").html(data.entity.projectBusinessModel==null?"暂无产品服务":data.entity.projectBusinessModel);
 		$("#projectDesc_show").html(data.entity.userPortrait==null?"暂无用户画像":data.entity.userPortrait);
 		$("#location_show").html(data.entity.companyLocation==null?"暂无公司定位":data.entity.companyLocation);
 		$("#portrait_show").html(data.entity.userPortrait==null?"暂无用户画像":data.entity.userPortrait);
 		$("#analysis_show").html(data.entity.prospectAnalysis==null?"暂无竞情分析":data.entity.prospectAnalysis);
 		
 		sendGetRequest(platformUrl.getBusinessPlanFile+"/"+"${projectId}",null,function(data){
			var uploadOperator;
			var html;
			if(data.result.status=="OK"){
				//为空时候显示
				if(data.result.errorCode=="null"){				
					html = "<span class='content'>状态：未上传</span><span class='content'>更新时间：无</span>";
				}else{
					html =  "<span class='content'>状态：已上传</span><span class='content'>更新时间："+data.entity.createDate+"</span>" + 
						"<a href='javascript:;' class='ico f1' data-btn='download' onclick='downloadBusinessPlan(" + data.entity.id +")' >下载</a>";
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
 	function showLogdetail(selectRowId){
 		var interviewSelectRow = $('#projectProgress_1_table').bootstrapTable('getRowByUniqueId', selectRowId);
 		var _url = Constants.sopEndpointURL+"/galaxy/project/progress/interViewLog";
 		$.getHtml({
 			url:_url,//模版请求地址
 			data:"",//传递参数
 			okback:function(){
 			var um=UM.getEditor('viewNotes');
 			um.setContent(interviewSelectRow.viewNotes);
 			//alert(uid+"----"+interviewSelectRow.createdId);
 			$("#vid").val(selectRowId);
 			if(uid!=interviewSelectRow.createdId){
 				$("#interviewsave").hide();
 				um.setDisabled();
 			}
 			
 		}//模版反回成功执行	
 	});
 		return false;
 	}
 	function interviewsave(){  
 			var um = UM.getEditor('viewNotes');
 		var log = um.getContent();
 		var pid=$("#vid").val();
 		if(pid != '' && log != ''){
 			sendPostRequestByJsonObj(platformUrl.updateInterview, {"id" : pid, "viewNotes" : log}, function(data){
 				if (data.result.status=="OK") {
 					$("#hint_all").css("display","none");
 					layer.msg("保存成功");
 					$(".meetingtc").find("[data-close='close']").click();
 					$("#projectProgress_1_table").bootstrapTable('refresh');
 				} else {
 					layer.msg(data.result.message);
 					$("#hint_all").css("display","block");
 				}
 				
 			});
 		}
 	}
 	
 	function initData(id)
 	{
		$(".myprojecttc .tabtable").tabchange5();
		$('.searchbox').toggleshow();
		leicj();
		/**
		 * 加载项目详情数据
		 */
		sendGetRequest(platformUrl.detailProject + id, {}, function(data){
			hasClosed = (data.entity.projectStatus == 'meetingResult:3' || data.entity.projectStatus == 'projectStatus:2' || data.entity.projectStatus == 'projectStatus:3');
			var pp = data.entity.projectProgress;
			var pNum = pp.substr(pp.length-1,1);
			var updatedTime = Number(data.entity.createdTime).toDate().format('yyyy-MM-dd');
			if(data.entity.hasOwnProperty('updatedTime'))
			{
				updatedTime = Number(data.entity.updatedTime).toDate().format('yyyy-MM-dd');
			}
			$("#pj-title-updated-time").html('<span>&#40;</span>'+updatedTime+'<span>&#41;</span>');
			$("#project_name").text(data.entity.projectName);
			$("input[name='projectId']").val(data.entity.id);
			$("#project_id").val(id);
			var progress = data.entity.projectProgress;
			progress = progress.replace(":","_");
			//var index = progress.substr(progress.length-1,1);
			var index = progress.substr("projectProgress_".length);
			for(var i = 1; i<11; i++){
				if(i > index){
					//当前阶段之后的tab变为不可用
					$("#projectProgress_" + i).addClass("disabled");
					$("#projectProgress_" + i).attr("disabled","disabled");
				}
				if(i == 1){
					if(hasClosed){
						$("#options_point1").remove();
					}
					tiggerTable($("#" + progress + "_table"),3);
				}
				if(i == 2){
					if(hasClosed){
						$("#options_point2").remove();
					}
				}
				if(i == 3){
					if(hasClosed){
						$("#options_point3").remove();
					}
				}
				if(i == 4){
					if(hasClosed){
						$("#options_point4").remove();
					}
				}
				if(i == 5){
					tzyxs(0);
				}
				if(i == 6){
					if(hasClosed){
						$("#jzdc_options").remove();
					}
					jzdc();
				}
				if(i == 7){
					if(hasClosed){
						$("#options_point7").remove();
					}
				}
				if(i == 8){
					if(hasClosed){
						$("#tzxy_options").remove();
					}
					tzxy(data.entity.stockTransfer,data.entity.projectType);
				}
				if(i == 9){
					gqjg();
				}
				
				
				//为Tab添加点击事件，用于重新刷新
				$("#projectProgress_" + i).on("click",function(){
					var id = $(this).attr("id");
					var indexNum = id.substr(id.length-1,1);
					if(indexNum == '1'){
						if(parseInt(indexNum) < parseInt(pNum)){
							$("#qdnbps").remove();
						}
					    $("#projectProgress_1_con").css("display","block");
						tiggerTable($("#projectProgress_1_table"),3);
					}else if(indexNum == '2'){
					    $("#projectProgress_2_con").css("display","block");
						tiggerTable($("#projectProgress_2_table"),3);
					}else if(indexNum == '3'){
						if(parseInt(indexNum) < parseInt(pNum)){
							$("#lxhpq").remove();
						}
						$("#projectProgress_3_con").css("display","block");
						tiggerTable($("#projectProgress_3_table"),3);
					} else if(indexNum == '4'){
						$("#projectProgress_4_con").css("display","block");
						if(parseInt(indexNum) < parseInt(pNum)){
							$("#reset_btn").css("display","none");
						}
					    tiggerTable($("#projectProgress_4_table"),3);
					} else if(indexNum == '5'){
						$("#projectProgress_7_con").css("display","none");
						$("#projectProgress_5").addClass("on");
						$("#projectProgress_5_con").css("display","block");
						if(parseInt(indexNum) < parseInt(pNum)){
							tzyxs(1);
						}else{
							tzyxs(0);
						}
					}else if(indexNum == '6'){
						$("#projectProgress_5_con").css("display","none");
						 $("#projectProgress_6_con").css("display","block");
						 tiggerTable($("#projectProgress_6_table"),3);
						 if(parseInt(indexNum) < parseInt(pNum)){
							 $("#jzdc_options").remove();
						 }
					}else if(indexNum == '7'){
						$("#projectProgress_6_con").css("display","none");
						$("#projectProgress_7_con").css("display","block");
						if(parseInt(indexNum) < parseInt(pNum)){
							$("#inSure_btn").css("display","none");
						}
						 tiggerTable($("#projectProgress_7_table"),3);
					}else if(indexNum == '8'){
						$("#projectProgress_7_con").css("display","none");
						$("#projectProgress_8_con").css("display","block");
						
						 if(parseInt(indexNum) < parseInt(pNum)){
							 $("#tzxy_options").remove();
						 }
						 tzxy(data.entity.stockTransfer,data.entity.projectType);
					}else if(indexNum == '9'){
						$("#projectProgress_8_con").css("display","none");
						$("#projectProgress_9").addClass("on");
						$("#projectProgress_9_con").css("display","block");
						gqjg();
					}
				});
			}
			$("#projectProgress").on("click",function(){
				$("#progress").addClass("on");
				$("#projectProgress_con").css("display","block");
				tiggerTable($("#projectProgress_table"),5);
				//$("#projectProgress_table").bootstrapTable("refresh");
			});
			$("#fileRepository").on("click",function(){
				$("#fileRepository").addClass("on");
				$("#file_repository").css("display","block");
				data = {
						_domid : "file_repository_table",
						_projectId : "${projectId}"
				}
				fileGrid.init(data);
			});				
			$("#" + progress).addClass("on");
			$("#" + progress + "_con").css("display","block");
		},null);
 	}
 	 /**
	  * 动态生成投资意向书阶段HTML
	  */
	function tzyxs(flag){
		 var pid = "${projectId}";
		 if(pid != '' && pid != null){
			 /**
			  *  生成尽职调查报告列表
			  */
			 sendGetRequest(
					 Constants.sopEndpointURL + '/galaxy/project/progress/proFileInfo/'+pid+'/5',
					 null, function(data){
						 var json = eval(data);
						 var dataList=json.entityList;
							for(var ii = 0 ; ii < dataList.length ; ii++){
							    var p = ii ;	
						        var htmlhead = '<div id="tzyxs_options" class="btnbox_f btnbox_f1 btnbox_m clearfix"></div>'+
							        '<div class="process clearfix">'+
							        '<h2>投资意向书盖章流程</h2>'+
							        '<img src="<%=path%>/img/process.png" alt="">'+
							        '</div>';
							        
								 var htmlstart=htmlhead+'<table width=\"100%" cellspacing="0" cellpadding="0" >'+
									             '<thead>'+
									                '<tr>'+
									                 '<th>业务分类</th>'+
									                 '<th>创建日期</th>'+
									                 '<th>存储类型</th>'+
									                 '<th>更新日期</th>'+
									                 '<th>档案状态</th>'+
									                 '<th>查看附件</th>'+
									                 '</tr>'+
									            '</thead>'+                                                                                                                                   
									             '<tbody>';
										var typehtml = "";
										if (typeof(dataList[p].fType) == "undefined") { 
											typehtml ='<td>未知</td>';
										}else{
											typehtml = '<td>'+dataList[p].fType+'</td>';
										}
										
										var endhtml ="";
										if (dataList[p].fileStatusDesc == "缺失") { 
											endhtml ='<td>缺失</td>';
										}else{
											endhtml = '<td><a href="javascript:;" onclick="filedown('+dataList[p].id+');" class="blue">查看</a></td>';
										}
										
										htmlstart +='<tr>'+
										'<td>'+dataList[p].fWorktype+'</td>'+
										'<td>'+dataList[p].createDate+'</td>'+
										typehtml
										+'<td>'+getVal(dataList[p].updatedDate,'')+'</td>'
										+'<td>'+dataList[p].fileStatusDesc+'</td>'+
										endhtml+
										'</tr>';   
										
							}
							var htmlend= '</tbody></table>';
							$("#projectProgress_5_con").html(htmlstart+htmlend);
							if(flag == 1){
					        	$("#tzyxs_options").remove();
					        }
					 });
		 }
	 }
		 
	 /**
	  * 尽职调查
	  */
	 function jzdc(){
		 
		 var pid = "${projectId}";
		 if(pid != '' && pid != null){
			 /**
			  *  生成尽职调查报告列表
			  */
			 sendGetRequest(
					 Constants.sopEndpointURL + '/galaxy/project/progress/proFileInfo/'+pid+'/6', 
					 null, function(data){
				 var html = "";
				 $.each(data.entityList, function(i,o){
					 html += "<tr>";
					 if(o.fileWorktype == 'fileWorktype:1'){
						 html += "<td>业务尽职调查报告";
						 html += "</td><td>" + getVal(o.updatedDate,o.createDate) + "</td>";
						 html += "<td>"+o.careerLineName+"</td><td>"+o.fType+"</td>";
					 }else if(o.fileWorktype == 'fileWorktype:2'){
						 html += "<td>人事尽职调查报告";
						 html += "</td><td>" + getVal(o.updatedDate,o.createDate) + "</td>";
						 html += "<td>人事部</td><td>"+o.fType+"</td>";
					 }else if(o.fileWorktype == 'fileWorktype:3'){
						 html += "<td>法务尽职调查报告";
						 html += "</td><td>" + getVal(o.updatedDate,o.createDate) + "</td>";
						 html += "<td>法务部</td><td>"+o.fType+"</td>";
					 }else if(o.fileWorktype == 'fileWorktype:4'){
						 html += "<td>财务尽职调查报告";
						 html += "</td><td>" + getVal(o.updatedDate,o.createDate) + "</td>";
						 html += "<td>财务部</td><td>"+o.fType+"</td>";
					 }
					 if(o.fileStatus == 'fileStatus:1'){
						 html += "<td>缺失</td>";
						 if(o.fileWorktype != 'fileWorktype:1'){
							 html +='<td></td>';

						 }else{
							 html += "<td></td>";
						 }
						 html += "<td>无</td>";
					 }else if(o.fileStatus == 'fileStatus:2'){
						 if(o.fileWorktype == 'fileWorktype:1'){
							 $("#jzdc_options a:eq(0)").text('更新业务尽职调查报告')
						 }
						 html += "<td>已上传</td>";
						 html += "<td></td>";
						 html += "<td><a href='javascript:filedown("+o.id+");' class='blue'>查看</a></td>";
					 }else if(o.fileStatus == 'fileStatus:3'){
						 html += "<td>已签署</td>";
						 html += "<td></td>";
						 html += "<td><a href='javascript:void(0);'>" + o.fileName + "</a></td>";
					 }
					 html += "</tr>";
			   	 });
				 $("#fileList").append(html);
			 });
		 }
	}
	/**
	 * 动态生成投资协议的HTML
	 */
	function tzxy(st,projectType){
		//0:首次展示 1：点击触发刷新
		var pid = "${projectId}";
		if(pid != '' && pid != null){
			var _table = $("#teamSeheetDataGrid");
			var _tbody = _table.find("tbody");
			_tbody.empty();
			sendPostRequestByJsonObj(
					platformUrl.searchSopFileListWithoutPage,
					{"projectId" : pid},
					function(data){
						
						_tbody.empty();
						$.each(data.entityList,function(){
							
								var $tr=$('<tr></tr>');
								
								//页面初始化
								if(this.fileWorktype == 'fileWorktype:7' && st==0){
									$tr.attr("id","gwxt_tr").css("display","none");
								}else if(this.fileWorktype == 'fileWorktype:7' && st==1){
									$tr.attr("id","gwxt_tr");
								}
								
								$tr.append('<td>'+this.fWorktype+'</td>') ;
								if(this.fileType){
									$tr.append('<td>'+this.fType+'</td>');
									$tr.append('<td>'+this.updatedDate+'</td>') ;
								}else{
									$tr.append('<td>未知</td>');
									$tr.append('<td></td>') ;
								}	
								$tr.append('<td>'+this.fileStatusDesc+'</td>');
								if(this.fileWorktype == 'fileWorktype:6'){
									if(this.fileKey == null){	
										$tr.append('<td></td>');
									}else{
										$tr.append('<td><a href="javascript:;" onclick="filedown('+this.id+')" class="blue">查看</a></td>'); 	
									}
									if(this.voucherFileKey == null){	
										$tr.append('<td></td>');
									}else{
										$tr.append('<td><a href="javascript:;" onclick="filedown('+this.voucherId+',null,\'voucher\'); " class="blue">查看</a></td>'); 	
									}
								}else if(this.fileWorktype == 'fileWorktype:7'){
									
									if(this.fileKey == null){	
										$tr.append('<td></td>');
									}else{
										$tr.append('<td><a href="javascript:;" onclick="filedown('+this.id+');" class="blue">查看</a></td>'); 	
									}
									if(this.voucherFileKey == null){	
										$tr.append('<td></td>');
									}else{
										$tr.append('<td><a href="javascript:;" onclick="filedown('+this.voucherId+',null,\'voucher\');" class="blue">查看</a></td>'); 	
									}
								}
								_tbody.append($tr);
								//涉及股权转让
								if(st == 1){
									$("#stock_transfer").attr("checked","checked");
									$("#stock_transfer").attr("disabled","true");
								}else{
									
								}
							
							
						});
						
						
					}
			);	
			if(projectType == 'projectType:2'){
				$("#stock_transfer_model").remove();
			}
		}
	}
	 
	 
	 /**
	  * 动态生成股权交割的HTML
	  */
	function gqjg(){
		var pid = "${projectId}";
		if(pid != '' && pid != null){
			sendGetRequest(
					platformUrl.getFileList + pid + "/9",
					null,
					function(data){
						var json = eval(data);
						 var dataList=json.entityList;
						 var htmlstart='<table width=\"100%" cellspacing="0" cellpadding="0" >'+
							             '<thead>'+
							                '<tr>'+
							                 '<th>业务分类</th>'+
							                 '<th>创建日期</th>'+
							                 '<th>存储类型</th>'+
							                 '<th>更新日期</th>'+
							                 '<th>查看附件</th>'+
							                 '</tr>'+
							            '</thead>'+                                                                                                                                   
							             '<tbody>';
							         	for(var ii = 0 ; ii < dataList.length ; ii++){
										    var p = ii ;	
													var typehtml = "";
													if (typeof(dataList[p].fType) == "undefined") { 
														typehtml ='<td></td>';
													}else{
														typehtml = '<td>'+dataList[p].fType+'</td>';
													}
													
													var handlehtml = "";
													if (dataList[p].fileStatusDesc == "缺失" && !hasClosed) { 
														handlehtml ='<td></td>';
													}else{
														handlehtml = '<td></td>';
													}
													
													var endhtml ="";
													if (dataList[p].fileStatusDesc == "缺失") { 
														endhtml ='<td>'+dataList[p].fileStatusDesc+'</td>';
													}else{
														endhtml = '<td><a href="javascript:; " onclick="filedown('+dataList[p].id+');" class="blue">查看</a></td>';
													}
													
													var updatedDate ="";
													if (dataList[p].updatedDate == null || dataList[p].updatedDate == "") { 
														updatedDate =dataList[p].createDate;
													}else{
														updatedDate = dataList[p].updatedData;
													}
													
													htmlstart +='<tr>'+
													'<td>'+dataList[p].fWorktype+'</td>'+
													'<td>'+dataList[p].createDate+'</td>'+
													typehtml+
													'<td>'+getVal(dataList[p].updatedDate,'')+'</td>'+  
													endhtml+   
													'</tr>';   
										}
							var htmlend= '</tbody></table>';
							$("#projectProgress_9_con").html(htmlstart+htmlend);
					}
			);	
		}
	}
	
	function ftcolumnFormat(value, row, index){
		var fileinfo = "" ;
		var rc = "";
		if( row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
			fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
		}
		rc = "<div style=\"text-align:left;margin-left:20%;\">"+
					"访谈时间："+row.viewDateStr+
					"</br>访谈对象："+row.viewTarget+
					"</br>访谈录音："+fileinfo+
				"</div>" ;
		return rc;
	}
		
	function metcolumnFormat(value, row, index){
		var fileinfo = "";
		var rc = "";
		if(row.fileId != null && row.fileId != undefined && row.fileId != "undefined"){
			fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
		}
		rc = "<div style=\"text-align:left;margin-left:20%;\">"+
					"会议日期："+row.meetingDateStr+
					"</br>会议结论："+row.meetingResultStr+
					"</br>会议录音："+fileinfo+
				"</div>" ;
		return rc;
	}
	
	
	function downFile(id){
		var pidParam = "";
		if(alertid>=0)
		{
			pidParam = "&projectId="+alertid;
		}
		var url = platformUrl.tempDownload+"?id="+id+pidParam;
		forwardWithHeader(url);
	}
	
</script>
