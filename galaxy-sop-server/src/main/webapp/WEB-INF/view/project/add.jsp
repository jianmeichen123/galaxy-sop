<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
<!-- 校验样式 -->
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/reset.css" /> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!-- 日历插件 -->
<%-- <link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForHour.js"></script> --%>
<!-- 日历插件 -->

<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/> 
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<!-- 日历2 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForHour.js"></script>



<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script> 
<style>
	body{
		background-color:#E9EBF2;
	}
	.after{
		position:relative;
	}
	.after::after{content: "万元";
    font-family: "Microsoft YaHei";
    font-size: 12px;
    color: #666;
    position: absolute;
    right: 15px;
    top: 1px;
	}
	.after2::after{content: "%";} 
</style>
</head>

<body >

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
		<div class="new_tit_b add-project-title">
            <span>添加项目</span>
		</div>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin add-project-ritmin">
		<%-- <div class="new_tit_a"><a href="#">工作桌面</a><img alt="" src="<%=path %>/img/arrow-brumd.png" class="arrow"/><a href="#">创投项目</a><img alt="" src="<%=path %>/img/arrow-brumd.png" class="arrow"/>添加项目</div> --%>
        <div class="new_left add-poject-right">
        	<div class="tabtable_con_on add-project-tabtable">
                    <!--基本信息-->
                 <form action="" id="add_form" method="post" autocomplete="off">
                  <div class='addpro-basic-content'>
                    <div class="addpro-new-title ">
                        <span class="new_color  add-pro-basicmessage">基本信息</span>
                    </div>  
                    <ul class="basic_ul addpro-basi-ul clearfix">
                    	<li>
                        	<span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>项目类型：</span></span>
                            <span class="m_r30 inpu-self inpu-self-checked"><input class='inpu-radio' name="projectType" type="radio" value="projectType:1" id="radio_w" checked='checked'><label for="radio_w">投资</label></span>
                            <span class="m_r30 inpu-self"><input class='inpu-radio' name="projectType" type="radio" value="projectType:3" id="radio_n"><label for="radio_n">直营</label></span>
                            <span class="basic_span addpro-basic-span addpro-left"><em class="red">*</em><span class='letter-space'>创建时间：</span></span>
                            <span class="m_r30"><input style='display: inline-block;vertical-align: middle;' type="text" class='datepicker-text addpro-input' name="createDate" id="createDate" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/><img style='display: inline-block;vertical-align: middle;margin-left:10px;' title="创建时间指的是投资业务人员首次接触该项目的时间，并非项目成立时间 " src="/sop/img/sop_progress/remindG-icon.png" class="alertImg"></span>
                        </li>
                        <li class='projectSourceli clearfix'>
                            <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>项目名称：</span></span>
                            <span class="m_r30"><input type="text" class='addpro-input' maxlength="24" id="projectName" name="projectName" <%-- data-msg-required="<font color=red>*</font>项目名称不能为空" --%>/><label class='project-name'></label></span>
                       		<span class="basic_span addpro-basic-span addpro-marin-lt"><em class="red">*</em><span class='letter-space rzlc_span'>本轮融资轮次：</span></span>
                            <span class="m_r30 selectcheck select">
								<select name="financeStatus" class=' '   data-title-id="1108" data-type="14">
									<!-- <option value="">请选择</option> -->
			                    </select>
							</span>
                        </li>
                        <li class="projectSourceli clearfix">
                        	
                            <span class="basic_span addpro-basic-span "><em class="red">*</em><span class='letter-space'>项目来源：</span></span>
                            <span class="m_r30  selectcheck select" >
	                            <select name="proSource" class="proSource" data-title-id="1120" data-type="14" >
				                    	<option value="">请选择</option>
				                </select> 
                       		</span>                       		
                       		<span class="basic_span addpro-basic-span addpro-marin-lt"><em class="red">*</em><span class='letter-space'>行业归属：</span></span>
                            <span class="m_r30 selectcheck select">
                            	<select name="industryOwn"   >
			                    	<option value="">请选择</option>
			                    </select>
                            </span>
                        </li>
                        <li class="projectSourceli clearfix">
                        <div class="projectSource projectSource5">
                        		 <span class="basic_span addpro-basic-span"><span class='letter-space'>合投机构名称 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1121" data-type="1"   maxlength="50"  placeholder="请输入合投机构名称" id="proS6" name="proS5"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource6">
                        		 <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>FA名称 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1122" data-type="1"   maxlength="20"  placeholder="请输入FA名称（必填）" id="proS6" name="proS6"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource7">
                        		 <span class="basic_span addpro-basic-span"><span class='letter-space'>孵化器名称 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1123" data-type="1" maxlength="50" placeholder="请输入孵化器名称" id="proS7" name="proS7"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource8">
                        		 <span class="basic_span addpro-basic-span"><span class='letter-space'>机构及路演名称：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1124" data-type="1" maxlength="50" placeholder="请输入机构及路演名称" id="proS8" name="proS8"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource9">
                        		 <span class="basic_span addpro-basic-span"><span class='letter-space'>创投机构名称 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1125" data-type="1" maxlength="50" placeholder="请输入创投机构名称" id="proS9" name="proS9"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource10">
                        		 <span class="basic_span addpro-basic-span"><span class='letter-space'>媒体名称 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1126" data-type="1" maxlength="50" placeholder="请输入媒体名称" id="proS10" name="proS10"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource11">
                        		 <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>项目承揽人 ：</span></span>
                        		 <span class="m_r30 selectcheck" >
									<select id="selectRadio" name="projectContractor" class="selectpicker" multiple data-live-search="true" data-type="23" data-title-id="1118">
									    
									  </select>
									  <input type="text" class="addpro-input" id="pickeother" maxlength="12" placeholder='请输入非投资线项目承揽人名称(必填)'/>
								</span>
                        	</div>
                        </li>
                        <li class="projectSourceli clearfix">
                        	<span class="basic_span addpro-basic-span"><span class='letter-space'>公司名称：</span></span></span>
                            <span class="m_r30"><input type="text" class='addpro-input' style='display: inline-block;' maxlength="50" data-title-id="1814" data-type="1" /> <img style='display: inline-block;vertical-align: middle; margin-left:10px;' title="正确填写公司名称可以快速调取法人信息、融资历史、股权结构，减少手动输入 " src="/sop/img/sop_progress/remindG-icon.png" class="alertImg"></span>
                        </li>
                        <li class="projectSourceli clearfix">
                        	<span class="basic_span addpro-basic-span"><span class='letter-space'>项目简介：</span></span></span>
                            <span class="">
                            	<textarea data-title-id="1203" style='display: inline-block; vertical-align: middle;'  name="projectInfo" data-type="8" type="text" class='textarea_h add_textarea' maxlength="2000" >该项目是一个通过或基于（技术或模式）的（选择三级以下分类) 的（具体品类：平台、运营商、服务商、技术提供商、解决方案提供商、工具），连接（服务一端）和（服务另一端），为（用户）提供（产品服务即内容）的产品或服务，满足了（需求，如有）的刚需或解决了（痛点，如有）。</textarea>
                            	<div></div> 
                           	</span>
                           	<img style='display: inline-block;vertical-align: middle; margin-left:10px;' title="该项目是一个通过或基于（VR定制屏和核心延时算法技术）的（提供全球最清晰的VR体验）的（头显一体化解决方案提供商），连接（优质内容）和（消费者），为（消费者）提供（显示清晰不眩晕的VR产品）的产品或服务，满足了（使用优质体验VR设备的需求）的刚需或解决了（VR设备不清晰、眩晕的痛点）。" src="/sop/img/sop_progress/remindG-icon.png" class="alertImg xmjj">
                        </li>
                          
                    </ul>  
                </div>
                    <!--融资计划-->
                <div class='addpro-finacing-plan'>
                    <div class="addpro-new-title ">
                        <span class="new_color  add-pro-basicmessage">融资计划</span>
                    </div> 
                    <ul class="basic_ul addpro-finacing-ul">
                        <li class='mr_50'>
                            <span class="basic_span letter-space add-finace-lf">融资金额：</span>
                            <span class="m_r15 after after1">
                            	<input type="text" required placeholder='融资金额' class='new_nputr_number addpro-input' id="formatContribution" data-title-id="1916" data-type="19" name="procontribution" data-rule-procontribution="true"  data-msg-procontribution="<font color=red>*</font>支持9位长度的支持6位小数"/>
                            	<div></div>
                            </span>
                            <!-- <span class="m_r30">万元</span> -->
                            
                        </li>
                        <li class='mr_50'>
	                        <span class="basic_span letter-space add-finace-lf">出让股份：</span>
                            <span class="m_r15 after after2">
                            	<input type="text" required placeholder='出让股份' class='new_nputr_number addpro-input ' id="formatShareRatio" data-title-id="1917" data-type="19" name="proshare"  data-rule-proshare="true" data-msg-proshare="<font color=red>*</font>0到100之间的5位小数"/>
                            	<div></div>
                            </span>
                            <!-- <span class="m_r30">% </span> -->
	                    </li>
                        <li>
                        	<span class="basic_span letter-space add-finace-lf">项目估值：</span>
                            <span class="m_r15 after after3">
                            	<input type="text"   placeholder='项目估值' class='new_nputr_number addpro-input' id="formatValuations" data-title-id="1943" data-type="19" name="provaluations"  data-rule-provaluations="true" data-msg-provaluations="<font color=red>*</font>支持13位长度的6位小数"/>
                            	<div></div>
                            </span>
                            <!-- <span class="m_r30">万元</span> -->
                        </li>
                    </ul>
               </div>
               <!-- 商业计划书 -->
                <div class='addpro-business-plan'>
	                <div class="addpro-new-title ">
                        <span class="new_color  add-pro-basicmessage">商业计划书<em>(文件上传大小不超过25MB)</em></span>
                    </div> 
	                <!-- 商业计划书表格-->
	                  <table  class="addSpBusForm" id="plan_business_table" cellspacing="0" cellpadding="0" class="business-plan-table">
		               
	                </table>
                </div>
               <!-- 团队成员 -->
                <div class='ADDcurrendTable clearfix'>
                 	<div class="addpro-new-title ">
                        <span class="new_color  add-pro-basicmessage">团队成员</span>
                    </div> 
                    <div class="pubbtn bluebtn addBtnC teamAdd">添加</div>
                    <table style="width:97%;" id="team-table" cellspacing="0" cellpadding="0" class="team-table">
		                 <thead>
		                	<tr>
		                		<th width='15%'>姓名</th>
		                		<th width='8%'>性别</th>
		                		<th width='11%'>最高学历</th>
		                		<th width='15%'>联系电话</th>
		                		<th width='15%'>微信号</th>
		                		<th width='30%'>职位</th>
		                		<th width='7%'></th>
		                	</tr>	
		                </thead>	
		                <tbody>
		                <!-- 第一个用于克隆。不保存 -->
		                	<tr>
		                		<td>
									 <input type="text" onblur='blurName(this)' class="txt"  name="field1" placeholder="姓名" class="txt"   value="" required="" data-msg-required="<font color=red>*</font><i></i>必填" maxLength="50" />
                 					<div></div>
								</td> 
		                		<td class="selectcheck select">
									<select name="field3">
										<option value='1343'>男</option>
										<option value='1344'>女</option>
									</select>
								</td>
								<td class="selectcheck select">
									<select name="field5" class="txt_select txt" id="field5">
	               
                   					</select>
								</td>
								<td>
									 <input  type="text" class="txt " name="field4" class="" placeholder="手机号"  data-rule-phone="true"  data-msg-phone="<font color=red>*</font>格式不对" />
								</td>
								<td>
									  <input  type="text" class="txt " name="field6" class="fn-tinput" placeholder="微信号"  maxlength="20" data-rule-wechat="true"  data-msg-phone="<font color=red>*</font>允许输入字母、数字、下划线和减号" />								
								</td>
								<td class="selectcheck select">
									<select name="field2" class="txt_select txt" id="field2">
									</select>
									<input class="txt" name="other" maxlength="20" data-msg-required="<font color=red>*</font>请输入职位"><div></div>
								</td>
								<td onclick="deleteTeam(this)" class="team_delete">删除</td>
		                	</tr> 
		                	<!-- 第一个用于克隆。不保存 -->
		                	<tr>
		                		<td>
									 <input type="text" onblur='blurName(this)' class="txt"  name="field1" placeholder="姓名" class="txt"   value="" required="" data-msg-required="<font color=red>*</font><i></i>必填" maxLength="50" />
                 					 <div></div>
								</td> 
		                		<td class="selectcheck select">
									<select name="field3">
										<option value='1343'>男</option>
										<option value='1344'>女</option>
									</select>
								</td>
								<td class="selectcheck select">
									<select name="field5" class="txt_select txt" id="field5">
	               
                   					</select>
								</td>
								<td>
									 <input  type="text" class="txt " name="field4" class="" placeholder="手机号"  data-rule-phone="true"  data-msg-phone="<font color=red>*</font>格式不对" />
								</td>
								<td>
									  <input  type="text" class="txt " name="field6" class="fn-tinput" placeholder="微信号"  maxlength="20" data-rule-wechat="true"  data-msg-phone="<font color=red>*</font>允许输入字母、数字、下划线和减号" />								
								</td>
								<td class="selectcheck select">
									<select name="field2" class="txt_select txt" id="field2">
									</select>
									<input class="txt" name="other" maxlength="20" data-msg-required="<font color=red>*</font>请输入职位"><div></div>
								</td>
								<td onclick="deleteTeam(this)" class="team_delete">删除
								
								</td>
		                	
		                	</tr>	
		                	
		                </tbody>
	                </table>
                </div>
                  
                <div class='ADDcurrendTable'>
                	<div class="addpro-new-title ">
                        <span class="new_color  add-pro-basicmessage">*访谈记录</span>
                    </div>  
                    <form class="myprojecttc new_poptxt myproject_add"  type="validate"> 
				        <div class="tab_con">
				        <!-- time+interviewee-->
				         <!-- 编辑状态下 title改成 编辑访谈记录  移除INPUT  dd填入内容-->
				            <div class="clearfix ">
				            <input type="hidden" id="recordId">
				                <dl class="fmdl clearfix intw_time">
				                    <dt id="toobar_time">访谈时间：</dt>
				                    <dd>
				                         <input type="text" class="datetimepickerHour txt time" readonly id="viewDate" name="viewDate"  />
				                        <div class='inline'></div>
				                    </dd>
				                </dl>   
				                <dl class="fmdl fml clearfix interviewee" id="targetView">
				                    <dt id="toobar_notes">访谈对象：</dt>
				                    <dd class="clearfix viewTarget">
				                        <input type="text" class="txt" id="viewTarget" name="viewTarget" placeholder="访谈对象" class="txt"   value=""/>
			                        	<div class='inline'></div>
				                    </dd>
				                </dl>
				            </div>
				           <!-- Interview summary -->
				            <div class="intw_summary">
				                <dl class="fmdl clearfix">
				                    <dt id="toobar_content">访谈纪要：</dt>
				                    <dd>
				                        <textarea id="viewNotes"></textarea> 
				                        <span id="viewNotes-error" class="error" for="viewNotes"><font color="red">*</font><i></i>不能超过5000字</span>
				                    </dd>
				                </dl>           
				            </div>
				            <dl class="fmdl clearfix">
				                <dt id="toobar_voice">访谈录音：</dt>
				                <dd>
				                
							        <!-- <input type="text" name="fileName" id="file_object" class="txt" readonly="readonly"/>
				                    <a href="javascript:;" class="pubbtn fffbtn" id="select_btn" style="position: relative; z-index: 1;">选择文件</a> -->
				                    <!-- 添加文件后或者有文件的状态下改为 -->
				
				                	<p id="file_object"></p>
				                    <a href="javascript:;" class="pubbtn fffbtn" id="select_btn" style="position: relative; z-index: 1;">选择文件</a>
				                </dd>
				            </dl>  
				            <dl class="fmdl clearfix check_result">
				                <dt id="toobar_result">访谈结论：</dt>
				                <dd id="resultRadion">
				                    
				                </dd>
				            </dl>   
				        </div>                
				    </form>
				    
				      <div class="compile_on_center">
                       <div class="compile_on_left addpro-compile" style="margin-top:20px;">
                           <span class="pubbtn adddpro-save" id="projectAdd">保存</span>
                           <span class="pubbtn addpro-cacel" data-name='industry' data-on="close">取消</span>
                       </div>  
                   </div>
                </div>  
                 </form>
                 
                    <!-- 商业计划书隐藏页面 -->
					<div id="uploadPanel"  style="display: none;">
						<div class="title_bj">上传更新</div>
						<div class="meetingtc margin_45">
						<dl class="fmdl clearfix">
					    	<dt>档案来源：</dt>
					        <dd class="clearfix">
					        	<label><input name="win_fileSource" type="radio" value = "1" checked="checked"/>内部</label>
					            <label><input name="win_fileSource" type="radio" value = "2"/>外部</label>
					        </dd>
					    </dl>
					    <dl class="fmdl clearfix">
					    	<dt>存储类型：</dt>
					        <dd>
					        	<select id="win_fileType">
					            	<option>sadasd</option>
					            </select>
					        </dd>
					    </dl>
					    <dl class="fmdl clearfix">
					    	<dt>业务分类：</dt>
					        <dd>
					<!--          	<input type="text" id="fileWorkType"  class="txt"/> -->
					<!--          	<input type="hidden" id="fileWorkTypeId"/> -->
					         	
					         	<select id="win_fileWorkType">
					            	<option>sadasd</option>
					            </select>
					         	
					        </dd>
					        <dd id="win_isProve_div">
					        	<label><input type="checkbox" value="1" id="win_isProve"/>签署凭证</label>
					        </dd>
					    </dl>
					    <dl class="fmdl clearfix">
					    	<dt>所属项目：</dt>
					        <dd>
					            <input type="hidden" id="win_sopFileId" data-tid=""  class="txt disabled"/>
					        	<input type="text" id="win_sopProjectId" data-tid=""  class="txt disabled"/>
					        </dd>
					       <dd><a class="searchbtn null" id="win_searchProjectBtn" href="javascript:;">搜索</a></dd>
					   
					    </dl>
					    
					     <dl class="fmdl clearfix">
					    	<dt>文档上传：</dt>
					        <dd>
					        	<input type="text" class="txt" id="win_fileTxt" readonly="readonly"/>
					        </dd>
					        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="win_selectBtn">选择档案</a></dd>
					    </dl>  
					    <TEXTAREA ID="win_FILELIST"></TEXTAREA>
					
					    <a href="javascript:;" class="pubbtn bluebtn" id="win_uploadBtn" style="margin-left:80px;">上传保存</a>
					<%--     <input type="hidden" id="pathInput" value="<%=path%>"> --%>
						</div>
					</div>
                    
          </div>
        </div>
       <!--右边-->
        <div class="basic_right"> 
        </div>
        <!--右边 end--> 
    </div> 
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include> 
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/config.js"></script>
<script type="text/javascript" src="<%=path %>/ckeditor/lang/zh-cn.js"></script>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/teamSheetNew2.js'></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/addPlanbusiness2.js'></script>
<!-- 校验 -->
<script type='text/javascript' src='<%=path%>/js/validate/jquery.validate.min.js'></script>
<script type='text/javascript' src='<%=path%>/js/projectDetail/tabInfoValidate.js'></script>

