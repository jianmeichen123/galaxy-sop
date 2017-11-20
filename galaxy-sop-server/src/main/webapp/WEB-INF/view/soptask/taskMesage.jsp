

<div class="pagebox clearfix task-pagebox">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin taskDetail-ritmin">
 		 <div class='taskDetail-mesage'>
 		 	<div class='taskDetail-mesage-top'>
	        	<div class='task-item task-item-left'>
	        		<ul>
	        			<li>项目名称：<span>创投</span></li>
	        			<li>创建时间：<span>2016-12-22</span></li>
	        			<li>合伙人：<span>李凯</span></li>
	        		</ul>
	        	</div>
	        	<div class='task-item'>
	        		<li>项目类型：<span>投资</span></li>
	       			<li>投资事业线：<span>人工智能</span></li>
	       			<li>公司名称：<span>星河互联集团</span></li>
	        	</div>
	        	<div class='task-item task-item-right'>
	        		<li>项目编码：<span>27000021</span></li>
	       			<li>投资经理：<span>人工智能-投资经理</span></li>
	       			<span class='pro-detail'>项目详细信息 ></span>
	        	</div>
        	</div>
        	<div class='taskDetail-mesage-table'>
	        	<table width='100%' class='task-detail-table' border='0' cellspacing='0' cellpadding='0'>
	        		<thead>
	        			<tr>
	        				<th>上传日期</th>
	        				<th>存储类型</th>
	        				<th>更新日期</th>
	        				<th>档案状态</th>
	        				<th>操作</th>
	        			</tr>
	        		</thead>
	        		<tbody>
	        			<tr>
	        				<td>2017-04-09</td>
	        				<td>图片</td>
	        				<td>2017-08-04</td>
	        				<td>已上传</td>
	        				<td class='task-operation'>
	        					<span>查看</span>
	        					<span>查看历史</span>
	        				</td>
	        				
	        			</tr>
	        		</tbody>
	        	
	        	</table>
        	</div>
        	<div class='taskDetail-mesage-update'>
        		<span class="upate-task">更新尽调报告</span>
        		<span class='upate-task submit-success'>提交完成</span>
        	
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

