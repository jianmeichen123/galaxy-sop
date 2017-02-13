

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
	
	var rz = [];
	var rzTr = $("#rzBody tr");
	$.each(rzTr,function(){
		var arz = {};
		arz.financeDate = $(this).find("[data-name='financeDate']").html();
		arz.financeAmount = $(this).find("[data-name='financeAmount']").html();
		rz.push(arz);
	});
	condition.finaceList = rz;
	console.log(condition);
	return condition;
}
//添加访问后台
function saveAdd(){
	var condition = getAddCondition();
	sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/zixun/addzixun",condition,saveAddBack);
}
function saveBack(data){
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
	var rzId = '';
	var zxId = $("#zixunForm [name='id']").val();
	var condition = JSON.parse($("#rzForm").serializeObject());
	
	if(zxId){
		condition.zixunId = zxId;
		sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/zixunFinance/addRz",condition,
				function(data){
					layer.msg(data.result.message);
					
					var result = data.result.status;
					if(result == "ERROR"){ //OK, ERROR
						return;
					}else{
						rzId = data.id;
					}
				}
		);
	}
	
	var edit = "<a href='javascript:;'  class=\"blue\" onclick=\"editRZ(this,'"+rzId+"')\" >编辑</a>";
	var del = "&nbsp;<a href='javascript:;' class=\"blue\" onclick=\"delRZ(this,'"+rzId+"')\" >删除</a>";
	var ope = edit + del;
	
	var htm = 
			"<tr data-id="+rzId+">" +
				"<td data-name='financeDate'>"+condition.financeDate+"</td>" +
				"<td data-name='financeAmount'>"+condition.financeAmount+"</td>" +
				"<td data-name='opetate' data-check='n'>"+ope+"</td>" +
			"tr>";
	$("#rzBody").append(htm);
	
	$("#rzForm").parent().find("[data-close='close']").click();
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
			$("#popup_name1").text("编辑融资信息");		
			$("#rzForm [name='id']").val(rzId);
			$("#rzForm [name='financeDate']").val(thisTr.find("[data-name='financeDate']").html());
			$("#rzForm [name='financeAmount']").val(thisTr.find("[data-name='financeAmount']").html());
		}	
	});
	return false;
}

//保存    融资编辑信息
function updateRzSave(){
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
						return;
					}
				}
		);
	}
	
	$(tr).find("[data-name='financeDate']").html(condition.financeDate);
	$(tr).find("[data-name='financeAmount']").html(condition.financeAmount);
	
	$("#rzForm").parent().find("[data-close='close']").click();
}



//删除 融资
function delRZ(obj,rzId){
	layer.confirm('是否删除该融资信息?', {
		btn : [ '确定', '取消' ]
	}, function(index, layero) {
		var _this = $(obj);
		var thisTr = _this.parent().parent();
		
		if(rzId){
			sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixunFinance/delRz/"+rzId, null,
					function(data){
						layer.msg(data.result.message);
						
						var result = data.result.status;
						if(result == "ERROR"){ //OK, ERROR
							return;
						}
					}
			);
		}
		
		thisTr.remove();
	}, function(index) {
	});
}






/**
 * 查看  编辑   删除   
 */
function operateFormatter(value,row,index){  
	var look = "<label class=\"blue\" onclick=\"to_look('"+row.id+"')\">查看</label>";
	var edit = "&nbsp;<label class=\"blue\" onclick=\"to_edit('"+row.id+"')\" >编辑</label>";
	var del = "&nbsp;<label class=\"blue\" onclick=\"to_del('"+row.id+"')\" >删除</label>";
	
	var content = look;
	content += edit;
	content += del;
	
	return content;
}



//弹框   编辑创意资讯
function to_edit(zixunId){
	var _name = "编辑资讯";
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/zixun/edit",
		data:"",
		okback:function(){
			$("#popup_name").text(_name);
			
			preEdit(zixunId);
			
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
					
					$.each(entity.finaceList,function(){
						var id = this.id;
						var edit = "<a href='javascript:;'  class=\"blue\" onclick=\"editRZ(this,'"+id+"')\" >编辑</a>";
						var del = "&nbsp;<a href='javascript:;' class=\"blue\" onclick=\"delRZ(this,'"+id+"')\" >删除</a>";
						var ope = edit + del;
						
						var htm = 
								"<tr data-id="+id+">" +
									"<td data-name='financeDate'>"+this.financeDate+"</td>" +
									"<td data-name='financeAmount'>"+this.financeAmount+"</td>" +
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
	console.log(condition);
	return condition;
}
//添加访问后台
function editAdd(){
	var condition = getEditCondition();
	sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/zixun/editzixun",condition,saveBack);
}






/**
 * 删除资讯
 */
function to_del(id) {
	layer.confirm('是否删除该项目资讯?', {
		btn : [ '确定', '取消' ]
	}, function(index, layero) {
		sendGetRequest( Constants.sopEndpointURL + "/galaxy/zixun/delzixun/"+rzId, null,
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





