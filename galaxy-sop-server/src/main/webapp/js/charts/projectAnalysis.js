
function init(){
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

$(document).ready(init());