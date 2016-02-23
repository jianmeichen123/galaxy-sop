<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>测试</title>
</head>
<script src="<%=request.getContextPath() %>/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<!-- <script type="text/javascript" src="http://code.hs-cn.com/jquery/jquery-1.7.1.min.js"></script> -->
<body>
<%String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<table>
	<tr>
		<td>测试方法:</td>
		<td>
			<select id="changeUrlSelect" onchange="javascript:changeUrl();">
				<option value="0">请选择</option>
				<option value="100">test信息</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>测试地址:</td>
		<td><input id="url" size="100" value=""/></td>
	</tr>
	<tr>
		<td>测试地址全路径:</td>
		<td><input id="allUrl" size="100" value=""/></td>
	</tr>
	<tr>
		<td>操作人ID:</td>
		<td><input id="userId" size="10" value=""/></td>
	</tr>
</table>
<br/>
<br/>
<br/>
<div id="showResultDiv" >
	<div style="float: left">
		<div style="margin-top: 100px;float: left">输入参数：</div>
		<textarea rows="20" cols="40" id="intextarea" onchange="changein();"></textarea>
	</div>
	<div style="float: right;margin-right: 300px">
		<div style="margin-top: 100px;float: left;">输出参数：</div>
		<%-- <iframe id="showResult" style="margin-right: 40px;float: right" width="400" height="330" frameborder=1 scrolling=auto src="<%=request.getContextPath() %>/mobile/result"></iframe> --%>
	</div>
	<input type="button" style="margin-top: 200px;margin-left: 50px;" onclick="getMethodReturnJsonValue();" value="提交">
</div>

</body>
<script type="text/javascript">
var loginInfo = "";
var returnDeftMsg = "";
function changeUrl() {
	var selectValue = $("#changeUrlSelect option:selected").val();
	var url = "";
	switch (selectValue) {

	case '100':
		url = "/aa/bb/test.json";
		loginInfo = '{"id":"222"}';
		$("#intextarea").val(loginInfo);
		$("#userId").val(12846);
		returnDeftMsg = '{}';
		break;
	}
	
	$("#url").val(url);
	$("#allUrl").val('<%=basePath %>'+url);
	$("#showResult").val("");
}

function getMethodReturnJsonValue() {
	
	var selectValue = $("#changeUrlSelect option:selected").val();
	if(selectValue=='0'){
		alert("请选择方法!");
		return;
	}
	if(returnDeftMsg==""){
	  $("#showResult").attr("src",returnDeftMsg);
	}else{
		var url = $("#allUrl").val();
		var json = $("#intextarea").val();
		var user = 14356;
		var userId = $("#userId").val();
		if(userId!=''){
			user = userId;
		}
		$.ajax({
			url : url,
			data : json,
			async : false,
			type : 'POST',
			dataType : "text",
			cache : false,
			error:function(){     
		        alert('error');     
		    }, 
			success : function(data) {
				$("#showResult").contents().find("#resultDiv").html(data);
			}
		});
	}
}

function changein(){
	 $("#showResult").val("");
}
function getParam(code){
	switch(code) {
	case '100':
		 return '{}'
	}
}
</script>
</html>

