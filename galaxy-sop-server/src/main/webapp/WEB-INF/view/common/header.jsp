<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<% 
	String path = request.getContextPath(); 
    User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
%>
<div class="header clearfix">
	<a href="javascript:;" class="logo null">繁星</a>
    <!--头部中间-->
    <div class="min clearfix">
        <!--用户信息-->
        <div class="usermsg clearfix">
            <span class="light_blue">当前您有：</span>
            <a href="/galaxy/soptask" class="work">待办任务<em class="totalUrgent"></em></a>
            <a href="javascript:;" class="work">紧急任务<em class="bubble"></em></a>
            <a href="javascript:;" class="work">消息提醒<em>4</em></a> 
        </div>    	<!--当日信息-->
    	<div class="todaymsg clearfix">
        	<span>北京</span>
            <span class="weather1">小雨</span>
            <span>7/-13度；</span>
            <span>今日限行尾号为 5、0，明日为不限行！</span>            
        </div>
    </div>
    <!-- 头部右边 -->
    <div class="usermsg rit clearfix">
       <%--  <span class="ico name">早上好，<%=nick_name%></span> --%>
        <span class="ico name">早上好，岩浩</span>
        <b class="line null">分割线</b>
        <a href="javascript:logout()" class="loginout">退出</a>
    </div>
</div>
<script type="text/javascript">
 fillHeaderdata();
 function logout(){
		$.ajax({
			url : platformUrl.logout,
			type : "POST",
			dataType : "json",
			contentType : "application/json; charset=UTF-8",
			async : false,
			beforeSend : function(xhr) {
				if (sessionId) {
					xhr.setRequestHeader("sessionId", sessionId);
				}
			},
			error : function(request) {
				alert("connetion error");
			},
			success : function(data) {
				if(data.result.status=="OK"){
					location.href=platformUrl.toLoginPage;
				}
			}
		}); 
} 
</script>