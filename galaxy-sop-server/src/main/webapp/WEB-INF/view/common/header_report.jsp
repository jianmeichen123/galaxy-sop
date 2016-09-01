<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<%
	User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
	String sessionId = "";
	String realName = "";
	String deptName = "";
    String roleName = "";
	long roleId = 1;
	Long userId = null;
	if(null != user) {
		sessionId = user.getSessionId();
		if(null != user.getRealName()){
			realName = user.getRealName();
			roleId = user.getRoleId();	
		}
		userId = user.getId();  
	}
	boolean isHHR = ( roleId==3 ? true : false ); 
	boolean isCEO = ( roleId==2 ? true : false );
	boolean isDSZ = ( roleId==1 ? true : false );
	
	if(null != user && null != user.getDepartmentName()){
    	deptName = user.getDepartmentName();
    //	 session.setAttribute("deptName", deptName);
     }
    if(null != user && null != user.getRole()){
    	roleName = user.getRole();
    //	   session.setAttribute("roleName", roleName);
     }
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
	<title>繁星</title>
</head>
<link rel="shortcut icon" href="<%=request.getContextPath() %>/img/favicon.ico" />
<script type="text/javascript">
var path = "<%=request.getContextPath()%>";
var basePath = "<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>";
var basePath_noport = "<%=request.getScheme()+"://"+request.getServerName()%>";
var hostPath = "<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>";
var isHHR = "<%=isHHR%>";
var isCEO = "<%=isCEO%>";
var isDSZ = "<%=isDSZ%>";
</script>
<!-- jsp文件头和头部 -->
<jsp:include page="taglib.jsp" flush="true"></jsp:include>
<!--[if lt IE 9]><link href="<%=request.getContextPath() %>/css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->


<!-- report start -->
<link href="<%=request.getContextPath() %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=request.getContextPath() %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>

<script src="<%=request.getContextPath() %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/highcharts_ext.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<!-- bootstrap-table -->
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-report.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- bootstrap-datetimepicker -->
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=request.getContextPath() %>/js/cookie.js" type="text/javascript"></script>

<script src="<%=request.getContextPath() %>/js/common_report.js" type="text/javascript"></script>
<!-- end -->


<body>
<div class="erwms erwm">
	<p>繁星APP</p>
   	<img src="<%=path %>/img/ewms.gif" alt="">
</div>
<div class="erwmb erwm">
   	<img src="<%=path %>/img/ewmb.gif" alt="">
   	<p>繁星APP客户端</p>
   	<p><a href="<%=path %>/html/installReadme.html?realname=1" target="_blank">查看安装说明</a></p>
</div>
<div class="header clearfix">
	<div class="warning" id="warning"><i></i>您的浏览器版本过低，繁星系统不提供对IE10以下浏览器的支持，快使用速度更快，体验更好的浏览器吧！&nbsp;<a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" class="red">IE11</a>&nbsp;&nbsp;<a href="http://rj.baidu.com/soft/detail/14744.html?ald" class="red">谷歌浏览器</a><em id="close" onclick="gb()"></em></div>
	<a href="javascript:;" class="logo null">繁星</a>
    <!--头部中间-->
    <div class="min clearfix">
        <!--用户信息-->
        <div class="usermsg clearfix">
            <span class="light_blue">当前您有：</span>
            <!-- <a href="javascript:;" class="work" id="work_dbrw">待办任务<em class="totalUrgent"></em></a> -->
            <!-- <a href="javascript:;" class="work" id="work_jjrw">紧急任务<em class="bubble"></em></a> -->
            <a href="javascript:;" class="work" id="work_xxtx">消息提醒<em action="remind">0</em></a>
        </div>    	<!--当日信息-->
    	<div class="todaymsg clearfix">
        	<span class="weather"><iframe allowtransparency="true" frameborder="0" width="220" height="36" scrolling="no" src="http://tianqi.2345.com/plugin/widget/index.htm?s=3&z=2&t=1&v=0&d=3&bd=0&k=000000&f=004080&q=1&e=1&a=1&c=54511&w=180&h=36&align=center"></iframe></span>
            <span>
                <em id="sday" style="display:none">
                2014-01-08
                </em>
                <div id="xianhao" class="xianhao">
                <em class="today" id="todayweek"></em>限行尾号为<em class="todaynum" id="todaynum"></em><em>，</em><em class="tomorrow" id="tomorrowweek"></em>为<em class="tomorrownum" id="tomorrownum"></em><em>！</em>　
                </div>            
            </span>              
        </div>
    </div>
    <!-- 头部右边 -->
    <div class="usermsg rit clearfix">
        <span class="ico name"  href="<%=path%>/html/register.html?&realName=<%=realName%>&deptName=<%=deptName%>&roleName=<%=roleName%>" data-btn="login_infor">您好，<%=realName%></span>
        <b class="line null">分割线</b>
        <a href="javascript:logout();" class="loginout">退出</a>
    </div>
