<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addmentc creative_edit1" id="ideaEdit">
<form id="update_form_basic" >
  <div class="form clearfix" >
    <div class="left">
      <dl class="fmdl fml">
        <dt>创意编号：</dt>
        <dd><input type="text"  id="ideaCode" class="txt disabled" disabled="disabled"/></dd>
      </dl>
       <dl class="fmdl">
        <dt>提出人：</dt>
        <dd><input type="text"  id="createdUname" class="txt disabled" disabled="disabled"/></dd>
      </dl>
      <dl class="fmdl">
        <dt>提出时间：</dt>
        <dd><input type="text" id="createdTime" data-formatter="dateFormatter" class="txt disabled" disabled="disabled"/></dd>
      </dl> 
      
    </div>
    <div class="right">
      <dl class="fmdl">
        <dt><b class="red">*</b>&nbsp;创意名称：</dt>
        <dd><input type="text" id="ideaName" name="ideaName" value="" class="txt"/></dd>
      </dl>  
      <dl class="fmdl">
        <dt>所属事业线：</dt>
        <dd>
          <select id="department">
            <option>合伙人姓名</option>
          </select>
          <input type="hidden" id="depid">
        </dd>
      </dl>
       <dl class="fmdl">
        <dt>创意来源：</dt>
        <dd><input type="text" id="ideaSource" name="ideaSource" value="" class="txt"/></dd>
      </dl>
    </div>
  </div>
    <dl class="fmdl">
      <dt>创意简述：</dt>
      <dd style="width:600px; ">
        <div type="text/plain"  id="edit_idea_desc" 
	        	  valType="MAXBYTE" regString="9000" msg="<font color=red>*</font>访谈纪要不能超过9000字节" >
	    </div>
      </dd>
    </dl>
    <div class="btnbox">
      <a href="javascript:;" class="pubbtn bluebtn" onclick="update()" >确定</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
  </div>
  </form>
  <!-- 富文本编辑器 -->
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
   <script type="text/javascript">
	UM.getEditor('edit_idea_desc');
  </script>