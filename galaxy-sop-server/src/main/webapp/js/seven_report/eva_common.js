
/**
 * 加载标题
 */
$("#eva-tabs li").click(function(){
	var $li = $(this);
	var code = $li.data('code');
	var relateId = $li.data('relateId');
	$(".pagebox").attr("data-lis","tab");  //区分离开页面时，点击的是tab标签
	$(".pagebox").attr("data-code",code);  //存当前li的code
	$(".pagebox").attr("data-relateId",relateId);   //存当前li的relateId
	//离开二次确认
	var _href=window.location.href;
	if((_href=platformUrl.toEvalindex) || (_href=platformUrl.toPreEva)){   //判断评测报告或初评报告
		var result=$(".pagebox").attr("data-result");
		if(result=="true"){   //数据变化弹出二次确认弹窗
			//$(window).unbind('beforeunload');
			beforeSave();
		}else{
			//$(window).unbind('beforeunload');
			//加载新页面
			if($li.hasClass('active'))
			{
				return;
			}
			$li.siblings().removeClass('active');
			$li.addClass('active');
			tabShow(code,relateId);
		}
	}
	
});
//加载tab页
function tabShow(code,relateId){
	sendGetRequest(platformUrl.queryAllTitleValues+code+"?reportType="+reportType, null,
			function(data){
			var result = data.result.status;
			if (result == 'OK') {
				$('#page_all').empty();
				var entity = data.entity;
				$("#part-title-name").text(entity.name);
				$("#test_tmpl").tmpl(entity).appendTo('#page_all');
				$(".pagebox").attr("data-result",false);  
				$("#save-rpt-btn").attr("disabled",true);
				/*显示结果  */
				/* 16类型内容处理 */				
				var content_16 = $(".content_16 p").html();
				if(content_16!="未填写"&&content_16!=undefined){
					content_16=content_16.replace(/<sitg>/g,'（<sitg>');
					content_16=content_16.replace(/<\/sitg>/g,'<\/sitg>）');
					$(".content_16 p").html(content_16); 
				}
				//显示结果和分数向
				showResultAndScoreList(relateId);
				 //修改分数时自动计算
				$(".score-column select,input").click(function(){
					$(".pagebox").attr("data-result",true);
					$("#save-rpt-btn").removeAttr("disabled"); 
					$("#save-rpt-btn em").removeClass("disabled")
				})
				 $(".score-column select,input").change(function(){
					 if(!$("#table_box").validate().form())
						{
							return false;
						}else{
							calcScore();
						}
					 
				 });
				//离开页面提示
				 $(window).bind('beforeunload',function(){ 
				 	if(checkCon()){
				 		return '您输入的内容尚未保存，确定离开此页面吗？'; 
				 	}
				 	});

				 function checkCon(){   //判断内容是否更改
				 	var result=false;
				 	var dataResult=$(".pagebox").attr("data-result");
				 	if(dataResult=="true"){
				 		result=true;
				 	}
				 	return result;
				 }

			}
		});
	
	$.getTabHtml({
		url : platformUrl.toOperateInfo ,
		okback:function(){
			right_anchor(code+"?reportType=1","seven","show");
		}
	});
	
}
//编辑按钮显示
function　mouserover(obj){
	if(isEditable != 'true') return;
	if($(obj).data('edit') == 'true') return;
	var target = $(obj).find('.editPic');
	 target.show();
};
function mouseout(obj){
	if(isEditable != 'true') return;
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
						buildFileList(this);
					});

					//权限判断， 没有编辑权限
                    if(!isEditable || isEditable != 'true'){
                        $("#save-rpt-btn").remove();
                        $.each($(".score-column"),function(){
                            var _input =$(this).find("input");
                            var _select =$(this).find("select");
                            if(_input.val()==""){
                                $(this).html("未打分");
                                $(this).css("color","#b2b2b2")
                            }else{
                                $(this).html(_input.val());
                            }
                            if(_select.val()=="请选择"){
                                $(this).html("未打分");
                                $(this).css("color","#b2b2b2")
                            }else{
                                $(this).html(_select.val());
                            }
                        })
					}


