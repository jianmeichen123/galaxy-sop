
	//1.8新加数据开始
	//不同表格公共方法
	function info_table(code,name,table){
		var pid=table.attr("parentid");
		table.attr("data-name",name);
	    table.attr("data-url-code",code);			
		sendGetRequest(platformUrl.getTitleResults+pid+"/"+projectInfo.id,null,function(data){
	        var result = data.result.status;
			var header=data.entityList;
			console.log(data);
			if(result=="OK"){
		    	$.each(header,function(){
		    		var _header =$(this);
		    		if(_header[0].name==name){
		    			buildTable(_header[0]);
		    			return false;
		    		}
		    	})
			}
	     })
	     resizetable(table);
	}
//1.8新加数据结束
function buildTable(title)
{
	//列表Header
	if(title.tableHeader)
	{	var header = title.tableHeader;
		var tables = $("table[data-title-id='"+header.titleId+"']");
		$.each(tables,function(){
			var table = $(this);
			table.attr('data-code',header.code);
			table.attr('data-funFlag',header.funFlag);
			table.empty();
			var tr="<thead><tr>";
			for(var key in header)
			{
				if(key.indexOf('field')>-1)
				{
					if((header.titleId == '1810'||header.titleId == '1811') && key == 'field2'){
						continue;
					}
					if(header.code=='finance-history'&&(key == 'field8'||key == 'field9'||key == 'field10')){
						continue;
					}
					if(key!="opt"){
					    tr +='<th data-field-name="'+key+'">'+header[key]+'</th>';
					}
				}
			}
			if(header.titleId =='1810'||header.titleId =='1811')
			{
				tr +='<th data-field-name="updateUserName">编辑人</th>';
				tr +='<th data-field-name="updateTimeStr">编辑日期</th>';
			}	 
				tr +='<th data-field-name="opt">操作</th>'; 
			tr+="</tr></thead>";
			table.append(tr);
		});
	}
	//列表Row
	if(title.dataList)
	{
		$("#location").hide();
		$.each(title.dataList,function(){
			var row = this;
			var tables = $("table[data-title-id='"+row.titleId+"']");
			var location_show = tables.parents(".location_show");
			location_show.show();
			$.each(tables,function(){
				var table = $(this);
				var tr = buildRow(row,table.hasClass('editable'),row.titleId);
				table.append(tr);
			});
		});
	}
}

function buildRow(row,showOpts,titleId)
{
	var ths = $('table[data-title-id="'+titleId+'"]:eq(0) th');
	var tr=$("<tr data-row-id='"+row.id+"'></tr>");
	for(var key in row)
	{
		//设置data
		tr.data(key,row[key]);
	}
	$.each(ths,function()
	{
		var $this = $(this);
		var k = $this.data('fieldName');
		if(k!="opt"){
			if(row[k]==""||row[k]==undefined){
				row[k]="—";
			}
			tr.append('<td data-field-name="'+k+'">'+row[k]+'</td>');
		}
		
	});
	var funFlg=$('table[data-title-id="'+titleId+'"]').attr("data-funFlag");
	var td = $('<td data-field-name="opt"></td>');
	td.append('<em class="blue" data-btn="btn" onclick="editRow(this)">查看</em>');
	td.append('<em class="blue" data-btn="btn" onclick="editRow(this)">编辑</em>');
	td.append('<em class="blue" data-btn="btn" onclick="delRow(this)">删除</em>');
	tr.append(td);
	return tr;

}

