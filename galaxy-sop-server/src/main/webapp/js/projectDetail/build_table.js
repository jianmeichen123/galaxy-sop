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
			td.append('<span class="blue" data-btn="btn" onclick="editRow(this)">查看</span>');
		    tr.append(td);
		}
	}
	return tr;

}