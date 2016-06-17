<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
	Long projectId = (Long)request.getAttribute("projectId");
// 	System.out.println(projectId);
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>项目详情</title>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>

<!-- 富文本编辑器 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>



<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />

<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">


<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/sopinfo.js"></script>

<script type="text/javascript">
var isCreatedByUser = "${fx:isCreatedByUser('project',projectId) }";
var projectInfo;
$(function(){
	createMenus(4);
	var dtd = $.Deferred();
	$.when(getProjectInfo(dtd))
	.done(function(){
		//initProjectData();
	});
});
/**
*项目信息
*
**/
function getProjectInfo(dtd)
{
	var hasDtd = typeof(dtd) != 'undefined';
	var url = platformUrl.detailProject+"${projectId}";
	sendGetRequest(
		url,
		null,
		function(data){
			projectInfo = data.entity;
			if(hasDtd)
			{
				dtd.resolve();
			}
		}
	);
	if(hasDtd)
	{
		return dtd.promise();
	}
}


</script>
</head>


<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
<div class="ritmin">
 	
    	<jsp:include page="sopcommon.jsp" flush="true"></jsp:include>
        
	<div class="new_left">
       	<div class="tabtable assessment label_static1">
          	<!-- tab标签 -->
            <ul class="tablink">
                <li class="on"><a href="javascript:;" onclick="showTabs(${projectId},0)">基本信息</a></li>
                <c:choose>
                <c:when test="${fx:hasRole(1) || fx:hasRole(2) || fx:hasRole(3)|| fx:isCreatedByUser('project',projectId) }">
                <li><a href="javascript:;" onClick="showTabs(${projectId},1)" >团队成员</a></li>
                <li><a href="javascript:;" onClick="showTabs(${projectId},2)">股权结构</a></li>
                <li><a href="javascript:;" onclick="showTabs(${projectId},3)">访谈记录</a></li>
                <li><a href="javascript:;" onclick="showTabs(${projectId},4)">会议纪要</a></li>
				<li><a href="javascript:;" onclick="showTabs(${projectId},5)">项目文档</a></li>
                <li><a href="javascript:;" onclick="showTabs(${pid},6)">操作日志</a></li> 
                </c:when>
                <c:otherwise>
                <li class="no"><a href="javascript:;">团队成员</a></li>
                <li class="no"><a href="javascript:;">股权结构</a></li>
                <li class="no"><a href="javascript:;">访谈记录</a></li>
                <li class="no"><a href="javascript:;">会议纪要</a></li>
				<li class="no"><a href="javascript:;">项目文档</a></li>
                <li class="no"><a href="javascript:;">操作日志</a></li> 
                </c:otherwise>
                </c:choose>
            </ul>

            <!-- 基本信息 -->
			<div class="tabtable_con tabtable_con_jbxx">
				<!-- 默认展示 -->
				<div class="basic_center">
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_basic"></span> <span class="new_color size16">基本信息</span>
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
						<span class="new_fctbox"> 
							<a href="javascript:;" class="ico f1" data-name="basic" data-on="data-open">编辑</a>
						</span>
					</c:if>
					</div>
					<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
					 <input type="hidden" id="pid" name="id" value="${projectId}"/>
						<tr>
							<td><span class="new_color_gray">项目名称：</span>
								<span class="new_color_black" id="project_name"></span>
							</td>
							<td><span class="new_color_gray">创建时间：</span>
								<span class="new_color_black" id="create_date"></span>
							</td>
						</tr>
						
						<tr>
							<td><span class="new_color_gray" >项目类型：</span><span class="new_color_black" id="projectType"></span></td>
							<td><span class="new_color_gray">最后编辑：</span><span class="new_color_black" id="updateDate"></span></td>
						</tr>
						
						<tr>
							<td><span class="new_color_gray">行业归属：</span><span class="new_color_black" id="industryOwnDs"></span></td>
							<td><span class="new_color_gray" >投资经理：</span>
								<span class="new_color_black" id="createUname"></span><span>(</span><span class="new_color_gray" id="projectCareerline"></span><span>)</span></td>
						</tr>
						
					<tr>
							<td><span class="new_color_gray">融资状态：</span><span class="new_color_black" id="financeStatusDs"></span></td>
							<td><span class="new_color_gray">项目进度：</span><span class="new_color_black" id="projectProgress"></span>
							<span>(</span>
								<span class="new_color_gray" id="projectStatusDs"></span></span><span>)</span><span id="insertImg"></span></td>
						</tr>
					
					</table>

					<!--融资计划-->
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_financing"></span> <span class="new_color size16">融资计划</span>
					</div>
					<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
						<tr>
							<td><span class="new_color_gray">融资金额：</span><span class="new_color_black" id="project_contribution"></span><span>&nbsp;万元人民币</span></td>
							<td><span class="new_color_gray">项目估值：</span><span class="new_color_black" id="project_valuations"></span><span>&nbsp;万元人民币</span></td>
						</tr>
						<tr>
							<td><span class="new_color_gray">出让股份：</span><span class="new_color_black" id="project_share_ratio"></span><span>&nbsp;%</span></td>
						</tr>
					</table>
					
					<!--实际投资-->
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_practical"></span> <span class="new_color size16">实际投资</span>
					</div>
					<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
						<tr>
							<td><span class="new_color_gray">投资金额：</span><span class="new_color_black" id="finalContribution"></span><span>&nbsp;万元人民币</span></td>
							<td><span class="new_color_gray">项目估值：</span><span class="new_color_black" id="finalValuations"></span><span>&nbsp;万元人民币</span></td>
						</tr>
						<tr>
							<td><span class="new_color_gray">股权占比：</span><span class="new_color_black" id="finalShareRatio"></span><span>&nbsp;%</span></td>
						</tr>
					</table>
				</div>
				<!-- 编辑页面 -->
				<div class="basic_on">
					<div class="compile_on_center">
				        <div class="compile_on_right">
				            <span class="compile_on_right_b"  data-on="save">保存</span>
				            <span class="compile_on_right_q" data-name="basic" data-on="close">取消</span>
				        </div> 
				        <div class="new_r_compile new_bottom_color">
							<span class="new_ico_basic"></span> <span class="new_color size16">基本信息</span>
						</div> 
				        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
				            <tr>
				                <td><span class="new_color_gray">项目名称：</span><span><input class="new_nputr"  size="20" id="project_name_edit" valType="required" msg="<font color=red>*</font>项目名称不能为空"></input></span></td>
				                <td><span class="new_color_gray">创建时间：</span><span class="new_color_black" id="create_date_edit"></span></td>
				            </tr>
				            <tr>
				                <td><span class="new_color_gray">项目类型：</span><span class="new_color_black" id="projectType_edit"></span></td>
				                <td><span class="new_color_gray">最后编辑：</span><span class="new_color_black" id="updateDate_edit"></span></td>
				            </tr>
				            <tr>
				                <td><span class="new_color_gray">行业归属：</span><span><select class="new_nputr" id="industry_own_sel" ></select></span></td>
				                <td><span class="new_color_gray">投资经理：</span><span class="new_color_black" id="createUname_edit"></span><span>(</span><span class="new_color_gray" id="projectCareerline_edit"></span><span>)</span></td>
				            </tr>
				            <tr>
                                <td><span class="new_color_gray">融资状态：</span><span><select class="new_nputr" id="finance_status_sel"></select></span></td>
                                 <td><span class="new_color_gray">项目进度：</span><span class="new_color_black" id="projectProgress_edit"></span><span>(</span><span class="new_color_gray" id="projectStatusDs_edit"></span><span>)</span><span id="editImg" class="" style="overflow:hidden;"></span></td>
                            </tr>
				        </table>  
				        
				        <!--融资计划-->
				        <div class="new_r_compile new_bottom_color">
				            <span class="new_ico_financing"></span>
				            <span class="new_color size16">融资计划</span>
				        </div>  
				       <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
				            <tr>
				                <td><span class="new_color_gray">融资金额：</span><span class="new_color_black"><input class="new_nputr_number" size="20"  id="project_contribution_edit" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>只能为整数或两位小数点的数字"/>　&nbsp;万元人民币</span></td>
				                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input  class="new_nputr_number" id="project_valuations_edit" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>只能为整数或两位小数点的数字"/>&nbsp;　万元人民币</span></td>
				            </tr>
				            <tr>
				                <td><span class="new_color_gray">出让股份：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="project_share_ratio_edit" allowNULL="yes" valType="OTHER" regString="^(\d{1,2}(\.\d{1,4})?)$" msg="<font color=red>*</font>请输入0-100的整数,小数点后四位"/>　&nbsp;%</span></td>
				            </tr>
				        </table>
				        <!--实际投资-->
				        <div class="new_r_compile new_bottom_color">
				            <span class="new_ico_practical"></span>
				            <span class="new_color size16">实际投资</span>
				        </div>  
				        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
				            <tr>
				                <td><span class="new_color_gray">投资金额：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="finalContribution_edit" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>只能为整数或两位小数点的数字"/>&nbsp;　万元人民币</span></td>
				                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input  class="new_nputr_number" id="finalValuations_edit" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>只能为整数或两位小数点的数字"/>&nbsp;　万元人民币</span></td>
				            </tr>
				            <tr>
				                <td><span class="new_color_gray">股权占比：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="finalShareRatio_edit" allowNULL="yes" valType="OTHER" regString="^(\d{1,2}(\.\d{1,4})?)$" msg="<font color=red>*</font>请输入0-100的整数,小数点后四位"/>&nbsp;　%</span></td>
				     		</tr>
				        </table>
				    </div>
				</div>
			</div>
			<!--商业计划书-->
			<div class="tabtable_con_on">
				<div class="new_r_compile ">
					<span class="new_ico_book"></span> <span class="new_color size16">商业计划书</span>
				</div>
				<ul class="new_ul_all new_top_color" id='business_plan'>
					<li></li>
					<li></li>
					<li></li>

					<li class="new_ul_right"><span class="new_fctbox"> <a href="javascript:;" class="ico f2" data-btn="describe">查看</a>
							<a href="javascript:;" class="ico new1" data-btn="edit" id="uploadOperator">更新</a>
							<a href="javascript:;" class="ico new2" data-btn="describe">查看历史</a>
					</span></li>
				</ul>
			</div>
			<!--项目概述-->
			<div class="tabtable_con_on">
				<div class="project_on">
                      <div id="describe_editor" type="text/plain" style="min-width:860px;height:200px; "></div>  
                        <div class="compile_on_center">
                           <div class="compile_on_right">
                               <span class="compile_on_right_b" id="save_describe">保存</span>
                               <span class="compile_on_right_q" data-name="project" data-on="close">取消</span>
                           </div>  
                       </div>
                 </div>
                 <div class="project_center">
					<div class="new_r_compile ">
						<span class="new_ico_project"></span> <span class="new_color size16">项目概述</span> <span class="bj_ico" id="descript">暂无数据</span>
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
						<span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-name="project" data-on="data-open">编辑</a>
						</span>
						</c:if>
					</div>
					<div class="new_ul_all new_top_color describe_show">
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
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
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
					<div id="company_editor" type="text/plain" style="min-width:860px;height:200px; "></div>  
                    <div class="compile_on_center">
                       <div class="compile_on_right">
                           <span class="compile_on_right_b" id="save_location">保存</span>
                           <span class="compile_on_right_q" data-name='company' data-on="close">取消</span>
                       </div>  
                   </div>
				</div>
				
			</div>
			<!--用户画像-->
			<div class="tabtable_con_on">
				<div class='portrayal_center'>
					<div class="new_r_compile ">
						<span class="new_ico_people"></span> <span class="new_color size16">用户画像</span> <span class="bj_ico" id="portrait">暂无数据</span>
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
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
					<div id="portrait_editor" type="text/plain" style="min-width:860px;height:200px; "></div>  
                    <div class="compile_on_center">
                       <div class="compile_on_right">
                           <span class="compile_on_right_b" id="save_portrait">保存</span>
                           <span class="compile_on_right_q" data-name='portrayal' data-on="close">取消</span>
                       </div>  
                   </div>
				</div>
				
			</div>
			
			
			<!--产品服务-->
			<div class="tabtable_con_on">
				<div class='product_center'>
					<div class="new_r_compile ">
						<span class="new_ico_product"></span> <span class="new_color size16">产品服务</span> <span class="bj_ico" id="business_model">暂无数据</span>
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
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
					<div id="business_editor" type="text/plain" style="min-width:860px;height:200px; "></div>  
                    <div class="compile_on_center">
                       <div class="compile_on_right">
                           <span class="compile_on_right_b" id="save_business">保存</span>
                           <span class="compile_on_right_q" data-name='product' data-on="close">取消</span>
                       </div>  
                   </div>
				</div>
				
			</div>

			<!--竞情分析-->
			<div class="tabtable_con_on">
				<div class='analysis_center'>
					<div class="new_r_compile ">
						<span class="new_ico_jq"></span> <span class="new_color size16">竞情分析</span> <span class="bj_ico" id="analysis">暂无数据</span>
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
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
					<div id="analysis_editor" type="text/plain" style="min-width:860px;height:200px; "></div>  
                    <div class="compile_on_center">
                       <div class="compile_on_right">
                           <span class="compile_on_right_b" id="save_analysis">保存</span>
                           <span class="compile_on_right_q" data-name='analysis' data-on="close">取消</span>
                       </div>  
                   </div>
				</div>
				
			</div>
			
			<!--运营数据-->
			<div class="tabtable_con_on">
				<div class='operation_center'>
					<div class="new_r_compile ">
						<span class="new_ico_run"></span> <span class="new_color size16">运营数据</span> <span class="bj_ico" id="operational_data">暂无数据</span>
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
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
					<div id="operation_editor" type="text/plain" style="min-width:860px;height:200px; "></div>  
                    <div class="compile_on_center">
                       <div class="compile_on_right">
                           <span class="compile_on_right_b" id="save_operation">保存</span>
                           <span class="compile_on_right_q" data-name='operation' data-on="close">取消</span>
                       </div>  
                   </div>
				</div>
				
			</div>
			
			
			<!--行业分析-->
			<div class="tabtable_con_on">
				<div class='industry_center'>
					<div class="new_r_compile ">
						<span class="new_ico_industry"></span> <span class="new_color size16">行业分析</span> <span class="bj_ico" id="industry_analysis">暂无数据</span>
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
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
					<div id="industry_editor" type="text/plain" style="min-width:860px;height:200px; "></div>  
                    <div class="compile_on_center">
                       <div class="compile_on_right">
                           <span class="compile_on_right_b" id="save_industry">保存</span>
                           <span class="compile_on_right_q" data-name='industry' data-on="close">取消</span>
                       </div>  
                   </div>
				</div>
				
			</div>
			
			
			

			<!--下一轮融资路径-->
			<div class="tabtable_con_on">
				<div class='next_financing_center'>
					<div class="new_r_compile ">
						<span class="new_ico_nex"></span> <span class="new_color size16">下一轮融资路径</span> <span class="bj_ico" id="next_financing_source">暂无数据</span>
						<c:if test="${fx:isCreatedByUser('project',projectId)}">
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
					<div id="next_financing_editor" type="text/plain" style="min-width:860px;height:200px; "></div>  
                    <div class="compile_on_center">
                       <div class="compile_on_right">
                           <span class="compile_on_right_b" id="save_next_financing">保存</span>
                           <span class="compile_on_right_q" data-name='next_financing' data-on="close">取消</span>
                       </div>  
                   </div>
				</div>
				
			</div>
			
			
			
		</div>
	</div>
        
        
        
       <!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>
        
