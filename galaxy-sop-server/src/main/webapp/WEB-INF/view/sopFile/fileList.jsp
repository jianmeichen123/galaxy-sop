<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
	<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<!-- jsp文件头和头部 -->
	<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%-- 	<link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css"  type="text/css"> --%>
	<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<%-- 	<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css"> --%>

	
	<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <script src="<%=path %>/js/init.js"></script>
<%--     <script src="<%=path %>/js/jquery.showLoading.min.js"></script> --%>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>项目文档</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="javascript:;" class="pubbtn bluebtn ico c3" id="show-mail-btn">发邮件给</a>
            </div>
        </div>	
        <!-- 搜索条件 -->
        <div class="min_document clearfix min_document_da"  id="custom-toolbar">
          <div class="top clearfix search_adjust1 searchall">
            <dl class="fmdl fml  fmdll clearfix">
              <dt >&nbsp;&nbsp;&nbsp;档案来源：</dt>
              <dd class="clearfix">
				<!--name="fileSource" -->
                <label><input type="radio" name="source" value = "all" checked="checked"/>不限</label>
                <label><input type="radio" name="source" value = "1"/>内部</label>
                <label><input type="radio" name="source" value = "2"/>外部</label>
            </dd>
          </dl>
          <dl class="fmdl fmdll clearfix">
            <dt>存储类型：</dt>
            <dd class="clearfix">
				<!--name = "fileType"  -->
                <select id="searchFileType">
                	<option>全部</option>
              	</select>
            </dd>
          </dl> 
          <dl class="fmdl fml fmdll clearfix">
            <dt>业务分类：</dt>
            <dd>
			  <!--name = "fileWorktype"  -->
              <select id="searchFileWorktype">
                <option>全部</option>
              </select>
            </dd>
          </dl>
          <dl class="fmdl fml fmdll clearfix" id="srearch_careerline_div">
            <dt>所属部门：</dt>
            <dd>
			  <!--name="careerLine" -->
              <select id="searchCareerLine">
                <option>全部</option>
              </select>
            </dd>
          </dl>
          <dl class="fmdl fmdll clearfix">
            <dt></dt>
            <dd>
              <input type="text" id="searchProjectId" class="txt" placeholder="请输入项目名称或投资经理姓名" />
            </dd>
            <dd>
            <button type="button" class="bluebtn ico cx"   id="searchBtn">搜索</button>
            </dd>
          </dl>         
        </div>
        </div>
       <div class="tab-pane active" id="view">		
			<table id="fileGrid"></table>
           </div>      
    </div>
</div>

<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<jsp:include page="../sopFile/projectDialog.jsp" flush="true"></jsp:include>
<jsp:include page="/galaxy/sopFile/showMailDialog" flush="true"></jsp:include>



<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
</body>
<script type="text/javascript">
var transferingIds = "${fx:getTransferingPids()}".split(",");
</script>


<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
 <script src="<%=path %>/js/commWin.js" type="text/javascript"></script>
 <script src="<%=path %>/js/teamSheetNew.js" type="text/javascript"></script>
<%--  <script src="<%=path %>/js/teamSheet.js" type="text/javascript"></script> --%>
 <script src="<%=path %>/js/sopFile.js" type="text/javascript"></script>
 
	



</html>

