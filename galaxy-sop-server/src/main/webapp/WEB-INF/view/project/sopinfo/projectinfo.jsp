<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
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
        	<span class="new_color size18">Utter绝对潮流</span><span class="new_color">ID987786600009</span>
        	<span class="b_span"><a href="#">返回项目列表></a></span>
        </div>
        
        
        <div class="new_left">
        	<div class="tabtable assessment label_static">
          	<!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">基本信息</a></li>
                <li data-tab="nav"><a href="javascript:;">团队成员</a></li>
                <li data-tab="nav"><a href="javascript:;">股权结构</a></li>
                <li data-tab="nav"><a href="javascript:;">访谈记录</a></li>
                <li data-tab="nav"><a href="javascript:;">会议纪要</a></li>
                <li data-tab="nav"><a href="javascript:;">项目文档</a></li>
                <li data-tab="nav" class="no"><a href="javascript:;">操作日志</a></li>
            </ul>

            <!-- 基本信息 -->
			<div data-tab="con">
				<div class="tabtable_con">
					<div class="new_r_compile">
						<span class="new_fctbox"> 
							<a href="javascript:;" class="ico f1" data-on="compile">编辑</a>
						</span>
					</div>
					
					<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
						<tr>
							<td><span class="new_color_gray">项目名称：</span>
								<span class="new_color_black">Utter绝对潮流</span>
							</td>
							<td><span class="new_color_gray">创建时间：</span>
								<span class="new_color_black">2016-01-25</span>
							</td>
						</tr>
						
						<tr>
							<td><span class="new_color_gray">项目类型：</span><span class="new_color_black">外部投资</span></td>
							<td><span class="new_color_gray">最后编辑：</span><span class="new_color_black">2016-01-25</span></td>
						</tr>
						
						<tr>
							<td><span class="new_color_gray">行业归属：</span><span class="new_color_black">互联网旅游</span></td>
							<td><span class="new_color_gray">投资经理：</span>
								<span class="new_color_black">刘佳</span><span class="new_color_gray">（O2O及电商）</span></td>
						</tr>
						
						<tr>
							<td><span class="new_color_gray">融资状态：</span><span class="new_color_black">尚未获投</span></td>
							<td><span class="new_color_gray">项目进度：</span><span class="new_color_black">接触访谈</span>
								<span class="new_color_gray">（跟进中）</span><span class="new_bj "></span></td>
						</tr>
					</table>

					<!--融资计划-->
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_financing"></span> <span class="new_color size16">融资计划</span>
					</div>
					<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
						<tr>
							<td><span class="new_color_gray">融资金额：</span><span class="new_color_black">600万人民币</span></td>
							<td><span class="new_color_gray">项目估值：</span><span class="new_color_black">600万人民</span></td>
						</tr>
						<tr>
							<td><span class="new_color_gray">出让股份：</span><span class="new_color_black">90%</span></td>
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
				<!--商业计划书-->
				<div class="tabtable_con_on">
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_book"></span> <span class="new_color size16">商业计划书</span>
					</div>
					<ul class="new_ul_all">
						<li><span>《XXXXXXXXXXXXXXXXX》</span></li>
						<li><span class="new_color_gray">状态：</span><span class="new_color_black">已上传</span></li>
						<li><span class="new_color_gray">更新时间：</span><span class="new_color_black">2016-01-26</span></li>
						<li class="new_ul_right"><span class="new_fctbox"> <a href="javascript:;" class="ico f2" data-btn="describe">查看</a>
								<a href="javascript:;" class="ico new1" data-btn="edit">更新</a>
								<a href="javascript:;" class="ico new2" data-btn="describe">查看历史</a>
						</span></li>
					</ul>
				</div>
				<!--项目概述-->
				<div class="tabtable_con_on">
					<div class="new_r_compile new_bottom_color">
						<span class="new_ico_project"></span> <span class="new_color size16">项目概述</span> <span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					<div class="new_ul_all">
						<span class="ico_dot ico"></span>
						<p>纯文本内容仅支持换行空格纯文本内容仅支持换行空格纯文本内容仅支持换行空格纯文本内容仅支持换行空格纯文本内容仅支持换行空格纯文容仅支持换行空格纯文本内容仅支持换行空格纯文本内容仅支持换行空格纯文本内容仅支持换行空格纯文本内容仅支持换行空格纯文本内容仅支持纯文本内容仅支持换行空格纯文本内容仅支持换行空格纯文本内容仅</p>
					</div>
				</div>
				<!--公司定位-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_firm"></span> <span class="new_color size16">公司定位</span> <span class="bj_ico">暂无数据</span>
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
				</div>
				<!--用户画像-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_people"></span> <span class="new_color size16">用户画像</span> <span class="bj_ico">暂无数据</span>
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
				</div>
				<!--产品服务-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_product"></span> <span class="new_color size16">产品服务</span> <span class="bj_ico">暂无数据</span>
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
				</div>

				<!--运营数据-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_run"></span> <span class="new_color size16">运营数据</span>
						<span class="bj_ico">暂无数据</span> <span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
				</div>
				<!--行业分析-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_industry"></span> <span class="new_color size16">行业分析</span> <span class="bj_ico">暂无数据</span>
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
				</div>
				<!--竞情分析-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_jq"></span> <span class="new_color size16">竞情分析</span>
						<span class="bj_ico">暂无数据</span> <span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
				</div>

				<!--下一轮融资路径-->
				<div class="tabtable_con_on">
					<div class="new_r_compile ">
						<span class="new_ico_nex"></span> <span class="new_color size16">下一轮融资路径</span>
						<span class="bj_ico">暂无数据</span> <span class="new_fctbox">
							<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
				</div>
				
			</div>
					
					
					
					
					
			<!-- 团队成员 -->
			<div data-tab="con">
				<div class="tabtable_con">
					<div class="new_r_compile">
						<span class="new_fctbox"> <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
						</span>
					</div>
					
					团队成员

				</div>
			</div>



			<!-- 股权结构 -->
            <div  data-tab="con" >   
            	<div class="tabtable_con">
                    <jsp:include page="/galaxy/project/tabShares">
			        	<jsp:param value="<%=request.getAttribute(\"projectId\") %>" name="id"/>
			        </jsp:include>
                </div>                 
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
        <div class="new_right">
        	<b class="sj_ico null">三角</b>
        	<div class="new_right_flow">
            	<div class="new_right_flow_line">
                	<ul class="line_ul_p">
                    	<li class="line_ul_li"><span class="one_dot"></span></li>
                    	<li class="green_dot"><span class="gray_dot"></span>接触访谈</li>
                        <li class="green_dot"><span class="gray_dot"></span>内部评审</li>
                        <li class="green_dot"><span class="gray_dot"></span>CEO评审</li>
                        <li class="green_dot"><span class="green_dot_on"></span><span class="gray_dot"></span>立项会</li>
                        <li><span class="gray_dot"></span>投资意向书</li>
                        <li><span class="gray_dot"></span>尽职调查</li>
                        <li><span class="gray_dot"></span>投决会</li>
                        <li><span class="gray_dot"></span>投资协议</li>
                        <li><span class="gray_dot"></span>股权交割</li>
                    </ul>
                </div>
                 <span class="bluebtn new_btn" >项目流程</span>
            </div>
           
            
            <div class="correlation">相关操作</div>
            <div class="new_correlation_cen">
            	<span class="bluebtn new_btn" >否决项目</span>
            </div>
            
            <div class="correlation">近期会议纪要
				<span class="more null new_righ" id="platform_jxkh_more" style="cursor: pointer;" href="#">more</span>
			</div>
            <div class="new_correlation_cen">
            	<div class="new_b_bottom">
                	<ul><li class="new_b_li_one">投资意向会</li><li  class="new_b_li_two"><span class="color_pass">通过</span></li><li  class="new_b_li_three">2016-1-25</li></ul>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                	
                </div>
                <div class="new_b_bottom">
                	<ul><li class="new_b_li_one">投资意向会</li><li  class="new_b_li_two"><span class="color_undetermined">待定</span></li><li  class="new_b_li_three">2016-1-25</li></ul>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                </div>
                <div class="new_b_bottom">
                	<ul><li class="new_b_li_one">投资意向会</li><li  class="new_b_li_two"><span class="color_veto">否决</span></li><li  class="new_b_li_three">2016-1-25</li></ul>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                </div>
                
                
                
            </div>
            <div class="correlation">近期访谈记录
				<span class="more null new_righ" id="platform_jxkh_more" style="cursor: pointer;" href="#">more</span>
			</div>
            <div class="new_correlation_cen">
            	<div class="new_b_bottom">
                	<div class="div_ul"><span class="new_b_li_one">投资意向会</span><span  class="new_b_li_three pull-right">2016-1-25</span></div>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                </div>
                
                <div class="new_b_bottom">
                	<div class="div_ul"><span class="new_b_li_one">投资意向会</span><span  class="new_b_li_three pull-right">2016-1-25</span></div>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                </div>
            </div>
            
        </div>
        
        <!--右边 end-->
    </div>
 
