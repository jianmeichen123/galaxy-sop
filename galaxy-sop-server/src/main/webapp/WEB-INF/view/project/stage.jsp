<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<div class="myprojecttc">
	<a href="javascript:;" data-close="close" class="close null">关闭</a>
	<!-- 项目介绍 -->
	<div class="pjt_introduct clearfix">
		<div class="clearfix">
			<h3 id="project_name"></h3>
			<dl class="update">
				<dt>最新内容更新</dt>
				<dd>
					<span>&#40;</span>2016-01-26<span>&#41;</span>
				</dd>
			</dl>
		</div>

		<!-- <ul class="pjt_brf clearfix">
              <li><b class="b4"></b>2016-01-22</li>
              <li>内部评审会</li>
              <li>
                <span class="grey"><b class="b4"></b>会议结论：</span>跟踪
              </li>
            </ul> -->
	</div>

	<div class="tabtable">
		<!-- tab标签 -->
		<ul class="tablink">
			<li data-tab="nav"><a href="javascript:;">基本信息</a></li>
			<li data-tab="nav"><a href="javascript:;">档案库</a></li>
			<li data-tab="nav" id="projectProgress_1"><a href="javascript:;">接触访谈</a></li>
			<li data-tab="nav" id="projectProgress_2"><a href="javascript:;">内部评审</a></li>
			<li data-tab="nav" id="projectProgress_3"><a href="javascript:;">CEO评审</a></li>
			<li data-tab="nav" id="projectProgress_4"><a href="javascript:;">立项会</a></li>
			<li data-tab="nav" id="projectProgress_5"><a href="javascript:;">投资意向书</a></li>
			<li data-tab="nav" id="projectProgress_6"><a href="javascript:;">尽职调查</a></li>
			<li data-tab="nav" id="projectProgress_7"><a href="javascript:;">投决会</a></li>
			<li data-tab="nav" id="projectProgress_8"><a href="javascript:;">投资协议</a></li>
			<li data-tab="nav" id="projectProgress_9"><a href="javascript:;">股权交割</a></li>
			<li data-tab="nav" id="projectProgress"><a href="javascript:;">操作日志</a></li>
		</ul>
		<!-- tab内容 -->
		<div class="tabtable_con tabtable_con_close">
			<!-- 项目基本信息 -->
			<div class="block block2 show" data-tab="con">
				<dl>
					<dt>项目概述</dt>
					<dd class="fctbox">
						<a href="javascript:;" class="ico1 f1">编辑</a><a
							href="javascript:;" class="ico1 f2">查看详情</a>
					</dd>
				</dl>
				<dl>
					<dt>商业模式</dt>
					<dd class="fctbox">
						<a href="javascript:;" class="ico1 f1">编辑</a><a
							href="javascript:;" class="ico1 f2">查看详情</a>
					</dd>
				</dl>
				<dl>
					<dt>项目概述</dt>
					<dd class="fctbox">
						<a href="javascript:;" class="ico1 f1">编辑</a><a
							href="javascript:;" class="ico1 f2">查看详情</a>
					</dd>
				</dl>
				<dl>
					<dt>团队成员</dt>
					<dd class="fctbox">
						<a href="javascript:;" class="ico1 f1">编辑</a><a
							href="javascript:;" class="ico1 f2">查看详情</a>
					</dd>
				</dl>
				<dl>
					<dt>股权结构</dt>
					<dd class="fctbox">
						<a href="javascript:;" class="ico1 f1">编辑</a><a
							href="javascript:;" class="ico1 f2">查看详情</a>
					</dd>
				</dl>
				<dl class="no_border">
					<dt>档案库</dt>
					<dd class="fctbox">
						<a href="javascript:;" class="ico1 f1">编辑</a><a
							href="javascript:;" class="ico1 f2">查看详情</a>
					</dd>
				</dl>
			</div>

			<!-- 档案库信息 -->
			<div class="block" data-tab="con">
				<!-- 查询选项 -->
				<div class="searchbox">
					<div class="show_more">
						<a href="#" class="blue open ico1 f4" data-btn='show'>展开</a> <a
							href="#" class="blue searchbox_hidden hide ico1 f3"
							data-btn='hide'>收起</a>
					</div>
					<div class="default">
						<dl class="fmdl fmmr fmmm">
							<dt>文件来源：</dt>
							<dd>
								<label for=""><input type="radio">不限</label>
							</dd>
							<dd>
								<label for=""><input type="radio">内部</label>
							</dd>
							<dd>
								<label for=""><input type="radio">外部</label>
							</dd>
						</dl>
						<dl class="fmdl fmmr fmmm">
							<dt>业务分类：</dt>
							<dd>
								<select>
									<option>全部</option>
									<option>全部1</option>
									<option>全部2</option>
								</select>
							</dd>
						</dl>
					</div>
					<div class="searchbox_hidden" data-btn='box'>
						<dl class="fmdl fmmr fmmm">
							<dt>文件来源：</dt>
							<dd>
								<label for=""><input type="radio">不限</label>
							</dd>
							<dd>
								<label for=""><input type="radio">缺失</label>
							</dd>
							<dd>
								<label for=""><input type="radio">外部</label>
							</dd>
						</dl>
						<dl class="fmdl fmmr fmmm">
							<dt>档案状态：</dt>
							<dd>
								<label for=""><input type="radio">不限</label>
							</dd>
							<dd>
								<label for=""><input type="radio">缺失</label>
							</dd>
							<dd>
								<label for=""><input type="radio">已上传</label>
							</dd>
							<dd>
								<label for=""><input type="radio">已签署</label>
							</dd>
						</dl>
						<dl class="fmdl fmmr fmmm">
							<dt>存储类型：</dt>
							<dd>
								<label for=""><input type="radio">不限</label>
							</dd>
							<dd>
								<label for=""><input type="radio">文档</label>
							</dd>
							<dd>
								<label for=""><input type="radio">图片</label>
							</dd>
							<dd>
								<label for=""><input type="radio">音视频</label>
							</dd>
						</dl>
						<dl class="fmdl fmmr fmmm clearfix">
							<dt>更新时间：</dt>
							<dd>
								<input type="text" class="txt time" value="2016-01-01" /> <span>至</span>
								<input type="text" class="txt time" value="2016-01-01" />
							</dd>
							<dd>
								<a href="javascript:;" class="pubbtn bluebtn">查询</a>
							</dd>
						</dl>
					</div>

				</div>
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="archivestc.html" data-btn="archives" class="pubbtn fffbtn">上传</a>
					<a href="javascript:;" class="pubbtn fffbtn">发送选中</a>
				</div>

				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th></th>
							<th>文件来源</th>
							<th>起草者</th>
							<th>存储类型</th>
							<th>业务分类</th>
							<th>更新日期</th>
							<th>档案状态</th>
							<th>操作</th>
							<th>附件查看</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="radio" name="document" /></td>
							<td>内部</td>
							<td>投资经理</td>
							<td>文档</td>
							<td>访谈备忘</td>
							<td>2016年1月26日</td>
							<td>缺失</td>
							<td><a href="javascript:; " class="blue">更新</a></td>
							<td><a href="javascript:; " class="blue">文件1.doc</a></td>
						</tr>
						<tr>
							<td><input type="radio" name="document" /></td>
							<td>内部</td>
							<td>投资经理</td>
							<td>文档</td>
							<td>访谈备忘</td>
							<td>2016年1月26日</td>
							<td>已上传</td>
							<td><a href="javascript:; " class="blue">更新</a></td>
							<td><a href="javascript:; " class="blue">文件1.doc</a></td>
						</tr>
						<tr>
							<td><input type="radio" name="document" /></td>
							<td>内部</td>
							<td>投资经理</td>
							<td>文档</td>
							<td>访谈备忘</td>
							<td>2016年1月26日</td>
							<td>已签署</td>
							<td><a href="javascript:; " class="blue">更新</a></td>
							<td><a href="javascript:; " class="blue">文件1.doc</a></td>
						</tr>
					</tbody>
				</table>
				<!--分页-->
				<div class="pagright pagright_m clearfix">
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
			<!-- 接触访谈信息 -->
			<div class="block" data-tab="con" id="projectProgress_1_con">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="#" data-btn="interview" onclick="air();" class="pubbtn fffbtn lpubbtn">添加访谈记录</a>
					<a href="javascript:startReview();" class="pubbtn fffbtn lpubbtn">启动内部评审</a>
				</div>
				<div id="projectProgress_1_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
				</div>
				<table id="projectProgress_1_table"
					data-url="<%=path%>/galaxy/project/progress/queryInterview"
					data-page-list="[3,6,10]" data-toolbar="#projectProgress_1_table_custom-toolbar">
					<thead>
						<tr>
							<th data-field="ftgk" data-align="center">访谈概况</th>
							<th data-field="viewNotes" data-align="center">访谈日志</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<!-- 内部评审信息      2  meetingType:1 -->
			<div class="block" data-tab="con" id="projectProgress_2_con">

				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:addLPH();" class="pubbtn fffbtn lpubbtn">添加会议记录</a>
				</div>
				<div id="projectProgress_2_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name="meetingType" value="meetingType:1">
				</div>
				<!--表格内容-->
				<table id="projectProgress_2_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"
					data-toolbar="#projectProgress_2_table_custom-toolbar" data-page-list="[3,6,10,20]">
					<thead>
						<tr>
							<th data-field="hygk" data-align="center">会议概况</th>
							<th data-field="meetingNotes" data-align="center">会议记要</th>
						</tr>
					</thead>
				</table>
			</div>



			<!-- CEO评审信息   3   meetingType:2-->
			<div class="block" data-tab="con" id="projectProgress_3_con">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:addCEOPS();" class="pubbtn fffbtn lpubbtn">添加会议记录</a> 
					<a href="javascript:lxhpq();" class="pubbtn fffbtn lpubbtn">立项会排期</a>
				</div>
				
				<div id="projectProgress_3_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:2">
				</div>
				<!--表格内容-->
				<table id="projectProgress_3_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[3,6,10,20]"
					data-toolbar="#projectProgress_3_table_custom-toolbar">
					<thead>
						<tr>
							<th data-field="hygk" data-align="center">会议概况</th>
							<th data-field="meetingNotes" data-align="center">会议记要</th>
						</tr>
					</thead>
				</table>

			</div>
			
			
			
			<!-- 立项会信息 -->
			<div class="block" data-tab="con" id="projectProgress_4_con">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:addLXH();" class="pubbtn fffbtn lpubbtn">添加会议记录</a>
				</div>

				<div id="projectProgress_4_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:3">
				</div>
				<!--表格内容-->
				<table id="projectProgress_4_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[3,6,10,20]"
					data-toolbar="#projectProgress_4_table_custom-toolbar">
					<thead>
						<tr>
							<th data-field="hygk" data-align="center">会议概况</th>
							<th data-field="meetingNotes" data-align="center">会议记要</th>
						</tr>
					</thead>
				</table>

			</div>
			
			
			<!-- 投决会信息 -->
			<div class="block" data-tab="con" id="projectProgress_7_con">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:addTJH();" class="pubbtn fffbtn lpubbtn">添加会议记录</a>
				</div>

				<div id="projectProgress_7_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:4">
				</div>
				<!--表格内容-->
				<table id="projectProgress_7_table"
					data-url="<%=path%>/galaxy/project/progress/queryMeet"  data-page-list="[3,6,10,20]"
					data-toolbar="#projectProgress_7_table_custom-toolbar">
					<thead>
						<tr>
							<th data-field="hygk" data-align="center">会议概况</th>
							<th data-field="meetingNotes" data-align="center">会议记要</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<!-- 投资意向书信息 -->
			<div class="block" data-tab="con" id="projectProgress_5_con">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" class="pubbtn fffbtn llpubbtn">下载投资意向书模板</a>
					<a href="javascript:;" class="pubbtn fffbtn lpubbtn">更新投资意向书</a> <a
						href="javascript:;" class="pubbtn fffbtn lpubbtn">上传签署证明</a>
				</div>
				<div class="process clearfix">
					<h2>投资意向书盖章流程</h2>
					<img src="img/process.png" alt="">
				</div>
				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>创建日期</th>
							<th>存储类型</th>
							<th>更新日期</th>
							<th>档案状态</th>
							<th>查看附件</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>2016-01-20</td>
							<td>文档</td>
							<td>2016-01-21</td>
							<td>已上传</td>
							<td><a href="#" class="blue">附件</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 尽职调查信息 -->
			<div class="block" data-tab="con" id="projectProgress_6_con">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" class="pubbtn fffbtn llpubbtn">上传业务尽职调查报告</a>
					<a href="javascript:;" class="pubbtn fffbtn lpubbtn" style="display:none;">申请投决会排期</a>
				</div>
				<div class="process clearfix">
					<h2>业务尽调报告审核流程</h2>
					<img src="<%=path %>/img/process1.png" alt="">
				</div>
				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>业务类别</th>
							<th>更新日期</th>
							<th>经办经理</th>
							<th>存储类型</th>
							<th>档案状态</th>
							<th>催办</th>
							<th>查看附件</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>人事尽职调查报告</td>
							<td>2016-01-21</td>
							<td>人事经理</td>
							<td>文档</td>
							<td>已上传</td>
							<td></td>
							<td><a href="#" class="blue">附件</a></td>
						</tr>
						<tr>
							<td>人事尽职调查报告</td>
							<td>2016-01-21</td>
							<td>人事经理</td>
							<td>文档</td>
							<td>已上传</td>
							<td></td>
							<td><a href="#" class="blue">附件</a></td>
						</tr>
						<tr>
							<td>人事尽职调查报告</td>
							<td>2016-01-21</td>
							<td>人事经理</td>
							<td>文档</td>
							<td>已上传</td>
							<td></td>
							<td><a href="#" class="blue">附件</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 投决会信息 -->
			<div class="block" data-tab="con" id="projectProgress_7_con">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
				<a href="#" onclick="voto();" class="pubbtn fffbtn lpubbtn">添加会议记录</a>
				</div>
				<div id="projectProgress_7_table_custom-toolbar">
					<input type="hidden" name="projectId" value="">
					<input type="hidden" name=meetingType value="meetingType:4">
				</div>	
				<!--表格内容-->
				<table id="projectProgress_7_table" width="100%" cellspacing="0" cellpadding="0" class='table_l'
					data-url="<%=path%>/galaxy/project/progress/queryMeet"
					data-page-list="[3,6,10]" data-toolbar="#projectProgress_7_table_custom-toolbar">
					<thead>
						<tr>
							<th data-field="hygk" data-align="center">会议概况</th>
							<th data-field="meetingNotes" data-align="center">会议纪要</th>
						</tr>
					</thead>
				</table>
			</div>
			<!-- 投资协议 -->
			<div class="block" data-tab="con" id="projectProgress_8_con">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" class="pubbtn fffbtn llpubbtn">下载投资协议模板</a>
					<a href="javascript:;" class="pubbtn fffbtn lpubbtn">上传签署证明</a>
				</div>
				<div class="process clearfix">
					<h2>投资协议盖章流程</h2>
					<img src="img/process2.png" alt="">
				</div>

				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>业务分类</th>
							<th>存储类型</th>
							<th>更新日期</th>
							<th>档案状态</th>
							<th>上传/查看附件</th>
							<th>签署凭证</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>投资协议</td>
							<td>文档</td>
							<td></td>
							<td>缺失</td>
							<td><a href="javascript:; " class="blue">上传</a></td>
							<td><a href="javascript:; " class="blue">上传</a></td>
						</tr>
						<tr>
							<td>投资协议</td>
							<td>文档</td>
							<td>2016-01-20</td>
							<td>缺失</td>
							<td><a href="javascript:; " class="blue">上传</a></td>
							<td><a href="javascript:; " class="blue">上传</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 股权交割信息  -->
			<div class="block" data-tab="con" id="projectProgress_9_con">
				<!--表格内容
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>业务分类</th>
							<th>创建日期</th>
							<th>存储类型</th>
							<th>更新日期</th>
							<th>催办</th>
							<th>查看附件</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>资金拨付凭证</td>
							<td></td>
							<td>文档</td>
							<td></td>
							<td><a href="javascript:; " class="blue">催办</a></td>
							<td><a href="javascript:; " class="blue"></a></td>
						</tr>
						<tr>
							<td>资金拨付凭证</td>
							<td></td>
							<td>文档</td>
							<td></td>
							<td><a href="javascript:; " class="blue">催办</a></td>
							<td><a href="javascript:; " class="blue"></a></td>
						</tr>
						<tr>
							<td>资金拨付凭证</td>
							<td></td>
							<td>文档</td>
							<td></td>
							<td><a href="javascript:; " class="blue">催办</a></td>
							<td><a href="javascript:; " class="blue"></a></td>
						</tr>
					</tbody>
				</table>-->
			</div>
			<!-- 操作日志  -->
			<div class="block" data-tab="con">
				<!--表格内容-->
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>时间</th>
							<th>操作者</th>
							<th>动作</th>
							<th>对象</th>
							<th>项目</th>
							<th>业务</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>2016-01-20</td>
							<td>投资经理</td>
							<td>创建</td>
							<td>人力资源尽职调查报告</td>
							<td>项目名称1</td>
							<td>创建项目</td>
						</tr>
						<tr>
							<td>2016-01-20</td>
							<td>投资经理</td>
							<td>创建</td>
							<td>人力资源尽职调查报告</td>
							<td>项目名称1</td>
							<td>创建项目</td>
						</tr>
						<tr>
							<td>2016-01-20</td>
							<td>投资经理</td>
							<td>创建</td>
							<td>人力资源尽职调查报告</td>
							<td>项目名称1</td>
							<td>创建项目</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

