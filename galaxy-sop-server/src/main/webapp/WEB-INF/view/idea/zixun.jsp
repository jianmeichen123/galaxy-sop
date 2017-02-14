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
    	
        <!--页眉-->
        <!-- <div class="top clearfix" resource-mark="zixun_add" style="display:none;"> -->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix" >
                <a id="addZixunBtn" href="javascript:;" class="pubbtn bluebtn ico c4" onclick="toAdd()" >添加创意资讯</a>
            </div>
        </div>
        
        
        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="zixun-custom-toolbar">
		
			<input type="hidden" name="property" value="updated_time">
			<input type="hidden" name="direction" value="DESC">
			
			<div class="bottom searchall clearfix search_adjust idea_list_searchall">
				<dl class="fmdl fmdll clearfix">
					<dt>创意资讯编号：</dt>
					<dd>
						<input type="text" class="txt" name="code"  />
					</dd>
				</dl>
				
				<dl class="fmdl fmdll clearfix">
	            	<dt>细分领域：</dt>
	            	<dd>
	            	<input type="text" class="txt" name="keyword" />  <!-- name="companyField" -->
	            	</dd>
	            </dl>
	            
				<dl class="fmdl fmdll clearfix">
	              <dt>归属部门：</dt>
	              <dd>
	                <select name="departmentId" >
	                </select>
	              </dd>
	            </dl>
	            
			</div>
			
			<div class="bottom searchall clearfix search_adjust idea_list_searchall">
	            <dl class="fmdl fmdll clearfix">
	            	<dt>　编辑时间：</dt>
	            	<dd>
	            		<input type="text" class="ideadatepicker txt time" name="startTime"  style="height:23px;"/>
	            		&nbsp;&nbsp;至&nbsp;&nbsp;
	            		<input type="text" class="ideadatepicker txt time" name="endTime"  style="height:23px;"/>
	            	</dd>
	            </dl>
	            <dl class="fmdl fmdll clearfix" style="width:150px;">
	            	<button type="submit" class="bluebtn ico cx" action="querySearch">搜索</button>
	            </dl>
			</div>
		</div>
		
		<div class="tab-pane active" id="view">	
		
			<div>
			    <a href="javascript:;" class="bluebtn ico tj" id="zizunExport">导出</a>
			</div>
				
			<table id="data-table-zixun" data-url="<%=path %>/galaxy/zixun/showlist" class="idea_list_table"
				data-page-list="[10, 20, 30]" data-toolbar="#zixun-custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="code"  class="data-input">创意资讯编码</th>
			        	<th data-field="companyField"  class="data-input">细分领域</th>
			        	<th data-field="departName"  class="data-input">归属部门</th>
			        	<th data-field="userName"  class="data-input">编辑人</th>
			        	<th data-field="updatedTime"  class="data-input" data-formatter="longTime_Format">编辑时间</th>
			        	<th data-field="id"  class="data-input" data-formatter="operateFormatter">操作</th>
 					</tr>	
 				</thead>
			</table>
        </div>
    </div>
    

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

	
	$(function(){
		getSearchDepartment($("#zixun-custom-toolbar [name='departmentId']"));
		
		var options=$("[name='departmentId'] option");
		var len=options.length;
		if(len<=2){
			$("[name='departmentId']").attr("disabled","true");
			$("[name='departmentId']").attr("class","disabled");
		}
		if(isContainResourceByMark("zixun_add")){
	       $('div[resource-mark="zixun_add"]').css("display","block");
		}
		
		
		$('#data-table-zixun').bootstrapTable({
			queryParamsType: 'size|page', // undefined
			pageSize:10,
			showRefresh : false ,
			sidePagination: 'server',
			method : 'post',
			pagination: true,
			uniqueId: "id", 
			idField : "id",
			clickToSelect: true,
	        search: false
		});
		
		
	});

	//初始化导出方法
	var reportChooseSuffix = {
			init : function(){
				$.getHtml({
					url:platformUrl.toChooseReportSuffix,//模版请求地址
					data:"",//传递参数
					okback:function(_this){
						$("#button_confirm").click(function(){
							var suffix = $("#chooseForm").find("input[name='suffix']:checked").val();
							window.location.href = platformUrl.exportZixunGrade + "?suffix=" + suffix;
							reportChooseSuffix.close(_this);
						})
						$("#button_close").click(function(){
							reportChooseSuffix.close(_this);
						})
					}
				});
			},
			close : function(_this){
				//启用滚动条
				 $(document.body).css({
				   "overflow-x":"auto",
				   "overflow-y":"auto"
				 });
				//关闭对外接口
				_this.hideback.apply(_this);
				$(_this.id).remove();
				$('.tip-yellowsimple').hide();
				//判断是否关闭背景
				if($(".pop").length==0){
					$("#popbg").hide();	
				}
			}
	}
	//咨询导出
	$("#zizunExport").on('click',function(){
//	 	window.location.href = platformUrl.exportKpiGrade;
		reportChooseSuffix.init();
	});
	
	
</script>

