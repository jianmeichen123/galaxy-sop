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
<title>繁星</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>

<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


	<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin prj_all">
    	<div class="new_tit_a"><a href="#">工作桌面</a>>创投项目</div>
    	 <input type="hidden" id="project_id" value=""/>
    	 <input type="hidden" id="uid" value=""/>
         <c:if test="${fx:hasRole(4)}">
         <!--页眉-->
         <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/app" class="pubbtn bluebtn ico c4">添加项目</a>
                <!-- <a href="编辑项目.html" class="pubbtn bluebtn ico c5">编辑</a> -->
            </div>
         </div>
         </c:if>
         <!--tips连接
          <ul class="tipslink tablink">
                <li class="on"><a href="javascript:;" query-by="proType" query-val="1" >我的项目<span></span></a></li>
                <li><a href="javascript:;"  query-by="proType" query-val="2">事业线项目<span></span></a></li>
          </ul>-->
        <!-- 搜索条件 -->
		<div class="top clearfix" id="custom-toolbar">
          <div class="searchall_prj clearfix">
            <div class="searchall_top" data-btn="box">
                <dl class="fmdl fml fmdll clearfix">
                  <dt>项目类型：</dt>
                  <dd>
                    <select name="projectType">
                      <option index="-1" value="">全部</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fml fmdll clearfix">
                  <dt>融资状态：</dt>
                  <dd>
                    <select name="financeStatusQuary">
                      <option index="-1" value="">全部</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fml fmdll clearfix">
                  <dt>项目进度：</dt>
                  <dd>
                    <select name="projectProgress">
                      <option index="-1" value="">全部</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fml fmdll clearfix">
                  <dt>项目状态：</dt>
                  <dd>
                    <select name="projectStatus">
                      <option index="-1" value="">全部</option>
                    </select>
                  </dd>
                </dl>
            </div>
            <div class="searchall_bottom clearfix">
                <dl class="fmdl fml fmdll clearfix">
                  <dt>事业部：</dt>
                  <dd>
                    <select name="projectDepartid">
                      <option value="0">全部</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fml fmdll clearfix">
                  <dt>投资经理：</dt>
                  <dd>
                    <select name="createUid">
                      <option value="0">全部</option>
                    </select>
                  </dd>
                </dl>
                <input type="text" class="txt" name="nameCodeLike" placeholder="请输入项目名称或编号">
                <div class="btn fr">
                    <button type="submit" class="bluebtn cx_prj" action="querySearch">搜索</button>
                    <input type="hidden" value="0" id="showResetBtn">
                    <button class="pubbtn bluebtn reset none" id="resetBtn">重置</button>
                </div>
            </div>
            <div class="show_more">
                <a href="#" class="blue open ico1 f4" data-btn="show" style="display: block;">展开</a> <a href="#" class="blue searchbox_hidden hide ico1 f3" data-btn="hide" style="display: none;">收起</a>
            </div>
          </div>
        </div>
		<div class="tab-pane active" id="view">	
			<table id="project-table" data-url="project/search" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
			        	<th data-field="projectName" data-align="left" class="data-input" data-formatter="projectInfo" data-width="16%">项目名称</th>
			        	<th data-field="project_type" data-formatter="typeFormat" data-align="left" class="data-input sort" data-sortable="true" data-width="8%">项目类型<span></span></th>
			        	<th data-field="finance_status" data-formatter="financeStatusFormat" data-align="left" class="data-input sort" data-sortable="true" data-width="8%">融资状态<span></span></th>
			        	<th data-field="project_progress" data-formatter="projectProgress" data-align="left" class="data-input sort" data-sortable="true" data-width="12%">项目进度<span></span></th>
			        	<th data-field="project_status" data-formatter="projectStatusFormat" data-align="left" class="data-input sort" data-sortable="true" data-width="8%">项目状态<span></span></th>
			        	<th data-field="projectCareerline" data-align="left" class="data-input" data-width="9%">事业部</th>
			        	<th data-field="createUname" data-align="left" class="data-input" data-width="14%">投资经理</th>
			        	<th data-field="created_time" data-formatter="createdFormat" data-align="left" class="data-input sort" data-sortable="true" data-width="8%">创建日期<span></span></th>
			        	<th data-field="updated_time" data-formatter="updateFormat" data-align="left" class="data-input sort" data-sortable="true" data-width="8%">最后编辑时间<span></span></th>
         				<c:if test="${fx:hasRole(4)}">
			        	<th data-align="left" class="col-md-2" data-formatter="editor" data-class="noborder" data-width="8%">操作</th>
 						</c:if>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
