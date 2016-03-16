<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc" type="validate">
  <div class="form clearfix">
  <form action="" id="person_form" method="post">
  	<input type="hidden" value="" name="projectId" id="projectId">
    <div class="left">
      <dl class="fmdl fml">
        <dt>姓名：</dt>
        <dd><input type="text" id="personName" name="personName" value="" class="txt" placeholder="姓名" valType="required" msg="<font color=red>*</font>账号不能为空" /></dd>
      </dl>
       <dl class="fmdl">
        <dt>年龄：</dt>
        <dd><input type="text" id="personAge" name="personAge" value="" class="txt" placeholder="年龄" valType="NUMBER" msg="<font color=red>*</font>年龄只能是数字"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>电话号码：</dt>
        <dd><input type="text" id="personTelephone" name="personTelephone" value="" class="txt" placeholder="电话号码" valType="MOBILE" msg="<font color=red>*</font>手机格式不正确"/></dd>
      </dl> 
      <dl class="fmdl">
        <dt>最高学历：</dt>
        <dd><input type="text" id="highestDegree" name="highestDegree" value="" class="txt" placeholder="最高学历" valType="required" msg="<font color=red>*</font>最高学历不能为空"/></dd>
      </dl>
    </div>
    <div class="right">
      <dl class="fmdl">
        <dt>性别：</dt>
        <dd>
			<label><input id="personSex0" name="personSex" type="radio" value="0" checked/>男</label>
            <label><input id="personSex1" name="personSex" type="radio" value="1" />女</label>
		</dd>
      </dl>  
      <dl class="fmdl">
        <dt>工龄：</dt>
        <dd><input type="text" id="workTime" name="workTime" value="" class="txt" placeholder="工龄" valType="NUMBER" msg="<font color=red>*</font>工龄只能是数字"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>身份证号码：</dt>
        <dd><input type="text" id="personIdcard" name="personIdcard" value="" class="txt" placeholder="身份证号码" valType="IDENTITY" msg="<font color=red>*</font>身份证号码格式不正确"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>职位：</dt>
        <dd><input type="text" id="personDuties" name="personDuties" value="" class="txt" placeholder="职位" valType="required" msg="<font color=red>*</font>账号不能为空"/></dd>
      </dl>
    </div>
    </form>
  </div>
      <a href="javascript:;" onclick="savePerson();" class="pubbtn bluebtn">保存</a>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>