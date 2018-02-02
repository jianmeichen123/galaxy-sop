<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script>

function menu_to_view(url,name,id){
	if(id=="1071"){
		buryPoint("96");
	}else if(id=="1081"){
		buryPoint("116");
	}else if(id=="1091"){
		buryPoint("117");
	}else if(id=="1101" ){
		buryPoint("118");
	}else if(id=="1111" ){
		buryPoint("123");
	}else if(id=="1211" ){
		buryPoint("124");
	}
	var _href=window.location.href;
	if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
		var result=$(".pagebox").attr("data-result");
		 $(".pagebox").attr("data-lis","other");  //区分离开页面时，点击的是tab标签
		if(result=="true"){
			//$(window).unbind('beforeunload');
			beforeSave(url);
		}else{
			//$(window).unbind('beforeunload');
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
		   			html += '<li class="on"><a href="' + o.url + '"  data-menueid="' + o.id + '" onclick= "menu_to_view(\''+o.url+'\',\''+o.menuName+'\',\''+o.id+'\')" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   			//html += '<li class="on"><a href="javascript:;" onclick= "menu_to_view(\''+o.url+'\',\''+o.menuName+'\')" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   			
	   			}else{
		   			/* html += '<li><a href="' + o.url + '"  data-menueid="' + o.id + '"><span class="navbar nav'+o.id+'"></span>' + o.menuName + '</a></li>'; */
		   			html += '<li><a href="' + o.url + '"  data-menueid="' + o.id + '" onclick= "menu_to_view(\''+o.url+'\',\''+o.menuName+'\',\''+o.id+'\')" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   			//html += '<li><a href="javascript:;" onclick= "menu_to_view(\''+o.url+'\',\''+o.menuName+'\')" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   		}
	   		 }else{
	   			var innerHtml ="";
	   			var isExend = false;
	   			 $.each(o.nodes, function(i,obj){
	   				 if(selected == obj.id){
	   					isExend = true;
	   					/* innerHtml += '<li class="on"><a href="' + obj.url + '" data-menueid="' + o.id + '">' + obj.menuName + '</a></li>'; */
	   					innerHtml += '<li  class="on"><a href="' + obj.url + '"  data-menueid="' + obj.id + '" onclick= "menu_to_view(\''+obj.url+'\',\''+obj.menuName+'\')" >' + obj.menuName + '</a></li>';
	   					//innerHtml += '<li  class="on"><a href="javascript:;" onclick= "menu_to_view(\''+obj.url+'\',\''+obj.menuName+'\')" >' + obj.menuName + '</a></li>';
			   		 }else{
			   			/* innerHtml += '<li><a href="' + obj.url + '" data-menueid="' + o.id + '">' + obj.menuName + '</a></li>'; */
			   			innerHtml += '<li><a href="' + obj.url + '"  data-menueid="' + obj.id + '" onclick= "menu_to_view(\''+obj.url+'\',\''+obj.menuName+'\')" >' + obj.menuName + '</a></li>';
			   			//innerHtml += '<li><a href="javascript:;" onclick= "menu_to_view(\''+obj.url+'\',\''+obj.menuName+'\')" >' + obj.menuName + '</a></li>';
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
	   	//意见反馈
	 	html += '<li data-type="feedback"><span>意见反馈<i class="sj"></i></span><img src="<%=request.getContextPath()%>/img/feedback.png" onclick="feedback();"/></li>' 
	  $("#menus").html(html);
	   //投后菜单显示隐藏    
	     $(".pagebox .lft .toggle_li").click(function(event) {
	           $(this).children('ul').stop().slideToggle();
	         });
	});
}

//吐槽
var _nickname=$('.man_info .name').text();
var _src='https://fxapp.galaxyinternet.com/appFiles/head_image/touxiang@3x.png';
var data = {
	      "nickname": _nickname,    //发帖人名称
	      "avatar": _src,
	      "openid": userId,    //用户ID
	  },
	  productId = 21398;
function feedback(){   //跳转吐个槽
	Tucao.request(productId, data);
} 
</script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/cookie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/tucao/tucao.js"></script>