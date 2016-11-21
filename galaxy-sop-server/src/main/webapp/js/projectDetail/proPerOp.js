

function getTabPerson(){
	var html='<table id="tablePerson"  data-height="555" data-id-field="id" data-unique-id="id" data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" ></table>';
	$("#view").html(html);
	var $table = $('#tablePerson');
    $table.bootstrapTable({
	    url: platformUrl.projectPersonList,
	    queryParamsType: 'size|page',
	    dataType: "json",
	    pagination: true, //分页
	    search: false, //显示搜索框
	    pageList: [10,20,30],
	    sidePagination: "server", //服务端处理分页
	    queryParams: function(params){
	    	params.projectId=projectId; 
	    	//var pn = get_del_pageNum("tablePerson",0);
	    	//params.pageNum = pn;
	    	console.log("params "+ JSON.stringify(params));
	    	return params;
	    },
		columns : [ {
					title : '姓名',
					field : 'personName',
	
					valign : 'middle',
					'class' : 'personName',
					formatter : 'personName'
				}, {
					title : '当前职务',
					field : 'personDuties',
	
					valign : 'middle',
					formatter : 'personDuties'
				}, {
					title : '性别',
					field : 'personSex',
	
					valign : 'middle',
					formatter : 'sexFormat'
				}, {
					title : '出生年月',
					field : 'personBirthday',
	
					valign : 'middle',
					formatter : 'date_str_format'
				}, {
					title : '手机号码',
					field : 'personTelephone',
	
					valign : 'middle'
				}, {
					title : '操作',
					field : 'id',
	
					formatter : 'proPerOpFormat'
				} ],

		onLoadSuccess : function(data) {
			if (data.pageList.total > 0 && isTransfering == 'true') {
				$table.find("tr span").parent().addClass('limits_gray');
			}
		}
	});
    
	//$table.bootstrapTable('refresh');
}

	
function remark_format(value, row, index) {
	if(value){
		if(getLength(value)>10){
			var cutString = cutStr_z(10,value);
			value = "<label title='"+value+"'>" + cutString + "...</label>";
		}
	}else{
		value =  "-";
	}
	return value;
}


function sexFormat(value, row, index) {
	if (row.personSex == 0) {
		return "男";
	} else if (row.personSex == 1) {
		return "女";
	} else {
		return "-";
	}
}

function date_str_format(value, row, index) {
	if(row.personBirthdayStr && row.personBirthdayStr !=null){
		return row.personBirthdayStr;
	}else if(value){
		return new Date(value).format("yyyy-MM-dd");
	}
	return "-";
}



function highestDegreeFormat(value, row, index) {
	if (row.highestDegree == 1) {
		return "高中";
	} else if (row.highestDegree == 2) {
		return "大专";
	} else if (row.highestDegree == 3) {
		return "本科";
	} else if (row.highestDegree == 4) {
		return "硕士";
	} else if (row.highestDegree == 5) {
		return "MBA";
	} else if (row.highestDegree == 6) {
		return "博士";
	} else if (row.highestDegree == 7) {
		return "其他";
	} else {
		return "-";
	}
}

function personName(value, row, index) {
	var str = row.personName;
	if (str.length > 20) {
		subStr = str.substring(0, 20);
		var options = "<label title='"+str+"'>" + subStr + "</label>";
		return options;
	} else {
		var options = "<label title='"+str+"'>" + str + "</label>";
		return options;
	}
}

function personDuties(value, row, index) {
	var str = row.personDuties;
	if (str.length > 10) {
		subStr = str.substring(0, 10);
		var options = "<label title='"+str+"'>" + subStr + "</label>";
		return options;
	} else {
		var options = "<label title='"+str+"'>" + str + "</label>";
		return options;
	}
}

