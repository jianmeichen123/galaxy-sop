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
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
   <script src="<%=path %>/js/init.js"></script>
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
    	    <div class="new_tit_a"><a href="<%=path %>/galaxy/index">工作桌面</a><a href="#">>创投项目</a><a href="#">>${projectName }</a><a href="#">>运营分析</a><a href="#">>运营数据记录</a>>查看运营数据</div>
    	<div class="new_tit_b">
        	<dl class="fmdl fmmt clearfix">
              <dt>运营数据统计区间：</dt>
              <dd>
                ${operationalData.operationIntervalDate }年${operationalData.dataTypeValue }
              </dd>
          </dl>
          <span class="b_span"> 
            <%-- <c:if test="${fx:hasRole(4)}"><a href="<%=path %>/galaxy/operationalData/editOperationalDataList/${operationalData.id}" class="blue edit_operation">编辑</a></c:if>
             --%>
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
                   <td id="productProcess"><fmt:formatNumber value="${operationalData.productProcess}" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                   <td>产品的开发进度，以正式上线为100%</td>
                 </tr>
                 <tr>
                   <td rowspan="10" class="rowtds">财务情况</td>
                   <td>员工工资(元)</td>
                   <td><fmt:formatNumber value="${operationalData.salary }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                   <td>当月需要支出的员工工资金额，如9月提交的是8月的工资金额</td>
                 </tr>
                 <tr>
                   <td>房租水电网(元)</td>
                   <td><fmt:formatNumber value="${operationalData.otherCoat }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber> <c:if test="${not empty operationalData.payType}">${operationalData.payType }</c:if><c:if test="${empty operationalData.payType}">无</c:if></td>
                   <td>房租、水电、网络需要支出的金额，如按年或季度付，请标注</td>
                 </tr>  
                 <tr>
                   <td>管理费用(元)</td>
                   <td><fmt:formatNumber value="${operationalData.manageCost }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                   <td>差旅、办公用品、应酬、快递等费用</td>
                 </tr> 
                <tr>
                  <td>市场费用(元)</td>
                  <td><fmt:formatNumber value="${operationalData.marketCost }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                  <td>用于产品宣传推广的支出金额</td>
                </tr>
                 <tr>
                  <td>业务运营费用(元)</td>
                  <td><fmt:formatNumber value="${operationalData.operatingCost }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                  <td>用于产品运营的支出费用，如活动、用户激励、促销成本等</td>
                </tr>
                 <tr>
                  <td>生产成本(元)</td>
                  <td><fmt:formatNumber value="${operationalData.productionCost }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                  <td>用于产生产品或服务的“制造”费用，如食材、原料、货价、上游采购等</td>
                </tr>
                 <tr>
                  <td>交易额(元)</td>
                  <td><fmt:formatNumber value="${operationalData.tradeCost }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                  <td>当月产生的交易额</td>
                </tr>
                 <tr>
                  <td>收入(元)</td>
                  <td><fmt:formatNumber value="${operationalData.incomeCost }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                  <td>当月产生的收入额</td>
                </tr>
                 <tr>
                  <td>净利润(元)</td>
                  <td><fmt:formatNumber value="${operationalData.profitCost }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                  <td>当月进账金额减所有应支付金额</td>
                </tr>
                 <tr>
                  <td>账面余额(元)</td>
                  <td><fmt:formatNumber value="${operationalData.accountBalance }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
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
                  <td>当月登录用户数</td>
                </tr>
                <tr>
                  <td>月均日活跃用户数(个)</td>
                  <td>${operationalData.userActiveDay }</td>
                  <td>日登录用户数的月平均数</td>
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
                  <td><fmt:formatNumber value="${operationalData.tradeOrderBlance }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                  <td>成交额／成交单量</td>
                </tr>
                 <tr>
                  <td>平均成交客单价(元)</td>
                  <td><fmt:formatNumber value="${operationalData.tradeUserBlance }" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber></td>
                  <td>成交额／成交人数（商户数）</td>
                </tr>                   
              </tbody>
          </table>  
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
</body>
<script type="text/javascript">
var transferingIds = "${fx:getTransferingPids()}".split(",");
</script>
<script>
var productProcess = '${operationalData.productProcess }';
if(productProcess){
	var math = Math.round(productProcess)+".0";
	if(math == productProcess){
		//整数
		$("#productProcess").text(Math.round(productProcess));
	}
}
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
    createMenus(14);
  })
</script>



</html>

