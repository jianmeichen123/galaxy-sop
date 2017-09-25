<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
	Long projectId = (Long)request.getAttribute("projectId");
%>
<!-- 富文本编辑器 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
	<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
  <c:set var="aclViewProject"
	value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }"
	scope="request" />
	
<c:set var="isEditable"
	value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}"
	scope="request" />
    <!-- 基本信息 -->
	<div class="tabtable_con_jbxx">
	<!-- 默认展示 -->
	<div class="basic_center">
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
	<div class="basic_on">
		<div class="title_bj_tzjl">编辑项目基本信息</div>
		<form id="basicForm" onsubmit="return false;">
		<div class="compile_on_center" id="updateProjectInfo">
	        <div class="compile_on_right">
	            <span class="pubbtn bluebtn"  data-on="save">保存</span>
	            <span class="pubbtn fffbtn" data-name="basic" data-on="close">取消</span>
	        </div> 
	       <!--  <div class="new_r_compile new_bottom_color">
				<span class="new_ico_basic"></span> <span class="new_color size16">基本信息</span>
			</div>  -->
	        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
	            <tr>
	                <td><span class="new_color_gray">项目名称：</span><span><input class="new_nputr"  size="20" maxlength="24"  id="project_name_edit" name="projectName" required data-msg-required="<font color=red>*</font><i></i>项目名称不能为空"></input></span></td>
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
                             <td><span class="new_color_gray">本轮融资轮次：</span><span><select class="new_nputr" id="finance_status_sel" name="financeStatus" required data-msg-required="<font color=red>*</font><i></i>本轮融资轮次不能为空" ></select></span></td>
                              <td><span class="new_color_gray">项目进度：</span><span class="new_color_black" id="projectProgress_edit"></span><span>(</span><span class="new_color_gray" id="projectStatusDs_edit"></span><span>)</span><span id="editImg" class="" style="overflow:hidden;"></span></td>
                         </tr>
                           <tr>
                             <td>
	                             <span class="new_color_gray">项目来源：</span>
	                             <span class="mar_left">
	                             	<select name="projectSource" class='new_nputr fl' required data-msg-required="<font color=red>*</font><i></i>项目来源不能为空" >
				                    	<option value="">--请选择--</option>
				                    </select>
	                             <input type="text" class="txt new_nputr fl"  placeholder="请输入FA名称"  name="faName"  id="faNameEdit" data-rule-faname="true" data-msg-required="<font color=red>*</font><i></i>必填" data-msg-faname="<font color=red>*</font><i></i>不能以空格开头，字符最大长度为20"/>
	                             </span>
                             </td>
                           </tr>

                 <tr>
                      <%-- <td colspan="2"><span class="new_color_gray" style="width:60px;text-align:right;">备注：</span><span><input id="remark" class="new_nputr text"  placeholder="最多输入50字" valType="OTHER" allowNULL="yes" regString="^[^\s](.{0,49})$" msg="<font color=red>*</font><i></i>不能超过50字符"></input></span></td>
                      --%>
                      <td colspan="2"><span class="new_color_gray" style="width:60px;text-align:right;">备注：</span><span><input id="remark" class="new_nputr text"  placeholder="最多输入50字" maxLength = "50"></input></span></td>
                 </tr>
	        </table>  
	        
	        <!--融资计划-->
	        <div class="new_r_compile new_bottom_color">
	            <span class="new_ico_financing"></span>
	            <span class="new_color size16">融资计划</span>
	        </div>  
	       <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
	            <tr>
	                <td><span class="new_color_gray">融资金额：</span><span class="new_color_black"><input class="new_nputr_number" size="20"  id="project_contribution_edit" name="procontribution" data-rule-procontribution="true" data-msg-procontribution="<font color=red>*</font><i></i>支持9位长度的四位小数" data-title-id="1916"/>　&nbsp;万元</span></td>
	                <td><span class="new_color_gray">出让股份：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="project_share_ratio_edit" name="proshare" data-rule-proshare="true" data-msg-proshare="<font color=red>*</font><i></i>0到100之间的四位小数" data-title-id="1917"/>　&nbsp;%</span></td>
	            </tr>
	            <tr>
	                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input  class="new_nputr_number" id="project_valuations_edit" name="provaluations" data-rule-provaluations="true" data-msg-provaluations="<font color=red>*</font><i></i>支持13位长度的支持四位小数" data-title-id="1943"/>&nbsp;　万元</span></td>
	            </tr>
	              </table>
	        <!--实际投资-->
	        <div class="new_r_compile new_bottom_color">
	            <span class="new_ico_practical"></span>
	            <span class="new_color size16">实际投资</span>
	        </div>  
	        <table width="100%" cellspacing="0" cellpadding="0" class="new_table">
	            <tr>
	                <td><span class="new_color_gray">投资金额：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="finalContribution_edit" name="finalContribution" data-rule-finalContribution="true" data-msg-finalContribution="<font color=red>*</font><i></i>支持9位长度的支持四位小数"  data-title-id="3004"/>&nbsp;　万元</span></td>
	                <td><span class="new_color_gray">股权占比：</span><span class="new_color_black"><input class="new_nputr_number" size="20" id="finalShareRatio_edit" name="finalShareRatio" data-rule-finalShareRatio="true" data-msg-finalShareRatio="<font color=red>*</font><i></i>0到100之间的四位小数"  data-title-id="3010"/>&nbsp;　%</span></td>
	            </tr>
	            <tr>
	                <td><span class="new_color_gray">项目估值：</span><span class="new_color_black"><input  class="new_nputr_number" id="finalValuations_edit" name="finalValuations" data-rule-provaluations="true" data-msg-provaluations="<font color=red>*</font><i></i>支持13位长度的支持四位小数"  data-title-id="3012"/>&nbsp;　万元</span></td>
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
	        </div>
	    </div>
	    </form>
	</div>
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
	<div class="tabtable_con_on">
	<div class="new_r_compile ">
		<span class="new_ico_book team_group"></span> <span class="new_color size16">团队成员</span>
	</div>
	<input type="hidden" id="pid" name="id" value="${pid}" />
	<c:if test="${aclViewProject==true}">
		<div class="member">
			<c:if test="${isEditable}">
				<div class="top clearfix border-top">
					<!--按钮-->
					<div class="btnbox_f btnbox_f1 clearfix">
						<a id="add_person_btn" href="javascript:;" onclick="addRow(this);" data-href='<%=path%>/html/team_compile.html' class="pubbtn bluebtn ico c4 add_prj add_profile" data-name="团队成员">添加</a>
						<!--  <a href="javascript:;" class="pubbtn bluebtn edit_profile" onclick="toSureMsg();">完善简历</a> -->
					</div>
				</div>
			</c:if>
			
			<!--表格内容-->
			<!-- <div class="tab-pane active commonsize" id="view">
				<table id="tablePerson"  data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" >
				
				</table>
			</div> -->
			<table cellspacing="0" class="table table-hover team_info info_table editable" data-code="team-members" data-title-id="1303"></table>
	
		</div>
	</c:if>
	</div>


