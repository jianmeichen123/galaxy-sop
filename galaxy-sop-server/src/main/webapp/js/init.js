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

function tiggerTable(e,pageSize){
	e.bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:pageSize,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
	});
}