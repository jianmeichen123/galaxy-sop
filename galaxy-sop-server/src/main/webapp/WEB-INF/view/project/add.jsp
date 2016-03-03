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
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    <h2>我的项目</h2>
      <!-- 面包屑 -->
     <!--  <ul class="breadcrumb">
       <li><a href="javascript:;" class="bcfirst">我的项目</a></li>
       <li><span>&gt;</span><a href="javascript:;">项目基本信息</a></li>
       <li><span>&gt;</span><a href="javascript:;" class="active">项目详情信息</a></li>
     </ul> -->
      <div class="clearfix"></div>
        <!--项目详细信息内容-->
        <div class="projectmsg_d clearfix">
          <!-- 第1部分 -->
          <div class="block block1">
            <table width="100%" cellspacing="5" cellpadding="0" >
             <tbody>
                  <tr>
                      <td>
                        <dl>
                          <dt>项目编码：</dt>
                          <dd>10000001</dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>创建时间：</dt>
                          <dd>2016-01-20</dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>项目名称：</dt>
                          <dd><input type="text" value="去哪儿旅游O2O项目"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>项目类型：</dt>
                          <dd>
                            <label><input name="type" type="radio" value="" />内部创建</label>
                            <label><input name="type" type="radio" value="" />外部投资</label>
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>计划额度：</dt>
                          <dd><input type="text" value="500"></dd>
                        </dl>
                      </td>                      <td>
                        <dl>
                          <dt>初始估值：</dt>
                          <dd>1500</dd>
                        </dl>
                      </td>

                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>出让股份：</dt>
                          <dd><input type="text" value="20%"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>单位（万）：</dt>
                          <dd>
                            <label><input name="money" type="radio" value="" />人民币</label>
                            <label><input name="money" type="radio" value="" />美元</label>
                            <label><input name="money" type="radio" value="" />英镑</label>
                            <label><input name="money" type="radio" value="" />欧元</label>
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>公司名称：</dt>
                          <dd><input type="text" value="星河互联"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>组织机构代码：</dt>
                          <dd><input type="text" value="010123908" class="zzjg_txt"></dd>
                        </dl>
                      </td>
                  </tr>                   
                </tbody>
              </table>
              <div class="btnbox">
                <a href="javascript:;" class="pubbtn bluebtn">保存</a>
              </div>
          </div>
          <!-- 第2部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>项目概述</dt>
              <dd class="describe">这里是要详情描述的内容</dd>
              <dd class="edit">这里是要编辑的内容</dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a><a href="javascript:;" class="ico f4" data-btn="submit">保存</a><a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a><a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第3部分 -->
          <div class="block block2">
            <dl>
              <dt>商业模式</dt>
              <dd class="describe">这里是要详情描述的内容</dd>
              <dd class="edit">这里是要编辑的内容</dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a><a href="javascript:;" class="ico f4" data-btn="submit">保存</a><a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a><a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第4部分 -->
          <div class="block block2">
            <dl>
              <dt>公司定位</dt>
              <dd class="describe">
                <img src="img/texttool.png" alt="">行业前景十分美好！行业前景十分美好！行业前景十分美好！行业前景十分美好！行业前景十分美好！行业前景十分美好！行业前景十分美好！行业前景十分美好！行业前景十分美好！行业前景十分美好！投资一家好的公司找到成熟的公司对其投资找到成熟的公司对其投资找到成熟的公司对其投资找到成熟的公司对其投资。</dd>
              <dd class="edit">这里是要编辑的内容</dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a><a href="javascript:;" class="ico f4" data-btn="submit">保存</a><a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a><a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第5部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>用户分析</dt>
              <dd class="describe">这里是要详情描述的内容</dd>
              <dd class="edit">这里是要编辑的内容</dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a><a href="javascript:;" class="ico f4" data-btn="submit">保存</a><a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a><a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第6部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>竞情分析</dt>
              <dd class="describe">这里是要详情描述的内容</dd>
              <dd class="edit">这里是要编辑的内容</dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a><a href="javascript:;" class="ico f4" data-btn="submit">保存</a><a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a><a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第7部分 -->
          <div class="block block2 clearfix">
            <dl>
              <dt>团队成员</dt>
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
                          <td><a class="blue">王小林</a></td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td><a href="tchtml/studyexpricetc.html" data-btn="studyexprice" class="blue">博士</a></td>
                          <td><a href="tchtml/workexpricetc.html" data-btn="workexprice" class="blue">12</a></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><a href="tchtml/resumetc.html" data-btn="resume" class="blue">个人简历</a></td>
                      </tr>
                      <tr>
                          <td><a href="javascript:;" class="blue">王小林</a></td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td><a href="javascript:;" class="blue">博士</a></td>
                          <td><a href="javascript:;" class="blue">12</a></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><a href="javascript:;" class="blue">个人简历</a></td>
                      </tr>
                      <tr>
                          <td><a href="javascript:;" class="blue">王小林</a></td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td><a href="javascript:;" class="blue">博士</a></td>
                          <td><a href="javascript:;" class="blue">12</a></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><a href="javascript:;" class="blue">个人简历</a></td>
                      </tr>
                      <tr>
                          <td><a href="javascript:;" class="blue">王小林</a></td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td><a href="javascript:;" class="blue">博士</a></td>
                          <td><a href="javascript:;" class="blue">12</a></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><a href="javascript:;" class="blue">个人简历</a></td>
                      </tr>
                      <tr>
                          <td><a href="javascript:;" class="blue">王小林</a></td>
                          <td>男</td>
                          <td>33</td>
                          <td>CEO</td>
                          <td>15901454545</td>
                          <td><a href="javascript:;" class="blue">博士</a></td>
                          <td><a href="javascript:;" class="blue">12</a></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><img src="img/star.png" alt="" class="star"></td>
                          <td><a href="javascript:;" class="blue">个人简历</a></td>
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
                      <input type="text" value="2016-01-01" data-date="time"/>
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
    </div> 
</div>
<textarea id="calendar" class="none">
<dl class="calendar">
    <dt>
        <strong></strong>
        <a href="javascript:;" class="btn lft" data-btn="prev">&lt;</a>
        <a href="javascript:;" class="btn rit" data-btn="next">&gt;</a>
    </dt>
    <dd class="week">
        <ul>
            <li><a href="javascript:;">天</a></li>
            <li><a href="javascript:;">一</a></li>
            <li><a href="javascript:;">二</a></li>
            <li><a href="javascript:;">三</a></li>
            <li><a href="javascript:;">四</a></li>
            <li><a href="javascript:;">五</a></li>
            <li><a href="javascript:;">六</a></li>
        </ul>
    </dd>
    <dd>
        <ul date="list"></ul>
    </dd>
</dl>
</textarea>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
	$(function(){
		createMenus(4);
	});
</script>
</html>

