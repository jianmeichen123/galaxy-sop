<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<script src="<%=path%>/js/resumetc.js" type="text/javascript"></script> 
<div class="resumetc" >
          <!-- 个人简历 -->
          <div class="tabtable resume">
          
          <!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">个人信息</a></li>
                <li data-tab="nav"><a href="javascript:;">学习经历</a></li>
                <li data-tab="nav"><a href="javascript:;">工作经历</a></li>           
                <li data-tab="nav"><a href="javascript:;">外部项目信息</a></li>           
            </ul>
            <!-- tab内容 -->
            <div class="tabtable_con show"  data-tab="con">
            <form action="" id="up_person_form" method="post">
              <div class="tabtable_con1">
                <h2>核心成员基本资料</h2>
                <table>
                  <tr>
                    <th>团队角色：</th>
                    <td><input name="teamRole" id="teamRole" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>姓名：</th>
                    <td><input name="name" id="name" type="text" value=""></td>
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
                      	<label><input id="personSex" name="personSex" type="radio" value="0" >男</label>
            			<label><input id="personSex" name="personSex" type="radio" value="1" >女</label>
                    </td>
                  </tr>
                  <tr>
                    <th>出生日期：</th>
                    <td><input name="personBirthday" id="personBirthday" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>身份证号码：</th>
                    <td><input name="personIdcard" id="personIdcard" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>电话：</th>
                    <td><input name="personTelephone" id="personTelephone" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>邮箱：</th>
                    <td><input name="personEmail" id="personEmail" type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>个人能力</h2>
                <table>
                  <tr>
                    <th>性格：</th>
                    <td><input name="personCharacter" id="personCharacter" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>优势：</th>
                    <td><input name="personGoodness" id="personGoodness" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>劣势：</th>
                    <td><input name="personDisparity" id="personDisparity" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>沟通能力：</th>
                    <td><input name="talkAbility " id="talkAbility " type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>团队协作能力：</th>
                    <td><input name="teamAbility" id="teamAbility" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>核心竞争力（业务相关能力）：</th>
                    <td><input name="businessStrength" id="businessStrength" type="text" value=""> </td>
                  </tr>
                </table>
              </div>
              <dl class="fmdl clearfix">
                <dt>是否有劳动纠纷：</dt>
                <dd>
                  <label for=""><input type="radio" name="laborDispute" />是</label>
                  <label for=""><input type="radio" name="laborDispute" />否</label>
                </dd>               
              </dl>
              <div class="tabtable_con1 tabtable_con2">
                <table>
                  <tr>
                    <th>能力匹配：</th>
                    <td><img src="img/star.png" alt=""></td>
                  </tr>
                  <tr>
                    <th>评级：</th>
                    <td><img src="img/star.png" alt=""></td>
                  </tr>
                  <tr>
                    <th>评语：</th>
                    <td><textarea id="endComment" name="endComment"></textarea></td>
                  </tr>
                </table>                
              </div>
              </form>
              <div class="btnbox">
                <a href="javascript:update()" class="pubbtn bluebtn">保存</a>
                <a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
              </div>
            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con">
              <div class="tabtable_con1">
                <h2>学习经历</h2>
                <input type="button" class="add bluebtn" value="+">
                <table>
                  <tr>
                    <th>学历：</th>
                    <td><input name ="degree " id="degree " type="text" value=""></td>
                    <td><input name ="degree " id="degree " type="text" value=""></td>
                    <td><input name ="degree " id="degree " type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>学校：</th>
                    <td><input name="school" id="school" type="text" value=""></td>
                    <td><input name="school" id="school" type="text" value=""></td>
                    <td><input name="school" id="school" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>专业：</th>
                    <td><input name="major"id="major" type="text" value=""></td>
                    <td><input name="major"id="major" type="text" value=""></td>
                    <td><input name="major"id="major" type="text" value=""></td>
                  </tr>                  
                  <tr>
                    <th>学历性质：</th>
                    <td><input name="educationType" id="educationType" type="text" value=""></td>
                    <td><input name="educationType" id="educationType" type="text" value=""></td>
                    <td><input name="educationType" id="educationType" type="text" value=""></td>
                  </tr>                  
                  <tr>
                    <th>毕业年份：</th>
                    <td><input name="overDate" id="overDate" type="text" value=""></td>
                    <td><input name="overDate" id="overDate" type="text" value=""></td>
                    <td><input name="overDate" id="overDate" type="text" value=""></td>
                  </tr>                  
                  <tr>
                    <th>毕业证书/学历证书的证书编号：</th>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value=""></td>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value=""></td>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>教育背调联系人</h2>
                <input type="button" class="add bluebtn" value="+">
                <table>
                  <tr>
                    <th>老师姓名：</th>
                    <td><input name="teacherName" id="teacherName" type="text" value=""></td>
                    <td><input name="teacherName" id="teacherName" type="text" value=""></td>
                    <td><input name="teacherName" id="teacherName" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>部门：</th>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value=""></td>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value=""></td>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>老师座机：</th>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value=""></td>
                    <td><input name="teacherPhone1" id="teacherPhone1" type="text" value=""></td>
                    <td><input name="teacherPhone2" id="teacherPhone2" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>同学姓名：</th>
                    <td><input name="classmateName" id="classmateName" type="text" value=""></td>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>同学电话：</th>
                    <td><input name="classmatePhone" id="classmatePhone" type="text" value=""></td>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <dl class="fmdl clearfix">
                <dt>是否有劳动纠纷：</dt>
                <dd>
                  <label for=""><input type="radio" name="radio" />是</label>
                  <label for=""><input type="radio" name="radio" />否</label>
                </dd>               
              </dl>
              <div class="tabtable_con1 tabtable_con2">
                <table>
                  <tr>
                    <th>能力匹配：</th>
                    <td><img src="img/star.png" alt=""></td>
                  </tr>
                  <tr>
                    <th>评级：</th>
                    <td><img src="img/star.png" alt=""></td>
                  </tr>
                  <tr>
                    <th>评语：</th>
                    <td><textarea>成员关系1</textarea></td>
                  </tr>
                </table>
              </div>
              <div class="btnbox">
                <a href="javascript:addlearn()" class="pubbtn bluebtn">保存</a>
                <a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
              </div>
            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con">
              <div class="tabtable_con1">
                <h2>工作记录</h2>
                <input type="button" class="add bluebtn" value="+">
                <table>
                  <tr>
                    <th>公司名称:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>部门:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>工作内容:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>工作业绩:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>薪酬水平（元/月）:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>入职时间:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>离职原因:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>上级姓名:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>工作关系（如直接上下级）:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>同事姓名:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>工作关系:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>               
                  </tr>                  
                  <tr>
                    <th>座机:</th>
                    <td><input type="text" value=""></td>
                    <td><input type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <dl class="fmdl clearfix">
                <dt>是否有劳动纠纷：</dt>
                <dd>
                  <label for=""><input type="radio" name="radio" />是</label>
                  <label for=""><input type="radio" name="radio" />否</label>
                </dd>               
              </dl>
              <div class="tabtable_con1 tabtable_con2">
                <table>
                  <tr>
                    <th>能力匹配：</th>
                    <td><img src="img/star.png" alt=""></td>
                  </tr>
                  <tr>
                    <th>评级：</th>
                    <td><img src="img/star.png" alt=""></td>
                  </tr>
                  <tr>
                    <th>评语：</th>
                    <td><textarea></textarea></td>
                  </tr>
                </table>
              </div>
              <div class="btnbox">
                <a href="javascript:;" class="pubbtn bluebtn">保存</a>
                <a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
              </div>
            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con">
            <form action="" id="up_in_form" method="post">
              <div class="tabtable_con1">
                <h2>投资方信息（天使轮）</h2>
                <table>
                  <tr>
                    <th>公司名称：</th>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>投资金额：</th>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>股权占比（%）：</th>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>投资方信息（Pre A轮）</h2>
                <table>
                  <tr>
                    <th>公司名称：</th>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>投资金额：</th>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>股权占比（%）：</th>
                    <td><input type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" value=""></td>
                  </tr>
                </table>
              </div>
               </form>
              <dl class="fmdl clearfix">
                <dt>是否有劳动纠纷：</dt>
                <dd>
                  <label for=""><input type="radio" name="radio" />是</label>
                  <label for=""><input type="radio" name="radio" />否</label>
                </dd>               
              </dl>
              <div class="tabtable_con1 tabtable_con2">
                <table>
                  <tr>
                    <th>能力匹配：</th>
                    <td><img src="img/star.png" alt=""></td>
                  </tr>
                  <tr>
                    <th>评级：</th>
                    <td><img src="img/star.png" alt=""></td>
                  </tr>
                  <tr>
                    <th>评语：</th>
                    <td><textarea></textarea></td>
                  </tr>
                </table>
              </div>
             
              <div class="btnbox">
                <a href="javascript:inadd()" class="pubbtn bluebtn">保存</a>
                <a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
              </div>
            </div>
          </div>
</div>