<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
	Long projectId = (Long)request.getAttribute("projectId");
%>
	<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
  <c:set var="aclViewProject"
	value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }"
	scope="request" />
	
<c:set var="isEditable"
	value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}"
	scope="request" />
    <!-- 基本信息 -->
	<div class="tabtable_con_jbxx">	
	<p class="title_2">基本信息</p>
	<!-- 默认展示 -->
	<div class="basic_center">
	
		<div class="tabinfo_con finance_infoC">
			<p class="title_3">融资计划
				<c:if test="${isEditable}">
					<span class="new_fctbox"> 
						<a href="javascript:;" class="edit" data-cont="finicial" data-name="basic" data-on="data-open">编辑</a>
					</span>
				</c:if>
			</p>
			<ul class="clearfix three_part">
				<li>
					<div class="con_box">
						<p>融资金额</p>
						<p class="data_con">
							<em class="new_color_black" id="project_contribution" data-title-id="1916"></em>
							<span>万元</span>					
						</p>
					</div>
				</li>
				<li>
					<div class="con_box">
						<p>项目估值</p>
						<p class="data_con">
							<em class="new_color_black" id="project_valuations" data-title-id="1943"></em>
							<span>万元</span>					
						</p>
					</div>
				</li>
				<li>
					<div class="con_box">
						<p>出让股份</p>
						<p class="data_con">
							<em class="new_color_black" id="project_share_ratio" data-title-id="1917"></em><span>%</span>				
						</p>
						<p id="finance_chart" data-title-id="1917">
						</p>
					</div>
				</li>
			</ul>
		</div>
		<div class="tabinfo_con clearfix">
			<div class="invest_infoC">
				<p class="title_3">实际投资
					<c:if test="${isEditable}">
						<span class="new_fctbox"> 
							<a href="javascript:;" class="edit" data-cont="invest" data-name="basic" data-on="data-open">编辑</a>
						</span>
					</c:if>
				</p>
				<ul class="clearfix two_part">
					<li>
						<div class="con_box">
							<div class="relative_box">						
								<em class="oval" id="financeMode" data-toggle="tooltip" data-name="basic" data-on="data-open" data-cont="investTogether" data-placement="top" data-trigger='hover'></em>
								<p class="img_box">
								出让股份
								</p>
							</div>	
							<div class="relative_box">
								<em class="rate new_color_black" id="finalShareRatio" data-title-id="3010"></em><span>%</span>
								<p>加速服务费占比：<em class="new_color_black" id="serviceCharge" data-title-id="3011"></em><span>%</span></p>
								<p id="invest_chart"></p>
							</div>
						</div>
					</li>
					<li> 
						<div class="con_box invest_money">
							<p>
								<i>投资金额</i>
								<em class="new_color_black" id="finalContribution" data-title-id="3004"></em><span>万元</span>
							</p> 
							<p>
								<i>项目估值</i>
								<em class="new_color_black" id="finalValuations" data-title-id="3012"></em><span>万元</span>
							</p> 
						</div>
					</li>			
				</ul>
			</div>
			<div class="plan_infoC">
				<p class="title_3">商业计划书
				</p>
				<ul>
					<li>
						<div class="con_box">
							<div class="relative_box" id='business_plan'>
							</div>
						</div>
					</li>		
				</ul>
			</div>		
		</div>
		<input type="hidden" id="pid" name="id" value="${projectId}"/>
	</div>
	<!-- 编辑页面 -->
	<div class="basic_on basic_common_width tab_info_common_width">
		<form id="basicForm" onsubmit="return false;">
		<!-- 编辑基本信息 -->
		<%-- <div class="basic_message message_current basic_current">
		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑基本信息<em class="agency_close" data-on="close" data-name="basic"></em></div>
		<div class="compile_on_center edit_com_color" id="updateProjectInfo">
			<div class="basic_message_cont">
				<table width='100%' cellspacing='0' cellpadding='0' class="edit_basic_table">
					<tr>
						<td>
							<span>投资经理：</span><span class="basic_mes_left">小王</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>项目类型：</span><span class="basic_mes_left" id="projectType_edit">2017-10-23</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>创建时间：</span><span class="basic_mes_left">2017-10-23</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>项目名称：</span>
							<input type="text" value="" class="basic_mes_input" maxlength="24" name="projectName" id="project_name_edit" required data-msg-required="<font color=red>*</font>项目名称不能为空" aria-required="true"/>
						</td>
					</tr>
					<tr>
						<td>
							<span style="display:inline-block">行业归属：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" autocomplete="off"  onclick="dropdown_select(this,event)"  value="请选择" id="industry_own_sel" name="industryOwn" required data-msg-required="<font color=red>*</font>行业归属不能为空" aria-required="true"/> 
									<ul class='base_select_ul'> 
										<li>企业服务</li> 
										<li>数字娱乐</li> 
										<li>互联网教育</li> 
										<li>互联网医疗</li> 
									</ul> 
							</div> 
						</td>
					</tr>
					<tr>
						<td>
							<span style="display:inline-block">本轮融资轮次：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" autocomplete="off" autocomplete="off"  autocomplete="off"  onclick="dropdown_select(this,event)" value="请选择" id="finance_status_sel" name="financeStatus" required data-msg-required="<font color=red>*</font>融资轮次不能为空" aria-required="true" data-title-id="1108" data-type="14"/> 
									<ul class='base_select_ul'> 
										<li>企业服务</li> 
										<li>数字娱乐</li> 
										<li>互联网教育</li> 
										<li>互联网医疗</li> 
									</ul> 
							</div> 
						</td>
					</tr>
					<tr>
						<td>
							<span style="display:inline-block">项目来源：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" autocomplete="off"  onclick="dropdown_select(this,event)" value="请选择" name="projectSource" required data-msg-required="<font color=red>*</font>项目来源不能为空" aria-required="true"/> 
									<ul class='base_select_ul'> 
										<li>企业服务</li> 
										<li>数字娱乐</li> 
										<li>互联网教育</li> 
										<li>互联网医疗</li> 
									</ul> 
							</div>
							<input type="text" value="" class="basic_mes_input  input_FA" placeholder="请输入FA名称" name="faName" id="faNameEdit" data-rule-faname="true" data-msg-faname="<font color=red>*</font>不能以空格开头，字符最大长度为20" data-msg-required="<font color=red>*</font>不能以空格开头，字符最大长度为20"/>
						</td>
					</tr>
				</table>
			
			</div>
	    </div>
	   		<div class="btn btnbox basic_mes_button">
              <button  class="pubbtn bluebtn version19_save_btn" data-on="save" save_type='save_basic'>保存</button>
              <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
            </div> 
	    </div> --%>
	    <!-- 编辑融资计划 -->
	 	   <div class="finacing_plan finicial_current basic_current">
	 	   		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑融资计划<em class="agency_close" data-on="close" data-name="basic"></em></div>
	 	   		<div class="compile_on_center edit_com_color">
					<div class="basic_message_cont">
						<table width='100%' cellspacing='0' cellpadding='0' class="edit_basic_table">
							<tr>
								<td class="million">
									<span>融资金额：</span>
									<input type="text" class="basic_mes_input" size="20" placeholder="融资金额"  id="project_contribution_edit" name="procontribution" data-rule-procontribution="true" data-msg-procontribution="<font color=red>*</font>支持9位长度的四位小数" data-title-id="1916">
								</td>
							</tr>
							<tr>
								<td class="percent">
									<span>出让股份：</span>
									<input type="text" class="basic_mes_input" placeholder="出让股份"  size="20" id="project_share_ratio_edit" name="proshare" data-rule-proshare="true" data-msg-proshare="<font color=red>*</font>0到100之间的两位小数" data-title-id="1917">
								</td>
							</tr>
							<tr>
								<td class="million">
									<span>项目估值：</span>
									<input type="text" class="basic_mes_input" placeholder="项目估值" data-title-id="1943" id="project_valuations_edit" name="provaluations" data-rule-provaluations="true" data-msg-provaluations="<font color=red>*</font>支持13位长度的四位小数" data-title-id="1943">
								</td>
							</tr>
							
							
						</table>
							
					</div>
	    	</div>
	    			<div class="btn btnbox basic_mes_button inves_plan_btn">
		             	 <button  class="pubbtn bluebtn version19_save_btn" data-on="save" save_type="finance">保存</button>
              			 <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
				    </div> 
	 	   </div>
	 	   <!-- 编辑实际投资 -->
			 	 <div class="finacing_plan real_investment invest_current basic_current">
	 	   		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑实际投资<em class="agency_close" data-on="close" data-name="basic"></em></div>
	 	   		<div class="compile_on_center edit_com_color invest_max_height">
					<div class="basic_message_cont">
						<table width='100%' cellspacing='0' cellpadding='0' class="edit_basic_table">
							<tr class="million">
								<td >
									<span>投资金额：</span>
									<input type="text" value="" class="basic_mes_input real_ivestest_input new_nputr_number" placeholder="投资金额" size="20" id="finalContribution_edit" name="finalContribution" data-rule-finalContribution="true" data-msg-finalContribution="<font color=red>*</font>支持9位长度的四位小数"  data-title-id="3004"/>
								</td>
							</tr>
							<tr class="percent">
								<td >
									<span>股权占比：</span>
									<input type="text" value="" class="basic_mes_input real_ivestest_input" placeholder="股权占比" size="20" id="finalShareRatio_edit" name="finalShareRatio" data-rule-finalShareRatio="true" data-msg-finalShareRatio="<font color=red>*</font>0到100之间的两位小数"  data-title-id="3010"/>
								</td>
							</tr>
							<tr class="million">
								<td>
									<span>项目估值：</span>
									<input type="text" value="" class="basic_mes_input real_ivestest_input" placeholder="项目估值" id="finalValuations_edit" name="finalValuations" data-rule-provaluations="true" data-msg-provaluations="<font color=red>*</font>支持13位长度的四位小数"  data-title-id="3012">
								</td>
							</tr>
							
							<tr class="percent">
								<td>
									<span>加速服务费占比：</span>
									<input type="text" value="" class="basic_mes_input real_ivestest_input real_invest_short" placeholder="加速服务费占比" id="serviceChargeedit" name="serviceChargeedit" data-rule-serviceChargeedit="true" data-msg-serviceChargeedit="<font color=red>*</font>0到5之间的两位小数"  data-title-id="3011"/>
								</td>
							</tr>
							<tr>
								<td>
									<span>投资形式：</span>
									<span class="vest_span">
										<label class='radio_cont'>
											<input type="radio"  name="investForm" value="financeMode:0" class="radioclass"/>
											独投
										</label>
										<label class='radio_cont' data-name="">
											<input type="radio"  name="investForm" value="financeMode:1" class="radioclass"/>
											领投
										</label>
										<label class='radio_cont'>
											<input type="radio" name="investForm" value="financeMode:2" class="radioclass"/>
											合投
										</label>
									
									</span>
								</td>
							</tr>
						</table>
						<div class="invest_institue">
							<span class="invest_type">合投机构：</span>
							<div class="institue_inputs">
								<!-- <div class="institue_content">
									<input type="text" placeholder="机构名称" class="inves_input input_stock_left">
									<input type="text" placeholder="投资金额(万元)" class="inves_input">
									<span class='select_cont'>
										<select class="inves_select">
											<option>人民币</option>
											<option>美元</option>
										</select>
									</span>
									<input type="text" placeholder="股权占比(%)" class="inves_input inves_stock">
									<em class="inves_delete"></em>
								</div> -->	
								<div class="inputsForm"></div>
								<div class="institutionBtn">
									<span class="inves_add"></span>
								</div>								
							</div>	
				
						</div>
					
					</div>
	    	</div>
	    			<div class="btn btnbox basic_mes_button inver_button">
		              <button  class="pubbtn bluebtn version19_save_btn" data-on="save" save_type="real_invest">保存</button>
            		  <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
		            </div> 
	 	   </div>  
	 	   <!--编辑法人信息  -->
	 	  <div class="legal_person  basic_current legal_current">
	 	   		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑法人信息<em class="agency_close" data-on="close" data-name="legal"></em></div>
	 	   		<div class="compile_on_center edit_com_color">
					<div class="basic_message_cont" id='company-info-form'>
					 <input type="hidden" name="id" value="${projectId }">
						<table width='100%' cellspacing='0' cellpadding='0' class="edit_basic_table">
							<tr>
								<td>
									<span>公司名称：</span>
									<input type="text" value="" class="basic_mes_input" placeholder="请输入公司名称" name="projectCompany" maxlength="50" data-title-id="1814">
								</td>
							</tr>
							<tr>
								<td style="position:relative;">
									<span>成立日期：</span>
									<input type="text" value="" class="basic_mes_input legal_date" name="formationDate" onkeydown="return false;"  data-title-id="1816">
									<!-- <i class="legal_date"></i> -->
								</td>
							</tr>
							<tr>
								<td>
									<span>法<em class="short_num">人:</em></span>
									<input type="text" value="" class="basic_mes_input legal_input" placeholder="法人名称" name="companyLegal" maxlength="30"  data-title-id="1815">
								</td>
							</tr>
						</table>
					
					</div>
	    	</div>
	    			<div class="btn btnbox basic_mes_button legal_button">
		              <button  class="pubbtn bluebtn version19_save_btn" data-btn="save">保存</button>
              		  <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
		            </div> 
	 	   </div>   
	 	   
	 	   <!-- 合投机构 --> 
	 	   <div class="agency_institute investTogether_current">
	 	   		<div class="title_bj_tzjl agency_radius"><span>合投机构</span> <em class="agency_close" data-on="close" data-name="investTogether"></em></div>
	 	   		<!-- <div class="compile_on_center edit_com_color" id="updateProjectInfo"> -->
					<div class="basic_message_cont invest_max_height">
						 <table width="100%" cellspacing="0" cellpadding="0" id='jointDelivery' >
							
						</table>
					
					</div>
	    	<!-- </div> -->
	 	   </div>
	 	  	
	 	   
	    </form>
	</div>
	  