function proPerOpFormat(value, row, index) {
	
	var toShow = "<a href=\"javascript:;\" class=\"blue\" onclick=\"toProPerInfoView('"+row.id+"')\" >查看</a>";
	var toEdit = "&nbsp; <a href=\"javascript:;\" class=\"blue\" onclick=\"toAddPerson('"+row.id+"','" + index + "')\" >编辑</a>";
	var toDelete = "&nbsp; <a href=\"javascript:;\" class=\"blue\" onclick=\"deletePer('"+row.id+"')\" >删除</a>";
	
	var content = toShow
	if (isCreatedByUser == 'true' && isTransfering == 'false') {
		content += toEdit;
		content += toDelete;
	}

	return content;
}

//团队成员表格title提示
function school(value, row, index) {
	var options = "<label title='"+row.school+"'>" + row.school + "</label>";
	return options;
}
function major(value, row, index) {
	var options = "<label title='"+row.major+"'>" + row.major + "</label>";
	return options;
}
function companyName(value, row, index) {
	var options = "<label title='"+row.companyName+"'>" + row.companyName + "</label>";
	return options;
}
function workPosition(value, row, index) {
	var options = "<label title='"+row.workPosition+"'>" + row.workPosition + "</label>";
	return options;
}
/**
 * 查看 团队成员
 * @param id
 */
function toProPerInfoView(id) {
	if(!(id && id!=null && typeof(id)!='undefined' )){
		layer.msg("页面信息获取失败");
		return;
	}
	
	var _name = "查看团队成员";
	$.getHtml({
		url : Constants.sopEndpointURL + "/galaxy/project/toProPerView", 
		data : "",//传递参数
		okback : function() {
			$("#popup_name").html(_name);
			
			$("#pool_id").val(id);
			$("input[name='personId']").val(id);
			
			tableShow("pro_per_info");
			tableShow("pro_per_learn_table");
			tableShow("pro_per_work_table");
		}
	});
}



/**
 * 删除团队成员
 * @param id
 */
function deletePer(id) {
	layer.confirm('是否删除事项?', {
		btn : [ '确定', '取消' ]
	}, function(index, layero) {
		var projectId = $("#pid").val();
		var url = platformUrl.deletePPerson + id + "/" + projectId;
		if (projectId != '') {
			$("#projectId").val(projectId);
			sendGetRequest(url, '', savePersonCallBack);
			//removePop1();
			layer.close(index);
			layer.msg("删除成功");
		}
	}, function(index) {
	});
}





//成员添加 编辑时   电话号码 校验处理
function radio_isContacts_tel(isContactsV){
	var phone = $("input[name='personTelephone']");
	if (isContactsV == 0 || isContactsV == '0') {
		$("input[name='personTelephone']").attr({placeholder:"请输入手机号码",allowNULL:"",valtype:"MOBILE",msg:"<font color=red>*</font>手机号码格式不正确"});
	} else if (isContactsV == 1 || isContactsV == '1') {
		$("input[name='personTelephone']").attr('allowNULL','yes').removeAttr('placeholder').removeAttr('msg');
	} 
}


/** 
 * 添加 编辑 团队成员
 */

 var personSelectRow;
 
var isEditOrCreatePerson = "c"; //判断   是新创建 person ：c  是新创建编辑person ： ne  是已有编辑 person ： oe 

