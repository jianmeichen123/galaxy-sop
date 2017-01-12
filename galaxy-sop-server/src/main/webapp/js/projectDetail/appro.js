/**
 * 
 */

	

var tabAppro = {
		init : function(pid){	
			if(!pid)
				pid == 0;
			$.getTabHtml({
				url : Constants.sopEndpointURL+"/galaxy/project/toAppropriation/null/"+pid,//模版请求地址
				data:"",//传递参数
				okback:function(){
					pId="${pid}";
					  $("#tabApprAllList").children('div').remove(); 
					   searchPartMoney ="${searchPartMoney}";
					  	if(searchPartMoney == "null" || "" == searchPartMoney){
					  		searchPartMoney = null;
					  		$("#searchPartMoney").val("");
					  	}else{
					  		$("#searchPartMoney").val(searchPartMoney);
					  	}  	
					  	reloadData(null,pid);
					  	showRow(0);
				}//模版反回成功执行	
			});
		}
}
/***
 *显示数据
 *param nums 显示记录数
 ***/
 function showRow(nums)
 {
	  var total = $("#tabApprAllList .agreement").length;
	  if(typeof(nums) == 'undefined')
	  {
		  nums = 2;
	  }
	  num = Math.min(nums,total);
     if(num<=0)
     {
     	$(".proOperation .show_total").css("display","none");
     	return;
     }
     //显示数据
	  $("#tabApprAllList .agreement:lt("+num+")").css("display","block");
     //统计信息
	  var numOfShow = $("#tabApprAllList .agreement:visible").length;
	  var numOfHide = $("#tabApprAllList .agreement:hidden").length;
	  $(".show_total .end").text(numOfShow);//左侧显示条数
	  $(".show_total .total").text(total);//左侧显示条数
	  //是否全部显示
	  if(numOfShow >= total)
	  {
		 $(".proOperation .show_more").css("display","none");
	  }
	  else
	  {
		 $(".proOperation .show_more").css("display","block");
	  }
	 $(".proOperation .show_more")
	 .unbind('click')
	 .click(function(event) {
		showRow(numOfShow+2);
	 });
	  
	  
 }
  
  function queryBack1(data){
	  var result = data.result.status;
		if(result == "ERROR"){ //OK, ERROR
			layer.msg(data.result.message);
			return;
		}else{
			 var grantTotal = data.entity;
			 if(null!=grantTotal.id&&typeof(grantTotal.id)=="underfined"){
				 $("#totallId").val(0);
			 }else{
				 $("#totallId").val(grantTotal.id);
			 }
			 if(grantTotal.is_edit==false){
				 $("#grantMoney").attr("readonly","readonly");
				 $("#grantMoney").css("background-color","#f8f8f8");
			 }
			 if(null!=grantTotal){
				 $("#info dd input")
					.each(function(){
						var self = $(this);
						if(self.attr('id') != 'undefined')
						{
						   var id = self.attr('id');
						   var text = grantTotal[id];
						   self.val(text);
						}
					});
			 }
		}
 }

  $("#search").click( function(){
	    var searchPartMoney=$("#searchPartMoney").val();
		if(null==searchPartMoney||""==searchPartMoney){
			searchPartMoney = null;
		}
		showTabs(null+"/"+'270',8);
		showRow(2);
	})


 //获取 页面数据\保存数据
function paramsContion(){
	 
	if(!beforeSubmitById("actual_aging_container")){
		return false;
	}
	var partMoney = $("#grantMoney").val();
	var grantDetail = $("#grantDetail").val();
	var check =/^[\s]*$/;
	if(check.test(grantDetail)){
		layer.msg("注资时间输入错误!");
		return false;
	}
	var remainMoney = $("#remainMoney").val();
	var grantMoneyOld=$("#oldRemainMoney").val();
	var newgrant = (Number(grantMoneyOld)+Number(remainMoney)).toFixed(4);
	
	var inputValueMoney = Number(partMoney).toFixed(4);
	if(parseFloat(inputValueMoney) > parseFloat(newgrant)){
		layer.msg("分期注资金额之和大于总注资金额");
		return false;
	}
	var condition = JSON.parse($("#actual_aging_form").serializeObject());
	condition.fileReidsKey = Date.parse(new Date());
	condition.fileNum = $("#filelist").find("tr").length - 1;
	var oldFids=[];
	var oldfileids = $("input[name='oldfileids']");
	if(oldfileids && oldfileids.length > 0){
		
		$.each(oldfileids, function(i) { 
			var idVal = oldfileids[i].value;
		   	if(!isNaN(idVal)){
		   		oldFids.push(idVal);
		   	}
		});
		condition.fileIds = oldFids;
	}
	return condition;
}
function toInitBachUpload(){
	toBachUpload(Constants.sopEndpointURL+'galaxy/sopFile/sendUploadByRedis',
			Constants.sopEndpointURL+'/galaxy/grant/part/addGrantPart',"textarea2","select_btn","win_ok_btn","actual_aging_container","filelist",
					paramsContion,"actual_aging_form",saveCallBackFuc);
}

