<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc margin_45 form_shares">
	<div class="title_bj">添加股权结构</div>
  <form action="" id="stock_form" method="post" type="validate">
  <input type="hidden" value="" name="projectId" id="projectId">
  <div class="form clearfix">

      <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;所有权人：</dt>
        <dd><input type="text" name="sharesOwner" value="" class="txt" valType="OTHER" regstring="^.{1,50}$" msg="<font color=red>*</font>不能为空且字符长度最大50"/></dd>
      </dl>
       <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;所有权人类型：</dt>
        <dd><input type="text" name="sharesType" value="" class="txt" valType="OTHER" regstring="^.{1,50}$" msg="<font color=red>*</font>不能为空且字符长度最大50"/></dd>
      </dl> 
      <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;占比：</dt>
        <dd><input type="text" name="sharesRatio" value="" class="percentTxt txt" valType="OTHER" regString="^(\d{1,2}(\.\d{1,2})?$" msg="<font color=red>*</font>0-100之间的两位小数"/><span>&nbsp;%</span></dd>
      </dl> 
      <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;出资金额：</dt>
        <dd><input type="text" name="financeAmount" value="" class="txt" allowNULL="no" valType="LIMIT_NUMBER" msg="<font color=red>*</font>支持两位小数"/><span>&nbsp;万元</span></dd>
      </dl>
      <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;币种：</dt>
        <dd>  
	         <select name="financeUnit">
                        <option value="0" select="selected" name="financeUnit">人民币</option>
                        <option value="1" name="financeUnit">美元</option>
                       <!--  <option value="2" name="financeUnit">英镑</option>
                        <option value="3" name="financeUnit">欧元</option> -->
             </select>
        </dd>
      </dl>
      
    <!--   <dl class="fmdl">
        <dt>获取方式：</dt>
        <dd><input type="text" name="gainMode" value="" class="txt" valType="OTHER" regString="^\S{1,50}$"msg="<font color=red>*</font>不能为空且字符长度最大50"/></dd>
      </dl>
--> 
  </div>
  <div class="form_textarea">
    <dl class="fmdl clearfix">
      <dt class="fl">备注：</dt>
      
      <dd class="fl"><textarea name="remark" class="new_nputr text" maxLength="50" placeholder="最多输入50字"></textarea></dd>
    </dl>
  </div>
      <a href="javascript:;" onclick="savaStock();" class="pubbtn bluebtn">保存</a>
  </form>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>