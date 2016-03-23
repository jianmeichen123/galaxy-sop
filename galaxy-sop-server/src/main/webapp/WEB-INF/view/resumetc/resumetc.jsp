<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="<%=path %>/stylesheet" type="text/css" href="css/lq.datetimepick.css"/>
<link rel="<%=path %>/stylesheet" type="text/css" href="bootstrap/bootstrap-datepicker/css/bootstrap-datepicker.min.css"/>
<link href="<%=path %>/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/reset.css" />



<div class="resumetc" >
          <!-- 个人简历 -->
          <div class="tabtable resume">
          <form action="" id="up_person_form" method="post">
          <input hidden="hidden" id="personId" value="${personId}">
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
                <table>
                  <tr>
                    <th>团队角色：</th>
                    <td data-by="id"><input name="teamRole" id="teamRole" type="text" value=""   valType="NUM_CHAR_CH" regString="^[A-Za-z0-9\u4e00-\u9fa5]+$" msg="<font color=red>*</font>身团队角色只能包含数字、字母、汉字"></td>
                  </tr>
                  <tr>
                    <th>姓名：</th>
                    <td><input name="personName" id="personName" type="text" value=""  valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="<font color=red>*</font>姓名只能包含字母、汉字、“.”"></td>
                  </tr>
                  <tr>
                    <th>成员关系：</th>
                    <td><input name="memberRelation"  id="memberRelation" type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>个人资料</h2>
                <table>
                  <tr>
                    <th>性别：</th>
                    <td>
                      	<label><input id="personSex0" name="personSex" type="radio" value="0" >男</label>
            			<label><input id="personSex1" name="personSex" type="radio" value="1" >女</label>
                    </td>
                  </tr>
