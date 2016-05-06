$(function () {
	$table = $('#data-table');
	var post_url = platformUrl[$table.attr("data-url")];
	if(post_url == "" ||post_url =="undefined"||post_url == undefined){
		post_url = $table.attr("data-url");
	}
	$table.bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		showRefresh : false ,
		url : post_url,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
        onLoadSuccess: function (data) {
        }
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
        search: false
	});
}
function tiggerTable1(e,pageSize,onLoadSuccess){
	e.bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:pageSize,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
        onLoadSuccess: onLoadSuccess
	});
}

































