<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>


<!-- 校验 -->
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>


<!-- bootstrap-table  -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>


	<!--右中部内容-->
 	<div class="tabtxt tabblock" style="background:#fff;padding-top:10px;">
    	
    	<a  data-btn="creative"></a>
    	
        <!--页眉-->
        <div class="top clearfix" resource-mark="idea_add" style="display:none;">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix" >
<%--             href="<%=path %>/galaxy/idea/add" --%>
                <a id="addBtn" href="javascript:;" class="pubbtn bluebtn ico c4">添加创意</a>
            </div>
        </div>
        
        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="custom-toolbar">
			<input type="hidden" name="property" value="created_time">
			<input type="hidden" name="direction" value="DESC">
			<div class="bottom searchall clearfix search_adjust idea_list_searchall">
				<dl class="fmdl fmdll clearfix">
					<dt>关键字：</dt>
					<dd>
						<input type="text" class="txt" name="keyword" placeholder="创意名称或编码" value="${name}" />
					</dd>
				</dl>
				<dl class="fmdl fmdll clearfix">
	              <dt>所属事业线：</dt>
	              <dd>
	                <select name="departmentId" >
	                  <option value="">全部</option>
	                </select>
	              </dd>
	            </dl>
	            <dl class="fmdl fmdll clearfix">
	              <dt>进度：</dt>
	              <dd>
	                <select name="ideaProgress">
	                  <option value="">全部</option>
	                  <option value="ideaProgress:1">待认领</option>
	                  <option value="ideaProgress:2">调研</option>
	                  <option value="ideaProgress:3">创建立项会</option>
	                  <option value="ideaProgress:4">搁置</option>
	                  <option value="ideaProgress:5">创建项目</option>
	                  <option value="ideaProgress:6">立项会</option>
	                  <option value="ideaProgress:7">投决会</option>
	                </select>
	              </dd>
	            </dl>				
			</div>
			<div class="bottom searchall clearfix search_adjust idea_list_searchall">
	            <dl class="fmdl fmdll clearfix">
	            	<dt>提出人：</dt>
	            	<dd>
	            	<input type="text" class="txt" name="createdUname" size="8"/>
	            	</dd>
	            </dl>
	            <dl class="fmdl fmdll clearfix">
	            	<dt>　提出时间：</dt>
	            	<dd>
	            		<input type="text" class="ideadatepicker txt time" name="createdDateFrom"  style="height:23px;"/>&nbsp;&nbsp;至&nbsp;&nbsp;
	            		<input type="text" class="ideadatepicker txt time" name="createdDateThrough"  style="height:23px;"/>
	            	</dd>
	            </dl>
	            <dl class="fmdl fmdll clearfix" style="width:150px;"><button type="submit" class="bluebtn ico cx" action="querySearch">搜索</button></dl>
				
			</div>
		</div>
		
		<input type="hidden" data-id="ideaNowId" value="" />
		
		<div class="tab-pane active" id="view">	
			<table id="data-table" data-url="idea/search" class="idea_list_table"
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="ideaCode"  class="data-input">创意编码</th>
			        	<th data-field="ideaName"  class="data-input" data-formatter="ideaNameLinkFormatter">创意名称</th>
			        	<th data-field="departmentDesc"  class="data-input">所属事业线</th>
			        	<th data-field="createdUname"  class="data-input">提出人</th>
			        	<th data-field="createdTime"  class="data-input" data-formatter="dateFormatter">提出时间</th>
			        	<th data-field="updatedTime"  class="data-input" data-formatter="dateFormatter">最后编辑时间</th>
			        	<th data-field="ideaProgress"  class="data-input" data-formatter="progressFormatter">进度</th>
 					</tr>	
 				</thead>
			</table>
           </div>
    </div>
    



<!--file--id  upload dialog start -->
<div id="upload-dialog" class="archivestc" style="display: none;">
	<form id="upload-form" style='margin-top:45px;'>
		<input type="hidden" name="id"> 
		<input type="hidden" name="isEdit"> 
		<input type="hidden" name="projectId">
		<input type="hidden" name="projectProgress">
		<div class="title_bj">上传可行性报告</div>

		<div class="fmdl clearfix ">
			<dt style="width:84px;text-align:right;">可行性报告：</dt>
			<dd>
				<input type="text" name="fileName" class="txt pointer-events" />
			</dd>
			<dd>
				<a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">选择档案</a>
			</dd>
		</div>

		<dl class="fmdl clearfix">
			<dt style="width:84px;text-align:right;">报告类型：</dt>
			<dd>
				<select id="fileType" name="fileType" disabled="disabled" class="disabled">
					<option value="">全部</option>
					<option value="fileType:1">文档</option>
					<option value="fileType:2">音频文件</option>
					<option value="fileType:3">视频文件</option>
					<option value="fileType:4">图片</option>
				</select>
			</dd>
		</dl>
	</form>
	
	<div >
	<span style=" float:left; margin-right:25px;"><a href="javascript:;" class="pubbtn bluebtn" id="upload-btn">提交</a></span>
	<span style=" float:left"><a href="javascript:;" class="pubbtn fffbtn" data-close="close" id="upload-close">取消</a></span>
	<div  style=" width:100%; height:20px; overflow:hidden">&nbsp;</div>
	</div>
</div>
<!-- upload dialog end -->


<jsp:include page="../idea/addDialog.jsp"></jsp:include>


<script src="<%=request.getContextPath() %>/js/operationMessage.js" type="text/javascript"></script>
<script src="<%=path %>/js/idea-common.js"></script>
<script type="text/javascript" src='<%=request.getContextPath() %>/js/ideaadd.js'></script>



<script type="text/javascript">
	$('.ideadatepicker').datepicker({
		format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    clearBtn:true,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:true
	});	

	getDepartment($("#custom-toolbar [name='departmentId']"));
	
	$("#custom-toolbar [name='ideaProgress']").change(function(){
		var val = $(this).val();
		if(val == 'ideaProgress:6' || val == 'ideaProgress:7')
		{
			$.getHtml({
				url:platformUrl.ideaProjectList,
				data:{ideaProgress:val},
				okback:function(){
					if(val == 'ideaProgress:6'){
						$('.title_bj').html('立项会')
					}
					if(val == 'ideaProgress:7'){
						$('.title_bj').html('投决会');
					}
				}
			});

			$(this).val("");
		}
	});
	
	$(function(){
		//var indextoid = "${indextoid}";
		if(!(!indextoid)){
			showIdeaDetail(indextoid);
			indextoid = null;
		}
		var options=$("[name='departmentId'] option");
		var len=options.length;
		if(len<=2){
		
			$("[name='departmentId']").attr("disabled","true");
			$("[name='departmentId']").attr("class","disabled");
		}
		if(isContainResourceByMark("idea_add")){
		       $('div[resource-mark="idea_add"]').css("display","block");
			}
		
		
	});
	cutStr(5,'cutstr');
	
</script>


</html>
