<%@ page language="java" pageEncoding="UTF-8"%>
<div class="meetingtc">
	<div class="top clearfix">
    	<div class="searchall clearfix">
            <dl>
            	<dt>项目 :</dt>
                <dd><input type="text" placeholder="请输入关键字查找" class="txt"/></dd>
            </dl>
            <a href="javascript:;" class="searchbtn null">搜索</a>
        </div>
        <dl class="fmdl clearfix">
            <dt>会议召开日期：</dt>
            <dd>
                2016-01-20
            </dd>
        </dl>
    </div>
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>会议类型：</dt>
            <dd class="clearfix">
                <label><input type="radio"/>内评会</label>
                <label><input type="radio"/>CEO评审</label>
                <label><input type="radio"/>立项会</label>
                <label><input type="radio"/>投决会</label>
            </dd>
        </dl>
        <dl class="fmdl clearfix">
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