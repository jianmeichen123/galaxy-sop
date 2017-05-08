<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
	String path = request.getContextPath(); 
    User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
    String realName="";
    String deptName = "";
    String roleName = "";
    Long userId=null;
    Long roleId=null;
 //   HttpSession session=request.getSession(); 
    if(null != user && null != user.getRealName()){
    	realName=user.getRealName();
    	userId=user.getId();
    	request.getSession().setAttribute("realName", realName);
    //	session.setAttribute("realName", realName);
    }
     
    if(null != user.getRoleId()){
	   roleId = user.getRoleId();
    }
    
    if(null != user && null != user.getDepartmentName()){
    	deptName = user.getDepartmentName();
    //	 session.setAttribute("deptName", deptName);
     }
    if(null != user && null != user.getRole()){
    	roleName = user.getRole();
    //	   session.setAttribute("roleName", roleName);
     }
    
    
   
 
%>
<link href="<%=path %>/css/more1280.css" type="text/css" rel="stylesheet" id="mainCss"/>
<div class="header clearfix">

  <a href="javascript:;" class="logo null">星河投</a>
    
    <!-- 头部右边 -->
    <div class="usermsg rit clearfix">
    	<div class="erwm fl">
    		<span class="erwmfont">APP客户端</span>
    		<div class="erwm_show">
    		<span class="triangle">三角形</span>
    			<ul class="clearfix">
    				<li class="fl">
    					<img alt="" src="<%=path %>/img/Android.png">
    					<span>Android</span>
    				</li>
    				<li class="fr">
    					<img alt="" src="<%=path %>/img/ios.png">
    					<span>iOS</span>
    				</li>    				
    			</ul>
    			<p>
    				<a href="<%=path %>/html/installReadme.html?realname=1" target="_blank">查看iOS安装说明></a>
    			</p>
    		</div>
    	</div>
    	
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
        <a href="http://ctdn.galaxyinternet.com/user/userlogin/auth?uid=<%=user.getSessionId() %>" data-menueid="" target="_blank"><span class="navbar xingmou ctdn"></span>创投大脑</a>
      </div>
    </div>
</div>

<script type="text/javascript">
$("#daiban").attr('href','<%=path %>/galaxy/soptask?sid='+sessionId+'&guid='+userId+'&_is_menu_=true');
reloadMessage();

if(roleId=='1'||roleId=='2'||roleId=='3'){
	
	$("#daiban").remove();
}else{
	
	fillHeaderdata();
}
if(isContainResourceByMark("task_into_view")){
    $('a[resource-mark="task_into_view"]').css("display","block");
	}
	
//window.setInterval("reloadMessage(),fillHeaderdata()",100000000); 
 function reloadMessage(){
 	sendPostRequest(platformUrl.operationMessageRemind, remindcbf);
 }
 function remindcbf(data){
	if(data.result.status == "OK"){
		var remindCount=data.entity.count;
		if(remindCount>99){
			$(".work em[action='remind']").html('<span style="line-height:12px;">99<sup>+</sup></span>')
		}else{
			$(".work em[action='remind']").html(remindCount);
		}
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
/*  function panhhr(){
		if(roleId=='1'&&roleId!=='2'&&roleId=='3'){
			$("#daiban").remove();
		}
	} */

 
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
      $(".wrap_right_bg").show();
      disposedWidth();
      //getScript();
    });
    $(".bico").click(function(){
      $(".small").show();
      $(".big").hide();
      $(".wrap_right_bg").hide();
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
    //getScript();
    if(w_win<=1280){
        $("#mainCss").attr("href","<%=path%>/css/less1280.css");
        $(".pagebox .lft").css("width","60px");
     }else{
       $("#mainCss").attr("href","<%=path%>/css/more1280.css");
        $(".pagebox .lft").css("width","9.375%");

     }
    disposedWidth();
  })
   /*关闭二维码*/
 $(".erwm").hover(function(){
	 $(".erwm_show").toggle();
 })
  })			
 
</script>
