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
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
	<!--右中部内容-->
 	<div class="ritmin">
    	<h2>会议纪要</h2>
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="<%=path %>/galaxy/project/progress/meetAddView" data-btn="meeting" class="pubbtn lpubbtn bluebtn ico c4">添加会议纪要</a>
            </div>
        </div>
        <!-- 搜索条件 -->
        <div class="min_document clearfix">
          <div class="bottom searchall clearfix">
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>会议类型：</dt>
              <dd class="clearfix">
                <label><input type="radio" name="source" checked/>不限</label>
                <label><input type="radio" name="source"/>内评会</label>
                <label><input type="radio" name="source"/>CEO评审</label>
                <label><input type="radio" name="source"/>立项会</label>
                <label><input type="radio" name="source"/>投决会</label>
              </dd>
            </dl>
            <dl class="fmdl fmmr fmdll clearfix">
              <dt>会议日期：</dt>
              <dd>
                <input type="text" class="txt time" value="2016-01-01"  />
                <span>至</span>
                <input type="text" class="txt time" value="2016-01-01"  />
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
        <table width="100%" cellspacing="0" cellpadding="0"  class='table_l'>
              <thead>
                  <tr>
                      <th>会议概况</th>
                      <th>项目信息</th>
                      <th>会议纪要</th>
                  </tr>
              </thead>                                                                                                                                    
              <tbody>
                  <tr>
                      <td><em></em>会议日期：<span>2016-01-26</span></td>
                      <td rowspan="3"><em></em><span>食乐淘</span><br/><em></em><span>立项会</span></td>
                      <td rowspan="3"><em></em><span>1.沟通有问题沟通有问题沟通有问题沟通有问题沟通有问题沟通有问题</span><br/><em></em><span>2.公司有水分；</span><br/><em></em><span>3.BP不完善！</span></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>会议结论：<span>通过</span></td>
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
                      <td rowspan="3"><em></em><span>食乐淘</span><br/><em></em><span>CEO评审会</span></td>
                      <td rowspan="3"><em></em><span>访谈有效，还有三个问题需要解决。</span></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>会议结论：<span>通过</span></td>
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
                      <td rowspan="3"><em></em><span>食乐淘</span><br/><em></em><span>立项会</span></td>
                      <td rowspan="3"><em></em><span>1.沟通有问题沟通有问题沟通有问题沟通有问题沟通有问题沟通有问题</span><br/><em></em><span>2.公司有水分；</span><br/><em></em><span>3.BP不完善！</span></td>
                  </tr>
                  <tr>
                      <td class="noborder"><em></em>会议结论：<span>通过</span></td>
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
	$.ajax({
		 url:"<%=path %>/galaxy/common/menu/7",
		 data:{},
		 async: false,
		 type: "GET",
		 dataType:"json",
		 contentType:"application/json; charset=UTF-8",
 	     cache: true,
 	     error: function(request) {
 	         alert("Connection error");
 	     },
 	     success: function(data) {
 	    	 var selected = data.header.attachment;
 	    	 var html = "";
 	    	 $.each(data.entityList, function(i,o){
 	    		 if(selected == o.id){
 	    			html += '<li class="on"><a href="' + o.url + '">' + o.menuName + '</a></li>';
 	    		 }else{
 	    			html += '<li><a href="' + o.url + '">' + o.menuName + '</a></li>';
 	    		 }
 	    	 });
 	    	 $("#menus").html(html);
 	  	 }
 	 }); 
});
</script>
</html>

