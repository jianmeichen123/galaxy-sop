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
				<h5>项目“滴滴”创建成功</h5>
				<p>下一步，请完善项目信息</p>
				<p><i>为了减少人工录入，</i>系统从“创投大脑”匹配出部分项目信息推荐给您，<i>可选择有效的信息，快速添加到项目内</i></p>
				<ul class="scheduleIcon clearfix">
					<li data-content ="1" class="active"><p>引用推荐项目</p></li>
					<li data-content="2"> <p>选择有效推荐信息</p></li>
					<li data-content="3"> <p>完善项目剩余信息</p></li>
				</ul>
				<a href="" class="rightLink">暂不引用推荐，开始完善项目></a>
			</div> 
			<div class="tableBox">
				<table class="infoList" id="infoList"> 
					<thead class="width">
						<tr  class="listTitle listThree">
							<th class="threeLi">项目信息</th>
							<th class="threeLi">工商信息</th>
							<th class="threeLi"s>操作</th>
						</tr>
					</thead>
					<tbody class="listCon "> 
						
						<tr class="listThree ">
							<td class="threeLi ">
								<img class="comImg" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514365971892&di=208cdf2cd2ba3a996b3f29d5374a06c7&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F5366d0160924ab183fe9fafd3cfae6cd7a890b78.jpg" alt="">
								<div class="conInfo">
									<p>华信区块链研究院</p>
									<p class="textBotm">
										<span>汽车交通</span>
										<span>江苏</span>
									</p>
								</div>
							</td>
							<td class="threeLi">
								<div class="conInfo">
									<p>华信区块链研究院</p>
									<p class="textBotm">
										<span>成立时间：2011-11</span> 
									</p>
								</div>
							</td>
							<td class="threeLi">
								<button type="button" class="enterIn blueBtn">引用</button>
							</td>
						</tr>
							<tr class="listThree">
							<td class="threeLi">
								<img class="comImg" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514365971892&di=208cdf2cd2ba3a996b3f29d5374a06c7&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F5366d0160924ab183fe9fafd3cfae6cd7a890b78.jpg" alt="">
								<div class="conInfo">
									<p>华信区块链研究院</p>
									<p class="textBotm">
										<span>汽车交通</span>
										<span>江苏</span>
									</p>
								</div>
							</td>
							<td class="threeLi">
								<div class="conInfo">
									<p>华信区块链研究院</p>
									<p class="textBotm">
										<span>成立时间：2011-11</span> 
									</p>
								</div>
							</td>
							<td class="threeLi">
								<button type="button" class="enterIn blueBtn">引用</button>
							</td>
						</tr> 
					</tbody>
				</table>
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
	// 分页
		$("#infoList").bootstrapTable({
			url: url,
			columns: columns,
			queryParamsType: 'size|page', 
			pageSize:10,
			showRefresh : false ,
			sidePagination: 'server',
			method : 'post',
			sortOrder : 'desc',
			sortName : 'created_time',
			pagination: true,
		    search: false,
		    queryParams: function(params){
		    	params.page = params.pageNum;
		    	params.size = params.pageSize;
		    	return params;
		    }
		});
	
	
	
	
})


</script>
