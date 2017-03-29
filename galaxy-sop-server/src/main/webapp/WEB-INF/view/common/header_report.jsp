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
	<title>星河投</title>
</head>
<link rel="shortcut icon" href="<%=request.getContextPath() %>/img/favicon.ico" />
<link href="<%=path %>/css/more1280.css" type="text/css" rel="stylesheet" id="mainCss"/>
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
	<p>星河投APP</p>
   	<img src="<%=path %>/img/ewms.gif" alt="">
</div>
<div class="erwmb erwm">
   	<img src="<%=path %>/img/ewmb.gif" alt="">
   	<p>星河投APP客户端</p>
   	<p><a href="<%=path %>/html/installReadme.html?realname=1" target="_blank">查看安装说明</a></p>
</div>
<div class="header clearfix">
	<a href="javascript:;" class="logo null">星河投</a>
     <!-- 头部右边 -->
    <div class="usermsg rit clearfix">
       <!--用户信息-->
        <div class="usermsg fl">
              <a href="<%=path %>/galaxy/soptask" class="work" resource-mark="task_into_view" style="display:none" id="daiban">待办<em class="totalUrgent"></em></a>
            <a href="<%=path %>/galaxy/operationMessage/index" class="work">消息<em action="remind">0</em></a> 
    
        </div>      <!--当日信息-->
      <div class="man_info fl">
        <span class="ico name"><span class="avator"></span><%=realName%></span>
        <ul>
          <li><a href="/sop/html/change_password.html" id="hid" data-btn="change_password">修改密码</a></li>
           <a href="javascript:;" onclick="logout()" class="loginout">退出</a>
        </ul>
      </div>
      <div class="xingmou fr">
        <a href="http://xm.galaxyinternet.com/galaxy/index?sid=' + sessionId + '&guid=' + userId + '" data-menueid="" target="_blank"><span class="navbar xingmou"></span>星眸</a>
      </div>
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
function initHref(){
	//$("#work_dbrw").attr('href' , platformUrl.showTask);//待办任务
	//$("#work_jjrw").attr('href' , platformUrl.showTask);//紧急任务
	$("#work_xxtx").attr('href' , platformUrl.MessageIndex);//消息提醒
}

$(function(){
	  /*展开/收起按钮定位*/
   var w_h=$(window).height();
       s_h=$(".sico").height();
       rit_h=Math.round($(".ritmin-index .floatBox").length/2);
   $(".sico").css({"top":(w_h-s_h)/2-80,"left":"-16px"});
   $(".bico").css({"top":(w_h-s_h)/2-30,"right":"330px"});
   if(w_h>226*rit_h){
   	$(".pagebox .big").css({"height":w_h})
   }else{
   	$(".pagebox .big").css("height",226*rit_h)
   }
   
   //获取当前日期
   var myDate = new Date();
   $(".month_box .month span").text(myDate.getMonth()+1);
   $(".month_box_date").text(myDate.getDate());

   //首页获取ritmin的宽度
   disposedWidth();
   function disposedWidth(){
     var w_win=$(window).width();
         display =$('.small').css('display');
         if(display == 'none'){
           w_rit=$(".big").outerWidth()+20;
         }else{
           w_rit=$(".small").outerWidth()+20;
         }
       w_lft=$(".lft").width();
       w_ritmin=w_win-w_rit-w_lft;
       $(".floatBox").css("width",w_ritmin/2-20);
       $(".pagebox .ritmin").css("margin","60px 0 0 9.375%");
       $(".pagebox .ritmin-index").css("margin","70px 0 0 9.375%");
       $(".pagebox .ritmin").css("width",w_ritmin-10);
       $(".pagebox .ritmin-index").css("width",w_ritmin);
       $(".pagebox .ritmin").css("margin-left",w_lft);
   }
   //右侧展开收起
   $(".sico").click(function(){
     $(".small").hide();
     $(".big").show();
     disposedWidth();
     //getScript();
   });
   $(".bico").click(function(){
     $(".small").show();
     $(".big").hide();
     disposedWidth();
     //getScript();
   })
   //下拉框
   $(".man_info .name").hover(function(){
     $(".man_info ul").show();
   });
   $(".man_info ul").closeDom();
   var man_info_width=$(".man_info").width();
   $(".man_info ul").css("left",(man_info_width-140)/2);
	
  $(".floatBox").each(function(){
	   var _this = this;
	   var opts = {
		   url : "/"+$(this).data('url'),
		   type:'POST'
	   };
	   $(_this).loadHtml(opts);
  });
  
 //改变屏幕大小时，重新调用图表的js文件
<%--  function getScript(){
	  $.getScript("<%=path %>/js/echarts_health.js");
     $.getScript("<%=path %>/js/charts/projectPostAnalysis.js");
     $.getScript("<%=path %>/js/indexProjectProgress.js");
     $.getScript("<%=path %>/js/charts/projectProgress.js");
     $.getScript("<%=path %>/js/charts/indexProjectDuration.js");
     $.getScript("<%=path %>/js/charts/indexKpi.js");
 } --%>
//浏览器小于1280的时候左侧导航
 var w_win=$(window).width();
 if(w_win<=1280){   //浏览器屏幕等于1280，默认加载样式
      $("#mainCss").attr("href","<%=path%>/css/less1280.css");
      $(".pagebox .lft").css("width","60px");
      disposedWidth();
       w_lft=$(".lft").width();        
   }else{
     $(".pagebox .lft").css("width","9.375%");
      disposedWidth();
   }
//浏览器窗口该变，自适应
$(window).resize(function(){
	var w_win=$(window).width();
   disposedWidth();
   //getScript();
   if(w_win<=1280){
       $("#mainCss").attr("href","<%=path%>/css/less1280.css");
       $(".pagebox .lft").css("width","60px");
    }else{
      $("#mainCss").attr("href","<%=path%>/css/more1280.css");
       $(".pagebox .lft").css("width","9.375%");

    }
 })
    /*二维码*/
 var ewm_w=$(".erwms").width();
     w_lft=$(".lft").width();
     $(".erwms").css("margin-left",(w_lft-ewm_w)/2);
 $(".erwms").on("mouseover",function(){
	 $(this).hide();
     $(".erwmb").show();
 })
  $(".erwmb").on("mouseout",function(){
	 $(".erwms").show();
     $(this).hide();
 })
 
 })	
</script>