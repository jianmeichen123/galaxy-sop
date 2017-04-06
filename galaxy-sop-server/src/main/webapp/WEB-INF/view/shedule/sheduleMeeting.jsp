<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://www.galaxyinternet.com/tags/acl" prefix="acl" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!--  shedule start -->
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel='stylesheet' href='<%=path %>/shedule/fullcalendar/lib/cupertino/jquery-ui.min.css' />
<link href='<%=path %>/shedule/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='<%=path %>/shedule/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<link href='<%=path %>/shedule/css/fancybox.css' rel='stylesheet' />
<%-- <script src="<%=request.getContextPath() %>/shedule/js/jquery-1.10.2.min.js" type="text/javascript"></script> --%>
<script src='<%=path %>/shedule/fullcalendar/lib/moment.min.js'></script>
<script src='<%=path %>/shedule/fullcalendar/fullcalendar.min.js'></script>
<script src='<%=path %>/shedule/js/jquery.fancybox-1.3.1.pack.js'></script>
<!--  shedule end -->
<script>

  $(document).ready(function() {
	  createMenus(1);
    var meetingType = '<%=request.getAttribute("type")%>';
    
    var meetShedul_defaultView=getCookieValue("meetShedul_defaultView");
    
    if(meetShedul_defaultView){
		deleteCookie("meetShedul_defaultView","/");
	}else{
		meetShedul_defaultView = 'agendaDay';
	}
    
    $('#calendar').fullCalendar({
      theme: true,
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
      },
      // 日期格式化
       monthNames: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],       
            monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],  
            dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],  
            dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],  
            today: ["今天"],  
            firstDay: 1,  
            buttonText: {  
              today: '今天',  
              month: '月',  
              week: '周',  
              day: '日'
              // prev: '上一月',  
              // next: '下一月'  
            }, 
            // allDaySlot:false, 是否显示allday

      defaultView:meetShedul_defaultView,//默认显示视图
      defaultDate: new Date(),
      editable: true,
      axisFormat:'H:mm',
      timeFormat: 'H:mm' ,
      // firstHour:9,
      minTime:8,
      minTime:'8:00',
      maxTime:20,
      maxTime:'20:00',
      allDayText:'',
      slotDuration:'00:10:00',
      eventLimit: true, // allow "more" link when too many events
      events:  function (view) {//动态把数据查出，按照月份动态查询
             $("#calendar").fullCalendar('removeEvents');
             var params={};
             var url = platformUrl.ceosh;
             if('${fx:hasRole(4)}' == 'true'){
            	 url = platformUrl.sh;
             }
             params = {"meetingType":meetingType};
             sendPostRequestByJsonObj(url,params,returnParames);
      },
      eventClick: function(calEvent, jsEvent, view) {
		$('.calendar_box').remove();
        var item = $(".ritmin");
       if(item.find('.calendar_box').length == 0){
        var info = '<div class="calendar_box"><span class="closed">关闭</span><dl class="clearfix"><dt>事项类型：</dt><dd>'+calEvent.title+'</dd></dl><dl class="clearfix"><dt>会议类型：</dt><dd>'+calEvent.meetingType+'</dd></dl><dl class="clearfix"><dt>开始时间：</dt><dd>'+calEvent.start.format("YYYY-MM-DD HH:mm")+'</dd></dl><dl class="clearfix"><dt>结束时间：</dt><dd>'+calEvent.end.format("YYYY-MM-DD HH:mm")+'</dd></dl><dl class="clearfix"><dt>参会项目：</dt><dd><a href="#" class="blue" onclick="showProjectDetail(\'' + calEvent.projectId + '\')">'+calEvent.projectName+'</a></dd></dl><dl class="clearfix"> <dt>投资事业线：</dt><dd>'+calEvent.projectCareerline+'</dd></dl><dl class="clearfix"><dt>投资经理：</dt><dd>'+calEvent.createUname+'</dd></dl></div>';
        item.append(info);
      }
       //var e = event || window.event || arguments.callee.caller.arguments[0];
       var e = event || window.event;
           scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
           scrollY = document.documentElement.scrollTop || document.body.scrollTop;
           x = e.pageX || e.clientX + scrollX;
           y = e.pageY || e.clientY + scrollY;
           x1=$(".calendar_box").width();
           y1=$(".calendar_box").height()/2;
           x2=$(window).width();
      if(x2-x-x1<x1){
        $(".calendar_box").show().css({'top': y-y1,'left':x-x1});
      }
      else{
        $(".calendar_box").show().css({'top': y-y1,'left': x+x1});
      }
        $('.closed').click(function(){
          $('.calendar_box').remove();
      }); 
       
      }
    });
    
    
    if(meetShedul_defaultView){
		
		var view_value = $(".ui-state-active").text();

	    if(meetShedul_defaultView == 'month'){
	    	$("fc-agendaDay-button").removeClass("ui-state-active");
	    	$(".fc-month-button").addClass("ui-state-active");
	    }else if(meetShedul_defaultView == 'agendaWeek'){
	    	$("fc-agendaDay-button").removeClass("ui-state-active");
	    	$(".fc-agendaWeek-button").addClass("ui-state-active");
	    }
	}
    
  });
  
  
  
  function returnParames(data){
	  var resultCollection = data.entityList;
      $.each(resultCollection, function (index, term) {
          $("#calendar").fullCalendar('renderEvent', term, true);
      });
  }

  function showProjectDetail(projectId)
	{
	    var href_url=window.location;
	    var view_value = $(".ui-state-active").text();

	    if(view_value == '月'){
	    	view_value = 'month';
	    }else if(view_value == '周'){
	    	view_value = 'agendaWeek';
	    }else{
	    	view_value = 'agendaDay';
	    }
	    setCookie("meetShedul_defaultView", view_value,24,'/');
		setCookie("href_url", href_url,24,'/');
	   forwardWithHeader(platformUrl.projectDetail+projectId);
	}

  
</script>
</head>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
     <jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	 <!--右中部内容-->
	 	<div class="ritmin">
	    <h2>日程</h2>
	    <div id='calendar'></div>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
</html>