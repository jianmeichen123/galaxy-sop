<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<style type="text/css">

</style></head>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<body>

	<!--左侧导航-->

			<div class="tabtable assessment label_static">
            	<div class="member proOperation">
                    <div class="top clearfix">
                        <!--按钮-->
                      
                        <c:if test="${isEditable && isExistFlag}">
                        <div class="btnbox_f btnbox_f1">
                            <span class="pbtn bluebtn h_bluebtn" href="/sop/html/actual_all.html" data-btn="actual_all" data-on="save" data-name='添加总注资计划'>添加总注资计划</span>
                        </div>
                        </c:if>
                    </div>
                    <!-- 搜索条件 -->
                   <!--  <div class="min_document fund_list clearfix">
                      <div class="bottom clearfix">
                        <dl class="fmdl fmdll clearfix">
                          <dt>计划注资金额：</dt>
                          <dd>
                            <input class=" txt " id="searchPartMoney" type="text" value="" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                          </dd>
                          <dd><a href="javascript:;" class="bluebtn ico cx" id="search" >搜索</a></dd>
                        </dl>
                      </div>
                    </div>   -->
                  <div id="tabApprAllList">
                   
                  </div>
              
        </div>  
                   <!--tab end-->
                 
          </div>
<script>
var key = Date.parse(new Date());
var keyJSON={};
var deleteJSON={};
var isEditable = "${isEditable}";
var isTransfering = "${fx:isTransfering(pid) }";
var pId;
var searchPartMoney;
  $(function(){
	pId="${pid}";
	  $("#tabApprAllList").children('div').remove(); 
	  searchPartMoney ="${searchPartMoney}";
	  	if(searchPartMoney == "null" || "" == searchPartMoney){
	  		searchPartMoney = null;
	  		$("#searchPartMoney").val("");
	  	}else{
	  		$("#searchPartMoney").val(searchPartMoney);
	  	}
	  	
	  	reloadData(searchPartMoney,pId);
	  //只有创建人显示编辑按钮
	  if(isEditable != 'true')
	  {
		  $("#tabApprAllList .b_agreement_r .rolehide").hide();
		  $("#tabApprAllList .edit-btn, #tabApprAllList .del-btn").hide();
	  }
		//添加，编辑总注资计划弹出页面
	$("[data-btn='actual_all']").on("click",function(){ 
			var $self = $(this);
		var _url=platformUrl.toApprAllAdd+"?pid=${pid}";
		var _name= $self.attr("data-name");
		var data_on=$self.attr("data-on");
		var id=$self.attr("data-val");
		if(data_on=="info"){
			_url=Constants.sopEndpointURL+'galaxy/grant/total/toActualTotalLook/'+id+"?pid=${pid}";
		}
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$("#popup_name").html(_name);
				if(data_on=="edit"){
					//sendPostRequest(platformUrl.getGrantTotal+"/"+id,queryBack1);
				}else{
					 $("#totallId").val(0);
				}
				//initDialogVal();
			}//模版反回成功执行	
		});
		return false;
	});
		
	    function showGrantPart(tid){
	    	
	    }
		//编辑总注资计划
		$("[data-btn='actual_aging']").on("click",function(){ 			
		var $self = $(this);
		var _data_type = $self.attr("data_type");
		
		var _id = $self.attr("data-id");
		var _url=  Constants.sopEndpointURL+'/galaxy/grant/part/toApprPartAging';
		var _name= $self.attr("data-name");
		var _total_name = $self.attr("data-total-name");
		//查看分期计划
		if(_data_type == "info"){
			_url = Constants.sopEndpointURL+'/galaxy/grant/part/toApprPartAgingInfo';
		}
		/* 
		var isFlag = false;
		
		if(_data_type == "edit"){
			var _is_url =  Constants.sopEndpointURL + '/galaxy/grant/part/isGrantPart/'+$self.attr("data-part-id");
			sendPostRequestByJsonObj(_is_url, {}, function(data){
				if (data.result.status=="ERROR") {
					isFlag = true;
				} 
			});
			
		} */
		//计算已有的计划金额
		 var trs=$(".approp_table tbody").find("tr");
		 var sum=0;
		 $.each(trs,function(){ 
			 sum+=Number($(this).find("td:nth-child(3)").text());
		 })
		$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					key = Date.parse(new Date());
					delete deleteJSON.partDelFile;
					$("#popup_name").html(_name);
					$("#totalName").html(_total_name);
					if($("#popup_name").text()=="添加分期注资计划"){
						$("#filelist").css("display","none");  //隐藏表头  
					}
					$("#projectId").val(pId);
					 getTotalAppr(projectInfo.id);
					//计算剩余金额
		                var totalMoneyInit=$("#totalMoneyPart").val();
		                $("#formatRemainMoney").text((Number(totalMoneyInit)-sum).toFixed(4)*10000/10000);
		                $(".moeny_all input").on("blur",function(){
		                	var val=$(this).val();
		                	var errorTips=$(this).siblings(".error");
		                	if(errorTips.is(":visible")){
		                		val=0;
		                		var formatRemainMoneyVal=((Number(totalMoneyInit)*10000-sum*10000-val*10000)/10000).toFixed(4);
		                		$("#formatRemainMoney").text(formatRemainMoneyVal*10000/10000);
		                	}else{
		                		if(Number(totalMoneyInit)-sum-val>0){
		                    		var formatRemainMoneyVal=((Number(totalMoneyInit)*10000-sum*10000-val*10000)/10000).toFixed(4);
		                    		$("#formatRemainMoney").text(formatRemainMoneyVal*10000/10000);
		                    	}else{
		                    		$("#formatRemainMoney").text(0);
		                    	}
		                	}
		                }) 
		                
					 keyJSON["b_part"]=key;
					 var params = {};
					 params.fileReidsKey = key;
					 params.projectId =  '${projectId}';
					 params.titleId = "3022";
					if(_data_type == "edit" || _data_type == "info"){
						var _part_id = $self.attr("data-part-id");
						//edit
						_url = Constants.sopEndpointURL + '/galaxy/grant/part/selectGrantPart/'+_part_id;
						sendGetRequest(_url, {}, function(data){
							var result = data.result.status;
							if(result == "OK"){
								var grantPartInfo = data.entity;
								console.log("添加添加")
								console.log(grantPartInfo)
								if(_data_type == "edit"){
									$("#actual_aging_container [data-name='id']").val(grantPartInfo.id);
									$("#actual_aging_container [data-name='field1']").val(grantPartInfo.field1);
									$("#actual_aging_container [data-name='field2']").val(grantPartInfo.field2);
									$("#actual_aging_container [data-name='field3']").val(grantPartInfo.field3);
									$("#actual_aging_container [data-name='field4']").val(grantPartInfo.field4);
								}else{
									$("#grantName").html(grantPartInfo.field1);
									$("#grantDetail").html(grantPartInfo.field2);
									$("#grantMoney").html(grantPartInfo.field3);
									$("#payCondition").html(grantPartInfo.field4);
								}
								/* 
								$("#actual_aging_container [name='id']").val(grantPartInfo.id);
								$("#actual_aging_container [name='totalGrantId']").val(grantPartInfo.totalGrantId);
								
								if(_data_type == "edit"){
									$("#actual_aging_container [name='grantDetail']").val(grantPartInfo.grantDetail);
									$("#actual_aging_container [name='grantMoney']").val(fixSizeDecimal(grantPartInfo.grantMoney,4));
									$("#actual_aging_container [name='oldRemainMoney']").val(fixSizeDecimal(grantPartInfo.grantMoney,4));
								}else{
									$("#grantDetail").html(grantPartInfo.grantDetail);
									$("#grantMoney").html(fixSizeDecimal(grantPartInfo.grantMoney,4));
									$("#oldRemainMoney").html(grantPartInfo.oldRemainMoney);
								}
								if(isFlag)
								{
									$("#actual_aging_container [name='grantMoney']").attr("type","hidden");
									$("#editMoney").css('display','block');
									$("#editMoney").html(grantPartInfo.grantMoney);
								} */
								$.each(data.entity.fileList,function(){
									var but = "<button type='button' id='"+this.id+"btn' onclick=delPart('"+this.id+"','"+this.fileName+"','textarea2','partDelFile')>删除</button>" ;
									var htm = "<tr id='"+this.id+"tr'>"+
													"<td>"+this.fileName+"."+this.fileSuffix+
														"<input type=\"hidden\" name=\"oldfileids\" value='"+this.id+"' />"+
													"</td>"+
													"<td>"+plupload.formatSize(this.fileLength)+"</td>";
										if(_data_type == "edit"){
											htm+=	"<td>"+ but +"</td><td>100%</td>";
										}			
										htm+= "</tr>";
									$("#filelist").append(htm);
								});
								/* toInitBachUpload(); */
								var fileLen=$("#filelist tr:gt(0)").length;
								if(fileLen==0){
									$("#filelist").css("display","none");
								}
								 toBachPartUpload(Constants.sopEndpointURL+'galaxy/informationFile/sendInformationByRedis',
											null,"textarea2","select_btn","win_ok_btn","actual_aging_container","filelist",
											params,"actual_aging_form",null,null);
								
							}else{
								layer.msg(data.result.message);
							}
						});
					}else{
						$("#partId").remove();
						 toBachPartUpload(Constants.sopEndpointURL+'galaxy/informationFile/sendInformationByRedis',
									null,"textarea2","select_btn","win_ok_btn","actual_aging_container","filelist",
									params,"actual_aging_form",null,null);
					
					
					
					}
					//initDialogVal();	
				}//模版反回成功执行	
			});
		
	    return false;
		});
		
		/**
		 * 获取总注资计划并校验
		 * @param projectId
		 * @returns {Boolean}
		 */
		function getTotalAppr(projectId){
			var flag = false;
			var params={};
			params.projectId = projectId;
			sendPostRequestByJsonObj(
						Constants.sopEndpointURL+'/galaxy/infoProject/getTotalAppr' , 
						params,
						function(data){
							if(data.result.status == "OK"){
								if(typeof(data.userData) == "object"){
									console.log("添加");
									console.log(data);
									if(data.userData.totalMoney || data.userData.remainMoney){
										flag = true;
										totalMoney = data.userData.totalMoney;
										remainMoney = data.userData.remainMoney == null ? 0 : data.userData.remainMoney;
										$("#remainMoneyPart").val(remainMoney);
										$("#totalMoneyPart").val(totalMoney);
									}
								}
							}
						});
			return flag;
		}

		//实际注资信息列表
		$("[data-btn='actual']").on("click",function(){ 
			var $self = $(this);
			var _url=platformUrl.toApprActualPage + "/" + $self.attr("data-part-id");
			var _name= $self.attr("data-name");
			var _flag= $self.attr("data-flag");
			var _part_id = $self.attr("data-part-id");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					$("#partFlag").val(_flag);
					$("#btn_add_appr_actual").attr("data-id",_part_id);
					var  v=$("#partFlag").val();
					$("#popup_name").html(_name);
				}//模版反回成功执行	
			});
			return false;
		});
	 showRow("${numOfShow}");
  });
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
  
  
  
  
  /**
   * 删除文件列表的文件
   * @param id
   * @param name
   * @param fieInputId
   */
  function delPart(id,name,fieInputId,partDelFile){
  	 if(deleteJSON[partDelFile]){
         deleteJSON[partDelFile] = deleteJSON[partDelFile] +","+id;
     }else{
         deleteJSON[partDelFile] = id;
     }
  	 var params = {};
	  params.projectId =  pId;
	  params.fileReidsKey = key;
	  params.newFileName = id;
     //文件id
     sendPostRequestByJsonObj(Constants.sopEndpointURL+'galaxy/informationFile/deleteRedisFile',params,function(data){
			//进行上传
			var result = data.status;
			if(result == "OK"){
			     //删除
			     $("#"+fieInputId).val($("#"+fieInputId).val().replace(name,""));
				 $("#"+id+"tr").remove();
			      var fieInputLen=$("tr[id]").length;
			      if(fieInputLen==0){
			      	$("#filelist").css("display","none");
			      }
			}else{
				layer.msg("删除失败!");
			}
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
		var numOfShow = $("#tabApprAllList .agreement:visible").length;
		var url = Constants.sopEndpointURL + "/galaxy/project/toAppropriation/"+searchPartMoney+"/${projectId}";
		if(numOfShow>0)
		{
			numOfShow = Math.max(numOfShow,2);
			url+="?numOfShow="+numOfShow;
		}
		$("#powindow").remove();
		$("#popbg").remove();
		$.getTabHtml({
			url : url
		});
		showRow(2);
	})


  //获取 页面数据\保存数据
function paramsContion(){
	if(!$("#actual_aging_form").validate().form())
	{
		return;
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
	$("#powindow").remove();
	$("#popbg").remove();
	//启用滚动条
	 $(document.body).css({
	   "overflow-x":"auto",
	   "overflow-y":"auto"
	 });
	$.getTabHtml({
		url : Constants.sopEndpointURL + "/galaxy/project/toAppropriation/"+url
	});
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
			initTabAppropriation(pId);
			/* var url = searchPartMoney+'/'+'${pid}';
			var numOfShow = $("#tabApprAllList .agreement:visible").length;
			if(numOfShow>0)
			{
				numOfShow = Math.max(numOfShow,2);
				url+="?numOfShow="+numOfShow;
			}
			$.getTabHtml({
				url : Constants.sopEndpointURL + "/galaxy/project/toAppropriation/"+url
			}); */
		} else {
			layer.msg(data.result.message);
		}
	});
}


</script>

</html>
