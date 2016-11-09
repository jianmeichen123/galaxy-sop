<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="aclViewProject"
	value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }"
	scope="request" />
	
<c:set var="isEditable"
	value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}"
	scope="request" />
	
<% 
	String path = request.getContextPath(); 
%>


<input type="hidden" id="pid" name="id" value="${pid}" />

<c:if test="${aclViewProject==true}">
	<div class="member">
		<c:if test="${isEditable}">
			<div class="top clearfix">
				<!--按钮-->
				<div class="btnbox_f btnbox_f1 clearfix">
					<a id="add_person_btn" href="javascript:;" onclick="toAddPerson(/'/',/'/');" class="pubbtn bluebtn ico c4 add_prj add_profile" >添加</a>
					<!--  <a href="javascript:;" class="pubbtn bluebtn edit_profile" onclick="toSureMsg();">完善简历</a> -->
				</div>
			</div>
		</c:if>
		
		<!--表格内容-->
		<div class="tab-pane active commonsize" id="view">
			<!-- <table id="tablePerson"  data-height="555" data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" >
			</table> -->
		</div>

	</div>
</c:if>

<%-- <script src="<%=path %>/js/person.js"></script> --%>


<script>

	var proinfo = '${proinfo}';
	var proid = '${pid}';
	var pname = '${pname}';
	var interviewSelectRow = null;
	var projectId ='${pid}';
	var flag = '${flag}';
	var isCreatedByUser = "${fx:isCreatedByUser('project',pid) }";
	var isTransfering = "${fx:isTransfering(pid) }";
	


var personPool = {};
var personLearn = []; //学习经历
var personWork = []; //工作经历

var personSelectRow;



$(function(){
	getTabPerson();
	if(isTransfering == 'true'){
		$("#add_person_btn").addClass('limits_gray');
	}
});	

/** 
 * 添加团队成员
 */
var isEditOrCreatePerson = "c"; //判断   是新创建 person ：c  是新创建编辑person ： ne  是已有编辑 person ： oe 

//弹窗  --  初始化
function toAddPerson(id,index){
	
	if(id!=null && typeof(id)!='undefined' ){
		isEditOrCreatePerson = "oe";
		personSelectRow = $('#tablePerson').bootstrapTable('getRowByUniqueId', id);
	}

	$.getHtml({
		url : Constants.sopEndpointURL + "/galaxy/project/addProPerson", 
		data : "",//传递参数
		okback : function() {
			//初始化
			$("#person_project_id").val($("#pid").val());
			$("#person_pool_id").val(personSelectRow.id);

			var personPool = {};
			var personLearn = []; //学习经历
			var personWork = []; //工作经历
			
			if(isEditOrCreatePerson == "oe"){//获取数据
				console.log("编辑 ： "+personSelectRow);
			
				$("#person_pool_id").val(personSelectRow.id);
				/* 
				type  name="personName"  name="personDuties"  name="personBirthdayStr"  name="personTelephone"
				radio name="personSex"
				 */	
				$("[name='personName']").val(personSelectRow.personName);
				$("[name='personDuties']").val(personSelectRow.personDuties);
				$("[name='personBirthdayStr']").val(personSelectRow.personBirthdayStr);
				$("[name='personTelephone']").val(personSelectRow.personTelephone);
				
				$("input:radio[name='personSex'][value='"+personSelectRow.personSex +"']").attr("checked","checked"); 
			}
			
			tableShow("per_learning_table");
			tableShow("per_work_table");
		}

	});
}

/** 
 * 团队成员学习
 * 团队成员工作
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
        }
	});
}


/*
 * 新建  编辑  团队成员
 */
function savePerson() {
	if (beforeSubmit()) {
		if ($("#person_project_id").val() && $("#person_project_id").val() != '') {
			
			var personResumetc = {};
			personPool = JSON.parse($("#person_form").serializeObject());
			personResumetc.personPool = personPool;
			if(isEditOrCreatePerson == "c"){
				//var options = $('#data-table-partnerkpi').bootstrapTable('getOptions'); $table.bootstrapTable('getData');
				personResumetc.personLearn = $('#per_learning_table').bootstrapTable('getData');
				personResumetc.personWork = $('#per_work_table').bootstrapTable('getData');
				sendPostRequestByJsonObj(platformUrl.addPerson, personResumetc, savePersonCallBack);
			}else{
				sendPostRequestByJsonObj(Constants.sopEndpointURL + '/galaxy/project/upp', personPool, savePersonCallBack);
			}
			
		}else{
			layer.msg("项目id缺失，保存失败"};
		}
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
		$('#tablePerson').bootstrapTable('refresh');
	}
}





function getTabPerson(){
	var html='<table id="tablePerson"  data-height="555" data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" ></table>';
	$("#view").html(html);
	var $table = $('#tablePerson');
    $table.bootstrapTable({
	    url: platformUrl.projectPersonList,
	    dataType: "json",
	    pagination: true, //分页
	    search: false, //显示搜索框
	    pageList: [10,20,30],
	    queryParamsType: 'size|page',
	    queryParams: function(params){params.projectId=projectId; return params;},
	    sidePagination: "server", //服务端处理分页
        
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
	
					valign : 'middle'
				}, /* {
					title : '年龄',
					field : 'personAge',
	
					valign : 'middle'
				}, {
					title : '最高学历',
					field : 'highestDegree',
	
					valign : 'middle',
					formatter : 'highestDegreeFormat'
				}, {
					title : '工作年限',
					field : 'workTime',
	
					valign : 'middle'
				}, */ {
					title : '电话',
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
	$table.bootstrapTable('refresh');
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
	/* viewSelectIndex = index;
	viewSelectRowId = selectRowId;
	interviewSelectRow = $('#pre_pro_view_table').bootstrapTable('getRowByUniqueId', selectRowId); */
	
	var toShow = "<a href=\"javascript:;\" class=\"blue\" onclick=\"showviewdetail('"+row.id+"','" + index + "')\" >查看</a>;
	var toEdit = "<a href=\"javascript:;\" class=\"blue\" onclick=\"toAddPerson('"+row.id+"','" + index + "')\" >编辑</a>;
	var toDelete = "<a href=\"javascript:;\" class=\"blue\" onclick=\"showviewdetail('"+row.id+"','" + index + "')\" >删除</a>;
	
	
	
	
	 else {
			$table.find("tr span").click(function() {
				var id = $(this).data('id');
				if ($(this).hasClass('resume')) {
					tiaozhuan(id);
				} else if ($(this).hasClass('edit')) {
					updatePer(id);
				} else if ($(this).hasClass('del')) {
					deletePer(id);
				}
			});
		}
	 
	 
	var content = 
	if (isCreatedByUser == 'true' && isTransfering == 'false') {
		content += '<span class="edit" data-id="'+row.id+'">编辑</span>';
		content += '<span class="del" data-id="'+row.id+'">删除</span>';
	}

	return content;
}










//查看 个人简历
function tiaozhuan(id) {
	//创建者可编辑
	var url = platformUrl.personHHr;
	if (isCreatedByUser == "true") {
		url = platformUrl.personHr;
	}
	$.getHtml({
		url : url + "?personId=" + id,
		data : "",//传递参数
		okback : function() {
			$(".resumetc .tabtable").tabchange2();
		}//模版反回成功执行	
	});

}



/**
 * 删除团队成员
 * @param id
 */
function deletePer(id, url) {
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

	
	



	


	function reloadJS() {
		loadJs('
<%=request.getContextPath() %>/js/axure.js'); 
	}
</script>

