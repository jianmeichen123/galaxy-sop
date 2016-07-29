/*function init(){
	createMenus(25);
	$('.project_analysis').tabchange2({
		onchangeSuccess:function(index){
			switch(index){
				case 0: overViewInit();  break;  //标签0:项目总览
				case 1: statisticsInit(); break;  //标签1:项目数统计
				case 2: riseRateInit(); break;  //标签2:项目完成增长率统计
				case 3: passMeetRateInit();   break;  //标签3:过会率统计
				case 4: investmentRateInit();   break;  //标签4:投决率统计
				default: return false;
			}
		}
	});
	overViewInit();
}

$(document).ready(init());

*/


function init(){
	
	var liList = $(".project_analysis .tablink").find("li");
	createMenus(25);
}


var isGG = true;
if(roleId == '1' || roleId == 1 || roleId == '2' || roleId == 2){
	isGG = true;
}else{
	isGG = false;
}

var pageNum = 1;
var queryParamsJson = {};


var utils = {
	each : function(_data,_dom,type){
		_dom.empty();
		if(type=="all"){
			_dom.append("<option value='all'>全部</option>");
		}
		$.each(_data.entityList,function(){
			if(this.code){
				_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
			}else{
				_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
			}
			
		});
	},
	confident : function(value,tem){
		if(value==tem){
			return;
		}else{
			return value;
		}
	}
}


function showCheckTabs(index)
{
	switch(index)
	{
	case 1 :  
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/toProOverView");
		break;
	case 2 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/paprojectlist");
		break;
	case 3 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/toprorRiseRate");
		break;
	case 4 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/toGhlSum");
		break;
	case 5 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/toTjlSum");
		break;
	default : 
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/toProOverView");
	
	}

}


//项目总览
function overViewInit(){
	searchOverviewPanel.init();
	console.log("overViewInit");
}
//项目统计数
function statisticsInit(){
	console.log("statisticsInit");
}
//项目增长率
function riseRateInit(){
	searchRiseRatePanel.init();
	console.log("riseRateInit");
}
//过会率
function passMeetRateInit(){
	console.log("passMeetRateInit");
}
//投决率
function investmentRateInit(){
	console.log("investmentRateInit");
}

