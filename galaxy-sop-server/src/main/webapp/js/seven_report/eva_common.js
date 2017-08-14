
/**
 * 加载标题
 */
$("#eva-tabs li").click(function(){
	var $li = $(this);
	if($li.hasClass('active'))
	{
		return;
	}
	var code = $li.data('code');
	var relateId = $li.data('relateId');
	$li.siblings().removeClass('active');
	$li.addClass('active');
	sendGetRequest(platformUrl.queryAllTitleValues+code+"?reportType="+reportType, null,
		function(data){
		var result = data.result.status;
		if (result == 'OK') {
			$('#page_all').empty();
			var entity = data.entity;
			$("#part-title-name").text(entity.name);
			$("#test_tmpl").tmpl(entity).appendTo('#page_all');
			/*显示结果  */
			/* 16类型内容处理 */
			var content_16 = $(".content_16").text();		
			content_16=content_16.replace(/<sitg>/g,'（');
			content_16=content_16.replace(/<\/sitg>/g,'）');
			$(".content_16 p").text(content_16); 
			//显示结果和分数向
			showResultAndScoreList(relateId);
			 //修改分数时自动计算
			 $(".score-column select,input").change(function(){
				 calcScore();
			 });
		}
	});
	$.getTabHtml({
		url : platformUrl.toOperateInfo ,
		okback:function(){
			right_anchor(code+"?reportType=1","seven","show");
		}
	});
});
//编辑按钮显示
function　mouserover(obj){
	if($(obj).data('edit') == 'true') return;
	var target = $(obj).find('.editPic');
	 target.show();
};
function mouseout(obj){
	var target = $(obj).find('.editPic');
	target.hide();
};
$("#eva-tabs li:eq(0)").click();

// 
/**
 * 显示结果和分数项
 */
function showResultAndScoreList(relateId)
{
	sendGetRequest(
			platformUrl.getRelateTitleResults+reportType+"/"+relateId+"/"+projId, 
			null,
			function(data){
				if(data.result.status == 'OK')
				{
					$.each(data.entityList,function(){
						var rid = this.relateId;
						var weight = this.weight;
						//分数选择
						if(rid==relateId)
						{
							$("#part-weight").text(this.weight+"%");
						}
						if(weight != 'undefined')
						{
							$("span[class='title-weight'][data-relate-id='"+rid+"']").html("<br/>( "+weight+"% )");
						}
						var autoList = this.autoList;
						if(typeof autoList != 'undefined' && autoList.length>0 )
						{
							var sel = $('td[class="score-column"][data-relate-id="'+rid+'"]').find('select');
							sel.empty();
							sel.append('<option>请选择</option>')
							$.each(autoList,function(){
								sel.append('<option>'+this.grade+'</option>')
							});
						}
						//结果
						buildResult(this);
						buildTable(this);
					});
					initScore(relateId);
					Tfun_8($(".type_8"));
				}
			}
		);
}
/**
 * 回显分数
 */
function popScore(titles,relateId)
{
	$.each(titles,function(rid,score){
		if(rid == 0)
		{
			$("#total-score").text(score);
		}
		else if(rid == relateId)
		{
			$("#part-score").text(score);
		}
		else
		{
			var td = $('td[class="score-column"][data-relate-id="'+rid+'"]');
			if(rid.indexOf('-')>0)
			{
				var arr = rid.split('-');
				rid = arr[0];
				var subId = arr[1];
				td = $('td[class="score-column"][data-relate-id="'+rid+'"][data-sub-id="'+subId+'"]');
			}
			var ele = td.children('input,select');
			if(ele.length ==0)
			{
				td.text(score)
			}
			else
			{
				ele.val(score);
			}
		}
	});
}
/**
 * 页面加载时获取
 */
function initScore(relateId)
{
	var rid = $("#eva-tabs li.active").data('relateId');
	sendGetRequest(
			platformUrl.getScores, 
			{"parentId":0,"projectId":projId,"reportType":reportType},
			function(data){
				if(data.result.status == 'OK')
				{
					var titles = data.userData;
					popScore(titles,rid);
				}
			}
		);
}
/**
 * 计算分数分数
 */