function editRow(ele)	
{
	var table = $(ele).closest('table');
	var code = $(ele).closest('table').data('code');
	var row = $(ele).closest('tr');
	var txt=$(ele).text();
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			//var title = $("#pop-title");
			 if(txt=="查看"){
					$("#detail-form").hide();
					$(".button_affrim").hide();
					$("#delivery_popup_name").text("查看交割事项");
					 $('#grant_popup_name').html('查看分期注资计划');
					 $('#finace_popup_name').html('查看融资历史');
					 $("#complete_title").html('查看综合竞争比较');
					 $("#pop-title-gs").text('查看同类公司');
					 $("#pop-title-time").text('查看里程碑和时间节点');
					 $("#pop-title").text('查看分期注资计划');
					 $("#pop-title-share").text('查看股东');
					
				}else{
					$(".see_block").hide();
					$("#delivery_popup_name").text("编辑交割事项");
					 $('#grant_popup_name').html('编辑分期注资计划');
					 $('#finace_popup_name').html('编辑融资历史');
					 $("#complete_title").html('编辑综合竞争比较');
					 $("#pop-title-tz").html('编辑投资人');
					 $("#pop-title-share").html('编辑股东');
					 $("#pop-title-yy").html('编辑运营指标');
					 $("#pop-title-gs").text('编辑同类公司');
					 $("#pop-title-time").text('编辑里程碑和时间节点');
					 $("#pop-title").text('编辑分期注资计划');
				}
			$("#detail-form input[name='subCode']").val(code);
			$("#detail-form input[name='titleId']").val(row.parent().parent().attr("data-title-id"));
			selectContext("detail-form");
			$.each($("#detail-form").find("input, select, textarea"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				var type=ele.attr('type');
				var idVal=ele.attr('id');
				if(type=="radio"){
					if(ele.val()==row.data(name)){
						ele.attr("checked","chedcked");
					}
				}else{
					ele.val(row.data(name));
				}
			});
			//查看显示
			$.each($(".see_block").find("dd[name]"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				ele.text(row.data(name));
				//历史融资特殊处理select,radio
				$.each($("#financeDetail select"),function(){
					var selectId=$(this).val();
					var selectVal=$("#financeDetail select").find("option[value='"+selectId+"']").text();
					if(row.data(name)==selectId && selectId!=""){
						ele.text(selectVal);
					}
					var val=$(".see_block").find("dd[name='field6']").text();
					if(row.data('field3')==""){
						$(".see_block").find("dd[name='field3']").text(row.data('field3'));
					}else{
						$(".see_block").find("dd[name='field3']").text(row.data('field3')+'万'+val);
					}
					if(row.data('field5')==""){
						$(".see_block").find("dd[name='field5']").text(row.data('field5'));
					}else{
						$(".see_block").find("dd[name='field5']").text(row.data('field5')+'万'+val);
					}
					
				});
				$.each($("#financeDetail input[type='radio']"),function(){
					var selectId=$(this).val();
					var selectVal=$("#financeDetail").find("input[type='radio'][value='"+selectId+"']").parent().text();
					if(row.data(name)==selectId){
						ele.text(selectVal);
					}
				});
				
			})
			resizetable(table)
			//分拨剩余金额显示
			$(".remainMoney span").text($("#formatRemainMoney").text());
			//特殊处理带万元单位的查看
			$.each($(".see_block").find("dd.money[name]"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				if(row.data(name)==""){
					ele.text(row.data(name));
				}else{
					ele.text(row.data(name)+'万元');
				}
				
			})
			//特殊处理带%单位的查看
			$.each($(".see_block").find("dd.percent[name]"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				if(row.data(name)==""){
					ele.text(row.data(name));
				}else{
					ele.text(row.data(name)+'%');
				}
				
			})
			//文本框剩余字数
			$.each($("textarea"),function(){
				var len=$(this).val().length;
				var initNum=$(this).siblings('.num_tj').find("span").text();
				$(this).siblings('.num_tj').find("span").text(initNum-len);
			})
			$("#detail-form input[name='index']").val(row.index());
			$("#save-detail-btn").click(function(){
				saveForm($("#detail-form"),table);
				
			});
			
		}//模版反回成功执行	
	});
}
function getDetailUrl(code)
{
	if(code == 'equity-structure')
	{
		return'../../../html/funcing_add_gd.html';
	}
	else if(code == 'investor-situation')
	{
		return'../../../html/funcing_add_tz.html';
	}
	else if(code =='operation-indices')
	{
		return'../../../html/fincing_add_yx.html';
	}
	else if(code == 'valuation-reference')
	{
		return'../../../html/fincing_add_tl.html';
	}
	else if(code == 'financing-milestone')
	{
		return'../../../html/fincing_add_jd.html';
	}
	else if(code == 'finance-history')
	{
		return'../../../html/finace_history.jsp';
	}
	else if (code =='team-members'){

	    return'../../../html/team_compile.html';
	}else if(code == 'share-holding')
    {
        return'../../../html/team_add_cgr.html';
    }else if(code == 'competition-comparison')
	{
		return'../../../html/compete_save.jsp';
	}else if(code == 'delivery-before')
	{
		return'../../../html/delivery_matter.jsp';
	}else if(code == 'delivery-after')
	{
		return'../../../html/delivery_matter.jsp';
	}else if(code == 'grant-part' || code == 'grant-actual')
	{
	    if(reportType == 7){
	    	return'../../../html/operation_appr_part.html';
	    }
		return'../../../html/grant-part.jsp';
	}
	return "";
}
//编辑保存  _this  当前 table
function saveForm(form,_this)
{
	if(form !=undefined&&form !=""&&form !="delete"){
		if($(form).validate().form())
		{
			var data = $(form).serializeObject();
			saveRow(data);
			//保存数据
			var post_data= {
				projectId : projectInfo.id
			};
			var infoTableModelList = new Array();
			$.each(_this.find("tbody tr[data-row-id]"),function(){
				var row = $(this).data();
				if(row.id=="")
				{
					row.id=null;
				}
				infoTableModelList.push($(this).data());
			});
			post_data.infoTableModelList = infoTableModelList;
			sendPostRequestByJsonObj(
					platformUrl.saveOrUpdateInfo , 
					post_data,
					function(data){		
						if(data.result.status=="OK"){
							$("tr.no-records-found").remove();
							layer.msg("保存成功");
							var code = _this.data("urlCode");
							var name = _this.data("name")					
							info_table(code,name,_this);
						}else{
							layer.msg(data.result.status);
							_this.find("tbody tr:last-child").remove();
						}
			})
		}
	}else{
		var post_data= {
				projectId : projectInfo.id
			};
		post_data.deletedRowIds = deletedRowIds;
		sendPostRequestByJsonObj(
				platformUrl.saveOrUpdateInfo , 
				post_data,
				function(data){	
					deletedRowIds = new Array();
					if(data.result.status=="OK"){
						layer.msg("删除成功");
					}
					
					
		})
	}
}
/**
 * 该方法不包含团队成员复杂的表格处理
 * 表格增删改查通用方法   **************************************************** 开始
 */
