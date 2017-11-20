

<style>
.fixed-table-container{padding-left:20px !important;padding-right:20px !important;border-radius:0px !important;}
.fixed-table-body{border-radius:0px !important;}
</style>

<body>

<div class="pagebox clearfix task-pagebox">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin taskDetail-ritmin">
 		 <div class='taskDetail-mesage'>
 		 
	        	<!--操作日志table  -->
	        	<table class='opretion-log' width='100%' cellspacing='0' cellpadding='0' border='0'>
	        		<thead>
	        			<tr>
	        				<th>时间</th>
	        				<th>操作者</th>
	        				<th>动作</th>
	        				<th>对象</th>
	        				<th>项目</th>
	        				<th>原因</th>
	        				<th>业务</th>
	        			</tr>
	        		</thead>
	        		<tbody>
	        			<tr>
	        				<td>2017-12-12</td>
	        				<td>王凯</td>
	        				<td>上传</td>
	        				<td>人事尽调报告</td>
	        				<td>村多多</td>
	        				<td>-</td>
	        				<td>尽职调查</td>
	        			</tr>
	        			<tr>
	        				<td>2017-12-12</td>
	        				<td>王凯</td>
	        				<td>上传</td>
	        				<td>人事尽调报告</td>
	        				<td>村多多</td>
	        				<td>-</td>
	        				<td>尽职调查</td>
	        			</tr>
	        			<tr>
	        				<td>2017-12-12</td>
	        				<td>王凯</td>
	        				<td>上传</td>
	        				<td>人事尽调报告</td>
	        				<td>村多多</td>
	        				<td>-</td>
	        				<td>尽职调查</td>
	        			</tr>
	        		</tbody>
	        	
	        	
	        	</table>
        	</div>
        
        </div>
	</div>
</div>

<script type="text/javascript">
//计算距离的左边距
detailHeaderWidth();
function detailHeaderWidth(){
	  var  w_lft=$(".lft").width();
	  	$('.task-top').css({'margin-left':w_lft});
	  	$('.task-top').css({'margin-left':w_lft});
}
$(window).resize(function(){
	detailHeaderWidth();
})	
function projectNameFormatter(value,row,index){
		var str=row.projectName;
		if(str.length>12){
			subStr = str.substring(0,12);
			var options = "<span title='"+str+"'>"+subStr+"</span>";
			return options;
		}
		else{
			var options = "<span title='"+str+"'>"+str+"</span>";
			return options;
		}
	}
    var flag="${flagUrl}";
    var num=0;
    if(flag=="jl"){
    	num=10;
    }
    if(flag=="pz"){
    	num=11;
    }
    if(flag=="gq"){
    	num=12;
    }
    if(flag=="jz"){
    	num=9;
    }
	$(function(){
		var flag="${flagUrl}";
		var num=2;
		if(flag=="jz"){
			num=9;
		}
		if(flag=="jl"){
			num=10;		
				}
		if(flag=="gq"){
			num=12;
		}
		if(flag=="pz"){
			num=11;
		}
		createMenus(num);
		
	});
	var aa=$("a[resource-mark]")
	  if(isContainResourceByMark("interView")){
	    	$('div[resource-mark="interView"]').css("display","block");
	    }
	
</script>