function calcScore()
{
	var rid = $("#eva-tabs li.active").data('relateId');
	
	var titleData = {};
	$(".title-value").each(function(){
		var _this = $(this);
		var relateId = _this.data('relateId');
		var subId = typeof _this.data('subId')=='undefined' ? null:_this.data('subId');
		var values = getTitleValue(relateId);
		var title = {
			"relateId": relateId,
			"subId": subId,
			"values": values	
		}
		titleData[relateId+"-"+subId] = title;
	});
	
	$(".score-column input,select").each(function(){
		var _this = $(this);
		var td = _this.parent();
		var relateId = td.data('relateId');
		var subId = typeof td.data('subId')=='undefined' ? null:td.data('subId');
		var values = getTitleValue(relateId);
		var score = _this.val();
		if(score == "" || isNaN(score))
		{
			score = null;
		}
		var title = {
			"relateId": relateId,
			"subId": subId,
			"values": values,
			"score": score	
		}
		if(titleData.hasOwnProperty(relateId+"-"+subId))
		{
			title = titleData[relateId+"-"+subId];
			title.score = score
		}
		titleData[relateId+"-"+subId] = title;
	});
	var items = new Array();
	$.each(titleData,function(key,item){
		items.push(item);
	});
	var data = {
			"parentId": 0	,
			"relateId": rid,
			"reportType": 1,
			"projectId":projId
		};
	data.items = items;
	sendPostRequestByJsonObj(
			platformUrl.calculateScore, 
			data,
			function(data){
				if(data.result.status == 'OK')
				{
					var titles = data.userData;
					popScore(titles,rid);
				}
			}
		);
}
function getTitleValue(relateId)
{
	var _ele = $(".title-value[data-relate-id='"+relateId+"']");
	var val = _ele.attr('data-title-value');
	if(typeof val == 'undefined' || val.length==0)
	{
		return null;
	}
	return val.split(',');
}
function getScore(relateId,subId)
{
	var score = null;
	var td = $('td[class="score-column"][data-relate-id="'+relateId+'"]');
	if(subId >0)
	{
		td = $('td[class="score-column"][data-relate-id="'+relateId+'"][data-sub-id="'+subId+'"]');
	}
	var ele = td.children('input,select');
	if(ele.length ==0)
	{
		score = td.text();
	}
	else
	{
		score = ele.val();
	}
	if(score == "" || isNaN(score))
	{
		return null;
	}
	return score;
}
/**
 * 手动填写、选择的分数
 * @param relateId
 * @param subId
 * @returns
 */
function getManualScore(relateId,subId)
{
	var score = null;
	var td = $('td[class="score-column"][data-relate-id="'+relateId+'"]');
	if(subId >0)
	{
		td = $('td[class="score-column"][data-relate-id="'+relateId+'"][data-sub-id="'+subId+'"]');
	}
	var ele = td.children('input,select');
	if(ele.length > 0)
	{
		score = ele.val();
	}
	if(score == "" || isNaN(score))
	{
		score = null;
	}
	return score;
}

function afterTitleSaved()
{
	calcScore();
}
/**
 * 结果回显
 */