<script>
$(function(){ 
	var url= platformUrl.fillStatus+'/12';
	$.ajax({
		 type:"get",
		 url:url,
		 dataType:'json',
		 success:function(data){ 
			 if(data.entity&&data.entity.status==1){
				// $(dom).show()
				 var tips = data.entity.standardDetails;
				$(".xmjj").attr('title',tips) 
			 } else{
				 
			 }
			
		 }
	 }) 
	//radio样式切换
	$('.inpu-self').click(function(){
		$(this).addClass('inpu-self-checked').siblings().removeClass('inpu-self-checked');
		$('.inpu-self-checked').find('input').attr('checked',true);
		$('.inpu-self-checked').siblings().find('input').attr('checked',false);
	});

	   var TOKEN;
	   
	  // var formData;
	$("#createDate").val(new Date().format("yyyy-MM-dd"));
	  document.getElementsByClassName("datetimepickerHour")[0].addEventListener('click', function(e) {
		    e.currentTarget.blur();  //解决input多次点击，日期插件不显示的问题
		});
	createMenus(5);  
	//ckeditor实例化
	var viewNotes=CKEDITOR.replace('viewNotes',{height:'100px',width:'538px'});
	/**
	 * 本轮融资轮次下拉数据
	 * 项目来源下拉数据
	 * @version 2018-4-11
	 *开始
	 */
	sendGetRequest(platformUrl.queryAllTitleValues+'FNO1?reportType=4', null,CallBackB);
	function CallBackB(data){ 
	    var _dom=$("select[name='financeStatus']");
	        _dom.html("");
	        _dom.append('<option value="">请选择</option>');
	    var childNum = _dom.find("option").length;
	    var entity=data.entity.childList[0];
	    if(!childNum || childNum !=0 ){
	    	$.each(entity.valueList,function(){
	    		_dom.append("<option value='"+this.id+"' data-title-id='"+this.titleId+"'>"+this.name+"</option>");
			});
	    	_dom.selectpicker();
	    }
	    //项目来源下拉数据
	    CallBackD(data)
	}
	function CallBackD(data){ 
	    var _dom=$("select[name='proSource']");
	        _dom.html("");
	        _dom.append('<option value="">请选择</option>');
	    var childNum = _dom.find("option").length;  
	    var entity=data.entity.childList.filter(function(val){return val.titleId=="1120"})[0];  
	    if(!childNum || childNum !=0 ){
	    	$.each(entity.valueList,function(){
	    		_dom.append("<option value='"+this.id+"' code='"+this.code+"'  data-title-id='"+this.titleId+"'>"+this.name+"</option>");
			}); 
	    	_dom.selectpicker()
	    }
	} 
})
//结束

