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
<script src="<%=path %>/js/sopinfo.js"></script>
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
                            <a class="pbtn bluebtn h_bluebtn" href="/sop/html/actual_all.html" data-btn="actual_all" data-name='添加总拨款计划'>添加总拨款计划</a>
                        </div>

                    </div>
                    <!-- 搜索条件 -->
                    
                    <div class="min_document fund_list clearfix">
                      <div class="bottom clearfix">
                        <dl class="fmdl fmdll clearfix">
                          <dt>计划拨款金额：</dt>
                          <dd>
                            <input type="text" class="txt"/>
                          </dd>
                          <dd><a href="javascript:;" class="bluebtn ico cx">搜索</a></dd>
                        </dl>
                      </div>
                    </div>  
                    <!-- 创业服务协议 --> 
                    <div class="agreement">
                      <div class="b_agreement clearfix">
                          <div class="b_agreement_l fl">
                            <h3>创业服务协议</h3>
                            <dl>
                              <dt>计划总拨款金额：</dt>
                              <dd>20,000,000</dd>
                            </dl>
                            <dl>
                              <dt>编辑人：</dt>
                              <dd>徐文秀</dd>
                            </dl>
                            <dl>
                              <dt>编辑日期：</dt>
                              <dd>2016-07-18</dd>
                            </dl>
                          </div>
                          <div class="b_agreement_r fr">
                                <button class="pbtn bluebtn" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name='添加分期拨款计划'>添加分期拨款计划</button>
                                <label class="blue" href="/sop/html/actual_all.html" data-btn="actual_all" data-name='编辑总拨款计划'>编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name='提示'>删除</label>
                          </div>
                      </div>                       
                      <!--表格内容-->
                      <table width="100%" cellspacing="0" cellpadding="0" class="commonsize delivery">
                            <thead>
                                <tr>
                                    <th>分拨</th>
                                    <th>拨款时间</th>
                                    <th>计划拨款金额（元）</th>
                                    <th>实际拨款金额（元）</th>
                                    <th>附件数</th>
                                    <th>操作</th>
                                </tr>
                            </thead>                                                                                                                                    
                            <tbody>
                                <tr>
                                    <td><a class="blue" href="/sop/html/actual.html" data-btn="actual" data-name='实际拨款信息列表'>分拨1 </a></td>
                                    <td>完成条款4-15个工作日内</td>
                                    <td>6,000,000</td>
                                    <td>6,000,000</td>
                                    <td>6</td>                                  
                                    <td><label class="blue" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name='编辑分期拨款计划'>编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name='提示'>删除</label><label class="blue noMargin">下载附件</label></td> 
                                </tr>
                                <tr>
                                    <td><a class="blue" href="/sop/html/actual.html" data-btn="actual" data-name='实际拨款信息列表'>分拨1 </a></td>
                                    <td>完成条款4-15个工作日内</td>
                                    <td>6,000,000</td>
                                    <td>6,000,000</td>
                                    <td>6</td>                                  
                                    <td><label class="blue" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name='编辑分期拨款计划'>编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name='提示'>删除</label><label class="blue noMargin">下载附件</label></td> 
                                </tr>
                                                       
                              </tbody>
                        </table>
                    </div>
                  
                    <!-- 追加投资协议 --> 
                    <div class="agreement">
                      <div class="b_agreement clearfix">
                          <div class="b_agreement_l fl">
                            <h3>追加投资协议</h3>
                            <dl>
                              <dt>计划总拨款金额：</dt>
                              <dd>20,000,000</dd>
                            </dl>
                            <dl>
                              <dt>编辑人：</dt>
                              <dd>徐文秀</dd>
                            </dl>
                            <dl>
                              <dt>编辑日期：</dt>
                              <dd>2016-07-18</dd>
                            </dl>
                          </div>
                          <div class="b_agreement_r fr">
                                <button class="pbtn bluebtn" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name='添加分期拨款计划'>添加分期拨款计划</button>
                                <label class="blue" href="/sop/html/actual_all.html" data-btn="actual_all" data-name='编辑总拨款计划'>编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name='提示'>删除</label>
                          </div>
                      </div>                       
                      <!--表格内容-->
                      <table width="100%" cellspacing="0" cellpadding="0" class="commonsize delivery moneyAgreement">
                            <thead>
                                <tr>
                                    <th>分拨</th>
                                    <th>拨款时间</th>
                                    <th>计划拨款金额（元）</th>
                                    <th>实际拨款金额（元）</th>
                                    <th>附件数</th>
                                    <th>操作</th>
                                </tr>
                            </thead>                                                                                                                                    
                            <tbody>
                                <tr>
                                    <td><a class="blue" href="/sop/html/run_digest.html" data-btn="run_digest" data-name='分拨1'>分拨1 </a></td>
                                    <td>完成条款4-15个工作日内</td>
                                    <td>6,000,000</td>
                                    <td>6,000,000</td>
                                    <td>6</td>                                  
                                    <td><label class="blue" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name='编辑分期拨款计划'>编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name='提示'>删除</label><label class="blue noMargin">下载附件</label></td> 
                                </tr>
                                <tr>
                                    <td><a class="blue" href="/sop/html/run_digest.html" data-btn="run_digest" data-name='分拨2'>分拨2 </a></td>
                                    <td>完成条款4-15个工作日内</td>
                                    <td>6,000,000</td>
                                    <td>6,000,000</td>
                                    <td>6</td>                                  
                                    <td><label class="blue" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name='编辑分期拨款计划'>编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name='提示'>删除</label><label class="blue noMargin">下载附件</label></td> 
                                </tr>
                                <tr>
                                    <td><a class="blue" href="/sop/html/run_digest.html" data-btn="run_digest" data-name='分拨3'>分拨3 </a></td>
                                    <td>完成条款4-15个工作日内</td>
                                    <td>6,000,000</td>
                                    <td>6,000,000</td>
                                    <td>6</td>                                  
                                    <td><label class="blue" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name='编辑分期拨款计划'>编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name='提示'>删除</label><label class="blue noMargin">下载附件</label></td> 
                                </tr>                                                       
                              </tbody>
                        </table>
                        <span class="show_more blue fr">显示更多</span><span class="show_hide blue fr">收起</span>
                    </div>
  
        </div>  
                   <!--tab end-->
          </div>
        </div>
        <!--右边-->
           <jsp:include page="includeRight.jsp" flush="true"></jsp:include>
      </div>
 
</div>
<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>
<script>
// 交割前事项js
  $(function(){
		//编辑总拨款计划
		$("[data-btn='actual_all']").on("click",function(){ 
			var $self = $(this);
		//	var _url = $self.attr("href");
		    var _url=platformUrl.toApprActualAll;
			var _name= $self.attr("data-name");
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
					$("#popup_name").html(_name);
				}//模版反回成功执行	
			});
			return false;
		});
		
	  createMenus(5);
     $("#bar").css("width","0px");  //初始化进度条宽度；
    var deliveryComplete=$(".delivery_complete").text();
        deliveryTotal=$(".delivery_total").text();
        Wh=$(".progressBar").width();
    barWidth=parseInt(deliveryComplete/deliveryTotal*Wh)+"px";
    $("#bar").css("width",barWidth)

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

  })
   
</script>
</html>