</div>
<script src="<%=path %>/js/car_limit.js"></script>
<script type="text/javascript">
fillHeaderdata();
initHref();
reloadMessage();
//window.setInterval("reloadMessage();",1000000000); 
function reloadMessage(){
	sendPostRequest(platformUrl.operationMessageRemind, remindcbf);
}
//浏览器判断start
/*var warn=document.getElementById('warning');
var close=document.getElementById('close');
    var userAgent = navigator.userAgent,   
rMsie = /(msie\s|trident.*rv:)([\w.]+)/,   
rFirefox = /(firefox)\/([\w.]+)/,   
rOpera = /(opera).+version\/([\w.]+)/,   
rChrome = /(chrome)\/([\w.]+)/,   
rSafari = /version\/([\w.]+).*(safari)/;  
var browser;  
var version;  
var ua = userAgent.toLowerCase();  
function uaMatch(ua){  
  var match = rMsie.exec(ua);  
  if(match != null){  
    return { browser : "IE", version : match[2] || "0" };  
  }  
  var match = rFirefox.exec(ua);  
  if (match != null) {  
    return { browser : match[1] || "", version : match[2] || "0" };  
  }  
  var match = rOpera.exec(ua);  
  if (match != null) {  
    return { browser : match[1] || "", version : match[2] || "0" };  
  }  
  var match = rChrome.exec(ua);  
  if (match != null) {  
    return { browser : match[1] || "", version : match[2] || "0" };  
  }  
  var match = rSafari.exec(ua);  
  if (match != null) {  
    return { browser : match[2] || "", version : match[1] || "0" };  
  }  
  if (match != null) {  
    return { browser : "", version : "0" };  
  }  
}  
var browserMatch = uaMatch(userAgent.toLowerCase());  
if (browserMatch.browser){  
  browser = browserMatch.browser;  
  version = browserMatch.version;  
}  
var ma=browser+version;
if (ma=='IE10.0'||ma=='IE11.0'||browser=='chrome'||browser=='safari'){
    warn.style.display='none';
}
else{
    warn.style.display='block';
}
function gb(){
     warn.style.display='none';
}*/
//浏览器判断end

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
			alert("connetion error");
		},
		success : function(data) {
			if(data.result.status=="OK"){
				location.href=platformUrl.toLoginPage;
			}
		}
	}); 
} 
function initHref(){
	//$("#work_dbrw").attr('href' , platformUrl.showTask);//待办任务
	//$("#work_jjrw").attr('href' , platformUrl.showTask);//紧急任务
	$("#work_xxtx").attr('href' , platformUrl.MessageIndex);//消息提醒
}

/*二维码放大*/
 $(".erwms").on("mouseover",function(){
	 $(this).hide();
     $(".erwmb").show();
 })
  $(".erwmb").on("mouseout",function(){
	 $(".erwms").show();
     $(this).hide();
 })
 
</script>