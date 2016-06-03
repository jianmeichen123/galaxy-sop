<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="<%=path %>/stylesheet" type="text/css" href="css/lq.datetimepick.css"/>
<link rel="<%=path %>/stylesheet" type="text/css" href="bootstrap/bootstrap-datepicker/css/bootstrap-datepicker.min.css"/>
<link href="<%=path %>/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>



<div class="resumetc" >
          <!-- 个人简历 -->
          <div class="tabtable resume">
          <form action="" id="up_person_form" method="post">
          <input hidden="hidden" id="personId" value="${personId}" class="none">
          <!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">个人信息</a></li>
                <li data-tab="nav"><a href="javascript:;">学习经历</a></li>
                <li data-tab="nav"><a href="javascript:;">工作经历</a></li>           
                <li data-tab="nav"><a href="javascript:;">外部项目信息</a></li>           
            </ul>
            <!-- tab内容 -->
            <div class="tabtable_con "  data-tab="con"  model="personPool" multi="false">
              <div class="tabtable_con1">
                 <h2>核心成员基本资料</h2>
                <table width="100%" cellspacing="0" cellpadding="0">
                  <tr>
                    <th>团队角色：</th>
                    <td data-by="id"><input name="teamRole" id="teamRole" type="text" value=""   valType="NUM_CHAR_CH" regString="^[A-Za-z0-9\u4e00-\u9fa5]+$" msg="身团队角色只能包含数字、字母、汉字 长度限制50"></td>
                  </tr>
                  <tr>
                    <th>姓名：</th>
                    <td><input name="personName" id="personName" type="text" value=""  valType="NAME" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="姓名格式错误 长度限制50"></td>
                  </tr>
                  <tr>
                    <th>成员关系：</th>
                 		 <td><input name="memberRelation"  id="memberRelation" type="text" value="" valType="MEMBERSHIP" msg="成员关系只能包含汉字，长度20"></td>  
