<!-- 添加访谈记录弹出层 -->
<div class="meetingtc">
	<div class="top clearfix">
        <dl class="fmdl clearfix">
            <dt>访谈日期：</dt>
            <dd class="date"></dd>
        </dl>
    </div>
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>访谈对象：</dt>
            <dd class="clearfix">
                <input type="text" id="viewTarget" name="viewTarget" placeholder="访谈对象" class="txt">
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