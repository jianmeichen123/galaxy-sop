<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% 
	String path = request.getContextPath(); 
%>      
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<div class="addmentc margin_45 form_shares">
<div class="title_bj">编辑股权结构</div>
  <form action="" id="up_stock_form" method="post" type="validate">
  <input type="hidden" value="" name="projectId" id="projectId">
  <input type="hidden" name="id" value="${share.id }">
  <div class="form clearfix">
      <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;所有权人：</dt>
        <dd><input type="text" name="sharesOwner" value="${share.sharesOwner }" class="txt" valType="OTHER" regstring="^.{1,50}$" msg="<font color=red>*</font>不能为空且字符长度最大50"/></dd>
      </dl>
      <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;所有权人类型：</dt>
        <dd><input type="text" name="sharesType" value="${share.sharesType}" class="txt" valType="OTHER" regstring="^\S{1,50}$" msg="<font color=red>*</font>不能为空且字符长度最大50"/></dd>
      </dl>  
      <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;占比：</dt>
        <dd><input type="text" name="sharesRatio" value="<fmt:formatNumber type="number" value="${share.sharesRatio } "/>" class="percentTxt txt" valType="OTHER" regString="^(\d{1,2}(\.\d{1,2})?|100(\.[0]{1,2}))$" msg="<font color=red>*</font>0-100之间的两位小数"/><span>%</span></dd>
      </dl> 
       <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;出资金额：</dt>
        <dd><input type="text" name="financeAmount" 
        	                
                <c:choose>
		        	<c:when test="${share.financeAmount != null}">
		        		value="<fmt:formatNumber type="number" value="${share.financeAmount} "/>" 
		        	</c:when>
		        	<c:otherwise>
		        		value=0
		        	</c:otherwise>
		        </c:choose>	
        	
        	<c:if test='${share.financeAmount != null}'>
        		
        	</c:if>
        	class="txt" allowNULL="no" valType="LIMIT_NUMBER" msg="<font color=red>*</font>支持两位小数"/><span>&nbsp;万元</span></dd>
      </dl>
      <dl class="fmdl fl">
        <dt><em class="red">*</em>&nbsp;币种：</dt>
        <dd>  
	         <select name="financeUnit">
                        <option value="0" <c:if test='${share.financeUnit==0}'>  selected="selected"  </c:if>>人民币</option>
                        <option value="1" <c:if test='${share.financeUnit==1}'>  selected="selected"  </c:if>>美元</option>
                      <%--   <option value="2" <c:if test='${share.financeUnit==2}'>  selected="selected"  </c:if>>英镑</option>
                        <option value="3" <c:if test='${share.financeUnit==3}'>  selected="selected"  </c:if>>欧元</option>
       --%>       </select>
        </dd>
      </dl>
 
      
      <%-- <dl class="fmdl">
        <dt>获取方式：</dt>
        <dd><input type="text" name="gainMode" value="${share.gainMode }" class="txt" valType="OTHER" regString="^\S{1,50}$"msg="<font color=red>*</font>不能为空且字符长度最大50"/></dd>
      </dl> --%>

  </div>
  <div class="form_textarea">
    <dl class="fmdl">
      <dt>备注：</dt>
      <dd><textarea class="new_nputr text" name="remark" maxLength="50" placeholder="最多输入50字">${share.remark}</textarea></dd>
    </dl>
  </div>
      <a href="javascript:;" onclick="updateStock()" class="pubbtn bluebtn">保存</a>
  </form>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>
<script type="text/javascript">

</script>