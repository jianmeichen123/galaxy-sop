var isGG = true;
if(roleId == '1' || roleId == 1 || roleId == '2' || roleId == 2){
	isGG = true;
}else{
	isGG = false;
}



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


$(function(){
	createMenus(8);
	
	//表单日期初始化
	var currDate = new Date();
	var sdate = currDate.format("yyyy-01-01");
	var edate = currDate.format("yyyy-MM-dd");
	$("input[name='sdate']").val(sdate);
	$("input[name='edate']").val(edate);
	
	
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
});
	



//项目总览
function overViewInit(){
	searchOverviewPanel.init();
	console.log("overViewInit");
}
//项目统计数
function statisticsInit(){
	pro_num_init();
	console.log("statisticsInit");
}
//项目增长率
function riseRateInit(){
	searchRiseRatePanel.init();
	console.log("riseRateInit");
}
//过会率
function passMeetRateInit(){
	pro_ghl_init();
	console.log("passMeetRateInit");
}
//投决率
function investmentRateInit(){
	pro_tjl_init();
	console.log("investmentRateInit");
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


//根据toobar id 获取表单参数
function getToobarQueryParams(ToolbarId){
	var toolbar = $("#"+ToolbarId);
	var query = {};
	toolbar.find("input[name][type!='radio']").each(function(){
		var input = $(this);
		var name = input.attr("name");
		var val = input.val();
		if(val!=''){
			query[name]=val;
		}
	});
	toolbar.find("input[type='radio']").each(function(){
		var input = $(this);
		var name = input.attr("name");
		if(input.attr("checked")=="checked"||input.prop("checked")==true){
			var val = input.val();
    		if(val!=''){
    			query[name]=val;
    		}
		}
	});
	toolbar.find("select[name]").each(function(){
		var select = $(this);
		var name = select.attr("name");
		var val = select.val();
		if(val!=''){
			query[name]=val;
		}
	});
	console.log(query);
	return query;
}


function json_2_1(json1,json2){
	var json = {};
	json = eval('('+(JSON.stringify(json1)+JSON.stringify(json2)).replace(/}{/,',')+')');
	return json;
}


