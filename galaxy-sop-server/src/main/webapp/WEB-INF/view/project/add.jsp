<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<!-- 校验样式 -->
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/reset.css" /> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<!-- 富文本编辑器 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
		<div class="new_tit_a"><a href="#">工作桌面</a>><a href="#">创投项目</a>>添加项目</div>
        <div class="new_tit_b">
            <span class="size18">添加项目</span>
        </div>
    	
        <div class="new_left">
        	<div class="tabtable_con_on">
        		<div class="page on clearfix" data-btn="page0">
        			 <!--融资计划-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_basic"></span>
                        <span class="new_color size16">基本信息</span>
                    </div>  
                    <form action="" id="add_form" method="post">
                    <ul class="basic_ul">
                    	<li>
                        	<span class="basic_span"><em class="red">*</em>项目类型：</span>
                            <span class="m_r30"><input name="projectType" type="radio" value="projectType:1" id="radio_w"><label for="radio_w">&nbsp;投资</label></span>
                            <span class="m_r30"><input name="projectType" type="radio" value="projectType:2" id="radio_n"><label for="radio_n">&nbsp;创建</label></span>
                           <span id="projectTypeTip"  style="display:none;">
                            	<div class="tip-yellowsimple" style="visibility: inherit; left: 452px; top: 202px; opacity: 1; width: 101px;"><div class="tip-inner tip-bg-image"><font color="red">*</font>项目类型不能为空</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div></div>
                            </span>
                        </li>
                        <li>
                            <span class="basic_span"><em class="red">*</em>项目名称：</span>
                            <span class="m_r30"><input type="text" class='new_nputr' maxlength="24" id="projectName" name="projectName" valType="required" msg="<font color=red>*</font>项目名称不能为空"/></span>
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
                        	<span class="basic_span"><em class="red">*</em>融资状态：</span>
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
                             <input type="text" class="new_nputr" value="请输入FA名称" style="display:none" maxlength="20" name="faName" allowNULL="yes" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>姓名只能是汉字或是字符,长度为20" id="faName"/>
                        </span>
                        </li>
                        <li>
                        <span class="basic_span">备注：</span>
                            <span>
                            	<textarea class="new_nputr text" maxlength="50" placeholder="最多输入50字"></textarea>
                        	</span>
                        </li>
                    </ul>  
                    
                    <!--融资计划-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_financing"></span>
                        <span class="new_color size16 m_r15">融资计划</span>
                        <span class="new_color_gray">请折算为人民币进行计算</span>
                    </div>  
                    <ul class="basic_ul">
                    	
                        <li>
                            <span class="basic_span"><em class="red">*</em>融资金额：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatContribution" name="formatContribution" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持两位小数"/>
                            </span>
                            <span class="m_r30">万元人民币</span>
                            <span class="basic_span"><em class="red">*</em>项目估值：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatValuations" name="formatValuations" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持两位小数"/>
                            </span>
                            <span class="m_r30">万元人民币</span>
                        </li>
                        <li>
                        	<span class="basic_span"><em class="red">*</em>出让股份：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatShareRatio" name="formatShareRatio" allowNULL="yes" valType="OTHER" regString="^(\d{1,2}(\.\d{1,4})?)$" msg="<font color=red>*</font>0到100之间的四位小数"/>
                            </span>
                            <span class="m_r30">% </span>
                        </li>
                    </ul>
                    </form>
                    <!--融资历史-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_book"></span>
                        <span class="new_color size16">融资历史</span>
                        <button class="blue fr add_history" href="tanchuan/historytc.jsp" data-btn="add_history" data-name="融资历史">添加</button>
                    </div>
                    <!-- 融资历史表格-->
                    <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                    	<tr>
                    		<th>融资时间</th>
                    		<th>投资方(机构或个人)</th>
                    		<th>投资额(万元)</th>
                    		<th>币种</th>
                    		<th>占比（%）</th>
                    		<th>融资轮次</th>
                    		<th>操作</th>
                    	</tr>
                    	<tr>
                    		<td>2015-12-16</td>
                    		<td>红杉资本</td>
                    		<td>1000</td>
                    		<td>人民币</td>
                    		<td>9</td>
                    		<td>A轮</td>
                    		<td>
	                    		<a class="meet_edit blue" href="javascript:void(0)">编辑</a>
	                    		<a class="meet_delete blue" href="javascript:void(0)">删除</a>
                    		</td>
                    	</tr>
                    </table> 
                    <div class="compile_on_center">
	                	<div class="compile_on_left fr clearfix">
	                        <span class="pubbtn bluebtn fl"  data-btn="next">下一步</span>
	                        <div class="fl pages">
	                        	<label class="current_page blue">1</label>/<label>4</label>
	                        </div>
	                    </div>
                    </div>
        		</div>
                <div class="page clearfix" data-btn="page1">
                	<!--商业计划书-->
					<div class="tabtable_con_on">
						<div class="new_r_compile ">
							<span class="new_ico_book"></span> <span class="new_color size16">商业计划书</span>
						</div>
						<table style="width:94%;" id="plan_business_table" cellspacing="0" cellpadding="0" class="basic_table">
                    	</table>
					</div>
					<!--项目概述-->
					<div class="tabtable_con_on" >
						<div class="project_on " >
							<div class="title_bj_tzjl">项目描述</div>
								<div class="describe1">
									<span class="basic_span"><em class="red">*</em>商业模式：</span>
								 	<div id="describe_editor" type="text/plain" class='width_fwb'></div>  
								</div>
								<div class="describe2">
									<span class="basic_span"><em class="red">*</em>业务简要概述和项目亮点：</span>
								 	<div id="describe_editor2" type="text/plain" class='width_fwb'></div>  
								</div>
					                  
					                     <div class="compile_on_center">
					                        <div class="compile_on_right">
					                            <span class="pubbtn bluebtn" id="save_describe">保存</span>
					                            <span class="pubbtn fffbtn" data-name="project" data-on="close">取消</span>
					                        </div>  
					                    </div>
					              </div>
					              <div class="project_center">
							<div class="new_r_compile ">
								<span class="new_ico_project"></span> <span class="new_color size16">项目描述</span> <span class="bj_ico" id="descript">暂无数据</span>
								<c:if test="${isEditable}">
								<span class="new_fctbox">
									<a href="javascript:;" class="ico f1" data-name="project" data-on="data-open">编辑</a>
								</span>
								</c:if>
							</div>
							<div class="new_ul_all new_top_color describe_show" style="display:none;">
								<span class="ico_dot ico"></span>
								<p id="describe_show"></p>
							</div>
						</div>
					</div>
					<!--公司定位-->
					<div class="tabtable_con_on">
						<div class='company_center'>
							<div class="new_r_compile ">
								<span class="new_ico_firm"></span> <span class="new_color size16">公司定位</span> <span class="bj_ico" id="location">暂无数据</span>
								<c:if test="${isEditable}">
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='company'  data-on="data-open">编辑</a>
								</span>
								</c:if>
							</div>
							<div class="new_ul_all new_top_color location_show">
								<span class="ico_dot ico"></span>
								<p id="location_show"></p>
							</div>
						</div>
						<div class='company_on'>					
							<div class="title_bj_tzjl">公司定位</div>
							<div id="company_editor" type="text/plain" class='width_fwb'></div>  
					                 <div class="compile_on_center">
					                    <div class="compile_on_right">
					                        <span class="pubbtn bluebtn" id="save_location">保存</span>
					                        <span class="pubbtn fffbtn" data-name='company' data-on="close">取消</span>
					                    </div>  
					                </div>
						</div>
						
					</div>
					<!--用户画像-->
					<div class="tabtable_con_on">
						<div class='portrayal_center'>
							<div class="new_r_compile ">
								<span class="new_ico_people"></span> <span class="new_color size16">用户画像</span> <span class="bj_ico" id="portrait">暂无数据</span>
								<c:if test="${isEditable}">
								<span class="new_fctbox"> 
								<a href="javascript:;" class="ico f1" data-name='portrayal'  data-on="data-open">编辑</a>
								</span>
								</c:if>
							</div>
							<div class="new_ul_all new_top_color portrait_show">
								<span class="ico_dot ico"></span>
								<p id="portrait_show"></p>
							</div>
						</div>
						<div class='portrayal_on'>
							<div class="title_bj_tzjl">用户画像</div>
							<div id="portrait_editor" type="text/plain" class='width_fwb'></div>  
					                 <div class="compile_on_center">
					                    <div class="compile_on_right">
					                        <span class="pubbtn bluebtn" id="save_portrait">保存</span>
					                        <span class="pubbtn fffbtn" data-name='portrayal' data-on="close">取消</span>
					                    </div>  
					                </div>
						</div>
						
					</div>
					
					
					<!--产品服务-->
					<div class="tabtable_con_on">
						<div class='product_center'>
							<div class="new_r_compile ">
								<span class="new_ico_product"></span> <span class="new_color size16">产品服务</span> <span class="bj_ico" id="business_model">暂无数据</span>
								<c:if test="${isEditable}">
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='product' data-on="data-open">编辑</a>
								</span>
								</c:if>
							</div>
							<div class="new_ul_all new_top_color business_model_show">
								<span class="ico_dot ico"></span>
								<p id="business_model_show"></p>
							</div>
						</div>
						<div class='product_on'>
							<div class="title_bj_tzjl">产品服务</div>
							<div id="business_editor" type="text/plain" class='width_fwb' ></div>  
					                 <div class="compile_on_center">
					                    <div class="compile_on_right">
					                        <span class="pubbtn bluebtn" id="save_business">保存</span>
					                        <span class="pubbtn fffbtn" data-name='product' data-on="close">取消</span>
					                    </div>  
					                </div>
						</div>
					</div>
					
					<!--运营数据-->
					<div class="tabtable_con_on">
						<div class='operation_center'>
							<div class="new_r_compile ">
								<span class="new_ico_run"></span> <span class="new_color size16">运营数据</span> <span class="bj_ico" id="operational_data">暂无数据</span>
								<c:if test="${isEditable}">
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='operation' data-on="data-open">编辑</a>
								</span>
								</c:if>
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
					                        <span class="pubbtn bluebtn" id="save_operation">保存</span>
					                        <span class="pubbtn fffbtn" data-name='operation' data-on="close">取消</span>
					                    </div>  
					                </div>
						</div>
						
					</div>
					
					
					<!--行业分析-->
					<div class="tabtable_con_on">
						<div class='industry_center'>
							<div class="new_r_compile ">
								<span class="new_ico_industry"></span> <span class="new_color size16">行业分析</span> <span class="bj_ico" id="industry_analysis">暂无数据</span>
								<c:if test="${isEditable}">
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='industry' data-on="data-open">编辑</a>
								</span>
								</c:if>
							</div>
							<div class="new_ul_all new_top_color industry_analysis_show">
								<span class="ico_dot ico"></span>
								<p id="industry_analysis_show"></p>
							</div>
						</div>
						<div class='industry_on'>
							<div class="title_bj_tzjl">行业分析</div>
							<div id="industry_editor" type="text/plain" class='width_fwb' ></div>  
					                 <div class="compile_on_center">
					                    <div class="compile_on_right">
					                        <span class="pubbtn bluebtn" id="save_industry">保存</span>
					                        <span class="pubbtn fffbtn" data-name='industry' data-on="close">取消</span>
					                    </div>  
					                </div>
						</div>
						
					</div>
					<!--竞情分析-->
					<div class="tabtable_con_on">
						<div class='analysis_center'>
							<div class="new_r_compile ">
								<span class="new_ico_jq"></span> <span class="new_color size16">竞争分析</span> <span class="bj_ico" id="analysis">暂无数据</span>
								<c:if test="${isEditable}">
								<span class="new_fctbox"> 
									<a href="javascript:;" class="ico f1" data-name='analysis' data-on="data-open">编辑</a>
								</span>
								</c:if>
							</div>
							<div class="new_ul_all new_top_color analysis_show">
								<span class="ico_dot ico"></span>
								<p id="analysis_show"></p>
							</div>
						</div>
						<div class='analysis_on'>
							<div class="title_bj_tzjl">竞争分析</div>
							<div id="analysis_editor" type="text/plain" class='width_fwb'></div>  
					                 <div class="compile_on_center">
					                    <div class="compile_on_right">
					                        <span class="pubbtn bluebtn" id="save_analysis">保存</span>
					                        <span class="pubbtn fffbtn" data-name='analysis' data-on="close">取消</span>
					                    </div>  
					                </div>
						</div>
					</div>
					
					<!--下一轮融资路径-->
					<div class="tabtable_con_on">
						<div class='next_financing_center'>
							<div class="new_r_compile ">
								<span class="new_ico_nex"></span> <span class="new_color size16">下一轮融资路径</span> <span class="bj_ico" id="next_financing_source">暂无数据</span>
								<c:if test="${isEditable}">
								<span class="new_fctbox"> 
								<a href="javascript:;" class="ico f1" data-name='next_financing' data-on="data-open">编辑</a>
								</span>
								</c:if>
							</div>
							<div class="new_ul_all new_top_color next_financing_source_show">
								<span class="ico_dot ico"></span>
								<p id="next_financing_source_show"></p>
							</div>
						</div>
						<div class='next_financing_on'>
							<div class="title_bj_tzjl">下一轮融资路径</div>
							<script id="next_financing_editor" type="text/plain" class='width_fwb'></script>  
					                 <div class="compile_on_center">
					                    <div class="compile_on_right">
					                        <span class="pubbtn bluebtn" id="save_next_financing">保存</span>
					                        <span class="pubbtn fffbtn" data-name='next_financing' data-on="close">取消</span>
					                    </div>  
					                </div>
						</div>
						
					</div>
					<div class="compile_on_center">
	                	<div class="compile_on_left fr clearfix">
	                    	<span class="pubbtn bluebtn fl"  data-btn="pre">上一步</span>
	                        <span class="pubbtn bluebtn fl"  data-btn="next">下一步</span>
	                        <div class="fl pages">
	                        	<label class="current_page blue">2</label>/<label>4</label>
	                        </div>
	                    </div>
                    </div>
                </div>
                <div class="page clearfix" data-btn="page2">
                	<!--团队成员-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_book"></span>
                        <span class="new_color size16">团队成员</span>
                        <button class="blue fr add_history" href="tanchuan/historytc.jsp" data-btn="add_history" data-name="融资历史">添加</button>
                    </div>
                    <!-- 融资历史表格-->
                    <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                    	<tr>
                    		<th>姓名</th>
                    		<th>当前职务</th>
                    		<th>性别</th>
                    		<th>出生年月</th>
                    		<th>电话号码</th>
                    		<th>操作</th>
                    	</tr>
                    	<tr>
                    		<td>李铭</td>
                    		<td>技术总监</td>
                    		<td>男</td>
                    		<td>1988-09-08</td>
                    		<td>15880609508</td>
                    		<td>
	                    		<a class="meet_see blue" href="javascript:void(0)">查看</a>
	                    		<a class="meet_edit blue" href="javascript:void(0)">编辑</a>
	                    		<a class="meet_delete blue" href="javascript:void(0)">删除</a>
                    		</td>
                    	</tr>
                    </table>
					<!--股权结构-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_book"></span>
                        <span class="new_color size16">股权结构</span>
                        <button class="blue fr add_history" href="tanchuan/historytc.jsp" data-btn="add_history" data-name="融资历史">添加</button>
                    </div>
                    <!--法人信息-->
					<div class="legal">
					    <div class="legal_box">
					      <div class="title">
					          <span class="new_color size16">法人信息</span>
					      </div>
					      <form action="#" id="company-info-form">
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
					
					<!--股权结构-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_color size16">股权结构</span>
                        <button class="blue fr add_history" href="tanchuan/historytc.jsp" data-btn="add_history" data-name="融资历史">添加</button>
                    </div>
                    <!-- 融资历史表格-->
                    <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                    	<tr>
                    		<th>所有权人</th>
                    		<th>所有权人类型</th>
                    		<th>占比(%)</th>
                    		<th>出资额(万元)</th>
                    		<th>币种</th>
                    		<th>备注</th>
                    		<th>操作</th>
                    	</tr>
                    	<tr>
                    		<td>李铭</td>
                    		<td>12</td>
                    		<td>12</td>
                    		<td>12</td>
                    		<td>美元</td>
                    		<td>黑熊精...</td>
                    		<td>
	                    		<a class="meet_edit blue" href="javascript:void(0)">编辑</a>
	                    		<a class="meet_delete blue" href="javascript:void(0)">删除</a>
                    		</td>
                    	</tr>
                    </table>
                     <div class="compile_on_center">
	                	<div class="compile_on_left fr clearfix">
	                    	<span class="pubbtn bluebtn fl"  data-btn="pre">上一步</span>
	                        <span class="pubbtn bluebtn fl"  data-btn="next">下一步</span>
	                        <div class="fl pages">
	                        	<label class="current_page blue">3</label>/<label>4</label>
	                        </div>
	                    </div>
                    </div>
                </div>
                <div class="page" data-btn="page3">
                	<!--访谈记录-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_book"></span>
                        <span class="new_color size16">访谈记录</span>
                        <button class="blue fr add_history" href="tanchuan/historytc.jsp" data-btn="add_history" data-name="融资历史">添加</button>
                    </div>
                    <!-- 访谈记录表格-->
                    <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table table_interview">
                    	<tr>
                    		<th>访谈概况</th>
                    		<th>访谈记录</th>
                    		<th>操作</th>
                    	</tr>
                    	<tr>
                    		<td>
                    			<div style="text-align:left;padding:10px 0 10px 0px;">访谈时间：2016-10-28 11:11<br>访谈对象：<label class="meeting_result color_pass">通过</label><br>访谈录音：</div>
                    		</td>
                    		<td>12</td>
                    		<td>
                    			<a class="meet_see blue" href="javascript:void(0)">查看</a>
	                    		<a class="meet_edit blue" href="javascript:void(0)">编辑</a>
                    		</td>
                    	</tr>
                    </table>
                    <div class="compile_on_center">
	                	<div class="compile_on_left fr clearfix">
	                    	<span class="pubbtn bluebtn fl"  data-btn="pre">上一步</span>
	                        <span class="pubbtn bluebtn fl" >生成项目</span>
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
        <!--右边 end--> 
    </div>
     
