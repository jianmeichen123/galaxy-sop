<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>繁星</title>
	<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
	<link rel="stylesheet" href="<%=path %>/css/bootstrap.min-v3.3.5.css"  type="text/css">
	<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<!-- jsp文件头和头部 -->
	<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
	<!-- 富文本编辑器 -->
	<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
    <script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script src="<%=path %>/js/init.js"></script>
	<style type="text/css">
		.content{
			float: left;
			padding:0 10px;
		}
	</style>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	 <!--右中部内容-->
 	<div class="ritmin">
    <h2>项目详情</h2>
      <!-- 面包屑
      <ul class="breadcrumb">
        <li><a href="javascript:;" class="bcfirst">待办任务</a></li>
        <li><span>&gt;</span><a href="javascript:;">项目基本信息</a></li>
        <li><span>&gt;</span><a href="javascript:;" class="active">项目详情信息</a></li>
      </ul> -->
      <div class="clearfix"></div>
        <!--项目详细信息内容-->
        <div class="projectmsg_d clearfix">
          <h2 id="project_name"></h2>
          <form action="" method="post" id="update_form_basic">
          <input type="hidden" id="pid" name="id" value="${requestScope.pid}"/>
          <!-- 第1部分 -->
          <div class="block block1">
            <table width="100%" cellspacing="5" cellpadding="0" >
             <tbody>
                  <tr>
                      <td><dl><dt>项目编码：</dt><dd id="project_code"></dd></dl></td>
                      <td><dl><dt>创建时间：</dt><dd id="create_date"></dd></dl></td>
                  </tr>
                  <tr>
                      <td><dl><dt>项目名称：</dt><dd id="projectName"></dd></dl></td>
                      <td><dl><dt>项目类型：</dt><dd id="projectType"></dd></dl></td>
                  </tr>
                  <tr><td><dl><dt>计划额度：</dt>
                          <dd><dd id="project_contribution"></dd></dd>
                        </dl></td>
                      <td><dl><dt>估值：</dt><dd id="project_valuations"></dd></dl></td>
                  </tr>
                  <tr>
                      <td><dl><dt>出让股份：</dt>
                          <dd><dd id="project_share_ratio"></dd></dd>
                        </dl></td>
                      <td>
                        <dl>
                          <dt>单位（万）：</dt>
                          <dd id="currencyUnit">
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>公司名称：</dt>
                          <dd id="project_company"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>组织机构代码：</dt>
                          <dd id="project_company_code"></dd>
                        </dl>
                      </td>
                  </tr>                  
                </tbody>
              </table>
              
              <div class="btnbox">   <!-- onclick="history.go(-1);location.reload()" -->
                <a href="javascript:;" onclick="history.go(-1);" class="bluebtn pubbtn">返回</a>
          </div>
          </div>
          
          
          </form>
          
          <!-- 商业计划  -->      
          <div class="block block2 shadow">
            <dl>
              <dt>商业计划书</dt>
               <dd id="business_plan_dd" class="fctbox">