//新增弹出页面渲染
function addRow_sp(ele)
{
	var table = $(ele).closest(".member").find("table")
	var code = table.data("code");
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
            $('#qualifications_popup_name1').html('添加持股人');
            $('#finace_popup_name').html('添加融资历史');
			$(".see_block").hide();
            $("#detail-form input[name='projectId']").val(projectInfo.id);
            $("#detail-form input[name='titleId']").val(table.data('titleId'));
            $("#detail-form input[name='subCode']").val(table.data('code'));
            $("input[name=updateTimeStr]").val(new Date().format("yyyy-MM-dd"));
            selectContext("detail-form");
            $("#save-detail-btn").click(function(){
                saveForm($("#detail-form"),table);
                check_table_tr_edit(table);
            });
		}//模版反回成功执行	
	});
}
function saveRow(data)
{
	data = JSON.parse(data);
	var titleId = data.titleId;
	var index = data.index;
	if(typeof index == 'undefined' || index == null || index == '')
	{
		var tr = buildRow(data,true,titleId);
		$('table[data-title-id="'+titleId+'"]').append(tr);
	}
	else
	{
		var tr = $('table[data-title-id="'+titleId+'"]').find('tbody tr:eq('+index+')');
		for(var key in data)
		{
			if(key.indexOf('field')>-1 || key == "updateTimeStr" || key == "updateUserName" || key == "updateTimeSign")
			{
				tr.data(key,data[key]);
				tr.find('td[data-field-name="'+key+'"]').text(data[key]);
			}
		}
	}
	resizetable($('table[data-title-id="'+titleId+'"]'))
	$("a[data-close='close']").click();
}
//检查是否10条tr
function check_table_tr_edit(table){
		var limit = 10;
		var trs=$(table).find("tr").length-1;
		if(trs==0){
			var th_length =$(table).find("th").length
			var noData='<tr class="no-records-found"><td colspan='+th_length+'  style=" text-align:center !important;color:#bbb;border:0;line-height:32px !important" class="noinfo no_info01"><label class="no_info_icon_xhhl">没有找到匹配的记录</label></td></tr>'
			$(table).append(noData);
		}else{
			$(table).find(".no-records-found").remove();
		}
		if(trs>=limit){
			$(table).closest(".tabtable_con_on").find(".bluebtn").hide(); 
			for(var i=limit;i<trs-limit;i++){   //历史数据展示前10条
				$(table).find("tr").eq(i).hide();
			}
		}else{
			$(table).closest(".tabtable_con_on").find(".bluebtn").show(); 
		}
}
/**
 * 调用此方法渲染下拉框，需要注意几点：
 * 1，<dd class="clearfix" id="field5" data-type="radio">如果是单选需要在dd标签上面加上两个属性（id="field5" ，data-type="radio"）
 * 2，下拉框需要添加id属性，id的属性值跟name的值一样
 * 数据字典加载页面渲染
 */

