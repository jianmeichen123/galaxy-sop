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
    	<div class="new_tit_a"><a href="#" onclick="backIndex()">工作桌面</a>>创投项目</div>
    	 <input type="hidden" id="project_id" value=""/>
    	 <input type="hidden" id="uid" value=""/>
         <%-- <c:if test="${fx:hasRole(4)}"> --%>
         <!--页眉-->
         <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/project/addProject" class="pubbtn bluebtn ico c4" style="margin-top:10px;display:none" resource-mark="project_add">添加项目</a>
                <!-- <a href="编辑项目.html" class="pubbtn bluebtn ico c5">编辑</a> -->
            </div>
         </div>
         <%-- </c:if> --%>
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
                    <select name="financeStatus">
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
                  <dl class="fmdl fmdll clearfix">
                  <dt>团队成员:</dt>
                  <dd style="width:135px;">
                    <input type="text" class="txt" name="projectPerson" placeholder="请输入团队成员姓名" onkeyup="onkeyupall(this)" style="margin-left:0">
                  </dd>
                </dl>
                 <dl class="fmdl fml  fmdll clearfix">
              		<dt >来源于FA：</dt>
              		<dd class="clearfix">
		                <label><input type="radio" name="faFlag" value = "1"/>是</label>
		                <label><input type="radio" name="faFlag" value = "0"/>否</label>
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
                 <dl class="fmdl fml fmdll clearfix">
                  <dt>项目进度：</dt>
                  <dd>
                    <select name="projectProgress">
                      <option index="-1" value="">全部</option>
                    </select>
                  </dd>
                </dl>  
                <dl class="fmdl fmdll clearfix">
                	<input type="text" class="txt" name="nameCodeLike" placeholder="请输入项目名称或编号" style="margin-left:15px;">
	                <div class="btn fr">
	                    <button type="submit" class="bluebtn cx_prj" action="querySearch">搜索</button>
	                    <input type="hidden" value="0" id="showResetBtn">
	                    <button class="pubbtn bluebtn reset none" id="resetBtn">重置</button>
	                </div>
                </dl>
                
            </div>
            <div class="show_more">
                <a href="#" class="blue open ico1 f4" data-btn="show" style="display: block;">展开</a> <a href="#" class="blue searchbox_hidden hide ico1 f3" data-btn="hide" style="display: none;">收起</a>
            </div>
          </div>
        </div>
		<div class="tab-pane active ctlist" id="view">	
			<table id="project-table" data-url="project/search" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
			        	<th data-field="projectName"  class="data-input" data-formatter="projectInfo" data-width="16%">项目名称</th>
			        	<th data-field="project_type" data-formatter="typeFormat"  class="data-input sort" data-sortable="true" data-width="8%">项目类型<span></span></th>
			        	<th data-field="finance_status" data-formatter="financeStatusFormat"  class="data-input sort" data-sortable="true" data-width="5%">融资状态<span></span></th>
			        	<th data-field="project_progress" data-formatter="projectProgress"  class="data-input sort" data-sortable="true" data-width="15%">项目进度<span></span></th>
			        	<th data-field="project_status" data-formatter="projectStatusFormat"  class="data-input sort" data-sortable="true" data-width="5%">项目状态<span></span></th>
			        	<th data-field="faFlag" data-formatter="projectFaFormat"   data-width="6%">来源于FA<span></span></th>
			        	<th data-field="projectCareerline"  class="data-input" data-width="9%">事业部</th>
			        	<th data-field="createUname"  class="data-input" data-width="17%">投资经理</th>
			        	<th data-field="created_time" data-formatter="createdFormat"  class="data-input sort" data-sortable="true" data-width="8%">创建日期<span></span></th>
			        	<th data-field="updated_time" data-formatter="updateFormat"  class="data-input sort" data-sortable="true" data-width="5%">最后编辑时间<span></span></th>
         				<c:if test="${fx:hasRole(4)}">
			        	<th  class="col-md-2" data-formatter="editor" data-class="noborder" data-width="6%">操作</th>
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
	/**权限点**/
	if(isContainResourceByMark("project_add")){
	       $('a[resource-mark="project_add"]').css("display","block");
	}
	/**
	 * 分页数据生成操作内容
	 */
	var uid='${galax_session_user.id }';
	function editor(value, row, index){
		var id=row.id;
		var transferingIds = "${fx:getTransferingPids()}".split(",");
		if(uid == row.createUid)
		{
			var options = "<span class=\"prc\" data-btn='myproject' onclick='info(" + id + ")'>项目流程</span>";
			if(transferingIds.contains(id))
			{
				options = "<span class=\"prc limits_gray\" data-btn='myproject' title=\"项目移交中\"></span>";
			}
		}
		return options;
	}
	
	 function projectInfo(value,row,index){
		    var id=row.id;
			var str=row.projectName;
			if(str.length>10){
				subStr = str.substring(0,10);
				var options = "<a href='#' class='blue' data-btn='myproject' onclick='proInfo(" + id + ")' title='"+str+"'>"+subStr+"</a>";
				return options;
			}
			else{
				var options = "<a href='#' class='blue' data-btn='myproject' onclick='proInfo(" + id + ")' title='"+str+"'>"+str+"</a>";
				return options;
			}
		}
	
	function proInfo(id){
		//项目详情页返回地址
		setCookie("project_detail_back_path", Constants.sopEndpointURL + 'galaxy/mpl',6,'/');
		//返回附带参数功能代码
		var options = $("#project-table").bootstrapTable('getOptions');
		var tempPageSize = options.pageSize ? options.pageSize : 10;
		var tempPageNum = options.pageNumber ? options.pageNumber : 1;
		var projectType = $("select[name='projectType']").val();
		var financeStatus = $("select[name='financeStatus']").val();
		var projectProgress = $("select[name='projectProgress']").val();
		var projectStatus = $("select[name='projectStatus']").val();
		var projectDepartid = $("select[name='projectDepartid']").val();
		var createUid = $("select[name='createUid']").val();
		var nameCodeLike = $("input[name='nameCodeLike']").val();
		var projectPerson = $("input[name='projectPerson']").val();
		var faFlag = $("input[name='faFlag']:checked").val();
		
		var formdata = {
				_paramKey : 'projectList',
				_url : Constants.sopEndpointURL + "/galaxy/project/detail/" + id,
				_path : "/",
				_param : {
					pageNum : tempPageNum,
	        		pageSize : tempPageSize,
	        		projectType : projectType,
	        		financeStatus : financeStatus,
	        		projectProgress : projectProgress,
	        		projectStatus : projectStatus,
	        		projectDepartid : projectDepartid,
	        		createUid : createUid,
	        		nameCodeLike : nameCodeLike,
	        		projectPerson:projectPerson,
	        		faFlag:faFlag
				}
		}
		var href_url=window.location;
		setCookie("href_url", href_url,24,'/');
		cookieOperator.forwardPushCookie(formdata);
	}
	
	function refreshProjectList(){
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
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus");
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
    createUserOptions_All(platformUrl.getUserList+$('select[name="projectDepartid"]').val(), "createUid", 0);
	$(function(){
		//返回附带参数功能代码
		var initParams,
			pageParams=cookieOperator.getDataNoDelete({_paramKey : 'projectList',_path : "/"}),
			initPageSize = 10;
		
		if(typeof(pageParams) !== 'undefined' && pageParams.pageSize !=''){
			initPageSize = pageParams.pageSize;
		}
		$("button[action='querySearch']").click(function(){
			initParams = cookieOperator.pullCookie({_paramKey : 'projectList',_path : "/"});
		});
		/**
		 * 初始化项目列表
		 * @version 2016-06-21
		 */
		$('#project-table').bootstrapTable({
			queryParamsType: 'size|page',
			pageSize:initPageSize,
			showRefresh : false,
			url : $('#project-table').attr("data-url"),
			sidePagination: 'server',
			method : 'post',
			sortOrder : 'desc',
			sortName : 'updated_time',
			pagination: true,
	        search: false,
	        //返回附带参数功能代码
	        queryParams : function(param){
	        	if(getCookieValue("backProjectList")!=''){
	        		initParams = cookieOperator.pullCookie({_paramKey : 'projectList',_path : "/"});
	        		deleteCookie("backProjectList","/");
	        	}else{
	        		initParams=undefined;
	        	}
	        	if(typeof(initParams) !== 'undefined'){
	    			param.pageNum = initParams.pageNum - 1;
	        		param.pageSize = initParams.pageSize;
	        		if(initParams.projectType != ''){
	        			param.projectType = initParams.projectType;
	        			$("select[name='projectType']").val(initParams.projectType);
	        		}
	        		if(initParams.financeStatus != ''){
	        			param.financeStatus = initParams.financeStatus;
	        			$("select[name='financeStatus']").val(initParams.financeStatus);
	        		}
	        		if(initParams.projectProgress != ''){
	        			param.projectProgress = initParams.projectProgress;
	        			$("select[name='projectProgress']").val(initParams.projectProgress);
	        		}
	        		if(initParams.projectStatus != ''){
	        			param.projectStatus = initParams.projectStatus;
	        			$("select[name='projectStatus']").val(initParams.projectStatus);
	        		}
	        		param.projectDepartid = initParams.projectDepartid;
	        		$("select[name='projectDepartid']").val(initParams.projectDepartid);
	        		createUserOptions_All(platformUrl.getUserList+initParams.projectDepartid, "createUid", 1);
	        		param.createUid = initParams.createUid;
	        		$("select[name='createUid']").val(initParams.createUid);
	        		param.nameCodeLike = initParams.nameCodeLike;
	        		$("input[name='nameCodeLike']").val(initParams.nameCodeLike);
	        		param.projectPerson = initParams.projectPerson;
	        		$("input[name='projectPerson']").val(initParams.projectPerson);
	        		param.faFlag = initParams.faFlag;
	        		$("input[name='faFlag'][value='"+initParams.faFlag+"']").prop("checked",true);
	        		var options = $("#data-table").bootstrapTable('getOptions');
	 	        	options.pageNumber = initParams.pageNum - 1;
	    		}
	        	return param;
	        },
	        onLoadSuccess: function (data) {
	        	if($("#showResetBtn").val() == '1'){
	    			$("#resetBtn").removeClass("none");
	    		}
	        	
	        	
	        	if(typeof(initParams) !== 'undefined' && initParams.pageNum != ''){
	    			if(initParams.pageNum==1){
	    				return;
	    			}else{
	    				$('.pagination li').removeClass('active');
	    				if($('.pagination .page-number').length< initParams.pageNum)
	    				{
	    					var len = $('.pagination .page-number').length;
	    					var totalPages = $("#project-table").bootstrapTable('getOptions').totalPages;
	    					var end = initParams.pageNum + Math.floor(len/2);
	    					if(end>totalPages)
    						{
	    						end = totalPages;
    						}
	    					
	    					for(var i=len-1; i>=0; i--)
	    					{
	    						$('.pagination .page-number').eq(i).html('<a href="javascript:void(0)">'+ end-- +'</a>');
	    					}
	    				}

	    				$('.pagination li').each(function(){
	    	    			if($(this).text()==initParams.pageNum){
	    	    				$(this).click();
	    	    				 return false;
	    	    				//$(this).addClass('active')
	    	    			}
	    				});
	    			}
	    		}
	        	initPageSize=10;
	        }
		});
		/**
		 * 改变事业线时获取该事业线下的投资经理
		 * @version 2016-06-21
		 */
		$('select[name="projectDepartid"]').change(function(){
			var did = $('select[name="projectDepartid"]').val();
		    createUserOptions_All(platformUrl.getUserList+did, "createUid", 1);
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
			//set selected status
// 			$("select[name='projectType']").find("option[index='-1']").attr("selected",true);
// 			$("select[name='financeStatus']").find("option[index='-1']").attr("selected",true);
// 			$("select[name='projectProgress']").find("option[index='-1']").attr("selected",true);
// 			$("select[name='projectStatus']").find("option[index='-1']").attr("selected",true);
// 			$("select[name='projectDepartid']").find('option[back="link"]').attr("selected",true);
			$("select[name='projectType']").val("");
			$("select[name='financeStatus']").val("");
			$("select[name='projectProgress']").val("");
			$("select[name='projectStatus']").val("");
			$("select[name='projectDepartid']").val("");
			var did = $("select[name='projectDepartid']").find('option[back="link"]').val();
			
			if(typeof(did) == "undefined"){
				$("select[name='projectDepartid']").val(0);
				createUserOptions_All(platformUrl.getUserList+$('select[name="projectDepartid"]').val(), "createUid", 1);
				$("select[name='createUid']").val(0);
			}else{
				$("select[name='projectDepartid']").val(did);
// 				$("select[name='projectDepartid']").find('option[back="link"]').attr("selected",true);
				createUserOptions_All(platformUrl.getUserList+did, "createUid", 1);
				if(($("select[name='createUid']").find("option[value='"+userId+"']")).length == 0){
					$("select[name='createUid']").val(0);
				}else $("select[name='createUid']").val(userId);
				
			}
			$('input[name="nameCodeLike"]').val("");
			$('input[name="faFlag"]').removeAttr("checked");
			$('input[name="projectPerson"]').val("");
			$("#showResetBtn").val(0);
			$("#resetBtn").addClass("none");
		});
	});
	/**
	 * 面包屑
	 * @version 2016-06-21
	 */
	function backIndex(){
	    var url=Constants.sopEndpointURL+"/galaxy/redirect";
	    forwardWithHeader(url);
	}
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
	 * 项目FA格式化
	 * @version 2016-06-21
	 */
	function projectFaFormat(value,row,index){
		var retStr = '-';
		if(row.faFlag=='1'){
			retStr = "是";
		}else if(row.faFlag=='0'){
			retStr = '否';
		}
		return retStr;
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
		if(pronum == 0 || pronum == 1){
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
	function onkeyupall(ele){
		var s = ele.value
		if(s.length>20){
			ele.value =s.substr(0, 20)
		}
		
	}
</script>
</html>
