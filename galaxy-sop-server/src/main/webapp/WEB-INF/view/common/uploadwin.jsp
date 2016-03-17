<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 弹出页面 -->
<div id="uploadPanel" class="archivestc" style="display: none;">
	<dl class="fmdl clearfix">
    	<dt>档案来源：</dt>
        <dd class="clearfix">
        	<label><input name="win_fileSource" type="radio" value = "1" checked="checked"/>内部</label>
            <label><input name="win_fileSource" type="radio" value = "2"/>外部</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>存储类型：</dt>
        <dd>
        	<select id="win_fileType">
            	<option>sadasd</option>
            </select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>业务分类：</dt>
        <dd>
<!--          	<input type="text" id="fileWorkType"  class="txt"/> -->
<!--          	<input type="hidden" id="fileWorkTypeId"/> -->
         	
         	<select id="win_fileWorkType">
            	<option>sadasd</option>
            </select>
         	
        </dd>
        <dd>
        	<label><input type="checkbox" id="win_isProve"/>签署凭证</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>所属项目：</dt>
        <dd>
        	<input type="text" id="win_sopProjectId" data-tid=""  class="txt"/>
        </dd>
        <dd><a class="searchbtn null" id="win_searchProjectBtn" href="javascript:;">搜索</a></dd>
    </dl>
    
     <dl class="fmdl clearfix">
    	<dt>文档上传：</dt>
        <dd>
        	<input type="text" class="txt" id="win_fileTxt"/>
        </dd>
        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="win_selectBtn">选择档案</a></dd>
    </dl>  
    <div class="fmarea">
    	<TEXTAREA ID="win_FILELIST"></TEXTAREA>
		<div  id="filelist"></div>
		<div  id="console"></div>
    </div>
    <a href="javascript:;" class="pubbtn bluebtn" id="win_uploadBtn">上传保存</a>
<%--     <input type="hidden" id="pathInput" value="<%=path%>"> --%>
</div>