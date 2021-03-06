<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--[if lt IE 9]><link href="<%=request.getContextPath() %>/css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
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

<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/more1280.css" type="text/css" rel="stylesheet" id="mainCss"/>
<div class="header clearfix">

  <a href="javascript:;" class="logo null">星河投</a>    
     <c:if test="${fx:hasPremission('project_search_overall')}">
    <div class='fl input-search'>
    	<input class="globleSearchInput" type="text" placeholder="请输入关键字进行搜索"/>
    	<span class="hideThis">全局搜索</span>
    	<ul class='globalSearhc-ul clearfix'>
    		<li class='search_history'>搜索历史</li>
    		<ul class="history-content">
    		
    		</ul>
    	</ul>
    </div>
  </c:if>
    <!-- 头部右边 -->
    <div class="usermsg rit clearfix">
    	<div class="erwm fl">
    		<span class="erwmfont">APP客户端</span>
    		<div class="erwm_show">
    		<span class="triangle">三角形</span>
    			<ul class="clearfix">
    				<li class="fl">
    					<img alt="" src="<%=path %>/img/Android.png?version=1.0">
    					<span>Android</span>
    				</li>
    				<li class="fr">
    					<img alt="" src="<%=path %>/img/ios.png?version=1.0">
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
              <a href="<%=path %>/galaxy/soptask" class="work" resource-mark="task_into_view" style="display:none" id="daiban" onclick="buryPoint('126')">待办<em class="totalUrgent"></em></a>
            <a href="<%=path %>/galaxy/operationMessage/index" class="work" onclick="buryPoint('125')">消息<em action="remind" style="display: none">0</em></a>
    
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
    <div class='system-tips' style="display:none"><span id="content">为了让您更好的使用星河投，我们将在今晚19:00后对系统进行升级，升级期间暂时无法访问，请提前保存好您的数据信息！</span><span class='system-tips-close' style='font-size: 20px;'>×</span></div>
</div>

<script type="text/javascript">
/*100个汉字限制  */
 $('.input-search').bind('input propertychange',function(){
	 	var inputValue = $('.input-search input').val();
	 	if(inputValue.length>100){
	 		var inputValue = inputValue.substring(0,100);
	 		$('.input-search input').val(inputValue)
	 	}
})

/* 页面跳转,通过url传递参数 ,保存搜索历史传给后台*/
	 $('.input-search span').click(function(){
		 var _href=window.location.href;
		 /* 获取搜索的关键字 */
		 var keyword = $('.globleSearchInput').val().trim();
		 if(keyword == ''){
			 return false
		 }else{
			 var _url="<%=path %>/galaxy/test/searchResult?keyword="+keyword; 
			 if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
					var result=$(".pagebox").attr("data-result");
					 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
					if(result=="true"){
						beforeSave(_url);
					}else{
					 	//调用保存搜索历史方法
					 	 var url= "<%=path %>/galaxy/infoDanao/saveSearchHistory"
							 var data = {
									 keyword:keyword
							 }
							 $.ajax({
								 type:"POST",
								 url:url,
								 data:data,
								 dataType:'json',
								 success:function(data){
								 }
							 }) 
					 
					 
					 window.location.href="<%=path %>/galaxy/test/searchResult?keyword="+keyword; 
						$('.globleSearchInput').val(keyword)
						
					}
				}
		 }
		 
		
		 
		
	 })
	 
	 /* 键盘事件 */
	<%--  $('.globleSearchInput').bind('keypress',function(event){
		 var keyword = $('.globleSearchInput').val().trim();
		 if(event.keyCode=='13'){
			 window.location.href="<%=path %>/galaxy/test/searchResult?keyword="+keyword;
		 }
		 
	 }) --%>
/* 从后台获取搜索历史 */
 $('.globleSearchInput').mouseenter(function(){
	 var url = "<%=path %>/galaxy/infoDanao/searchHistory"
	 $.ajax({
		 type:"POST",
		 url:url,
		 data:'',
		 dataType:'json',
		 success:function(data){
			// $('.globalSearhc-ul').show();
			 if(data.result.status=='OK'){
				 	if(data.entity != undefined){
				 		var hisList = data.entity.hisList;
						 var hisList = hisList.slice(0,10)
					 	 var html =''
						 for(var i = 0;i<hisList.length;i++){
							html += "<li class='seach_li'>"+hisList[i]+"</li>"
						 }
				 	}
					$('.history-content').html(html); 
					/* 点击li跳转页面进行搜索 */
					 $(".seach_li").click(function(){
							var keyword = $(this).text();
							var _href=window.location.href;
							var _url="<%=path %>/galaxy/test/searchResult?keyword="+keyword;
							if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
								var result=$(".pagebox").attr("data-result");
								 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
								if(result=="true"){
									beforeSave(_url);
								}else{
									window.location.href=_url;							
								}
							}
						  
						 
					 })
					 
			 }
			 
		 }
	 }) 
 })
 
 
 
 