/**
 * 项目名字重复
 * @version 2018-4-11
 *开始
 */ 
  $('#projectName').blur(function(){
	var projectName=$("#projectName").val().trim();
	if(projectName==""||projectName=="undefined"){
		return false
	}else{
		var data2 = {
				'projectName' : projectName
		}
		sendPostRequestByJsonObj(platformUrl.checkProjectName,data2,function(data){ 
				if(data.result.status=="ERROR"){
			       objDatad =data.userData;
                    if(data.result.errorCode == "name-repeat"){   
                        layer.alert("您输入的项目与【"+objDatad.projectName+"】项目重复，不能保存。<br/>项目承做人："+objDatad.teamPerson +" | "+ objDatad.departmentName);
                        $('.project-name').css('display','block');
                    } 
				}else if(data.result.status ==='OK'){
					$('.project-name').css('display','none');
				}
		})
	}  
})  
//结束

/**
 * 项目来源和承揽人等联动
 * @version 2018-4-11
 *开始
 */ 
$("select[name='proSource']").change(function(){
	$(".projectSource").hide();
	var selCode=$(this).find("option:checked").attr("code");
	$("."+selCode).show(); 
	$("#selectRadio option").attr("selected",false);
	$(".proSource button.selectpicker").attr("title",'请选择');
	$(".proSource button.selectpicker span").text("请选择");
	$(".proSource ul.selectpicker li").removeClass("selected");
	$(".projectSource input").val("")
	$(".trSouce input").val("");
	$(".trSouceOther").hide().val("")
	//插件选择后验证
	$('#add_form').validate().form();
	//$("#proSource-error").hide();
	$(".selectcheck input.addpro-input").hide();
	 $('#selectRadio').selectpicker({
	 	dropupAuto:false
      });
}) 
$("select[name='financeStatus']").change(function(){
	//插件选择后验证
	$('#add_form').validate().form();
})
$("select[name='industryOwn']").change(function(){
	//插件选择后验证
	$('#add_form').validate().form();
})
$(".meetingUndeterminedReason").change(function(){
	//插件选择后验证
	$('#add_form').validate().form();
})
$(".meetingVetoReason").change(function(){
	//插件选择后验证
	$('#add_form').validate().form();
})

