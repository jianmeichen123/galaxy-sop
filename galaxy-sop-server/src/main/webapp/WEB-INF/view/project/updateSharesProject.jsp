<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc margin_45">
<div class="title_bj">编辑股权结构</div>
  <form action="" id="up_stock_form" method="post" type="validate">
  <input type="hidden" value="" name="projectId" id="projectId">
  <input type="hidden" name="id" value="${share.id }">
  <div class="form clearfix">
    <div class="left">
      <dl class="fmdl fml">
        <dt>所有权人：</dt>
        <dd><input type="text" name="sharesOwner" value="${share.sharesOwner }" class="txt" valType="OTHER" regString="^\S{1,20}[^\d]+$"msg="<font color=red>*</font>只能是汉字或是字符,最长度为20"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>占比：</dt>
        <dd><input type="text" name="sharesRatio" value="${share.sharesRatio }" class="percentTxt txt" valType="OTHER" regString="^(\d{1,2}(\.\d{1,2})?|100(\.[0]{1,2}))$" msg="<font color=red>*</font>0-100之间的两位小数"/><span>%</span></dd>
      </dl> 
    </div>
    <div class="right">
      <dl class="fmdl">
        <dt>股东类型：</dt>
        <dd><input type="text" name="sharesType" value="${share.sharesType }" class="txt" valType="OTHER" regString="^\S{1,30}$"msg="<font color=red>*</font>不能为空且字符长度最大30"/></dd>
      </dl>  
      <%-- <dl class="fmdl">
        <dt>获取方式：</dt>
        <dd><input type="text" name="gainMode" value="${share.gainMode }" class="txt" valType="OTHER" regString="^\S{1,50}$"msg="<font color=red>*</font>不能为空且字符长度最大50"/></dd>
      </dl> --%>
    </div>
  </div>
  <div class="form_textarea">
    <dl class="fmdl">
      <dt>备注：</dt>
      <dd><textarea name="remark" valType="requiredDiv" regString="^.{0,2000}$" msg="<font color=red>*</font>不能超过2000字符">${share.remark }</textarea></dd>
    </dl>
  </div>
      <a href="javascript:;" onclick="updateStock()" class="pubbtn bluebtn">保存</a>
  </form>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>