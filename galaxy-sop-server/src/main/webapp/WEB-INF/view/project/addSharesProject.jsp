<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc">
  <form action="" id="stock_form" method="post" type="validate">
  <input type="hidden" value="" name="projectId" id="projectId">
  <div class="form clearfix">
    <div class="left">
      <dl class="fmdl fml">
        <dt>所有权人：</dt>
        <dd><input type="text" name="sharesOwner" value="" class="txt" valType="required" msg="<font color=red>*</font>所有权不能为空"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>占比：</dt>
        <dd><input type="text" name="sharesRatio" value="" class="txt" valType="required" msg="<font color=red>*</font>占比不能为空"/></dd>
      </dl> 
    </div>
    <div class="right">
      <dl class="fmdl">
        <dt>类型：</dt>
        <dd><input type="text" name="sharesType" value="" class="txt" valType="required" msg="<font color=red>*</font>类型不能为空"/></dd>
      </dl>  
      <dl class="fmdl">
        <dt>获取方式：</dt>
        <dd><input type="text" name="gainMode" value="" class="txt" valType="required" msg="<font color=red>*</font>获取方式不能为空"/></dd>
      </dl>
    </div>
  </div>
  <div class="form_textarea">
    <dl class="fmdl">
      <dt>备注：</dt>
      <dd><textarea name="remark" valType="required" msg="<font color=red>*</font>备注不能为空"></textarea></dd>
    </dl>
  </div>
      <a href="javascript:;" onclick="savaStock();" class="pubbtn bluebtn">保存</a>
  </form>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>