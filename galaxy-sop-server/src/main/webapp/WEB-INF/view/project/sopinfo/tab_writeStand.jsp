<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<% 
	String path = request.getContextPath(); 
%>
<style>
 .bg{
 	background:#fff;
 	padding:20px 28px 20px 20px;
 	min-height:200px;
 }
 .intw_summary li,.intw_summary p,.intw_summary span,.intw_summary i,.intw_summary em{
 line-height:30px;
 }
</style>
<div class="bg intw_summary" id="standard"> 
</div>
<script> 
sendGetRequest(platformUrl.getStandard+'/7',null, function(data){
	var standHtml = data.entity.standardDetails;
	$("#standard").html(standHtml);
})
</script>