function buildResult(title)
{
	var results = title.resultList;
	var type = title.type;
	if(typeof results == 'undefined' || results.length == 0)
	{
		return;
	}
	var _ele = $('.title-value[data-title-id="'+title.id+'"]');
	var _scoreEle = $('.score-column[data-title-id="'+title.id+'"]');
	var _sign = _ele.parent().attr("class");
	//Radio
	if(type == 2 || type==14)
	{
		if(_sign=="sign_3"){
			_ele.find("span").text(results[0].valueName);
		}else{
			_ele.text(results[0].valueName);
		}
		_ele.attr('data-title-value',results[0].contentChoose)
		_ele.attr("data-result-id",results[0].id);
	}
	//文本域
	else if(type == 1 || type == 8|| type == 18)
	{
		if(_sign=="sign_3"){
			_ele.find("span").text(results[0].contentDescribe1);
		}else{
			_ele.text(results[0].contentDescribe1);
		}
		_ele.attr("data-result-id",results[0].id);
	}
	//复选带备注
	else if (type == 3 || type == 6 || type == 13)
	{
		if(results.length==0)
		{
			return;
		}
		var content = new Array();
		var values = new Array();
		var remark;
		$.each(results,function(){
			var text = this.valueName;
			if(text == '其他')
			{
				text = this.contentDescribe1;
				remark = text;
			}
			content.push(text);
			values.push(this.contentChoose);
		});
		_ele.html(content.join('、')); 
		_ele.attr("data-title-value",values.join(','));
		var _val_id =_ele.attr("data-title-value");
		if(_val_id!=""){
			_val_id=_val_id+",";
		}
		_ele.attr("data-title-value",_val_id);
		_ele.attr("data-remark",remark);
	}
	//一个标题带两个文本域、
	else if(type == 15)
	{
		_ele = $('.title-value[data-title-id="'+title.id+'"][data-sub-id="'+title.subId+'"]');
		var val = results[0]["contentDescribe"+title.subId];
		if(typeof val == 'undefined')
		{
			_ele.text('未填写');
		}
		else
		{
			_ele.text(val);
		}
		_ele.attr("data-result-id",results[0].id);
	}
	else if(type == 16)
	{
		var val = results[0].contentDescribe1;
		if(val != null && val.length>0)
		{
			_ele.attr("data-remark",val);
			val=val.replace(/<sitg>/g,'（').replace(/<\/sitg>/g,'）');
			_ele.text(val);
		}
		_ele.attr("data-result-id",results[0].id);
	}
	
	if(_scoreEle.length>0)
	{
		_scoreEle.attr("data-result-id",results[0].id);
	}
}
function buildTable(title)
{
	var rows = title.dataList;
	var type = title.type;
	var _ele = $('.title-value[data-title-id="'+title.id+'"]');
	if(typeof rows == 'undefined' || rows.length == 0)
	{
		return;
	}
	if (type == 10)
	{
		var content = JSON.stringify(rows);
		var em = $('<em class="income_table" >[表格]</em>');
		em.attr('data-tr',content);
		_ele.find('span').html(em);
		income_table();
	}
}
function getValues()
{
	
	var dupCheck = {};
	var infoModelList = new Array();
	var titleEles = $(".title-value");
	$.each(titleEles,function(){
		var _this = $(this);
		var type = _this.data('type');
		var titleId = _this.data('titleId');
		var value = _this.data('titleValue');
		var resultId = _this.data('resultId');
		var remark = _this.data('remark');
		var text = _this.text();
		if(dupCheck.hasOwnProperty(titleId))
		{
			return;
		}
		dupCheck[titleId]=titleId;
		if(_this.parent().hasClass('sign_3'))
		{
			text = _this.find('span').text();
		}
		//input,textarea
		if(type == 1 || type == 8||type == 18)
		{
			var model = {
				tochange:"true",
				type:type
			};
			model.projectId = projId;
			model.titleId = titleId;
			if(typeof resultId != 'undefined')
			{
				model.resultId = resultId;
			}
			if(typeof text == 'undefined' || text == '未填写'|| text == '未选择')
			{
				model.remark1 = '';
			}
			else
			{
				model.remark1 = text;
			}
			infoModelList.push(model);
		}
		//radio,radio+备注,select
		else if(type == 2 || type == 5 ||type==14)
		{
			var model = {
					tochange:"true",
					type:type		
				};
			model.projectId = projId;
			model.titleId = titleId;
			if(typeof resultId != 'undefined')
			{
				model.resultId = resultId;
			}
			if(typeof value != 'undefined')
			{
				model.value = value;
			}
			if(typeof remark != 'undefined')
			{
				model.remark1 = remark;
			}
			infoModelList.push(model);
		}
		//checkbox,checkbox+input,checkbox+textarea
		else if( type == 3 || type == 6 || type == 13)
		{
			if(typeof value != 'undefined')
			{
				var values = value.split(',');
				values.pop();
				$.each(values,function(i,val){
					var model = {
							tochange:"true",
							type:type		
						};
					model.projectId = projId;
					model.titleId = titleId;
					if(typeof resultId != 'undefined')
					{
						model.resultId = resultId;
					}
					if(typeof value != 'undefined')
					{
						model.value = val;
					}
					if(typeof remark != 'undefined')
					{
						model.remark1 = remark;
					}
					infoModelList.push(model);
				})
			}
		}
		else if(type == 16)
		{
			var model = {
					tochange:"true",
					type:type
				};
				model.projectId = projId;
				model.titleId = titleId;
				if(typeof resultId != 'undefined')
				{
					model.resultId = resultId;
				}
				if(typeof remark == 'undefined' || remark == '未填写')
				{
					model.remark1 = '';
				}
				else
				{
					model.remark1 = remark;
				}
				infoModelList.push(model);
		}
	});
	return infoModelList;
	
}
function getScores()
{
	var scoreData = {};
	var scores = $(".score-column input,select");
	$.each(scores,function(){
		var _this = $(this);
		var td = _this.parent();
		var titleId= td.data('titleId');
		var relateId= td.data('relateId');
		var subId = td.data('subId');
		var val = _this.val();
		if(val == "" || isNaN(val))
		{
			val = null;
		}
		var model = {
				relateId:relateId,
				projectId: projId
			};
		if(scoreData.hasOwnProperty(relateId))
		{
			model = scoreData[relateId];
		}
		if(subId == 2)
		{
			model.score2 = val;
		}
		else
		{
			model.score1 = val;
		}
		scoreData[relateId] = model;
	});
	var scoreList = new Array();
	$.each(scoreData,function(id,model){
		scoreList.push(model);
	});
	return scoreList;
}
function getTalbleData()
{
	var infoTableModelList = new Array();
	var titleEles = $(".title-value");
	$.each(titleEles,function(){
		var _this = $(this);
		var type = _this.data('type');
		var titleId = _this.data('titleId');
		if(type == 10)
		{
			var em = _this.find('em');
			if(em.length>0)
			{
				var rows = em.data('tr');
				$.each(rows,function(){
					var row = this;
					$.extend(row,{projectId:projId,titleId:titleId});
					infoTableModelList.push(row);
				});
			}
		}
	});
	
	return infoTableModelList;
	
}
$("#save-rpt-btn").click(function(){
	var data = {
		projectId: projId,
		scoreList:	getScores(),
		infoModeList: getValues(),
		infoTableModelList: getTalbleData()
	};
	sendPostRequestByJsonObj(
			platformUrl.saveOrUpdateInfo+'?_='+new Date().getTime() , 
			data,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					layer.msg('保存成功');
				} 
				else 
				{
					layer.msg(data.result.message);
				}
		})
});

