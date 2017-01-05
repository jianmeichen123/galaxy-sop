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
<title>投后运营</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>

<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
</head>
<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<div class="ritmin">
    	<h2>投后项目跟踪</h2>
        <div class="tabtable assessment label_static">
           <!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">投资金额跟踪分析</a></li>
                <li data-tab="nav"><a href="javascript:;">项目退出占比</a></li>
                <li data-tab="nav"><a href="javascript:;">事业部创投项目列表</a></li>
            </ul>
            <!-- tab内容 -->

            <!-- 投资金额跟踪分析部分 -->
            <div class="tabtable_con" data-tab="con" >
            <div class="mask"></div>
            <img src="/sop/img/sy.png" alt="">
              <div class="search_box searchall disabled">
                <dl class="fmdl fmmr clearfix">
                  <dt>投资事业线：</dt>
                  <dd>
                    <select class="disabled">
                      <option>全部</option>
                      <option>互联网医疗</option>
                      <option>企业服务</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fmmr clearfix">
                  <dt>项目类型：</dt>
                  <dd>
                    <select class="disabled">
                      <option>全部</option>
                      <option>创建</option>
                      <option>投资</option>
                    </select>
                  </dd>
                </dl>

                <dl class="fmdl fmmr clearfix">
                  <dt>统计时间：</dt>
                  <dd>
                    <input type="text" class="txt time1 disabled" value="2015-11-01"  />
                    <span>至</span>
                    <input type="text" class="txt time1 disabled" value="2015-11-06"  />
                  </dd>
                  <dd>
                  <a href="javascript:;" class="bluebtn ico tj">统计</a>
                  </dd>
                </dl>
              </div>
               <!--柱状图部分-->
              <div id="container_qytzje" style="min-width:400px"></div>
              <!--表格内容-->
               <table width="100%" cellspacing="0" cellpadding="0" >
                  <thead>
                      <tr>
                          <th>时间</th>
                          <th>投资事业线</th>
                          <th>合伙人</th>
                          <th>项目名称</th>
                          <th>申请时间</th>
                          <th>注资时间</th>
                          <th>融资估值（百万）</th>
                          <th>融资总额（百万）</th>
                          <th>出让股份占比</th>
                          <th>投资总金额（百万）</th>
                          <th>已使用金额</th>
                          <th>剩余金额</th>
                      </tr>                        
                  </thead>                                                                                                                                    
                  <tbody>
                      <tr>
                          <td>2015-11-02</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>互联网教育</td>
                          <td>李氏</td>
                          <td>窝窝团</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>互联网农业</td>
                          <td>王五</td>
                          <td>峰巢天下</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>数字娱乐</td>
                          <td>王月</td>
                          <td>去跑车</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>互联网餐饮</td>
                          <td>戴明明</td>
                          <td>艾格拉斯</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>互联网金融</td>
                          <td>吴昊</td>
                          <td>微网</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>互联网医疗</td>
                          <td>肖邦</td>
                          <td>食乐淘</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-02</td>
                          <td>人工智能</td>
                          <td>周立波</td>
                          <td>西柚买手</td>
                          <td>2015-11-14</td>
                          <td>2015-11-17</td>
                          <td>¥3,500,000</td>
                          <td>¥1,000,000</td>   
                          <td>28.57%</td>   
                          <td>¥2,500,000</td>   
                          <td>¥2,000,000</td>   
                          <td>¥500000</td>   
                      </tr>
                  </tbody>
              </table>
              <!--分页-->
              <div class="pagright clearfix">
                  <ul class="paging clearfix">
                      <li>每页<input type="text" class="txt" value="20"/>条/共<span>9</span>条记录</li>
                      <li class="margin">共1页</li>
                      <li><a href="javascript:;">|&lt;</a></li>
                      <li><a href="javascript:;">&lt;</a></li>
                      <li><a href="javascript:;">&gt;</a></li>
                      <li><a href="javascript:;">&gt;|</a></li>
                      <li class="jump clearfix">
                          第<input type="text" class="txt" value="1"/>页
                          <input type="button" class="btn margin" value="GO">
                      </li>
                  </ul>
              </div>            
            </div>

            <!-- 事业部创投项目列表 -->
            <div class="tabtable_con" data-tab="con" >
            <div class="mask"></div>
            <img src="/sop/img/sy.png" alt="">
			<div class="search_box searchall disabled" id="custom-toolbasr-deptkpi">
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="deptkpi_projectType">
								<option value="">全部</option>
								<option value="projectType:2">创建</option>
								<option value="projectType:1">投资</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目创建时间：</dt>
						<dd>
							<input type="text" class="txt time datepicker" id="deptkpi_sdate1" name="sdate" value="" /> 
							<span>至</span> 
							<input type="text" class="txt time datepicker" id="deptkpi_edate1" name="edate" value="" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj" id="querySearch_teamkpi">统计</a>  <!-- id="querySearch_deptkpi" -->
						</dd>
					</dl>
				</div>
               <!--柱状图部分-->
              <div id="container_xmtczb"></div>
              <!--表格内容-->
               <table width="100%" cellspacing="0" cellpadding="0" class="table_m">
                  <thead>
                      <tr>
                          <th>时间</th>
                          <th>投资事业线</th>
                          <th>合伙人</th>
                          <th>项目名称</th>
                          <th>退出时间</th>
                          <th>退出状态</th>
                          <th>资产额</th>
                      </tr>                        
                  </thead>                                                                                                                                   
                  <tbody>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                      <tr>
                          <td>2015-11-01</td>
                          <td>企业服务</td>
                          <td>张山</td>
                          <td>闪惠</td>
                          <td>2015-11-14</td>
                          <td>自行退出</td>
                          <td>¥3,500,000</td>   
                      </tr>
                  </tbody>
              </table>
              <!--分页-->
              <div class="pagright clearfix">
                  <ul class="paging clearfix">
                      <li>每页<input type="text" class="txt" value="20"/>条/共<span>9</span>条记录</li>
                      <li class="margin">共1页</li>
                      <li><a href="javascript:;">|&lt;</a></li>
                      <li><a href="javascript:;">&lt;</a></li>
                      <li><a href="javascript:;">&gt;</a></li>
                      <li><a href="javascript:;">&gt;|</a></li>
                      <li class="jump clearfix">
                          第<input type="text" class="txt" value="1"/>页
                          <input type="button" class="btn margin" value="GO">
                      </li>
                  </ul>
              </div>            
            </div>
                        <!-- 项目退出占比部分 -->
            <div class="tabtable_con" data-tab="con" >
              <div class="search_box searchall" id="custom-toolbasr-deptProject">
                    <dl class="fmdl fmmr clearfix">
						<dt>投资事业部：</dt>
						<dd>
							<select name="deptid" id="userTrack_deptid">
								<option value="">全部</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>查询时段：</dt>
						<dd>
							<input id="week" type="radio" name="isNullTime"  value="yes"  checked/>全部
							<input id="defined" type="radio"  name="isNullTime" value="no" />注资时间
						</dd>
						
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dd id="weekType">
								<input type="text" class="txt time" readonly id="deptkpi_sdate" name="sdate" value="" /> 
								<span>至</span> 
								<input type="text" class="txt time" readonly id="deptkpi_edate" name="edate" value="" />
							</dd>
							
						   <dd id="definedType" style="display:none">
								<input type="text" class="txt time datepicker" name="sdate" readonly id="deptkpi_sdate" value="" /> 
								<span>至</span> 
								<input type="text" class="txt time datepicker" name="edate" readonly id="deptkpi_edate" value="" />
							</dd>
						<!-- <dd id="weekType">
							<input type="text" class="txt time datepicker" id="deptkpi_sdate" name="sdate" value="" /> 
							<span>至</span> 
							<input type="text" class="txt time datepicker" id="deptkpi_edate" name="edate" value="" />
						</dd> -->
						<dd>
							<a href="javascript:;" class="bluebtn ico tj cx_prj" id="querySearch_depetProject">搜索</a>  <!-- id="querySearch_deptkpi" -->
						</dd>
					</dl>
			   
				</div>
				 <div>
				    <a href="javascript:;" class="bluebtn ico tj" id="ProjectExport">导出</a>
				</div>
              <!--表格内容-->
              <table id="data-table-deptProject"
					width="100%" data-url="project/deptProjectList"  cellspacing="0" cellpadding="0" class="table_m">
					<thead>
						<tr>
							<th data-field="projectName"  	class="data-input" data-formatter="projectNameFormat">项目名称</th>
							<th data-field="projectCompany"  			class="data-input" data-formatter="projectCompanyFormat">公司名称 </th>
							<th data-field="type"  		class="data-input" data-width="6%">项目类型</th>
							<th data-field="projectCareerline"  		class="data-input" data-width="11%">事业部</th>
							<th data-field="financeStatusDs"  		class="data-input sort" data-sortable="true" data-width="8%">融资状态<span></span></th>
							<th data-field="ctime"  		class="data-input"  data-width="6%">注资时间</th>
							<th data-field="finalContribution"  		class="data-input" >投资金额（万元）</th>
						    <th data-field="finalShareRatio"  		class="data-input" data-formatter="finalShareRatioFormat"  data-width="6%">占比（%）</th>
					        <th data-field="financeHistory"  		class="data-input" data-formatter="financeHistoryFormat"  data-width="6%">融资历史</th>
							<th data-field="healthState"  		class="data-input" data-formatter="healthStateFormatter"  data-width="6%">项目现状</th>
						    <th data-field="projectDescribe"  		class="data-input" data-formatter="descriptLineFormat">商业模式</th>
						    <th data-field="projectDescribeFinancing"  		class="data-input" data-formatter="financingFormat" data-width="12%">业务简要概述和项目亮点</th>
 						</tr>
					</thead>
				</table>           
            </div> 	

          </div>
          </div>
          </div>
    
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<!-- table分页 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<!-- highcharts -->
<script src="<%=request.getContextPath() %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/highcharts_ext.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForWeek.js"></script>

