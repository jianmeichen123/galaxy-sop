$(function () {
	createMenus(8);
	//切换tab，加载目标tab数据
	$('.assessment').tabchange2({
		onchangeSuccess:function(index){
			switch(index){
				case 0: loadDateFinance(); break; //标签0:投资经理绩效
				case 1: loadDataQuitProject(); break; //标签1:团队绩效
				case 2: loadDeptProject();break; //标签1:合伙人绩效考核
				default: return false;
			}
		}
	});
});
function loadDateFinance(){
	
}
function loadDataQuitProject(){
	
}
function loadDeptProject(){
	$("#userTrack_deptid option").not(":first").remove();
	createCareelineOptions(platformUrl.getCareerlineListByRole,"projectDepartid","");
	track_depProject_init();
}
$("#querySearch_depetProject").on('click',function(){
	alert(9);
	$("#data-table-deptProject").bootstrapTable('destroy');
	track_depProject_init();
});
var partnerpi_url = platformUrl.deptProjectList;
var partnerkpi_pageNum = 1;
function track_depProject_init(){
	$('#data-table-deptProject').bootstrapTable('destroy');
	//绑定querySearch事件
	$('#data-table-deptProject').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		sortOrder : 'asc',
		method : 'post',
		queryParams:function(params){
			var deptid = null;
			if($('#userTrack_deptid option:selected').val()){
				deptid = $('#userTrack_deptid option:selected').val();
			}
			var $selectedvalue = $("input:radio[name='isNullTime']:checked").val();
			if($selectedvalue=="yes"){
				params.sDate=null;
				params.eDate=null;
			}else{
				params.sDate=$("#deptkpi_sdate[data='false']").val();
				params.eDate=$("#deptkpi_edate[data='false']").val();
				if(params.sDate>params.eDate){
					layer.msg("开始时间不能大于结束时间")
				}
				console.log(params.sDate+"______"+params.eDate);
			}
			params.projectDepartid=deptid;
			params.isNullTime=$selectedvalue;
			
			return params;
		},
		pagination: true,
        search: false,
        url: partnerpi_url
	});
}
var reportChooseSuffix = {
		init : function(){
			$.getHtml({
				url:platformUrl.toChooseReportSuffix,//模版请求地址
				data:"",//传递参数
				okback:function(_this){
					$("#button_confirm").click(function(){
						var suffix = $("#chooseForm").find("input[name='suffix']:checked").val();
						window.location.href = platformUrl.exportProjectGrade + "?suffix=" + suffix;
						reportChooseSuffix.close(_this);
					})
					$("#button_close").click(function(){
						reportChooseSuffix.close(_this);
					})
				}
			});
		},
		close : function(_this){
			//启用滚动条
			 $(document.body).css({
			   "overflow-x":"auto",
			   "overflow-y":"auto"
			 });
			//关闭对外接口
			_this.hideback.apply(_this);
			$(_this.id).remove();
			$('.tip-yellowsimple').hide();
			//判断是否关闭背景
			if($(".pop").length==0){
				$("#popbg").hide();	
			}
		}
}