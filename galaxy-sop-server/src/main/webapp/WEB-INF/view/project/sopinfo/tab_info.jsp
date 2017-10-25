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
							<em class="new_color_black" id="project_share_ratio" data-title-id="1917"></em>%				
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
								<em class="oval" data-toggle="tooltip" data-name="basic" data-on="data-open" data-cont="investTogether" data-placement="top" data-trigger='hover' title="点击查看合投列表">合投</em>
								<p class="img_box">
								出让股份
								</p>
							</div>	
							<div class="relative_box">
								<em class="rate new_color_black" id="finalShareRatio" data-title-id="3010"></em>%
								<p>加速服务费占比：<em class="new_color_black" id="serviceCharge" data-title-id="3011"></em>%</p>
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

		<div class="new_r_compile_new">
			<c:if test="${isEditable}">
			<span class="new_fctbox"> 
				<a href="javascript:;" class="ico f1" data-name="basic" data-on="data-open">编辑</a>
			</span>
		</c:if>
		</div>
		<table width="100%" cellspacing="0" cellpadding="0" class="new_table" id="project_info">
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
				<td><span class="new_color_gray">行业归属：</span><span class="new_color_black" id="industryOwnDs" ></span></td>
				<td><span class="new_color_gray" >投资经理：</span>
					<span class="new_color_black" id="createUname"></span><span class="new_color_gray">(</span><span class="new_color_gray" id="projectCareerline"></span><span class="new_color_gray">)</span></td>
			</tr>
			
		     <tr>
				<td><span class="new_color_gray">本轮融资轮次：</span><span class="new_color_black" id="financeStatusDs"  data-title-id="1108"></span></td>
				<td><span class="new_color_gray">项目进度：</span><span class="new_color_black" id="projectProgress"></span>
				<span class="new_color_gray" id="s">(</span>
					<span class="new_color_gray" id="projectStatusDs"></span><span class="new_color_gray" id="end">)</span><span id="insertImg"></span></td>
			</tr>
			     <tr>
				     <td><span class="new_color_gray">项目来源：</span><span class="new_color_black" id="faName"></span></td>
					</tr>
			 <tr>
				     <td colspan="2"><span class="new_color_gray" style="width:60px;text-align:right;">备注：</span><span class="new_color_black" id="remarkStr"></span></td>
					</tr>
		</table>

		<!--融资计划-->
		<div class="new_r_compile new_bottom_color">
			<span class="new_ico_financing"></span> <span class="new_color size16"><em class="red">*</em>融资计划</span>
		</div>
		<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
			<tr>
				<td><span class="new_color_gray">融资金额：</span><span class="new_color_black" id="project_contribution"  data-title-id="1916"></span><span class="new_color_black">&nbsp;万元</span></td>
				<td><span class="new_color_gray">出让股份：</span><span class="new_color_black" id="project_share_ratio"  data-title-id="1917"></span><span class="new_color_black">&nbsp;%</span></td>
			</tr>
			<tr>
				<td><span class="new_color_gray">项目估值：</span><span class="new_color_black" id="project_valuations"  data-title-id="1943"></span><span class="new_color_black">&nbsp;万元</span></td>
			</tr>
		</table>
		
		<!--实际投资-->
		<div class="new_r_compile new_bottom_color">
			<span class="new_ico_practical"></span> <span class="new_color size16">实际投资</span>
		</div>
		<table width="100%" cellspacing="0" cellpadding="0" class="new_table">
			<tr>
				<td><span class="new_color_gray">投资金额：</span><span class="new_color_black" id="finalContribution" data-title-id="3004"></span><span class="new_color_black">&nbsp;万元</span></td>
				<td><span class="new_color_gray">股权占比：</span><span class="new_color_black" id="finalShareRatio" data-title-id="3010"></span><span class="new_color_black">&nbsp;%</span></td>
			</tr>
			<tr>
				<td><span class="new_color_gray">项目估值：</span><span class="new_color_black" id="finalValuations" data-title-id="3012"></span><span class="new_color_black">&nbsp;万元</span></td>
				<td><span class="new_color_gray">加速服务费占比：</span><span class="new_color_black" id="serviceCharge" data-title-id="3011"></span><span class="new_color_black">&nbsp;%</span></td>
	
			</tr>
			<tr>
				<td><span class="new_color_gray">投资形式：</span><span class="new_color_black" id="financeMode"></span></td>
			</tr>
		</table>
		<table width="100%" cellspacing="0" cellpadding="0" id='jointDelivery' >
			
		</table>
	</div>
	<!-- 编辑页面 -->
	<div class="basic_on basic_common_width">
		<form id="basicForm" onsubmit="return false;">
		<!-- 编辑基本信息 -->
		<div class="basic_message message_current basic_current">
		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑基本信息</div>
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
							<span>创建时间：</span><span class="basic_mes_left">2017-10-23</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>项目名称：</span>
							<input type="text" value="摩拜单车" class="basic_mes_input">
						</td>
					</tr>
					<tr>
						<td>
							<span style="display:inline-block">项目类型：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" value="请选择"/> 
									<ul> 
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
							<span style="display:inline-block">行业归属：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" value="请选择"/> 
									<ul> 
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
							<span style="display:inline-block">融资轮次：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" value="请选择"/> 
									<ul> 
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
								<input class="input_select" type="text" value="请选择"/> 
									<ul> 
										<li>企业服务</li> 
										<li>数字娱乐</li> 
										<li>互联网教育</li> 
										<li>互联网医疗</li> 
									</ul> 
							</div>
							<input type="text" value="" class="basic_mes_input  input_FA"/>
						</td>
					</tr>
				</table>
			
			</div>
	    </div>
	   		<div class="btn btnbox basic_mes_button">
              <button  class="pubbtn bluebtn version19_save_btn" data-on="save">保存</button>
              <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
            </div> 
	    </div>
	    <!-- 编辑融资计划 -->
	 	   <div class="finacing_plan finicial_current basic_current">
	 	   		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑融资计划</div>
	 	   		<div class="compile_on_center edit_com_color">
					<div class="basic_message_cont">
						<table width='100%' cellspacing='0' cellpadding='0' class="edit_basic_table">
							<tr>
								<td>
									<span>项目名称：</span>
									<input type="text" value="2000" class="basic_mes_input">
								</td>
							</tr>
							<tr>
								<td>
									<span>项目估值：</span>
									<input type="text" value="9999" class="basic_mes_input">
								</td>
							</tr>
							<tr>
								<td>
									<span>出让股份：</span>
									<input type="text" value="" class="basic_mes_input" placeholder="出让股份(%)">
								</td>
							</tr>
							
						</table>
							
					</div>
	    	</div>
	    			<div class="btn btnbox basic_mes_button inves_plan_btn">
		             	 <button  class="pubbtn bluebtn version19_save_btn" data-on="save">保存</button>
              			 <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
				    </div> 
	 	   </div>
	 	   <!-- 编辑实际投资 -->
			 	 <div class="finacing_plan real_investment invest_current basic_current">
	 	   		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑实际投资</div>
	 	   		<div class="compile_on_center edit_com_color invest_max_height">
					<div class="basic_message_cont">
						<table width='100%' cellspacing='0' cellpadding='0' class="edit_basic_table">
							<tr>
								<td>
									<span>投资金额：</span>
									<input type="text" value="2000" class="basic_mes_input real_ivestest_input" placeholder="万元">
								</td>
							</tr>
							<tr>
								<td>
									<span>项目估值：</span>
									<input type="text" value="9999" class="basic_mes_input real_ivestest_input">
								</td>
							</tr>
							<tr>
								<td>
									<span>股权占比：</span>
									<input type="text" value="" class="basic_mes_input real_ivestest_input" placeholder="股权占比(%)">
								</td>
							</tr>
							<tr>
								<td>
									<span>加速服务费占比：</span>
									<input type="text" value="3" class="basic_mes_input real_ivestest_input real_invest_short" placeholder="">
								</td>
							</tr>
							<tr>
								<td>
									<span>投资形式：</span>
									<span class="vest_span">
										<label class='radio_cont radio_checked'>
											<input type="radio" value="" name="invest_form" class="radioclass"/>
											独投
										</label>
										<label class='radio_cont' data-name="">
											<input type="radio" value="" name="invest_form" class="radioclass"/>
											领投
										</label>
										<label class='radio_cont'>
											<input type="radio" value="" name="invest_form" class="radioclass"/>
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
								<span class="inves_add"></span>
							</div>	
				
						</div>
					
					</div>
	    	</div>
	    			<div class="btn btnbox basic_mes_button inver_button">
		              <button  class="pubbtn bluebtn version19_save_btn" data-on="save">保存</button>
            		  <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
		            </div> 
	 	   </div>  
	 	   <!--编辑法人信息  -->
	 	   <div class="legal_person  basic_current legal_current">
	 	   		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑法人信息</div>
	 	   		<div class="compile_on_center edit_com_color">
					<div class="basic_message_cont">
						<table width='100%' cellspacing='0' cellpadding='0' class="edit_basic_table">
							<tr>
								<td>
									<span>公司名称：</span>
									<input type="text" value="某某股份有限公司" class="basic_mes_input" placeholder="">
								</td>
							</tr>
							<tr>
								<td style="position:relative;">
									<span>成立日期：</span>
									<input type="text" value="" class="basic_mes_input ">
									<i class="legal_date"></i>
								</td>
							</tr>
							<tr>
								<td>
									<span>法<em class="short_num">人:</em></span>
									<input type="text" value="" class="basic_mes_input legal_input" placeholder="法人名称">
								</td>
							</tr>
						</table>
					
					</div>
	    	</div>
	    			<div class="btn btnbox basic_mes_button legal_button">
		              <button  class="pubbtn bluebtn version19_save_btn" data-on="save">保存</button>
              		  <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
		            </div> 
	 	   </div>  
	 	   
	 	   <!-- 合投机构 --> 
	 	   <div class="agency_institute investTogether_current">
	 	   		<div class="title_bj_tzjl agency_radius">合投机构</div>
	 	   		<!-- <div class="compile_on_center edit_com_color" id="updateProjectInfo"> -->
					<div class="basic_message_cont">
						 <table width="100%" cellspacing="0" cellpadding="0" id='jointDelivery' >
							
						</table>
					
					</div>
	    	<!-- </div> -->
	 	   </div>
	 	  	
	 	   
	    </form>
	</div>
	  <%-- <table width="600px" cellspacing="0" cellpadding="0" class="new_table edit_basic_table">
	            <tr>
	                <td><span class="new_color_gray">投资经理：</span><span>小王</span></td>
	            </tr>
	            <tr>
	            	<td><span class="new_color_gray">创建时间：</span><span class="new_color_black" id="create_date_edit"></span></td>
	            </tr>
	            <tr>
	                <td><span class="new_color_gray">项目类型：</span><span class="new_color_black" id="projectType_edit"></span></td>
	                <td><span class="new_color_gray">最后编辑：</span><span class="new_color_black" id="updateDate_edit"></span></td>
	            </tr>
	            <tr>
	                <td>
	                	<span class="new_color_gray">行业归属：</span>
	                	<span>
	                		<select class="new_nputr" id="industry_own_sel" name="industryOwn" required data-msg-required="<font color=red>*</font><i></i>行业归属不能为空">
	                			<option value="">--请选择--</option>
	               			 </select>
	                	</span>
	                </td>
	                <td><span class="new_color_gray">投资经理：</span><span class="new_color_black" id="createUname_edit"></span><span>(</span><span class="new_color_gray" id="projectCareerline_edit"></span><span>)</span></td>
	            </tr>
	            <tr>
                     	 <td><span class="new_color_gray">融资轮次：</span><span><select class="new_nputr" id="finance_status_sel" name="financeStatus" required data-msg-required="<font color=red>*</font><i></i>本轮融资轮次不能为空" ></select></span></td>
                     	 <!-- <td><span class="new_color_gray">项目进度：</span><span class="new_color_black" id="projectProgress_edit"></span><span>(</span><span class="new_color_gray" id="projectStatusDs_edit"></span><span>)</span><span id="editImg" class="" style="overflow:hidden;"></span></td> -->
               </tr>
               <tr>
                        <td>
                         <span class="new_color_gray">项目来源：</span>
                         <span class="mar_left">
                         	<select name="projectSource" class='new_nputr fl' required data-msg-required="<font color=red>*</font><i></i>项目来源不能为空" >
                   				<option value="">--请选择--</option>
                  			 </select>
                         	<input type="text" class="txt new_nputr fl"  placeholder="请输入FA名称"  name="faName"  id="faNameEdit" data-rule-faname="true" data-msg-faname="<font color=red>*</font><i></i>不能以空格开头，字符最大长度为20" data-msg-required="<font color=red>*</font><i></i>不能以空格开头，字符最大长度为20" />
                         </span>
                        </td>
               </tr>
                 <tr>
                      <td colspan="2"><span class="new_color_gray" style="width:60px;text-align:right;">备注：</span><span><input id="remark" class="new_nputr text"  placeholder="最多输入50字" maxLength = "50"></input></span></td>
                 </tr>
	        </table>  --%> 
	        
	        
	        <!--编辑融资计划-->
	       <!--  <div class="new_r_compile new_bottom_color">
	            <span class="new_ico_financing"></span>
	            <span class="new_color size16">融资计划</span>
	        </div>  --> 
	       <%-- <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
	            <tr>
	                <td><span class="new_color_gray">融资金额：</span><span class="new_color_black"><input class="new_nputr_number" size="20"  id="project_contribution_edit" name="procontribution" data-rule-procontribution="true" data-msg-procontribution="<font color=red>*</font><i></i>支持9位长度的四位小数" data-title-id="1916"/>　&nbsp;万元</span></td>
	                <td><span class="new_color_gray">出让股份：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="project_share_ratio_edit" name="proshare" data-rule-proshare="true" data-msg-proshare="<font color=red>*</font><i></i>0到100之间的四位小数" data-title-id="1917"/>　&nbsp;%</span></td>
	            </tr>
	            <tr>
	                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input  class="new_nputr_number" id="project_valuations_edit" name="provaluations" data-rule-provaluations="true" data-msg-provaluations="<font color=red>*</font><i></i>支持13位长度的四位小数" data-title-id="1943"/>&nbsp;　万元</span></td>
	            </tr>
	       </table> --%>
	   <%--      <!--实际投资-->
	        <div class="new_r_compile new_bottom_color">
	            <span class="new_ico_practical"></span>
	            <span class="new_color size16">实际投资</span>
	        </div>  
	        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
	            <tr>
	                <td><span class="new_color_gray">投资金额：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="finalContribution_edit" name="finalContribution" data-rule-finalContribution="true" data-msg-finalContribution="<font color=red>*</font><i></i>支持9位长度的四位小数"  data-title-id="3004"/>&nbsp;　万元</span></td>
	                <td><span class="new_color_gray">股权占比：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="finalShareRatio_edit" name="finalShareRatio" data-rule-finalShareRatio="true" data-msg-finalShareRatio="<font color=red>*</font><i></i>0到100之间的四位小数"  data-title-id="3010"/>&nbsp;　%</span></td>
	            </tr>
	            <tr>
	                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input  class="new_nputr_number" id="finalValuations_edit" name="finalValuations" data-rule-provaluations="true" data-msg-provaluations="<font color=red>*</font><i></i>支持13位长度的四位小数"  data-title-id="3012"/>&nbsp;　万元</span></td>
	                <td><span class="new_color_gray">加速服务费占比：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="serviceChargeedit" name="serviceChargeedit" data-rule-serviceChargeedit="true" data-msg-serviceChargeedit="<font color=red>*</font><i></i>0到5之间的四位小数"  data-title-id="3011"/>&nbsp;　%</span></td>
	     	
	     		</tr>
	     		<tr>
	                <td><span class="new_color_gray">投资形式：</span>
	                <span class="mar_left">
                        <div class="mar_left"><input type="radio" name="investForm" value="financeMode:0">独投 </div>
                        <div class="mar_left"><input type="radio" name="investForm" value="financeMode:1">领投</div>
                        <div class="mar_left"><input type="radio" name="investForm" value="financeMode:2">合投</div>
                     </span>
	                </td>
	     		</tr>
	        </table>
	        <div class="institution clearfix" >
	        	<div class="fl"><span class='new_color_gray'>合投机构：</span></div>
	        	<div class="fl">
	        		<div class="inputsForm"></div>
	        		<div class="institutionBtn"><span class="pubbtn bluebtn">新增</span></div>
	        	</div>
	        </div> --%>
