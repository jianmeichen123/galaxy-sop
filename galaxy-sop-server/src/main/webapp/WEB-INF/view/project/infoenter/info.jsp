<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>  
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include> 
</head>

<body >

	<div class="pagebox">
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
		<div class="ritmin">
			<div class="infoTop clearfix">
				<h5>已选择项目“滴滴出行”进行引用</h5>
				<p><i>选择有效的信息，快速添加到项目内</i></p>
				<ul class="scheduleIcon clearfix">
					<li data-content ="1" class="active"><p>引用推荐项目</p></li>
					<li data-content="2" class="active"> <p>选择有效推荐信息</p></li>
					<li data-content="3"> <p>完善项目剩余信息</p></li>
				</ul>
				<a href="javascript:history.go(-1)" class="rightLink">重新选择项目引用></a>
			</div> 
			<div class="tableBox infoBox">
				 <ul class="infoConList">
				 	<li class="infoItem" id="companyInfo">
				 		<p class="titles">法人信息</p>
				 		<table class="tabrow"> 
				 			<tbody>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>公司名称</td>
				 					<td>data</td>
				 				</tr>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>成立日期</td>
				 					<td>data</td>
				 				</tr>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>法人</td>
				 					<td>data</td>
				 				</tr>
				 			</tbody>
				 		</table>
				 	</li>
			 	 	<li class="infoItem" id="equityInfo">
				 		<p class="titles">股权结构</p>
				 		<table class="tabrow"> 
				 			<thead>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td> 
				 					<td>股东名称</td>
				 					<td>股东类型</td>
				 					<td>股东性质</td>
				 					<td>占股比例</td>
				 					<td>备注</td>	
				 				</tr>
				 			</thead>
				 			<tbody>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td> 
				 				</tr>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td> 
				 				</tr>
				 			</tbody>
				 		</table>
				 	</li>
				 	<li class="infoItem" id="teamInfo">
				 		<p class="titles">团队成员</p>
		 				<table class="tabrow"> 
				 			<thead>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td> 
				 					<td>姓名</td>
				 					<td>职位</td> 	
				 				</tr>
				 			</thead>
				 			<tbody>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>data</td>
				 					<td>data</td> 
				 				</tr>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>data</td>
				 					<td>data</td> 
				 				</tr>
				 			</tbody>
				 		</table>
				 	</li>
				 	<li class="infoItem" id="fina_historyInfo ">
				 		<p class="titles">历史融资</p>
				 		<table class="tabrow"> 
				 			<thead>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td> 
				 					<td>融资轮次</td>
				 					<td>融资时间</td>
				 					<td>融资金额</td>
				 					<td>币种</td>
				 					<td>股权占比</td>	
				 					<td>投资方</td>	 
				 				</tr>
				 			</thead>
				 			<tbody>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>	
				 					<td>data</td>
				 				</tr>
				 				<tr>
				 					<td>
				 						<input type="checkbox" />
				 					</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>
				 					<td>data</td>	
				 					<td>data</td>
				 				</tr>
				 			</tbody>
				 		</table>
				 	</li>
				 </ul>
				 <a href="javascript:;" class="add_pro" >保存</a>
			</div>
			<div class="tableBox emptyInfo">
				<div class="empty">
					 暂无推荐信息
				</div>
			</div>
		</div> 
	</div>
	

</body> 
<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>
<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../../common/uploadwin.jsp" flush="true"></jsp:include>
	  
</html>
<script>
$(function(){
	//导航
	createMenus(5);
	var _url = Constants.sopEndpointURL +"/galaxy/infoDanao/searchProject";
	var jsonObj={
			
	}
	//sendPostRequestByJsonObj
	sendGetRequest(_url, jsonObj, function(data){
		debugger;
	})
	
	
	
})


</script>