<script src="<%=request.getContextPath() %>/js/report/afterInvestTrack.js" type="text/javascript"></script>
<script>
//周报|自定义选择切换
$("#week").on('click',function(){
	$("#weekType").find(':input').attr('data', 'false');
	$("#weekType").show();
	$("#definedType").hide();
});

$("#defined").on('click',function(){
	$("#definedType").find(':input').attr('data', 'false');
	$("#weekType").hide();
	$("#definedType").show();
	setDefineDate("definedType");
});

function setDefineDate(id){
	//表单日期初始化
    var currDate = new Date();
	var sdate = currDate.format("yyyy-MM-dd");
	var edate = currDate.format("yyyy-MM-dd");
	$("#"+id).find("input[name='sdate']").val(sdate);
	$("#"+id).find("input[name='edate']").val(edate);
}

$("#kpiExport").on('click',function(){
// 	window.location.href = platformUrl.exportKpiGrade;
	reportChooseSuffix.init();
});
	
createCareelineOptions(platformUrl.getCareerlineListByRole,"deptid","");
function projectInfo(value,row,index){
    var id=row.id;
	var str=row.projectName;
	console.log(str)
	if(str.length>10){
		subStr = str.substring(0,10);
		var options = '<a href="#" class="blue" data-btn="myproject" onclick="proInfo(' + id + ')" title="'+str+'">'+subStr+'</a>';
		return options;
	}
	else{
		var options = '<a href="#" class="blue" data-btn="myproject" onclick="proInfo(' + id + ')" title="'+str+'">'+str+'</a>';
		return options;
	}
}
function projectNameFormat(value, row, index){
	var str=row.projectName;
	if(str!="" && typeof(str)!="undefined"){
		str=delHtmlTag($.trim(str))
		var str = "<span title='"+str+"'>"+str+"</span>";
	}else{
		str='-'
	}
	return str;
}
function projectCompanyFormat(value, row, index){
	var str=row.projectCompany;
	if(str!="" && typeof(str)!="undefined"){
		str=delHtmlTag($.trim(str))
		var str = "<span title='"+str+"'>"+str+"</span>";
	}else{
		str='-'
	}
	return str;
}
function descriptLineFormat(value, row, index){
	var str=row.projectDescribe;
	if(str!="" && typeof(str)!="undefined"){
		str=delHtmlTag($.trim(str))
		var str = "<span title='"+str+"'>"+str+"</span>";
	}else{
		str='-'
	}
	return str;
}
function financingFormat(value, row, index){
	var str=row.projectDescribeFinancing;
	if(str!="" && typeof(str)!="undefined"){
		str=delHtmlTag($.trim(str))
		var str = "<span title='"+str+"'>"+str+"</span>";
	}else{
		str='-'
	}
	return str;
}
function finalShareRatioFormat(value, row, index){
	var val;
	var finalValuations;
	var serviceCharge;
	if(null!=row.finalValuations&&row.finalValuations!=""){
		finalValuations=row.finalValuations;
	}else{
		finalValuations="-";
	}
	if(null!=row.serviceCharge&&row.serviceCharge!=""){
		serviceCharge=row.serviceCharge;
	}else{
		serviceCharge="-";
	}
	return finalValuations+','+serviceCharge;
}
function financeHistoryFormat(value, row, index){
	
	var options = '<a href="#" class="blue" data-btn="financeHistory" onclick="financeHistory(' + row.id + ')">查看</a>';
	return options;
}
function healthStateFormatter(value, row, index){
	var val;
	var data={
		'0':'初始',	
		'1':'高于',
		'2':'正常',
		'3':'健康预警',
		'4':'清算'
	};
	if(null!=row.healthState&&row.healthState!=""){
		val=data[row.healthState];
	}else{
		val="-";
	}
	return val;
	
}
function financeHistory(id){
	
}
$("#ProjectExport").on('click',function(){
// 	window.location.href = platformUrl.exportKpiGrade;
	reportChooseSuffix.init();
});
</script>