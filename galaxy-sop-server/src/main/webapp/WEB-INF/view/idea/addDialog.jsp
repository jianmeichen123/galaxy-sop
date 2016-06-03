<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<head>
<meta charset="utf-8">
	<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<%--     <script src="<%=path %>/js/jquery.showLoading.min.js"></script> --%>
</head>
<!-- 弹出页面 -->

<div id="addDialog"  style="display: none;">
	<div class="meetingtc creative_edit" >
	<form id="idea_form">
	<!-- <h2>添加创意</h2> -->
	<div class="clearfix">
	<input type="hidden" id="win_idea_id" name="id" class="txt"/>
			<dl class="fmdl clearfix fl">
    			<dt>创意编号：</dt>
        		<dd class="clearfix">
        			<input type="text" id="win_idea_code" name="ideaCode" class="txt"/>
       	 		</dd>
		    </dl>
		    <dl class="fmdl clearfix fl">
		    	<dt>创意名称：</dt>
		        <dd class="clearfix">
<%-- 		        	<input type="text" id="win_idea_name" valType="required" msg="<font color=red>*</font>创意名称不能为空" name="ideaName" class="txt"/> --%>
						<input type="text" id="win_idea_name" msg="<font color=red>*</font>创意名称不能为空" name="ideaName" class="txt"/>
		        </dd>
		    </dl>
		    <dl class="fmdl clearfix fl">
		    	<dt>提出人：</dt>
		        <dd>
		        	<input type="hidden" id="win_idea_create_id" name="createdUid"/>
		        	<input type="text" id="win_idea_create_name" name="createdUname"  class="txt"/>
		        </dd>
		    </dl>
		    <dl class="fmdl clearfix fl">
		    	<dt>所属事业线：</dt>
		        <dd>
<!-- 		        	<input type="text" id="win_idea_department" data-tid="" name="departmentId"  class="txt"/>        	 -->
						<select id="win_idea_department" name="departmentId" msg="<font color=red>*</font>请选择所属事业线">
            				<option>sadasd</option>
            			</select>
		        </dd>
		    </dl>
		    <dl class="fmdl clearfix fl">
		    	<dt>提出时间：</dt>
		        <dd>
		        	<input type="text" id="win_idea_create_Date" data-tid="" name="createDate"  class="txt"/>
		        </dd>
		    </dl>
		    <dl class="fmdl clearfix fl">
		    	<dt>创意来源：</dt>
		        <dd>
		        	<input type="text" class="txt" id="win_idea_source" name="ideaSource"/>
		        </dd>
		    </dl>  
	</div>
	<dl class="fmdl clearfix">
		  <dt>创意简述：</dt>
		  <dd class="eduidd">
		      <div type="text/plain" name="ideaDescHtml" id="win_idea_desc" style="width:100%; height:150px;"
	        	  valType="MAXBYTE" regString="9000" msg="<font color=red>*</font>访谈纪要不能超过9000字节" >
	          </div>
		  </dd>
	</dl> 
	<div class="box">
		 <a href="javascript:;" class="pubbtn bluebtn" id="win_uploadBtn">保存</a>
		 <a href="javascript:;" class="pubbtn fffbtn" id="win_cancelBtn">取消</a>	
	</div>
   </form>
	</div>
</div>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<!-- 富文本编辑器 -->
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>