var startPath = Constants.sopEndpointURL;
$(function(){
 	sendGetRequest(platformUrl.toaddPersonHr+$("#personId").val(), null, wanshancbf);
 	$("body").delegate(".datepicker", "focusin", function(){
 		$(this).datepicker({
 		    format: 'yyyy-mm-dd',
 		    language: "zh-CN",
 		    autoclose: true,
 		    todayHighlight: false,
 		    calendarWeeks: true,
 		    weekStart:1,
 		    today: "Today",
 		    todayBtn:'linked',
 		    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
 		    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
 		    forceParse:false
 		    //defaultViewDate: { year: 1977, month: 04, day: 25 },
 		    //daysOfWeekDisabled: "0",
 		    //daysOfWeekHighlighted: "0",
 		    //clearBtn: true,
 		    //startView: 1, //0,month 1,year 2,decade
 		    //minViewMode: 1,  //0,days 1,month 2,years
 		    //maxViewMode: 1, //0,days 1,month 2,years
 		    //startDate: '-3d',
 		    //endDate: '+3d'
 		});
 	});
})
function wanshancbf(data){
	if(data.result.status == "OK"){
		var personInvest = data.entity.personInvest;
		var personLearns = data.entity.personLearn;
		var personPool = data.entity.personPool;
		var personWorks = data.entity.personWork;
		if(personInvest != undefined ){
			var model_personInvest = $("div[model='personInvest']");
			model_personInvest.find("input[type!='radio']").each(function(index,input_item){
				var input  = $(input_item);
				var name = input.attr("name");
				input.val(personInvest[name]);
			});
			model_personInvest.find("td[data-by]").attr("data-val",personInvest["id"]);
		}
		if(personPool != undefined ){
			var model_personPool = $("div[model='personPool']");
			model_personPool.find("input[type!='radio']").each(function(index,input_item){
				var input  = $(input_item);
				var name = input.attr("name");
				input.val(personPool[name]);
			});
			model_personPool.find("td[data-by]").attr("data-val",personPool["id"]);
			
 			$("input:radio[name='personSex'][value='"+personPool['personSex']+"']").attr("checked","checked"); 
			$("input:radio[name='laborDispute'][value='"+personPool['laborDispute']+"']").attr("checked","checked") ; 	
/*			成员关系的下拉框不需要
 * 			var obj=$("#memberRelation option");
			for(var i=0;i<obj.length;i++){
				if(obj[i].value==personPool['memberRelation']){
					obj[i].selected='selected' ;
				}
			}*/
			$("#endComment").val(personPool['endComment']);
			$("#levelStar").raty({
				starOn:startPath+"/star/img/star-on.png",
			    starHalf:startPath+ "/star/img/star-half.png",
			    starOff :startPath +"/star/img/star-off.png",
			    starOn : startPath+"/star/img/star-on.png",
				score: personPool['levelStar']});
			$("#abilityStar").raty({
				starOn:startPath+"/star/img/star-on.png",
			    starHalf:startPath+ "/star/img/star-half.png",
			    starOff : startPath+"/star/img/star-off.png",
			    starOn :startPath +"/star/img/star-on.png",
				score: personPool['abilityStar'] });
		}
		var model_personLearn =  $("div[model='personLearn']");
		var td_personLearn = model_personLearn.find("td[data-by]");
		for(var i = 0 ;i < personLearns.length ;i++){
			var personLearn = personLearns[i];
			model_personLearn.find("tr").each(function(m,tr_item){
				var input = $($(tr_item).find("input[name]")[i]);
				input.val(personLearn[input.attr("name")]);
			});
			$(td_personLearn[i]).attr("data-val",personLearn["id"]);			
			var dege = personLearn['degree'];
			var objg=$("#de"+i+" option");			
			for(var j=0;j<objg.length;j++){				
				if(objg[j].value==dege){
					objg[j].selected='selected' ;
				}
			}
			
			if(personLearns.length > td_personLearn.length){
				appendTd(model_personLearn);				
			}
			td_personLearn = model_personLearn.find("td[data-by]");
		}
		
		var model_personWork =  $("div[model='personWork']");
		var td_personWork = model_personWork.find("td[data-by]");
		for(var i = 0 ;i < personWorks.length ;i++){
			var personWork = personWorks[i];
			model_personWork.find("tr").each(function(m,tr_item){
				var input = $($(tr_item).find("input[name]")[i]);
				input.val(personWork[input.attr("name")]);
			});
			$(td_personWork[i]).attr("data-val",personWork["id"]);
				var dege = personWork['leaderRelationship'];
				f=i+1;
				
				var objg=$("#le"+f+" option");			
				for(var j=0;j<objg.length;j++){				
					if(objg[j].value==dege){
						objg[j].selected='selected' ;
					}
				}
			if(personWorks.length > td_personWork.length){
				appendTd1(model_personWork);				
			}
			td_personWork = model_personWork.find("td[data-by]");
		}
	}
}
$("div[model]").on("click",".add",function(){
	var model = $(this).parent().parent();
	if(model.find("td[data-by]").length > 3){
		layer.msg("不能再添加了！");
		return;
	}
	appendTd(model)
});
$("div[model]").on("click",".addd",function(){
	var model = $(this).parent().parent();
	if(model.find("td[data-by]").length > 3){
		layer.msg("不能再添加了！");
		return;
	}
	appendTd1(model)
});
function appendTd(model){
	model.find("tr").each(function(index,tr){
		var select = $($(tr).find("select")[0]);
		/*var did = select.attr("id");*/
		var namee = "degree";
		var idd = "de3";
		var iddd="degree3"
		var ename = "de4";
		var valuee = "请选择"
		var ese = "setValue3(this)";
		var option = $($(select).find("option"));
		var classs = "none";
		for(var i = 1 ;i < option.length ;i++){
			if(str=='undefined'||str== undefined){
				str ="<option value='' >"+valuee+"</option>";
			}
			var value = $(option[i]).attr("value");
			var str = str +"<option value='"+value+"' >"+value+"</option>";
		}
		
		var input =  $($(tr).find("input")[1]);
		var hidden = input.attr("hidden");
		var name = input.attr("name");
		var type = input.attr("type");
		var class_name = input.attr("class");
		var valType = input.attr("valType");
		var msg = input.attr("msg");
		var regString = input.attr("regString");
		var textsIn = input.attr("textsIn");
		if(textsIn=='undefined'||textsIn== undefined){
			textsIn = "";
		}
		if(str=='undefined'||str== undefined){
			str = "";
		}
		if(hidden=='undefined'||hidden== undefined){
			hidden = "";
		}
		if(msg=='undefined'||msg== undefined){
			msg = "";
		}
		if(valType=='undefined'||valType== undefined){
			valType = "";
		}
		if(regString=='undefined'||regString== undefined){
			regString = "";
		}

		if(index == 0 ){
			$(tr).append("<td data-by='id'><select id='"+idd+"' name='"+ename+"' onchange='"+ese+"'>"+str+"</select><input name ='"+namee+"' id='"+iddd+"' hidden='"+hidden+"' class='"+classs+"'></td>");
		}else{
				if(class_name == "datepicker"){
					$(tr).append("<td><input textsIn='"+textsIn+"' regString='"+regString+"' msg='"+msg+"' valType='"+valType+"' class='"+class_name+"' type='"+type+"' name='"+name+"' /></td>");
				}else{
					$(tr).append("<td><input textsIn='"+textsIn+"' regString='"+regString+"' msg='"+msg+"' valType='"+valType+"' type='"+type+"' name='"+name+"'/></td>");
				}
			}
		});
}

