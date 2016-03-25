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
		   			html += '<li class="on"><a href="' + o.url + '">' + o.menuName + '</a></li>';
		   		}else{
		   			html += '<li><a href="' + o.url + '">' + o.menuName + '</a></li>';
		   		}
	   		 }else{
	   			html += '<li><div><i></i>投后运营</div><ul>';
	   			$.each(o.nodes, function(i,o){
	   				if(selected == o.id){
			   			html += '<li class="on"><a href="' + o.url + '">' + o.menuName + '</a></li>';
			   		}else{
			   			html += '<li><a href="' + o.url + '">' + o.menuName + '</a></li>';
			   		}
	   			});
	   			html += '</ul></li>';
	   		 }
	   	 });
	   	 $("#menus").html(html);
	});
}
</script>
