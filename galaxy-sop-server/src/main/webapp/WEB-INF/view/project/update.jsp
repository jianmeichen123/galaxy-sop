<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>繁星SOP-添加项目</title>
	<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<!-- jsp文件头和头部 -->
	<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
	<!-- 富文本编辑器 -->
	<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
    <script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" src="<%=path %>/js/project.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	 <!--右中部内容-->
 	<div class="ritmin">
    <h2>我的项目</h2>
      <!-- 面包屑
      <ul class="breadcrumb">
        <li><a href="javascript:;" class="bcfirst">待办任务</a></li>
        <li><span>&gt;</span><a href="javascript:;">项目基本信息</a></li>
        <li><span>&gt;</span><a href="javascript:;" class="active">项目详情信息</a></li>
      </ul> -->
      <div class="clearfix"></div>
        <!--项目详细信息内容-->
        <div class="projectmsg_d clearfix">
          <h2 id="project_name"></h2>
          <form action="" method="post" id="update_form_basic">
          <input type="hidden" id="pid" name="id" value=""/>
          <!-- 第1部分 -->
          <div class="block block1">
            <table width="100%" cellspacing="5" cellpadding="0" >
             <tbody>
                  <tr>
                      <td><dl><dt>项目编码：</dt><dd id="project_code"></dd></dl></td>
                      <td><dl><dt>创建时间：</dt><dd id="create_date"></dd></dl></td>
                  </tr>
                  <tr>
                      <td><dl><dt>项目名称：</dt><dd id="projectName"></dd></dl></td>
                      <td><dl><dt>项目类型：</dt><dd id="projectType"></dd></dl></td>
                  </tr>
                  <tr><td><dl><dt>计划额度：</dt>
                          <dd><input id="project_contribution" name="projectContribution" type="text" value="" placeholder="计划额度"></dd>
                        </dl></td>
                      <td><dl><dt>初始估值：</dt><dd id="project_valuations"></dd></dl></td>
                  </tr>
                  <tr>
                      <td><dl><dt>出让股份：</dt>
                          <dd><input id="project_share_ratio" name="projectShareRatio" type="text" value="" placeholder="出让股份"></dd>
                        </dl></td>
                      <td>
                        <dl>
                          <dt>单位（万）：</dt>
                          <dd>
                            <label><input id="currencyUnit0" name="currencyUnit" type="radio" value="0" />人民币</label>
                            <label><input id="currencyUnit1" name="currencyUnit" type="radio" value="1" />美元</label>
                            <label><input id="currencyUnit2" name="currencyUnit" type="radio" value="2" />英镑</label>
                            <label><input id="currencyUnit3" name="currencyUnit" type="radio" value="3" />欧元</label>
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>公司名称：</dt>
                          <dd><input type="text" value="" id="project_company" name="projectCompany" placeholder="公司名称"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>组织机构代码：</dt>
                          <dd><input type="text" value="" class="zzjg_txt" name="projectCompanyCode" id="project_company_code" placeholder="组织机构代码"></dd>
                        </dl>
                      </td>
                  </tr>                  
                </tbody>
              </table>
              <div class="btnbox">
                <a href="javascript:update();" class="pubbtn bluebtn">保存</a><!-- <a href="javascript:;" class="pubbtn fffbtn"data-close="close">关闭项目</a> -->
              </div>
          </div>
          </form>
          <!-- 第2部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>项目概述</dt>
              <dd class="edit">
              	  <script type="text/plain" id="describe_editor" style="width:1000px;height:100px;">
				  </script>
			 </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                <a href="javascript:;" id="save_describe" class="ico f4" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第3部分 -->
          <div class="block block2">
            <dl>
              <dt>商业模式</dt>
              <dd class="edit">
              	  <script type="text/plain" id="business_model_editor" style="width:1000px;height:100px;">
				  </script>
		      </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                <a href="javascript:;" id="save_business_model" class="ico f4" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第4部分 -->
          <div class="block block2">
            <dl>
              <dt>公司定位</dt>
              <dd class="edit">
			      <script type="text/plain" id="location_editor" style="width:1000px;height:100px;">
				  </script>
			  </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                <a href="javascript:;" class="ico f4" id="save_location" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第5部分 -->
          <div class="block block2">
            <dl>
              <dt>用户分析</dt>
              <dd class="edit">
			  	  <script type="text/plain" id="portrait_editor" style="width:1000px;height:100px;">
				  </script>
			  </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                <a href="javascript:;" class="ico f4" id="save_portrait" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第6部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>竞情分析</dt>
              <dd class="edit">
			      <script type="text/plain" id="analysis_editor" style="width:1000px;height:100px;">
				  </script>
			  </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                <a href="javascript:;" id="save_analysis" class="ico f4" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第7部分 -->
          <div class="block block2 clearfix">
            <dl>
              <dt>团队成员</dt>
              <dd class="full_w describe clearfix">
              	<div class="btnbox_f">
                  <a href="<%=path %>/galaxy/addperson" data-btn="addmen" class="ico b1">添加</a>
                  <a href="javascript:;" class="ico b2">修改</a>
                  <a href="javascript:;" class="ico b3">删除</a>
                </div>
                <div class="clearfix"></div>
                <table width="100%" cellspacing="0"  cellpadding="0">
                  <thead>
                      <tr>
                          <th>姓名</th>
                          <th>性别</th>
                          <th>年龄</th>
                          <th>当前职务</th>
                          <th>电话</th>
                          <th>最高学历</th>
                          <th>工作年限</th>
                          <th>能力匹配</th>
                          <th>评级</th>
                          <th>操作</th>
                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td>王小林</td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td>博士</td>
                          <td>12</td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td>个人简历</td>
                      </tr>
                      <tr>
                          <td>王小林</td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td>博士</td>
                          <td>12</td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td>个人简历</td>
                      </tr>
                      <tr>
                          <td>王小林</td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td>博士</td>
                          <td>12</td>
                          <td></td>
                          <td></td>
                          <td>个人简历</td>
                      </tr>
                      <tr>
                          <td>王小林</td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td>博士</td>
                          <td>12</td>
                          <td></td>
                          <td></td>
                          <td>个人简历</td>
                      </tr>
                      <tr>
                          <td>王小林</td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td>博士</td>
                          <td>12</td>
                          <td></td>
                          <td></td>
                          <td>个人简历</td>
                      </tr>
                  </tbody>
                </table>
                <ul>
                  <li><a href="javascript:;">首页</a></li>
                  <li><a href="javascript:;">上一页</a><span class="active">/</span><a href="javascript:;" class="active">下一页</a></li>
                  <li><a href="javascript:;" class="active">末页</a></li>
                </ul>
              </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div> 
          <!-- 第8部分  -->      
          <div class="block block2">
            <dl>
              <dt>股权结构</dt>
              <dd class="full_w describe clearfix">
              <div class="btnbox_f">
                  <a href="javascript:;" class="ico b1">添加</a>
                  <a href="javascript:;" class="ico b2">修改</a>
                  <a href="javascript:;" class="ico b3">删除</a>
                </div>
                <div class="clearfix"></div>
                <table width="100%" cellspacing="0"  cellpadding="0">
                  <thead>
                      <tr>
                          <th>类型</th>
                          <th>所有权人</th>
                          <th>占比</th>
                          <th>获取方式</th>
                          <th>备注</th>
                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td>自然人</td>
                          <td>CEO</td>
                          <td>20%</td>
                          <td>创始人</td>
                          <td></td>
                      </tr>
                      <tr>
                          <td>自然人</td>
                          <td>CEO</td>
                          <td>20%</td>
                          <td>创始人</td>
                          <td></td>
                      </tr>
                      <tr>
                          <td>自然人</td>
                          <td>CEO</td>
                          <td>20%</td>
                          <td>创始人</td>
                          <td></td>
                      </tr>
                      <tr>
                          <td>自然人</td>
                          <td>CEO</td>
                          <td>20%</td>
                          <td>创始人</td>
                          <td></td>
                      </tr>
                      <tr>
                          <td>自然人</td>
                          <td>CEO</td>
                          <td>20%</td>
                          <td>创始人</td>
                          <td></td>
                      </tr>
                  </tbody>
                </table>
              </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div> 
          <!-- 第9部分 -->       
          <div class="block block2">
            <dl>
              <dt>档案库</dt>
              <dd class="full_w describe clearfix">
              	<div class="clearfix">
                	<div class="btnbox_f">
                  <a href="javascript:;" class="ico b4">上传</a>
                  <a href="javascript:;" class="ico b5">发送选中</a>
                </div>
                <div class="search tsear clearfix">
                    <a href="javascript:;" class="bluebtn ico cx">查询</a>
                    <div class="searoption">
                      <span>更新时间:</span>
                      <input type="text" value="2016-01-01" data-date="time" class="time"/>
                      <span>至</span>
                      <input type="text" value="2016-05-01" data-date="time"/>
                    </div>
                </div>
                </div>
                <table width="100%" cellspacing="0"  cellpadding="0">
                  <thead>
                      <tr>
                          <th></th>
                          <th>文件来源</th>
                          <th>起草者</th>
                          <th>存储类型</th>
                          <th>业务分类</th>
                          <th>更新日志</th>
                          <th>档案状态</th>
                          <th>操作</th>
                          <th>附件查看</th>
                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td><input type="checkbox" name="checkbox" checked="checked"/></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                      <tr>
                          <td><input type="checkbox" name="checkbox" checked="checked"/></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                      <tr>
                          <td><input type="checkbox" name="checkbox" /></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                      <tr>
                          <td><input type="checkbox" name="checkbox" checked="checked"/></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                      <tr>
                          <td><input type="checkbox" name="checkbox"/></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                  </tbody>
                </table>
                 <ul>
                  <li><a href="javascript:;">首页</a></li>
                  <li><a href="javascript:;">上一页</a><span class="active">/</span><a href="javascript:;" class="active">下一页</a></li>
                  <li><a href="javascript:;" class="active">末页</a></li>
                </ul>
              </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>        
        </div>
        <!-- 关闭按钮 -->
        <a href="javascript:;" class="pubbtn fffbtn close" data-close="close">关闭项目</a>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
</html>