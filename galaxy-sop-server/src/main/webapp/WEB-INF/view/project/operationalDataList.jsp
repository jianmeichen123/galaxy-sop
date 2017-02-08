<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
	<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<!-- jsp文件头和头部 -->
	<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%-- 	<link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css"  type="text/css"> --%>
	<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<%-- 	<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css"> --%>

	
	<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <script src="<%=path %>/js/init.js"></script>
<%--     <script src="<%=path %>/js/jquery.showLoading.min.js"></script> --%>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datePicker-handler-init.js"></script>
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
            
        </div>	
        <!-- 搜索条件 -->
        <div class="min_document clearfix min_document_da"  id="custom-toolbar">
          <div class="top clearfix search_adjust1 searchall">
            
           <dl class="fmdl fmmt clearfix">
              <dd class="clearfix">
                <label><input type="radio" checked="checked" name="typeData" id="month">月数据</label>
                <label><input type="radio" name="typeData" id="quarter">季数据</label>
                <select name="" id="monthData">
                  <option value="">--请选择--</option>
                  <option value="">1月</option>
                  <option value="">2月</option>
                  <option value="">3月</option>
                  <option value="">4月</option>
                  <option value="">5月</option>
                </select>
                <select name="" id="quarterData">
                  <option value="">--请选择--</option>
                  <option value="">第一季度</option>
                  <option value="">第二季度</option>
                  <option value="">第三季度</option>
                  <option value="">第四季度</option>
                </select>
              </dd>
            </dl>
         
          <dl class="fmdl fmdll clearfix"">
             <dt>会议日期：</dt>
                  <dd>
	<input type="text" class="datepicker txt time" name="meet_startDate"  /> 
	    </dd>
          </dl>     
         <dl>
            <dd>
             <span>至</span>
	<input type="text" class="datepicker txt time" name="meet_endDate"  /> 
            </dd>
             <dd>
            <button type="button" class="bluebtn ico cx"   id="searchBtn">搜索</button>
            </dd>
         </dl>   
        </div>
        </div>
       <div class="tab-pane active" id="view">		
			<table id="fileGrid22"></table>
           </div>      
    </div>
</div>

<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<jsp:include page="../sopFile/projectDialog.jsp" flush="true"></jsp:include>
<jsp:include page="/galaxy/sopFile/showMailDialog" flush="true"></jsp:include>



<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
</body>
<script type="text/javascript">
var transferingIds = "${fx:getTransferingPids()}".split(",");
</script>


<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
 <script src="<%=path %>/js/commWin.js" type="text/javascript"></script>
 <script src="<%=path %>/js/teamSheetNew.js" type="text/javascript"></script>
<%--  <script src="<%=path %>/js/teamSheet.js" type="text/javascript"></script> --%>
 <script src="<%=path %>/js/sopFile.js" type="text/javascript"></script>
 
<script>
$('#fileGrid22').bootstrapTable({
    url : '<%=path%>/galaxy/operationalData/operationalDataList',     //请求后台的URL（*）
    queryParamsType: 'size|page', // undefined
    showRefresh : false ,
    search: false,
    method : 'post',           //请求方式（*）
//    toolbar: '#toolbar',        //工具按钮用哪个容器
//    striped: true,           //是否显示行间隔色
    cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
    pagination: true,          //是否显示分页（*）
    sortable: false,           //是否启用排序
    sortOrder: "asc",          //排序方式
    //queryParams: fileGrid.queryParams,//传递参数（*）
    sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
    pageNumber:1,            //初始化加载第一页，默认第一页
    pageSize: 10,            //每页的记录行数（*）
    pageList: [10, 20],    //可供选择的每页的行数（*）
    strictSearch: true,
    clickToSelect: false,        //是否启用点击选中行
//    height: 460,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
    uniqueId: "id",           //每一行的唯一标识，一般为主键列
    cardView: false,          //是否显示详细视图
    detailView: false,          //是否显示父子表
    columns: [
		{
      field: 'employNum',
      title: '职工个数'
    }/* , {
      field: 'projectName',
      title: '所属项目',
      formatter: fileGrid.projectNameFormatter	
    }, {
      field: 'fSource',
      title: '档案来源'
    }, {
      field: 'fileUName',
      title: '起草者'
    }, {
      field: 'fType',
      title: '存储类型'
    }, {
      field: 'fWorktype',
      title: '业务分类'
    }, {
	    field: 'voucherFile',
	    title: '签署凭证',
	    events : fileGrid.operateEvents,
	    formatter: fileGrid.operateVFormatter 	
	  }, {
      field: 'updatedDate',
      title: '更新日期'
    }, {
      field: 'fileStatusDesc',
      title: '档案状态'
    },{
  	  field: 'operate', 
  	  title: '操作', 
  	  events: fileGrid.operateEvents, 
  	  formatter: fileGrid.operateFormatter 

    } */]
  });
  
$(function(){
    $("#quarterData").hide();
    $("#quarter").click(function(){
      $(this).attr("checked","checked");
      $("#month").removeAttr('checked');
      $("#quarterData").show();
      $("#monthData").hide();
    })
    $("#month").click(function(){
      $(this).attr("checked","checked");
      $("#quarter").removeAttr('checked');
      $("#monthData").show();
      $("#quarterData").hide();
    })
  })
</script>



</html>