</div>

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
<!--     <div class="fmarea"> -->
<!-- 		<div  id="filelist"></div> -->
<!-- 		<div  id="console"></div> -->
<!--     </div> -->
    <a href="javascript:;" class="pubbtn bluebtn" id="win_uploadBtn" style="margin-left:80px;">上传保存</a>
<%--     <input type="hidden" id="pathInput" value="<%=path%>"> --%>
	</div>
</div>

<!--团队成员 -->
	<div class="tabtable_con_on role_hide tabinfo_con team_infoC ">
	<div class="new_r_compile title_3">
		<span class="new_color size16">团队成员</span>
	</div>
	<input type="hidden" id="pid" name="id" value="${pid}" />
		<div class='member'>			
			<!--表格内容-->
			<!-- <div class="tab-pane active commonsize" id="view">
				<table id="tablePerson"  data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" >
				
				</table>
			</div> -->
			<table cellspacing="0" class="table table-hover team_info  editable base_table" data-code="team-members" data-title-id="1303"></table>
			<c:if test="${isEditable}">
				<div class="top clearfix border_bottom">
					<!--按钮-->
					<div class="btnbox_f btnbox_f1 clearfix borderRadius">
						<a id="add_person_btn" href="javascript:;" onclick="addRow(this);" data-href='<%=path%>/html/team_compile.html' class="add_pro" data-name="团队成员">添加</a>	
						<a id="" href="javascript:;" onclick="pagePop(this)" urlCode="/galaxy/project/infoenter/infoList" data-href='' class="add_pro lightbg" >参考信息</a>
					</div>
				</div>
			</c:if>
		</div>
	</div>