$(".datetimepickerHour").change(function(){
	//插件选择后验证
	$('#add_form').validate().form();
})
 
$("#selectRadio").change(function(){
        $(".add-project-tabtable #selectRadio-error").hide();
		var otherValue = $(this).find("option").last().val();
		var value = $(this).val();
		if(value==null){
			$(".selectcheck .addpro-input").hide().val("").removeAttr("name");
			return;
		}
		var filt = value.filter(function(val){return val==otherValue});
		if(filt.length>0){
			$(".selectcheck .addpro-input").show().attr("name",'pickeother');
			$(".selectcheck .addpro-input").attr("ovalue",filt[0])
		}else{
			$(".selectcheck .addpro-input").hide().val("").removeAttr("name");
		}
	})
//结束
/**
 * 获取项目承揽人下拉项
 * @version 2016-06-21
 */
 sendGetRequest(platformUrl.searchCLR, null,CallBackE);
 function CallBackE(data){ 
 	var data_list = data.entityList; 
 	var res="";
 	$.each(data_list,function(){
 		if(this.departmentName!=null){
 			res+="<option value='"+this.id+"' data-type='23' data-title-id='1118'>"+this.realName+'&nbsp;&nbsp;|&nbsp;&nbsp;'+this.departmentName+"</option>"
 		}else{
 			res+="<option value='"+this.realName+"' data-type='23' data-title-id='1118'>"+this.realName+"</option>"
 		}
 		
 	})
 	$("#selectRadio").html(res) 
} 
//结束
/**
* 查询事业线  行业归属下拉
* @version 2016-06-21
*/
createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"industryOwn","industryOwn");
$("select[name='industryOwn']").selectpicker() 
//结束

