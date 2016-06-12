<%@ page language="java" pageEncoding="UTF-8"%>
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

<!-- jsp文件头和头部 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>

<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />

<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/sopinfo.js"></script>
<script type="text/javascript">
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
 	
    	<div class="new_tit_a"><a href="#">工作桌面</a>><a href="#">创投项目</a>>Utter绝对潮流</div>
    	
    	<div class="new_tit_b">
        	<span class="new_color size18" id="project_name_title"></span><span class="new_color" id="project_code">ID987786600009</span>
        	<span class="b_span"><a href="#">返回项目列表></a></span>
        </div>
        
        
        <div class="new_left">
        	<div class="tabtable assessment label_static">
          	<!-- tab标签 -->
            <ul class="tablink">
                <li class="on"><a href="javascript:;">基本信息</a></li>
                <li><a href="javascript:;" onClick="changeTab(Constants.sopEndpointURL+'/galaxy/personTab/','${projectId}')" >团队成员</a></li>
                <li><a href="javascript:;" onClick="showTabs(${projectId},2)">股权结构</a></li>
                <li><a href="javascript:;" onclick="showTabs(${projectId},3)">访谈记录</a></li>
                <li><a href="javascript:;" onclick="showTabs(${projectId},4)">会议纪要</a></li>
                <li><a href="javascript:;">项目文档</a></li>
                <li><a href="javascript:;">操作日志</a></li>
            </ul>

            <!-- 基本信息 -->
			<div>
				<div class="tabtable_con tabtable_con_jbxx">
				<!-- 默认展示 -->
				<div class="compile_default">
					<div class="new_r_compile">
						<span class="new_fctbox"> 
							<a href="javascript:;" class="ico f1" data-on="compile">编辑</a>
						</span>
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
							<td><span class="new_color_gray">行业归属：</span><span class="new_color_black">互联网旅游</span></td>
							<td><span class="new_color_gray" >投资经理：</span>
								<span class="new_color_black" id="createUname"></span><span class="new_color_gray" id="projectCareerline"></span></td>
						</tr>
						
						<tr>
							<td><span class="new_color_gray">融资状态：</span><span class="new_color_black" id="financeStatusDs"></span></td>
							<td><span class="new_color_gray">项目进度：</span><span class="new_color_black" id="projectProgress"></span>
								<span class="new_color_gray" id="projectStatusDs"></span><span class="new_bj "></span></td>
						</tr>
					</table>

					<!--融资计划-->
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_financing"></span> <span class="new_color size16">融资计划</span>
					</div>
					<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
						<tr>
							<td><span class="new_color_gray">融资金额：</span><span class="new_color_black" id="project_contribution"></span></td>
							<td><span class="new_color_gray">项目估值：</span><span class="new_color_black" id="project_valuations"></span></td>
						</tr>
						<tr>
							<td><span class="new_color_gray">出让股份：</span><span class="new_color_black" id="project_share_ratio"></span></td>
						</tr>
					</table>
					
					<!--实际投资-->
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_practical"></span> <span class="new_color size16">实际投资</span>
					</div>
					<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
						<tr>
							<td><span class="new_color_gray">投资金额：</span><span class="new_color_black">600万人民币</span></td>
							<td><span class="new_color_gray">项目估值：</span><span class="new_color_black">600万人民</span></td>
						</tr>
						<tr>
							<td><span class="new_color_gray">股权占比：</span><span class="new_color_black">90%</span></td>
						</tr>
					</table>
					</div>
					<!-- 编辑页面 -->
					<div class="compile_on">
	<div class="compile_on_center">
        <div class="compile_on_right">
            <span class="compile_on_right_b"  data-on="save">保存</span>
            <span class="compile_on_right_q" data-on="close">取消</span>
        </div>  
        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
            <tr>
                <td><span class="new_color_gray">项目名称：</span><span><input class="new_nputr"  id="project_name_edit"></input></span></td>
                <td><span class="new_color_gray">创建时间：</span><span class="new_color_black" id="create_date_edit"></span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">项目类型：</span><span class="new_color_black" id="projectType_edit"></span></td>
                <td><span class="new_color_gray">最后编辑：</span><span class="new_color_black" id="updateDate_edit"></span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">行业归属：</span><span><select class="new_nputr" id="industry_own_sel"></select></span></td>
                <td><span class="new_color_gray">投资经理：</span><span class="new_color_black" id="createUname_edit"></span><span class="new_color_gray" id="projectCareerline_edit"></span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">融资状态：</span><span><select class="new_nputr" id="finance_status_sel"></select></span></td>
                <td><span class="new_color_gray">项目进度：</span><span class="new_color_black" id="projectProgress_edit"></span><span class="new_color_gray" id="projectStatusDs_edit"></span><span class="new_bj "></span></td>
            </tr>
        </table>  
        
        <!--融资计划-->
        <div class="new_r_compile new_bottom_color">
            <span class="new_ico_financing"></span>
            <span class="new_color size16">融资计划</span>
        </div>  
        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
            <tr>
                <td><span class="new_color_gray">融资金额：</span><span class="new_color_black"><input class="new_nputr_number" id="project_contribution_edit" />　万人民币</span></td>
                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input class="new_nputr_number" id="project_valuations_edit" />　万人民</span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">出让股份：</span><span class="new_color_black"><input class="new_nputr_number" id="project_share_ratio_edit" />　%</span></td>
            </tr>
        </table>
        <!--实际投资-->
        <div class="new_r_compile new_bottom_color">
            <span class="new_ico_practical"></span>
            <span class="new_color size16">实际投资</span>
        </div>  
        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
            <tr>
                <td><span class="new_color_gray">投资金额：</span><span class="new_color_black"><input class="new_nputr_number" />　万人民币</span></td>
                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input class="new_nputr_number" />　万人民</span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">股权占比：</span><span class="new_color_black"><input class="new_nputr_number" />　%</span></td>
            </tr>
        </table>
    </div>
