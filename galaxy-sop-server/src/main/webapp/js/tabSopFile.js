function init(){
//	alert(prograss);
	data = {
			_domid : "file_repository_table",
			_projectId : projectId,
			_progress : prograss,
			_callFuc : function(){
//				console.log("新追加页面");
				showTabs(projectId,5);
			}
	}
	fileGrid.init(data);
}

$(document).ready(init());