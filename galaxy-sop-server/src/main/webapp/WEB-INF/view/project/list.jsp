<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星SOP-添加项目</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/my_ext.js"></script>
<script src="<%=path %>/js/my.js"></script>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>我的项目</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="添加项目.html" class="pubbtn bluebtn ico c4">添加项目</a>
                <a href="编辑项目.html" class="pubbtn bluebtn ico c5">编辑</a>
            </div>
        </div>
        <!-- 搜索条件 -->
		<div class="min_document clearfix">
			<div class="bottom searchall clearfix" id="custom-toolbar">
				<dl class="fmdl fml fmdll clearfix">
	              <dt>项目类别：</dt>
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
	                  <!-- <option value="projectProgress:10">投后运营</option> -->
	                </select>
	              </dd>
	            </dl>
				<dl class="fmdl fmdll clearfix">
					<dt></dt>
					<dd>
						<input type="text" class="txt" id="search_text" placeholder="请输入姓名或手机号" />
					</dd>
					<dd>
						<button type="submit" class="bluebtn ico cx" name="querySearch">搜索</button>
					</dd>
				</dl>
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<input type="hidden" id="project_id" value="">	
			<table id="data-table" data-url="project/spl" data-height="555" 
				data-method="post" data-show-refresh="true" 
				data-side-pagination="server" data-pagination="true" 
				data-page-list="[1, 5, 50]" data-search="false">
				<thead>
				    <tr>
				    	<th data-field="projectCode" data-align="center" class="data-input">项目编码</th>
			        	<th data-field="projectName" data-align="center" class="data-input">项目名称</th>
			        	<th data-field="progress" data-align="center" class="data-input">项目进度</th>
			        	<th data-field="type" data-align="center" class="data-input">项目类型</th>
			        	<th data-field="createDate" data-align="center" class="data-input">创建日期</th>
			        	<th data-align="center" class="col-md-2" data-formatter="editor">操作</th>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script type="text/javascript">
	createMenus(5);
	function editor(value, row, index){
		var id=row.id;
		var options = "<a href='#' data-btn='myproject' onclick='info(" + id + ")'>查看</a>";
		return options;
	}
	
	function info(id){
		var _url='<%=path%>/galaxy/ips';
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
					$("#project_name").text(data.entity.projectName);
					var progress = data.entity.projectProgress;
					progress = progress.replace(":","_");
					var index = progress.substr(progress.length-1,1);
					for(var i = 1; i<10; i++){
						if(i > index){
							$("#projectProgress_" + i).addClass("disabled");
						}
					}
					$("#" + progress).addClass("on");
					$("#" + progress + "_con").css("display","block");
				},null);
				
				//
				
			}
		});
		$("#project_id").val(id);
		return false;
	}
	
	function air(id){
		var _url='<%=path%>/galaxy/air';
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".meetingtc").tabchange();
				$('.searchbox').toggleshow();
				leicj();
			}
		});
		return false;
	}
	
</script>
</html>