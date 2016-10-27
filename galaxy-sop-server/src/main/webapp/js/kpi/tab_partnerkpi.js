var partnerpi_url = platformUrl.partnerkpi;
var partnerkpi_pageNum = 1;

function partner_kpi_init(){
	$('#data-table-partnerkpi').bootstrapTable('destroy');
	$("#kpiExport").attr("class","bluebtn ico tj disabled");
	$("#kpiExport").attr("disabled","disabled");
	//绑定querySearch事件
	$('#data-table-partnerkpi').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		queryParams:function(params){
			return json_2_1(params,getPartnerToobarQueryParams('custom-toolbasr-partnerkpi'));
		},
		pagination: true,
        search: false,
        url: partnerpi_url,
        onLoadSuccess: function(backdata){
        	queryParamsJson = eval("("+backdata.queryParamsJsonStr+")");
        	var options = $('#data-table-partnerkpi').bootstrapTable('getOptions');
        	var data = options.data;
        	partnerkpi_pageNum = options.pageNumber;
        	if(partnerkpi_pageNum == 1){
        		var re = [];
    	   		var categories = [];
        		for(var i=0;i<data.length;i++){
        			if(i>=10){
        				break;
        			}else{
        				re.push(data[i].completed);
           	   			categories.push(data[i].departmentName);
        			}
            	}
        		containerDeptKpiOptions.series[0].data = re;
	    		containerDeptKpiOptions.xAxis.categories = categories;
    	   		var chart = new Highcharts.Chart(containerDeptKpiOptions);
        	}
        	$("#kpiExport").attr("class","bluebtn ico tj");
        	$("#kpiExport").removeAttr("disabled");
        }
	});
}

$("#querySearch_partnerkpi").on('click',function(){
	$("#kpiExport").attr("class","bluebtn ico tj disabled");
	$("#kpiExport").attr("disabled","disabled");
	getPartnerToobarQueryParams('custom-toolbasr-partnerkpi');
	partner_kpi_init();
	//$('#data-table-partnerkpi').bootstrapTable('refresh',getPartnerToobarQueryParams('custom-toolbasr-partnerkpi')); 
});


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
	if(query.partnerSdate>query.partnerEdate){
		layer.msg("开始时间不能大于结束时间");
		$('#data-table-partnerkpi').bootstrapTable('destroy');
		return false;
	}
	return query;
}




 