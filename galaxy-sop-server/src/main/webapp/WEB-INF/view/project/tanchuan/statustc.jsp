<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="statustc status">
    <div class="title_bj" id="popup_name"></div>
    <div class="status_con">
      <dl class="fmdl clearfix">
        <dt>健康状况：</dt>
        <dd>
          <label class="radio">
            <input type="radio" name="status"> 高于预期
          </label>
          <label class="radio">
            <input type="radio" name="status"> 正常
          </label>
          <label class="radio">
            <input type="radio" name="status"> 健康预警
          </label>        
        </dd>

      </dl> 
      <dl class="fmdl clearfix">
        <dt>风险点：</dt>
          <dd class="clearfix">
            <textarea>记得和团队见面时候，带着上次记录的问题。另外要通知傅总一下。</textarea>
          </dd>
      </dl>

      <div class="btnbox">
        <a href="javascript:;" class="pubbtn bluebtn" >确定</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
      </div>    
  </div>

</div>