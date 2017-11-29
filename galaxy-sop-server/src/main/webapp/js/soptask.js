/**
 * sop用户任务
 */
$(function(){
	   //更多操作点击显示
	   $('.more-task').mouseenter(function(){
		   $('.task-toggle').slideDown();
	   });
	   $('.task-toggle').mouseleave(function(){
		    $('.task-toggle').slideUp();
	   });
	   
	//待认领
	$("table").on("click", "a[data-btn='claim']", function() {
		var obj=this;
		var taskId=obj.childNodes[1].value;;
		var projectid=obj.childNodes[2].value;;
	    var  _url=Constants.sopEndpointURL+"/galaxy/soptask/goClaimtcPage?id="+taskId+"&sid="+sessionId+"&guid="+userId;
	  // 	var _url = forwardWithHeader(claimUrl);
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
			//	var taskid=getTaskId();
				$(".btnbox").on("click", "#dotask", function() {	
				//	var endUrl=Constants.sopEndpointURL+"/galaxy/soptask/doTask?taskId="+taskId+"&sid="+sessionId+"&guid="+userId;
					var endUrl=Constants.sopEndpointURL+"/galaxy/soptask/doTask?taskId="+taskId;
					forwardWithHeader(endUrl);
	            });
				//单击按钮刷新页列表里面的内容
				$(".btnbox").on("click", "#notdo", function() {
					$("#task-table").bootstrapTable("refresh");
				 });
				$(".pop").on("click", "[data-close='close']", function(){
					$("#task-table").bootstrapTable("refresh");
				 });
			}//模版反回成功执行	
		});
		return false;
	});
	//待认领
	$("table").on("click", "#doclaim", function() {
		var task=this;
		var taskId=task.childNodes[1].value;
		var url=Constants.sopEndpointURL+"/galaxy/soptask/doTask?taskId="+taskId;
	    forwardWithHeader(url);
	//	this.href=endUrl;
	});
	//指派任务点击跳转
	$(".more-task").on("click",function() {
		var url=Constants.sopEndpointURL+"/galaxy/soptask/detail";
	    forwardWithHeader(url);
	});
	
	/*指派任务弹窗点击事件*/
	$('.task-toggle li').click(function(){
		var index = $(this).index();
		var code = $(this).attr("data-code");
		console.log(code)
		if(index == 0){
			$.getHtml({
				url:getDetailUrl(code)
			});
			$('.close').addClass('tast-close')//添加关闭按钮
		}else if(index == 1){
			$.getHtml({
				url:getDetailUrl(code)
			});
			$('.close').addClass('tast-close')
		}
		$('.pop').addClass('task-pop');//去掉圆角
	});
	
	//页面请求地址
function getDetailUrl(code)
{
	if(code =='transfer-task')
	{	
		return '../html/task_todeal.html';
	}else if(code === 'abandon-task'){
		return '../html/task_toabandon.html';
	}
	return "";
}
	var _talbe = $("#task-table");
	var tableDefaultOpts = {
			queryParamsType: 'size|page', // undefined
			pageSize:10,
			showRefresh : false ,
			sidePagination: 'server',
			method : 'post',
			sortOrder : 'desc',
			sortName : 'created_time',
			pagination: true,
		    search: false,
		    onLoadSuccess: function (data) {
		    }
		};
	$("#task-table").bootstrapTable(tableDefaultOpts);
	$(".tipslink").on("click","a",function(){
		var a = $(this);
		if(a.parent().hasClass('on'))
		{
			return;
		}
		a.parent().addClass('on').siblings().removeClass('on');
		var tipslink = $("#tipslink_val");
		var url = a.attr("data-query-url");
		var id = a.attr('id');
		var opts = {url:url,pageNumber : 1};
		var options = _talbe.bootstrapTable('getOptions');
		if(id == 'dep-unfinished')
		{
			opts.checkbox = true;
			
			var originalCols = options.columns[0];
			var columns = new Array();
			columns.push({checkbox:true});
			columns = columns.concat(originalCols.slice(0,5));
			columns.push({field:'assignUidName',title:'认领人'});
			columns = columns.concat(originalCols.slice(5));
			opts.columns = columns;
		}
		else
		{
			opts.columns = new Array();
		}
		opts = $.extend({},tableDefaultOpts,opts)
		_talbe.bootstrapTable('refreshOptions',opts);
	});
	
		
});