//type_8  展开收起公共方法
function Tfun_8(data){
	$.each(data,function(){
		if(!data.hasClass("type_8")){
			return;
		}
		var _this = $(this);
		_this.parent().find("em").hide();
		_this.removeClass("mare_text");
		var height = _this.height();
		if(height>90){
			_this.addClass("mare_text");
			_this.parent().find(".detail").show();
		}
	})
}
//详情，展开，  收起方法
$('div').delegate(".detail","click",function(){
	$(this).closest("div").find(".type_8").removeClass("mare_text")
	$(this).hide();
	$(this).prev().show();
})
	
$('div').delegate(".packup","click",function(){
	$(this).closest("div").find(".type_8").addClass("mare_text")	
	$(this).hide();
	$(this).next().show();
})
//评测报告图片缩略图 公共方法
function img_fun(data){
	if(data==undefined){
		return;
	}
	var fi_this = $(data);
	var firsr_ul = fi_this.closest(".fl_none").find("ul:first-child");	
	var last_ul = fi_this.closest(".fl_none").find("ul:last-child");
	if (fi_this.val() != '') {
		var files = !!data.files ? data.files : [];
	    if (!files.length || !window.FileReader){return;}	    
	    if (/^image/.test(files[0].type)){
	        var reader = new FileReader();
	        reader.readAsDataURL(files[0]);
	        reader.onloadend = function(){  
        	firsr_ul.append("<li class=\"pic_list fl\"><a href=\"javascript:;\" class=\"h_img_del\" ></a><img src="+this.result+" /></li>");
	        }
	    }
	 }else{
		 firsr_ul.append("<li class=\"pic_list fl\"<a href=\"javascript:;\" class=\"h_img_del\" ></a><img src="+this.result+" /><li>");
	 }
	last_ul.find(".h_imgs_add").html("<input type=\"file\" onchange=\"img_fun(this)\"/>");
	if(firsr_ul.find("li").length>=4){
		last_ul.hide()
	 }else{
	    last_ul.show()
	 }
}

