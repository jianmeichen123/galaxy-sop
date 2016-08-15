<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="../common/header_report.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
<div class="ritmin">
    	<h2>投后企业财报</h2>
        <div class="tabtable assessment label_static">
          <img src="<%=request.getContextPath() %>/img/sy.png" alt="">  <!-- 静态图标识 -->
          <!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">余额表</a></li>
                <li data-tab="nav"><a href="javascript:;">银行流水表</a></li>
                <li data-tab="nav"><a href="javascript:;">企业现金流表</a></li>
                <li data-tab="nav"><a href="javascript:;">企业资产负债表</a></li>
                <li data-tab="nav"><a href="javascript:;">企业利润表</a></li>
            </ul>
            <!-- tab内容 -->

            <!-- 余额表部分 -->
            <div class="mask"></div>
            <div class="tabtable_con" data-tab="con" >
              <div class="search_box searchall disabled">
                <dl class="fmdl fmmr clearfix">
                  <dt>企业名称：</dt>
                  <dd>
                    <select class="disabled">
                      <option>O2O电商</option>
                      <option>互联网医疗</option>
                      <option>企业服务</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fmmr clearfix">
                  <dt>统计时间：</dt>
                  <dd>
                    <input type="text" class="txt time1 disabled" value="2015-12-01"  />
                    <span>至</span>
                    <input type="text" class="txt time1 disabled" value="2015-12-06"  />
                  </dd>
                  <dd>
                    <a href="javascript:;" class="bluebtn ico tj">统计</a>
                  </dd>
                </dl>
              </div>
              <!--表格内容-->
               <table width="100%" cellspacing="0" cellpadding="0" >
                  <thead>
                      <tr>
                          <th>日期</th>
                          <th>科目编码</th>
                          <th>科目名称</th>
                          <th>期初借方</th>
                          <th>期初贷方</th>
                          <th>本期发生借方</th>
                          <th>本期发生贷方</th>
                          <th>期末借方</th>
                          <th>期末贷方</th>
                      </tr>                        
                  </thead>                                                                                                                                    
                  <tbody>
                      <tr>
                          <td>2015.12</td>
                          <td>合计</td>
                          <td></td>
                          <td>¥3,305,352.57</td>
                          <td>¥3,305,352.57</td>
                          <td>¥1,147,347.36</td>
                          <td>¥1,147,347.36</td>
                          <td>¥3,202,677.18</td>   
                          <td>¥3,202,677.18</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>1001</td>
                          <td>库存现金</td>
                          <td>¥3,210.39</td>
                          <td>¥0.00</td>
                          <td>¥0.00</td>
                          <td>¥796.00</td>
                          <td>¥2,414.39</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>1002</td>
                          <td>银行存款</td>
                          <td>¥1,387,187.48</td>
                          <td>¥0.00</td>
                          <td>¥135,332.22</td>
                          <td>¥526,853.37</td>
                          <td>¥995,666.33</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>100201</td>
                          <td>中国银行文慧园支行</td>
                          <td>¥1,385,387.48</td>
                          <td>¥0.00</td>
                          <td>¥135,332.22</td>
                          <td>¥526,853.37</td>
                          <td>¥993,866.33</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>100202</td>
                          <td>中国工商银行北京石门支行</td>
                          <td>¥1,800.00</td>
                          <td>¥0.00</td>
                          <td>¥0.00</td>
                          <td>¥0.00</td>
                          <td>¥1,800.00</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>1012</td>
                          <td>其他货币资金</td>
                          <td>¥103,029.65</td>
                          <td>¥0.00</td>
                          <td>¥0.00</td>
                          <td>¥47,372.60</td>
                          <td>¥55,657.05</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>1122</td>
                          <td>应收账款</td>
                          <td>¥300.00</td>
                          <td>¥0.00</td>
                          <td>¥0.00</td>
                          <td>¥300.00</td>
                          <td>¥0.00</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>112201</td>
                          <td>单位</td>
                          <td>¥300.00</td>
                          <td>¥0.00</td>
                          <td>¥0.00</td>
                          <td>¥300.00</td>
                          <td>¥0.00</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>1123</td>
                          <td>预付账款</td>
                          <td>¥164,857.00</td>
                          <td>¥0.00</td>
                          <td>¥250,300.00</td>
                          <td>¥59,847.00</td>
                          <td>¥355,310.00</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>1001</td>
                          <td>库存现金</td>
                          <td>¥3,210.39</td>
                          <td>¥0.00</td>
                          <td>¥0.00</td>
                          <td>¥796.00</td>
                          <td>¥2,414.39</td>   
                          <td>¥0.00</td>   
                      </tr>
                      <tr>
                          <td>2015.12</td>
                          <td>1001</td>
                          <td>库存现金</td>
                          <td>¥3,210.39</td>
                          <td>¥0.00</td>
                          <td>¥0.00</td>
                          <td>¥796.00</td>
                          <td>¥2,414.39</td>   
                          <td>¥0.00</td>   
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
            <!-- 银行流水部分 -->
            <div class="tabtable_con" data-tab="con" >
              <div class="search_box searchall disabled">
                <dl class="fmdl fmmr clearfix">
                  <dt>企业名称：</dt>
                  <dd>
                    <select class="disabled">
                      <option>O2O电商</option>
                      <option>互联网医疗</option>
                      <option>企业服务</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fmmr clearfix">
                  <dt>统计时间：</dt>
                  <dd>
                    <input type="text" class="txt time1 disabled" value="2015-12-01"  />
                    <span>至</span>
                    <input type="text" class="txt time1 disabled" value="2015-12-06"  />
                  </dd>
                  <dd>
                    <a href="javascript:;" class="bluebtn ico tj">统计</a>
                  </dd>
                </dl>
              </div>
              <!--表格内容-->
               <table width="100%" cellspacing="0" cellpadding="0" >
                  <thead>
                      <tr>
                          <th>交易类型</th>
                          <th>业务类型</th>
                          <th>付款人开户行名</th>
                          <th>付款人账号</th>
                          <th>付款人名称</th>
                          <th>收款人开户行名</th>
                          <th>收款人账号</th>
                          <th>交易日期</th>
                          <th>交易金额</th>
                          <th>交易后余额</th>
                          <th>汇率</th>
                      </tr>                        
                  </thead>                                                                                                                                    
                  <tbody>
                      <tr>
                          <td>往账</td>
                          <td>大额支付</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td>中国民生银行股份有限公司北京奥运村支行</td>
                          <td>0128014170018005</td>
                          <td>2015.12.01</td>   
                          <td>-150,000.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>往账</td>
                          <td>收费</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td></td>
                          <td></td>
                          <td>2015.12.01</td>   
                          <td>-15.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>往账</td>
                          <td>大额支付</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td>中国民生银行股份有限公司北京奥运村支行</td>
                          <td>0128014170018005</td>
                          <td>2015.12.01</td>   
                          <td>-150,000.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>往账</td>
                          <td>大额支付</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td>中国民生银行股份有限公司北京奥运村支行</td>
                          <td>0128014170018005</td>
                          <td>2015.12.01</td>   
                          <td>-150,000.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>来账</td>
                          <td>小额普通</td>
                          <td>招商银行股份有限公司北京西三环支行</td>
                          <td>110907903510801</td>
                          <td>兆讯恒达微电子技术（北京）有限公司</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>2015.12.04</td>   
                          <td>1,300.00</td>   
                          <td>1,158,661.38</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>往账</td>
                          <td>大额支付</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td>中国民生银行股份有限公司北京奥运村支行</td>
                          <td>0128014170018005</td>
                          <td>2015.12.01</td>   
                          <td>-150,000.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>往账</td>
                          <td>大额支付</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td>中国民生银行股份有限公司北京奥运村支行</td>
                          <td>0128014170018005</td>
                          <td>2015.12.01</td>   
                          <td>-150,000.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>往账</td>
                          <td>大额支付</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td>中国民生银行股份有限公司北京奥运村支行</td>
                          <td>0128014170018005</td>
                          <td>2015.12.01</td>   
                          <td>-150,000.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>往账</td>
                          <td>大额支付</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td>中国民生银行股份有限公司北京奥运村支行</td>
                          <td>0128014170018005</td>
                          <td>2015.12.01</td>   
                          <td>-150,000.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
                      </tr>
                      <tr>
                          <td>往账</td>
                          <td>大额支付</td>
                          <td>中国银行北京文慧园支行</td>
                          <td>345461527928</td>
                          <td>北京数字幻想科技有限公司</td>
                          <td>中国民生银行股份有限公司北京奥运村支行</td>
                          <td>0128014170018005</td>
                          <td>2015.12.01</td>   
                          <td>-150,000.00</td>   
                          <td>1,235,387.48</td>   
                          <td>1.000000</td>   
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

            <!-- 企业现金流量表部分 -->
            <div class="tabtable_con" data-tab="con" >
              <div class="search_box searchall disabled">
                <dl class="fmdl fmmr clearfix">
                  <dt>企业名称：</dt>
                  <dd>
                    <select class="disabled">
                      <option>O2O电商</option>
                      <option>互联网医疗</option>
                      <option>企业服务</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fmmr clearfix">
                  <dt>统计时间：</dt>
                  <dd>
                    <input type="text" class="txt time1 disabled" value="2015-12-01"  />
                    <span>至</span>
                    <input type="text" class="txt time1 disabled" value="2015-12-06"  />
                  </dd>
                  <dd>
                    <a href="javascript:;" class="bluebtn ico tj">统计</a>
                  </dd>
                </dl>
              </div>
               <!--柱状图部分-->
              <div id="container_qyxjl"></div>
              <!--表格内容-->
               <table width="100%" cellspacing="0" cellpadding="0" class="table_m">
                  <thead>
                      <tr>
                          <th>时间</th>
                          <th>经营活动净额</th>
                          <th>投资活动净额</th>
                          <th>筹款活动净额</th>
                          <th>其他净额</th>
                      </tr>                        
                  </thead>                                                                                                                                   
                  <tbody>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.11</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
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
            <!-- 资产负债部分 -->
            <div class="tabtable_con" data-tab="con" >
              <div class="search_box searchall disabled">
                <dl class="fmdl fmmr clearfix">
                  <dt>企业名称：</dt>
                  <dd>
                    <select class="disabled">
                      <option>O2O电商</option>
                      <option>互联网医疗</option>
                      <option>企业服务</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fmmr clearfix">
                  <dt>统计时间：</dt>
                  <dd>
                    <input type="text" class="txt time1 disabled" value="2015-12-01"  />
                    <span>至</span>
                    <input type="text" class="txt time1 disabled" value="2015-12-06"  />
                  </dd>
                  <dd>
                    <a href="javascript:;" class="bluebtn ico tj">统计</a>
                  </dd>
                </dl>
              </div>
               <!--柱状图部分-->
              <div id="container_zcfz"></div>                
            </div>
            <!-- 企业利润表部分 -->
            <div class="tabtable_con" data-tab="con" >
              <div class="search_box searchall disabled">
                <dl class="fmdl fmmr clearfix">
                  <dt>企业名称：</dt>
                  <dd>
                    <select class="disabled">
                      <option>O2O电商</option>
                      <option>互联网医疗</option>
                      <option>企业服务</option>
                    </select>
                  </dd>
                </dl>
                <dl class="fmdl fmmr clearfix">
                  <dt>统计时间：</dt>
                  <dd>
                    <input type="text" class="txt time1 disabled" value="2015-12-01"  />
                    <span>至</span>
                    <input type="text" class="txt time1 disabled" value="2015-12-06"  />
                  </dd>
                  <dd>
                    <a href="javascript:;" class="bluebtn ico tj">统计</a>
                  </dd>
                </dl>
              </div>
               <!--柱状图部分-->
              <div id="container_qylr"></div>
              <!--表格内容-->
               <table width="100%" cellspacing="0" cellpadding="0" class="table_m">
                  <thead>
                      <tr>
                          <th>时间</th>
                          <th>营业收入</th>
                          <th>管理费用</th>
                          <th>财务费用</th>
                          <th>营业利润</th>
                          <th>利润总额</th>
                          <th>净利润</th>
                      </tr>                        
                  </thead>                                                                                                                                   
                  <tbody>
                      <tr>
                          <td>2015.01</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.02</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.03</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.04</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.05</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.06</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.07</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.08</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.09</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
                      </tr>
                      <tr>
                          <td>2015.10</td>
                          <td>¥1,565,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,595,537.53</td>
                          <td>¥1,665,537.53</td>
                          <td>¥1,567,537.53</td>
                          <td>¥1,665,537.53</td>
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

          </div>
    </div>
    </div>
 
    
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script src="<%=request.getContextPath() %>/js/report/afterInvestFinace.js" type="text/javascript"></script>