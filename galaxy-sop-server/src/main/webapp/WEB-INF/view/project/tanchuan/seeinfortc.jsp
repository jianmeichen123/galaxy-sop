<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="status statustc">
    <div class="title_bj" id="popup_name"></div>
    <div class="status_con seeinfor_con">
    <form id="deliver_form" >
      <dl class="fmdl clearfix">
        <dt>事项简述：</dt>
        <dd class="clearfix" >
        	<span name="delDescribe"></span>
            <!-- <input type="text" name="delDescribe"  class="txt ltxt"/> -->
        </dd>
      </dl> 
      <dl class="fmdl clearfix">
        <dt>详细内容：</dt>
          <dd class="clearfix">
          	<span name="details" style="word-wrap:break-word;display:block"></span>
            <!-- <textarea name="details"></textarea> -->
          </dd>
      </dl>
      <dl class="fmdl clearfix">
        <dt>完成情况：</dt>
        <dd class="clearfix" >
        	<span name="delStatus"></span>
            <!-- <input type="text" class="txt ltxt" name="delStatus"/> -->
        </dd>
      </dl> 
   </form>
  </div>
	
</div>