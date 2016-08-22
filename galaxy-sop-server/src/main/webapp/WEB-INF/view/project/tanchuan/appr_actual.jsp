<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<div class="addmentc">
	<div class="title_bj" id="popup_name">实际拨款信息列表</div>
     
    <div class="addbutton btnbox_f1 clearfix margin_45">                        	
        <a href="tchtml/edit_actual.html" data-btn="edit_actual" data-name='添加实际拨款信息' class="pbtn bluebtn h_bluebtn">添加实际拨款信息</a>        
    </div>
    <div class="form clearfix">
        <div class="actual_all">
        	<input type="hidden" value="" name="partGrantId">
        	<table id="actual-table" data-page-list="[2, 3]" 
        		data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
			        	<th data-field="grantMoney"  class="data-input" data-formatter="projectInfo" data-width="25%">实际拨款金额（元）</th>
			        	<th data-field="project_type" class="data-input  data-width="25%">拨款日期<span></span></th>
			        	<th data-field="createUname" class="data-input  data-width="25%">拨款人<span></span></th>
			        	<th class="col-md-2" data-formatter="editor" data-class="noborder" data-width="25%">操作</th>
 					</tr>	
 				</thead>
			</table>
        	<table class='health_case' cellpadding="0" cellspacing="0">
            	<tr>
                	<th>实际拨款金额（元）</th><th>拨款日期</th><th>拨款人</th><th>操作</th>
                </tr>
            	<tr>
                	<td>26,000,000</td><td> 2016-02-09</td><td>分析人</td><td><a class="blue" href="tchtml/actual_look.html" data-btn="actual_look" data-name='查看实际拨款信息'>查看</a>  <a class="meet_edit blue" href="tchtml/edit_actual.html" data-btn="edit_actual" data-name='编辑实际拨款信息'>编辑</a>  <a class="meet_delete blue" href="javascript:void(0)">删除</a>  </td>
                </tr>
            </table>
               
        </div>
    </div>
    <script type="text/javascript">
	    $('#actual-table').bootstrapTable({
	    	queryParamsType: 'size|page',
			pageSize:2,
			showRefresh : false ,
			url : Constants.sopEndpointURL+"/galaxy/grant/actual/searchActualList",
			sidePagination: 'server',
			method : 'post',
			sortOrder : 'desc',
			sortName : 'updated_time',
			pagination: true,
	        search: false,
	        queryParams:function(param){
	        	param.partGrantId = ${partId};
	        	return param;
	        },
	        onLoadSuccess: function (data) {
	        }
	    });
    </script>
  	
</div>