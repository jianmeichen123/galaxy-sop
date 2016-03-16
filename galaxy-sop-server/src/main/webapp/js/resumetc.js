/*function test() {
	window.location.href=forwardWithHeader(sopContentUrl+"/galaxy/hrjl/resumetcc/");
}*/
//与完善简历相关js

//完善简历之外部项目保存
/*function inadd(){
	var id = $("id").val();
	var personId = 2;
//	alert(111)
	if(id == ''){
		alert(1)
		$("#personId").val(personId);
		sendPostRequestByJsonObj("/galaxy/hrInvest/addPersonInvest ", JSON.parse($("#up_in_form").serializeObject()), saveCallBack);
	}else(id !=''){
		alert("有值");
		sendPostRequestByJsonObj("/galaxy/hrInvest/UPersonInvest ", JSON.parse($("#up_in_form").serializeObject()), saveCallBack);
	}
}

//外部项目保存成功回调
function saveCallBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		//alert("error "+data.result.message);
		return;
	}
	alert("操作成功!");

}
//完善简历之个人简历保存
function update(){
	var Id = 15;
	alert(111)
	if(Id != ''){
		$("#Id").val(Id);
		sendPostRequestByJsonObj("/galaxy/hrjl/addPersonHr ", JSON.parse($("#up_person_form").serializeObject()), saveCallBackl);
	}
}

//个人简历保存成功回调
function saveCallBackl(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		//alert("error "+data.result.message);
		return;
	}
	alert("操作成功!");

}
//完善简历之外部项目回显数据 
function sp(){
	var id = $("id").val();
	var id = 4;
	alert(1)
	if(id !=''){
		$("#id").val(id);
		sendGetRequest(platformUrl.detailWorkInvest + id, {}, function(data){
			$("#companyName").text(data.entity.companyName);
			$("#investmentAmount").text(data.entity.investmentAmount);
			$("#shareRatio").text(data.entity.shareRatio);
			$("#telephone").text(data.entity.telephone);
			$("#acompanyName").text(data.entity.acompanyName);
			$("#ainvestmentAmount").val(data.entity.ainvestmentAmount);
			$("#ashareRatio").text(data.entity.ashareRatio);
			$("#atelephone").val(data.entity.atelephone);
		});
	}
	
	
}
*/

//完善简历修改版

function inadd(){
	alert(11)
//	var personId =  $("personId").val();
	var personId = 4;
	if(personId != ''){
		alert(2222)
		$("#personId").val(personId);
	sendPostRequestByJsonObj("/galaxy/personResumetc/addpersonResumetc ", JSON.parse($("#up_person_form").serializeObject()), saveCallBack);
	}
}
function saveCallBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		//alert("error "+data.result.message);
		return;
	}
	alert("操作成功!");

}

