//估值计算 
$("#formatShareRatio").blur(function(){ 
	var projectShareRatio = $("#formatShareRatio").val();
	var projectContribution = $("#formatContribution").val();
	var valuations = finalValue(projectContribution,projectShareRatio);
	if(valuations != null){
		$("#formatValuations").val(valuations).attr("guzhi",valuations);
	}else{
		$("#formatValuations").removeAttr("guzhi");
	}
});
$("#formatContribution").blur(function(){ 
	var projectShareRatio = $("#formatShareRatio").val();
	var projectContribution = $("#formatContribution").val();
	var valuations = finalValue(projectContribution,projectShareRatio);
	if(valuations != null){
		$("#formatValuations").val(valuations).attr("guzhi",valuations);
	}else{
		$("#formatValuations").removeAttr("guzhi");
	}
});
//结束
//团队下拉 
var map_pos = selectCache("team-members","field5") 
/**
团队select 字典缓存
*/
var map_pos = selectCache("team-members","field5");
var map_field2 = selectCache("team-members","field2");

var xlOP="";
var xlOP2="";
$.each(map_field2,function(e,index){ 
	xlOP2 += "<option value="+e+">"+index+"</option>"
})
$.each(map_pos,function(e,index){ 
	xlOP += "<option value="+e+">"+index+"</option>"
})
$("#team-table select[name=field5]").html(xlOP);
$("#team-table select[name=field2]").html(xlOP2);  
$("#team-table tbody tr:gt(0) select[name=field2]").selectpicker()
$("#team-table tbody tr:gt(0) select[name=field3]").selectpicker()
$("#team-table tbody tr:gt(0) select[name=field5]").selectpicker()