<!--                   	 <td>
	                   <select id="memberRelation" name="memberRelation">	                   		               
		                  <option value="">无</option>
		                  <option value="上级" >上级</option>
		                  <option value="同事" >同事</option>
		                  <option value="下级" >下级</option>              
		                </select>			                      	                  	 
                  	 </td> -->
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>个人资料</h2>
                <table width="100%" cellspacing="0" cellpadding="0">
                  <tr>
                    <th>性别：</th>
                    <td>
                      	<label><input id="personSex0" name="personSex" type="radio" value="0" >男</label>
            			<label><input id="personSex1" name="personSex" type="radio" value="1" >女</label>
                    </td>
                  </tr>
                <tr>
                    <th>出生日期：</th>
                     <td><input  type="text"  name="personBirthdayStr" id="personBirthdayStr" value="" class="datepicker"/> </td>
                  </tr>  
                  <tr>
                    <th>身份证号码：</th>
                    <td><input name="personIdcard" id="personIdcard" type="text" value="" class="txt" placeholder="身份证号码" valType="IDENTITY" msg="身份证号码格式不正确"></td>
                  </tr>
                  <tr>
                    <th>电话：</th>
                    <td><input name="personTelephone" id="personTelephone" type="text" value="" class="txt" placeholder="电话号码" valType="MOBILE" msg="手机格式不正确" ></td>
                  </tr>
                  <tr>
                    <th>邮箱：</th>
                    <td><input name="personEmail" id="personEmail" type="text" value="" class="txt" placeholder="邮箱" valType="MAIL" msg="电子邮箱格式不正确" ></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>个人能力</h2>
                <table width="100%" cellspacing="0" cellpadding="0">
                  <tr>
                    <th>性格：</th>
                    <td><input name="personCharacter" id="personCharacter" type="text" value="" valType="GENERAL" msg="性格文本限制长度500"></td>
                  </tr>
                   <tr>
                    <th>优势：</th>
                    <td><input name="personGoodness" id="personGoodness" type="text" value="" valType="GENERAL" msg="优势限制长度500"></td>
                  </tr>
                  <tr>
                    <th>劣势：</th>
                    <td><input name="personDisparity" id="personDisparity" type="text" value="" valType="GENERAL" msg="劣势限制长度500"></td>
                  </tr>
                  <tr>
                    <th>沟通能力：</th>
                    <td><input name="talkAbility" id="talkAbility " type="text" value=""  valType="GENERAL"  msg="沟通能力限制长度500"></td>
                  </tr>
                  <tr>
                    <th>团队协作能力：</th>
                    <td><input name="teamAbility" id="teamAbility" type="text" value=""  valType="GENERAL" msg="沟通能力限制长度500"></td>
                  </tr>
                  <tr>
                    <th>核心竞争力（业务相关能力）：</th>
                    <td><input name="businessStrength" id="businessStrength" type="text" value=""  valType="GENERAL" msg="沟通能力限制长度500"> </td>
                  </tr>
                </table>
              </div>

            </div>

            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personLearn" multi="true">
              <div class="tabtable_con1">
                <h2>学习经历</h2>
                <input type="button" class="add bluebtn" value="+">
              <table width="100%" cellspacing="0" cellpadding="0">
               <tr>
                    <th>学历：</th>
                	<!-- <td data-by="id"><input name ="degree" id="degree" type="text" value=""  valType="CHAR_CH_SYB" regString="^[A-Za-z0-9\u4e00-\u9fa5]+$" msg="学历只能包字母、汉字 长度50"></td>
                   	<td data-by="id"><input name ="degree" id="degree" type="text" value=""  valType="CHAR_CH_SYB" regString="^[A-Za-z0-9\u4e00-\u9fa5]+$" msg="学历只能包含字母、汉字 长度50"></td>
                    <td data-by="id"><input name ="degree" id="degree" type="text" value=""  valType="CHAR_CH_SYB" regString="^[A-Za-z0-9\u4e00-\u9fa5]+$" msg="学历只能包含数字母、汉字 长度50"></td>  -->
                 	<td data-by="id">                 	    	                   
                 		<select id="de0" name="de" onchange="setValue(this)">		               		                		                  
		                  <option value="1" >请选择</option>		                  
		                  <option value="高中" >高中</option>		                  
		                  <option value="大专" >大专</option>
		                  <option value="本科" >本科</option>
		                  <option value="硕士" >硕士</option>
		                  <option value="MBA" >MBA</option>
		                  <option value="博士" >博士</option>	
		                  <option value="其他" >其他</option>		                    		                              
		                </select>
		                <input name ="degree" id="degree" hidden="hidden" value="" class="none" >
		         	</td>
		         	<td data-by="id">		         			                   
                 		<select id="de1" name="de" onchange="setValue1(this)">
		                  <option value="1" >请选择</option>		                  
		                  <option value="高中" >高中</option>		                  
		                  <option value="大专" >大专</option>
		                  <option value="本科" >本科</option>
		                  <option value="硕士" >硕士</option>
		                  <option value="MBA" >MBA</option>
		                  <option value="博士" >博士</option>	
		                  <option value="其他" >其他</option>	                                
		                </select>
		                <input name ="degree" id="degree1" hidden="hidden" value="" class="none" >
		         	</td>
		         	<td data-by="id">		         			                   
                 		<select id="de2" name="de" onchange="setValue2(this)">
		                  <option value="1" >请选择</option>		                  
		                  <option value="高中" >高中</option>		                  
		                  <option value="大专" >大专</option>
		                  <option value="本科" >本科</option>
		                  <option value="硕士" >硕士</option>
		                  <option value="MBA" >MBA</option>
		                  <option value="博士" >博士</option>
		                  <option value="其他" >其他</option>	                               
		                </select>
		                <input name ="degree" id="degree2" hidden="hidden" value="" class="none" > 
		         	</td> 
              </tr> 
                  <tr>
                    <th>学校：</th>
                    <td><input name="school" id="school" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="学校只能包含字母、汉字"></td>
                  	<td><input name="school" id="school" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="学校只能包含字母、汉字"></td>
                    <td><input name="school" id="school" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="学校只能包含字母、汉字"></td>
                  </tr>
                  <tr>
                    <th>专业：</th>
                    <td><input name="major"id="major" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="专业只能包含字母、汉字 长度50"></td>
                    <td><input name="major"id="major" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="专业只能包含字母、汉字 长度50"></td>
                    <td><input name="major"id="major" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="专业只能包含字母、汉字 长度50"></td> 
                  </tr>                  
                  <tr>
                    <th>学历性质：</th>
                    <td><input name="educationType" id="educationType" type="text" value="" valType="CHAR_CH_SYB"  msg="学历性质只能包含字母、汉字 长度限制50"></td>
                	<td><input name="educationType" id="educationType" type="text" value="" valType="CHAR_CH_SYB"  msg="学历性质只能包含字母、汉字 长度限制50"></td>
                    <td><input name="educationType" id="educationType" type="text" value="" valType="CHAR_CH_SYB"  msg="学历性质只能包含字母、汉字 长度限制50"></td>
                  </tr>                  
   				 <tr>
                    <th>毕业年份：</th>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker" valType="DATE" msg="毕业年份错误" ></td>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker" valType="DATE" msg="毕业年份错误"></td>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker" valType="DATE" msg="毕业年份错误"></td>
                  </tr>             
                  <tr>
                    <th>毕业证书/学历证书的证书编号：</th>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value="" valType="CERTIFICATE" msg="证书编号格式错误 长度限制18"></td>
                   	<td><input name="certificateNumber" id="certificateNumber" type="text" value="" valType="CERTIFICATE" msg="证书编号格式错误 长度限制18"></td>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value="" valType="CERTIFICATE" msg="证书编号格式错误 长度限制18"></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>教育背调联系人</h2>               
                <table width="100%" cellspacing="0" cellpadding="0">
                  <tr>
                    <th>老师姓名：</th>
                    <td><input name="teacherName" id="teacherName" type="text" value="" valType="NAME" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="老师姓名格式错误 长度限制50"></td>
                   	<td><input name="teacherName" id="teacherName" type="text" value="" valType="NAME" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="老师姓名格式错误 长度限制50"></td>
                    <td><input name="teacherName" id="teacherName" type="text" value="" valType="NAME" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="老师姓名格式错误 长度限制50"></td> 
                  </tr>
                  <tr>
                    <th>部门：</th>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value="" valType="NUM_CHAR_CH"  msg="部门只能包含字母、汉字数字 长度限制50"></td>
					<td><input name="teacherPosition" id="teacherPosition" type="text" value="" valType="NUM_CHAR_CH" msg="部门只能包含字母、汉字数字 长度限制50"></td>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value="" valType="NUM_CHAR_CH" msg="部门只能包含字母、汉字数字 长度限制50"></td>
                  </tr>
                  <tr>
                    <th>老师座机：</th>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value="" valType="TEL"  msg="老师座机错误" ></td>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value="" valType="TEL"  msg="老师座机错误"></td>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value="" valType="TEL"  msg="老师座机错误"></td>
                  </tr>
                  <tr>
                    <th>同学姓名：</th>
                    <td><input name="classmateName" id="classmateName" type="text" value="" valType="NAME"  msg="同学姓名格式错误长度限制50"></td>
                  	<td><input type="text" name="classmateName" valType="NAME"  msg="同学姓名格式错误 长度限制50" ></td>
                    <td><input type="text" name="classmateName" valType="NAME"  msg="同学姓名格式错误 长度限制50" ></td>
                  </tr>
                  <tr>
                    <th>同学电话：</th>
                    <td><input name="classmatePhone" id="classmatePhone" type="text" value="" valType="MOBILE" msg="同学电话格式错误" ></td>
	                <td><input type="text" value="" name="classmatePhone" valType="MOBILE"  msg="同学电话格式错误" ></td>
                    <td><input type="text" value="" name="classmatePhone" valType="MOBILE"  msg="同学电话格式错误" ></td>
                  </tr>
                </table>
              </div>
            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personWork" multi="true" >
              <div class="tabtable_con1">
                <h2>工作记录</h2>
                <input type="button"  class="addd bluebtn" value="+">
                <table width="100%" cellspacing="0" cellpadding="0">
                  <tr>
                    <th>公司名称：</th>
                    <td data-by="id" ><input type="text" name="companyName" id="companyName" value="" valType="COMPANYNAME" msg="公司名称格式错误"> </td>
              
                  </tr>
                  <tr>
                    <th>部门：</th>
                    <td><input type="text" name="workDepart" id="workDepart" value="" valType="CHAR_CH" msg="部门名称格式错误"></td>
                  	
                  </tr>
                  <tr>
                    <th>职位：</th>
                    <td><input type="text" name="workPosition" id="workPosition" value="" valType="CHAR_CH" msg="职位名称格式错误"></td>
                  	
                  </tr>
                  <tr>
                    <th>工作内容：</th>
                    <td><input type="text" name="workContent" id="workContent" value="" valType="CHAR_CH" msg="工作内容格式错误"></td>
                 	
                  </tr>
                  <tr>
                    <th>工作业绩：</th>
                    <td><input type="text" name="workEffect" id="workEffect" value="" valType="CHAR_CH" msg="工作业绩名称格式错误"></td>
                  	
                  </tr>
                  <tr>
                    <th>薪酬水平（元/月）：</th>
                    <td><input type="text" name="workEmolument" id="workEmolument" value="" valType="ONLYINT" msg="薪酬水平只能是正整数"></td>
                  
                  </tr>
                <tr>
                    <th>离职时间：</th>
                    <td><input type="text" name="beginWorkStr" id="beginWorkStr" value="" valType="DATE"  class="datepicker"  msg="入职时间格式错误"></td>
                  
                  </tr>
                  <tr>
                    <th>离职原因：</th>
                    <td><input type="text" name="leaveReason" id="leaveReason" value="" valType="CHAR_CH" msg="离职原因格式错误"></td>
                  	
                  </tr>
                  <tr>
                    <th>上级姓名：</th>
                    <td><input type="text" name="leaderName" id="leaderName" value="" valType="NAME" msg="上级姓名格式错误"></td>
                  	
                  </tr>
                  <tr>
                    <th>职位：</th>
                    <td><input type="text" name="leaderPosition" id="leaderPosition" value="" valType="CHAR_CH" msg="职位格式错误"></td>
                  	
                  </tr>
                  <tr>
                    <th>工作关系（如直接上下级）：</th>
                    <!--  <td><input type="text" name="leaderRelationship" id="leaderRelationship" value="" valType="MEMBERSHIP" msg="工作关系格式错误"></td>  -->
                  	<td>		         			                   
	                 		<select id="le1" name="le" onchange="setValue4(this)">
			                  <option value="" >请选择</option>
			                  <option value="上级" >上级</option>
			                  <option value="下级" >下级</option>		                  
			                  <option value="同级" >同级</option>		                              
			                </select>
		                <input name ="leaderRelationship" id="leaderRelationship"  hidden="hidden" value="" class="none"> 
		         	</td> 

                  </tr>
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" name="leaderPhone" id="leaderPhone" value="" valType="MOBILE" msg="联系方式格式错误"></td>
                
                  </tr>
                  <tr>
                    <th>同事姓名：</th>
                    <td><input type="text" name="colleagueName" id="colleagueName" value="" valType="NAME" msg="同事姓名格式错误"></td>
                 	
                  </tr>
