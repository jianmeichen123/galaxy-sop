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
<td>${item.level }</td>
</tr>
</c:forEach>
</table>
</body>
</html>