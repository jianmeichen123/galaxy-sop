<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc creative_edit1">
  <div class="form clearfix">
    <div class="left">
      <dl class="fmdl fml">
        <dt>创意编号：</dt>
        <dd><input type="text" value="cy00001" class="txt disabled" disabled="disabled"/></dd>
      </dl>
       <dl class="fmdl">
        <dt>提出人：</dt>
        <dd><input type="text" value="张三" class="txt disabled" disabled="disabled"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>提出时间：</dt>
        <dd><input type="text" value="2016-05-04" class="txt disabled" disabled="disabled"/></dd>
      </dl> 
      <dl class="fmdl">
        <dt>创意来源：</dt>
        <dd><input type="text" value="" class="txt"/></dd>
      </dl>
    </div>
    <div class="right">
      <dl class="fmdl">
        <dt><b class="red">*</b>&nbsp;创意名称：</dt>
        <dd><input type="text" value="食乐淘" class="txt"/></dd>
      </dl>  
      <dl class="fmdl">
        <dt>所属事业线：</dt>
        <dd>
          <select>
            <option>合伙人姓名</option>
          </select>
        </dd>
      </dl>
      <dl class="fmdl">
        <dt>指派负责人：</dt>
        <dd>
          <select>
            <option>合伙人姓名</option>
          </select>
        </dd>
      </dl>
    </div>
  </div>
    <dl class="fmdl">
      <dt>创意简述：</dt>
      <dd>
        <textarea></textarea>
      </dd>
    </dl>
    <div class="btnbox">
      <a href="javascript:;" class="pubbtn bluebtn" >确定</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
  </div>