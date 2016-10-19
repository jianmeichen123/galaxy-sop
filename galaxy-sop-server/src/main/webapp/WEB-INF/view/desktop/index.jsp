<%@ page language="java" pageEncoding="UTF-8"%>
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
        <span class="ico name"><span class="avator"></span>闫皓</span>
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
  <ul class="lft">
      <li class="on"><a href="#"><span class="navbar nav160"></span>工作桌面</a></li>
        <li>
          <a href="javascript:;"><span class="navbar nav171"></span>创投项目</a>
        </li>
        <li>
          <a href="javascript:;"><span class="navbar nav143"></span>数据简报</a>
        </li>
        <li>
          <a href="javascript:;"><span class="navbar nav144"></span>项目分析</a>
        </li>
        <li>
          <a href="javascript:;"><span class="navbar nav147"></span>绩效考核</a>
        </li>
        <li class="toggle_li">
          <a href="javascript:;"><span class="navbar nav150"></span>投后运营</a>
          <ul>
            <li><a href="#" >投后项目跟踪</a></li>
            <li><a href="#">投后业务运营</a></li>
            <li><a href="#">投后企业财报</a></li>
          </ul>
        </li>
    </ul>
    <!--内容显示区域-->
 	<div class="ritmin clearfix">
	<%
		String[] modules = {"task","dataChart","div_duration_gg","div_performance_gg","idea_summary","message_tip","projectProgress","matterPreview","ytxmfx"};
		for(String module : modules)
		{
			String url = "/galaxy/desktop/"+module;
			//if(!hasPermission) break;
	%>
			<div class="floatBox fl" style="display:none">
			<jsp:include page="<%=url %>" flush="true"></jsp:include>
			</div>
	<%
			
		}
	%>
  </div>
<!--内容显示区域结束-->


</div>

<script src="<%=path%>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/js/axure.js" type="text/javascript"></script>
<script src="<%=path%>/js/axure_ext.js" type="text/javascript"></script>
<script>
  /*展开/收起按钮定位*/
  $(function(){
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
	
    $(".floatBox").show();
    
  })
</script>


</html>


