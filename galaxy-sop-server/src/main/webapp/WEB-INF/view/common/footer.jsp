<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script type="text/javascript">
function createMenus(current){
	sendGetRequest(platformUrl.createMenus + current, {}, function(data){
		 var selected = data.header.attachment;
	   	 var html = "";
	   	 $.each(data.entityList, function(i,o){
	   		 if(typeof(o.nodes) == "undefined"){
	   			if(selected == o.id){
		   			html += '<li class="on"><a href="' + o.url + '" data-menueid="' + o.id + '" >' + o.menuName + '</a></li>';
		   		}else{
		   			html += '<li><a href="' + o.url + '"  data-menueid="' + o.id + '">' + o.menuName + '</a></li>';
		   		}
	   		 }else{
	   			var innerHtml ="";
	   			var isExend = false;
	   			 $.each(o.nodes, function(i,obj){
	   				 if(selected == obj.id){
	   					isExend = true;
	   					innerHtml += '<li class="on"><a href="' + obj.url + '" data-menueid="' + o.id + '">' + obj.menuName + '</a></li>';
			   		 }else{
			   			innerHtml += '<li><a href="' + obj.url + '" data-menueid="' + o.id + '">' + obj.menuName + '</a></li>';
			   		 }
	   			 });
	   			 
	   			 if(isExend){
	   				html += '<li><div><i class="hide"></i>'+o.menuName+'</div><ul style="display:block;">';
	   			 }else{
	   				html += '<li><div><i></i>'+o.menuName+'</div><ul>';
	   			 }
	   			 html += innerHtml;
	   			 html += '</ul></li>';
	   		 }
	   	 });
	   	 $("#menus").html(html);
	   	 $(".pagebox .lft div").click(function(event) {
	   		 $(this).siblings().stop().slideToggle();
	   		 $(this).children('i').toggleClass('hide');
	   	 });
	});
}
</script>
