/**
 * 
 */
var tabFile = {
	init : function(projectId) {
		
		$.getTabHtml({
			url : platformUrl.toTabFile + "/" + projectId,//模版请求地址
			data : "",//传递参数
			okback : function(){
				//搜索框显示隐藏
				  $('.show_more a').on("click",function(){
				    var $self=$(this),
				        _name = $self.attr("data-btn"),
				        _siblings = $self.siblings();
				        //点击展开
				          if(_name=="show"){
				          _siblings.show();
				          $self.hide();
				          $self.parent().siblings("[data-btn='box']").show();
				        };
				        //点击隐藏
				        if(_name=="hide"){
				          _siblings.show();
				          $self.hide();
				          $self.parent().siblings("[data-btn='box']").hide();
				        }
				        return false;
				  });
				
				var isTransfering = getIsTransfering();
				var prograss = getPrograss();
				var data = {
						_domid : "file_repository_table",
						_projectId : projectId,
						_progress : prograss,
						_callFuc : function(){
//							console.log("新追加页面");
							tabFile.init(projectId);
						}
				}
				fileGrid.init(data);
			}
		});
	}
}