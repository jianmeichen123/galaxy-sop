
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
	else if(type == 8)
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
		$.each(results,function(){
			content.push(this.valueName);
			values.push(this.contentChoose);
		});
		_ele.html(content.join('、')); 
		_ele.attr("data-title-value",values.join(','));
		var _val_id =_ele.attr("data-title-value");
		if(_val_id!=""){
			_val_id=_val_id+",";
		}
		_ele.attr("data-title-value",_val_id);
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
	if(_scoreEle.length>0)
	{
		_scoreEle.attr("data-result-id",results[0].id);
	}
}
function getValues()
{
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
		//input,textarea
		if(type == 1 || type == 8)
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
			if(typeof text == 'undefined' || text == '未填写')
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
$("#save-rpt-btn").click(function(){
	var data = {
		projectId: projId,
		scoreList:	getScores(),
		infoModeList: getValues()
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


//评测报告图片缩略图 公共方法
function img_fun(data){
	var fi_this = $(data);
	if (fi_this.val() != '') {
		var files = !!data.files ? data.files : [];
	    if (!files.length || !window.FileReader) return;
	    if (/^image/.test( files[0].type)){
	        var reader = new FileReader();
	        reader.readAsDataURL(files[0]);
	        reader.onloadend = function(){  
	        fi_this.parent(".h_imgs_add").html("<a href=\"javascript:;\" class=\"h_img_del\" ></a><img src="+this.result+" />");
	
	        }
	    }
	 }else{
		 fi_this.parent(".h_imgs_add").html("<a href=\"javascript:;\" class=\"h_img_del\" ></a><img src="+this.result+" />");
	 }
	 var _id = fi_this.closest(".fl_none").find("ul:first-child").attr("id");
	 var _idnum = _id.split("-")[1];
	 var _htm ="<li class=\"h_imgs_add\" id=\"h_imgs_add_"+_idnum+"\"><input type=\"file\" onchange=\"img_fun(this)\" file-title-id="+_idnum+" id=\"selected_file_"+_idnum+"\"></li>"
	 var add_html= "<ul class=\"h_imgs\" id="+_id+">"+_htm+"</ul>"
	 if(fi_this.closest(".h_edit_txt").find("ul").length<6){
		 fi_this.closest(".fl_none").append(add_html);
	 }
}

$("div").delegate(".h_img_del","click",function(){
	var _id = $(this).closest(".h_imgs").attr("id");
	var _idnum = _id.split("-")[1];
	var _htm ="<li class=\"h_imgs_add\" id=\"h_imgs_add_"+_idnum+"\"><input type=\"file\" onchange=\"img_fun(this)\" file-title-id="+_idnum+" id=\"selected_file_"+_idnum+"\"></li>"
	var add_html= "<ul class=\"h_imgs\" id="+_id+">"+_htm+"</ul>";
	var _src = $(this).closest(".fl_none").find("ul:last-child").find("img").attr("src")
	console.log(_src);
	if(_src){
		$(this).closest(".fl_none").append(add_html);
	}
	$(this).closest(".h_imgs").remove();
})

