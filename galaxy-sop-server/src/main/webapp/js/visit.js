
$("#querySearch").on('click',function(){
	alert(9);
	visit();
});



//首页日程数据渲染
function visit(){
	$.ajax({
		url : Constants.sopEndpointURL+"galaxy/visit/getVisitStatistics",
		data : null,
		async : false,
		type : 'GET',
		dataType : "json",
		contentType:"application/json; charset=UTF-8",
		success : function(data) {
			 var json = eval(data);
			 var map = json.userData;
			 $("#planVisit").html(map.visitCount);
			 $("#completeVisit").html(map.completedVisitCount);
			 $("#interviewRate").html(map.visitRate);
		}
	});
}
