<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="claimtc">
	<c:choose>
		<c:when test="${tipType eq 1}">
			<p class="tips">
				<b class="null ok ico">ico</b>
				认领成功<br>
				是否现在去处理任务！
			</p>
		    <div class="btnbox">
		    	<a href="javascript:;" class="pubbtn bluebtn" >是</a>
		    	<a href="javascript:;" class="pubbtn fffbtn"data-close="close">否</a>
		    </div>
		</c:when>
		<c:otherwise>
			
		</c:otherwise>
	</c:choose>
</div>