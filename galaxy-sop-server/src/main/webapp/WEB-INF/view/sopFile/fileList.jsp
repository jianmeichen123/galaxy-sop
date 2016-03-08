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
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<!-- jsp文件头和头部 -->
	<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>档案管理</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a class="pubbtn bluebtn ico c1" href="javascript:;" id="uploadOpenBtn">档案上传</a>
                <a href="javascript:;" class="pubbtn bluebtn ico c2" id = "openBtn">档案更新</a>
                <a href="javascript:;" class="pubbtn bluebtn ico c3" >发邮件给</a>
            </div>
        </div>
        <!-- 搜索条件 -->
        <div class="min_document clearfix"  id="custom-toolbar">
          <div class="top clearfix">
            <dl class="fmdl fml  fmdll clearfix">
              <dt>档案来源：</dt>
              <dd class="clearfix">
                <label><input type="radio" name="source" checked/>不限</label>
                <label><input type="radio" name="source"/>内部</label>
                <label><input type="radio" name="source"/>外部</label>
            </dd>
          </dl>
          <dl class="fmdl fmdll clearfix">
            <dt>存储类型：</dt>
            <dd class="clearfix">
                <label><input type="radio" name="type" checked/>不限</label>
                <label><input type="radio" name="type"/>文档</label>
                <label><input type="radio" name="type"/>图片</label>
                <label><input type="radio" name="type"/>音视频</label>
            </dd>
          </dl>          
        </div>
        <div class="bottom searchall clearfix">
          <dl class="fmdl fml fmdll clearfix">
            <dt>业务分类：</dt>
            <dd>
              <select>
                <option>全部</option>
              </select>
            </dd>
          </dl>
          <dl class="fmdl fml fmdll clearfix">
            <dt>所属业务线：</dt>
            <dd>
              <select>
                <option>全部</option>
              </select>
            </dd>
          </dl>
          <dl class="fmdl fmdll clearfix">
            <dt></dt>
            <dd>
              <input type="text" class="txt" placeholder="请输入项目名称或投资经理名称" />
            </dd>
            <dd>
            <a href="javascript:;" class="bluebtn ico cx" action="querySearch">查询</a>
            </dd>
          </dl>
        </div>
        </div>
       <div class="tab-pane active" id="view">		
			<table  id="data-table" data-url="/galaxy/sopFile/querySopFile" data-method="post" 
	          		data-side-pagination="server" data-pagination="true" 
	          		data-toolbar="#custom-toolbar" data-page-list="[3,6,10,20]"
					data-id-field="lottoId" data-show-refresh="true">
				<thead>
					<tr>
						<th></th>
                      	<th data-field="ftgk" data-align="center">所属业务线</th>
                      	<th data-field="ftgk" data-align="center">所属项目</th>
                      	<th data-field="ftgk" data-align="center">档案管理</th>
                      	<th data-field="ftgk" data-align="center">起草者</th>
                      	<th data-field="ftgk" data-align="center">存储类型</th>
                      	<th data-field="ftgk" data-align="center">业务分类</th>
                      	<th data-field="ftgk" data-align="center">更新日期</th>
                      	<th data-field="ftgk" data-align="center">档案状态</th>
                     	<th >附件查看</th>
                      	   
						<th data-field="ftgk" data-align="center">访谈概况</th>
						<th data-field="proName" data-align="center">所属项目</th>  
						<th data-field="viewNotes" data-align="center">访谈日志</th>
					</tr>
				</thead>
			</table>
           </div>      
    </div>
</div>

<!-- 弹出页面 -->
<div id="addFile" class="archivestc" style="display: none;">
	<dl class="fmdl clearfix">
    	<dt>档案来源：</dt>
        <dd class="clearfix">
        	<label><input name="fileSource" type="radio" value = "1" checked="checked"/>内部</label>
            <label><input name="fileSource" type="radio" value = "2"/>外部</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>存储类型：</dt>
        <dd>
        	<select id="fileType">
            	<option>sadasd</option>
            </select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>业务分类：</dt>
        <dd>
        	<select id="fileWorkType">
            	<option>sadasd</option>
            </select>
        </dd>
        <dd>
        	<label><input type="checkbox"/>签署凭证</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>所属项目：</dt>
        <dd>
        	<input type="text" placeholder="请输入项目名称或编号" class="txt"/>
        </dd>
        <dd><a class="searchbtn null" href="javascript:;">搜索</a></dd>
    </dl>
    
     <dl class="fmdl clearfix">
    	<dt>文档上传：</dt>
        <dd>
        	<input type="text" class="txt" id="fileTxt"/>
        </dd>
        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="selectBtn">选择档案</a></dd>
    </dl>  
<!--     <div class="fmarea"> -->
<!--     	<TEXTAREA ID="FILELIST"></TEXTAREA> -->
<!-- 		<div  id="filelist"></div> -->
<!-- 		<div  id="console"></div> -->
<!--     </div> -->
    <a href="javascript:;" class="pubbtn bluebtn" id="uploadBtn";>上传保存</a>
    <input type="hidden" id="pathInput" value="<%=path%>">
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/sopFile.js" type="text/javascript"></script>
<script type="text/javascript">

</script>
</html>

