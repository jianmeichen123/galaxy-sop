<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
   // String projectid=request.getParameter("projectId");
  
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/> 
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
 <script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
 <script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script> 
          <!-- 下半部分 -->
          <DIV class="btm">
            <TABLE width="100%" cellspacing="0" cellpadding="0" >            
              <thead>
                  <tr>
                      <th>姓名 </th>
                      <th>性别</th>
                      <th>年龄 </th>
                      <th>当前职务</th>
                      <th>电话</th>
					  <th>操作</th>
                  </tr>
              </thead>                                                                                                                     
              <tbody id="wanShan">
              </tbody>
          </TABLE> 
       <ul>
		<li><a href="javascript:;" id="complete-task-btn" >提交完成</a></li>
	   </ul>

          </DIV>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
$(function(){
	$(".breadcrumb .bottom_align a").text("完善简历");	
	createMenus(2);
	ProjectRenyuan();
	$("#complete-task-btn").click(function(){
		//更新task为完成状态
		sendPostRequestByJsonObj(
			platformUrl.updateTaskStatus,
			{
				id:"${taskId}",
				taskStatus:"taskStatus:3",
				taskFlag:"${taskFlag}"
			},
			function(data){
				if(data.result.status=="OK"){
					layer.msg("提交成功");
					forwardWithHeader(platformUrl.showTask);
				}
				else
				{
					layer.msg("操作失败");
				}
			}
		);
	});
});
function projectLoaded(project)
{
	
}
function ProjectRenyuan() {		
	var jsonData={"projectId":"${projectId}","tid":"${taskId}"}; 
	sendPostRequestByJsonObj(platformUrl.toWanshan,jsonData, SopTaskRenyuan);
}
function SopTaskRenyuan(data){
	//组装数据	
	var list =  data.entityList;
	if(list != "" || list != undefined || list != null){
		var tbodyList = $("#wanShan"); 
		var i=0;
		$(list).each(function(){
			 var temp = $(this)[0];
			 var personId = temp.id;
			 i=i+1;
			 var tr='<tr>'+
				 '<td>'+ temp.personName+'</td>'+
				 '<td>'+ getValue(temp.personSex)+'</td>'+
				 '<td>'+ geta(temp.personAge)+'</td>'+
				 '<td>'+ geta(temp.personDuties)+'</td>'+
				 '<td>'+ temp.personTelephone+'</td>'+
				 '<td>'+"<a href='javascript:;' onclick='tiaozhuan(" + personId + ");' DATA-btn='resume' >完善简历</a>"+'</td>'+
				' </tr>'; 				
			 tbodyList.append(tr);
		  });	
	}
	function getValue(str) {
		if (str == 0) { 
			 return "男";
		}  else {
			return "女";
		}
	}
	function geta(str) {
		if (typeof(str) == "undefined") { 
			 return "-";
		}  else {
			return str;
		}
	}
/* 	function get(str) {
		if(str=='undefined'||str== undefined){
			return  "";
		}
	}
	 */
}
 function tiaozhuan(personId){
	
	var url =platformUrl.personHr
	$.getHtml({
		url: url+"?personId="+personId, 
		data:"",//传递参数
		okback:function(){
		$(".resumetc .tabtable").tabchange2();
		}//模版反回成功执行	
	}); 
	
}

</script>
</body>
</html>
	