//弹窗  --  初始化
function toAddPerson(id,index){
	if(id && id!=null && typeof(id)!='undefined' ){
		isEditOrCreatePerson = "oe";
		personSelectRow = $('#tablePerson').bootstrapTable('getRowByUniqueId', id);
		console.log("person select : " + JSON.stringify(personSelectRow));
	}else{
		isEditOrCreatePerson = "c";
		personSelectRow = null;
	}

	$.getHtml({
		url : Constants.sopEndpointURL + "/galaxy/project/addProPerson", 
		data : "",//传递参数
		okback : function() {
			var _name = "添加团队成员";
			
			//初始化
			$("#person_project_id").val($("#pid").val());
			
			if(isEditOrCreatePerson == "oe"){//获取数据
				_name = "编辑团队成员";
			
				$("#person_pool_id").val(personSelectRow.id);
				$("[name='personId']").val(personSelectRow.id);
				
				$("#person_form [name='personName']").val(personSelectRow.personName);
				$("#person_form [name='personDuties']").val(personSelectRow.personDuties);
				
				if(personSelectRow.personBirthdayStr && personSelectRow.personBirthdayStr !=null){
					$("#person_form [name='personBirthdayStr']").val(personSelectRow.personBirthdayStr);
				}else if(personSelectRow.personBirthday && personSelectRow.personBirthday !=null){
					var str = new Date(personSelectRow.personBirthday).format("yyyy-MM-dd");
					$("#person_form [name='personBirthdayStr']").val(str);
				}
				$("#person_form [name='personTelephone']").val(personSelectRow.personTelephone);
				$("#person_form [name='remark']").val(personSelectRow.remark);
				
				$("input:radio[name='personSex'][value='"+personSelectRow.personSex +"']").attr("checked","checked"); 
				$("input:radio[name='isContacts'][value='"+personSelectRow.isContacts +"']").attr("checked","checked"); 
				radio_isContacts_tel(personSelectRow.isContacts);
			}
			
			tableShow("per_learning_table");
			tableShow("per_work_table");
			
			$("#popup_name").html(_name);
		}
	});
}
/** 
 * 团队成员学习
 * 团队成员工作
 deleteIndex
 */
function tableShow(tableId){
	$('#'+tableId).bootstrapTable({
		queryParamsType: 'size|page', // undefined
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: false,
		clickToSelect: true,
        search: false,
        onLoadSuccess: function (data) {
        	$('#'+tableId).bootstrapTable('hideColumn', 'deleteIndex');
        	if(tableId == 'per_learning_table'){
        		learn_code_index = 0;
        	}else if(tableId == 'per_work_table'){
        		work_code_index = 0;
        	}
        }
	});
}


/*
 * 新建  编辑  团队成员
 */

function savePerson(){

	if (beforeSubmitScroll("addPerson_all")) {
		var learnList = $('#per_learning_table').bootstrapTable('getData');
		var workList = $('#per_work_table').bootstrapTable('getData');
		
		if(!learnList || learnList.length == 0){
			$("#learn-tip").css("display","block");
			return;
		}else{
			var learnList_hidden = $("#per_learning_table tbody tr:hidden");
			if(learnList_hidden && learnList_hidden.length > 0){
				var allL = learnList.length;
				var hiddenL = learnList_hidden.length;
				if(allL == hiddenL){
					$("#learn-tip").css("display","block");
					return;
				}
			}
		}
		
		if(!workList || workList.length == 0){
			$("#work-tip").css("display","block");
			return;
		}else{
			var workList_hidden = $("#per_work_table tbody tr:hidden");
			if(workList_hidden && workList_hidden.length > 0){
				var allL = workList.length;
				var hiddenL = workList_hidden.length;
				if(allL == hiddenL){
					$("#learn-tip").css("display","block");
					return;
				}
			}
		}
		
		savePerson_do(learnList,workList);
	}
}
function savePerson_do(learnList,workList) {
	if ($("#person_project_id").val() && $("#person_project_id").val() != '') {

		var personPool = JSON.parse($("#person_form").serializeObject());
		/*
		var learnList = $('#per_learning_table').bootstrapTable('getData');
		var workList = $('#per_work_table').bootstrapTable('getData');
		if(!learnList || learnList.length == 0){
			layer.msg("学历背景不能为空");
			return;
		}
		if(!workList || workList.length == 0){
			layer.msg("工作履历不能为空");
			return;
		}*/
		
		/*if(isEditOrCreatePerson == "c"){
			personPool.id = null;
			personPool.plc = learnList;
			personPool.pwc = workList;
			sendPostRequestByJsonObj(platformUrl.addPerson, personPool, savePersonCallBack);
		}else{
			sendPostRequestByJsonObj(Constants.sopEndpointURL + '/galaxy/project/upp', personPool, savePersonCallBack);
		}*/
		if(isEditOrCreatePerson == "c"){
			personPool.id = null;
		}
		personPool.plc = learnList;
		personPool.pwc = workList;
		sendPostRequestByJsonObj(platformUrl.addPerson, personPool, savePersonCallBack);
	}else{
		layer.msg("项目id缺失，保存失败");
	}
}
//保存成功回调
function savePersonCallBack(data) {
	//启用滚动条
	$(document.body).css({
		"overflow-x" : "auto",
		"overflow-y" : "auto"
	});
	
	var result = data.result.status;
	if (result == "ERROR") { //OK, ERROR
		layer.msg(data.result.message);
		//return;
	}else{
		$("#popbg,#powindow").remove();
		/* var projectId = $("#pid").val();
		getTabPerson(projectId); */
		$('#tablePerson').bootstrapTable('querySearch');
	}
}



