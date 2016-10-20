<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/highcharts_ext.js" type="text/javascript"></script>
<script src="<%=path %>/js/echarts.js" type="text/javascript"></script>
</head>
<body>
<div class="header clearfix">

  <a href="javascript:;" class="logo null">繁星</a>
    
    <!-- 头部右边 -->
    <div class="usermsg rit clearfix">
       <!--用户信息-->
        <div class="usermsg fl">
            <a href="javascript:;" class="work">待办</a>
            <a href="javascript:;" class="work">紧急<em class="bubble">5</em></a>
            <a href="javascript:;" class="work">消息</a> 
        </div>      <!--当日信息-->
      <div class="man_info fl">
        <span class="ico name"><span class="avator"></span>${sessionScope.galax_session_user.realName }</span>
        <ul>
          <li><a href="#">修改密码</a></li>
          <li><a href="#">退出</a></li>
        </ul>
      </div>
      <div class="xingmou fr">
        <a href="#" target="_blank"><span class="navbar xingmou"></span>星眸</a>
      </div>
    </div>
</div>
<div class="pagebox clearfix">
  <!--右侧-->
  <!-- 收起 -->
    <div class="rit small">
        <div class="sico"></div>
        <!--时间-->
        <div class="top_small">
          <a href="#" class="add blue">添加日程</a>
           <span><b class="b1 null">点</b>今日日程</span>
        </div>
        <!--立项排期会-->
        <div class="bottom_small">
          <ul>
            <li><span class="ico_small lxh"></span><span>立项会</span></li>
            <li><span class="ico_small tjh"></span><span>投决会</span></li>
            <li><span class="ico_small psh"></span><span>CEO评审</span></li>
            <li><span class="ico_small tool"></span><span>常用工具</span></li>
          </ul>
        </div>
    </div>
    <!-- 展开 -->
    <div class="rit big">
      <div class="bico"></div>
      <div class="big_con">
        <!--时间-->
        <div class="top">
            <div class="tody ico">
              <div class="month_box fl">
                <p class="month"><span></span>月</p>
                <p class="month_box_date"></p>
              </div>
              <p class="time time_moment fl"></p>
              <button class="bluebtn btn fr">添加日程</button>
            </div>
            <a href="javascript:;" class="link"><b class="b1 null">点</b>明天，要和创业团队见面</a>
            <a href="javascript:;" class="link"><b class="b2 null">点</b>后天，要和夹克的虾团队见面</a>
        </div>
        <!--立项排期会-->
        <dl>
          <dt>
            <a href="javascript:;" class="blue">排期时间</a>
            <a href="javascript:;" class="more"></a>
          </dt>
            <!-- <dd class='no_content'>暂无内容</dd> -->
 <dd class='clearfix'> 
    <div class="ico_index lxh fl"></div>
    <div class="table fr">
      <table width="100%" cellspacing="0" cellpadding="0" class="index">
        <tbody>
            <tr>
                <td>美好时代</td>
                <td>2016-1-1</td>
            </tr>
            <tr>
                <td class="noborder">美好时代</td>
                <td class="noborder">2016-1-1</td>
            </tr>
            
        </tbody>
    </table>
    </div>    
</dd>
        </dl>
        <!--投决会排期-->
         <dl>
          <dt>
            <a href="javascript:;" class="blue">排期时间</a>
            <a href="javascript:;" class="more"></a>
          </dt>
            <!-- <dd class='no_content'>暂无内容</dd> -->
 <dd class='clearfix'> 
    <div class="ico_index tjh fl"></div>
    <div class="table fr">
      <table width="100%" cellspacing="0" cellpadding="0" class="index">
        <tbody>
            <tr>
                <td>美好时代</td>
                <td>2016-1-1</td>
            </tr>
            <tr>
                <td class="noborder">美好时代</td>
                <td class="noborder">2016-1-1</td>
            </tr>
            
        </tbody>
    </table>
    </div>    
</dd>
        </dl>
        <!-- ceo评审 -->
         <dl>
          <dt>
            <a href="javascript:;" class="blue">排期时间</a>
            <a href="javascript:;" class="more"></a>
          </dt>
            <!-- <dd class='no_content'>暂无内容</dd> -->
 <dd class='clearfix'> 
    <div class="ico_index psh fl"></div>
    <div class="table fr">
      <table width="100%" cellspacing="0" cellpadding="0" class="index">
        <tbody>
            <tr>
                <td>美好时代</td>
                <td>2016-1-1</td>
            </tr>
            <tr>
                <td class="noborder">美好时代</td>
                <td class="noborder">2016-1-1</td>
            </tr>
            
        </tbody>
    </table>
    </div>    
</dd>
        </dl>
        <!-- 常用工具 -->
        <dl  class="tool_radius">
            <dd class="tool">
              <a href="javascript:;" class="light_gray"><b class="b1 ico null">ico</b>通讯录</a>
                <a href="javascript:;" class="light_gray"><b class="b2 ico null">ico</b>估值计算</a>
                <a href="javascript:;"><b class="b3 ico null">ico</b>新增会议</a>
                <a href="javascript:;"><b class="b4 ico null">ico</b>新增访谈</a>
                <a href="javascript:;" class="light_gray"><b class="b5 ico null">ico</b>发邮件</a>
                <a href="javascript:;" class="add ico">&nbsp;</a>
            </dd>
        </dl>
      </div>
      
      
    </div>
	<!--左侧导航-->
  <jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
    <!--内容显示区域-->
 	<div class="ritmin clearfix">
 	<c:forEach var="module" items="${modules }">
 		<div class="floatBox fl" data-url="${module.resourceUrl }"></div>
 	</c:forEach>
  </div>
  
<!--内容显示区域结束-->


</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
  /*展开/收起按钮定位*/
  $(function(){
	  createMenus(1);
    var w_h=$(window).height();
        s_h=$(".sico").height();
    $(".sico").css({"top":(w_h-s_h)/2-80,"left":"-16px"});
    $(".bico").css({"top":(w_h-s_h)/2-80,"left":"-13px"});
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
        $(".pagebox .ritmin").css("margin","70px 0 0 9.375%");
    }

    //右侧展开收起
    $(".sico").click(function(){
      $(".small").hide();
      $(".big").show();
      disposedWidth();
    });
    $(".bico").click(function(){
      $(".small").show();
      $(".big").hide();
      disposedWidth();
    })
    //下拉框
    $(".man_info .name").hover(function(){
      $(".man_info ul").show();
    });
    $(".man_info ul").closeDom();
	
   $(".floatBox").each(function(){
	   var _this = this;
	   var opts = {
		   url : "/"+$(this).data('url'),
		   type:'POST'
	   };
	   $(_this).loadHtml(opts);
   });
    
  })
</script>


</html>


