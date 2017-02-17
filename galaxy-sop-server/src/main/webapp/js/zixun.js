

/**
 * 截取、格式化
 */
function zixun_length_Format(value){  
	var old = value;
	var cut = zixun_cutStr(35,old,'...');
	var hasCut = getLength(value) > 35;
	//var info = "<spance class=\"blue\"  >"+cut+"</spance>";
	if(hasCut && hasCut == true){
		cut = "<spance  title='"+old+"' >"+cut+"</spance>";
	}
	return cut;
}


/**
 *  字符阶段处理
 *  theNum : 要显示的字节数（字符*2）
 *  theOldStr : 需要长度处理的字符
 *  theEndStr ：截断后附加的字符（ '...'）
 */
function zixun_cutStr(theNum,theOldStr,theEndStr){
	if( theOldStr == null || typeof(theOldStr) == 'undefined' || theOldStr ==''){
		return '-';
	}
	var leaveStr = "";
	var leng = getLength(theOldStr);
	if(theNum < leng){
		var cont = 0;
		for (var i = 0; i < theOldStr.length; i++) {
			if (theOldStr.charCodeAt(i) >= 0x4e00 && theOldStr.charCodeAt(i) <= 0x9fa5){ 
				cont += 2;
			}else {
				cont++;
			}
			if(cont > theNum){
				break;
			}
			leaveStr += theOldStr.charAt(i);
		}
		return leaveStr + theEndStr;
	}
	return theOldStr;
}

//检查录入数据 字节 长度
function getLength(val){
	if(val!=null){
		var len = 0;
		for (var i = 0; i < val.length; i++) {
			if (val.charCodeAt(i) >= 0x4e00 && val.charCodeAt(i) <= 0x9fa5){ 
				len += 2;
			}else {
				len++;
			}
		}
		return len;
	}
	
}





function checkHasZx(id){
	var check = true;
	sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/checkHasZx/"+id, null,
			function(data){
				var result = data.result.status;
				if(result == "ERROR"){ //OK, ERROR
					check = false;
				}else{
					var count = data.id;
					if( count == 0 || count == '0'){
						check = false;
					}
				}
			}
	);
	return check;
}

function getSearchDepartment($depField) {
	sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/getDepartment", null,
			function(data){
				if(data.result.status = 'OK') {
					$depField.empty();
					if(data.entityList.length >1) {
						$depField.append('<option value="">全部</option>');
					}
					$.each(data.entityList,function(){
						$depField.append('<option value="'+this.id+'">'+this.name+'</option>');
					});
				}
			}
	);
}

function getZixunDepartment($depField) {
	sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/getDepartment", null,
			function(data){
				if(data.result.status = 'OK') {
					$depField.empty();
					if(data.entityList.length >1) {
						$depField.append('<option value="">请选择</option>');
					}
					$.each(data.entityList,function(){
						$depField.append('<option value="'+this.id+'">'+this.name+'</option>');
					});
				}
			}
	);
}










// 添加创意资讯
function toAdd(){
	var $self = $(this);
	var _url = Constants.sopEndpointURL + "/galaxy/zixun/add";
	var _name = "添加资讯";
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#popup_name").text(_name);
			
			sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/preAdd", null,
					function(data){
						if(data.result.status == 'OK') {
							var entity = data.entity;
							var code = entity.code;
							$("#code").html(code);
							$("#zixunForm [name='code']").val(code);
						}
					}
			);
			
			getZixunDepartment($("#zixunForm [name='departmentId']"));
			
			$("[data-btn='add_rzzx']").on("click",function(){ 
				var $self = $(this);
				var _name=$self.attr("data-name")
				$.getHtml({
					url:Constants.sopEndpointURL + "/galaxy/zixunFinance/add",
					data:"",
					okback:function(){
						$("#popup_name1").text(_name);	
						initDialogValstr("rzForm");
					}	
				});
				return false;
			});
			
		}
	});
	return false;
}


//资讯 信息获取
function getAddCondition(){
	var condition = JSON.parse($("#zixunForm").serializeObject());
	if(!condition.departmentId || condition.departmentId ==''){
		condition.departmentId = null;
	}
	var rz = [];
	var rzTr = $("#rzBody tr");
	$.each(rzTr,function(){
		var arz = {};
		/*arz.financeDate = $(this).find("[data-name='financeDate']").html();
		arz.financeAmount = $(this).find("[data-name='financeAmount']").html();*/
		
		arz.financeDate = $(this).find("[data-name='financeDate']").attr("data-val");
		arz.financeAmount = $(this).find("[data-name='financeAmount']").attr("data-val");
		rz.push(arz);
	});
	condition.finaceList = rz;
	console.log(condition);
	return condition;
}
//添加访问后台
function saveAdd(){
	if(!beforeSubmitScroll("zixuntc")){
		return false;
	}
	
	$("div .zixuntc").showLoading(
		 {
		    'addClass': 'loading-indicator'						
		 });
	
	var condition = getAddCondition();
	sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/zixun/addzixun",condition,saveBack);
}
function saveBack(data){
	$("div .zixuntc").hideLoading();
	layer.msg(data.result.message);
	
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		return;
	}else{
		$("#data-table-zixun").bootstrapTable('refresh');
		removePop1();
	}
}





