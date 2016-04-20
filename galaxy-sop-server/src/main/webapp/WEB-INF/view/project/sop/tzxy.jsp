<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 弹出页面 -->
<div id="addFile" class="meetingtc">
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
        	<select id="fileType" disabled="disabled">
        	    <option value="">全部</option>
            	<option value="fileType:1">文档</option>
            	<option value="fileType:2">音频文件</option>
            	<option value="fileType:3">视频文件</option>
            	<option value="fileType:4">图片</option>
            </select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>业务分类：</dt>
        <dd>
        	<select id="fileWorkType" disabled="disabled">
            	<option value="fileWorktype:6">投资协议</option>
            </select>
        </dd>
        <dd>
        	<label id="voucherDiv"><input type="checkbox" value="1" id="voucherType"/>签署凭证</label>
        </dd>
    </dl>
    
     <dl class="fmdl clearfix">
    	<dt>文档上传：</dt>
        <div class="fmload clearfix">
            <dd>
	        	<input type="text" name="fileName" id="file_obj" class="txt" readonly="readonly"/>
	        </dd>
	        <dd>
	        	<a href="javascript:;" class="pubbtn fffbtn" id="select_file_btn">选择档案</a>
    		</dd>
        </div>
    </dl>  

    <div class="btnbox">
    	<a href="javascript:;" id="save_file_btn" class="pubbtn bluebtn">上传保存</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>