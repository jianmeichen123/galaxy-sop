
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
			$(".content_16").text(content_16); 
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
						buildResult(this)
					});
					initScore(relateId);
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
	var data = {
		"parentId": 0	,
		"relateId": rid,
		"reportType": 1,
		"projectId":projId
	};
	var items = new Array();
	$(".title-value").each(function(){
		var _this = $(this);
		var relateId = _this.data('relateId');
		var subId = typeof _this.data('subId')=='undefined' ? null:_this.data('subId');
		var values = getTitleValue(relateId);
		var score = getScore(relateId,subId);
		items.push({
			"relateId": relateId,
			"subId": subId,
			"values": values,
			"score": score
		});
	});
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
	//Radio
	if(type == 2 || type==14)
	{
		_ele.attr('data-title-value',results[0].contentChoose)
		_ele.text(results[0].valueName);
		_ele.attr("data-result-id",results[0].id);
	}
	//文本域
	else if(type == 8)
	{
		_ele.text(results[0].contentDescribe1);
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
		$.each(results,function(){
			content.push(this.valueName);
			values.push(this.contentChoose);
		});
		_ele.html(content.join('、'));
		_ele.attr("data-title-value",values.join(','));
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
	}
}
$("#save-rpt-btn").click(function(){
	var data = {};
	var eles = $(".title-value");
	var infoModeList = new Array();
	$.each(eles,function(){
		var _this = $(this);
		var type = _this.data('type');
		var titleId = _this.data('titleId');
		var value = _this.data('titleValue');
		var resultId = _this.data('resultId');
		var model = {};
		model.projectId = projId;
		model.titleId = titleId;
		if(typeof resultId != 'undefined')
		{
			model.resultId = resultId;
		}
		if(type == 2 || type==14)
		{
			model.value = value;
		}
		infoModeList.push(model);
		
	});
	data.infoModeList = infoModeList;
	console.log(data);
})

