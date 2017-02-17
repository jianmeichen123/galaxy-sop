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
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!-- jsp文件头和头部 -->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<!-- time -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>

<!-- 分页二css+四js -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<link rel="stylesheet" href="<%=path %>/plugins/daterangepicker/css/font-awesome.min.css"  type="text/css">
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>运营数据记录</h2>
        <!--页眉-->
        <div class="top clearfix">
          <c:if test="${fx:hasRole(4)}">
            <a href="<%=path %>/galaxy/operationalData/addOperationalDataList/${projectId}"  style="width:130px;" class="pbtn bluebtn h_bluebtn" >添加运营数据记录</a>
          </c:if>
        </div>	
        <!-- 搜索条件 -->
        <div class="min_document clearfix min_document_da"  id="custom-toolbar-operate">
          <div class="top clearfix search_adjust1 searchall">
            <input type="hidden" name="projectId" value="${projectId}">
          <dl class="fmdl fmdll clearfix"">
             <dt>会议日期：</dt>
              <dd>
	         <input type="text" class="datepicker-year-text txt time" name="operateDateStart"  /> 
	          </dd>
          </dl>     
         <dl>
            <dd>
             <span>至</span>
	<input type="text" class="datepicker-year-text txt time" name="operateDateEnd"  /> 
            </dd>
         </dl>
          <dl class="fmdl fmmt clearfix">
              <dd class="clearfix">
                <label><input type="radio" checked="checked" name="dataTypeMonth" value="0" id="month">月数据</label>
                <label><input type="radio" checked="checked" name="dataTypeQuarter" value="1" id="quarter">季数据</label>
              </dd>
              <dd>
                 <button type="submit" class="bluebtn ico cx" action="querySearch">搜索</button>
              </dd>
            </dl>   
            
        </div>
        </div>
       <div class="tab-pane active" id="view">		
       	  <!--  <table id="fileGridOperation">
			</table> --> 
			<table id="fileGridOperation" data-url="<%=path %>/galaxy/operationalData/operationalDataList"  
				data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="operationIntervalDate"  data-formatter="dataRange" class="data-input">运营数据统计区间</th>
			        	<th data-field="updateDate"  class="data-input">编辑时间</th>
			        	<th data-field="updateUserName"  class="data-input">编辑人</th>
			        	<th  class="col-md-2" data-formatter="editor" data-class="noborder">操作</th>
 					</tr>	
 				</thead>
			</table>
           </div>      
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
</body>


<script type="text/javascript">
$('input[name="operateDateStart"], input[name="operateDateEnd"]').val(new Date().format("yyyy"));

var transferingIds = "${fx:getTransferingPids()}".split(",");
var isflag= "${fx:hasRole(4)}";
createMenus(14);
var projectId = '${projectId}';
  
$(function(){
	 $("#quarter").click(function(){
	      if($(this).attr("checked") == "checked"){
	    	  $(this).removeAttr('checked');
	      }else{
	    	  $(this).attr("checked","checked");
	      }
	    })
	    $("#month").click(function(){
	    	 if($(this).attr("checked") == "checked"){
		    	  $(this).removeAttr('checked');
		      }else{
		    	  $(this).attr("checked","checked");
		      }
	    })
	
    $("#fileGridOperation").bootstrapTable({
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
		queryParamsType: 'size|page',
	    pageNumber:1,            //初始化加载第一页，默认第一页
	    pageSize: 10,            //每页的记录行数（*）
	    pageList: [10, 20, 30],    //可供选择的每页的行数（*）
        search: false,
        //返回附带参数功能代码
        queryParams:function(params){
	    	return json_2_1(params,getToobarQueryParams('custom-toolbar-operate'));
		},
        onLoadSuccess: function(data){
		}
	});
  })
//根据toobar id 获取表单参数
 function getPartnerToobarQueryParams(ToolbarId){
 	$("#"+ToolbarId).find('dd:hidden').find(':input').attr('data', 'true');
 	var toolbar = $("#"+ToolbarId);
 	var query = {};
 	toolbar.find("input[name][type!='radio'][ data!='true']").each(function(){
 		
 			var input = $(this);
 			var name = input.attr("name");
 			var val = input.val();
 			if(val!=''){
 				query[name]=val;
 			}
 		
 	});
 	return query;
 }  
  //查看 or 编辑 会议纪要
function editor(value,row,index){
	var info = "<span  class=\"edit blue\"  onclick=\"operateOperationalData('"+row.id+"','info')\" >查看</span>";
	var edit = "";
	var deletes = "";
	if(isflag == "true"){
		edit = " <span  class=\"edit blue\"  onclick=\"operateOperationalData('"+row.id+"','edit')\" >编辑</span>";
		deletes = " <span  class=\"edit blue\"  onclick=\"deleteOperationalData('"+row.id+"','e')\" >删除</span>";
	}
	return info + edit + deletes;
}
function dataRange(value,row,index){
	return value+"年"+row.dataTypeValue;
}

function operateOperationalData(id,i){
	var _url='<%=path %>/galaxy/operationalData/editOperationalDataList/'+id;
	if(i == "info"){
		_url='<%=path %>/galaxy/operationalData/infoOperationalDataList/'+id;
	}
	window.location.href=_url;
}
function deleteOperationalData(selectRowId){
	layer.confirm('是否删除运营数据?',
		{
		  btn: ['确定', '取消'] ,
		  title :'提示',
		}, 
		
		function(index, layero){
			del_operate(selectRowId);
		}, 
		function(index){
		}
	);
}

function del_operate(id){  
	var _url =  '<%=path %>/galaxy/operationalData/delOperationalData/'+id;
	sendPostRequestByJsonObj(_url, {}, function(data){
		if (data.result.status=="OK") {
			layer.msg("删除成功");
			$("#fileGridOperation").bootstrapTable('refresh');
		} else {
			layer.msg(data.result.message);
		}
	});
}

$("button[action='querySearch']").click(function(){
	var start = $('input[name="operateDateStart"]').val();
	var end = $('input[name="operateDateEnd"]').val();
	if(start > end){
		layer.msg("开始时间不能大于结束时间!");
		return;
	}
	$("#fileGridOperation").bootstrapTable('refresh');
});
</script>



</html>