function appendTd1(model){
	model.find("tr").each(function(index,tr){
		if(index==10){
			var k = model.find("td[data-by]").length;			
		}				
		var input = $($(tr).find("input")[0]);
		var name = input.attr("name");		
		var type = input.attr("type");
		var class_name = input.attr("class");
		var valType = input.attr("valType");
		var msg = input.attr("msg");
		var regString = input.attr("regString");
		var textsIn = input.attr("textsIn");
		var hidden = input.attr("hidden");
		
		//工作关系下拉框
		var idd = "le"+k;
		var select = $($(tr).find("select")[0]);		
		var namee = select.attr("name");		
		var valuee = "请选择";
		f=4+k;
		var ese ="setValue"+f+"(this)"/* select.attr("onchange");*/
		var option = $($(select).find("option"));
		var id = "leaderRelationship"+k;
		
		for(var i = 1 ;i < option.length ;i++){
			if(str=='undefined'||str== undefined){
				str ="<option value='' >"+valuee+"</option>";
			}
			var value = $(option[i]).attr("value");
			var str = str +"<option value='"+value+"' >"+value+"</option>";
		}
		
		if(textsIn=='undefined'||textsIn== undefined){
			textsIn = "";
		}
		if(msg=='undefined'||msg== undefined){
			msg = "";
		}
		if(valType=='undefined'||valType== undefined){
			valType = "";
		}
		if(regString=='undefined'||regString== undefined){
			regString = "";
		}
		if(regString=='undefined'||regString== undefined){
			regString = "";
		}

		if(index == 0 ){
			$(tr).append("<td data-by='id'><input textsIn='"+textsIn+"' regString='"+regString+"' msg='"+msg+"' valType='"+valType+"' type='"+type+"' name='"+name+"' /></td>");
		}else if(index == 10 )
		{

			$(tr).append("<td><select id='"+idd+"' name='"+namee+"' onchange='"+ese+"'>"+str+"</select><input name ='"+name+"' hidden='"+hidden+"' id='"+id+"' class='"+class_name+"' ></td>");
		}
		else{			
			if(class_name == "datepicker"){
			
				$(tr).append("<td><input textsIn='"+textsIn+"' regString='"+regString+"' msg='"+msg+"' valType='"+valType+"' class='"+class_name+"' type='"+type+"' name='"+name+"' /></td>");
		
			}else{
				$(tr).append("<td><input textsIn='"+textsIn+"' regString='"+regString+"' msg='"+msg+"' valType='"+valType+"' type='"+type+"' name='"+name+"'/></td>");
			}
		}				
	});

}
function prependTd(model,model_data){
	model.find("tr[type!=hidden]").each(function(index,item){
		var tr = $(item);
		var td = tr.find("td")[0];
		tr.prepend("<td>"+td.innerHTML+"</td>");
	});
}
function setValue(obj) {
    
	 var ar = obj.value;	  
	var text = document.getElementById("degree");
	text.value = ar;
		
}
function setValue1(obj) {
    
/*	 var ar = obj.value;
	var text = document.getElementById("degree1"); $("input[name='degree']");
	text.value = ar;
		*/
	 var ar = obj.value;
	var text = document.getElementById("degree1");
	text.value = ar;
}
function setValue2(obj) {
    
	 var ar = obj.value;
	var text = document.getElementById("degree2");
	text.value = ar;
		
}
function setValue3(obj) {
    
	 var ar = obj.value;
	var text = document.getElementById("degree3");
	text.value = ar;
		
}
function setValue4(obj) {
    
	 var ar = obj.value;
	 
	var text =document.getElementById("leaderRelationship");
	text.value = ar;
	
		
}
function setValue6(obj) {
    
	 var ar = obj.value;
	  
	var text =document.getElementById("leaderRelationship2");
	text.value = ar;

		
}
function setValue7(obj) {
    
	 var ar = obj.value;
	 
	var text =document.getElementById("leaderRelationship3");
	text.value = ar;
	
		
}
function setValue8(obj) {
    
	 var ar = obj.value;
	 
	var text =document.getElementById("leaderRelationship4");
	text.value = ar;
	
		
}
$(".btnbox").on("click",".bluebtn",function(){	
	var models = $("div[model]");
	var data = {};
	var flag = true;
	models.each(function(i,item){
		var it = $(item);
		//单个实体
		var model = null ;
		var name = it.attr("model");
		var multi = it.attr("multi");
		if(multi == true || multi =="true"){
			model = new Array();
			var len = it.find("tr").eq(0).find("input[name]").length;
			/*var lenn = it.find("tr").eq(0).find("select[name]").length;*/			
			for(var i = 0 ;i <len;i++){
				var son_model = {};
				it.find("tr").each(function(m,tr_item){
					var input = $(tr_item).find("input[name][type!=hidden]")[i];
					var val = $(input).val().trim();		
					if(val != ''){
						son_model[$(input).attr("name")] = val;
						if(!resemetValidate($(input))){
							flag = false;
							return;
						}
					}
					
				});
				var td = $($(it.find("tr")).find("td[data-by]").eq(i));
				son_model[td.attr("data-by")] = td.attr("data-val");
				model[i] = son_model;
			}
			data[name] = model;	
		}else{
			model = {};
			it.find("input[name]").each(function(index,input){
				var val = $(input).val().trim();
				if(val != ''){
					model[$(input).attr("name")] = val ;
					if(!resemetValidate($(input))){
						flag = false;
						return;
					}
				}
				
			});
			var td = it.find("td[data-by]");
			model[td.attr("data-by")] = td.attr("data-val");
			data[name] = model;
		}
		
	});
	if(data['personPool']['personName'] == ''||data['personPool']['personName'] == undefined ||data['personPool']['personName'] == 'undefined'){
		layer.msg("核心成员基本资料中姓名不能为空");
		return;
	}
	if(!flag){
		return;
	}
	data['personId'] = $("#personId").val();
	data['personPool']['personSex'] = $("input[name='personSex']:checked").val();
	data['personPool']['laborDispute'] = $("input[name='laborDispute']:checked").val();
	data['personPool']['endComment'] = $("#endComment").val();
	//var v=$();
	/*data['personPool']['memberRelation'] = $("#memberRelation option:selected").val();*/
   //拿到选中项的值
	if($("#levelStar").find("input[name='score']").val() != ''){
		data['personPool']['levelStar'] = $("#levelStar").find("input[name='score']").val();
	}
	if($("#abilityStar").find("input[name='score']").val() != ''){
		data['personPool']['abilityStar'] = $("#abilityStar").find("input[name='score']").val();
	}
	var flag = 0;
	var json = {};
	json['personId'] = $("#personId").val();
	json['personPool'] = data['personPool'] ;
	for(var x in data['personInvest']){
		if(x !='id'){
			flag ++;
		}
	}
	if(flag > 0){
		json['personInvest'] = data['personInvest'] ;
	}
	var arry_personWork = new Array()
	for(var x in data['personWork']){
		flag = 0;
		for(var y in data['personWork'][x]){
			if(y !='id'){
				flag ++;
			}
		}
		if(flag > 0){
			arry_personWork.push(data['personWork'][x]);
		}
	}
	if(arry_personWork.length >0){
		json['personWork'] = arry_personWork ;
	}

	
	var arry_personLearn = new Array()
	for(var x in data['personLearn']){
		/*data['personLearn']['degree'] = $("#degree option:selected").val()*/
		flag = 0;
		
		for(var y in data['personLearn'][x]){
			
			if(y !='id'){
				flag ++;
			}
		}
		if(flag > 0){

			arry_personLearn.push(data['personLearn'][x]);
		}
	}
	if(arry_personLearn.length >0){
		json['personLearn'] =  arry_personLearn;
	}
	sendPostRequestByJsonObj(platformUrl.addPersonHr, json, savecbf);
});
function resemetValidate(input){
	var valType = input.attr("valType");
	var flag = true;
	//flag = beforeSubmit();
 	var value = input.val().trim();
	var regString = input.attr("regString");
	var textsIn = input.attr("textsIn");
	if(valType==''||valType=='undefined'||valType==undefined){
		return flag;
	}
	switch (valType) {
	case "MONEY":
		//flag = /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/.test(value);
		flag = /^(([1-9]+)(\.[0-9]{1,2})?|([0-9]+\.[0-9]{1,2}))$/.test(value);
		break;
	case "RATIO":
		flag = /(^[1-9][0-9]$)|(^100&)|(^[1-9]$)$/.test(value);
		break;
	case "COMPANYNAME":
		flag = value.length < 100;
		break;
	case "NAME":
		flag = value.length < 21;
		break;
	case "MEMBERSHIP":
		flag = /^[\u4e00-\u9fa5]{1,20}$/.test(value);
		break;
	case "CERTIFICATE":
		flag = /^[a-zA-Z0-9]{1,18}$/.test(value);
		break;
	case "NUM_CHAR_CH":
		flag = /[\u4e00-\u9fa5a-zA-Z\d]{1,50}/.test(value);
		break;
	case "IDENTITY":
		flag = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|x|X)$/.test(value);
		break;
	case "CHAR_CH":
		flag =  value.length < 100;
		break;		
	case "CHAR_CH_SYB":
		flag = /^[\u4e00-\u9fa5a-zA-Z]{1,100}$/.test(value);
		break;	
	case "TEL":
		flag = /0\d{2,3}-\d{5,9}|0\d{2,3}-\d{5,9}/.test(value);
		break;			
	case "MOBILE":
		flag = /^1[0-9]{10}$/.test(value);
		break;	
	case "MAIL":
		flag = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/.test(value);
		break;
	case "ONLYINT":
		flag = /^[0-9]*$/.test(value);
		break;
	case "ONLYZH":
		flag = /^[\u4e00-\u9fa5]+$/.test(value);
		break;	
	case "DATE":
		flag = /([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))/.test(value);
		break;	
		
	default:
		if(regString!=''){
			var regexp = new RegExp(regString);
			flag = regexp.test(value);
		}
		break;
	}
	if(!flag){
		layer.msg(input.attr("msg"));
	} 
	
	return flag;
}
function savecbf(data){
	if(data.result.status == "OK"){
		layer.msg("成功");
		$("a[data-close='close']").trigger("click");
		
		//location.reload();
		initTabProjectTeam();
	}else{
		layer.msg(data.result.message);
	}
}