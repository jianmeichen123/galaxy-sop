<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<script src="<%=path %>/js/sopinfo.js"></script>
<script src="<%=path %>/js/base_appropriation.js"></script>
<style type="text/css">
.bars{margin:0 !important;}
</style></head>
<body>
<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
 	<div class="ritmin">
 	
    	<jsp:include page="sopcommon.jsp" flush="true"></jsp:include>


		<div class="new_left">
			<div class="tabtable assessment label_static">
				<!-- tab标签 -->
	            <jsp:include page="tab_header.jsp?index=8" flush="true"></jsp:include>
            <!-- 拨款信息 -->
        
            	<div class="member proOperation">
                    <div class="top clearfix">
                        <!--按钮-->
                        <c:if test="${isCreatedByUser }">
                        <div class="btnbox_f btnbox_f1">
                            <a class="pbtn bluebtn h_bluebtn" href="/sop/html/actual_all.html" data-btn="actual_all" data-on="save" data-name='添加总拨款计划'>添加总拨款计划</a>
                        </div>
                        </c:if>

                    </div>
                    <!-- 搜索条件 -->
                    
                    <div class="min_document fund_list clearfix">
                      <div class="bottom clearfix">
                        <dl class="fmdl fmdll clearfix">
                          <dt>计划拨款金额：</dt>
                          <dd>
                            <input type="text" class="txt" id="searchPartMoney"/>
                          </dd>
                          <dd><a href="javascript:;" class="bluebtn ico cx" id="search" >搜索</a></dd>
                        </dl>
                      </div>
                    </div>  
                  <div id="tabApprAllList">
                   
                  </div>
             <span class="show_total">显示第 <span class="start">1</span> 条到第 <span class="end">2</span> 条，总共 <span class="total">2</span> 条记录</span>
            <span class="show_more blue fr" id="reloadAll">显示更多</span>
        </div>  
                   <!--tab end-->
          </div>
        </div>
        <!--右边-->
           <jsp:include page="includeRight.jsp" flush="true"></jsp:include>
      </div>
 
