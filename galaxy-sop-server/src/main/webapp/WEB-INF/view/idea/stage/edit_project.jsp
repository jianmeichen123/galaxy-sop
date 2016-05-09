<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="edit_nametc">
  <div class="top clearfix">
      <dl class="fmdl fml">
        <dt>旧项目名称：</dt>
        <dd><input type="text" value="项目11" class="txt"/></dd>
      </dl>
       <dl class="fmdl">
        <dt>新项目名称：</dt>
        <dd><input type="text" value="项目11" class="txt"/></dd>
      </dl> 
  </div>
    <div class="btnbox">
      <a href="javascript:;" class="pubbtn bluebtn" >确定</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
  </div>