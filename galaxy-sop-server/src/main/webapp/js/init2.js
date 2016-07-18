/**
 * 
 */
$(function () {
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
	});
	
});