//融资信息添加
function addRz(){
	if(!beforeSubmitById("rzForm")){
		return false;
	}
	$("#add_rz").showLoading(
			 {
			    'addClass': 'loading-indicator'						
			 });
	
	var save_result = true;
	
	var rzId = '';
	var zxId = $("#zixunForm [name='id']").val();
	var condition = JSON.parse($("#rzForm").serializeObject());
	
	if((condition.financeDate==null && condition.financeAmount==null)
		|| (condition.financeDate=='' && condition.financeAmount==''))
		{
		$("#add_rz").hideLoading();
		$("#rzForm").parent().find("[data-close='close']").click();
		return;
	}
	
	if(zxId){
		condition.zixunId = zxId;
		sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/zixunFinance/addRz",condition,
				function(data){
					layer.msg(data.result.message);
					
					var result = data.result.status;
					if(result == "ERROR"){ //OK, ERROR
						$("#add_rz").hideLoading();
						save_result = false;
					}else{
						rzId = data.id;
					}
				}
		);
	}
	

	if(save_result){
		var edit = "<a href='javascript:;'  class=\"blue\" onclick=\"editRZ(this,'"+rzId+"')\" >编辑</a>";
		var del = "&nbsp;<a href='javascript:;' class=\"blue\" onclick=\"delRZ(this,'"+rzId+"')\" >删除</a>";
		var ope = edit + del;
		
		var htm = 
				"<tr data-id="+rzId+">" +
					"<td data-name='financeDate' data-val='"+condition.financeDate+"'>"+zixun_length_Format(condition.financeDate)+"</td>" +
					"<td data-name='financeAmount' data-val='"+condition.financeAmount+"'>"+zixun_length_Format(condition.financeAmount)+"</td>" +
					"<td data-name='opetate' data-check='n'>"+ope+"</td>" +
				"tr>";
		$("#rzBody .noinfo").parent('tr').remove();
		$("#rzBody").append(htm);
		
		if($("#rzBody").children().length >= 10){
			$("[data-btn='add_rzzx']").hide();	
		}
		
		$("#add_rz").hideLoading();
		$("#rzForm").parent().find("[data-close='close']").click();
	}
	
}



//弹框  融资信息编辑
function editRZ(obj,rzId){
	var op_td = $("#rzBody").find("[data-check='y']");
	if(op_td && op_td.length > 0){
		$(op_td).attr("data-check","n");
	}
	
	var _this = $(obj);
	_this.parent().attr("data-check","y");
	var thisTr = _this.parent().parent();
	
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/zixunFinance/edit",
		data:"",
		okback:function(){
			initDialogValstr("rzForm");
			$("#popup_name1").text("编辑融资信息");		
			$("#rzForm [name='id']").val(rzId);
			/*$("#rzForm [name='financeDate']").val(thisTr.find("[data-name='financeDate']").html());
			$("#rzForm [name='financeAmount']").val(thisTr.find("[data-name='financeAmount']").html());*/
			$("#rzForm [name='financeDate']").val(thisTr.find("[data-name='financeDate']").attr("data-val"));
			$("#rzForm [name='financeAmount']").val(thisTr.find("[data-name='financeAmount']").attr("data-val"));
		}	
	});
	return false;
}

//保存    融资编辑信息
function updateRzSave(){
	$("#edit_rz").showLoading(
			 {
			    'addClass': 'loading-indicator'						
			 });
	if(!beforeSubmitById("rzForm")){
		return false;
	}
	
	var save_result = true;
	
	var condition = JSON.parse($("#rzForm").serializeObject());
	var op_td = $("#rzBody").find("[data-check='y']");
	var tr = $(op_td).parent();
	
	var zxId = $("#zixunForm [name='id']").val();
	var rzId = $("#rzForm [name='id']").val();
	
	if(zxId && rzId){
		condition.zixunId = zxId;
		sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/zixunFinance/editRz",condition,
				function(data){
					layer.msg(data.result.message);
					
					var result = data.result.status;
					if(result == "ERROR"){ //OK, ERROR
						$("#edit_rz").hideLoading();
						save_result = false;
					}
				}
		);
	}
	
	if(save_result){
		$(tr).find("[data-name='financeDate']").attr("data-val",condition.financeDate);
		$(tr).find("[data-name='financeAmount']").attr("data-val",condition.financeAmount);
		
		$(tr).find("[data-name='financeDate']").html(zixun_length_Format(condition.financeDate));
		$(tr).find("[data-name='financeAmount']").html(zixun_length_Format(condition.financeAmount));
			
		$("#edit_rz").hideLoading();
		$("#rzForm").parent().find("[data-close='close']").click();
	}
	
}


