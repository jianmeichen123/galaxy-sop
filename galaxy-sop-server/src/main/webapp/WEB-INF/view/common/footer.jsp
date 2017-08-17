<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script>

function menu_to_view(url,name){
	var _href=window.location.href;
	if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
		var result=$(".pagebox").attr("data-result");
		 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
		if(result=="true"){
			$(window).unbind('beforeunload');
			beforeSave(url);
		}else{
			$(window).unbind('beforeunload');
			if(name == '立项会' || name == '投决会' || name == 'CEO评审会'){
				deleteCookie("meetingSheduleList","/");
			}
			window.location.href = url;
		}
	}else{
		if(name == '立项会' || name == '投决会' || name == 'CEO评审会'){
			deleteCookie("meetingSheduleList","/");
		}
		window.location.href = url;
	}
}

function createMenus(current){
	sendGetRequest(platformUrl.createMenus + current, {}, function(data){
		 var selected = data.header.attachment;
	   	 var html = "";
	   	 $.each(data.entityList, function(i,o){
	   		 if(typeof(o.nodes) == "undefined" || o.nodes == null){
	   			if(selected == o.id){
		   			/* html += '<li class="on"><a href="' + o.url + '" data-menueid="' + o.id + '" ><span class="navbar nav'+o.id+'"></span>' + o.menuName + '</a></li>'; */
		   			//html += '<li class="on"><a href="' + o.url + '"  data-menueid="' + o.id + '" onclick= "menu_to_view(\''+o.url+'\',\''+o.menuName+'\')" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   			html += '<li class="on"><a href="javascript:;" onclick= "menu_to_view(\''+o.url+'\',\''+o.menuName+'\')" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   			
	   			}else{
		   			/* html += '<li><a href="' + o.url + '"  data-menueid="' + o.id + '"><span class="navbar nav'+o.id+'"></span>' + o.menuName + '</a></li>'; */
		   			//html += '<li><a href="' + o.url + '"  data-menueid="' + o.id + '" onclick= "menu_to_view(\''+o.url+'\',\''+o.menuName+'\')" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   			html += '<li><a href="javascript:;" onclick= "menu_to_view(\''+o.url+'\',\''+o.menuName+'\')" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   		}
	   		 }else{
	   			var innerHtml ="";
	   			var isExend = false;
	   			 $.each(o.nodes, function(i,obj){
	   				 if(selected == obj.id){
	   					isExend = true;
	   					/* innerHtml += '<li class="on"><a href="' + obj.url + '" data-menueid="' + o.id + '">' + obj.menuName + '</a></li>'; */
	   					//innerHtml += '<li  class="on"><a href="' + obj.url + '"  data-menueid="' + obj.id + '" onclick= "menu_to_view(\''+obj.url+'\',\''+obj.menuName+'\')" >' + obj.menuName + '</a></li>';
	   					innerHtml += '<li  class="on"><a href="javascript:;" onclick= "menu_to_view(\''+obj.url+'\',\''+obj.menuName+'\')" >' + obj.menuName + '</a></li>';
			   		 }else{
			   			/* innerHtml += '<li><a href="' + obj.url + '" data-menueid="' + o.id + '">' + obj.menuName + '</a></li>'; */
			   			//innerHtml += '<li><a href="' + obj.url + '"  data-menueid="' + obj.id + '" onclick= "menu_to_view(\''+obj.url+'\',\''+obj.menuName+'\')" >' + obj.menuName + '</a></li>';
			   			innerHtml += '<li><a href="javascript:;" onclick= "menu_to_view(\''+obj.url+'\',\''+obj.menuName+'\')" >' + obj.menuName + '</a></li>';
			   		 }
	   			 });
	   			 
	   			 if(isExend){
	   				html += '<li class="toggle_li on"><a href="javascript:;"><span class="navbar nav'+o.navNum+'"></span>'+o.menuName+'</a><ul style="display:block;">';
	   			 }else{
	   				html +='<li class="toggle_li"><a href="javascript:;"><span class="navbar nav'+o.navNum+'"></span>'+o.menuName+'</a><ul>';
	   			 }
	   			 html += innerHtml;
	   			 html += '</ul></li>';
	   		 }
	   	 });
	   	 //星眸链接
	  // 	 html += '<li class="on"><a href="http://xm.galaxyinternet.com/galaxy/index?sid=' + sessionId + '&guid=' + userId + '" data-menueid="" target="_blank"><span class="navbar xingmou"></span>星眸</a></li>';
	   	 $("#menus").html(html);
	   //投后菜单显示隐藏    
	     $(".pagebox .lft .toggle_li").click(function(event) {
	           $(this).children('ul').stop().slideToggle();
	         });
	});
}
</script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/cookie.js"></script>