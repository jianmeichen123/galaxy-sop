function init(){
	data = {
			_domid : "file_repository_table",
			_projectId : projectId,
			_progress : prograss,
			_callFuc : function(){
				showTabs(projectId,5);
			}
	}
	fileGrid.init(data);
}

$(document).ready(init());