$(function () {
	createMenus(8);
	//切换tab，加载目标tab数据
	$('.assessment').tabchange2({
		onchangeSuccess:function(index){
			switch(index){
				case 0: loadDateFinance(); break; //标签0:投资经理绩效
				case 1: loadDataQuitProject(); break; //标签1:团队绩效
				case 2: loadDeptProject(); break; //标签1:合伙人绩效考核
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
	track_depProject_init();
}
$("#querySearch_depetProject").on('click',function(){
	$("#data-table-deptProject").bootstrapTable('destroy');
	track_depProject_init();
});
var partnerpi_url = platformUrl.deptProjectList;
var partnerkpi_pageNum = 1;
function track_depProject_init(){
	//表单事业线下拉初始化
	createCareelineOptions(platformUrl.getCareerlineListByRole,"deptid","");
	
	//绑定querySearch事件
	$('#data-table-deptProject').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		queryParams:function(params){
			return json_2_1(params,getToobarQueryParams('custom-toolbasr-userkpi'));
		},
		pagination: true,
        search: false,
        url: partnerpi_url
	});
}
/*$('#project-table').bootstrapTable({
	queryParamsType: 'size|page',
	pageSize:initPageSize,
	showRefresh : false,
	url : $('#project-table').attr("data-url"),
	sidePagination: 'server',
	method : 'post',
	sortOrder : 'desc',
	sortName : 'updated_time',
	pagination: true,
    search: false,
    //返回附带参数功能代码
    queryParams : function(param){
    	if(getCookieValue("backProjectList")!=''){
    		initParams = cookieOperator.pullCookie({_paramKey : 'projectList',_path : "/"});
    		deleteCookie("backProjectList","/");
    	}else{
    		initParams=undefined;
    	}
    	if(typeof(initParams) !== 'undefined'){
			param.pageNum = initParams.pageNum - 1;
    		param.pageSize = initParams.pageSize;
    		if(initParams.projectType != ''){
    			param.projectType = initParams.projectType;
    			$("select[name='projectType']").val(initParams.projectType);
    		}
    		if(initParams.financeStatus != ''){
    			param.financeStatus = initParams.financeStatus;
    			$("select[name='financeStatus']").val(initParams.financeStatus);
    		}
    		if(initParams.projectProgress != ''){
    			param.projectProgress = initParams.projectProgress;
    			$("select[name='projectProgress']").val(initParams.projectProgress);
    		}
    		if(initParams.projectStatus != ''){
    			param.projectStatus = initParams.projectStatus;
    			$("select[name='projectStatus']").val(initParams.projectStatus);
    		}
    		param.projectDepartid = initParams.projectDepartid;
    		$("select[name='projectDepartid']").val(initParams.projectDepartid);
    		createUserOptions_All(platformUrl.getUserList+initParams.projectDepartid, "createUid", 1);
    		param.createUid = initParams.createUid;
    		$("select[name='createUid']").val(initParams.createUid);
    		param.nameCodeLike = initParams.nameCodeLike;
    		$("input[name='nameCodeLike']").val(initParams.nameCodeLike);
    		param.projectPerson = initParams.projectPerson;
    		$("input[name='projectPerson']").val(initParams.projectPerson);
    		param.faFlag = initParams.faFlag;
    		$("input[name='faFlag'][value='"+initParams.faFlag+"']").prop("checked",true);
    		var options = $("#data-table").bootstrapTable('getOptions');
	        	options.pageNumber = initParams.pageNum - 1;
		}
    	return param;
    },
    onLoadSuccess: function (data) {
    	if($("#showResetBtn").val() == '1'){
			$("#resetBtn").removeClass("none");
		}
    	
    	
    	if(typeof(initParams) !== 'undefined' && initParams.pageNum != ''){
			if(initParams.pageNum==1){
				return;
			}else{
				$('.pagination li').removeClass('active');
				if($('.pagination .page-number').length< initParams.pageNum)
				{
					var len = $('.pagination .page-number').length;
					var totalPages = $("#project-table").bootstrapTable('getOptions').totalPages;
					var end = initParams.pageNum + Math.floor(len/2);
					if(end>totalPages)
					{
						end = totalPages;
					}
					
					for(var i=len-1; i>=0; i--)
					{
						$('.pagination .page-number').eq(i).html('<a href="javascript:void(0)">'+ end-- +'</a>');
					}
				}

				$('.pagination li').each(function(){
	    			if($(this).text()==initParams.pageNum){
	    				$(this).click();
	    				 return false;
	    				//$(this).addClass('active')
	    			}
				});
			}
		}
    	initPageSize=10;
    }
});*/