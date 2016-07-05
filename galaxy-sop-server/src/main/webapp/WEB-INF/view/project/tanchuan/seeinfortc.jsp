<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="status">
    <div class="title_bj" id="popup_name"></div>
    <div class="status_con seeinfor_con">
      <dl class="fmdl clearfix">
        <dt>事项简述：</dt>
        <dd class="clearfix">
            <input type="text" class="txt ltxt"/>
        </dd>
      </dl> 
      <dl class="fmdl clearfix">
        <dt>详细内容：</dt>
          <dd class="clearfix">
            <textarea>记得和团队见面时候，带着上次记录的问题。另外要通知傅总一下。</textarea>
          </dd>
      </dl>
      <dl class="fmdl clearfix">
        <dt>完成情况：</dt>
        <dd class="clearfix">
            <input type="text" class="txt ltxt"/>
        </dd>
      </dl> 
   
  </div>

</div>