//					角色判断
					/*if(roleId== 1 || roleId == 2|| roleId== 3){
						$("#save-rpt-btn").remove();
						$.each($(".score-column"),function(){
							var _input =$(this).find("input");
							var _select =$(this).find("select");
							if(_input.val()==""){
								$(this).html("未打分");
								$(this).css("color","#b2b2b2")
							}else{
								$(this).html(_input.val());
							}
							if(_select.val()=="请选择"){
								$(this).html("未打分");
								$(this).css("color","#b2b2b2")
							}else{
								$(this).html(_select.val());
							}
						})
					}*/
					//权重==0的时候有红字
					var part_weight =$("#part-weight").text();
					if(part_weight=="0%"){
						$(".new_tit_b p").last().remove();
						$(".new_tit_b").append("<p>请选择本轮的融资轮次以查看正确的评测结果</p>")
					}
					
					
					initScore(relateId);
					Tfun_8($(".type_8"));	
					eva_validate();
					$("#table_box").validate();
					//渲染数据结束对字体颜色进行操作
					font_color($(".condition"));
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
			score=score.toFixed(2);
			$("#total-score").text(score);
		}
		else if(rid == relateId)
		{
			$("#part-score").text(score);
		}
		else
		{
			var td = $('td.score-column[data-relate-id="'+rid+'"]');
			if(rid.indexOf('-')>0)
			{
				var arr = rid.split('-');
				rid = arr[0];
				var subId = arr[1];
				td = $('td.score-column[data-relate-id="'+rid+'"][data-sub-id="'+subId+'"]');
			}
			var ele = td.children('input,select');
			if(ele.length ==0)
			{
				td.text(score);
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
	else if(type == 1 || type == 8|| type == 18|| type == 20)
	{
		if(_sign=="sign_3"){
			var val = results[0].contentDescribe1;
			var currency_id = results[0].contentDescribe2;
			if(currency_id!=undefined){
				var currency=currency_id.split("p")[0];
			}
			if(type == 20){
				if(val==""||val==undefined){
					val="未填写";
					currency_id="";
				}else{
					val=val+"万元"+currency; 
				}
				_ele.find("span").attr("currency",currency_id);
			 }
			_ele.find("span").html(val);
		}else{
			_ele.html(results[0].contentDescribe1);
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
		if(_sign=="sign_3"){
			_ele.find("span").html(content.join('、')); 
		}else{
			if(content==""||content==undefined){
				_ele.html("未选择"); 
			}else{
				_ele.html(content.join('、')); 
			}
		}
		_ele.attr("data-title-value",values.join(','));
		_ele.attr("data-remark",remark);
	}
	else if(type == 12)
	{
		var text = results[0].valueName;
		if(text == '其他')
		{
			text = results[0].contentDescribe1;
			remark = results[0].contentDescribe1;
		}
		if(_sign=="sign_3"){
			_ele.find("span").text(text);
		}else{
			_ele.text(text);
		}
		_ele.attr('data-title-value',results[0].contentChoose)
		_ele.attr("data-result-id",results[0].id);
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
			val=val.replace(/<sitg>/g,'（<sitg>').replace(/<\/sitg>/g,'<\/sitg>）');
			$(_ele).each(function(){
				if($(this).data("relateId")=="1006"){
					val=val.split("的产品或服务，");
					val = val[1];
				}
				$(this).html(val);
			})
			
		}
		_ele.attr("data-result-id",results[0].id);
	}
	
	
	if(_scoreEle.length>0)
	{
		_scoreEle.attr("data-result-id",results[0].id);
	}
}
function buildFileList(title)
{
	var _ele = $('.title-value[data-title-id="'+title.id+'"]');
	var fileList = title.fileList;
	var type = title.type;
	var span = _ele.find('span');
	if(typeof fileList == 'undefined' || fileList.length == 0)
	{
		return;
	}
	if(type == 7)
	{
		span.empty();
		$.each(fileList,function(){
			var em = $('<em>[图片]</em>');
			em.addClass('income_pic');
			em.attr('data-file-id',this.id);
			em.attr('data-file-url',this.fileUrl);
			span.append(em);
		});
		
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
	
	var titleObj = {};
	var infoModelList = new Array();
	var titleEles = $(".title-value");
	var dupCheck = {};
	$.each(titleEles,function(){
		var _this = $(this);
		var type = _this.data('type');
		var titleId = _this.attr('data-title-id');
		var subId = _this.data('subId');
		var value = _this.attr('data-title-value');
		var resultId = _this.attr('data-result-id');
		var remark = _this.attr('data-remark');
		var text = _this.text();
		if(_this.parent().hasClass('sign_3'))
		{
			_this= _this.find('span');
			text = _this.text();
		}
		//input,textarea
		if(type == 1 || type == 8||type == 18||type == 20)
		{
			var model = {
				tochange:"true",
				type:type
			};		
			text = _this.html();
			if(type == 20){				
				text=text.replace("万元人民币","");
				text=text.replace("万元美元","");
				model.remark2 =_this.attr("currency") ;
			}			
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
		else if(type == 2 || type == 5 ||type==12 ||type==14)
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
				value=value+","
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

					if(i + 1 == values.length){
                        if(typeof remark != 'undefined')
                        {
                            model.remark1 = remark;
                        }
					}
					var key = titleId+'_'+val;
					if(!dupCheck.hasOwnProperty(key))
					{
						dupCheck[key]=model;
						infoModelList.push(model);
					}	
				})
			}
		}
		else if(type == 15)
		{
			var model;
			if(titleObj.hasOwnProperty(titleId))
			{
				model=titleObj[titleId];
			}
			else
			{
				model = {
						tochange:"true",
						type:type,
						projectId: projId,
						titleId: titleId
					};
			}
			if(typeof text == 'undefined' || text == '未填写'|| text == '未选择')
			{
				text = '';
			}
			if(typeof resultId != 'undefined')
			{
				model.resultId = resultId;
			}
			if(subId == 2)
			{
				model['remark2']=text;
			}
			else
			{
				model['remark1']=text;
			}
			titleObj[titleId]=model;
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
	$.each(titleObj,function(key){
		infoModelList.push(titleObj[key]);
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
var deleteFileIds = new Array();
$("#save-rpt-btn").click(function(){
	//判断是否让其进行保存
	var editbox = $(".radioShow");	
	var edit_status = false;
	var scores=$(".score-columns");
	var scores_status = false;
	$.each(editbox,function(){
		if(!$(this).is(":hidden")){
			edit_status=true;
			return edit_status; 
			return false;
		}
		
	})
	$.each(scores,function(){
		if($(this).find(".error").length>0&&$(this).find(".error").is(":visible")){
			scores_status=true;
			return scores_status; 
			return false;
		}
		
	})
	if(scores_status==true){
		layer.msg("打分错误无法保存");
	}else if(edit_status==true){
		layer.msg("正在编辑无法保存");
	}else{
	var data = {
		projectId: projId,
		scoreList:	getScores(),
		infoModeList: getValues(),
		infoTableModelList: getTalbleData(),
		infoFileList: getImageList(),
		deletedRowIds: deletedRowIds,
		deleteFileIds: deleteFileIds
	};
	sendPostRequestByJsonObj(
			platformUrl.saveOrUpdateInfo+'?_='+new Date().getTime() , 
			data,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var relateId = $("#eva-tabs li.active").data('relateId');
					showResultAndScoreList(relateId);
					deletedRowIds=[];
					deleteFileIds=[];
					$(".pagebox").attr("data-result",false);
					$("#save-rpt-btn").attr("disabled",true);
					$("#save-rpt-btn em").addClass("disabled");
					layer.msg('保存成功');
				} 
				else 
				{
					layer.msg(data.result.message);
				}
		})
		
}
});
function getImageList()
{
	var infoFileList = new Array();
	var imgs = $(".title-value .income_pic");
	$.each(imgs,function(){
		var _this = $(this);
		var fileId = _this.data('fileId');
		var titleId = _this.closest('p').data('titleId');
		var data = _this.data('url');
		var model = {
			titleId: titleId,
			projectId: projId,
			data: data
		};
		if(typeof fileId != 'undefined')
		{
			model.id = fileId;
		}
		infoFileList.push(model);
	});
	return infoFileList;
}
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
		if(height>70){
			_this.addClass("mare_text");
			_this.parent().find(".detail").show();
			
		}
	})
}
//详情，展开，  收起方法
$('div').delegate(".detail","click",function(){
	$(this).closest("div").find(".type_8").removeClass("mare_text")
	$(this).hide();
	$(this).prev().css("display","inline");
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
	var fileSize = 0;
	var fi_this = $(data);
	var firsr_ul = fi_this.closest(".fl_none").find("ul:first-child");	
	var last_ul = fi_this.closest(".fl_none").find("ul:last-child");
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
	//判断文件大小
	 if (isIE && !data.files) {     
	       var filePath = data.value;     
	       var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
	       var file = fileSystem.GetFile (filePath);     
	       fileSize = file.Size;    
	     } else {    
	      fileSize = data.files[0].size;     
	      }   
	      var size = fileSize / 1024;    
	      if(size>2000){  
    		layer.msg("图片不能大于2M");
	        data.value="";
	       return
	      }
	  	//
	if (fi_this.val() != '') {
		
		var files = !!data.files ? data.files : [];
	    if (!files.length || !window.FileReader){return;}	    
	    if (/^image/.test(files[0].type)){
	        var reader = new FileReader();
	        reader.readAsDataURL(files[0]);
	        reader.onloadend = function(){  
        	firsr_ul.append("<li class=\"pic_list fl\"><a href=\"javascript:;\" class=\"h_img_del\" ></a><img src="+this.result+" /></li>");
	        }
	    }else{
	    	return;
	    }
	 }else{
		 firsr_ul.append("<li class=\"pic_list fl\"<a href=\"javascript:;\" class=\"h_img_del\" ></a><img src="+this.result+" /><li>");
	 }
	last_ul.find(".h_imgs_add").html("<input type=\"file\" onchange=\"img_fun(this)\" accept=\"image/png,image/gif,image/jpeg,image/jpg,image/BMP\"/>");
	
	
	if(firsr_ul.find("li").length>=4){
		last_ul.hide()
	 }else{
	    last_ul.show()
	 }
}

$("div").delegate(".h_img_del","click",function(){
	$(this).parents(".fl_none").find("ul").show()
	$(this).parent().remove();
	var del_id = $(this).next().data("id");
	deleteFileIds.push(del_id);
})
//图片点击弹窗
$('div').delegate(".income_pic","click",function(){
	$('.customer_income').show();
	var _target = $(this);
	var  leftNum = _target.offset().left-20;
	var  topNum = _target.offset().top-188;
	$('.customer_income').css('left',leftNum).css('top',topNum);
	var im_url=$(this).data("fileUrl");
	if(im_url==undefined||im_url==""){
		$(".img_inner").attr("src",$(this).data("url"));
	}else{
		$(".img_inner").attr("src",$(this).data("fileUrl"));
	}
	
})
//图片点击原图效果
$('div').delegate(".master_pic","click",function(){
	$(".mashLayer").show().css('opacity',0.5);
	var  _src = $(this).prev().attr("src");
	var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
	$(".o_picture").attr("src",_src).show().css('top',scrollTop+70);
	$(".x_picture").show();
	$(".x_picture").click( function(){
		$(".x_picture,.mashLayer,.o_picture").hide();
		$(".o_picture").attr("src","")
	})
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
			$("#detail-form input[name='index']").val(row.index());
			$("#save-detail-btn").click(function(){
				saveForm($("#detail-form"));
			});
		}//模版反回成功执行	
	});
}
//字体颜色进行操作以及对打分进行限制
function font_color(data){
	$.each(data,function(){
		var _this=$(this).find(".align_left").find("p");
		var sign_3 = $(this).find(".align_left").find(".sign_3")
		var sign_status = _this.hasClass("income_structor_content");
		var code_dom =_this.closest("td").siblings(".score-column").find("input,select");
		var code_input = _this.closest("td").siblings(".score-column").find("input");
		var code_select = _this.closest("td").siblings(".score-column").find("select");
		if(sign_status){
			//多道题 sign=3
			var num=0;
			$.each(sign_3,function(){
				var _span = $(this).find("span").text();				
				if(_span=="未填写"||_span=="未选择"||_span=="未添加"){
					$(this).find("span").removeClass("black");			
					num++;
				}else{
					$(this).find("span").addClass("black");
				}
			})
			if(num>0){
				code_dom.addClass("disabled").attr("disabled",true);
				code_dom.val("");
				code_select.val("请选择");
			}else{
				code_dom.removeClass("disabled").attr("disabled",false);
			}
		}else{
			if(_this.text()=="未填写"||_this.text()=="未选择"||_this.text()=="未添加"){
				_this.removeClass("black");			
				code_dom.addClass("disabled").attr("disabled",true);
				code_dom.val("");
				code_select.val("请选择");
			}else{
				_this.addClass("black");
				code_dom.removeClass("disabled").attr("disabled",false);
			}
		}
		//16类型特殊处理
		var clean_status=0;
		if(_this.parent().hasClass("content_16")){
			code_dom.addClass("disabled").attr("disabled",true);
			var sitg=_this.find("sitg");			
			$.each(sitg,function(i,n){
				if($(this).html()!=""){
					code_dom.removeClass("disabled").attr("disabled",false);
					clean_status=1;
					return clean_status;
					return false;
				}
			})
			if(clean_status==0){
				code_dom.val("");
				code_select.val("请选择");
			}
		}
		if(_this.data("relateId")=="1006"){
			code_input.addClass("disabled").attr("disabled",true);
			var sitg=_this.find("sitg");
			var clean_status=0;
			$.each(sitg,function(i,n){
				var n_this=$(this)
				var len = sitg.length;
				if(i==len-1||i==len-2){
					if(n_this.text()!=""){
						code_input.removeClass("disabled").attr("disabled",false);
						clean_status=1;
						return clean_status;
						return false;
					}
				}				
			})
			if(clean_status==0){
				code_dom.val("");
				code_select.val("请选择");
			}
		}
		
	})
}
