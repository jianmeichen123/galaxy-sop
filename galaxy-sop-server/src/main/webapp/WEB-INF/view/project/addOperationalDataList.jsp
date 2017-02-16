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
<%@ include file="../common/taglib.jsp"%>
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
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
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
    	<div class="new_tit_a"><a href="#">工作桌面</a><a href="#">>创投项目</a><a href="#">>${projectName }</a><a href="#">>运营分析</a><a href="#">>运营数据记录</a>>添加运营数据</div>
    	<form id="operationData_form" action="" method="post" onsubmit="return false">
    	<input id="projectId" type="hidden" name="projectId" value="${projectId }"/>
    	<input type="hidden" name="operationalDataId" value="${operationalData.id }"/>
    	<div class="new_tit_b">
        	<dl class="fmdl fmmt clearfix">
              <dt>运营数据统计区间：</dt>
              <dd>
               <input type="text" class="datepicker-year-text txt time" readonly="" id="startTime" name="operationIntervalDate" style="height:23px;" value="${operationalData.operationIntervalDate }">
              </dd>
          </dl>
          <dl class="fmdl fmmt clearfix">
              <dd class="clearfix">
                <label><input type="radio" checked="checked" name="dataType" value="0" id="month" <c:if test="${operationalData.dataType == '0'}">checked="checked"</c:if>>月数据</label>
                <label><input type="radio" name="dataType" value="1" id="quarter" <c:if test="${operationalData.dataType == '1'}">checked="checked"</c:if>>季数据</label>
                <select name="dataTypeValue" id="monthData">
                  <option value="">--请选择--</option>
                  <option value="1月">1月</option>
                  <option value="2月">2月</option>
                  <option value="3月">3月</option>
                  <option value="4月">4月</option>
                  <option value="5月">5月</option>
                  <option value="6月">6月</option>
                  <option value="7月">7月</option>
                  <option value="8月">8月</option>
                  <option value="9月">9月</option>
                  <option value="10月">10月</option>
                  <option value="11月">11月</option>
                  <option value="12月">12月</option>
                </select>
                <select name="dataTypeValue" id="quarterData">
                  <option value="">--请选择--</option>
                  <option value="第一季度">第一季度</option>
                  <option value="第二季度">第二季度</option>
                  <option value="第三季度">第三季度</option>
                  <option value="第四季度">第四季度</option>
                </select>
              </dd>
            </dl>
         
          <span class="b_span"> 
            <a href="<%=path %>/galaxy/operationalData/toOperationalDataList/${projectId}" class="blue">返回&gt;</a>
          </span>
      </div>
      <div class="tabtable_con_on operationalInfo">
        <div class="new_r_compile ">
          <span class="new_color size16">基础情况</span>
        </div>
        <div class="new_ul_all new_top_color">
          <table>
            <tr>
              <td>员工人数：</td>
              <td><input type="text" name="employNum" class="txt new_nputr" value="${operationalData.employNum }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>当月在职员工数（所有需要开工资的人数）</td>
            </tr>
            <tr>
              <td>分站（店）数量：</td>
              <td><input type="text" name="branchNum" class="txt new_nputr" value="${operationalData.branchNum }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>截至当月结束已经开设的分站或分店数量</td>
            </tr>
            <tr>
              <td>产品进度：</td>
              <td><input type="text" name="formatProductProcess" class="txt new_nputr" value="${operationalData.productProcess }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,2}\.\d{0,2}))|([1-9](\d{0,1}))|100)$" msg="<font color=red>*</font>0到100之间的两位小数">%</td>
              <td>产品的开发进度，以正式上线为100%</td>
            </tr>
          </table>
        </div>
        <div class="new_r_compile ">
          <span class="new_color size16">财务情况</span>
        </div>
        <div class="new_ul_all new_top_color">
          <table>
            <tr>
              <td>员工工资：</td>
              <td><input type="text" name="formatSalary" class="txt new_nputr" value="${operationalData.salary }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>当月需要支出的员工工资金额，如9月提交的是8月的工资金额</td>
            </tr>
            <tr>
              <td>房租水电网：</td>
              <td>
                <input type="text" name="formatOtherCoat" class="txt new_nputr" value="${operationalData.otherCoat }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元
                <select name="payType" id="" class="new_nputr">
                  <option value="">无</option>
                  <option value="月付">月付</option>
                  <option value="季付">季付</option>
                  <option value="半年付">半年付</option>
                  <option value="年付">年付</option>
                </select>
              </td>
              <td>房租、水电、网络需要支出的金额，如按年或季度付，请标注</td>
            </tr>
            <tr>
              <td>管理费用：</td>
              <td><input type="text" name="formatManageCost" class="txt new_nputr" value="${operationalData.manageCost }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>差旅、办公用品、应酬、快递等费用</td>
            </tr>
             <tr>
              <td>市场费用：</td>
              <td><input type="text" name="formatMarketCost" class="txt new_nputr" value="${operationalData.marketCost }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>用于产品宣传推广的支出金额</td>
            </tr>
             <tr>
              <td>业务运营费用：</td>
              <td><input type="text" name="formatOperatingCost" class="txt new_nputr" value="${operationalData.operatingCost }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>用于产品运营的支出费用，如活动、用户激励、促销成本等</td>
            </tr>
             <tr>
              <td>生产成本：</td>
              <td><input type="text" name="formatProductionCost" class="txt new_nputr" value="${operationalData.productionCost }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>用于产生产品或服务的“制造”费用，如食材、原料、货价、上游采购等</td>
            </tr>
             <tr>
              <td>交易额：</td>
              <td><input type="text" name="formatTradeCost" class="txt new_nputr" value="${operationalData.tradeCost }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>当月产生的交易额</td>
            </tr>
             <tr>
              <td>收入：</td>
              <td><input type="text" name="formatIncomeCost" class="txt new_nputr" value="${operationalData.incomeCost }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>当月产生的收入额</td>
            </tr>
             <tr>
              <td>净利润：</td>
              <td><input type="text" name="formatProfitCost" class="txt new_nputr" value="${operationalData.profitCost }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>当月进账金额减所有应支付金额</td>
            </tr>
             <tr>
              <td>账面余额：</td>
              <td><input type="text" name="formatAccountBalance" class="txt new_nputr" value="${operationalData.accountBalance }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>还剩多少钱</td>
            </tr>
          </table>
        </div>
        <div class="new_r_compile ">
          <span class="new_color size16">业务情况</span>
        </div>
        <div class="new_ul_all new_top_color">
          <table>
            <tr>
              <td>总用户数：</td>
              <td><input type="text" name="userNum" class="txt new_nputr" value="${operationalData.userNum }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>累计app、网站注册用户数、微博微信关注数</td>
            </tr>
            <tr>
              <td>新增用户数：</td>
              <td><input type="text" name="userNew" class="txt new_nputr" value="${operationalData.userNew }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>当月新增累积app、网站注册用户数、微博微信关注数</td>
            </tr>
            <tr>
              <td>月活跃用户数：</td>
              <td><input type="text" name="userActiveMonth" class="txt new_nputr" value="${operationalData.userActiveMonth }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>当月登陆用户数</td>
            </tr>
            <tr>
              <td>月均日活跃用户数：</td>
              <td><input type="text" name="userActiveDay" class="txt new_nputr" value="${operationalData.userActiveDay }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>日登陆用户数的月平均数</td>
            </tr>
            <tr>
              <td>购买用户数：</td>
              <td><input type="text" name="userBuy" class="txt new_nputr" value="${operationalData.userBuy }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>当月产生的购买用户数</td>
            </tr>
            <tr>
              <td>在线商户数：</td>
              <td><input type="text" name="userOnline" class="txt new_nputr" value="${operationalData.userOnline }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>当月共有多少提供服务的商户在线、共有多少个SKU，C2C模式填提供服务方</td>
            </tr>
             <tr>
              <td>新增商户数：</td>
              <td><input type="text" name="businessNew" class="txt new_nputr" value="${operationalData.businessNew }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>当月新增商户数量、新增SKU数量，C2C模式填提供服务方</td>
            </tr>
             <tr>
              <td>购买商户数：</td>
              <td><input type="text" name="businessBuy" class="txt new_nputr" value="${operationalData.businessBuy }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">个</td>
              <td>有多少商户产生了购买，C2C模式填提供服务方</td>
            </tr>
             <tr>
              <td>成交订单数：</td>
              <td><input type="text" name="tradeOrders" class="txt new_nputr" value="${operationalData.tradeOrders }" allowNULL="yes" valType="OTHER" regString="^([1-9](\d{0,9}))$" msg="<font color=red>*</font>0到9999999999之间的整数">单</td>
              <td>当月成交订单数</td>
            </tr>
             <tr>
              <td>平均成交单价：</td>
              <td><input type="text" name="formatTradeOrderBlance" class="txt new_nputr" value="${operationalData.tradeOrderBlance }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>成交额／成交单量</td>
            </tr>
             <tr>
              <td>平均成交客单价：</td>
              <td><input type="text" name="formatTradeUserBlance" class="txt new_nputr" value="${operationalData.tradeUserBlance }" allowNULL="yes" valType="OTHER" regString="^(([0-9](?:\d{0,9}\.\d{0,2}))|([1-9](\d{0,9})))$" msg="<font color=red>*</font>0到9999999999.99之间的两位小数">元</td>
              <td>成交额／成交人数（商户数）</td>
            </tr>
          </table>
        </div>
       <!--  <span class="pubbtn bluebtn" onclick="saveOperationData();">保存</span> -->
        <button class="pubbtn bluebtn" onclick="saveOperationData();">保存</button>
        </form>
      </div>     
    </div>
