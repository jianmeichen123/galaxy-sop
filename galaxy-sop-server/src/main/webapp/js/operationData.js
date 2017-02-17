var url = platformUrl.operationalDataList;
function  operationData_init(){
	//$("#fileGridOperation").bootstrapTable('destroy'); 
	$('#fileGridOperation').bootstrapTable({
	    url : url,     //请求后台的URL（*）
	    queryParamsType: 'size|page', // undefined
	    showRefresh : false ,
	    search: false,
	    method : 'post',           //请求方式（*）
	    cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	    pagination: true,          //是否显示分页（*）
	    sortable: false,           //是否启用排序
	    sortOrder: "asc",          //排序方式
	   /* queryParams:function(params){
	    	console.log(json_2_1(params,getPartnerToobarQueryParams('custom-toolbar-operate')));
			return json_2_1(params,getPartnerToobarQueryParams('custom-toolbar-operate'));
		},*/
	    sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
	    pageNumber:1,            //初始化加载第一页，默认第一页
	    pageSize: 10,            //每页的记录行数（*）
	    pageList: [10, 20],    //可供选择的每页的行数（*）
	    strictSearch: true,
	    clickToSelect: false,        //是否启用点击选中行
	    uniqueId: "id",           //每一行的唯一标识，一般为主键列
	    cardView: false,          //是否显示详细视图
	    detailView: false,          //是否显示父子表
	    columns: [
	       {
		    field: 'createUid',
		    title: '运营数据统计区间'
		  }/* ,{
	      field: 'updatedTime',
	      title: '编辑时间'
	   	 },
	     {
	        field: 'updatedUid',
	        title: '编辑人'
	      } */
			,{
			  	  field: 'operate', 
			  	  title: '操作', 
			  	  formatter: operateFormatter 

			    } ],
			    onLoadSuccess: function(backdata){
		        	
		        		layer.msg(backdata.result.message);
		        		return;
		        }
	  }); 
}

  
 
//根据toobar id 获取表单参数
 function getPartnerToobarQueryParams(ToolbarId){
 	$("#"+ToolbarId).find('dd:hidden').find(':input').attr('data', 'true');
 	var toolbar = $("#"+ToolbarId);
 	var query = {};
 	toolbar.find("input[name][type!='radio'][ data!='true']").each(function(){
 		
 			var input = $(this);
 			var name = input.attr("name");
 			var val = input.val();
 			if(val!=''){
 				query[name]=val;
 			}
 		
 	});
 	return query;
 }