<!-- 法人信息 -->
<div class="tabtable_con_on role_hide tabinfo_con">
<div class="legal">
	<div class="show">
		<div class="title_3">
	        <span class="new_color size16">法人信息</span>
	        <c:if test="${isEditable}">
	         	<span class="new_fctbox">
	            	<a href="javascript:;" class="edit" data-cont="legal" data-name="basic" data-on="data-open">编辑</a>
	         	</span>
	        </c:if>
	    </div>
	    <table width="100%" cellspacing="0" cellpadding="0" class="new_table role_table " id="company-info">
	        <tr>
	            <td><span class="new_color_gray th">公司名称：</span><span class="new_color_black" id="projectCompany" data-title-id="1814"></span></td>
	        </tr>
	        <tr>
        	    <td><span class="new_color_gray th">成立日期：</span><span class="new_color_black" id="formationDate"  data-title-id="1816"></span></td>
	        </tr>
	        <tr>
	       		 <td><span class="new_color_gray th">法人：</span><span class="new_color_black" id="companyLegal"  data-title-id="1815"></span></td>	      
	        </tr>
	    </table>                
	</div>

</div>
</div>

<!--隐藏-->
<div class="bj_hui_on"></div>



<!--股权结构 -->
<div class="tabtable_con_on member financeHistory role_hide tabinfo_con equity_infoC">
	<div class='company_center'>
		<div class="new_r_compile title_3">
			<span class="new_color size16 title_3">股权结构 </span>
		</div>		
		<div class="location_show history_show new_ul_all">
			<span class="ico_dot ico"></span>
			<p id="location_show" class="clearfix"></p>
			<div class="fixed-table-container">			
				<table id="equity" cellspacing="0" class="table  base_table" data-title-id="1906" parentid="1902" ></table>
			<div class="top clearfix border_bottom">
        <!--按钮-->
	       <c:if test="${isEditable}">
	          <div class="btnbox_f btnbox_f1 clearfix borderRadius">
	              <a href="javascript:;" class="add_pro" id='add_equity' onclick="addRow_sp(this)">添加</a>
	              <a id="" href="javascript:;" onclick="" data-href='' class="add_pro lightbg" >参考信息</a>
	          </div>
	        </c:if>
	    </div>
			</div>
		</div>
	</div>	