function selectContext(formId){

	 var $fileds=$("#"+formId).find("select,dd[data-type='radio']");
	 $.each($fileds,function(){
		var field = $(this);
		var titleId=$("#"+formId+" input[name='titleId']").val();
		var subCode=$("#"+formId+" input[name='subCode']").val();
		var filedName;
	    if(field[0].tagName=="DD"){
	    	filedName=field.attr("id");
		}else if(field[0].tagName=="select"||field[0].tagName=="SELECT"){
			filedName=field.attr("name");
		}
	    selectDirect(titleId,subCode,filedName);
	})
}
/**
 * 数据字典加载请求
 */
function selectDirect(tittleId,subCode,filed){
	sendGetRequest(platformUrl.getDirectory+ tittleId+'/'+subCode+"/"+filed,null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK')
				{
					var dataMap = data.userData;
				    var $filed=$("[id='"+filed+"']");
				    var list=dataMap[filed];
				    var name=""
				    $filed.children().remove();
				    if($filed[0].tagName=="SELECT"){
				    	$filed.append("<option value='' name='"+filed+"' data-name='请选择'>请选择</option>");
				    }
					$.each(list, function(i, value){
                        if($filed[0].tagName=="SELECT"){
                        	var code=value.code;
                        	if(code.indexOf("currency")>-1){    //币种的请选择移除
                        		$filed.find("option[data-name]").remove();
                        	}
                        	$filed.append("<option value="+value.id+"  name='"+filed+"'>"+value.name+"</option>");
				    	}else if($filed[0].tagName=="DD"&&$filed.attr("data-type")=="radio"){
				    			$filed.append("<label><input type='radio' value='"+value.id+"' data-remark='"+value.name+"' name='"+filed+"'>"+value.name+"</label>")
				    	}
					});
				}
			})
	}
var deletedRowIds = new Array();
function delRow(ele)
{
	layer.confirm('是否删除?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero) {
		var tr = $(ele).closest('tr');
		var id = tr.data('id');
		var table = $(ele).closest('table')
		var code =table.data("code");
        if(typeof id != 'undefined' && id>0)
        {
        	 deletedRowIds.push(id);
        }
		tr.remove();
		check_table_tr_edit(table);
		$(".layui-layer-close1").click();
		saveForm("delete",$(ele).closest("table"));
	}, function(index) {
	});
}
//table  select  显示转换
function resizetable(table){
    var dict_map = {};
    var title_id = table.attr("data-title-id")
    var  code = table.attr("data-code")
    var fields_json=tableDictColumn(code);
    if (fields_json && code in fields_json){
        var fields = fields_json[code]
        for(var i=0;i<fields.length;i++){
            var v = fields[i]
            var dict = dictCache(title_id,code,v)
            dict_map[title_id+"-"+code+"-"+v] = dict
            table.find('td[data-field-name="'+v+'"]').each(function(){
                var o = $(this)
                o.text(dict[o.text()])
            })
           var dd = $('.see_block dd[name="'+v+'"]');
           dd.text(dict[dd.text()])
        }
    }
}
function dictCache(titleId,subCode,filed){
    var map = {};
    map["undefined"] = ""
    map[""] = ""
	sendGetRequest(platformUrl.getDirectory+titleId+'/'+subCode+"/"+filed,null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK')
				{
					var dataMap = data.userData;
				    var list=dataMap[filed];
				    var name=""
					$.each(list, function(i, value){
					     map[value.id]=value.name;
					});
				}
			})
			return map;
}
function tableDictColumn(code){
	var json;
	var arr=[];
	if(code == 'competition-comparison')
	{
        return json={"competition-comparison":["field5"]};
	}else if(code == 'finance-history'){
		return json={"finance-history":["field6","field7","field8"]};
	}else if(code=="equity-structure"){
		return json={"equity-structure":["field3","field4"]};
	}else if(code=="investor-situation"){
		return json={"investor-situation":["field1","field6"]};
	}
}