function selectCache(subCode,filed){
    var map = {};
	sendGetRequest(platformUrl.getDirectory+"1303"+'/'+subCode+"/"+filed,null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK')
			{
				var dataMap = data.userData;
			    var list=dataMap[filed];
			    var name=""
				$.each(list, function(i, value){
				     map[value.id]=value.name;
				});
			}
		})
		return map;
} 
/**
* 查询事业线  行业归属下拉
* @version 2018-04-11
*/ 
//验证不忽略隐藏的select（使用了插件）
$.validator.setDefaults({ignore: ".projectSource :hidden,#team-table tr :hidden"});
//团队other
$("#team-table select[name='field2']").change(function(){
	  var val=$(this).find("option:selected").attr("value");
	  if(val=='1363'){
		  $(this).siblings('input[name="other"]').show();
		  $(this).siblings('input[name="other"]').attr("required",true);
	  }else{
		  $(this).siblings('input[name="other"]').hide();
		  $(this).siblings('input[name="other"]').removeAttr("required");
		  $(this).siblings('input[name="other"]').next().find(".error").hide(); 
		  $(this).siblings('input[name="other"]').val('');
		  
	  }
})
//验证方法 
function addValidate(){
	  //验证估值 
    var s_val1=$("#formatValuations").attr("guzhi"),
	s_val2=$("#formatValuations").val(),
	s_val3=accSub(s_val1,s_val2); 
	if(s_val3>10||s_val3<-10){
		layer.msg('项目估值的修改结果超出自动计算得出结论的 +/-10万');
		return false;
	}
	var val=$('input:radio[name="projectType"]:checked').val();
	if(val == null || typeof(val) == "undefined"){
		$("#projectTypeTip").css("display","block");
		return false;
	} 
	//团队验证
	var teamValidate=true;
	$.each($("#team-table tbody tr:gt(0)"),function(){ 
		if($(this).find("input[name=field4]").val().trim()=='' && $(this).find("input[name=field6]").val().trim()==''){
			teamValidate=false;
			return false;
		}
	})
	if($("#team-table tbody tr:gt(0)").length<1||!teamValidate ){
		layer.msg('团队成员必须有一条记录且联系电话或微信号至少填写一项');
		return false;
	}
	/* 访谈纪要 */ 
	if($.trim(CKEDITOR.instances.viewNotes.getData())==''&&$("#file_object").text()==''){
		layer.msg('团队成员必须有一条记录且联系电话或微信号至少填写一项');
		return false;
	} 
	return true;
}
 
 
	$("#projectAdd").click(function(){  
		 if(!$('.project-name').is(":hidden")&&$("#projectName").val().trim()!=''){
			  layer.alert("您输入的项目与【"+objDatad.projectName+"】项目重复，不能保存。<br/>项目承做人："+objDatad.teamPerson +" | "+ objDatad.departmentName);
			return false;
		}
		//2.项目承揽人
	    $("#selectRadio[name=projectContractor]").css("display","inline-block");
		//3.表单验证  
	     if(!$('#add_form').validate().form()){//验证不通过时候执行
			$(".adddpro-save").submit();
			return false;	
		}   

	    var VDStatus = addValidate();
	    if(!VDStatus){return false;}
		//数据
		 var data={
			"industryOwn": $("select[name=industryOwn]").val(),//行业归属
			"createDate": $("input[name=createDate]").val(),//项目创建时间
			"projectName": $("input[name=projectName]").val(),//项目名称
			"projectType": $(".inpu-self-checked .inpu-radio").val(),//项目类型
		} 
		/*  var data1={
					
				} */
		//会议纪要
		var projectQuery={
			"content": $.trim(CKEDITOR.instances.viewNotes.getData()),//会议纪要
			"createDate": $("input[name=viewDate]").val(),//访谈时间
			"interviewResult": $("input[name=interviewResult]:checked").val(),//访谈结果
			"reasonOther": $("#resultRadion select.reson").closest(".resel_box").next().find("input").val(),//注意该字段为访谈结果对应的原因选择“其他原因”时，文本框的值
			"resultReason": $("#resultRadion select.reson").val(),//原因
			"stage": "projectProgress:1",//当前阶段
			"target": $("input[name=viewTarget]").val()//访谈对象
		}  
		data.projectQuery=projectQuery; 
		var informationData ={};
		var infoModeList = new Array();
		var fields = $("#add_form").find("input[data-title-id],select[data-title-id]");
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var sele = field.get(0).tagName;
			var _resultId = field.attr("data-result-id");
			
			if(_resultId==undefined){
				_resultId=null;
			}
			var infoMode = {
				titleId	: field.data('titleId'),
				tochange:'true',
				resultId:_resultId,
				type : type
			};
			if(field.data('titleId')=="1118"&&type=="23"){
				//获取多选带备注数据 proSource
				var judgment = $("select[name=proSource]").val();
				if(judgment!='2257'&&judgment!='2262'){
					var judgName = $(".man_info .name").text();
					var val = $("select[data-title-id=1118]").find("option:contains("+judgName+")").attr("value");
					 if(val!=undefined){
						 var infoMode = {
							titleId	: field.data('titleId'),
							tochange:true,
							resultId:"",
							type : type,
							value:val
						};	
					 }else{
						 val = $("select[data-title-id=1118]").find("option").last().attr("value"); 
						 var infoMode = {
							titleId	: field.data('titleId'),
							tochange:true,
							resultId:_resultId,
							type : type,
							value:val,
							remark1:judgName
							
						};
					 }
					 infoModeList.push(infoMode); 
					 informationData.infoModeList = infoModeList;
					 return; 
				}else if(judgment=='2257'){
					data.deletedResultTids=['1118'];
					return;
				
				}else{
				var values =[] ; 
				var doms = $(".selectcheck li.selected span");
				$.each(doms,function(){ 
					values.push($(this).attr('data-value'))
				})  
				var remark = $('.selectcheck .addpro-input').val();
				var other = $('.selectcheck .addpro-input').attr("ovalue");  
				for(i=0;i<values.length;i++){ 
					var infoMode = {
							titleId	: field.data('titleId'),
							tochange:'true',
							resultId:"",
							type : type
						};
					var that = values[i]; 
					infoMode.value=that;  
					if(other==that&&remark!=''&&remark!=null){  
						infoMode.remark1=remark;
					}
					infoModeList.push(infoMode); 
				}   
				informationData.infoModeList = infoModeList;
				return;

			}
			}else if(type==14 )
			{
				infoMode.value = field.val();
			}else if(type==19 || type==1){
				infoMode.remark1 = field.val();
			}
			if (infoMode != null&&type!="13") {
		        infoModeList.push(infoMode);
		    } 
			informationData.infoModeList = infoModeList;
		
		});  
		//团队数据
		var tableTr = $("#team-table tbody tr:gt(0)");

		var infoTableModelList= [];
		$.each(tableTr,function(){
			var that = $(this); 
			var list={
					 code: 'team-members', //表格编号
				     titleId: '1303',//标题id
				     subCode: 'team-members',
				}; 
			$.each(that.find("input[name],select[name]"),function(){
				var field = $(this); 
				var fil = field.attr("name"); 
				list[fil]=field.val();
			})

			infoTableModelList.push(list);
		})
		informationData.infoTableModelList = infoTableModelList;
		 data.informationData=informationData;  
			sendPostRequestByJsonObj(platformUrl.addProject,data,function(data){  
				if(!data){
					layer.msg("提交表单过于频繁!");
				}else if(data.result.status=="ERROR"){
					if(data.result.errorCode == "csds"){
						layer.msg("必要的参数丢失!");
					}else if(data.result.errorCode == "myqx"){
						layer.msg("没有权限添加项目!");
					}  
				}else if(data.result.status=="OK"){
					//判断大脑数据 
					var Id=data.id;
					var projectName = $("#projectName").val();
					var _url = Constants.sopEndpointURL +"/galaxy/infoDanao/searchProject";
					var jsonObj={
							keyword:projectName
					} 
					 sendPostRequestByJsonObj(_url, jsonObj, function(data){ 
						if(data.result.status=="ERROR"){
							forwardWithHeader(Constants.sopEndpointURL + "/galaxy/project/detail/"+Id+ "?backurl=list");
							return false;
						}
						var num =data.pageList.total;
						if(num==0||!num){
							forwardWithHeader(Constants.sopEndpointURL + "/galaxy/project/detail/"+Id+ "?backurl=list");
						}else{
							forwardWithHeader(Constants.sopEndpointURL + "/galaxy/infoDanao/list/"+Id);
						} 
					})  
				}
			})  
		}) 



 

 

