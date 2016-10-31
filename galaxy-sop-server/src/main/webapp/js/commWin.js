var commWin = {
		init : function(id,fillDom){	
			if(fillDom){
				this.fillDom = fillDom;
			}
			$.popup({
				txt : $("#" + id).html(),
				showback:function(){
					var _this = this;
					commWin.initData(_this);
					
				},
				hideback:function(t){
					
				}				
			});
		},
		fillDom : {},
		initData : function(_this){
			//初始化數據函數
			projectDialog.dataGrid.init(_this);
		},
		//关闭弹出框
		close : function(_this){
				$(_this.id).remove();	
				//关闭对外接口
				_this.hideback.apply(_this);
				//判断是否关闭背景
				if($(".pop").length==0){
					$("#popbg").hide();	
				}
		},
}
var projectDialog = {
		dataGrid : {
			init : function(_this){
				var dom = $(_this.id).find("#dialogGrid")
				dom.bootstrapTable({
				      url : platformUrl.searchProject,     //请求后台的URL（*）
				      queryParamsType: 'size|page', // undefined
				      showRefresh : false ,
				      search: false,
				      method : 'post',           //请求方式（*）
				      cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				      pagination: true,          //是否显示分页（*）
				      sortable: false,           //是否启用排序
				      sortOrder: "asc",          //排序方式
				      sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
				      pageNumber:1,            //初始化加载第一页，默认第一页
				      pageSize: 5,            //每页的记录行数（*）
				      pageList: [5],    //可供选择的每页的行数（*）
				      strictSearch: true,
				      clickToSelect: true,        //是否启用点击选中行
//				      height: 460,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				      uniqueId: "id",           //每一行的唯一标识，一般为主键列
				      cardView: false,          //是否显示详细视图
				      detailView: false,          //是否显示父子表
				      columns: [{
				        field: 'projectCode',
				        title: '项目编码'
				      }, {
				        field: 'projectName',
				        title: '项目名称'
				      }]
				    });
				dom.on('click-row.bs.table', function(e, row, $element){
					commWin.fillDom.data("tid",row.id);
					commWin.fillDom.val(row.projectName);
					commWin.close(_this);
				});
			}
		}
}