<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<div class="addmentc creative_edit1 zixuntc" id="zixuntc">

	<div class="title_bj" id="popup_name"></div>
	
	<form id="zixunForm" > 
	<div class="zx_name">
		创意资讯编码<span id="code" >16251627</span>
		<input type="hidden" name="code" value="" />
		<input type="hidden" name="id" value="" />
	</div>
	
	<div class="block">
		<div class="form clearfix">
			<h3>公司信息</h3>
			<div class="left">
				<dl class="fmdl fml clearfix">
					<dt><em class="red">*</em>公司名称：</dt>
					<dd>
						<input type="text" class="txt" name="companyName"  maxlength="100"  
							valType1="required"/>
						<div class="tip-yellowsimple" id="companyName_valiate">
							<div class="tip-inner tip-bg-image">
								<font color="red">*</font>公司名称不能为空
							</div>
							<div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
						</div>
					</dd>
				</dl>
				<dl class="fmdl clearfix">
					<dt>细分领域：</dt>
					<dd>
						<input type="text" class="txt" name="companyField" maxlength="50" />
					</dd>
				</dl>
				<dl class="fmdl clearfix">
					<dt>创始人：</dt>
					<dd>
						<input type="text" class="txt" name="companyCuser"  maxlength="100" />
					</dd>
				</dl>
				<dl class="fmdl clearfix">
					<dt>网址：</dt>
					<dd>
						<input type="text" value="" class="txt" name="companyUrl" maxlength="200"/>
					</dd>
				</dl>
			</div>
			<div class="right">
				<dl class="fmdl clearfix">
					<dt>成立日期：</dt>
					<dd>
						<input type="text" class="txt"  name="companyBtime"  maxlength="50" />
					</dd>
				</dl>
				<dl class="fmdl clearfix">
					<dt>归属部门：</dt>
					<dd>
						<select name="departmentId" >
						</select>
					</dd>
				</dl>
				<dl class="fmdl clearfix">
					<dt>所在地：</dt>
					<dd>
						<input type="text" value="" class="txt"  name="companyAddress" maxlength="100" />
					</dd>
				</dl>
			</div>
		</div>
		<div>
			<h3>创意信息</h3>
			<dl class="fmdl clearfix">
				<dt>简介：</dt>
				<dd>
					<textarea maxlength="500" name="remark"></textarea>
				</dd>
			</dl>
			<dl class="fmdl clearfix">
				<dt>详细描述：</dt>
				<dd>
					<textarea maxlength="1000" name="detailInfo"></textarea>
				</dd>
			</dl>
		</div>
		
		
		<div class="rz_info">
			<h3>融资信息</h3>
			<a class="blue add" style="cursor:pointer" data-btn="add_rzzx" data-name="添加融资信息">添加</a>
			<table class="health_case table financing_t" style="width: 700px; margin: 10px auto 0;">
				<thead>
					<tr>
						<th>融资时间</th>
						<th>金额</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="rzBody">
				</tbody>
			</table>
		</div>

	</div>
    </form>
	<div class="btnbox">
		<a href="javascript:;" class="pubbtn bluebtn" onclick="editAdd()">确定</a>
		<a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
	</div>
</div>