<!--                 <a href="javascript:;" class="ico f1" data-btn="upload" onclick="uploadBusinessPlan()" >更新</a> -->
<!--                 <a href="javascript:;" class="ico f1" data-btn="download" onclick="downloadBusinessPlan()" >下载</a> -->
              </dd>
            </dl>
          </div> 
          
          <!-- 第2部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>项目概述</dt>
              <dd class="edit">
              	  <script type="text/plain" id="describe_editor" style="width:100%;height:100px;">
				  </script>
			  </dd>
			  <dd class="describe pro_block" id="describe_show"></dd>
              <!-- <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd> -->
            </dl>
          </div>
          <!-- 第3部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>商业模式</dt>
              <dd class="edit">
              	  <script type="text/plain" id="business_model_editor" style="width:100%;height:100px;">
				  </script>
		      </dd>
		      <dd class="describe pro_block" id="model_show"></dd>
              <!-- <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd> -->
            </dl>
          </div>
          <!-- 第4部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>公司定位</dt>
              <dd class="edit">
			      <script type="text/plain" id="location_editor" style="width:100%;height:100px;">
				  </script>
			  </dd>
			  <dd class="describe pro_block" id="location_show"></dd>
             <!--  <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd> -->
            </dl>
          </div>
          <!-- 第5部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>用户分析</dt>
              <dd class="edit">
			  	  <script type="text/plain" id="portrait_editor" style="width:100%;height:100px;">
				  </script>
			  </dd>
			   <dd class="describe pro_block" id="portrait_show"></dd>
              <!-- <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd> -->
            </dl>
          </div>
          <!-- 第6部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>竞情分析</dt>
              <dd class="edit">
			      <script type="text/plain" id="analysis_editor" style="width:100%;height:100px;">
				  </script>
			  </dd>
			  <dd class="describe pro_block" id="analysis_show"></dd>
              <!-- <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd> -->
            </dl>
          </div>
          <!-- 第7部分 -->
          <div class="block block2 shadow clearfix">
            <dl>
              <dt>团队成员</dt>
              <dd class="full_w describe clearfix pro_block">
                <div class="clearfix"></div>
                <div class="tab-pane active" id="view">	
	               	<table id="tablePerson"  data-height="555" 
	               	data-method="post" data-show-refresh="true" >
					</table> 
				</div>
				
              </dd>
              <!-- <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd> -->
            </dl>
          </div> 
          <!-- 第8部分  -->      
          <div class="block block2 shadow">
            <dl>
              <dt>股权结构</dt>
              <dd class="full_w describe clearfix pro_block">
                <div class="clearfix"></div>
                  <div class="tab-pane active" id="pView">	
	               <table id="table" data-height="555" data-method="post"
	               	 data-show-refresh="true">
					</table>
				</div>
              </dd>
              <!-- <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd> -->
            </dl>
          </div> 
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=path %>/js/init.js"></script>
<!-- bootstrap-table -->
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=request.getContextPath() %>/js/axure.js"></script>
<script>
var d_scrollTop=0;
	$(function(){
		createMenus(4);
		/**
		 * 加载项目详情数据
		 */
		sendGetRequest(platformUrl.detailProject + pid, {}, function(data){
			$("#project_name").text(data.entity.projectName);
			$("#project_code").text(data.entity.projectCode);
			$("#create_date").text(data.entity.createDate);
			$("#projectName").text(data.entity.projectName);
			$("#projectType").text(data.entity.type);
			$("#project_contribution").text(data.entity.projectContribution);
			$("#project_valuations").text(data.entity.projectValuations);
			if(data.entity.projectShareRatio == undefined || data.entity.projectShareRatio == 'undefined' 
					||  data.entity.projectShareRatio==null ||  data.entity.projectShareRatio ==''){
				$("#project_share_ratio").text("");
			}else{ 
				$("#project_share_ratio").text(data.entity.projectShareRatio+"%");
			}
			var currencyUnit = "";
			if(data.entity.currencyUnit == 0){
				currencyUnit = "人民币"
			}else if(data.entity.currencyUnit == 1){
				currencyUnit="美元";
			}else if(data.entity.currencyUnit == 2){
				currencyUnit="英镑";
			}else if(data.entity.currencyUnit == 3){
				currencyUnit="欧元";
			}
			$("#currencyUnit").text(currencyUnit);
			$("#project_company").text(data.entity.projectCompany);
			$("#project_company_code").text(data.entity.projectCompanyCode);
			$("#describe_show").html(data.entity.projectDescribe==null?"暂无项目概述":data.entity.projectDescribe);
			$("#model_show").html(data.entity.projectBusinessModel==null?"暂无商业模式":data.entity.projectBusinessModel);
			$("#portrait_show").html(data.entity.userPortrait==null?"暂无用户分析":data.entity.userPortrait);
			$("#location_show").html(data.entity.companyLocation==null?"暂无公司定位":data.entity.companyLocation);
			$("#analysis_show ").html(data.entity.prospectAnalysis==null?"暂无竞情分析":data.entity.prospectAnalysis);
			var um = UM.getEditor('describe_editor');
			if(data.entity.projectDescribe){
				um.setContent(data.entity.projectDescribe);
				describe_show();
			}else{
				um.setContent("");
			}
			var um = UM.getEditor('business_model_editor');
			if(data.entity.projectBusinessModel){
				um.setContent(data.entity.projectBusinessModel);
				model_show();
			}else{
				um.setContent("");
			}
			var um = UM.getEditor('location_editor');
			if(data.entity.companyLocation){
				um.setContent(data.entity.companyLocation);
				location_show();
			}else{
				um.setContent("");
			}
			
			
			
			var um = UM.getEditor('portrait_editor');
			if(data.entity.userPortrait){
				um.setContent(data.entity.userPortrait);
				portrait_show();
			}else{
				um.setContent("");
			}
			
			var um = UM.getEditor('analysis_editor');
			if(data.entity.prospectAnalysis){
				um.setContent(data.entity.prospectAnalysis);
				analysis_show();
			}else{
				um.setContent("");
			}
			
			
			
			sendGetRequest(platformUrl.getBusinessPlanFile+"/"+pid,null,function(data){
				var uploadOperator;
				var html;
				if(data.result.status=="OK"){
					//为空时候显示
					if(data.result.errorCode=="null"){				
						html = "<span class='content'>状态：未上传</span><span class='content'>更新时间：无</span>";
					}else{
						html =  "<span class='content'>状态：已上传</span><span class='content'>更新时间："+data.entity.createDate+"</span><a href='javascript:;' class='ico f1' data-btn='download' onclick='downloadBusinessPlan(" + data.entity.id +")' >下载</a>";
					}
					$("#business_plan_dd").html(html);
				}else{
					
				}
			});
			
			
			
			
		});
	});

	
	
	function downloadBusinessPlan(id){
		window.location.href=platformUrl.downLoadFile+'/'+id ;
	}
	
    var pid='${requestScope.pid}';
	function formatGender(index, row) {
		if (row.gender == true) {
			return "男";
		} else {
			return "女";
		}
	}
	   
	getTabPerson();
	getTabShare();
	function describe_show(){
		//d_scrollTop();
		var box = document.getElementById("describe_show");  
		var text = box.innerHTML.replace(/<[^>]+>/g,"");  //去掉所有的html标记
		var newBox = document.createElement("span");  
		var btn = document.createElement("a");  
		newBox.innerHTML = text.substring(0,300);  
		btn.innerHTML = text.length >300 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
			AAAscrollTop();
			
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情"; 
		AAAscrollTop();
		newBox.innerHTML = text.substring(0,300)+"...";  
		}  
		}  
		box.innerHTML = "";  
		box.appendChild(newBox);  
		box.appendChild(btn);
		
		
		
	}
	function AAAscrollTop(){
		d_scrollTop=$(document).scrollTop();
		$("html,body").animate({scrollTop:d_scrollTop},10);
	}
	function model_show(){
		var box = document.getElementById("model_show");  
		var text = box.innerHTML.replace(/<[^>]+>/g,"");  //去掉所有的html标记
		var newBox = document.createElement("span");  
		var btn = document.createElement("a");  
		newBox.innerHTML = text.substring(0,300);  
		btn.innerHTML = text.length >300 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
			AAAscrollTop();
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情"; 
		AAAscrollTop();
		newBox.innerHTML = text.substring(0,300);  
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
		newBox.innerHTML = text.substring(0,300);  
		btn.innerHTML = text.length >300 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
			AAAscrollTop()
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情";  
		AAAscrollTop();
		newBox.innerHTML = text.substring(0,300);  
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
		newBox.innerHTML = text.substring(0,300);  
		btn.innerHTML = text.length >300 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
			AAAscrollTop();
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text; 
		}else{  
		btn.innerHTML = "查看详情";  
		AAAscrollTop();
		newBox.innerHTML = text.substring(0,300);  
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
		newBox.innerHTML = text.substring(0,300);  
		btn.innerHTML = text.length >300 ? "查看详情" : "";  
		btn.href = "#"; 
		btn.className="see_detail blue" ;
		btn.onclick = function(){  
		if (btn.innerHTML == "查看详情"){  
			AAAscrollTop()
		btn.innerHTML = "收起"; 
		newBox.innerHTML = text;  
		}else{  
		btn.innerHTML = "查看详情";  
		AAAscrollTop()
		newBox.innerHTML = text.substring(0,300);  
		}  
		}  
		box.innerHTML = "";  
		box.appendChild(newBox);  
		box.appendChild(btn);  		
	}
	
	function getTabPerson(){
		var html='<table id="tablePerson"  data-height="555" data-method="post" data-show-refresh="true" ></table>';
		$("#view").html(html);
		var $table = $('#tablePerson');
	    $table.bootstrapTable({
	    url: platformUrl.projectPersonList,
	    dataType: "json",
	    pagination: true, //分页
	    search: false, //显示搜索框
	    pageList: [1,5,20],
	    queryParams: queryParams,
	    sidePagination: "server", //服务端处理分页
	          columns: [
	                  {
	                    title: '姓名',
	                      field: 'personName',
	                      align: 'center',
	                      valign: 'middle'
	                  },
	                  {
                        title: '性别',
                        field: 'personSex',
                        align: 'center',
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
	                          align: 'center',
	                          valign: 'middle'
	                     },
	                     {
	                          title: '当前职务',
	                            field: 'personDuties',
	                            align: 'center',
	                            valign: 'middle'
	                  },
	                  {
	                      title: '电话',
	                        field: 'personTelephone',
	                        align: 'center',
	                        valign: 'middle'
	                  },
	                  {
	                      title: '最高学历',
	                        field: 'highestDegree',
	                        align: 'center',
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
	                        align: 'center',
	                        valign: 'middle'
	                  }
	              ]
	      });
	      $table.bootstrapTable('refresh');
		}
	//股权结构列表
	function getTabShare(){
	var html='<table id="table" data-height="555" data-method="post" data-show-refresh="true"></table>';
	$("#pView").html(html);
	var $table = $('#table');
    $table.bootstrapTable({
    url: platformUrl.projectSharesList,  
    dataType: "json",
    pagination: true, //分页
    search: false, //显示搜索框
    showRefresh: true,
    pageList: [1,5,20],
    queryParams: queryParams,
    sidePagination: "server", //服务端处理分页
          columns: [
                  {
                    title: '类型',
                      field: 'sharesType',
                      align: 'center',
                      valign: 'middle'
                  },
                  {
                      title: '所有权人',
                        field: 'sharesOwner',
                        align: 'center',
                        valign: 'middle'
                    },
                    {
                        title: '占比(%)',
                          field: 'sharesRatio',
                          align: 'center',
                          valign: 'middle'
                     },
                     {
                          title: '获取方式',
                            field: 'gainMode',
                            align: 'center',
                            valign: 'middle'
                  },
                  {
                      title: '备注',
                        field: 'remark',
                        align: 'center',
                        valign: 'middle'
                  }
              ]
      });
      $table.bootstrapTable('refresh');
	}
    //页面传参
    function queryParams(params) {
    	return {
	    	pageSize: params.limit,
	    	pageNum: params.offset,
	    	order: params.order,
	    	projectId:pid
    	};
    }
</script>	   
</html>
