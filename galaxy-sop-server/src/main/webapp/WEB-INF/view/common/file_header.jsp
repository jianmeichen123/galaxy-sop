<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<% 
	String path = request.getContextPath(); 
    User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
    String realName="";
    if(null != user && null != user.getRealName()){
    	realName=user.getRealName();
    }
%>
<link rel="shortcut icon" href="img/favicon.ico" />
<div class="header clearfix">
	<a href="javascript:;" class="logo null">繁星</a>
    <!--头部中间-->
    <div class="min clearfix">
        <!--用户信息-->
        <div class="usermsg clearfix">
            <%-- <span class="light_blue">当前您有：</span>
            <a href="<%=path %>/galaxy/soptask" class="work">待办任务<em class="totalUrgent"></em></a> --%>
            <!-- <a href="<%=path %>/galaxy/soptask" class="work">紧急任务<em class="bubble"></em></a> -->
            <%-- <a href="<%=path %>/galaxy/operationMessage/index" class="work">消息提醒<em action="remind">4</em></a>  --%>
        </div>    	<!--当日信息-->
    	<div class="todaymsg clearfix">
        	<span class="weather"><iframe allowtransparency="true" frameborder="0" width="133" height="36" scrolling="no" src="http://tianqi.2345.com/plugin/widget/index.htm?s=3&z=2&t=1&v=0&d=3&bd=0&k=000000&f=004080&q=1&e=1&a=1&c=54511&w=180&h=36&align=center"></iframe></span>
            <span>
                <em id="sday" style="display:none">
                2014-01-08
                </em>
                <div id="xianhao" class="xianhao">
                <em class="today" id="todayweek"></em>限行尾号为&nbsp;<em class="todaynum" id="todaynum"></em><em>，</em><em class="tomorrow" id="tomorrowweek"></em>限行尾号为&nbsp;<em class="tomorrownum" id="tomorrownum"></em><em>！</em>　
                </div>            
            </span>              
        </div>
    </div>
     <!-- 头部右边 -->
    <div class="usermsg rit clearfix">
        <span class="ico name">您好，<%=realName%></span>
        <b class="line null">分割线</b>
        <a href="javascript:;" onclick="logout()" class="loginout">退出</a>
    </div>
</div>
<script src="<%=path %>/js/car_limit.js"></script>
<script type="text/javascript">
 fillHeaderdata();
 sendPostRequest("<%=request.getContextPath() %>"+"/galaxy/operationMessage/remind", remindcbf);
 function remindcbf(data){
	if(data.result.status == "OK"){
		 $(".work em[action='remind']").html(data.entity.count);
	}
 }
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
				if(userId){
					xhr.setRequestHeader("guserId", userId);
				}
			},
			error : function(request) {
			},
			success : function(data) {
				if(data.result.status=="OK"){
					location.href = platformUrl.toLoginPage;
				}
			}
		}); 
} 
</script>
