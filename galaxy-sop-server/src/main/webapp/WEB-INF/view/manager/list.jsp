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
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>

</head>
<script>
//设置事业线下拉框
function setCheckLine(data){
	
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		//layer.msg(data.result.message);
		_this.empty();
		_this.append("<option value='0000'></option>");
		return;
	}else{
		if(data.result.message == 'notg'){
			$("#ishas").remove();
		}else{
			var _this = $("select[name='projectDepartid']");
			var entityList = data.entityList;
			_this.empty();
			_this.append("<option value=''>全部</option>");
			for(var i=0;i<data.entityList.length;i++){
				_this.append("<option value='"+data.entityList[i].id+"' "+data.entityList[i].remark+">"+data.entityList[i].name+"</option>");
		    } 
		}
	}
}
</script>
<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
 	<h2>项目列表</h2>
    	<input type="hidden" id="project_id" value=""/>
        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="custom-toolbar">
			<div class="bottom searchall clearfix search_adjust2">
				<dl class="fmdl fml fmdll clearfix" id="ishas">
	              <dt>投资事业线：</dt>
	              <dd>
	                <select name="projectDepartid"> 
	                  <option value="">全部</option>
	                </select>
	              </dd>
	            </dl>
	            
				<dl class="fmdl fml fmdll clearfix">
	              <dt>项目类型：</dt>
	              <dd>
	                <select name="projectType">
	                  <option value="">全部</option>
	                  <option value="projectType:1">外部投资</option>
	                  <option value="projectType:2">内部创建</option>
	                </select>
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	              <dt>项目进度：</dt>
	              <dd>
	                <select name="projectProgress">
	                  <option value="">全部</option>
	                  <option value="projectProgress:1">接触访谈</option>
	                  <option value="projectProgress:2">内部评审</option>
	                  <option value="projectProgress:3">CEO评审</option>
	                  <option value="projectProgress:4">立项会</option>
	                  <option value="projectProgress:5">投资意向书</option>
	                  <option value="projectProgress:6">尽职调查</option>
	                  <option value="projectProgress:7">投资决策会</option>
	                  <option value="projectProgress:8">投资协议</option>
	                  <option value="projectProgress:9">股权交割</option>
	                  <option value="projectProgress:10">投后运营</option>
	                  <option value="guanbi">已关闭的项目</option>	                  
	                </select>
	              </dd>
	            </dl>
				<dl class="fmdl fmdll clearfix">
					<dt></dt>
					<dd>
						<input type="text" class="txt" id="keyword" name="keyword" placeholder="请输入项目名称或项目编码"/>
					</dd>
					<dd style="float:right">
						<button type="submit" class="bluebtn ico cx" name="querySearch" style="margin:0">搜索</button>
					</dd>
				</dl>
			</div>
		</div>
		
		<script type="text/javascript">
			sendGetRequest(platformUrl.queryCheckLine,null,setCheckLine);
		</script>
		
		<div class="tab-pane active" id="view">	
			<table id="data-table" data-url="project/queryAllProjects" data-height="555" 
				data-page-list="[10,20,30]" data-toolbar="#custom-toolbar">
				<thead>
				    <tr>
				    	<th data-field="projectCode" data-align="center" class="data-input">项目编码</th>				    	
			        	<th data-field="projectName" data-align="center" class="data-input" data-formatter="projectNameFormatter">项目名称</th>			        	
			        	<th data-field="progress" data-align="center" class="data-input" data-formatter="progressFormatter">项目进度</th>
			        	<th data-field="projectCareerline" data-align="center" class="data-input">投资事业线</th>
			        	<th data-field="hhrName" data-align="center" class="data-input">合伙人</th>
			        	<th data-field="createUname" data-align="center" class="data-input">投资经理</th>
			        	<th data-field="type" data-align="center" class="data-input">项目类型</th>
			        	<th data-field="projectContribution" data-align="center" class="data-input">投资金额（万）</th>
			        	<th data-field="createDate" data-align="center" class="data-input" data-sortable="true">创建日期</th>
			        	<th data-field="updateDate" data-align="center" class="data-input">最后修改时间</th>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/my_ext.js"></script>
<script src="<%=path %>/js/my.js"></script>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>

<script type="text/javascript" src="<%=path %>/js/manager/js/filerepository.js"></script>
<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>


<script type="text/javascript">
	$(function(){
		createMenus(4);
	});
	var uid='${galax_session_user.id }';
	
	/**
	 * 分页数据生成操作内容
	 */
	function editor(value, row, index){
		var id=row.id;
		var options = "<a href='#' class='blue' data-btn='myproject' onclick='info(" + id + ")'>项目流程</a>";
		return options;
	}
	function projectNameFormatter(val,row,index)
	{
		return '<a href="#" class="blue" onclick="showProjectDetail(\'' + row.id + '\')">'+val+'</a>';
	}
	function progressFormatter(val,row,index)
	{
		return '<a href="#" class="blue" onclick="info(\'' + row.id + '\')">'+val+'</a>'
	}
	function showProjectDetail(projectId)
	{
		window.location.href = platformUrl.projectDetail+projectId;
	}
	
	//全局变量
	var hasClosed=false;
	function info(id){
		alertid=id;
		var _url='<%=path%>/galaxy/sop';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".myprojecttc .tabtable").tabchange();
				$('.searchbox').toggleshow();
				leicj();
				/**
				 * 加载项目详情数据
				 */
				sendGetRequest(platformUrl.detailProject + id, {}, function(data){
					hasClosed = (data.entity.projectStatus == 'meetingResult:3');
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
								_projectId : $("#project_id").val()
						}
						fileGrid.init(data);
					});				
					$("#" + progress).addClass("on");
					$("#" + progress + "_con").css("display","block");
				},null);
			}
		});
		return false;
	}
	
	 /**
	  * 动态生成投资意向书阶段HTML
	  */
	function tzyxs(flag){
		 var pid = $("#project_id").val();
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
		 
		 var pid = $("#project_id").val();
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
		var pid = $("#project_id").val();
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
		var pid = $("#project_id").val();
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
</html>