</div>
<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>
<!-- file -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/batchUpload.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<script>
var pId;
  $(function(){  
	pId="${pid}";
	  $("#tabApprAllList").children('div').remove(); 
		reloadData(null,pId);
	 //只有创建人显示编辑按钮
	  if(isCreatedByUser != 'true')
	  {
		  $("#tabApprAllList .b_agreement_r").hide();
		  $("#tabApprAllList .edit-btn, #tabApprAllList .del-btn").hide();
	  }
		//添加，编辑总拨款计划弹出页面
	$("[data-btn='actual_all']").on("click",function(){ 
			var $self = $(this);
		var _url=platformUrl.toApprActualAll+"?pid=${pid}";
		var _name= $self.attr("data-name");
		var data_on=$self.attr("data-on");
		var id=$self.attr("data-val");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					$("#popup_name").html(_name);
					if(data_on=="edit"){
						sendPostRequest(platformUrl.getGrantTotal+"/"+id,queryBack1);
					}
					initDialogVal();
				}//模版反回成功执行	
			});
			return false;
		});
		
	    function showGrantPart(tid){
	    	
	    }
		//编辑总拨款计划
		$("[data-btn='actual_aging']").on("click",function(){ 
		var $self = $(this);
		var _data_type = $self.attr("data_type");
		
		var _id = $self.attr("data-id");
		var _url=  Constants.sopEndpointURL+'/galaxy/grant/part/toApprPartAging'+"/"+_id;
		var _name= $self.attr("data-name");
		var _total_name = $self.attr("data-total-name");
		
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					
					$("#popup_name").html(_name);
					$("#totalName").html(_total_name);
					if(_data_type == "edit"){
						var _part_id = $self.attr("data-part-id");
						//edit
						_url = Constants.sopEndpointURL + '/galaxy/grant/part/selectGrantPart/'+_part_id;
						sendGetRequest(_url, {}, function(data){
							var result = data.result.status;
							if(result == "OK"){
								var grantPartInfo = data.entity;
								$("#actual_aging_container [name='id']").val(grantPartInfo.id);
								$("#actual_aging_container [name='totalGrantId']").val(grantPartInfo.totalGrantId);
								$("#actual_aging_container [name='grantDetail']").val(grantPartInfo.grantDetail);
								$("#actual_aging_container [name='grantMoney']").val(grantPartInfo.grantMoney);
								
								$.each(data.entity.files,function(){
									var but = "<button type='button' id='"+this.id+"btn' onclick=del('"+this.id+"','"+this.fileName+"','textarea2')>删除</button>" ;
									var htm = "<tr id='"+this.id+"tr'>"+
													"<td>"+this.fileName+"."+this.fileSuffix+
														"<input type=\"hidden\" name=\"oldfileids\" value='"+this.id+"' />"+
													"</td>"+
													"<td>"+plupload.formatSize(this.fileLength)+"</td>"+
													"<td>"+ but +"</td>"+
													"<td>100%</td>"+
												"</tr>"
									$("#filelist").append(htm);
								});
								toInitBachUpload();
								
							}else{
								layer.msg(data.result.message);
							}
						});
					}else{
						$("#partId").remove();
						toInitBachUpload();
					}
					initDialogVal();
					
				}//模版反回成功执行	
			});
			return false;
		});

		//实际拨款信息列表
		$("[data-btn='actual']").on("click",function(){ 
			var $self = $(this);
			var _url=platformUrl.toApprActualPage + "/" + $self.attr("data-part-id");;
			var _name= $self.attr("data-name");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					$("#popup_name").html(_name);
						//查看实际拨款信息
					$("[data-btn='actual_look']").on("click",function(){ 
						var $self = $(this);
						var _url = platformUrl.toApprActualLock;
						var _name= $self.attr("data-name");
						$.getHtml({
							url:_url,//模版请求地址
							data:"",//传递参数
							okback:function(){
								$("#popup_name_look").html(_name);
							}//模版反回成功执行	
						});
						return false;
					});
						//编辑实际拨款信息
					$("[data-btn='edit_actual']").on("click",function(){ 
						var $self = $(this);
						var _url=platformUrl.toApprActualPage;
						var _name= $self.attr("data-name");
						$.getHtml({
							url:_url,//模版请求地址
							data:"",//传递参数
							okback:function(){
								$(".popup_name_edit").html(_name);
							}//模版反回成功执行	
						});
						return false;
					});
				//添加实际拨款信息
					$("[data-btn='edit_actual']").on("click",function(){ 
						var $self = $(this);
						var _url = platformUrl.toApprActualPage;
						var _name= $self.attr("data-name");
						$.getHtml({
							url:_url,//模版请求地址
							data:"",//传递参数
							okback:function(){
								$(".popup_name_edit").html(_name);
							}//模版反回成功执行	
						});
						return false;
					});
				}//模版反回成功执行	
			});
			return false;
		});
		//拨款进度
		  $("#bar_m").css("width","0px");  //初始化进度条宽度；
		    var moneyComplete=$(".money_complete").text();
		        moneyTotal=$(".money_total").text();
		        m_width=$(".progressBar").width();
		        barWidth=parseInt(moneyComplete/moneyTotal*m_width)+"px";
		    $("#bar_m").css("width",barWidth)
		    //获取表格除第一行，第二行之外的元素
		    var tr_n=$(".moneyAgreement tbody tr")
		    var tr_s=$(".moneyAgreement tbody tr").eq(1).nextAll();
		    tr_s.css("display","none");
		    if(tr_n.length>2){
		      $(".agreement .show_more").show();
		      $(".agreement .show_more").click(function(){
		        $(this).hide();
		        $(".agreement .show_hide").show();
		        tr_n.show();
		      })
		       $(".agreement .show_hide").click(function(){
		        $(this).hide();
		        $(".agreement .show_more").show();
		        tr_s.css("display","none");
		      })
		    }

	 createMenus(5);
	 appropriationProcess();
     showTwo();
  })
 
   function showTwo(){
	// 点击一次加载2条
	    var tabApprSingleListBlock=$("#tabApprAllList .agreement").length;
	    $("#tabApprAllList .agreement:lt(2)").css("display","block");
	    if(tabApprSingleListBlock==0){
	    	$(".proOperation .show_total").css("display","none");
	    }
	    if(tabApprSingleListBlock<3){
	    	 $(".proOperation .show_more").css("display","none");
	    	 $(".show_total .end").text(tabApprSingleListBlock);//左侧显示条数
	    	 $(".show_total .total").text(tabApprSingleListBlock);//左侧显示条数
	    }
	    if(tabApprSingleListBlock>2){
	 	   $(".proOperation .show_more").css("display","block");
	 	  $(".show_total .end").text('2');//左侧显示条数
	 	  $(".show_total .total").text(tabApprSingleListBlock);//左侧显示条数
	    }

	    var clickNum = 0; //点击的次数
	    $(".proOperation .show_more").unbind('click').click(function(event) {
	    clickNum++;
	    var iNum = 2*clickNum+2; //每次点击加载的条数
	    //console.log(iNum)
	    $("#tabApprAllList .agreement:lt("+iNum+")").css("display","block");
	    if(iNum>tabApprSingleListBlock){
	    	$(".show_total .end").text(tabApprSingleListBlock);
	    }else{
	    	$(".show_total .end").text(iNum);
	    }
	    if(iNum>tabApprSingleListBlock || iNum==tabApprSingleListBlock){
	    	 $(".proOperation .show_more").css("display","none");
	    } 
	    return false;
	    });
	    
  }
   function queryBack1(data){
	  var result = data.result.status;
		if(result == "ERROR"){ //OK, ERROR
			layer.msg(data.result.message);
			return;
		}else{
			 var grantTotal = data.entity;
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
			reloadData(null,pId);
		}else{
			reloadData(searchPartMoney,pId);
		}
		  showTwo();
	})

  //获取 页面数据\保存数据
function paramsContion(){
	 
	if(!beforeSubmit()){
		return false;
	}
	var partMoney = $("#grantMoney").val();
	var remainMoney = $("#remainMoney").val();
	if(parseFloat(partMoney) > parseFloat(remainMoney)){
		layer.msg("分期拨款金额之和大于总拨款金额");
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
	showTabs('${pid}',8);
}
function appropriationProcess(){
	sendPostRequest(platformUrl.getApprProcess+"/"+pId,appropriationProcessBack);
}
function appropriationProcessBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		layer.msg(data.result.message);
		return;
	}else{
		 var grantTotal = data.entity;
	}
}

function to_del_grantPart(selectRowId){
	layer.confirm('是否删除事项?',
		{
		  btn: ['确定', '取消'] 
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
	//var id = $("#del_grantPart_id").val();
	var _url =  Constants.sopEndpointURL + '/galaxy/grant/part/delGrantPart/'+id;
	sendPostRequestByJsonObj(_url, {}, function(data){
		if (data.result.status=="OK") {
			layer.msg("删除成功");
			removePop1();
			showTabs('${pid}',8);
		} else {
			layer.msg(data.result.message);
		}
	});
}	
</script>

</html>
