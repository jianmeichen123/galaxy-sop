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
<%-- <script src="<%=path %>/js/axure_ext.js" type="text/javascript" ></script> --%>

<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<script src="<%=path %>/js/sopFile.js" type="text/javascript"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>档案管理</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a class="pubbtn bluebtn ico c1" href="toUploadFile" data-btn="archives">档案上传</a>
                <a href="javascript:;" class="pubbtn bluebtn ico c2">档案更新</a>
                <a href="javascript:;" class="pubbtn bluebtn ico c3">发邮件给</a>
            </div>
        </div>
        <!-- 搜索条件 -->
        <div class="min_document clearfix">
          <div class="top clearfix">
            <dl class="fmdl fml  fmdll clearfix">
              <dt>档案来源：</dt>
              <dd class="clearfix">
                <label><input type="radio" name="source" checked/>不限</label>
                <label><input type="radio" name="source"/>内部</label>
                <label><input type="radio" name="source"/>外部</label>
            </dd>
          </dl>
          <dl class="fmdl fmdll clearfix">
            <dt>存储类型：</dt>
            <dd class="clearfix">
                <label><input type="radio" name="type" checked/>不限</label>
                <label><input type="radio" name="type"/>文档</label>
                <label><input type="radio" name="type"/>图片</label>
                <label><input type="radio" name="type"/>音视频</label>
            </dd>
          </dl>          
        </div>
        <div class="bottom searchall clearfix">
          <dl class="fmdl fml fmdll clearfix">
            <dt>业务分类：</dt>
            <dd>
              <select>
                <option>全部</option>
              </select>
            </dd>
          </dl>
          <dl class="fmdl fml fmdll clearfix">
            <dt>所属业务线：</dt>
            <dd>
              <select>
                <option>全部</option>
              </select>
            </dd>
          </dl>
          <dl class="fmdl fmdll clearfix">
            <dt></dt>
            <dd>
              <input type="text" class="txt" placeholder="请输入项目名称或投资经理名称" />
            </dd>
            <dd>
            <a href="javascript:;" class="bluebtn ico cx">查询</a>
            </dd>
          </dl>
        </div>
        </div>
        <!--表格内容-->
        <table width="100%" cellspacing="0" cellpadding="0" >
              <thead>
                  <tr>
                      <th></th>
                      <th>所属业务线</th>
                      <th>所属项目</th>
                      <th>档案管理</th>
                      <th>起草者</th>
                      <th>存储类型</th>
                      <th>业务分类</th>
                      <th>更新日期</th>
                      <th>档案状态</th>
                      <th>附件查看</th>
                  </tr>
              </thead>                                                                                                                                    
              <tbody>
                  <tr>
                      <td><input type="radio" name="document" checked/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
                  </tr>
                  <tr>
                      <td><input type="radio" name="document"/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
                  </tr>
                  <tr>
                      <td><input type="radio" name="document"/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
                  </tr>
                  <tr>
                      <td><input type="radio" name="document"/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
                  </tr>
                  <tr>
                      <td><input type="radio" name="document"/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
                  </tr>
                  <tr>
                      <td><input type="radio" name="document"/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
                  </tr>
                  <tr>
                      <td><input type="radio" name="document"/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
                  </tr>
                  <tr>
                      <td><input type="radio" name="document"/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
                  </tr>
                  <tr>
                      <td><input type="radio" name="document"/></td>
                      <td>互联网旅游</td>
                      <td>项目名称</td>
                      <td>内部</td>
                      <td>万晓宇</td>
                      <td>文档</td>
                      <td>BP</td>
                      <td>2016-01-22</td>
                      <td>已签署归档</td>
                      <td><a href="javascript:; " class="blue">附件</a></td>   
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
		createMenus(14);
	});
</script>
</html>

