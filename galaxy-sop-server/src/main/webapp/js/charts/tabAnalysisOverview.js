var searchOverviewPanel = {
		init : function(){
			//初始化日期
			datePickerInitByHandler();
			$("#search_overview_form").find(".datepicker").val("");
			//初始化投资事业线
			sendGetRequest(platformUrl.getDepartMentDict+"/department",null,function(data){
				var _dom;
				var _type;
				switch(data.result.message){
				case "fileWorktype":
					_dom = $("#searchFileWorktype");
					break;
				default :
					_dom = $("#search_project_depart_id")
				}
				utils.each(data,_dom,"all");
			});
		},
}