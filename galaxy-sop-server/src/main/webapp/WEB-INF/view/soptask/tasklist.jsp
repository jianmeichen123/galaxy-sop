<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<% 
	String path = request.getContextPath(); 
    java.util.Date date=new java.util.Date();
    System.out.println(date);
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>待办任务</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!--  <script src="<%=path %>/js/soptask.js" type="text/javascript"></script>-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
</head>

<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
    <!--右中部内容-->
 	<div class="ritmin">
    	<h2>待办任务</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--搜索-->
          <div class="searchbox clearfix">
            <input type="text" placeholder="请输入项目名或投资经理名" class="txt"/>
            <a href="javascript:;" class="bluebtn ico cx" id="search">查询</a>
          </div>
            <!--tips连接-->
        	<ul class="tipslink">
            	<li><a href="javascript:;" id="all" >全部<span>(14)</span></a></li>
                <li><a href="javascript:;" id="urgent" >紧急<span>(2)</span></a></li>
                <li><a href="javascript:;" id="normal" >正常<span>(5)</span></a></li>
                <li><a href="javascript:;" id="claim" >待认领<span>(10)</span></a></li>
                <li><a href="javascript:;" id="todeal">待完工<span>(4)</span></a></li>
                <li><a href="javascript:;"id="finish">已完成</a></li>   
                <a href="/galaxy/soptask/goClaimtcPage?id=1" data-btn="claim">认领</a>        
          </ul>
        </div>
        <!--表格内容-->
        <table width="100%" cellspacing="0" cellpadding="0" >
              <thead>
                  <tr>
                      <th>序号</th>
                      <th>优先级</th>
                      <th>剩余时间</th>
                      <th>提交日期</th>
                      <th>任务类型</th>
                      <th>任务名称</th>
                      <th>任务状态</th>
                      <th>所属项目</th>
                      <th>投资经理</th>
                      <th>备注</th>
                      <th>操作</th>
                  </tr>
              </thead >                                                                                                                                    
              <tbody id="tasklist">
           
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
		createMenus(2);
	});
</script>
</html>