//删除 融资
function delRZ(obj,rzId){
	layer.confirm('是否删除该融资信息?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero) {
		var _this = $(obj);
		var thisTr = _this.parent().parent();
		
		var save_result = true;
		if(rzId){
			sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixunFinance/delRz/"+rzId, null,
					function(data){
						var result = data.result.status;
						if(result == "ERROR"){ //OK, ERROR
							layer.msg(data.result.message);
							save_result = false;
						}
					}
			);
		}
		
		if(save_result){
			layer.msg("删除成功！");
			thisTr.remove();
			if($("#rzBody").children().length==0){  //无数据
				var tr='<tr><td colspan="3" style="text-align:center !important;color:#bbb;border:0;line-height:32px !important" class="noinfo"><label class="no_info_icon_xhhl">没有找到匹配的记录</label></td></tr>'
				$("#rzBody").append(tr);
			}
			if($("#rzBody").children().length < 10){
				$("[data-btn='add_rzzx']").show();	
			}
		}
		
		
	}, function(index) {
	});
}






/**
 * 查看  编辑   删除   
 */
function operateFormatter(value,row,index){  
	
	var look = "<a href='javascript:;'  class=\"blue\" onclick=\"to_look('"+row.id+"')\">查看</a>";
	var edit = "&nbsp;<a href='javascript:;'  class=\"blue\" onclick=\"to_edit('"+row.id+"')\" >编辑</a>";
	var del = "&nbsp;<a href='javascript:;'  class=\"blue\" onclick=\"to_del('"+row.id+"')\" >删除</a>";
	
	var content = look;
	if(row.canEdit == 0 || row.canEdit == '0' ){
		content += edit;
		content += del;
	}
	
	return content;
}



//弹框   编辑创意资讯
function to_edit(zixunId){
	var _name = "编辑创意资讯";
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/zixun/edit",
		data:"",
		okback:function(){
			$("#popup_name").text(_name);
			
			preEdit(zixunId);
			if($("#rzBody").children().length==0){  //无数据
				var tr='<tr><td colspan="3" style="text-align:center !important;color:#bbb;border:0;line-height:32px !important" class="noinfo"><label class="no_info_icon_xhhl">没有找到匹配的记录</label></td></tr>'
				$("#rzBody").append(tr);
			}
			
			$("[data-btn='add_rzzx']").on("click",function(){ 
				var $self = $(this);
				var _name=$self.attr("data-name")
				$.getHtml({
					url:Constants.sopEndpointURL + "/galaxy/zixunFinance/add",
					data:"",
					okback:function(){
						$("#popup_name1").text(_name);							
					}	
				});
				return false;
			});
		}
	});
	return false;
}

function preEdit(zixunId){
	
	sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/zixunInfo/"+zixunId, null,
			function(data){
				if(data.result.status == 'OK') {
					var entity = data.entity;
					
					$("#code").html(entity.code);
					$("#zixunForm [name='code']").val(entity.code);
					$("#zixunForm [name='id']").val(entity.id);
					$("#zixunForm [name='companyName']").val(entity.companyName);
					$("#zixunForm [name='companyField']").val(entity.companyField);
					$("#zixunForm [name='companyCuser']").val(entity.companyCuser);
					$("#zixunForm [name='companyUrl']").val(entity.companyUrl);
					$("#zixunForm [name='companyBtime']").val(entity.companyBtime);
					$("#zixunForm [name='companyAddress']").val(entity.companyAddress);
					$("#zixunForm [name='remark']").val(entity.remark);
					$("#zixunForm [name='detailInfo']").val(entity.detailInfo);
					
					/*if(entity.departmentId){
						$("#zixunForm [name='departmentId'][value='"+entity.departmentId+"']").attr("selected",true);  
					}*/
					sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/getDepartment", null,
							function(data){
								if(data.result.status = 'OK') {
									$("#zixunForm [name='departmentId']").empty();
									if(data.entityList.length >1) {
										$("#zixunForm [name='departmentId']").append('<option value="">请选择</option>');
									}
									$.each(data.entityList,function(){
										if(entity.departmentId && this.id == entity.departmentId){
											$("#zixunForm [name='departmentId']").append('<option selected="selected" value="'+this.id+'">'+this.name+'</option>');
										}else
											$("#zixunForm [name='departmentId']").append('<option value="'+this.id+'">'+this.name+'</option>');
									});
								}
							}
					);
					
					if(entity.finaceList && entity.finaceList.length >= 10){
						$("[data-btn='add_rzzx']").hide();
					}
					
					$.each(entity.finaceList,function(){
						var id = this.id;
						var edit = "<a href='javascript:;'  class=\"blue\" onclick=\"editRZ(this,'"+id+"')\" >编辑</a>";
						var del = "&nbsp;<a href='javascript:;' class=\"blue\" onclick=\"delRZ(this,'"+id+"')\" >删除</a>";
						var ope = edit + del;
						
						var htm = 
								"<tr data-id="+id+">" +
									"<td data-name='financeDate' data-val='"+this.financeDate+"'>"+zixun_length_Format(this.financeDate)+"</td>" +
									"<td data-name='financeAmount' data-val='"+this.financeAmount+"'>"+zixun_length_Format(this.financeAmount)+"</td>" +
									"<td data-name='opetate' data-check='n'>"+ope+"</td>" +
								"tr>";
						$("#rzBody").append(htm);
					});
					
				}
			}
	);
	
}