</div>
<!-- 融资历史 -->
<div class="tabtable_con_on member financeHistory role_hide tabinfo_con fHistory_infoC">
	<div class='company_center'>
		<div class="new_r_compile title_3">
			<span class="new_color size16 title_3">融资历史</span>
		</div>		
		<div class="location_show history_show new_ul_all">
			<span class="ico_dot ico"></span>
			<p id="location_show" class="clearfix"></p>
			<div class="fixed-table-container">			
				<table id="tablePerson" cellspacing="0" class="fina_history table base_table financeHistoryTable"  data-title-id="1903" parentid="1902" ></table>
				<div class="top clearfix border_bottom">
		        <!--按钮-->
			       <c:if test="${isEditable}">
			          <div class="btnbox_f btnbox_f1 clearfix borderRadius">
			              <a href="javascript:;" class="add_pro" id='add_history' onclick="addRow_sp(this)">添加</a>
			              <a id="" href="javascript:;" onclick="" data-href='' class="add_pro lightbg" >参考信息</a>
			          </div>
			        </c:if>
		    </div>
			</div>
		</div>
	</div>	
</div>
<!--隐藏-->
<div class="bj_hui_on"></div>
<script type="text/javascript">	
     //投资机构是否删除字段标示
     var isDelete=[];
	var projectInfo = ${proinfo};
	$("[data-toggle='tooltip']").tooltip();//提示
	//运营数据分析返回
