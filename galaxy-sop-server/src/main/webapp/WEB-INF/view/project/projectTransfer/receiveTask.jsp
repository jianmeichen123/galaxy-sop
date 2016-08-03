<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
   // String projectid=request.getParameter("projectId");
  
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/> 
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
 <script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
 <script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script> 
          <!-- 下半部分 -->
      <DIV class="btm">
        <ul>
		<li><a href="javascript:;" id="complete-task-btn" >接收项目</a></li>
		<li><a href="javascript:;" id="complete-task-btn" >拒绝项目</a></li>
	   </ul>

          </DIV>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
$(function(){
	
});
function projectLoaded(project)
{
	
}
</script>
</body>
</html>
	