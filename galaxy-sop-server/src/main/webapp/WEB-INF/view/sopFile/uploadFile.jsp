<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/sopFileUpload.js" type="text/javascript"></script>
</head>

<body>
<div class="archivestc">
	<dl class="fmdl clearfix">
    	<dt>档案来源：</dt>
        <dd class="clearfix">
        	<label><input type="radio"/>内部</label>
            <label><input type="radio"/>外部</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>存储类型：</dt>
        <dd>
        	<select>
            	<option>sadasd</option>
            </select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>业务分类：</dt>
        <dd>
        	<select>
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
    <div class="fmload clearfix">
    	<p class="loadname"></p>
        <input type="file" class="load"/>
        <a href="javascript:;" class="pubbtn fffbtn" id="selectBtn">选择档案</a>
    </div>
    <div class="fmarea">
<!--     	<TEXTAREA ID="FILELIST"></TEXTAREA> -->
		<div  id="filelist"></div>
		<div  id="console"></div>
    </div>
    <a href="javascript:;" class="pubbtn bluebtn" id="uploadBtn">上传保存</a>
    <input type="hidden" id="pathInput" value="<%=path%>">
</div>
</body>
</html>

