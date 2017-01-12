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
<style>
.bars{display:none;}
.bootstrap-table #actual-table>tbody>tr>td{padding:0 5px !important;}
</style>
<div class="addmentc" style="max-height:350px;">
	<div class="title_bj" id="popup_name">实际注资信息列表</div>
     
    <div class="addbutton btnbox_f1 clearfix margin_45">                        	
        <span id="btn_add_appr_actual" style="display: none"  resource-mark="add_appr_actual" class="pbtn bluebtn h_bluebtn">添加实际注资信息</span>        
    </div>
    <div class="form clearfix">
        <div class="actual_all">
        	<input type="hidden" value="" name="partGrantId">
        	<input type="hidden" value="" id="partFlag">
        	<table id="actual-table" data-page-list="[5,10]" 
        		data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
			        	<th data-field="grantMoney" data-formatter="grantMoneyFormat"  class="data-input" >实际注资金额（万元）</th>
			        	<th data-field="actualTime" data-formatter="createDateFormat" class="data-input"  >实际注资日期<span></span></th>
			        	<th data-field="createUname" class="data-input">注资人<span></span></th>
			        	<th class="col-md-2" data-formatter="operatorFormat" data-events="operatorEvent" data-class="noborder">操作</th>
 					</tr>	
 				</thead>
			</table>
        </div>
    </div>
    <script type="text/javascript">
    	if(isContainResourceByMark('add_appr_actual') && isTransfering != 'true')
		{
			$('[resource-mark="add_appr_actual"]').css("display","inline-block");
		}
	    function createDateFormat(value, row, index){
	    	if(value && value != ''){
	    		return value;
	    		/* return time_zh(value, "年", "月", "日"); */
	    	}else return '';
	    }
	    function grantMoneyFormat(value, row, index){
	    	return fixSizeDecimal(value,4);
	    }
	    function operatorFormat(value, row, index){
	    	var opts = '';
	    	
	    	opts += '<label class="showActualLink blue">查看</label>';
	    	
	    	if(isContainResourceByMark('edit_appr_actual') && isTransfering != 'true')
    		{
	    		opts += '<label class="editActualLink blue"  href="javascript:void(0)">编辑</label>';
    		}
	    	if(isContainResourceByMark('delete_appr_actual') && isTransfering != 'true')
    		{
	    		opts += '<label class="deleteActualLink blue"  href="javascript:void(0)">删除</label>';
    		}
	    	if(row.fileNum && row.fileNum > 0){
	    		opts += '<label class="downfile blue" href="javascript:void(0)">下载附件</label>';
	    	}
	    	
		    return opts;
		 }
	    var operatorEvent = {
	    		'click .downfile' : function(e, value, row, index){
	    			try {
	    				var url = Constants.sopEndpointURL + '/galaxy/grant/actual/downActualFile'+"/"+row.id;
	    				layer.msg('正在下载，请稍后...',{time:2000});
	    				window.location.href=url+"?sid="+sessionId+"&guid="+userId;
	    			} catch (e) {
	    				layer.msg("下载失败");
	    			}
	    		},
	    		'click .editActualLink' : function(e, value, row, index){
	    			var formdata = {
	    					parentId :	${partId},
	    					actualId : row.id,
	    					operatorFlag : 2,
	    					callFuc : function(data){
		    					 $('#actual-table').bootstrapTable('refresh',function(param){
		    				        	param.partGrantId = ${partId};
		    				        	return param;
		    				        });
		    					 flushData(${partId});
		    					}
	    			}
	    			editApprActualDialog.init(formdata);
	    		},
	    		'click .deleteActualLink'  : function(e, value, row, index){
	    			layer.confirm('是否删除实际注资信息?', {
		        		  btn: ['确定', '取消'], //可以无限个按钮
		        		  title:"提示",
		        		}, function(index, layero){
		        			
		        			
		        			sendGetRequest(platformUrl.deleteApprActual + "/" + row.id ,null,function(data){
		    	        		if(data.result.status=="OK"){
		    	        			layer.msg("删除成功");
	    	                		flushData(${partId});
	    	                		//reference('${projectId}');
		    	        			var options = $('#actual-table').bootstrapTable('getOptions');
		    	                	var data = options.data;
		    	                	var pageNum_ = options.pageNumber; 
		    	                	
		    	                	var toPageNum = 1;
		    	                	if(pageNum_ != 1 &&　data.length != 1){
		    	                		toPageNum = pageNum_;
	                	        	}else if(pageNum_ != 1 &&　data.length == 1){
		    	                		toPageNum = pageNum_ - 1;
	                	        	}else
	                	        		toPageNum = pageNum_;
		    	                	
		    	        			$('#actual-table').bootstrapTable('destroy');
	    	                		$('#actual-table').bootstrapTable({
	    	                	    	queryParamsType: 'size|page',
	    	                			pageSize:5,
	    	                			pageNumber:toPageNum,
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
	    	                	        	 $.each(allResourceToUser, function(index, element){
	    	                	        		 console.log(element.resourceMark)
	    	                	     			 $('[resource-mark="' + element.resourceMark + '"]').css("display","inline-block");
	    	                	     			 
	    	                	     		});
	    	                	        }
	    	                	    });
		    	        			}else{
		    						layer.msg(data.result.errorCode);
		    					}
		    	        	});
		        		  //按钮【按钮一】的回调
		        		}, function(index){
		        		  //按钮【按钮二】的回调
		        		});
	    		},
				'click .showActualLink'  : function(e, value, row, index){
					var formdata = {
	    					parentId　:	${partId},
	    					actualId : row.id,
	    					operatorFlag : 3
	    			}
	    			editApprActualDialog.init(formdata);
	    		}
	    };
	    $('#actual-table').bootstrapTable({
	    	queryParamsType: 'size|page',
			pageSize:5,
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
	    
	    function actualLook(actualId){
	    	$.getHtml({
				url:Constants.sopEndpointURL+"/galaxy/grant/actual/lookActual/"+actualId,
				data:"",
				okback:function(){
					
				}	
			});
	    }
    </script>
    <script src="<%=path %>/js/editApprActualDialog.js"></script>
    <script type="text/javascript">
	    function init(){
	    	$("#btn_add_appr_actual").click(function(){
	    		var formdata = {
	    				parentId　:	${partId},
	    				callFuc : function(data){
	    					 $('#actual-table').bootstrapTable('refresh',function(param){
	    				        	param.partGrantId = ${partId};
	    				        	return param;
	    				        });
	    					 flushData(${partId});
	    				}
	    		}
	    		editApprActualDialog.init(formdata);
	    	});
	    }
	    $(document).ready(init());
    	
    </script>
  	
</div>