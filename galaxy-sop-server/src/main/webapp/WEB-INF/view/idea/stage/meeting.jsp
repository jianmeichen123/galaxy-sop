<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="meetingtc">
	<div class="top clearfix">
        <dl class="fmdl clearfix">
            <dt>参会时间：</dt>
            <dd>
                <input type="text" class="txt time">
            </dd>
        </dl>
    </div>
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>会议结论：</dt>
            <dd class="clearfix">
                <label><input type="radio"/>通过</label>
                <label><input type="radio"/>待定</label>
                <label><input type="radio"/>否决</label>
            </dd>
        </dl>
    </div>
    <dl class="fmdl clearfix">
        <dt>会议纪要：</dt>
        <dd>
            <div class="edit">
                这里是编辑器
            </div>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
        <dt>会议录音：</dt>
        <dd>
            <div class="fmload clearfix">
                <p class="loadname"></p>
                <input type="file" class="load"/>
                <a href="javascript:;" class="pubbtn fffbtn">上传录音</a>
            </div>
        </dd>
    </dl>
    <div class="btnbox">
    	<a href="javascript:;" class="pubbtn bluebtn">保存</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>