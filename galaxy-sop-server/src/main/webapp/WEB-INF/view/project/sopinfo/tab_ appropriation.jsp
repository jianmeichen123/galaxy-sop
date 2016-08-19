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
	            <ul class="tablink tablinks">
	                <li><a href="javascript:;" onclick="showTabs(${pid},0)">基本信息</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},1)">团队成员</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},2)">股权结构</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},3)">访谈记录</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},4)">会议纪要</a></li>
	                <li ><a href="javascript:;" onclick="showTabs(${pid},7)">交割前事项</a></li>
	               <li class="on"><a href="javascript:;" onclick="showTabs(${pid},8)">拨款信息</a></li> 
                	<li><a href="javascript:;" onclick="showTabs(${pid},9)">运营分析</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},5)">项目文档</a></li>
	                <li><a href="javascript:;" onclick="showTabs(${pid},6)">操作日志</a></li>
	                
	            </ul>
            <!-- 拨款信息 -->
        
            	<div class="member proOperation">
                    <div class="top clearfix">
                        <!--按钮-->
                        <div class="btnbox_f btnbox_f1">
                            <a class="pbtn bluebtn h_bluebtn" href="/sop/html/actual_all.html" data-btn="actual_all" data-on="save" data-name='添加总拨款计划'>添加总拨款计划</a>
                        </div>

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
            <span class="show_total">总共 41 条记录</span>
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
  $(function(){  
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
		
		//编辑总拨款计划
		$("[data-btn='actual_aging']").on("click",function(){ 
			var $self = $(this);
		var _url=platformUrl.toApprActualAging+"/${pid}";
		var _name= $self.attr("data-name");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					toInitBachUpload();
					$("#popup_name").html(_name);
					initDialogVal();
					
				}//模版反回成功执行	
			});
			return false;
		});
	 createMenus(5);
    showTwo();
  })
 
  function showTwo(){
	// 点击一次加载2条
	    var tabApprSingleListBlock=$("#tabApprAllList .agreement").length;
	    console.log(tabApprSingleListBlock)
	    $("#tabApprAllList .agreement:lt(2)").css("display","block");
	    if(tabApprSingleListBlock==0){
	    	$(".proOperation .show_total").css("display","none");
	    }
	    if(tabApprSingleListBlock<3){
	    	 $(".proOperation .show_more").css("display","none");
	    }
	    if(tabApprSingleListBlock>2){
	 	   $(".proOperation .show_more").css("display","block");
	    }
	    var clickNum = 0; //点击的次数
	    $(".proOperation .show_more").unbind('click').click(function(event) {
	    clickNum++;
	    var iNum = 2*clickNum+2; //每次点击加载的条数
	    //console.log(iNum)
	    $("#tabApprAllList .agreement:lt("+iNum+")").css("display","block");
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
			reloadData(0,9999,null);
		}else{
			reloadData(0,9999,searchPartMoney);
		}
		  showTwo();
	})

  //获取 页面数据\保存数据
function paramsContion(){
	if(!beforeSubmit()){
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
			        platformUrl.toAddApprActualAging,"textarea2","select_btn","win_ok_btn","actual_aging_container","filelist",
					paramsContion,"actual_aging_form",saveCallBackFuc);
}

/**
 * 回调函数
 */
 
function saveCallBackFuc(data){
	removePop1();
	//启用滚动条
	 $(document.body).css({
	   "overflow-x":"auto",
	   "overflow-y":"auto"
	 });
	$("#project_delivery_table").bootstrapTable('refresh');
}
</script>

</html>