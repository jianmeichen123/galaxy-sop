<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc">
  <div class="form clearfix margin_45">
  <div class='title_bj'>添加团队成员</div>
  <form action="" id="person_form" method="post" type="validate">
  	<input type="hidden" value="" name="projectId" id="projectId">
    <div class="left">
      <dl class="fmdl fml">
        <dt>姓名：</dt>
        <dd><input type="text" onkeyup="cleanSpelChar(this)" id="personName" name="personName" value="" class="txt" placeholder="姓名" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>姓名只能是汉字或是字符,长度为20" /></dd>
      </dl>
       <dl class="fmdl">
        <dt>年龄：</dt>
        <dd><input type="text" id="personAge" name="formatAgeStr" value="" class="txt" placeholder="年龄" allowNULL="yes" valType="NUMBER" msg="<font color=red>*</font>年龄只能是数字"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>电话号码：</dt>
        <dd><input type="text" id="personTelephone" name="personTelephone" value="" class="txt" placeholder="电话号码"  valType="MOBILE" msg="<font color=red>*</font>手机格式不正确"/></dd>
      </dl> 
      <dl class="fmdl">
        <dt>最高学历：</dt>
        <dd><select id="highestDegree" name="highestDegree" value="" class="txt_select"  allowNULL="yes" msg="<font color=red>*</font>最高学历不能为空"></select></dd>
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
        <dd><input type="text" id="workTime" name="formatWorkTime" value="" class="txt" placeholder="工龄" allowNULL="yes" valType="NUMBER" msg="<font color=red>*</font>工龄只能是数字"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>身份证号码：</dt>
        <dd><input type="text" id="personIdcard" name="personIdcard" value="" class="txt" placeholder="身份证号码" allowNULL="yes" valType="IDENTITY" msg="<font color=red>*</font>身份证号码格式不正确"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>当前职务：</dt>
        <dd><input type="text" id="personDuties" name="personDuties" value="" class="txt" placeholder="职位" allowNULL="yes" msg="<font color=red>*</font>职位不能为空"/></dd>
      </dl>
    </div>
    </form>
  </div>
      <a href="javascript:;" onclick="savePerson();" class="pubbtn bluebtn">保存</a>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>
<script>
//验证输入框内不能输入特殊字符,输入就立刻清除
function cleanSpelChar(th){
    if(/["'<>%;)(&+]/.test(th.value)){
          $(th).val(th.value.replace(/["'<>%;)(&+]/,""));
    }
}
</script>