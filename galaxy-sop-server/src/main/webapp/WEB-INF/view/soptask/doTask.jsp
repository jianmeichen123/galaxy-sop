<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<% 
	String path = request.getContextPath(); 
String projectId=(String)request.getAttribute("projectId");
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
</head>

<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin">
    <!-- 面包屑 -->
    <ul class="breadcrumb">
      <li><a href="javascript:;" class="bcfirst">待办任务</a></li>
      <!-- <li class="bottom_align"><span>&gt;</span><a href="javascript:;" class="active">上传文档</a></li> -->
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
                          <dd>投资</dd>
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
                        <dt>项目描述：</dt>
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
                      <th>创建日期 </th>
                      <th>存储类型</th>
                      <th>更新日期 </th>
                      <th>档案状态</th>
                      <th>查看附件</th>
                  </tr>
              </thead>                                                                                                                     
              <tbody>
                  <tr>
                      <td>2016-01-20</td>
                      <td>文档</td>
                      <td>2016-01-20</td>
                      <td>已上传</td>
                      <td><a href="javascript:;">投资意向书.doc</a></td>
                  </tr>
              </tbody>
          </table> 
          <ul>
            <li><a href="javascript:;">下载投资意向书模板</a></li>
            <li><a href="javascript:;">更新投资意向书</a></li>
            <li><a href="javascript:;">上传签署凭证</a></li>
          </ul>
          </div>
        </div>

    </div>
 
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
	$(function(){
		createMenus(11);
	});
</script>
</body>
</html>