<!--                <tr>
                    <th>出生日期：</th>
                    <td><input  type="text"  name="personBirthday" id="personBirthday" value=""/> </td>
                  </tr>  -->
                  <tr>
                    <th>身份证号码：</th>
                    <td><input name="personIdcard" id="personIdcard" type="text" value="" class="txt" placeholder="身份证号码" valType="IDENTITY" msg="<font color=red>*</font>身份证号码格式不正确"></td>
                  </tr>
                  <tr>
                    <th>电话：</th>
                    <td><input name="personTelephone" id="personTelephone" type="text" value="" class="txt" placeholder="电话号码" valType="MOBILE" msg="<font color=red>*</font>手机格式不正确" ></td>
                  </tr>
                  <tr>
                    <th>邮箱：</th>
                    <td><input name="personEmail" id="personEmail" type="text" value="" class="txt" placeholder="邮箱" valType="MAIL" msg="<font color=red>*</font>电子邮箱格式不正确" ></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>个人能力</h2>
                <table>
                  <tr>
                    <th>性格：</th>
                    <td><input name="personCharacter" id="personCharacter" type="text" value="" valType="GENERAL" msg="<font color=red>*</font>性格文本限制长度500"></td>
                  </tr>
                  <tr>
                    <th>劣势：</th>
                    <td><input name="personGoodness" id="personGoodness" type="text" value="" valType="GENERAL" msg="<font color=red>*</font>劣势限制长度500"></td>
                  </tr>
                  <tr>
                    <th>劣势：</th>
                    <td><input name="personDisparity" id="personDisparity" type="text" value="" valType="GENERAL" msg="<font color=red>*</font>劣势限制长度500"></td>
                  </tr>
                  <tr>
                    <th>沟通能力：</th>
                    <td><input name="talkAbility" id="talkAbility " type="number" value="" ></td>
                  </tr>
                  <tr>
                    <th>团队协作能力：</th>
                    <td><input name="teamAbility" id="teamAbility" type="text" value="" ></td>
                  </tr>
                  <tr>
                    <th>核心竞争力（业务相关能力）：</th>
                    <td><input name="businessStrength" id="businessStrength" type="text" value="" > </td>
                  </tr>
                </table>
              </div>

            </div>

            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personLearn" multi="true">
              <div class="tabtable_con1">
                <h2>学习经历</h2>
                <input type="button" class="add bluebtn" value="+">
                <table>
                  <tr>
                    <th>学历：</th>
                    <td data-by="id"><input name ="degree" id="degree" type="text" value=""  valType="NUM_CHAR_CH" regString="^[A-Za-z0-9\u4e00-\u9fa5]+$" msg="<font color=red>*</font>身团队角色只能包含数字、字母、汉字"></td>
                   	<td data-by="id"><input name ="degree" id="degree" type="text" value=""  valType="NUM_CHAR_CH" regString="^[A-Za-z0-9\u4e00-\u9fa5]+$" msg="<font color=red>*</font>身团队角色只能包含数字、字母、汉字"></td>
                    <td data-by="id"><input name ="degree" id="degree" type="text" value=""  valType="NUM_CHAR_CH" regString="^[A-Za-z0-9\u4e00-\u9fa5]+$" msg="<font color=red>*</font>身团队角色只能包含数字、字母、汉字"></td>
                  </tr>
                  <tr>
                    <th>学校：</th>
                    <td><input name="school" id="school" type="text" value="" valType="CHAR_CH" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="<font color=red>*</font>学校只能包含字母、汉字"></td>
                  	<td><input name="school" id="school" type="text" value="" valType="CHAR_CH" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="<font color=red>*</font>学校只能包含字母、汉字"></td>
                    <td><input name="school" id="school" type="text" value="" valType="CHAR_CH" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="<font color=red>*</font>学校只能包含字母、汉字"></td>
                  </tr>
                  <tr>
                    <th>专业：</th>
                    <td><input name="major"id="major" type="text" value="" valType="CHAR_CH" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="<font color=red>*</font>专业只能包含字母、汉字"></td>
                    <td><input name="major"id="major" type="text" value="" valType="CHAR_CH" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="<font color=red>*</font>专业只能包含字母、汉字"></td>
                    <td><input name="major"id="major" type="text" value="" valType="CHAR_CH" regString="^[A-Za-z\u4e00-\u9fa5]+$" msg="<font color=red>*</font>专业只能包含字母、汉字"></td> 
                  </tr>                  
                  <tr>
                    <th>学历性质：</th>
                    <td><input name="educationType" id="educationType" type="text" value="" valType="CHAR_CH"  msg="<font color=red>*</font>学历性质只能包含字母、汉字"></td>
                	<td><input name="educationType" id="educationType" type="text" value="" valType="CHAR_CH"  msg="<font color=red>*</font>学历性质只能包含字母、汉字"></td>
                    <td><input name="educationType" id="educationType" type="text" value="" valType="CHAR_CH"  msg="<font color=red>*</font>学历性质只能包含字母、汉字"></td>
                  </tr>                  
   				 <tr>
                    <th>毕业年份：</th>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker"></td>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker"></td>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker"></td>
                  </tr>             
                  <tr>
                    <th>毕业证书/学历证书的证书编号：</th>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value="" valType="NUMBER" msg="<font color=red>*</font>证书编号只能是数字"></td>
                   	<td><input name="certificateNumber" id="certificateNumber" type="text" value="" valType="NUMBER" msg="<font color=red>*</font>证书编号只能是数字"></td>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value="" valType="NUMBER" msg="<font color=red>*</font>证书编号只能是数字"></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>教育背调联系人</h2>               
                <table>
                  <tr>
                    <th>老师姓名：</th>
                    <td><input name="teacherName" id="teacherName" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="<font color=red>*</font>姓名只能包含字母、汉字、“.”"></td>
                   	<td><input name="teacherName" id="teacherName" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="<font color=red>*</font>姓名只能包含字母、汉字、“.”"></td>
                    <td><input name="teacherName" id="teacherName" type="text" value="" valType="CHAR_CH_SYB" regString="^[A-Za-z\u4e00-\u9fa5\.]+$" msg="<font color=red>*</font>姓名只能包含字母、汉字、“.”"></td> 
                  </tr>
                  <tr>
                    <th>部门：</th>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value="" valType="NUM_CHAR_CH"  msg="<font color=red>*</font>姓名只能包含字母、汉字数字、“.”"></td>
					<td><input name="teacherPosition" id="teacherPosition" type="text" value="" valType="NUM_CHAR_CH" msg="<font color=red>*</font>姓名只能包含字母、汉字数字、“.”"></td>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value="" valType="NUM_CHAR_CH" msg="<font color=red>*</font>姓名只能包含字母、汉字数字、“.”"></td>
                  </tr>
                  <tr>
                    <th>老师座机：</th>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value="" ></td>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value=""></td>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>同学姓名：</th>
                    <td><input name="classmateName" id="classmateName" type="text" value=""></td>
                  	<td><input type="text" name="classmateName"></td>
                    <td><input type="text" name="classmateName"></td>
                  </tr>
                  <tr>
                    <th>同学电话：</th>
                    <td><input name="classmatePhone" id="classmatePhone" type="text" value="" valType="MOBILE"></td>
	                <td><input type="text" value="" name="classmatePhone" valType="MOBILE"></td>
                    <td><input type="text" value="" name="classmatePhone" valType="MOBILE"></td>
                  </tr>
                </table>
              </div>
            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personWork" multi="true" >
              <div class="tabtable_con1">
                <h2>工作记录</h2>
                <input type="button" class="add bluebtn" value="+">
                <table>
                  <tr>
                    <th>公司名称:</th>
                    <td data-by="id" ><input type="text" name="companyName" id="companyName" value=""></td>
                  </tr>
                  <tr>
                    <th>部门:</th>
                    <td><input type="text" name="workDepart" id="workDepart" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" name="workPosition" id="workPosition" value=""></td>
                  </tr>
                  <tr>
                    <th>工作内容:</th>
                    <td><input type="text" name="workContent" id="workContent" value=""></td>
                  </tr>
                  <tr>
                    <th>工作业绩:</th>
                    <td><input type="text" name="workEffect" id="workEffect" value=""></td>
                  </tr>
                  <tr>
                    <th>薪酬水平（元/月）:</th>
                    <td><input type="text" name="workEmolument" id="workEmolument" value=""></td>
                  </tr>
                <tr>
                    <th>入职时间:</th>
                    <td><input type="text" name="beginWorkStr" id="beginWorkStr" value=""  class="datepicker"></td>
                  </tr>
                  <tr>
                    <th>离职原因:</th>
                    <td><input type="text" name="leaveReason" id="leaveReason" value=""></td>
                  </tr>
                  <tr>
                    <th>上级姓名:</th>
                    <td><input type="text" name="leaderName" id="leaderName" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" name="leaderPosition" id="leaderPosition" value=""></td>
                  </tr>
                  <tr>
                    <th>工作关系（如直接上下级）:</th>
                    <td><input type="text" name="leaderRelationship" id="leaderRelationship" value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式:</th>
                    <td><input type="text" name="leaderPhone" id="leaderPhone" value=""></td>
                  </tr>
                  <tr>
                    <th>同事姓名:</th>
                    <td><input type="text" name="colleagueName" id="colleagueName" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" name="colleaguePosition" id="colleaguePosition" value=""></td>
                  </tr>
                  <tr>
                    <th>工作关系:</th>
                    <td><input type="text" name="colleagueRelationship" id="colleagueRelationship" value=""></td>
                  </tr>                  
                  <tr>
                    <th>座机:</th>
                    <td><input type="text" name="colleaguePhone" id="colleaguePhone" value=""></td>
                  </tr>
                </table>
              </div>

            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personInvest" multi="false">
              <div class="tabtable_con1">
                <h2>投资方信息（天使轮）</h2>
                   <table>
                  <tr>
                    <th>公司名称：</th>
                    <td data-by="id"><input type="text" name="icompanyName" id="icompanyName"  value=""></td>
                  </tr>
                  <tr>
                    <th>投资金额：</th>
                    <td><input type="text" name="investmentAmount" id="investmentAmount"  value=""></td>
                  </tr>
                  <tr>
                    <th>股权占比（%）：</th>
                    <td><input type="number" name="shareRatio" id="shareRatio"  value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" name="telephone" id="telephone"  value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>投资方信息（Pre A轮）</h2>
 				<table>
                  <tr>
                    <th>公司名称：</th>
                    <td><input type="text" name="acompanyName" id="acompanyName"  value=""></td>
                  </tr>
                  <tr>
                    <th>投资金额：</th>
                    <td><input type="text" name="ainvestmentAmount" id="ainvestmentAmount"  value=""></td>
                  </tr>
                  <tr>
                    <th>股权占比（%）：</th>
                    <td><input type="number" name="ashareRatio" id="ashareRatio"  value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" name="atelephone" id="atelephone"  value=""></td>
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
                <table>
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
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/star-rating.min.js'></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<script src="<%=path %>/js/resumetc.js" type="text/javascript"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
           