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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]--> 
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include> 
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/projectTransfer.js"></script>
</head>

<body >
<div class="pagebox assign_project" >
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<div class='content_task'>
		<div class='title_top'>
			
			 <c:if test="${from eq 'assign' }">
			 <h3>指派项目</h3>
			<span class='operate_project' data-code='transfer-task'>指派项目</span>
			 </c:if> 
			 <c:if test="${from eq 'transfer'}">
			 <h3>移交项目</h3>
			<span class='operate_project' data-code='abandon-task'>移交项目</span>
			</c:if>
		</div>
		<div class="pageTop clearfix">
			<div class="buttonGroup clearfix">				
				<div class="form-group">
			      <select name='projectDepartid' class="selectpicker">
					  <option index="-1" value="0">全部事业部</option>
					</select>
			  	</div>

				<div class="form-group">
			      <select name="createUid" class="selectpicker">
					  <option>投资经理</option>
					 <option index="-1">全部</option>
					</select>
			  	</div>
				
				<div class="form-group">
			      <select name="projectProgress" class="selectpicker">
					  <option>项目进度</option>
					  <option index="-1">全部</option>
					</select>
			  	</div>
				<!-- <div class="form-group">
			      <select name="projectProgress" class="selectpicker">
					  <option>项目进度</option>
					 <option value="0">全部</option>
					</select>
			 	</div> -->

				<div class="form-group">
			      <select name="projectStatus" class="selectpicker">
					  <option>项目状态</option>
					 <option index="-1">全部</option>
					</select>
			    </div>

				<div class="form-group">
			      <select name="financeStatus" class="selectpicker">
					  <option>融资状态</option>
					 <option index="-1">全部</option>
					</select>
			  	</div>

				<div class="form-group">
			      <select name="faFlag" class="selectpicker">
					  <option>项目来源</option>
					 <option index="-1">全部</option>
					</select>
			  	</div>
				
		  	</div>
		  	<div class="input-group">
			  	<di class='input-content'>
			  		<input type="text" class="form-control" name="nameCodeLike" placeholder="请输入项目名称">
			  		<span class="querySearch"></span>
			  	</di>
		      <!-- <span class="input-group-btn">
		        <button class="btn btn-default" type="button"></button>
		      </span> -->
		      <span class='reset_search'>重置</span>
		    </div>
		</div> 
	</div>
	<div class="ritmin"> 
		
		<div class="tab-pane active ctlist pagination_common" id="view">
			<table   class='assingTable table-hover' id="assign-table"  
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				   		<th data-field="projectNameOne"  data-formatter="projectCheckbox" class="data-input" data-width="1%">
				    		<label class='highlighCheckbox_th'>
				    				<input type="checkbox">
				    		</label> 
				    		
				    	</th>
			        	<th data-field="projectName"  data-formatter="projectInfo" data-width="10%">项目名称</th>
			        	<th data-field="project_type" data-formatter="typeFormat"    data-width="8%">项目类型</th>
			        	<th data-field="finance_status" data-formatter="financeStatusFormat"   data-width="8%">融资状态</th>
			        	<th data-field="project_progress" data-formatter="projectProgress"   data-width="8%">项目进度</th>
			        	<th data-field="project_status" data-formatter="projectStatusFormat"   data-width="8%">项目状态</th>
			        	<th data-field="faFlag" data-formatter="projectFaFormat"   data-width="8%">项目来源</th>
			        	<th data-field="projectCareerline"   data-width="12%">事业部</th>
			        	<th data-field="createUname"   data-width="14%">投资经理</th>
			        	<th data-field="created_time" data-formatter="createdFormat"    data-width="8%">创建日期</th>
			        	<th data-field="updated_time" data-formatter="updateFormat"   data-width="8%">最后编辑时间</th>
 					</tr>	
 				</thead>
 				<tbody>
 				
 				</tbody>
			</table> 
	
			
       </div>
	</div>
