/**
 * sop用户任务
 */
$(function(){
	  $('.task-tips-li').on('click',function(){
	        $(this).addClass('on').siblings().removeClass('on');          
	      });
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
					$("#data-table").bootstrapTable("refresh");
				 });
				$(".pop").on("click", "[data-close='close']", function(){
					$("#data-table").bootstrapTable("refresh");
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
	//ul tab切换显示
	$('.to-task-tips').tabLazyChange({
		defaultnum:0,
		onchangeSuccess : function(index){
			switch(index){
				case 0 : initTabTaskMessage();break;
				case 1 :initTabTaskLog();break;
			}
		}
	})	
	//页面请求地址
	function initTabTaskMessage(){
		$.getTabHtml({
			url : platformUrl.toTaskMesage,
		});
	}
	function initTabTaskLog(){
		console.log(platformUrl.toTaskLog)
		$.getTabHtml({
			url : platformUrl.toTaskLog
		});
	}
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

	$(".tipslink").on("click","a",function(){
		var a = $(this);
		var tipslink = $("#tipslink_val");
		var query_by = a.attr("query-by");
		var query_val = a.attr("query-val");
		if(tipslink.attr("name")== query_by && tipslink.val()==query_val){
			return;
		}
		if(query_by == "all"){
			tipslink.removeAttr("name");
			tipslink.removeAttr("value");
			tipslink.attr("class","on");
		}else{
			tipslink.attr("name",query_by);
			tipslink.attr("value",query_val)
        }
		
		$("#data-table").bootstrapTable("querySearch");
	
	});
		
});


