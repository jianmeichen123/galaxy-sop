<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<c:if test="${aclViewProject==true}">
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link href="css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
</head>
<body>

<div class="pagebox clearfix">
    <!--右边内容-->
 	<div class="ritmin">
    	<div class="new_tit_a"><a href="#">工作桌面</a><a href="#">>创投项目</a><a href="#">>少时诵书会</a><a href="#">>运营分析</a><a href="#">>运营数据记录</a>>添加运营数据</div>
    	<div class="new_tit_b">
        	<dl class="fmdl fmmt clearfix">
              <dt>运营数据统计区间：</dt>
              <dd>
                <input type="text" class="datepicker txt time" readonly="" id="startTime" name="startTime" style="height:23px;">
              </dd>
          </dl>
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
         
          <span class="b_span"> 
            <a href="#" class="blue">返回&gt;</a>
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
              <td><input type="text" class="txt new_nputr">个</td>
              <td>当月在职员工数（所有需要开工资的人数）</td>
            </tr>
            <tr>
              <td>分站（店）数量：</td>
              <td><input type="text" class="txt new_nputr">个</td>
              <td>截至当月结束已经开设的分站或分店数量</td>
            </tr>
            <tr>
              <td>产品进度：</td>
              <td><input type="text" class="txt new_nputr">%</td>
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
              <td><input type="text" class="txt new_nputr">元</td>
              <td>当月需要支出的员工工资金额，如9月提交的是8月的工资金额</td>
            </tr>
            <tr>
              <td>房租水电网：</td>
              <td>
                <input type="text" class="txt new_nputr">元
                <select name="" id="" class="new_nputr">
                  <option value="">无</option>
                  <option value="">月付</option>
                  <option value="">季付</option>
                  <option value="">半年付</option>
                  <option value="">年付</option>
                </select>
              </td>
              <td>房租、水电、网络需要支出的金额，如按年或季度付，请标注</td>
            </tr>
            <tr>
              <td>管理费用：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>差旅、办公用品、应酬、快递等费用</td>
            </tr>
             <tr>
              <td>市场费用：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>用于产品宣传推广的支出金额</td>
            </tr>
             <tr>
              <td>业务运营费用：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>用于产品运营的支出费用，如活动、用户激励、促销成本等</td>
            </tr>
             <tr>
              <td>生产成本：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>用于产生产品或服务的“制造”费用，如食材、原料、货价、上游采购等</td>
            </tr>
             <tr>
              <td>交易额：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>当月产生的交易额</td>
            </tr>
             <tr>
              <td>收入：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>当月产生的收入额</td>
            </tr>
             <tr>
              <td>净利润：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>当月进账金额减所有应支付金额</td>
            </tr>
             <tr>
              <td>账面余额：</td>
              <td><input type="text" class="txt new_nputr">元</td>
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
              <td><input type="text" class="txt new_nputr">个</td>
              <td>累计app、网站注册用户数、微博微信关注数</td>
            </tr>
            <tr>
              <td>新增用户数：</td>
              <td><input type="text" class="txt new_nputr">个</td>
              <td>当月新增累积app、网站注册用户数、微博微信关注数</td>
            </tr>
            <tr>
              <td>月活跃用户数：</td>
              <td><input type="text" class="txt new_nputr">个</td>
              <td>当月登陆用户数</td>
            </tr>
            <tr>
              <td>月均日活跃用户数：</td>
              <td><input type="text" class="txt new_nputr">个</td>
              <td>日登陆用户数的月平均数</td>
            </tr>
            <tr>
              <td>购买用户数：</td>
              <td><input type="text" class="txt new_nputr">个</td>
              <td>当月产生的购买用户数</td>
            </tr>
            <tr>
              <td>在线商户数：</td>
              <td><input type="text" class="txt new_nputr">个</td>
              <td>当月共有多少提供服务的商户在线、共有多少个SKU，C2C模式填提供服务方</td>
            </tr>
             <tr>
              <td>新增商户数：</td>
              <td><input type="text" class="txt new_nputr">个</td>
              <td>当月新增商户数量、新增SKU数量，C2C模式填提供服务方</td>
            </tr>
             <tr>
              <td>购买商户数：</td>
              <td><input type="text" class="txt new_nputr">个</td>
              <td>有多少商户产生了购买，C2C模式填提供服务方</td>
            </tr>
             <tr>
              <td>成交订单数：</td>
              <td><input type="text" class="txt new_nputr">单</td>
              <td>当月成交订单数</td>
            </tr>
             <tr>
              <td>平均成交单价：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>成交额／成交单量</td>
            </tr>
             <tr>
              <td>平均成交客单价：</td>
              <td><input type="text" class="txt new_nputr">元</td>
              <td>成交额／成交人数（商户数）</td>
            </tr>
          </table>
        </div>
        <button class="pubbtn bluebtn" >保存</button>
      </div>    
                 
  </div>
<!--右边内容结束-->


</div>

<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script>
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
