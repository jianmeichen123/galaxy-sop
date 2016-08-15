<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="../common/header_report.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
	<div class="ritmin">
		<h2>投后业务运营</h2>
		<div class="tabtable assessment label_static">
			<img src="<%=request.getContextPath()%>/img/sy.png" alt="">
			<!-- 静态图标识 -->
			<!-- tab标签 -->
			<ul class="tablink">
				<li data-tab="nav"><a href="javascript:;">企业交易走势</a></li>
				<li data-tab="nav"><a href="javascript:;">企业用户分析</a></li>
			</ul>
			<!-- tab内容 -->

			<!-- 投资金额跟踪分析部分 -->
			<div class="tabtable_con" data-tab="con">
				<div class="mask"></div>
				<div class="search_box searchall disabled">
					<dl class="fmdl fmmr clearfix">
						<dt>企业名称：</dt>
						<dd>
							<select class="disabled">
								<option>全部</option>
								<option>互联网医疗</option>
								<option>企业服务</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>指标项：</dt>
						<dd>
							<select class="disabled">
								<option>笔数</option>
								<option>交易额</option>
								<option>平均交易额</option>
								<option>毛利率</option>
								<option>成功支付笔数</option>
							</select>
						</dd>
					</dl>

					<dl class="fmdl fmmr clearfix">
						<dt>统计时间：</dt>
						<dd>
							<label><input type="radio" name="time">周</label> <label><input
								type="radio" name="time">月</label> <label><input
								type="radio" name="time">季</label>
						</dd>
						<dd>
							<input type="text" class="txt time1 disabled" value="2015-12-01" /> <span>至</span>
							<input type="text" class="txt time1 disabled" value="2015-12-06" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj">统计</a>
						</dd>
					</dl>
				</div>
				<!--柱状图部分-->
				<div id="container_jdjy"></div>
				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>时间</th>
							<th>投资事业线</th>
							<th>企业名称</th>
							<th>项目名称</th>
							<th>交易笔数</th>
							<th>成功交易笔数</th>
							<th>交易金额</th>
							<th>毛利额</th>
							<th>平均交易金额</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>2015-11-02</td>
							<td>企业服务</td>
							<td>闪惠</td>
							<td>闪惠</td>
							<td>2500</td>
							<td>2500</td>
							<td>¥3,500,000.00</td>
							<td>¥3,500,000.00</td>
							<td>¥3,500,000.00</td>
						</tr>
						<tr>
							<td>2015-11-03</td>
							<td>互联网教育</td>
							<td>窝窝团</td>
							<td>窝窝团</td>
							<td>2620</td>
							<td>2620</td>
							<td>¥4,000,000.00</td>
							<td>¥4,000,000.00</td>
							<td>¥4,000,000.00</td>
						</tr>
						<tr>
							<td>2015-11-04</td>
							<td>互联网农业</td>
							<td>峰巢天下</td>
							<td>峰巢天下</td>
							<td>2740</td>
							<td>2740</td>
							<td>¥4,500,000.00</td>
							<td>¥4,500,000.00</td>
							<td>¥4,500,000.00</td>
						</tr>
						<tr>
							<td>2015-11-05</td>
							<td>数字娱乐</td>
							<td>去跑车</td>
							<td>去跑车</td>
							<td>2860</td>
							<td>2860</td>
							<td>¥5,000,000.00</td>
							<td>¥5,000,000.00</td>
							<td>¥5,000,000.00</td>
						</tr>
						<tr>
							<td>2015-11-06</td>
							<td>互联网餐饮</td>
							<td>艾格拉斯</td>
							<td>艾格拉斯</td>
							<td>2980</td>
							<td>2980</td>
							<td>¥5,500,000.00</td>
							<td>¥5,500,000.00</td>
							<td>¥5,500,000.00</td>
						</tr>
						<tr>
							<td>2015-11-07</td>
							<td>互联网金融</td>
							<td>微网</td>
							<td>微网</td>
							<td>3100</td>
							<td>3100</td>
							<td>¥6,000,000.00</td>
							<td>¥6,000,000.00</td>
							<td>¥6,000,000.00</td>
						</tr>
						<tr>
							<td>2015-11-08</td>
							<td>互联网医疗</td>
							<td>食乐淘</td>
							<td>食乐淘</td>
							<td>3220</td>
							<td>3220</td>
							<td>¥6,500,000.00</td>
							<td>¥6,500,000.00</td>
							<td>¥6,500,000.00</td>
						</tr>
						<tr>
							<td>2015-11-09</td>
							<td>人工智能</td>
							<td>西柚买手</td>
							<td>西柚买手</td>
							<td>3340</td>
							<td>3340</td>
							<td>¥7,000,000.00</td>
							<td>¥7,000,000.00</td>
							<td>¥7,000,000.00</td>
						</tr>
						<tr>
							<td>2015-11-10</td>
							<td>O2O电商</td>
							<td>唇味</td>
							<td>唇味</td>
							<td>3460</td>
							<td>3460</td>
							<td>¥7,500,000.00</td>
							<td>¥7,500,000.00</td>
							<td>¥7,500,000.00</td>
						</tr>
						<tr>
							<td>2015-11-11</td>
							<td>互联网旅游</td>
							<td>我家大厨</td>
							<td>我家大厨</td>
							<td>3580</td>
							<td>3580</td>
							<td>¥8,000,000.00</td>
							<td>¥8,000,000.00</td>
							<td>¥8,000,000.00</td>
						</tr>
					</tbody>
				</table>
				<!--分页-->
				<div class="pagright clearfix">
					<ul class="paging clearfix">
						<li>每页<input type="text" class="txt" value="20" />条/共<span>9</span>条记录
						</li>
						<li class="margin">共1页</li>
						<li><a href="javascript:;">|&lt;</a></li>
						<li><a href="javascript:;">&lt;</a></li>
						<li><a href="javascript:;">&gt;</a></li>
						<li><a href="javascript:;">&gt;|</a></li>
						<li class="jump clearfix">第<input type="text" class="txt"
							value="1" />页 <input type="button" class="btn margin" value="GO">
						</li>
					</ul>
				</div>
			</div>

			<!-- 项目退出占比部分 -->
			<div class="tabtable_con" data-tab="con">
				<div class="search_box searchall disabled">
					<div class="mask1"></div>
					<dl class="fmdl fmmr clearfix">
						<dt>企业名称：</dt>
						<dd>
							<select class="disabled">
								<option>全部</option>
								<option>互联网医疗</option>
								<option>企业服务</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>统计时间：</dt>
						<dd>
							<input type="text" class="txt time1 disabled" value="2015-12-01" /> <span>至</span>
							<input type="text" class="txt time1 disabled" value="2015-12-06" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj">统计</a>
						</dd>
					</dl>
				</div>
				<!--柱状图部分-->
				<div class="chartbox">
					<div class="chartbox_top">
						<div class="chart_list clearfix">
							<h2 class="chart_name">昨日关键指标</h2>
							<dl>
								<dt style="color:#62d1b0">新增用户数</dt>
								<dd class="orange" style="color:#62d1b0">2.057</dd>
								<dd>
									<ul>
										<li>日<span class="ico d1">21.60%</span></li>
										<li>周<span class="ico d1">15.90%</span></li>
										<li>月<span class="ico d1">1704.40%</span></li>
									</ul>
								</dd>
							</dl>
							<dl>
								<dt style="color:#ff9d8a">活跃用户数</dt>
								<dd class="orange" style="color:#ff9d8a">1.741</dd>
								<dd>
									<ul>
										<li>日<span class="ico d1">26.30%</span></li>
										<li>周<span class="ico d1">19.50%</span></li>
										<li>月<span class="ico d1">1641.00%</span></li>
									</ul>
								</dd>
							</dl>
							<dl>
								<dt style="color:#ea5c77">流失用户数</dt>
								<dd class="orange" style="color:#ea5c77">316</dd>
								<dd>
									<ul>
										<li>日<span class="ico d1">1.00%</span></li>
										<li>周<span class="ico d2">1.30%</span></li>
										<li>月<span class="ico d1">2157.10%</span></li>
									</ul>
								</dd>
							</dl>
							<dl class="no_border">
								<dt style="color:#ff9d8a">累计用户总量</dt>
								<dd class="orange"  style="color:#ff9d8a">29,575</dd>
								<dd>
									<ul>
										<li>日<span class="ico d1">8.50%</span></li>
										<li>周<span class="ico d1">49.20%</span></li>
										<li>月<span class="ico d1">158.00%</span></li>
									</ul>
								</dd>
							</dl>
						</div>
					</div>
					<div class="chartbox_bottom">
						<img src="<%=request.getContextPath()%>/img/sy.png" alt="">
						<h2 class="chart_name" style="margin-bottom:10px;">关键指标详解</h2>
						<ul class='tablink tablink_business' style="width:100%;margin-bottom:15px;">
							<li data-tab="nav1">活跃用户数</li>
							<li data-tab="nav1">新增用户数</li>
							<li data-tab="nav1">流失用户数</li>
							<li data-tab="nav1">累计用户数</li>
						</ul>
						<!-- 活跃用户数tab部分 -->
						<div class="mask"></div>
						<div class="tabtable" data-tab="con1">
							<div class="tabtable_top disabled">
								<select class="disabled">
									<option>月</option>
									<option>日</option>
									<option>周</option>
									<option>季</option>
									<option>年</option>
								</select> <a href="javascript" class="pubbtn bluebtn">按时间对比</a>
							</div>
							<div id="container_hyyhs"></div>
						</div>
						<!-- 新增用户数tab部分 -->
						<div class="tabtable" data-tab="con1">
							<div class="tabtable_top disabled">
								<select class="disabled">
									<option>月</option>
									<option>日</option>
									<option>周</option>
									<option>季</option>
									<option>年</option>
								</select> <a href="javascript" class="pubbtn bluebtn">按时间对比</a>
							</div>
							<div id="container_xzyhs"></div>
						</div>
						<!-- 流失用户数tab部分 -->
						<div class="tabtable" data-tab="con1">
							<div class="tabtable_top disabled">
								<select class="disabled">
									<option>月</option>
									<option>日</option>
									<option>周</option>
									<option>季</option>
									<option>年</option>
								</select> <a href="javascript" class="pubbtn bluebtn">按时间对比</a>
							</div>
							<div id="container_lsyhs"></div>
						</div>
						<!-- 累计用户数tab部分 -->
						<div class="tabtable" data-tab="con1">
							<div class="tabtable_top disabled">
								<select class="disabled">
									<option>月</option>
									<option>日</option>
									<option>周</option>
									<option>季</option>
									<option>年</option>
								</select> <a href="javascript" class="pubbtn bluebtn">按时间对比</a>
							</div>
							<div id="container_total"></div>
						</div>
					</div>
				</div>
				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0" class="table_m">
					<thead>
						<tr>
							<th>时间</th>
							<th>活跃用户数</th>
							<th>新增用户数</th>
							<th>流失用户数</th>
							<th>累计用户数</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
						<tr>
							<td>2015-12-01</td>
							<td>300</td>
							<td>100</td>
							<td>50</td>
							<td>600</td>
						</tr>
					</tbody>
				</table>
				<!--分页-->
				<div class="pagright clearfix">
					<ul class="paging clearfix">
						<li>每页<input type="text" class="txt" value="20" />条/共<span>9</span>条记录
						</li>
						<li class="margin">共1页</li>
						<li><a href="javascript:;">|&lt;</a></li>
						<li><a href="javascript:;">&lt;</a></li>
						<li><a href="javascript:;">&gt;</a></li>
						<li><a href="javascript:;">&gt;|</a></li>
						<li class="jump clearfix">第<input type="text" class="txt"
							value="1" />页 <input type="button" class="btn margin" value="GO">
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script src="<%=request.getContextPath()%>/js/report/afterInvestBusiness.js"
	type="text/javascript"></script>