//TODO 学习
var learn_code_index = 0;

function deleteIndex_Format(value, row, index) {
	if(row.deleteIndex){
		return row.deleteIndex;
	}
	learn_code_index = learn_code_index + 1;
	row.deleteIndex = learn_code_index;
	return learn_code_index;
}

function learn_TimeFormat(value, row, index) {
	var bstr;
	var estr;
	if(row.beginDateStr){
		bstr = row.beginDateStr;
	}else if(row.beginDate){
		bstr = row.beginDate;
		bstr = new Date(bstr).format("yyyy-MM-dd");
	}else{
		bstr="-";
	}
	
	if(row.overDateStr){
		estr = row.overDateStr;
	}else if(row.overDate){
		estr = row.overDate;
		estr = new Date(estr).format("yyyy-MM-dd");
	}else{
		estr="-";
	}
	
	//return bstr +" - "+ estr ;
	var options = "<label title='"+bstr +' - '+ estr+"'>" + bstr +' - '+ estr + "</label>";
	return options;
}

function pro_learning_format(value, row, index) {
	var toEdit = "<a href=\"javascript:;\" class=\"blue\" onclick=\"toAddPersonLearning('" + row.deleteIndex + "')\" >编辑</a>";
	var toDelete = "&nbsp;<a href=\"javascript:;\" class=\"blue\" onclick=\"deleteLearn('"+ row.deleteIndex +"')\" >删除</a>";
	
	return toEdit + toDelete;
}


/*
 * 新建 :c 、 编辑:e    学习经历 - 弹窗
 */
var learnSelectRow;
var isCreatOrEditLearn = "c";  //判断   是新创建 person ：c  是编辑person ： e  是新创建编辑person ： ne  是已有编辑 person ： oe 

