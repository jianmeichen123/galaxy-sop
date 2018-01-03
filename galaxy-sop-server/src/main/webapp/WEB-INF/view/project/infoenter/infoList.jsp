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
<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include> 
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

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
	</div>
	

</body> 
<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>
<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../../common/uploadwin.jsp" flush="true"></jsp:include>
	  
</html>
<script>
$(function(){
	//导航
	function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}
	createMenus(5); 
	var _url = Constants.sopEndpointURL +"/galaxy/infoDanao/searchProject"
	var projectName=getURLParameter("projectName"); 

	//分页
    initTable(); 
    function initTable() {
        $('#dataTable').bootstrapTable({
            method: 'post',
            toolbar: '#toolbar',    //工具按钮用哪个容器
            striped: false,      //是否显示行间隔色
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,     //是否显示分页（*）
            sortable: true,      //是否启用排序
            sortOrder: "asc",     //排序方式
            pageNumber: 1,      //初始化加载第一页，默认第一页
            pageSize: 10,      //每页的记录行数（*）
            pageList: [10, 50, 100, 150],  //可供选择的每页的行数（*）
            url: _url,//这个接口需要处理bootstrap table传递的固定参数
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            // 设置为 '' 在这种情况下传给服务器的参数为：pageSize,pageNumber 
            queryParams: function (params) {
            	var params={
           			"keyword":projectName,
       				"pageNo":params.pageNumber,                 
       				"pageSize":params.pageSize,
       				"order":params.sortOrder,
       				"orderBy":"projTitle",
            		}
				return params;
			} ,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            //showColumns: true, //是否显示所有的列
            //showRefresh: true, //是否显示刷新按钮
            minimumCountColumns: 2,    //最少允许的列数
            clickToSelect:false,  //是否启用点击选中行
            searchOnEnterKey: true,
            search:false,
            columns: [
             {
                title: "项目信息",
                field: "Name",
                align: "left",
                valign: "middle",
                formatter: function (value, row, index) {
                    //通过formatter可以自定义列显示的内容
                    //value：当前field的值，即id
                    //row：当前行的数据
                    var a = '<img class="comImg" src='+filter(row.projImage)+' alt="">'
							+'<div class="conInfo">'
							+'<p>'+row.projTitle+'</p>'
							+'<p class="textBotm">'
							+'<span class="industryName">'+filter(row.industryName)+'</span>'
							+'<span class="districtSubName">'+filter(row.districtSubName)+'</span>'
							+'</p>'
							+'</div>';
                    return a;
                }
            },
            {
                title: "工商信息",
                field: "busInfor",
                align: "left",
                valign: "middle",
                formatter: function (value, row, index) {
                    //通过formatter可以自定义列显示的内容
                    //value：当前field的值，即id
                    //row：当前行的数据
                    var a ='<div class="conInfo">'
							+'<p>'+filter(row.projCompanyName)+'</p>'
							+'<p class="textBotm">'
								+'<span>成立时间：'+filter(row.setupDT)+'</span>' 
							+'</p>'
						+'</div>'
                    return a;
                }

            },
            {
                title: "操作",
                field: "ProductName",
                align: "left",
                valign: "middle",
                formatter: function (value, row, index) {
                    //通过formatter可以自定义列显示的内容
                    //value：当前field的值，即id
                    //row：当前行的数据
                    var a ='<button type="button" class="enterIn blueBtn" val='+row.projCode+'>引用</button>'
                    return a;
                }
            }, 

            ],
            pagination: true
        });
}
function filter(str){
	return str==undefined?"--":str
}
	
	
})	
	
	
	
	
	
	
	
	
	
	
	
</script>
