function addProjectPerson(){
	var _url=Constants.sopEndpointURL + '/galaxy/project/addProjectPerson';
	$.getHtml({
		url:_url,
		data:"",
		okback:function(_this){
			
		}
	});
	return false;
}

function lookPerson(){
	var uuid = $(this).attr("uuid");
}
function editPerson(){
	var uuid = $(this).attr("uuid");
}
function deletePerson(){
	var uuid = $(this).attr("uuid");
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/deleteProjectPerson/"+uuid+"/581ae7822b7c2b20f4a747bc", 
			null, 
			function(data){
		if(data.result.status == 'OK'){
			generatePersonInnerHtml(data.entityList);
			$("#person").val(data.entityList.length);
		}else{
			layer.msg(data.result.message);
		}
	});
}
function generatePersonInnerHtml(list){
	var innerHtml = "";
	$.each(list, function(i, o){
		if(o.tempStatus == '1'){
			innerHtml += '<tr>';
			innerHtml += '<td>'+o.personName+'</td>';
			innerHtml += '<td>'+o.personDuties+'</td>';
			innerHtml += '<td>'+o.personSex+'</td>';
			innerHtml += '<td>'+o.personBirthdayStr+'</td>';
			innerHtml += '<td>'+o.personTelephone+'</td>';
			innerHtml += '<td>';
			innerHtml += '<a uuid="'+o.uuid+'" class="meet_see blue" href="javascript:void(0);">查看</a>';
			innerHtml += '<a uuid="'+o.uuid+'" class="meet_edit blue" href="javascript:void(0);">编辑</a>';
			innerHtml += '<a uuid="'+o.uuid+'" class="meet_delete blue" href="javascript:void(0);">删除</a>';
			innerHtml += '</td>';
			innerHtml += '</tr>';
		}
	});
	$("#person-tbody").empty();
	$("#person-tbody").append(innerHtml);
	$(".meet_see").bind('click', lookPerson);
	$(".meet_edit").bind('click', editPerson);
	$(".meet_delete").bind('click', deletePerson);
}

function generatePersonEmptyInnerHtml(){
	var innerHtml = "<tr><td>暂无数据</td></tr>";
	$("#work-tbody").empty();
	$("#work-tbody").append(innerHtml);
}


function addPersonLearning(){
	var _url=Constants.sopEndpointURL + '/galaxy/project/addPersonLearning';
	$.getHtml({
		url:_url,
		data:"",
		okback:function(_this){
			
		}
	});
	return false;
}



function addProjectShares(){
	var _url=Constants.sopEndpointURL + '/galaxy/project/addProjectShares';
	$.getHtml({
		url:_url,
		data:"",
		okback:function(_this){
			
		}
	});
	return false;
}

function deleteLearn(){
	var puuid = $('input[name="uuid"]').val();
	var uuid = $(this).attr("uuid");
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/deleteProjectLearning/"+puuid+"/"+uuid+"/581ae7822b7c2b20f4a747bc", 
			null, 
			function(data){
		if(data.result.status == 'OK'){
			generateLearningInnerHtml(data.entityList);
			$("#person-learning").val(data.entityList.length);
		}else{
			layer.msg(data.result.message);
		}
	});
}



function generateLearningInnerHtml(list){
	var innerHtml = "";
	$.each(list, function(i, o){
		innerHtml += '<tr>';
		innerHtml += '<td>'+o.school+'</td>';
		innerHtml += '<td>'+o.major+'</td>';
		innerHtml += '<td>'+o.beginDateStr+' - '+o.overDateStr+'</td>';
		innerHtml += '<td>'+o.degree+'</td>';
		innerHtml += '<td><a uuid="'+o.uuid+'" class="blue operatorDelete" href="javascript:void(0);">删除</a></td>';
		innerHtml += '</tr>';
	});
	$("#learning-tbody").empty();
	$("#learning-tbody").append(innerHtml);
	$(".operatorDelete").bind('click', deleteLearn);
}

function generateLearningEmptyInnerHtml(){
	var innerHtml = "<tr><td>暂无数据</td></tr>";
	$("#learning-tbody").empty();
	$("#learning-tbody").append(innerHtml);
}





function addPersonWork(){
	var _url=Constants.sopEndpointURL + '/galaxy/project/addPersonWork';
	$.getHtml({
		url:_url,
		data:"",
		okback:function(_this){
			
		}
	});
	return false;
}
function deleteWork(){
	var puuid = $('input[name="uuid"]').val();
	var uuid = $(this).attr("uuid");
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/deleteProjectWork/"+puuid+"/"+uuid+"/581ae7822b7c2b20f4a747bc", 
			null, 
			function(data){
		if(data.result.status == 'OK'){
			generateWorkInnerHtml(data.entityList);
			$("#person-work").val(data.entityList.length);
		}else{
			layer.msg(data.result.message);
		}
	});
}
function generateWorkInnerHtml(list){
	var innerHtml = "";
	$.each(list, function(i, o){
		innerHtml += '<tr>';
		innerHtml += '<td>'+o.beginWorkStr+' - '+o.overWorkStr+'</td>';
		innerHtml += '<td>'+o.companyName+'</td>';
		innerHtml += '<td>'+o.workPosition+'</td>';
		innerHtml += '<td><a uuid="'+o.uuid+'" class="blue operatorDelete" href="javascript:void(0);">删除</a></td>';
		innerHtml += '</tr>';
	});
	$("#work-tbody").empty();
	$("#work-tbody").append(innerHtml);
	$(".operatorDelete").bind('click', deleteWork);
}

function generateWorkEmptyInnerHtml(){
	var innerHtml = "<tr><td>暂无数据</td></tr>";
	$("#work-tbody").empty();
	$("#work-tbody").append(innerHtml);
}
