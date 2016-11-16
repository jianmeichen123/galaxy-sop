<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!-- 通用样式 -->
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=path %>/js/validate/lib/jq.validate.js'></script>
<!-- table列表 -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- 富文本编辑器 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<div class="ritmin">
		<div class="new_tit_a"><a href="#">创投项目</a>>添加项目</div>
        <div class="new_tit_b">
            <span class="size18">添加项目</span>
        </div>
        <div class="new_left addProject">
        	<div class="">
        		<div class="page on clearfix" data-btn="page0">
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_basic ico_add_project"></span>
                        <span class="new_color size16">基本信息</span>
                    </div>  
                    <form action="" id="add_form" method="post">
                     <input type="hidden" id="flagId" name="flagId" value="">
                    <ul class="basic_ul">
                    	<li>
                        	<span class="basic_span"><em class="red">*</em>项目类型：</span>
                            <span class="m_r30">
                            	<input name="projectType" type="radio" value="projectType:1" id="radio_w">
                            	<label for="radio_w">&nbsp;投资</label>
                            </span>
                            <span class="m_r30">
                            	<input name="projectType" type="radio" value="projectType:2" id="radio_n">
                            	<label for="radio_n">&nbsp;创建</label>
                            </span>
                            <span id="projectTypeTip"  style="display:none;">
                            	<div class="tip-yellowsimple" style="visibility: inherit; left: 452px; top: 202px; opacity: 1; width: 101px;"><div class="tip-inner tip-bg-image"><font color="red">*</font>项目类型不能为空</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div></div>
                            </span>
                        </li>
                        <li>
                            <span class="basic_span"><em class="red">*</em>项目名称：</span>
                            <span class="m_r30">
                            	<input type="text" class='new_nputr' maxlength="24" id="projectName" name="projectName" valType="required" msg="<font color=red>*</font>项目名称不能为空"/>
                            </span>
                            <span class="basic_span"><em class="red">*</em>创建时间：</span>
                            <span class="m_r30"><input type="text" class='datepicker-text new_nputr' name="createDate" id="createDate" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/></span>
                        </li>
                        <li>
                        	<span class="basic_span"><em class="red">*</em>行业归属：</span>
                            <span class="m_r30">
                            	<select name="industryOwn" class='new_nputr' valType="required" msg="<font color=red>*</font>行业归属不能为空">
			                    	<option value="">--请选择--</option>
			                    </select>
                            </span>
                        	<span class="basic_span" ><em class="red">*</em>融资状态：</span>
                 	          <span class="m_r30">
								<select name="financeStatus" class='new_nputr'>
			                    </select>
							</span>
                        </li>
                        <li>
	                        <span class="basic_span"><em class="red">*</em>来源于FA：</span>
	                        <span class="m_r30" style="with:400px">
	                             <input type="radio" name="faFlag" checked=checked  value="0" onclick="setText('reset')">否
	                             <input type="radio" name="faFlag" onclick="setText('set')" value="1" id="faFlag2">是
	                             <input type="text" class="new_nputr" placeholder="请输入FA名称" style="display:none" maxlength="20" name="faName" allowNULL="yes" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>只能是汉字或是字符,长度为20" id="faName"/>
	                        </span>
                        </li>
                        <li>
                        	<span class="basic_span">备注：</span>
                            <span>
                            	<textarea class="new_nputr text" maxlength="50" placeholder="最多输入50字" valType="OTHER" allowNULL="yes" regString="^.{0,50}$" msg="<font color=red>*</font>不能超过50字符"></textarea>
                        	</span>
                        </li>
                    </ul>  
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_financing"></span>
                        <span class="new_color size16 m_r15">融资计划</span>
                        <span class="new_color_gray">请折算为人民币进行计算</span>
                    </div>  
                    <ul class="basic_ul">
                        <li>
                            <span class="basic_span"><em class="red">*</em>融资金额：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatContribution" name="formatContribution" allowNULL="no" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持两位小数"/>
                            </span>
                            <span class="m_r30">万元人民币</span>
                            <span class="basic_span"><em class="red">*</em>项目估值：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatValuations" name="formatValuations" allowNULL="no" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持两位小数"/>
                            </span>
                            <span class="m_r30">万元人民币</span>
                        </li>
                        <li>
                        	<span class="basic_span"><em class="red">*</em>出让股份：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatShareRatio" name="formatShareRatio" allowNULL="no" valType="OTHER" regString="^([1-9]{1,2}(\.\d{1,4})?)$" msg="<font color=red>*</font>0到100之间的四位小数"/>
                            </span>
                            <span class="m_r30">% </span>
                        </li>
                    </ul>
                    </form>
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_history ico_add_project"></span>
                        <span class="new_color size16">融资历史</span>
                        <input type="hidden" id="">
                        <button class="blue fr add_history" href="tanchuan/historytc.jsp" id="add_history" data-btn="add_history" data-name="添加融资历史">添加</button>
                    </div>
                    <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                     <thead>
                    	<tr>
                    		<th>融资时间</th>
                    		<th>投资方(机构或个人)</th>
                    		<th>投资金额(万元)</th>
                    		<th>币种</th>
                    		<th>占比（%）</th>
                    		<th>融资轮次</th>
                    		<th>操作</th>
                    	</tr>
                    </thead>
	                    <tbody id="financeHistory_table">
	                    </tbody>
                    </table> 
                    <div class="compile_on_center">
	                	<div class="compile_on_left fr clearfix">
	                        <span class="pubbtn bluebtn disabled fl"  data-btn="next">下一步</span>
	                        <div class="fl pages">
	                        	<label class="current_page blue">1</label>/<label>4</label>
	                        </div>
	                    </div>
                    </div>
        		</div>
                <div id="step2" class="page clearfix" data-btn="page1">
					<div class="tabtable_con_on">
						<div class="new_r_compile ">
							<span class="new_ico_book"></span> <span class="new_color size16"><em class="red">*</em>商业计划书</span>
							<span id="file-tip" class="tip-yellowsimple" style="display:none;visibility: inherit;">
							    <div class="tip-inner tip-bg-image">
							        <font color="red">*</font>商业计划书不能为空
							    </div>
							    <div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
							</span>
						</div>
						<input type="hidden" value="0" id="buess_doc"/>
						<input type="hidden" value="" id="file_key"/>
						<table style="width:94%;" id="plan_business_table" cellspacing="0" cellpadding="0" class="basic_table table">
                    	<thead>
                    		<tr class="">
                    			<th style="" data-field="createDate">
                    				<div class="th-inner ">更新时间</div>
                    				<div class="fht-cell"></div>
                    			</th>
                    			<th style="" data-field="fileName">
                    				<div class="th-inner ">文档名称</div>
                    				<div class="fht-cell"></div>
                    			</th>
                    			<th style="" data-field="operate">
                    				<div class="th-inner ">操作</div>
                    				<div class="fht-cell"></div>
                    			</th>
                    		</tr>
                    	</thead>
                    	<tbody id="doc_tbody">
                    		
                    	</tbody>
                    	</table>
					</div>
					<div class="tabtable_con_on" >
						<div class="project_on " >
							<div class="title_bj_tzjl">项目描述</div>
							<div class="describe1">
								<span class="basic_span1"><em class="red">*</em>商业模式：</span>
							 	<div id="describe_editor" type="text/plain" class='width_fwb'></div>  
							</div>
							<div class="describe2">
								<span class="basic_span1"><em class="red">*</em>业务简要概述和项目亮点：</span>
							 	<div id="describe_editor2" type="text/plain" class='width_fwb'></div>  
							</div>
		                    <div class="compile_on_center">
		                        <div class="compile_on_right">
		                            <span class="pubbtn bluebtn" id="save_describe" data-name="project" data-on="close">保存</span>
		                            <span class="pubbtn fffbtn" data-name="project" data-on="close">取消</span>
		                        </div>  
		                    </div>
					    </div>
					    <div class="project_center">
							<div class="new_r_compile ">
								<span class="new_ico_project"></span> <span class="new_color size16"><em class="red">*</em>项目描述</span>
								<span id="describe_valiate" class="tip-yellowsimple" style="display:none;visibility: inherit;">
								    <div class="tip-inner tip-bg-image">
								        <font color="red">*</font>项目描述不能为空
								    </div>
								    <div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
								</span> 
								<span class="new_fctbox">
									<a href="javascript:;" class="ico f1" data-name="project" data-on="data-open">编辑</a>
								</span>
							</div>
							<div class="new_ul_all new_top_color describe_show" >
								<div><span class="ico_dot ico"></span><span style="font-size:14px;font-family:'微软雅黑';line-height:36px;">商业模式</span></div>
								<p style="padding-left:22px" id="describe_show" valiate="required"></p>
								<div><span class="ico_dot ico"></span><span style="font-size:14px;font-family:'微软雅黑';line-height:36px;">业务简要概述和项目亮点</span></div>
								<p style="padding-left:22px" id="describe2_show" valiate="required"></p>
							</div>
						</div>
					</div>
					<div class="tabtable_con_on">
						<div class='company_center'>
							<div class="new_r_compile ">
								<span class="new_ico_firm"></span> <span class="new_color size16"><em class="red">*</em>公司定位</span>
								<span id="location_valiate" class="tip-yellowsimple" style="display:none;visibility: inherit;">
								    <div class="tip-inner tip-bg-image">
								        <font color="red">*</font>公司定位不能为空
								    </div>
								    <div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
								</span>
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='company'  data-on="data-open">编辑</a>
								</span>
							</div>
							<div class="new_ul_all new_top_color location_show">
								<span class="ico_dot ico"></span>
								<p id="location_show" valiate="required"></p>
							</div>
						</div>
						<div class='company_on'>					
							<div class="title_bj_tzjl">公司定位</div>
							<div id="company_editor" type="text/plain" class='width_fwb'></div>  
			                <div class="compile_on_center">
			                    <div class="compile_on_right">
			                        <span class="pubbtn bluebtn" id="save_location" data-name='company' data-on="close">保存</span>
			                        <span class="pubbtn fffbtn" data-name='company' data-on="close">取消</span>
			                    </div>  
			                </div>
						</div>
					</div>
					<div class="tabtable_con_on">
						<div class='portrayal_center'>
							<div class="new_r_compile ">
								<span class="new_ico_people"></span> <span class="new_color size16"><em class="red">*</em>用户画像</span>
								<span id="portrait_valiate" class="tip-yellowsimple" style="display:none;visibility: inherit;">
								    <div class="tip-inner tip-bg-image">
								        <font color="red">*</font>用户画像不能为空
								    </div>
								    <div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
								</span>
								<span class="new_fctbox"> 
								<a href="javascript:;" class="ico f1" data-name='portrayal'  data-on="data-open">编辑</a>
								</span>
							</div>
							<div class="new_ul_all new_top_color portrait_show">
								<span class="ico_dot ico"></span>
								<p id="portrait_show" valiate="required"></p>
							</div>
						</div>
						<div class='portrayal_on'>
							<div class="title_bj_tzjl">用户画像</div>
							<div id="portrait_editor" type="text/plain" class='width_fwb'></div>  
			                <div class="compile_on_center">
			                    <div class="compile_on_right">
			                        <span class="pubbtn bluebtn" id="save_portrait" data-name='portrayal' data-on="close">保存</span>
			                        <span class="pubbtn fffbtn" data-name='portrayal' data-on="close">取消</span>
			                    </div>  
			                </div>
						</div>
					</div>
					<div class="tabtable_con_on">
						<div class='product_center'>
							<div class="new_r_compile ">
								<span class="new_ico_product"></span> <span class="new_color size16"><em class="red">*</em>产品服务</span>
								<span id="business_valiate" class="tip-yellowsimple" style="display:none;visibility: inherit;">
								    <div class="tip-inner tip-bg-image">
								        <font color="red">*</font>产品服务不能为空
								    </div>
								    <div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
								</span>
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='product' data-on="data-open">编辑</a>
								</span>
							</div>
							<div class="new_ul_all new_top_color business_model_show">
								<span class="ico_dot ico"></span>
								<p id="business_model_show" valiate="required"></p>
							</div>
						</div>
						<div class='product_on'>
							<div class="title_bj_tzjl">产品服务</div>
							<div id="business_editor" type="text/plain" class='width_fwb' ></div>  
			                <div class="compile_on_center">
			                    <div class="compile_on_right">
			                        <span class="pubbtn bluebtn" id="save_business" data-name='product' data-on="close">保存</span>
			                        <span class="pubbtn fffbtn" data-name='product' data-on="close">取消</span>
			                    </div>  
			                </div>
						</div>
					</div>
					<div class="tabtable_con_on">
						<div class='operation_center'>
							<div class="new_r_compile ">
								<span class="new_ico_run"></span> <span class="new_color size16">运营数据</span> <span class="bj_ico" style="display:none" id="operational_data_valiate">暂无数据</span>
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='operation' data-on="data-open">编辑</a>
								</span>
							</div>
							<div class="new_ul_all new_top_color operational_data_show">
								<span class="ico_dot ico"></span>
								<p id="operational_data_show"></p>
							</div>
						</div>
						<div class='operation_on'>
							<div class="title_bj_tzjl">运营数据</div>
							<div id="operation_editor" type="text/plain" class='width_fwb' ></div>  
			                <div class="compile_on_center">
			                    <div class="compile_on_right">
			                        <span class="pubbtn bluebtn" id="save_operation" data-name='operation' data-on="close">保存</span>
			                        <span class="pubbtn fffbtn" data-name='operation' data-on="close">取消</span>
			                    </div>  
			                </div>
						</div>
					</div>
					<div class="tabtable_con_on">
						<div class='industry_center'>
							<div class="new_r_compile ">
								<span class="new_ico_industry"></span> <span class="new_color size16"><em class="red">*</em>行业分析</span>
								<span id="industry_valiate" class="tip-yellowsimple" style="display:none;visibility: inherit;">
								    <div class="tip-inner tip-bg-image">
								        <font color="red">*</font>行业分析不能为空
								    </div>
								    <div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
								</span>
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='industry' data-on="data-open">编辑</a>
								</span>
							</div>
							<div class="new_ul_all new_top_color industry_show">
								<span class="ico_dot ico"></span>
								<p id="industry_show" valiate="required"></p>
							</div>
						</div>
						<div class='industry_on'>
							<div class="title_bj_tzjl">行业分析</div>
							<div id="industry_editor" type="text/plain" class='width_fwb' ></div>  
			                <div class="compile_on_center">
			                    <div class="compile_on_right">
			                        <span class="pubbtn bluebtn" id="save_industry" data-name='industry' data-on="close">保存</span>
			                        <span class="pubbtn fffbtn" data-name='industry' data-on="close">取消</span>
			                    </div>  
			                </div>
						</div>
					</div>
					<div class="tabtable_con_on">
						<div class='analysis_center'>
							<div class="new_r_compile ">
								<span class="new_ico_jq"></span> <span class="new_color size16"><em class="red">*</em>竞争分析</span>
								<span id="analysis_valiate" class="tip-yellowsimple" style="display:none;visibility: inherit;">
								    <div class="tip-inner tip-bg-image">
								        <font color="red">*</font>竞争分析不能为空
								    </div>
								    <div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
								</span>
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='analysis' data-on="data-open">编辑</a>
								</span>
							</div>
							<div class="new_ul_all new_top_color analysis_show">
								<span class="ico_dot ico"></span>
								<p id="analysis_show" valiate="required"></p>
							</div>
						</div>
						<div class='analysis_on'>
							<div class="title_bj_tzjl">竞争分析</div>
							<div id="analysis_editor" type="text/plain" class='width_fwb'></div>  
			                <div class="compile_on_center">
			                    <div class="compile_on_right">
			                        <span class="pubbtn bluebtn" id="save_analysis" data-name='analysis' data-on="close">保存</span>
			                        <span class="pubbtn fffbtn" data-name='analysis' data-on="close">取消</span>
			                    </div>  
			                </div>
						</div>
					</div>
					<div class="tabtable_con_on">
						<div class='next_financing_center'>
							<div class="new_r_compile ">
								<span class="new_ico_nex"></span> <span class="new_color size16">下一轮融资路径</span> <span class="bj_ico" style="display:none" id="next_financing_source_valiate">暂无数据</span>
								<span class="new_fctbox"> 
								<a href="javascript:;" class="ico f1" data-name='next_financing' data-on="data-open" >编辑</a>
								</span>
							</div>
							<div class="new_ul_all new_top_color next_financing_source_show">
								<span class="ico_dot ico"></span>
								<p id="next_financing_source_show" ></p>
							</div>
						</div>
						<div class='next_financing_on'>
							<div class="title_bj_tzjl">下一轮融资路径</div>
							<script id="next_financing_editor" type="text/plain" class='width_fwb'></script>  
			                <div class="compile_on_center">
			                    <div class="compile_on_right">
			                        <span class="pubbtn bluebtn" id="save_next_financing" data-name='next_financing' data-on="close">保存</span>
			                        <span class="pubbtn fffbtn" data-name='next_financing' data-on="close">取消</span>
			                    </div>  
			                </div>
						</div>
					</div>
					<div class="compile_on_center">
	                	<div class="compile_on_left fr clearfix">
	                    	<span class="pubbtn bluebtn fl"  data-btn="pre" id="toStep1">上一步</span>
	                        <span class="pubbtn bluebtn disabled fl"  data-btn="next" id="toStep3">下一步</span>
	                        <div class="fl pages">
	                        	<label class="current_page blue">2</label>/<label>4</label>
	                        </div>
	                    </div>
                    </div>
                </div>
                <div class="page clearfix" data-btn="page2">
               	    <input type="hidden" value="0" id="person"/>
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_person ico_add_project"></span>
                        <span class="new_color size16"><em class="red">*</em>团队成员</span>
                        <button onclick="addProjectPerson();" class="blue fr add_history" data-name="添加团队成员" data-btn="addProjectPerson">添加</button>
                    </div>
                    <table id="person-table" style="width:94%;" cellspacing="0" cellpadding="0" class="basic_table table">
                    	<thead>
                    	<tr>
                    		<th>姓名</th>
                    		<th>当前职务</th>
                    		<th>性别</th>
                    		<th>出生年月</th>
                    		<th>电话号码</th>
                    		<th>操作</th>
                    	</tr>
                    	</thead>
                    	<tbody id="person-tbody">
	                    
                    	</tbody>
                    </table>
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_stock_add ico_add_project"></span>
                        <span class="new_color size16"><em class="red">*</em>股权结构</span>
                    </div>
					<div class="legal">
					    <div class="legal_box">
					      <div class="title">
					          <span class="new_color size16">法人信息</span>
					      </div>
					      <form action="" id="company-info-form">
					      <input type="hidden" name="id" value="${projectId }">
					      <table width="100%" cellspacing="0" cellpadding="0" class="new_table new_table_stock">
					          <tr>
					              <td><span class="new_color_gray th">公司名称：</span><input type="text" placeholder="请输入公司名称" name="projectCompany" maxlength="30"></td>
					              <td><span class="new_color_gray th">组织代码：</span><input type="text" placeholder="请输入组织机构代码" name="projectCompanyCode" maxlength="20"></td>
					          </tr>
					          <tr>
					              <td><span class="new_color_gray th">法人：</span><input type="text" placeholder="请输入法人名称" name="companyLegal" maxlength="30"></td>
					              <td><span class="new_color_gray th">成立日期：</span><input type="text" class="timeico" name="formationDate" onkeydown="return false;"></td>
					          </tr>
					      </table>                    
					      </form>
					  </div>
					</div>
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_color size16"><em class="red">*</em>股权结构</span>
                        <button onclick="addProjectShares();" class="blue fr add_history">添加</button>
                    </div>
                    <input type="hidden" value="0" id="shares"/>
                    <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table sharestable">
                    	<thead>
                    	<tr>
                    		<th>所有权人</th>
                    		<th>所有权人类型</th>
                    		<th>占比(%)</th>
                    		<th>出资金额(万元)</th>
                    		<th>币种</th>
                    		<th>备注</th>
                    		<th>操作</th>
                    	</tr>
                    	</thead>
                    	<tbody id="shares-tbody">
	                    
                    	</tbody>
                    </table>
                     <div class="compile_on_center">
	                	<div class="compile_on_left fr clearfix">
	                    	<span class="pubbtn bluebtn fl" id="step3-previous" data-btn="pre">上一步</span>
	                        <span class="pubbtn bluebtn disabled fl" id="step3-next"  data-btn="next">下一步</span>
	                        <div class="fl pages">
	                        	<label class="current_page blue">3</label>/<label>4</label>
	                        </div>
	                    </div>
                    </div>
                </div>
                
                
                
                 <div class="page" data-btn="page3">
                	 <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_book"></span>
                        <span class="new_color size16"><em class="red">*</em>访谈记录</span>
                        <button class="blue fr add_history" href="<%=path %>/galaxy/project/progress/interViewAdd" data-btn="pro_interview" data-name="访谈记录">添加</button>
                    </div>
                    
			        <div id="view_custom-toolbar">
						<input type="hidden" name="pid" value="">
					</div>
                    <table style="table-layout:fixed"  id="pre_pro_view_table" 
                    	data-url="<%=path %>/galaxy/project/queryPreProView" data-method="post" 
		          		data-toolbar="#view_custom-toolbar" 
						data-id-field="uuid" data-unique-id="uuid" data-show-refresh="true">
						<colgroup >
							<col style="width:30%;">
							<col style="width:50%;">
							<col style="width:20%;">
						</colgroup>
						<thead>
							<tr>
								<th  data-formatter="pro_viewInfo_format" data-class="th_no1">访谈概况</th>
								<th  data-field="viewNotes"  data-formatter="pro_viewNote_format">访谈记录</th>
								<th  data-formatter="pro_viewOp_format">操作</th>
							</tr>
						</thead>
					</table> 
                    <div class="compile_on_center">
	                	<div class="compile_on_left fr clearfix">
	                    	<span class="pubbtn bluebtn fl"  data-btn="pre">上一步</span>
	                        <span class="pubbtn bluebtn disabled fl" onclick="createProject();" data-btn="createProject">生成项目</span>
	                        <span class="pubbtn fffbtn fl"  data-btn="close">取消</span>
	                        <div class="fl pages">
	                        	<label class="current_page blue">4</label>/<label>4</label>
	                        </div>
	                    </div>
                    </div>
                </div>
                    <!--去掉保存取消按钮 <div class="compile_on_center">
                       <div class="compile_on_left">
                           <span class="pubbtn bluebtn" onclick="add();">保存</span>
                           <span class="pubbtn fffbtn" data-name='industry' data-on="close">取消</span>
                       </div>  
                   </div> -->
            </div>
        </div>
       <!--右边-->
        <div class="basic_right">
        	<div class="tabtable_con_on">
            	<div class="new_bottom_color">
                    <span class="new_ico_hint"></span>
                    <span class="new_color size16">温馨提示</span>
                </div>
                <ul class="basic_right_ul">
                	<li>有效项目数据的最低标准如下:</li>
                    <li>1、已录入商业计划书</li>
                    <li>2、已录入至少一条访谈记录</li>
                    <li>3、已录入至少一名创业团队成员</li>
                </ul>     	
                <p class="basic_p">不满足以上标准的项目数据，将会被系统删除。请尽快将项目信息补充完整，以达到项目数据的最低标准。</p>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<!--隐藏-->