<!-- 法人信息 -->
<div class="tabtable_con_on">
<div class="legal">
	<div class="show">
		<div class="title">
	        <span class="new_ico_legal icon"></span>
	        <span class="new_color size16">法人信息</span>
	        <c:if test="${isEditable}">
	        <div class="btn">
	         	<span class="new_fctbox">
	            	<a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
	         	</span>
	        </div> 
	        </c:if>
	    </div>
	    <table width="100%" cellspacing="0" cellpadding="0" class="new_table new_table_stock table_default" id="company-info">
	        <tr>
	            <td><span class="new_color_gray th">公司名称：</span><span class="new_color_black" id="projectCompany" data-title-id="1814"></span></td>
	            <!-- <td><span class="new_color_gray th">组织代码：</span><span class="new_color_black" id="projectCompanyCode"></span></td> -->
	        	<td><span class="new_color_gray th">法人：</span><span class="new_color_black" id="companyLegal"  data-title-id="1815"></span></td>	            
	        </tr>
	        <tr>
	            <td><span class="new_color_gray th">成立日期：</span><span class="new_color_black" id="formationDate"  data-title-id="1816"></span></td>
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
<div class="tabtable_con_on member financeHistory">
	<div class='company_center'>
		<div class="new_r_compile ">
			<span class="new_ico_stock icon"></span> <span class="new_color size16">股权结构 </span>
		</div>
		<div class="top clearfix border-top">
        <!--按钮-->
	       <c:if test="${isEditable}">
	          <div class="btnbox_f btnbox_f1 clearfix">
	              <span href="#" class="pubbtn bluebtn ico c4 add_prj add_profile" id='add_equity' onclick="addRow_sp(this)">添加</span>
	          </div>
	        </c:if>
	    </div>
		<div class="location_show history_show new_ul_all">
			<span class="ico_dot ico"></span>
			<p id="location_show" class="clearfix"></p>
			<div class="fixed-table-container">			
				<table id="equity" cellspacing="0" class="table info_table" data-title-id="1906" parentid="1902" ></table>
			</div>
		</div>
	</div>	