</div>

<!--隐藏-->
<div class="bj_hui_on none"></div>
<div class="compile_on none">
	<div class="compile_on_center">
        <div class="compile_on_right">
            <span class="compile_on_right_b">保存</span>
            <span class="compile_on_right_q" data-ｏｎ="close">取消</span>
        </div>  
        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
            <tr>
                <td><span class="new_color_gray">项目名称：</span><span><input class="new_nputr" value="Utter绝对潮流"></input></span></td>
                <td><span class="new_color_gray">创建时间：</span><span class="new_color_black">2016-01-25</span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">项目类型：</span><span class="new_color_black">外部投资</span></td>
                <td><span class="new_color_gray">最后编辑：</span><span class="new_color_black">2016-01-25</span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">行业归属：</span><span><select class="new_nputr"><option>ddddd</option></select></span></td>
                <td><span class="new_color_gray">投资经理：</span><span class="new_color_black">刘佳</span><span class="new_color_gray">（O2O及电商）</span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">融资状态：</span><span><input class="new_nputr" value="Utter绝对潮流"></input></span></td>
                <td><span class="new_color_gray">项目进度：</span><span class="new_color_black">接触访谈</span><span class="new_color_gray">（跟进中）</span><span class="new_bj "></span></td>
            </tr>
        </table>  
        
        <!--融资计划-->
        <div class="new_r_compile new_bottom_color">
            <span class="new_ico_financing"></span>
            <span class="new_color size16">融资计划</span>
        </div>  
        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
            <tr>
                <td><span class="new_color_gray">融资金额：</span><span class="new_color_black"><input class="new_nputr_number" />　万人民币</span></td>
                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input class="new_nputr_number" />　万人民</span></td>
            </tr>
            <tr>
                <td><span class="new_color_gray">出让股份：</span><span class="new_color_black"><input class="new_nputr_number" />　90%</span></td>
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

<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="js/axure.js" type="text/javascript"></script>
<script src="js/axure_ext.js" type="text/javascript"></script>
<script>
	$('[data-ｏｎ="compile"]').on('click',function(){
		$('.bj_hui_on').show();
		$('.compile_on').show();
	})
	$('[data-ｏｎ="close"]').on('click',function(){
		$('.bj_hui_on').hide();
		$('.compile_on').hide();
	})

</script>
</html>