<div class="bj_hui_on"></div>
<script type="text/javascript">
createMenus(5);
initDialogValstr("add_form");
var pid;
//上一步，下一步
$('[data-btn="next"]').click(function(){
	var pageNum=$(this).parent().parent().parent().attr("data-btn");
	num=Number(pageNum.substr(pageNum.length-1,1));
	if(num==0){
		var result=add();
		if(!result){
		//	alert("重要参数丢失");
			//return;
		} 
	}else if(num==1){
		if(!step2Valiate("step2")){
			return;
		}
	}else if(num==2){
		sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/save3/"+pid, 
				$("#company-info-form").serializeObject(), 
				function(data){
			viewTableShow(pid);
		});
	}
	$("[data-btn='page"+(num+1)+"']").addClass("on").siblings().removeClass("on");
})
$('[data-btn="pre"]').click(function(){
	$("body").css("overflow","auto");
	var prePageNum=$(this).parent().parent().parent().attr("data-btn");
	num=Number(prePageNum.substr(prePageNum.length-1,1));
	$("[data-btn='page"+(num-1)+"']").addClass("on").siblings().removeClass("on");
})

$('input[name="formationDate"]').datepicker({
    format: 'yyyy-mm-dd',
    language: "zh-CN",
    autoclose: true,
    todayHighlight: false,
    defaultDate : Date,
    today: "Today",
    todayBtn:'linked',
    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
    forceParse:false,
    currentText: 'Now'
});