//弹窗  编辑 、保存  --  初始化
function toAddPersonLearning(selectIndex){
	
	if(selectIndex && selectIndex!=null && typeof(selectIndex)!='undefined' ){ //判断是编辑模式
		learnSelectRow = $('#per_learning_table').bootstrapTable('getRowByUniqueId', selectIndex);
		isCreatOrEditLearn = "e";
		
		console.log("learn select : " + JSON.stringify(learnSelectRow));
	}else{
		isCreatOrEditLearn = "c";
		learnSelectRown = null;
	}
	
	var _url=Constants.sopEndpointURL + '/galaxy/project/addProPerLearning';
	var _name= "添加学历背景";
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#learn_person_Id").val($("#person_pool_id").val());

			if(isCreatOrEditLearn == "e"){//获取数据
				_name= "编辑学历背景";
				$("#learn_id").val(learnSelectRow.id);
				
				if(learnSelectRow.beginDateStr && learnSelectRow.beginDateStr !=null){
					$("#add_person_learning [name='beginDateStr']").val(learnSelectRow.beginDateStr);
				}else if(learnSelectRow.beginDate && learnSelectRow.beginDate !=null){
					var str = new Date(learnSelectRow.beginDate).format("yyyy-MM-dd");
					$("#add_person_learning [name='beginDateStr']").val(str);
				}
				 
			 	if(learnSelectRow.overDateStr && learnSelectRow.overDateStr !=null){
					$("#add_person_learning [name='overDateStr']").val(learnSelectRow.overDateStr);
				}else if(learnSelectRow.overDate && learnSelectRow.overDate !=null){
					var str = new Date(learnSelectRow.overDate).format("yyyy-MM-dd");
					$("#add_person_learning [name='overDateStr']").val(str);
				}
				
				$("#add_person_learning [name='school']").val(learnSelectRow.school);
				$("#add_person_learning [name='major']").val(learnSelectRow.major);
				
				$("select[name='degree'] option[value='"+learnSelectRow.degree+"']").attr("selected", "selected"); 
				//$(" select option[value='"+tem+"']").attr("select","selected");  
			}
			
			$("#qualifications_popup_name").html(_name);
		}
	});
	return false;
}

	
// 新建  -- 编辑   学习经历
function savePersonLearning(){
	var learn = JSON.parse($("#add_person_learning").serializeObject());
	
	console.log("save learn : " + JSON.stringify(learn));
	
	var id = learn.id;
	var perondId = learn.personId;
	
	if(!(id && id!=null && typeof(id)!='undefined')){
		learn.id = null;
	}
	if(!(perondId && perondId!=null && typeof(perondId)!='undefined')){
		learn.personId = null;
	}
	
	/*if(perondId && perondId!=null && typeof(perondId)!='undefined'){  //有人员信息，编辑 保存 数据库
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"/galaxy/project/saveOrEditProPerLearn",learn,function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}else{
				learn.id = data.id;
				layer.msg("保存成功", {time : 500});
				learnTableRefresh(learn);
			}
		});
	}else{
		learnTableRefresh(learn);
	}*/
	learnTableRefresh(learn);
	//去除弹层
	$(".qualificationstc").find("[data-close='close']").click();
}


function learnTableRefresh(newDataRow){
	if(isCreatOrEditLearn == "e"){
		if(newDataRow.id && newDataRow.id != null){
			newDataRow.isEditOrCreate = 1;
		}
		newDataRow.deleteIndex = learnSelectRow.deleteIndex;
		$('#per_learning_table').bootstrapTable('updateRow', {index: learnSelectRow.deleteIndex-1, row: newDataRow});
		//$('#per_learning_table').bootstrapTable('updateByUniqueId', {deleteIndex: learnSelectRow.deleteIndex, row: newDataRow});
	}else{
		learn_code_index = learn_code_index+1;
		newDataRow.deleteIndex = learn_code_index;
		$('#per_learning_table').bootstrapTable('append', newDataRow);
	}
}


