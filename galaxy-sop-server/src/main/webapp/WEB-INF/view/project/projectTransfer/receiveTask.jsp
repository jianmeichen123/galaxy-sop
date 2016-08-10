<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
   // String projectid=request.getParameter("projectId");
  
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/> 
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
 <script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
 <script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script> 
          <!-- 下半部分 -->
      <DIV class="btm">
        <ul>
		<li><a href="javascript:;" id="receive-task-btn" >接收项目</a></li>
		<li><a href="javascript:;" id="refuse-task-btn" >拒接项目</a></li>
	   </ul>

          </DIV>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
function projectLoaded(project)
{
	
}
var pid = "${projectId}";
 $("#refuse-task-btn").click(function(){
	 var url =platformUrl.toRefuseTransfer
		$.getHtml({
			url: url, 
			data:"",//传递参数
			okback:function(){
				refuseTransfer(pid);
			}//模版反回成功执行	
		}); 
		
 })
 
function refuseTransfer(pid){
	 
	 $("textarea[name='refuseReason']").on("keydown",function(){
			var refuseReason = $(this).val();
			if(refuseReason != ''){
				$("#refuse-reason").css("visibility","hidden");
			}
		});
		$('input[name="projectId"]').val(pid);
		
		$("#refuseReason").click(function(){
			var revokeReason = $("textarea[name='refuseReason']").val();
			if(revokeReason == ''){
				$("#refuse-reason").css("visibility","inherit");
				return;
			}
			var reqUrl=platformUrl.rejectTransfer;
			sendPostRequestByJsonStr(reqUrl, $("#refuse_form").serializeObject(), callbackFunRefuse);
		});
}
function callbackFunRefuse(data){
		if (data.result.status != "OK") {
			layer.msg("拒接项目失败");
	} else {
		layer.msg("拒接项目成功")
		var url = $("#menus .on a").attr('href');
		window.location=url;		
		
	}
}

$("#receive-task-btn").click(function(){
	
	layer.confirm('你确定要接收该项目吗?', 
			{
			  btn: ['确定', '取消'] 
			}, 
			function(index, layero){
				var json = {};
				json['projectId']=pid;
				sendPostRequestByJsonObj(platformUrl.receiveTransfer,json,closeback);
			}, 
			function(index){
				
			}
		);
})

//关闭回调
function closeback(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		layer.msg("error "+data.result.message);
		return;
	}else{
		layer.msg("接收项目成功");
		var url = $("#menus .on a").attr('href');
		window.location=url;		//forwardWithHeader(platformUrl.mpl);
	}
}

</script>
</body>
</html>
	