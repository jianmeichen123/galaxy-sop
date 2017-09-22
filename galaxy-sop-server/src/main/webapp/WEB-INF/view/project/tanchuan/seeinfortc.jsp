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
        	<span id="delDescribe"></span>
        </dd>
      </dl> 
      <dl class="fmdl clearfix">
        <dt>详细内容：</dt>
          <dd class="clearfix">
          	<span id="details" style="word-wrap:break-word;display:block"></span>
            <!-- <textarea name="details"></textarea> -->
          </dd>
      </dl>
      <dl class="fmdl clearfix">
        <dt>完成情况：</dt>
        <dd class="clearfix" >
        	<span id="delStatus"></span>
        </dd>
      </dl> 
   </form>
     <dl class="fmdl fl_l" id="show_up_file">
           <table style="width:530px;margin: auto;" id="filelist"  cellspacing="0" cellpadding="0">
           <thead>
              <tr>
                <th style="width:265px;">文件名称</th>
                <th style="width:105px" align="center">文件大小</th>
              </tr>
           </thead>
           </table> 
      </dl>
  </div>
	
</div>