//删除
function deleteLearn(selectIndex){
	layer.confirm(
			'确定要删除数据？',
			{btn : [ '确定', '取消' ]}, 
			function(){
				
				if(selectIndex && selectIndex!=null && typeof(selectIndex)!='undefined' ){
					learnSelectRow = $('#per_learning_table').bootstrapTable('getRowByUniqueId', selectIndex);
				}else{
					layer.msg("选择判断错误");
					return;
				}
				
				if(learnSelectRow && learnSelectRow!=null && typeof(learnSelectRow)!='undefined' 
						&& learnSelectRow.id!=null && typeof(learnSelectRow.id)!='undefined' ){
					
					learnSelectRow.isEditOrCreate = 2;
					$('#per_learning_table').bootstrapTable('hideRow', {index: selectIndex-1, uniqueId: selectIndex});
					layer.msg('删除成功');
					//全部删除后暂无数据样式
					var learnList = $('#per_learning_table').bootstrapTable('getData');
					var learnList_hidden = $("#per_learning_table tbody tr:hidden");
					if(learnList_hidden && learnList_hidden.length > 0){
						var allL = learnList.length;
						var hiddenL = learnList_hidden.length;
						if(allL == hiddenL){
							var noData='<tr class="no-records-found"><td colspan="5" style="text-align:center !important;color:#bbb;border:0;line-height:32px !important" class="noinfo no_info01"><label class="no_info_icon_xhhl">没有找到匹配的记录</label></td></tr>'
							$("#per_learning_table tbody").append(noData);
						}
					}
					/*sendGetRequest(Constants.sopEndpointURL + "/galaxy/project/deleteProPerLearning/"+learnSelectRow.id, null, function(data){
						var result = data.result.status;
						if(result == "ERROR"){ //OK, ERROR
							layer.msg(data.result.message);
							return;
						}
					});*/
				}else{
					$('#per_learning_table').bootstrapTable('removeByUniqueId', selectIndex);
				//$('#per_learning_table').bootstrapTable('remove', {field: 'deleteIndex', values: selectIndex});
				}
				$(".layui-layer-btn1").click();
			}, 
			function(index) {
			}
		);
}
function deleteLearn_do(selectIndex){
	
	if(selectIndex && selectIndex!=null && typeof(selectIndex)!='undefined' ){
		learnSelectRow = $('#per_learning_table').bootstrapTable('getRowByUniqueId', selectIndex);
	}else{
		layer.msg("选择判断错误");
		return;
	}
	
	if(learnSelectRow && learnSelectRow!=null && typeof(learnSelectRow)!='undefined' 
			&& learnSelectRow.id!=null && typeof(learnSelectRow.id)!='undefined' ){
		
		learnSelectRow.isEditOrCreate = 2;
		$('#per_learning_table').bootstrapTable('hideRow', {index: selectIndex-1, uniqueId: selectIndex});
		
		/*sendGetRequest(Constants.sopEndpointURL + "/galaxy/project/deleteProPerLearning/"+learnSelectRow.id, null, function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}
		});*/
	}else
		$('#per_learning_table').bootstrapTable('removeByUniqueId', selectIndex);
	//$('#per_learning_table').bootstrapTable('remove', {field: 'deleteIndex', values: selectIndex});
}






//TODO 工作

/*
 * 新建 :c 、 编辑:e    工作经历 - 弹窗
 */

var work_code_index = 0;

function work_deleteIndex_Format(value, row, index) {
	if(row.deleteIndex){
		return row.deleteIndex;
	}
	work_code_index = work_code_index + 1;
	row.deleteIndex = work_code_index;
	return work_code_index;
}


function work_TimeFormat(value, row, index) {
	var bstr;
	var estr;
	
	if(row.beginWorkStr){
		bstr = row.beginWorkStr;
	}else if(row.beginWork){
		bstr = row.beginWork;
		bstr = new Date(bstr).format("yyyy-MM-dd");
	}else{
		bstr="-";
	}
	
	if(row.overWorkStr){
		estr = row.overWorkStr;
	}else if(row.overWork){
		estr = row.overWork;
		estr = new Date(estr).format("yyyy-MM-dd");
	}else{
		estr = "-";
	}
	
	//return bstr +" - "+ estr ;
	var options = "<label title='"+bstr +' - '+ estr+"'>" + bstr +' - '+ estr + "</label>";
	return options;
}

function pro_work_format(value, row, index) {
	var toEdit = "<a href=\"javascript:;\" class=\"blue\" onclick=\"toAddPersonWork('" + row.deleteIndex + "')\" >编辑</a>";
	var toDelete = "&nbsp;<a href=\"javascript:;\" class=\"blue\" onclick=\"deleteWork('"+ row.deleteIndex +"')\" >删除</a>";
	
	return toEdit + toDelete;
}


