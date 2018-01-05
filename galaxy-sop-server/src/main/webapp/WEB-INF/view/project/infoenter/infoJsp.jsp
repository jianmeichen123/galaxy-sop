<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
 
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/>
<div class="ritmin bigPop">
	<div class="infoTop clearfix">
	<!--1是页面  0是弹窗  -->
			<div id="dataHidden">1</div>
		<h5>项目“<span id="projectName"></span>”创建成功</h5>
		<p>下一步，请完善项目信息</p>
		<p><i>为了减少人工录入，</i>系统从“创投大脑”匹配出部分项目信息推荐给您，<i>可选择有效的信息，快速添加到项目内</i></p>
		<ul class="scheduleIcon clearfix">
			<li data-content ="1" class="active"><p>引用推荐项目</p></li>
			<li data-content="2"> <p>选择有效推荐信息</p></li>
			<li data-content="3"> <p>完善项目剩余信息</p></li>
		</ul>
		<a href="javascript:;" onclick="jumpPage()" class="rightLink">暂不引用推荐，开始完善项目></a>
	</div> 
	<div class="tableBox">
		<table class="infoList" id="dataTable" > 
			<thead class="width">
				<tr class="listTitle listThree">
					<th data-field="projTitle" class="threeLi threeLiFirst">项目信息</th>
					<th data-field="setupDT" class="threeLi threeLiSecond">工商信息</th>
					<th class="threeLi">操作</th>
				</tr>
			</thead>
			<tbody class="listCon" id="dataBody"> </tbody>
		</table>
		
 	</div>
</div>  

<script>  
</script>