<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<div class="pagebox">
 <div class="min">
        <!--表格列表-->
        <div class="tablist clearfix">
        <div class="l l_executive">
<!-- 事项预览 -->
<dl>
	<dt>
		<h3 class="ico t7">事项预览</h3>
	</dt>
	<dd>
		<div class="l_previewTop">
			<div style="margin-left: 25%; display: none">
				<span vertical-align:middle>今日会议总数<a id="meeting_number_today" href="javascript:;">0</a>个
				</span>
			</div>
			<span class='bj_pagebox'>今日：立项会 <a id="lxh_number_today" href="javascript:;">0</a>个
			</span> <span class='bj_pagebox bj_pagebox_left'>投决会 <a id="tjh_number_today" href="javascript:;">0</a>个
			</span> <span class='bj_pagebox bj_pagebox_left'>评审会 <a id="psh_number_today" href="javascript:;">0</a>个
			</span>
		</div>
		<div class="l_previewBottom clearfix">
			<div class="l_previewBottom_l l_previewBottom_b">
				<span><p>立项会</p>
					<p>排期等待</p></span><a href="javascript:;" id="lxh_eduling_wait"></a>
			</div>
			<div class="l_previewBottom_r l_previewBottom_b">
				<span><p>投决会</p>
					<p>排期等待</p></span><a href="javascript:;" id="tjh_eduling_wait"></a>
			</div>
			<div class="l_previewBottom_r l_previewBottom_b">
				<span><p>评审会</p>
					<p>排期等待</p></span><a href="javascript:;" id="psh_eduling_wait"></a>
			</div>
		</div>
	</dd>
</dl>
</div>
</div>
</div>
</div>
<script src="<%=path %>/js/charts/indexMatterPreview.js"></script>
<script>
matterPreviewUtils.init();
</script>
