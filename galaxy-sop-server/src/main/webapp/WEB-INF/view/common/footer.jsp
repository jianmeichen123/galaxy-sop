<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script src="<%=path %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script>
<script src="<%=path %>/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
function createMenus(current){
	$.ajax({
		 url:"<%=path %>/galaxy/common/menu/" + current,
		 data:{},
		 async: false,
		 type: "GET",
		 dataType:"json",
		 contentType:"application/json; charset=UTF-8",
	     cache: true,
	     error: function(request) {
	         alert("Connection error");
	     },
	     success: function(data) {
	    	 var selected = data.header.attachment;
	    	 var html = "";
	    	 $.each(data.entityList, function(i,o){
	    		 if(selected == o.id){
	    			html += '<li class="on"><a href="' + o.url + '">' + o.menuName + '</a></li>';
	    		 }else{
	    			html += '<li><a href="' + o.url + '">' + o.menuName + '</a></li>';
	    		 }
	    	 });
	    	 $("#menus").html(html);
	  	 }
	 }); 
}
</script>
