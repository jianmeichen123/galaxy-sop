<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<%
	String path = request.getContextPath();
	User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
	String sessionId = "";
	Long userId = null;
	if(null != user) {
		sessionId = user.getSessionId();
		userId = user.getId();  
	}
%>
<ul class="lft" id="menus">
	    <li>
    	<a href="<%=request.getContextPath() %>/demo/indexSample?sid=<%=sessionId %>&guid=<%=userId%>">项目历时</a>
        </li>
         <li>
    	<a href="<%=request.getContextPath() %>/demo/indexProgress?sid=<%=sessionId %>&guid=<%=userId%>">项目进度</a>
        </li>
         <li>
    	<a href="<%=request.getContextPath() %>/demo/indexKpi?sid=<%=sessionId %>&guid=<%=userId%>">绩效考核</a>
        </li>
        <li>
    	<a href="<%=request.getContextPath() %>/demo/indexTZ?sid=<%=sessionId %>&guid=<%=userId%>">投资资金</a>
        </li>
         <li>
    	<a href="<%=request.getContextPath() %>/demo/indexXMMB?sid=<%=sessionId %>&guid=<%=userId%>">目标追踪</a>
        </li>
           <li>
    	<a href="<%=request.getContextPath() %>/demo/indexTZJEZZ?sid=<%=sessionId %>&guid=<%=userId%>">金额追踪</a>
        </li>
          <li>
    	<a href="<%=request.getContextPath() %>/demo/indexXMYY?sid=<%=sessionId %>&guid=<%=userId%>">项目运营</a>
        </li>
          <li>
    	<a href="<%=request.getContextPath() %>/demo/indexXMMBWCB?sid=<%=sessionId %>&guid=<%=userId%>">目标完成比</a>
        </li>
           <li>
    	<a href="<%=request.getContextPath() %>/demo/indexXMWCLFX?sid=<%=sessionId %>&guid=<%=userId%>">完成率分析</a>
        </li>
        <li>
    	<a href="<%=request.getContextPath() %>/demo/indexXMJDFT?sid=<%=sessionId %>&guid=<%=userId%>">进度分布图</a>
        </li>
          <li>
    	<a href="<%=request.getContextPath() %>/demo/indexXMTJ?sid=<%=sessionId %>&guid=<%=userId%>">项目数统计</a>
        </li>
	</ul>