</div>
</body> 
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>  
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/> 
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=path %>/js/init.js"></script>  
</html>
<script>
$(function(){
  //导航
	createMenus(5);

 ///////////////////////初始化筛选条件
  createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"projectProgress","projectProgress")//项目进度
 createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"projectStatus","projectStatus")//项目状态
	/**
	 * 获取融资状态下拉项
	 * @version 2016-06-21
	 */
	sendGetRequest(platformUrl.queryAllTitleValues+'FNO1?reportType=4', null,CallBackB);
	function CallBackB(data){
	    var _dom=$("select[name='financeStatus']");
	    var childNum = _dom.find("option").length;
	    var entity=data.entity.childList[0];
	    if(!childNum || childNum !=0 ){
	    	$.each(entity.valueList,function(){
	    		_dom.append("<option value='"+this.id+"' data-title-id='"+this.titleId+"'>"+this.name+"</option>");
			});
	    }
	}
createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"projectSource","faFlag");//项目来源
if(${from eq 'assign' }){
	alert('${fx:reloadCondition('project_batch_assign')}');
	var result='${fx:reloadCondition('project_batch_assign')}';
	if(result!=""){
		if(result==1){
			$("[name='projectDepartid']").css("display","none");
			$("[name='createUid']").css("display","none");
		}else if(result==2){
			createCareelineOptions(platformUrl.getCareerlineList,"projectDepartid");//全部事业部
		}else if(result==3){
			 $("[name='projectDepartid']").parent(".form-group ").attr("style","display:none");
		}else if(result==6){
			  var depts
				if('${fx:reloadDepts('project_batch_assign')!=""}'){
					  depts='${fx:reloadDepts('project_batch_assign')}'
					  var deptArr=null!=depts?depts.split(","):[];
					  createCareelinePartShow(platformUrl.getCareerlineList,"projectDepartid",1,deptArr);
					
				}
		}
	}
	

}else if(${from eq 'transfer' }){
	var result='${fx:reloadCondition('project_batch_transfer')}';
	if(result!=""){
		if(result==1){
              $("[name='projectDepartid']").parent(".form-group ").attr("style","display:none");
			  $("[name='createUid']").parent(".form-group ").attr("style","display:none");
		}
    }
}

/**
 * 根据事业线查询相应的投资经理
 * @version 2016-06-21
 */
createUserOptions_All(platformUrl.getUserList+$('select[name="projectDepartid"]').val(), "createUid", 0);//投资经理
	/**
	 * 改变事业线时获取该事业线下的投资经理
	 * @version 2016-06-21
	 */
	$('select[name="projectDepartid"]').change(function(){
		var did = $('select[name="projectDepartid"]').val();
	    createUserOptions_All(platformUrl.getUserList+did, "createUid", 1);
	    $('.selectpicker').selectpicker('refresh');
	});
	 $('.selectpicker').selectpicker({
		  style: 'btn-info',
		  size: 4,
		  
		});
	 $('.selectpicker').selectpicker('refresh');
	

