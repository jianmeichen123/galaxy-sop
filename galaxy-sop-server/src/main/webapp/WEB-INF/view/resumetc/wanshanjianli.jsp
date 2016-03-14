
<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>待办任务-基本信息</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<script src="<%=path%>/js/jquery-1.11.1.js" type="text/javascript"></script>
<script src="<%=path%>/js/axure.js" type="text/javascript"></script>
<script src="<%=path%>/js/axure_ext.js" type="text/javascript"></script>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
</head>

<body>
<div class="header clearfix">

  <a href="javascript:;" class="logo null">繁星</a>
    <!--头部中间-->
    <div class="min clearfix">
        <!--用户信息-->
        <div class="usermsg clearfix">
            <span class="light_blue">当前您有：</span>
            <a href="javascript:;" class="work">待办任务<em>23</em></a>
            <a href="javascript:;" class="work">紧急任务<em class="bubble">5</em></a>
            <a href="javascript:;" class="work">消息提醒<em>4</em></a> 
        </div>      <!--当日信息-->
      <div class="todaymsg clearfix">
          <span>北京</span>
            <span class="weather1">小雨</span>
            <span>7/-13度；</span>
            <span>今日限行尾号为 5、0，明日为不限行！</span>            
        </div>
    </div>
    <!-- 头部右边 -->
    <div class="usermsg rit clearfix">
        <span class="ico name">早上好，闫皓</span>
        <b class="line null">分割线</b>
        <a href="javascript:;" class="loginout">退出</a>
    </div>
</div>
<div class="pagebox clearfix">
	
    </ul>
    <!--右中部内容-->
 	<div class="ritmin">
    <!-- 面包屑 -->
    <ul class="breadcrumb">
      <li><a href="javascript:;" class="bcfirst">待办任务</a></li>
      <li class="bottom_align"><span>&gt;</span><a href="javascript:;" class="active">上传文档</a></li>
    </ul>
      <div class="clearfix"></div>
        <!--项目基本信息内容-->
        <div class="projectmsg clearfix">
          <h2>去哪儿旅游020</h2>
          <!-- 上半部分 -->
          <div class="top">
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
                          <dt>来源：</dt>
                          <dd>外部投资</dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>项目名称：</dt>
                          <dd>去哪儿旅游项目</dd>
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
                          <dt>投资事业线：</dt>
                          <dd>旅游O2O</dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>合伙人：</dt>
                          <dd>张志成</dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>投资经理：</dt>
                          <dd>唐伟明</dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                    <td colspan="2">
                      <dl>
                        <dt>项目概述：</dt>
                        <dd class="tarea"><a href="javascript:;" class="xmms" title="好的项目，除了模式和市场机会以外，关键是要看其核心团队的能力，经验，和创业的激情与韧性。好的项目，除好的项目，除了模式和市场机会以外，关键是要看其核心团队的能力，经验，和创业的激情与韧性。好的项目好的项目，除了模式和市场机会以外，关键是要看其核心团队的能力，经验，和创业的激情与韧性。好的项目。">好的项目，除了模式和市场机会以外，关键是要看其核心团队的能力，经验，和创业的激情与韧性。好的项目，除好的项目，除了模式和市场机会以外。好的项目，除好的项目，除了模式和市场机会以外。除了模式和市场机会以外的</a></dd>
                      </dl>
                    </td>
                  </tr>
                </tbody>
              </table>
            <a href="javascript:;"  class="pjt_more">项目详细信息&gt;</a>
          </div>
          <!-- 下半部分 -->
          <div class="btm">
            <table width="100%" cellspacing="0" cellpadding="0" >
              <thead>
                  <tr>
                      <th>姓名 </th>
                      <th>性别</th>
                      <th>年龄 </th>
                      <th>当前职务</th>
                      <th>电话</th>
					  <th>操作</th>
                  </tr>
              </thead>                                                                                                                     
              <tbody>
                  <tr>
                      <td>完善简历</td>
                      <td>男</td>
                      <td>21</td>
                      <td>2016-01-20</td>
                      <td>已上传</td>
                      <td><a href="/galaxy/hrjl/resumetcc/"  data-btn="resume" >完善简历</a></td>
                  </tr>
              </tbody>
          </table> 

          </div>
        </div>

    </div>
 
</div>

</body>

</html>