$(function(){
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/lookBuessDoc/"+pid, 
			null, 
			function(data){
		if(data.result.status == 'OK' 
				&& typeof(data.entity) != 'undefined'){
			generateBuessDocInnerHtml(data.entity);
			$("#buess_doc").val(1);
		}else{
			generateBuessDocEmptyInnerHtml();
		}
	});
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/searchProjectPerson/"+pid, 
			null, 
			function(data){
		if(data.result.status == 'OK' 
				&& typeof(data.entityList) != 'undefined' 
				&& data.entityList.length > 0){
			generatePersonInnerHtml(data.entityList);
			$("#person").val(data.entityList.length);
		}else{
			generatePersonEmptyInnerHtml();
		}
	});
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/searchProjectShares/"+pid, 
			null, 
			function(data){
		if(data.result.status == 'OK' 
				&& typeof(data.entityList) != 'undefined' 
				&& data.entityList.length > 0){
			generateSharesInnerHtml(data.entityList);
			$("#shares").val(data.entityList.length);
		}else{
			generateSharesEmptyInnerHtml();
		}
	});
});
</script>
<!-- step2 for JS -->
<script src="<%=path%>/js/v_add_project_1.js"></script>
<script src="<%=path%>/js/v_add_project_2.js"></script>
<script src="<%=path%>/js/v_add_project_3.js"></script>
<script src="<%=path%>/js/v_add_project_4.js"></script>
<script src="<%=path%>/js/v_add_project_history.js"></script>
</body>
</html>