</div>

<!-- 校验 -->
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=path %>/js/validate/lib/jq.validate.js'></script>



<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
</body>
<script>
if(!'${operationalData.operationIntervalDate}'){
	$('input[name="operationIntervalDate"]').val(new Date().format("yyyy"));
}

var projectId = $("#projectId").val();
var dataTypeValue = '${operationalData.dataTypeValue}';
var payType = '${operationalData.payType}';
$(" select option[value='"+dataTypeValue+"']").attr("selected",true);
$(" select option[value='"+payType+"']").attr("selected",true);
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
  
function saveOperationData(){
	var projectId = $("#projectId").val();
	
	var dataMonth = $("#monthData").val();
	var dataQuarter = $("#quarterData").val();
	
	if(dataMonth == '' && dataQuarter == ''){
		layer.msg("请选择数据类型!");
		return;
	}
	
	if(projectId != ''){
		if(beforeSubmit()){
			sendPostRequestByJsonObj(platformUrl.addOperationData, JSON.parse($("#operationData_form").serializeObjectIsNotNull()), function(data){if(data.result.status == "OK"){
	               window.location.href='<%=path %>/galaxy/operationalData/toOperationalDataList/'+projectId;
			}});
		}
	}
	
}
//保存成功回调
function saveOperationDataCallBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		alert(data.result.message);
	}
}
</script>



</html>

