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
<title>项目详情</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>

<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/sopinfo.js"></script>
</head>
<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
  	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin">
 		
 		<jsp:include page="sopcommon.jsp" flush="true"></jsp:include>
    	
        <div class="new_left">
        	<div class="tabtable assessment label_static">
          	<!-- tab标签 -->
            <jsp:include page="tab_header.jsp?index=5" flush="true"></jsp:include>
            <!-- 档案库信息 -->
            <div id="file_repository">
            	<c:if test="${aclViewProject==true or isThyy}">
            	<form id = 'file_repository_search_form'>
            	<div class="member document">
            	 <c:if test="${fx:isCreatedByUser('project',projectId) }">
                    <div class="top clearfix">
                        <!--按钮-->
                        <div class="btnbox_f btnbox_f1 btnbox_m clearfix">
							<a href="javascript:;" class="pubbtn bluebtn email_btn" id="file-show-mail-btn">发邮件给</a>
						</div>
                    </div>
                    </c:if>
                    <!-- 搜索条件 -->
                    <div class="min_document clearfix" >
                    <!-- 查询选项 -->
                      <div class="searchbox_document">
                        <div class="show_more" style="z-index:9;">
                          <a href="#" class="blue open ico1 f4" data-btn='show'>展开</a>
                          <a href="#" class="blue searchbox_hidden hide ico1 f3" data-btn='hide'>收起</a>
                        </div>
                        <div class="default">
                           <dl class="fmdl">
                             <dt>文件来源：</dt>
                             <dd><label for=""><input type="radio" name="search_fileSource" value="all" checked/>不限</label></dd>
                             <dd><label for=""><input type="radio" name="search_fileSource" value="1">内部</label></dd>
                             <dd><label for=""><input type="radio" name="search_fileSource" value="2">外部</label></dd>
                           </dl>                  
                           <dl class="fmdl">
                             <dt>业务分类：</dt>
                             <dd>
                               <select name="search_fileWorktype" id="search_file_worktype">
                                 <option value = "all">全部</option>  
                               </select>
                             </dd>
                           </dl>                  
                        </div>
                        <div class="searchbox_hidden" data-btn='box'>                 
                          <dl class="fmdl" id="search_file_status">
                            <dt>档案状态：</dt>
                          </dl>                  
                          <dl class="fmdl" id="search_file_type">
                            <dt>存储类型：</dt>
                          </dl>                  
                          <dl class="fmdl clearfix">
                            <dt>更新时间：</dt>
                            <dd>
								<input type="text" class="datepicker txt time" name="file_startDate" value="2016-01-01" /> 
								<span>至</span>
								<input type="text" class="datepicker txt time" name="file_endDate" value="2016-01-01" />
							</dd>
                            <dd>
								<a href="javascript:;" id="file_repository_btn" class="pubbtn bluebtn">查询</a>
							</dd>
                          </dl>
                        </div>
                              
                      </div>
                    </div>
     
                            <!--表格内容-->
                    <table width="100%" id="file_repository_table" cellspacing="0" cellpadding="0" class="commonsize"></table>  
        	</div>
        	</form>  
        	</c:if>
        </div>
            <!--tab end-->
          </div>
        </div>
        
        <!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>
        <!--右边 end-->
    </div>
 
</div>

<jsp:include page="../../common/uploadwin.jsp" flush="true"></jsp:include>
<jsp:include page="../../sopFile/projectDialog.jsp" flush="true"></jsp:include>
<jsp:include page="/galaxy/sopFile/showMailDialog" flush="true"></jsp:include>



<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>


<!-- 分页二css+四js -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- file -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>

<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>

<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script>

	var projectId = '${projectId}';
	var prograss = '${prograss}';
	var isTransfering = "${fx:isTransfering(projectId) }";
	createMenus(5);
    //搜索框显示隐藏
  $('.show_more a').on("click",function(){
    var $self=$(this),
        _name = $self.attr("data-btn"),
        _siblings = $self.siblings();
        //点击展开
          if(_name=="show"){
          _siblings.show();
          $self.hide();
          $self.parent().siblings("[data-btn='box']").show();
        };
        //点击隐藏
        if(_name=="hide"){
          _siblings.show();
          $self.hide();
          $self.parent().siblings("[data-btn='box']").hide();
        }
        return false;
  })
</script>

<script src="<%=path %>/js/teamSheetNew.js"></script>
<script src="<%=path %>/js/filerepository.js"></script>
<script src="<%=path %>/js/tabSopFile.js"></script>
</html>