$("div").delegate(".h_img_del","click",function(){
	$(this).parents(".fl_none").find("ul").show()
	$(this).parent().remove();	

})
//图片点击弹窗
	
$('div').delegate(".income_pic","click",function(){
	$('.customer_income').show();
	var _target = $(this);
	var  leftNum = _target.offset().left-20;
	var  topNum = _target.offset().top-188;
	$('.customer_income').css('left',leftNum).css('top',topNum);
	$(".img_inner").attr("src",$(this).data("url"));
	$(".master_pic").attr("href",$(this).data("url"))
	$('.mashLayer').show();
})
//图片点击弹窗
	
$('div').delegate(".income_pic","click",function(){
	$('.customer_income').show();
	var _target = $(this);
	var  leftNum = _target.offset().left-20;
	var  topNum = _target.offset().top-188;
	$('.customer_income').css('left',leftNum).css('top',topNum);
	$(".img_inner").attr("src",$(this).data("url"));
	$(".master_pic").attr("href",$(this).data("url"))
	$('.mashLayer').show();
})
//表格预览 
function income_table(){
	$('.income_table').unbind().click(function(){
		$('.reasonable_stock').show();
		var _target = $(this);
		var  leftNum = _target.offset().left-20;
		var  topNum = _target.offset().top-188;
		$('.reasonable_stock').css('left',leftNum).css('top',topNum);
		var tr_arry = _target.data("tr");
		var tr_html="";
		$.each(tr_arry,function(i,n){
			tr_html+="<tr><td>"+n.field1+"</td><td>"+n.field2+"</td></tr>"
		})
		$('.reasonable_stock').find("tbody").append(tr_html);
		 
	})
}
$('div').delegate(".reasonable_stock .close_tab","click",function(){
	$(this).parents(".reasonable_stock").find("tbody").html("");
	$(this).parents(".reasonable_stock").hide();
})
//新增表格

//表格编辑tr方法
function s_editRow(ele)	
{	var code = $(ele).closest('table').data('code');
	var row = $(ele).closest('tr');
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			var title = $("#pop-title");
			$("#detail-form input[name='subCode']").val(code);
			$("#detail-form input[name='titleId']").val(row.parent().parent().attr("data-title-id"));
			selectContext("detail-form");
			$.each($("#detail-form .info").find("input"),function(){
				var _ele = $(this);
				var name = _ele.attr("name");
				var _td=row.find("td[data-field-name='"+name+"']");
				_ele.val(_td.text());
			});
		}//模版反回成功执行	
	});
}
function eva_validate(){
	for(var i=0;i<$(".score-column").length;i++){
		var input = $(this).find("input[type='text']");
		var rulerMarket=input.attr("rulerMarket");
		var name1="data-rule-eva-"+rulerMarket,
		name2="data-msg-eva-"+rulerMarket;
		var validate={
				name1:"true",
				"name":i,
				name2:"<font color=red>*</font>只允许输入数字0~"+rulerMarket+"整数"			
		}
		eva_validator(rulerMarket);
	}
}
function  eva_validator(data){
	$.validator.setDefaults({
		errorElement:'span'
	});
	jQuery.validator.addMethod("eva-"+data, function(value, element) {   
		var ruler = "^[1-9]\d*$";
		return this.optional(element) || (ruler.test(value));
		;
	}, "只允许输入数字0~"+data+"整数");
}