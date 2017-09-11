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
				saveForm($("#detail-form"));
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
