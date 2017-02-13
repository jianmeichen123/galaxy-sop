<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!-- 校验 -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript'
	src='<%=request.getContextPath()%>/js/validate/lib/jq.validate.js'></script>
<link rel="stylesheet" href="<%=path%>/css/showLoading.css"
	type="text/css">

<div class="addmentc creative_edit1 zx_looktc" id="zixun_info">
	<div class="title_bj" id="popup_name"></div>
	<div class="zx_name">
		创意资讯编码<span id="code">16251627</span>
	</div>
	<div class="block">
		<div class="form clearfix">
			<h3>公司信息</h3>
			<div class="left">
				<dl class="fmdl fml">
					<dt>公司名称：</dt>
					<dd id="companyName"></dd>
				</dl>
				<dl class="fmdl">
					<dt>细分领域：</dt>
					<dd id="companyField"></dd>
				</dl>
				<dl class="fmdl">
					<dt>创始人：</dt>
					<dd id="companyCuser"></dd>
				</dl>
				<dl class="fmdl">
					<dt>网址：</dt>
					<dd id="companyUrl"></dd>
				</dl>
			</div>
			<div class="right">
				<dl class="fmdl">
					<dt>成立日期：</dt>
					<dd id="companyBtime"></dd>
				</dl>
				<dl class="fmdl">
					<dt>归属部门：</dt>
					<dd id="departmentId"></dd>
				</dl>
				<dl class="fmdl">
					<dt>所在地：</dt>
					<dd id="companyAddress"></dd>
				</dl>
			</div>
		</div>
		<div>
			<h3>创意信息</h3>
			<div class="j">
				<span class="ico_dot ico"></span> <span>简介</span>
				<p id="remark"></p>
			</div>
			<div class="j">
				<span class="ico_dot ico"></span> <span>详细描述</span>
				<p id="detailInfo"></p>
			</div>
		</div>
		
		<div class="rz_info" id="rz_info">
			<h3>融资信息</h3>
			<table class="health_case table"
				style="width: 630px; margin: 10px auto 0;">
				<thead>
					<tr>
						<th>融资时间</th>
						<th>金额</th>
					</tr>
				</thead>
				<tbody id="rzBody">
				</tbody>
			</table>
		</div>

	</div>

</div>