</div>
<!--隐藏-->
<div class="bj_hui_on"></div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/teamSheetNew.js'></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/addPlanbusiness.js'></script>
<script src="<%=path %>/js/projectDetail/addProject.js" type="text/javascript"></script>
<script type="text/javascript">
//上一步，下一步
$('[data-btn="next"]').click(function(){
	var pageNum=$(this).parent().parent().parent().attr("data-btn");
	num=Number(pageNum.substr(pageNum.length-1,1));
	$("[data-btn='page"+(num+1)+"']").addClass("on").siblings().removeClass("on");
})
$('[data-btn="pre"]').click(function(){
	var prePageNum=$(this).parent().parent().parent().attr("data-btn");
	num=Number(prePageNum.substr(prePageNum.length-1,1));
	$("[data-btn='page"+(num-1)+"']").addClass("on").siblings().removeClass("on");
})
//日历
$('#company-info-form [name="formationDate"]').datepicker({
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

	/**
	 * @version 2016-06-21
	 */
	$('[data-on="compile"]').on('click',function(){
		$('.bj_hui_on').show();
		$('.compile_on').show();
	});
	/* $('[data-on="close"]').on('click',function(){
		forwardWithHeader(Constants.sopEndpointURL + "/galaxy/mpl");
	}); */
	/**
	 * 查询事业线
	 * @version 2016-06-21
	 */
	createCareelineOptions(platformUrl.getCareerlineList,"industryOwn","select");
	/**
	 * 获取融资状态下拉项
	 * @version 2016-06-21
	 */
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus", 17);
	
	
   var TOKEN;
   var formData;
	$(function(){
		$("#createDate").val(new Date().format("yyyy-MM-dd"));
		createMenus(5);
		//获取TOKEN 用于验证表单提交
		sendPostRequest(platformUrl.getToken,function(data){
			TOKEN=data.TOKEN;
			return TOKEN;
		});
		$("#formatShareRatio").blur(function(){
			var valuations = calculationValuations();
			if(valuations != null){
				$("#formatValuations").val(valuations.toFixed(2));
			}
		});
		$("#formatContribution").blur(function(){
			var valuations = calculationValuations();
			if(valuations != null){
				$("#formatValuations").val(valuations.toFixed(2));
			}
		});
		$('input:radio[name="projectType"]').click(function(){
			$("#projectTypeTip").css("display","none");
		});
		
	});
	function calculationValuations(){
		var projectShareRatio = $("#formatShareRatio").val();
		var projectContribution = $("#formatContribution").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			return projectContribution * (100/projectShareRatio);
		}
		return null;
	}
	
	function add(){
		var val=$('input:radio[name="projectType"]:checked').val();
		if(val == null || typeof(val) == "undefined"){
			$("#projectTypeTip").css("display","block");
			return;
		}
		var nowFormData = $("#add_form").serializeObject();
		if(formData != nowFormData){
			//获取TOKEN 用于验证表单提交
			sendPostRequest(platformUrl.getToken,function(data){
				TOKEN=data.TOKEN;
				return TOKEN;
			});
		}
		if(beforeSubmit()){
			sendPostRequestBySignJsonStr(platformUrl.addProject, $("#add_form").serializeObject(), function(data){
				if(!data){
					layer.msg("提交表单过于频繁!");
				}else if(data.result.status=="ERROR"){
					if(data.result.errorCode == "csds"){
						layer.msg("必要的参数丢失!");
					}else if(data.result.errorCode == "myqx"){
						layer.msg("没有权限添加项目!");
					}else if(data.result.errorCode == "mccf"){
						layer.msg("项目名重复!");
					}
					formData = $("#add_form").serializeObject();
				}else{
					forwardWithHeader(Constants.sopEndpointURL + "/galaxy/mpl");
				}
				
			},TOKEN);
		}
	}
	
	function setText(obj){
		if(obj=="set"){
			$("#faName").attr("style","display:inline-block;")
			$("#faName").removeAttr("allowNULL");
			$("#faName").focus();
			if($("#faName").val()=="请输入FA名称"){
				$("#faName").attr("style","color:#999;");
			}
		}else{
			$('.tip-yellowsimple').remove();
			$("#faName").val('');
			$("#faName").attr("allowNULL","yes");
			$("#faName").attr("style","display:none;");
		}
		
	}

	$("#faName").keydown(function(){
  		if(this.value=="请输入FA名称"){
  			this.value = "";
  		}
  		if(this.value!="请输入FA名称"){
  			$("#faName").attr("style","color:#333;");
  		}
		
	})
	$("#faName").blur(function(){
  		if(this.value==""){
  			this.value = "请输入FA名称";
  		}
  		if(this.value=="请输入FA名称"){
  			$("#faName").attr("style","color:#999;");
  		}
		
	})
	
</script>

</html>

