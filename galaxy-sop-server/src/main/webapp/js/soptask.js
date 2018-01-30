/**
 * sop用户任务
 */
$(function(){
	//待认领
	$("table").on("click", "a[data-btn='claim']", function() {
		addTaskCookie();
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
		addTaskCookie();
		var task=this;
		var taskId=task.childNodes[1].value;
		var url=Constants.sopEndpointURL+"/galaxy/soptask/doTask?taskId="+taskId;
	    forwardWithHeader(url);
	//	this.href=endUrl;
	});
	//指派任务点击跳转
	$('#custom-toolbar').on('click','[data-code="assign-task"], [data-code="transfer-task"],[data-code="abandon-task"]',function(){
		var len = $('.highlighCheckbox.highlighCheckbox_checked').length;
		if(len == 0){
			layer.msg('请至少选择一条待办任务')
			return
		}
		var code = $(this).attr('data-code');
		$.getHtml({
			url:getDetailUrl(code),
			okback:function(){
				$('.task-qulification').hide();
			}
		});
		$('.close').addClass('tast-close')//添加关闭按钮
		$('.pop').addClass('task-pop');//去掉圆角
		
	});
	
	//页面请求地址
	function getDetailUrl(code)
	{
		if(code =='transfer-task')
		{	
			return platformUrl.transferTask;
		}
		else if(code === 'abandon-task')
		{
			return platformUrl.giveupTask;
		}
		else if(code === 'assign-task')
		{
			return platformUrl.assignTask;
		}
		return "";
	}
	//根据cookie信息回显tab
	var originAcitveTab = getCookieValue('task-active-tab');
	if(originAcitveTab.length > 0)
	{
		$(".tipslink #"+originAcitveTab).parent().addClass('on').siblings().removeClass('on');
	}
	
	$("#task-table").bootstrapTable(tableDefaultOpts);
	searchTask();
	$(".tipslink").on("click","a",function(){
		var a = $(this);
		if(a.parent().hasClass('on'))
		{
			return;
		}
		a.parent().addClass('on').siblings().removeClass('on');
		searchTask();
	});
});
//表格默认参数
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
    	toggleOperatBtns();
    	$('.ul_content').mouseenter(function(){
    		$(this).children('.task-toggle').stop(true).slideDown();
    	}).mouseleave(function(){
    		$(this).children('.task-toggle').stop(true).slideUp();
    	});
    	
    	checkboxClick();
    	judgeCheckbox();
      	  
    }
};

 /* checkbox 点击 */
function checkboxClick(){
	$('.highlighCheckbox_th ').removeClass('highlighCheckbox_checked')
	 $('.highlighCheckbox').click(function(event){
		 if($(this).hasClass("highlighCheckbox_checked")){
    		$(this).prop('checked',false);
   		$(this).removeClass('highlighCheckbox_checked'); 
		  }else{
    		$(this).prop('checked',true);
   		$(this).addClass('highlighCheckbox_checked'); 
		  }
		var table = $(this).closest("table");
		var Tbody = table.find("tbody");
		var length=Tbody.find("input[type=checkbox]").length;
		var checkLength =Tbody.find(".highlighCheckbox_checked").length;  
		if(length==checkLength){ 
			table.find(".highlighCheckbox_th input").prop('checked',true);
		 	$('.highlighCheckbox_th').addClass("highlighCheckbox_checked")
		} else{ 
			table.find(".highlighCheckbox_th input").prop('checked',false);
   		 $('.highlighCheckbox_th').removeClass("highlighCheckbox_checked")
		} 
			getSelectedIds(this)
			event.preventDefault(); 
		 
	 });
	 //全选 
	  $('.highlighCheckbox_th').unbind('click').bind('click',function(event){
		$(this).toggleClass('highlighCheckbox_checked'); 
		  if($(this).hasClass("highlighCheckbox_checked")){
    		$(this).find("input").prop('checked',true);
    		$(this).closest("table").find('input').prop('checked', $(this).find("input").prop('checked'));
    		$(this).closest("table").find(".highlighCheckbox").addClass('highlighCheckbox_checked');
		  }else{
    		$(this).find("input").prop('checked',false);
    		$(this).closest("table").find('input').prop('checked', $(this).find("input").prop('checked'));
    		$(this).closest("table").find(".highlighCheckbox").removeClass('highlighCheckbox_checked');
		  }
		  getSelectedIds(this)
		  event.preventDefault(); 
	  }) 
	
}
function judgeCheckbox(){
	//th
  	var a = $('.tipslink li.on a');
  	var id = a.attr('id');
  	if(id == 'finish')
	{
		
		$('.task-ritmin table thead tr th div label').hide()
		
	}
	else if(id == 'claim')
	{
				if(hasAssignPremission == "true"){
					$('.task-ritmin table thead tr th div label').show()
				}else{
					$('.task-ritmin table thead tr th div label').hide()
				}
	}
	else
	{
		$('.task-ritmin table thead tr th div label').show()
	}
}

function searchTask()
{
	var a = $('.tipslink li.on a');
	var tipslink = $("#tipslink_val");
	var url = a.attr("data-query-url");
	var id = a.attr('id');
	var opts = {url:url,pageNumber : 1};
	var options = $("#task-table").bootstrapTable('getOptions');
	//设置checkbox
/*	if(id == 'finish')
	{
		//opts.checkboxHeader = false;
		console.log($('.task-ritmin table thead tr th div label'))
		$('.task-ritmin table thead tr th div label').hide()
		
	}
	else if(id == 'claim')
	{
		opts.checkboxHeader = hasAssignPremission == "true";
	}
	else
	{
		opts.checkboxHeader = true;
	}*/
	if(id == 'dep-unfinished')
	{
		var originalCols = options.columns[0];
		var columns = new Array();
		columns = columns.concat(originalCols.slice(0,5));
		columns.push({field:'assignUidName',title:'认领人'});
		columns = columns.concat(originalCols.slice(5));
		opts.columns = columns;
	}
	else
	{
		opts.columns = new Array();
	}
	opts = $.extend({},tableDefaultOpts,opts);
	var pageNumber = getCookieValue('task-curr-page');
	var originalQ = getCookieValue('task-curr-q');
	if(originalQ.length>0)
	{
		$('#custom-toolbar input[name="keyword"]').val(originalQ);
	}
	if(pageNumber>0)
	{
		opts = $.extend({},opts,{pageNumber:parseInt(pageNumber)});
		clearTaskCookie();
	}
	$("#task-table").bootstrapTable('refreshOptions',opts);
}
/**
 * 记录task页面cookie
 * @returns
 */
function addTaskCookie()
{
	var activeTab = $('.tipslink li.on a').attr('id');
	var currPage = $("#task-table").bootstrapTable('getOptions').pageNumber;
	var currQ = $('#custom-toolbar input[name="keyword"]').val();
	setCookie('task-active-tab',activeTab,24,'/');
	setCookie('task-curr-page',currPage,24,'/');
	setCookie('task-curr-q',currQ,24,'/');
}
function clearTaskCookie()
{
	deleteCookie('task-active-tab','/');
	deleteCookie('task-curr-page','/');
	deleteCookie('task-curr-q','/');
}