function getUpdateData(){  //获取保存数据
	var projectType=$('input:radio[name="projectType"]:checked').val();
	var projectName=$("#projectName").val().trim();
	var createDate=$("#createDate").val().trim();
	var industryOwn=$('select[name="industryOwn"] option:selected').attr("value");	
	var formatData={
	   "projectType":projectType,
       "projectName":projectName,
       "createDate":createDate,
       "industryOwn":industryOwn
	};
	return formatData;
}
/* 会议记录 */
 //会议结论原因数据字段获取
radioSearch(platformUrl.searchDictionaryChildrenItems+"meetingResult"); 
var arrName=[];
arrName.push("meetingUndeterminedReason");
arrName.push("meetingVetoReason"); 
selectDict(arrName);

$(".check_result select").selectpicker();
$("#targetView").attr("style","display:block");
function radioSearch(url, name){
	sendGetRequest(url,null, function(data){
		radionDiv(data);
	}); 
}
function radionDiv(data){
	var dd=$("#resultRadion");
	$.each(data.entityList, function(i, value){
		var lable;
		if(i==0){
			lable='<label><input name="interviewResult" type="radio"  value='+value.code+' /><div class="absoerror left48"></div>'+value.name+'</label>';
		}else{
			lable='<label><input name="interviewResult" type="radio" value='+value.code+' />'+value.name+'</label>';
		}
		var htmlDiv= 
		'<div id="div_'+i+'" class="clearfix">'+lable
		     var parentCode=changeSelect(value);
	       if(parentCode!=""){
	    	   var htmlSelect='<div class="resel_box selectcheck select"><select required="required" disabled="disabled" class="disabled" name="'+parentCode+'" id="'+parentCode+'" data-msg-required="<font color=red>*</font><i></i>必选">'+
	           '<option value="">请选择原因</option>'+
	           '</select></div>'+
	         '<div class="reason_box"><input type="text" style="display:inline-block;" disabled="disabled" name="reasonOther_'+i+'" id="reasonOther" class="txt disabled" placeholder="请填写其它原因" data-msg-required="<font color=red>*</font><i></i>必填" maxlength="50" data-rule-reasonOther="true"><span></span></div>';
	    	 htmlDiv=htmlDiv+htmlSelect;
		  }
	     htmlDiv=htmlDiv+'</div>';	     
		dd.append(htmlDiv);		
	})
}