delivery();
function delivery(){
	var id=$("#project_id").val();
	$.ajax({
		url : '/galaxy/project/progress/proFileInfo/'+id,
		data : null,
		async : false,
		type : 'GET',
		dataType : "json",
		contentType:"application/json; charset=UTF-8",
		cache : false,
		error:function(){     
	        alert('error');     
	    }, 
		success : function(data) {
			 var json = eval(data);
			 var dataList=json.entityList;
			 var htmlstart='<table width=\"100%" cellspacing="0" cellpadding="0" >'+
				             '<thead>'+
				                '<tr>'+
				                 '<th>业务分类</th>'+
				                 '<th>创建日期</th>'+
				                 '<th>存储类型</th>'+
				                 '<th>更新日期</th>'+
				                 '<th>催办</th>'+
				                 '<th>查看附件</th>'+
				                 '</tr>'+
				            '</thead>'+                                                                                                                                   
				             '<tbody>';
				for(var p in dataList){
							var typehtml = "";
							if (typeof(dataList[p].fType) == "undefined") { 
								typehtml ='<td></td>';
							}else{
								typehtml = '<td>'+dataList[p].fType+'</td>';
							}
							var handlehtml ="";
							
							if (dataList[p].fileStatus == "fileStatus:1") { 
								handlehtml ='<td><a href="javascript:; " class="blue">催办</a></td>';
							}else{
								handlehtml = '<td>'+dataList[p].fileName+'</td>';
							}
							
							htmlstart +='<tr>'+
							'<td>'+dataList[p].fWorktype+'</td>'+
							'<td>'+dataList[p].createDate+'</td>'+
							typehtml+
							'<td></td>'+
							handlehtml+   
							'<td><a href="javascript:; " class="blue"></a></td>'+   
							'</tr>';   
							
				}
				var htmlend= '</tbody></table>';
				//$("#projectProgress_9_con").html="<h1>test</h1>";
				//$("#delivery").html +=htmlstart+htmlend;
				document.getElementById("projectProgress_9_con").innerHTML +=htmlstart+htmlend;
		}
	});
	
}
</script>