//资讯  编辑后   信息获取
function getEditCondition(){
	var condition = JSON.parse($("#zixunForm").serializeObject());
	if(!condition.departmentId || condition.departmentId ==''){
		condition.departmentId = null;
	}
	console.log(condition);
	return condition;
}
//添加访问后台
function editAdd(){
	if(!beforeSubmitScroll("zixuntc")){
		return false;
	}
	
	$("div .zixuntc").showLoading(
		 {
		    'addClass': 'loading-indicator'						
		 });
	
	var condition = getEditCondition();
	sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/zixun/editzixun",condition,saveBack);
}





/**
 * 查看 资讯
 */
function to_look(zixunId){
	var _name = "查看创意资讯";
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/zixun/look",
		data:"",
		okback:function(){
			$("#popup_name").text(_name);
			
			zixunInfo(zixunId);
			
			
		}
	});
	return false;
}
function zixunInfo(zixunId){
	
	sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/zixunInfo/"+zixunId, null,
			function(data){
				if(data.result.status == 'OK') {
					var entity = data.entity;
					
					$("#zixun_info #code").html(entity.code);
					$("#zixun_info #companyName").html(entity.companyName);
					$("#zixun_info #companyField").html(entity.companyField);
					$("#zixun_info #companyCuser").html(entity.companyCuser);
					$("#zixun_info #companyUrl").html(entity.companyUrl);
					$("#zixun_info #companyBtime").html(entity.companyBtime);
					$("#zixun_info #companyAddress").html(entity.companyAddress);
					$("#zixun_info #remark").html(entity.remark);
					$("#zixun_info #detailInfo").html(entity.detailInfo);
					$("#zixun_info #departmentId").html(entity.departName);
					/*添加title属性*/
					$("#zixun_info #code").attr("title",entity.code);
					$("#zixun_info #companyName").attr("title",entity.companyName);
					$("#zixun_info #companyField").attr("title",entity.companyField);
					$("#zixun_info #companyCuser").attr("title",entity.companyCuser);
					$("#zixun_info #companyUrl").attr("title",entity.companyUrl);
					$("#zixun_info #companyBtime").attr("title",entity.companyBtime);
					$("#zixun_info #companyAddress").attr("title",entity.companyAddress);
					$("#zixun_info #departmentId").attr("title",entity.departName);
					
					
					if(entity.finaceList && entity.finaceList.length > 0){
						$.each(entity.finaceList,function(){
							var htm = 
									"<tr>" +
										"<td data-name='financeDate'>"+zixun_length_Format(this.financeDate)+"</td>" +
										"<td data-name='financeAmount'>"+zixun_length_Format(this.financeAmount)+"</td>" +
									"tr>";
							$("#zixun_info #rzBody").append(htm);
						});
					}else{
						$("#zixun_info #rz_info").remove();
					}
					
				}
			}
	);
	
}




/**
 * 删除资讯
 */
function to_del(id) {
	layer.confirm('是否删除该项目资讯?', {
		btn : [ '确定', '取消' ],
		title :'提示'
	}, function(index, layero) {
		sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/delzixun/"+id, null,
				function(data){
					layer.msg(data.result.message);
					var result = data.result.status;
					if(result == "ERROR"){ //OK, ERROR
						return;
					}
				}
		);
		
		$("#data-table-zixun").bootstrapTable('querySearch');
		
	}, function(index) {
	});
}





