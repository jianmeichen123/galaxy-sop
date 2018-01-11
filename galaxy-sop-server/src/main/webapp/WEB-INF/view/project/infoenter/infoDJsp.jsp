<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<style>
input+ label,table input  {
width:12px;
height:12px;
background: #FFFFFF;
border: 1px solid #B2B2B2;
display: block;
position:absolute;
z-index:10;
  top:50%;
  left:50%;
  transform:translate(-50%,-50%);
}


table input {
opacity: 0; 
z-index:11;
}
input:checked + label {
width:12px;
height:12px;
background:#55A7FF ;
  text-align:center;
  border-color:#55A7FF ;    
  overflow: hidden;
    line-height: 12px;
}
input:checked + label::before {
   content:"✔";
   width:12px;
   color:#fff; 
}
</style>
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/>
<div class="ritmin bigPop">
	<div class="infoTop clearfix">
		<h5 style="background:none;">已选择项目“<span id="projectName"></span>”进行引用</h5>
		<p><i>选择有效的信息，快速添加到项目内</i></p>
		<ul class="scheduleIcon clearfix">
			<li data-content ="1" class="active"><p>引用推荐项目</p></li>
			<li data-content="2" class="active"> <p>选择有效推荐信息</p></li>
			<li data-content="3"> <p>完善项目剩余信息</p></li>
		</ul>
		<a href="javascript:history.go(-1)" class="backLink">重新选择项目引用></a>
	</div> 
	<div class="tableBox infoBox ">
		 <ul class="infoConList">
		 	<li class="infoItem" id="companyInfo">
		 		<p class="titles">法人信息</p>
		 		<table class="tabrow" code="company-info"> 
		 			<tbody>
		 				<tr>
		 					<td>
		 						<input type="checkbox" class="checkAll" onclick="checkAll(this)"/>
		 						<label></label>
		 					</td>
		 					<td>公司名称</td>
		 					<td id="DN_projectCompany" titleId="1814">--</td>
		 				</tr>
		 				<tr>
		 					<td>
		 						<input type="checkbox" /><label></label>
		 					</td>
		 					<td>成立日期</td>
		 					<td id="DN_formationDate" titleId="1816">--</td>
		 				</tr>
		 				<tr>
		 					<td>
		 						<input type="checkbox" /><label></label>
		 					</td>
		 					<td>法人</td>
		 					<td id="DN_companyLegal" titleId="1815" >--</td>
		 				</tr>
		 			</tbody>
		 		</table>
		 	</li>
	 	 	<li class="infoItem" id="equityInfo">
		 		<p class="titles">股权结构</p>
		 		<table class="tabrow"  titleId="1906" code="equity-structure"> 
		 			<thead>
		 				<tr>
		 					<td>
		 						<input type="checkbox" class="checkAll" onclick="checkAll(this)"/>
		 						<label></label>
		 					</td> 
		 					<td>股东名称</td>
		 					<td>股东类型</td>
		 					<td>股东性质</td>
		 					<td>占股比例</td>
		 					<td>备注</td>	
		 				</tr>
		 			</thead>
		 			<tbody> 
		 			</tbody>
		 		</table>
		 	</li>
		 	<li class="infoItem" id="teamInfo">
		 		<p class="titles">团队成员</p>
 				<table class="tabrow" titleId="1303" code="team-members" > 
		 			<thead>
		 				<tr>
		 					<td>
		 						<input type="checkbox" class="checkAll" onclick="checkAll(this)"/>		 						
		 						<label></label>
		 					</td> 
		 					<td>姓名</td>
		 					<td>职位</td> 	
		 				</tr>
		 			</thead>
		 			<tbody> 
		 			</tbody>
		 		</table>
		 	</li>
		 	<li class="infoItem" id="fina_historyInfo">
		 		<p class="titles">历史融资</p>
		 		<table class="tabrow" code="finance-history" titleId="1903"> 
		 			<thead>
		 				<tr>
		 					<td>
		 						<input type="checkbox" class="checkAll" onclick="checkAll(this)" />
		 						<label></label>
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
		 			</tbody>
		 		</table>
		 	</li>
		 </ul>
		 
	</div>
	<div class="tableBox emptyInfo" style="display:none;">
		<div class="empty">
			 暂无推荐信息
		</div>		
		<a href="javascript:;" class="add_pro" style="position:relative; top:300px" onclick="jumpPage()">跳过</a>
	</div>
</div> 
<div class="fixedbottom">
	<a href="javascript:;" class="add_pro" onclick="saveDN(this)" >保存</a>
	<a href="javascript:;" class="over_pro"  onclick="jumpPage()">跳过</a>
</div> 
<div class="jumpBox">
	<div class="center jumpCon">
		<p class="p1">完善项目剩余信息，即将跳转至项目详情页面</p> 

		<ul class="scheduleIcon clearfix">
			<li data-content="1" class="active"><p>引用推荐项目</p></li>
			<li data-content="2" class="active"> <p>选择有效推荐信息</p></li>
			<li data-content="3" class="active"> <p>完善项目剩余信息</p></li>
		</ul>
	  	<img src="<%=path %>/img/junpPage.png" alt=""> 
		<p class="p2">
			<span id="time">3</span>秒后自动跳转
		</p>
		<div  class="over_pro jumpBtn"  onclick="jumpPage()">立即前往</div>
    </div>
</div>
<script>
function checkAll(event){   
    $(event).closest("table").find('input').prop('checked', $(event).prop('checked')); 
}
function checkSelf(event){
	var table = $(event).closest("table");
	var Tbody = table.find("tbody");
	var length=Tbody.find("input[type=checkbox]").length;
	var checkLength =Tbody.find("input[type=checkbox]:checked").length;
	if(length==checkLength){ 
		table.find(".checkAll").prop('checked',true);
	} else{ 
		table.find(".checkAll").prop('checked',false);
	}
}

</script>