var workSelectRow;
var isCreatOrEditWork = "c";  //判断   是新创建 person ：c  是新创建编辑person ： ne  是已有编辑 person ： oe 

//弹窗  --  初始化
function toAddPersonWork(selectIndex){
	
	if(selectIndex && selectIndex!=null && typeof(selectIndex)!='undefined' ){ //判断是编辑模式
		workSelectRow = $('#per_work_table').bootstrapTable('getRowByUniqueId', selectIndex);
		isCreatOrEditWork = "e";
	}else{
		isCreatOrEditWork = "c";
		workSelectRow = null;
	}
	
		
	
	var _url=Constants.sopEndpointURL + '/galaxy/project/addProPerWork';
	var _name= "添加工作履历";
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#work_person_Id").val($("#person_pool_id").val());
			
			if(isCreatOrEditWork == "e"){//获取数据
				_name= "编辑工作履历";
				
				$("#work_id").val(workSelectRow.id);
				
				if(workSelectRow.beginWorkStr && workSelectRow.beginWorkStr !=null){
					$("#add_person_work [name='beginWorkStr']").val(workSelectRow.beginWorkStr);
				}else if(workSelectRow.beginWork && workSelectRow.beginWork !=null){
					var str = new Date(workSelectRow.beginWork).format("yyyy-MM-dd");
					$("#add_person_work [name='beginWorkStr']").val(str);
				}
				 
			 	if(workSelectRow.overWorkStr && workSelectRow.overWorkStr !=null){
					$("#add_person_work [name='overWorkStr']").val(workSelectRow.overWorkStr);
				}else if(workSelectRow.overWork && workSelectRow.overWork !=null){
					var str = new Date(workSelectRow.overWork).format("yyyy-MM-dd");
					$("#add_person_work [name='overWorkStr']").val(str);
				}
				
				$("#add_person_work [name='companyName']").val(workSelectRow.companyName);
				$("#add_person_work [name='workPosition']").val(workSelectRow.workPosition);
			}
			
			$("#qualifications_popup_name").html(_name);
		}
	});
	return false;
}

//新建  -- 编辑   工作经历
function savePersonWork(){
	var work = JSON.parse($("#add_person_work").serializeObject());
	
	console.log("save work : " + JSON.stringify(work));
	
	var id = work.id;
	var perondId = work.personId;

	if(!(id && id!=null && typeof(id)!='undefined')){
		work.id = null;
	}
	if(!(perondId && perondId!=null && typeof(perondId)!='undefined')){
		work.personId = null;
	}
	
	/*if(perondId && perondId!=null && typeof(perondId)!='undefined'){  //有人员信息，编辑 保存 数据库
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"/galaxy/project/saveOrEditProPerWork",work,function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}else{
				work.id = data.id;
				layer.msg("保存成功", {time : 500});
				workTableRefresh(work);
			}
		});
	}else{
		workTableRefresh(work);
	}*/
	workTableRefresh(work);
	//去除弹层
	$(".qualificationstc").find("[data-close='close']").click();
}

function workTableRefresh(newRowData){
	if(isCreatOrEditWork == "e"){
		if(newRowData.id && newRowData.id != null){
			newRowData.isEditOrCreate = 1;
		}
		newRowData.deleteIndex = workSelectRow.deleteIndex;
		$('#per_work_table').bootstrapTable('updateRow', {index: workSelectRow.deleteIndex-1, row: newRowData});
	}else{
		work_code_index = work_code_index+1;
		newRowData.deleteIndex = work_code_index;
		$('#per_work_table').bootstrapTable('append', newRowData);
	}
}