</div>
<!--商业计划书-->
<div class="tabtable_con_on">
	<div class="new_r_compile ">
		<span class="new_ico_book"></span> <span class="new_color size16"><em class="red">*</em>商业计划书</span>
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
		<div>			
			<!--表格内容-->
			<!-- <div class="tab-pane active commonsize" id="view">
				<table id="tablePerson"  data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" >
				
				</table>
			</div> -->
			<table cellspacing="0" class="table table-hover team_info  editable base_table" data-code="team-members" data-title-id="1303"></table>
			<c:if test="${isEditable}">
				<div class="top clearfix border_bottom">
					<!--按钮-->
					<div class="btnbox_f btnbox_f1 clearfix">
						<a id="add_person_btn" href="javascript:;" onclick="addRow(this);" data-href='<%=path%>/html/team_compile.html' class="add_pro" data-name="团队成员"><em class="plus">+</em> 添加</a>
						<!--  <a href="javascript:;" class="pubbtn bluebtn edit_profile" onclick="toSureMsg();">完善简历</a> -->
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
    <div class="hidden">
      <div class="title">
          <span class="new_ico_legal icon"></span>
          <span class="new_color size16">法人信息</span>
          <div class="btn btnbox">
              <button href="javascript:;" class="pubbtn bluebtn" data-btn="save">保存</button>
              <button href="javascript:;" class="pubbtn fffbtn" data-btn="cancle">取消</button>
          </div> 
      </div>
      <form action="#" id="company-info-form">
      <input type="hidden" name="id" value="${projectId }">
      <table width="100%" cellspacing="0" cellpadding="0" class="new_table new_table_stock">
          <tr>
              <td><span class="new_color_gray th">公司名称：</span><input type="text" placeholder="请输入公司名称" name="projectCompany" maxlength="50" data-title-id="1814"></td>
              <!-- <td><span class="new_color_gray th">组织代码：</span><input type="text" placeholder="请输入组织机构代码" name="projectCompanyCode" maxlength="20"></td> -->
        	  <td><span class="new_color_gray th">成立日期：</span><input type="text" class="timeico" name="formationDate" onkeydown="return false;"  data-title-id="1816"></td>
          </tr>
          <tr>
              <td><span class="new_color_gray th">法人：</span><input type="text" placeholder="请输入法人名称" name="companyLegal" maxlength="30"  data-title-id="1815"></td>              
          </tr>
      </table>                    
      </form>
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
	          <div class="btnbox_f btnbox_f1 clearfix">
	              <a href="javascript:;" class="add_pro" id='add_equity' onclick="addRow_sp(this)"><em class="plus">+</em> 添加</a>
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
			          <div class="btnbox_f btnbox_f1 clearfix">
			              <a href="javascript:;" class="add_pro" id='add_history' onclick="addRow_sp(this)"><em class="plus">+</em> 添加</a>
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
	//项目来源切换
	$("select[name='projectSource']").change(function(){
		var text=$(this).find("option:checked").text();
		if(text=="FA"){
			$("#projectSource-error").hide();
			$(this).siblings(".new_nputr").show();
			$(this).siblings(".new_nputr").attr("required","required");
		}else{
			$(this).siblings(".new_nputr").hide();
			$(this).siblings(".new_nputr").remove("required");
			$("#faNameEdit-error").hide();
		}
	})
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
	     var inputsRow='<div class="block_inputs">'
	          +'<span><input placeholder="填写机构名称" class="name" name="deliveryName_'+addInputs_i+'" required maxLength="50" data-msg-required="<font color=red>*</font><i></i>必填，且不超过50字" data-rule-delivery="true" data-msg-delivery="<font color=red>*</font><i></i>不能为空"/></span>'
	          +'<span><input placeholder="填写投资金额（万元）" name="deliveryAmount_'+addInputs_i+'" required data-rule-amount="true" data-msg-required="<font color=red>*</font><i></i>支持0-1000000的四位小数" data-msg-amount="<font color=red>*</font><i></i>支持0-1000000的四位小数"/></span>'
	          +'<span><select><option value="currency:0">人民币</option><option value="currency:1">美元</option></select></span>'
	          +'<span><input placeholder="填写占股比例（%）" name="deliveryShareRatio_'+addInputs_i+'" required data-rule-share="true" data-msg-required="<font color=red>*</font><i></i>0到100之间的两位小数" data-msg-share="<font color=red>*</font><i></i>0到100之间的两位小数"/></span>'
	            +'<span class="del">删除</span>'
	            +'</div>';
	  	$(".inputsForm").append(inputsRow);
	  };
	  //新增
	  var addInputs_i=0;
	$(".institutionBtn span").click(function(){
		addInputs_i++;
		addInputsRow(addInputs_i-1);
		var inputsLength=$(".block_inputs").length;
		if(inputsLength>0){
			$(this).css("margin-top","50px");
		}else{
			$(this).css("margin-top","0");
		}
		if(inputsLength>=10){
			$(this).hide()
		}else{
			$(this).show()
		}
		//新增验证样式调整
		$.each($("#basicForm input"),function(){
			$(this).on("blur",function(){
				labelPosition();
			})
		})
	})
	//删除
	$(document).on('click','.block_inputs .del',function(){
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
		if(inputsLength>0){
			$(".institutionBtn span").css("margin-top","50px")
		}else{
			$(".institutionBtn span").css("margin-top","0")
		}
	});
	
	/* 股权结构 */
	var isTransfering = "${fx:isTransfering(pid) }";
	if(isTransfering == 'true')
	{
		$('.legal [data-btn="edit"]').addClass('limits_gray');
	}
	$('.legal [data-btn="edit"]').on('click',function(){
		if($(this).hasClass('limits_gray'))
		{
			return;
		}
		editCompany();		
	});
	$('.legal [data-btn="save"]').on('click',function(){
		var projectCompany=$("input[name='projectCompany']").val();
		var formationDate=$("input[name='formationDate']").val();
		var companyLegal=$("input[name='companyLegal']").val();
		$('.bj_hui_on').hide();
	    $('.legal .show').show();
		$('.legal .hidden').hide();		
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
	$('.legal [data-btn="cancle"]').on('click',function(){
		$('.bj_hui_on').hide();
	    $('.legal .show').show();
		$('.legal .hidden').hide();
	});
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

	
	
	function editCompany()
	{
		buildShareResult("4","5812");
    	$('.bj_hui_on').show();
		$('.legal .show').hide();
		$('.legal .hidden').show();
	}
	
	
	//chen{}
	//radio自定义事件
	$(".radio_cont").on("click",function(){
		$(this).addClass("radio_checked").siblings().removeClass("radio_checked");
		//领投合投点击显示
		var index = $(this).index();
		if(index===1){
			$('.invest_institue').show();
			$(".invest_type").text('领投机构：');
		}else if(index===2){
			$('.invest_institue').show();
			$(".invest_type").text('合投机构：');
		}else{
			$('.invest_institue').hide();
		}
	});
	

$('.inves_add').on("click",function(){
	$('.inves_add').before(inputContent);
	$('.institue_content').show();
	var inputContLength = $('.institue_content').length;
	console.log(inputContLength);
	if(inputContLength === 10){
		$('.inves_add').hide();
		return false;
	}
	
});
	
	//拼接出input一行
  var inputContent = '<div class="institue_content">'
					  +'<input type="text" placeholder="机构名称" class="inves_input input_stock_left">'
					  +'<input type="text" placeholder="投资金额(万元)" class="inves_input">'
					  +'<span class="select_cont"><select class="inves_select"><option>人民币</option><option>美元</option></select></span>'
					  +'<input type="text" placeholder="股权占比(%)" class="inves_input inves_stock">'
					  +'<em class="inves_delete" onclick="deleteRow(this)"></em>'
					  +'</div> ';
  
function deleteRow(obj){
	var _this = $(obj);
	_this.closest('.institue_content').remove();
	var inputContLength = $('.institue_content').length;
	if(inputContLength <= 10){
		$('.inves_add').show();
	}
}


  	
	  
	  
	  
	  
	
	
	




	
	
	
	
</script>
<script type='text/javascript' src='<%=path%>/js/validate/jquery.validate.min.js'></script>
<script type='text/javascript' src='<%=path%>/js/projectDetail/tabInfoValidate.js'></script>
<script src="<%=path %>/js/echarts.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/build_table.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/tabInfo.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/team_info.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/saveBase.js" type="text/javascript"></script>


