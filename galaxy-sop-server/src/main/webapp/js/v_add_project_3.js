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

function generateLearningInnerHtml(list){
	var innerHtml = "";
	$.each(list, function(i, o){
		innerHtml += '<tr>';
		innerHtml += '<td>'+o.school+'</td>';
		innerHtml += '<td>'+o.major+'</td>';
		innerHtml += '<td>'+o.beginDateStr+' - '+o.overDateStr+'</td>';
		innerHtml += '<td>'+o.degree+'</td>';
		innerHtml += '<td><a class="blue" href="javascript:void(0)">删除</a></td>';
		innerHtml += '</tr>';
	});
	$("#learning-tbody").empty();
	$("#learning-tbody").append(innerHtml);
}

function generateLearningEmptyInnerHtml(){
	var innerHtml = "<tr><td>暂无数据</td></tr>";
	$("#learning-tbody").empty();
	$("#learning-tbody").append(innerHtml);
}