</div>
<!-- 融资历史 -->
<div class="tabtable_con_on member financeHistory">
	<div class='company_center'>
		<div class="new_r_compile ">
			<span class="new_ico_history icon"></span> <span class="new_color size16">融资历史</span>
		</div>
		<div class="top clearfix border-top">
        <!--按钮-->
	       <c:if test="${isEditable}">
	          <div class="btnbox_f btnbox_f1 clearfix">
	              <span href="#" class="pubbtn bluebtn ico c4 add_prj add_profile" id='add_history' onclick="addRow_sp(this)">添加</span>
	          </div>
	        </c:if>
	    </div>
		<div class="location_show history_show new_ul_all">
			<span class="ico_dot ico"></span>
			<p id="location_show" class="clearfix"></p>
			<div class="fixed-table-container">			
				<table id="tablePerson" cellspacing="0" class="fina_history table info_table financeHistoryTable"  data-title-id="1903" parentid="1902" ></table>
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
	//项目来源切换
	$("select[name='projectSource']").change(function(){
		var text=$(this).find("option:checked").text();
		if(text=="FA"){
			$(this).siblings(".new_nputr").show();
			$(this).siblings(".new_nputr").attr("required","required");
		}else{
			$(this).siblings(".new_nputr").hide();
			$(this).siblings(".new_nputr").remove("required");
			$("#faNameEdit-error").hide();
		}
	})
	//运营数据分析返回
	if(document.URL.indexOf("back=operateList")>0){
		initTabPostMeeting();
		$(".projectDetail li").eq(7).addClass("on").siblings().removeClass("on");
	}
	//新增添加合投机构
	function addInputsRow(i){
	     var inputsRow='<div class="block_inputs">'
	          +'<span><input placeholder="填写机构名称" class="name" name="deliveryName_'+addInputs_i+'" required maxLength="50" data-msg-required="<font color=red>*</font><i></i>必填，且不超过50字" data-rule-delivery="true" data-msg-delivery="<font color=red>*</font><i></i>不能为空"/></span>'
	          +'<span><input placeholder="填写投资金额（万元）" name="deliveryAmount_'+addInputs_i+'" required data-rule-amount="true" data-msg-required="<font color=red>*</font><i></i>支持0-1000000的四位小数" data-msg-amount="<font color=red>*</font><i></i>支持0-1000000的四位小数"/></span>'
	          +'<span><select><option value="人民币">人民币</option><option value="美元">美元</option></select></span>'
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
	
</script>
<script type="text/javascript" src="<%=path %>/js/sop.js"></script>
<script type="text/javascript" src="<%=path %>/js/sop_progress/sop_progress.js"></script>
<script type="text/javascript" src="<%=path %>/js/sop_progress/sop_file.js"></script>
<script type='text/javascript' src='<%=path%>/js/validate/jquery.validate.min.js'></script>
<script type='text/javascript' src='<%=path%>/js/projectDetail/tabInfoValidate.js'></script>
<script src="<%=path %>/js/projectDetail/build_table.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/tabInfo.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/team_info.js" type="text/javascript"></script>
<script src="<%=path %>/js/projectDetail/saveBase.js" type="text/javascript"></script>


