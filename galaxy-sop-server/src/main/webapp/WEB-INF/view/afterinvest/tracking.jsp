<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="../common/header_report.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<div class="ritmin">
    	<h2>投后项目跟踪</h2>
        <div class="tabtable assessment label_static">
          <img src="<%=request.getContextPath() %>/img/sy.png" alt="">  <!-- 静态图标识 -->
          <!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">投资金额跟踪分析</a></li>
                <li data-tab="nav"><a href="javascript:;">项目退出占比</a></li>
            </ul>
            <!-- tab内容 -->

            <!-- 投资金额跟踪分析部分 -->
            <div class="mask"></div>
            <div class="tabtable_con" data-tab="con" >
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

            <!-- 项目退出占比部分 -->
            <div class="tabtable_con" data-tab="con" >
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

          </div>
          </div>
          </div>
    
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script src="<%=request.getContextPath() %>/js/report/afterInvestTrack.js" type="text/javascript"></script>