$(document).click(function(e){
	//e.preventDefault()
	var target  = $(e.target);
	if(target.parents('.input-search').length == 0){
		$('.globalSearhc-ul').hide();
	}
	
}); 
 $('.globleSearchInput').focus(function(){
	$(this).parent().find('.globalSearhc-ul').show();
}) 
/* $('.globalSearhc-ul').mouseleave(function(){
	$(this).hide();
}) */
	 
	  
			 
	 
	 
	 
	 
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
	 sendPostRequestByJsonObj(platformUrl.operationMessageRemind,{uid:"<%=userId%>"}, remindcbf);
 }
 function remindcbf(data){
	if(data.status == "OK"){
		var remindCount=data.map.count;

        if(remindCount == 0) {
            $(".work em[action='remind']").css("display","none");
        }else{
            $(".work em[action='remind']").css("display","block");
            if(remindCount>99){
                $(".work em[action='remind']").html('<span style="line-height:12px;">99<sup>+</sup></span>')
            }else{
                $(".work em[action='remind']").html(remindCount);
            }
        }
	}
 }
 function logout(url){
	 var _href=window.location.href;
	 var url=platformUrl.toLoginPage;
	 if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
			var result=$(".pagebox").attr("data-result");
			 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
			if(result=="true"){
				//$(window).unbind('beforeunload');
				beforeSave(url);
			}else{
				//$(window).unbind('beforeunload');
				$.ajax({
					url : platformUrl.logout,
					type : "POST",
					dataType : "json",
					contentType : "application/json; charset=UTF-8",
					async : false,
					beforeSend : function(xhr) {
						xhr.setRequestHeader("gt", "pc");
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
						location.href = url
					}
				}); 
			}
		}
} 
/*  function panhhr(){
		if(roleId=='1'&&roleId!=='2'&&roleId=='3'){
			$("#daiban").remove();
		}
	} */

 
$(function(){
	queryExitMessage();
	
	//$("")
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
       // alert(w_ritmin)
		var Sys = {};
		var ua = navigator.userAgent.toLowerCase();
		var s;
		(s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] :
		(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
		 0;
		if(Sys.ie!=undefined){
			//w_ritmin=w_ritmin-25;
			w_ritmin=w_ritmin-10;
		}else{
			w_ritmin=w_ritmin-10;
		}
        $(".floatBox").css("width",w_ritmin/2-20);
        $(".pagebox .ritmin").css("margin-left","9.375%");
        $(".pagebox .ritmin-index").css("margin","70px 0 0 9.375%");
        $(".pagebox .ritmin").css("width",w_ritmin);
        $(".pagebox .ritmin-index").css("width",w_ritmin);
        $(".pagebox .ritmin").css("margin-left",w_lft);
        if($("#new_right").css("width")!=undefined){
        	var anchor_width=$("#new_right").css("width").replace("px","");
        	var  anchor_nav=$("#div-content").contents().find(".anchor_nav");
        	anchor_nav.css("width",Number(anchor_width));
        }
    }
    //右侧展开收起
    $(".sico").click(function(){
      $(".small").hide();
      $(".big").show();
      $(".wrap_right_bg").show();
      disposedWidth();
      //getScript();
      if($('#container_kpi').is(":visible")){
    	  load_data_chart_kpi();   //重新渲染绩效考核
      }
      
    });
    $(".bico").click(function(){
      $(".small").show();
      $(".big").hide();
      $(".wrap_right_bg").hide();
      disposedWidth();
      //getScript();
      if($('#container_kpi').is(":visible")){
    	  load_data_chart_kpi();   //重新渲染绩效考核
      }
    })
   // $(".man_info ul").closeDom();
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
  if(w_win<=1290){   //浏览器屏幕等于1280，默认加载样式,1290解决适配
       $("#mainCss").attr("href","<%=path%>/css/less1280.css");
       $(".pagebox .lft").css("width","60px");
      $(".add-project-title").css("left","60px");
       disposedWidth();
        w_lft=$(".lft").width();        
    }else{
      $(".pagebox .lft").css("width","9.375%");      
      $(".add-project-title").css("left","9.375%");
       disposedWidth();
    }
//浏览器窗口该变，自适应
$(window).resize(function(){
	var w_win=$(window).width();
    //getScript();
    if(w_win<=1290){
        $("#mainCss").attr("href","<%=path%>/css/less1280.css");
        $(".pagebox .lft").css("width","60px");
      $(".add-project-title").css("left","60px");
     }else{
       $("#mainCss").attr("href","<%=path%>/css/more1280.css");
        $(".pagebox .lft").css("width","9.375%");
      $(".add-project-title").css("left","9.375%");

     }
    disposedWidth();
  })
   /*关闭二维码*/
 $(".erwm").hover(function(){
	 $(".erwm_show").toggle();
 })
 
 
  })
  
  
  
 var messageId;
function queryExitMessage(){
	  var url = "<%=path %>/galaxy/systemMessage/sml?"+new Date().getTime();
		var dataJson={
				"osType":"web",
				"sendStatus":"messageStatus:2",
				"maxTime":new Date().format('yyyy-MM-dd hh:mm')
		}
		sendPostRequestByJsonObj(
			 url,
		dataJson,
		function(data){ 
			if(data.result.status=="OK"){
				if(null!=data.entityList&&data.entityList.length>0){
					messageId=data.entityList[0].id;
					$("#content").html(data.entityList[0].messageContent);
					queryExitUserMessage(messageId);
				}
			}
		 })
	}
function queryExitUserMessage(messageId){
	  var url = "<%=path %>/galaxy/systemMessageUser/suml?"+new Date().getTime();
		var dataJson={
				"messageOs":"web",
				"messageId":messageId
		}
		sendPostRequestByJsonObj(
			 url,
		dataJson,
		function(data){ 
			if(data.result.status=="OK"){
				var message;
				if(null!=data.entityList&&data.entityList.length==0){
					$(".system-tips").css("display","block")
				}
			}
		 })
	}
$('.system-tips-close').click(function(){
	 // $(this).parent().remove();
	  $(this).parent().remove();
	  var data = {
		};
		var url = "<%=path %>/galaxy/systemMessageUser/amu?"+new Date().getTime();
		data.messageOs="web";
		data.messageId=messageId;
		sendPostRequestByJsonObj(url, data, function(data) {
			var result = data.result.status;
			if (result == "ERROR") { //OK, ERROR
				layer.msg(data.result.message);
				return;
			} else {
				/* layer.msg("保存成功", {
					time : 500
				}); */
			}
		});
 })
 
</script>