///////////////////////初始化筛选条件finish
 
 
 detailHeaderWidth();
 function detailHeaderWidth(){
 	  var  w_lft=$(".lft").width();
 	  	$('.content_task').css({'margin-left':w_lft});
 }
 $(window).resize(function(){
 	detailHeaderWidth();
 })	


 
 
 	/*指派项目弹窗点击事件*/
	$('.title_top span').click(function(){
		/* var rows = $("#task-table").bootstrapTable('getSelections');
		if(rows.length==0)
		{
			layer.msg('请至少选择一条待办任务');
			return;
		} */
		//var index = $(this).index();
		var param=countNum();
		var code = $(this).attr("data-code");
		$.getHtml({
			url:getDetailUrl(code),
			okback:function(){
				$("#projectNum").html(param.num);
				$("#actionStyle").val(param.actionStyle);
				doSumbit(param.projectIds);
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
			return '<%=path%>/html/assign_project.html';
		}else if(code === 'abandon-task'){
			return '<%=path%>/html/handover_project.html';
		}		
		return "";
	}
	//搜索
	var initParams;
	var initPageSize = 10;
	//重置方法
	$(".reset_search").click(function(){
		$('.selectpicker').selectpicker('deselectAll');
		$(".form-control").val("");
	});
	
  	
	function queryParams(param){
		var valueNum = $('select[name="projectDepartid"]').val();//事业线
		var valueManager = $('select[name="createUid"]').val();//投资经理
		var valueProjectProgress = $('select[name="projectProgress"]').val();//项目进度
		var valueProjectStatus= $('select[name="projectStatus"]').val();//项目状态
		var valuefinanceStatus= $('select[name="financeStatus"]').val();//融资状态
		var valueFlag= $('select[name="faFlag"]').val();//项目来源
		if(valueProjectProgress=="全部"||valueProjectProgress=="项目进度"){
		}else{
			param.projectProgress =valueProjectProgress//项目进度
		}
		
		if(valueProjectStatus=="全部"||valueProjectStatus=="项目状态"){
		}else{
			param.projectStatus = valueProjectStatus;//项目状态
		}
		
		if(valuefinanceStatus=="全部"||valuefinanceStatus=="融资状态"){
		}else{
			param.financeStatus = valuefinanceStatus;//融资状态
		}
		if(valueManager=="全部"||valueManager=="投资经理"){
			
		}else{
			param.createUid = valueManager;//投资经理
		}
		if(valueNum=="全部"||valueNum=="全部事业部"){
			
		}else{
			param.projectDepartid = valueNum;//全部事业部
		}
		var nameCodeLike = $("input[name='nameCodeLike']").val();
		param.nameCodeLike = nameCodeLike!="undefined"?nameCodeLike:"";
		return param;
		
	} 	
	
	
	/* change事件*/
	//事业线
	$('select[name="projectDepartid"]').change(function(){
		//queryParams(param)
		$('#assign-table').bootstrapTable('refresh')
	});
	//投资经理
	$('select[name="createUid"]').change(function(){
		if($('select[name="createUid"]').find('.ilter-option').text()=="全部"){
			$('select[name="createUid"]').find('.ilter-option').text('aaa')
		}
		$('#assign-table').bootstrapTable('refresh')
	});
	//项目进度
	$('select[name="projectProgress"]').change(function(){
		$('#assign-table').bootstrapTable('refresh')
	});
	//项目状态
	$('select[name="projectStatus"]').change(function(){
		$('#assign-table').bootstrapTable('refresh')
	});
	//融资状态
	$('select[name="financeStatus"]').change(function(){
		$('#assign-table').bootstrapTable('refresh')
	});
	//项目来源
	$('select[name="faFlag"]').change(function(){
		$('#assign-table').bootstrapTable('refresh')
	});
	//搜索方法
	$("span[class='querySearch']").click(function(){
		//buryPoint("98");
		//initParams = cookieOperator.pullCookie({_paramKey : 'projectList',_path : "/"});
			
		 $('#assign-table').bootstrapTable('refresh')
	});

	
	
	
	//初始化项目列表
	var initPageSize = 10;
	$('#assign-table').bootstrapTable({
		queryParamsType: 'size|page',
		pageSize:initPageSize,
		pageNum:1,
		showRefresh : false,
		url : 'http://fx.local.galaxyinternet.com/sop/galaxy/project/search',
		sidePagination: 'server',
		method : 'post',
		sortName : 'updated_time',
		pagination: true,
        search: false,
        singleSelect:true,
        //返回附带参数功能代码
        queryParams : queryParams,
        onLoadSuccess: function (data) {
        	if($("#showResetBtn").val() == '1'){
    			$("#resetBtn").removeClass("none");
    		}
        	if($("select[name='faFlag']").val()=="projectSource:1"){
        		$("input[name='faName']").show();
        	}
        	
       	 /* checkbox 点击 */
       	 $('.highlighCheckbox').click(function(event){
       		 $(this).toggleClass('highlighCheckbox_checked');
       		 event.preventDefault(); 
       		 
       	 });
       	 //全选
       	 $('.highlighCheckbox_th').click(function(event){
       		 $(this).toggleClass('highlighCheckbox_checked');
       		 $('.highlighCheckbox').addClass('highlighCheckbox_checked');
       		 if(!$(this).hasClass('highlighCheckbox_checked')){
       			 $('.highlighCheckbox').removeClass('highlighCheckbox_checked');
       		 }
       		 event.preventDefault(); 
       	 })
        }
	});
})
 

 	 function projectInfo(value,row,index){//项目名称
		    var id=row.id;
			var str=row.projectName;
			if(str.length>10){
				subStr = str.substring(0,10);
				//var options = '<a href="#" class="blue" data-btn="myproject" onclick="proInfo(' + id + ')" title="'+str+'">'+subStr+'</a>';
				return subStr;
			}
			else{
				//var options = '<a href="#" class="blue" data-btn="myproject" onclick="proInfo(' + id + ')" title="'+str+'">'+str+'</a>';
				return str;
			}
		} 

/**
 * 项目类型格式化
 * @version 2016-06-21
 */
function typeFormat(value,row,index){
	return row.type;
}
/**
 * 融资状态格式化
 * @version 2016-06-21
 */
function financeStatusFormat(value,row,index){
	return row.financeStatusDs;
}

/**
 * 项目FA格式化
 * @version 2016-06-21
 */
	function projectFaFormat(value,row,index){
		var retStr = '-';
		if(!row.faFlag)
		{
			return '-';
		}
		if(row.faName)
		{
			if(row.faName.length>4){
				var faName=row.faName.substring(0,4);
				retStr="<div title='"+row.faFlagStr+'-'+row.faName+"'>"+row.faFlagStr+'-'+faName+"</div>";
			}else{
				retStr="<div title='"+row.faFlagStr+'-'+row.faName+"'>"+row.faFlagStr+'-'+row.faName+"</div>";
			}
			
		}else{
			retStr="<div title='"+row.faFlagStr+"'>"+row.faFlagStr+"</div>";
		}
		return retStr;
		
	}
/**
 * 项目进度格式化
 * @version 2016-06-21
 */
 function projectProgress(value,row,index){
	var projectPro = row.projectProgress;
	var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);
	var proStatus = row.projectStatus;
	var pronum = proStatus.substring(proStatus.lastIndexOf(":")+1,proStatus.length);

	return row.progress;
}

	/**
	 * 创建时间格式化
	 * @version 2016-06-21
	 */
	function createdFormat(value,row,index){
		return row.createDate;
	}
	/**
	 * 更新时间格式化
	 * @version 2016-06-21
	 */
	function updateFormat(value,row,index){
		return row.updateDate;
	}
	/**
	 * 项目状态格式化
	 * @version 2016-06-21
	 */
	function projectStatusFormat(value,row,index){
		return row.projectStatusDs;
	}
	 /*checkbox column format */
	function projectCheckbox(value, row, index) {
		var options = "<label class='highlighCheckbox'><input type='checkbox' name='isCheck' value='"+row.id+"'/></label> ";
		return options;
	}
	 /*checked project num*/
	function countNum() {
		var projectIds=[];
		var num=$(".highlighCheckbox_checked [name='isCheck']").length;
		$(".highlighCheckbox_checked [name='isCheck']").each(function (n) {
			projectIds[n]=this.value;
		});
		var param={
				"num":num,
		 "projectIds":projectIds,
		 "actionStyle":'${from}'
			};
		return param;
		
	}
</script>
