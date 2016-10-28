var isGG = true;
if(roleId == '1' || roleId == 1 || roleId == '2' || roleId == 2){
	isGG = true;
}else{
	isGG = false;
}

//不是高管，隐藏tag
if(isGG){
	$("#team_kpi_li").css("display","block");
}


$(function(){
	createMenus(7);
	
	//表单日期初始化
    var currDate = new Date();
	var sdate = currDate.format("yyyy-01-01");
	var edate = currDate.format("yyyy-MM-dd");
	
	$("input[name='sdate']").val(sdate);
	$("input[name='edate']").val(edate);
	
	
	
	//切换tab，加载目标tab数据
	$('.assessment').tabchange2({
		onchangeSuccess:function(index){
			switch(index){
				case 0: loadDataUserKpi(); break; //标签0:投资经理绩效
				case 1: loadDataDetpKpi(); break; //标签1:团队绩效
				case 2: loadDataPartnerKpi(); break; //标签1:合伙人绩效考核
				default: return false;
			}
		}
	});
	
    //加载 部门数据 
	var index_check_url=document.location.href;
	if(index_check_url.indexOf("toteamkpi")!=-1 && isGG){ // 首页跳转功能
		$(".assessment").tabchange3();
	    loadDataDetpKpi();
	}else{
		loadDataUserKpi();
	}
	
});
	



function loadDataUserKpi(){
	per_kpi_init();
}

function loadDataDetpKpi(){
	team_kpi_init();
}

function loadDataPartnerKpi(){
	partner_kpi_init();
	$("#defined").removeAttr("checked");
	//$("input[type=radio][name=week][value=0]").attr("checked","checked");
	//$("#week").attr('checked');
	$("#week").prop("checked",true) 
	$("#weekType").find(':input').attr('data', 'false');
	$("#weekType").show();
	$("#definedType").hide();
	setDateRange(new Date(),"INIT");
}



//格式化比率
function rate_format(value, row, index){
	if(value=='undefined' || value==0 || !value){
		return "0%";
	} else if(value=='-'){
		return value;
	}else{
		value = value * 100;
		return value.toFixed(2)+"%";
	}
}


