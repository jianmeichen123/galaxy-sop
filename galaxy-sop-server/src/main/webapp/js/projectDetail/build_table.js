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
			var editable = table.hasClass('editable');
		
			if(editable == true||header.funFlag=="1")
			{
				tr +='<th data-field-name="opt">操作</th>';
			}
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
			tables.show();   //有数据表格显示
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
		var k  = $this.data('fieldName');
		if(k!="opt"){
			tr.append('<td data-field-name="'+k+'">'+row[k]+'</td>');
		}
		
	});
	var funFlg=$('table[data-title-id="'+titleId+'"]').attr("data-funFlag");
	var td = $('<td data-field-name="opt"></td>');
	if(showOpts == true)
	{
		td.append('<em class="blue" data-btn="btn" onclick="editRow(this)">查看</em>');
		td.append('<em class="blue" data-btn="btn" onclick="editRow(this)">编辑</em>');
		td.append('<em class="blue" data-btn="btn" onclick="delRow(this)">删除</em>');
		tr.append(td);
	}else{
		if(funFlg=="1"){
			td.append('<em class="blue" data-btn="btn" onclick="editRow(this)">查看</em>');
			td.append('<em class="blue" data-btn="btn" onclick="editRow(this)">编辑</em>');
			td.append('<em class="blue" data-btn="btn" onclick="delRow(this)">删除</em>');
			tr.append(td);
		}
	}
	return tr;

}

function editRow(ele)	
{
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
				saveForm($("#detail-form"),$(ele).closest("table"));
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
//编辑保存
function saveForm(form,_this)
{
	if($(form).validate().form())
	{
		var data = $(form).serializeObject();
		saveRow(data);
		
		var post_data= {
			projectId : projectInfo.id
		};
		var infoTableModelList = new Array();
		$.each(_this,function(){
			$.each($(this).find('tr:gt(0)'),function(){
				var row = $(this).data();
				if(row.id=="")
				{
					row.id=null;
				}
				infoTableModelList.push($(this).data());
			});
		});
	
		post_data.infoTableModelList = infoTableModelList;
		
		console.log(post_data);
		sendPostRequestByJsonObj(
				platformUrl.saveOrUpdateInfo , 
				post_data,
				function(data){
					console.log(data);
					
		})
		
		
		
	}
}
/**
 * 该方法不包含团队成员复杂的表格处理
 * 表格增删改查通用方法   **************************************************** 开始
 */
//新增弹出页面渲染
function addRow(ele)
{
	var table = $(ele).closest(".member").find("table")
	var code = table.data("code");
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			$('#qualifications_popup_name').html('添加简历');
            $('#qualifications_popup_name1').html('添加持股人');
            $('#finace_popup_name').html('添加融资历史');
            $('#finace_popup_name').html('添加融资历史');
			$("#complete_title").html('添加综合竞争比较');
			$("#delivery_popup_name").html("添加交割事项")
			$(".see_block").hide();
            $("#detail-form input[name='projectId']").val(projectInfo.id);
            $("#detail-form input[name='titleId']").val(table.data('titleId'));
            $("#detail-form input[name='subCode']").val(table.data('code'));
            $("input[name=updateTimeStr]").val(new Date().format("yyyy-MM-dd"));
            selectContext("detail-form");
            $("#save-detail-btn").click(function(){
                saveForm($("#detail-form"),table);
                check_table();
                check_table_tr_edit();
            });
            $("#save_person_learning").click(function(){
                check_table();
                check_table_tr_edit();
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
        }
    }
}
function check_table(){
	$.each($('table.editable'),function(){
		if($(this).find('tr').length<=1){
			$(this).hide();
		}
		else{
			$(this).show();
		}
	})
}	
//检查是否10条tr
function check_table_tr_edit(){
	$.each($("table.editable"),function(){
		var code = $(this).data('code');
		var limit = getTableRowLimit(code);
		var trs=$(this).find("tr").length-1;
		if(trs>=limit){
			$(this).siblings(".bluebtn").hide();
		}else{
			$(this).siblings(".bluebtn").show();
		}
	})
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