$("#resultRadion input[type='radio']").click(function(){
	var _select = $(this).parent("label").next().find("select");
	var oh_select = $(this).parents("#resultRadion").find("select"); 
	var oh_input = $(this).parent().parent().siblings().find("input[type='text']"); 
	oh_input.val("").addClass("disabled").attr("disabled","true");
	oh_select.val("").addClass("disabled").attr("disabled","true");
	_select.attr("required","true");
	_select.removeClass("disabled").removeAttr("disabled").addClass("reson");
	$(".check_result select").selectpicker('refresh');
	_select.next().removeClass("disabled");
})
function changeSelect(value){
	//meeting5Result:1:跟进中
	//meeting5Result:2:否决
	//meeting3Result:6:否决
	//meetingResult:2:待定
	//meetingResult:3:否决
	var parentCode="";
	if(value.code=='meeting5Result:1'||value.code=='meeting2Result:3'){
		parentCode="meetingFollowingReason";
	 }
	if(value.code=='meetingResult:2'){
		parentCode="meetingUndeterminedReason";
	}
	if(value.code=='meetingResult:3'||value.code=='meeting5Result:2'||value.code=='meeting1Result:4'||value.code=='meeting3Result:6'||value.code=='meeting4Result:3'){
		parentCode="meetingVetoReason";
	}
	return parentCode;
}
function selectDict(arr){ 
	if(null!=arr){
		for(var i=0;i<arr.length;i++){
			createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+arr[i],arr[i]);
		}
	}
}
//原因选择其他时 
	reason('select[name="meetingUndeterminedReason"]','meetingUndeterminedReason:2');
	reason('select[name="meetingVetoReason"]','meetingVetoReason:5') 
function reason(obj,value){ 
	$(obj).change(function(){ 
		var val=$(this).children("option:selected").val();
		var _this= $(this).parent().siblings(".reason_box").find("input");
		if(val==value){
			_this.attr("required","true").removeAttr("disabled").removeClass("disabled");
		}else{
			_this.val("").attr("disabled","true").addClass("disabled");
		}
	})
}
 /* 团队添加 删除 */
 $(".teamAdd").click(function(){
	 $("#team-table tbody .no-records-found").remove();
	 var copy = $("#team-table tbody tr:first-child").clone(); 
		copy.find("select").selectpicker(); 
	 $("#team-table tbody").append(copy); 
	 if($("#team-table tbody tr").length>=11){$(".teamAdd").hide();}
 })
 function deleteTeam(event){
	 $(event).closest("tr").remove();
	 if($("#team-table tbody tr").length==1){
		nodata='<tr class="no-records-found"><td colspan="6" style="text-align:center !important;color:#bbb;border:0;line-height:32px !important" class="noinfo no_info01"><label class="no_info_icon_xhhl">没有找到匹配的记录</label></td></tr>'
			$("#team-table tbody").append(nodata);
	 }else{
		
	 }
	 if($("#team-table tbody tr").length<11){$(".teamAdd").show();}
 }  
 /*结束 */
  /* 团队 成员填入访谈记录*/
 function blurName(event){ 
	 var val = $(event).val().trim();
	 if($("#viewTarget").val().trim()==''&& val!=''){
		 $("#viewTarget").val(val); 
	 }
 }
 //platformUrl.commonUploadFile   上传录音、
 
 var viewuploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#select_btn")[0], 
		url :platformUrl.uploadBpToSession,
		multipart:true,
		multi_selection:false,
		//multipart_params:{"flag":'video'},
		filters : {
			max_file_size : '25mb',
			mime_types: paramsFilter(1)
		},

		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function(up) {
				
			},
			
			FilesAdded: function(up, files) { 
				if(viewuploader.files.length >= 2){
					viewuploader.splice(0, viewuploader.files.length-1)
				}
				plupload.each(files, function(file) {
					$("#file_object").text(file.name);
				}); 
				$("#select_btn").text("更新")
				viewuploader.start();
			},
			
			UploadProgress: function(up, file) { 
			},
			
			FileUploaded: function(up, files, rtn) {  //上传回调
				$("#powindow").hideLoading();
				var response = $.parseJSON(rtn.response);
				var rs = response.result.status;
				if(rs == "ERROR"){ //OK, ERROR
					$("#save_interview").removeClass("disabled");
					$("#file_object").val("");
					viewuploader.splice(0, meetuploader.files.length)
					layer.msg(response.result.message);
					return false;
				}else{
					//layer.msg("保存成功", {time : 500}); 
				 
				}
				
			},
			
			BeforeUpload:function(up){ 
				viewuploader.setOption("multipart_params",{"flag":'video'});
			}, 
			
			Error: function(up, err) {
				$("#powindow").hideLoading();
				$("#save_interview").removeClass("disabled");
				$("#file_object").val("");
				layer.msg(err.message);
			}
		}
	}); 
	viewuploader.init();
 
 
</script>
</html>

