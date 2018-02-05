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
 }
</style>
<div class="bg">
勝多負少
</div>
<script>
var data ={
		id:7
}
sendGetRequest(platformUrl.getStandardById,data, function(data){
	debugger;
})
</script>