</div>
 
</div>

<!--隐藏-->
<div class="bj_hui_on"></div>


<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/teamSheetNew.js"></script>
<script src="<%=path %>/js/projectDetail.js"></script>
<script src="<%=path %>/js/planbusiness.js"></script>
<script src="<%=path %>/js/person.js"></script>
<script>

var projectId = <%=projectId%>;

$(function(){
	createMenus(5);
	UM.getEditor('editor');
	UM.getEditor('describe_editor');
	UM.getEditor('company_editor');
	UM.getEditor('portrait_editor');
	UM.getEditor('operation_editor');
	UM.getEditor('business_editor');
	UM.getEditor('industry_editor');
	UM.getEditor('analysis_editor');
	UM.getEditor('next_financing_editor');
	//统一显示
	$('[data-on="data-open"]').on('click',function(){
		var open=$(this).attr('data-name')
		//alert(open)
		$('.'+open+'_on').show();
		$('.'+open+'_center').hide();
		$('.bj_hui_on').show();
	})
	//统一关
	$('[data-on="close"]').on('click',function(){
		var close=$(this).attr('data-name')
		$('.'+close+'_on').hide();
		$('.'+close+'_center').show();
		$('.bj_hui_on').hide();
		$('.tip-yellowsimple').hide();
	})
	
})



	



</script>

<jsp:include page="../../common/uploadwin.jsp" flush="true"></jsp:include>
</html>
