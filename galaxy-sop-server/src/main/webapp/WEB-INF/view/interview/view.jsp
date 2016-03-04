<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>访谈跟进</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/project/progress/interViewAdd" data-btn="interview" class="pubbtn lpubbtn bluebtn ico c4">添加访谈记录</a>
            </div>
        </div>
        <!-- 搜索条件 -->
        <div class="min_document clearfix">
          <div class="bottom searchall clearfix">
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>访谈日期：</dt>
              <dd>
                <input type="text" class="txt time" value="2016-01-01"  />
                <span>至</span>
                <input type="text" class="txt time" value="2016-02-01"  />
              </dd>
            </dl>
            <dl class="fmdl fmdll clearfix">
              <dt></dt>
              <dd>
                <input type="text" class="txt s_txt" placeholder="请输入项目名称或编号" />
              </dd>
              <dd>
               <a href="javascript:;" class="bluebtn ico cx">查询</a>
              </dd>

            </dl>
          </div>
        </div>
        <!--表格内容-->
        <table width="100%" cellspacing="0" cellpadding="0" class='table_l'>
              <thead>
                  <tr>
                      <th>访谈概况</th>
                      <th>所属项目</th>
                      <th>访谈日志</th>
                  </tr>
              </thead>                                                                                                                                    
              <tbody>
                  <tr>
                      <td><em></em>会议日期：<span>2016-01-26</span></td>
                      <td rowspan="3"><em></em><span>食乐淘</span></td>
                      <td rowspan="3"><em></em><span>1.沟通有问题沟通有问题沟通有问题沟通有问题沟通有问题沟通有问题</span><br/><em></em><span>2.公司有水分；</span><br/><em></em><span>3.BP不完善！</span></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>访谈对象：<span>CTO</span></td>
                      <td></td>
                      <td></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>会议录音：<a href="#" class="blue">会议录音.mp3</a></td>
                      <td></td>
                      <td></td>
                  </tr>                  
                  <tr>
                      <td><em></em>会议日期：<span>2016-01-26</span></td>
                      <td rowspan="3"><em></em><span>食乐淘</span></td>
                      <td rowspan="3"><em></em><span>访谈有效，还有三个问题需要解决。</span></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>访谈对象：<span>CTO</span></td>
                      <td></td>
                      <td></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>会议录音：<a href="#" class="blue">会议录音.mp3</a></td>
                      <td></td>
                      <td></td>
                  </tr>                  
                  <tr>
                      <td><em></em>会议日期：<span>2016-01-26</span></td>
                      <td rowspan="3"><em></em><span>食乐淘</span></td>
                      <td rowspan="3"><em></em><span>1.沟通有问题沟通有问题沟通有问题沟通有问题沟通有问题沟通有问题</span><br/><em></em><span>2.公司有水分；</span><br/><em></em><span>3.BP不完善！</span></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>访谈对象：<span>CTO</span></td>
                      <td></td>
                      <td></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>会议录音：<a href="#" class="blue">会议录音.mp3</a></td>
                      <td></td>
                      <td></td>
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


<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>


<script type="text/javascript">
$(function(){
	createMenus(6);


	
	
});
</script>
</html>