/* 	if(getCookieValue('backListOperation')=='7'){
		 if(document.URL.indexOf("back=operateList")>0){
			initTabPostMeeting();
			$(".projectDetail li").eq(7).addClass("on").siblings().removeClass("on");
		} 
		 deleteCookie("backListOperation",'/');
	} */
	
	//新增添加合投机构
	function addInputsRow(i){
	     var inputsRow='<div class="block_inputs institue_content">'
	          +'<span class="input_box"><input placeholder="机构名称" class="name inves_input input_stock_left" name="deliveryName_'+addInputs_i+'" required maxLength="50" data-msg-required="<font color=red>*</font>必填且不超过50字" data-rule-delivery="true" data-msg-delivery="<font color=red>*</font>不能为空"/></span>'
	          +'<span class="input_box"><input placeholder="投资金额(万元)" class="inves_input" name="deliveryAmount_'+addInputs_i+'" required data-rule-amount="true" data-msg-required="<font color=red>*</font>支持6位长度的四位小数" data-msg-amount="<font color=red>*</font>支持6位长度的四位小数"/></span>'
	          +'<span class="input_box"><div id="dropdown"> <input class="input_select" autocomplete="off"  onclick="dropdown_select(this,event)" type="text" value="人民币" m-val="currency:0" id="industry_own_sel" name="industryOwn" required data-msg-required="<font color=red>*</font>行业归属不能为空" aria-required="true"/> <ul class="base_select_ul"><li value="currency:0">人民币</li><li value="currency:1">美元</li></ul></div> </span>'
	          +'<span class="input_box"><input placeholder="占股比例(%)"  class="inves_input inves_stock" name="deliveryShareRatio_'+addInputs_i+'" required data-rule-share="true" data-msg-required="<font color=red>*</font>0-100间的两位小数" data-msg-share="<font color=red>*</font>0-100间的两位小数"/></span>'
	            +'<em class="inves_delete"></em>'
	            +'</div>';
	  	$(".inputsForm").append(inputsRow);
	  };
	  //新增
 	  var addInputs_i=0;
	$(".institutionBtn span").click(function(){
		addInputs_i++;
		addInputsRow(addInputs_i-1);
		$('.invest_max_height').scrollTop('1000');
		var inputsLength=$(".block_inputs").length;
		
		if(inputsLength>=10){
			$(this).hide()
		}else{
			$(this).show()
		}
	}) 
	//删除
	 $(document).on('click','.block_inputs .inves_delete',function(){
		var input1=$(this).siblings("span:first").children("input:first").attr("data-id");
		if(null!=input1){
			isDelete.push(input1);
		}
		$(this).closest(".block_inputs").remove();
		var inputsLength=$(".block_inputs").length;
		if(inputsLength <10){
			$(".institutionBtn span").show()
		}else{
			$(".institutionBtn span").hide()
		}
	});
	
	/* 股权结构 */
	var isTransfering = "${fx:isTransfering(pid) }";
	if(isTransfering == 'true')
	{
		$('.legal [data-on="data-open"]').addClass('limits_gray');
	}
	/*编辑法人信息保存方法交互  */
	$('.legal_current [data-btn="save"]').on('click',function(){
		var projectCompany=$("input[name='projectCompany']").val();
		var formationDate=$("input[name='formationDate']").val();
		var companyLegal=$("input[name='companyLegal']").val();
		$('.bj_hui_on').hide();
		$('.legal_current').hide();
		if(projectCompany==""){
			projectCompany="—";
		}
		if(formationDate==""){
			formationDate="—";
		}
		if(companyLegal==""){
			companyLegal="—";
		}
		saveBaseInfo("company-info-form",projectCompany,formationDate,companyLegal);
		buildShareResult("4","5812");
		
		
	});
	//日期选择
	$('.legal_current input[name="formationDate"]').datepicker({
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
	//radio自定义事件
	$(".radio_cont").on("click",function(){
		$(this).addClass("radio_checked").siblings().removeClass("radio_checked");
		$(this).checked=true;
		//领投合投点击显示
		var index = $(this).index();
		if(index===1){
			$('.invest_institue').show();
			$(".invest_type").text('跟投机构：');
			$('.invest_max_height').css('height','315px');
		}else if(index===2){
			$('.invest_institue').show();
			$(".invest_type").text('合投机构：');
			$('.invest_max_height').css('height','315px');
		}else{
			$('.invest_max_height').css('height','auto');
			$(".inputsForm").html("");
			$(".invest_institue").hide();
		}
	});
	
	
</script>
<script type='text/javascript' src='<%=path%>/js/validate/jquery.validate.min.js'></script>
<script type='text/javascript' src='<%=path%>/js/projectDetail/tabInfoValidate.js'></script>
<script src="<%=path %>/js/echarts.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/build_table.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/tabInfo.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/team_info.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/saveBase.js" type="text/javascript"></script>


