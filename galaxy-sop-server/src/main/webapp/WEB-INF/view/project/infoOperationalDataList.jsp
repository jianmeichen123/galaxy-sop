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
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	    <div class="new_tit_a"><a href="#">工作桌面</a><a href="#">>创投项目</a><a href="#">>少时诵书会</a><a href="#">>运营分析</a><a href="#">>运营数据记录</a>>查看运营数据</div>
    	<div class="new_tit_b">
        	<dl class="fmdl fmmt clearfix">
              <dt>运营数据统计区间：</dt>
              <dd>
                ${operationalData.operateDate }
              </dd>
          </dl>
          <span class="b_span"> 
            <a href="<%=path %>/galaxy/operationalData/editOperationalDataList/${operationalData.id}" class="blue edit_operation">编辑</a>
            <a href="<%=path %>/galaxy/operationalData/toOperationalDataList/${operationalData.projectId}" class="blue">返回&gt;</a>
          </span>
      </div>
      <table width="100%" cellspacing="0" cellpadding="0" style="border-radius:6px;" class="basic_table operation_look">
              <thead>
                  <tr>
                      <th></th>
                      <th>项目</th>
                      <th>数据</th>
                      <th>说明</th>
                  </tr>
              </thead>                                                                                                                                    
              <tbody>
                 <tr>
                   <td rowspan="3" class="rowtds">基础情况</td>
                   <td>员工人数(个)</td>
                   <td>${operationalData.employNum }</td>
                   <td>当月在职员工数（所有需要开工资的人数）</td>
                 </tr>
                 <tr>
                   <td>分站（店）数量(个)</td>
                   <td>${operationalData.branchNum }</td>
                   <td>截至当月结束已经开设的分站或分店数量</td>
                 </tr>  
                 <tr>
                   <td>产品进度(%)</td>
                   <td>${operationalData.productProcess }</td>
                   <td>产品的开发进度，以正式上线为100%</td>
                 </tr>
                 <tr>
                   <td rowspan="10" class="rowtds">财务情况</td>
                   <td>员工工资(元)</td>
                   <td>${operationalData.salary }</td>
                   <td>当月需要支出的员工工资金额，如9月提交的是8月的工资金额</td>
                 </tr>
                 <tr>
                   <td>房租水电网(元)</td>
                   <td>${operationalData.otherCoat } 月付</td>
                   <td>房租、水电、网络需要支出的金额，如按年或季度付，请标注</td>
                 </tr>  
                 <tr>
                   <td>管理费用(元)</td>
                   <td>${operationalData.manageCost }</td>
                   <td>差旅、办公用品、应酬、快递等费用</td>
                 </tr> 
                <tr>
                  <td>市场费用(元)</td>
                  <td>${operationalData.marketCost }</td>
                  <td>用于产品宣传推广的支出金额</td>
                </tr>
                 <tr>
                  <td>业务运营费用(元)</td>
                  <td>${operationalData.operatingCost }</td>
                  <td>用于产品运营的支出费用，如活动、用户激励、促销成本等</td>
                </tr>
                 <tr>
                  <td>生产成本(元)</td>
                  <td>${operationalData.productionCost }</td>
                  <td>用于产生产品或服务的“制造”费用，如食材、原料、货价、上游采购等</td>
                </tr>
                 <tr>
                  <td>交易额(元)</td>
                  <td>${operationalData.tradeCost }</td>
                  <td>当月产生的交易额</td>
                </tr>
                 <tr>
                  <td>收入(元)</td>
                  <td>${operationalData.incomeCost }</td>
                  <td>当月产生的收入额</td>
                </tr>
                 <tr>
                  <td>净利润(元)</td>
                  <td>${operationalData.profitCost }</td>
                  <td>当月进账金额减所有应支付金额</td>
                </tr>
                 <tr>
                  <td>账面余额(元)</td>
                  <td>${operationalData.accountBalance }</td>
                  <td>还剩多少钱</td>
                </tr> 
                <tr>
                   <td rowspan="11" class="rowtds">业务情况</td>
                   <td>总用户数(个)</td>
                   <td>${operationalData.userNum }</td>
                   <td>累计app、网站注册用户数、微博微信关注数</td>
                 </tr> 
                <tr>
                  <td>新增用户数(个)</td>
                  <td>${operationalData.userNew }</td>
                  <td>当月新增累积app、网站注册用户数、微博微信关注数</td>
                </tr>
                <tr>
                  <td>月活跃用户数(个)</td>
                  <td>${operationalData.userActiveMonth }</td>
                  <td>当月登陆用户数</td>
                </tr>
                <tr>
                  <td>月均日活跃用户数(个)</td>
                  <td>${operationalData.userActiveDay }</td>
                  <td>日登陆用户数的月平均数</td>
                </tr>
                <tr>
                  <td>购买用户数(个)</td>
                  <td>${operationalData.userBuy }</td>
                  <td>当月产生的购买用户数</td>
                </tr>
                <tr>
                  <td>在线商户数(个)</td>
                  <td>${operationalData.userOnline }</td>
                  <td>当月共有多少提供服务的商户在线、共有多少个SKU，C2C模式填提供服务方</td>
                </tr>
                 <tr>
                  <td>新增商户数(个)</td>
                  <td>${operationalData.businessNew }</td>
                  <td>当月新增商户数量、新增SKU数量，C2C模式填提供服务方</td>
                </tr>
                 <tr>
                  <td>购买商户数(个)</td>
                  <td>${operationalData.businessBuy }</td>
                  <td>有多少商户产生了购买，C2C模式填提供服务方</td>
                </tr>
                 <tr>
                  <td>成交订单数(单)</td>
                  <td>${operationalData.tradeOrders }</td>
                  <td>当月成交订单数</td>
                </tr>
                 <tr>
                  <td>平均成交单价(元)</td>
                  <td>${operationalData.tradeOrderBlance }</td>
                  <td>成交额／成交单量</td>
                </tr>
                 <tr>
                  <td>平均成交客单价(元)</td>
                  <td>${operationalData.tradeUserBlance }</td>
                  <td>成交额／成交人数（商户数）</td>
                </tr>                   
              </tbody>
          </table>  
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
    }
		,{
		  	  field: 'operate', 
		  	  title: '操作', 
		  	  events: fileGrid.operateEvents, 
		  	  formatter: fileGrid.operateFormatter 

		    } /* , {
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
    }*/]
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

