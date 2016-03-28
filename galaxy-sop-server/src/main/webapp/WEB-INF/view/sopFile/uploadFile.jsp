<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/sopFileUpload.js" type="text/javascript"></script>
</head>

<body>
<div class="archivestc" id="searchProjectPanel">
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
    <div class="tab-pane active" >		
		<table id="panelGrid"  width="100%" cellspacing="0"  cellpadding="0"></table>
    </div>
</div>
</body>
</html>