/**
* 回调函数
*/
function saveCallBackFuc(data){
	//编辑之后刷新，显示相同记录数 - fix bug 953
	var url = searchPartMoney+'/'+'${pid}';
	var numOfShow = $("#tabApprAllList .agreement:visible").length;
	if(numOfShow>0)
	{
		numOfShow = Math.max(numOfShow,2);
		url+="?numOfShow="+numOfShow;
	}
	showTabs(url,8);
}
function to_del_grantPart(selectRowId){
	
	layer.confirm('是否删除分期注资计划?',
			
			
		{
		  btn: ['确定', '取消'] ,
		  title :'提示',
		}, 
		
		function(index, layero){
			del_grantPart(selectRowId);
		}, 
		function(index){
		}
	);
}

function to_download_grantPart(id){
	try {
		var url = Constants.sopEndpointURL + '/galaxy/grant/part/downloadBatchFile'+"/"+id;
		layer.msg('正在下载，请稍后...',{time:2000});
		window.location.href=url+"?sid="+sessionId+"&guid="+userId;
	} catch (e) {
		layer.msg("下载失败");
	}
}

function del_grantPart(id){  
	var _url =  Constants.sopEndpointURL + '/galaxy/grant/part/delGrantPart/'+id;
	sendPostRequestByJsonObj(_url, {}, function(data){
		if (data.result.status=="OK") {
			layer.msg("删除成功");
			removePop1();
			showTabs('null/'+'${pid}',8);
		} else {
			layer.msg(data.result.message);
		}
	});
}
function reloadData(searchPartMoney,pid){
	var data = {};
	  data.searchPartMoney=searchPartMoney;
	  data.projectId=pid;
	  sendPostRequestByJsonStr(platformUrl.queryGrantTotalList,JSON.stringify(data),queryBack);

}
function queryBack(data){
	$("#tabApprAllList").children('div').remove(); 
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		layer.msg(data.result.message);
		return;
	}else{ 
	    var entityList = data.pageList;
	    //暂无注资计划
	    if(entityList.total==0){
	    	var noContent='<div class="no_con" style="display: block;">没有找到匹配的记录</div>';
	    	$("#tabApprAllList").append(noContent);
	    }
		if(typeof(entityList)!="underfined"&&entityList!=null){
			var content=entityList.content;			
			if(content.length>0){
				for(var i=0;i<content.length;i++){
					var grantTotal=content[i];
					var _this=$("#tabApprAllList");
					var kk=assembleHtml(grantTotal,i);
					$("#tabApprAllList").append(kk);
					var partList=grantTotal.partList;
					if(null!=partList&&partList.length>0){
						for(var k=0;k<partList.length;k++){
							  var grantPart=partList[k];
							  var o=_this;
							  $("#tabApprSingleList_"+i+"").append(assembleSingleTabHtml(grantPart,grantTotal.grantName,i,k));
							}
						}else{
							var noData =
								'<tr>'+
								 '<td colspan="6" class="no_info no_info01" style="text-align:center;"><p class="no_info_icon">没有找到匹配的记录</p></td>'+
								' </tr>'; 			
							 $("#tabApprSingleList_"+i+"").append(noData)
						}
					}
					if(isEditable != 'true')
					{
					 	$("#tabApprAllList .b_agreement_r").hide();
					 	$("#tabApprAllList .edit-btn, #tabApprAllList .del-btn").hide();
					}
			}
		}
	}
}