</div>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<jsp:include page="/galaxy/sopFile/showMailDialog" flush="true"></jsp:include>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=request.getContextPath() %>/js/operationMessage.js" type="text/javascript"></script>
<script id="a" src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/fx.upload.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/my_ext.js"></script>
<script src="<%=path %>/js/my.js"></script>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>

<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>




<script type="text/javascript" src="<%=path %>/js/teamSheetNew.js"></script>
<script type="text/javascript" src="<%=path %>/js/filerepository.js"></script>

<script type="text/javascript" src="<%=path %>/js/sop.js"></script>

<script type="text/javascript">
	createMenus(5);
	/**
	 * 分页数据生成操作内容
	 */
	var uid='${galax_session_user.id }';
	function editor(value, row, index){
		var id=row.id;
		if(uid == row.createUid){
			var options = "<a href='#' class='blue' data-btn='myproject' onclick='info(" + id + ")'><span class=\"prc\">项目流程</span></a>";
		}
		return options;
	}
	
	 function projectInfo(value,row,index){
		    var id=row.id;
			var str=row.projectName;
			if(str.length>12){
				subStr = str.substring(0,12);
				var options = "<a href='#' class='blue' data-btn='myproject' onclick='proInfo(" + id + ")' title='"+str+"'>"+subStr+"</a>";
				return options;
			}
			else{
				var options = "<a href='#' class='blue' data-btn='myproject' onclick='proInfo(" + id + ")' title='"+str+"'>"+str+"</a>";
				return options;
			}
		}
	
	function proInfo(id){
		var options = $("#project-table").bootstrapTable('getOptions');
		var tempPageSize = options.pageSize ? options.pageSize : 10;
		var tempPageNum = options.pageNumber ? options.pageNumber : 1;
		
		var nameCodeLike = $("input[name='nameCodeLike']").val();
		var projectDepartid = $("select[name='projectDepartid']").val();
		var createUid = $("select[name='createUid']").val();
		
		
		var formdata = {
				_paramKey : 'projectList',
				_url : Constants.sopEndpointURL + "/galaxy/project/detail/" + id,
				_param : {
					pageNum : tempPageNum,
	        		pageSize : tempPageSize,
	        		nameCodeLike : nameCodeLike,
	        		createUid : createUid,
	        		projectDepartid : projectDepartid
				}
		}
		cookieOperator.forwardPushCookie(formdata);
	}
	
	function refreshProjectList()
	{
		$("#project-table").bootstrapTable('refresh');
	}
	
	$(function(){
		var pid = "${pid}";
		if(!(!pid)){
			info(pid);
		}	
	});
	
	
	
	
	
	
	
	
	/**
	 * 获取融资状态下拉项
	 * @version 2016-06-21
	 */
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatusQuary");
	/**
	 * 获取项目类型下拉项
	 * @version 2016-06-21
	 */
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"projectType","projectType");
	/**
	 * 获取项目进度下拉项
	 * @version 2016-06-21
	 */
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"projectProgress","projectProgress");
	/**
	 * 获取项目状态下拉项
	 * @version 2016-06-21
	 */
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"projectStatus","projectStatus");
	/**
	 * 查询事业线
	 * @version 2016-06-21
	 */
	createCareelineOptions(platformUrl.getCareerlineList,"projectDepartid");
	/**
	 * 根据事业线查询相应的投资经理
	 * @version 2016-06-21
	 */
    createUserOptions(platformUrl.getUserList+$('select[name="projectDepartid"]').val(), "createUid", 0);
	$(function(){
		var num_size='';
		/**
		 * 初始化项目列表
		 * @version 2016-06-21
		 */
		$('#project-table').bootstrapTable({
			queryParamsType: 'size|page',
			pageSize:10,
			showRefresh : false ,
			url : $('#project-table').attr("data-url"),
			sidePagination: 'server',
			method : 'post',
			sortOrder : 'desc',
			sortName : 'updated_time',
			pagination: true,
	        search: false,
	        queryParams : function(param){
	        	var backSign = ${backSign}
	        	
	        	if(backSign){
	        		var formdata = {
		        			_paramKey : 'projectList'
		        	}
		        	var tempParam = cookieOperator.pullCookie(formdata);
		        	if(tempParam){
		        		param.pageNum = tempParam.pageNum - 1;
		        		param.pageSize = tempParam.pageSize;
		        		param.nameCodeLike = tempParam.nameCodeLike;
		        		param.createUid = tempParam.createUid;
		        		param.projectDepartid = tempParam.projectDepartid;
		        		var options = $("#project-table").bootstrapTable('getOptions');
		        		num_size =tempParam.pageNum;
	 	        		options.pageNumber = tempParam.pageNum - 1;
	 	        		console.log('options.pageNumber ='+options.pageNumber );
	 	        		//给搜索表单赋值
	 	        		$("input[name='nameCodeLike']").val(tempParam.nameCodeLike ? tempParam.nameCodeLike : "");
	 	       			$("select[name='projectDepartid']").val(tempParam.projectDepartid ? tempParam.projectDepartid : "0");
	 	       			$("select[name='createUid']").val(tempParam.createUid ? tempParam.createUid : "0");
		        	}
	        	}
	        	
	        	return param;
	        },
	        onLoadSuccess: function (data) {
	        	if($("#showResetBtn").val() == '1'){
	    			$("#resetBtn").removeClass("none");
	    		}
	        	if(num_size!=''){
	        		$('.pagination li').removeClass('active');
	        		$('.pagination li').each(function(){
	        			if($(this).text()==num_size){
	        				$(this).addClass('active')
	        			}
	        		})
	        		num_size='';
	        	}
	        }
		});
		/**
		 * 改变事业线时获取该事业线下的投资经理
		 * @version 2016-06-21
		 */
		$('select[name="projectDepartid"]').change(function(){
			var did = $('select[name="projectDepartid"]').val();
		    createUserOptions(platformUrl.getUserList+did, "createUid", 1);
		});
		/**
		 * 控制"重置"按钮
		 */
		$('button[action="querySearch"]').click(function(){
			$("#showResetBtn").val(1);
		});
		/**
		 * "重置"操作
		 */
		$("#resetBtn").click(function(){
			//clean selected status
			$("select[name='projectType']").find("option[index='-1']").removeAttr("selected");
			$("select[name='financeStatus']").find("option[index='-1']").removeAttr("selected");
			$("select[name='projectProgress']").find("option[index='-1']").removeAttr("selected");
			$("select[name='projectStatus']").find("option[index='-1']").removeAttr("selected");
			$("select[name='projectDepartid']").find('option[back="link"]').removeAttr("selected");
			//set selected status
			$("select[name='projectType']").find("option[index='-1']").attr("selected",true);
			$("select[name='financeStatus']").find("option[index='-1']").attr("selected",true);
			$("select[name='projectProgress']").find("option[index='-1']").attr("selected",true);
			$("select[name='projectStatus']").find("option[index='-1']").attr("selected",true);
			$("select[name='projectDepartid']").find('option[back="link"]').attr("selected",true);
			var did = $("select[name='projectDepartid']").find('option[back="link"]').val();
			createUserOptions(platformUrl.getUserList+did, "createUid", 1);
			$('input[name="nameCodeLike"]').val("");
			$("#resetBtn").addClass("none");
		});
	});
	/**
	 * 创建时间格式化
	 * @version 2016-06-21
	 */
	function createdFormat(value,row,index){
		return row.createDate;
	}
	/**
	 * 更新时间格式化
	 * @version 2016-06-21
	 */
	function updateFormat(value,row,index){
		return row.updateDate;
	}
	/**
	 * 项目状态格式化
	 * @version 2016-06-21
	 */
	function projectStatusFormat(value,row,index){
		return row.projectStatusDs;
	}
	/**
	 * 项目进度格式化
	 * @version 2016-06-21
	 */
	 function projectProgress(value,row,index){
		var projectPro = row.projectProgress;
		var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);
		var proStatus = row.projectStatus;
		var pronum = proStatus.substring(proStatus.lastIndexOf(":")+1,proStatus.length);
		if( pronum == 0){
			return "<img src=\"<%=path%>/img/process/p"+num+".gif\" class=\"fl\">"+row.progress;
		}else{
			return "<img src=\"<%=path%>/img/process/pd"+num+".gif\" class=\"fl\">"+row.progress;
		}
	}
	/**
	 * 融资状态格式化
	 * @version 2016-06-21
	 */
	function financeStatusFormat(value,row,index){
		return row.financeStatusDs;
	}
	/**
	 * 项目类型格式化
	 * @version 2016-06-21
	 */
	function typeFormat(value,row,index){
		return row.type;
	}
</script>

</html>