//删除
function deleteWork(selectIndex){
	layer.confirm(
			'确定要删除数据？',
			{btn : [ '确定', '取消' ]}, 
			function(){
				if(selectIndex && selectIndex!=null && typeof(selectIndex)!='undefined' ){ 
					workSelectRow = $('#per_work_table').bootstrapTable('getRowByUniqueId', selectIndex);
				}else{
					layer.msg("选择判断错误");
					return;
				}
				
				if(workSelectRow && workSelectRow!=null && typeof(workSelectRow)!='undefined' 
					&& workSelectRow.id!=null && typeof(workSelectRow.id)!='undefined' ){
					
					workSelectRow.isEditOrCreate = 2;
					$('#per_work_table').bootstrapTable('hideRow', {index: selectIndex-1, uniqueId: selectIndex});
					layer.msg('删除成功');
					//全部删除后暂无数据样式
					var learnList = $('#per_work_table').bootstrapTable('getData');
					var learnList_hidden = $("#per_work_table tbody tr:hidden");
					if(learnList_hidden && learnList_hidden.length > 0){
						var allL = learnList.length;
						var hiddenL = learnList_hidden.length;
						if(allL == hiddenL){
							var noData='<tr class="no-records-found"><td colspan="4" style="text-align:center !important;color:#bbb;border:0;line-height:32px !important" class="noinfo no_info01"><label class="no_info_icon_xhhl">没有找到匹配的记录</label></td></tr>'
							$("#per_work_table tbody").append(noData);
						}
					}
					/*sendGetRequest(Constants.sopEndpointURL + "/galaxy/project/deleteProPerWork/"+workSelectRow.id, null, function(data){
						var result = data.result.status;
						if(result == "ERROR"){ //OK, ERROR
							layer.msg(data.result.message);
							return;
						}
					});*/
				}else{
					$('#per_work_table').bootstrapTable('removeByUniqueId', selectIndex);
				}
				$(".layui-layer-btn1").click();
			}, 
			function(index) {
			}
		);
	
	/*layer.confirm(
			'确定要删除数据？',
			deleteWork_do(selectIndex)
		);*/
}
function deleteWork_do(selectIndex){

	if(selectIndex && selectIndex!=null && typeof(selectIndex)!='undefined' ){ 
		workSelectRow = $('#per_work_table').bootstrapTable('getRowByUniqueId', selectIndex);
	}else{
		layer.msg("选择判断错误");
		return;
	}
	
	if(workSelectRow && workSelectRow!=null && typeof(workSelectRow)!='undefined' 
		&& workSelectRow.id!=null && typeof(workSelectRow.id)!='undefined' ){
		
		workSelectRow.isEditOrCreate = 2;
		$('#per_work_table').bootstrapTable('hideRow', {index: selectIndex-1, uniqueId: selectIndex});
		
		/*sendGetRequest(Constants.sopEndpointURL + "/galaxy/project/deleteProPerWork/"+workSelectRow.id, null, function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}
		});*/
	}else
		$('#per_work_table').bootstrapTable('removeByUniqueId', selectIndex);
}




function beTimeCompare(btime,etime){
	if(btime && etime){
		var startTime = (new Date(btime)).getTime();		
		var endTime = (new Date(etime)).getTime();
		if(startTime > endTime){
			layer.msg("开始时间不能大于结束时间");
			return false;
		}
		//兼容safari
		if(btime>etime){
			layer.msg("开始时间不能大于结束时间");
			return false;
		}
	}
	return true;
}


function delHtmlTag(str)
{
	if(str){
		return str.replace(/<[^>]+>/g,"");//去掉所有的html标记
	}
}

function cutStr_z(theNum,theOldStr){
	var leaveStr = "";
	var leng = getLength(theOldStr);
	if(theNum >= leng){
		return theOldStr;
	}else{
		var cont = 0;
		for (var i = 0; i < theOldStr.length; i++) {
			if (theOldStr.charCodeAt(i) >= 0x4e00 && theOldStr.charCodeAt(i) <= 0x9fa5){ 
				cont += 2;
			}else {
				cont++;
			}
			if(cont >= theNum){
				break;
			}
			leaveStr += theOldStr.charAt(i);
		}
		return leaveStr;
	}
	return theOldStr;
}