</div>
				</div>
				<!--商业计划书-->
				<div class="tabtable_con_on">
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_book"></span> <span class="new_color size16">商业计划书</span>
					</div>
					<ul class="new_ul_all" id='business_plan'>
						<li><span>《XXXXXXXXXXXXXXXXX》</span></li>
						<li><span class="new_color_gray">状态：</span><span class="new_color_black">已上传</span></li>
						<li><span class="new_color_gray">更新时间：</span><span class="new_color_black">2016-01-26</span></li>

						<li class="new_ul_right"><span class="new_fctbox"> <a href="javascript:;" class="ico f2" data-btn="describe">查看</a>
								<a href="javascript:;" class="ico new1" data-btn="edit" id="uploadOperator">更新</a>
								<a href="javascript:;" class="ico new2" data-btn="describe">查看历史</a>
						</span></li>
					</ul>
				</div>
				<!--项目概述-->
				<div class="tabtable_con_on">
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_project"></span> <span class="new_color size16">项目概述</span> <span class="bj_ico" id="descript">暂无数据</span><span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p id="describe_show"></p>
					</div>
				</div>
				<!--公司定位-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_firm"></span> <span class="new_color size16">公司定位</span> <span class="bj_ico" id="location">暂无数据</span>
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p id="location_show"></p>
					</div>
				</div>
				<!--用户画像-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_people"></span> <span class="new_color size16">用户画像</span> <span class="bj_ico" id="portrait">暂无数据</span>
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p id="portrait_show"></p>
					</div>
				</div>
				<!--产品服务-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_product"></span> <span class="new_color size16">产品服务</span> <span class="bj_ico" id="business_model">暂无数据</span>
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p id="business_model_show"></p>
					</div>
				</div>

				<!--运营数据-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_run"></span> <span class="new_color size16">运营数据</span>
						<span class="bj_ico" id="operational_data">暂无数据</span> <span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p id="operational_data_show"></p>
					</div>
				</div>
				<!--行业分析-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_industry"></span> <span class="new_color size16">行业分析</span> <span class="bj_ico" id="industry_analysis">暂无数据</span>
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p id="industry_analysis_show"></p>
					</div>
				</div>
				<!--竞情分析-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_jq"></span> <span class="new_color size16">竞情分析</span>
						<span class="bj_ico" id="analysis">暂无数据</span> <span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p id="analysis_show"></p>
					</div>
				</div>

				<!--下一轮融资路径-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_nex"></span> <span class="new_color size16">下一轮融资路径</span>
						<span class="bj_ico" id="next_financing_source">暂无数据</span> <span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p id="next_financing_source_show"></p>
					</div>
				</div>
				
			</div>
					
					
					
					
					
			<!-- 团队成员 -->
			<div id="cur_tab" data-tab="con">
				
			</div>



			<!-- 股权结构 -->
            <div  data-tab="con" >   
            </div>
            
            
            
             <!-- 访谈记录 -->
            <div  data-tab="con" >   
            	<div class="tabtable_con">
                    <div class="new_r_compile">
                        <span class="new_fctbox">
                            <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                          </span>
                    </div>  
                    	访谈记录  
                </div>                 
            </div>
            
            
             <!-- 会议纪要 -->
            <div  data-tab="con" >   
            	<div class="tabtable_con">
                    <div class="new_r_compile">
                        <span class="new_fctbox">
                            <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                          </span>
                    </div>  
                    	会议纪要  
                </div>                 
            </div>
            
            
             <!-- 项目文档 -->
            <div  data-tab="con" >   
            	<div class="tabtable_con">
                    <div class="new_r_compile">
                        <span class="new_fctbox">
                            <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                          </span>
                    </div>  
                    	项目文档  
                </div>                 
            </div>
            
            
             <!-- 操作日志 -->
            <div  data-tab="con" >   
            	<div class="tabtable_con">
                    <div class="new_r_compile">
                        <span class="new_fctbox">
                            <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                          </span>
                    </div>  
                  	  操作日志  
                </div>                 
            </div>
            <!--tab end-->
          </div>
        </div>
        
        
        
       <!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>
        
    </div>
 
</div>

<!--隐藏-->
<div class="bj_hui_on"></div>

<jsp:include page="../../common/uploadwin.jsp" flush="true"></jsp:include>
<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
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
	
	
	$('[data-on="compile"]').on('click',function(){
		$('.bj_hui_on').show();
		$('.compile_default').hide();
		$('.compile_on').show();
	})
	$('[data-on="close"]').on('click',function(){
		$('.bj_hui_on').hide();
		$('.compile_default').show();
		$('.compile_on').hide();
	})
})


	



</script>
</html>
