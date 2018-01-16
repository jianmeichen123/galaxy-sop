<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Logger</title>
</head>
<body>
<table border="1">
<c:forEach var="item" items="${list }">
<tr>
<td>${item.name }</td>
<td>
<select id="${item.name }">
	<option value=""></option>
	<option value="OFF">OFF</option>
	<option value="ERROR">ERROR</option>
	<option value="WARN">WARN</option>
	<option value="INFO">INFO</option>
	<option value="DEBUG">DEBUG</option>
	<option value="TRACE">TRACE</option>
	<option value="ALL">ALL</option>
</select>
</td>
</tr>
</c:forEach>
</table>
</body>
<script src="<%=request.getContextPath() %>/js/jquery-1.12.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var data = new Array();
	<c:forEach var="item" items="${list }" varStatus="status">
	data.push({name:"${item.name}",level:"${item.level}"});
	$("select:eq(${status.index})").val("${item.level}");
	</c:forEach>
	
	$("select").change(function(){
		var url="../common/setLevel";
		var data={name:$(this).attr("id"),level:$(this).val()};
		var callback = function(){
			console.log('success');
		}
		$.post(url,data,callback);
	});
</script>
</html>