<!--                   <tr>
                    <th>职位：</th>
                    <td><input type="text" name="colleaguePosition" id="colleaguePosition" value=""></td>
                  </tr>
                  <tr>
                    <th>工作关系：</th>
                    <td><input type="text" name="colleagueRelationship" id="colleagueRelationship" value=""></td>
                  </tr>   -->                
                  <tr>
                    <th>座机：</th>
                    <td><input type="text" name="colleaguePhone" id="colleaguePhone" value="" valType="TEL" msg="座机格式错误"></td>
                  
                  </tr>
                </table>
              </div>

            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personInvest" multi="false">
              <div class="tabtable_con1">
                <h2>投资方信息（天使轮）</h2>
                   <table width="100%" cellspacing="0" cellpadding="0">
                  <tr>
                    <th>公司名称：</th>
                    <td data-by="id"><input type="text" name="icompanyName" id="icompanyName"  value="" valType="COMPANYNAME" msg="公司名称格式错误"></td>
                  </tr>
                  <tr>
                    <th>投资金额：</th>
                    <td><input type="text" name="investmentAmount" id="investmentAmount"  value="" valType="MONEY" msg="投资金额格式方式错误包含数字  万后小数点2位 "></td>
                  </tr>
                  <tr>                  
                    <th>股权占比（%）：</th>
                    <td><input type="text" name="shareRatio" id="shareRatio"  value="" valType="RATIO" msg="股权占比格式方式错误 股权占比值必须 在0-100之间"></td>
                  </tr>
                  <tr>
                    <th>项目负责人：</th>
                     
                    <td><input type="text" name="projectDirector" id="projectDirector"   value="" valType="NAME" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="项目负责人姓名格式错误"></td>
                  </tr>
                 
                  <tr>
                    <th>职位：</th>
                    <td><input type="text" name="emceedPosition" id="emceedPosition"  value=""  valType="CHAR_CH" msg="职位格式错误"></td>
                  </tr>
                  
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" name="telephone" id="telephone"  value="" valType="MOBILE" msg="联系方式错误"></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>投资方信息（Pre A轮）</h2>
 				<table width="100%" cellspacing="0" cellpadding="0">
                  <tr>
                    <th>公司名称：</th>
                    <td><input type="text" name="acompanyName" id="acompanyName"  value="" valType="COMPANYNAME" msg="公司名称格式错误"></td>
                  </tr>
                  <tr>
                    <th>投资金额：</th>
                    <td><input type="text" name="ainvestmentAmount" id="ainvestmentAmount"  value="" valType="MONEY" msg="投资金额格式方式错误包含数字  万后小数点2位"></td>
                  </tr>
                  <tr>
                    <th>股权占比（%）：</th>
                    <td><input type="text" name="ashareRatio" id="ashareRatio"  value=""  valType="RATIO" msg="股权占比格式方式错误 股权占比值必须在0-100之间"></td>
                  </tr>
                  <tr>
                    <th>项目负责人：</th>
                    <td><input type="text" name="aprojectDirector" id="aprojectDirector" value="" valType="NAME" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="项目负责人姓名格式错误"></td>
                  </tr>
                 
                  <tr>
                    <th>职位：</th>
                    <td><input type="text" name="aemceedPosition" id="aemceedPosition"   value="" valType="CHAR_CH" msg="职位格式错误"></td>
                  </tr>
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" name="atelephone" id="atelephone"  value="" valType="MOBILE" msg="联系方式错误"></td>
                  </tr>
                </table>
              </div>
            </div>
            <dl class="fmdl clearfix">
                <dt>是否有劳动纠纷：</dt>
                <dd>
                  <label for=""><input type="radio" name="laborDispute" value="1"/>是</label>
                  <label for=""><input type="radio" name="laborDispute"  value="0"/>否</label>
                </dd>                
              </dl>
              <div class="tabtable_con1 tabtable_con2">
                <table width="100%" cellspacing="0" cellpadding="0">
                  <tr>
                    <th>能力匹配：</th>
                    <td>
                    	<div id="abilityStar" action="star"></div>
                    	<input id="input-21c" name="abilityStar"  type="hidden" target="star">
                    </td>
                  </tr>
                  <tr>
                    <th>评级：</th>
                    <td>
                    	<div id="levelStar" action="star"></div>
                    	<input id="input-21c" name="levelStar" type="hidden" target="star">
                    </td>
                  </tr>
                  <tr>
                    <th>评语：</th>
                    <td><textarea id="endComment" name="endComment"></textarea></td>
                  </tr>
                </table>
              </div>
              </form>
              <div class="btnbox">
                <a href="javascript:;"  class="pubbtn bluebtn">保存</a>
                <a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
              </div>

          </div>
</div>


<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/star/jquery.raty.min.js" type="text/javascript"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/star-rating.min.js'></script>
<script src="<%=path %>/js/resumetc.js" type="text/javascript"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
           